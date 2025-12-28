package net.m3tte.ego_weapons.skill.rat;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsModElements;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.RatShankMovesetAnims;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCategories;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.EnergizingGuardSkill;
import yesman.epicfight.skill.GuardSkill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillDataManager.SkillDataKey;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.HurtEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

import static net.m3tte.ego_weapons.skill.GenericActiveGuard.canParryHeavy;
import static yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

public class RatKnifeGuard extends EnergizingGuardSkill {
    public RatKnifeGuard(Builder builder) {
        super(builder);
    }

    private static final SkillDataManager.SkillDataKey<Integer> PARRY_MOTION_COUNTER;

    private static final SkillDataKey<Integer> LAST_ACTIVE;
    public static Builder createBuilder(ResourceLocation resourceLocation) {
        return GuardSkill.createBuilder(resourceLocation)
                .addAdvancedGuardMotion(EgoWeaponsCategories.RAT_KNIFE, (item, player) -> new StaticAnimation[]{RatShankMovesetAnims.RAT_KNIFE_PARRY_1, RatShankMovesetAnims.RAT_KNIFE_PARRY_2})
                .addGuardMotion(EgoWeaponsCategories.RAT_KNIFE, (item, player) -> RatShankMovesetAnims.RAT_KNIFE_PARRY_1)
                .addGuardBreakMotion(EgoWeaponsCategories.RAT_KNIFE, (item, player) -> BlackSilenceMovesetAnims.RANGA_GUARD_STAGGER);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getDataManager().registerData(LAST_ACTIVE);
        container.getDataManager().registerData(PARRY_MOTION_COUNTER);
        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.SERVER_ITEM_USE_EVENT, EVENT_UUID, (event) -> {
            CapabilityItem itemCapability = event.getPlayerPatch().getHoldingItemCapability(Hand.MAIN_HAND);
            if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.GUARD) && this.isExecutableState(event.getPlayerPatch())) {
                event.getPlayerPatch().getOriginal().startUsingItem(Hand.MAIN_HAND);
            }

            container.getDataManager().setData(LAST_ACTIVE, event.getPlayerPatch().getOriginal().tickCount);
        });

        container.getExecuter().getEventListener().addEventListener(EventType.SERVER_ITEM_STOP_EVENT, EVENT_UUID, (event) -> {
            ServerPlayerEntity serverplayer = event.getPlayerPatch().getOriginal();
            container.getDataManager().setDataSync(LAST_HIT_TICK, serverplayer.tickCount, serverplayer);
            if (serverplayer.tickCount - container.getDataManager().getDataValue(LAST_ACTIVE) < 30) {
                container.getDataManager().setDataSync(PENALTY, container.getDataManager().getDataValue(PENALTY) + 0.4f, serverplayer);
                event.getPlayerPatch().playSound(SoundEvents.ITEM_BREAK, 0.6f, 0.5F, 0.7F);

            }

        });
    }
    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecuter().getEventListener().removeListener(EventType.SERVER_ITEM_USE_EVENT, EVENT_UUID);
        super.onRemoved(container);
    }





    @Override
    public void guard(SkillContainer container, CapabilityItem itemCapapbility, HurtEvent.Pre event, float knockback, float impact, boolean advanced) {
        boolean canUse = this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapapbility, BlockType.ADVANCED_GUARD);
        DamageSource damageSource = event.getDamageSource();

        if (damageSource.isExplosion()) {
            impact = event.getAmount();
        }


        if (this.isBlockableSource(damageSource, advanced)) {

            float penalty = container.getDataManager().getDataValue(PENALTY);

            boolean successParrying = event.getPlayerPatch().getOriginal().tickCount - container.getDataManager().getDataValue(LAST_ACTIVE) < 8;

            if (isRudimentaryBlockable(damageSource, advanced) && successParrying) {
                penalty = penalty + 0.05f;
                container.getDataManager().setDataSync(LAST_ACTIVE, container.getDataManager().getDataValue(LAST_ACTIVE) - 30, event.getPlayerPatch().getOriginal());

            }
            ServerPlayerEntity serverPlayer = event.getPlayerPatch().getOriginal();




            if (damageSource.getEntity() instanceof LivingEntity) {
                knockback += EnchantmentHelper.getKnockbackBonus((LivingEntity)damageSource.getEntity()) * 0.1F;
            }


            event.getPlayerPatch().knockBackEntity(damageSource.getEntity().position(), knockback);

            float stamina = event.getPlayerPatch().getStamina() - penalty * impact;

            StaticAnimation animation = null;
            BlockType blockType = (stamina >= 0.0F) ? BlockType.GUARD : BlockType.GUARD_BREAK;




            if (blockType == BlockType.GUARD && successParrying) {

                if (EgoWeaponsEffects.SPEED_UP.get().getPotency(serverPlayer) >= 2) {
                    animation = RatShankMovesetAnims.RAT_KNIFE_EVADE;
                    stamina = event.getPlayerPatch().getStamina() - 0.3f;
                    EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(serverPlayer, 3, 1);
                } else {
                    event.getPlayerPatch().playSound(EpicFightSounds.CLASH, -0.05F, 0.1F);
                    EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(((ServerWorld)serverPlayer.level), HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, serverPlayer, damageSource.getDirectEntity());
                    blockType = canParryHeavy(true, event.getPlayerPatch(), blockType, stamina, impact, event, penalty);
                    animation = this.getGuardMotion(event.getPlayerPatch(), itemCapapbility, blockType);
                }

            }

            if (blockType == BlockType.GUARD_BREAK) {
                animation = this.getGuardMotion(event.getPlayerPatch(), itemCapapbility, blockType);
            }
            container.getDataManager().setDataSync(PENALTY, penalty, event.getPlayerPatch().getOriginal());
            event.getPlayerPatch().setStamina(stamina);
            if (animation != null) {
                event.getPlayerPatch().playAnimationSynchronized(animation, 0.0F);
            }

            if (blockType == BlockType.GUARD_BREAK) {
                event.getPlayerPatch().playSound(EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:stagger", ':')),3,  -0.05F, 0.1F);
                event.getPlayerPatch().playSound(EpicFightSounds.NEUTRALIZE_MOBS, 3.0F, 0.0F, 0.1F);
            }
            EmotionSystem.handleGuard(serverPlayer, event.getAmount(), impact, successParrying, event.getDamageSource().getEntity());
            this.dealEvent(event.getPlayerPatch(), event, !successParrying);
        }
    }


    @Nullable
    protected StaticAnimation getGuardMotion(PlayerPatch<?> playerpatch, CapabilityItem itemCapability, GuardSkill.BlockType blockType) {
        if (blockType == BlockType.ADVANCED_GUARD) {
            StaticAnimation[] motions = (StaticAnimation[])((StaticAnimation[])((BiFunction)this.getGuradMotionMap(blockType).getOrDefault(itemCapability.getWeaponCategory(), (a, b) -> null)).apply(itemCapability, playerpatch));
            if (motions != null) {
                SkillDataManager dataManager = playerpatch.getSkill(this.getCategory()).getDataManager();
                int motionCounter = (Integer)dataManager.getDataValue(PARRY_MOTION_COUNTER);
                dataManager.setDataF(PARRY_MOTION_COUNTER, (v) -> v + 1);
                motionCounter %= motions.length;
                return motions[motionCounter];
            }
        }

        return super.getGuardMotion(playerpatch, itemCapability, blockType);
    }

    public void dealEvent(PlayerPatch<?> playerpatch, HurtEvent.Pre event, boolean attackPass) {
        event.setAmount(attackPass ? event.getAmount() * 0.0F : 0.0F);
        event.setResult(attackPass ? AttackResult.ResultType.SUCCESS : AttackResult.ResultType.BLOCKED);
        if (event.getDamageSource() instanceof ExtendedDamageSource) {
            ((ExtendedDamageSource)event.getDamageSource()).setStunType(ExtendedDamageSource.StunType.NONE);
        }


        if (!attackPass)
            event.setCanceled(true);

        //event.setCanceled(true);
        Entity directEntity = ((DamageSource)event.getDamageSource()).getDirectEntity();
        LivingEntityPatch<?> entitypatch = (LivingEntityPatch)directEntity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).orElse((EntityPatch) null);
        if (entitypatch != null) {
            entitypatch.onAttackBlocked(event, playerpatch);
        }

    }

    protected boolean isRudimentaryBlockable(DamageSource damageSource, boolean advanced) {
        return !damageSource.isBypassArmor() && !damageSource.isBypassInvul() && !damageSource.isProjectile() && !damageSource.isExplosion() && !damageSource.isMagic() && !damageSource.isFire();
    }

    @Override
    public float getPenaltyMultiplier(CapabilityItem itemCap) {
        return 0.5F;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldDraw(SkillContainer container) {
        return container.getExecuter().getHoldingItemCapability(Hand.MAIN_HAND).getWeaponCategory() == EgoWeaponsCategories.RAT_KNIFE && (Float)container.getDataManager().getDataValue(PENALTY) > 0.0F;
    }

    protected boolean isAdvancedGuard() {
        return false;
    }

    static {
        LAST_ACTIVE = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
        PARRY_MOTION_COUNTER = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
    }
}

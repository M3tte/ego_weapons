package net.m3tte.tcorp.skill.AtelierPistol;

import net.m3tte.tcorp.TcorpModElements;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.gameasset.TCorpAnimations;
import net.m3tte.tcorp.world.capabilities.item.TCorpCategories;
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

import static yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

public class BlacksilenceAtelierGuard extends EnergizingGuardSkill {
    public BlacksilenceAtelierGuard(Builder builder) {
        super(builder);
    }

    private static final SkillDataKey<Integer> LAST_ACTIVE = SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
    public static Builder createBuilder(ResourceLocation resourceLocation) {
        return GuardSkill.createBuilder(resourceLocation)
                .addAdvancedGuardMotion(TCorpCategories.ATELIER_REVOLVER, (item, player) -> TCorpAnimations.ATELIER_REVOLVER_GUARD_HIT)
                .addGuardMotion(TCorpCategories.ATELIER_REVOLVER, (item, player) -> TCorpAnimations.ATELIER_REVOLVER_GUARD_HIT)
                .addGuardBreakMotion(TCorpCategories.ATELIER_REVOLVER, (item, player) -> TCorpAnimations.RANGA_GUARD_STAGGER);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getDataManager().registerData(LAST_ACTIVE);
        container.getExecuter().getEventListener().addEventListener(EventType.SERVER_ITEM_USE_EVENT, EVENT_UUID, (event) -> {
            CapabilityItem itemCapability = event.getPlayerPatch().getHoldingItemCapability(Hand.MAIN_HAND);

            ServerPlayerEntity player = event.getPlayerPatch().getOriginal();

            if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.GUARD) && this.isExecutableState(event.getPlayerPatch())) {
                player.startUsingItem(Hand.MAIN_HAND);
            }


            if (0 >= (player.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                    .orElse(new TcorpModVariables.PlayerVariables())).globalcooldown && !((player.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                    .orElse(new TcorpModVariables.PlayerVariables())).firingMode)) {
                container.getDataManager().setData(LAST_ACTIVE, player.tickCount);
            }

        });

        container.getExecuter().getEventListener().addEventListener(EventType.SERVER_ITEM_STOP_EVENT, EVENT_UUID, (event) -> {
            ServerPlayerEntity serverplayer = event.getPlayerPatch().getOriginal();
            container.getDataManager().setDataSync(LAST_HIT_TICK, serverplayer.tickCount, serverplayer);
            if (serverplayer.tickCount - container.getDataManager().getDataValue(LAST_ACTIVE) < 30 &&  container.getDataManager().getDataValue(PENALTY) < 0.2f) {
                container.getDataManager().setDataSync(PENALTY, container.getDataManager().getDataValue(PENALTY) + 0.5f, serverplayer);
                event.getPlayerPatch().playSound(SoundEvents.ITEM_BREAK, 0.6f, 0.5F, 0.7F);
            }

        });
    }
    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecuter().getEventListener().removeListener(EventType.SERVER_ITEM_USE_EVENT, EVENT_UUID);
        super.onRemoved(container);
    }


    private static boolean isSpecialDamageSource(DamageSource damageSource) {
        return (damageSource.isExplosion() || damageSource.isMagic() || damageSource.isFire() || damageSource.isProjectile()) && (!damageSource.isBypassArmor());
    }

    public void dealEvent(PlayerPatch<?> playerpatch, HurtEvent.Pre event, boolean parrySuccess) {

        float mult = (parrySuccess ? 0 : 1);
        boolean isSpecialSource = isSpecialDamageSource(event.getDamageSource());
        event.setAmount(isSpecialSource ? event.getAmount() * 0.6F * mult : 0.4F * mult);

        event.setResult(parrySuccess ? AttackResult.ResultType.FAILED : AttackResult.ResultType.SUCCESS);
        if (event.getDamageSource() instanceof ExtendedDamageSource) {
            ((ExtendedDamageSource)event.getDamageSource()).setStunType(ExtendedDamageSource.StunType.NONE);
        }

        event.setCanceled(true);
        Entity directEntity = event.getDamageSource().getDirectEntity();
        LivingEntityPatch<?> entitypatch = (LivingEntityPatch)directEntity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).orElse((EntityPatch) null);
        if (entitypatch != null) {
            entitypatch.onAttackBlocked(event, playerpatch);
        }

    }


    @Override
    public void guard(SkillContainer container, CapabilityItem itemCapapbility, HurtEvent.Pre event, float knockback, float impact, boolean advanced) {
        boolean canUse = this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapapbility, BlockType.ADVANCED_GUARD);
        DamageSource damageSource = event.getDamageSource();

        if (damageSource.isExplosion()) {
            impact = event.getAmount();
        }

        ServerPlayerEntity serverPlayer = event.getPlayerPatch().getOriginal();
        if (this.isBlockableSource(damageSource, advanced) && (serverPlayer.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new TcorpModVariables.PlayerVariables())).gunMagSize < 2)  {


            float penalty = container.getDataManager().getDataValue(PENALTY) + this.getPenaltyMultiplier(itemCapapbility);

            boolean successParrying = event.getPlayerPatch().getOriginal().tickCount - container.getDataManager().getDataValue(LAST_ACTIVE) < 6;



            if (isRudimentaryBlockable(damageSource, advanced) && container.getDataManager().getDataValue(PENALTY) < 0.2f && successParrying) {
                if (event.getDamageSource().getEntity() instanceof LivingEntity) {
                    event.getPlayerPatch().playSound(TcorpModElements.sounds.get(ResourceLocation.of("tcorp:guard_win", ':')), -0.05F, 0.1F);
                    event.getPlayerPatch().playSound(TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.evade", ':')), -0.05F, 0.1F);
                    LivingEntity entitySource = (LivingEntity) event.getDamageSource().getEntity();
                    LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) entitySource.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

                    if (entitypatch != null) {
                        if (entitypatch.getHitAnimation(ExtendedDamageSource.StunType.SHORT) != null) {
                            entitypatch.playAnimationSynchronized(entitypatch.getHitAnimation(ExtendedDamageSource.StunType.SHORT), 1f);
                        }
                        entitypatch.knockBackEntity(event.getPlayerPatch().getOriginal().position(), 0.1f);
                    }


                    container.getDataManager().setDataSync(LAST_ACTIVE, container.getDataManager().getDataValue(LAST_ACTIVE) - 30, event.getPlayerPatch().getOriginal());


                    EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(((ServerWorld)serverPlayer.level), HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, serverPlayer, damageSource.getEntity());

                    penalty = penalty + 0.4f;

                }
            }
            else {
                event.getPlayerPatch().playSound(EpicFightSounds.BLUNT_HIT, -0.5F, -0.3F);
                successParrying = false;
            }





            if (damageSource.getEntity() instanceof LivingEntity) {
                knockback += EnchantmentHelper.getKnockbackBonus((LivingEntity)damageSource.getEntity()) * 0.1F;
            }


            event.getPlayerPatch().knockBackEntity(damageSource.getEntity().position(), knockback);

            float stamina = event.getPlayerPatch().getStamina() - penalty * impact;
            event.getPlayerPatch().setStamina(stamina);
            StaticAnimation animation;
            BlockType blockType = (stamina >= 0.0F) ? BlockType.GUARD : BlockType.GUARD_BREAK;


            if (container.getDataManager().getDataValue(PENALTY) < 0.2 && successParrying) {
                animation = TCorpAnimations.ATELIER_REVOLVER_COUNTER;
            } else {
                animation = this.getGuardMotion(event.getPlayerPatch(), itemCapapbility, blockType);
            }
            container.getDataManager().setDataSync(PENALTY, penalty, event.getPlayerPatch().getOriginal());

            if (animation != null) {
                event.getPlayerPatch().playAnimationSynchronized(animation, 0.0F);
            }

            if (blockType == BlockType.GUARD_BREAK) {
                event.getPlayerPatch().playSound(TcorpModElements.sounds.get(ResourceLocation.of("tcorp:stagger", ':')),3,  -0.05F, 0.1F);
                event.getPlayerPatch().playSound(EpicFightSounds.NEUTRALIZE_MOBS, 3.0F, 0.0F, 0.1F);
            }

            this.dealEvent(event.getPlayerPatch(), event, successParrying);
        }
    }

    protected boolean isRudimentaryBlockable(DamageSource damageSource, boolean advanced) {
        return !damageSource.isBypassArmor() && !damageSource.isBypassInvul() && !damageSource.isProjectile() && !damageSource.isExplosion() && !damageSource.isMagic() && !damageSource.isFire();
    }

    @Override
    public float getPenaltyMultiplier(CapabilityItem itemCap) {
        return 0.1F;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldDraw(SkillContainer container) {
        return container.getExecuter().getHoldingItemCapability(Hand.MAIN_HAND).getWeaponCategory() == TCorpCategories.ATELIER_REVOLVER && (Float)container.getDataManager().getDataValue(PENALTY) > 0.0F;
    }

    protected boolean isAdvancedGuard() {
        return false;
    }
}

package net.m3tte.tcorp.skill.AtelierShotgun;

import net.m3tte.tcorp.TCorpSounds;
import net.m3tte.tcorp.TcorpModElements;
import net.m3tte.tcorp.gameasset.TCorpAnimations;
import net.m3tte.tcorp.world.capabilities.EmotionSystem;
import net.m3tte.tcorp.world.capabilities.item.TCorpCategories;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
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

public class AtelierShotgunGuard extends EnergizingGuardSkill {
    public AtelierShotgunGuard(Builder builder) {
        super(builder);
    }

    private static final SkillDataKey<Integer> LAST_ACTIVE = SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
    public static Builder createBuilder(ResourceLocation resourceLocation) {
        return GuardSkill.createBuilder(resourceLocation)
                .addAdvancedGuardMotion(TCorpCategories.ATELIER_SHOTGUN, (item, player) -> TCorpAnimations.ATELIER_SHOTGUN_GUARD_HIT)
                .addGuardMotion(TCorpCategories.ATELIER_SHOTGUN, (item, player) -> TCorpAnimations.ATELIER_SHOTGUN_GUARD_HIT)
                .addGuardBreakMotion(TCorpCategories.ATELIER_SHOTGUN, (item, player) -> TCorpAnimations.RANGA_GUARD_STAGGER);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getDataManager().registerData(LAST_ACTIVE);
        container.getExecuter().getEventListener().addEventListener(EventType.SERVER_ITEM_USE_EVENT, EVENT_UUID, (event) -> {
            CapabilityItem itemCapability = event.getPlayerPatch().getHoldingItemCapability(Hand.MAIN_HAND);

            if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.GUARD) && this.isExecutableState(event.getPlayerPatch())) {
                event.getPlayerPatch().getOriginal().startUsingItem(Hand.MAIN_HAND);
            }
        });

        container.getExecuter().getEventListener().addEventListener(EventType.SERVER_ITEM_STOP_EVENT, EVENT_UUID, (event) -> {
            ServerPlayerEntity serverplayer = event.getPlayerPatch().getOriginal();
            container.getDataManager().setDataSync(LAST_HIT_TICK, serverplayer.tickCount, serverplayer);


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


            float penalty = container.getDataManager().getDataValue(PENALTY) + this.getPenaltyMultiplier(itemCapapbility);

            //boolean successParrying = event.getPlayerPatch().getOriginal().ticksExisted - container.getDataManager().getDataValue(LAST_ACTIVE) < 6;

            event.getPlayerPatch().playSound(EpicFightSounds.CLASH, -0.05F, 0.1F);


            ServerPlayerEntity serverPlayer = event.getPlayerPatch().getOriginal();

            EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(((ServerWorld)serverPlayer.level), HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, serverPlayer, damageSource.getDirectEntity());

            if (damageSource.getEntity() instanceof LivingEntity) {
                knockback += EnchantmentHelper.getKnockbackBonus((LivingEntity)damageSource.getEntity()) * 0.1F;
            }


            event.getPlayerPatch().knockBackEntity(damageSource.getEntity().position(), knockback);

            float stamina = event.getPlayerPatch().getStamina() - penalty * impact;
            event.getPlayerPatch().setStamina(stamina);
            StaticAnimation animation;
            BlockType blockType = (stamina >= 0.0F) ? BlockType.GUARD : BlockType.GUARD_BREAK;


            animation = this.getGuardMotion(event.getPlayerPatch(), itemCapapbility, blockType);
            container.getDataManager().setDataSync(PENALTY, penalty, event.getPlayerPatch().getOriginal());

            if (animation != null) {
                event.getPlayerPatch().playAnimationSynchronized(animation, 0.0F);
            }

            if (blockType == BlockType.GUARD_BREAK) {
                event.getPlayerPatch().playSound(TCorpSounds.STAGGER,3,  -0.05F, 0.1F);
                event.getPlayerPatch().playSound(EpicFightSounds.NEUTRALIZE_MOBS, 3.0F, 0.0F, 0.1F);
            }

            EmotionSystem.handleGuard(serverPlayer, event.getAmount(), impact, false);

            this.dealEvent(event.getPlayerPatch(), event);
        }
    }

    private static boolean isSpecialDamageSource(DamageSource damageSource) {
        return (damageSource.isExplosion() || damageSource.isMagic() || damageSource.isFire() || damageSource.isProjectile()) && (!damageSource.isBypassArmor());
    }
    public void dealEvent(PlayerPatch<?> playerpatch, HurtEvent.Pre event) {

        boolean isSpecialSource = isSpecialDamageSource(event.getDamageSource());
        event.setAmount(isSpecialSource ? event.getAmount() * 0.6F : 0.4F);

        event.setResult(isSpecialSource ? AttackResult.ResultType.SUCCESS : AttackResult.ResultType.BLOCKED);
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

    protected boolean isRudimentaryBlockable(DamageSource damageSource, boolean advanced) {
        return !damageSource.isBypassArmor() && !damageSource.isBypassInvul() && !damageSource.isProjectile() && !damageSource.isExplosion() && !damageSource.isMagic() && !damageSource.isFire();
    }

    @Override
    public float getPenaltyMultiplier(CapabilityItem itemCap) {
        return 0.5F;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldDraw(SkillContainer container) {
        return container.getExecuter().getHoldingItemCapability(Hand.MAIN_HAND).getWeaponCategory() == TCorpCategories.ATELIER_SHOTGUN && (Float)container.getDataManager().getDataValue(PENALTY) > 0.0F;
    }

    protected boolean isAdvancedGuard() {
        return false;
    }
}

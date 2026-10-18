package net.m3tte.ego_weapons.skill.lamp;

import com.google.common.collect.Lists;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.FirefistMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.LampCrossbowMovesetAnims;
import net.m3tte.ego_weapons.potion.countEffects.ClashStunEffect;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.skill.NonSpamGuardSkill;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCategories;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.gameasset.Skills;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.GuardSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.HurtEvent;

import javax.annotation.Nullable;
import java.util.List;

import static net.m3tte.ego_weapons.skill.GenericActiveGuard.canParryHeavy;
import static net.m3tte.ego_weapons.world.capabilities.UtilitySystems.calculateBlockDelay;


public class LampGuard extends NonSpamGuardSkill {
    public LampGuard(Builder builder) {
        super(builder, 0.3f, 20, 0.3f, 1.5f);
    }

    public static Builder createBuilder(ResourceLocation resourceLocation) {
        return GuardSkill.createBuilder(resourceLocation)
                .addGuardMotion(EgoWeaponsCategories.LAMP_CROSSBOW, (item, player) -> LampCrossbowMovesetAnims.LAMP_CB_GUARD_HIT)
                .addGuardBreakMotion(EgoWeaponsCategories.LAMP_CROSSBOW, (item, player) -> BlackSilenceMovesetAnims.RANGA_GUARD_STAGGER)
                .addAdvancedGuardMotion(EgoWeaponsCategories.LAMP_CROSSBOW, (itemCap, playerpatch) ->
                        new StaticAnimation[] { LampCrossbowMovesetAnims.LAMP_CB_PARRY_1, LampCrossbowMovesetAnims.LAMP_CB_PARRY_2, LampCrossbowMovesetAnims.LAMP_CB_PARRY_3});
    }

    public void guard(SkillContainer container, CapabilityItem itemCapability, HurtEvent.Pre event, float knockback, float impact, boolean advanced) {
        if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.ADVANCED_GUARD)) {
            DamageSource damageSource = event.getDamageSource();
            if (this.isBlockableSource(damageSource, true)) {
                ServerPlayerEntity playerentity = event.getPlayerPatch().getOriginal();
                boolean successParrying = playerentity.tickCount     - container.getDataManager().getDataValue(getLastActive()) < calculateBlockDelay(event.getPlayerPatch().getOriginal(),8);
                float penalty = container.getDataManager().getDataValue(PENALTY);
                //event.getPlayerPatch().playSound(EpicFightSounds.CLASH, -0.05F, 0.1F);
                // EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument((ServerWorld)playerentity.level, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, playerentity, damageSource.getDirectEntity());
                if (successParrying) {
                    knockback *= 0.4F;

                    penalty += 0.1F;
                } else {
                    penalty += this.getPenaltyMultiplier(itemCapability);
                }

                SkillDataManager dataManager = container.getExecuter().getSkill(this.getCategory()).getDataManager();
                int motionCounter = dataManager.getDataValue(getParryMotions());

                if (motionCounter % 3 == 2 && successParrying) {
                    if (event.getDamageSource().getDirectEntity() instanceof LivingEntity) {
                        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) (event.getDamageSource().getDirectEntity()).getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

                        event.getPlayerPatch().playSound(EgoWeaponsSounds.CLASH_WIN , -0.05F, 0.1F);

                        EgoWeaponsEffects.LAMP.get().increment(playerentity, 0, 2);

                        if (entitypatch != null) {
                            SharedFunctions.hitstunEntity(entitypatch, 1, false, 0.1f);
                            entitypatch.knockBackEntity(playerentity.position().add(0,1,0), 0.3f);
                        }


                    }

                }
                if (motionCounter % 3 == 1 && successParrying) {
                    event.getPlayerPatch().playSound(EgoWeaponsSounds.BLACK_SILENCE_EVADE, -0.05F, 0.1F);
                    if (event.getDamageSource().getDirectEntity() instanceof LivingEntity) {
                        ((LivingEntity)(event.getDamageSource().getDirectEntity())).addEffect(new EffectInstance(EgoWeaponsEffects.CLASH_STUN.get(), 8, 1));
                    }
                } else {
                    event.getPlayerPatch().playSound(EpicFightSounds.CLASH, -0.05F, 0.1F);
                    EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument((ServerWorld)playerentity.level, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, playerentity, damageSource.getDirectEntity());
                    ((LivingEntity)(event.getDamageSource().getDirectEntity())).addEffect(new EffectInstance(EgoWeaponsEffects.CLASH_STUN.get(), 3, 2));
                }

                container.getDataManager().setDataSync(getLastActive(), 0, playerentity );

                container.getDataManager().setDataSync(PENALTY, penalty, playerentity);

                handleKnockback(event, knockback, successParrying);

                float stamina = event.getPlayerPatch().getStamina();
                stamina -= penalty * impact;
                event.getPlayerPatch().setStamina(stamina);
                BlockType blockType = successParrying ? BlockType.ADVANCED_GUARD : (stamina >= 0.0F ? BlockType.GUARD : BlockType.GUARD_BREAK);


                // Part condition. Strong attacks cannot be parried if stamina were to reach 0
                blockType = canParryHeavy(successParrying, event.getPlayerPatch(), blockType, stamina, impact, event, penalty);
                if (blockType.equals(BlockType.GUARD_BREAK))
                    successParrying = false;

                StaticAnimation animation = this.getGuardMotion(event.getPlayerPatch(), itemCapability, blockType);
                if (animation != null) {
                    event.getPlayerPatch().playAnimationSynchronized(animation, 0.0F);
                }

                EmotionSystem.handleGuard(playerentity, event.getAmount(), impact, successParrying, event.getDamageSource().getEntity());
                this.dealEvent(event.getPlayerPatch(), event);
                return;
            }
        }

        super.guard(container, itemCapability, event, knockback, impact, false);
    }

    protected boolean isBlockableSource(DamageSource damageSource, boolean advanced) {
        return damageSource.isProjectile() && advanced || super.isBlockableSource(damageSource, false);
    }

    @Override
    protected float getPenaltyMultiplier(CapabilityItem itemCapapbility) {
        return itemCapapbility.getWeaponCategory().equals(EgoWeaponsCategories.FIREFIST_GAUNTLET) ? 1 : 0.6F;
    }

    @Nullable
    protected StaticAnimation getGuardMotion(PlayerPatch<?> playerpatch, CapabilityItem itemCapability, BlockType blockType) {
        if (blockType == BlockType.ADVANCED_GUARD) {
            StaticAnimation[] motions = (StaticAnimation[])this.getGuradMotionMap(blockType).getOrDefault(itemCapability.getWeaponCategory(), (a, b) -> null).apply(itemCapability, playerpatch);

            if (motions != null) {
                SkillDataManager dataManager = playerpatch.getSkill(this.getCategory()).getDataManager();
                int motionCounter = dataManager.getDataValue(getParryMotions());
                dataManager.setDataF(getParryMotions(), (v) -> v + 1);
                motionCounter %= motions.length;

                return motions[motionCounter];


            }
        }

        return super.getGuardMotion(playerpatch, itemCapability, blockType);
    }

    public Skill getPriorSkill() {
        return Skills.GUARD;
    }

    protected boolean isAdvancedGuard() {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public List<Object> getTooltipArgs() {
        List<Object> list = Lists.newArrayList();
        list.add(String.format("%s, %s, %s, %s", EgoWeaponsCategories.FIREFIST_GAUNTLET).toLowerCase());
        return list;
    }


}

package net.m3tte.ego_weapons.skill.heishou_mao;

import com.google.common.collect.Lists;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.HeishouMaoBranchAnims;
import net.m3tte.ego_weapons.gameasset.movesets.OeufiAssocMovesetAnims;
import net.m3tte.ego_weapons.potion.countEffects.TremorEffect;
import net.m3tte.ego_weapons.procedures.BlipTick;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.skill.NonSpamGuardSkill;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCategories;
import net.minecraft.enchantment.EnchantmentHelper;
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
import yesman.epicfight.client.gui.BattleModeGui;
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
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.HurtEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import javax.annotation.Nullable;
import java.util.List;

import static net.m3tte.ego_weapons.skill.GenericActiveGuard.canParryHeavy;


public class HeishouMaoActiveGuard extends NonSpamGuardSkill {
    public HeishouMaoActiveGuard(Builder builder) {
        super(builder, 0.3f, 30, 0.3f, 1.5f);
    }


    public static Builder createBuilder(ResourceLocation resourceLocation) {
        return GuardSkill.createBuilder(resourceLocation)
                .addGuardMotion(EgoWeaponsCategories.HEISHOU_MAO_SWORD, (item, player) -> HeishouMaoBranchAnims.HEISHOU_MAO_GUARD_HIT)
                .addGuardBreakMotion(EgoWeaponsCategories.HEISHOU_MAO_SWORD, (item, player) -> BlackSilenceMovesetAnims.RANGA_GUARD_STAGGER)
                .addAdvancedGuardMotion(EgoWeaponsCategories.HEISHOU_MAO_SWORD, (itemCap, playerpatch) ->
                        new StaticAnimation[] { HeishouMaoBranchAnims.HEISHOU_MAO_PARRY_3, HeishouMaoBranchAnims.HEISHOU_MAO_PARRY_1, HeishouMaoBranchAnims.HEISHOU_MAO_PARRY_3 });
    }


    public void guard(SkillContainer container, CapabilityItem itemCapability, HurtEvent.Pre event, float knockback, float impact, boolean advanced) {
        if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.ADVANCED_GUARD)) {
            DamageSource damageSource = event.getDamageSource();
            if (this.isBlockableSource(damageSource, true)) {
                ServerPlayerEntity playerentity = event.getPlayerPatch().getOriginal();
                boolean successParrying = playerentity.tickCount     - container.getDataManager().getDataValue(this.getLastActive()) < 8;
                float penalty = container.getDataManager().getDataValue(PENALTY);

                if (successParrying) {
                    knockback *= 0.4F;

                    penalty += 0.1F;
                } else {
                    penalty += this.getPenaltyMultiplier(itemCapability);
                }
                container.getDataManager().setDataSync(PENALTY, penalty, playerentity);
                container.getDataManager().setDataSync(getLastActive(), 0, playerentity );

                if (damageSource.getDirectEntity() instanceof LivingEntity) {
                    knockback += (float) EnchantmentHelper.getKnockbackBonus((LivingEntity)damageSource.getDirectEntity()) * 0.1F;
                }

                event.getPlayerPatch().knockBackEntity(damageSource.getDirectEntity().position(), knockback);
                float stamina = event.getPlayerPatch().getStamina();
                stamina -= penalty * impact;
                event.getPlayerPatch().setStamina(stamina);
                BlockType blockType = successParrying ? BlockType.ADVANCED_GUARD : (stamina >= 0.0F ? BlockType.GUARD : BlockType.GUARD_BREAK);


                // Part condition. Strong attacks cannot be parried if stamina were to reach 0
                blockType = canParryHeavy(successParrying, event.getPlayerPatch(), blockType, stamina, impact, event, penalty);
                if (blockType.equals(BlockType.GUARD_BREAK))
                    successParrying = false;

                StaticAnimation animation = this.getGuardMotion(event.getPlayerPatch(), itemCapability, blockType, damageSource);
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
        return itemCapapbility.getWeaponCategory().equals(EgoWeaponsCategories.SOLEMN_LAMENT) ? 1 : 0.6F;
    }

    @Nullable
    protected StaticAnimation getGuardMotion(PlayerPatch<?> playerpatch, CapabilityItem itemCapability, BlockType blockType, DamageSource damageSource) {
        if (blockType == BlockType.ADVANCED_GUARD) {
            StaticAnimation[] motions = (StaticAnimation[])this.getGuradMotionMap(blockType).getOrDefault(itemCapability.getWeaponCategory(), (a, b) -> null).apply(itemCapability, playerpatch);

            if (motions != null) {
                SkillDataManager dataManager = playerpatch.getSkill(this.getCategory()).getDataManager();
                int motionCounter = dataManager.getDataValue(getParryMotions());
                dataManager.setDataF(getParryMotions(), (v) -> v + 1);
                motionCounter %= motions.length + 1;

                if (motionCounter == motions.length) {
                    LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) damageSource.getDirectEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                    EgoWeaponsEffects.SPEED_UP.get().increment(playerpatch.getOriginal(), 0, 2);

                    if (damageSource.getEntity() instanceof LivingEntity) {
                        int playerSpeed = EgoWeaponsEffects.speedMult(playerpatch.getOriginal());
                        int sourceSpeed = EgoWeaponsEffects.speedMult((LivingEntity) damageSource.getEntity());

                        if (playerSpeed - sourceSpeed >= 5) {
                            SharedFunctions.hitstunEntity(entitypatch, 3, false, 0.0f);
                            return HeishouMaoBranchAnims.HEISHOU_MAO_PARRY_EVADE;
                        }
                    }
                    BlipTick.chargeBlips(playerpatch.getOriginal(), 1, true);
                    return HeishouMaoBranchAnims.HEISHOU_MAO_PARRY_2;
                } else {

                    playerpatch.playSound(EgoWeaponsSounds.HEISHOU_MAO_PARRY, -0.05F, 0.1F);
                    EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument((ServerWorld)playerpatch.getOriginal().level, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, playerpatch.getOriginal(), damageSource.getDirectEntity());

                    return motions[motionCounter];
                }


            }
        }
        playerpatch.playSound(EgoWeaponsSounds.HEISHOU_MAO_PARRY, -0.05F, 0.1F);
        EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument((ServerWorld)playerpatch.getOriginal().level, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, playerpatch.getOriginal(), damageSource.getDirectEntity());
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
        list.add(String.format("%s, %s, %s, %s", EgoWeaponsCategories.HEISHOU_MAO_SWORD).toLowerCase());
        return list;
    }



}

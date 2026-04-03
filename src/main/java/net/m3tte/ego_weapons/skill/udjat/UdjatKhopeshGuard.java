package net.m3tte.ego_weapons.skill.udjat;

import com.google.common.collect.Lists;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.movesets.ArdorBlossomMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.UdjatKhopeshMovesetAnims;
import net.m3tte.ego_weapons.skill.NonSpamGuardSkill;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCategories;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.types.StaticAnimation;
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


public class UdjatKhopeshGuard extends NonSpamGuardSkill {
    public UdjatKhopeshGuard(Builder builder) {
        super(builder, 0.3f, 20, 0.3f, 1.5f, 0.5f, 0.5f);
    }

    public static Builder createBuilder(ResourceLocation resourceLocation) {
        return GuardSkill.createBuilder(resourceLocation)
                .addGuardMotion(EgoWeaponsCategories.UDJAT_KHOPESH, (item, player) -> UdjatKhopeshMovesetAnims.KHOPESH_GUARD_HIT)
                .addGuardBreakMotion(EgoWeaponsCategories.UDJAT_KHOPESH, (item, player) -> BlackSilenceMovesetAnims.RANGA_GUARD_STAGGER)
                .addAdvancedGuardMotion(EgoWeaponsCategories.UDJAT_KHOPESH, (itemCap, playerpatch) ->
                        new StaticAnimation[] { UdjatKhopeshMovesetAnims.KHOPESH_PARRY_1, UdjatKhopeshMovesetAnims.KHOPESH_PARRY_2, UdjatKhopeshMovesetAnims.KHOPESH_PARRY_3});
    }



    public void guard(SkillContainer container, CapabilityItem itemCapability, HurtEvent.Pre event, float knockback, float impact, boolean advanced) {
        if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.ADVANCED_GUARD)) {
            DamageSource damageSource = event.getDamageSource();
            if (this.isBlockableSource(damageSource, true)) {
                ServerPlayerEntity playerentity = event.getPlayerPatch().getOriginal();
                boolean successParrying = playerentity.tickCount     - container.getDataManager().getDataValue(getLastActive()) < 8;
                float penalty = container.getDataManager().getDataValue(PENALTY);
                event.getPlayerPatch().playSound(EgoWeaponsSounds.UDJAT_KHOPESH_PARRY, -0.05F, 0.1F);
                EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument((ServerWorld)playerentity.level, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, playerentity, damageSource.getDirectEntity());



                if (successParrying) {
                    knockback *= 0.4F;
                    penalty += 0.1F;
                } else {
                    penalty += this.getPenaltyMultiplier(itemCapability);
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
        return itemCapapbility.getWeaponCategory().equals(EgoWeaponsCategories.ARDOR_BLOSSOM_BLUNT) ? 1 : 0.6F;
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

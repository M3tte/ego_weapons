//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.entities.NothingThere2Entity;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.procedures.TeamLockedPredicate;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.m3tte.ego_weapons.world.capabilities.UtilitySystems;
import net.m3tte.ego_weapons.world.capabilities.threatlevel.ThreatLevelSystem;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.particle.EpicFightParticles;

import java.util.List;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;

public class LampEffect extends PotencyOnlyStatus {
    public LampEffect() {
        super(EffectType.BENEFICIAL, "lamp",-16777216, true, 20, 600);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        super.applyEffectTick(entity, amplifier);
        if (amplifier >= 19) {
            triggerDazzle(entity);
        }
    }

    public void triggerDazzle(LivingEntity self) {
        List<LivingEntity> nearEntities = ThreatLevelSystem.sortEntitiesByRisk(SharedFunctions.getNearbyEntities(self, 23, 10, TeamLockedPredicate.ONLY_HOSTILES));
        LivingEntity target = null;

        for (LivingEntity nearEntity : nearEntities) {
            if (!nearEntity.hasEffect(EgoWeaponsEffects.DAZZLE.get())) {
                target = nearEntity;
                break;
            }
        }


        boolean armorFlag = EgoWeaponsItems.LAMP_SUIT.get().equals(self.getItemBySlot(EquipmentSlotType.CHEST).getItem());
        boolean weaponflag = EgoWeaponsItems.LAMP_CROSSBOW.get().equals(self.getItemBySlot(EquipmentSlotType.MAINHAND).getItem());


        if (target != null) {
            boolean success = DialogueSystem.speakEvalDialogue(self, "dialogue.ego_weapons.skills.lamp_crossbow.trigger_dazzle", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);
            if (!self.level.isClientSide() && success) {
                self.level.playSound(null, self,
                        EgoWeaponsSounds.LAMP_CROSSBOW_VOICE_SPECIAL_DAZZLE,
                        SoundCategory.PLAYERS, (float) 1, (float) 1);
            }

            if (!self.level.isClientSide()) {
                self.level.playSound(null, target,
                        EgoWeaponsSounds.LAMP_CROSSBOW_RANGED_RELOAD,
                        SoundCategory.PLAYERS, (float) 1, (float) 1);
                self.level.playSound(null, target,
                        SoundEvents.BELL_BLOCK,
                        SoundCategory.PLAYERS, (float) 1, (float) 1);
                self.level.playSound(null, target,
                        SoundEvents.BELL_BLOCK,
                        SoundCategory.PLAYERS, (float) 1, (float) 0.8f);


                self.level.playSound(null, target,
                        SoundEvents.BELL_RESONATE,
                        SoundCategory.PLAYERS, (float) 1, (float) 0.8f);
            }



            if (self.level instanceof ServerWorld) {
                ((ServerWorld) self.level).sendParticles(EgoWeaponsParticles.LAMP_EMBERS_INV.get(), target.getX(), (target.getY() + target.getEyeHeight()), target.getZ(), 20, 0.1, 0.1, 0.1, 1);
            }

            // Draw a line between the target and the caster
            Vector3d sourcePos = self.position().add(0, self.getEyeHeight(), 0);
            Vector3d targetPos = target.position().add(0, target.getEyeHeight(), 0);
            Vector3d difference = targetPos.subtract(sourcePos).normalize();
            double dist = Math.min(20,targetPos.distanceTo(sourcePos));
            for (float x = 0; x < dist; x += 0.5) {
                Vector3d calculatedVector = sourcePos.add(difference.multiply(x,x,x));

                if (self.level instanceof ServerWorld)
                    ((ServerWorld) self.level).sendParticles(EgoWeaponsParticles.LAMP_EMBERS.get(), calculatedVector.x(), calculatedVector.y(), calculatedVector.z(), 3, 0.1, 0.1, 0.1, 0.2);
            }
            if (target instanceof PlayerEntity) {
                UtilitySystems.sendShockwavePacket((PlayerEntity) target, 3f, 0.8f, 1f, 2);
            }
            if (self instanceof PlayerEntity) {
                UtilitySystems.sendShockwavePacket((PlayerEntity) self, 2f, 0.45f, 1f, 2);
            }

            EgoWeaponsEffects.DAZZLE.get().increment(target, 0, 1);

            if (weaponflag) {
                EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(self, 0, 2);
            }

            if (armorFlag) {
                EgoWeaponsEffects.SALVATION.get().increment(self, 20, 20);
                EgoWeaponsEffects.DEFENSE_LEVEL_UP.get().increment(self, 0, 3);
            }

            EgoWeaponsEffects.LAMP.get().decrement(self, 0, 30);
        }

    }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.gameasset.EgoWeaponsSkills;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.gameasset.Skills;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class EGOAttArdorBlossom extends PotencyOnlyStatus {
    public EGOAttArdorBlossom() {
        super(EffectType.BENEFICIAL, "ego_att_ardor",-16777216, true, 99, 400);
    }


    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeModifierManager p_111187_2_, int p_111187_3_) {
        super.removeAttributeModifiers(entity, p_111187_2_, p_111187_3_);
        if (entity instanceof PlayerEntity) {
            PlayerPatch<?> entitypatch = (PlayerPatch<?>) ((PlayerEntity) entity).getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            SkillContainer evade = entitypatch.getSkillCapability().skillContainers[SkillCategories.DODGE.universalOrdinal()];


            if (evade != null) {
                if (evade.getSkill() != null) {
                    if (evade.getSkill().equals(EgoWeaponsSkills.ARDOR_BLOSSOM_EVADE)) {
                        String savedAnim = entity.getPersistentData().getString("savedDodgeAnimationArmor");

                        System.out.println("STOPPED ANIMATION SAVED SHOULD BE :: "+savedAnim);
                        if (savedAnim.split(":").length == 2) {
                            evade.setSkill(EgoWeaponsSkills.REGISTERED_SKILLS_REF.get(new ResourceLocation(savedAnim)));
                        }
                    }
                }
            }

        }
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeModifierManager p_111185_2_, int p_111185_3_) {
        super.addAttributeModifiers(entity, p_111185_2_, p_111185_3_);

        if (entity instanceof PlayerEntity) {
            PlayerPatch<?> entitypatch = (PlayerPatch<?>) ((PlayerEntity) entity).getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            SkillContainer evade = entitypatch.getSkillCapability().skillContainers[SkillCategories.DODGE.universalOrdinal()];

            if (evade.getSkill() != null) {
                entity.getPersistentData().putString("savedDodgeAnimationArmor", evade.getSkill().getRegistryName().toString());
                evade.setSkill(EgoWeaponsSkills.ARDOR_BLOSSOM_EVADE);
            }
        }
    }
}

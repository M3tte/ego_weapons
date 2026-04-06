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

public class EGOAttArdorBlossom extends CountPotencyStatus {
    public EGOAttArdorBlossom() {
        super(EffectType.BENEFICIAL, "ego_att_ardor",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.ego_att_ardor";
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }

    @Override
    public boolean shouldRenderInvText(EffectInstance effect) {
        return true;
    }

    @Override
    public boolean shouldRender(EffectInstance effect) {
        return true;
    }

    @Override
    public boolean shouldRenderHUD(EffectInstance effect) {
        return true;
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

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        World world = entity.level;
        if (entity.getEffect(this).getDuration() <= 10 && amplifier > 0) {
            entity.removeEffect(this);
            entity.addEffect(new EffectInstance(this, 210, amplifier-1));
            entity.playSound(SoundEvents.CHORUS_FLOWER_GROW, 1, 1);

            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(EpicFightParticles.BLOOD.get(), (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
                        (entity.getZ()), (int) 20, (entity.getBbWidth() / 2.5), (entity.getBbHeight() / 3), (entity.getBbWidth() / 2.5), 0);
            }
        }

    }

    @Override
    public void increment(LivingEntity entity, int limit, int potency) {
        if (entity.level.isClientSide)
            return;

        if (limit == 0 || limit >= 5)
            limit = 5;

        if (potency > limit)
            potency = limit;

        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, 210, potency-1));
        } else {
            entity.getEffect(this).update(new EffectInstance(this, entity.getEffect(this).getDuration(), Math.min(entity.getEffect(this).getAmplifier() + potency, limit-1)));
        }
        syncEffect(entity);
    }

    @Override
    public void decrement(LivingEntity entity, int limit, int potency) {
        if (entity.level.isClientSide)
            return;

        int previousPotency = 0;
        if (entity.hasEffect(this)) {
            previousPotency = entity.getEffect(this).getAmplifier()+1;
            entity.removeEffect(this);
        }

        if ((previousPotency - potency) > 0) {
            entity.addEffect(new EffectInstance(this, 210, previousPotency - potency -1));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public int getCount(EffectInstance ef) {
        return 0;
    }
}

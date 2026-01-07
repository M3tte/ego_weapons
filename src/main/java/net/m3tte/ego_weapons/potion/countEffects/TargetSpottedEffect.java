//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.AttackMoveType;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.procedures.EntityTick;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCategories;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static net.m3tte.ego_weapons.procedures.SharedFunctions.incrementBonusDamage;

public class TargetSpottedEffect extends CountPotencyStatus {

    public TargetSpottedEffect() {
        super(EffectType.HARMFUL, "target_spotted",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.target_spotted";
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
    public int getCount(EffectInstance ef) {
        return 0;
    }

    @Override
    public int getPotency(EffectInstance ef) {
        return 0;
    }

    @Override
    public int getCount(LivingEntity entity) {
        return 0;
    }

    @Override
    public int getPotency(LivingEntity entity) {
        return 0;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public static float applyOnHit(LivingEntityPatch<?> sourcePatch, LivingEntity target, float oldDamage, DamageSource src) {

        DynamicAnimation currentanim = sourcePatch.getServerAnimator().animationPlayer.getAnimation();

        if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation) {
            //System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

            AttackMoveType attackType;
            String weaponIdentifier;

            //boolean consumesAmmo;
            //boolean finale;
            attackType = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE).orElse(AttackMoveType.MELEE);
            weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");


            if (attackType.equals(AttackMoveType.RANGED)) {
                ((World) sourcePatch.getOriginal().level).playSound(null, sourcePatch.getOriginal().blockPosition(),
                        (net.minecraft.util.SoundEvent) EgoWeaponsSounds.TARGET_SPOTTED,
                        SoundCategory.PLAYERS, (float) 2, (float) 1);

                if (sourcePatch.getHoldingItemCapability(Hand.MAIN_HAND).getWeaponCategory().equals(EgoWeaponsCategories.FULLSTOP_SNIPER)) {
                    EgoWeaponsEffects.POISE.get().increment(sourcePatch.getOriginal(), 1, 2);
                }

                if (sourcePatch.getHoldingItemCapability(Hand.MAIN_HAND).getWeaponCategory().equals(EgoWeaponsCategories.FULLSTOP_SNIPER)) {
                    if (target.hasEffect(EgoWeaponsEffects.TARGET_SPOTTED.get()) && sourcePatch.getOriginal() instanceof PlayerEntity) {
                        EntityTick.regenerateLight((PlayerEntity) sourcePatch.getOriginal(), weaponIdentifier.equals("fs_sn_innate") ? 1 : 0, true);
                    }
                }


                if (!sourcePatch.getOriginal().hasEffect(EgoWeaponsEffects.ASSIST_FIRE.get())) {
                    target.removeEffect(EgoWeaponsEffects.TARGET_SPOTTED.get());
                } else {
                    target.removeEffect(EgoWeaponsEffects.ASSIST_FIRE.get());
                }

                ((ServerWorld) target.level).sendParticles(EgoWeaponsParticles.ATELIER_SHOTGUN_IMPACT.get(), (target.getX()), (target.getY() + target.getBbHeight() / 2),
                        (target.getZ()), 1, 0, 0, 0, 0);


                incrementBonusDamage(src, 0.2f);


                return oldDamage * 1.2f;
            }
        }

        return oldDamage;
    }
}

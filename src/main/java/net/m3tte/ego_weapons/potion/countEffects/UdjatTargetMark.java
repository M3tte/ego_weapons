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
import net.m3tte.ego_weapons.world.capabilities.UtilitySystems;
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

public class UdjatTargetMark extends PotencyOnlyStatus {

    public UdjatTargetMark() {
        super(EffectType.HARMFUL, "target_mark_udjat",-16777216);
    }

    public static float applyOnHit(LivingEntityPatch<?> sourcePatch, LivingEntity target, float oldDamage, DamageSource src) {

        UtilitySystems.EGOAttackContext ctx = new UtilitySystems.EGOAttackContext(sourcePatch);

        if (ctx.isValidEgoAnimation()) {
            //System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));


            if (ctx.getMoveType().equals(AttackMoveType.RANGED)) {
                ((World) sourcePatch.getOriginal().level).playSound(null, sourcePatch.getOriginal().blockPosition(),
                        (net.minecraft.util.SoundEvent) EgoWeaponsSounds.TARGET_SPOTTED,
                        SoundCategory.PLAYERS, (float) 2, (float) 1);

                ((ServerWorld) target.level).sendParticles(EgoWeaponsParticles.ATELIER_SHOTGUN_IMPACT.get(), (target.getX()), (target.getY() + target.getBbHeight() / 2),
                        (target.getZ()), 1, 0, 0, 0, 0);

                return oldDamage + incrementBonusDamage(src, 0.15f);
            }
        }

        return oldDamage;
    }
}

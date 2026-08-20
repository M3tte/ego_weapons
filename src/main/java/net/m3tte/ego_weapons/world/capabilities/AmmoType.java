package net.m3tte.ego_weapons.world.capabilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.ResourceLocation;

public enum AmmoType {
    StandardLight(DamageTypes.RED, AttackTypes.PIERCE,false, EgoWeaponsParticles.BASIC_BULLET_FIRE.get(), EgoWeaponsParticles.BASIC_BULLET_IMPACT.get(), EgoWeaponsParticles.BASIC_BULLET_FIRE_SIDE.get(), EgoWeaponsParticles.RIFLE_SHOCKWAVE.get(), new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/bullets/basic_bullet.png")),
    Incendiary(DamageTypes.RED, AttackTypes.PIERCE, true, EgoWeaponsParticles.INCENDIARY_BULLET_FIRE.get(), EgoWeaponsParticles.INCENDIARY_BULLET_IMPACT.get(), EgoWeaponsParticles.INCENDIARY_BULLET_FIRE_SIDE.get(), EgoWeaponsParticles.RIFLE_SHOCKWAVE.get(), new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/bullets/incendiary_light_bullet.png")),
    Moonstone(DamageTypes.WHITE, AttackTypes.PIERCE, true, EgoWeaponsParticles.MOONSTONE_BULLET_FIRE.get(), EgoWeaponsParticles.MOONSTONE_BULLET_IMPACT.get(), EgoWeaponsParticles.MOONSTONE_BULLET_FIRE_SIDE.get(), EgoWeaponsParticles.RIFLE_SHOCKWAVE.get(), new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/bullets/moonstone_light_bullet.png")),
    StandardRifle(DamageTypes.RED, AttackTypes.PIERCE, false, EgoWeaponsParticles.BASIC_BULLET_FIRE.get(), EgoWeaponsParticles.BASIC_BULLET_IMPACT.get(), EgoWeaponsParticles.RIFLE_BASIC_BULLET_FIRE_SIDE.get(), EgoWeaponsParticles.RIFLE_SHOCKWAVE.get(), new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/bullets/basic_rifle_bullet.png")),
    ALHVRifle(DamageTypes.RED, AttackTypes.PIERCE, false, EgoWeaponsParticles.MOONSTONE_BULLET_FIRE.get(), EgoWeaponsParticles.ATELIER_PISTOL_IMPACT.get(), EgoWeaponsParticles.RIFLE_ALHV_BULLET_FIRE_SIDE.get(), EgoWeaponsParticles.RIFLE_ALHV_SHOCKWAVE.get(), new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/bullets/al_hv_rifle_bullet.png")),
    LCAShockRifleWhite(DamageTypes.WHITE, AttackTypes.PIERCE, true, EgoWeaponsParticles.BASIC_BULLET_FIRE.get(), EgoWeaponsParticles.BASIC_BULLET_IMPACT.get(), EgoWeaponsParticles.RIFLE_BASIC_BULLET_FIRE_SIDE.get(), EgoWeaponsParticles.RIFLE_SHOCKWAVE.get(), new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/bullets/lca_fracture_rifle_round_w.png"));


    private final BasicParticleType fireParticle;
    private final BasicParticleType hitParticle;
    private final BasicParticleType fireSideParticle;
    private final BasicParticleType shockwaveParticle;
    private final ResourceLocation ammoTexture;

    private final AttackTypes attackType;
    private final DamageTypes damageType;
    private final boolean hasEffect;

    public static void executeDefaultAmmoEffect(AmmoType ammoType, boolean isFinalCoin, LivingEntity sourceEntity, LivingEntity targetEntity) {


        System.out.println("AMMOTYPE IS : "+ammoType);
        switch (ammoType) {
            case Incendiary:
                EgoWeaponsEffects.BURN.get().increment(targetEntity, isFinalCoin ? 1 : 0, 1);
                break;
            case Moonstone:
                EgoWeaponsEffects.SINKING.get().increment(targetEntity, isFinalCoin ? 1 : 0, 1);
                break;
            case LCAShockRifleWhite:
                EgoWeaponsEffects.TREMOR.get().increment(targetEntity, isFinalCoin ? 1 : 0, 1);
                if (isFinalCoin)
                    EgoWeaponsEffects.SINKING.get().increment(targetEntity, 0, 1);
                break;
        }
    }

    public BasicParticleType getShockwaveParticle() {
        return shockwaveParticle;
    }

    public BasicParticleType getFireParticle() {
        return fireParticle;
    }

    public BasicParticleType getHitParticle() {
        return hitParticle;
    }

    public boolean hasEffect() {
        return hasEffect;
    }

    public BasicParticleType getFireSideParticle() {
        return fireSideParticle;
    }

    public ResourceLocation getAmmoTexture() {
        return ammoTexture;
    }

    public AttackTypes getAttackType() {
        return attackType;
    }

    public DamageTypes getDamageType() {
        return damageType;
    }

    public boolean isHasEffect() {
        return hasEffect;
    }

    AmmoType(DamageTypes damageType, AttackTypes attackType, boolean hasEffect, BasicParticleType fireParticle, BasicParticleType hitParticle, BasicParticleType fireSideParticle, BasicParticleType shockwaveParticle, ResourceLocation ammoTexture) {
        this.fireParticle = fireParticle;
        this.hitParticle = hitParticle;
        this.fireSideParticle = fireSideParticle;
        this.shockwaveParticle = shockwaveParticle;
        this.ammoTexture = ammoTexture;
        this.hasEffect = hasEffect;
        this.damageType = damageType;
        this.attackType = attackType;
    }
}

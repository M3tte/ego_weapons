package net.m3tte.ego_weapons.world.capabilities;

import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.ResourceLocation;

public enum AmmoType {
    Standard(DamageTypes.RED, AttackTypes.PIERCE,false, EgoWeaponsParticles.BASIC_BULLET_FIRE.get(), EgoWeaponsParticles.BASIC_BULLET_IMPACT.get(), EgoWeaponsParticles.BASIC_BULLET_FIRE_SIDE.get(), EgoWeaponsParticles.RIFLE_SHOCKWAVE.get(), new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/bullets/basic_bullet.png")),
    Incendiary(DamageTypes.RED, AttackTypes.PIERCE, true, EgoWeaponsParticles.INCENDIARY_BULLET_FIRE.get(), EgoWeaponsParticles.INCENDIARY_BULLET_IMPACT.get(), EgoWeaponsParticles.INCENDIARY_BULLET_FIRE_SIDE.get(), EgoWeaponsParticles.RIFLE_SHOCKWAVE.get(), new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/bullets/incendiary_light_bullet.png")),
    Moonstone(DamageTypes.WHITE, AttackTypes.PIERCE, true, EgoWeaponsParticles.MOONSTONE_BULLET_FIRE.get(), EgoWeaponsParticles.MOONSTONE_BULLET_IMPACT.get(), EgoWeaponsParticles.MOONSTONE_BULLET_FIRE_SIDE.get(), EgoWeaponsParticles.RIFLE_SHOCKWAVE.get(), new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/bullets/moonstone_light_bullet.png")),
    StandardRifle(DamageTypes.RED, AttackTypes.PIERCE, false, EgoWeaponsParticles.BASIC_BULLET_FIRE.get(), EgoWeaponsParticles.BASIC_BULLET_IMPACT.get(), EgoWeaponsParticles.RIFLE_BASIC_BULLET_FIRE_SIDE.get(), EgoWeaponsParticles.RIFLE_SHOCKWAVE.get(), new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/bullets/basic_rifle_bullet.png")),
    ALHVRifle(DamageTypes.RED, AttackTypes.PIERCE, false, EgoWeaponsParticles.MOONSTONE_BULLET_FIRE.get(), EgoWeaponsParticles.ATELIER_PISTOL_IMPACT.get(), EgoWeaponsParticles.RIFLE_ALHV_BULLET_FIRE_SIDE.get(), EgoWeaponsParticles.RIFLE_ALHV_SHOCKWAVE.get(), new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/bullets/al_hv_rifle_bullet.png"));


    private final BasicParticleType fireParticle;
    private final BasicParticleType hitParticle;
    private final BasicParticleType fireSideParticle;
    private final BasicParticleType shockwaveParticle;
    private final ResourceLocation ammoTexture;

    private final AttackTypes attackType;
    private final DamageTypes damageType;
    private final boolean hasEffect;
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

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.gameasset;

import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsModElements;
import net.m3tte.ego_weapons.skill.AtelierPistol.AtelierPassive;
import net.m3tte.ego_weapons.skill.AtelierPistol.BlacksilenceAtelierGuard;
import net.m3tte.ego_weapons.skill.AtelierShotgun.AtelierShotgunGuard;
import net.m3tte.ego_weapons.skill.AtelierShotgun.AtelierShotgunPassive;
import net.m3tte.ego_weapons.skill.*;
import net.m3tte.ego_weapons.skill.allas.AllasPassive;
import net.m3tte.ego_weapons.skill.allas.BlackSilenceAllasGuard;
import net.m3tte.ego_weapons.skill.durandal.BasicBlockablePassive;
import net.m3tte.ego_weapons.skill.magic_bullet.MagicBulletDetonate;
import net.m3tte.ego_weapons.skill.magic_bullet.MagicBulletGuard;
import net.m3tte.ego_weapons.skill.magic_bullet.MagicBulletPassive;
import net.m3tte.ego_weapons.skill.oldBoys.OldBoysGuard;
import net.m3tte.ego_weapons.skill.oldBoys.OldBoysPassive;
import net.m3tte.ego_weapons.skill.red_mist.RedMistActiveGuard;
import net.m3tte.ego_weapons.skill.red_mist.RedMistBlockable;
import net.m3tte.ego_weapons.skill.solemnLament.SolemnLamentActiveGuard;
import net.m3tte.ego_weapons.skill.solemnLament.SolemnLamentPassive;
import net.m3tte.ego_weapons.skill.sunshower.SunshowerActiveGuard;
import net.m3tte.ego_weapons.skill.sunshower.SunshowerPassive;
import net.m3tte.ego_weapons.skill.sunshower.SunshowerPuddleStomp;
import net.minecraft.util.ResourceLocation;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.forgeevent.SkillRegistryEvent;
import yesman.epicfight.api.utils.ExtendedDamageSource.StunType;
import yesman.epicfight.api.utils.math.ExtraDamageType;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.skill.*;

public class EgoWeaponsSkills {
    public static Skill LESSER_SPLIT_VERTICAL;
    public static Skill GREATER_SPLIT_VERTICAL;
    public static Skill HELLO;

    public static Skill WHEELS_SMASH;
    public static Skill CRYSTAL_ATELIER;

    public static Skill BLACKSILENCE_WEAK_GUARD;

    public static Skill BLACKSILENCE_PASSIVE;

    public static Skill KALI_GUARD;

    public static Skill KALI_PASSIVE;

    public static Skill RANGA_EVISCERATE;

    public static Skill ALLAS_GUARD;

    public static Skill ALLAS_PASSIVE;

    public static Skill ATELIER_GUARD;

    public static Skill ATELIER_PASSIVE;

    public static Skill ATELIER_SHOTGUN_GUARD;

    public static Skill ATELIER_SHOTGUN_PASSIVE;

    public static Skill GENERIC_ACTIVE_GUARD;

    public static Skill BASIC_BLOCKABLE_PASSIVE;

    public static Skill MOOK_CUT;
    public static Skill OLD_BOYS_GUARD;

    public static Skill OLD_BOYS_PASSIVE;

    public static Skill MAGIC_BULLET_GUARD;

    public static Skill MAGIC_BULLET_EVADE;

    public static Skill MAGIC_BULLET_PASSIVE;

    public static Skill MAGIC_BULLET_DETONATE;

    public static Skill SOLEMN_LAMENT_BURST;

    public static Skill SOLEMN_LAMENT_PASSIVE;
    public static Skill SOLEMN_LAMENT_GUARD;

    public static Skill SUNSHOWER_PASSIVE;
    public static Skill SUNSHOWER_GUARD;
    public static Skill SUNSHOWER_PUDDLE_STOMP;
    public EgoWeaponsSkills() {
    }

    public static void registerSkills(SkillRegistryEvent event) {
        LESSER_SPLIT_VERTICAL = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "lesser_split_vertical"))
                    .setConsumption(50.0F).setAnimations(EgoWeaponsAnimations.DURANDAL_DRAW)))
                    .newPropertyLine().addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(2.0F))
                    .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.durandal.strong", ':')))
                    .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.0F))
                    .addProperty(AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(20.0F))
                    .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(3.0F))
                    .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG).registerPropertiesToAnimation(), false);

        GREATER_SPLIT_VERTICAL = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "greater_split_vertical"))
                        .setConsumption(70.0F).setAnimations(EgoWeaponsAnimations.GREAT_SPLIT_VERTICAL)))
                .newPropertyLine().addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(2.0F))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.8F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(60.0F))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(6.0F)), false);

        HELLO = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "hello"))
                        .setConsumption(70.0F).setAnimations(EgoWeaponsAnimations.MIMICRY_HELLO)))
                .newPropertyLine().addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(6.0F)), false);


        SOLEMN_LAMENT_BURST = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "solemn_burst"))
                        .setConsumption(65.0F).setAnimations(EgoWeaponsAnimations.SOLEMN_LAMENT_SPECIAL_ATTACK)))
                .newPropertyLine(), false);


        WHEELS_SMASH = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "wheels_smash"))
                        .setConsumption(80.0F).setAnimations(EgoWeaponsAnimations.WHEELS_HEAVY)))
                .newPropertyLine().addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(2.0F))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.0F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(40.0F))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(3.0F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG).registerPropertiesToAnimation(), false);

        MOOK_CUT = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "mook_cut"))
                        .setConsumption(40.0F).setMaxStack(2).setAnimations(EgoWeaponsAnimations.MOOK_CUT))), false);
        CRYSTAL_ATELIER = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "crystal_atelier"))
                    .setConsumption(50.0F)
                    .setAnimations(EgoWeaponsAnimations.CRYSTAL_ATELIER_SKILL))).newPropertyLine()
                    .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(3.0F))
                    .addProperty(AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(20.0F))
                    .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.0F))
                    .addProperty(AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(20.0F))
                    .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(6.0F))
                    .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                    .registerPropertiesToAnimation(), false);

        BLACKSILENCE_WEAK_GUARD =event.registerSkill(new BlackSilenceWeakGuard(BlackSilenceWeakGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "weak_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        BLACKSILENCE_PASSIVE = event.registerSkill(new BlackSilencePassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "blacksilence_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        ALLAS_GUARD =event.registerSkill(new BlackSilenceAllasGuard(BlackSilenceAllasGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "allas_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        ALLAS_PASSIVE = event.registerSkill(new AllasPassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "allas_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        ATELIER_GUARD =event.registerSkill(new BlacksilenceAtelierGuard(BlacksilenceAtelierGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "atelier_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        ATELIER_PASSIVE = event.registerSkill(new AtelierPassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "atelier_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        ATELIER_SHOTGUN_GUARD =event.registerSkill(new AtelierShotgunGuard(AtelierShotgunGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "atelier_shotgun_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        ATELIER_SHOTGUN_PASSIVE = event.registerSkill(new AtelierShotgunPassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "atelier_shotgun_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        RANGA_EVISCERATE = event.registerSkill(new BlacksilenceEviscerate(SpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "ranga_eviscerate")).setConsumption(35.0F))
                .newPropertyLine()
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.setter(2.0F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .newPropertyLine()
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, ExtraDamageType.get(ExtraDamageType.PERCENT_OF_TARGET_LOST_HEALTH, 0.35F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(50.0F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .registerPropertiesToAnimation(), false);

        MAGIC_BULLET_DETONATE = event.registerSkill(new MagicBulletDetonate(SpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "magic_bullet_detonate")).setConsumption(45.0F)), false);

        GENERIC_ACTIVE_GUARD = event.registerSkill(new BlackSilenceActiveGuard(BlackSilenceActiveGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "blacksilence_active_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        BASIC_BLOCKABLE_PASSIVE = event.registerSkill(new BasicBlockablePassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "basic_blockable_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        KALI_GUARD = event.registerSkill(new RedMistActiveGuard(BlackSilenceActiveGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "redmist_active_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        KALI_PASSIVE = event.registerSkill(new RedMistBlockable(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "redmist_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);



        OLD_BOYS_GUARD = event.registerSkill(new OldBoysGuard(OldBoysGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "old_boys_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        OLD_BOYS_PASSIVE = event.registerSkill(new OldBoysPassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "old_boys_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        MAGIC_BULLET_GUARD =event.registerSkill(new MagicBulletGuard(MagicBulletGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "magic_bullet_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        MAGIC_BULLET_PASSIVE = event.registerSkill(new MagicBulletPassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "magic_bullet_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        MAGIC_BULLET_EVADE = event.registerSkill(new StepSkill(DodgeSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "magic_bullet_evade")).setConsumption(3.0F).setAnimations(EgoWeaponsAnimations.MAGIC_BULLET_EVADE_FORWARD, EgoWeaponsAnimations.MAGIC_BULLET_EVADE_BACKWARD, EgoWeaponsAnimations.MAGIC_BULLET_EVADE_LEFT, EgoWeaponsAnimations.MAGIC_BULLET_EVADE_RIGHT)), false);

        SOLEMN_LAMENT_GUARD =event.registerSkill(new SolemnLamentActiveGuard(SolemnLamentActiveGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "solemn_lament_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        SOLEMN_LAMENT_PASSIVE = event.registerSkill(new SolemnLamentPassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "solemn_lament_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        SUNSHOWER_GUARD =event.registerSkill(new SunshowerActiveGuard(SunshowerActiveGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "sunshower_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        SUNSHOWER_PASSIVE = event.registerSkill(new SunshowerPassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "sunshower_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);
        SUNSHOWER_PUDDLE_STOMP = event.registerSkill(new SunshowerPuddleStomp(SpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "puddle_stomp")).setConsumption(40.0F)), false);


    }
}

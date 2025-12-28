//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.gameasset;

import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.gameasset.movesets.*;
import net.m3tte.ego_weapons.skill.AtelierPistol.AtelierPassive;
import net.m3tte.ego_weapons.skill.AtelierPistol.AtelierPistolSpecial;
import net.m3tte.ego_weapons.skill.AtelierPistol.BlacksilenceAtelierGuard;
import net.m3tte.ego_weapons.skill.AtelierShotgun.AtelierShotgunGuard;
import net.m3tte.ego_weapons.skill.AtelierShotgun.AtelierShotgunPassive;
import net.m3tte.ego_weapons.skill.*;
import net.m3tte.ego_weapons.skill.allas.AllasPassive;
import net.m3tte.ego_weapons.skill.allas.BlackSilenceAllasGuard;
import net.m3tte.ego_weapons.skill.durandal.BasicBlockablePassive;
import net.m3tte.ego_weapons.skill.durandal.DurandalCleave;
import net.m3tte.ego_weapons.skill.firefist.FirefistActiveGuard;
import net.m3tte.ego_weapons.skill.firefist.FirefistPassive;
import net.m3tte.ego_weapons.skill.fullstop_rep.FullstopRepCounterGuard;
import net.m3tte.ego_weapons.skill.fullstop_rep.FullstopRepPassive;
import net.m3tte.ego_weapons.skill.fullstop_sniper.FullstopCounterGuard;
import net.m3tte.ego_weapons.skill.fullstop_sniper.FullstopSniperPassive;
import net.m3tte.ego_weapons.skill.heishou_mao.HeishouMaoActiveGuard;
import net.m3tte.ego_weapons.skill.heishou_mao.HeishouMaoBlinkstep;
import net.m3tte.ego_weapons.skill.heishou_mao.HeishouMaoPassive;
import net.m3tte.ego_weapons.skill.justitia.JustitiaActiveGuard;
import net.m3tte.ego_weapons.skill.justitia.JustitiaPassive;
import net.m3tte.ego_weapons.skill.liu_s6.LiuS6Passive;
import net.m3tte.ego_weapons.skill.liu_s6.LiuS6WeakGuard;
import net.m3tte.ego_weapons.skill.magic_bullet.MagicBulletDetonate;
import net.m3tte.ego_weapons.skill.magic_bullet.MagicBulletGuard;
import net.m3tte.ego_weapons.skill.magic_bullet.MagicBulletPassive;
import net.m3tte.ego_weapons.skill.oeufi.OeufiActiveGuard;
import net.m3tte.ego_weapons.skill.oeufi.OeufiInnate;
import net.m3tte.ego_weapons.skill.oeufi.OeufiPassive;
import net.m3tte.ego_weapons.skill.oldBoys.OldBoysGuard;
import net.m3tte.ego_weapons.skill.oldBoys.OldBoysPassive;
import net.m3tte.ego_weapons.skill.rat.RatKnifeGuard;
import net.m3tte.ego_weapons.skill.rat.RatKnifePassive;
import net.m3tte.ego_weapons.skill.rat.RatPipeGuard;
import net.m3tte.ego_weapons.skill.rat.RatPipePassive;
import net.m3tte.ego_weapons.skill.red_mist.RedMistActiveGuard;
import net.m3tte.ego_weapons.skill.red_mist.RedMistBlockable;
import net.m3tte.ego_weapons.skill.solemnLament.SolemnLamentActiveGuard;
import net.m3tte.ego_weapons.skill.solemnLament.SolemnLamentPassive;
import net.m3tte.ego_weapons.skill.stigmaSword.SunsetBladeSkill;
import net.m3tte.ego_weapons.skill.sunshower.SunshowerActiveGuard;
import net.m3tte.ego_weapons.skill.sunshower.SunshowerPassive;
import net.m3tte.ego_weapons.skill.sunshower.SunshowerPuddleStomp;
import net.m3tte.ego_weapons.skill.wheels.WheelsCounterGuard;
import net.m3tte.ego_weapons.skill.wheels.WheelsPassive;
import net.minecraft.util.ResourceLocation;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.forgeevent.SkillRegistryEvent;
import yesman.epicfight.api.utils.ExtendedDamageSource.StunType;
import yesman.epicfight.api.utils.math.ExtraDamageType;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.skill.*;

public class EgoWeaponsSkills {
    public static Skill DURANDAL_CLEAVE;
    public static Skill GREATER_SPLIT_VERTICAL;
    public static Skill HELLO;

    public static Skill WHEELS_SMASH;
    public static Skill CRYSTAL_ATELIER;

    public static Skill BLACKSILENCE_WEAK_GUARD;

    public static Skill BLACKSILENCE_PASSIVE;

    public static Skill KALI_GUARD;

    public static Skill KALI_PASSIVE;

    public static Skill RANGA_EVISCERATE;

    public static Skill ATELIER_PISTOLS_FRENZY;

    public static Skill ALLAS_GUARD;

    public static Skill ALLAS_PASSIVE;

    public static Skill WHEELS_PASSIVE;
    public static Skill WHEELS_GUARD;

    public static Skill ATELIER_GUARD;

    public static Skill ATELIER_PASSIVE;

    public static Skill ATELIER_SHOTGUN_GUARD;

    public static Skill ATELIER_SHOTGUN_PASSIVE;

    public static Skill GENERIC_ACTIVE_GUARD;

    public static Skill BASIC_BLOCKABLE_PASSIVE;
    public static Skill BASIC_BLOCKABLE_PASSIVE_STIGMA;

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
    public static Skill ZELKOVA_SHATTERING_SPIN;

    public static Skill OEUFI_SLAM;
    public static Skill OEUFI_GUARD;
    public static Skill OEUFI_PASSIVE;
    public static Skill FULLSTOP_REP_INNATE;
    public static Skill FULLSTOP_REP_GUARD;
    public static Skill FULLSTOP_REP_PASSIVE;
    public static Skill FULLSTOP_SNIPER_INNATE;
    public static Skill FULLSTOP_SNIPER_GUARD;
    public static Skill FULLSTOP_SNIPER_PASSIVE;
    public static Skill LIU_S6_GUARD;
    public static Skill LIU_S6_PASSIVE;

    public static Skill LIU_S6_INNATE;
    public static Skill FIREFIST_INNATE;
    public static Skill RAT_KNIFE_INNATE;
    public static Skill RAT_PIPE_INNATE;
    public static Skill JUSTITIA_INNATE;
    public static Skill FIREFIST_GUARD;
    public static Skill FIREFIST_PASSIVE;
    public static Skill SUNSET_BLADE;
    public static Skill BLINKSTEP;
    public static Skill HEISHOU_MAO_GUARD;
    public static Skill HEISHOU_MAO_PASSIVE;
    public static Skill RAT_KNIFE_GUARD;
    public static Skill RAT_KNIFE_PASSIVE;
    public static Skill RAT_PIPE_GUARD;
    public static Skill RAT_PIPE_PASSIVE;
    public static Skill JUSTITIA_GUARD;
    public static Skill JUSTITIA_PASSIVE;
    public EgoWeaponsSkills() {
    }

    public static void registerSkills(SkillRegistryEvent event) {
        DURANDAL_CLEAVE = event.registerSkill(new DurandalCleave(SpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "durandal_cleave")).setConsumption(55.0F)), false);

        GREATER_SPLIT_VERTICAL = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "greater_split_vertical"))
                        .setConsumption(100.0F).setAnimations(MimicryMovesetAnims.GREAT_SPLIT_VERTICAL)))
                .newPropertyLine().addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(2.0F))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.8F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(30.0F))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(6.0F)), false);

        HELLO = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "hello"))
                        .setConsumption(70.0F).setAnimations(MimicryMovesetAnims.MIMICRY_HELLO)))
                .newPropertyLine().addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(6.0F)), false);


        SOLEMN_LAMENT_BURST = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "solemn_burst"))
                        .setConsumption(65.0F).setAnimations(SolemnLamentMovesetAnims.SOLEMN_LAMENT_INNATE)))
                .newPropertyLine(), false);


        WHEELS_SMASH = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "wheels_smash"))
                        .setConsumption(80.0F).setAnimations(BlackSilenceMovesetAnims.WHEELS_HEAVY)))
                .newPropertyLine().addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(2.0F))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(3.0F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).registerPropertiesToAnimation(), false);

        MOOK_CUT = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "mook_cut"))
                        .setConsumption(40.0F).setMaxStack(2).setAnimations(BlackSilenceMovesetAnims.MOOK_CUT))), false);
        CRYSTAL_ATELIER = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "crystal_atelier"))
                    .setConsumption(50.0F)
                    .setAnimations(BlackSilenceMovesetAnims.CRYSTAL_ATELIER_SKILL))).newPropertyLine()
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

        WHEELS_GUARD =event.registerSkill(new WheelsCounterGuard(WheelsCounterGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "wheels_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        WHEELS_PASSIVE = event.registerSkill(new WheelsPassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "wheels_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

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
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(30.0F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .registerPropertiesToAnimation(), false);

        MAGIC_BULLET_DETONATE = event.registerSkill(new MagicBulletDetonate(SpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "magic_bullet_detonate")).setConsumption(45.0F)), false);

        GENERIC_ACTIVE_GUARD = event.registerSkill(new GenericActiveGuard(GenericActiveGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "blacksilence_active_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        BASIC_BLOCKABLE_PASSIVE = event.registerSkill(new BasicBlockablePassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "basic_blockable_passive")).setCategory(SkillCategories.WEAPON_PASSIVE), "other"), false);

        BASIC_BLOCKABLE_PASSIVE_STIGMA = event.registerSkill(new BasicBlockablePassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "basic_blockable_passive")).setCategory(SkillCategories.WEAPON_PASSIVE), "stigma_workshop_sword"), false);

        KALI_GUARD = event.registerSkill(new RedMistActiveGuard(GenericActiveGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "redmist_active_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        KALI_PASSIVE = event.registerSkill(new RedMistBlockable(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "redmist_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);



        OLD_BOYS_GUARD = event.registerSkill(new OldBoysGuard(OldBoysGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "old_boys_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        OLD_BOYS_PASSIVE = event.registerSkill(new OldBoysPassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "old_boys_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        MAGIC_BULLET_GUARD =event.registerSkill(new MagicBulletGuard(MagicBulletGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "magic_bullet_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        MAGIC_BULLET_PASSIVE = event.registerSkill(new MagicBulletPassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "magic_bullet_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        MAGIC_BULLET_EVADE = event.registerSkill(new StepSkill(DodgeSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "magic_bullet_evade")).setConsumption(3.0F).setAnimations(MagicBulletMovesetAnims.MAGIC_BULLET_EVADE_FORWARD, MagicBulletMovesetAnims.MAGIC_BULLET_EVADE_BACKWARD, MagicBulletMovesetAnims.MAGIC_BULLET_EVADE_LEFT, MagicBulletMovesetAnims.MAGIC_BULLET_EVADE_RIGHT)), false);

        SOLEMN_LAMENT_GUARD =event.registerSkill(new SolemnLamentActiveGuard(SolemnLamentActiveGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "solemn_lament_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        SOLEMN_LAMENT_PASSIVE = event.registerSkill(new SolemnLamentPassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "solemn_lament_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        SUNSHOWER_GUARD =event.registerSkill(new SunshowerActiveGuard(SunshowerActiveGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "sunshower_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        SUNSHOWER_PASSIVE = event.registerSkill(new SunshowerPassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "sunshower_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);
        SUNSHOWER_PUDDLE_STOMP = event.registerSkill(new SunshowerPuddleStomp(SpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "puddle_stomp")).setConsumption(40.0F)), false);

        ATELIER_PISTOLS_FRENZY = event.registerSkill(new AtelierPistolSpecial(SpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "atelier_frenzy")).setConsumption(35.0F)), false);
        ZELKOVA_SHATTERING_SPIN = event.registerSkill(new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "shattering_spin"))
                .setConsumption(70.0F).setAnimations(BlackSilenceMovesetAnims.ZELKOVA_SPECIAL_1)), false);

        OEUFI_SLAM = event.registerSkill(new OeufiInnate(SpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "oeufi_slam")).setConsumption(60.0F)), false);
        OEUFI_GUARD =event.registerSkill(new OeufiActiveGuard(OeufiActiveGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "oeufi_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        OEUFI_PASSIVE = event.registerSkill(new OeufiPassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "oeufi_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        FULLSTOP_REP_INNATE = event.registerSkill(new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "target_marked"))
                .setConsumption(70.0F).setAnimations(FullstopOfficeRepMovesetAnims.FULLSTOP_INNATE)), false);

        FULLSTOP_REP_GUARD =event.registerSkill(new FullstopRepCounterGuard(FullstopRepCounterGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "fullstop_rep_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        FULLSTOP_REP_PASSIVE = event.registerSkill(new FullstopRepPassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "fullstop_rep_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);
        FULLSTOP_SNIPER_INNATE = event.registerSkill(new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "fs_headshot"))
                .setResource(Skill.Resource.STAMINA).setConsumption(8.0F).setAnimations(FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_INNATE)), false);

        FULLSTOP_SNIPER_GUARD =event.registerSkill(new FullstopCounterGuard(FullstopCounterGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "fullstop_sniper_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        FULLSTOP_SNIPER_PASSIVE = event.registerSkill(new FullstopSniperPassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "fullstop_sniper_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        LIU_S6_GUARD =event.registerSkill(new LiuS6WeakGuard(LiuS6WeakGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "liu_s6_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        LIU_S6_PASSIVE = event.registerSkill(new LiuS6Passive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "liu_s6_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);
        LIU_S6_INNATE = event.registerSkill(new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "liu_s6_innate"))
                .setConsumption(30.0F).setAnimations(LiuSouth6MovesetAnims.LIU_S6_INNATE_1)), false);

        FIREFIST_INNATE = event.registerSkill(new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "spew_fire"))
                .setConsumption(30.0F).setMaxStack(2).setAnimations(FirefistMovesetAnims.FIREFIST_INNATE)), false);

        RAT_KNIFE_INNATE = event.registerSkill(new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "backstreets_dash"))
                .setConsumption(22.0F).setMaxStack(2).setAnimations(RatShankMovesetAnims.RAT_KNIFE_INNATE)), false);

        RAT_PIPE_INNATE = event.registerSkill(new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "dirty_blow"))
                .setConsumption(25.0F).setMaxStack(1).setAnimations(RatPipeMovesetAnims.RAT_PIPE_INNATE)), false);

        JUSTITIA_INNATE = event.registerSkill(new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "weight_of_sin"))
                .setConsumption(25.0F).setMaxStack(1).setAnimations(JustitiaMovesetAnims.JUSTITIA_INNATE_1)), false);

        FIREFIST_PASSIVE = event.registerSkill(new FirefistPassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "firefist_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);
        FIREFIST_GUARD =event.registerSkill(new FirefistActiveGuard(FirefistActiveGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "firefist_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);
        SUNSET_BLADE = event.registerSkill(new SunsetBladeSkill(SpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "sunset_blade")).setConsumption(20.0F).setMaxStack(3)), false);
        BLINKSTEP = event.registerSkill(new HeishouMaoBlinkstep(SpecialAttackSkill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "blinkstep")).setConsumption(20.0F).setMaxStack(2)), false);
        HEISHOU_MAO_PASSIVE = event.registerSkill(new HeishouMaoPassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "heishou_mao_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);
        HEISHOU_MAO_GUARD =event.registerSkill(new HeishouMaoActiveGuard(HeishouMaoActiveGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "heishou_mao_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);
        RAT_KNIFE_PASSIVE = event.registerSkill(new RatKnifePassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "rat_knife_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);
        RAT_KNIFE_GUARD =event.registerSkill(new RatKnifeGuard(RatKnifeGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "rat_knife_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        RAT_PIPE_PASSIVE = event.registerSkill(new RatPipePassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "rat_pipe_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);
        RAT_PIPE_GUARD = event.registerSkill(new RatPipeGuard(RatPipeGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "rat_pipe_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        JUSTITIA_PASSIVE = event.registerSkill(new JustitiaPassive(Skill.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "justitia_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);
        JUSTITIA_GUARD = event.registerSkill(new JustitiaActiveGuard(JustitiaActiveGuard.createBuilder(new ResourceLocation(EgoWeaponsMod.MODID, "justitia_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

    }
}

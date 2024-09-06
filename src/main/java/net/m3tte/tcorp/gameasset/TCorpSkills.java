//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.tcorp.gameasset;

import net.m3tte.tcorp.TcorpMod;
import net.m3tte.tcorp.TcorpModElements;
import net.m3tte.tcorp.skill.AtelierPistol.AtelierPassive;
import net.m3tte.tcorp.skill.AtelierPistol.BlacksilenceAtelierGuard;
import net.m3tte.tcorp.skill.AtelierShotgun.AtelierShotgunGuard;
import net.m3tte.tcorp.skill.AtelierShotgun.AtelierShotgunPassive;
import net.m3tte.tcorp.skill.*;
import net.m3tte.tcorp.skill.allas.AllasPassive;
import net.m3tte.tcorp.skill.allas.BlackSilenceAllasGuard;
import net.m3tte.tcorp.skill.durandal.BasicBlockablePassive;
import net.m3tte.tcorp.skill.magic_bullet.MagicBulletDetonate;
import net.m3tte.tcorp.skill.magic_bullet.MagicBulletGuard;
import net.m3tte.tcorp.skill.magic_bullet.MagicBulletPassive;
import net.m3tte.tcorp.skill.oldBoys.OldBoysGuard;
import net.m3tte.tcorp.skill.oldBoys.OldBoysPassive;
import net.m3tte.tcorp.skill.red_mist.RedMistActiveGuard;
import net.m3tte.tcorp.skill.red_mist.RedMistBlockable;
import net.minecraft.util.ResourceLocation;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.forgeevent.SkillRegistryEvent;
import yesman.epicfight.api.utils.ExtendedDamageSource.StunType;
import yesman.epicfight.api.utils.math.ExtraDamageType;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.*;

public class TCorpSkills {
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
    public TCorpSkills() {
    }

    public static void registerSkills(SkillRegistryEvent event) {
        LESSER_SPLIT_VERTICAL = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(TcorpMod.MODID, "lesser_split_vertical"))
                    .setConsumption(40.0F).setAnimations(TCorpAnimations.DURANDAL_DRAW)))
                    .newPropertyLine().addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(2.0F))
                    .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.durandal.strong", ':')))
                    .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.0F))
                    .addProperty(AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(20.0F))
                    .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(3.0F))
                    .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG).registerPropertiesToAnimation(), false);

        GREATER_SPLIT_VERTICAL = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(TcorpMod.MODID, "greater_split_vertical"))
                        .setConsumption(80.0F).setAnimations(TCorpAnimations.GREAT_SPLIT_VERTICAL)))
                .newPropertyLine().addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(2.0F))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.8F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(60.0F))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(6.0F)), false);

        HELLO = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(TcorpMod.MODID, "hello"))
                        .setConsumption(90.0F).setAnimations(TCorpAnimations.MIMICRY_HELLO)))
                .newPropertyLine().addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(6.0F)), false);


        WHEELS_SMASH = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(TcorpMod.MODID, "wheels_smash"))
                        .setConsumption(80.0F).setAnimations(TCorpAnimations.WHEELS_HEAVY)))
                .newPropertyLine().addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(2.0F))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.0F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(40.0F))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(3.0F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG).registerPropertiesToAnimation(), false);

        MOOK_CUT = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(TcorpMod.MODID, "mook_cut"))
                        .setConsumption(40.0F).setMaxStack(2).setAnimations(TCorpAnimations.MOOK_CUT))), false);
        CRYSTAL_ATELIER = event.registerSkill((
                new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(TcorpMod.MODID, "crystal_atelier"))
                    .setConsumption(40.0F)
                    .setAnimations(TCorpAnimations.CRYSTAL_ATELIER_SKILL))).newPropertyLine()
                    .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(3.0F))
                    .addProperty(AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(20.0F))
                    .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.0F))
                    .addProperty(AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(20.0F))
                    .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(6.0F))
                    .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                    .registerPropertiesToAnimation(), false);

        BLACKSILENCE_WEAK_GUARD =event.registerSkill(new BlackSilenceWeakGuard(BlackSilenceWeakGuard.createBuilder(new ResourceLocation(TcorpMod.MODID, "weak_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        BLACKSILENCE_PASSIVE = event.registerSkill(new BlackSilencePassive(Skill.createBuilder(new ResourceLocation(TcorpMod.MODID, "blacksilence_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        ALLAS_GUARD =event.registerSkill(new BlackSilenceAllasGuard(BlackSilenceAllasGuard.createBuilder(new ResourceLocation(TcorpMod.MODID, "allas_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        ALLAS_PASSIVE = event.registerSkill(new AllasPassive(Skill.createBuilder(new ResourceLocation(TcorpMod.MODID, "allas_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        ATELIER_GUARD =event.registerSkill(new BlacksilenceAtelierGuard(BlacksilenceAtelierGuard.createBuilder(new ResourceLocation(TcorpMod.MODID, "atelier_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        ATELIER_PASSIVE = event.registerSkill(new AtelierPassive(Skill.createBuilder(new ResourceLocation(TcorpMod.MODID, "atelier_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        ATELIER_SHOTGUN_GUARD =event.registerSkill(new AtelierShotgunGuard(AtelierShotgunGuard.createBuilder(new ResourceLocation(TcorpMod.MODID, "atelier_shotgun_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        ATELIER_SHOTGUN_PASSIVE = event.registerSkill(new AtelierShotgunPassive(Skill.createBuilder(new ResourceLocation(TcorpMod.MODID, "atelier_shotgun_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        RANGA_EVISCERATE = event.registerSkill(new BlacksilenceEviscerate(SpecialAttackSkill.createBuilder(new ResourceLocation(TcorpMod.MODID, "ranga_eviscerate")).setConsumption(35.0F))
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

        MAGIC_BULLET_DETONATE = event.registerSkill(new MagicBulletDetonate(SpecialAttackSkill.createBuilder(new ResourceLocation(TcorpMod.MODID, "magic_bullet_detonate")).setConsumption(35.0F)), false);

        GENERIC_ACTIVE_GUARD = event.registerSkill(new BlackSilenceActiveGuard(BlackSilenceActiveGuard.createBuilder(new ResourceLocation(TcorpMod.MODID, "blacksilence_active_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        BASIC_BLOCKABLE_PASSIVE = event.registerSkill(new BasicBlockablePassive(Skill.createBuilder(new ResourceLocation(TcorpMod.MODID, "basic_blockable_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        KALI_GUARD = event.registerSkill(new RedMistActiveGuard(BlackSilenceActiveGuard.createBuilder(new ResourceLocation(TcorpMod.MODID, "redmist_active_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        KALI_PASSIVE = event.registerSkill(new RedMistBlockable(Skill.createBuilder(new ResourceLocation(TcorpMod.MODID, "redmist_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);



        OLD_BOYS_GUARD = event.registerSkill(new OldBoysGuard(OldBoysGuard.createBuilder(new ResourceLocation(TcorpMod.MODID, "old_boys_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        OLD_BOYS_PASSIVE = event.registerSkill(new OldBoysPassive(Skill.createBuilder(new ResourceLocation(TcorpMod.MODID, "old_boys_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        MAGIC_BULLET_GUARD =event.registerSkill(new MagicBulletGuard(MagicBulletGuard.createBuilder(new ResourceLocation(TcorpMod.MODID, "magic_bullet_guard")).setRequiredXp(0).setCategory(GenericSkill.TC_GUARD)), false);

        MAGIC_BULLET_PASSIVE = event.registerSkill(new MagicBulletPassive(Skill.createBuilder(new ResourceLocation(TcorpMod.MODID, "magic_bullet_passive")).setCategory(SkillCategories.WEAPON_PASSIVE)), false);

        MAGIC_BULLET_EVADE = event.registerSkill(new StepSkill(DodgeSkill.createBuilder(new ResourceLocation(TcorpMod.MODID, "magic_bullet_evade")).setConsumption(3.0F).setAnimations(TCorpAnimations.MAGIC_BULLET_EVADE_FORWARD, TCorpAnimations.MAGIC_BULLET_EVADE_BACKWARD, TCorpAnimations.MAGIC_BULLET_EVADE_LEFT, TCorpAnimations.MAGIC_BULLET_EVADE_RIGHT)), false);
    }
}

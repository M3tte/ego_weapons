package net.m3tte.tcorp.skill.magic_bullet;

import net.m3tte.tcorp.gameasset.TCorpSkills;
import net.m3tte.tcorp.skill.GenericSkill;
import yesman.epicfight.gameasset.Skills;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class MagicBulletPassive extends Skill {
    public MagicBulletPassive(Builder<? extends Skill> builder) {
        super(builder);
    }
    Skill savedEvade;
    public void onInitiate(SkillContainer container) {
        SkillContainer guard = container.getExecuter().getSkillCapability().skillContainers[SkillCategories.GUARD.universalOrdinal()];
        SkillContainer evade = container.getExecuter().getSkillCapability().skillContainers[SkillCategories.DODGE.universalOrdinal()];


        if (!guard.isEmpty()) {
            container.getExecuter().getSkillCapability().skillContainers[GenericSkill.TC_GUARD.universalOrdinal()].setSkill(TCorpSkills.MAGIC_BULLET_GUARD);
        }
        if (!evade.isEmpty()) {
            savedEvade = evade.getSkill();
            evade.setSkill(TCorpSkills.MAGIC_BULLET_EVADE);
        }
    }


    public void onRemoved(SkillContainer container) {
        PlayerPatch<?> executer = container.getExecuter();
        SkillContainer mbguardskill = executer.getSkillCapability().skillContainers[GenericSkill.TC_GUARD.universalOrdinal()];

        SkillContainer guardskill = executer.getSkillCapability().skillContainers[SkillCategories.GUARD.universalOrdinal()];

        if (mbguardskill.hasSkill(TCorpSkills.MAGIC_BULLET_GUARD)) {
            mbguardskill.setSkill((Skill) null);
            guardskill.getSkill().onInitiate(mbguardskill);
        }

        SkillContainer dodgeskill = executer.getSkillCapability().skillContainers[SkillCategories.DODGE.universalOrdinal()];

        if (dodgeskill.hasSkill(TCorpSkills.MAGIC_BULLET_EVADE)) {
            if (savedEvade != null) {
                dodgeskill.setSkill(savedEvade);
            } else {
                dodgeskill.setSkill(Skills.STEP);
            }
            dodgeskill.getSkill().onInitiate(dodgeskill);

        }
    }


}

package net.m3tte.tcorp.skill;

import net.m3tte.tcorp.gameasset.TCorpSkills;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class BlackSilencePassive extends Skill {
    public BlackSilencePassive(Builder<? extends Skill> builder) {
        super(builder);
    }

    public void onInitiate(SkillContainer container) {
        SkillContainer guard = container.getExecuter().getSkillCapability().skillContainers[SkillCategories.GUARD.universalOrdinal()];


        if (!guard.isEmpty()) {
            container.getExecuter().getSkillCapability().skillContainers[GenericSkill.TC_GUARD.universalOrdinal()].setSkill(TCorpSkills.BLACKSILENCE_WEAK_GUARD);
        }

    }


    public void onRemoved(SkillContainer container) {
        PlayerPatch<?> executer = container.getExecuter();
        SkillContainer bsguardskill = executer.getSkillCapability().skillContainers[GenericSkill.TC_GUARD.universalOrdinal()];

        SkillContainer guardskill = executer.getSkillCapability().skillContainers[SkillCategories.GUARD.universalOrdinal()];

        if (bsguardskill.hasSkill(TCorpSkills.BLACKSILENCE_WEAK_GUARD)) {
            bsguardskill.setSkill((Skill) null);
            guardskill.getSkill().onInitiate(bsguardskill);
        }


    }
}

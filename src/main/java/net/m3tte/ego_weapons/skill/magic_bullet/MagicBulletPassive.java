package net.m3tte.ego_weapons.skill.magic_bullet;

import net.m3tte.ego_weapons.gameasset.EgoWeaponsSkills;
import net.m3tte.ego_weapons.skill.GenericSkill;
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
            container.getExecuter().getSkillCapability().skillContainers[GenericSkill.TC_GUARD.universalOrdinal()].setSkill(EgoWeaponsSkills.MAGIC_BULLET_GUARD);
        }
        if (!evade.isEmpty()) {
            savedEvade = evade.getSkill();
            evade.setSkill(EgoWeaponsSkills.MAGIC_BULLET_EVADE);
        }
    }


    public void onRemoved(SkillContainer container) {
        PlayerPatch<?> executer = container.getExecuter();
        SkillContainer mbguardskill = executer.getSkillCapability().skillContainers[GenericSkill.TC_GUARD.universalOrdinal()];

        SkillContainer guardskill = executer.getSkillCapability().skillContainers[SkillCategories.GUARD.universalOrdinal()];

        if (mbguardskill.hasSkill(EgoWeaponsSkills.MAGIC_BULLET_GUARD)) {
            mbguardskill.setSkill((Skill) null);
            guardskill.getSkill().onInitiate(mbguardskill);
        }

        SkillContainer dodgeskill = executer.getSkillCapability().skillContainers[SkillCategories.DODGE.universalOrdinal()];

        if (dodgeskill.hasSkill(EgoWeaponsSkills.MAGIC_BULLET_EVADE)) {
            if (savedEvade != null && savedEvade != EgoWeaponsSkills.MAGIC_BULLET_EVADE) {
                dodgeskill.setSkill(savedEvade);
            } else {
                dodgeskill.setSkill(Skills.STEP);
            }
            dodgeskill.getSkill().onInitiate(dodgeskill);

        }
    }


}

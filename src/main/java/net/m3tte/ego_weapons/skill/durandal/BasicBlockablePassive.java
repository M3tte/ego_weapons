package net.m3tte.ego_weapons.skill.durandal;

import net.m3tte.ego_weapons.gameasset.EgoWeaponsSkills;
import net.m3tte.ego_weapons.gameasset.movesets.HeishouMaoBranchAnims;
import net.m3tte.ego_weapons.gameasset.movesets.StigmaWorkshopMovesetAnims;
import net.m3tte.ego_weapons.skill.GenericSkill;
import net.minecraft.item.Item;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class BasicBlockablePassive extends Skill {

    String item;
    public BasicBlockablePassive(Builder<? extends Skill> builder, String item) {
        super(builder);
        this.item = item;
    }

    public void onInitiate(SkillContainer container) {
        SkillContainer guard = container.getExecuter().getSkillCapability().skillContainers[SkillCategories.GUARD.universalOrdinal()];

        switch (this.item) {
            case "stigma_workshop_sword":
                container.getExecuter().playAnimationSynchronized(StigmaWorkshopMovesetAnims.STIGMA_SWORD_EQUIP, 0);
                break;
        }


        if (!guard.isEmpty()) {
            container.getExecuter().getSkillCapability().skillContainers[GenericSkill.TC_GUARD.universalOrdinal()].setSkill(EgoWeaponsSkills.GENERIC_ACTIVE_GUARD);
        }
    }



    public void onRemoved(SkillContainer container) {
        PlayerPatch<?> executer = container.getExecuter();
        SkillContainer bsguardskill = executer.getSkillCapability().skillContainers[GenericSkill.TC_GUARD.universalOrdinal()];

        SkillContainer guardskill = executer.getSkillCapability().skillContainers[SkillCategories.GUARD.universalOrdinal()];

        if (bsguardskill.hasSkill(EgoWeaponsSkills.GENERIC_ACTIVE_GUARD)) {
            bsguardskill.setSkill((Skill) null);
            guardskill.getSkill().onInitiate(bsguardskill);
        }


    }


}

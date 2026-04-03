package net.m3tte.ego_weapons.skill.udjat;

import net.m3tte.ego_weapons.gameasset.EgoWeaponsSkills;
import net.m3tte.ego_weapons.gameasset.movesets.RatPipeMovesetAnims;
import net.m3tte.ego_weapons.skill.GenericSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.UUID;

import static yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType.ATTACK_SPEED_MODIFY_EVENT;

public class UdjatKhopeshPassive extends Skill {
    public UdjatKhopeshPassive(Builder<? extends Skill> builder) {
        super(builder);
    }

    private static final UUID EVENT_UUID = UUID.fromString("a395b592-fd97-12cb-9c13-03b3ac131203");


    public void onInitiate(SkillContainer container) {
        SkillContainer guard = container.getExecuter().getSkillCapability().skillContainers[SkillCategories.GUARD.universalOrdinal()];

        container.getExecuter().playAnimationSynchronized(RatPipeMovesetAnims.RAT_PIPE_EQUIP, 0);

        if (!guard.isEmpty()) {
            container.getExecuter().getSkillCapability().skillContainers[GenericSkill.TC_GUARD.universalOrdinal()].setSkill(EgoWeaponsSkills.UDJAT_KH_GUARD);
        }
    }



    public void onRemoved(SkillContainer container) {
        PlayerPatch<?> executer = container.getExecuter();
        SkillContainer bsguardskill = executer.getSkillCapability().skillContainers[GenericSkill.TC_GUARD.universalOrdinal()];

        SkillContainer guardskill = executer.getSkillCapability().skillContainers[SkillCategories.GUARD.universalOrdinal()];

        if (bsguardskill.hasSkill(EgoWeaponsSkills.UDJAT_KH_GUARD)) {
            bsguardskill.setSkill((Skill) null);
            guardskill.getSkill().onInitiate(bsguardskill);
        }

        container.getExecuter().getEventListener().removeListener(ATTACK_SPEED_MODIFY_EVENT, EVENT_UUID);
    }


}

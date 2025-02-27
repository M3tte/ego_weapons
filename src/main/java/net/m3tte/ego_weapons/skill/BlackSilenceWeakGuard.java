package net.m3tte.ego_weapons.skill;

import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.movesets.AtelierLogicMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCategories;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.EnergizingGuardSkill;
import yesman.epicfight.skill.GuardSkill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.eventlistener.HurtEvent;

public class BlackSilenceWeakGuard extends EnergizingGuardSkill {
    public BlackSilenceWeakGuard(Builder builder) {
        super(builder);
    }

    public static Builder createBuilder(ResourceLocation resourceLocation) {
        return GuardSkill.createBuilder(resourceLocation)
                .addAdvancedGuardMotion(EgoWeaponsCategories.RANGA, (item, player) -> BlackSilenceMovesetAnims.RANGA_GUARD_HIT)
                .addGuardMotion(EgoWeaponsCategories.RANGA, (item, player) -> BlackSilenceMovesetAnims.RANGA_GUARD_HIT)
                .addGuardBreakMotion(EgoWeaponsCategories.RANGA, (item, player) -> BlackSilenceMovesetAnims.RANGA_GUARD_STAGGER)
                .addAdvancedGuardMotion(EgoWeaponsCategories.WHEELS_INDUSTRY, (item, player) -> Animations.GREATSWORD_GUARD_HIT)
                .addGuardMotion(EgoWeaponsCategories.WHEELS_INDUSTRY, (item, player) -> Animations.GREATSWORD_GUARD_HIT)
                .addGuardBreakMotion(EgoWeaponsCategories.WHEELS_INDUSTRY, (item, player) -> Animations.GREATSWORD_GUARD_BREAK)
                .addAdvancedGuardMotion(EgoWeaponsCategories.ATELIER_SHOTGUN, (item, player) -> AtelierLogicMovesetAnims.ATELIER_REVOLVER_GUARD_HIT)
                .addGuardMotion(EgoWeaponsCategories.ATELIER_SHOTGUN, (item, player) -> AtelierLogicMovesetAnims.ATELIER_SHOTGUN_GUARD)
                .addGuardBreakMotion(EgoWeaponsCategories.ATELIER_SHOTGUN, (item, player) -> BlackSilenceMovesetAnims.RANGA_GUARD_STAGGER);
    }

    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
    }

    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
    }

    @Override
    public void guard(SkillContainer container, CapabilityItem itemCapapbility, HurtEvent.Pre event, float knockback, float impact, boolean advanced) {
        EmotionSystem.handleGuard(event.getPlayerPatch().getOriginal(), event.getAmount(), impact, false);
        super.guard(container, itemCapapbility, event, knockback, impact, advanced);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldDraw(SkillContainer container) {

        WeaponCategory[] cat = {EgoWeaponsCategories.ATELIER_SHOTGUN, EgoWeaponsCategories.WHEELS_INDUSTRY, EgoWeaponsCategories.RANGA};

        if (!((Float)container.getDataManager().getDataValue(PENALTY) > 0.0F))
            return false;


        for (WeaponCategory c : cat) {
            if (c.equals(container.getExecuter().getHoldingItemCapability(Hand.MAIN_HAND).getWeaponCategory()))
                    return true;
        }
        return false;
    }

    protected boolean isAdvancedGuard() {
        return false;
    }
}

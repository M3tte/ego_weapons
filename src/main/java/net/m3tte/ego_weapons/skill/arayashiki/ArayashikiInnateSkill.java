package net.m3tte.ego_weapons.skill.arayashiki;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.gameasset.movesets.ArayashikiMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.StigmaWorkshopMovesetAnims;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SpecialAttackSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.List;
import java.util.UUID;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

public class ArayashikiInnateSkill extends SpecialAttackSkill {
    private static final UUID EVENT_UUID = UUID.fromString("f082546a-b2f9-11eb-8539-0242ac130af3");
    private StaticAnimation sheathedVariant;
    private StaticAnimation unsheathedVariant;
    private StaticAnimation unsheathedVariant2;

    public ArayashikiInnateSkill(Builder<? extends Skill> builder) {
        super(builder);
        this.sheathedVariant = ArayashikiMovesetAnims.ARAYASHIKI_INNATE_1_S;
        this.unsheathedVariant = ArayashikiMovesetAnims.ARAYASHIKI_INNATE_1_U;
        this.unsheathedVariant2 = ArayashikiMovesetAnims.ARAYASHIKI_INNATE_2_U;
    }





    @Override
    public void executeOnServer(ServerPlayerPatch executer, PacketBuffer args) {
        //updateContainer(executer.getSkill(this.category));

        EgoWeaponsModVars.PlayerVariables playerVars = executer.getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

        if (playerVars.firingMode && executer.getOriginal().hasEffect(EgoWeaponsEffects.TIANSHIA_STAR.get())) {
            if (executer.getSkill(SkillCategories.WEAPON_SPECIAL_ATTACK).getStack() >= 2) {
                executer.playAnimationSynchronized(this.unsheathedVariant2, 0);
                setStackSynchronize(executer, 0);
            } else {
                executer.playAnimationSynchronized(this.unsheathedVariant, 0);

            }

        } else {
            executer.playAnimationSynchronized(this.sheathedVariant, 0);
        }

        super.executeOnServer(executer, args);
    }

    @Override
    public List<ITextComponent> getTooltipOnItem(ItemStack itemStack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<ITextComponent> list = super.getTooltipOnItem(itemStack, cap, playerCap);
        this.generateTooltipforPhase(list, itemStack, cap, playerCap, this.properties.get(0), "First Strike:");
        this.generateTooltipforPhase(list, itemStack, cap, playerCap, this.properties.get(1), "Second Strike:");
        this.generateTooltipforPhase(list, itemStack, cap, playerCap, this.properties.get(2), "Third Strike:");
        return list;
    }

    @Override
    public SpecialAttackSkill registerPropertiesToAnimation() {
        AttackAnimation _first = ((AttackAnimation)this.sheathedVariant);
        _first.phases[0].addProperties(this.properties.get(0).entrySet());

        return this;
    }
}
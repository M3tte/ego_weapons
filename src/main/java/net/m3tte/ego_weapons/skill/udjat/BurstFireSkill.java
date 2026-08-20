package net.m3tte.ego_weapons.skill.udjat;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.gameasset.movesets.LCARifleMovesetAnims;
import net.m3tte.ego_weapons.procedures.EntityTick;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
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

public class BurstFireSkill extends SpecialAttackSkill {
    private static final UUID EVENT_UUID = UUID.fromString("f032257b-b2f9-11eb-8539-0242ac130af3");
    private StaticAnimation first;

    public BurstFireSkill(Builder<? extends Skill> builder) {
        super(builder);
        this.first = LCARifleMovesetAnims.LCA_RIFLE_INNATE_1;
    }


    public static void reuseEventTest(ServerPlayerPatch patch, String animationIdentifier) {

        PlayerEntity player = patch.getOriginal();
        ItemStack mainHandItem = player.getItemInHand(Hand.MAIN_HAND);
        int entitySpeed = EgoWeaponsEffects.speedMult(player);

        SkillContainer container = patch.getSkill(SkillCategories.WEAPON_SPECIAL_ATTACK);
        if (!patch.currentlyAttackedEntity.isEmpty() && (container.isReady() || (container.getExecuter().getOriginal()).isCreative())) {

            if (animationIdentifier.equals("innate_1") && entitySpeed >= 2) {

                container.getSkill().setStackSynchronize(patch, container.getStack()-1);
                patch.currentlyAttackedEntity.clear();
                if (!player.level.isClientSide()) {
                    patch.playAnimationSynchronized(LCARifleMovesetAnims.LCA_RIFLE_INNATE_2, 0);
                }

            } else if (animationIdentifier.equals("innate_2") && entitySpeed >= 4) {
                container.getSkill().setStackSynchronize(patch, container.getStack()-1);
                patch.currentlyAttackedEntity.clear();
                if (!player.level.isClientSide()) {
                    patch.playAnimationSynchronized(LCARifleMovesetAnims.LCA_RIFLE_INNATE_3, 0);
                }
            }
        }
    }

    public static void reuseOffhandTest(ServerPlayerPatch patch, String animationIdentifier) {

        PlayerEntity player = patch.getOriginal();
        int entitySpeed = EgoWeaponsEffects.speedMult(player);

        if (!patch.currentlyAttackedEntity.isEmpty()) {

            if (animationIdentifier.equals("sp_o_1")) {

                if (entitySpeed >= 2) {
                    patch.currentlyAttackedEntity.clear();
                    if (!player.level.isClientSide()) {
                        patch.playAnimationSynchronized(LCARifleMovesetAnims.LCA_RIFLE_SPECIAL_O_2, 0);
                    }
                } else {
                    EntityTick.regenerateLight(patch.getOriginal(), 1, true);
                }


            } else if (animationIdentifier.equals("sp_o_2") && entitySpeed >= 4) {
                patch.currentlyAttackedEntity.clear();
                if (!player.level.isClientSide()) {
                    patch.playAnimationSynchronized(LCARifleMovesetAnims.LCA_RIFLE_SPECIAL_O_3, 0);
                }
            }
        }
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);

    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecuter().getEventListener().removeListener(EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID);
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, PacketBuffer args) {

        executer.playAnimationSynchronized(this.first, 0);
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
        AttackAnimation _first = ((AttackAnimation)this.first);
        _first.phases[0].addProperties(this.properties.get(0).entrySet());

        return this;
    }
}
package net.m3tte.ego_weapons.skill.stigmaSword;

import net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.StigmaWorkshopMovesetAnims;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.server.SPSkillExecutionFeedback;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SpecialAttackSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.List;
import java.util.UUID;

public class SunsetBladeSkill extends SpecialAttackSkill {
    private static final UUID EVENT_UUID = UUID.fromString("f082557a-b2f9-11eb-8539-0242ac130af3");
    private StaticAnimation first;
    private StaticAnimation firstWinddown;
    private StaticAnimation second;
    private StaticAnimation secondWinddown;
    private StaticAnimation third;

    public SunsetBladeSkill(Builder<? extends Skill> builder) {
        super(builder);
        this.first = StigmaWorkshopMovesetAnims.STIGMA_SWORD_INNATE_1;
        this.firstWinddown = StigmaWorkshopMovesetAnims.STIGMA_SWORD_INNATE_1E;
        this.second = StigmaWorkshopMovesetAnims.STIGMA_SWORD_INNATE_2;
        this.secondWinddown = StigmaWorkshopMovesetAnims.STIGMA_SWORD_INNATE_2E;
        this.third = StigmaWorkshopMovesetAnims.STIGMA_SWORD_INNATE_3;
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getEventListener().addEventListener(EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID, (event) -> {
            List<LivingEntity> hitEnemies = event.getHitEntity();
            if (!hitEnemies.isEmpty() && (container.isReady() || (container.getExecuter().getOriginal()).isCreative())) {

                if (event.getAnimationId() == StigmaWorkshopMovesetAnims.STIGMA_SWORD_INNATE_1.getId()) {
                    setStackSynchronize(event.getPlayerPatch(), Math.max(0, container.getStack() - 1));

                    updateContainer(container);
                    event.getPlayerPatch().reserveAnimation(this.second);
                    event.getPlayerPatch().getServerAnimator().getPlayerFor(null).reset();
                    event.getPlayerPatch().currentlyAttackedEntity.clear();
                    this.second.tick(event.getPlayerPatch());
                } else if (event.getAnimationId() == StigmaWorkshopMovesetAnims.STIGMA_SWORD_INNATE_2.getId()) {
                    setStackSynchronize(event.getPlayerPatch(), Math.max(0, container.getStack() - 1));

                    updateContainer(container);
                    event.getPlayerPatch().reserveAnimation(this.third);
                    event.getPlayerPatch().getServerAnimator().getPlayerFor(null).reset();
                    event.getPlayerPatch().currentlyAttackedEntity.clear();
                    this.third.tick(event.getPlayerPatch());
                }


            } else {
                if (event.getAnimationId() == StigmaWorkshopMovesetAnims.STIGMA_SWORD_INNATE_1.getId()) {
                    event.getPlayerPatch().reserveAnimation(this.firstWinddown);
                    event.getPlayerPatch().getServerAnimator().getPlayerFor(null).reset();
                    event.getPlayerPatch().currentlyAttackedEntity.clear();
                    this.firstWinddown.tick(event.getPlayerPatch());
                }
                else if (event.getAnimationId() == StigmaWorkshopMovesetAnims.STIGMA_SWORD_INNATE_2.getId()) {
                    event.getPlayerPatch().reserveAnimation(this.secondWinddown);
                    event.getPlayerPatch().getServerAnimator().getPlayerFor(null).reset();
                    event.getPlayerPatch().currentlyAttackedEntity.clear();
                    this.secondWinddown.tick(event.getPlayerPatch());
                }
            }
        });
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
        AttackAnimation _second = ((AttackAnimation)this.second);
        _first.phases[0].addProperties(this.properties.get(0).entrySet());
        _second.phases[0].addProperties(this.properties.get(1).entrySet());

        return this;
    }
}
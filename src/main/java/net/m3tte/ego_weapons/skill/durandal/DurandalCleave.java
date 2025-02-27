package net.m3tte.ego_weapons.skill.durandal;

import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.movesets.DurandalMovesetAnims;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SpecialAttackSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.List;
import java.util.UUID;

public class DurandalCleave extends SpecialAttackSkill {
    private static final UUID EVENT_UUID = UUID.fromString("f082557a-b2f9-11eb-8529-0242ac130af3");
    private StaticAnimation first;
    private StaticAnimation second;
    private StaticAnimation third;

    public DurandalCleave(Builder<? extends Skill> builder) {
        super(builder);
        this.first = DurandalMovesetAnims.DURANDAL_SPECIAL_1;
        this.second = DurandalMovesetAnims.DURANDAL_SPECIAL_2;
        this.third = DurandalMovesetAnims.DURANDAL_SPECIAL_3;
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getEventListener().addEventListener(EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID, (event) -> {
            if (event.getAnimationId() == this.first.getId()) {
                List<LivingEntity> hitEnemies = event.getHitEntity();

                if (!hitEnemies.isEmpty() && hitEnemies.get(0).isAlive()) {
                    event.getPlayerPatch().reserveAnimation(this.second);
                    event.getPlayerPatch().getServerAnimator().getPlayerFor(null).reset();
                    event.getPlayerPatch().currentlyAttackedEntity.clear();
                    this.second.tick(event.getPlayerPatch());
                }
            } else if (event.getAnimationId() == this.second.getId()) {
                List<LivingEntity> hitEnemies = event.getHitEntity();

                if (!hitEnemies.isEmpty() && hitEnemies.get(0).isAlive()) {
                    event.getPlayerPatch().reserveAnimation(this.third);
                    event.getPlayerPatch().getServerAnimator().getPlayerFor(null).reset();
                    event.getPlayerPatch().currentlyAttackedEntity.clear();
                    this.third.tick(event.getPlayerPatch());
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
        return list;
    }

    @Override
    public SpecialAttackSkill registerPropertiesToAnimation() {
        AttackAnimation _first = ((AttackAnimation)this.first);
        AttackAnimation _second = ((AttackAnimation)this.second);
        AttackAnimation _third = ((AttackAnimation)this.third);
        _first.phases[0].addProperties(this.properties.get(0).entrySet());
        _second.phases[0].addProperties(this.properties.get(1).entrySet());
        _third.phases[0].addProperties(this.properties.get(2).entrySet());

        return this;
    }
}
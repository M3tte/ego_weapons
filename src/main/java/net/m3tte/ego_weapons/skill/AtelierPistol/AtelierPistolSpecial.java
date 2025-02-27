package net.m3tte.ego_weapons.skill.AtelierPistol;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.movesets.AtelierLogicMovesetAnims;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SpecialAttackSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.List;
import java.util.UUID;

public class AtelierPistolSpecial extends SpecialAttackSkill {
    private static final UUID EVENT_UUID = UUID.fromString("f082557a-b229-11eb-8529-0242ac131df3");
    private StaticAnimation first;
    private StaticAnimation second;

    public AtelierPistolSpecial(Builder<? extends Skill> builder) {
        super(builder);
        this.first = AtelierLogicMovesetAnims.ATELIER_REVOLVER_SPECIAL_EMPTY_1;
        this.second = AtelierLogicMovesetAnims.ATELIER_REVOLVER_SPECIAL_EMPTY_2;
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
            }
        });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecuter().getEventListener().removeListener(EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID);
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, PacketBuffer args) {
        if (executer.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("ammo") >= 2) {
            executer.playAnimationSynchronized(AtelierLogicMovesetAnims.ATELIER_REVOLVER_SPECIAL, 0);
        } else {
            executer.playAnimationSynchronized(this.first, 0);
        }

        super.executeOnServer(executer, args);
    }



    @Override
    public List<ITextComponent> getTooltipOnItem(ItemStack itemStack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<ITextComponent> list = super.getTooltipOnItem(itemStack, cap, playerCap);
        return list;
    }

    @Override
    public SpecialAttackSkill registerPropertiesToAnimation() {
        AttackAnimation _first = ((AttackAnimation)this.first);
        AttackAnimation _second = ((AttackAnimation)this.second);

        return this;
    }
}
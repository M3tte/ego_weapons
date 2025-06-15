package net.m3tte.ego_weapons.skill.heishou_mao;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.gameasset.movesets.HeishouMaoBranchAnims;
import net.m3tte.ego_weapons.gameasset.movesets.StigmaWorkshopMovesetAnims;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SpecialAttackSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.effect.EpicFightMobEffects;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.List;
import java.util.UUID;

public class HeishouMaoBlinkstep extends SpecialAttackSkill {
    private static final UUID EVENT_UUID = UUID.fromString("f082557a-b2f9-11eb-8539-0242ac130af3");
    private StaticAnimation first;
    private StaticAnimation firstWinddown;
    private StaticAnimation second;
    private StaticAnimation secondWinddown;
    private StaticAnimation third;

    public HeishouMaoBlinkstep(Builder<? extends Skill> builder) {
        super(builder);
        this.first = HeishouMaoBranchAnims.HEISHOU_MAO_INNATE_1;
        this.firstWinddown = HeishouMaoBranchAnims.HEISHOU_MAO_INNATE_1E;
        this.second = HeishouMaoBranchAnims.HEISHOU_MAO_INNATE_2;
        this.secondWinddown = HeishouMaoBranchAnims.HEISHOU_MAO_INNATE_2E;
        this.third = HeishouMaoBranchAnims.HEISHOU_MAO_INNATE_3;
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getEventListener().addEventListener(EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID, (event) -> {
            List<LivingEntity> hitEnemies = event.getHitEntity();

            if (EgoWeaponsEffects.speedMult(container.getExecuter().getOriginal()) >= 5 && event.getAnimationId() == this.second.getId()) {
                event.getPlayerPatch().reserveAnimation(this.third);
                event.getPlayerPatch().getServerAnimator().getPlayerFor(null).reset();
                event.getPlayerPatch().currentlyAttackedEntity.clear();
                if (EgoWeaponsEffects.speedMult(container.getExecuter().getOriginal()) >= 7) {
                    container.getExecuter().getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 50, 0));
                }
                this.third.tick(event.getPlayerPatch());
            } else if ((container.isReady() || (container.getExecuter().getOriginal()).isCreative())) {
                if (event.getAnimationId() == this.first.getId()) {
                    setStackSynchronize(event.getPlayerPatch(), Math.max(0, container.getStack() - 1));
                    updateContainer(container);
                    event.getPlayerPatch().reserveAnimation(this.second);
                    if (EgoWeaponsEffects.speedMult(container.getExecuter().getOriginal()) >= 7) {
                        container.getExecuter().getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 50, 0));
                    }
                    event.getPlayerPatch().getServerAnimator().getPlayerFor(null).reset();
                    event.getPlayerPatch().currentlyAttackedEntity.clear();
                    this.second.tick(event.getPlayerPatch());
                }
            } else {
                if (event.getAnimationId() == this.first.getId()) {
                    event.getPlayerPatch().reserveAnimation(this.firstWinddown);
                    event.getPlayerPatch().getServerAnimator().getPlayerFor(null).reset();
                    event.getPlayerPatch().currentlyAttackedEntity.clear();
                    this.firstWinddown.tick(event.getPlayerPatch());
                }
                else if (event.getAnimationId() == this.second.getId()) {
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

        if (EgoWeaponsEffects.speedMult(executer.getOriginal()) >= 7) {
            executer.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 50, 0));
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
        AttackAnimation _first = ((AttackAnimation)this.first);
        AttackAnimation _second = ((AttackAnimation)this.second);
        _first.phases[0].addProperties(this.properties.get(0).entrySet());
        _second.phases[0].addProperties(this.properties.get(1).entrySet());

        return this;
    }
}
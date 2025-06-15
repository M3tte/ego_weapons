package net.m3tte.ego_weapons.skill;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import yesman.epicfight.skill.GuardSkill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

public class NonSpamGuardSkill extends GuardSkill {
    public NonSpamGuardSkill(Builder builder, float blockCancelPenalty, int blockCancelTime, float attackPenaltyDecrement, float attackPenaltyFactor) {
        super(builder);

        this.blockCancelPenalty = blockCancelPenalty;
        this.blockCancelTime = blockCancelTime;
        this.attackPenaltyFactor = attackPenaltyFactor;
        this.attackPenaltyDecrement = attackPenaltyDecrement;
    }
    private static final SkillDataManager.SkillDataKey<Integer> LAST_ACTIVE = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
    private static final SkillDataManager.SkillDataKey<Integer> PARRY_MOTION_COUNTER = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);

    float blockCancelPenalty = 0.3f;
    int blockCancelTime = 30;
    float attackPenaltyDecrement = 0.5f;
    float attackPenaltyFactor = 2f;


    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.SERVER_ITEM_USE_EVENT, EVENT_UUID);
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.SERVER_ITEM_STOP_EVENT, EVENT_UUID);
        super.onRemoved(container);
    }


    public SkillDataManager.SkillDataKey<Integer> getLastActive() {
        return LAST_ACTIVE;
    }

    public SkillDataManager.SkillDataKey<Integer> getParryMotions() {
        return PARRY_MOTION_COUNTER;
    }

    // Ensures the last active is actually applied
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getDataManager().registerData(LAST_ACTIVE);
        container.getDataManager().registerData(PARRY_MOTION_COUNTER);
        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.SERVER_ITEM_USE_EVENT, EVENT_UUID, (event) -> {
            CapabilityItem itemCapability = event.getPlayerPatch().getHoldingItemCapability(Hand.MAIN_HAND);
            if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.GUARD) && this.isExecutableState(event.getPlayerPatch())) {
                event.getPlayerPatch().getOriginal().startUsingItem(Hand.MAIN_HAND);
            }
            container.getDataManager().setData(LAST_ACTIVE, event.getPlayerPatch().getOriginal().tickCount);
        });

        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.SERVER_ITEM_STOP_EVENT, EVENT_UUID, (event) -> {
            ServerPlayerEntity serverplayer = event.getPlayerPatch().getOriginal();
            container.getDataManager().setDataSync(LAST_HIT_TICK, serverplayer.tickCount, serverplayer);
            if (serverplayer.tickCount - container.getDataManager().getDataValue(LAST_ACTIVE) < 30) {
                container.getDataManager().setDataSync(PENALTY, container.getDataManager().getDataValue(PENALTY) + 0.3f, serverplayer);
                event.getPlayerPatch().playSound(SoundEvents.ITEM_BREAK, 0.2f, 0.5F, 0.7F);
            }
        });
        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_POST, EVENT_UUID, (event) -> {
            container.getDataManager().setDataSync(PENALTY, Math.max(0, container.getDataManager().getDataValue(PENALTY) / this.attackPenaltyFactor - this.attackPenaltyDecrement), (ServerPlayerEntity)((ServerPlayerPatch)event.getPlayerPatch()).getOriginal());
        });
    }

}

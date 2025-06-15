package net.m3tte.ego_weapons.gameasset;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.EpicFightDamageSource;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class BasicEgoAttackAnimation extends BasicAttackAnimation {

    boolean triggersEffects = true;
    String uniqueIdentifier = "";

    public BasicEgoAttackAnimation(float convertTime, float antic, float contact, float recovery, @Nullable Collider collider, String index, String path, Model model) {
        super(convertTime, antic, contact, recovery, collider, index, path, model);
    }

    public BasicEgoAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, String index, String path, Model model) {
        super(convertTime, antic, preDelay, contact, recovery, collider, index, path, model);
    }

    public BasicEgoAttackAnimation(float convertTime, float antic, float contact, float recovery, Hand hand, @Nullable Collider collider, String index, String path, Model model) {
        super(convertTime, antic, contact, recovery, hand, collider, index, path, model);
    }


    public <V> BasicEgoAttackAnimation addProperty(EgoWeaponsAttackProperty<V> propertyType, V value) {
        this.properties.put(propertyType, value);
        return this;
    }

    @Override
    public ExtendedDamageSource getExtendedDamageSource(LivingEntityPatch<?> entitypatch, Entity target, Phase phase) {
        ExtendedDamageSource source = super.getExtendedDamageSource(entitypatch, target, phase);

        if (source instanceof EpicFightDamageSource) {
            EpicFightDamageSource EFsource = (EpicFightDamageSource) source;
            if (this.properties.getOrDefault(EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.DEFAULT).equals(AttackLogicPredicate.MAGIC_BULLET_FIRE)) {
                EFsource.setMagic();
            }
            if (this.properties.getOrDefault(EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.DEFAULT).equals(AttackLogicPredicate.GSH)) {
                EFsource.setMagic();
            }
            if (this.properties.getOrDefault(EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.DEFAULT).equals(AttackLogicPredicate.PIERCE_GUARD_DODGE)) {
                EFsource.setMagic();
            }
            if (entitypatch.getOriginal().getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.SUIT_OF_THE_BLACK_SILENCE.get())) {
                if (this.properties.getOrDefault(EgoWeaponsAttackProperty.LAST_OF_COMBO, false).equals(true)) {
                    EFsource.setMagic();
                    // Applies cooldown after. Locks all other on hit effects from triggering as well.
                    if (entitypatch.getOriginal() instanceof PlayerEntity) {
                        PlayerEntity player = (PlayerEntity) entitypatch.getOriginal();

                        if (!player.getCooldowns().isOnCooldown(player.getMainHandItem().getItem())) {
                            player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), 5);
                            EgoWeaponsEffects.POISE.get().increment(entitypatch.getOriginal(), 0, 3);

                        }
                    }
                }
            }
        }
        return source;
    }


}

package net.m3tte.ego_weapons.gameasset;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.EpicFightDamageSource;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Optional;
import java.util.function.Consumer;

public class EgoAttackAnimation extends AttackAnimation {


    public EgoAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, String index, String path, Model model) {
        super(convertTime, antic, preDelay, contact, recovery, collider, index, path, model);
    }

    public EgoAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, Hand hand, @Nullable Collider collider, String index, String path, Model model) {
        super(convertTime, antic, preDelay, contact, recovery, hand, collider, index, path, model);
    }

    public EgoAttackAnimation(float convertTime, String path, Model model, Phase... phases) {
        super(convertTime, path, model, phases);
    }

    public <V> EgoAttackAnimation addProperty(EgoWeaponsAttackProperty<V> propertyType, V value) {
        this.properties.put(propertyType, value);
        return this;
    }



    @Override
    public ExtendedDamageSource getExtendedDamageSource(LivingEntityPatch<?> entitypatch, Entity target, Phase phase) {
        ExtendedDamageSource source = super.getExtendedDamageSource(entitypatch, target, phase);

        if (source instanceof EpicFightDamageSource) {
            EpicFightDamageSource EFsource = (EpicFightDamageSource) source;
            AttackLogicPredicate predicate = (AttackLogicPredicate) this.properties.getOrDefault(EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.DEFAULT);

            switch (predicate) {
                case GSH:
                case PIERCE_GUARD_DODGE:
                case FIREFIST_3:
                case MAGIC_BULLET_FIRE:
                    EFsource.setMagic();
                    break;
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

    @Override
    protected int getMaxStrikes(LivingEntityPatch<?> entitypatch, Phase phase) {

        if (this.properties.getOrDefault(EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.DEFAULT).equals(AttackLogicPredicate.MAGIC_BULLET_FIRE)) {
            return EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entitypatch.getOriginal());
        }

        return super.getMaxStrikes(entitypatch, phase);
    }

    public static boolean canProcEffects(LivingEntity entity) {

        if (entity == null)
            return false;

        LivingEntityPatch<?> entityPatch = (LivingEntityPatch<?>) entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

        if (entityPatch != null) {
            DynamicAnimation currentanim = entityPatch.getServerAnimator().animationPlayer.getAnimation();

            if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation) {
                //System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

                boolean triggersEffects = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.TRIGGERS_EFFECTS).orElse(true);

                AttackAnimation.Phase phase = null;
                if (currentanim instanceof EgoAttackAnimation) {
                    phase = ((EgoAttackAnimation) currentanim).getPhaseByTime(entityPatch.getAnimator().getPlayerFor(currentanim).getElapsedTime());
                }

                if (phase instanceof EgoAttackAnimation.EgoAttackPhase) {
                    Boolean elp = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.SWING_EVENT).orElse(null);

                    if (elp != null)
                        triggersEffects = elp;
                }

                return triggersEffects;
            }
        }

        return true;
    }

    public static class EgoWeaponsAttackProperty<T> extends AnimationProperty<T> {
        public static final EgoWeaponsAttackProperty<Consumer<LivingEntityPatch<?>>> SWING_EFFECT = new EgoWeaponsAttackProperty<>();
        public static final EgoWeaponsAttackProperty<Boolean> TRIGGERS_EFFECTS = new EgoWeaponsAttackProperty<>();
        public static final EgoWeaponsAttackProperty<String> IDENTIFIER = new EgoWeaponsAttackProperty<>();
        public static final EgoWeaponsAttackProperty<Boolean> LAST_OF_COMBO = new EgoWeaponsAttackProperty<>();
        public static final EgoWeaponsAttackProperty<Boolean> CONSUMES_AMMO = new EgoWeaponsAttackProperty<>();
        public static final EgoWeaponsAttackProperty<AttackMoveType> ATTACK_MOVE_TYPE = new EgoWeaponsAttackProperty<>();
        public static final EgoWeaponsAttackProperty<AttackTypes> ATTACK_TYPE = new EgoWeaponsAttackProperty<>();
        public static final EgoWeaponsAttackProperty<DamageTypes> DAMAGE_TYPE = new EgoWeaponsAttackProperty<>();
        public static final EgoWeaponsAttackProperty<AttackLogicPredicate> LOGIC_PREDICATE = new EgoWeaponsAttackProperty<>();
        public static final EgoWeaponsAttackProperty<Boolean> SHOULD_CLASH = new EgoWeaponsAttackProperty<>();
        public static final EgoWeaponsAttackProperty<Boolean> CLASH_KNOCK = new EgoWeaponsAttackProperty<>();
        public static final EgoWeaponsAttackProperty<String> DEATH_MESSAGE = new EgoWeaponsAttackProperty<>();
        public static final EgoWeaponsAttackProperty<Boolean> DISABLE_COLLISION = new EgoWeaponsAttackProperty<>();

        public EgoWeaponsAttackProperty() {
        }
    }

    public static class EgoAttackPhase extends Phase {

        public <V> Optional<V> getProperty(EgoWeaponsAttackPhaseProperty<V> propertyType) {
            return super.getProperty(propertyType);
        }




        public static class EgoWeaponsAttackPhaseProperty<T> extends AttackPhaseProperty<T> {
            public static final EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<Boolean> SWING_EVENT = new EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<>();
            public static final EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<Consumer<LivingEntityPatch<?>>> SWING_EFFECT = new EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<>();
            public static final EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<String> IDENTIFIER = new EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<>();
            public static final EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<Boolean> LAST_OF_COMBO = new EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<>();
            public static final EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<Boolean> CONSUMES_AMMO = new EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<>();
            public static final EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<AttackMoveType> ATTACK_MOVE_TYPE = new EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<>();
            public static final EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<AttackTypes> ATTACK_TYPE = new EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<>();
            public static final EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<DamageTypes> DAMAGE_TYPE = new EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<>();

            public static final  EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<Boolean> SHOULD_CLASH = new EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<>();
            public static final  EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<Boolean> CLASH_KNOCK = new EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<>();
            public static final EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<AttackLogicPredicate> LOGIC_PREDICATE = new EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<>();
            public static final  EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<String> DEATH_MESSAGE = new EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty<>();
        }

        public EgoAttackPhase(float start, float antic, float contact, float recovery, float end, String jointName, Collider collider) {
            super(start, antic, contact, recovery, end, jointName, collider);
        }

        public EgoAttackPhase(float start, float antic, float contact, float recovery, float end, Hand hand, String jointName, Collider collider) {
            super(start, antic, contact, recovery, end, hand, jointName, collider);
        }

        public EgoAttackPhase(float start, float antic, float preDelay, float contact, float recovery, float end, String jointName, Collider collider) {
            super(start, antic, preDelay, contact, recovery, end, jointName, collider);
        }

        public EgoAttackPhase(float start, float antic, float preDelay, float contact, float recovery, float end, Hand hand, String jointName, Collider collider) {
            super(start, antic, preDelay, contact, recovery, end, hand, jointName, collider);
        }

        public <V> EgoAttackPhase addProperty(EgoWeaponsAttackPhaseProperty<V> propertyType, V value) {
            this.properties.put(propertyType, value);
            return this;
        }
    }
}

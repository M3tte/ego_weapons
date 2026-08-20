package net.m3tte.ego_weapons.world.capabilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.gameasset.AttackLogicPredicate;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.network.packages.VFXPackages;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;
import org.spongepowered.asm.mixin.Unique;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Random;

public class UtilitySystems {


    public static int calculateBlockDelay(LivingEntity entity, int time) {
        int chesedStacks = EgoWeaponsEffects.CHESEDS_LATENCY.get().getPotency(entity);

        if (chesedStacks > 0) {
            time = (int) (time + chesedStacks);
        }

        return time;
    }

    public static float getLossOfSelf(Entity entity) {
        float amnt = 0;

        if (entity instanceof LivingEntity) {
            amnt = Math.min(1,0.01f * EgoWeaponsEffects.LOSS_OF_SELF.get().getPotency((LivingEntity) entity) + 0.01f * EgoWeaponsEffects.SEVER_THE_THREAD.get().getPotency((LivingEntity) entity));
        }

        return amnt;
    }




    public static void sendShockwavePacket(PlayerEntity entity, float impact, float shakeStrength, float shakeSpeed, float fov) {
        if (entity instanceof ServerPlayerEntity) {
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) entity), new VFXPackages.ShockwaveShakePackage(impact, shakeStrength, shakeSpeed, fov));
        }
    }


    public static final String[] valids = {"ego_weapons:oeufi_contract", "ego_weapons:lca_rifle"};

    @Unique
    public static String noisyText(String input, float amount, long seed) {
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {

            char c = input.charAt(i);

            // preserve spaces
            if (c == ' ') {
                out.append(c);
                continue;
            }

            // pseudo-random value based on index
            double noise = pseudoRandom(i, seed);

            if (noise < amount) {
                out.append(' ');
            } else {
                out.append(c);
            }
        }

        return out.toString();
    }

    private static double pseudoRandom(int x, long seed) {
        long n = x * 374761393L + seed * 668265263L;
        n = (n ^ (n >> 13)) * 1274126177L;
        n ^= (n >> 16);

        return (n & 0xFFFFFFFFL) / (double)0xFFFFFFFFL;
    }

    public static EGOAttackContext generateAttackContext(LivingEntityPatch<?> patch) {

        if (patch == null)
            return null;

        return new EGOAttackContext(patch);
    }

    public static Hand getHandFromAnim(LivingEntity source) {
        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

        Hand targetHand = Hand.MAIN_HAND;

        if (entitypatch != null) {

            DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

            targetHand = (currentanim.getRealAnimation()).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.TARGET_HAND).orElse(Hand.MAIN_HAND);

            AttackAnimation.Phase phase = null;
            if (currentanim instanceof AttackAnimation) {
                phase = ((AttackAnimation) currentanim).getPhaseByTime(entitypatch.getAnimator().getPlayerFor(currentanim).getElapsedTime());
            }

            if (phase instanceof EgoAttackAnimation.EgoAttackPhase) {
                targetHand = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TARGET_HAND).orElse(targetHand);
            }
        }

        return targetHand;
    }

    public static class EGOAttackContext {
        boolean finalCoin = false;
        String animationIdentifier = "";
        boolean usesAmmo = false;

        boolean triggersEffects = true;

        boolean validEgoAnimation = false;
        DynamicAnimation animation = null;
        AttackLogicPredicate logicPredicate = AttackLogicPredicate.DEFAULT;

        public EGOAttackContext(LivingEntityPatch<?> entityPatch) {

            DynamicAnimation currentanim = entityPatch.getServerAnimator().animationPlayer.getAnimation();

            this.validEgoAnimation = currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation;
            this.animation = currentanim;
            if (this.validEgoAnimation) {
                //System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

                String animIdent = (currentanim.getRealAnimation()).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER).orElse("");
                boolean consumesAmmo = (currentanim.getRealAnimation()).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO).orElse(false);
                boolean finalOfCombo = (currentanim.getRealAnimation()).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.FINAL_COIN).orElse(false);
                boolean triggersFX = (currentanim.getRealAnimation()).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.TRIGGERS_EFFECTS).orElse(true);
                AttackLogicPredicate logicPredicate = (currentanim.getRealAnimation()).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE).orElse(AttackLogicPredicate.DEFAULT);

                AttackAnimation.Phase phase = null;
                if (currentanim instanceof AttackAnimation) {
                    phase = ((AttackAnimation) currentanim).getPhaseByTime(entityPatch.getAnimator().getPlayerFor(currentanim).getElapsedTime());
                }

                if (phase instanceof EgoAttackAnimation.EgoAttackPhase) {
                    animIdent = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER).orElse(animIdent);
                    consumesAmmo = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.CONSUMES_AMMO).orElse(consumesAmmo);
                    finalOfCombo = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.FINAL_COIN).orElse(finalOfCombo);
                    triggersFX = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TRIGGERS_EFFECTS).orElse(triggersFX);
                    logicPredicate = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.LOGIC_PREDICATE).orElse(logicPredicate);
                }

                this.animationIdentifier = animIdent;
                this.usesAmmo = consumesAmmo;
                this.finalCoin = finalOfCombo;
                this.triggersEffects = triggersFX;
                this.logicPredicate = logicPredicate;
            }
        }

        public boolean isFinalCoin() {
            return finalCoin;
        }

        public String getAnimationIdentifier() {
            return animationIdentifier;
        }

        public boolean isAmmoSkill() {
            return usesAmmo;
        }

        public boolean triggersEffects() {
            return triggersEffects;
        }

        public boolean isValidEgoAnimation() {
            return validEgoAnimation;
        }

        public AttackLogicPredicate getLogicPredicate() {
            return logicPredicate;
        }

        public DynamicAnimation getAnimation() {
            return animation;
        }
    }

    // Truly GPD'd noise function i am not smart enough for random interpolation
    private static final Random RANDOM = new Random();

    private static float sample(int x) {
        RANDOM.setSeed(x * 49632L + 325176L);
        return RANDOM.nextFloat() * 2.0f - 1.0f;
    }

    public static float noise(float x) {
        int x0 = (int) Math.floor(x);
        int x1 = x0 + 1;

        float t = x - x0;

        // Smooth interpolation
        t = t * t * (3.0f - 2.0f * t);

        return sample(x0) * (1.0f - t) + sample(x1) * t;
    }
}

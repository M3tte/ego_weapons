package net.m3tte.ego_weapons.gameasset.abilities.weaponAbilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.gameasset.movesets.FirefistMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.HeishouMaoBranchAnims;
import net.m3tte.ego_weapons.gameasset.movesets.SolemnLamentMovesetAnims;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.LinkedList;
import java.util.List;

import static net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils.applyBlipCooldown;

public class SolemnLamentWeaponAbility extends ItemAbility {



    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        int extra = 0;

        return 5;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("fireishallfire");

    }

    @Override
    public ResourceLocation getOverlay(PlayerEntity player, PlayerVariables playerVars) {

        return super.getOverlay(player, playerVars);
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.WAW;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "FireIShallFire           ";
    }

    @Override
    public float getAvailability(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.light < getBlipCost(player, playerVars)) {
            return (float) (playerVars.light / getBlipCost(player, playerVars));
        }

        return 1.0f;
    }


    List<Integer> followupAnimIds = null;
    private void calcAnims() {
        followupAnimIds = new LinkedList<>();

        followupAnimIds.add(SolemnLamentMovesetAnims.SOLEMN_LAMENT_ARMOR_ABILITY_PARRY.getId());
        followupAnimIds.add(SolemnLamentMovesetAnims.SOLEMN_LAMENT_GUARD_COUNTERATTACK.getId());
        followupAnimIds.add(FirefistMovesetAnims.FIREFIST_COUNTER.getId());
        followupAnimIds.add(HeishouMaoBranchAnims.HEISHOU_MAO_PARRY_ATTACK.getId());
        followupAnimIds.add(HeishouMaoBranchAnims.HEISHOU_MAO_PARRY_3.getId());
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (playerVars.light >= getBlipCost(player, playerVars)) {


            playerVars.light -= getBlipCost(player, playerVars);
            World world = player.level;
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            int potency = 1;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), x, (y + 1), z, this.getBlipCost(player, playerVars), 0, 0.3, 0, 0.05);
            }



            if (followupAnimIds == null) {
                calcAnims();
            }

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            playerVars.globalcooldown = 100;

            player.getItemBySlot(EquipmentSlotType.MAINHAND).getOrCreateTag().remove("ffkilled");
            player.inventory.setChanged();
            entitypatch.playSound(EgoWeaponsSounds.SOLEMN_LAMENT_SPIN, 1, 1);
            player.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 40, 0));

            if (!player.level.isClientSide()) {
                DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

                if (followupAnimIds.contains(currentanim.getId())) {
                    entitypatch.playAnimationSynchronized(SolemnLamentMovesetAnims.SOLEMN_LAMENT_SPECIAL_A, 0.1f);
                } else {
                    entitypatch.playAnimationSynchronized(SolemnLamentMovesetAnims.SOLEMN_LAMENT_SPECIAL, 0.1f);
                }
            }


            /*if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }*/
            applyBlipCooldown(8, playerVars);
            playerVars.syncPlayerVariables(player);
        }


    }


}

package net.m3tte.ego_weapons.procedures.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.block.ChainsofjusticeBlock;
import net.m3tte.ego_weapons.procedures.abilities.ItemAbility;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.procedures.abilities.AbilityUtils.applyBlipCooldown;
import static net.m3tte.ego_weapons.procedures.abilities.AbilityUtils.findValidPlacement;

public class JustiziaArmorAbility extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, EgoWeaponsModVars.PlayerVariables playerVars) {
        return 6;
    }


    @Override
    public void trigger(PlayerEntity player, EgoWeaponsModVars.PlayerVariables playerVars) {

        if (playerVars.blips > 6) {
            playerVars.blips -= 6;
            World world = player.level;
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();

            List<Entity> foundHostiles = world.getEntities(player, new AxisAlignedBB(x - 8, y - 4, z - 8, x + 4, y + 4, z + 8), null)
                    .stream().sorted(new Object() {
                        Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                            return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.distanceToSqr(_x, _y, _z)));
                        }
                    }.compareDistOf(x, y, z)).collect(Collectors.toList());

            player.playSound(EgoWeaponsSounds.OMINOUS_BELL, 1, 1);
            player.playSound(EgoWeaponsSounds.OMINOUS_EFFECT,1,1);

            for (Entity targetEnt : foundHostiles) {
                if (targetEnt instanceof PlayerEntity) {
                    PlayerEntity targetLiving = (PlayerEntity) targetEnt;
                    EgoWeaponsModVars.PlayerVariables targetPlayerVars = targetLiving.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

                    if (targetPlayerVars.teamid == playerVars.teamid) {
                        continue;
                    }
                    attemptPlaceJustiziaChains(world, targetLiving, (int)playerVars.teamid);
                    targetLiving.playSound(SoundEvents.EVOKER_FANGS_ATTACK, 1,1);

                } else if (targetEnt instanceof LivingEntity) {

                    LivingEntity targetLiving = (LivingEntity) targetEnt;
                    attemptPlaceJustiziaChains(world, targetLiving, (int)playerVars.teamid);
                    targetLiving.playSound(SoundEvents.EVOKER_FANGS_ATTACK, 1,1);

                }
            }

            applyBlipCooldown(25, playerVars);

            playerVars.syncPlayerVariables(player);
        }
    }

    private static void attemptPlaceJustiziaChains(World world, Entity target, int teamId) {
        BlockPos toPlaceAt = findValidPlacement(world, target.blockPosition(), 0.6f);

        if (toPlaceAt != null) {
            world.setBlock(toPlaceAt,
                    ChainsofjusticeBlock.block.defaultBlockState(), 3);

            TileEntity chainsEntity = world.getBlockEntity(toPlaceAt);
            BlockState _bs = world.getBlockState(toPlaceAt);
            if (chainsEntity != null)
                chainsEntity.getTileData().putDouble("team", teamId);

            world.sendBlockUpdated(toPlaceAt, _bs, _bs, 3);
        }
    }
}

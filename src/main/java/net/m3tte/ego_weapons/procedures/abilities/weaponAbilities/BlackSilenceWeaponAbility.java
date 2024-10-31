package net.m3tte.ego_weapons.procedures.abilities.weaponAbilities;

import io.netty.buffer.Unpooled;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.execFunctions.SwapWeapon;
import net.m3tte.ego_weapons.gui.BlackSilenceSwapGUI;
import net.m3tte.ego_weapons.procedures.abilities.AbilityTier;
import net.m3tte.ego_weapons.procedures.abilities.AbilityUtils;
import net.m3tte.ego_weapons.procedures.abilities.ItemAbility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlackSilenceWeaponAbility extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 1;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("pocket_dimension");
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.ALPHA;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Pocket Dim.";
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (playerVars.blips >= 1 || player.getPersistentData().getDouble("furiosohits") > 10) {
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();

            if (player.isCrouching()) {

                if (player instanceof ServerPlayerEntity) {
                    BlockPos _bpos = new BlockPos(x, y, z);
                    NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                        @Override
                        public ITextComponent getDisplayName() {
                            return new StringTextComponent("Blacksilenceswapui");
                        }

                        @Override
                        public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
                            return new BlackSilenceSwapGUI.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
                        }
                    }, _bpos);
                }
            } else {
                // Cycles through the weapons passively
                SwapWeapon.SwapWeapon(player.level, player, (int)playerVars.blacksilence_ws + 1);
            }

            playerVars.syncPlayerVariables(player);
        }
    }


}

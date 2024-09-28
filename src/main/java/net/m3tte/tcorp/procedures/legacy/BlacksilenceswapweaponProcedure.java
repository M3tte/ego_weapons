package net.m3tte.tcorp.procedures.legacy;

import io.netty.buffer.Unpooled;
import net.m3tte.tcorp.TcorpMod;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.execFunctions.SwapWeapon;
import net.m3tte.tcorp.gui.BlackSilenceSwapGUI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Map;

public class BlacksilenceswapweaponProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure Blacksilenceswapweapon!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				TcorpMod.LOGGER.warn("Failed to load dependency x for procedure Blacksilenceswapweapon!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				TcorpMod.LOGGER.warn("Failed to load dependency y for procedure Blacksilenceswapweapon!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				TcorpMod.LOGGER.warn("Failed to load dependency z for procedure Blacksilenceswapweapon!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure Blacksilenceswapweapon!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		Entity target = null;
		if (entity.isCrouching()) {
			{
				Entity _ent = entity;
				if (_ent instanceof ServerPlayerEntity) {
					BlockPos _bpos = new BlockPos(x, y, z);
					NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
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
			}
		} else {
			// Cycles through the weapons passively
			entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				SwapWeapon.SwapWeapon(world, (LivingEntity) entity, (int)capability.blacksilence_ws + 1);
			});
		}
	}
}

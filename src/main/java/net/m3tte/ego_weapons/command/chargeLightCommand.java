
package net.m3tte.ego_weapons.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.procedures.EntityTick;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;

@Mod.EventBusSubscriber
public class chargeLightCommand {
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event) {
		event.getDispatcher().register(LiteralArgumentBuilder.<CommandSource>literal("chargeLight").requires(s -> s.hasPermission(4))
				.then(Commands.argument("target", EntityArgument.players()).then(Commands.argument("amount", IntegerArgumentType.integer()).then(Commands.argument("sound", BoolArgumentType.bool()).executes(arguments -> {

					System.out.println("Executing");
					Collection<ServerPlayerEntity> targets = EntityArgument.getPlayers(arguments, "target");
					int amount = IntegerArgumentType.getInteger(arguments, "amount");
					for (ServerPlayerEntity target : targets) {
						EntityTick.regenerateLight(target, amount, BoolArgumentType.getBool(arguments, "sound"));
					}


					return 0;
				})))));
	}
}

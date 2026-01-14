
package net.m3tte.ego_weapons.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.m3tte.ego_weapons.procedures.EntityTick;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;

@Mod.EventBusSubscriber
public class sanityCommand {
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event) {
		event.getDispatcher().register(LiteralArgumentBuilder.<CommandSource>literal("sanity").requires(s -> s.hasPermission(4))
				.then(Commands.argument("target", EntityArgument.players()).then(Commands.argument("amount", FloatArgumentType.floatArg()).executes(arguments -> {

					System.out.println("Executing");
					Collection<ServerPlayerEntity> targets = EntityArgument.getPlayers(arguments, "target");
					float amount = FloatArgumentType.getFloat(arguments, "amount");
					for (ServerPlayerEntity target : targets) {
						if (amount >= 0) SanitySystem.healSanity(target, amount); else SanitySystem.damageSanity(target, amount*-1);
					}


					return 0;
				}))));
	}
}

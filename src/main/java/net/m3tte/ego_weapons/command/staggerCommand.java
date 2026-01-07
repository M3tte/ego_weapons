
package net.m3tte.ego_weapons.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;

@Mod.EventBusSubscriber
public class staggerCommand {
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event) {
		event.getDispatcher().register(LiteralArgumentBuilder.<CommandSource>literal("stagger").requires(s -> s.hasPermission(4))
				.then(Commands.argument("target", EntityArgument.players()).then(Commands.argument("amount", IntegerArgumentType.integer()).then(Commands.argument("bypassArmor", BoolArgumentType.bool()).executes(arguments -> {

					System.out.println("Executing");
					Collection<ServerPlayerEntity> targets = EntityArgument.getPlayers(arguments, "target");
					int amount = IntegerArgumentType.getInteger(arguments, "amount");
					for (ServerPlayerEntity target : targets) {
						if (amount < 0) StaggerSystem.reduceStagger(target, amount * -1, BoolArgumentType.getBool(arguments, "bypassArmor")); else StaggerSystem.healStagger(target, amount);
					}


					return 0;
				})))));
	}
}

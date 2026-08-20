
package net.m3tte.ego_weapons.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.m3tte.ego_weapons.procedures.legacy.SetteamprocProcedure;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


public class SetteamCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher
				.register(LiteralArgumentBuilder.<CommandSource>literal("setteam").requires(s -> s.hasPermission(2))
						.then(Commands.argument("player", EntityArgument.entity())
								.then(Commands.argument("teamid", DoubleArgumentType.doubleArg(0, 6)).executes(arguments -> {
									ServerWorld world = arguments.getSource().getLevel();
									double x = arguments.getSource().getPosition().x();
									double y = arguments.getSource().getPosition().y();
									double z = arguments.getSource().getPosition().z();
									Entity entity = arguments.getSource().getEntity();
									if (entity == null)
										entity = FakePlayerFactory.getMinecraft(world);
									Direction direction = entity.getDirection();

									SetteamprocProcedure.executeProcedure(Stream.of(new AbstractMap.SimpleEntry<>("arguments", arguments))
											.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
									return 0;
								}))));
	}
}

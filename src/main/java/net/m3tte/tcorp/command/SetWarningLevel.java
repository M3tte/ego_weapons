
package net.m3tte.tcorp.command;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.TcorpModVariables.MapVariables;
import net.m3tte.tcorp.procedures.legacy.SetattrprocProcedure;
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

@Mod.EventBusSubscriber
public class SetWarningLevel {
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event) {
		event.getDispatcher()
				.register(LiteralArgumentBuilder.<CommandSource>literal("setWarning").requires(s -> s.hasPermission(2))
						.then(Commands.argument("tier", IntegerArgumentType.integer()).executes(args -> {
							float tier = IntegerArgumentType.getInteger(args, "tier");

							MapVariables mapVars = MapVariables.get(args.getSource().getLevel());
							mapVars.globalWarningTension = tier + 0.6f;
							mapVars.syncData(args.getSource().getLevel());
							return 0;
						})));
	}
}


package net.m3tte.ego_weapons.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.m3tte.ego_weapons.EgoWeaponsModVars.MapVariables;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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

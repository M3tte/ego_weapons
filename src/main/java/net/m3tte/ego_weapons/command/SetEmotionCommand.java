
package net.m3tte.ego_weapons.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SetEmotionCommand {
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event) {
		event.getDispatcher().register(LiteralArgumentBuilder.<CommandSource>literal("setEmotionLevel").requires(s -> s.hasPermission(4))
				.then(Commands.argument("name", EntityArgument.players())
						.then(Commands.argument("level", IntegerArgumentType.integer(0, 5)).executes(arguments -> {
					ServerWorld world = arguments.getSource().getLevel();
					double x = arguments.getSource().getPosition().x();
					double y = arguments.getSource().getPosition().y();
					double z = arguments.getSource().getPosition().z();
					Entity entity = arguments.getSource().getEntity();
					if (entity == null)
						entity = FakePlayerFactory.getMinecraft(world);

					int lvl = IntegerArgumentType.getInteger(arguments, "level");

					for (PlayerEntity player : EntityArgument.getPlayers(arguments, "name")) {
						PlayerVariables vars = player.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY).orElse(new PlayerVariables());

						vars.globalcooldown = 0;
						vars.emotionLevel = lvl;
						vars.emotionLevelProgress = lvl;
						EmotionSystem.updateMaxEnergy(player, vars);
						vars.syncEmotionLevel(player);
					}




					return 0;
				}))));
	}
}

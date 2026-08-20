
package net.m3tte.ego_weapons.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;


public class SetEmotionCommand {

	/**
	 * <h4>/setEmotionLevel [target] [level 0 - 5]</h4>
	 * @param dispatcher
	 */
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal("setEmotionLevel").requires(s -> s.hasPermission(2))
				.then(Commands.argument("name", EntityArgument.players())
						.then(Commands.argument("level", IntegerArgumentType.integer(0, 5)).executes(arguments -> {

					int lvl = IntegerArgumentType.getInteger(arguments, "level");

					Collection<ServerPlayerEntity> players = EntityArgument.getPlayers(arguments, "name");

					for (PlayerEntity player : players) {
						PlayerVariables vars = player.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY).orElse(new PlayerVariables());

						vars.globalcooldown = 0;
						vars.emotionLevel = lvl;
						vars.emotionLevelProgress = lvl;
						EmotionSystem.updateMaxEnergy(player, vars);
						vars.syncEmotionLevel(player);
					}

					arguments.getSource().sendSuccess(new TranslationTextComponent("commands.ego_weapons.success.chargeLight.1", lvl,players.size(), players.size()==1?"":"s"), true);



					return 1;
				}))));
	}
}

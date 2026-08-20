
package net.m3tte.ego_weapons.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.procedures.EntityTick;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;


public class LightCommands {

	/**
	 * <h4>/fillLight [targets]</h4>
	 * <h4>/chargeLight [targets] [amount] [sound?]</h4>
	 * @param dispatcher
	 */
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal("fillLight").requires(s -> s.hasPermission(2))
				.then(Commands.argument("target", EntityArgument.players()).executes(arguments -> {

					Collection<ServerPlayerEntity> targets = EntityArgument.getPlayers(arguments, "target");


					for (ServerPlayerEntity target : targets) {
						PlayerVariables vars = target.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY).orElse(new PlayerVariables());
						vars.globalcooldown = 0;
						vars.light = EgoWeaponsAttributes.getMaxLight(target);
						vars.syncPlayerVariables(target);
					}

					arguments.getSource().sendSuccess(new TranslationTextComponent("commands.ego_weapons.success.fillLight.1", targets.size(), targets.size()==1?"":"s"), true);

					return 1;
				})));

		dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal("chargeLight").requires(s -> s.hasPermission(2))
				.then(Commands.argument("target", EntityArgument.players()).then(Commands.argument("amount", IntegerArgumentType.integer()).then(Commands.argument("sound", BoolArgumentType.bool()).executes(arguments -> {

					Collection<ServerPlayerEntity> targets = EntityArgument.getPlayers(arguments, "target");
					int amount = IntegerArgumentType.getInteger(arguments, "amount");
					for (ServerPlayerEntity target : targets) {
						EntityTick.regenerateLight(target, amount, BoolArgumentType.getBool(arguments, "sound"));
					}

					arguments.getSource().sendSuccess(new TranslationTextComponent("commands.ego_weapons.success.chargeLight.1", amount,targets.size(), targets.size()==1?"":"s"), true);



					return 1;
				})))));
	}
}

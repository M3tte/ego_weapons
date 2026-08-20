
package net.m3tte.ego_weapons.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;


public class SanityCommands {

	/**
	 * <h4>/sanity [target] [amount]</h4>
	 * @param dispatcher
	 */
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal("sanity").requires(s -> s.hasPermission(4))
				.then(Commands.argument("target", EntityArgument.players()).then(Commands.argument("amount", FloatArgumentType.floatArg()).executes(arguments -> {

					Collection<ServerPlayerEntity> targets = EntityArgument.getPlayers(arguments, "target");
					float amount = FloatArgumentType.getFloat(arguments, "amount");
					for (ServerPlayerEntity target : targets) {
						if (amount >= 0) SanitySystem.healSanity(target, amount); else SanitySystem.damageSanity(target, amount*-1);
					}
					arguments.getSource().sendSuccess(new TranslationTextComponent("commands.ego_weapons.success.sanity.1", amount, targets.size(), targets.size()==1?"":"s"), true);

					return 1;
				}))));
	}
}

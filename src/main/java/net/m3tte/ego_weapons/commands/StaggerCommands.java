
package net.m3tte.ego_weapons.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;


public class StaggerCommands {

	/**
	 * <h4>/stagger [target] [amount] [bypassArmor]</h4>
	 * @param dispatcher
	 */
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal("stagger").requires(s -> s.hasPermission(2))
				.then(Commands.argument("target", EntityArgument.entities()).then(Commands.argument("amount", FloatArgumentType.floatArg()).then(Commands.argument("bypassArmor", BoolArgumentType.bool()).executes(arguments -> {

					System.out.println("Executing");
					Collection<? extends Entity> targets = EntityArgument.getEntities(arguments, "target");
					float amount = FloatArgumentType.getFloat(arguments, "amount");
					int affected = 0;
					for (Entity target : targets) {
						if (target instanceof LivingEntity) {
							affected++;
							if (amount < 0) StaggerSystem.reduceStagger((LivingEntity) target, amount * -1, BoolArgumentType.getBool(arguments, "bypassArmor")); else StaggerSystem.healStagger((LivingEntity) target, amount);
						}

					}

					if (affected > 0)
						arguments.getSource().sendSuccess(new TranslationTextComponent("commands.ego_weapons.success.stagger.1", amount, affected, affected==1?"y":"ies"), true);
					else
						arguments.getSource().sendSuccess(new TranslationTextComponent("commands.ego_weapons.errors.no_targets", targets.size()), true);

					return 1;
				})))));
	}
}

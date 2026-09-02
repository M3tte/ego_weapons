
package net.m3tte.ego_weapons.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.potion.countEffects.CountPotencyStatus;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.stream.Collectors;

public class StatusEffectCommands {

	private static final SuggestionProvider<CommandSource> SUGGEST_EGO_EFFECT = (p_198206_0_, p_198206_1_) -> {
		Collection<ResourceLocation> collection = EgoWeaponsEffects.EFFECTS.getEntries().stream().filter((e) -> e.get() instanceof CountPotencyStatus).map((e) -> e.getId()).collect(Collectors.toList());
		return ISuggestionProvider.suggestResource(collection, p_198206_1_);
	};


	/**
	 * /incrementStatus
	 * target [Entities] | Who to target
	 * potency [int] | How much to increase the status potency for
	 * count [int] | How much to increase the status count for. For statuses without count this is the max potency.
	 * status_effect | The status effect to use.
	 * <p>
	 * /decrementStatus
	 * target [Entities] | Who to target
	 * potency [int] | How much to decrease the status potency for
	 * count [int] | How much to decrease the status count for.
	 * status_effect | The status effect to use.
	 */
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal("incrementStatus").requires(s -> s.hasPermission(2))
				.then(Commands.argument("target", EntityArgument.entities())
						.then(Commands.argument("potency", IntegerArgumentType.integer())
						.then(Commands.argument("count", IntegerArgumentType.integer())
						.then(Commands.argument("status_effect", ResourceLocationArgument.id()).suggests(SUGGEST_EGO_EFFECT)
						.executes(arguments -> {
							CommandSource src = arguments.getSource();
							System.out.println("Executing");
							Collection<? extends Entity> targets = EntityArgument.getEntities(arguments, "target");
							int potency = IntegerArgumentType.getInteger(arguments, "potency");
							int count = IntegerArgumentType.getInteger(arguments, "count");

							ResourceLocation status = ResourceLocationArgument.getId(arguments, "status_effect");

							if (potency < 0 || count < 0) {
								src.sendFailure(new TranslationTextComponent("commands.ego_weapons.errors.should_be_positive.incr"));
								return 0;
							}
							int affected = 0;
							try {
								Effect effect = ForgeRegistries.POTIONS.getValue(status);

								if (!(effect instanceof CountPotencyStatus)) {
									src.sendFailure(new TranslationTextComponent("commands.ego_weapons.errors.no_effect"));
									return 0;
								}


								for (Entity target : targets) {
									if (target instanceof LivingEntity) {
										affected++;
										((CountPotencyStatus) effect).increment((LivingEntity) target, count, potency);
									}
								}

							} catch (Exception e) {
								src.sendFailure(new TranslationTextComponent("commands.ego_weapons.errors.no_effect"));
							}

							if (affected > 0)
								arguments.getSource().sendSuccess(new TranslationTextComponent("commands.ego_weapons.success.incrementStatus.1", status.getPath(), affected, affected==1?"y":"ies"), true);
							else
								arguments.getSource().sendSuccess(new TranslationTextComponent("commands.ego_weapons.errors.no_targets", targets.size()), true);

					return 1;
				}))))));

		dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal("decrementStatus").requires(s -> s.hasPermission(2))
				.then(Commands.argument("target", EntityArgument.players())
						.then(Commands.argument("potency", IntegerArgumentType.integer())
						.then(Commands.argument("count", IntegerArgumentType.integer())
								.then(Commands.argument("status_effect", ResourceLocationArgument.id()).suggests(SUGGEST_EGO_EFFECT)
						.executes(arguments -> {
							CommandSource src = arguments.getSource();
							System.out.println("Executing");
							Collection<ServerPlayerEntity> targets = EntityArgument.getPlayers(arguments, "target");
							int potency = IntegerArgumentType.getInteger(arguments, "potency");
							int count = IntegerArgumentType.getInteger(arguments, "count");

							if (potency < 0 || count < 0) {
								src.sendFailure(new TranslationTextComponent("commands.ego_weapons.errors.should_be_positive.decr"));
								return 0;
							}

							ResourceLocation status = ResourceLocationArgument.getId(arguments, "status_effect");
							int affected = 0;
							try {
								Effect effect = ForgeRegistries.POTIONS.getValue(status);

								if (!(effect instanceof CountPotencyStatus)) {
									src.sendFailure(new TranslationTextComponent("commands.ego_weapons.errors.no_effect"));
									return 0;
								}

								for (LivingEntity target : targets) {
									((CountPotencyStatus) effect).decrement(target, count, potency);
									affected++;
								}

							} catch (Exception e) {
								src.sendFailure(new TranslationTextComponent("commands.ego_weapons.errors.no_effect"));
								return 0;
							}

							if (affected > 0)
								arguments.getSource().sendSuccess(new TranslationTextComponent("commands.ego_weapons.success.decrementStatus.1", status.getPath(),affected, affected==1?"y":"ies"), true);
							else
								arguments.getSource().sendSuccess(new TranslationTextComponent("commands.ego_weapons.errors.no_targets", targets.size()), true);

							return 1;
						}))))));
	}
}

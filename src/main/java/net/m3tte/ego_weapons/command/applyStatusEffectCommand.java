
package net.m3tte.ego_weapons.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.potion.countEffects.CountPotencyStatus;
import net.m3tte.ego_weapons.procedures.EntityTick;
import net.minecraft.advancements.Advancement;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber
public class applyStatusEffectCommand {

	private static final SuggestionProvider<CommandSource> SUGGEST_EGO_EFFECT = (p_198206_0_, p_198206_1_) -> {
		Collection<ResourceLocation> collection = EgoWeaponsEffects.EFFECTS.getEntries().stream().filter((e) -> e.get() instanceof CountPotencyStatus).map((e) -> e.getId()).collect(Collectors.toList());
		return ISuggestionProvider.suggestResource(collection, p_198206_1_);
	};

	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event) {
		event.getDispatcher().register(LiteralArgumentBuilder.<CommandSource>literal("incrementStatus").requires(s -> s.hasPermission(4))
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

							ResourceLocation status = ResourceLocationArgument.getId(arguments, "status_effect");

							if (potency < 0 || count < 0) {
								src.sendFailure(new TranslationTextComponent("commands.ego_weapons.errors.should_be_positive.incr"));
								return 0;
							}

							try {
								Effect effect = ForgeRegistries.POTIONS.getValue(status);

								if (!(effect instanceof CountPotencyStatus)) {
									src.sendFailure(new TranslationTextComponent("commands.ego_weapons.errors.no_effect"));
									return 0;
								}


								for (ServerPlayerEntity target : targets) {
									((CountPotencyStatus) effect).increment(target, count, potency);
								}

							} catch (Exception e) {
								src.sendFailure(new TranslationTextComponent("commands.ego_weapons.errors.no_effect"));
							}


					return 0;
				}))))));

		event.getDispatcher().register(LiteralArgumentBuilder.<CommandSource>literal("decrementStatus").requires(s -> s.hasPermission(4))
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

							try {
								Effect effect = ForgeRegistries.POTIONS.getValue(status);

								if (!(effect instanceof CountPotencyStatus)) {
									src.sendFailure(new TranslationTextComponent("commands.ego_weapons.errors.no_effect"));
									return 0;
								}

								for (ServerPlayerEntity target : targets) {
									((CountPotencyStatus) effect).decrement(target, count, potency);
								}

							} catch (Exception e) {
								src.sendFailure(new TranslationTextComponent("commands.ego_weapons.errors.no_effect"));
							}


							return 0;
						}))))));
	}
}


package net.m3tte.ego_weapons.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.potion.countEffects.CountPotencyStatus;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.damage.SimpleEgoDamageSource;
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
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class UtilityCommands {

	private static final SuggestionProvider<CommandSource> SUGGEST_DAMAGE_TYPE = (p_198206_0_, p_198206_1_) -> {
		Collection<String> collection = Arrays.stream(GenericEgoDamage.DamageTypes.values()).map(Enum::toString).collect(Collectors.toList());
		return ISuggestionProvider.suggest(collection, p_198206_1_);
	};

	private static final SuggestionProvider<CommandSource> SUGGEST_ATTACK_TYPE = (p_198206_0_, p_198206_1_) -> {
		Collection<String> collection = Arrays.stream(GenericEgoDamage.AttackTypes.values()).map(Enum::toString).collect(Collectors.toList());
		return ISuggestionProvider.suggest(collection, p_198206_1_);
	};


	/**
	 * /EGODamage
	 * target [Entities] | Who to target
	 * damage [float] | How much damage to deal
	 * Attack Type [AttackType] | What attack type to use
	 * Damage Type [DamageType] | What Damage type to use
	 * ~ source [Entity] {Optional} | Who dealt the damage. Defaults to "null"
	 * ~ Attack Identifier [String] {Optional} | What text to use on kill. Defaults to ""
	 */

	public static int resolveDamageCommand(CommandContext<CommandSource> arguments, Collection<? extends Entity> targets, float damage, String attackTypeIdent, String damageTypeIdent, Entity sourceEntity, String attackIdent) {
		CommandSource src = arguments.getSource();

		GenericEgoDamage.AttackTypes resolvedAttackType = null;
		GenericEgoDamage.DamageTypes resolvedDamageType = null;


		try {
			resolvedAttackType = GenericEgoDamage.AttackTypes.valueOf(attackTypeIdent);
		} catch (Exception e) {
			src.sendFailure(new TranslationTextComponent("commands.ego_weapons.errors.invalid_parameter.attack_type"));
			return 0;
		}

		try {
			resolvedDamageType = GenericEgoDamage.DamageTypes.valueOf(damageTypeIdent);
		} catch (Exception e) {
			src.sendFailure(new TranslationTextComponent("commands.ego_weapons.errors.invalid_parameter.damage_type"));
			return 0;
		}

		int affected = 0;

		for (Entity target : targets) {
			if (target instanceof LivingEntity) {
				if (target.hurt(new SimpleEgoDamageSource("", sourceEntity, resolvedAttackType, resolvedDamageType, attackIdent), damage)) {
					affected++;
				}

			}
		}

		if (affected > 0)
			arguments.getSource().sendSuccess(new TranslationTextComponent("commands.ego_weapons.success.egodamage.1", damage, affected, targets.size()==1?"y":"ies", resolvedAttackType.getIcon(),resolvedDamageType.getIcon(), attackIdent.isEmpty() ? "" : " - \""+attackIdent+"\""), true);
		else
			arguments.getSource().sendSuccess(new TranslationTextComponent("commands.ego_weapons.errors.no_targets", targets.size()), true);

		return 1;
	}
	public static void register(CommandDispatcher<CommandSource> dispatcher) {

		dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal("egodamage").requires(s -> s.hasPermission(2))
				.then(Commands.argument("target", EntityArgument.entities())
						.then(Commands.argument("damage", FloatArgumentType.floatArg(0))
						.then(Commands.argument("attackType", StringArgumentType.string()).suggests(SUGGEST_ATTACK_TYPE)
						.then(Commands.argument("damageType", StringArgumentType.string()).suggests(SUGGEST_DAMAGE_TYPE)
						.executes(arguments -> {

							Collection<? extends Entity> targets = EntityArgument.getEntities(arguments, "target");

							float damage = FloatArgumentType.getFloat(arguments, "damage");
							String attackTypeIdent = StringArgumentType.getString(arguments, "attackType");
							String damageTypeIdent = StringArgumentType.getString(arguments, "damageType");

							return resolveDamageCommand(arguments, targets, damage, attackTypeIdent, damageTypeIdent, null, "");
						}).then(Commands.argument("source", EntityArgument.entity())
						.executes(arguments -> {
							Collection<? extends Entity> targets = EntityArgument.getEntities(arguments, "target");
							Entity source = EntityArgument.getEntity(arguments, "source");
							float damage = FloatArgumentType.getFloat(arguments, "damage");
							String attackTypeIdent = StringArgumentType.getString(arguments, "attackType");
							String damageTypeIdent = StringArgumentType.getString(arguments, "damageType");

							return resolveDamageCommand(arguments, targets, damage, attackTypeIdent, damageTypeIdent, source, "");
						})
						.then(Commands.argument("attackIdent", StringArgumentType.string())
						.executes(arguments -> {
							Collection<? extends Entity> targets = EntityArgument.getEntities(arguments, "target");
							Entity source = EntityArgument.getEntity(arguments, "source");
							float damage = FloatArgumentType.getFloat(arguments, "damage");
							String attackTypeIdent = StringArgumentType.getString(arguments, "attackType");
							String damageTypeIdent = StringArgumentType.getString(arguments, "damageType");
							String attackIdent = StringArgumentType.getString(arguments, "attackIdent");

							return resolveDamageCommand(arguments, targets, damage, attackTypeIdent, damageTypeIdent, source, attackIdent);
						}))))))));
	}
}

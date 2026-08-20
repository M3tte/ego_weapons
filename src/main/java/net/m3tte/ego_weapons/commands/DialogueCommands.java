
package net.m3tte.ego_weapons.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.network.packages.CapabilityPackages;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Collection;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;


public class DialogueCommands {
	/**
	 * <h4>/dialogue [targets] [formattingPreset] [text...]</h4>
	 * <h4>/personalityDialogue [targets] [formattingPreset] [message]</h4>
	 * @param dispatcher
	 */
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal("dialogue").requires(s -> s.hasPermission(2)).then(Commands
				.argument("targets", EntityArgument.entities()).then(Commands.argument("formattingPreset", StringArgumentType.string()).then(Commands.argument("message", StringArgumentType.greedyString()).executes(arguments -> {
					ServerWorld world = arguments.getSource().getLevel();

					System.out.println("Sent dialogue request");

					CommandSource src = arguments.getSource();
					double x = arguments.getSource().getPosition().x();
					double y = arguments.getSource().getPosition().y();
					double z = arguments.getSource().getPosition().z();

					Collection<? extends Entity> targets = EntityArgument.getEntities(arguments, "targets");

					TextFormatting formatting = TextFormatting.WHITE;

					try {
						formatting = TextFormatting.getByName(StringArgumentType.getString(arguments, "formattingPreset"));

						if (formatting == null)
							src.sendFailure(new TranslationTextComponent("commands.ego_weapons.errors.formatting").append(" \""+ TextFormatting.getNames(true, true) + "\""));

					} catch (Exception e) {
						src.sendFailure(new TranslationTextComponent("commands.ego_weapons.errors.formatting").append(" \""+ TextFormatting.getNames(true, true) + "\""));
					}




					for (Entity target : targets) {
						EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(),
								new CapabilityPackages.ApplyDialogueData(target.getId(), StringArgumentType.getString(arguments, "message"), formatting.ordinal(), false, -1, DialogueSystem.DialogueTypes.FORCE));


					}


					return 0;
				})))));

		dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal("personalityDialogue").requires(s -> s.hasPermission(2)).then(Commands
				.argument("targets", EntityArgument.entities()).then(Commands.argument("formattingPreset", StringArgumentType.string()).then(Commands.argument("message", StringArgumentType.greedyString()).executes(arguments -> {
					ServerWorld world = arguments.getSource().getLevel();

					System.out.println("Sent dialogue request");

					CommandSource src = arguments.getSource();
					double x = arguments.getSource().getPosition().x();
					double y = arguments.getSource().getPosition().y();
					double z = arguments.getSource().getPosition().z();

					Collection<? extends Entity> targets = EntityArgument.getEntities(arguments, "targets");

					TextFormatting formatting = TextFormatting.WHITE;

					try {
						formatting = TextFormatting.getByName(StringArgumentType.getString(arguments, "formattingPreset"));

						if (formatting == null)
							src.sendFailure(new TranslationTextComponent("ego_weapons.errors.formatting").append(" \""+ TextFormatting.getNames(true, true) + "\""));

					} catch (Exception e) {
						src.sendFailure(new TranslationTextComponent("ego_weapons.errors.formatting").append(" \""+ TextFormatting.getNames(true, true) + "\""));
					}

					for (Entity target : targets) {

						String personality = "default";

						if (target.getPersistentData().contains("personality")) {
							personality = target.getPersistentData().getString("personality");
						}
						else if (target instanceof PlayerEntity) {
							EgoWeaponsModVars.PlayerVariables entityData = target.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);
							personality = entityData.personality;


						}

						if (personality.isEmpty()) {
							personality = "default";
						}

						int dialogueLevel = target.level.getRandom().nextInt(5);



						EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(),
								new CapabilityPackages.ApplyDialogueData(target.getId(), StringArgumentType.getString(arguments, "message")+"."+personality, formatting.ordinal(), true, dialogueLevel, DialogueSystem.DialogueTypes.FORCE));


					}


					return 0;
				})))));
	}
}

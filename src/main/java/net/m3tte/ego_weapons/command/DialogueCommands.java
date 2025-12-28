
package net.m3tte.ego_weapons.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.network.packages.AbilityPackages;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.xml.soap.Text;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

@Mod.EventBusSubscriber
public class DialogueCommands {
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event) {
		event.getDispatcher().register(LiteralArgumentBuilder.<CommandSource>literal("dialogue").requires(s -> s.hasPermission(2)).then(Commands
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
								new AbilityPackages.ApplyDialogueData(target.getId(), StringArgumentType.getString(arguments, "message"), formatting.ordinal(), false, -1, DialogueSystem.DialogueTypes.FORCE));


					}


					return 0;
				})))));

		event.getDispatcher().register(LiteralArgumentBuilder.<CommandSource>literal("personalityDialogue").requires(s -> s.hasPermission(2)).then(Commands
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
								new AbilityPackages.ApplyDialogueData(target.getId(), StringArgumentType.getString(arguments, "message")+"."+personality, formatting.ordinal(), true, dialogueLevel, DialogueSystem.DialogueTypes.FORCE));


					}


					return 0;
				})))));
	}
}

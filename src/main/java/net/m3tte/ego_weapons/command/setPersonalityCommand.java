
package net.m3tte.ego_weapons.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.network.packages.AbilityPackages;
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
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Collection;

@Mod.EventBusSubscriber
public class setPersonalityCommand {
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event) {
		event.getDispatcher().register(LiteralArgumentBuilder.<CommandSource>literal("setPersonality").requires(s -> s.hasPermission(4))
				.then(Commands.argument("targets", EntityArgument.entities())
						.then(Commands.argument("personality", StringArgumentType.string()).executes(arguments -> {
					ServerWorld world = arguments.getSource().getLevel();
					double x = arguments.getSource().getPosition().x();
					double y = arguments.getSource().getPosition().y();
					double z = arguments.getSource().getPosition().z();

					Collection<? extends Entity> targets = EntityArgument.getEntities(arguments, "targets");

					String personality = StringArgumentType.getString(arguments, "personality");

					for (Entity ent : targets) {
						if (ent instanceof PlayerEntity) {
							PlayerVariables vars = ent.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY).orElse(new PlayerVariables());

							vars.personality = personality;
							vars.syncPlayerVariables(ent);
						} else {
							EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(),
									new AbilityPackages.ChangePersonality(ent.getId(), personality));

						}
					}




					return 0;
				}))));
	}
}

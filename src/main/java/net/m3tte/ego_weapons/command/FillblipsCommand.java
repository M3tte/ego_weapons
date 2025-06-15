
package net.m3tte.ego_weapons.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class FillblipsCommand {
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event) {
		event.getDispatcher().register(LiteralArgumentBuilder.<CommandSource>literal("fillblips").requires(s -> s.hasPermission(4))
				.then(Commands.argument("name", EntityArgument.player()).executes(arguments -> {

					System.out.println("Executing");
					PlayerEntity target = EntityArgument.getPlayer(arguments, "name");
					PlayerVariables vars = target.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY).orElse(new PlayerVariables());
					vars.globalcooldown = 0;
					vars.light = EgoWeaponsAttributes.getMaxLight(target);
					vars.syncPlayerVariables(target);
					return 0;
				})));
	}
}

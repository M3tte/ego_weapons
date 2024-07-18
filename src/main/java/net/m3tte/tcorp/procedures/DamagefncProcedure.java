package net.m3tte.tcorp.procedures;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.m3tte.tcorp.TcorpMod;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

import java.util.Map;

public class DamagefncProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("arguments") == null) {
			if (!dependencies.containsKey("arguments"))
				TcorpMod.LOGGER.warn("Failed to load dependency arguments for procedure Damagefnc!");
			return;
		}
		CommandContext<CommandSource> arguments = (CommandContext<CommandSource>) dependencies.get("arguments");
		try {
			for (Entity entityiterator : EntityArgument.getEntities(arguments, "plr")) {
				entityiterator.hurt(DamageSource.GENERIC, (float) (DoubleArgumentType.getDouble(arguments, "dmg")));
			}
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
	}
}

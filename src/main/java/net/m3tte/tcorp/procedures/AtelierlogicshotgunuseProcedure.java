package net.m3tte.tcorp.procedures;

import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.execFunctions.AtelierCooldownHandler;
import net.m3tte.tcorp.gameasset.TCorpAnimations;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.IWorld;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Map;

public class AtelierlogicshotgunuseProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {

		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		if ((3 <= (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new TcorpModVariables.PlayerVariables())).blips || AtelierCooldownHandler.hasFuriosoBonus((LivingEntity) entity))
				&& 0 >= (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).globalcooldown && ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new TcorpModVariables.PlayerVariables())).firingMode)) {
			{

				if (entity instanceof PlayerEntity) {
					LivingEntity player = (LivingEntity) entity;

					LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);


					entitypatch.playAnimationSynchronized(TCorpAnimations.ATELIER_SHOTGUN_FIRE, 0);


				}
			}


		}
	}
}

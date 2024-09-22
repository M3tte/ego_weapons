package net.m3tte.tcorp.procedures.legacy;

import net.m3tte.tcorp.TCorpItems;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.execFunctions.AtelierCooldownHandler;
import net.m3tte.tcorp.gameasset.TCorpAnimations;
import net.m3tte.tcorp.item.blackSilence.weapons.AtelierlogicpistolsItem;
import net.m3tte.tcorp.world.capabilities.item.TCorpStyles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.IWorld;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Map;

public class AtelierLogicPistolUseProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {

		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		if ((2 <= (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new TcorpModVariables.PlayerVariables())).blips || AtelierCooldownHandler.hasFuriosoBonus((LivingEntity) entity))
				&& 0 >= (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).globalcooldown && ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new TcorpModVariables.PlayerVariables())).firingMode)) {
			{

				if (entity instanceof PlayerEntity) {
					LivingEntity player = (LivingEntity) entity;

					LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);


					if (entitypatch.getHoldingItemCapability(Hand.MAIN_HAND).getStyle(entitypatch) == TCorpStyles.FURIOSO)
						switch ((int) (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).gunMagSize) {
							case 0:
								entitypatch.playAnimationSynchronized(TCorpAnimations.ATELIER_REVOLVER_FURIOSO_1, 0);
								break;
							case 1:
								if (entitypatch.getOriginal().getItemInHand(Hand.OFF_HAND).getItem().equals(TCorpItems.ATELIER_LOGIC_PISTOLS.get()))
									entitypatch.playAnimationSynchronized(TCorpAnimations.ATELIER_REVOLVER_FURIOSO_2, 0);
								else
									entitypatch.playAnimationSynchronized(TCorpAnimations.ATELIER_REVOLVER_FURIOSO_1, 0);
						}
					else
						switch ((int) (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).gunMagSize) {
							case 0:
								entitypatch.playAnimationSynchronized(TCorpAnimations.ATELIER_REVOLVER_FIRE_1, 0);
								break;
							case 1:
								if (entitypatch.getOriginal().getItemInHand(Hand.OFF_HAND).getItem().equals(TCorpItems.ATELIER_LOGIC_PISTOLS.get()))
									entitypatch.playAnimationSynchronized(TCorpAnimations.ATELIER_REVOLVER_FIRE_2, 0);
								else
									entitypatch.playAnimationSynchronized(TCorpAnimations.ATELIER_REVOLVER_FIRE_1, 0);
								break;
						}

				}
			}


		}
	}
}

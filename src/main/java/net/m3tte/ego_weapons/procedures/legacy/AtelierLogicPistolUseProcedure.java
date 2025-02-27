package net.m3tte.ego_weapons.procedures.legacy;

import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.execFunctions.AtelierCooldownHandler;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.movesets.AtelierLogicMovesetAnims;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsStyles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.IWorld;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Map;

public class AtelierLogicPistolUseProcedure {

	@Deprecated
	public static void executeProcedure(Map<String, Object> dependencies) {

		/*IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		if ((2 <= (entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EgoWeaponsModVars.PlayerVariables())).blips || AtelierCooldownHandler.hasFuriosoBonus((LivingEntity) entity))
				&& 0 >= (entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new EgoWeaponsModVars.PlayerVariables())).globalcooldown && ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new EgoWeaponsModVars.PlayerVariables())).firingMode)) {
			{

				if (entity instanceof PlayerEntity) {
					LivingEntity player = (LivingEntity) entity;

					LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);


					if (entitypatch.getHoldingItemCapability(Hand.MAIN_HAND).getStyle(entitypatch) == EgoWeaponsStyles.FURIOSO)
						switch ((int) (entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).gunMagSize) {
							case 0:
								entitypatch.playAnimationSynchronized(AtelierLogicMovesetAnims.ATELIER_REVOLVER_FURIOSO_1, 0);
								break;
							case 1:
								if (entitypatch.getOriginal().getItemInHand(Hand.OFF_HAND).getItem().equals(EgoWeaponsItems.ATELIER_LOGIC_PISTOLS.get()))
									entitypatch.playAnimationSynchronized(AtelierLogicMovesetAnims.ATELIER_REVOLVER_FURIOSO_2, 0);
								else
									entitypatch.playAnimationSynchronized(AtelierLogicMovesetAnims.ATELIER_REVOLVER_FURIOSO_1, 0);
						}
					else
						switch ((int) (entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).gunMagSize) {
							case 0:
								entitypatch.playAnimationSynchronized(AtelierLogicMovesetAnims.ATELIER_REVOLVER_FIRE_1, 0);
								break;
							case 1:
								if (entitypatch.getOriginal().getItemInHand(Hand.OFF_HAND).getItem().equals(EgoWeaponsItems.ATELIER_LOGIC_PISTOLS.get()))
									entitypatch.playAnimationSynchronized(AtelierLogicMovesetAnims.ATELIER_REVOLVER_FIRE_2, 0);
								else
									entitypatch.playAnimationSynchronized(AtelierLogicMovesetAnims.ATELIER_REVOLVER_FIRE_1, 0);
								break;
						}

				}
			}


		}*/
	}
}

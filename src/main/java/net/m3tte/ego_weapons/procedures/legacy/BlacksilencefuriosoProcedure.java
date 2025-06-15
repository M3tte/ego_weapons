//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.procedures.legacy;

import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.particle.BlacksilenceshadowParticle;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.potion.FuriosoPotionEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.Map;

public class BlacksilencefuriosoProcedure {
	public BlacksilencefuriosoProcedure() {
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world")) {
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency world for procedure Blacksilencefurioso!");
			}

		} else if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x")) {
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency x for procedure Blacksilencefurioso!");
			}

		} else if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y")) {
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency y for procedure Blacksilencefurioso!");
			}

		} else if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z")) {
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency z for procedure Blacksilencefurioso!");
			}

		} else if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity")) {
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency entity for procedure Blacksilencefurioso!");
			}

		} else {
			IWorld world = (IWorld)dependencies.get("world");
			double x = dependencies.get("x") instanceof Integer ? (double)(Integer)dependencies.get("x") : (Double)dependencies.get("x");
			double y = dependencies.get("y") instanceof Integer ? (double)(Integer)dependencies.get("y") : (Double)dependencies.get("y");
			double z = dependencies.get("z") instanceof Integer ? (double)(Integer)dependencies.get("z") : (Double)dependencies.get("z");
			Entity entity = (Entity)dependencies.get("entity");
			Entity target = null;
			entity.getPersistentData().putDouble("furiosohits", 0);
			entity.getPersistentData().putDouble("furiosoattacks", 0);
			double _setval = entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EgoWeaponsModVars.PlayerVariables()).light - 5.0;
			entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent((capability) -> {
				capability.light = _setval;
				capability.syncPlayerVariables(entity);
			});
			if (!world.isClientSide()) {
				if (world instanceof ServerWorld) {
					((ServerWorld)world).sendParticles(BlipeffectParticle.particle, x, y + 1.0, z, 8, 0.4, 0.6, 0.4, 0.0);
				}

				if (world instanceof ServerWorld) {
					((ServerWorld)world).sendParticles(BlacksilenceshadowParticle.particle, x, y + 1.0, z, 15, 0.2, 0.6, 0.2, 0.0);
				}

				if (world instanceof World && !((World) world).isClientSide) {
					world.playSound(null, new BlockPos(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:dice_roll")), SoundCategory.PLAYERS, 1.0F, 0.8F);
				} else {
					((World)world).playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:dice_roll")), SoundCategory.PLAYERS, 1.0F, 0.8F, false);
				}
			}

			/*if (!entity.world.isRemote() && entity.field_70170_p.func_73046_m() != null) {
				Optional<FunctionObject> _fopt = entity.world.playSound().func_193030_aL().func_215361_a(new ResourceLocation("tcorp:furiosotrigger"));
				if (_fopt.isPresent()) {
					FunctionObject _fobj = _fopt.get();
					entity.field_70170_p.func_73046_m().func_193030_aL().func_195447_a(_fobj, entity.func_195051_bN());
				}
			}*/

			if (entity instanceof LivingEntity) {
				LivingEntity entityLiving = (LivingEntity)entity;
				//entityLiving.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 40, 1, false, false));
				//entityLiving.addEffect(new EffectInstance(Effects.DIG_SPEED, 40, 1, false, false));
				entityLiving.addEffect(new EffectInstance(FuriosoPotionEffect.potion, 340, 0, false, false));
				entityLiving.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 340, 1, false, false));
			}

			if (entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EgoWeaponsModVars.PlayerVariables()).blipcooldown < 40.0) {
				double _setvalue = 40.0;
				entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent((capability) -> {
					capability.blipcooldown = _setvalue;
					capability.syncPlayerVariables(entity);
				});
			}

		}
	}
}

package net.m3tte.tcorp.procedures;

import net.m3tte.tcorp.TcorpMod;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.potion.EnergyboostPotionEffect;
import net.m3tte.tcorp.potion.EnergyfatiguePotionEffect;
import net.m3tte.tcorp.potion.Terror;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

import static net.m3tte.tcorp.TcorpModVariables.PLAYER_VARIABLES_CAPABILITY;

public class BliphandlerProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
			if (event.phase == TickEvent.Phase.END) {
				Entity entity = event.player;
				World world = entity.level;
				double i = entity.getX();
				double j = entity.getY();
				double k = entity.getZ();
				Map<String, Object> dependencies = new HashMap<>();
				dependencies.put("x", i);
				dependencies.put("y", j);
				dependencies.put("z", k);
				dependencies.put("world", world);
				dependencies.put("entity", entity);
				dependencies.put("event", event);
				executeProcedure(dependencies);
			}
		}
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure Bliphandler!");
			return;
		}
		PlayerEntity entity = (PlayerEntity) dependencies.get("entity");
		double cd = 0;



		TcorpModVariables.PlayerVariables entityData = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);


		if (entityData == null)
			return;

		if (entity.level.isClientSide)
			return;

		if (entity.tickCount % 10 == 0) {
			if (entityData.blips < entityData.maxblips) {
				if (entityData.blipcooldown <= 0 && !entity.hasEffect(Terror.get())) {

					double baseblipregen = 0.07 + 0.32 * entityData.determination / 150;

					if (entity.hasEffect(EnergyfatiguePotionEffect.get())) {
						baseblipregen /= (1 + 0.4*entity.getEffect(EnergyfatiguePotionEffect.get()).getAmplifier());
					}

					if (entity.hasEffect(EnergyboostPotionEffect.get())) {
						baseblipregen *= (1 + 0.5*entity.getEffect(EnergyboostPotionEffect.get()).getAmplifier());
					}
					entityData.blips = Math.min(entityData.blips + baseblipregen, entityData.maxblips);

				} else {
					entityData.blipcooldown -= 1;
				}
			} else if (entityData.blips > entityData.maxblips) {
				entityData.blips = entityData.maxblips;
			}
		}

		if (entityData.globalcooldown > 0)
			entityData.globalcooldown--;

		entityData.syncPlayerVariables(entity);

	}
}

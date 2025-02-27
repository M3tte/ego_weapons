package net.m3tte.ego_weapons.procedures;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.network.packages.AbilityPackages;
import net.m3tte.ego_weapons.potion.EnergyboostPotionEffect;
import net.m3tte.ego_weapons.potion.EnergyfatiguePotionEffect;
import net.m3tte.ego_weapons.potion.Terror;
import net.m3tte.ego_weapons.potion.countEffects.CountPotencyStatus;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

public class BlipTick {
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


	public static void chargeBlips(PlayerEntity player, EgoWeaponsModVars.PlayerVariables vars, double amount) {
		chargeBlips(player, vars, amount, false);
	}

	public static void chargeBlips(PlayerEntity player, EgoWeaponsModVars.PlayerVariables vars, double amount, boolean sfx) {
		float multiplier = 1;
		float pitch = 1;

		if (EgoWeaponsItems.MAGIC_BULLET_CLOAK.get().equals(player.getItemBySlot(EquipmentSlotType.CHEST).getItem())) {

			if (!player.level.isClientSide() && player.level.random.nextFloat() > 0.7f) {
				pitch = 1.5f;
				multiplier += 1;
			}

		}
		vars.blips = Math.min(vars.blips + amount * multiplier, vars.maxblips);
		if (sfx)
			player.level.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(),
					EgoWeaponsSounds.RESULT_POSITIVE,
					SoundCategory.PLAYERS, 1, pitch);
	}

	public static void chargeBlips(PlayerEntity player, double amount, boolean sfx) {
		EgoWeaponsModVars.PlayerVariables playerVariables = player.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EgoWeaponsModVars.PlayerVariables());
		chargeBlips(player, playerVariables, amount, sfx);
		playerVariables.syncPlayerVariables(player);
	}

	public static void chargeBlips(PlayerEntity player, double amount) {
		EgoWeaponsModVars.PlayerVariables playerVariables = player.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EgoWeaponsModVars.PlayerVariables());
		chargeBlips(player, playerVariables, amount, false);
		playerVariables.syncPlayerVariables(player);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency entity for procedure Bliphandler!");
			return;
		}
		PlayerEntity entity = (PlayerEntity) dependencies.get("entity");
		double cd = 0;



		EgoWeaponsModVars.PlayerVariables entityData = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);


		if (entityData == null)
			return;

		if (entity.level.isClientSide)
			return;


		if (entity.tickCount % 20 == 0) {


			if (entityData.stagger < entityData.maxStagger) {
				StaggerSystem.healStagger(entity, 0.1f);
			}

			if (entity.hasEffect(Terror.get())) {
				SanitySystem.damageSanity(entity, 0.1f);
			} else {
				if (entityData.sanity < entityData.maxSanity) {
					float sanityHeal = 0.1f;

					if (entity.getItemBySlot(EquipmentSlotType.CHEST).equals(EgoWeaponsItems.SUNSHOWER_CLOAK.get())) {
						if (EntityType.byString(entity.getShoulderEntityRight().getString("id")).filter((p_215344_0_) -> p_215344_0_ == EgoWeaponsEntities.SUNSHOWER_FOX.get()).isPresent()) {
							sanityHeal += 0.3f;
						} else if (EntityType.byString(entity.getShoulderEntityLeft().getString("id")).filter((p_215344_0_) -> p_215344_0_ == EgoWeaponsEntities.SUNSHOWER_FOX.get()).isPresent()) {
							sanityHeal += 0.3f;
						}
					}



					SanitySystem.healSanity(entity, sanityHeal);
				}
			}
		}

		if (entity.tickCount % 10 == 0) {




			EmotionSystem.decreaseEmotionPoints(entity, 0.6f);

			entityData.maxblips = EmotionSystem.getMaxEnergy(entityData);

			if (SanitySystem.getSanity(entity)<=0.1f) {
				entity.addEffect(new EffectInstance(Effects.BLINDNESS, 300, 0));
				entity.addEffect(new EffectInstance(Effects.WEAKNESS, 300, 1));
				entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 300, 1));
				entity.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 300, 1));
				SanitySystem.healSanity(entity, 10);
			}

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

			if (entity.getPersistentData().contains("onrushChain") && entity.tickCount % 20 == 0) {
				int onrushLeft = entity.getPersistentData().getInt("onrushChain") - 1;
				System.out.println("Onrushchain found, with: "+onrushLeft+" left");
				if ( onrushLeft > 0) {
					entity.getPersistentData().putInt("onrushChain", onrushLeft);
				} else {
					entity.getPersistentData().remove("onrushChain");
				}

				EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(),
						new AbilityPackages.SyncOnrushData(entity.getId(), onrushLeft));
			}
		}

		if (entityData.globalcooldown > 0)
			entityData.globalcooldown--;

		entityData.syncPlayerVariables(entity);

	}
}

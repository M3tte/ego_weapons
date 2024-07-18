package net.m3tte.tcorp.procedures;

import net.m3tte.tcorp.TcorpMod;
import net.m3tte.tcorp.item.SuitItem;
import net.m3tte.tcorp.item.blackSilence.BlacksilencesuitItem;
import net.m3tte.tcorp.particle.BlacksilenceshadowParticle;
import net.m3tte.tcorp.potion.OrlandoPotionEffect;
import net.m3tte.tcorp.potion.FuriosoPotionEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class BlacksilenceOnEffectActiveTickProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure BlacksilenceOnEffectActiveTick!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				TcorpMod.LOGGER.warn("Failed to load dependency x for procedure BlacksilenceOnEffectActiveTick!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				TcorpMod.LOGGER.warn("Failed to load dependency y for procedure BlacksilenceOnEffectActiveTick!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				TcorpMod.LOGGER.warn("Failed to load dependency z for procedure BlacksilenceOnEffectActiveTick!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure BlacksilenceOnEffectActiveTick!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(BlacksilenceshadowParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
					(entity.getZ()), (int) 3, (entity.getBbWidth() / 2.5), (entity.getBbHeight() / 3), (entity.getBbWidth() / 2.5), 0);
		}
		if (!(((entity instanceof LivingEntity) ? ((LivingEntity) entity).getItemBySlot(EquipmentSlotType.CHEST) : ItemStack.EMPTY)
				.getItem() == BlacksilencesuitItem.body)
				|| !(((entity instanceof LivingEntity) ? ((LivingEntity) entity).getItemBySlot(EquipmentSlotType.HEAD) : ItemStack.EMPTY)
						.getItem() == SuitItem.helmet)) {
			if (world instanceof World && !((World) world).isClientSide) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather")),
						SoundCategory.NEUTRAL, (float) 1, (float) 1);
			} else {
				((World) world).playLocalSound(x, y, z,
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather")),
						SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
			}
			if (entity instanceof LivingEntity) {
				((LivingEntity) entity).removeEffect(OrlandoPotionEffect.potion);
			}
			if (entity instanceof LivingEntity) {
				((LivingEntity) entity).removeEffect(FuriosoPotionEffect.potion);
			}
			BlacksilenceEffectExpiresProcedure.executeProcedure(Stream.of(new AbstractMap.SimpleEntry<>("entity", entity)).collect(HashMap::new,
					(_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}

		/*if (entity instanceof LivingEntity) {
			LivingEntity living = (LivingEntity) entity;

			if (!(living.hasEffect(Effects.DAMAGE_RESISTANCE) && living.getEffect(Effects.DAMAGE_RESISTANCE).getDuration() > 4)) {

				int time = living.getEffect(BlacksilencePotionEffect.potion).getDuration();

				living.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, (int) time, (int) 1, (false), (false)));
				living.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, (int) time, (int) 1, (false), (false)));
				living.addEffect(new EffectInstance(Effects.DIG_SPEED, (int) time, (int) 1, (false), (false)));
			}
		}*/
	}
}

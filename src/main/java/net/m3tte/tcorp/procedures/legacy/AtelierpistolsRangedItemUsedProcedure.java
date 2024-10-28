package net.m3tte.tcorp.procedures.legacy;

import net.m3tte.tcorp.TCorpItems;
import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.m3tte.tcorp.potion.FuriosoPotionEffect;
import net.m3tte.tcorp.TcorpMod;

import java.util.Map;
import java.util.Collection;

public class AtelierpistolsRangedItemUsedProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure AtelierpistolsRangedItemUsed!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				TcorpMod.LOGGER.warn("Failed to load dependency itemstack for procedure AtelierpistolsRangedItemUsed!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		if (new Object() {
			boolean check(Entity _entity) {
				if (_entity instanceof LivingEntity) {
					Collection<EffectInstance> effects = ((LivingEntity) _entity).getActiveEffects();
					for (EffectInstance effect : effects) {
						if (effect.getEffect() == FuriosoPotionEffect.potion)
							return true;
					}
				}
				return false;
			}
		}.check(entity) && entity.getPersistentData().getDouble("furiosohits") <= 10) {
			if (TCorpItems.ATELIER_LOGIC_PISTOLS.get() == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getOffhandItem().getItem() : ItemStack.EMPTY.getItem())) {
				if (new Object() {
					boolean check(Entity _entity) {
						if (_entity instanceof LivingEntity) {
							Collection<EffectInstance> effects = ((LivingEntity) _entity).getActiveEffects();
							for (EffectInstance effect : effects) {
								if (effect.getEffect() == Effects.DIG_SPEED)
									return true;
							}
						}
						return false;
					}
				}.check(entity)) {
					if (entity instanceof PlayerEntity)
						((PlayerEntity) entity).getCooldowns().addCooldown(itemstack.getItem(), (int) 8);
				} else {
					if (entity instanceof PlayerEntity)
						((PlayerEntity) entity).getCooldowns().addCooldown(itemstack.getItem(), (int) 15);
				}
			} else {
				if (entity instanceof PlayerEntity)
					((PlayerEntity) entity).getCooldowns().addCooldown(itemstack.getItem(), (int) 30);
			}
		} else {
			if (TCorpItems.ATELIER_LOGIC_PISTOLS.get() == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getOffhandItem().getItem() : ItemStack.EMPTY.getItem())) {
				if (entity instanceof PlayerEntity)
					((PlayerEntity) entity).getCooldowns().addCooldown(itemstack.getItem(), (int) 30);
			} else {
				if (entity instanceof PlayerEntity)
					((PlayerEntity) entity).getCooldowns().addCooldown(itemstack.getItem(), (int) 40);
			}
		}
	}
}

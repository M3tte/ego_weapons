package net.m3tte.ego_weapons.execFunctions;

import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModElements;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims;
import net.m3tte.ego_weapons.item.CrimsonkimonoItem;
import net.m3tte.ego_weapons.particle.ArmourupparticleParticle;
import net.m3tte.ego_weapons.particle.CrimsonfanparticleParticle;
import net.m3tte.ego_weapons.potion.MarkedeffectPotionEffect;
import net.m3tte.ego_weapons.potion.WoodsmansstancePotionEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.HashMap;
import java.util.Map;

import static net.m3tte.ego_weapons.procedures.SharedFunctions.applyStaggerDamageGeneric;

public class HitProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {


		@SubscribeEvent
		public static void onEntityDamaged(LivingDamageEvent event) {
			System.out.println("DAMAGED TEST");
			applyStaggerDamageGeneric(event.getSource(), event.getAmount(), event, event.getEntityLiving());
		}

	}

		public static void hitStunEffect(LivingEntityPatch<?> patch) {
			hitStunEffect(patch, 0);
		}

		public static void hitStunEffect(LivingEntityPatch<?> patch, float timeMod) {
			LivingEntity living = patch.getOriginal();
			if (patch.getHitAnimation(ExtendedDamageSource.StunType.LONG) == Animations.BIPED_HIT_LONG) {
				patch.playAnimationSynchronized(BlackSilenceMovesetAnims.RANGA_GUARD_STAGGER, timeMod);
				living.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), (int) (20 * (timeMod * 2 + 1)), (int) 0, (false), (false)));
			} else {
				living.addEffect(new EffectInstance(Effects.WEAKNESS, (int) 15, (int) 4, (false), (false)));
				living.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, (int) 40, (int) 4, (false), (false)));
				if (patch.getHitAnimation(ExtendedDamageSource.StunType.SHORT) != null) {
					patch.playAnimationSynchronized(patch.getHitAnimation(ExtendedDamageSource.StunType.SHORT), 1f);
				}
			}
		}

}

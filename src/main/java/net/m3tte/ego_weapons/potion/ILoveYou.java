
package net.m3tte.ego_weapons.potion;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims;
import net.m3tte.ego_weapons.potion.countEffects.Shell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ILoveYou {
	@ObjectHolder("ego_weapons:i_love_you")
	public static final Effect potion = null;

	public static Effect get() {
		Objects.requireNonNull(potion, () -> "Registry Object not present: ILOVEYOU");
		return potion;
	}

	@SubscribeEvent
	public static void registerEffect(RegistryEvent.Register<Effect> event) {
		event.getRegistry().register(new EffectCustom());
	}

	public static class EffectCustom extends Effect {
		public EffectCustom() {
			super(EffectType.BENEFICIAL, -16777216);
			setRegistryName("i_love_you");
		}

		@Override
		public String getDescriptionId() {
			return "effect.i_love_you";
		}

		@Override
		public boolean isBeneficial() {
			return true;
		}

		@Override
		public boolean isInstantenous() {
			return false;
		}

		@Override
		public boolean shouldRenderInvText(EffectInstance effect) {
			return true;
		}

		@Override
		public boolean shouldRender(EffectInstance effect) {
			return true;
		}

		@Override
		public boolean shouldRenderHUD(EffectInstance effect) {
			return true;
		}

		@Override
		public boolean isDurationEffectTick(int duration, int amplifier) {
			return true;
		}


	}

	public static void onHit(Entity sourceEntity, LivingEntity target) {

		if (sourceEntity instanceof LivingEntity) {
			LivingEntity sourceLiving = (LivingEntity) sourceEntity;

			System.out.println("Attack damage found is: "+sourceLiving.getAttributeValue(Attributes.ATTACK_DAMAGE));
			// 6, 12, 18, 24, 30
			int result = (int)(sourceLiving.getAttributeValue(Attributes.ATTACK_DAMAGE) / 6);
			target.level.playSound(null,target.blockPosition(), EgoWeaponsSounds.RESULT_POSITIVE, SoundCategory.PLAYERS, 1, 1);
			target.level.playSound(null,target.blockPosition(), EgoWeaponsSounds.METAL_CLASH, SoundCategory.PLAYERS, 1, 1);
			if (result > 0) {
				target.addEffect(new EffectInstance(Shell.get(),200, result-1));
				EgoWeaponsEffects.IMITATION.get().increment(sourceLiving, 200, result-1);
				target.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(),30, result-1));
			}

			float healAmount = sourceLiving.getMaxHealth() * 0.25f;
			if (healAmount > target.getMaxHealth() * 0.35)
				healAmount = target.getMaxHealth() * 0.35f;
			target.heal(healAmount);
			sourceLiving.addEffect(new EffectInstance(Terror.get(),300, 0));
			// TODO: HEAL SANITY ONCE IMPLEMENTED

			if (target instanceof PlayerEntity) {
				PlayerPatch<?> entitypatch = (PlayerPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

				entitypatch.playAnimationSynchronized(BlackSilenceMovesetAnims.RANGA_GUARD_HIT, 0);
			}


			target.removeEffect(ILoveYou.get());
		}

	}
}

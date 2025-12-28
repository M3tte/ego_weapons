
package net.m3tte.ego_weapons.item.blackSilence.weapons;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.execFunctions.BlackSilenceEvaluator;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.movesets.DurandalMovesetAnims;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.particle.BlacksilenceshadowParticle;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

// durandal
public class DurandalItem extends EgoWeaponsWeapon {


	public DurandalItem(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(durandalTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}
	// elements.items.add(() -> new SwordItem(, 3, -2.9f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("A holy sword wielded by a certain fixer.").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 4) + 1) + "/4] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.aleph"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 4) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{});
				else
					generateDescription(list, "blacksilenceweapon", "ability", 1);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "power_up"});
				else
					generateDescription(list,"durandal", "auto", 3);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "offense_up"});
				else
					generateDescription(list,"durandal", "innate", 2);
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "power_up"});
				else
					generateDescription(list,"durandal", "dash", 3);
		}

		list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
	}

	int[] hitAnimations = null;

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		BlackSilenceEvaluator.onHitDurandal(entity.level, entity, sourceentity);

		EgoWeaponsModVars.PlayerVariables entityData = sourceentity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		if (hitAnimations == null)
			hitAnimations = new int[]{DurandalMovesetAnims.DURANDAL_AUTO_4.getId(), DurandalMovesetAnims.DURANDAL_DASH_ATTACK.getId()};

		if (currentanim != null) {
			int animID = currentanim.getId();

			/*if (!sourceentity.level.isClientSide()) {
				sourceentity.level.addParticle(EgoWeaponsParticles.DURANDAL_SWIPE_UP.get(), entity.getX(), entity.getY()+1.1f, entity.getZ(), 0, entity.getId(), sourceentity.getId());
			}*/
			// If animation is part of the ones to register hits.
			if (Arrays.stream(hitAnimations).anyMatch((i) -> i == animID)) {
				itemstack.getOrCreateTag().putBoolean("durandalLastHit", true);
			}

			if (animID == DurandalMovesetAnims.DURANDAL_FURIOSO_3.getId() || animID == DurandalMovesetAnims.DURANDAL_SPECIAL_1.getId() || animID == DurandalMovesetAnims.DURANDAL_JUMP_ATTACK.getId()) {
				if (!sourceentity.level.isClientSide())
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entity.getId(), sourceentity.getId(), EgoWeaponsParticles.DURANDAL_SWIPE_DOWN.get().getRegistryName()));
				itemstack.getOrCreateTag().putBoolean("durandalLastHit", true);
			}

			if (animID == DurandalMovesetAnims.DURANDAL_FURIOSO_2.getId() || animID == DurandalMovesetAnims.DURANDAL_SPECIAL_2.getId()) {
				if (!sourceentity.level.isClientSide())
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entity.getId(), sourceentity.getId(), EgoWeaponsParticles.DURANDAL_SWIPE_UP.get().getRegistryName()));
				itemstack.getOrCreateTag().putBoolean("durandalLastHit", true);
			}
			if (animID == DurandalMovesetAnims.DURANDAL_FURIOSO_1.getId() || animID == DurandalMovesetAnims.DURANDAL_SPECIAL_3.getId() || animID == DurandalMovesetAnims.DURANDAL_AUTO_1.getId()) {
				if (!sourceentity.level.isClientSide())
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entity.getId(), sourceentity.getId(), EgoWeaponsParticles.DURANDAL_SWIPE_HORIZONTAL.get().getRegistryName()));
				itemstack.getOrCreateTag().putBoolean("durandalLastHit", true);
			}
		}

		return retval;
	}

	static IItemTier durandalTier = new IItemTier() {

		@Override
		public int getUses() {
			return 0;
		}

		@Override
		public float getSpeed() {
			return 4.44f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 8;
		}

		@Override
		public int getLevel() {
			return 1;
		}

		@Override
		public int getEnchantmentValue() {
			return 0;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.EMPTY;
		}

		public Ingredient getRepairMaterial() {
			return Ingredient.EMPTY;
		}
	};

	public static StaticAnimation.Event[] sheathableIdleReset() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(0.01F, (entitypatch) -> {

			int val = entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("unsheathed");

			if (val > 1) {
				entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", val-1);
			} else {
				entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 0);
			}

		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}
	public static StaticAnimation.Event[] sheathableWeaponStateManager(float time, int state) {
		return sheathableWeaponStateManager(time, state, null);
	}

	public static StaticAnimation.Event[] sheathableWeaponStateManager(float time, int state, Consumer<LivingEntityPatch<?>> cbk) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(time, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", state);

			if (cbk != null)
				cbk.accept(entitypatch);

		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] genericSheathEvent(float time, boolean powerBuff) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(time, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;

			if (powerBuff)
				EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(entitypatch.getOriginal(), 0, 3);

			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(BlacksilenceshadowParticle.particle, (entity.getX()), (entity.getY() + 1), (entity.getZ()),
						30, 0.2, 0.5, 0.2, 0);
			}
			entity.playSound(EpicFightSounds.SWORD_IN, 1, 1);
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 0);
		}, StaticAnimation.Event.Side.BOTH);


		return events;
	}

	public static StaticAnimation.Event[] durandalFuriosoEvent(int amount) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];
		events[0] = StaticAnimation.Event.create(0.0F, (entitypatch) -> {
			Entity entity = entitypatch.getOriginal();
			BlackSilenceEvaluator.furiosoAddAttackStat(entity, amount);
		}, StaticAnimation.Event.Side.BOTH);

		events[1] = StaticAnimation.Event.create(0.40F, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 2);
		}, StaticAnimation.Event.Side.BOTH);

		return events;
	}

	public static StaticAnimation.Event[] durandalSheatheWeapon(float time, int powerLimit) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];
		events[0] = StaticAnimation.Event.create(0.2f, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 2);
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("durandalLastHit");
		}, StaticAnimation.Event.Side.BOTH);
		events[1] = StaticAnimation.Event.create(time, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 3);
			if (entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getBoolean("durandalLastHit")) {
				EgoWeaponsEffects.POWER_UP.get().increment(entitypatch.getOriginal(), powerLimit, 1);
				if (!entitypatch.getOriginal().level.isClientSide())
					entitypatch.reserveAnimation(DurandalMovesetAnims.DURANDAL_AUTO_REC);
			} else {
				if (!entitypatch.getOriginal().level.isClientSide())
					entitypatch.reserveAnimation(DurandalMovesetAnims.DURANDAL_AUTO_SHEATH);
			}
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] durandalSheatheWeaponNormal(float time) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];
		events[0] = StaticAnimation.Event.create(0.2f, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 2);
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("durandalLastHit");
		}, StaticAnimation.Event.Side.BOTH);
		events[1] = StaticAnimation.Event.create(time, (entitypatch) -> {
			if (!entitypatch.getOriginal().level.isClientSide())
				entitypatch.reserveAnimation(DurandalMovesetAnims.DURANDAL_AUTO_SHEATH);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] durandalCleaveConnection(float time, StaticAnimation anim) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];
		events[0] = StaticAnimation.Event.create(0.1f, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 2);
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("durandalLastHit", 0);
		}, StaticAnimation.Event.Side.BOTH);
		events[1] = StaticAnimation.Event.create(time, (entitypatch) -> {
			if (entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getBoolean("durandalLastHit"))
				if (!entitypatch.getOriginal().level.isClientSide())
					entitypatch.playAnimationSynchronized(anim, 0.01f);


		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}
}

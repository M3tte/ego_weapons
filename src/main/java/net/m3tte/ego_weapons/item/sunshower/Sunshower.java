
package net.m3tte.ego_weapons.item.sunshower;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.Arrays;
import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import static net.m3tte.ego_weapons.execFunctions.HitProcedure.hitStunEffect;
import static net.m3tte.ego_weapons.procedures.SharedFunctions.*;

public class Sunshower extends SwordItem {

	private static IItemTier sunshowerTier = new IItemTier() {

		@Override
		public int getUses() {
			return 0;
		}

		@Override
		public float getSpeed() {
			return 4.2f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 7;
		}

		@Override
		public int getLevel() {
			return 1;
		}

		@Override
		public int getEnchantmentValue() {
			return 2;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.EMPTY;
		}

	};

	public Sunshower(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(sunshowerTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
		ItemModelsProperties.register(this, new ResourceLocation("open"), (itemStack, clientWorld, livingEntity) -> {

			return itemStack.hasTag() ? (itemStack.getTag().getInt("open")) : 0;
		});
	}




	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("Abandonment."));
		list.add(new StringTextComponent("[Ability] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Drift").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" \uE004\uE004\uE004\uE004\uE004").withStyle(TextFormatting.WHITE)));
		list.add(new TranslationTextComponent("desc.ego_weapons.sunshower.1"));
		//list.add(coloredText("[I] Gain 5 \uE003", TextFormatting.WHITE).append(coloredText(" Sinking ", TextFormatting.RED)).append(coloredText(" Potency ", TextFormatting.WHITE)).append(" and 1 \uE003").append(coloredText(" Sinking ", TextFormatting.RED)).append(" Count"));
		list.add(coloredText("[III] Inflict 4 \uE005", TextFormatting.WHITE).append(coloredText(" Rupture", TextFormatting.RED)));

		list.add(new StringTextComponent("[Innate] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Puddle Stomp").withStyle(TextFormatting.GRAY)));
		list.add(coloredText("[III] Spend 5 \uE003", TextFormatting.WHITE).append(coloredText(" Sinking ", TextFormatting.RED)).append(coloredText(" to cast [III]", TextFormatting.WHITE)));
		list.add(coloredText("[IV] Inflict 5 \uE005", TextFormatting.WHITE).append(coloredText(" Rupture", TextFormatting.RED)));

		list.add(new StringTextComponent("[Auto] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Umbrella Thwack").withStyle(TextFormatting.GRAY)));
		//list.add(new StringTextComponent("[Passive] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Heal when dealing damage").withStyle(TextFormatting.GRAY)));

	}

	private static int[] sunshowerAutoAnimIds = null;
	public static void interruptedAttack(PlayerEntity target) {

		if (sunshowerAutoAnimIds == null) {
			sunshowerAutoAnimIds = new int[]{EgoWeaponsAnimations.SUNSHOWER_AUTO_1.getId(), EgoWeaponsAnimations.SUNSHOWER_AUTO_2.getId(), EgoWeaponsAnimations.SUNSHOWER_AUTO_3.getId(), EgoWeaponsAnimations.SUNSHOWER_AUTO_4.getId()};
        }

		PlayerPatch<?> entitypatch = (PlayerPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		final int anim_id = currentanim.getId();

		if (Arrays.stream(sunshowerAutoAnimIds).anyMatch((v) -> v==anim_id)) {
			EgoWeaponsEffects.SINKING.get().increment(target, 1, 2);
		}
	}


	int[] puddleStompAnimations = null;

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, target, sourceentity);
		double x = target.getX();
		double y = target.getY();
		double z = target.getZ();

		if (puddleStompAnimations == null) {
			puddleStompAnimations = new int[]{EgoWeaponsAnimations.SUNSHOWER_PUDDLE_STOMP_2.getId(), EgoWeaponsAnimations.SUNSHOWER_PUDDLE_STOMP_3.getId()};;
		}

		World world = target.level;

		Item chestItem = sourceentity.getItemBySlot(EquipmentSlotType.CHEST).getItem();

		PlayerVariables entityData = sourceentity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		PlayerPatch<?> entitypatch = (PlayerPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		final int anim_id = currentanim.getId();

		if (anim_id == EgoWeaponsAnimations.SUNSHOWER_AUTO_3.getId()) {
			EgoWeaponsEffects.SINKING.get().increment(target, 3, 1);
		}

		if (anim_id == EgoWeaponsAnimations.SUNSHOWER_JUMP_ATTACK.getId()) {
			entitypatch.playAnimationSynchronized(EgoWeaponsAnimations.SUNSHOWER_JUMP_ATTACK_F, 0);
			LivingEntityPatch<?> targetentitypatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
			EgoWeaponsEffects.SINKING.get().increment(target, 1, 0);

			if (targetentitypatch != null) {
				pummelDownEntity(targetentitypatch, 2);
			}

			entitypatch.playSound(EgoWeaponsSounds.SWORD_STAB, 1, 1, 1);
		}

		if (anim_id == EgoWeaponsAnimations.SUNSHOWER_SPREAD_OUT_1.getId()) {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("spreadoutcounter",1);
			EgoWeaponsEffects.SINKING.get().increment(target, 2, 0);
		}

		if (anim_id == EgoWeaponsAnimations.SUNSHOWER_SPREAD_OUT_2.getId()) {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("spreadoutcounter",2);
			LivingEntityPatch<?> targetentitypatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

			if (targetentitypatch != null) {
				target.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 3, 0));
				pummelUpEntity(targetentitypatch, 2);
			}

			entitypatch.playSound(EgoWeaponsSounds.SWORD_STAB, 1, 1, 1);
		}

		if (anim_id == EgoWeaponsAnimations.SUNSHOWER_SPREAD_OUT_3.getId()) {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("spreadoutcounter");
			EgoWeaponsEffects.RUPTURE.get().increment(target, 0, 4);
		}

		if (anim_id == EgoWeaponsAnimations.SUNSHOWER_COUNTER_ATTACK.getId()) {
			EgoWeaponsEffects.SINKING.get().increment(target, 1, 2);
		}

		// Any of the puddle stomp animations.
		if (Arrays.stream(puddleStompAnimations).anyMatch((e) -> e == anim_id)) {

			LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

			if (targetPatch != null) {
				targetPatch.knockBackEntity(sourceentity.position(), -1);
				pummelDownEntity(targetPatch, 2);
			}

		}

		if (anim_id == EgoWeaponsAnimations.SUNSHOWER_PUDDLE_STOMP_3.getId()) {
			EgoWeaponsEffects.RUPTURE.get().increment(target, 0, 5);
		}


		return retval;
	}

	public static StaticAnimation.Event[] setOpenstate(int state) {
		return setOpenstate(state, 0.1f);
	}
	public static StaticAnimation.Event[] setOpenstate(int state, float timeStamp) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(timeStamp, (entitypatch) -> {
			//System.out.println("TRYING TO CHANGE STATE TO: "+state);
			if (state != 0 || (!entitypatch.currentCompositeMotion.equals(LivingMotions.BLOCK) && !entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().contains("changestateblock"))) { //|| ( && !(TCorpAnimations.SUNSHOWER_PUDDLE_STOMP_3.getId() == entitypatch.getServerAnimator().animationPlayer.getAnimation().getId()))) {
				//System.out.println("CHANGING STATE TO: "+state);
				entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("open", state);
				entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("changestateblock");
			}


		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] auto1Event(SoundEvent e, float timeStamp) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];

		events[0] = StaticAnimation.Event.create(timeStamp, (entitypatch) -> {
			entitypatch.playSound(e, 1, 1);

			EgoWeaponsEffects.SINKING.get().increment(entitypatch.getOriginal(), 1, 0);
		}, StaticAnimation.Event.Side.BOTH);

		return events;
	}

	public static StaticAnimation.Event[] auto2Event() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(0.04F, (entitypatch) -> {
			entitypatch.playSound(EgoWeaponsSounds.SUNSHOWER_AUTO_2, 1, 1);
			EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0,-1.5f), 1, EgoWeaponsParticles.SUNSHOWER_OPEN.get(), 0, "Tool_R");
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("open", 1);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] counterEvent1() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];
		events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
			entitypatch.playSound(EgoWeaponsSounds.SUNSHOWER_COUNTER_1,2, 1, 1);
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("open", 0);
			EgoWeaponsEffects.SINKING.get().increment(entitypatch.getOriginal(), 0, 2);
		}, StaticAnimation.Event.Side.BOTH);
		events[1] = StaticAnimation.Event.create(0.55f, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("open", 0);
			entitypatch.playAnimationSynchronized(EgoWeaponsAnimations.SUNSHOWER_COUNTER_ATTACK, 0);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] counterEvent2() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[3];
		events[0] = StaticAnimation.Event.create(0.83f, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("open", 1);
			EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0,-1.5f), 1, EgoWeaponsParticles.SUNSHOWER_OPEN.get(), 0, "Tool_R");
		}, StaticAnimation.Event.Side.BOTH);
		events[1] = StaticAnimation.Event.create(1.3F, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("open", 3);
			entitypatch.playSound(EgoWeaponsSounds.SUNSHOWER_COUNTER_2,2, 1, 1);
			EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0,-1.5f), 1, EgoWeaponsParticles.SUNSHOWER_AUTO1_STRIKE.get(), 0, "Tool_R");
		}, StaticAnimation.Event.Side.BOTH);
		events[2] = StaticAnimation.Event.create(1.8F, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("open", 0);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] auto3Event() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(0.5F, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("open", 0);
		}, StaticAnimation.Event.Side.BOTH);

		return events;
	}

	public static StaticAnimation.Event[] puddleStomp2Event() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[4];
		events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putBoolean("changestateblock", true);
			entitypatch.playSound(EgoWeaponsSounds.SUNSHOWER_PUDDLE_STOMP_1, 1, 1, 1);
		}, StaticAnimation.Event.Side.BOTH);
		events[1] = StaticAnimation.Event.create(0.8F, (entitypatch) -> {
			entitypatch.playSound(EgoWeaponsSounds.SUNSHOWER_PUDDLE_STOMP_2, 1, 1, 1);
			entitypatch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 20, 0));
		}, StaticAnimation.Event.Side.BOTH);
		events[2] = StaticAnimation.Event.create(1.15F, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("open", 1);
			EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0,-1f), 1, EgoWeaponsParticles.PUDDLE_STOMP_RIPPLE.get(), 0, "Tool_R");
		}, StaticAnimation.Event.Side.BOTH);
		events[3] = StaticAnimation.Event.create(1.3F, (entitypatch) -> {
			if (EgoWeaponsEffects.SINKING.get().getPotency(entitypatch.getOriginal()) >= 5) {//entitypatch.getOriginal().getPersistentData().contains("sunshowerhit")) {
				EgoWeaponsEffects.SINKING.get().decrement(entitypatch.getOriginal(), 0, 5);
				entitypatch.playAnimationSynchronized(EgoWeaponsAnimations.SUNSHOWER_PUDDLE_STOMP_3, 0);
			} else {
				entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("open", 0);
			}
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] puddleStomp3Event() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];
		events[0] = StaticAnimation.Event.create(1.45F, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("open", 2);
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("changestateblock");
			entitypatch.playSound(EgoWeaponsSounds.SUNSHOWER_PUDDLE_STOMP_3, 1, 1, 1);
			EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0,-1.5f), 1, EgoWeaponsParticles.SUNSHOWER_OPEN.get(), 0, "Tool_R");
			EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0,-1.05f), 1, EgoWeaponsParticles.PUDDLE_STOMP_IMPACT.get(), 0, "Tool_R");
			EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0,-1.1f), 1, EgoWeaponsParticles.PUDDLE_STOMP_RIPPLE.get(), 0, "Tool_R");
		}, StaticAnimation.Event.Side.BOTH);
		events[1] = StaticAnimation.Event.create(2.4F, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("open", 0);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] spreadout1Event() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];

		events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
			entitypatch.playSound(EgoWeaponsSounds.SUNSHOWER_SPREAD_OUT_1, 1f,1, 1);
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("spreadoutcounter",1);
		}, StaticAnimation.Event.Side.BOTH);
		events[1] = StaticAnimation.Event.create(0.33f, (entitypatch) -> {
			LivingEntity ent = entitypatch.getOriginal();
			ent.level.addParticle(EgoWeaponsParticles.SUNSHOWER_DRIFT_B.get(), ent.getX(), ent.getY()+0.9f, ent.getZ(), 0, ent.getId(), 0);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] spreadout2Event() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];

		events[0] = StaticAnimation.Event.create(0.0f, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("spreadoutcounter");
		}, StaticAnimation.Event.Side.BOTH);

		events[1] = StaticAnimation.Event.create(0.9f, (entitypatch) -> {
			if (entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("spreadoutcounter") == 2) {
				entitypatch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 20, 0));
				entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("spreadoutcounter");
				entitypatch.playAnimationSynchronized(EgoWeaponsAnimations.SUNSHOWER_SPREAD_OUT_3, 0);
			}
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] dashAttackEvent() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];

		events[0] = StaticAnimation.Event.create(0f, (entitypatch) -> {
			if (entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("spreadoutcounter") >= 1) {
				entitypatch.playAnimationSynchronized(EgoWeaponsAnimations.SUNSHOWER_SPREAD_OUT_2, 0);
				entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("open", 0);
				entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("spreadoutcounter");
				entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("changestateblock");
			}
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] spreadout3Event() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[3];

		events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
			entitypatch.playSound(EgoWeaponsSounds.SUNSHOWER_SPREAD_OUT_3, 1f,1, 1);
		}, StaticAnimation.Event.Side.CLIENT);

		events[1] = StaticAnimation.Event.create(0.5f, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("open", 1);
			EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0,-1.75f), 1, EgoWeaponsParticles.PUDDLE_STOMP_RIPPLE.get(), 0, "Tool_R");
		}, StaticAnimation.Event.Side.BOTH);

		events[2] = StaticAnimation.Event.create(1f, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("open", 0);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}
}

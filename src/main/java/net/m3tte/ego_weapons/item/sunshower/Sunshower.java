
package net.m3tte.ego_weapons.item.sunshower;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsEntities;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.entities.SunshowerUmbrellaEntity;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.movesets.SunshowerMovesetAnims;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.potion.countEffects.TremorEffect;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
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
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class Sunshower extends EgoWeaponsWeapon {

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
	}

	@Override
	public String getDefaultKillIdentifier() {
		return "sunshower";
	}

	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("I’ll show you how much it hurts to have ragged broken ribs from a dumped umbrella jammed in you.").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 4) + 1) + "/4] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.he"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 4) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"white", "sinking"});
				else
					generateDescription(list, "sunshower", "auto", 7);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"white", "sinking", "tremor_burst", "rupture"});
				else
					generateDescription(list,"sunshower", "innate", 6);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"white", "sinking", "rupture"});
				else
					generateDescription(list,"sunshower", "ability", 4);
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"white", "sinking"});
				else
					generateDescription(list,"sunshower", "guard", 3);
		}

		list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));



	}

	private static int[] sunshowerAutoAnimIds = null;
	public static void interruptedAttack(LivingEntity target) {

		if (sunshowerAutoAnimIds == null) {
			sunshowerAutoAnimIds = new int[]{SunshowerMovesetAnims.SUNSHOWER_AUTO_1.getId(), SunshowerMovesetAnims.SUNSHOWER_AUTO_2.getId(), SunshowerMovesetAnims.SUNSHOWER_AUTO_3.getId(), SunshowerMovesetAnims.SUNSHOWER_AUTO_4.getId()};
        }

		PlayerPatch<?> entitypatch = (PlayerPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		final int anim_id = currentanim.getId();

		if (Arrays.stream(sunshowerAutoAnimIds).anyMatch((v) -> v==anim_id)) {
			EgoWeaponsEffects.SINKING.get().increment(target, 0, 2);
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
			puddleStompAnimations = new int[]{SunshowerMovesetAnims.SUNSHOWER_PUDDLE_STOMP_2.getId(), SunshowerMovesetAnims.SUNSHOWER_PUDDLE_STOMP_3.getId()};;
		}

		World world = target.level;

		Item chestItem = sourceentity.getItemBySlot(EquipmentSlotType.CHEST).getItem();

		PlayerVariables entityData = sourceentity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		PlayerPatch<?> entitypatch = (PlayerPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		final int anim_id = currentanim.getId();

		String animIdentifier = currentanim.getRealAnimation().getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

		switch (animIdentifier) {
			case "sunshower_auto_1":
				EgoWeaponsEffects.SINKING.get().increment(target, 1, 1);
				EgoWeaponsEffects.SINKING.get().increment(sourceentity, 0, 2);
				break;

			case "sunshower_auto_2":
			case "sunshower_auto_3":
				EgoWeaponsEffects.SINKING.get().increment(target, 1, 1);
				break;

			case "sunshower_auto_4":
				EgoWeaponsEffects.SINKING.get().increment(target, 3, 1);
				break;

			case "sunshower_jump_attack":
				entitypatch.playAnimationSynchronized(SunshowerMovesetAnims.SUNSHOWER_JUMP_ATTACK_F, 0);
				LivingEntityPatch<?> targetentitypatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
				EgoWeaponsEffects.SINKING.get().increment(target, 1, 0);

				if (targetentitypatch != null) {
					pummelDownEntity(targetentitypatch, 2);
				}

				entitypatch.playSound(EgoWeaponsSounds.SWORD_STAB, 1, 1, 1);
				break;

			case "sunshower_spread_out_1":
				entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("spreadoutcounter",1);
				EgoWeaponsEffects.SINKING.get().increment(target, 4, 0);
				break;

			case "sunshower_spread_out_2":
				entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("spreadoutcounter",2);
				LivingEntityPatch<?> trgtPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

				if (trgtPatch != null) {
					target.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 3, 0));
					pummelUpEntity(trgtPatch, 2);
				}

				entitypatch.playSound(EgoWeaponsSounds.SWORD_STAB, 1, 1, 1);
				break;

			case "sunshower_spread_out_3":
				entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("spreadoutcounter");
				EgoWeaponsEffects.RUPTURE.get().increment(target, 2, 4);
				break;

			case "sunshower_counter":
				EgoWeaponsEffects.SINKING.get().increment(target, 1, 2);
				break;

			case "sunshower_puddle_stomp_3":
				EgoWeaponsEffects.RUPTURE.get().increment(target, 2, 5);
				TremorEffect.burstTremor(target, true);
				EgoWeaponsEffects.SINKING.get().increment(target, 1, 2);
				break;
		}



		// Any of the puddle stomp animations.
		if (Arrays.stream(puddleStompAnimations).anyMatch((e) -> e == anim_id)) {
			LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

			if (targetPatch != null) {
				targetPatch.knockBackEntity(sourceentity.position(), -1);
				pummelDownEntity(targetPatch, 2);
			}
		}

		return retval;
	}

	public static float modifyDamageAmount(LivingEntity target, LivingEntity source, float mult, DamageSource damageSource) {

		PlayerPatch<?> entitypatch = (PlayerPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		String weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

		PlayerVariables entityData = source.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);


		int selfSinkingPotency = EgoWeaponsEffects.SINKING.get().getPotency(source);
		int targetSinkingPotency = EgoWeaponsEffects.SINKING.get().getPotency(source);



		switch (weaponIdentifier) {
			case "sunshower_puddle_stomp_3":
				SharedFunctions.incrementBonusDamage(damageSource, 0.5f);
				mult += 0.5f;
				break;

			case "sunshower_spread_out_3":
				SharedFunctions.incrementBonusDamage(damageSource, 1f);
				mult += 1f;
				break;
		}



		return mult;
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
			entitypatch.playAnimationSynchronized(SunshowerMovesetAnims.SUNSHOWER_COUNTER_ATTACK, 0);
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
				entitypatch.playAnimationSynchronized(SunshowerMovesetAnims.SUNSHOWER_PUDDLE_STOMP_3, 0);
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
			if (entitypatch.getOriginal() instanceof PlayerEntity)
				SanitySystem.healSanity((PlayerEntity) entitypatch.getOriginal(), 5);
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
				entitypatch.playAnimationSynchronized(SunshowerMovesetAnims.SUNSHOWER_SPREAD_OUT_3, 0);
			}
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] dashAttackEvent() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];

		events[0] = StaticAnimation.Event.create(0f, (entitypatch) -> {
			if (entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("spreadoutcounter") >= 1) {
				if (!entitypatch.getOriginal().level.isClientSide())
					entitypatch.playAnimationSynchronized(SunshowerMovesetAnims.SUNSHOWER_SPREAD_OUT_2, 0);
				entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("open", 0);
				entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("spreadoutcounter");
				entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("changestateblock");
			}
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] getAwayEvent() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];

		events[0] = StaticAnimation.Event.create(0.4f, (entitypatch) -> {
			entitypatch.playSound(EgoWeaponsSounds.SWORD_STAB, 1f,1, 1);
		}, StaticAnimation.Event.Side.CLIENT);

		events[1] = StaticAnimation.Event.create(1.1f, (entitypatch) -> {

			SunshowerUmbrellaEntity umbrellaEntity = EgoWeaponsEntities.SUNSHOWER_UMBRELLA.get().create(entitypatch.getOriginal().level);

			entitypatch.playSound(EpicFightSounds.EVISCERATE, 1f,1, 1);
			if (umbrellaEntity != null) {

				int sinkingPotency = (EgoWeaponsEffects.SINKING.get().getPotency(entitypatch.getOriginal()) / 5) * 5;

				EgoWeaponsEffects.SINKING.get().decrement(entitypatch.getOriginal(), 0, sinkingPotency);

				if (entitypatch.getOriginal().getAbsorptionAmount() < ((float) sinkingPotency / 2)) {
					entitypatch.getOriginal().setAbsorptionAmount(((float) sinkingPotency / 3));
				}


				Vector3d pos = entitypatch.getOriginal().position();
				umbrellaEntity.setPos(pos.x(), pos.y(), pos.z());
				umbrellaEntity.getPersistentData().putInt("ownerEntity", entitypatch.getOriginal().getId());
				umbrellaEntity.getPersistentData().putInt("consumedSinking", sinkingPotency);

				entitypatch.getOriginal().hurt(DamageSource.GENERIC, 6);
				EgoWeaponsEffects.BLEED.get().increment(entitypatch.getOriginal(), 1, 2);
				if (entitypatch.getOriginal() instanceof PlayerEntity)
					SanitySystem.healSanity((PlayerEntity) entitypatch.getOriginal(), (float) sinkingPotency /2);

				entitypatch.getOriginal().level.addFreshEntity(umbrellaEntity);
				EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0,0.2), 20, EpicFightParticles.BLOOD.get(), 0.2f, "Tool_L", false);
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

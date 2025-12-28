
package net.m3tte.ego_weapons.item.fullstop_rep;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.item.guns.GunCaliber;
import net.m3tte.ego_weapons.item.guns.GunItem;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.AmmoSystem;
import net.m3tte.ego_weapons.world.capabilities.AmmoType;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class FullstopRepWeapon extends GunItem {

	private static IItemTier fullstopRepTier = new IItemTier() {

		@Override
		public int getUses() {
			return 0;
		}

		@Override
		public float getSpeed() {
			return 4.0f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 5;
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

	public FullstopRepWeapon(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_, GunCaliber caliber, int capacity) {
		super(fullstopRepTier, p_i48460_2_, p_i48460_3_, p_i48460_4_, capacity, caliber);
		}




	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("Manufactured by Atelier Logic").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 5) + 1) + "/5] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.waw"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 5) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"poise", "ammo"});
				else
					generateDescription(list, "fullstop_rep", "passive", 3);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"poise", "ammo"});
				else
					generateDescription(list,"fullstop_rep", "auto", 6);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "poise"});
				else
					generateDescription(list,"fullstop_rep", "auto_alt", 2);
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "poise", "target_marked"});
				else
					generateDescription(list,"fullstop_rep", "innate", 7);
				break;
			case 4:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "poise", "ammo", "target_marked"});
				else {
					generateDescription(list,"fullstop_rep", "ability", 8);
					list.add(new StringTextComponent("   ").withStyle(TextFormatting.GRAY));
					generateDescription(list,"fullstop_rep", "ability_alt", 4);
				}

				break;
			}

		list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
	}
	public static void interruptedAttack(LivingEntity target) {

	}



	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, target, sourceentity);
		double x = target.getX();
		double y = target.getY();
		double z = target.getZ();


		World world = target.level;

		Item chestItem = sourceentity.getItemBySlot(EquipmentSlotType.CHEST).getItem();

		PlayerVariables entityData = sourceentity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

			String weaponIdentifier;

			boolean consumesAmmo;
			boolean finale;

			if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation) {
				weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");
				consumesAmmo = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO).orElse(false);
				finale = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO).orElse(false);
			} else {
				weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");
				consumesAmmo = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO).orElse(false);
				finale = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO).orElse(false);
			}
			System.out.println("IDENTIFIER IS : "+weaponIdentifier+" - MELEE TAG IS : "+sourceentity.getItemInHand(Hand.OFF_HAND).getOrCreateTag().contains("meleeAttackTag"));

			if (consumesAmmo && !sourceentity.getItemInHand(Hand.OFF_HAND).getOrCreateTag().contains("meleeAttackTag")) {


				Hand hand = EgoWeaponsItems.FULLSTOP_REP_MACHETE.get().equals(sourceentity.getItemInHand(Hand.MAIN_HAND).getItem()) ? Hand.OFF_HAND : Hand.MAIN_HAND;

				AmmoType lastFired = AmmoType.values()[sourceentity.getItemInHand(hand).getOrCreateTag().getInt("lastFired")];
				//itemstack.getOrCreateTag().remove("lastFired");
				System.out.println("AMMO USED IS: "+ lastFired);
				if (sourceentity.level instanceof ServerWorld) {
					((ServerWorld) sourceentity.level).sendParticles(lastFired.getHitParticle(), target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), (int) 1, 0, 0, 0, 0);
					switch (lastFired) {
						case Standard:
							break;
						case Moonstone:
							EgoWeaponsEffects.SINKING.get().increment(target, finale ? 1 : 0, 1);
							break;
						case Incendiary:
							EgoWeaponsEffects.BURN.get().increment(target, finale ? 1 : 0, 1);
							break;
					}

				}
			}

			// Different animation effects
			switch (weaponIdentifier) {
				case "fullstop_rep_innate":
					target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200, 0));
					target.addEffect(new EffectInstance(Effects.WEAKNESS, 200, 0));
					break;
				case "fullstop_special_b":
				case "fullstop_special_g":
					if (!sourceentity.getItemInHand(Hand.OFF_HAND).getOrCreateTag().contains("lastFired")) {
						sourceentity.getPersistentData().putInt("lastTargetSpotted", target.getId());
						target.addEffect(new EffectInstance(EgoWeaponsEffects.TARGET_SPOTTED.get(), 120, 0));
					}
					break;
			}

		}

		return true;
	}


	public static float critDamageCalculations(LivingEntity target, LivingEntityPatch<?> sourcePatch, float multiplier, DamageSource source) {

		DynamicAnimation currentanim = sourcePatch.getServerAnimator().animationPlayer.getAnimation();

		if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

			String weaponIdentifier;

			//boolean consumesAmmo;
			//boolean finale;
			if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation) {
				weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");
				//consumesAmmo = (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO).orElse(false);
				//finale = (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO).orElse(false);
			} else {
				weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");
			}

			switch (weaponIdentifier) {
				case "fs_rep_auto3":
						multiplier += 0.1f;
						SharedFunctions.incrementBonusDamage(source, 0.1f);
					break;
				case "fullstop_rep_innate":

						if (EgoWeaponsEffects.POISE.get().getPotency(sourcePatch.getOriginal()) >= 5) {
							EgoWeaponsEffects.POISE.get().decrement(sourcePatch.getOriginal(), 0, 5);
							sourcePatch.getOriginal().getPersistentData().putInt("lastTargetSpotted", target.getId());
							target.addEffect(new EffectInstance(EgoWeaponsEffects.TARGET_SPOTTED.get(), 120, 0));
						}
				case "fs_rep_auto_4_g":
						multiplier += 0.2f;
						SharedFunctions.incrementBonusDamage(source, 0.2f);
					break;

				case "fullstop_special_b":
				case "fullstop_special_g":
						if (!sourcePatch.getOriginal().getItemInHand(Hand.OFF_HAND).getOrCreateTag().contains("lastFired")) {
							multiplier += 0.3f;
							SharedFunctions.incrementBonusDamage(source, 0.3f);
						}
					break;
			}
		}
		return multiplier;
	}

	public static StaticAnimation.Event[] fireGun(float time) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(time, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;

			if (!world.isClientSide()) {
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.CLICK,
						SoundCategory.NEUTRAL, 0.5f, (float) 0.8);
			}
			Hand hand = EgoWeaponsItems.FULLSTOP_REP_MACHETE.get().equals(entity.getItemInHand(Hand.MAIN_HAND).getItem()) ? Hand.OFF_HAND : Hand.MAIN_HAND;

			AmmoType ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(hand), entity, true);
			if (ammo == AmmoType.Standard) {
				EgoWeaponsEffects.POISE.get().increment(entity, 1, 1);
			}
			ItemStack offhandItem = entitypatch.getOriginal().getOffhandItem();
			if (offhandItem != null) {
				if (ammo != null) {
					spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.4,-0.3), 1, ammo.getFireParticle(), 0, "Tool_L", false);
					spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.4,-0.3), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_L");
				}
			}
			}, StaticAnimation.Event.Side.BOTH);
		return events;
	}


	private static void specialGunshot(LivingEntity entity, LivingEntityPatch<?> patch) {
		World world = entity.level;

		if (!world.isClientSide()) {
			world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
					EgoWeaponsSounds.CLICK,
					SoundCategory.NEUTRAL, 0.5f, (float) 0.8);
		}
		Hand hand = EgoWeaponsItems.FULLSTOP_REP_MACHETE.get().equals(entity.getItemInHand(Hand.MAIN_HAND).getItem()) ? Hand.OFF_HAND : Hand.MAIN_HAND;

		AmmoType ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(hand), entity, true);
		if (ammo == AmmoType.Standard) {
			EgoWeaponsEffects.POISE.get().increment(entity, 0, 1);
		}

		ItemStack offhandItem = entity.getOffhandItem();
		if (offhandItem != null) {
			if (ammo != null) {
				spawnArmatureParticle(patch, 0, new Vector3d(0,-0.4,-0.3), 1, ammo.getFireParticle(), 0, "Tool_L", false);
			}
		}
	}




	public static StaticAnimation.Event[] specialGun(float time1, float time2, float time3, float time4) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[5];
		events[0] = StaticAnimation.Event.create(0.5f, (entitypatch) -> {
			LivingEntity living = entitypatch.getOriginal();
			Vector3d lookV = living.getLookAngle();
			World world = living.level;
			Hand hand = EgoWeaponsItems.FULLSTOP_REP_MACHETE.get().equals(living.getItemInHand(Hand.MAIN_HAND).getItem()) ? Hand.OFF_HAND : Hand.MAIN_HAND;
			entitypatch.getOriginal().getItemInHand(hand).getOrCreateTag().remove("meleeAttackTag");

			if (!world.isClientSide())
				((World) world).playSound(null, living.blockPosition().offset(lookV.x * 1.5, lookV.y * 1.5, lookV.z * 1.5),
						(net.minecraft.util.SoundEvent) EgoWeaponsSounds.FULLSTOP_FULL_STOP_TO_LIFE_G,
						SoundCategory.PLAYERS, (float) 3, (float) 1);
		}, StaticAnimation.Event.Side.SERVER);
		events[1] = StaticAnimation.Event.create(time1, (entitypatch) -> {

			specialGunshot(entitypatch.getOriginal(), entitypatch);


		}, StaticAnimation.Event.Side.BOTH);

		events[2] = StaticAnimation.Event.create(time2, (entitypatch) -> {
			specialGunshot(entitypatch.getOriginal(), entitypatch);

		}, StaticAnimation.Event.Side.BOTH);

		events[3] = StaticAnimation.Event.create(time3, (entitypatch) -> {
			specialGunshot(entitypatch.getOriginal(), entitypatch);
		}, StaticAnimation.Event.Side.BOTH);

		events[4] = StaticAnimation.Event.create(time4, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			Hand hand = EgoWeaponsItems.FULLSTOP_REP_MACHETE.get().equals(entity.getItemInHand(Hand.MAIN_HAND).getItem()) ? Hand.OFF_HAND : Hand.MAIN_HAND;
			entitypatch.getOriginal().getItemInHand(hand).getOrCreateTag().putBoolean("meleeAttackTag", true);
			DialogueSystem.speakEvalDialogue(entity, "dialogue.ego_weapons.skills.fullstop_pistol.2", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);

			entity.getItemInHand(hand).getOrCreateTag().remove("lastFired");

		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] specialBlade(float time1, float time4) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];
		events[0] = StaticAnimation.Event.create(time1, (entitypatch) -> {
			Hand hand = EgoWeaponsItems.FULLSTOP_REP_MACHETE.get().equals(entitypatch.getOriginal().getItemInHand(Hand.MAIN_HAND).getItem()) ? Hand.OFF_HAND : Hand.MAIN_HAND;
			entitypatch.getOriginal().getItemInHand(hand).getOrCreateTag().putInt("lastFired", 0);
		}, StaticAnimation.Event.Side.SERVER);

		events[1] = StaticAnimation.Event.create(time4, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			Hand hand = EgoWeaponsItems.FULLSTOP_REP_MACHETE.get().equals(entity.getItemInHand(Hand.MAIN_HAND).getItem()) ? Hand.OFF_HAND : Hand.MAIN_HAND;
			entity.getItemInHand(hand).getOrCreateTag().remove("lastFired");

		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}


	public static StaticAnimation.Event[] slideEvent() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(0.1f, (entitypatch) -> {
			EgoWeaponsEffects.POISE.get().increment(entitypatch.getOriginal(), 2, 0);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] reloadEvent() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(0.8f, (entitypatch) -> {
			World world = entitypatch.getOriginal().level;
			world.playSound(null, entitypatch.getOriginal().blockPosition(),
                    EgoWeaponsSounds.FULLSTOP_REP_RELOAD,
					SoundCategory.PLAYERS, (float) 2, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}


}

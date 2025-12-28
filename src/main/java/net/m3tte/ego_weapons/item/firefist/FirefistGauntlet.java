
package net.m3tte.ego_weapons.item.firefist;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.gameasset.movesets.FirefistMovesetAnims;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class FirefistGauntlet extends EgoWeaponsWeapon {

	private static IItemTier liuFireGauntletTier = new IItemTier() {

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
			return 4;
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

	public FirefistGauntlet(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(liuFireGauntletTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}




	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("A gauntlet manufactured by a no-name district 12 Workshop.").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 5) + 1) + "/5] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.he"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 5) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "burn", "d10fuel"});
				else
					generateDescription(list,"firefist_gauntlet", "passive", 4);
				break;
			case 1:
                if (EgoWeaponsKeybinds.isHoldingShift())
                    generateStatusDescription(list, new String[]{"red", "burn", "d10fuel", "power_up"});
                else
                    generateDescription(list, "firefist_gauntlet", "ability", 11);
                break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "burn", "d10fuel"});
				else
					generateDescription(list,"firefist_gauntlet", "auto", 5);
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "burn", "d10fuel"});
				else {
					generateDescription(list,"firefist_gauntlet", "innate", 6);
					list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
					generateDescription(list,"firefist_gauntlet", "innateb", 3);
				}
				break;
			case 4:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "burn", "d10fuel"});
				else
					generateDescription(list,"firefist_gauntlet", "guard", 3);
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

		PlayerVariables entityData = sourceentity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();


		/*if (sourceentity.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.FIREFIST_SUIT.get())) {
			int fuel = sourceentity.getItemBySlot(EquipmentSlotType.CHEST).getOrCreateTag().getInt("d10fuel");
			sourceentity.getItemBySlot(EquipmentSlotType.CHEST).getOrCreateTag().putInt("d10fuel",fuel - 8);
		}*/




		if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

			String weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

			int fuel = getFuel(sourceentity);

			AttackAnimation.Phase phase = null;
			if (currentanim instanceof EgoAttackAnimation) {
				phase = ((EgoAttackAnimation)currentanim).getPhaseByTime(entitypatch.getAnimator().getPlayerFor(currentanim).getElapsedTime());
			}

			if (phase instanceof EgoAttackAnimation.EgoAttackPhase) {
				String elp = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER).orElse(null);

				if (elp != null)
					weaponIdentifier = elp;
			}

			switch (weaponIdentifier) {


                case "firefist_innate_b":
                    EgoWeaponsEffects.BURN.get().increment(target, 1, 2);
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.LIU_S6_AUTO_SIDE.get().getRegistryName()));
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.LIU_PUNCH_SHOCKWAVE.get().getRegistryName()));
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), 8, target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 0.05, 0.6f, 1.5f, 0.5f, 1f, 0.5f));

					break;

				case "firefist_auto3":

					fuel = getFuel(sourceentity, entityData, 15);
					if (fuel >= 15) {
						reduceFuel(sourceentity, 15, entityData, 4);
						EgoWeaponsEffects.BURN.get().increment(target, 1, 2);
						EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.LIU_PUNCH_SHOCKWAVE.get().getRegistryName()));
						EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), 8, target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 0.05, 0.6f, 1.5f, 0.5f, 1f, 0.5f));

					} else {
						EgoWeaponsEffects.BURN.get().increment(target, 0, 1);
					}



					break;
				case "firefist_counter":
					EgoWeaponsEffects.BURN.get().increment(target, 1, 1);
					break;
				case "firefist_dash":
					// Delegate to modify damage to give 15% dmg
					EgoWeaponsEffects.BURN.get().increment(target, 0, 1);
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.LIU_PUNCH_SHOCKWAVE.get().getRegistryName()));
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), 8, target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 0.05, 0.6f, 1.5f, 0.5f, 1f, 0.5f));

				break;
				case "firefist_auto1":
				case "firefist_auto2":
					EgoWeaponsEffects.BURN.get().increment(target, 0, 1);
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.LIU_S6_AUTO_SIDE.get().getRegistryName()));
				break;
                case "firefist_innate_a_final":
                case "firefist_innate_a_start":
                    EgoWeaponsEffects.BURN.get().increment(target, 1, 2);
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), 8, target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 0.05, 0.6f, 1.5f, 0.5f, 1f, 0.5f));

					break;
                case "firefist_sp1":
				case "firefist_sp2":
					if (!world.isClientSide()) {
						if (weaponIdentifier.equals("firefist_sp1"))
							world.playSound(null, new BlockPos(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()),
									EgoWeaponsSounds.FIREFIST_SPECIAL_GROAN_1,
									SoundCategory.PLAYERS, 1f, (float) 1);
						else
							world.playSound(null, new BlockPos(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()),
									EgoWeaponsSounds.FIREFIST_SPECIAL_GROAN_2,
									SoundCategory.PLAYERS, 1f, (float) 1);
					}
					SharedFunctions.forLivingEntity(target, (targetp) -> SharedFunctions.hitstunEntity(targetp, 1, false, 0));
					EgoWeaponsEffects.BURN.get().increment(target, 0, 2);
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.LIU_S6_AUTO_SIDE.get().getRegistryName()));
					entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putBoolean("ff_gauntlet_hit", true);
					break;
				case "firefist_sp3":

					EgoWeaponsEffects.BURN.get().increment(target, 3, 2);
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.LIU_S6_AUTO_SIDE.get().getRegistryName()));
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.LIU_PUNCH_SHOCKWAVE.get().getRegistryName()));
					target.setDeltaMovement(target.getDeltaMovement().add(0,0.3,0));
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), 10, target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 0.05, 0.6f, 1.5f, 0.5f, 1f, 0.5f));

					break;
			}
		}

		return true;
	}

	public static float modifyDamageAmount(LivingEntity target, LivingEntity source, float mult, DamageSource damageSource) {

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		String weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

		PlayerVariables entityData = source.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);



		if (weaponIdentifier.equals("firefist_sp1")) {
			int fuel = FirefistGauntlet.getFuel(source);

			FirefistGauntlet.reduceFuel(source, fuel);

			source.getItemBySlot(EquipmentSlotType.MAINHAND).getOrCreateTag().putInt("consumedPunchFuel",fuel);
			source.getItemBySlot(EquipmentSlotType.MAINHAND).getOrCreateTag().remove("ffkilled");

			if (fuel <= 50 || source.hasEffect(EgoWeaponsEffects.FUEL_IGNITION.get()))
				source.getItemBySlot(EquipmentSlotType.MAINHAND).getOrCreateTag().putInt("consumedHeatFuel",fuel);
			else
				source.getItemBySlot(EquipmentSlotType.MAINHAND).getOrCreateTag().remove("consumedHeatFuel");

			if (source instanceof PlayerEntity)
				((PlayerEntity)source).inventory.setChanged();
		}

		int consumedFuel = source.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("consumedPunchFuel");
		int consumedHeatFuel = source.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("consumedHeatFuel");
		int fuel = FirefistGauntlet.getFuel(source);
		if (fuel > 0 && fuel <= 50 || source.hasEffect(EgoWeaponsEffects.FUEL_IGNITION.get())) {
			SharedFunctions.incrementBonusDamage(damageSource, 0.1f);
			mult += 0.1f;
		} else if (fuel <= 0 && !weaponIdentifier.contains("firefist_sp")) {
			SharedFunctions.incrementBonusDamage(damageSource, -0.1f);
			mult -= 0.1f;
		}




		switch (weaponIdentifier) {
			case "firefist_dash": // +15% Damage if consuming fuel
				if (entityData != null) {
					int dashFuel = getFuel(source, entityData, 15);

					if (dashFuel >= 15) {
						reduceFuel(source, 15, entityData, 5);

						SharedFunctions.incrementBonusDamage(damageSource, 0.15f);
						mult += 0.15f;
					}
				}
				break;
			case "firefist_innate_a": // +20% Damage if consuming overheated fuel
				int innateFuel = getFuel(source);
				if (entityData.onHitCounter > 0)
					innateFuel += 20;

				if (innateFuel <= 50 || source.hasEffect(EgoWeaponsEffects.FUEL_IGNITION.get())) {
					SharedFunctions.incrementBonusDamage(damageSource, 0.2f);
					mult += 0.2f;
				}
				break;
			case "firefist_sp3": // +1% Damage per consumed overheated fuel, Max 50%
				if (consumedHeatFuel > 0) {
					float factor = consumedHeatFuel * (0.01f);
					SharedFunctions.incrementBonusDamage(damageSource, factor);
					mult += factor;
				}
			case "firefist_sp1": // +1% Damage per consumed fuel (doubled if in overheated fuel state) Max 125%
			case "firefist_sp2":
				if (consumedFuel > 0) {
					float factor = Math.min(consumedFuel * (consumedHeatFuel > 0 ? 0.02f : 0.01f),1.0f);
					SharedFunctions.incrementBonusDamage(damageSource, factor);
					mult += factor;
				}
				break;
		}



		return mult;
	}



	public static int getFuel(LivingEntity entity, PlayerVariables entityData, int expected) {
		if (entityData != null) {
			if (entityData.onHitCounter > 0)
				return expected;
		}
		if (!entity.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.FIREFIST_SUIT.get()))
			return 0;

		return entity.getItemBySlot(EquipmentSlotType.CHEST).getOrCreateTag().getInt("d10fuel");
	}

	public static int getFuel(LivingEntity entity) {
		if (!entity.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.FIREFIST_SUIT.get()))
			return 0;

		return entity.getItemBySlot(EquipmentSlotType.CHEST).getOrCreateTag().getInt("d10fuel");
	}
	public static void reduceFuel(LivingEntity entity, int value) {
		reduceFuel(entity, value, null, 0);
	}
	public static void reduceFuel(LivingEntity entity, int value, PlayerVariables entityData, int timeout) {

		if (entityData != null) {
			if (entityData.onHitCounter > 0)
				return;
			else {
				entityData.onHitCounter = timeout;
				entityData.syncPlayerVariables(entity);
			}
		}


		if (!entity.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.FIREFIST_SUIT.get()))
			return;

		int fuel = entity.getItemBySlot(EquipmentSlotType.CHEST).getOrCreateTag().getInt("d10fuel");

		fuel = Math.max(0, fuel-value);

		entity.getItemBySlot(EquipmentSlotType.CHEST).getOrCreateTag().putInt("d10fuel", fuel);
	}


	public static StaticAnimation.Event[] ignitionReset() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(0.1f, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ignition", 0);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}


	private static ItemStack getMaskInInv(PlayerEntity player) {
		for (ItemStack stk : player.inventory.items) {
			System.out.println("COMP : "+ stk.getItem()+" | "+EgoWeaponsItems.FIREFIST_MASK.get());
			if (stk.getItem().equals(EgoWeaponsItems.FIREFIST_MASK.get())) {
				System.out.println("RETURNING");
				return stk;
			}

		}
		return ItemStack.EMPTY;
	}

	public static StaticAnimation.Event[] firefistHitEvent(int nextAnimation, float timeStamp, SoundEvent startSound, SoundEvent voiceSound, float end) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[4];

		events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();

			if (nextAnimation == 1)
				entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 2, 0);

		}, StaticAnimation.Event.Side.BOTH);

		events[1] = StaticAnimation.Event.create(0.1f, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("ff_gauntlet_hit");

			if (startSound != null) {
				entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ignition", 1);
				if (!entitypatch.getOriginal().level.isClientSide()) {
					entitypatch.playSound(startSound, 1, 1, 1);
				}

			} else {
				entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ignition", 0);
			}

			if (nextAnimation == 0 && entitypatch.getOriginal() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) entitypatch.getOriginal();

				ItemStack mask = getMaskInInv(player);
				if ((!mask.isEmpty()) && player.getItemBySlot(EquipmentSlotType.HEAD).isEmpty()) {
					player.inventory.removeItem(mask);
					player.setItemSlot(EquipmentSlotType.HEAD, mask);
					player.inventory.setChanged();
				}
			}



			if (voiceSound != null && !entitypatch.getOriginal().level.isClientSide())
				entitypatch.playSound(voiceSound, 0.5f, 1, 1);
		}, StaticAnimation.Event.Side.BOTH);

		events[2] = StaticAnimation.Event.create(timeStamp, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();

			if (entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().contains("ff_gauntlet_hit")) {
				switch (nextAnimation) {
					case 0:
						//entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
						entity.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 20, 0));
						if (!entitypatch.getOriginal().level.isClientSide())
							entitypatch.playAnimationSynchronized(FirefistMovesetAnims.FIREFIST_SPECIAL_2, 0);
						break;
					case 1:
						entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
						entity.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 20, 0));
						if (!entitypatch.getOriginal().level.isClientSide())
							entitypatch.playAnimationSynchronized(FirefistMovesetAnims.FIREFIST_SPECIAL_3, 0);
						break;
				}
			}


		}, StaticAnimation.Event.Side.BOTH);

		events[3] = StaticAnimation.Event.create(end, (entitypatch) -> {

			if (entitypatch.getOriginal() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) entitypatch.getOriginal();

				ItemStack mask = player.getItemBySlot(EquipmentSlotType.HEAD);
				if (!mask.isEmpty()) {
					player.setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
					player.inventory.add(mask);
					player.inventory.setChanged();
				}
			}

			if (entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().contains("ffkilled") && !entitypatch.getOriginal().level.isClientSide()) {
				entitypatch.playSound(EgoWeaponsSounds.FIREFIST_SPECIAL_KILL, 1f, 1, 1);
			}
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] firefistFinalHitEvent(int nextAnimation, float timeStamp, float end) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[4];

		events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();

			entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 2, 0);


		}, StaticAnimation.Event.Side.BOTH);

		events[1] = StaticAnimation.Event.create(0.1f, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("ff_gauntlet_hit");
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ignition", 1);
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("ffkilled");

			if (!entitypatch.getOriginal().level.isClientSide()) {
				entitypatch.playSound(EgoWeaponsSounds.FIREFIST_SPECIAL_FLARE, 1.5f, 1, 1);
			}



			if (!entitypatch.getOriginal().level.isClientSide())
				entitypatch.playSound(EgoWeaponsSounds.FIREFIST_SPECIAL_YELL, 0.8f, 1, 1);
		}, StaticAnimation.Event.Side.BOTH);

		events[2] = StaticAnimation.Event.create(timeStamp, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ignition", 0);
		}, StaticAnimation.Event.Side.BOTH);

		events[3] = StaticAnimation.Event.create(end, (entitypatch) -> {

			if (entitypatch.getOriginal() instanceof PlayerEntity && !((PlayerEntity) entitypatch.getOriginal()).hasEffect(EgoWeaponsEffects.FUEL_IGNITION.get())) {
				PlayerEntity player = (PlayerEntity) entitypatch.getOriginal();

				ItemStack mask = player.getItemBySlot(EquipmentSlotType.HEAD);
				if (!mask.isEmpty()) {
					player.setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
					player.inventory.add(mask);
					player.inventory.setChanged();
				}
			}

			if (entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().contains("ffkilled")) {

				DialogueSystem.speakEvalDialogue(entitypatch.getOriginal(), "dialogue.ego_weapons.skills.firefist.1", DialogueSystem.DialogueTypes.SKILL, TextFormatting.RED);

				if (!entitypatch.getOriginal().level.isClientSide())
					entitypatch.playSound(EgoWeaponsSounds.FIREFIST_SPECIAL_KILL, 1f, 1, 1);
			}
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] fireCounter() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(0.1f, (entitypatch) -> {
			Entity entity = entitypatch.getOriginal();
			entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);

		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}


	public static StaticAnimation.Event[] spewFireAnimation() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[4];
		events[0] = StaticAnimation.Event.create(0.33f, (entitypatch) -> {
			if (getFuel(entitypatch.getOriginal()) < 20) {
				if (!entitypatch.getOriginal().level.isClientSide())
					entitypatch.playAnimationSynchronized(FirefistMovesetAnims.FIREFIST_INNATE_B, 0);

				Entity entity = entitypatch.getOriginal();
				entitypatch.getOriginal().addEffect(new EffectInstance(EgoWeaponsEffects.RESILIENCE.get(), 20, 0));
			}
			if (entitypatch.getOriginal().level.isClientSide())
				entitypatch.playSound(EgoWeaponsSounds.FIREFIST_CLICK, 1, 1, 1);
		}, StaticAnimation.Event.Side.BOTH);

		events[1] = StaticAnimation.Event.create(0.9f, (entitypatch) -> {

			reduceFuel(entitypatch.getOriginal(), 20);
			Entity entity = entitypatch.getOriginal();
			spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0,0), 1, EgoWeaponsParticles.FIREFIST_FLAME_SPEW.get(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");

			for (int i = 0; i < 30; i++) {
				spawnArmatureParticle(entitypatch, 0, new Vector3d(random.nextFloat() - 0.5f,0f - random.nextFloat() * 5,random.nextFloat() - 0.5f), 1, EgoWeaponsParticles.SIMPLE_EMBER.get(), new Vector3f(0.05f, 0.5f, 1.5f), "Tool_R");
			}

			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ignition", 3);
		}, StaticAnimation.Event.Side.BOTH);

		events[2] = StaticAnimation.Event.create(1.2f, (entitypatch) -> {

			Entity entity = entitypatch.getOriginal();

			for (int i = 0; i < 30; i++) {
				spawnArmatureParticle(entitypatch, 0, new Vector3d(random.nextFloat() - 0.5f,0f - random.nextFloat() * 5,random.nextFloat() - 0.5f), 1, EgoWeaponsParticles.SIMPLE_EMBER.get(), new Vector3f(0.05f, 0.5f, 1.5f), "Tool_R");

			}
		}, StaticAnimation.Event.Side.BOTH);


		events[3] = StaticAnimation.Event.create(2.4f, (entitypatch) -> {

			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ignition", 0);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}


	public static StaticAnimation.Event[] reloadEvent() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(0.8f, (entitypatch) -> {
			World world = entitypatch.getOriginal().level;
			world.playSound(null, entitypatch.getOriginal().blockPosition(),
					EgoWeaponsSounds.FIREFIST_CLICK,
					SoundCategory.PLAYERS, (float) 2, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] ignitionEvent() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[3];
		events[0] = StaticAnimation.Event.create(0.1f, (entitypatch) -> {
			World world = entitypatch.getOriginal().level;
			world.playSound(null, entitypatch.getOriginal().blockPosition(),
					EgoWeaponsSounds.FIREFIST_IGNITION_TRIGGER,
					SoundCategory.PLAYERS, (float) 1, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		events[1] = StaticAnimation.Event.create(0.6f, (entitypatch) -> {
			World world = entitypatch.getOriginal().level;
			world.playSound(null, entitypatch.getOriginal().blockPosition(),
					EgoWeaponsSounds.FIREFIST_CLICK,
					SoundCategory.PLAYERS, (float) 1, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		events[2] = StaticAnimation.Event.create(1.4f, (entitypatch) -> {
			World world = entitypatch.getOriginal().level;
			world.playSound(null, entitypatch.getOriginal().blockPosition(),
					EgoWeaponsSounds.FIREFIST_SPECIAL_FLARE,
					SoundCategory.PLAYERS, (float) 2, (float) 1);
			entitypatch.getOriginal().addEffect(new EffectInstance(EgoWeaponsEffects.FUEL_IGNITION.get(), 600, 0));
			EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(entitypatch.getOriginal(), 0, 3);
			EgoWeaponsEffects.DEFENSE_LEVEL_DOWN.get().increment(entitypatch.getOriginal(), 0, 3);
			EgoWeaponsEffects.BURN.get().increment(entitypatch.getOriginal(), 2, 1);

			if (entitypatch.getOriginal() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) entitypatch.getOriginal();

				ItemStack mask = getMaskInInv(player);
				if ((!mask.isEmpty()) && player.getItemBySlot(EquipmentSlotType.HEAD).isEmpty()) {
					player.inventory.removeItem(mask);
					player.setItemSlot(EquipmentSlotType.HEAD, mask);
					player.inventory.setChanged();
				}
			}

			world.playSound(null, entitypatch.getOriginal().blockPosition(),
					EgoWeaponsSounds.FIREFIST_SPECIAL_GROAN_1,
					SoundCategory.PLAYERS, (float) 2, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}
}

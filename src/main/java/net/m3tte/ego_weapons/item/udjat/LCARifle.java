
package net.m3tte.ego_weapons.item.udjat;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.gameasset.movesets.FullstopOfficeSniperMovesetAnims;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.item.guns.GunCaliber;
import net.m3tte.ego_weapons.item.guns.GunItem;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.potion.countEffects.TremorEffect;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.procedures.TooltipFuncs;
import net.m3tte.ego_weapons.world.capabilities.AmmoSystem;
import net.m3tte.ego_weapons.world.capabilities.AmmoType;
import net.m3tte.ego_weapons.world.capabilities.UtilitySystems;
import net.m3tte.ego_weapons.world.capabilities.UtilitySystems.EGOAttackContext;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Arrays;
import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.*;
import static net.m3tte.ego_weapons.world.capabilities.AmmoType.executeDefaultAmmoEffect;
import static net.m3tte.ego_weapons.world.capabilities.UtilitySystems.generateAttackContext;

public class LCARifle extends GunItem {

	private static IItemTier fullstopSniperTier = new IItemTier() {

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

	public LCARifle(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_, GunCaliber caliber, int capacity) {
		super(fullstopSniperTier, p_i48460_2_, p_i48460_3_, p_i48460_4_, capacity, caliber);
	}




	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		TooltipFuncs.generateItemDescription(list, "desc.ego_weapons.lca_rifle.desc");
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		appendAmmoDialogueLine(list);
		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 8) + 1) + "/8] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.2"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 8) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"ammo", "offense_up"});
				else
					generateDescription(list, "lca_rifle", "reload", 3);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"tremor", "ammo", "offense_up"});
				else
					generateDescription(list, "lca_rifle", "passive", 4);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"protection"});
				else
					generateDescription(list, "lca_rifle", "passive2", 2);
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"ammo", "tremor", "protection"});
				else
					generateDescription(list,"lca_rifle", "auto", 7);
				break;
			case 4:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"white", "protection", "tremor"});
				else
					generateDescription(list,"lca_rifle", "auto_m", 4);
				break;
			case 5:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"protection", "speed", "ammo", "tremor"});
				else
					generateDescription(list,"lca_rifle", "innate", 9);
				break;
			case 6:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"speed", "udjat_vanguard", "ammo", "tremor", "speed_down", "white_fragility"});
				else {
					generateDescription(list,"lca_rifle", "ability", 12);
				}
				break;
			case 7:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"speed", "udjat_vanguard", "ammo", "tremor", "speed_down", "white_fragility"});
				else {
					generateDescription(list,"lca_rifle", "o_ability", 9);
				}
				break;
		}

		generateStatusHelp(list);
		generateOffhandHelp(list);
	}

	public static void interruptedAttack(LivingEntity target) {

	}


	@Override
	public String getDefaultKillIdentifier() {
		return "gun";
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, target, sourceentity);

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		EGOAttackContext context = generateAttackContext(entitypatch);

		LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		if (context.isAmmoSkill()) {
			Hand hand = EgoWeaponsItems.LCA_RIFLE.get().equals(sourceentity.getItemInHand(Hand.MAIN_HAND).getItem()) ? Hand.MAIN_HAND : Hand.OFF_HAND;

			AmmoType lastFired = AmmoType.values()[sourceentity.getItemInHand(hand).getOrCreateTag().getInt("lastFired")];
			//itemstack.getOrCreateTag().remove("lastFired");

			// executeDefaultAmmoEffect( lastFired, context.isFinalCoin(), sourceentity, target);
		}


		if (context.isValidEgoAnimation()) {


			switch (context.getAnimationIdentifier()) {
				case "lca_rifle_sp_1":
					SharedFunctions.hitstunEntity(targetPatch, 1, false, 0);
					break;
				case "lca_rifle_sp_2":
					SharedFunctions.staggerEntity(targetPatch, 1, false);
					System.out.println("SETTING TARGET ID TO : "+target.getId());
					sourceentity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("specialHitEntity", target.getId());
					sourceentity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().putDouble("oldPosX", sourceentity.getX());
					sourceentity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().putDouble("oldPosY", sourceentity.getY());
					sourceentity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().putDouble("oldPosZ", sourceentity.getZ());
					break;

				case "lca_rifle_sp_b_1":
					SharedFunctions.hitstunEntity(targetPatch, 1, false, 0);
					break;
				case "lca_rifle_sp_b_2":
					SharedFunctions.staggerEntity(targetPatch, 1, false);
					System.out.println("SETTING TARGET ID TO : "+target.getId());
					sourceentity.getItemInHand(Hand.OFF_HAND).getOrCreateTag().putInt("specialHitEntity", target.getId());
					sourceentity.getItemInHand(Hand.OFF_HAND).getOrCreateTag().putDouble("oldPosX", sourceentity.getX());
					sourceentity.getItemInHand(Hand.OFF_HAND).getOrCreateTag().putDouble("oldPosY", sourceentity.getY());
					sourceentity.getItemInHand(Hand.OFF_HAND).getOrCreateTag().putDouble("oldPosZ", sourceentity.getZ());
					break;
			}

		}

		return true;
	}




	public static float modifyDamageAmount(LivingEntity target, LivingEntity source, float mult, DamageSource damageSource) {

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		EGOAttackContext context = generateAttackContext(entitypatch);



		if (context.isValidEgoAnimation()) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));


			LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
			int speedDiff = Math.max(0, EgoWeaponsEffects.speedMult(source) - EgoWeaponsEffects.speedMult(target));

			// mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.30f, 0.03f * sinkingOnTarget));
			// mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.35f, 0.07f * protectionOnSelf));

			if (context.isAmmoSkill()) {
				Hand hand = EgoWeaponsItems.LCA_RIFLE.get().equals(source.getItemInHand(Hand.MAIN_HAND).getItem()) ? Hand.MAIN_HAND : Hand.OFF_HAND;

				AmmoType lastFired = AmmoType.values()[source.getItemInHand(hand).getOrCreateTag().getInt("lastFired")];

				if (source.level instanceof ServerWorld) {
					((ServerWorld) source.level).sendParticles(lastFired.getHitParticle(), target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), (int) 1, 0, 0, 0, 0);
				}
				executeDefaultAmmoEffect( lastFired, context.isFinalCoin(), source, target);
			}

			switch (context.getAnimationIdentifier()) {

				case "lca_rifle_sp_b_1":
				case "lca_rifle_sp_b_2":
					if (source.hasEffect(EgoWeaponsEffects.UDJAT_VANGUARD.get()))
						mult += SharedFunctions.incrementBonusDamage(damageSource, 0.30f);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.2f, 0.04f * speedDiff));
					EgoWeaponsEffects.TREMOR.get().increment(target, 1, 0);
					EgoWeaponsEffects.SINKING.get().increment(target, 2, 0);
					break;


				case "lca_rifle_sp_b_5":
					if (source.hasEffect(EgoWeaponsEffects.UDJAT_VANGUARD.get()))
						mult += SharedFunctions.incrementBonusDamage(damageSource, 0.30f);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.2f, 0.04f * speedDiff));
					EgoWeaponsEffects.TREMOR.get().increment(target, 0, 1);
					TremorEffect.burstTremor(target, true);
					break;
				case "lca_rifle_auto_2_g":
                case "autom_1":
                    EgoWeaponsEffects.TREMOR.get().increment(target, 0, 1);
					break;

				case "lca_rifle_auto_3_g":
					EgoWeaponsEffects.TREMOR.get().increment(target, 1, 1);
					break;

				case "lca_rifle_innate_1":
					EgoWeaponsEffects.TREMOR.get().increment(target, 0, 1);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.2f, 0.04f * speedDiff));
					break;

				case "lca_rifle_innate_2":
					mult += SharedFunctions.incrementBonusDamage(damageSource, 0.30f);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.2f, 0.04f * speedDiff));
					EgoWeaponsEffects.TREMOR.get().increment(target, 0, 1);
					break;

				case "lca_rifle_innate_3":
					mult += SharedFunctions.incrementBonusDamage(damageSource, 0.60f);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.2f, 0.04f * speedDiff));
					EgoWeaponsEffects.TREMOR.get().increment(target, 0, 1);
					TremorEffect.burstTremor(target, true);
					break;

				case "lca_rifle_sp_o_1":
					EgoWeaponsEffects.TREMOR.get().increment(target, 0, 1);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.2f, 0.04f * speedDiff));
					break;

				case "lca_rifle_sp_o_2":
					if (source.hasEffect(EgoWeaponsEffects.UDJAT_VANGUARD.get()))
						mult += SharedFunctions.incrementBonusDamage(damageSource, 0.30f);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.2f, 0.04f * speedDiff));
					EgoWeaponsEffects.TREMOR.get().increment(target, 0, 1);
					break;

				case "lca_rifle_sp_o_3":
					if (source.hasEffect(EgoWeaponsEffects.UDJAT_VANGUARD.get()))
						mult += SharedFunctions.incrementBonusDamage(damageSource, 0.30f);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.2f, 0.04f * speedDiff));
					EgoWeaponsEffects.TREMOR.get().increment(target, 0, 1);
					TremorEffect.burstTremor(target, true);
					break;

				case "lca_rifle_sp_1":
				case "lca_rifle_sp_2":
					EgoWeaponsEffects.TREMOR.get().increment(target, 1, 0);
					break;
                case "autom_2":
					EgoWeaponsEffects.TREMOR.get().increment(target, 2, 1);
					break;

				case "lca_rifle_sp_3":
					TremorEffect.burstTremor(target, true);
					EgoWeaponsEffects.SPEED_DOWN.get().increment(target, 0, 2);
					EgoWeaponsEffects.WHITE_FRAGILITY.get().increment(target, 0, 1);
					break;
			}
		}

		return mult;
	}


	@Override
	public void inventoryTick(ItemStack stack, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {

		int charge = stack.getOrCreateTag().getInt("charge");
		if (charge > 0 && charge < 30) {
			stack.getOrCreateTag().putInt("charge", stack.getOrCreateTag().getInt("charge") + 1);
		} else if (charge >= 30)
			stack.getOrCreateTag().remove("charge");

		int dropped = stack.getOrCreateTag().getInt("dropped");
		if (dropped > 0 && dropped < 90) {
			stack.getOrCreateTag().putInt("dropped", stack.getOrCreateTag().getInt("dropped") + 1);
		} else if (dropped >= 90)
			stack.getOrCreateTag().remove("dropped");


		super.inventoryTick(stack, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
	}

	public static StaticAnimation.Event[] fireFSRailgunSpecial(float chargeTime, float time) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[7];
		events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;
			if (!world.isClientSide())
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_START,
						SoundCategory.PLAYERS, 1f, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		events[1] = StaticAnimation.Event.create(0.6f, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("dropped", 1);
			if (!world.isClientSide())
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_FLIP,
						SoundCategory.PLAYERS, 1f, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		events[2] = StaticAnimation.Event.create(1.25f, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;
			if (!world.isClientSide())
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_SPECIAL_START,
						SoundCategory.PLAYERS, 1f, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		events[3] = StaticAnimation.Event.create(1.55f, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;

			int[] retAmmo = AmmoSystem.loadLimitedFromRight(entity.getItemInHand(Hand.MAIN_HAND), null, entity, 1);

			System.out.println("RETAMMO RESULT: "+ Arrays.toString(retAmmo));

			if (!world.isClientSide() && retAmmo[1] > 0)
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_RELOAD,
						SoundCategory.PLAYERS, 1f, (float) 1);

			if (retAmmo[0] == 0)
				entitypatch.playAnimationSynchronized(FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_IDLE, 0.01f);
		}, StaticAnimation.Event.Side.BOTH);
		events[4] = StaticAnimation.Event.create(chargeTime, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("charge", 1);
			if (!world.isClientSide())
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_SPECIAL_CHARGE,
						SoundCategory.PLAYERS, 1f, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		events[5] = StaticAnimation.Event.create(time, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;

			if (!world.isClientSide()) {
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.CLICK,
						SoundCategory.PLAYERS, 0.5f, (float) 0.8);
			}

			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("charge", 0);
			AmmoType ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.MAIN_HAND), entity, true);

			if (ammo != null) {
				if (!ammo.hasEffect()) {
					EgoWeaponsEffects.POISE.get().increment(entity, 0, 1);
				}

				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireParticle(), 0, "Tool_R", false);
				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getShockwaveParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");

			}
		}, StaticAnimation.Event.Side.BOTH);
		events[6] = StaticAnimation.Event.create(time + 0.3f, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;
			if (!world.isClientSide())
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_AFTER,
						SoundCategory.PLAYERS, 1f, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] fireFSRailgun(float time, boolean finalB) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(time, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;

			if (!world.isClientSide()) {
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.CLICK,
						SoundCategory.NEUTRAL, 0.5f, (float) 0.8);
			}

			AmmoType ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.MAIN_HAND), entity, true);

			if (ammo != null) {

				int finalAdd = finalB ? 1 : 0;

				EgoWeaponsEffects.POISE.get().increment(entity, 1, ammo.hasEffect() ? finalAdd : 1 + finalAdd);

				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireParticle(), 0, "Tool_R", false);
				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
			}



			}, StaticAnimation.Event.Side.BOTH);
		return events;
	}


	public static StaticAnimation.Event[] fireFSRailgunInnate(float time) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[5];
		events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;



			if (!world.isClientSide())
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_START,
						SoundCategory.PLAYERS, 1f, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		events[1] = StaticAnimation.Event.create(0.3f, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;

			EgoWeaponsEffects.POISE.get().increment(entitypatch.getOriginal(), 0, 2);

			if (!world.isClientSide())
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_FLIP,
						SoundCategory.PLAYERS, 1f, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		events[2] = StaticAnimation.Event.create(0.5f, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("charge", 1);
			if (!world.isClientSide())
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_CHARGE,
						SoundCategory.PLAYERS, 1f, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		events[3] = StaticAnimation.Event.create(time, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;

			if (!world.isClientSide()) {
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.CLICK,
						SoundCategory.PLAYERS, 0.5f, (float) 0.8);
			}

			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("charge", 0);
			AmmoType ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.MAIN_HAND), entity, true);
			if (ammo != null) {
				if (!ammo.hasEffect()) {
					EgoWeaponsEffects.POISE.get().increment(entity, 0, 1);
				}

				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireParticle(), 0, "Tool_R", false);
				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getShockwaveParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");

			}
		}, StaticAnimation.Event.Side.BOTH);
		events[4] = StaticAnimation.Event.create(time + 0.3f, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;
			if (!world.isClientSide())
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_AFTER,
						SoundCategory.PLAYERS, 1f, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}






	public static StaticAnimation.Event[] fireFSRailgunFirst(float time) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];
		events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
					LivingEntity entity = entitypatch.getOriginal();
					World world = entity.level;
					if (!world.isClientSide()) {
						world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
								EgoWeaponsSounds.FULLSTOP_SNIPER_AUTO_START,
								SoundCategory.NEUTRAL, 1f, (float) 1);
					}
				}, StaticAnimation.Event.Side.BOTH);
		events[1] = StaticAnimation.Event.create(time, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;

			if (!world.isClientSide()) {
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.CLICK,
						SoundCategory.NEUTRAL, 0.5f, (float) 0.8);
			}

			AmmoType ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.MAIN_HAND), entity, true);

			if (ammo != null) {

				if (!ammo.hasEffect()) {
					EgoWeaponsEffects.POISE.get().increment(entity, 0, 1);
				}


				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireParticle(), 0, "Tool_R", false);
				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
			}
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


package net.m3tte.ego_weapons.item.rat;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.gameasset.movesets.OeufiAssocMovesetAnims;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.potion.OrlandoPotionEffect;
import net.m3tte.ego_weapons.potion.countEffects.TremorEffect;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class RatPipe extends EgoWeaponsWeapon {

	private static IItemTier ratTier = new IItemTier() {

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

	public RatPipe(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(ratTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}

	@Override
	public String getDefaultKillIdentifier() {
		return "rat_pipe";
	}

	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("A crude pipe... good enough?").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 6) + 1) + "/6] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.nuun"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 6) {
			case 0:
                if (EgoWeaponsKeybinds.isHoldingShift())
                    generateStatusDescription(list, new String[]{"tremor", "defense_up"});
                else
                    generateDescription(list, "rat_pipe", "passive", 4);
                break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "tremor"});
				else {
					int len = 7;
					if (Minecraft.getInstance().player == null) {
						len = 6;
					} else if (!Minecraft.getInstance().player.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.SUIT_OF_THE_BLACK_SILENCE.get())) {
						len = 6;
					}
					generateDescription(list,"rat_pipe", "ability", len);
				}
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "tremor"});
				else
					generateDescription(list,"rat_pipe", "innate", 5);
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "tremor"});
				else
					generateDescription(list,"rat_pipe", "auto", 4);
				break;
			case 4:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "haste", "tremor"});
				else
					generateDescription(list,"rat_pipe", "dash", 2);
				break;
			case 5:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"tremor", "defense_up"});
				else
					generateDescription(list,"rat_pipe", "guard", 5);
		}

		list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
	}


	@Override
	public void inventoryTick(ItemStack item, World p_77663_2_, Entity entity, int p_77663_4_, boolean p_77663_5_) {
		super.inventoryTick(item, p_77663_2_, entity, p_77663_4_, p_77663_5_);

		if (entity instanceof PlayerEntity && entity.tickCount % 20 == 0) {
			PlayerEntity plr = (PlayerEntity) entity;
			PlayerPatch<?> patch = (PlayerPatch<?>) plr.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

			if (patch == null)
				return;

			if (patch.getStamina() > patch.getMaxStamina() / 2) {
				item.getOrCreateTag().putBoolean("canKickback", true);
			}
		}
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

		LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);


		if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

			String attackIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

			// Different animation effects
			switch (attackIdentifier) {
				case "rat_pipe_sp_1":
					SharedFunctions.hitstunEntity(targetPatch, 3, false, 0.5f);
					EgoWeaponsEffects.TREMOR.get().increment(target, 0, 2);
					itemstack.getOrCreateTag().putInt("followUpHit", 1);
					break;
				case "rat_pipe_sp_2":
					itemstack.getOrCreateTag().putInt("followUpHit", 1);
					SharedFunctions.pummelDownEntity(targetPatch, 3);
					break;
				case "rat_pipe_sp_3":
					TremorEffect.burstTremor(target, true);
					TremorEffect.burstTremor(sourceentity, true);

					if (sourceentity.hasEffect(OrlandoPotionEffect.potion)) {
						System.out.println("PLAYING RAT PIPE FALLING SOUND NOW");
						entitypatch.playSound(EgoWeaponsSounds.RAT_PIPE_FALLING, 1, 1);
					}
					break;
				case "rat_pipe_auto2":
					EgoWeaponsEffects.TREMOR.get().increment(target, 0, 1);
					break;
				case "rat_pipe_auto3":
					EgoWeaponsEffects.TREMOR.get().increment(target, 1, 1);
					EgoWeaponsEffects.TREMOR.get().increment(sourceentity, 1, 0);
					break;
				case "rat_pipe_dash":
					EgoWeaponsEffects.SPEED_UP.get().increment(sourceentity, 1, 1);

					TremorEffect targetTremorFX = TremorEffect.detectTremorType(target);

					int targetTremor = targetTremorFX != null ? targetTremorFX.getPotency(target) : 0;

					if (targetTremor < 3) {
						EgoWeaponsEffects.TREMOR.get().increment(sourceentity, 0, 1);
					}

					break;
				case "rat_pipe_innate":
					EgoWeaponsEffects.TREMOR.get().increment(target, 2, 1);
					EgoWeaponsEffects.TREMOR.get().increment(sourceentity, 2, 0);
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


		TremorEffect selfTremorFX = TremorEffect.detectTremorType(source);
		int selfTremor = selfTremorFX != null ? selfTremorFX.getCount(source) : 0;

		TremorEffect targetTremorFX = TremorEffect.detectTremorType(target);
		int targetTremor = targetTremorFX != null ? targetTremorFX.getPotency(target) : 0;

		System.out.println("SELF TREMOR IS : "+selfTremor);

		if (selfTremor > 0) {
			SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.2f, 0.03f * selfTremor));
			mult += Math.min(0.2f, 0.03f * selfTremor);
		}

		switch (weaponIdentifier) {
			case "rat_pipe_innate":
				if (targetTremor > 5 || StaggerSystem.isStaggered(target)) {
					SharedFunctions.incrementBonusDamage(damageSource, 0.25f);
					mult += 0.25f;
				}
				break;
			case "rat_pipe_sp_2":
				SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.2f, 0.02f * targetTremor));
				mult += Math.min(0.2f, 0.02f * targetTremor);
				break;
			case "rat_pipe_sp_3":
				SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.5f, 0.05f * targetTremor));
				mult += Math.min(0.5f, 0.05f * targetTremor);

				if (source.hasEffect(OrlandoPotionEffect.potion)) {
					SharedFunctions.incrementBonusDamage(damageSource, 0.5f);
					mult += 0.5f;

				}

				break;
		}



		return mult;
	}


	public static void onStagger(LivingEntity self) {
		EgoWeaponsEffects.TREMOR.get().increment(self, 3, 0);

		TremorEffect selfTremorFX = TremorEffect.detectTremorType(self);
		int selfTremor = selfTremorFX != null ? selfTremorFX.getCount(self) : 0;

		if (selfTremor >= 3) {
			EgoWeaponsEffects.DEFENSE_LEVEL_UP.get().increment(self, 0, Math.min(3,selfTremor / 3));
		}

	}

	public static StaticAnimation.Event[] counterEvent1() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(0.55f, (entitypatch) -> {
			entitypatch.playAnimationSynchronized(OeufiAssocMovesetAnims.OEUFI_PARRY_COUNTER_ATTACK, 0);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}



}

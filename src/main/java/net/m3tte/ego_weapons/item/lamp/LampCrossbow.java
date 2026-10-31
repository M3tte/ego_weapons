
package net.m3tte.ego_weapons.item.lamp;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.client.util.descriptionHandler.EmptyDescriptionPage;
import net.m3tte.ego_weapons.gameasset.AttackLogicPredicate;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.gameasset.movesets.LampCrossbowMovesetAnims;
import net.m3tte.ego_weapons.client.util.descriptionHandler.SkillDescriptionPage;
import net.m3tte.ego_weapons.item.guns.GunCaliber;
import net.m3tte.ego_weapons.item.guns.GunItem;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.potion.countEffects.CountPotencyStatus;
import net.m3tte.ego_weapons.potion.countEffects.EffectLike;
import net.m3tte.ego_weapons.procedures.DelayedEvent;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.procedures.TooltipFuncs;
import net.m3tte.ego_weapons.world.capabilities.UtilitySystems;
import net.m3tte.ego_weapons.world.capabilities.damage.DirectEgoDamageSource;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.*;
import static net.m3tte.ego_weapons.world.capabilities.UtilitySystems.generateAttackContext;


public class LampCrossbow extends GunItem {

	@Override
	public String getCaliberIdentifier() {
		return "desc.ego_weapons.ammo.feather_wick_bolt";
	}

	@Override
	public void setupWeaponDescription() {
		super.setupWeaponDescription();
		this.tierText = "desc.ego_weapons.risk.waw";
		this.identifier = "lamp_crossbow";

		addPage(new SkillDescriptionPage(EmptyDescriptionPage.PAGE_TYPE.SPECIAL, this.identifier, "ability", 16,
				new String[]{"black", "burn", "sinking", "feather_wick_bolt","lamp","black_fragility"},
				EgoWeaponsEffects.LAMP.get(), EgoWeaponsEffects.DAZZLE.get()));

		addPage(new SkillDescriptionPage(EmptyDescriptionPage.PAGE_TYPE.INNATE, this.identifier, "innate", 8,
				new String[]{"black", "burn", "sinking", "feather_wick_bolt","lamp","black_fragility"},
				EgoWeaponsEffects.LAMP.get()));

		addPage(new SkillDescriptionPage(EmptyDescriptionPage.PAGE_TYPE.AUTO, this.identifier, "auto2", 10,
				new String[]{"black", "burn", "sinking", "feather_wick_bolt","lamp"},
				EgoWeaponsEffects.LAMP.get()));

		addPage(new SkillDescriptionPage(EmptyDescriptionPage.PAGE_TYPE.AUTO, this.identifier, "auto", 4,
				new String[]{"black", "burn", "sinking", "lamp"},
				EgoWeaponsEffects.LAMP.get()));

		addPage(new SkillDescriptionPage(EmptyDescriptionPage.PAGE_TYPE.PASSIVE, this.identifier, "passive2", 2,
				new String[]{"burn", "sinking", "lamp"}, true,
				EgoWeaponsEffects.DAZZLE.get()));

		addPage(new SkillDescriptionPage(EmptyDescriptionPage.PAGE_TYPE.PASSIVE, this.identifier, "passive", 6,
				new String[]{"offense_up","lamp","dazzle"}, true,
				EgoWeaponsEffects.LAMP.get(), EgoWeaponsEffects.DAZZLE.get()));
	}

	@Override
	public EffectLike[] getKeywords() {
		return new EffectLike[]{EgoWeaponsEffects.BURN.get(), EgoWeaponsEffects.SINKING.get(), EgoWeaponsEffects.LAMP.get(), EgoWeaponsEffects.DAZZLE.get()};
	}

	//@Override
	public void appendHoverTexta(@NotNull ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		TooltipFuncs.generateItemDescription(list, "desc.ego_weapons.magic_bullet.desc");
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 6) + 1) + "/6] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.waw"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage()) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"burn", "sinking", "feather_wick_bolt","lamp","black_fragility"});
				else
					generateDescription(list,"lamp_crossbow", "ability", 16, EgoWeaponsEffects.LAMP.get(), EgoWeaponsEffects.DAZZLE.get());
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"burn", "sinking", "feather_wick_bolt","lamp","black_fragility"});
				else
					generateDescription(list,"lamp_crossbow", "innate", 8, EgoWeaponsEffects.LAMP.get());
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"burn", "sinking", "feather_wick_bolt","lamp"});
				else {
					generateDescription(list,"lamp_crossbow", "auto2", 10, EgoWeaponsEffects.LAMP.get());
				}
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"burn", "sinking", "lamp"});
				else
					generateDescription(list,"lamp_crossbow", "auto", 4, EgoWeaponsEffects.LAMP.get());
				break;
			case 4:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"burn","sinking","dazzle"});
				else
					generateDescription(list,"lamp_crossbow", "passive2", 2, true, EgoWeaponsEffects.DAZZLE.get());
				break;
			case 5:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"offense_up","lamp","dazzle"});
				else
					generateDescription(list,"lamp_crossbow", "passive", 6, true, EgoWeaponsEffects.LAMP.get(), EgoWeaponsEffects.DAZZLE.get());
				break;
			default:
				EgoWeaponsKeybinds.setUiPage(0);
				break;
		}

		generateStatusHelp(list);
	}


	public static float damageMultiplier(LivingEntity target, LivingEntity sourceentity, float multiplier, DamageSource source) {
		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);



		if (entitypatch == null)
			return multiplier;

		if (entitypatch.getServerAnimator() == null)
			return multiplier;

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation) {

			AttackLogicPredicate type = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.LOGIC_PREDICATE).orElse(AttackLogicPredicate.DEFAULT);

			if (type.equals(AttackLogicPredicate.MAGIC_BULLET_FIRE)) {

				int burnOnTarget = EgoWeaponsEffects.BURN.get().getPotency(target);

				multiplier += SharedFunctions.incrementBonusDamage(source, Math.min(1,0.05f * burnOnTarget));


				int potency = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entitypatch.getOriginal());

				if (potency == 0)
					potency = 7;

				multiplier += SharedFunctions.incrementBonusDamage(source, 0.20f * potency);

				float amountPredicate = (0.02f * Math.min(15, EgoWeaponsEffects.BURN.get().getPotency(target)));

				SharedFunctions.incrementBonusDamage(source, amountPredicate);

				multiplier += 1 + amountPredicate;
			}
		}
		return multiplier;
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity source){
		boolean retval = super.hurtEnemy(itemstack, target, source);

		EgoWeaponsModVars.PlayerVariables entityData = source.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
		LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		if (entitypatch == null)
			return retval;

		if (entitypatch.getServerAnimator() == null || entityData == null)
			return retval;

		UtilitySystems.EGOAttackContext context = generateAttackContext(entitypatch);


		int burnSinking = EgoWeaponsEffects.BURN.get().getPotency(target) + EgoWeaponsEffects.SINKING.get().getPotency(target);
		int FburnSinking = burnSinking / 5;
		int lampOnSelf = EgoWeaponsEffects.LAMP.get().getPotency(source);

		if (context.isValidEgoAnimation() && entityData != null) {

			// Different animation effects
			switch (context.getAnimationIdentifier()) {
				case "lamp_cb_auto_m_2":
					EgoWeaponsEffects.LAMP.get().increment(source, 0, 1);
					break;
				case "lamp_cb_auto_r_3":
					EgoWeaponsEffects.LAMP.get().increment(source, 0, 2);
					break;
				case "lamp_cb_innate":
					EgoWeaponsEffects.LAMP.get().increment(source, 0, 2);
					break;
				case "lamp_cb_sp_1b_1":
				case "lamp_cb_sp_1b_2":
				case "lamp_cb_sp_1":
				case "lamp_cb_sp_2_1":
					SharedFunctions.hitstunEntity(targetPatch, 3, false, 0.3f);
					break;
			}


		}

		entityData.syncPlayerVariables(source);
		return retval;
	}

	public static float modifyDamageAmount(LivingEntity target, LivingEntity source, float mult, DamageSource damageSource) {

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		UtilitySystems.EGOAttackContext context = generateAttackContext(entitypatch);

		ItemStack mainhandItem = target.getMainHandItem();

		int burnSinking = EgoWeaponsEffects.BURN.get().getPotency(target) + EgoWeaponsEffects.SINKING.get().getPotency(target);
		int FburnSinking = burnSinking / 5;
		int lampOnSelf = EgoWeaponsEffects.LAMP.get().getPotency(source);
		float burstMult = 1.0f;

		// Deal +20% Damage to targets with Dazzle
		if (target.hasEffect(EgoWeaponsEffects.DAZZLE.get())) {
			mult += SharedFunctions.incrementBonusDamage(damageSource, 0.2f);
		}

		// For every 5 Burn + Sinking. Deal +5% Damage [Max 25%]
		if (FburnSinking > 0) {
			mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.25f, 0.05f * FburnSinking));
		}

		switch (context.getAnimationIdentifier()) {
			case "lamp_cb_auto_m_dash":
			case "lamp_cb_auto_m_1":
			case "lamp_cb_auto_m_2":
				EgoWeaponsEffects.BURN.get().increment(target, 0, 1);
				break;
			case "lamp_cb_auto_r_1":
				EgoWeaponsEffects.BURN.get().increment(target, 0, 1);
				EgoWeaponsEffects.SINKING.get().increment(target, 0, 1);
				break;
			case "lamp_cb_auto_r_2":
				EgoWeaponsEffects.BURN.get().increment(target, 1, 0);
				EgoWeaponsEffects.SINKING.get().increment(target, 2, 0);
				break;
			case "lamp_cb_auto_r_3":
				EgoWeaponsEffects.BURN.get().increment(target, 1, 1);
				EgoWeaponsEffects.SINKING.get().increment(target, 1, 1);
				break;
			case "lamp_cb_innate":

				boolean isEmpowered = false;

				if (mainhandItem.isEmpty())
					isEmpowered = mainhandItem.getOrCreateTag().getBoolean("empoweredInnate");

				if (isEmpowered) {
					EgoWeaponsEffects.BLACK_FRAGILITY.get().increment(target, 0, 1);
					mult += SharedFunctions.incrementBonusDamage(damageSource, 0.5f);
				}

				EgoWeaponsEffects.BURN.get().increment(target, isEmpowered ? 2 : 0, 1);
				EgoWeaponsEffects.SINKING.get().increment(target, isEmpowered ? 3 : 0, 1);
				break;
			case "lamp_cb_sp_1b_1":
				mult += SharedFunctions.incrementBonusDamage(damageSource, 0.3f);
			case "lamp_cb_sp_1":
				EgoWeaponsEffects.BURN.get().increment(target, 2, 0);
				EgoWeaponsEffects.SINKING.get().increment(target, 3, 0);

				mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(FburnSinking * 0.05f, 0.25f));
				mult += SharedFunctions.incrementBonusDamage(damageSource, lampOnSelf * 0.03f);
				break;
			case "lamp_cb_sp_1b_2":
				mult += SharedFunctions.incrementBonusDamage(damageSource, 0.3f);
			case "lamp_cb_sp_2_1":
				EgoWeaponsEffects.BURN.get().increment(target, 0, 2);
				EgoWeaponsEffects.SINKING.get().increment(target, 0, 2);
				mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(FburnSinking * 0.05f, 0.25f));
				mult += SharedFunctions.incrementBonusDamage(damageSource, lampOnSelf * 0.03f);
				break;
			case "lamp_cb_sp_2b":
				burstMult = 1.5f;
				mult += SharedFunctions.incrementBonusDamage(damageSource, 0.3f);
			case "lamp_cb_sp_2_2":
				EgoWeaponsEffects.BLACK_FRAGILITY.get().increment(target, 0, 1);
				EgoWeaponsEffects.BURN.get().increment(target, 2, 0);
				EgoWeaponsEffects.SINKING.get().increment(target, 4, 0);
				mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(FburnSinking * 0.05f, 0.25f));
				mult += SharedFunctions.incrementBonusDamage(damageSource, lampOnSelf * 0.03f);

				float finalBurstMult = burstMult;
				new DelayedEvent(20, (a) -> {

					target.hurt(new DirectEgoDamageSource("", target, ExtendedDamageSource.StunType.NONE, LampCrossbowMovesetAnims.LAMP_CB_SP_2, GenericEgoDamage.AttackTypes.GENERIC, GenericEgoDamage.DamageTypes.BLACK, "lamp"), (int)Math.min(burnSinking * finalBurstMult, (15 * finalBurstMult)));
				});
				break;
		}



		return mult;
	}

	private final ResourceLocation featherWickBolt = new ResourceLocation(EgoWeaponsMod.MODID, "textures/mob_effect/feather_wick_bolt.png");;
	private final ResourceLocation emptyBoltIcon = new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/bullets/empty_bolt_lamp.png");;
	public LampCrossbow(IItemTier tier, int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_, GunCaliber caliber, int capacity) {
		super(tier, p_i48460_2_, p_i48460_3_, p_i48460_4_, capacity, caliber);
		this.ammoRL = featherWickBolt;
		this.ammoBGOverride = emptyBoltIcon;
	}


}


package net.m3tte.ego_weapons.item.liu;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.gameasset.movesets.LiuSouth6MovesetAnims;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class LiuFireGauntlet extends EgoWeaponsWeapon {

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

	public LiuFireGauntlet(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(liuFireGauntletTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}




	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("A standard Gauntlet made for low ranked Liu Association fixers").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 5) + 1) + "/5] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.7"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 5) {
			case 0:
                if (EgoWeaponsKeybinds.isHoldingShift())
                    generateStatusDescription(list, new String[]{"red", "burn"});
                else
                    generateDescription(list, "liu_south_6_gauntlet", "ability", 5);
                break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "burn"});
				else
					generateDescription(list,"liu_south_6_gauntlet", "auto", 4);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "burn"});
				else
					generateDescription(list,"liu_south_6_gauntlet", "innate", 3);
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "burn"});
				else
					generateDescription(list,"liu_south_6_gauntlet", "guard", 1);
				break;
			case 4:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "burn"});
				else
					generateDescription(list,"liu_south_6_gauntlet", "passive", 1);
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

		PlayerVariables entityData = sourceentity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

			String weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

			switch (weaponIdentifier) {


                case "liu_s6_innate2":
                    EgoWeaponsEffects.BURN.get().increment(target, 0, 2);
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.LIU_S6_AUTO_SIDE.get().getRegistryName()));
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.LIU_PUNCH_SHOCKWAVE.get().getRegistryName()));
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), 8, target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 0.05, 0.6f, 1.5f, 0.5f, 1f, 0.5f));

					break;

				case "liu_s6_auto3":
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.LIU_PUNCH_SHOCKWAVE.get().getRegistryName()));
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), 8, target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 0.05, 0.6f, 1.5f, 0.5f, 1f, 0.5f));

				case "liu_s6_auto1":
				case "liu_s6_auto2":
					EgoWeaponsEffects.BURN.get().increment(target, 0, 1);
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.LIU_S6_AUTO_SIDE.get().getRegistryName()));
				break;
                case "liu_s6_innate1":
					entitypatch.playAnimationSynchronized(LiuSouth6MovesetAnims.LIU_S6_INNATE_2, 0);
					EgoWeaponsEffects.BURN.get().increment(target, 0, 2);
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.LIU_S6_AUTO_SIDE.get().getRegistryName()));

					break;
				case "liu_s6_sp1":
				case "liu_s6_sp2":
				case "liu_s6_sp3":
					EgoWeaponsEffects.BURN.get().increment(target, 0, 1);
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.LIU_S6_AUTO_SIDE.get().getRegistryName()));
					entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putBoolean("liu_gauntlet_hit", true);
					break;
				case "liu_s6_sp4":
					EgoWeaponsEffects.BURN.get().increment(target, 1, 3);
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.LIU_S6_AUTO_SIDE.get().getRegistryName()));
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.LIU_PUNCH_SHOCKWAVE.get().getRegistryName()));
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), 8, target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 0.05, 0.6f, 1.5f, 0.5f, 1f, 0.5f));

					target.setDeltaMovement(target.getDeltaMovement().add(0,0.3,0));
					break;
			}
		}

		return true;
	}

	public static float modifyDamageAmount(LivingEntity target, LivingEntity source, float mult, DamageSource damageSource) {

		int burnEffect = EgoWeaponsEffects.BURN.get().getPotency(target);
		if (burnEffect > 0) {
			float multiplier = Math.min(burnEffect*0.02f,1.2f);
			SharedFunctions.incrementBonusDamage(damageSource, multiplier);
			mult += multiplier;
		}
		return mult;
	}


	public static StaticAnimation.Event[] specialHitEvent(int nextAnimation, float timeStamp) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];
		events[0] = StaticAnimation.Event.create(0.1f, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("liu_gauntlet_hit");
		}, StaticAnimation.Event.Side.BOTH);

		events[1] = StaticAnimation.Event.create(timeStamp, (entitypatch) -> {
			if (entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().contains("liu_gauntlet_hit")) {
				switch (nextAnimation) {
					case 0:
						entitypatch.playAnimationSynchronized(LiuSouth6MovesetAnims.LIU_S6_SPECIAL_2, 0);
						break;
					case 1:
						entitypatch.playAnimationSynchronized(LiuSouth6MovesetAnims.LIU_S6_SPECIAL_3, 0);
						break;
					case 2:
						entitypatch.playAnimationSynchronized(LiuSouth6MovesetAnims.LIU_S6_SPECIAL_4, 0);
						break;
				}
			}

		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] focusEmbers() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];
		events[0] = StaticAnimation.Event.create(0.1f, (entitypatch) -> {

		}, StaticAnimation.Event.Side.BOTH);

		events[1] = StaticAnimation.Event.create(0.3f, (entitypatch) -> {
			entitypatch.getOriginal().addEffect(new EffectInstance(EgoWeaponsEffects.RESILIENCE.get(), 40, 0));
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

}

package net.m3tte.ego_weapons.execFunctions;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims;
import net.m3tte.ego_weapons.particle.BlacksilenceshadowParticle;
import net.m3tte.ego_weapons.particle.ShadowpuffParticle;
import net.m3tte.ego_weapons.potion.OrlandoPotionEffect;
import net.m3tte.ego_weapons.potion.FuriosoPotionEffect;
import net.m3tte.ego_weapons.procedures.legacy.ClearOldWeaponsProcedure;
import net.minecraft.command.FunctionObject;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Optional;

public class SwapWeapon {

	public static void SwapWeapon(IWorld world, LivingEntity entity, int weaponIndex) {

		if (entity.hasEffect(OrlandoPotionEffect.potion) && entity.getPersistentData().getDouble("furiosoattacks") >= 10) {

			if (entity.getPersistentData().getDouble("furiosohits") >= 10) {
				entity.addEffect(new EffectInstance(Effects.DIG_SPEED, 60, 2, (false), (false)));
				entity.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 60, 2, (false), (false)));
				entity.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 60, 2, (false), (false)));
				entity.addEffect(new EffectInstance(Effects.REGENERATION, 40, 2, (false), (false)));
			}


			if (!entity.level.isClientSide && entity.level.getServer() != null) {
				Optional<FunctionObject> _fopt = entity.level.getServer().getFunctions().get(new ResourceLocation("ego_weapons:furiososwap"));
				if (_fopt.isPresent()) {
					FunctionObject _fobj = _fopt.get();
					entity.level.getServer().getFunctions().execute(_fobj, entity.createCommandSourceStack());
				}
			}
		} else {
			{
				double _setval = ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new EgoWeaponsModVars.PlayerVariables())).light - 1);
				entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.light = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if (10 > (entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new EgoWeaponsModVars.PlayerVariables())).globalcooldown) {
				{
					entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.globalcooldown = 10;
						capability.syncPlayerVariables(entity);
					});
				}
			}
		}
		if (entity.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.SUIT_OF_THE_BLACK_SILENCE.get())) {
			EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(entity, entity.hasEffect(OrlandoPotionEffect.potion.getEffect()) ? 2 : 1, entity.hasEffect(OrlandoPotionEffect.potion.getEffect()) ? 2 : 1);
		}
		entity.getPersistentData().putDouble("furiosohits", 0);
		entity.getPersistentData().putDouble("furiosoattacks", 0);
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(ShadowpuffParticle.particle, (entity.getX()), (entity.getY() + 1), (entity.getZ()), 8,
					0.4, 0.6, 0.4, 0);
		}
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(BlacksilenceshadowParticle.particle, (entity.getX()), (entity.getY() + 1), (entity.getZ()),
                    15, 0.2, 0.6, 0.2, 0);
		}
		if ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new EgoWeaponsModVars.PlayerVariables())).blipcooldown < 4) {
			{
				entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.blipcooldown = 4;
					capability.syncPlayerVariables(entity);
				});
			}
		}


		if (entity instanceof PlayerEntity) {
			if (ItemTags.getAllTags().getTag(new ResourceLocation("ego_weapons:blacksilenceweapons"))
					.contains(entity.getItemInHand(Hand.MAIN_HAND).getItem())) {
				ItemStack _stktoremove = entity.getItemInHand(Hand.MAIN_HAND);
				((PlayerEntity)entity).inventory.removeItem(_stktoremove);
			}
		}


		ClearOldWeaponsProcedure.executeProcedure((PlayerEntity) entity);
		weaponIndex = weaponIndex % 10;

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
		StaticAnimation animation = null;

		// Cycles through the weapons via an index. The switch is way more efficient than mcreators goofy if chain.
		switch(weaponIndex) {
			// If the index is 0, switch to durandal
			case 0: // Durandal
				entity.setItemInHand(Hand.MAIN_HAND, new ItemStack(EgoWeaponsItems.DURANDAL.get()));


				animation = EgoWeaponsAnimations.DURANDAL_EQUIP;

				break;
			case 1: // Pistols
				entity.setItemInHand(Hand.MAIN_HAND, new ItemStack(EgoWeaponsItems.ATELIER_LOGIC_PISTOLS.get()));
				entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ammo", 2);
				entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.light -= 1;
					capability.syncPlayerVariables(entity);
				});
				entity.setItemInHand(Hand.OFF_HAND, new ItemStack(EgoWeaponsItems.ATELIER_LOGIC_PISTOLS.get()));

				animation = EgoWeaponsAnimations.DUAL_EQUIP;


				break;
			case 2: // Allas
				entity.setItemInHand(Hand.MAIN_HAND, new ItemStack(EgoWeaponsItems.ALLAS_SPEAR.get()));

				animation = BlackSilenceMovesetAnims.ALLAS_SPEAR_EQUIP;

				break;

			case 3: // Old Boys
				entity.setItemInHand(Hand.MAIN_HAND, new ItemStack(EgoWeaponsItems.OLD_BOYS_WORKSHOP.get()));
				animation = BlackSilenceMovesetAnims.OLD_BOYS_EQUIP;
				break;

			case 4: // Mook
				entity.setItemInHand(Hand.MAIN_HAND, new ItemStack(EgoWeaponsItems.MOOK_WORKSHOP.get()));
				animation = EgoWeaponsAnimations.DURANDAL_EQUIP;

				break;

			case 5: // Ranga
				entity.setItemInHand(Hand.MAIN_HAND, new ItemStack(EgoWeaponsItems.RANGA_CLAW.get()));
				entity.setItemInHand(Hand.OFF_HAND, new ItemStack(EgoWeaponsItems.RANGA_CLAW_L.get()));
				animation = EgoWeaponsAnimations.DUAL_EQUIP;

				break;
			case 6: // Zelkova
				entity.setItemInHand(Hand.MAIN_HAND, new ItemStack(EgoWeaponsItems.ZELKOVA_AXE.get()));
				entity.setItemInHand(Hand.OFF_HAND, new ItemStack(EgoWeaponsItems.ZELKOVA_MACE.get()));
				animation = EgoWeaponsAnimations.DUAL_EQUIP;

				break;

			case 7: // Wheels Industry
				entity.setItemInHand(Hand.MAIN_HAND, new ItemStack(EgoWeaponsItems.WHEELS_INDUSTRY.get()));
				animation = BlackSilenceMovesetAnims.ATELIER_SHOTGUN_EQUIP;
				break;
			case 8: // Crystal Atelier
				entity.setItemInHand(Hand.MAIN_HAND, new ItemStack(EgoWeaponsItems.CRYSTAL_ATELIER.get()));
				entity.setItemInHand(Hand.OFF_HAND, new ItemStack(EgoWeaponsItems.CRYSTAL_ATELIER.get()));
				animation = EgoWeaponsAnimations.DUAL_EQUIP;
				break;
			case 9: // Atelier Logic Shotgun
				entity.setItemInHand(Hand.MAIN_HAND, new ItemStack(EgoWeaponsItems.ATELIER_LOGIC_SHOTGUN.get()));
				animation = BlackSilenceMovesetAnims.ATELIER_SHOTGUN_EQUIP;

				break;
		}

		if (entity.hasEffect(FuriosoPotionEffect.potion))
			animation = null;

		if (animation != null)
			entitypatch.playAnimationSynchronized(animation, 0);



		if (entity instanceof ServerPlayerEntity)
			((ServerPlayerEntity) entity).inventory.setChanged();
		int finalWeaponIndex = weaponIndex;
		entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
			capability.blacksilence_ws = finalWeaponIndex; // Modulo of total weapons
			capability.syncPlayerVariables(entity);
		});
	}
}

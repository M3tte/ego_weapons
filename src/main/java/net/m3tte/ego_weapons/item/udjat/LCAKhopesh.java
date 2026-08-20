
package net.m3tte.ego_weapons.item.udjat;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.network.packages.VFXPackages;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.procedures.TooltipFuncs;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.m3tte.ego_weapons.world.capabilities.UtilitySystems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.*;
import static net.m3tte.ego_weapons.world.capabilities.UtilitySystems.generateAttackContext;

public class LCAKhopesh extends EgoWeaponsWeapon {
	private static IItemTier ardorBlossomTier = new IItemTier() {

		@Override
		public int getUses() {
			return 0;
		}

		@Override
		public float getSpeed() {
			return 3.6f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 6.5f;
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

	public LCAKhopesh(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(ardorBlossomTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}



	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		TooltipFuncs.generateItemDescription(list, "desc.ego_weapons.lca_khopesh.desc");
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 5) + 1) + "/5] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.2"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 5) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"sinking", "blue_sand"});
				else
					generateDescription(list,"lca_khopesh", "passive", 3);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"protection", "offense_down"});
				else
					generateDescription(list,"lca_khopesh", "passive2", 3);
				break;
			case 2:

				if (Minecraft.getInstance().player.getItemBySlot(EquipmentSlotType.OFFHAND).getItem().equals(EgoWeaponsItems.LCA_RIFLE.get())) {
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"speed", "udjat_vanguard", "ammo", "speed_down", "white_fragility", "protection", "tremor", "sinking", "blue_sand", "sheut_fracture"});
					else
						generateDescription(list, "lca_khopesh", "combo_ability", 14, true);
				} else {
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"protection", "sinking", "blue_sand", "sheut_fracture", "white_fragility"});
					else
						generateDescription(list, "lca_khopesh", "ability", 14, true);
				}


				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"fragile", "protection","sinking", "blue_sand", "sheut_fracture", "white_fragility"});
				else {
					generateDescription(list,"lca_khopesh", "innate", 7);
				}

				break;
			case 4:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"white", "sinking", "protection", "sheut_fracture", "white_fragility"});
				else
					generateDescription(list,"lca_khopesh", "auto", 8);
				break;
		}

		generateStatusHelp(list);
	}



	public static void interruptedAttack(LivingEntity target) {

	}



	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, target, sourceentity);

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		UtilitySystems.EGOAttackContext context = generateAttackContext(entitypatch);

		System.out.println("LCA KHOPESH HIT REG");


		if (!target.level.isClientSide())
			EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.UDJAT_SAND.get(), 10, target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 0f, 0.6f, 0.5, 0,0,0));


		if (context.isValidEgoAnimation()) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

			LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

			System.out.println("LCA KHOPESH HIT WITH : "+context.getAnimationIdentifier());

			switch (context.getAnimationIdentifier()) {


				case "lca_rifle_sp_b_3":
					SharedFunctions.staggerEntity(targetPatch, 1, false);
					break;
				case "lca_rifle_sp_b_4":
					target.getPersistentData().remove("lcaKhopeshL");
					SharedFunctions.hitstunEntity(targetPatch, 1, false, 0);
					break;
				case "khopesh_innate":
					EgoWeaponsEffects.PROTECTION.get().increment(sourceentity, 5, 1);
					break;
				case "khopesh_auto_3":
					itemstack.getOrCreateTag().putInt("hitEntity", 1);
					EgoWeaponsEffects.PROTECTION.get().increment(sourceentity, 5, 1);
					break;
				case "khopesh_auto_3f":
					if (!sourceentity.level.isClientSide())
						EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendShakeMessage(target.getId(), 3));

					EgoWeaponsEffects.DEFENSE_LEVEL_UP.get().increment(sourceentity, 0, 2);
					break;
				case "khopesh_special_1":
					itemstack.getOrCreateTag().putInt("hitEntity", 1);
					SharedFunctions.hitstunEntity(targetPatch, 1, false, 1);
					break;

				case "khopesh_special_2_a":
				case "khopesh_special_2_b":
					if (targetPatch != null)
						SharedFunctions.hitstunEntity(targetPatch, 1, false, 1);
					break;

				case "khopesh_special_2_c":
					if (targetPatch != null)
						SharedFunctions.staggerEntity(targetPatch, 1, false);
					itemstack.getOrCreateTag().putInt("hitEntity", 1);
					break;

				case "khopesh_special_3":
					if (!sourceentity.level.isClientSide())
						EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendShakeMessage(target.getId(), 2));
					break;
			}
		}

		return true;
	}


	public static float modifyDamageAmount(LivingEntity target, LivingEntity source, float mult, DamageSource damageSource) {

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		UtilitySystems.EGOAttackContext context = generateAttackContext(entitypatch);


		if (context.isValidEgoAnimation()) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));


			LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

			int blueSandOnTarget = EgoWeaponsEffects.BLUE_SAND.get().getPotency(target);
			int protectionOnSelf = EgoWeaponsEffects.PROTECTION.get().getPotency(source);
			int sinkingOnTarget = EgoWeaponsEffects.SINKING.get().getPotency(target);
			if (blueSandOnTarget >= 0) {

				mult += SharedFunctions.incrementBonusDamage(damageSource, 0.15f);
			}

			mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.30f, 0.03f * sinkingOnTarget));
			mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.35f, 0.07f * protectionOnSelf));

			if (target instanceof PlayerEntity) {
				double sanity = SanitySystem.getSanity((PlayerEntity) target) / EgoWeaponsAttributes.getMaxSanity((PlayerEntity) target);

				if (sanity <= 0.5f) {
					mult += SharedFunctions.incrementBonusDamage(damageSource, 0.15f);

				}
			}
			int speedDiff = Math.max(0, EgoWeaponsEffects.speedMult(source) - EgoWeaponsEffects.speedMult(target));

			switch (context.getAnimationIdentifier()) {

				case "lca_rifle_sp_b_3":
					if (source.hasEffect(EgoWeaponsEffects.UDJAT_VANGUARD.get()))
						mult += SharedFunctions.incrementBonusDamage(damageSource, 0.30f);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.2f, 0.04f * speedDiff));
					EgoWeaponsEffects.BLUE_SAND.get().increment(target, 3, 2);
					EgoWeaponsEffects.SINKING.get().increment(target, 0, 2);
					EgoWeaponsEffects.SHEUT_FRACTURE.get().increment(target, 0, 4);

					break;
				case "lca_rifle_sp_b_4": // Carryover
					if (source.hasEffect(EgoWeaponsEffects.UDJAT_VANGUARD.get()))
						mult += SharedFunctions.incrementBonusDamage(damageSource, 0.30f);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.2f, 0.04f * speedDiff));
					break;

				case "khopesh_auto_1":
					EgoWeaponsEffects.SINKING.get().increment(target, 0, 2);
					break;
				case "khopesh_auto_3":
					EgoWeaponsEffects.SHEUT_FRACTURE.get().increment(target, 10, 1);
					break;
                case "khopesh_auto_3f":
                    EgoWeaponsEffects.SINKING.get().increment(target, 0, 1);
					EgoWeaponsEffects.SHEUT_FRACTURE.get().increment(target, 10, 1);
					break;
				case "khopesh_auto_2":
					EgoWeaponsEffects.SINKING.get().increment(target, 2, 0);
					break;
				case "khopesh_innate":
					EgoWeaponsEffects.SINKING.get().increment(target, 2, 0);
					EgoWeaponsEffects.FRAGILE.get().increment(target, 10, 1);
					EgoWeaponsEffects.BLUE_SAND.get().increment(target, 3, 1);
					EgoWeaponsEffects.SHEUT_FRACTURE.get().increment(target, 10, 4);
					break;
				case "khopesh_special_1":
					EgoWeaponsEffects.SINKING.get().increment(target, blueSandOnTarget > 0 ? 1 : 0, 2);
					if (blueSandOnTarget > 0)
						EgoWeaponsEffects.SHEUT_FRACTURE.get().increment(target, 10, 1);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(1f, 0.15f * protectionOnSelf));
					break;
				case "khopesh_special_2_a":
					EgoWeaponsEffects.SINKING.get().increment(target, blueSandOnTarget > 1 ? 1 : 0, 2);
					if (blueSandOnTarget > 1)
						EgoWeaponsEffects.SHEUT_FRACTURE.get().increment(target, 10, 1);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(1f, 0.15f * protectionOnSelf));
					break;
				case "khopesh_special_2_b":
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(1f, 0.15f * protectionOnSelf));
					break;
				case "khopesh_special_2_c":
					EgoWeaponsEffects.SINKING.get().increment(target, blueSandOnTarget > 2 ? 2 : 1, 0);
					if (blueSandOnTarget > 2)
						EgoWeaponsEffects.SHEUT_FRACTURE.get().increment(target, 10, 1);
					EgoWeaponsEffects.BLUE_SAND.get().increment(target, 3, 1);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(1f, 0.15f * protectionOnSelf));
					break;
				case "khopesh_special_3":
					EgoWeaponsEffects.BLUE_SAND.get().increment(target, 3, 2);
					EgoWeaponsEffects.SHEUT_FRACTURE.get().increment(target, 10, 3);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(1f, 0.15f * protectionOnSelf));
					StaggerSystem.reduceStagger(target, EgoWeaponsEffects.SINKING.get().getPotency(target), false);
					break;
            }
		}




		return mult;
	}



	@Override
	public String getDefaultKillIdentifier() {
		return "udjat_khopesh";
	}

}


package net.m3tte.ego_weapons.item.ardor_blossom;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.network.packages.VFXPackages;
import net.m3tte.ego_weapons.procedures.DelayedEvent;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.procedures.TooltipFuncs;
import net.m3tte.ego_weapons.specialParticles.numberParticle.NumberParticleTypes;
import net.m3tte.ego_weapons.world.capabilities.DamageResistanceSystem;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.m3tte.ego_weapons.world.capabilities.UtilitySystems;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.damage.SimpleEgoDamageSource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.*;
import static net.m3tte.ego_weapons.world.capabilities.UtilitySystems.generateAttackContext;

public class ArdorBlossomBat extends EgoWeaponsWeapon {
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
			return 7f;
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

	public ArdorBlossomBat(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(ardorBlossomTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}



	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		TooltipFuncs.generateItemDescription(list, "desc.ego_weapons.ardor_blossom_bat.desc");
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 5) + 1) + "/5] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.waw"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 5) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "burn", "embers"});
				else
					generateDescription(list,"ardor_blossom_bat", "passive", 3, EgoWeaponsEffects.EMBERS.get(), EgoWeaponsEffects.EGO_ATTUNEMENT_ARDOR_BLOSSOM.get());
				break;
			case 1:
				boolean swap = false;
				if (Minecraft.getInstance().player != null) {
					if (EgoWeaponsEffects.BURN.get().getPotency(Minecraft.getInstance().player) >= 25) {
						swap = true;
					}
				}

				if (swap) {
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"red", "burn", "embers", "ego_att_ardor", "fragile"});
					else
						generateDescription(list, "ardor_blossom_bat", "ability2", 15, true, EgoWeaponsEffects.EMBERS.get());
				} else {
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"red", "burn", "embers", "ego_att_ardor"});
					else
						generateDescription(list, "ardor_blossom_bat", "ability1", 10, true, EgoWeaponsEffects.EMBERS.get(), EgoWeaponsEffects.EGO_ATTUNEMENT_ARDOR_BLOSSOM.get());
				}

				break;
			case 2:
                if (EgoWeaponsKeybinds.isHoldingShift())
                    generateStatusDescription(list, new String[]{"red", "burn", "embers"});
                else
                    generateDescription(list, "ardor_blossom_bat", "ability", 9, true, EgoWeaponsEffects.EMBERS.get());
                break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "burn", "embers"});
				else {
					generateDescription(list,"ardor_blossom_bat", "innate", 9, true, EgoWeaponsEffects.EMBERS.get());
				}

				break;
			case 4:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "burn", "embers"});
				else
					generateDescription(list,"ardor_blossom_bat", "auto", 6, EgoWeaponsEffects.EMBERS.get());
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

		if (context.isValidEgoAnimation()) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

			AttackAnimation.Phase phase = null;


			LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

			switch (context.getAnimationIdentifier()) {

				case "ardor_blossom_innate_1":
				case "ardor_blossom_innate_2":
					SharedFunctions.hitstunEntity(targetPatch, 1, false, 0.1f);

					break;

				case "ardor_blossom_special_1":
					SharedFunctions.staggerEntity(targetPatch, 2, false);
					break;

				case "ardor_blossom_sp_b_1":
					SharedFunctions.hitstunEntity(targetPatch, 1, false, 1);
					break;

			}
		}

		return true;
	}

	public static void triggerEmbersEffect(LivingEntity target, int delay, boolean hit, LivingEntity source) {

		LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		if (!target.level.isClientSide())
			EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), 20, target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 1.5f, 1.5f, 10, 0,0,0));

		if (targetPatch == null)
			return;

		DelayedEvent.animDelayEvent(targetPatch, delay, (e) -> {
			if (target.hasEffect(EgoWeaponsEffects.EMBERS.get())) {

				if (!target.level.isClientSide()) {
					((ServerWorld) target.level).sendParticles(EgoWeaponsParticles.FIREFIST_HIT.get(), target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 1, 0,0,0,0);
				}

				int burnPotency = EgoWeaponsEffects.BURN.get().getPotency(target);
				if (burnPotency >= 0) {
					DamageSource src = new SimpleEgoDamageSource("", null, GenericEgoDamage.AttackTypes.HIDDEN, GenericEgoDamage.DamageTypes.RED, "ardor_spark");
					target.hurt(src, (float) burnPotency);


					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.NumberLabelParticle(target.position().add(target.getRandom().nextFloat() - 0.5f,1,target.getRandom().nextFloat() - 0.5f), NumberParticleTypes.EMBERS, DamageResistanceSystem.processDamageForEntity(target, null, 1, src, StaggerSystem.isStaggered(target)) * burnPotency));

					EgoWeaponsEffects.EMBERS.get().decrement(target, 0, 1);
					target.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_INNATE_HIT_3, 1f, 1f);

					// String damageTypeIn, Entity damageSourceEntityIn, StunType stunType, StaticAnimation animation, AttackTypes attackType, DamageTypes damageType, String attackIdentifier
				}
			}

			if (hit) {
				DamageSource src1 = new SimpleEgoDamageSource("", source, GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.RED, "ardor_blossom_sp_b_3");
				target.hurt(src1, (float) 15);
			}
		}, "Ardor Blossom Effect");

	}


	public static float modifyDamageAmount(LivingEntity target, LivingEntity source, float mult, DamageSource damageSource) {

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);



		System.out.println("MODIFY DAMAGE CALLED ON : "+target);

		if (target.hasEffect(EgoWeaponsEffects.EMBERS.get())) {
			mult += SharedFunctions.incrementBonusDamage(damageSource, 0.1f);
		}

		int burnTarget = EgoWeaponsEffects.BURN.get().getPotency(target);
		int burnSelf = EgoWeaponsEffects.BURN.get().getPotency(source);
		int burnCSelf = EgoWeaponsEffects.BURN.get().getCount(source);

		if (!target.level.isClientSide())
			EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), 8, target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 0.05, 0.6f, 1.5f, 0.5f, 1f, 0.5f));

		UtilitySystems.EGOAttackContext context = generateAttackContext(entitypatch);

		if (context.isValidEgoAnimation()) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));



			LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

			int emotionLevel = 0;
			float missingHPSource = Math.min(1,(source.getMaxHealth() - source.getHealth()) / source.getMaxHealth()) * 100;

			if (source instanceof PlayerEntity) {
				emotionLevel = EmotionSystem.getEmotionLevel((PlayerEntity) source);
			}

			switch (context.getAnimationIdentifier()) {
				case "ardor_blossom_auto_2":
					EgoWeaponsEffects.BURN.get().increment(source, 0, 1);
				case "ardor_blossom_auto_1":
					EgoWeaponsEffects.BURN.get().increment(target, 0, 1);
					break;


				case "ardor_blossom_auto_3":
					EgoWeaponsEffects.BURN.get().increment(source, 0, 1);
					EgoWeaponsEffects.BURN.get().increment(target, 0, burnSelf >= 5 ? 2 : 1);

					if (burnSelf >= 7) {
						EgoWeaponsEffects.EMBERS.get().increment(target, 0, 1);
					}

					break;
				case "ardor_blossom_innate_1":
					EgoWeaponsEffects.BURN.get().increment(target, 0, 1);
					break;

				case "ardor_blossom_innate_2":
					EgoWeaponsEffects.BURN.get().increment(target, 2, 1);
					EgoWeaponsEffects.BURN.get().increment(source, 2, 0);

					mult += SharedFunctions.incrementBonusDamage(damageSource, 0.2f);
					break;

				case "ardor_blossom_innate_3":
					EgoWeaponsEffects.BURN.get().increment(target, 2, 1);
					EgoWeaponsEffects.BURN.get().increment(source, 2, 0);
					if (target.hasEffect(EgoWeaponsEffects.EMBERS.get())) triggerEmbersEffect(target, 30, false, source);
					if (targetPatch != null) targetPatch.knockBackEntity(source.position(), 0.4f);

					mult += SharedFunctions.incrementBonusDamage(damageSource, 0.4f);
					break;
				case "ardor_blossom_special_1":
					EgoWeaponsEffects.BURN.get().increment(target, 0, 3);
					break;
				case "ardor_blossom_special_2":
					EgoWeaponsEffects.BURN.get().increment(target, 0, 2);
					EgoWeaponsEffects.BURN.get().increment(source, 0, 2);
					EgoWeaponsEffects.EMBERS.get().increment(target, 0, 1);

					mult += SharedFunctions.incrementBonusDamage(damageSource, 0.25f);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.5f, 0.025f * burnTarget * burnSelf));
					break;

				case "ardor_blossom_sp_b_1":
					EgoWeaponsEffects.EMBERS.get().increment(target, 0, 1);
					EgoWeaponsEffects.BURN.get().increment(target, 1, 2 + Math.min(2, burnSelf/10));
					break;
				case "ardor_blossom_sp_b_2":
					EgoWeaponsEffects.BURN.get().increment(target, 1, 2 + Math.min(2, burnSelf/10));
					EgoWeaponsEffects.BURN.get().increment(source, 0, 2);
					if (target.hasEffect(EgoWeaponsEffects.EMBERS.get())) triggerEmbersEffect(target, 22, true, source);
					break;

				case "ardor_blossom_sp_b_3":
					break;


				case "ardor_blossom_sp_u_1":
					mult += SharedFunctions.incrementBonusDamage(damageSource, 0.05f * emotionLevel + missingHPSource * 0.01f);
					EgoWeaponsEffects.BURN.get().increment(target, 4, 3);
					EgoWeaponsEffects.EMBERS.get().increment(target, 0, 1);

					break;

				case "ardor_blossom_sp_u_2":
					mult += SharedFunctions.incrementBonusDamage(damageSource, 0.05f * emotionLevel + missingHPSource * 0.01f);
					EgoWeaponsEffects.BURN.get().increment(target, 4, 3);

					break;

				case "ardor_blossom_sp_u_3":
					mult += SharedFunctions.incrementBonusDamage(damageSource, 0.05f * emotionLevel + missingHPSource * 0.01f);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.5f, burnSelf + burnCSelf));

					break;
			}
		}




		return mult;
	}



	@Override
	public String getDefaultKillIdentifier() {
		return "ardor_blossom_bat";
	}

}


package net.m3tte.ego_weapons.item.oeufi;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.gameasset.movesets.OeufiAssocMovesetAnims;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.potion.countEffects.TremorDecayEffect;
import net.m3tte.ego_weapons.potion.countEffects.TremorEffect;
import net.m3tte.ego_weapons.procedures.EntityTick;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;
import static net.m3tte.ego_weapons.procedures.SharedFunctions.*;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class OeufiHalberd extends EgoWeaponsWeapon {

	private static IItemTier oeufiTier = new IItemTier() {

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

	public OeufiHalberd(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(oeufiTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}

	@Override
	public String getDefaultKillIdentifier() {
		return "oeufi_halberd";
	}

	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("Manufactured by Kai Atelier").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 4) + 1) + "/4] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.he"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 4) {
			case 0:
                if (EgoWeaponsKeybinds.isHoldingShift())
                    generateStatusDescription(list, new String[]{"black", "tremor", "tremor_decay", "tremor_burst", "tremor_conversion"});
                else
                    generateDescription(list, "oufi_halberd", "ability", 6);
                break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "tremor"});
				else
					generateDescription(list,"oufi_halberd", "auto", 4);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "tremor", "tremor_burst", "tremor_decay"});
				else
					generateDescription(list,"oufi_halberd", "innate", 4);
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "tremor"});
				else
					generateDescription(list,"oufi_halberd", "guard", 3);
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

		final int anim_id = currentanim.getId();

		LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

			String attackIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

			// Different animation effects
			switch (attackIdentifier) {
				case "oeufi_contract_counter":
					TremorEffect tr = TremorEffect.incrementTremor(target, 3, 2);
					TremorEffect.burstTremor(target, true);
					break;
				case "oeufi_counter_2":
					TremorEffect ef = TremorEffect.incrementTremor(target, 2, 2);
					if (!StaggerSystem.isStaggered(target)) {

						if (targetPatch != null && ef.getPotency(target) > 10)
							staggerEntity(targetPatch, 2, false);
					}
					break;
				case "oeufi_auto1":
                case "oeufi_innate":
					if (targetPatch != null)
						SharedFunctions.hitstunEntity(targetPatch, 3, false, 0);
                    TremorEffect.incrementTremor(target, 0, 1);
					break;
				case "oeufi_auto2":
					TremorEffect.incrementTremor(target, 1, 1);
					break;
                case "oeufi_dash":
                case "oeufi_auto3":
					TremorEffect.incrementTremor(target, 1, 1);
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.OUFI_PIERCE.get().getRegistryName()));
					break;
                case "oeufi_innate_2":
					TremorEffect.burstTremor(target, true);
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.OUFI_SWIPE_DOWN.get().getRegistryName()));
					if (TremorEffect.detectTremorType(target) instanceof TremorDecayEffect) {

						if (sourceentity instanceof PlayerEntity) {
							PlayerEntity player = (PlayerEntity)sourceentity;
							((PlayerPatch)entitypatch).setStamina(Math.min(((PlayerPatch)entitypatch).getMaxStamina(), ((PlayerPatch)entitypatch).getStamina() + ((PlayerPatch)entitypatch).getMaxStamina() * 0.3F));
							EntityTick.chargeBlips(player, 1, true);
						}
					}


					if (targetPatch != null)
						pummelDownEntity(targetPatch, 2);
					break;
				case "special1":
					TremorEffect.incrementTremor(target, 0, 3);
					entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putBoolean("specialHit", true);
					if (targetPatch != null)
						SharedFunctions.hitstunEntity(targetPatch, 3, false, 0);
					break;
				case "special2":
					TremorEffect.incrementTremor(target, 1, 1);
					entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putBoolean("specialHit", true);
					if (targetPatch != null)
						SharedFunctions.hitstunEntity(targetPatch, 3, false, 0);
					break;
				case "special3":

					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.OUFI_PIERCE.get().getRegistryName()));
					TremorEffect tremor = TremorEffect.detectTremorType(target);

					if (tremor != null) {
						if (tremor instanceof TremorDecayEffect) {
							TremorEffect.burstTremor(target, true);

							if (sourceentity instanceof PlayerEntity) {
								PlayerEntity player = (PlayerEntity)sourceentity;
								((PlayerPatch)entitypatch).setStamina(Math.min(((PlayerPatch)entitypatch).getMaxStamina(), ((PlayerPatch)entitypatch).getStamina() + ((PlayerPatch)entitypatch).getMaxStamina() * 0.3F));
								EntityTick.chargeBlips(player, 1, true);
							}

						} else if (tremor.getPotency(target) > 15) {
							((TremorEffect)EgoWeaponsEffects.TREMOR_DECAY.get()).amplitudeConversion(target);
						}
					}



					break;
			}


		}

		return true;
	}


	public static float modifyDamageAmount(LivingEntity target, LivingEntity source, float dmgMult, DamageSource damageSource) {

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		boolean finale = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO).orElse(false);

		if (source.hasEffect(EgoWeaponsEffects.OBLIGATION_FULLFILLMENT.get())) {
			if (target.hasEffect(EgoWeaponsEffects.TREMOR_DECAY.get()) && finale) {
				SharedFunctions.incrementBonusDamage(damageSource, 0.25f);
				dmgMult += 0.25f;
			}
		}

		if (source.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.OEUFI_CHESTPLATE.get())) {
			TremorEffect tremor = TremorEffect.detectTremorType(target);
			if (tremor != null) {
				int potency = tremor.getPotency(target);

				SharedFunctions.incrementBonusDamage(damageSource, Math.min(potency*0.01f,0.4f));
				dmgMult += Math.min(potency*0.01f,0.4f);
			}
		}


		return dmgMult;
	}

	public static StaticAnimation.Event[] contractOpen() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[3];
		events[0] = StaticAnimation.Event.create(0.0f, (entitypatch) -> {
			if (entitypatch.getOriginal().level.isClientSide()) {
				entitypatch.playSound(EgoWeaponsSounds.OUFI_CONTRACT_OPEN, 1, 1, 1);
			}

			if (entitypatch.getOriginal().getItemInHand(Hand.OFF_HAND).isEmpty()) {
				entitypatch.getOriginal().setItemInHand(Hand.OFF_HAND, EgoWeaponsItems.OEUFI_CONTRACT.get().getDefaultInstance());
			}
		}, StaticAnimation.Event.Side.BOTH);
		events[1] = StaticAnimation.Event.create(0.5f, (entitypatch) -> {
			entitypatch.getOriginal().addEffect(new EffectInstance(EgoWeaponsEffects.OBLIGATION_FULLFILLMENT.get(), 440, 0));
			entitypatch.getOriginal().addEffect(new EffectInstance(EgoWeaponsEffects.RESILIENCE.get(), 9999, 1));
		}, StaticAnimation.Event.Side.BOTH);
		events[2] = StaticAnimation.Event.create(3.1f, (entitypatch) -> {
			entitypatch.playSound(EgoWeaponsSounds.OUFI_CONTRACT_FINISH, 1, 1, 1);
			if (entitypatch.getOriginal() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) entitypatch.getOriginal();
				if (!player.level.isClientSide()) {
					MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();
					if (mcserv != null)
						mcserv.getPlayerList().broadcastMessage(
								new StringTextComponent((player.getDisplayName().getString() + ">")).withStyle(TextFormatting.DARK_PURPLE).withStyle(TextFormatting.ITALIC).append(new StringTextComponent(" I am in attendance.").withStyle(TextFormatting.LIGHT_PURPLE).withStyle(TextFormatting.ITALIC)), ChatType.CHAT,
								player.getUUID());
				}
			}

		}, StaticAnimation.Event.Side.SERVER);
		return events;
	}
	public static StaticAnimation.Event[] counterEvent1() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(0.55f, (entitypatch) -> {
			entitypatch.playAnimationSynchronized(OeufiAssocMovesetAnims.OEUFI_PARRY_COUNTER_ATTACK, 0);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] contractEvent() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[3];
		events[0] = StaticAnimation.Event.create(0.0f, (entitypatch) -> {
				if (!entitypatch.getOriginal().level.isClientSide()) {
					entitypatch.playSound(EgoWeaponsSounds.OUFI_CONTRACT_INTERRUPT, 2, 1, 1);
					entitypatch.playSound(EgoWeaponsSounds.METAL_CLASH, 1, 1, 1);
				}

				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.2,-0.4), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_R", false);
		}, StaticAnimation.Event.Side.BOTH);
		events[1] = StaticAnimation.Event.create(0.40f, (entitypatch) -> {
					if (!entitypatch.getOriginal().level.isClientSide())
						entitypatch.playSound(EgoWeaponsSounds.OUFI_CONTRACT_COUNTER, 1, 1, 1);
				}, StaticAnimation.Event.Side.SERVER);
		events[2] = StaticAnimation.Event.create(0.60f, (entitypatch) -> {
			entitypatch.playAnimationSynchronized(OeufiAssocMovesetAnims.OEUFI_COUNTER_CONTRACT, 0);

			if (entitypatch.getOriginal() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) entitypatch.getOriginal();

				DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.oeufi_armor.2", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);

			}



		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] special1Event(float timeStamp) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];

		events[0] = StaticAnimation.Event.create(0.01f, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("specialHit");
		}, StaticAnimation.Event.Side.BOTH);

		events[1] = StaticAnimation.Event.create(timeStamp, (entitypatch) -> {
			if (entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getBoolean("specialHit")) {
				entitypatch.playAnimationSynchronized(OeufiAssocMovesetAnims.OEUFI_SPECIAL_2, 0.01f);
			}
		}, StaticAnimation.Event.Side.BOTH);

		return events;
	}
	public static StaticAnimation.Event[] special2Event(float timeStamp) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];

		events[0] = StaticAnimation.Event.create(0.01f, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("specialHit");
		}, StaticAnimation.Event.Side.BOTH);

		events[1] = StaticAnimation.Event.create(timeStamp, (entitypatch) -> {
			if (entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getBoolean("specialHit")) {
				entitypatch.playAnimationSynchronized(OeufiAssocMovesetAnims.OEUFI_SPECIAL_3, 0.01f);
			}
		}, StaticAnimation.Event.Side.BOTH);

		return events;
	}

}

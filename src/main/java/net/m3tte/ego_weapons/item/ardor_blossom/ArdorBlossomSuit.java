
package net.m3tte.ego_weapons.item.ardor_blossom;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.AttackCycleType;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.item.NoArmorToughnessMaterial;
import net.m3tte.ego_weapons.item.magic_bullet.MagicBulletArmor;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.procedures.DelayedEvent;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoWeaponsArmor;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.function.Consumer;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class ArdorBlossomSuit extends GenericEgoWeaponsArmor {

	static IArmorMaterial firefistArmor = new IArmorMaterial() {
		@Override
		public int getDurabilityForSlot(EquipmentSlotType slot) {
			return new int[]{13, 15, 15, 11}[slot.getIndex()] * 999;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType slot) {
			return new int[]{0, 0, 20, 0}[slot.getIndex()];
		}

		@Override
		public int getEnchantmentValue() {
			return 9;
		}

		@Override
		public SoundEvent getEquipSound() {
			return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather"));
		}

		@Override
		public Ingredient getRepairIngredient() {
			return null;
		}

		@OnlyIn(Dist.CLIENT)
		@Override
		public String getName() {
			return "ardor_blossom";
		}

		@Override
		public float getToughness() {
			return 6f;
		}

		@Override
		public float getKnockbackResistance() {
			return 0.1f;
		}
	};

	public ArdorBlossomSuit(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}

	public ArdorBlossomSuit(IArmorMaterial armorMaterial, EquipmentSlotType slot, Properties props, float redResistance, float whiteResistance, float blackResistance, float paleResistance, float slashResistance, float pierceResistance, float bluntResistance, float bonusStagger, float bonusSanity) {
		super(armorMaterial, slot, props, redResistance, whiteResistance, blackResistance, paleResistance,slashResistance, pierceResistance, bluntResistance, bonusStagger, bonusSanity);


	}


	public static void setWingActivationState(Entity target, boolean state, int delay) {
		new DelayedEvent(delay, (a) -> {

			if (!target.getPersistentData().contains("ardorWingAnimationData")) {
				target.getPersistentData().put("ardorWingAnimationData", new CompoundNBT());
			}

			CompoundNBT data = target.getPersistentData().getCompound("ardorWingAnimationData");
			if (!state) {
				System.out.println("PROCCING WING DEACTIVATION AT TICK COUNT : "+target.tickCount);
				data.putInt("lastWingDeactivation", target.tickCount);
			}
			data.putBoolean("wingActivation", state);
		});
	}

	public static void setWingRotationState(Entity target, int delay, int targetTime, int triggerTimePre, int triggerTimePost, float targetRotation, float overrideStartRot) {
		new DelayedEvent(delay, (a) -> {
			if (!target.getPersistentData().contains("ardorWingAnimationData")) {
				target.getPersistentData().put("ardorWingAnimationData", new CompoundNBT());
			}

			CompoundNBT data = target.getPersistentData().getCompound("ardorWingAnimationData");

			data.putInt("targetTime", target.tickCount + targetTime);
			data.putInt("triggerTimePre", triggerTimePre);
			data.putInt("triggerTimePost", triggerTimePost);
			data.putFloat("targetRotation", targetRotation);
			data.putFloat("overrideStartRot", overrideStartRot);
		});
	}
	public static void setWingRotationState(Entity target, int delay, int targetTime, int triggerTimePre, int triggerTimePost, float targetRotation) {
		setWingRotationState(target, delay,targetTime,triggerTimePre, triggerTimePost, targetRotation,-1);
	}

	public static boolean getWingActivationState(Entity target) {
		if (!target.getPersistentData().contains("ardorWingAnimationData")) {
			target.getPersistentData().put("ardorWingAnimationData", new CompoundNBT());
		}

		CompoundNBT data = target.getPersistentData().getCompound("ardorWingAnimationData");

		return data.getBoolean("wingActivation");
	}

	public static CompoundNBT getWingMetadata(Entity target) {
		if (!target.getPersistentData().contains("ardorWingAnimationData")) {
			target.getPersistentData().put("ardorWingAnimationData", new CompoundNBT());
		}
        return target.getPersistentData().getCompound("ardorWingAnimationData");
	}



	/**
	 *   "desc.ego_weapons.ardor_blossom_suit.passive2.2": "§7Deal §f+7.5% §7Damage for every §f5 \uE002 §cBurn §7Potency on Self. §8[Max 45%]",
	 *   "desc.ego_weapons.ardor_blossom_suit.passive2.3": "§7Deal §f+1% §7Damage for every §f\uE002 §cBurn §7Potency on Target. §8[Max 20%]",
	 */
	public static float modifyDamageAmount(LivingEntity target, LivingEntity source, float dmgMult, DamageSource damageSource) {

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);


		int targetPotency = EgoWeaponsEffects.BURN.get().getPotency(target);
		int selfPotency = EgoWeaponsEffects.BURN.get().getPotency(source);

		if (selfPotency >= 5) {
			int cnt = selfPotency / 5;
			dmgMult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.075f * cnt,0.45f));
		}

		if (targetPotency >= 0) {
			dmgMult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.2f, targetPotency * 0.01f));
		}

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		int potencyInflictionBuff = 0;

		if (selfPotency >= 25)
			potencyInflictionBuff = 2;
		else if (selfPotency >= 15)
			potencyInflictionBuff = 1;

		if (potencyInflictionBuff > 0 && currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

			AttackCycleType cycleType = (currentanim.getRealAnimation()).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE).orElse(AttackCycleType.AUTO);

			if (cycleType.equals(AttackCycleType.SPECIAL) || cycleType.equals(AttackCycleType.INNATE))
				EgoWeaponsEffects.BURN.get().increment(target, 0, potencyInflictionBuff);
		}

		return dmgMult;
	}

	// Texture Override
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "ego_weapons:textures/entities/ardor_blossom/ardor_blossom_star.png";
	}

	public static Item getArmorForSlot(EquipmentSlotType slot) {
		switch (slot) {
			default:
				return null;
			case CHEST:
				return chest;
		}
	}

	public static void tickEvent(LivingEntity wearer) {
		ServerWorld serverLVL = null;

		if (!wearer.level.isClientSide()) {
			serverLVL = (ServerWorld) wearer.level;
		}

		int burnPot = EgoWeaponsEffects.BURN.get().getPotency(wearer);

		if (burnPot >= 6) {
			int potInc = Math.min(5,burnPot / 6);

			EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(wearer, potInc, potInc);

			if (serverLVL != null && wearer.tickCount % Math.max(1, 4 - potInc) == 0) {

				EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), Math.min(1, potInc - 2), wearer.getX(), wearer.getY() + wearer.getBbHeight()/2, wearer.getZ(), 0.01, 0.3f, 0.5f, 0.5f, 1f, 0.5f));
			}
		}

		if (wearer.getHealth() / wearer.getMaxHealth() <= 0.2) {
			if (serverLVL != null) {
				serverLVL.sendParticles(ParticleTypes.LAVA, wearer.getX(), wearer.getY(), wearer.getZ(), 1, 0, 0.2f, 0.3f, 0.2f);
			}

			if (wearer instanceof PlayerEntity) {
				if (EmotionSystem.getEmotionLevel((PlayerEntity) wearer) >= 3) {
					EgoWeaponsEffects.POWER_UP.get().increment(wearer, 2, 2);
					if (burnPot < 30) {
						if(serverLVL != null) {
							serverLVL.playSound(null, wearer.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundCategory.PLAYERS, 1, 1);
						}
						EgoWeaponsEffects.BURN.get().increment(wearer, 2, 30 - burnPot);
					}
				}

			}

		}

	}


	static Item chest = new ArdorBlossomSuit(firefistArmor, EquipmentSlotType.CHEST, new Properties().tab(EgoWeaponsCreativeTabs.EGO_WEAPONS), 0.6f, 1.5f, 0.8f ,1f, 1f, 1.3f, 0.7f, 4, 0) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);


			ArdorBlossomStarModel m = new ArdorBlossomStarModel();
			armorModel.body = m.Body;
			armorModel.leftArm = m.LeftArm;
			armorModel.rightArm = m.RightArm;
			armorModel.crouching = living.isCrouching();
			armorModel.riding = defaultModel.riding;
			armorModel.young = living.isBaby();
			return armorModel;
		}




		@Override
		public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(new TranslationTextComponent("desc.ego_weapons.ardor_blossom_suit.desc"));
			list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

			list.add(new StringTextComponent("= - - - - - - - [Page: " + ((EgoWeaponsKeybinds.getUiPage() % 5) + 1) + "/5] - - - - - - - =").withStyle(TextFormatting.GRAY));
			list.add(new TranslationTextComponent("desc.ego_weapons.risk.waw"));
			list.add(new StringTextComponent(" "));
			switch (EgoWeaponsKeybinds.getUiPage() % 5) {
				case 0:
					resistanceMods(itemstack, world, list, flag);
					break;
				case 1:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"burn"});
					else
						generateDescription(list, "ardor_blossom_suit", "passive", 3);
					break;
				case 2:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"offense_up", "burn"});
					else
						generateDescription(list, "ardor_blossom_suit", "passive2", 5);
					break;
				case 3:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"burn", "power_up"});
					else
						generateDescription(list, "ardor_blossom_suit", "passive3", 4, true);
					break;
				case 4:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"offense_up", "burn", "ego_att_ardor"});
					else {
						generateDescription(list, "ardor_blossom_suit", "ability", 5, true);
						list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
						generateDescription(list, "ardor_blossom_suit", "ability2", 4);
					}
					break;
			}

			list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
		}

	};



	public class ArdorBlossomStarModel extends EntityModel<Entity> {

		private final ModelRenderer Body;
		private final ModelRenderer BodyLayer_r1;
		private final ModelRenderer BodyLayer_r2;
		private final ModelRenderer LeftArm;
		private final ModelRenderer RightArm;

		public ArdorBlossomStarModel() {
			texWidth = 128;
			texHeight = 70;

			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(4, 4).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 0.0F, 0.2F, false);
			Body.texOffs(23, 0).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 14.0F, 5.0F, -0.2F, false);
			Body.texOffs(17, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 14.0F, 4.0F, 0.5F, false);

			BodyLayer_r1 = new ModelRenderer(this);
			BodyLayer_r1.setPos(-1.25F, 2.25F, -0.75F);
			Body.addChild(BodyLayer_r1);
			setRotationAngle(BodyLayer_r1, 0.0F, 0.0F, 0.0873F);
			BodyLayer_r1.texOffs(47, 0).addBox(-4.0F, -3.0F, -2.0F, 5.0F, 4.0F, 0.0F, 0.0F, false);

			BodyLayer_r2 = new ModelRenderer(this);
			BodyLayer_r2.setPos(4.25F, 2.0F, -0.75F);
			Body.addChild(BodyLayer_r2);
			setRotationAngle(BodyLayer_r2, 0.0F, 0.0F, -0.0873F);
			BodyLayer_r2.texOffs(47, 0).addBox(-4.0F, -3.0F, -2.0F, 5.0F, 4.0F, 0.0F, 0.0F, true);

			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(16, 51).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);
			LeftArm.texOffs(43, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, true);

			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(16, 51).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.15F, false);
			RightArm.texOffs(43, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
			RightArm.texOffs(58, 0).addBox(-3.5F, -1.1F, -2.5F, 5.0F, 3.0F, 5.0F, -0.1F, false);
			RightArm.texOffs(59, 8).addBox(-5.5F, -3.1F, -4.5F, 9.0F, 7.0F, 9.0F, -2.05F, false);
		}

		@Override
		public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
			//previously the render function, render code was moved to a method below
		}

		@Override
		public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
			Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.xRot = x;
			modelRenderer.yRot = y;
			modelRenderer.zRot = z;
		}

	}
	
}
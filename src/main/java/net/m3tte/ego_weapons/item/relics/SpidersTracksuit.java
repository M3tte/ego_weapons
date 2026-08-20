
package net.m3tte.ego_weapons.item.relics;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.EgoWeaponsCreativeTabs;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.gameasset.movesets.ArayashikiMovesetAnims;
import net.m3tte.ego_weapons.item.NoArmorToughnessMaterial;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.network.packages.KeybindPackages;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.procedures.TooltipFuncs;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.m3tte.ego_weapons.world.capabilities.UtilitySystems;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoWeaponsArmor;
import net.minecraft.client.Minecraft;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.*;
import static net.m3tte.ego_weapons.world.capabilities.UtilitySystems.generateAttackContext;

public class SpidersTracksuit extends GenericEgoWeaponsArmor {

	static IArmorMaterial tracksuitArmor = new IArmorMaterial() {
		@Override
		public int getDurabilityForSlot(EquipmentSlotType slot) {
			return new int[]{13, 15, 16, 11}[slot.getIndex()] * 999;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType slot) {
			return new int[]{0, 0, 19, 0}[slot.getIndex()];
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
			return "spider_tracksuit";
		}

		@Override
		public float getToughness() {
			return 9f;
		}

		@Override
		public float getKnockbackResistance() {
			return 0.1f;
		}
	};

	public SpidersTracksuit(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}

	// Texture Override
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "ego_weapons:textures/entities/tracksuit.png";
	}

	public static Item getArmorForSlot(EquipmentSlotType slot) {
		switch (slot) {
			default: return null;
			case LEGS: return legs;
			case CHEST: return chest;
		}
	}

	static Item legs = new SpidersTracksuit(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.LEGS, new Properties().tab(EgoWeaponsCreativeTabs.EGO_WEAPONS)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			SpidersTracksuitModel m = new SpidersTracksuitModel();
			armorModel.body = m.Dummy;
			armorModel.leftLeg = m.LeftLeg;
			armorModel.rightLeg = m.RightLeg;
			armorModel.crouching = living.isCrouching();
			armorModel.riding = defaultModel.riding;
			armorModel.young = living.isBaby();

			return armorModel;
		}

		@Override
		public float getToughness() {
			return 0;
		}

		@Override
		public int getDefense() {
			return 0;
		}
	};

	public SpidersTracksuit(IArmorMaterial armorMaterial, EquipmentSlotType slot, Properties props, float redResistance, float whiteResistance, float blackResistance, float paleResistance, float slashResistance, float pierceResistance, float bluntResistance, float bonusStagger, float bonusSanity) {
		super(armorMaterial, slot, props, redResistance, whiteResistance, blackResistance, paleResistance,slashResistance, pierceResistance, bluntResistance, bonusStagger, bonusSanity);
	}
	static Item chest = new SpidersTracksuit(tracksuitArmor, EquipmentSlotType.CHEST, new Properties().tab(EgoWeaponsCreativeTabs.EGO_WEAPONS), 0.9f, 1.3f, 1f ,1.2f, 0.7f, 1f, 1.3f, 5, 5) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);


			SpidersTracksuitModel m = new SpidersTracksuitModel();
			armorModel.head = m.Head;
			armorModel.hat = m.Head;
			armorModel.body = m.Body;
			armorModel.leftArm = m.LeftArm;
			armorModel.rightArm = m.RightArm;
			armorModel.crouching = living.isCrouching();
			armorModel.riding = defaultModel.riding;
			armorModel.young = living.isBaby();
			return armorModel;
		}

		@OnlyIn(Dist.CLIENT)
		@Override
		public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			TooltipFuncs.generateItemDescription(list, "desc.ego_weapons.spider_tracksuit.desc");
			list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

			list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 5) + 1) + "/5] - - - - - - - =").withStyle(TextFormatting.GRAY));
			list.add(new TranslationTextComponent("desc.ego_weapons.risk.3"));
			list.add(new StringTextComponent(" "));

			boolean firemode = Minecraft.getInstance().player != null ? KeybindPackages.getFireMode(Minecraft.getInstance().player) : false;

			switch (EgoWeaponsKeybinds.getUiPage() % 5) {
				case 0:
					resistanceMods(itemstack, world, list, flag);
					break;
				case 1:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"offense_up"});
					else
						generateDescription(list, "arayashiki_armor", "passive", 4);
					break;
				case 2:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"poise"});
					else
						generateDescription(list, "arayashiki_armor", "passive2", 2);
					break;
				case 3:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"poise"});
					else
						generateDescription(list, "arayashiki_armor", "passive3", 6);
					break;
				case 4:
					if (EgoWeaponsKeybinds.isHoldingShift())
						if (firemode) {
							generateStatusDescription(list, new String[]{"loss_of_self","protection","offense_up","poise"});
						} else {
							generateStatusDescription(list, new String[]{"defense_down","offense_up","loss_of_self"});
						}

					else {
						if (firemode) {
							generateDescription(list,"arayashiki_armor", "ability", 5, true);
						} else {
							generateDescription(list,"arayashiki_armor", "ability2", 4, true);
						}
					}
					break;
			}
			generateStatusHelp(list);
		}

	};

	public static boolean testTryDeathAvoid(LivingEntity target, float amount) {
		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		int poise = EgoWeaponsEffects.POISE.get().getPotency(target);

		if (poise >= 15 && entitypatch != null && amount <= 15) {
			if (!target.level.isClientSide()) {
				entitypatch.playAnimationSynchronized(ArayashikiMovesetAnims.ARAYASHIKI_AUTO_EVADE, 0);
			}

			EgoWeaponsEffects.POISE.get().decrement(target, 0, 10);

			return true;
		}

		return false;
	}

	public static float modifyDamageAmount(LivingEntity target, LivingEntity source, float mult, DamageSource damageSource, boolean crit) {

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		UtilitySystems.EGOAttackContext context = generateAttackContext(entitypatch);

		int emotionLevel = source instanceof PlayerEntity ? EmotionSystem.getEmotionLevel((PlayerEntity) source) : 0;

		mult += SharedFunctions.incrementBonusDamage(damageSource, 0.151f - 0.05f * emotionLevel);

		float healthPercent = source.getHealth() / source.getMaxHealth();

		if (healthPercent <= 0.4f) {
			mult += SharedFunctions.incrementBonusDamage(damageSource, 0.1f);
		}

		if (crit) {
			mult += SharedFunctions.incrementBonusDamage(damageSource, 0.1f);
		}

		if (context.isValidEgoAnimation()) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));


			if (context.isFinalCoin()) {
				EgoWeaponsEffects.POISE.get().increment(source, 1, 0);
			}
		}

		return mult;
	}

	public static float modifyDamageAmountTarget(LivingEntity target, LivingEntity source, float mult, DamageSource damageSource, boolean crit) {

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		UtilitySystems.EGOAttackContext context = generateAttackContext(entitypatch);

		int emotionLevel = target instanceof PlayerEntity ? EmotionSystem.getEmotionLevel((PlayerEntity) target) : 0;

		mult -= SharedFunctions.incrementBonusDamage(damageSource, 0.151f - 0.05f * emotionLevel);

		float healthPercent = target.getHealth() / target.getMaxHealth();

		if (healthPercent <= 0.4f) {
			mult -= SharedFunctions.incrementBonusDamage(damageSource, 0.1f);
		}


		return mult;
	}



	// Made with Blockbench 4.7.1
	// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
	// Paste this class into your mod and generate all required imports
	public static class SpidersTracksuitModel extends EntityModel<Entity> {
		private final ModelRenderer Dummy;
		private final ModelRenderer Head;
		private final ModelRenderer Body;
		private final ModelRenderer Suit_r1;
		private final ModelRenderer RightArm;
		private final ModelRenderer LeftArm;
		private final ModelRenderer RightLeg;
		private final ModelRenderer right_leg_l1;
		private final ModelRenderer LeftLeg;
		private final ModelRenderer left_leg_l1;

		public SpidersTracksuitModel() {
			texWidth = 72;
			texHeight = 64;

			Dummy = new ModelRenderer(this);
			Head = new ModelRenderer(this);
			Head.setPos(0.0F, 0.0F, 0.0F);
			setRotationAngle(Head, -0.1047F, 0.0873F, 0.0F);


			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(24, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 0.0F, 0.27F, false);
			Body.texOffs(48, 32).addBox(-4.0F, -1.2F, -2.0F, 8.0F, 1.0F, 4.0F, 0.15F, false);
			Body.texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.4F, false);
			Body.texOffs(22, 41).addBox(0.5F, -1.2F, -2.325F, 0.0F, 1.0F, 1.0F, 0.1F, false);
			Body.texOffs(22, 41).addBox(0.5F, -0.2F, -2.425F, 0.0F, 12.0F, 1.0F, 0.1F, false);
			Body.texOffs(22, 41).addBox(0.5F, 11.3F, -2.425F, 0.0F, 1.0F, 1.0F, 0.15F, false);

			Suit_r1 = new ModelRenderer(this);
			Suit_r1.setPos(4.5F, 20.0F, -11.575F);
			Body.addChild(Suit_r1);
			setRotationAngle(Suit_r1, -2.7201F, 0.3614F, -2.9844F);
			Suit_r1.texOffs(12, 50).addBox(2.0F, -24.2F, -3.0F, 3.0F, 4.0F, 1.0F, -1.1F, false);

			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(56, 48).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.26F, false);
			RightArm.texOffs(48, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.35F, false);

			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(56, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.26F, true);
			LeftArm.texOffs(48, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.35F, true);

			RightLeg = new ModelRenderer(this);
			RightLeg.setPos(-1.9F, 12.0F, 0.0F);
			setRotationAngle(RightLeg, 0.192F, 0.0F, 0.0349F);
			RightLeg.texOffs(24, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.3F, false);
			RightLeg.texOffs(39, 47).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.5F, false);

			right_leg_l1 = new ModelRenderer(this);
			right_leg_l1.setPos(1.9F, 9.925F, 0.8F);
			RightLeg.addChild(right_leg_l1);
			setRotationAngle(right_leg_l1, 0.3927F, 0.0F, 0.0F);
			right_leg_l1.texOffs(44, 51).addBox(-2.9F, -3.0F, -2.0F, 2.0F, 1.0F, 0.0F, 0.5F, false);

			LeftLeg = new ModelRenderer(this);
			LeftLeg.setPos(1.9F, 12.0F, 0.0F);
			setRotationAngle(LeftLeg, -0.1745F, 0.0F, -0.0349F);
			LeftLeg.texOffs(24, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.3F, false);
			LeftLeg.texOffs(39, 47).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.5F, false);

			left_leg_l1 = new ModelRenderer(this);
			left_leg_l1.setPos(-1.9F, 10.075F, 0.825F);
			LeftLeg.addChild(left_leg_l1);
			setRotationAngle(left_leg_l1, 0.3927F, 0.0F, 0.0F);
			left_leg_l1.texOffs(44, 51).addBox(0.9F, -3.0F, -2.0F, 2.0F, 1.0F, 0.0F, 0.5F, false);
		}

		@Override
		public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
			//previously the render function, render code was moved to a method below
		}

		@Override
		public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
			Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			RightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.xRot = x;
			modelRenderer.yRot = y;
			modelRenderer.zRot = z;
		}
	}

}

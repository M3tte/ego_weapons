
package net.m3tte.ego_weapons.item.oeufi;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.item.NoArmorToughnessMaterial;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.potion.countEffects.TremorEffect;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoWeaponsArmor;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
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
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class OeufiArmor extends GenericEgoWeaponsArmor {

	static IArmorMaterial solemnLamentArmor = new IArmorMaterial() {
		@Override
		public int getDurabilityForSlot(EquipmentSlotType slot) {
			return new int[]{13, 15, 16, 11}[slot.getIndex()] * 999;
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
			return "oeufi";
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

	public static float modifyDamageAmount(LivingEntity target, LivingEntity source, float dmgMult, DamageSource damageSource) {

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		boolean finale = (currentanim.getRealAnimation()).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO).orElse(false);

		TremorEffect tremor = TremorEffect.detectTremorType(target);
		if (tremor != null) {
			int potency = tremor.getPotency(target);

			SharedFunctions.incrementBonusDamage(damageSource, Math.min(potency*0.01f,0.4f));
			dmgMult += Math.min(potency*0.01f,0.4f);
		}


		return dmgMult;
	}



	public OeufiArmor(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}

	// Texture Override
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "ego_weapons:textures/entities/oeufi_armor.png";
	}

	public static Item getArmorForSlot(EquipmentSlotType slot) {
		switch (slot) {
			default: return null;
			case CHEST: return chest;
			case HEAD: return hat;
			case LEGS: return pants;
		}
	}

	public OeufiArmor(IArmorMaterial armorMaterial, EquipmentSlotType slot, Properties props, float redResistance, float whiteResistance, float blackResistance, float paleResistance, float slashResistance, float pierceResistance, float bluntResistance, float bonusStagger, float bonusSanity) {
		super(armorMaterial, slot, props, redResistance, whiteResistance, blackResistance, paleResistance,slashResistance, pierceResistance, bluntResistance, bonusStagger, bonusSanity);
	}
	static Item chest = new OeufiArmor(solemnLamentArmor, EquipmentSlotType.CHEST, new Properties().tab(ItemGroup.TAB_COMBAT), 0.9f, 1.3f, 0.7f ,1.5f, 1.3f, 0.7f, 1f, 0, 0) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);


			OeufiArmorModel m = new OeufiArmorModel();
			armorModel.head = m.Head;
			armorModel.hat = m.Head;
			armorModel.body = m.Body;
			armorModel.leftArm = m.LeftArm;
			armorModel.rightArm = m.RightArm;
			armorModel.leftLeg = m.LeftLeg;
			armorModel.rightLeg = m.RightLeg;
			armorModel.crouching = living.isCrouching();
			armorModel.riding = defaultModel.riding;
			armorModel.young = living.isBaby();
			return armorModel;
		}

		@Override
		public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(new StringTextComponent("Manufactured by Kai Atelier").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
			list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

			list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 3) + 1) + "/3] - - - - - - - =").withStyle(TextFormatting.GRAY));
			list.add(new TranslationTextComponent("desc.ego_weapons.risk.he"));
			list.add(new StringTextComponent(" "));
			switch (EgoWeaponsKeybinds.getUiPage() % 3) {
				case 0:
					resistanceMods(itemstack, world, list, flag);
					break;
				case 1:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"tremor"});
					else
						generateDescription(list, "oufi_armor", "passive", 1);
					break;
				case 2:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"obligation", "tremor_burst", "tremor_decay", "resilience"});
					else
						generateDescription(list,"oufi_armor", "ability", 7);
					break;
			}

			list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
		}

	};
	static Item hat = new OeufiArmor(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.HEAD, new Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			OeufiArmorModel m = new OeufiArmorModel();
			armorModel.head = m.Dummy;
			armorModel.hat = m.Head;
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

	static Item pants = new OeufiArmor(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.LEGS, new Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			OeufiArmorModel m = new OeufiArmorModel();
			armorModel.leftLeg = m.LeftLeg;
			armorModel.rightLeg = m.RightLeg;
			armorModel.body = m.Dummy;
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

	// Made with Blockbench 4.7.1
	// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
	// Paste this class into your mod and generate all required imports
	public static class OeufiArmorModel extends EntityModel<Entity> {
		private final ModelRenderer Dummy;
		private final ModelRenderer Body;
		private final ModelRenderer RightArm;
		private final ModelRenderer Armpads;
		private final ModelRenderer RightArmLayer_r1;
		private final ModelRenderer RightArmLayer_r2;
		private final ModelRenderer RightArmLayer_r3;
		private final ModelRenderer RightArmLayer_r4;
		private final ModelRenderer RightArmLayer_r5;
		private final ModelRenderer RightArmLayer_r6;
		private final ModelRenderer RightArmLayer_r7;
		private final ModelRenderer LeftArm;
		private final ModelRenderer Armpads2;
		private final ModelRenderer RightArmLayer_r8;
		private final ModelRenderer RightArmLayer_r9;
		private final ModelRenderer RightArmLayer_r10;
		private final ModelRenderer RightArmLayer_r11;
		private final ModelRenderer RightArmLayer_r12;
		private final ModelRenderer RightArmLayer_r13;
		private final ModelRenderer RightArmLayer_r14;
		private final ModelRenderer Head;
		private final ModelRenderer cube_r1;
		private final ModelRenderer cube_r2;
		private final ModelRenderer cube_r3;
		private final ModelRenderer RightLeg;
		private final ModelRenderer LeftLeg;

		public OeufiArmorModel() {
			texWidth = 145;
			texHeight = 80;
			Dummy = new ModelRenderer(this);
			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 0.0F, 0.15F, false);
			Body.texOffs(8, 0).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 13.0F, 5.0F, -0.3F, false);
			Body.texOffs(37, 1).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 14.0F, 4.0F, 0.5F, false);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.4F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(16, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.28F, false);
			RightArm.texOffs(8, 70).addBox(-4.0F, -3.0F, -3.0F, 6.0F, 3.0F, 6.0F, -0.25F, false);

			Armpads = new ModelRenderer(this);
			Armpads.setPos(1.075F, 0.625F, 1.825F);
			RightArm.addChild(Armpads);


			RightArmLayer_r1 = new ModelRenderer(this);
			RightArmLayer_r1.setPos(0.0F, 0.0F, 0.0F);
			Armpads.addChild(RightArmLayer_r1);
			setRotationAngle(RightArmLayer_r1, 2.7142F, -0.8821F, -3.0298F);
			RightArmLayer_r1.texOffs(65, 74).addBox(-1.3431F, -1.2467F, -0.5912F, 3.0F, 3.0F, 3.0F, -0.3F, false);

			RightArmLayer_r2 = new ModelRenderer(this);
			RightArmLayer_r2.setPos(-2.25F, -1.025F, -0.075F);
			Armpads.addChild(RightArmLayer_r2);
			setRotationAngle(RightArmLayer_r2, 2.7087F, 0.6833F, 2.8441F);
			RightArmLayer_r2.texOffs(80, 73).addBox(-1.3431F, -1.0F, -0.5858F, 3.0F, 4.0F, 3.0F, -0.3F, false);

			RightArmLayer_r3 = new ModelRenderer(this);
			RightArmLayer_r3.setPos(-3.775F, -0.05F, 0.425F);
			Armpads.addChild(RightArmLayer_r3);
			setRotationAngle(RightArmLayer_r3, 2.9157F, 0.7608F, 3.1331F);
			RightArmLayer_r3.texOffs(65, 74).addBox(-1.3431F, -1.2467F, -0.5912F, 3.0F, 3.0F, 3.0F, -0.3F, false);

			RightArmLayer_r4 = new ModelRenderer(this);
			RightArmLayer_r4.setPos(-3.825F, -0.975F, -2.375F);
			Armpads.addChild(RightArmLayer_r4);
			setRotationAngle(RightArmLayer_r4, 0.0626F, 0.8208F, 0.3008F);
			RightArmLayer_r4.texOffs(80, 73).addBox(-1.3431F, -1.0F, -0.5858F, 3.0F, 4.0F, 3.0F, -0.3F, false);

			RightArmLayer_r5 = new ModelRenderer(this);
			RightArmLayer_r5.setPos(-4.15F, 0.0F, -3.675F);
			Armpads.addChild(RightArmLayer_r5);
			setRotationAngle(RightArmLayer_r5, -0.3491F, 0.7854F, 0.0F);
			RightArmLayer_r5.texOffs(65, 74).addBox(-1.3431F, -1.2467F, -0.5912F, 3.0F, 3.0F, 3.0F, -0.3F, false);

			RightArmLayer_r6 = new ModelRenderer(this);
			RightArmLayer_r6.setPos(-1.325F, -0.975F, -3.65F);
			Armpads.addChild(RightArmLayer_r6);
			setRotationAngle(RightArmLayer_r6, -0.4342F, -0.7106F, 0.2796F);
			RightArmLayer_r6.texOffs(80, 73).addBox(-1.3431F, -1.0F, -0.5858F, 3.0F, 4.0F, 3.0F, -0.3F, false);

			RightArmLayer_r7 = new ModelRenderer(this);
			RightArmLayer_r7.setPos(-0.1F, 0.0F, -3.95F);
			Armpads.addChild(RightArmLayer_r7);
			setRotationAngle(RightArmLayer_r7, -0.4073F, -0.7444F, -0.0004F);
			RightArmLayer_r7.texOffs(65, 74).addBox(-1.3431F, -1.2467F, -0.5912F, 3.0F, 3.0F, 3.0F, -0.3F, false);

			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.4F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(8, 70).addBox(-2.0F, -3.0F, -3.0F, 6.0F, 3.0F, 6.0F, -0.25F, true);
			LeftArm.texOffs(16, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.28F, true);

			Armpads2 = new ModelRenderer(this);
			Armpads2.setPos(-1.175F, 0.6F, -1.55F);
			LeftArm.addChild(Armpads2);
			setRotationAngle(Armpads2, 0.0F, 3.1416F, 0.0F);


			RightArmLayer_r8 = new ModelRenderer(this);
			RightArmLayer_r8.setPos(0.0F, 0.0F, 0.0F);
			Armpads2.addChild(RightArmLayer_r8);
			setRotationAngle(RightArmLayer_r8, 2.7142F, -0.8821F, -3.0298F);
			RightArmLayer_r8.texOffs(65, 74).addBox(-1.3431F, -1.2467F, -0.5912F, 3.0F, 3.0F, 3.0F, -0.3F, false);

			RightArmLayer_r9 = new ModelRenderer(this);
			RightArmLayer_r9.setPos(-2.25F, -1.025F, -0.075F);
			Armpads2.addChild(RightArmLayer_r9);
			setRotationAngle(RightArmLayer_r9, 2.7087F, 0.6833F, 2.8441F);
			RightArmLayer_r9.texOffs(80, 73).addBox(-1.3431F, -1.0F, -0.5858F, 3.0F, 4.0F, 3.0F, -0.3F, false);

			RightArmLayer_r10 = new ModelRenderer(this);
			RightArmLayer_r10.setPos(-4.025F, -0.05F, 0.475F);
			Armpads2.addChild(RightArmLayer_r10);
			setRotationAngle(RightArmLayer_r10, 2.7848F, 0.7608F, 3.1331F);
			RightArmLayer_r10.texOffs(65, 74).addBox(-1.3431F, -1.2467F, -0.5912F, 3.0F, 3.0F, 3.0F, -0.3F, false);

			RightArmLayer_r11 = new ModelRenderer(this);
			RightArmLayer_r11.setPos(-3.825F, -0.975F, -2.375F);
			Armpads2.addChild(RightArmLayer_r11);
			setRotationAngle(RightArmLayer_r11, 0.0626F, 0.8208F, 0.3008F);
			RightArmLayer_r11.texOffs(80, 73).addBox(-1.3431F, -1.0F, -0.5858F, 3.0F, 4.0F, 3.0F, -0.3F, false);

			RightArmLayer_r12 = new ModelRenderer(this);
			RightArmLayer_r12.setPos(-4.15F, 0.0F, -3.35F);
			Armpads2.addChild(RightArmLayer_r12);
			setRotationAngle(RightArmLayer_r12, -0.3491F, 0.7854F, 0.0F);
			RightArmLayer_r12.texOffs(65, 74).addBox(-1.3431F, -1.2467F, -0.5912F, 3.0F, 3.0F, 3.0F, -0.3F, false);

			RightArmLayer_r13 = new ModelRenderer(this);
			RightArmLayer_r13.setPos(-1.325F, -0.975F, -3.35F);
			Armpads2.addChild(RightArmLayer_r13);
			setRotationAngle(RightArmLayer_r13, -0.4342F, -0.7106F, 0.2796F);
			RightArmLayer_r13.texOffs(80, 73).addBox(-1.3431F, -1.0F, -0.5858F, 3.0F, 4.0F, 3.0F, -0.3F, false);

			RightArmLayer_r14 = new ModelRenderer(this);
			RightArmLayer_r14.setPos(-0.1F, 0.0F, -3.6F);
			Armpads2.addChild(RightArmLayer_r14);
			setRotationAngle(RightArmLayer_r14, -0.4073F, -0.7444F, -0.0004F);
			RightArmLayer_r14.texOffs(65, 74).addBox(-1.3431F, -1.2467F, -0.5912F, 3.0F, 3.0F, 3.0F, -0.3F, false);

			Head = new ModelRenderer(this);
			Head.setPos(1.0F, 1F, 0.0F);
			setRotationAngle(Head, -0.1047F, 0.0873F, 0.0F);
			Head.texOffs(88, 23).addBox(-6.5F, -9.1F, -5.5F, 11.0F, 3.0F, 11.0F, -0.5F, false);
			Head.texOffs(87, 38).addBox(-5.5F, -12.0F, -4.5F, 9.0F, 4.0F, 9.0F, -0.5F, false);

			cube_r1 = new ModelRenderer(this);
			cube_r1.setPos(-1.3F, 24.0F, -1.8F);
			Head.addChild(cube_r1);
			setRotationAngle(cube_r1, -0.2666F, -0.2288F, -0.0013F);
			cube_r1.texOffs(125, 10).addBox(-5.0F, -33.5F, -12.0F, 2.0F, 3.0F, 7.0F, -0.6F, false);

			cube_r2 = new ModelRenderer(this);
			cube_r2.setPos(-1.3F, 24.0F, -1.8F);
			Head.addChild(cube_r2);
			setRotationAngle(cube_r2, 0.3007F, -0.2288F, -0.0013F);
			cube_r2.texOffs(125, 10).addBox(-4.95F, -33.075F, 6.55F, 2.0F, 3.0F, 7.0F, -0.6F, false);

			cube_r3 = new ModelRenderer(this);
			cube_r3.setPos(-1.3F, 24.0F, -1.8F);
			Head.addChild(cube_r3);
			setRotationAngle(cube_r3, -0.0135F, -0.2288F, -0.0013F);
			cube_r3.texOffs(87, 0).addBox(-4.9F, -34.1F, -1.0F, 2.0F, 2.0F, 2.0F, -0.2F, false);

			RightLeg = new ModelRenderer(this);
			RightLeg.setPos(-1.9F, 12.0F, 0.0F);
			setRotationAngle(RightLeg, 0.192F, 0.0F, 0.0349F);
			RightLeg.texOffs(40, 41).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
			RightLeg.texOffs(64, 14).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 13.0F, 4.0F, 0.3F, false);
			RightLeg.texOffs(64, 7).addBox(-2.0F, 10.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.25F, false);
			RightLeg.texOffs(15, 45).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.4F, false);
			RightLeg.texOffs(15, 45).addBox(-2.0F, 5.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.8F, false);

			LeftLeg = new ModelRenderer(this);
			LeftLeg.setPos(1.9F, 12.0F, 0.0F);
			setRotationAngle(LeftLeg, -0.1745F, 0.0F, -0.0349F);
			LeftLeg.texOffs(41, 41).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
			LeftLeg.texOffs(64, 14).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 13.0F, 4.0F, 0.3F, false);
			LeftLeg.texOffs(64, 7).addBox(-2.0F, 10.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.25F, false);
			LeftLeg.texOffs(15, 45).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.4F, true);
			LeftLeg.texOffs(15, 45).addBox(-2.0F, 5.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.8F, true);
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
			Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
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

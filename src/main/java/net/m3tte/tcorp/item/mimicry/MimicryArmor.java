
package net.m3tte.tcorp.item.mimicry;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.tcorp.TcorpModElements;
import net.m3tte.tcorp.item.NoArmorToughnessMaterial;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class MimicryArmor extends ArmorItem {

	static IArmorMaterial armormaterial = new IArmorMaterial() {
		@Override
		public int getDurabilityForSlot(EquipmentSlotType slot) {
			return new int[]{13, 15, 16, 11}[slot.getIndex()] * 999;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType slot) {
			return new int[]{0, 0, 29, 0}[slot.getIndex()];
		}

		@Override
		public int getEnchantmentValue() {
			return 9;
		}

		@Override
		public SoundEvent getEquipSound() {
			return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
		}


		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.EMPTY;
		}

		@OnlyIn(Dist.CLIENT)
		@Override
		public String getName() {
			return "mimicry";
		}

		@Override
		public float getToughness() {
			return 10f;
		}

		@Override
		public float getKnockbackResistance() {
			return 0f;
		}
	};

	public MimicryArmor(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}

	// Texture Override
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "tcorp:textures/entities/mimicry_armor.png";
	}

	public static Item getArmorForSlot(EquipmentSlotType slot) {
		switch (slot) {
			default: return null;
			case CHEST: return chest;
			case LEGS: return legs;
		}
	}

	static Item chest = new MimicryArmor(armormaterial, EquipmentSlotType.CHEST, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.body = new mimicry_armor().Body;
			armorModel.leftArm = new mimicry_armor().LeftArm;
			armorModel.rightArm = new mimicry_armor().RightArm;
			armorModel.crouching = living.isCrouching();
			armorModel.riding = defaultModel.riding;
			armorModel.young = living.isBaby();

			return armorModel;
		}

		@Override
		public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(new StringTextComponent("The many shells cried out one word, \"Manager\"."));
			list.add(new StringTextComponent("[Ability] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent(" Terror -").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" 10E").withStyle(TextFormatting.AQUA)));
			list.add(new StringTextComponent("[Passive] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent(" Increase Mimicry life leech.")));
			list.add(new StringTextComponent("[Passive] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent(" \"HELLO\" recovers energy.")));
		}
	};

	static Item legs = new MimicryArmor(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.LEGS, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.body = new mimicry_armor().Dummy;
			armorModel.leftLeg = new mimicry_armor().LeftLeg;
			armorModel.rightLeg = new mimicry_armor().RightLeg;
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



	// Made with Blockbench 4.7.0
	// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
	// Paste this class into your mod and generate all required imports
	public static class mimicry_armor extends EntityModel<Entity> {
		private final ModelRenderer Body;
		private final ModelRenderer RightArm;
		private final ModelRenderer LeftArm;
		private final ModelRenderer RightLeg;
		private final ModelRenderer Dummy;
		private final ModelRenderer LeftLeg;

		public mimicry_armor() {
			texWidth = 64;
			texHeight = 64;

			Dummy = new ModelRenderer(this);
			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.3F, false);
			Body.texOffs(0, 16).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 13.0F, 5.0F, 0.2F, false);

			ModelRenderer cube_r1 = new ModelRenderer(this);
			cube_r1.setPos(-5.0F, 2.0F, 0.0F);
			Body.addChild(cube_r1);
			setRotationAngle(cube_r1, 0.0F, -1.5708F, 0.3927F);
			cube_r1.texOffs(34, 8).addBox(-3.15F, 1.75F, -8.75F, 1.0F, 3.0F, 4.0F, -0.3F, false);

			ModelRenderer cube_r2 = new ModelRenderer(this);
			cube_r2.setPos(-5.0F, 2.0F, 0.0F);
			Body.addChild(cube_r2);
			setRotationAngle(cube_r2, 0.8147F, 0.2316F, -0.1231F);
			cube_r2.texOffs(24, 9).addBox(2.25F, -2.25F, -2.0F, 0.5F, 3.0F, 0.5F, 0.0F, false);

			ModelRenderer cube_r3 = new ModelRenderer(this);
			cube_r3.setPos(-5.0F, 2.0F, 0.0F);
			Body.addChild(cube_r3);
			setRotationAngle(cube_r3, 1.3819F, 0.2316F, -0.1231F);
			cube_r3.texOffs(24, 9).addBox(2.0F, -3.0F, -4.25F, 0.75F, 3.0F, 0.75F, 0.0F, false);

			ModelRenderer cube_r4 = new ModelRenderer(this);
			cube_r4.setPos(-5.0F, 2.0F, 0.0F);
			Body.addChild(cube_r4);
			setRotationAngle(cube_r4, -0.8727F, 0.0F, 0.0F);
			cube_r4.texOffs(24, 9).addBox(5.15F, -3.25F, 2.4F, 0.7F, 3.0F, 0.7F, 0.0F, false);

			ModelRenderer cube_r5 = new ModelRenderer(this);
			cube_r5.setPos(-5.0F, 2.0F, 0.0F);
			Body.addChild(cube_r5);
			setRotationAngle(cube_r5, -1.4835F, 0.0F, 0.0F);
			cube_r5.texOffs(24, 9).addBox(5.0F, -4.5F, 4.75F, 1.0F, 3.0F, 1.0F, 0.0F, false);

			ModelRenderer cube_r6 = new ModelRenderer(this);
			cube_r6.setPos(-5.0F, 2.0F, 0.0F);
			Body.addChild(cube_r6);
			setRotationAngle(cube_r6, -1.2654F, 0.0F, 0.0F);
			cube_r6.texOffs(24, 9).addBox(5.0F, -4.5F, 2.75F, 1.0F, 3.0F, 1.0F, 0.0F, false);

			ModelRenderer cube_r7 = new ModelRenderer(this);
			cube_r7.setPos(-5.0F, 2.0F, 0.0F);
			Body.addChild(cube_r7);
			setRotationAngle(cube_r7, 1.0765F, 0.2316F, -0.1231F);
			cube_r7.texOffs(24, 9).addBox(2.0F, -3.0F, -3.25F, 1.0F, 3.0F, 1.0F, 0.0F, false);

			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(24, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.55F, false);
			RightArm.texOffs(24, 0).addBox(-3.0F, 2.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.35F, false);
			RightArm.texOffs(40, 0).addBox(-3.0F, 7.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.3F, false);
			RightArm.texOffs(32, 6).addBox(-3.35F, -1.75F, -1.5F, 0.0F, 3.0F, 3.0F, -0.3F, false);

			ModelRenderer cube_r8 = new ModelRenderer(this);
			cube_r8.setPos(0.0F, 0.0F, 0.0F);
			RightArm.addChild(cube_r8);
			setRotationAngle(cube_r8, 0.0F, 0.0F, -0.9599F);
			cube_r8.texOffs(24, 9).addBox(-6.0F, -2.5F, -0.75F, 1.0F, 4.0F, 1.0F, 0.0F, false);

			ModelRenderer cube_r9 = new ModelRenderer(this);
			cube_r9.setPos(0.0F, 0.0F, 0.0F);
			RightArm.addChild(cube_r9);
			setRotationAngle(cube_r9, 0.0F, 0.0F, -1.1345F);
			cube_r9.texOffs(24, 9).addBox(0.0F, -6.5F, -0.5F, 0.5F, 4.0F, 0.5F, 0.0F, false);
			cube_r9.texOffs(24, 9).addBox(-7.0F, -2.5F, -0.5F, 0.5F, 4.0F, 0.5F, 0.0F, false);

			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(4.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(40, 0).addBox(0.0F, 7.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.3F, true);
			LeftArm.texOffs(24, 0).addBox(0.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.55F, true);
			LeftArm.texOffs(24, 0).addBox(0.0F, 2.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.35F, true);

			ModelRenderer cube_r10 = new ModelRenderer(this);
			cube_r10.setPos(-10.0F, 0.0F, 0.0F);
			LeftArm.addChild(cube_r10);
			setRotationAngle(cube_r10, 0.0F, 1.5708F, -0.5236F);
			cube_r10.texOffs(32, 6).addBox(-2.3F, 5.25F, 9.5F, 0.0F, 3.0F, 3.0F, -0.3F, false);

			ModelRenderer cube_r11 = new ModelRenderer(this);
			cube_r11.setPos(-10.0F, 0.0F, 0.0F);
			LeftArm.addChild(cube_r11);
			setRotationAngle(cube_r11, 0.0F, 0.0F, -1.5272F);
			cube_r11.texOffs(24, 9).addBox(-0.25F, 12.5F, -0.75F, 1.0F, 4.0F, 1.0F, 0.0F, false);

			ModelRenderer cube_r12 = new ModelRenderer(this);
			cube_r12.setPos(-10.0F, 0.0F, 0.0F);
			LeftArm.addChild(cube_r12);
			setRotationAngle(cube_r12, 0.0F, 0.0F, -1.9635F);
			cube_r12.texOffs(24, 9).addBox(-5.0F, 11.5F, -0.5F, 0.5F, 4.0F, 0.5F, 0.0F, false);

			ModelRenderer cube_r13 = new ModelRenderer(this);
			cube_r13.setPos(-10.0F, 0.0F, 0.0F);
			LeftArm.addChild(cube_r13);
			setRotationAngle(cube_r13, 0.0F, 0.0F, -1.1345F);
			cube_r13.texOffs(24, 9).addBox(2.0F, 12.5F, -0.5F, 0.5F, 4.0F, 0.5F, 0.0F, false);

			RightLeg = new ModelRenderer(this);
			RightLeg.setPos(-1.9F, 12.0F, 0.0F);
			setRotationAngle(RightLeg, 0.192F, 0.0F, 0.0349F);
			RightLeg.texOffs(15, 46).addBox(-2.475F, 0.0F, -2.5F, 5.0F, 12.0F, 5.0F, -0.25F, false);
			RightLeg.texOffs(14, 55).addBox(-2.45F, 9.0F, -2.5F, 5.0F, 4.0F, 5.0F, 0.0F, false);
			RightLeg.texOffs(12, 47).addBox(-2.5F, -1.0F, -2.475F, 5.0F, 4.0F, 5.0F, 0.0F, false);

			ModelRenderer cube_r14 = new ModelRenderer(this);
			cube_r14.setPos(0.15F, 5.0F, -3.0F);
			RightLeg.addChild(cube_r14);
			setRotationAngle(cube_r14, -1.4909F, -1.091F, -1.5626F);
			cube_r14.texOffs(24, 9).addBox(0.1369F, -1.5641F, -0.526F, 1.0F, 4.0F, 1.0F, 0.0F, false);

			LeftLeg = new ModelRenderer(this);
			LeftLeg.setPos(1.9F, 12.0F, 0.0F);
			setRotationAngle(LeftLeg, -0.1745F, 0.0F, -0.0349F);
			LeftLeg.texOffs(28, 47).addBox(-2.525F, 0.0F, -2.5F, 5.0F, 12.0F, 5.0F, -0.25F, false);
			LeftLeg.texOffs(14, 55).addBox(-2.5F, 10.0F, -2.5F, 5.0F, 3.0F, 5.0F, 0.0F, true);
			LeftLeg.texOffs(32, 53).addBox(-0.5F, 6.0F, -2.5F, 3.0F, 4.0F, 5.0F, 0.0F, true);
			LeftLeg.texOffs(12, 47).addBox(-2.55F, -1.0F, -2.475F, 5.0F, 4.0F, 5.0F, 0.0F, true);

			ModelRenderer cube_r15 = new ModelRenderer(this);
			cube_r15.setPos(0.35F, 2.0F, -2.5F);
			LeftLeg.addChild(cube_r15);
			setRotationAngle(cube_r15, -1.4863F, -0.611F, -1.5662F);
			cube_r15.texOffs(24, 9).addBox(0.1369F, -1.5641F, -0.526F, 1.0F, 3.0F, 1.0F, -0.3F, false);

			ModelRenderer cube_r16 = new ModelRenderer(this);
			cube_r16.setPos(0.35F, 7.0F, -2.5F);
			LeftLeg.addChild(cube_r16);
			setRotationAngle(cube_r16, -1.4841F, -0.1311F, -1.567F);
			cube_r16.texOffs(24, 9).addBox(0.1369F, -1.5641F, -0.526F, 1.0F, 3.0F, 1.0F, -0.3F, false);

			ModelRenderer cube_r17 = new ModelRenderer(this);
			cube_r17.setPos(0.35F, 8.0F, -2.5F);
			LeftLeg.addChild(cube_r17);
			setRotationAngle(cube_r17, -1.4816F, 0.4798F, -1.5665F);
			cube_r17.texOffs(24, 9).addBox(0.1369F, -1.5641F, -0.526F, 1.0F, 3.0F, 1.0F, -0.3F, false);
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

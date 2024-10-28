
package net.m3tte.tcorp.item.sunshower;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.tcorp.TCorpItems;
import net.m3tte.tcorp.item.NoArmorToughnessMaterial;
import net.m3tte.tcorp.potion.ManifestEgoPotionEffect;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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

import javax.annotation.Nullable;
import java.util.List;

public class SunshowerArmor extends ArmorItem {

	static IArmorMaterial armormaterial = new IArmorMaterial() {

		@Override
		public int getDurabilityForSlot(EquipmentSlotType p_200896_1_) {
			return Integer.MAX_VALUE;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType slot)  {
			return new int[]{0, 0, 20, 0}[slot.getIndex()];
		}

		@Override
		public int getEnchantmentValue() {
			return 0;
		}



		@Override
		public SoundEvent getEquipSound() {
			return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather"));
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.EMPTY;
		}


		@OnlyIn(Dist.CLIENT)
		@Override
		public String getName() {
			return "sunshower";
		}

		@Override
		public float getToughness() {
			return 16f;
		}



		@Override
		public float getKnockbackResistance() {
			return 0.3f;
		}
	};

	public SunshowerArmor(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "tcorp:textures/entities/sunshower.png";
	}

	public static Item getArmorForSlot(EquipmentSlotType slot) {
		switch (slot) {
			default: return null;
			case HEAD: return head;
			case CHEST: return chest;
		}
	}

	static Item head = new SunshowerArmor(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.HEAD, new Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			SunshowerArmorModel m = new SunshowerArmorModel();
			armorModel.head = m.Head;
			armorModel.hat = m.Head;
			armorModel.body = m.Body;
			return armorModel;
		}
	};

	static Item chest = new SunshowerArmor(armormaterial, EquipmentSlotType.CHEST, new Properties().tab(ItemGroup.TAB_COMBAT)) {



		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);

			SunshowerArmorModel m = new SunshowerArmorModel();

			armorModel.head = m.Head;
			armorModel.body = m.Body;
			armorModel.leftArm = m.LeftArm;
			armorModel.rightArm = m.RightArm;
			armorModel.rightLeg = m.RightLeg;
			armorModel.leftLeg = m.LeftLeg;
			armorModel.crouching = living.isCrouching();
			armorModel.riding = defaultModel.riding;
			armorModel.young = living.isBaby();
			armorModel.setAllVisible(true);
			return armorModel;
		}

		@Override
		public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> list, ITooltipFlag p_77624_4_) {
			super.appendHoverText(p_77624_1_, p_77624_2_, list, p_77624_4_);
			list.add(new StringTextComponent("Abandonment"));
			//list.add(new StringTextComponent("[Ability] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent(" Manifest E.G.O -").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" 10E").withStyle(TextFormatting.AQUA)));
			//list.add(new StringTextComponent("[Ability] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent(" Onrush -").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" 3E").withStyle(TextFormatting.AQUA)));
			//list.add(new StringTextComponent("[Passive] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent(" Augment MIMICRY moveset")));
		}
	};
	//@ObjectHolder("tcorp:cigarette")
	//public static final Item head = null;

	public static class SunshowerArmorModel extends EntityModel<Entity> {
		private final ModelRenderer Head;
		private final ModelRenderer Body;
		private final ModelRenderer Umbrella;
		private final ModelRenderer cube_r1;
		private final ModelRenderer cube_r2;
		private final ModelRenderer cube_r3;
		private final ModelRenderer cube_r4;
		private final ModelRenderer Umbrella2;
		private final ModelRenderer cube_r5;
		private final ModelRenderer cube_r6;
		private final ModelRenderer cube_r7;
		private final ModelRenderer cube_r8;
		private final ModelRenderer RightArm;
		private final ModelRenderer LeftArm;
		private final ModelRenderer RightLeg;
		private final ModelRenderer LeftLeg;

		public SunshowerArmorModel() {
			texWidth = 128;
			texHeight = 64;

			Head = new ModelRenderer(this);
			Head.setPos(0.0F, 0.0F, 0.0F);
			setRotationAngle(Head, -0.1047F, 0.0873F, 0.0F);
			Head.texOffs(0, 48).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.6F, false);
			Head.texOffs(32, 48).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.4F, false);

			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 1.0F, 0.15F, false);
			Body.texOffs(0, 12).addBox(-4.5F, -0.5F, -2.55F, 9.0F, 11.0F, 5.0F, 0.0F, false);
			Body.texOffs(0, 32).addBox(-4.5F, 11.5F, -2.575F, 9.0F, 7.0F, 5.0F, 0.45F, false);
			Body.texOffs(28, 32).addBox(-4.5F, 8.0F, -2.575F, 9.0F, 3.0F, 5.0F, 0.25F, false);
			Body.texOffs(9, 5).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 3.0F, 1.0F, -0.3F, false);

			Umbrella = new ModelRenderer(this);
			Umbrella.setPos(2.0F, 8.0F, 2.0F);
			Body.addChild(Umbrella);
			setRotationAngle(Umbrella, -0.6811F, 0.1671F, 0.2024F);
			Umbrella.texOffs(73, 0).addBox(0.0F, -17.6163F, -0.0442F, 1.0F, 20.0F, 1.0F, 0.0F, false);

			cube_r1 = new ModelRenderer(this);
			cube_r1.setPos(-0.75F, -16.3663F, 0.4558F);
			Umbrella.addChild(cube_r1);
			setRotationAngle(cube_r1, -3.1416F, 0.0F, 2.618F);
			cube_r1.texOffs(79, 11).addBox(-0.25F, 1.0F, -7.5F, 11.0F, 0.0F, 14.0F, 0.0F, false);

			cube_r2 = new ModelRenderer(this);
			cube_r2.setPos(0.5F, -16.3663F, -0.7942F);
			Umbrella.addChild(cube_r2);
			setRotationAngle(cube_r2, 1.5708F, 1.0472F, 1.5708F);
			cube_r2.texOffs(79, 11).addBox(-0.25F, 1.0F, -7.5F, 11.0F, 0.0F, 14.0F, 0.0F, false);

			cube_r3 = new ModelRenderer(this);
			cube_r3.setPos(0.5F, -16.3663F, 1.7058F);
			Umbrella.addChild(cube_r3);
			setRotationAngle(cube_r3, -1.5708F, -1.0472F, 1.5708F);
			cube_r3.texOffs(79, 11).addBox(-0.25F, 1.0F, -7.5F, 11.0F, 0.0F, 14.0F, 0.0F, false);

			cube_r4 = new ModelRenderer(this);
			cube_r4.setPos(1.7F, -16.3663F, 0.4558F);
			Umbrella.addChild(cube_r4);
			setRotationAngle(cube_r4, 0.0F, 0.0F, 0.5236F);
			cube_r4.texOffs(79, 11).addBox(-0.25F, 1.0F, -7.5F, 11.0F, 0.0F, 14.0F, 0.0F, false);

			Umbrella2 = new ModelRenderer(this);
			Umbrella2.setPos(-3.0F, 6.0F, 2.0F);
			Body.addChild(Umbrella2);
			setRotationAngle(Umbrella2, -0.4564F, -0.402F, -0.5314F);
			Umbrella2.texOffs(73, 0).addBox(0.0F, -17.6163F, -0.0442F, 1.0F, 20.0F, 1.0F, 0.0F, false);

			cube_r5 = new ModelRenderer(this);
			cube_r5.setPos(-0.75F, -16.3663F, 0.4558F);
			Umbrella2.addChild(cube_r5);
			setRotationAngle(cube_r5, -3.1416F, 0.0F, 2.618F);
			cube_r5.texOffs(79, 11).addBox(-0.25F, 1.0F, -7.5F, 11.0F, 0.0F, 14.0F, 0.0F, false);

			cube_r6 = new ModelRenderer(this);
			cube_r6.setPos(0.5F, -16.3663F, -0.7942F);
			Umbrella2.addChild(cube_r6);
			setRotationAngle(cube_r6, 1.5708F, 1.0472F, 1.5708F);
			cube_r6.texOffs(79, 11).addBox(-0.25F, 1.0F, -7.5F, 11.0F, 0.0F, 14.0F, 0.0F, false);

			cube_r7 = new ModelRenderer(this);
			cube_r7.setPos(0.5F, -16.3663F, 1.7058F);
			Umbrella2.addChild(cube_r7);
			setRotationAngle(cube_r7, -1.5708F, -1.0472F, 1.5708F);
			cube_r7.texOffs(79, 11).addBox(-0.25F, 1.0F, -7.5F, 11.0F, 0.0F, 14.0F, 0.0F, false);

			cube_r8 = new ModelRenderer(this);
			cube_r8.setPos(1.7F, -16.3663F, 0.4558F);
			Umbrella2.addChild(cube_r8);
			setRotationAngle(cube_r8, 0.0F, 0.0F, 0.5236F);
			cube_r8.texOffs(79, 11).addBox(-0.25F, 1.0F, -7.5F, 11.0F, 0.0F, 14.0F, 0.0F, false);

			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(28, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.2F, false);
			RightArm.texOffs(28, 18).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.4F, false);

			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(28, 18).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.4F, true);
			LeftArm.texOffs(28, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.2F, false);

			RightLeg = new ModelRenderer(this);
			RightLeg.setPos(-1.9F, 12.0F, 0.0F);
			setRotationAngle(RightLeg, 0.192F, 0.0F, 0.0349F);
			RightLeg.texOffs(19, 24).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
			RightLeg.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);

			LeftLeg = new ModelRenderer(this);
			LeftLeg.setPos(1.9F, 12.0F, 0.0F);
			setRotationAngle(LeftLeg, -0.1745F, 0.0F, -0.0349F);
			LeftLeg.texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
			LeftLeg.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
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

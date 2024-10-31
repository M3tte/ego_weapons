
package net.m3tte.ego_weapons.item.redmist;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.EgoWeaponsModElements;
import net.m3tte.ego_weapons.item.NoArmorToughnessMaterial;
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

import javax.annotation.Nullable;
import java.util.List;

@EgoWeaponsModElements.ModElement.Tag
public class RedMistJacket extends ArmorItem {
	public RedMistJacket(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}

	//@ObjectHolder("ego_weapons:cigarette")
	//public static final Item head = null;

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "ego_weapons:textures/entities/redmistcoat.png";
	}

	static IArmorMaterial armormaterial = new IArmorMaterial() {

		@Override
		public int getDurabilityForSlot(EquipmentSlotType p_200896_1_) {
			return Integer.MAX_VALUE;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType slot)  {
			return new int[]{0, 0, 24, 0}[slot.getIndex()];
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
			return "redmist";
		}

		@Override
		public float getToughness() {
			return 13f;
		}



		@Override
		public float getKnockbackResistance() {
			return 0.3f;
		}
	};

	public static Item getArmorForSlot(EquipmentSlotType slot) {
		switch (slot) {
			default: return null;
			case HEAD: return cig;
			case CHEST: return chest;
		}
	}
	static Item chest = new RedMistJacket(armormaterial, EquipmentSlotType.CHEST, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);

			RedmistModel m = new RedmistModel();

			armorModel.head = m.Head;
			armorModel.body = m.Body;
			armorModel.leftArm = m.LeftArm;
			armorModel.rightArm = m.RightArm;
			armorModel.crouching = living.isCrouching();
			armorModel.riding = defaultModel.riding;
			armorModel.young = living.isBaby();
			return armorModel;
		}

		@Override
		public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> list, ITooltipFlag p_77624_4_) {
			super.appendHoverText(p_77624_1_, p_77624_2_, list, p_77624_4_);
			list.add(new StringTextComponent("The courage..."));
			list.add(new StringTextComponent("[Ability] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent(" Manifest E.G.O -").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" 10E").withStyle(TextFormatting.AQUA)));
			list.add(new StringTextComponent("[Ability] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent(" Onrush -").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" 3E").withStyle(TextFormatting.AQUA)));
			list.add(new StringTextComponent("[Passive] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent(" Augment MIMICRY moveset")));
		}
	};
	static Item cig = new RedMistJacket(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.HEAD, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			RedmistModel m = new RedmistModel();
			armorModel.head = m.Head;
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

	public static class RedmistModel extends EntityModel<Entity> {

		private final ModelRenderer Body;
		private final ModelRenderer RightArm;
		private final ModelRenderer Pad;
		private final ModelRenderer Pad2;
		private final ModelRenderer LeftArm;
		private final ModelRenderer Pad3;
		private final ModelRenderer Pad4;
		private final ModelRenderer Head;
		private final ModelRenderer cig_r1;

		public RedmistModel() {
			texWidth = 56;
			texHeight = 43;

			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.1F, false);
			Body.texOffs(23, 0).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 17.0F, 5.0F, -0.2F, false);
			Body.texOffs(0, 16).addBox(4.0F, 7.5F, -1.5F, 1.0F, 6.0F, 3.0F, 0.0F, false);
			Body.texOffs(0, 16).addBox(-5.0F, 7.5F, -1.5F, 1.0F, 6.0F, 3.0F, 0.0F, true);

			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(23, 22).addBox(-3.0F, 6.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.08F, false);
			RightArm.texOffs(0, 25).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
			RightArm.texOffs(39, 22).addBox(-2.975F, 5.225F, -2.0F, 4.0F, 2.0F, 4.0F, 0.2F, false);

			Pad = new ModelRenderer(this);
			Pad.setPos(5.0F, 21.5F, 0.0F);
			RightArm.addChild(Pad);
			Pad.texOffs(38, 36).addBox(-6.95F, -22.775F, -2.575F, 1.0F, 1.0F, 1.0F, -0.1F, false);
			Pad.texOffs(23, 30).addBox(-8.5F, -23.25F, -2.45F, 3.0F, 2.0F, 5.0F, -0.05F, false);
			Pad.texOffs(38, 36).addBox(-6.95F, -22.775F, 1.725F, 1.0F, 1.0F, 1.0F, -0.1F, false);

			Pad2 = new ModelRenderer(this);
			Pad2.setPos(5.0F, 24.0F, 0.0F);
			RightArm.addChild(Pad2);
			Pad2.texOffs(38, 36).addBox(-6.95F, -22.775F, -2.575F, 1.0F, 1.0F, 1.0F, -0.1F, false);
			Pad2.texOffs(23, 30).addBox(-8.5F, -23.25F, -2.45F, 3.0F, 2.0F, 5.0F, -0.05F, false);
			Pad2.texOffs(38, 36).addBox(-6.95F, -22.775F, 1.725F, 1.0F, 1.0F, 1.0F, -0.1F, false);

			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(39, 22).addBox(-1.025F, 5.225F, -2.0F, 4.0F, 2.0F, 4.0F, 0.2F, true);
			LeftArm.texOffs(0, 25).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, true);
			LeftArm.texOffs(23, 22).addBox(-1.0F, 6.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.08F, true);

			Pad3 = new ModelRenderer(this);
			Pad3.setPos(8.9F, 21.5F, 0.0F);
			LeftArm.addChild(Pad3);
			Pad3.texOffs(38, 36).addBox(-8.075F, -22.775F, -2.575F, 1.0F, 1.0F, 1.0F, -0.1F, false);
			Pad3.texOffs(23, 30).addBox(-8.5F, -23.25F, -2.45F, 3.0F, 2.0F, 5.0F, -0.05F, false);
			Pad3.texOffs(38, 36).addBox(-8.075F, -22.775F, 1.725F, 1.0F, 1.0F, 1.0F, -0.1F, false);

			Pad4 = new ModelRenderer(this);
			Pad4.setPos(8.9F, 24.0F, 0.0F);
			LeftArm.addChild(Pad4);
			Pad4.texOffs(38, 36).addBox(-8.075F, -22.775F, -2.575F, 1.0F, 1.0F, 1.0F, -0.1F, false);
			Pad4.texOffs(23, 30).addBox(-8.5F, -23.25F, -2.45F, 3.0F, 2.0F, 5.0F, -0.05F, false);
			Pad4.texOffs(38, 36).addBox(-8.075F, -22.775F, 1.725F, 1.0F, 1.0F, 1.0F, -0.1F, false);

			Head = new ModelRenderer(this);
			Head.setPos(0.0F, 0.0F, 0.0F);
			setRotationAngle(Head, -0.1047F, 0.0873F, 0.0F);


			cig_r1 = new ModelRenderer(this);
			cig_r1.setPos(0.0F, 24.0F, 0.0F);
			Head.addChild(cig_r1);
			setRotationAngle(cig_r1, 0.2182F, -0.4363F, 0.0F);
			cig_r1.texOffs(16, 34).addBox(-2.35F, -26.2F, -1.275F, 2.0F, 2.0F, 2.0F, -0.799F, false);
			cig_r1.texOffs(16, 30).addBox(-1.85F, -25.7F, -0.775F, 1.0F, 1.0F, 3.0F, -0.3F, false);
		}

		@Override
		public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			//previously the render function, render code was moved to a method below
		}

		@Override
		public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
			Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.xRot = x;
			modelRenderer.yRot = y;
			modelRenderer.zRot = z;
		}
	}
}

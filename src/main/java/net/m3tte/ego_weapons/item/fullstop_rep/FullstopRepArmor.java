
package net.m3tte.ego_weapons.item.fullstop_rep;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.item.NoArmorToughnessMaterial;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
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

import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class FullstopRepArmor extends GenericEgoWeaponsArmor {

	static IArmorMaterial fullstopRepArmor = new IArmorMaterial() {
		@Override
		public int getDurabilityForSlot(EquipmentSlotType slot) {
			return new int[]{13, 15, 16, 11}[slot.getIndex()] * 999;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType slot) {
			return new int[]{0, 0, 18, 0}[slot.getIndex()];
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
			return "fullstop_rep";
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

	public FullstopRepArmor(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}

	// Texture Override
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "ego_weapons:textures/entities/fullstop_rep_armor.png";
	}

	public static Item getArmorForSlot(EquipmentSlotType slot) {
		switch (slot) {
			default: return null;
			case LEGS: return legs;
			case CHEST: return chest;
			case HEAD: return earpiece;
		}
	}

	static Item legs = new FullstopRepArmor(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.LEGS, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			FullStopRepArmorModel m = new FullStopRepArmorModel();
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

	public FullstopRepArmor(IArmorMaterial armorMaterial, EquipmentSlotType slot, Properties props, float redResistance, float whiteResistance, float blackResistance, float paleResistance, float slashResistance, float pierceResistance, float bluntResistance, float bonusStagger, float bonusSanity) {
		super(armorMaterial, slot, props, redResistance, whiteResistance, blackResistance, paleResistance,slashResistance, pierceResistance, bluntResistance, bonusStagger, bonusSanity);
	}
	static Item chest = new FullstopRepArmor(fullstopRepArmor, EquipmentSlotType.CHEST, new Properties().tab(ItemGroup.TAB_COMBAT), 0.7f, 1.3f, 0.7f ,1.3f, 1f, 0.7f, 1.3f, 5, 0) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);


			FullStopRepArmorModel m = new FullStopRepArmorModel();
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

		@Override
		public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(new StringTextComponent("Manufactured by Atelier Logic").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
			list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

			list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 3) + 1) + "/3] - - - - - - - =").withStyle(TextFormatting.GRAY));
			list.add(new TranslationTextComponent("desc.ego_weapons.risk.waw"));
			list.add(new StringTextComponent(" "));
			switch (EgoWeaponsKeybinds.getUiPage() % 3) {
				case 0:
					resistanceMods(itemstack, world, list, flag);
					break;
				case 1:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"poise"});
					else
						generateDescription(list, "fullstop_rep_armor", "passive", 2);
					break;
				case 2:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"poise", "target_marked", "assist_fire"});
					else {
						generateDescription(list,"fullstop_rep_armor", "ability", 5);
					}
					break;
			}
			list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
		}

	};
	static Item earpiece = new FullstopRepArmor(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.HEAD, new Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			FullStopRepArmorModel m = new FullStopRepArmorModel();
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

	// Made with Blockbench 4.7.1
	// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
	// Paste this class into your mod and generate all required imports
	public class FullStopRepArmorModel extends EntityModel<Entity> {
		private final ModelRenderer Body;
		private final ModelRenderer RightArm;
		private final ModelRenderer LeftArm;
		private final ModelRenderer Dummy;
		private final ModelRenderer Head;
		private final ModelRenderer Head_r1;
		private final ModelRenderer Head_r2;
		private final ModelRenderer RightLeg;
		private final ModelRenderer LeftLeg;

		public FullStopRepArmorModel() {
			texWidth = 90;
			texHeight = 80;

			Dummy = new ModelRenderer(this);
			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 0.0F, 0.2F, false);
			Body.texOffs(8, 0).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 13.0F, 5.0F, -0.2F, false);
			Body.texOffs(37, 1).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 14.0F, 4.0F, 0.5F, false);
			Body.texOffs(32, 35).addBox(-4.5F, 13.75F, -2.5F, 9.0F, 3.0F, 5.0F, 0.3F, false);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(16, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.25F, false);

			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(16, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.25F, true);

			Head = new ModelRenderer(this);
			Head.setPos(1.0F, -1.0F, 0.0F);
			setRotationAngle(Head, -0.1047F, 0.0873F, 0.0F);


			Head_r1 = new ModelRenderer(this);
			Head_r1.setPos(-5.55F, -1.65F, -2.45F);
			Head.addChild(Head_r1);
			setRotationAngle(Head_r1, 0.1584F, -0.1293F, -0.0206F);
			Head_r1.texOffs(40, 57).addBox(0.0F, -0.3938F, -2.2976F, 1.0F, 1.0F, 5.0F, 0.0F, true);

			Head_r2 = new ModelRenderer(this);
			Head_r2.setPos(-4.975F, -2.225F, 0.0F);
			Head.addChild(Head_r2);
			setRotationAngle(Head_r2, 0.0873F, 0.0F, 0.0F);
			Head_r2.texOffs(32, 63).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 3.0F, 0.05F, true);

			RightLeg = new ModelRenderer(this);
			RightLeg.setPos(-1.9F, 12.0F, 0.0F);
			setRotationAngle(RightLeg, 0.192F, 0.0F, 0.0349F);
			RightLeg.texOffs(37, 19).addBox(-2.075F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
			RightLeg.texOffs(32, 48).addBox(-2.0F, 7.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.3F, false);

			LeftLeg = new ModelRenderer(this);
			LeftLeg.setPos(1.9F, 12.0F, 0.0F);
			setRotationAngle(LeftLeg, -0.1745F, 0.0F, -0.0349F);
			LeftLeg.texOffs(37, 19).addBox(-1.975F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
			LeftLeg.texOffs(32, 48).addBox(-2.0F, 7.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.3F, false);
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


package net.m3tte.ego_weapons.item.liu;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.item.NoArmorToughnessMaterial;
import net.m3tte.ego_weapons.item.magic_bullet.MagicBulletArmor;
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

public class LiuSection6Armor extends GenericEgoWeaponsArmor {

	static IArmorMaterial solemnLamentArmor = new IArmorMaterial() {
		@Override
		public int getDurabilityForSlot(EquipmentSlotType slot) {
			return new int[]{13, 15, 16, 11}[slot.getIndex()] * 999;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType slot) {
			return new int[]{0, 0, 16, 0}[slot.getIndex()];
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
			return 6f;
		}

		@Override
		public float getKnockbackResistance() {
			return 0.1f;
		}
	};

	public LiuSection6Armor(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}

	// Texture Override
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "ego_weapons:textures/entities/liu_section_6.png";
	}

	public static Item getArmorForSlot(EquipmentSlotType slot) {
		switch (slot) {
			default:
				return null;
			case CHEST:
				return chest;
			case LEGS:
				return pants;
		}
	}

	public LiuSection6Armor(IArmorMaterial armorMaterial, EquipmentSlotType slot, Properties props, float redResistance, float whiteResistance, float blackResistance, float paleResistance, float slashResistance, float pierceResistance, float bluntResistance, float bonusStagger, float bonusSanity) {
		super(armorMaterial, slot, props, redResistance, whiteResistance, blackResistance, paleResistance,slashResistance, pierceResistance, bluntResistance, bonusStagger, bonusSanity);
	}

	static Item chest = new LiuSection6Armor(solemnLamentArmor, EquipmentSlotType.CHEST, new Properties().tab(ItemGroup.TAB_COMBAT), 0.9f, 0.9f, 1.2f ,1.5f, 1f, 1.3f, 0.7f, -5, 0) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);


			LiuSection6Model m = new LiuSection6Model();
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
			list.add(new StringTextComponent("A vest manufactured for Liu Association fixers. Provides decent physical and mental Protection.").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
			list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

			list.add(new StringTextComponent("= - - - - - - - [Page: " + ((EgoWeaponsKeybinds.getUiPage() % 4) + 1) + "/4] - - - - - - - =").withStyle(TextFormatting.GRAY));
			list.add(new TranslationTextComponent("desc.ego_weapons.risk.7"));
			list.add(new StringTextComponent(" "));
			switch (EgoWeaponsKeybinds.getUiPage() % 4) {
				case 0:
					resistanceMods(itemstack, world, list, flag);
					break;
				case 1:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"burn"});
					else
						generateDescription(list, "liu_south_6_armor", "passive", 1);
					break;
				case 2:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"burn"});
					else
						generateDescription(list, "liu_south_6_armor", "passive2", 2);
					break;
				case 3:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"burn", "power_up"});
					else
						generateDescription(list, "liu_south_6_armor", "ability", 5);
					break;
			}

			list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
		}

	};

	static Item pants = new LiuSection6Armor(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.LEGS, new Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			LiuSection6Model m = new LiuSection6Model();
			armorModel.leftLeg = m.LeftLeg;
			armorModel.rightLeg = m.RightLeg;
			armorModel.body = m.SuitPants;
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
	public static class LiuSection6Model extends EntityModel<Entity> {
		private final ModelRenderer Body;
		private final ModelRenderer Suit_r1;
		private final ModelRenderer Suit_r2;
		private final ModelRenderer SuitPants;
		private final ModelRenderer RightArm;
		private final ModelRenderer RightArmor_r1;
		private final ModelRenderer RightArmor_r2;
		private final ModelRenderer LeftArm;
		private final ModelRenderer RightLeg;
		private final ModelRenderer LeftLeg;

		public LiuSection6Model() {
			texWidth = 64;
			texHeight = 48;

			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 0.0F, 0.15F, false);
			Body.texOffs(16, 0).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 14.0F, 5.0F, -0.2F, false);

			Suit_r1 = new ModelRenderer(this);
			Suit_r1.setPos(-3.5F, 25.5F, 4.5F);
			Body.addChild(Suit_r1);
			setRotationAngle(Suit_r1, 0.0F, 0.0F, 0.1309F);
			Suit_r1.texOffs(44, 28).addBox(-4.0F, -24.0F, -2.0F, 10.0F, 7.0F, 0.0F, 0.0F, false);

			Suit_r2 = new ModelRenderer(this);
			Suit_r2.setPos(-3.7F, 25.5F, -0.5F);
			Body.addChild(Suit_r2);
			setRotationAngle(Suit_r2, 0.0F, 0.0F, 0.1309F);
			Suit_r2.texOffs(16, 28).addBox(-4.0F, -24.0F, -2.0F, 10.0F, 7.0F, 0.0F, 0.0F, false);

			SuitPants = new ModelRenderer(this);
			SuitPants.setPos(0, 0, 0);
			SuitPants.texOffs(15, 19).addBox(-4.5f, 9.5f, -2.5f, 9.0F, 4.0F, 5.0F, -0.23F, false);

			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(0, 14).addBox(-3.5F, 3.75F, -2.5F, 5.0F, 4.0F, 5.0F, -0.1F, false);
			RightArm.texOffs(44, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.2F, false);

			RightArmor_r1 = new ModelRenderer(this);
			RightArmor_r1.setPos(-2.1F, -1.5F, 0.5F);
			RightArm.addChild(RightArmor_r1);
			setRotationAngle(RightArmor_r1, 0.0F, 0.0F, 0.0873F);
			RightArmor_r1.texOffs(0, 40).addBox(-1.3088F, -1.3206F, -3.0F, 4.0F, 3.0F, 5.0F, 0.0F, false);

			RightArmor_r2 = new ModelRenderer(this);
			RightArmor_r2.setPos(-2.25F, 0.75F, 0.5F);
			RightArm.addChild(RightArmor_r2);
			setRotationAngle(RightArmor_r2, 0.0F, 0.0F, 0.0873F);
			RightArmor_r2.texOffs(0, 40).addBox(-1.3088F, -1.3206F, -3.0F, 4.0F, 3.0F, 5.0F, -0.1F, false);

			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(32, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.2F, true);
			LeftArm.texOffs(0, 14).addBox(-1.5F, 4.0F, -2.5F, 5.0F, 4.0F, 5.0F, -0.1F, true);

			RightLeg = new ModelRenderer(this);
			RightLeg.setPos(-1.9F, 12.0F, 0.0F);
			setRotationAngle(RightLeg, 0.192F, 0.0F, 0.0349F);
			RightLeg.texOffs(44, 19).addBox(-2.5F, 9.375F, -2.65F, 5.0F, 3.0F, 5.0F, -0.1F, false);
			RightLeg.texOffs(0, 24).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.2F, false);

			LeftLeg = new ModelRenderer(this);
			LeftLeg.setPos(1.9F, 12.0F, 0.0F);
			setRotationAngle(LeftLeg, -0.1745F, 0.0F, -0.0349F);
			LeftLeg.texOffs(0, 24).addBox(-1.975F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.2F, true);
			LeftLeg.texOffs(44, 19).addBox(-2.475F, 9.375F, -2.65F, 5.0F, 3.0F, 5.0F, -0.1F, false);
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
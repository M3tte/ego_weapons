
package net.m3tte.ego_weapons.item.firefist;

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

public class FirefistArmor extends GenericEgoWeaponsArmor {

	static IArmorMaterial firefistArmor = new IArmorMaterial() {
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
			return "firefist";
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

	public FirefistArmor(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}

	public FirefistArmor(IArmorMaterial armorMaterial, EquipmentSlotType slot, Properties props, float redResistance, float whiteResistance, float blackResistance, float paleResistance, float slashResistance, float pierceResistance, float bluntResistance, float bonusStagger, float bonusSanity) {
		super(armorMaterial, slot, props, redResistance, whiteResistance, blackResistance, paleResistance,slashResistance, pierceResistance, bluntResistance, bonusStagger, bonusSanity);


	}

	// Texture Override
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "ego_weapons:textures/entities/firefist_suit.png";
	}

	public static Item getArmorForSlot(EquipmentSlotType slot) {
		switch (slot) {
			default:
				return null;
			case CHEST:
				return chest;
			case LEGS:
				return pants;
			case HEAD:
				return mask;
		}
	}

	static Item mask = new MagicBulletArmor(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.HEAD, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			FirefistSuit m = new FirefistSuit();
			armorModel.head = m.Head;
			armorModel.hat = m.dummy;
			return armorModel;


		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "ego_weapons:textures/entities/firefist_suit.png";
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
	static Item chest = new FirefistArmor(firefistArmor, EquipmentSlotType.CHEST, new Properties().tab(ItemGroup.TAB_COMBAT), 0.7f, 1.3f, 0.7f ,1.5f, 0.7f, 1.3f, 1.0f, 5, -5) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);


			FirefistSuit m = new FirefistSuit();
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
			list.add(new StringTextComponent("A suit originating from the Firefist office. Contains a fuel tank.").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
			list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

			list.add(new StringTextComponent("= - - - - - - - [Page: " + ((EgoWeaponsKeybinds.getUiPage() % 5) + 1) + "/5] - - - - - - - =").withStyle(TextFormatting.GRAY));
			list.add(new TranslationTextComponent("desc.ego_weapons.risk.he"));
			list.add(new StringTextComponent(" "));
			switch (EgoWeaponsKeybinds.getUiPage() % 5) {
				case 0:


					resistanceMods(itemstack, world, list, flag);
					break;
				case 1:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"burn"});
					else
						generateDescription(list, "firefist_armor", "passive", 1);
					break;
				case 2:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"d10fuel"});
					else
						generateDescription(list, "firefist_armor", "passive2", 3);
					break;
				case 3:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"d10fuel"});
					else
						generateDescription(list, "firefist_armor", "reload", 1);
					break;
				case 4:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"burn", "offense_up", "defense_down", "firefist_overdrive"});
					else
						generateDescription(list, "firefist_armor", "ability", 4);
					break;
			}

			list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
		}

	};

	static Item pants = new FirefistArmor(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.LEGS, new Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			FirefistSuit m = new FirefistSuit();
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
	public static class FirefistSuit extends EntityModel<Entity> {

		private final ModelRenderer dummy;
		private final ModelRenderer Body;
		private final ModelRenderer GasTank;
		private final ModelRenderer SuitPants;
		private final ModelRenderer RightArm;
		private final ModelRenderer RightShirt_r1;
		private final ModelRenderer RightArmor_r1;
		private final ModelRenderer LeftArm;
		private final ModelRenderer RightLeg;
		private final ModelRenderer LeftLeg;
		private final ModelRenderer Head;
		private final ModelRenderer HatLayer_r1;
		private final ModelRenderer HatLayer_r2;

		public FirefistSuit() {
			texWidth = 84;
			texHeight = 64;

			dummy = new ModelRenderer(this);
			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 0.0F, 0.15F, false);
			Body.texOffs(16, 0).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 14.0F, 5.0F, -0.2F, false);

			GasTank = new ModelRenderer(this);
			GasTank.setPos(0.0F, 24.0F, 0.0F);
			Body.addChild(GasTank);
			GasTank.texOffs(59, 30).addBox(0.2F, -26.5F, 2.2F, 4.0F, 14.0F, 4.0F, -0.2F, false);
			GasTank.texOffs(59, 30).addBox(-4.2F, -26.5F, 2.2F, 4.0F, 14.0F, 4.0F, -0.2F, false);
			GasTank.texOffs(61, 32).addBox(-1.5F, -23.5F, 3.2F, 3.0F, 2.0F, 2.0F, -0.2F, false);
			GasTank.texOffs(64, 9).addBox(-4.5F, -24.5F, -2.5F, 2.0F, 5.0F, 5.0F, -0.19F, false);
			GasTank.texOffs(64, 9).addBox(2.5F, -24.5F, -2.5F, 2.0F, 5.0F, 5.0F, -0.19F, false);
			GasTank.texOffs(61, 32).addBox(-1.5F, -17.5F, 3.2F, 3.0F, 2.0F, 2.0F, -0.2F, false);
			GasTank.texOffs(61, 32).addBox(-1.5F, -20.5F, 3.2F, 3.0F, 2.0F, 2.0F, -0.2F, false);
			GasTank.texOffs(64, 25).addBox(1.2F, -27.5F, 3.2F, 2.0F, 2.0F, 2.0F, -0.2F, false);
			GasTank.texOffs(64, 25).addBox(-3.2F, -27.5F, 3.2F, 2.0F, 2.0F, 2.0F, -0.2F, false);

			SuitPants = new ModelRenderer(this);
			SuitPants.setPos(-0.5F, 11.5F, -0.5F);
			Body.addChild(SuitPants);
			SuitPants.texOffs(15, 19).addBox(-4.0F, -2.0F, -2.0F, 9.0F, 4.0F, 5.0F, -0.23F, false);

			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(44, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.2F, false);

			RightShirt_r1 = new ModelRenderer(this);
			RightShirt_r1.setPos(2.95F, 22.2F, 0.4F);
			RightArm.addChild(RightShirt_r1);
			setRotationAngle(RightShirt_r1, -0.1081F, -0.3437F, 0.0701F);
			RightShirt_r1.texOffs(49, 35).addBox(-6.0F, -25.0F, 2.0F, 5.0F, 9.0F, 0.0F, 0.0F, false);

			RightArmor_r1 = new ModelRenderer(this);
			RightArmor_r1.setPos(-2.1F, -1.5F, 0.5F);
			RightArm.addChild(RightArmor_r1);
			setRotationAngle(RightArmor_r1, 0.0F, 0.0F, 0.0873F);
			RightArmor_r1.texOffs(14, 35).addBox(-1.3087F, -1.3206F, -3.0F, 4.0F, 5.0F, 5.0F, 0.0F, false);

			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(32, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.2F, true);
			LeftArm.texOffs(34, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.3F, true);

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

			Head = new ModelRenderer(this);
			Head.setPos(0.0F, 0.0F, 0.0F);
			setRotationAngle(Head, -0.1047F, 0.0873F, 0.0F);
			Head.texOffs(62, 0).addBox(-4.0F, -8.0F, -4.5F, 8.0F, 8.0F, 1.0F, 0.2F, true);
			Head.texOffs(2, 49).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 7.0F, 8.0F, 0.3F, true);
			Head.texOffs(74, 10).addBox(1.575F, -5.55F, -4.45F, 1.0F, 1.0F, 0.0F, 0.4F, true);
			Head.texOffs(73, 10).addBox(-2.6F, -5.525F, -4.45F, 1.0F, 1.0F, 0.0F, 0.4F, true);

			HatLayer_r1 = new ModelRenderer(this);
			HatLayer_r1.setPos(-0.85F, 23.6F, 5.1F);
			Head.addChild(HatLayer_r1);
			setRotationAngle(HatLayer_r1, 0.2618F, 0.2618F, 0.0F);
			HatLayer_r1.texOffs(73, 7).addBox(0.5F, -27.5F, -4.0F, 1.0F, 1.0F, 1.0F, 0.4F, true);

			HatLayer_r2 = new ModelRenderer(this);
			HatLayer_r2.setPos(-1.1F, 23.6F, 4.85F);
			Head.addChild(HatLayer_r2);
			setRotationAngle(HatLayer_r2, 0.2618F, -0.2618F, 0.0F);
			HatLayer_r2.texOffs(73, 7).addBox(0.5F, -27.5F, -4.0F, 1.0F, 1.0F, 1.0F, 0.4F, true);
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
			Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.xRot = x;
			modelRenderer.yRot = y;
			modelRenderer.zRot = z;
		}
	}
	
}
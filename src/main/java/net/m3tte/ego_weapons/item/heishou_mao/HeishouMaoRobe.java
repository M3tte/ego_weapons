
package net.m3tte.ego_weapons.item.heishou_mao;

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
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
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

public class HeishouMaoRobe extends GenericEgoWeaponsArmor {

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
			return "heishou_mao_robe";
		}

		@Override
		public float getToughness() {
			return 5f;
		}

		@Override
		public float getKnockbackResistance() {
			return 0.1f;
		}
	};

	public HeishouMaoRobe(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}

	public HeishouMaoRobe(IArmorMaterial armorMaterial, EquipmentSlotType slot, Properties props, float redResistance, float whiteResistance, float blackResistance, float paleResistance, float slashResistance, float pierceResistance, float bluntResistance, float bonusStagger, float bonusSanity) {
		super(armorMaterial, slot, props, redResistance, whiteResistance, blackResistance, paleResistance,slashResistance, pierceResistance, bluntResistance, bonusStagger, bonusSanity);


	}

	// Texture Override
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "ego_weapons:textures/entities/heishou_pack_mao_robe.png";
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

	static Item mask = new HeishouMaoRobe(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.HEAD, new Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			HeishouPackRobeModel m = new HeishouPackRobeModel();
			armorModel.head = m.Head;
			armorModel.hat = m.Dummy;
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
	static Item chest = new HeishouMaoRobe(firefistArmor, EquipmentSlotType.CHEST, new Properties().tab(ItemGroup.TAB_COMBAT), 1.2f, 1.2f, 0.8f ,0.8f, 0.7f, 1f, 1.3f, 2, 0) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);


			HeishouPackRobeModel m = new HeishouPackRobeModel();
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
			list.add(new StringTextComponent("A robe commonly used by Heishou hares.").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
			list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

			list.add(new StringTextComponent("= - - - - - - - [Page: " + ((EgoWeaponsKeybinds.getUiPage() % 4) + 1) + "/4] - - - - - - - =").withStyle(TextFormatting.GRAY));
			list.add(new TranslationTextComponent("desc.ego_weapons.risk.4"));
			list.add(new StringTextComponent(" "));
			switch (EgoWeaponsKeybinds.getUiPage() % 4) {
				case 0:
					resistanceMods(itemstack, world, list, flag);
					break;
				case 1:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"rupture", "speed"});
					else
						generateDescription(list, "heishou_mao_robe", "passive", 2);
					break;
				case 2:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"speed_up"});
					else
						generateDescription(list, "heishou_mao_robe", "passive2", 1);
					break;
				case 3:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"speed_up","strider_mao"});
					else
						generateDescription(list, "heishou_mao_robe", "ability", 7);
					break;
			}

			list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
		}

	};

	static Item pants = new HeishouMaoRobe(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.LEGS, new Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			HeishouPackRobeModel m = new HeishouPackRobeModel();
			armorModel.leftLeg = m.LeftLeg;
			armorModel.rightLeg = m.RightLeg;
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
	public static class HeishouPackRobeModel extends EntityModel<Entity> {
		private final ModelRenderer Dummy;
		private final ModelRenderer Body;
		private final ModelRenderer cube_r1;
		private final ModelRenderer cube_r2;
		private final ModelRenderer cube_r3;
		private final ModelRenderer cube_r4;
		private final ModelRenderer cube_r5;
		private final ModelRenderer Cloak_r1;
		private final ModelRenderer RightArm;
		private final ModelRenderer LeftArm;
		private final ModelRenderer Head;
		private final ModelRenderer Hat_r1;
		private final ModelRenderer Hat_r2;
		private final ModelRenderer Hat_r3;
		private final ModelRenderer Hat_r4;
		private final ModelRenderer Hat_r5;
		private final ModelRenderer Hat_r6;
		private final ModelRenderer RightLeg;
		private final ModelRenderer LeftLeg;

		public HeishouPackRobeModel() {
			texWidth = 145;
			texHeight = 80;

			Dummy = new ModelRenderer(this);
			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(24, 0).addBox(-4.5F, 0.0F, -2.5F, 9.0F, 14.0F, 5.0F, 0.0F, false);
			Body.texOffs(24, 19).addBox(-4.5F, 14.0F, -2.5F, 9.0F, 2.0F, 5.0F, 0.25F, false);
			Body.texOffs(23, 27).addBox(-5.0F, 16.0F, -3.0F, 10.0F, 3.0F, 6.0F, 0.0F, false);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
			Body.texOffs(52, 0).addBox(-4.0F, 8.5F, -2.0F, 8.0F, 2.0F, 4.0F, 0.35F, false);
			Body.texOffs(55, 30).addBox(-1.25F, 10.75F, 2.0F, 3.0F, 3.0F, 3.0F, -0.2F, false);

			cube_r1 = new ModelRenderer(this);
			cube_r1.setPos(1.0F, 13.25F, 2.5F);
			Body.addChild(cube_r1);
			setRotationAngle(cube_r1, -1.0046F, -0.544F, 0.6379F);
			cube_r1.texOffs(55, 30).addBox(-2.0F, -3.0F, -1.0F, 3.0F, 3.0F, 3.0F, -0.2F, false);

			cube_r2 = new ModelRenderer(this);
			cube_r2.setPos(2.0F, 1.0F, -1.825F);
			Body.addChild(cube_r2);
			setRotationAngle(cube_r2, 0.0F, 0.0F, 0.0873F);
			cube_r2.texOffs(17, 44).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 5.0F, 1.0F, 0.0F, false);

			cube_r3 = new ModelRenderer(this);
			cube_r3.setPos(-2.0F, 0.5F, -2.075F);
			Body.addChild(cube_r3);
			setRotationAngle(cube_r3, 0.0F, 0.0F, -0.0873F);
			cube_r3.texOffs(17, 44).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 9.0F, 1.0F, 0.0F, false);

			cube_r4 = new ModelRenderer(this);
			cube_r4.setPos(0.0F, 1.25F, 1.0F);
			Body.addChild(cube_r4);
			setRotationAngle(cube_r4, 0.0F, 0.0F, -0.2225F);
			cube_r4.texOffs(19, 36).addBox(-3.0F, -2.0F, -4.0F, 8.0F, 2.0F, 6.0F, 0.0F, false);

			cube_r5 = new ModelRenderer(this);
			cube_r5.setPos(0.0F, 0.75F, 0.0F);
			Body.addChild(cube_r5);
			setRotationAngle(cube_r5, 0.0F, 0.0F, 0.0829F);
			cube_r5.texOffs(17, 36).addBox(-5.0F, -2.0F, -3.0F, 10.0F, 2.0F, 6.0F, -0.1F, false);

			Cloak_r1 = new ModelRenderer(this);
			Cloak_r1.setPos(1.25F, 17.0F, -3.65F);
			Body.addChild(Cloak_r1);
			setRotationAngle(Cloak_r1, -0.0873F, 0.0F, 0.0F);
			Cloak_r1.texOffs(55, 36).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 7.0F, 1.0F, -0.5F, false);

			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(0, 26).addBox(-3.0F, 4.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.3F, false);
			RightArm.texOffs(0, 36).addBox(-3.0F, 3.75F, -2.0F, 4.0F, 6.0F, 4.0F, 0.5F, false);
			RightArm.texOffs(52, 6).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.5F, false);
			RightArm.texOffs(52, 16).addBox(-4.0F, 3.75F, -3.0F, 6.0F, 1.0F, 6.0F, -0.2F, false);

			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(52, 6).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.5F, false);
			LeftArm.texOffs(52, 16).addBox(-2.0F, 3.75F, -3.0F, 6.0F, 1.0F, 6.0F, -0.2F, false);
			LeftArm.texOffs(0, 26).addBox(-1.0F, 4.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.3F, true);
			LeftArm.texOffs(0, 36).addBox(-1.0F, 3.675F, -2.0F, 4.0F, 6.0F, 4.0F, 0.5F, true);

			Head = new ModelRenderer(this);
			Head.setPos(1.0F, -1.0F, 0.0F);
			setRotationAngle(Head, -0.1047F, 0.0873F, 0.0F);
			Head.texOffs(21, 44).addBox(-5.5F, -0.5F, -4.5F, 9.0F, 2.0F, 9.0F, 0.0F, false);

			Hat_r1 = new ModelRenderer(this);
			Hat_r1.setPos(-1.0F, -7.075F, 4.75F);
			Head.addChild(Hat_r1);
			setRotationAngle(Hat_r1, 2.7925F, 0.0F, 0.0F);
			Hat_r1.texOffs(66, 9).addBox(-7.0F, 0.0F, -5.0F, 14.0F, 0.0F, 10.0F, 0.0F, false);

			Hat_r2 = new ModelRenderer(this);
			Hat_r2.setPos(2.25F, -7.575F, 0.0F);
			Head.addChild(Hat_r2);
			setRotationAngle(Hat_r2, 0.0F, -1.5708F, 0.3054F);
			Hat_r2.texOffs(66, 9).addBox(-7.0F, 0.0F, -7.0F, 14.0F, 0.0F, 10.0F, 0.0F, false);

			Hat_r3 = new ModelRenderer(this);
			Hat_r3.setPos(-5.5F, -7.075F, 0.0F);
			Head.addChild(Hat_r3);
			setRotationAngle(Hat_r3, 0.0F, -1.5708F, 2.7925F);
			Hat_r3.texOffs(66, 9).addBox(-7.0F, 0.0F, -5.0F, 14.0F, 0.0F, 10.0F, 0.0F, false);

			Hat_r4 = new ModelRenderer(this);
			Hat_r4.setPos(-1.0F, -7.575F, -3.5F);
			Head.addChild(Hat_r4);
			setRotationAngle(Hat_r4, 0.2618F, 0.0F, 0.0F);
			Hat_r4.texOffs(66, 19).addBox(-7.0F, 0.0F, -7.0F, 14.0F, 0.0F, 10.0F, 0.0F, false);

			Hat_r5 = new ModelRenderer(this);
			Hat_r5.setPos(-1.0F, -2.825F, -10.25F);
			Head.addChild(Hat_r5);
			setRotationAngle(Hat_r5, 1.6144F, 0.0F, 0.0F);
			Hat_r5.texOffs(80, 0).addBox(-7.0F, 0.0F, -7.0F, 14.0F, 0.0F, 10.0F, 0.0F, false);

			Hat_r6 = new ModelRenderer(this);
			Hat_r6.setPos(-1.0F, -7.575F, -3.0F);
			Head.addChild(Hat_r6);
			setRotationAngle(Hat_r6, 0.3491F, 0.0F, 0.0F);
			Hat_r6.texOffs(66, 9).addBox(-7.0F, 0.0F, -7.0F, 14.0F, 0.0F, 10.0F, 0.0F, false);

			RightLeg = new ModelRenderer(this);
			RightLeg.setPos(-1.9F, 12.0F, 0.0F);
			setRotationAngle(RightLeg, 0.192F, 0.0F, 0.0349F);
			RightLeg.texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.3F, false);
			RightLeg.texOffs(0, 47).addBox(-2.0F, -0.25F, -2.0F, 4.0F, 12.0F, 4.0F, 0.45F, false);

			LeftLeg = new ModelRenderer(this);
			LeftLeg.setPos(1.9F, 12.0F, 0.0F);
			setRotationAngle(LeftLeg, -0.1745F, 0.0F, -0.0349F);
			LeftLeg.texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.3F, true);
			LeftLeg.texOffs(0, 47).addBox(-2.05F, -0.25F, -2.0F, 4.0F, 12.0F, 4.0F, 0.45F, true);
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

package net.m3tte.ego_weapons.item.solemn_lament;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.item.NoArmorToughnessMaterial;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
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

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class SolemnLamentArmor extends ArmorItem {

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
			return "solemn_lament";
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

	public SolemnLamentArmor(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}

	// Texture Override
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "ego_weapons:textures/entities/solemn_lament.png";
	}

	public static Item getArmorForSlot(EquipmentSlotType slot) {
		switch (slot) {
			default: return null;
			case CHEST: return chest;
			case HEAD: return butterfly;
		}
	}
	static Item chest = new SolemnLamentArmor(solemnLamentArmor, EquipmentSlotType.CHEST, new Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);



			armorModel.head = new SolemnLamentSuitModel().Head;
			armorModel.hat = new SolemnLamentSuitModel().Head;
			armorModel.body = new SolemnLamentSuitModel().Body;
			armorModel.leftArm = new SolemnLamentSuitModel().LeftArm;
			armorModel.rightArm = new SolemnLamentSuitModel().RightArm;
			armorModel.crouching = living.isCrouching();
			armorModel.riding = defaultModel.riding;
			armorModel.young = living.isBaby();
			return armorModel;
		}

		@Override
		public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(new StringTextComponent("Could the small wings... of a butterfly have... fluttered away from this... place...?").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
			list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

			// Temporary Ineffeciency, Will be resolved once damage efficiencies exist
			list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 1) + 1) + "/1] - - - - - - - =").withStyle(TextFormatting.GRAY));

			switch (EgoWeaponsKeybinds.getUiPage() % 1) {
				case 0:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"eternal_rest","living_departed", "sinking"});
					else
						generateDescription(list,"solemn_lament_armor", "ability", 2);
					break;
			}

			list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));



		}

	};
	static Item butterfly = new SolemnLamentArmor(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.HEAD, new Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			SolemnLamentSuitModel m = new SolemnLamentSuitModel();
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
	public static class SolemnLamentSuitModel extends EntityModel<Entity> {
		private final ModelRenderer Body;
		private final ModelRenderer Coffin;
		private final ModelRenderer cube_r1;
		private final ModelRenderer cube_r2;
		private final ModelRenderer cube_r3;
		private final ModelRenderer cube_r4;
		private final ModelRenderer RightArm;
		private final ModelRenderer LeftArm;
		private final ModelRenderer Head;
		private final ModelRenderer cube_r5;
		private final ModelRenderer cube_r6;

		public SolemnLamentSuitModel() {
			texWidth = 80;
			texHeight = 80;

			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 0.0F, 0.15F, false);
			Body.texOffs(8, 0).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 13.0F, 5.0F, -0.3F, false);
			Body.texOffs(37, 1).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 14.0F, 4.0F, 0.5F, false);
			Body.texOffs(37, 19).addBox(-4.0F, 15.25F, -2.0F, 8.0F, 5.0F, 4.0F, 0.8F, false);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

			Coffin = new ModelRenderer(this);
			Coffin.setPos(1.0F, 3.0F, -15.0F);
			Body.addChild(Coffin);
			setRotationAngle(Coffin, 0.0F, 0.0F, 0.5236F);
			Coffin.texOffs(32, 37).addBox(-5.0F, -12.0F, 18.0F, 7.0F, 23.0F, 4.0F, 0.01F, false);

			cube_r1 = new ModelRenderer(this);
			cube_r1.setPos(-5.975F, -4.35F, 21.0F);
			Coffin.addChild(cube_r1);
			setRotationAngle(cube_r1, 0.0F, 0.0F, 0.5236F);
			cube_r1.texOffs(32, 64).addBox(-3.0F, -7.0F, -3.0F, 5.0F, 6.0F, 4.0F, -0.01F, true);

			cube_r2 = new ModelRenderer(this);
			cube_r2.setPos(3.85F, -4.95F, 21.0F);
			Coffin.addChild(cube_r2);
			setRotationAngle(cube_r2, 0.0F, 0.0F, -0.5236F);
			cube_r2.texOffs(32, 64).addBox(-3.0F, -7.0F, -3.0F, 5.0F, 6.0F, 4.0F, -0.01F, false);

			cube_r3 = new ModelRenderer(this);
			cube_r3.setPos(-3.75F, 0.625F, 21.0F);
			Coffin.addChild(cube_r3);
			setRotationAngle(cube_r3, 0.0F, 0.0F, -0.1745F);
			cube_r3.texOffs(54, 42).addBox(-3.0F, -8.0F, -3.0F, 5.0F, 18.0F, 4.0F, 0.0F, true);

			cube_r4 = new ModelRenderer(this);
			cube_r4.setPos(1.75F, 0.75F, 21.0F);
			Coffin.addChild(cube_r4);
			setRotationAngle(cube_r4, 0.0F, 0.0F, 0.1745F);
			cube_r4.texOffs(54, 42).addBox(-3.0F, -8.0F, -3.0F, 5.0F, 18.0F, 4.0F, 0.0F, false);

			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(0, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
			RightArm.texOffs(16, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);

			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(0, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, true);
			LeftArm.texOffs(16, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, true);

			Head = new ModelRenderer(this);
			Head.setPos(1.0F, 0.0F, 0.0F);
			setRotationAngle(Head, -0.1047F, 0.0873F, 0.0F);


			cube_r5 = new ModelRenderer(this);
			cube_r5.setPos(1.25F, -8.35F, -1.5F);
			Head.addChild(cube_r5);
			setRotationAngle(cube_r5, 0.1847F, -0.7767F, 2.8457F);
			cube_r5.texOffs(-6, 55).addBox(-4.0F, 0.0F, -3.0F, 5.0F, 0.0F, 6.0F, 0.0F, false);

			cube_r6 = new ModelRenderer(this);
			cube_r6.setPos(0.0F, -8.625F, -0.225F);
			Head.addChild(cube_r6);
			setRotationAngle(cube_r6, 0.4314F, 0.6891F, 0.5913F);
			cube_r6.texOffs(-6, 55).addBox(-4.0F, 0.0F, -3.0F, 5.0F, 0.0F, 6.0F, 0.0F, false);
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
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.xRot = x;
			modelRenderer.yRot = y;
			modelRenderer.zRot = z;
		}
	}

}

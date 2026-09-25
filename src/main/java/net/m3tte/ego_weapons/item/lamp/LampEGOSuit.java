
package net.m3tte.ego_weapons.item.lamp;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.EgoWeaponsCreativeTabs;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.item.NoArmorToughnessMaterial;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.procedures.TooltipFuncs;
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

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.*;

public class LampEGOSuit extends GenericEgoWeaponsArmor {

	static IArmorMaterial lobotomyEgoLamp = new IArmorMaterial() {
		@Override
		public int getDurabilityForSlot(EquipmentSlotType slot) {
			return new int[]{13, 15, 15, 11}[slot.getIndex()] * 999;
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
			return "lamp";
		}

		@Override
		public float getToughness() {
			return 8f;
		}

		@Override
		public float getKnockbackResistance() {
			return 0.1f;
		}
	};

	public LampEGOSuit(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}

	public LampEGOSuit(IArmorMaterial armorMaterial, EquipmentSlotType slot, Properties props, float redResistance, float whiteResistance, float blackResistance, float paleResistance, float slashResistance, float pierceResistance, float bluntResistance, float bonusStagger, float bonusSanity) {
		super(armorMaterial, slot, props, redResistance, whiteResistance, blackResistance, paleResistance,slashResistance, pierceResistance, bluntResistance, bonusStagger, bonusSanity);


	}

	// Texture Override
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "ego_weapons:textures/entities/lamp/suit_base.png";
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

	static Item pants = new LampEGOSuit(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.LEGS, new Properties().tab(EgoWeaponsCreativeTabs.EGO_WEAPONS)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			LobotomyEgoLampModel m = new LobotomyEgoLampModel();
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
	static Item chest = new LampEGOSuit(lobotomyEgoLamp, EquipmentSlotType.CHEST, new Properties().tab(EgoWeaponsCreativeTabs.EGO_WEAPONS), 0.5f, 0.5f, 0.5f ,0.5f, 1.3f, 1f, 0.7f, -4, -4) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);


			LobotomyEgoLampModel m = new LobotomyEgoLampModel();
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
			TooltipFuncs.generateItemDescription(list, "desc.ego_weapons.justitia_cloak.desc");
			list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

			list.add(new StringTextComponent("= - - - - - - - [Page: " + ((EgoWeaponsKeybinds.getUiPage() % 4) + 1) + "/4] - - - - - - - =").withStyle(TextFormatting.GRAY));
			list.add(new TranslationTextComponent("desc.ego_weapons.risk.aleph"));
			list.add(new StringTextComponent(" "));
			switch (EgoWeaponsKeybinds.getUiPage() % 4) {
				case 0:
					resistanceMods(itemstack, world, list, flag);
					break;
				case 1:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"rupture", "speed"});
					else
						generateDescription(list, "lamp_suit", "passive", 2, true, EgoWeaponsEffects.BLEED.get(), EgoWeaponsEffects.BURN.get());
					break;
				case 2:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"speed_up"});
					else
						generateDescription(list, "justitia_cloak", "passive2", 4);
					break;
				case 3:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"speed_up","strider_mao"});
					else
						generateDescription(list, "justitia_cloak", "ability", 6);
					break;
			}

			generateStatusHelp(list);
		}

	};


	public static class LobotomyEgoLampModel extends EntityModel<Entity> {
		private final ModelRenderer RightLeg;
		private final ModelRenderer LeftLeg;
		private final ModelRenderer Body;
		private final ModelRenderer Fuzz_r1;
		private final ModelRenderer Fuzz_r2;
		private final ModelRenderer RightArm;
		private final ModelRenderer RightArmLayer;
		private final ModelRenderer RightArmLayer2;
		private final ModelRenderer LeftArm;
		private final ModelRenderer LeftArmLayer;
		private final ModelRenderer LeftArmLayer2;

		public LobotomyEgoLampModel() {
			texWidth = 128;
			texHeight = 128;

			RightLeg = new ModelRenderer(this);
			RightLeg.setPos(-1.9F, 12.0F, 0.0F);
			setRotationAngle(RightLeg, 0.192F, 0.0F, 0.0349F);
			RightLeg.texOffs(0, 58).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, true);
			RightLeg.texOffs(0, 74).addBox(-2.5F, 10.5F, -3.575F, 5.0F, 2.0F, 6.0F, -0.1F, false);

			LeftLeg = new ModelRenderer(this);
			LeftLeg.setPos(1.9F, 12.0F, 0.0F);
			setRotationAngle(LeftLeg, -0.1745F, 0.0F, -0.0349F);
			LeftLeg.texOffs(0, 58).addBox(-1.8F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
			LeftLeg.texOffs(0, 74).addBox(-2.3F, 10.5F, -3.575F, 5.0F, 2.0F, 6.0F, -0.1F, false);

			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 0.0F, 0.2F, false);
			Body.texOffs(8, 0).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 13.0F, 5.0F, -0.2F, false);
			Body.texOffs(8, 18).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 15.0F, 5.0F, 0.2F, false);
			Body.texOffs(36, 0).addBox(-5.0F, 14.525F, -3.0F, 10.0F, 3.0F, 6.0F, 0.05F, false);
			Body.texOffs(36, 11).addBox(-5.0F, 17.625F, -3.0F, 10.0F, 4.0F, 6.0F, 0.4F, false);

			Fuzz_r1 = new ModelRenderer(this);
			Fuzz_r1.setPos(-1.3F, 35.55F, -3.55F);
			Body.addChild(Fuzz_r1);
			setRotationAngle(Fuzz_r1, 0.0F, 0.7854F, 0.0F);
			Fuzz_r1.texOffs(54, 48).addBox(-9.0F, -36.0F, 2.0F, 3.0F, 22.0F, 0.0F, 0.0F, false);

			Fuzz_r2 = new ModelRenderer(this);
			Fuzz_r2.setPos(-1.55F, 35.55F, -0.55F);
			Body.addChild(Fuzz_r2);
			setRotationAngle(Fuzz_r2, 0.0F, 2.3562F, 0.0F);
			Fuzz_r2.texOffs(54, 48).addBox(-9.0F, -36.0F, 2.0F, 3.0F, 22.0F, 0.0F, 0.0F, false);

			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(68, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.15F, false);
			RightArm.texOffs(86, 0).addBox(-3.5F, -2.5F, -2.5F, 5.0F, 11.0F, 5.0F, -0.25F, false);

			RightArmLayer = new ModelRenderer(this);
			RightArmLayer.setPos(3.2F, 21.75F, 1.45F);
			RightArm.addChild(RightArmLayer);
			setRotationAngle(RightArmLayer, 0.0F, -0.7854F, 0.0F);
			RightArmLayer.texOffs(55, 34).addBox(-8.0F, -25.0F, 2.0F, 6.0F, 12.0F, 0.0F, 0.0F, false);

			RightArmLayer2 = new ModelRenderer(this);
			RightArmLayer2.setPos(0.25F, 21.75F, -4.25F);
			RightArm.addChild(RightArmLayer2);
			setRotationAngle(RightArmLayer2, 0.0F, 0.7854F, 0.0F);
			RightArmLayer2.texOffs(43, 34).addBox(-8.0F, -25.0F, 2.0F, 6.0F, 12.0F, 0.0F, 0.0F, false);

			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(32, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
			LeftArm.texOffs(68, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.15F, true);
			LeftArm.texOffs(86, 0).addBox(-1.5F, -2.5F, -2.5F, 5.0F, 11.0F, 5.0F, -0.25F, true);

			LeftArmLayer = new ModelRenderer(this);
			LeftArmLayer.setPos(-0.375F, 21.775F, 4.225F);
			LeftArm.addChild(LeftArmLayer);
			setRotationAngle(LeftArmLayer, 0.0F, -2.3562F, 0.0F);
			LeftArmLayer.texOffs(43, 34).addBox(-8.0F, -25.0F, 2.0F, 6.0F, 12.0F, 0.0F, 0.0F, false);

			LeftArmLayer2 = new ModelRenderer(this);
			LeftArmLayer2.setPos(-3.225F, 21.8F, -1.35F);
			LeftArm.addChild(LeftArmLayer2);
			setRotationAngle(LeftArmLayer2, 0.0F, 2.3562F, 0.0F);
			LeftArmLayer2.texOffs(55, 34).addBox(-8.0F, -25.0F, 2.0F, 6.0F, 12.0F, 0.0F, 0.0F, false);
		}

		@Override
		public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
			//previously the render function, render code was moved to a method below
		}

		@Override
		public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
			RightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.xRot = x;
			modelRenderer.yRot = y;
			modelRenderer.zRot = z;
		}
	}
	
}

package net.m3tte.ego_weapons.item.rat;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.item.NoArmorToughnessMaterial;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.potion.countEffects.TremorEffect;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoWeaponsArmor;
import net.minecraft.client.renderer.entity.EntityRendererManager;
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

import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class RatJacket extends GenericEgoWeaponsArmor {

	static IArmorMaterial solemnLamentArmor = new IArmorMaterial() {
		@Override
		public int getDurabilityForSlot(EquipmentSlotType slot) {
			return new int[]{13, 15, 16, 11}[slot.getIndex()] * 999;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType slot) {
			return new int[]{0, 0, 14, 0}[slot.getIndex()];
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
			return "rat";
		}

		@Override
		public float getToughness() {
			return 3f;
		}

		@Override
		public float getKnockbackResistance() {
			return 0.1f;
		}
	};

	public RatJacket(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}

	// Texture Override
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "ego_weapons:textures/entities/rat_outfit.png";
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

	public RatJacket(IArmorMaterial armorMaterial, EquipmentSlotType slot, Properties props, float redResistance, float whiteResistance, float blackResistance, float paleResistance, float slashResistance, float pierceResistance, float bluntResistance, float bonusStagger, float bonusSanity) {
		super(armorMaterial, slot, props, redResistance, whiteResistance, blackResistance, paleResistance,slashResistance, pierceResistance, bluntResistance, bonusStagger, bonusSanity);
	}

	static Item chest = new RatJacket(solemnLamentArmor, EquipmentSlotType.CHEST, new Properties().tab(ItemGroup.TAB_COMBAT), 0.9f, 1.25f, 1f ,1.5f, 1f, 0.7f, 1.5f, 2, -2) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);


			RatOutfitModel m = new RatOutfitModel();
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
			list.add(new StringTextComponent("A shabby jacket stuffed with cloth. Provides a bit of protection.").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
			list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

			list.add(new StringTextComponent("= - - - - - - - [Page: " + ((EgoWeaponsKeybinds.getUiPage() % 3) + 1) + "/3] - - - - - - - =").withStyle(TextFormatting.GRAY));
			list.add(new TranslationTextComponent("desc.ego_weapons.risk.10"));
			list.add(new StringTextComponent(" "));
			switch (EgoWeaponsKeybinds.getUiPage() % 3) {
				case 0:
					resistanceMods(itemstack, world, list, flag);
					break;
				case 1:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{});
					else
						generateDescription(list, "rat_outfit", "passive", 1);
					break;
				case 2:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"speed_up","offense_up","defense_down"});
					else
						generateDescription(list, "rat_outfit", "ability", 3);
					break;
			}

			list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
		}

	};
	public static float modifyDamageAmount(LivingEntity target, LivingEntity source, float dmgMult, DamageSource damageSource) {

		int missingHealthOnSource = (int)((source.getHealth() / source.getMaxHealth()) * 100);
		if (missingHealthOnSource >= 15) {
			int stacks = missingHealthOnSource / 15;

			SharedFunctions.incrementBonusDamage(damageSource, stacks*0.05f);
			dmgMult += stacks*0.05f;

		}

		return dmgMult;
	}

	static Item pants = new RatJacket(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.LEGS, new Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			RatOutfitModel m = new RatOutfitModel();
			armorModel.body = m.Dummy;
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
	public class RatOutfitModel extends EntityModel<Entity> {

		private final ModelRenderer Dummy;
		private final ModelRenderer Body;
		private final ModelRenderer Cloak6_r1;
		private final ModelRenderer Cloak5_r1;
		private final ModelRenderer Cloak4_r1;
		private final ModelRenderer Cloak5_r2;
		private final ModelRenderer Cloak4_r2;
		private final ModelRenderer Cloak4_r3;
		private final ModelRenderer Cloak3_r1;
		private final ModelRenderer RightArm;
		private final ModelRenderer LeftArm;
		private final ModelRenderer RightLeg;
		private final ModelRenderer LeftLeg;

		public RatOutfitModel() {
			texWidth = 145;
			texHeight = 80;

			Dummy = new ModelRenderer(this);
			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(-2, -2).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 2.0F, 0.15F, false);
			Body.texOffs(37, 1).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 14.0F, 4.0F, 0.6F, false);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

			Cloak6_r1 = new ModelRenderer(this);
			Cloak6_r1.setPos(3.0F, 11.0F, 8.0F);
			Body.addChild(Cloak6_r1);
			setRotationAngle(Cloak6_r1, 0.1743F, -1.1245F, -0.3959F);
			Cloak6_r1.texOffs(46, 58).addBox(-6.0F, -12.0F, -3.0F, 2.0F, 0.0F, 2.0F, 0.85F, false);

			Cloak5_r1 = new ModelRenderer(this);
			Cloak5_r1.setPos(8.0F, 10.0F, 7.0F);
			Body.addChild(Cloak5_r1);
			setRotationAngle(Cloak5_r1, 0.0911F, -0.6049F, -0.2903F);
			Cloak5_r1.texOffs(48, 60).addBox(-6.0F, -12.0F, -2.0F, 2.0F, 0.0F, 0.0F, 0.85F, false);

			Cloak4_r1 = new ModelRenderer(this);
			Cloak4_r1.setPos(9.0F, 11.0F, 3.0F);
			Body.addChild(Cloak4_r1);
			setRotationAngle(Cloak4_r1, 0.0F, -0.1745F, -0.1309F);
			Cloak4_r1.texOffs(47, 59).addBox(-6.0F, -12.0F, -2.0F, 3.0F, 0.0F, 1.0F, 0.85F, false);

			Cloak5_r2 = new ModelRenderer(this);
			Cloak5_r2.setPos(-3.0F, 12.0F, 1.0F);
			Body.addChild(Cloak5_r2);
			setRotationAngle(Cloak5_r2, 0.0594F, 0.8266F, 0.5139F);
			Cloak5_r2.texOffs(49, 60).addBox(-5.0F, -12.0F, -2.0F, 2.0F, 0.0F, 0.0F, 0.85F, false);

			Cloak4_r2 = new ModelRenderer(this);
			Cloak4_r2.setPos(-4.0F, 12.0F, 2.0F);
			Body.addChild(Cloak4_r2);
			setRotationAngle(Cloak4_r2, 0.0453F, 0.478F, 0.491F);
			Cloak4_r2.texOffs(49, 60).addBox(-5.0F, -12.0F, -2.0F, 2.0F, 0.0F, 0.0F, 0.85F, false);

			Cloak4_r3 = new ModelRenderer(this);
			Cloak4_r3.setPos(-4.0F, 12.0F, 1.0F);
			Body.addChild(Cloak4_r3);
			setRotationAngle(Cloak4_r3, 0.0F, 0.0F, 0.3927F);
			Cloak4_r3.texOffs(47, 59).addBox(-6.0F, -12.0F, -2.0F, 3.0F, 0.0F, 1.0F, 0.85F, false);

			Cloak3_r1 = new ModelRenderer(this);
			Cloak3_r1.setPos(9.0F, 11.0F, 0.0F);
			Body.addChild(Cloak3_r1);
			setRotationAngle(Cloak3_r1, 0.0F, 0.1309F, -0.1309F);
			Cloak3_r1.texOffs(47, 59).addBox(-6.0F, -12.0F, -2.0F, 2.0F, 0.0F, 1.0F, 0.85F, false);

			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(92, 52).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
			RightArm.texOffs(127, 41).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.3F, false);

			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(92, 52).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, true);
			LeftArm.texOffs(129, 40).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.3F, true);

			RightLeg = new ModelRenderer(this);
			RightLeg.setPos(-1.9F, 12.0F, 0.0F);
			setRotationAngle(RightLeg, 0.192F, 0.0F, 0.0349F);
			RightLeg.texOffs(40, 41).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.2F, false);
			RightLeg.texOffs(73, 52).addBox(-2.0F, 7.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.4F, false);

			LeftLeg = new ModelRenderer(this);
			LeftLeg.setPos(1.9F, 12.0F, 0.0F);
			setRotationAngle(LeftLeg, -0.1745F, 0.0F, -0.0349F);
			LeftLeg.texOffs(41, 41).addBox(-1.95F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.2F, false);
			LeftLeg.texOffs(73, 52).addBox(-2.0F, 7.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.4F, false);
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
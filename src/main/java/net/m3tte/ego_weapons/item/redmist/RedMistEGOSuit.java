
package net.m3tte.ego_weapons.item.redmist;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.item.NoArmorToughnessMaterial;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.potion.ManifestEgoPotionEffect;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoWeaponsArmor;
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
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class RedMistEGOSuit extends GenericEgoWeaponsArmor {

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
			return "kali_ego";
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

	public RedMistEGOSuit(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "ego_weapons:textures/entities/red_mist_ego.png";
	}

	public static Item getArmorForSlot(EquipmentSlotType slot) {
		switch (slot) {
			default: return null;
			case HEAD: return head;
			case CHEST: return chest;
			case LEGS: return legs;
		}
	}

	static Item head = new RedMistEGOSuit(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.HEAD, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			RedMistEGOModel m = new RedMistEGOModel();
			armorModel.head = m.Head;
			armorModel.hat = m.Head;
			armorModel.body = m.Body;
			return armorModel;
		}

		@Override
		public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
			if (!entity.hasEffect(ManifestEgoPotionEffect.potion) && entity.getItemBySlot(EquipmentSlotType.HEAD).equals(itemstack)) {
				entity.inventory.removeItem(itemstack);
			}
		}
	};

	public RedMistEGOSuit(IArmorMaterial armorMaterial, EquipmentSlotType slot, Properties props, float redResistance, float whiteResistance, float blackResistance, float paleResistance, float slashResistance, float pierceResistance, float bluntResistance, float bonusStagger, float bonusSanity) {
		super(armorMaterial, slot, props, redResistance, whiteResistance, blackResistance, paleResistance,slashResistance, pierceResistance, bluntResistance, bonusStagger, bonusSanity);
	}
	static Item chest = new RedMistEGOSuit(armormaterial, EquipmentSlotType.CHEST, new Item.Properties().tab(ItemGroup.TAB_COMBAT), 0.5f, 0.5f, 1.0f ,1.6f, 0.8f, 0.5f, 1.3f, 10, 0) {



		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);

			RedMistEGOModel m = new RedMistEGOModel();

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
		public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
			if (!entity.hasEffect(ManifestEgoPotionEffect.potion) && entity.getItemBySlot(EquipmentSlotType.CHEST).equals(itemstack)) {
				entity.setItemSlot(EquipmentSlotType.CHEST, EgoWeaponsItems.JACKET_OF_THE_RED_MIST.get().getDefaultInstance());
			}
		}

		@Override
		public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(new StringTextComponent("It was that armor... To protect everyone...").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
			list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

			list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 5) + 1) + "/5] - - - - - - - =").withStyle(TextFormatting.GRAY));
			list.add(new TranslationTextComponent("desc.ego_weapons.risk.1"));
			list.add(new StringTextComponent(" "));
			switch (EgoWeaponsKeybinds.getUiPage() % 5) {
				case 0:
					resistanceMods(itemstack, world, list, flag);
					break;
				case 1:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{});
					else
						generateDescription(list, "red_mist_armor", "passive", 1);
					break;
				case 2:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"defense_up", "offense_up", "power_up", "manifest_ego"});
					else
						generateDescription(list, "red_mist_armor", "passive2", 3);
					break;
				case 3:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"manifest_ego"});
					else
						generateDescription(list,"red_mist_armor", "ability", 1);
					break;
				case 4:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"resilience","protection"});
					else
						generateDescription(list,"red_mist_armor", "ability2", 4);
					break;
			}

			list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
		}
	};

	static Item legs = new RedMistEGOSuit(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.LEGS, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);

			RedMistEGOModel m = new RedMistEGOModel();

			armorModel.body = m.EMPTY;
			armorModel.rightLeg = m.RightLeg;
			armorModel.leftLeg = m.LeftLeg;
			armorModel.crouching = living.isCrouching();
			armorModel.riding = defaultModel.riding;
			armorModel.young = living.isBaby();
			return armorModel;
		}

		@Override
		public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
			if (!entity.hasEffect(ManifestEgoPotionEffect.potion) && entity.getItemBySlot(EquipmentSlotType.LEGS).equals(itemstack)) {
				entity.setItemSlot(EquipmentSlotType.LEGS, EgoWeaponsItems.SUIT_LEGGINGS.get().getDefaultInstance());
			}
		}

		@Override
		public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> list, ITooltipFlag p_77624_4_) {
			super.appendHoverText(p_77624_1_, p_77624_2_, list, p_77624_4_);
		}
	};
	//@ObjectHolder("ego_weapons:cigarette")
	//public static final Item head = null;

	public static class RedMistEGOModel extends EntityModel<Entity> {
		private final ModelRenderer Head;
		private final ModelRenderer Body;
		private final ModelRenderer BodyLayer_r1;
		private final ModelRenderer BodyLayer_r2;
		private final ModelRenderer BodyLayer_r3;
		private final ModelRenderer BodyLayer_r4;
		private final ModelRenderer BodyLayer_r5;
		private final ModelRenderer RightArm;
		private final ModelRenderer EMPTY;
		private final ModelRenderer LeftArm;
		private final ModelRenderer RightLeg;
		private final ModelRenderer LeftLeg;

		public RedMistEGOModel() {
			texWidth = 128;
			texHeight = 64;
			EMPTY = new ModelRenderer(this);
			Head = new ModelRenderer(this);
			Head.setPos(0.0F, 0.0F, 0.0F);
			setRotationAngle(Head, -0.1047F, 0.0873F, 0.0F);
			Head.texOffs(52, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.5F, false);
			Head.texOffs(52, 16).addBox(-4.5F, -8.425F, -4.5F, 9.0F, 9.0F, 9.0F, 0.15F, false);
			Head.texOffs(52, 0).addBox(-1.5F, -5.425F, -5.0F, 3.0F, 3.0F, 1.0F, -0.3F, false);

			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
			Body.texOffs(0, 0).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 13.0F, 5.0F, -0.3F, false);
			Body.texOffs(28, 0).addBox(-4.0F, 0.3F, -2.0F, 8.0F, 12.0F, 4.0F, 0.4F, false);
			Body.texOffs(28, 16).addBox(-4.0F, 0.2F, -2.0F, 8.0F, 13.0F, 4.0F, 0.55F, false);

			BodyLayer_r1 = new ModelRenderer(this);
			BodyLayer_r1.setPos(0.0F, 24.0F, 0.0F);
			Body.addChild(BodyLayer_r1);
			setRotationAngle(BodyLayer_r1, 0.0F, 0.0F, -0.1309F);
			BodyLayer_r1.texOffs(31, 33).addBox(-2.0F, -25.35F, -2.75F, 4.0F, 4.0F, 0.0F, 0.0F, true);

			BodyLayer_r2 = new ModelRenderer(this);
			BodyLayer_r2.setPos(0.0F, 24.0F, 0.0F);
			Body.addChild(BodyLayer_r2);
			setRotationAngle(BodyLayer_r2, 0.0F, 0.0F, 0.1745F);
			BodyLayer_r2.texOffs(31, 33).addBox(-3.0F, -25.3F, -2.75F, 4.0F, 4.0F, 0.0F, 0.0F, false);

			BodyLayer_r3 = new ModelRenderer(this);
			BodyLayer_r3.setPos(0.0F, 24.0F, 0.0F);
			Body.addChild(BodyLayer_r3);
			setRotationAngle(BodyLayer_r3, 2.477F, 1.1377F, 2.5235F);
			BodyLayer_r3.texOffs(0, 18).addBox(-3.45F, -10.3F, -5.5F, 3.0F, 8.0F, 0.0F, 0.0F, false);

			BodyLayer_r4 = new ModelRenderer(this);
			BodyLayer_r4.setPos(0.0F, 24.0F, 0.0F);
			Body.addChild(BodyLayer_r4);
			setRotationAngle(BodyLayer_r4, 0.8027F, 1.2027F, 0.7681F);
			BodyLayer_r4.texOffs(0, 18).addBox(-3.925F, -10.3F, 5.2F, 3.0F, 8.0F, 0.0F, 0.0F, false);

			BodyLayer_r5 = new ModelRenderer(this);
			BodyLayer_r5.setPos(0.0F, 24.0F, 0.0F);
			Body.addChild(BodyLayer_r5);
			setRotationAngle(BodyLayer_r5, 0.2618F, 0.0F, 0.0F);
			BodyLayer_r5.texOffs(12, 18).addBox(-4.0F, -9.3F, 5.125F, 8.0F, 7.0F, 0.0F, 0.0F, false);

			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.3F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(32, 48).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.2F, true);
			RightArm.texOffs(48, 48).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.3F, false);

			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.3F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.2F, false);
			LeftArm.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.3F, true);

			RightLeg = new ModelRenderer(this);
			RightLeg.setPos(-1.9F, 12.0F, 0.0F);
			setRotationAngle(RightLeg, 0.192F, 0.0F, 0.0349F);
			RightLeg.texOffs(0, 26).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.2F, false);
			RightLeg.texOffs(0, 42).addBox(-2.45F, -0.5F, -2.5F, 5.0F, 13.0F, 5.0F, -0.15F, false);

			LeftLeg = new ModelRenderer(this);
			LeftLeg.setPos(1.9F, 12.0F, 0.0F);
			setRotationAngle(LeftLeg, -0.1745F, 0.0F, -0.0349F);
			LeftLeg.texOffs(0, 26).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.2F, true);
			LeftLeg.texOffs(0, 42).addBox(-2.45F, -0.5F, -2.5F, 5.0F, 13.0F, 5.0F, -0.15F, true);
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

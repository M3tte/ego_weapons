
package net.m3tte.ego_weapons.item.blackSilence;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.item.NoArmorToughnessMaterial;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.potion.OrlandoPotionEffect;
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

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class BlackSilenceArmor  extends ArmorItem {

	static IArmorMaterial blackSilenceArmor = new IArmorMaterial() {
		@Override
		public int getDurabilityForSlot(EquipmentSlotType p_200896_1_) {
			return Integer.MAX_VALUE;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType slot)  {
			return new int[]{0, 0, 22, 0}[slot.getIndex()];
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
			return "blacksilencesuit";
		}

		@Override
		public float getToughness() {
			return 10f;
		}

		@Override
		public float getKnockbackResistance() {
			return 2f;
		}
	};

	public BlackSilenceArmor(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}


	// Texture Override
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "ego_weapons:textures/entities/suppression_unit_suit.png";
	}

	public static Item getArmorForSlot(EquipmentSlotType slot) {
		switch (slot) {
			default: return null;
			case CHEST: return chest;
			case HEAD: return mask;
		}
	}
	static Item chest = new BlackSilenceArmor(blackSilenceArmor, EquipmentSlotType.CHEST, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.head = new Modelperceptionmask().Head;
			armorModel.body = new Modelblack_silence_suit().Body;
			armorModel.leftArm = new Modelblack_silence_suit().LeftArm;
			armorModel.rightArm = new Modelblack_silence_suit().RightArm;
			armorModel.crouching = living.isCrouching();
			armorModel.riding = defaultModel.riding;
			armorModel.young = living.isBaby();
			return armorModel;
		}

		@Override
		public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(new StringTextComponent("Thats that. And this is this.").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
			list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

			list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 5) + 1) + "/5] - - - - - - - =").withStyle(TextFormatting.GRAY));

			switch (EgoWeaponsKeybinds.getUiPage() % 5) {
				case 0:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"poise"});
					else
						generateDescription(list, "black_silence_suit", "passive", 4);
					break;
				case 1:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"offense_up", "orlando"});
					else
						generateDescription(list, "black_silence_suit", "passive1", 4);
					break;
				case 2:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"offense_up", "power_up"});
					else
						generateDescription(list, "black_silence_suit", "passive2", 3);
					break;
				case 3:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"orlando"});
					else
						generateDescription(list, "black_silence_suit", "ability", 1);
					break;
				case 4:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"furioso"});
					else
						generateDescription(list, "black_silence_suit", "ability1", 1);
					break;
			}

			list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
		}
	};
	static Item mask = new ArmorItem(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.HEAD, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.head = new Modelperceptionmask().Head;
			//armorModel.body	 = new Modelperceptionmask().Head;
			armorModel.crouching = living.isCrouching();
			armorModel.riding = defaultModel.riding;
			armorModel.young = living.isBaby();
			return armorModel;
		}

		@Override
		public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
			if (!entity.hasEffect(OrlandoPotionEffect.potion) && entity.getItemBySlot(EquipmentSlotType.HEAD).equals(itemstack)) {
				entity.inventory.removeItem(itemstack);
			}
		}

		@Override
		public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(new StringTextComponent("A dark mask protecting the wearers identity."));
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "ego_weapons:textures/entities/headcosmetics.png";
		}
	};

	// Made with Blockbench 4.6.5
	// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
	// Paste this class into your mod and generate all required imports
	public static class Modelblack_silence_suit extends EntityModel<Entity> {
		private final ModelRenderer Body;
		private final ModelRenderer RightArm;
		private final ModelRenderer LeftArm;
		private final ModelRenderer RightLeg;
		private final ModelRenderer LeftLeg;

		public Modelblack_silence_suit() {
			texWidth = 72;
			texHeight = 64;
			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(24, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 0.0F, 0.27F, false);
			Body.texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.4F, false);
			Body.texOffs(10, 34).addBox(-4.0F, 11.8F, -2.0F, 8.0F, 0.0F, 4.0F, 0.5F, false);
			Body.texOffs(0, 32).addBox(-4.0F, 8.6F, -3.25F, 3.0F, 4.0F, 2.0F, -0.4F, false);
			Body.texOffs(6, 34).addBox(-3.8F, 6.6F, -3.0F, 1.0F, 3.0F, 1.0F, -0.4F, false);
			Body.texOffs(6, 34).addBox(-3.3F, 7.1F, -3.0F, 1.0F, 3.0F, 1.0F, -0.4F, false);
			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngles(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(56, 48).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.26F, false);
			RightArm.texOffs(48, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.35F, false);
			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngles(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(56, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.26F, true);
			LeftArm.texOffs(48, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.35F, true);
			RightLeg = new ModelRenderer(this);
			RightLeg.setPos(-1.9F, 12.0F, 0.0F);
			setRotationAngles(RightLeg, 0.192F, 0.0F, 0.0349F);
			RightLeg.texOffs(24, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.3F, false);
			LeftLeg = new ModelRenderer(this);
			LeftLeg.setPos(1.9F, 12.0F, 0.0F);
			setRotationAngles(LeftLeg, -0.1745F, 0.0F, -0.0349F);
			LeftLeg.texOffs(24, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.3F, false);
		}

		@Override
		public void setupAnim(Entity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

		}


		@Override
		public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			//Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			RightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

		public void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.xRot = x;
			modelRenderer.yRot = y;
			modelRenderer.zRot = z;
		}

	}

	// Made with Blockbench 4.6.5
	// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
	// Paste this class into your mod and generate all required imports
	public static class Modelperceptionmask extends EntityModel<Entity> {
		private final ModelRenderer Head;

		public Modelperceptionmask() {
			texWidth = 64;
			texHeight = 64;
			Head = new ModelRenderer(this);
			Head.setPos(0.0F, 0.0F, 0.0F);
			setRotationAngle(Head, -0.1047F, 0.0873F, 0.0F);
			Head.texOffs(14, 15).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
			Head.texOffs(0, 20).addBox(-3.5F, -7.5F, -4.25F, 7.0F, 7.0F, 0.0F, 0.4F, false);
		}

		@Override
		public void setupAnim(Entity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

		}

		@Override
		public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.xRot = x;
			modelRenderer.yRot = y;
			modelRenderer.zRot = z;
		}

	}

}

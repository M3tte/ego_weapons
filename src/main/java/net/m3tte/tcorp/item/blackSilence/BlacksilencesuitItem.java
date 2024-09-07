
package net.m3tte.tcorp.item.blackSilence;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.tcorp.TcorpModElements;
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
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;
import java.util.List;

@TcorpModElements.ModElement.Tag
public class BlacksilencesuitItem extends TcorpModElements.ModElement {
	@ObjectHolder("tcorp:blacksilencesuit_helmet")
	public static final Item helmet = null;
	@ObjectHolder("tcorp:suit_of_the_black_silence")
	public static final Item body = null;
	@ObjectHolder("tcorp:blacksilencesuit_leggings")
	public static final Item legs = null;
	@ObjectHolder("tcorp:blacksilencesuit_boots")
	public static final Item boots = null;

	public BlacksilencesuitItem(TcorpModElements instance) {
		super(instance, 65);
	}

	@Override
	public void initElements() {
		IArmorMaterial armormaterial = new IArmorMaterial() {

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
		elements.items.add(() -> new ArmorItem(armormaterial, EquipmentSlotType.CHEST, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
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
			public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> list, ITooltipFlag p_77624_4_) {
				super.appendHoverText(p_77624_1_, p_77624_2_, list, p_77624_4_);
				list.add(new StringTextComponent("Thats that... and this is this..."));
				list.add(new StringTextComponent("[Ability] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Orlando").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" 10 E").withStyle(TextFormatting.AQUA)));
				list.add(new StringTextComponent("[Ability] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Furioso").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" 6 E").withStyle(TextFormatting.AQUA)));
			}

			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
				return "tcorp:textures/entities/suppression_unit_suit.png";
			}
		}.setRegistryName("suit_of_the_black_silence"));
	}

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

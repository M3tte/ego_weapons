
package net.m3tte.tcorp.item;

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
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@TcorpModElements.ModElement.Tag
public class WoodsmansarmorItem extends TcorpModElements.ModElement {
	@ObjectHolder("tcorp:woodsmansarmor_helmet")
	public static final Item helmet = null;
	@ObjectHolder("tcorp:woodsmansarmor_chestplate")
	public static final Item body = null;
	@ObjectHolder("tcorp:woodsmansarmor_leggings")
	public static final Item legs = null;
	@ObjectHolder("tcorp:woodsmansarmor_boots")
	public static final Item boots = null;

	public WoodsmansarmorItem(TcorpModElements instance) {
		super(instance, 124);
	}

	@Override
	public void initElements() {
		IArmorMaterial armormaterial = new IArmorMaterial() {
			@Override
			public int getDurabilityForSlot(EquipmentSlotType slot) {
				return new int[]{13, 15, 16, 11}[slot.getIndex()] * 999;
			}

			@Override
			public int getDefenseForSlot(EquipmentSlotType slot) {
				return new int[]{2, 5, 18, 2}[slot.getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 9;
			}

			@Override
			public SoundEvent getEquipSound() {
				return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.EMPTY;
			}


			@OnlyIn(Dist.CLIENT)
			@Override
			public String getName() {
				return "woodsmansarmor";
			}

			@Override
			public float getToughness() {
				return 8f;
			}

			@Override
			public float getKnockbackResistance() {
				return 1f;
			}
		};
		elements.items.add(() -> new ArmorItem(armormaterial, EquipmentSlotType.CHEST, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
			@Override
			@OnlyIn(Dist.CLIENT)
			public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
				BipedModel armorModel = new BipedModel(1);
				armorModel.body = new Modelwoodsmans_armor().Body;
				armorModel.leftArm = new Modelwoodsmans_armor().LeftArm;
				armorModel.rightArm = new Modelwoodsmans_armor().RightArm;
				armorModel.crouching = living.isCrouching();
				armorModel.riding = defaultModel.riding;
				armorModel.young = living.isBaby();
				return armorModel;
			}

			@Override
			public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
				super.appendHoverText(itemstack, world, list, flag);
				list.add(new StringTextComponent("This plating hums with the energy of the forest."));
				list.add(new StringTextComponent("[Ability] Protective Stance - 5E"));
			}

			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
				return "tcorp:textures/entities/woodsmans_armor.png";
			}
		}.setRegistryName("woodsmansarmor_chestplate"));
	}

	// Made with Blockbench 4.6.5
	// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
	// Paste this class into your mod and generate all required imports
	public static class Modelwoodsmans_armor extends EntityModel<Entity> {
		private final ModelRenderer Body;
		private final ModelRenderer RightArm;
		private final ModelRenderer LeftArm;

		public Modelwoodsmans_armor() {
			texWidth = 64;
			texHeight = 16;
			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 4.0F, 4.0F, 0.55F, false);
			Body.texOffs(36, 8).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 4.0F, 4.0F, 0.6F, false);
			Body.texOffs(3, 2).addBox(-3.0F, 1.0F, -2.35F, 6.0F, 2.0F, 3.0F, 0.5F, false);
			Body.texOffs(0, 8).addBox(-3.0F, 6.0F, -2.0F, 6.0F, 4.0F, 4.0F, 0.55F, false);
			Body.texOffs(24, 2).addBox(-5.0F, 7.0F, -2.5F, 10.0F, 1.0F, 5.0F, -0.3F, false);
			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(20, 8).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.4F, true);
			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(20, 8).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.4F, false);
		}

		@Override
		public void setupAnim(Entity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

		}


		@Override
		public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
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


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
public class CursedlabcoatItem extends TcorpModElements.ModElement {
	@ObjectHolder("tcorp:cursed_labcoat_helmet")
	public static final Item helmet = null;
	@ObjectHolder("tcorp:cursed_labcoat_chestplate")
	public static final Item body = null;
	@ObjectHolder("tcorp:cursed_labcoat_leggings")
	public static final Item legs = null;
	@ObjectHolder("tcorp:cursed_labcoat_boots")
	public static final Item boots = null;

	public CursedlabcoatItem(TcorpModElements instance) {
		super(instance, 5);
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
				return new int[]{2, 5, 16, 2}[slot.getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 9;
			}

			@Override
			public SoundEvent getEquipSound() {
				return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather"));
			}

			@Override
			public Ingredient getRepairIngredient() {
				return null;
			}

			@OnlyIn(Dist.CLIENT)
			@Override
			public String getName() {
				return "cursed_labcoat";
			}

			@Override
			public float getToughness() {
				return 5f;
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
				armorModel.body = new Modelcursedcoat().Body;
				armorModel.leftArm = new Modelcursedcoat().LeftArm;
				armorModel.rightArm = new Modelcursedcoat().RightArm;
				armorModel.crouching = living.isCrouching();
				armorModel.riding = defaultModel.riding;
				armorModel.young = living.isBaby();
				return armorModel;
			}

			@Override
			public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
				super.appendHoverText(itemstack, world, list, flag);
				list.add(new StringTextComponent("[Ability] Phasewalker - 5 E"));
			}

			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
				return "tcorp:textures/entities/labcoat.png";
			}
		}.setRegistryName("cursed_labcoat_chestplate"));
	}

	// Made with Blockbench 4.6.5
	// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
	// Paste this class into your mod and generate all required imports
	public static class Modelcursedcoat extends EntityModel<Entity> {
		private final ModelRenderer Body;
		private final ModelRenderer cube_r1;
		private final ModelRenderer RightArm;
		private final ModelRenderer LeftArm;

		public Modelcursedcoat() {
			texWidth = 64;
			texHeight = 64;
			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(0, 32).addBox(-3.9F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, 0.45F, false);
			Body.texOffs(0, 32).addBox(-0.1F, 0.0F, -2.01F, 4.0F, 14.0F, 4.0F, 0.45F, true);
			Body.texOffs(16, 42).addBox(0.75F, 1.8F, -2.35F, 3.0F, 2.0F, 0.0F, -0.2F, false);
			cube_r1 = new ModelRenderer(this);
			cube_r1.setPos(0.0F, 24.0F, 0.0F);
			Body.addChild(cube_r1);
			setRotationAngle(cube_r1, 0.0436F, 0.0F, 0.0F);
			cube_r1.texOffs(16, 32).addBox(-3.7F, -23.5F, -2.45F, 2.0F, 4.0F, 2.0F, -0.78F, false);
			cube_r1.texOffs(16, 36).addBox(-4.3F, -23.7F, -2.45F, 2.0F, 4.0F, 2.0F, -0.76F, false);
			cube_r1.texOffs(16, 45).addBox(-4.0F, -21.7F, -1.75F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(24, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.4F, false);
			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(24, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.4F, true);
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

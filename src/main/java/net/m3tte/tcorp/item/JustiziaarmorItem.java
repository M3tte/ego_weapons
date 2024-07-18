
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@TcorpModElements.ModElement.Tag
public class JustiziaarmorItem extends TcorpModElements.ModElement {
	@ObjectHolder("tcorp:justiziaarmor_helmet")
	public static final Item helmet = null;
	@ObjectHolder("tcorp:justiziaarmor_chestplate")
	public static final Item body = null;
	@ObjectHolder("tcorp:justiziaarmor_leggings")
	public static final Item legs = null;
	@ObjectHolder("tcorp:justiziaarmor_boots")
	public static final Item boots = null;

	public JustiziaarmorItem(TcorpModElements instance) {
		super(instance, 148);
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
				return new int[]{2, 5, 27, 2}[slot.getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 9;
			}

			@Override
			public net.minecraft.util.SoundEvent getEquipSound() {
				return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.EMPTY;
			}


			@OnlyIn(Dist.CLIENT)
			@Override
			public String getName() {
				return "justiziaarmor";
			}

			@Override
			public float getToughness() {
				return 10f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0f;
			}
		};
		elements.items.add(() -> new ArmorItem(armormaterial, EquipmentSlotType.CHEST, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
			@Override
			@OnlyIn(Dist.CLIENT)
			public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
				BipedModel armorModel = new BipedModel(1);
				armorModel.body = new Modeljustizia_clothing().Body;
				armorModel.leftArm = new Modeljustizia_clothing().LeftArm;
				armorModel.rightArm = new Modeljustizia_clothing().RightArm;
				armorModel.crouching = living.isCrouching();
				armorModel.riding = defaultModel.riding;
				armorModel.young = living.isBaby();
				return armorModel;
			}

			@Override
			public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
				super.appendHoverText(itemstack, world, list, flag);
				list.add(new StringTextComponent("A coat of justice protecting the wearer."));
				list.add(new StringTextComponent("[Ability] Chains of Judgement - 6E"));
			}

			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
				return "tcorp:textures/entities/justizia_clothing.png";
			}
		}.setRegistryName("justiziaarmor_chestplate"));
	}

	// Made with Blockbench 4.6.5
	// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
	// Paste this class into your mod and generate all required imports
	public static class Modeljustizia_clothing extends EntityModel<Entity> {
		private final ModelRenderer Body;
		private final ModelRenderer cube_r1;
		private final ModelRenderer RightArm;
		private final ModelRenderer LeftArm;

		public Modeljustizia_clothing() {
			texWidth = 56;
			texHeight = 39;
			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.35F, false);
			Body.texOffs(24, 0).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 16.0F, 5.0F, 0.1F, false);
			cube_r1 = new ModelRenderer(this);
			cube_r1.setPos(0.0F, 0.0F, 0.0F);
			Body.addChild(cube_r1);
			setRotationAngle(cube_r1, -0.7418F, 0.0F, 0.0F);
			cube_r1.texOffs(0, 17).addBox(-5.0F, -2.25F, 1.5F, 10.0F, 2.0F, 2.0F, 0.0F, false);
			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(0, 21).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.4F, false);
			RightArm.texOffs(8, 21).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.6F, false);
			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(0, 21).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.4F, false);
			LeftArm.texOffs(8, 21).addBox(-0.9F, -2.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.6F, false);
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

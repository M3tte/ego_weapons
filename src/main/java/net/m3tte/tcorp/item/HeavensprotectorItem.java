
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
public class HeavensprotectorItem extends TcorpModElements.ModElement {
	@ObjectHolder("tcorp:heavensprotector_helmet")
	public static final Item helmet = null;
	@ObjectHolder("tcorp:heavensprotector_chestplate")
	public static final Item body = null;
	@ObjectHolder("tcorp:heavensprotector_leggings")
	public static final Item legs = null;
	@ObjectHolder("tcorp:heavensprotector_boots")
	public static final Item boots = null;

	public HeavensprotectorItem(TcorpModElements instance) {
		super(instance, 21);
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
				return new int[]{2, 5, 20, 2}[slot.getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 9;
			}

			@Override
			public SoundEvent getEquipSound() {
				return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
			}

			@Override
			public Ingredient getRepairIngredient() {
				return null;
			}

			@OnlyIn(Dist.CLIENT)
			@Override
			public String getName() {
				return "heavensprotector";
			}

			@Override
			public float getToughness() {
				return 8f;
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
				armorModel.body = new Modelarmor_heaven().Body;
				armorModel.leftArm = new Modelarmor_heaven().LeftArm;
				armorModel.rightArm = new Modelarmor_heaven().RightArm;
				armorModel.crouching = living.isCrouching();
				armorModel.riding = defaultModel.riding;
				armorModel.young = living.isBaby();
				return armorModel;
			}

			@Override
			public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
				super.appendHoverText(itemstack, world, list, flag);
				list.add(new StringTextComponent("\"The masses' strikes couldn't make a dent in the beast\""));
				list.add(new StringTextComponent("Uproots deal damage when spawning."));
				list.add(new StringTextComponent("[Ability] Overgrowth - 8 E"));
			}

			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
				return "tcorp:textures/entities/heavens_protector.png";
			}
		}.setRegistryName("heavensprotector_chestplate"));
	}

	// Made with Blockbench 4.6.5
	// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
	// Paste this class into your mod and generate all required imports
	public static class Modelarmor_heaven extends EntityModel<Entity> {
		private final ModelRenderer Body;
		private final ModelRenderer Eye_r1;
		private final ModelRenderer Eye_r2;
		private final ModelRenderer Eye_r3;
		private final ModelRenderer RightArm;
		private final ModelRenderer Eye_r4;
		private final ModelRenderer LeftArm;
		private final ModelRenderer Eye_r5;

		public Modelarmor_heaven() {
			texWidth = 64;
			texHeight = 32;
			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(40, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.35F, false);
			Body.texOffs(0, 0).addBox(-3.0F, 0.85F, 2.4F, 6.0F, 4.0F, 0.0F, 0.35F, false);
			Body.texOffs(0, 0).addBox(-3.0F, 0.75F, -2.5F, 6.0F, 4.0F, 0.0F, 0.35F, false);
			Body.texOffs(0, 4).addBox(-2.0F, 5.45F, -2.5F, 4.0F, 4.0F, 0.0F, 0.35F, false);
			Body.texOffs(4, 4).addBox(-3.0F, 9.1F, -2.3F, 6.0F, 2.0F, 0.0F, 0.35F, false);
			Body.texOffs(4, 12).addBox(-3.0F, 9.1F, 2.4F, 6.0F, 2.0F, 0.0F, 0.35F, false);
			Eye_r1 = new ModelRenderer(this);
			Eye_r1.setPos(0.0F, 5.0F, 0.0F);
			Body.addChild(Eye_r1);
			setRotationAngle(Eye_r1, 0.0F, 0.0F, -0.48F);
			Eye_r1.texOffs(0, 8).addBox(-2.2007F, 1.3113F, -3.55F, 3.0F, 3.0F, 1.0F, -0.3F, false);
			Eye_r2 = new ModelRenderer(this);
			Eye_r2.setPos(0.0F, 5.0F, 0.0F);
			Body.addChild(Eye_r2);
			setRotationAngle(Eye_r2, 0.0F, 3.1416F, 0.3491F);
			Eye_r2.texOffs(0, 8).addBox(-2.2007F, -3.6887F, -3.3F, 3.0F, 3.0F, 1.0F, -0.3F, false);
			Eye_r3 = new ModelRenderer(this);
			Eye_r3.setPos(0.0F, 5.0F, 0.0F);
			Body.addChild(Eye_r3);
			setRotationAngle(Eye_r3, 0.0F, 0.0F, 0.3491F);
			Eye_r3.texOffs(0, 8).addBox(-3.7007F, -3.6887F, -3.3F, 3.0F, 3.0F, 1.0F, -0.3F, false);
			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(16, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.4F, false);
			RightArm.texOffs(28, -3).addBox(-3.1F, 4.0F, -1.5F, 0.0F, 4.0F, 3.0F, 0.35F, false);
			RightArm.texOffs(34, -3).addBox(-3.3F, 1.0F, -1.5F, 0.0F, 10.0F, 3.0F, 0.0F, false);
			Eye_r4 = new ModelRenderer(this);
			Eye_r4.setPos(5.0F, 3.0F, 0.0F);
			RightArm.addChild(Eye_r4);
			setRotationAngle(Eye_r4, -1.5708F, 1.3526F, -1.5708F);
			Eye_r4.texOffs(0, 8).addBox(-0.9507F, -4.4387F, -9.05F, 3.0F, 3.0F, 1.0F, -0.3F, false);
			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(48, 14).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.35F, false);
			LeftArm.texOffs(16, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.5F, true);
			LeftArm.texOffs(16, 8).addBox(1.0F, 2.5F, -2.0F, 2.0F, 2.0F, 4.0F, 0.4F, true);
			Eye_r5 = new ModelRenderer(this);
			Eye_r5.setPos(-5.0F, 3.0F, 0.0F);
			LeftArm.addChild(Eye_r5);
			setRotationAngle(Eye_r5, 1.618F, -0.3923F, 3.1235F);
			Eye_r5.texOffs(0, 8).addBox(-7.1507F, 0.7613F, -5.95F, 3.0F, 3.0F, 1.0F, -0.3F, false);
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

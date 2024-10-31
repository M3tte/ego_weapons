
package net.m3tte.ego_weapons.item;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.ResourceLocation;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ArmorItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.BipedModel;

import net.m3tte.ego_weapons.EgoWeaponsModElements;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@EgoWeaponsModElements.ModElement.Tag
public class LabcoatItem extends EgoWeaponsModElements.ModElement {
	@ObjectHolder("tcorp:labcoat_helmet")
	public static final Item helmet = null;
	@ObjectHolder("tcorp:labcoat_chestplate")
	public static final Item body = null;
	@ObjectHolder("tcorp:labcoat_leggings")
	public static final Item legs = null;
	@ObjectHolder("tcorp:labcoat_boots")
	public static final Item boots = null;

	public LabcoatItem(EgoWeaponsModElements instance) {
		super(instance, 6);
	}

	@Override
	public void initElements() {
		IArmorMaterial armormaterial = new IArmorMaterial() {
			@Override
			public int getDurabilityForSlot(EquipmentSlotType slot) {
				return new int[]{13, 15, 16, 11}[slot.getIndex()] * 44;
			}

			@Override
			public int getDefenseForSlot(EquipmentSlotType slot) {
				return new int[]{2, 5, 5, 2}[slot.getIndex()];
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
				return Ingredient.EMPTY;
			}


			@OnlyIn(Dist.CLIENT)
			@Override
			public String getName() {
				return "labcoat";
			}

			@Override
			public float getToughness() {
				return 0.2f;
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
				armorModel.body = new Modellabcoatstandard().Body;
				armorModel.leftArm = new Modellabcoatstandard().LeftArm;
				armorModel.rightArm = new Modellabcoatstandard().RightArm;
				armorModel.crouching = living.isCrouching();
				armorModel.riding = defaultModel.riding;
				armorModel.young = living.isBaby();
				return armorModel;
			}

			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
				return "tcorp:textures/entities/labcoat.png";
			}
		}.setRegistryName("labcoat_chestplate"));
	}

	// Made with Blockbench 4.6.5
	// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
	// Paste this class into your mod and generate all required imports
	public static class Modellabcoatstandard extends EntityModel<Entity> {
		private final ModelRenderer Body;
		private final ModelRenderer cube_r1;
		private final ModelRenderer RightArm;
		private final ModelRenderer LeftArm;

		public Modellabcoatstandard() {
			texWidth = 64;
			texHeight = 64;
			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(0, 14).addBox(-3.9F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, 0.44F, false);
			Body.texOffs(0, 14).addBox(-0.1F, 0.0F, -2.01F, 4.0F, 14.0F, 4.0F, 0.46F, true);
			Body.texOffs(16, 24).addBox(0.75F, 1.8F, -2.35F, 3.0F, 2.0F, 0.0F, -0.2F, false);
			cube_r1 = new ModelRenderer(this);
			cube_r1.setPos(0.0F, 24.0F, 0.0F);
			Body.addChild(cube_r1);
			setRotationAngle(cube_r1, 0.0436F, 0.0F, 0.0F);
			cube_r1.texOffs(16, 32).addBox(-3.7F, -23.5F, -2.45F, 2.0F, 4.0F, 2.0F, -0.8F, false);
			cube_r1.texOffs(16, 36).addBox(-4.3F, -23.7F, -2.45F, 2.0F, 4.0F, 2.0F, -0.8F, false);
			cube_r1.texOffs(12, 26).addBox(-4.0F, -21.7F, -1.75F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(24, 14).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.45F, false);
			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(24, 14).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.45F, true);
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

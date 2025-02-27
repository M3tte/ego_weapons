
package net.m3tte.ego_weapons.item.magic_bullet;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.item.NoArmorToughnessMaterial;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class MagicBulletArmor extends ArmorItem {

	static IArmorMaterial magicBulletArmor = new IArmorMaterial() {
		@Override
		public int getDurabilityForSlot(EquipmentSlotType slot) {
			return new int[]{13, 15, 16, 11}[slot.getIndex()] * 999;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType slot) {
			return new int[]{0, 0, 20, 0}[slot.getIndex()];
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
			return "magic_bullet";
		}

		@Override
		public float getToughness() {
			return 9f;
		}

		@Override
		public float getKnockbackResistance() {
			return 0.1f;
		}
	};

	public MagicBulletArmor(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}

	// Texture Override
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "ego_weapons:textures/entities/magic_bullet.png";
	}

	public static Item getArmorForSlot(EquipmentSlotType slot) {
		switch (slot) {
			default: return null;
			case CHEST: return chest;
			case HEAD: return pipe;
		}
	}
	static Item chest = new MagicBulletArmor(magicBulletArmor, EquipmentSlotType.CHEST, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.head = new MagicBulletModel().Head;
			armorModel.hat = new MagicBulletModel().Head;
			armorModel.body = new MagicBulletModel().Body;
			armorModel.leftArm = new MagicBulletModel().LeftArm;
			armorModel.rightArm = new MagicBulletModel().RightArm;
			armorModel.crouching = living.isCrouching();
			armorModel.riding = defaultModel.riding;
			armorModel.young = living.isBaby();
			return armorModel;
		}

		@Override
		public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(new StringTextComponent("...The contract won't end here...").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
			list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

			list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 2) + 1) + "/2] - - - - - - - =").withStyle(TextFormatting.GRAY));

			switch (EgoWeaponsKeybinds.getUiPage() % 2) {
				case 0:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"dark_flame", "poise"});
					else
						generateDescription(list, "magic_bullet_armor", "passive", 4);
					break;
				case 1:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"magic_bullet", "poise", "power_up"});
					else
						generateDescription(list, "magic_bullet_armor", "ability", 3);
					break;
			}

			list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
		}

	};
	static Item pipe = new MagicBulletArmor(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.HEAD, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			MagicBulletModel m = new MagicBulletModel();
			armorModel.head = m.Head;
			armorModel.hat = m.Head;
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
	private static class MagicBulletModel extends EntityModel<Entity> {
		private final ModelRenderer Body;
		private final ModelRenderer RightArm;
		private final ModelRenderer LeftArm;
		private final ModelRenderer Head;
		private final ModelRenderer Pipe;
		private final ModelRenderer cube_r1;
		private final ModelRenderer cube_r2;

		public MagicBulletModel() {
			texWidth = 64;
			texHeight = 64;

			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 0.0F, 0.15F, false);
			Body.texOffs(8, 0).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 14.0F, 5.0F, -0.3F, false);
			Body.texOffs(37, 1).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 15.0F, 4.0F, 0.5F, false);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
			Body.texOffs(0, 18).addBox(-4.5F, 7.75F, -2.5F, 9.0F, 5.0F, 5.0F, -0.2F, false);
			Body.texOffs(32, 20).addBox(-4.0F, -8.425F, -4.0F, 8.0F, 8.0F, 8.0F, 0.3F, false);

			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(0, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
			RightArm.texOffs(16, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);

			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(0, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, true);
			LeftArm.texOffs(16, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, true);

			Head = new ModelRenderer(this);
			Head.setPos(1.0F, 0.0F, 0.0F);
			setRotationAngle(Head, -0.1047F, 0.0873F, 0.0F);


			Pipe = new ModelRenderer(this);
			Pipe.setPos(-1.0F, 0.0F, -0.175F);
			Head.addChild(Pipe);
			setRotationAngle(Pipe, 0.0F, -0.2182F, 0.0F);


			cube_r1 = new ModelRenderer(this);
			cube_r1.setPos(0.0F, 0.0F, 0.0F);
			Pipe.addChild(cube_r1);
			setRotationAngle(cube_r1, 0.2618F, -0.2182F, 0.0F);
			cube_r1.texOffs(0, 56).addBox(-1.0F, -3.0F, -3.75F, 1.0F, 1.0F, 1.0F, -0.03F, false);
			cube_r1.texOffs(7, 59).addBox(-1.0F, -2.5F, -7.075F, 1.0F, 1.0F, 1.0F, 0.03F, false);
			cube_r1.texOffs(2, 56).addBox(-1.0F, -3.0F, -4.75F, 1.0F, 1.0F, 2.0F, -0.1F, false);
			cube_r1.texOffs(8, 61).addBox(-1.475F, -3.475F, -8.1F, 2.0F, 1.0F, 2.0F, -0.1F, false);
			cube_r1.texOffs(0, 61).addBox(-1.475F, -3.7F, -8.1F, 2.0F, 1.0F, 2.0F, -0.3F, false);
			cube_r1.texOffs(11, 57).addBox(-1.475F, -3.25F, -8.1F, 2.0F, 2.0F, 2.0F, -0.2F, false);

			cube_r2 = new ModelRenderer(this);
			cube_r2.setPos(0.0F, 0.0F, 0.0F);
			Pipe.addChild(cube_r2);
			setRotationAngle(cube_r2, 0.5672F, -0.2182F, 0.0F);
			cube_r2.texOffs(3, 56).addBox(-1.0F, -4.25F, -5.525F, 1.0F, 1.0F, 2.0F, -0.03F, false);
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
			Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.xRot = x;
			modelRenderer.yRot = y;
			modelRenderer.zRot = z;
		}
	}

	public static void poiseEffect(LivingEntity target, LivingEntity source, float amount, DamageSource damageSource) {
		EgoWeaponsModVars.PlayerVariables entityData = source.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);



		// Applies cooldown after. Locks all other on hit effects from triggering as well.
		if (entityData != null) {
			if (entityData.globalcooldown <= 0) {

				if (SharedFunctions.hasDefenseDown(target))
					EgoWeaponsEffects.POISE.get().increment(source, 0, target.hasEffect(EgoWeaponsEffects.DARK_BURN.get()) ? 2 : 1);

				entityData.globalcooldown = 10;
				entityData.syncPlayerVariables(source);
			}
		}
	}

}

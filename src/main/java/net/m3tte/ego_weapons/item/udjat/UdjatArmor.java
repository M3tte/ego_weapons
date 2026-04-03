
package net.m3tte.ego_weapons.item.udjat;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.movesets.UdjatKhopeshMovesetAnims;
import net.m3tte.ego_weapons.item.NoArmorToughnessMaterial;
import net.m3tte.ego_weapons.item.oeufi.OeufiArmor;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.potion.OrlandoPotionEffect;
import net.m3tte.ego_weapons.procedures.EntityTick;
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
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class UdjatArmor extends GenericEgoWeaponsArmor {

	static IArmorMaterial udjatArmor = new IArmorMaterial() {
		@Override
		public int getDurabilityForSlot(EquipmentSlotType p_200896_1_) {
			return Integer.MAX_VALUE;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType slot)  {
			return new int[]{0, 0, 20, 0}[slot.getIndex()];
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
			return "udjat";
		}

		@Override
		public float getToughness() {
			return 8f;
		}

		@Override
		public float getKnockbackResistance() {
			return 2f;
		}
	};

	public UdjatArmor(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}


	// Texture Override
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "ego_weapons:textures/entities/udjat_armor.png";
	}

	public static boolean evaluateAntiStagger(LivingEntity entity, EgoWeaponsModVars.PlayerVariables entityData, Entity source) {

		int protPotency = EgoWeaponsEffects.PROTECTION.get().getPotency(entity);



		if (protPotency >= 5) {

            entityData.stagger = EgoWeaponsAttributes.getMaxStagger(entity) * 0.33f;


			entity.removeEffect(EgoWeaponsEffects.PROTECTION.get());

			LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

			if (entitypatch != null && !entity.level.isClientSide()) {
				entitypatch.playAnimationSynchronized(UdjatKhopeshMovesetAnims.KHOPESH_ARMOR_MIRAGE, 0.05f);
			}

			if (source instanceof LivingEntity) {
				EgoWeaponsEffects.SPEED_DOWN.get().increment((LivingEntity) source, 0, 2);
			}

			return true;
		}
		return false;
	}

	public static boolean evaluateAntiDeath(LivingEntity entity, Entity source) {

		int protPotency = EgoWeaponsEffects.PROTECTION.get().getPotency(entity);



		if (protPotency >= 5) {


			entity.playSound(EgoWeaponsSounds.UDJAT_MIRAGE, 1, 1);

			entity.removeEffect(EgoWeaponsEffects.PROTECTION.get());
			LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

			if (entitypatch != null) {
				entitypatch.playAnimationSynchronized(UdjatKhopeshMovesetAnims.KHOPESH_ARMOR_MIRAGE, 0);
			}

			if (entity instanceof PlayerEntity) {
				int light = (int) EntityTick.getLight((PlayerEntity) entity);
				World world = entity.level;
				entity.setAbsorptionAmount(entity.getAbsorptionAmount() + light);
				if (world instanceof ServerWorld) {
					((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), entity.getX(), (entity.getY() + 1), entity.getZ(), light, 0, 0.3, 0, 0.05);
				}
				EntityTick.consumeLight((PlayerEntity) entity, light);

			}


			if (source instanceof LivingEntity) {
				EgoWeaponsEffects.SPEED_DOWN.get().increment((LivingEntity) source, 0, 4);
			}

			return true;
		}
		return false;
	}

	public static Item getArmorForSlot(EquipmentSlotType slot) {
		switch (slot) {
			default: return null;
			case CHEST: return chest;
			case LEGS: return pants;
			case HEAD: return mask;
		}
	}

	static Item pants = new UdjatArmor(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.LEGS, new Properties().tab(ItemGroup.TAB_SEARCH)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			UdjatArmorModel m = new UdjatArmorModel();
			armorModel.leftLeg = m.LeftLegLayer;
			armorModel.rightLeg = m.RightLegLayer;
			armorModel.body = m.Dummy;
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

	public UdjatArmor(IArmorMaterial armorMaterial, EquipmentSlotType slot, Properties props, float redResistance, float whiteResistance, float blackResistance, float paleResistance, float slashResistance, float pierceResistance, float bluntResistance, float bonusStagger, float bonusSanity) {
		super(armorMaterial, slot, props, redResistance, whiteResistance, blackResistance, paleResistance,slashResistance, pierceResistance, bluntResistance, bonusStagger, bonusSanity);
	}
	static Item chest = new UdjatArmor(udjatArmor, EquipmentSlotType.CHEST, new Properties().tab(ItemGroup.TAB_SEARCH), 0.7f, 1f, 0.5f ,1.2f, 0.8f, 1.3f, 0.8f, 5, -5) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);

			UdjatArmorModel udjatArmorModel = new UdjatArmorModel();

			armorModel.head = udjatArmorModel.Head;
			armorModel.body = udjatArmorModel.Body;
			armorModel.leftArm = udjatArmorModel.LeftArm;
			armorModel.rightArm = udjatArmorModel.RightArm;
			armorModel.crouching = living.isCrouching();
			armorModel.riding = defaultModel.riding;
			armorModel.young = living.isBaby();
			return armorModel;
		}

		@Override
		public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(new TranslationTextComponent("desc.ego_weapons.udjat_suit.desc"));
			list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

			list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 6) + 1) + "/6] - - - - - - - =").withStyle(TextFormatting.GRAY));
			list.add(new TranslationTextComponent("desc.ego_weapons.risk.3"));
			list.add(new StringTextComponent(" "));
			switch (EgoWeaponsKeybinds.getUiPage() % 6) {
				case 0:
					resistanceMods(itemstack, world, list, flag);
					break;
				case 1:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"offense_up","blue_sand"});
					else
						generateDescription(list, "udjat_suit", "passive", 4);
					break;
				case 2:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"protection"});
					else
						generateDescription(list, "udjat_suit", "passive2", 1);
					break;
				case 3:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"protection","speed_down"});
					else
						generateDescription(list, "udjat_suit", "passive3", 8, true);
					break;
				case 4:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"protection","offense_up","power_up"});
					else
						generateDescription(list, "udjat_suit", "ability", 5);
					break;
			}

			list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
		}
	};
	static Item mask = new UdjatArmor(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.HEAD, new Properties().tab(ItemGroup.TAB_SEARCH)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.head = new UdjatArmorModel().Head;
			armorModel.hat = new UdjatArmorModel().Dummy;
			//armorModel.body	 = new Modelperceptionmask().Head;
			armorModel.crouching = living.isCrouching();
			armorModel.riding = defaultModel.riding;
			armorModel.young = living.isBaby();
			return armorModel;
		}


		@Override
		public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(new StringTextComponent("A dark mask protecting the wearers identity."));
		}

	};

	// Made with Blockbench 4.6.5
	// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
	// Paste this class into your mod and generate all required imports
	public static class UdjatArmorModel extends EntityModel<Entity> {

		private final ModelRenderer Dummy;
		private final ModelRenderer Body;
		private final ModelRenderer RightArm;
		private final ModelRenderer LeftArmLayer_r1;
		private final ModelRenderer LeftArm;
		private final ModelRenderer LeftArmLayer_r2;
		private final ModelRenderer Head;
		private final ModelRenderer RightLegLayer;
		private final ModelRenderer LeftLegLayer;

		public UdjatArmorModel() {
			texWidth = 80;
			texHeight = 80;

			Dummy = new ModelRenderer(this);

			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 0.0F, 0.15F, false);
			Body.texOffs(8, 0).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 13.0F, 5.0F, -0.3F, false);
			Body.texOffs(37, 1).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 14.0F, 4.0F, 0.5F, false);
			Body.texOffs(37, 19).addBox(-4.0F, 14.25F, -2.0F, 8.0F, 5.0F, 4.0F, 0.8F, false);
			Body.texOffs(32, 32).addBox(-5.0F, 18.25F, -3.0F, 10.0F, 2.0F, 6.0F, 0.0F, false);
			Body.texOffs(33, 41).addBox(-5.0F, 14.25F, -1.0F, 10.0F, 4.0F, 3.0F, 0.0F, false);
			Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(0, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
			RightArm.texOffs(16, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
			RightArm.texOffs(13, 21).addBox(-3.5F, 6.5F, -2.5F, 5.0F, 1.0F, 5.0F, -0.1F, true);

			LeftArmLayer_r1 = new ModelRenderer(this);
			LeftArmLayer_r1.setPos(1.325F, 19.0F, 0.5F);
			RightArm.addChild(LeftArmLayer_r1);
			setRotationAngle(LeftArmLayer_r1, 0.0F, 0.0F, 0.1745F);
			LeftArmLayer_r1.texOffs(13, 21).addBox(-8.0F, -18.0F, -3.0F, 5.0F, 1.0F, 5.0F, 0.0F, true);

			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(0, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, true);
			LeftArm.texOffs(16, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, true);
			LeftArm.texOffs(13, 21).addBox(-1.5F, 6.5F, -2.5F, 5.0F, 1.0F, 5.0F, -0.1F, true);
			LeftArm.texOffs(13, 21).addBox(-1.5F, 6.5F, -2.5F, 5.0F, 1.0F, 5.0F, -0.1F, true);

			LeftArmLayer_r2 = new ModelRenderer(this);
			LeftArmLayer_r2.setPos(9.5F, 16.0F, 0.5F);
			LeftArm.addChild(LeftArmLayer_r2);
			setRotationAngle(LeftArmLayer_r2, 0.0F, 0.0F, -0.1745F);
			LeftArmLayer_r2.texOffs(13, 21).addBox(-8.0F, -18.0F, -3.0F, 5.0F, 1.0F, 5.0F, 0.0F, true);

			Head = new ModelRenderer(this);
			Head.setPos(0.0F, 0.0F, 0.0F);
			setRotationAngle(Head, -0.1047F, 0.0873F, 0.0F);
			Head.texOffs(6, 70).addBox(-4.0F, -8.0F, -4.2F, 8.0F, 8.0F, 2.0F, 0.1F, false);
			Head.texOffs(40, 63).addBox(-5.0F, -9.25F, -5.625F, 10.0F, 10.0F, 7.0F, -1.3F, false);

			RightLegLayer = new ModelRenderer(this);
			RightLegLayer.setPos(-1.5F, 12.0F, 0.0F);
			RightLegLayer.texOffs(16, 48).addBox(-2.4F, 0, -2.0F, 4.0F, 12.0F, 4.0F, 0.3F, false);
			RightLegLayer.texOffs(13, 21).addBox(-2.9F, 9.5f, -2.5F, 5.0F, 1.0F, 5.0F, -0.1F, true);

			LeftLegLayer = new ModelRenderer(this);
			LeftLegLayer.setPos(1.5F, 12.0F, 0.0F);
			LeftLegLayer.texOffs(16, 48).addBox(-1.5F, 0, -2.0F, 4.0F, 12.0F, 4.0F, 0.3F, false);
			LeftLegLayer.texOffs(13, 21).addBox(-2F, 9.5f, -2.5F, 5.0F, 1.0F, 5.0F, -0.1F, true);
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
			RightLegLayer.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			LeftLegLayer.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.xRot = x;
			modelRenderer.yRot = y;
			modelRenderer.zRot = z;
		}
	}

}

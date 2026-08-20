
package net.m3tte.ego_weapons.item.udjat;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.movesets.LCARifleMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.UdjatKhopeshMovesetAnims;
import net.m3tte.ego_weapons.item.NoArmorToughnessMaterial;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.procedures.EntityTick;
import net.m3tte.ego_weapons.procedures.TooltipFuncs;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoWeaponsArmor;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
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

import static net.m3tte.ego_weapons.EgoWeaponsCreativeTabs.EGO_WEAPONS;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.*;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusHelp;

public class LCAUdjatArmor extends GenericEgoWeaponsArmor {

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
			return "lca_udjat";
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

	public LCAUdjatArmor(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}


	// Texture Override
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "ego_weapons:textures/entities/lca_udjat_armor.png";
	}



	public static Item getArmorForSlot(EquipmentSlotType slot) {
		switch (slot) {
			default: return null;
			case CHEST: return chest;
			case LEGS: return pants;
			case HEAD: return mask;
		}
	}

	static Item pants = new LCAUdjatArmor(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.LEGS, new Properties().tab(EGO_WEAPONS)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			LCAUdjatArmorModel m = new LCAUdjatArmorModel();
			armorModel.leftLeg = m.LeftLeg;
			armorModel.rightLeg = m.RightLeg;
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

	public static boolean evaluateUdjatBrace(LivingEntity entity, Entity source) {

		int protPotency = EgoWeaponsEffects.PROTECTION.get().getPotency(entity);

		if (!entity.hasEffect(EgoWeaponsEffects.SHIELDING_ALLOY_REGENERATIVE_CYCLE.get())) {
			entity.playSound(EgoWeaponsSounds.UDJAT_MIRAGE, 1, 1);

			entity.removeEffect(EgoWeaponsEffects.PROTECTION.get());
			LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

			DialogueSystem.speakEvalDialogue(entity, "dialogue.ego_weapons.skills.udjat_armor.severe_damage", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);

			if (!entity.level.isClientSide()) {
				entity.level.playSound(null, entity.blockPosition(),
						EgoWeaponsSounds.UDJAT_DIALOGUE_SEVERE_DAMAGE,
						SoundCategory.PLAYERS, (float) 2, (float) 1);

			}


			if (entitypatch != null) {
				entitypatch.playAnimationSynchronized(LCARifleMovesetAnims.LCA_UDJAT_BRACE, 0);
			}

			entity.addEffect(new EffectInstance(EgoWeaponsEffects.SHIELDING_ALLOY_REGENERATIVE_CYCLE.get(), 20 * (15 - protPotency), 0));

			return true;
		}
		return false;
	}

	public LCAUdjatArmor(IArmorMaterial armorMaterial, EquipmentSlotType slot, Properties props, float redResistance, float whiteResistance, float blackResistance, float paleResistance, float slashResistance, float pierceResistance, float bluntResistance, float bonusStagger, float bonusSanity) {
		super(armorMaterial, slot, props, redResistance, whiteResistance, blackResistance, paleResistance,slashResistance, pierceResistance, bluntResistance, bonusStagger, bonusSanity);
	}
	static Item chest = new LCAUdjatArmor(udjatArmor, EquipmentSlotType.CHEST, new Properties().tab(EGO_WEAPONS), 0.8f, 1f, 0.8f ,1.5f, 0.7f, 1f, 1.3f, 5, 5) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);

			LCAUdjatArmorModel udjatArmorModel = new LCAUdjatArmorModel();

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
			TooltipFuncs.generateItemDescription(list, "desc.ego_weapons.lca_udjat_suit.desc");
			list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

			list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 6) + 1) + "/6] - - - - - - - =").withStyle(TextFormatting.GRAY));
			list.add(new TranslationTextComponent("desc.ego_weapons.risk.2"));
			list.add(new StringTextComponent(" "));
			switch (EgoWeaponsKeybinds.getUiPage() % 6) {
				case 0:
					resistanceMods(itemstack, world, list, flag);
					break;
				case 1:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"udjat_vanguard"});
					else
						generateDescription(list, "lca_udjat_armor", "passive", 3);
					break;
				case 2:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"udjat_vanguard"});
					else
						generateDescription(list, "lca_udjat_armor", "passive1", 3);
					break;
				case 3:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"protection"});
					else
						generateDescription(list, "lca_udjat_armor", "passive2", 6);
					break;
				case 4:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"udjat_vanguard","offense_up"});
					else
						generateDescription(list, "lca_udjat_armor", "passive3", 3);
					break;
				case 5:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"udjat_vanguard","target_mark_udjat","speed_down"});
					else
						generateDescription(list, "lca_udjat_armor", "ability", 3);
					break;
			}

			generateStatusHelp(list);
		}
	};
	static Item mask = new LCAUdjatArmor(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.HEAD, new Properties().tab(EGO_WEAPONS)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.head = new LCAUdjatArmorModel().Head;
			armorModel.hat = new LCAUdjatArmorModel().Dummy;
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
	public class LCAUdjatArmorModel extends EntityModel<Entity> {
		private final ModelRenderer Dummy;
		private final ModelRenderer Head;
		private final ModelRenderer Body;
		private final ModelRenderer RightArm;
		private final ModelRenderer LeftArm;
		private final ModelRenderer RightLeg;
		private final ModelRenderer RightLegLayer_r1;
		private final ModelRenderer Right_Leg_Layer_r2;
		private final ModelRenderer LeftLeg;
		private final ModelRenderer LeftLegLayer1;
		private final ModelRenderer LeftLegLayer2;

		public LCAUdjatArmorModel() {
			texWidth = 128;
			texHeight = 64;

			Dummy = new ModelRenderer(this);


			Head = new ModelRenderer(this);
			Head.setPos(0.0F, 0.0F, 0.0F);
			setRotationAngle(Head, -0.1047F, 0.0873F, 0.0F);
			Head.texOffs(0, 0).addBox(-4.0F, -8.575F, -4.5F, 8.0F, 9.0F, 1.0F, 0.0F, false);
			Head.texOffs(1, 10).addBox(-3.5F, -7.25F, -4.75F, 7.0F, 7.0F, 1.0F, 0.1F, false);
			Head.texOffs(83, 43).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.25F, false);

			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(18, 0).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 14.0F, 5.0F, -0.1F, false);
			Body.texOffs(18, 19).addBox(-4.5F, 13.25F, -2.5F, 9.0F, 6.0F, 5.0F, 0.4F, false);
			Body.texOffs(17, 32).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 11.0F, 5.0F, 0.25F, false);
			Body.texOffs(13, 48).addBox(-4.5F, -0.5F, -2.95F, 9.0F, 11.0F, 0.0F, 0.0F, false);

			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(46, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
			RightArm.texOffs(46, 16).addBox(-3.0F, -1.25F, -2.0F, 4.0F, 11.0F, 4.0F, 0.25F, false);
			RightArm.texOffs(62, 0).addBox(-3.5F, 2.25F, -2.5F, 1.0F, 5.0F, 5.0F, -0.05F, false);
			RightArm.texOffs(62, 14).addBox(-3.5F, -2.2F, -2.5F, 5.0F, 4.0F, 5.0F, -0.15F, false);
			RightArm.texOffs(62, 23).addBox(-3.75F, -2.5F, -2.5F, 5.0F, 3.0F, 5.0F, -0.05F, false);

			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(46, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, true);
			LeftArm.texOffs(86, 16).addBox(-1.0F, -1.25F, -2.0F, 4.0F, 11.0F, 4.0F, 0.25F, true);
			LeftArm.texOffs(102, 0).addBox(2.5F, 2.25F, -2.5F, 1.0F, 5.0F, 5.0F, -0.05F, true);
			LeftArm.texOffs(102, 23).addBox(-1.5F, -2.5F, -2.5F, 5.0F, 3.0F, 5.0F, -0.05F, true);
			LeftArm.texOffs(102, 14).addBox(-1.5F, -2.2F, -2.5F, 5.0F, 4.0F, 5.0F, -0.15F, true);

			RightLeg = new ModelRenderer(this);
			RightLeg.setPos(-1.9F, 12.0F, 0.0F);
			setRotationAngle(RightLeg, 0.192F, 0.0F, 0.0349F);
			RightLeg.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.15F, false);
			RightLeg.texOffs(36, 56).addBox(-2.5F, 9.5F, -2.5F, 5.0F, 3.0F, 5.0F, -0.1F, false);

			RightLegLayer_r1 = new ModelRenderer(this);
			RightLegLayer_r1.setPos(1.275F, 8.975F, 0.5F);
			RightLeg.addChild(RightLegLayer_r1);
			setRotationAngle(RightLegLayer_r1, 0.0F, 0.0F, 0.048F);
			RightLegLayer_r1.texOffs(36, 48).addBox(-3.9F, -1.0F, -3.0F, 5.0F, 1.0F, 5.0F, 0.1F, false);

			Right_Leg_Layer_r2 = new ModelRenderer(this);
			Right_Leg_Layer_r2.setPos(1.325F, 9.3F, 0.5F);
			RightLeg.addChild(Right_Leg_Layer_r2);
			setRotationAngle(Right_Leg_Layer_r2, 0.0F, 0.0F, -0.0305F);
			Right_Leg_Layer_r2.texOffs(36, 48).addBox(-3.9F, -3.0F, -3.0F, 5.0F, 3.0F, 5.0F, -0.15F, false);

			LeftLeg = new ModelRenderer(this);
			LeftLeg.setPos(1.9F, 12.0F, 0.0F);
			setRotationAngle(LeftLeg, -0.1745F, 0.0F, -0.0349F);
			LeftLeg.texOffs(0, 32).addBox(-1.8F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.15F, true);
			LeftLeg.texOffs(36, 56).addBox(-2.3F, 9.5F, -2.5F, 5.0F, 3.0F, 5.0F, -0.1F, true);

			LeftLegLayer1 = new ModelRenderer(this);
			LeftLegLayer1.setPos(1.475F, 8.975F, 0.5F);
			LeftLeg.addChild(LeftLegLayer1);
			setRotationAngle(LeftLegLayer1, 0.0F, 0.0F, -0.0393F);
			LeftLegLayer1.texOffs(36, 48).addBox(-3.9F, -1.0F, -3.0F, 5.0F, 1.0F, 5.0F, 0.1F, true);

			LeftLegLayer2 = new ModelRenderer(this);
			LeftLegLayer2.setPos(1.525F, 9.3F, 0.5F);
			LeftLeg.addChild(LeftLegLayer2);
			setRotationAngle(LeftLegLayer2, 0.0F, 0.0F, 0.0567F);
			LeftLegLayer2.texOffs(36, 48).addBox(-3.9F, -3.0F, -3.0F, 5.0F, 3.0F, 5.0F, -0.15F, true);
		}

		@Override
		public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
			//previously the render function, render code was moved to a method below
		}

		@Override
		public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
			Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
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


package net.m3tte.ego_weapons.item.justitia;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.item.NoArmorToughnessMaterial;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoWeaponsArmor;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class JustitiaCloak extends GenericEgoWeaponsArmor {

	static IArmorMaterial justitiaArmor = new IArmorMaterial() {
		@Override
		public int getDurabilityForSlot(EquipmentSlotType slot) {
			return new int[]{13, 15, 15, 11}[slot.getIndex()] * 999;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType slot) {
			return new int[]{0, 0, 16, 0}[slot.getIndex()];
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
			return "justitia_cloak";
		}

		@Override
		public float getToughness() {
			return 5f;
		}

		@Override
		public float getKnockbackResistance() {
			return 0.1f;
		}
	};

	public JustitiaCloak(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}

	public JustitiaCloak(IArmorMaterial armorMaterial, EquipmentSlotType slot, Properties props, float redResistance, float whiteResistance, float blackResistance, float paleResistance, float slashResistance, float pierceResistance, float bluntResistance, float bonusStagger, float bonusSanity) {
		super(armorMaterial, slot, props, redResistance, whiteResistance, blackResistance, paleResistance,slashResistance, pierceResistance, bluntResistance, bonusStagger, bonusSanity);


	}

	// Texture Override
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "ego_weapons:textures/entities/justitia_cloak.png";
	}

	public static Item getArmorForSlot(EquipmentSlotType slot) {
		switch (slot) {
			default:
				return null;
			case CHEST:
				return chest;
			case HEAD:
				return mask;
		}
	}

	static Item mask = new JustitiaCloak(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.HEAD, new Properties().tab(ItemGroup.TAB_COMBAT)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			JustitiaArmorModel m = new JustitiaArmorModel();
			armorModel.head = m.Head;
			armorModel.hat = m.Dummy;
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
	static Item chest = new JustitiaCloak(justitiaArmor, EquipmentSlotType.CHEST, new Properties().tab(ItemGroup.TAB_COMBAT), 0.5f, 0.5f, 0.5f ,0.5f, 1.3f, 1f, 0.7f, -4, -4) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);


			JustitiaArmorModel m = new JustitiaArmorModel();
			armorModel.body = m.Body;
			armorModel.leftArm = m.LeftArm;
			armorModel.rightArm = m.RightArm;
			armorModel.crouching = living.isCrouching();
			armorModel.riding = defaultModel.riding;
			armorModel.young = living.isBaby();
			return armorModel;
		}

		@Override
		public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(new StringTextComponent("A feathery shroud worn by those who swear to enforce justice.").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
			list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

			list.add(new StringTextComponent("= - - - - - - - [Page: " + ((EgoWeaponsKeybinds.getUiPage() % 4) + 1) + "/4] - - - - - - - =").withStyle(TextFormatting.GRAY));
			list.add(new TranslationTextComponent("desc.ego_weapons.risk.aleph"));
			list.add(new StringTextComponent(" "));
			switch (EgoWeaponsKeybinds.getUiPage() % 4) {
				case 0:
					resistanceMods(itemstack, world, list, flag);
					break;
				case 1:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"rupture", "speed"});
					else
						generateDescription(list, "justitia_cloak", "passive", 3);
					break;
				case 2:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"speed_up"});
					else
						generateDescription(list, "justitia_cloak", "passive2", 4);
					break;
				case 3:
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"speed_up","strider_mao"});
					else
						generateDescription(list, "justitia_cloak", "ability", 6);
					break;
			}

			list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
		}

	};


	// Made with Blockbench 4.7.1
	// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
	// Paste this class into your mod and generate all required imports
	public class JustitiaArmorModel extends EntityModel<Entity> {
		private final ModelRenderer Body;
		private final ModelRenderer cube_r1;
		private final ModelRenderer cube_r2;
		private final ModelRenderer cube_r3;
		private final ModelRenderer cube_r4;
		private final ModelRenderer cube_r5;
		private final ModelRenderer cube_r6;
		private final ModelRenderer cube_r7;
		private final ModelRenderer cube_r8;
		private final ModelRenderer cube_r9;
		private final ModelRenderer cube_r10;
		private final ModelRenderer cube_r11;
		private final ModelRenderer RightArm;
		private final ModelRenderer LeftArm;
		private final ModelRenderer Head;
		private final ModelRenderer Head_r1;
		private final ModelRenderer Head_r2;

		private final ModelRenderer Dummy;
		public JustitiaArmorModel() {
			texWidth = 90;
			texHeight = 64;
			Dummy = new ModelRenderer(this);
			Body = new ModelRenderer(this);
			Body.setPos(0.0F, 0.0F, 0.0F);
			Body.texOffs(23, 33).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 13.0F, 5.0F, -0.3F, false);
			Body.texOffs(24, 0).addBox(-5.0F, -0.5F, -3.0F, 10.0F, 16.0F, 6.0F, -0.1F, false);
			Body.texOffs(21, 53).addBox(-5.0F, 14.0F, -2.9F, 10.0F, 5.0F, 6.0F, 0.1F, false);
			Body.texOffs(24, 23).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 10.0F, 0.0F, 0.15F, false);

			cube_r1 = new ModelRenderer(this);
			cube_r1.setPos(5.0F, 3.5F, 2.5F);
			Body.addChild(cube_r1);
			setRotationAngle(cube_r1, 0.95F, 0.0792F, -0.0497F);
			cube_r1.texOffs(0, 35).addBox(0.0F, -5.25F, 0.5F, 0.0F, 5.0F, 5.0F, 0.0F, false);

			cube_r2 = new ModelRenderer(this);
			cube_r2.setPos(-5.0F, 5.0F, 2.5F);
			Body.addChild(cube_r2);
			setRotationAngle(cube_r2, 0.9112F, -0.3033F, 0.0198F);
			cube_r2.texOffs(0, 35).addBox(0.0F, -5.25F, 0.5F, 0.0F, 5.0F, 5.0F, 0.0F, false);

			cube_r3 = new ModelRenderer(this);
			cube_r3.setPos(0.25F, 1.25F, -0.25F);
			Body.addChild(cube_r3);
			setRotationAngle(cube_r3, -0.3542F, -0.3033F, 0.0198F);
			cube_r3.texOffs(0, 35).addBox(0.0F, -5.25F, 0.5F, 0.0F, 5.0F, 5.0F, 0.0F, false);

			cube_r4 = new ModelRenderer(this);
			cube_r4.setPos(-6.5F, 3.5F, -1.25F);
			Body.addChild(cube_r4);
			setRotationAngle(cube_r4, -2.7376F, 0.3471F, -2.6722F);
			cube_r4.texOffs(0, 40).addBox(0.0F, -5.25F, 0.5F, 0.0F, 5.0F, 5.0F, 0.0F, false);

			cube_r5 = new ModelRenderer(this);
			cube_r5.setPos(-4.75F, 3.5F, 0.5F);
			Body.addChild(cube_r5);
			setRotationAngle(cube_r5, 3.0951F, 0.3201F, 3.1415F);
			cube_r5.texOffs(0, 44).addBox(0.0F, -5.25F, 0.5F, 0.0F, 5.0F, 5.0F, 0.0F, false);

			cube_r6 = new ModelRenderer(this);
			cube_r6.setPos(1.0F, 3.5F, 0.25F);
			Body.addChild(cube_r6);
			setRotationAngle(cube_r6, 3.1242F, 0.3228F, -3.0497F);
			cube_r6.texOffs(0, 35).addBox(0.0F, -5.25F, 0.5F, 0.0F, 5.0F, 5.0F, 0.0F, false);

			cube_r7 = new ModelRenderer(this);
			cube_r7.setPos(3.25F, 3.5F, 0.5F);
			Body.addChild(cube_r7);
			setRotationAngle(cube_r7, 2.9675F, 0.4993F, 2.3602F);
			cube_r7.texOffs(0, 40).addBox(0.0F, -5.25F, 0.5F, 0.0F, 5.0F, 5.0F, 0.0F, false);

			cube_r8 = new ModelRenderer(this);
			cube_r8.setPos(2.25F, 2.5F, -0.25F);
			Body.addChild(cube_r8);
			setRotationAngle(cube_r8, -0.2294F, 0.3784F, -0.134F);
			cube_r8.texOffs(0, 40).addBox(0.0F, -5.25F, 0.5F, 0.0F, 5.0F, 5.0F, 0.0F, false);

			cube_r9 = new ModelRenderer(this);
			cube_r9.setPos(2.0F, 2.5F, 0.75F);
			Body.addChild(cube_r9);
			setRotationAngle(cube_r9, -0.2131F, 0.0376F, -0.0561F);
			cube_r9.texOffs(0, 46).addBox(0.0F, -4.25F, 0.5F, 0.0F, 4.0F, 4.0F, 0.0F, false);

			cube_r10 = new ModelRenderer(this);
			cube_r10.setPos(-3.0F, 2.5F, 0.75F);
			Body.addChild(cube_r10);
			setRotationAngle(cube_r10, -0.2182F, -0.2182F, 0.0F);
			cube_r10.texOffs(0, 46).addBox(0.0F, -4.25F, 0.5F, 0.0F, 4.0F, 4.0F, 0.0F, false);

			cube_r11 = new ModelRenderer(this);
			cube_r11.setPos(0.0F, 0.0F, 0.0F);
			Body.addChild(cube_r11);
			setRotationAngle(cube_r11, -0.7418F, 0.0F, 0.0F);
			cube_r11.texOffs(0, 17).addBox(-5.0F, -2.25F, 1.5F, 10.0F, 2.0F, 2.0F, 0.0F, false);

			RightArm = new ModelRenderer(this);
			RightArm.setPos(-5.0F, 2.0F, 0.0F);
			setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
			RightArm.texOffs(0, 21).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.35F, true);
			RightArm.texOffs(0, 47).addBox(-3.5F, -2.0F, -2.5F, 5.0F, 12.0F, 5.0F, 0.05F, true);
			RightArm.texOffs(8, 21).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.6F, false);

			LeftArm = new ModelRenderer(this);
			LeftArm.setPos(5.0F, 2.0F, 0.0F);
			setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
			LeftArm.texOffs(0, 21).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.35F, false);
			LeftArm.texOffs(8, 21).addBox(-0.9F, -2.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.6F, false);
			LeftArm.texOffs(0, 47).addBox(-1.5F, -2.0F, -2.5F, 5.0F, 12.0F, 5.0F, 0.05F, false);

			Head = new ModelRenderer(this);
			Head.setPos(0, -1.0F, 0.0F);
			setRotationAngle(Head, -0.1047F, 0.0873F, 0.0F);


			Head_r1 = new ModelRenderer(this);
			Head_r1.setPos(0, -4.0F, 0.0F);
			Head.addChild(Head_r1);
			setRotationAngle(Head_r1, 0.0F, 0.0F, -0.0436F);
			Head_r1.texOffs(44, 33).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 3.0F, 8.0F, 0.3F, false);

			Head_r2 = new ModelRenderer(this);
			Head_r2.setPos(0, -4.0F, 0.0F);
			Head.addChild(Head_r2);
			setRotationAngle(Head_r2, 0.0F, 0.0F, 0.0436F);
			Head_r2.texOffs(40, 22).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 3.0F, 8.0F, 0.2F, false);
		}

		@Override
		public void setupAnim(Entity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

		}


		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.xRot = x;
			modelRenderer.yRot = y;
			modelRenderer.zRot = z;
		}

		@Override
		public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
			Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}
	}
	
}
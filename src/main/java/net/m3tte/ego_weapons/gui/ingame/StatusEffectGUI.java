package net.m3tte.ego_weapons.gui.ingame;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.client.renderer.EgoWeaponsRenderTypes;
import net.m3tte.ego_weapons.item.firefist.FirefistGauntlet;
import net.m3tte.ego_weapons.item.guns.GunItem;
import net.m3tte.ego_weapons.potion.SolemnLamentEffects;
import net.m3tte.ego_weapons.potion.countEffects.CountPotencyStatus;
import net.m3tte.ego_weapons.world.capabilities.AmmoSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.gui.EntityIndicator;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

import static net.m3tte.ego_weapons.gui.ingame.ThreatLevelGUI.OVERLAY_TEXTURES;

@OnlyIn(Dist.CLIENT)
public class StatusEffectGUI extends EntityIndicator {

    @Override
    public boolean shouldDraw(ClientPlayerEntity player, LivingEntity entityIn) {

        if (!entityIn.canChangeDimensions() || entityIn.isInvisible() || entityIn == player.getVehicle()) {
            return false;
        } else if (entityIn.distanceToSqr(Minecraft.getInstance().getCameraEntity()) >= 64) {
            return false;
        }
        return true;
    }

    private ResourceLocation solemnLamentButterflyRL = new ResourceLocation(EgoWeaponsMod.MODID, "textures/mob_effect/living_and_departed.png");
    private ResourceLocation ammoRL = new ResourceLocation(EgoWeaponsMod.MODID, "textures/mob_effect/ammo.png");
    private ResourceLocation d10fuelRL = new ResourceLocation(EgoWeaponsMod.MODID, "textures/mob_effect/district_10_fuel.png");
    private ResourceLocation ov_d10fuelRL = new ResourceLocation(EgoWeaponsMod.MODID, "textures/mob_effect/overheated_district_10_fuel.png");

    private ResourceLocation solemnLamentEffectRL = new ResourceLocation(EgoWeaponsMod.MODID, "textures/mob_effect/living_departed.png");

    @Override
    public void drawIndicator(LivingEntity entityIn, MatrixStack matStackIn, IRenderTypeBuffer bufferIn, float partialTicks) {
        Matrix4f mvMatrix = super.getMVMatrix(matStackIn, entityIn, 0.0F, entityIn.getBbHeight() + 0.25F, 0.0F, true, partialTicks);
        Collection<EffectInstance> activeEffects = entityIn.getActiveEffects().stream().filter((st) -> st.getEffect() instanceof CountPotencyStatus).filter((st) -> ((CountPotencyStatus) st.getEffect()).displaysOnHudNormally()).collect(Collectors.toList());
        int column;
        float startX;
        float startY;

        int additionalRenders = 0;

        if (entityIn.hasEffect(SolemnLamentEffects.getLiving()) ||entityIn.hasEffect(SolemnLamentEffects.getDeparted()))
            additionalRenders++;

        if (entityIn.hasEffect(EgoWeaponsEffects.THE_LIVING.get()) ||entityIn.hasEffect(EgoWeaponsEffects.THE_DEPARTED.get()))
            additionalRenders++;

        if (entityIn.getItemInHand(Hand.MAIN_HAND).getItem() instanceof GunItem || entityIn.getItemInHand(Hand.OFF_HAND).getItem() instanceof GunItem)
            additionalRenders++;

        int fuel = FirefistGauntlet.getFuel(entityIn);
        if (fuel > 0 || entityIn.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.FIREFIST_SUIT.get())) {
            additionalRenders++;
        }

        if (!activeEffects.isEmpty() || additionalRenders > 0) {
            Iterator<EffectInstance> iter = activeEffects.iterator();
            int actives = activeEffects.size() + additionalRenders;



            column = (actives - 1);
            int prevActives = 0;

            startX = -0.6F - entityIn.getBbWidth() / 2;
            startY = -0.2F + 0.15F * (float)column;

            // Solemn Lament Butterfly
            if (entityIn.hasEffect(EgoWeaponsEffects.THE_LIVING.get()) ||entityIn.hasEffect(EgoWeaponsEffects.THE_DEPARTED.get())) {
                int slCount = EgoWeaponsEffects.THE_DEPARTED.get().getPotency(entityIn);
                int slPotency = EgoWeaponsEffects.THE_LIVING.get().getPotency(entityIn);

                renderEffect(solemnLamentButterflyRL, prevActives, startX, startY, slCount, slPotency, bufferIn, mvMatrix, true, true);

                prevActives++;
            }

            // Solemn Lament Ammo
            if (entityIn.hasEffect(SolemnLamentEffects.getLiving()) ||entityIn.hasEffect(SolemnLamentEffects.getDeparted())) {
                int slCount = SolemnLamentEffects.getAmmoCount(entityIn, SolemnLamentEffects.getDeparted());
                int slPotency = SolemnLamentEffects.getAmmoCount(entityIn, SolemnLamentEffects.getLiving());

                renderEffect(solemnLamentEffectRL, prevActives, startX, startY, slCount, slPotency, bufferIn, mvMatrix, true, true);

                prevActives++;
            }

            if (fuel > 0 || entityIn.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.FIREFIST_SUIT.get())) {
                renderEffect((fuel > 50 || fuel <= 0) && !(entityIn.hasEffect(EgoWeaponsEffects.FUEL_IGNITION.get()) && fuel > 0) ? d10fuelRL : ov_d10fuelRL, prevActives, startX, startY, 0, fuel, bufferIn, mvMatrix, true, false);
                prevActives++;
            }

            if (entityIn.getItemInHand(Hand.MAIN_HAND).getItem() instanceof GunItem || entityIn.getItemInHand(Hand.OFF_HAND).getItem() instanceof GunItem) {
                ItemStack mainHandWeapon = (entityIn.getItemInHand(Hand.MAIN_HAND));
                ItemStack offHandWeapon = (entityIn.getItemInHand(Hand.OFF_HAND));
                int ammo = 0;

                if (mainHandWeapon.getItem() instanceof GunItem)
                    ammo += AmmoSystem.getAmmoCount(mainHandWeapon);

                if (offHandWeapon.getItem() instanceof GunItem)
                    ammo += AmmoSystem.getAmmoCount(offHandWeapon);

                renderEffect(ammoRL, prevActives, startX, startY, 0, ammo, bufferIn, mvMatrix, true, false);

                prevActives++;
            }



            for(int j = prevActives; j <= actives; ++j) {
                if (!iter.hasNext())
                    break;

                EffectInstance effectInst = iter.next();
                Effect effect = effectInst.getEffect();

                if (effect instanceof CountPotencyStatus) { //

                    ResourceLocation rl;
                    rl = ((CountPotencyStatus)effect).getIcon();
                    int count = ((CountPotencyStatus) effect).getCount(effectInst);
                    int potency = ((CountPotencyStatus) effect).getPotency(effectInst);

                    renderEffect(rl, j, startX, startY, count, potency, bufferIn, mvMatrix, false, false);

                }

                if (!iter.hasNext()) {
                    break;
                }
            }


        }
    }

    public void renderEffect(ResourceLocation rl, float j, float startX, float startY, int count, int potency, IRenderTypeBuffer bufferIn, Matrix4f mvMatrix, boolean renderZero, boolean renderCountZero) {


        Minecraft.getInstance().getTextureManager().bind(rl);
        float y = startY + -0.3F * (float)j;
        IVertexBuilder vertexBuilder1 = bufferIn.getBuffer(EgoWeaponsRenderTypes.overlayTextures(rl));

        this.drawTexturedModalRect2DPlane(mvMatrix, vertexBuilder1, startX, y, startX + 0.3F, y + 0.3F, 0.0F, 0.0F, 256.0F, 256.0F);
        IVertexBuilder numberVertex = bufferIn.getBuffer(EgoWeaponsRenderTypes.overlayTextures(OVERLAY_TEXTURES));

        if (potency > 0 || renderZero)
            renderNumber(potency, mvMatrix, numberVertex, startX-0.01f, y, 0.045f, renderZero);

        if (count > 0 || renderCountZero)
            renderNumber(count, mvMatrix, numberVertex, startX+0.25f, y,0.01f, renderCountZero);
    }

    public void renderNumber(int number, Matrix4f mvMatrix, IVertexBuilder vertexBuilder, float x, float y, float offsetMult, boolean renderZero) {
        int index = 0;

        int len = (int)Math.ceil(Math.log10(number + 1));
        x += len * offsetMult;

        while (number > 0 || renderZero) {
            renderDigit(number % 10, mvMatrix, vertexBuilder, x - 0.07f*index, y);

            number = number / 10;
            index++;
            renderZero = false;
        }
    }

    private void renderDigit(int digit, Matrix4f mvMatrix, IVertexBuilder vertexBuilder, float x, float y) {
        this.drawTexturedModalRect3DPlane(mvMatrix, vertexBuilder, x, y,0.05f, x+0.06f, y+0.1f, 0.05f,5*digit, 248, 5+5*digit, 256);
    }
}



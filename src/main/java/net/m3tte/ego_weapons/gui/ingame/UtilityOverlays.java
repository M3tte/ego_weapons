package net.m3tte.ego_weapons.gui.ingame;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.client.renderer.EgoWeaponsRenderTypes;
import net.m3tte.ego_weapons.world.capabilities.DamageResistanceSystem;
import net.m3tte.ego_weapons.world.capabilities.threatlevel.ThreatLevelSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.gui.EntityIndicator;

@OnlyIn(Dist.CLIENT)
public class UtilityOverlays extends EntityIndicator {

    public static final ResourceLocation UTIL_OVERLAY_ELEMENTS = new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/overlays/util_overlay_elements.png");

    @Override
    public boolean shouldDraw(ClientPlayerEntity player, LivingEntity entityIn) {

        if (!entityIn.canChangeDimensions() || entityIn.isInvisible() || entityIn == player.getVehicle()) {
            return false;
        } else if (entityIn.distanceToSqr(Minecraft.getInstance().getCameraEntity()) >= 64) {
            return false;
        } else
        return true;
    }

    @Override
    public void drawIndicator(LivingEntity entityIn, MatrixStack matStackIn, IRenderTypeBuffer bufferIn, float partialTicks) {
        Matrix4f mvMatrix = super.getMVMatrix(matStackIn, entityIn, 0.0F, entityIn.getBbHeight() + 0.25F, 0.0F, true, partialTicks);

        if (entityIn.hasEffect(EgoWeaponsEffects.TARGET_MARK_UDJAT.get()) && Minecraft.getInstance().player != null) {

            if (Minecraft.getInstance().player.hasEffect(EgoWeaponsEffects.UDJAT_VANGUARD.get())) {
                int damageTypeIndex = DamageResistanceSystem.getWeakestDamageType(entityIn, false).ordinal();

                this.drawTexturedModalRect2DPlane(mvMatrix, bufferIn.getBuffer(EgoWeaponsRenderTypes.overlayTextures(UTIL_OVERLAY_ELEMENTS)), -0.4F, 0.4F, 0.4F, 1.2F, 0, 24 * damageTypeIndex, 24, 24 + 24 * damageTypeIndex);
            }


        }






    }
}



package net.m3tte.ego_weapons.gui.ingame;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.client.renderer.EgoWeaponsRenderTypes;
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
public class ThreatLevelGUI extends EntityIndicator {

    public static final ResourceLocation OVERLAY_TEXTURES = new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/overlays/gui_overlay_elements.png");

    @Override
    public boolean shouldDraw(ClientPlayerEntity player, LivingEntity entityIn) {

        if (!entityIn.canChangeDimensions() || entityIn.isInvisible() || entityIn == player.getVehicle()) {
            return false;
        } else if (entityIn.distanceToSqr(Minecraft.getInstance().getCameraEntity()) >= 64) {
            return false;
        } else if (entityIn instanceof PlayerEntity) {
            return false;
        }
        return ThreatLevelSystem.getThreatRegistry().get(entityIn.getType()) != null;
    }

    @Override
    public void drawIndicator(LivingEntity entityIn, MatrixStack matStackIn, IRenderTypeBuffer bufferIn, float partialTicks) {
        Matrix4f mvMatrix = super.getMVMatrix(matStackIn, entityIn, 0.0F, entityIn.getBbHeight() + 0.25F, 0.0F, true, partialTicks);
        int index = ThreatLevelSystem.getThreatRegistry().get(entityIn.getType()).ordinal();
        this.drawTexturedModalRect2DPlane(mvMatrix, bufferIn.getBuffer(EgoWeaponsRenderTypes.overlayTextures(OVERLAY_TEXTURES)), -1F, 0.2F, 1F, 0.7F, 0, 16 * index, 64, 16 + 16 * index);
    }
}



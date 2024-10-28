package net.m3tte.tcorp.world.capabilities.entitypatch;

import net.m3tte.tcorp.client.models.entities.SunshowerFoxModel;
import net.m3tte.tcorp.entities.SunshowerFoxEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class SunshowerFoxPatchRenderer extends PatchedLivingEntityRenderer<SunshowerFoxEntity, SunshowerFoxPatch, SunshowerFoxModel> {
    public SunshowerFoxPatchRenderer() {

    }

    @Override
    protected void setJointTransforms(SunshowerFoxPatch entitypatch, Armature armature, float partialTicks) {
        this.setJointTransform(4, armature, entitypatch.getHeadMatrix(partialTicks));
    }

    @Override
    protected int getRootJointIndex() {
        return 0;
    }

    @Override
    protected double getLayerCorrection() {
        return 0.5F;
    }
}

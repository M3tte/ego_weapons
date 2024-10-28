package net.m3tte.tcorp.world.capabilities.entitypatch;

import net.m3tte.tcorp.entities.NothingThere2Entity;
import net.m3tte.tcorp.client.models.entities.NothingTherePhase2Model;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class NothingThereRenderer extends PatchedLivingEntityRenderer<NothingThere2Entity, NothingTherePatch, NothingTherePhase2Model> {
    public NothingThereRenderer() {

    }

    @Override
    protected void setJointTransforms(NothingTherePatch entitypatch, Armature armature, float partialTicks) {
        this.setJointTransform(3, armature, entitypatch.getHeadMatrix(partialTicks));
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

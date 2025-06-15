package net.m3tte.ego_weapons.world.capabilities.entitypatch;

import net.m3tte.ego_weapons.client.models.entities.CravingBloodbagModel;
import net.m3tte.ego_weapons.entities.CravingBloodbagEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class CravingBloodbagRendererPatch extends PatchedLivingEntityRenderer<CravingBloodbagEntity, CravingBloodbagPatch, CravingBloodbagModel> {
    public CravingBloodbagRendererPatch() {

    }

    @Override
    protected void setJointTransforms(CravingBloodbagPatch entitypatch, Armature armature, float partialTicks) {
        // this.setJointTransform(9, armature, entitypatch.getHeadMatrix(partialTicks));
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

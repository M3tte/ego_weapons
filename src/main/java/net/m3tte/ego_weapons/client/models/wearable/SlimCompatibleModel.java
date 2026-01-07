package net.m3tte.ego_weapons.client.models.wearable;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;



// Full credit to https://www.curseforge.com/minecraft/mc-mods/wings-of-freedom
// Copying this is a necessary evil to make overlays properly work with even slim versions
public class SlimCompatibleModel<T extends LivingEntity> extends BipedModel<T> {

    private final ModelRenderer slimLeftArm;
    private final ModelRenderer slimRightArm;
    private final ModelRenderer standardLeftArm;
    private final ModelRenderer standardRightArm;


    public SlimCompatibleModel(float scale) {
        super(scale);
        this.standardLeftArm = this.leftArm;
        this.standardRightArm = this.rightArm;
        this.slimLeftArm = new ModelRenderer(this, 40, 16);
        this.slimLeftArm.mirror = true;
        this.slimLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, scale);
        this.slimLeftArm.setPos(5.0F, 2.5F + scale, 0.0F);
        this.slimRightArm = new ModelRenderer(this, 40, 16);
        this.slimRightArm.addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, scale);
        this.slimRightArm.setPos(-5.0F, 2.5F + scale, 0.0F);
    }

    public BipedModel<T> applyStandardVersion() {
        this.leftArm = this.standardLeftArm;
        this.rightArm = this.standardRightArm;
        return this;
    }

    public BipedModel<T> applySlimVersion() {
        this.leftArm = this.slimLeftArm;
        this.rightArm = this.slimRightArm;
        return this;
    }

}

package net.m3tte.ego_weapons.client.renderer;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class EgoWeaponsRenderTypes extends RenderType {
    public EgoWeaponsRenderTypes(String p_i225992_1_, VertexFormat p_i225992_2_, int p_i225992_3_, int p_i225992_4_, boolean p_i225992_5_, boolean p_i225992_6_, Runnable p_i225992_7_, Runnable p_i225992_8_) {
        super(p_i225992_1_, p_i225992_2_, p_i225992_3_, p_i225992_4_, p_i225992_5_, p_i225992_6_, p_i225992_7_, p_i225992_8_);
    }


    private static final Function<ResourceLocation, RenderType> RISK_INDICATOR;

    public static RenderType overlayTextures(ResourceLocation locationIn) {
        return RISK_INDICATOR.apply(locationIn);
    }


    static {
        RISK_INDICATOR = (textureLocation) -> {
            RenderType.State state = State.builder().setTextureState(new RenderState.TextureState(textureLocation, false, false)).setShadeModelState(FLAT_SHADE).setTransparencyState(RenderState.TRANSLUCENT_TRANSPARENCY).setLightmapState(RenderState.NO_LIGHTMAP).setAlphaState(MIDWAY_ALPHA).createCompositeState(true);
            return create("ego_weapons:risk_indicator", DefaultVertexFormats.POSITION_TEX, 7, 256, true, false, state);
        };

    }
}

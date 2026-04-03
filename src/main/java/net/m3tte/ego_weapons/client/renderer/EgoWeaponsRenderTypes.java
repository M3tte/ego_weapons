package net.m3tte.ego_weapons.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class EgoWeaponsRenderTypes extends RenderType {


    private static void setupBloodTexturing(float p_228548_0_) {
        RenderSystem.matrixMode(5890);
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.translatef(100, 100, 0.0F);
        RenderSystem.rotatef(3.0F, 0.0F, 0.0F, 1.0F);
        RenderSystem.scalef(p_228548_0_, p_228548_0_, p_228548_0_);
        RenderSystem.matrixMode(5888);
    }
    protected static final RenderState.TexturingState ENTITY_BLOOD_TEXTURING = new RenderState.TexturingState("entity_glint_texturing", () -> {
        setupBloodTexturing(2.5F);
    }, () -> {
        RenderSystem.matrixMode(5890);
        RenderSystem.popMatrix();
        RenderSystem.matrixMode(5888);
    });

    protected static final RenderState.TexturingState ENTITY_BLOOD_TEXTURING_ALT = new RenderState.TexturingState("entity_glint_texturing", () -> {
        setupBloodTexturing(2F);
    }, () -> {
        RenderSystem.matrixMode(5890);
        RenderSystem.popMatrix();
        RenderSystem.matrixMode(5888);
    });

    public EgoWeaponsRenderTypes(String p_i225992_1_, VertexFormat p_i225992_2_, int p_i225992_3_, int p_i225992_4_, boolean p_i225992_5_, boolean p_i225992_6_, Runnable p_i225992_7_, Runnable p_i225992_8_) {
        super(p_i225992_1_, p_i225992_2_, p_i225992_3_, p_i225992_4_, p_i225992_5_, p_i225992_6_, p_i225992_7_, p_i225992_8_);
    }


    public static final ResourceLocation BLOOD_0_LOC = new ResourceLocation("ego_weapons","textures/screens/shaders/blood_stage_0.png");
    public static final ResourceLocation BLOOD_1_LOC = new ResourceLocation("ego_weapons","textures/screens/shaders/blood_stage_1.png");
    public static final ResourceLocation BLOOD_2_LOC = new ResourceLocation("ego_weapons","textures/screens/shaders/blood_stage_2.png");
    public static final ResourceLocation BLOOD_3_LOC = new ResourceLocation("ego_weapons","textures/screens/shaders/blood_stage_3.png");
    public static final ResourceLocation BLOOD_4_LOC = new ResourceLocation("ego_weapons","textures/screens/shaders/blood_stage_4.png");

    protected static final RenderState.LayerState VIEW_OFFSET_Z_LAYERING_ALT = new RenderState.LayerState("view_offset_z_layering", () -> {
        RenderSystem.pushMatrix();
        RenderSystem.scalef(0.99955586F, 0.99955586F, 0.99955586F);
    }, RenderSystem::popMatrix);
    public static final ResourceLocation FIRE_GLINT_LOC = new ResourceLocation("ego_weapons","textures/shaders/fire_glint.png");
    //private static final RenderType FIRE_ENTITY_GLINT_DIRECT = create("ego_weapons:flame_glint", DefaultVertexFormats.NEW_ENTITY, 7, 256, RenderType.State.builder().setTextureState(new RenderState.TextureState(FIRE_GLINT_LOC, true, false)).setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(GLINT_TRANSPARENCY).setTexturingState(ENTITY_GLINT_TEXTURING).setLayeringState(VIEW_OFFSET_Z_LAYERING).createCompositeState(false));
    private static final RenderType FIRE_GLINT = create("ego_weapons:fire_glint_fx", DefaultVertexFormats.NEW_ENTITY, 4, 256, false, false, State.builder().setTextureState(new RenderState.TextureState(FIRE_GLINT_LOC, false, false)).setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(GLINT_TRANSPARENCY).setTexturingState(ENTITY_GLINT_TEXTURING).setLayeringState(VIEW_OFFSET_Z_LAYERING_ALT).createCompositeState(false));
    private static final RenderType ENTITY_BLOOD_OVERLAY_0 = create("ego_weapons:ego_entity_blood_fx", DefaultVertexFormats.NEW_ENTITY, 4, 256, RenderType.State.builder().setTextureState(new RenderState.TextureState(BLOOD_0_LOC, false, false)).setDiffuseLightingState(DIFFUSE_LIGHTING).setLightmapState(LIGHTMAP).setCullState(NO_CULL).setDepthTestState(RenderState.EQUAL_DEPTH_TEST).setTransparencyState(RenderState.TRANSLUCENT_TRANSPARENCY).setTexturingState(ENTITY_BLOOD_TEXTURING_ALT).setOutputState(TRANSLUCENT_TARGET).createCompositeState(false));
    private static final RenderType ENTITY_BLOOD_OVERLAY_1 = create("ego_weapons:ego_entity_blood_fx", DefaultVertexFormats.NEW_ENTITY, 4, 256, RenderType.State.builder().setTextureState(new RenderState.TextureState(BLOOD_1_LOC, false, false)).setDiffuseLightingState(DIFFUSE_LIGHTING).setLightmapState(LIGHTMAP).setCullState(NO_CULL).setDepthTestState(RenderState.EQUAL_DEPTH_TEST).setTransparencyState(RenderState.TRANSLUCENT_TRANSPARENCY).setTexturingState(ENTITY_BLOOD_TEXTURING_ALT).setOutputState(TRANSLUCENT_TARGET).createCompositeState(false));
    private static final RenderType ENTITY_BLOOD_OVERLAY_2 = create("ego_weapons:ego_entity_blood_fx", DefaultVertexFormats.NEW_ENTITY, 4, 256, RenderType.State.builder().setTextureState(new RenderState.TextureState(BLOOD_2_LOC, false, false)).setDiffuseLightingState(DIFFUSE_LIGHTING).setLightmapState(LIGHTMAP).setCullState(NO_CULL).setDepthTestState(RenderState.EQUAL_DEPTH_TEST).setTransparencyState(RenderState.TRANSLUCENT_TRANSPARENCY).setTexturingState(ENTITY_BLOOD_TEXTURING_ALT).setOutputState(TRANSLUCENT_TARGET).createCompositeState(false));
    private static final RenderType ENTITY_BLOOD_OVERLAY_3 = create("ego_weapons:ego_entity_blood_fx", DefaultVertexFormats.NEW_ENTITY, 4, 256, RenderType.State.builder().setTextureState(new RenderState.TextureState(BLOOD_3_LOC, false, false)).setDiffuseLightingState(DIFFUSE_LIGHTING).setLightmapState(LIGHTMAP).setCullState(NO_CULL).setDepthTestState(RenderState.EQUAL_DEPTH_TEST).setTransparencyState(RenderState.TRANSLUCENT_TRANSPARENCY).setTexturingState(ENTITY_BLOOD_TEXTURING_ALT).setOutputState(TRANSLUCENT_TARGET).createCompositeState(false));
    private static final RenderType ENTITY_BLOOD_OVERLAY_4 = create("ego_weapons:ego_entity_blood_fx", DefaultVertexFormats.NEW_ENTITY, 4, 256, RenderType.State.builder().setTextureState(new RenderState.TextureState(BLOOD_4_LOC, false, false)).setDiffuseLightingState(DIFFUSE_LIGHTING).setLightmapState(LIGHTMAP).setCullState(NO_CULL).setDepthTestState(RenderState.EQUAL_DEPTH_TEST).setTransparencyState(RenderState.TRANSLUCENT_TRANSPARENCY).setTexturingState(ENTITY_BLOOD_TEXTURING_ALT).setOutputState(TRANSLUCENT_TARGET).createCompositeState(false));
    private static final RenderType BLOOD_OVERLAY_0 = create("ego_weapons:ego_blood_fx", DefaultVertexFormats.NEW_ENTITY, 4, 256, false, false, State.builder().setTextureState(new RenderState.TextureState(BLOOD_0_LOC, false, false)).setDiffuseLightingState(DIFFUSE_LIGHTING).setLightmapState(LIGHTMAP).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(RenderState.TRANSLUCENT_TRANSPARENCY).setTexturingState(ENTITY_BLOOD_TEXTURING).setLayeringState(RenderState.VIEW_OFFSET_Z_LAYERING).createCompositeState(false));
    private static final RenderType BLOOD_OVERLAY_1 = create("ego_weapons:ego_blood_fx", DefaultVertexFormats.NEW_ENTITY, 4, 256, false, false, State.builder().setTextureState(new RenderState.TextureState(BLOOD_1_LOC, false, false)).setDiffuseLightingState(DIFFUSE_LIGHTING).setLightmapState(LIGHTMAP).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(RenderState.TRANSLUCENT_TRANSPARENCY).setTexturingState(ENTITY_BLOOD_TEXTURING).setLayeringState(RenderState.VIEW_OFFSET_Z_LAYERING).createCompositeState(false));
    private static final RenderType BLOOD_OVERLAY_2 = create("ego_weapons:ego_blood_fx", DefaultVertexFormats.NEW_ENTITY, 4, 256, false, false, State.builder().setTextureState(new RenderState.TextureState(BLOOD_2_LOC, false, false)).setDiffuseLightingState(DIFFUSE_LIGHTING).setLightmapState(LIGHTMAP).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(RenderState.TRANSLUCENT_TRANSPARENCY).setTexturingState(ENTITY_BLOOD_TEXTURING).setLayeringState(RenderState.VIEW_OFFSET_Z_LAYERING).createCompositeState(false));
    private static final RenderType BLOOD_OVERLAY_3 = create("ego_weapons:ego_blood_fx", DefaultVertexFormats.NEW_ENTITY, 4, 256, false, false, State.builder().setTextureState(new RenderState.TextureState(BLOOD_3_LOC, false, false)).setDiffuseLightingState(DIFFUSE_LIGHTING).setLightmapState(LIGHTMAP).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(RenderState.TRANSLUCENT_TRANSPARENCY).setTexturingState(ENTITY_BLOOD_TEXTURING).setLayeringState(RenderState.VIEW_OFFSET_Z_LAYERING).createCompositeState(false));
    private static final RenderType BLOOD_OVERLAY_4 = create("ego_weapons:ego_blood_fx", DefaultVertexFormats.NEW_ENTITY, 4, 256, false, false, State.builder().setTextureState(new RenderState.TextureState(BLOOD_4_LOC, false, false)).setDiffuseLightingState(DIFFUSE_LIGHTING).setLightmapState(LIGHTMAP).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(RenderState.TRANSLUCENT_TRANSPARENCY).setTexturingState(ENTITY_BLOOD_TEXTURING).setLayeringState(RenderState.VIEW_OFFSET_Z_LAYERING).createCompositeState(false));

    private static final Function<ResourceLocation, RenderType> FULLBRIGHT_ANIMATED_ARMOR = (p_173206_) -> {
        RenderType.State rendertype$compositestate = State.builder().setTextureState(new RenderState.TextureState(p_173206_, false, false)).setTransparencyState(NO_TRANSPARENCY).setCullState(NO_CULL).setOverlayState(OVERLAY).setLayeringState(VIEW_OFFSET_Z_LAYERING_ALT).setAlphaState(DEFAULT_ALPHA).createCompositeState(true);
        return create("ego_weapons:fullbright_armor", DefaultVertexFormats.NEW_ENTITY, 4, 256, true, false, rendertype$compositestate);
    };


    private static final Function<ResourceLocation, RenderType> RISK_INDICATOR;

    private static final Function<ResourceLocation, RenderType> ARMOR_TRANSLUCENT_NO_CULL;


    public static RenderType getFullbrightAnimatedArmor(ResourceLocation tex) {
        return FULLBRIGHT_ANIMATED_ARMOR.apply(tex);
    }

    public static RenderType getFireGlintDirect() {
        return FIRE_GLINT;
    }
    public static RenderType overlayTextures(ResourceLocation locationIn) {
        return RISK_INDICATOR.apply(locationIn);
    }
    public static RenderType bloodOverlay(int idx) {
        switch (idx) {
            default: return BLOOD_OVERLAY_0;
            case 1: return BLOOD_OVERLAY_1;
            case 2: return BLOOD_OVERLAY_2;
            case 3: return BLOOD_OVERLAY_3;
            case 4: return BLOOD_OVERLAY_4;
        }
    }

    public static RenderType entityBloodOverlay(int idx) {
        switch (idx) {
            default: return ENTITY_BLOOD_OVERLAY_0;
            case 1: return ENTITY_BLOOD_OVERLAY_1;
            case 2: return ENTITY_BLOOD_OVERLAY_2;
            case 3: return ENTITY_BLOOD_OVERLAY_3;
            case 4: return ENTITY_BLOOD_OVERLAY_4;
        }
    }

    public static RenderType armorTranslucentNoCull(ResourceLocation locationIn) {
        return ARMOR_TRANSLUCENT_NO_CULL.apply(locationIn);
    }

    static {
        RISK_INDICATOR = (textureLocation) -> {
            RenderType.State state = State.builder().setTextureState(new RenderState.TextureState(textureLocation, false, false)).setShadeModelState(FLAT_SHADE).setTransparencyState(RenderState.TRANSLUCENT_TRANSPARENCY).setLightmapState(RenderState.NO_LIGHTMAP).setAlphaState(MIDWAY_ALPHA).createCompositeState(true);
            return create("ego_weapons:risk_indicator", DefaultVertexFormats.POSITION_TEX, 7, 256, true, false, state);
        };

        ARMOR_TRANSLUCENT_NO_CULL = (textureLocation) -> {
            RenderType.State state = State.builder().setTransparencyState(RenderState.TRANSLUCENT_TRANSPARENCY).setCullState(RenderState.NO_CULL).setDiffuseLightingState(new RenderState.DiffuseLightingState(true)).setAlphaState(RenderState.DEFAULT_ALPHA).setCullState(new RenderState.CullState(false)).setLightmapState(new RenderState.LightmapState(true)).setOverlayState(new RenderState.OverlayState(true)).setLayeringState(RenderState.VIEW_OFFSET_Z_LAYERING).setTextureState(new RenderState.TextureState(textureLocation, false, false)).createCompositeState(true);
            return create("ego_weapons:armor_translucent_no_cull", DefaultVertexFormats.NEW_ENTITY, 7, 256, true, false, state);
        };


    }

}

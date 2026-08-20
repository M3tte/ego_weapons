package net.m3tte.ego_weapons.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.potion.ManifestEgoPotionEffect;
import net.m3tte.ego_weapons.potion.OrlandoPotionEffect;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.m3tte.ego_weapons.world.capabilities.UtilitySystems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

import static net.m3tte.ego_weapons.client.renderer.EgoWeaponsShaders.*;
import static net.minecraft.client.gui.AbstractGui.blit;

@Mod.EventBusSubscriber({Dist.CLIENT})
@OnlyIn(Dist.CLIENT)
public class EgoWeaponsRenderSystem {



    private static Queue<Particle> distortionParticles = new ArrayDeque<>();
    private static Framebuffer DISTORTION_MASK = null;

    static int savedWidth = 0;
    static int savedHeight = 0;
    public static ActiveRenderInfo savedRenderInfo = null;
    public static Framebuffer getDistortionMask() {
        if (DISTORTION_MASK == null) {
            Framebuffer main = Minecraft.getInstance().getMainRenderTarget();
            DISTORTION_MASK = new Framebuffer(main.width / 2, main.height / 2, true, Minecraft.ON_OSX);
            savedWidth = Minecraft.getInstance().getWindow().getWidth();
            savedHeight = Minecraft.getInstance().getWindow().getHeight();

        }



        return DISTORTION_MASK;


    }


    private static boolean renderColorOverrideState = false;


    /***
     * Gets the current override state for rendering colors on particles.
     * true - Current render state is override so different colors may be used.
     * false - Current render state is the default. Normal colors should be used.
     * @return
     */
    public static boolean getRenderColorOverrideState() {
        return renderColorOverrideState;
    }

    /**
     * Called whenever the system swaps to rendering distortion particles. Shouldnt be called outside of that.
     * @param newState What renderColorOverrideState to swap to.
     *
     * true - Current render state is override so different colors may be used.
     * false - Current render state is the default. Normal colors should be used.
     */
    public static void toggleRenderColorOverrideState(boolean newState) {
        renderColorOverrideState = newState;
    }


    public static Queue<Particle> getDistortionParticles() {
        return distortionParticles;
    }


    @SubscribeEvent
    public static void renderEvent(RenderWorldLastEvent event) {

        Framebuffer main = Minecraft.getInstance().getMainRenderTarget();

        if (savedRenderInfo == null)
            return;


        if (main.width != savedWidth || main.height != savedHeight) {
            getDistortionMask().resize(main.width / 3, main.height / 3, Minecraft.ON_OSX);
            savedHeight = main.height;
            savedWidth = main.width;

            resizeScreens(main.width, main.height);
        }

        if (!getDistortionParticles().isEmpty()) {
            renderDistortionParticles(event);
        }


        setupShaderData();

        renderOverlays(event);
    }

    private static void setupShaderData() {
        if (!setupCompleted)
            return;


        float time = (System.currentTimeMillis() % 1000000L) / 1000.0F;


        ShaderUniform noiseTime = STAR_SHADER.getUniform("GameTime");

        if (noiseTime != null)
            noiseTime.set(time);

        STAR_SHADER.setSampler(
                "StarSampler",
                () -> Objects.requireNonNull(Minecraft.getInstance().getTextureManager().getTexture(PORTAL_EFFECT)).getId()
        );
    }



    private static void renderOverlays(RenderWorldLastEvent event) {
        Queue<ShaderGroup> toProcessGroups = new ArrayDeque<>();


        if (!setupCompleted)
            return;

        if (Minecraft.getInstance().player == null)
            return;

        if (!getDistortionParticles().isEmpty())
            toProcessGroups.add(DISTORT_GROUP);

        if (Minecraft.getInstance().player.hasEffect(EgoWeaponsEffects.UDJAT_VANGUARD.get()))
            toProcessGroups.add(UDJAT_CCTV_GROUP);

        if (Minecraft.getInstance().player.hasEffect(ManifestEgoPotionEffect.potion))
            toProcessGroups.add(REDMIST_GROUP);

        if (Minecraft.getInstance().player.hasEffect(OrlandoPotionEffect.potion))
            toProcessGroups.add(BLACK_SILENCE_GROUP);

        if (Minecraft.getInstance().player.hasEffect(EgoWeaponsEffects.CHESEDS_LATENCY.get()))
            toProcessGroups.add(CHESED_GROUP);

        if (EgoWeaponsRenderSystem.getLastAmpl() > 0) {
            toProcessGroups.add(PANIC_EFFECT_GROUP);
        } else if (Minecraft.getInstance().player.hasEffect(EgoWeaponsEffects.TERROR.get()) || SanitySystem.getSanity(Minecraft.getInstance().player) <= 0.05f) {
            toProcessGroups.add(PANIC_EFFECT_GROUP);
        }

        if (EgoWeaponsRenderSystem.getShockwaveAmpl() > 0 || EgoWeaponsRenderSystem.getShockwaveAmplTarget() > 0) {
            toProcessGroups.add(SHOCKWAVE_DISTORTION_GROUP);
        }


        // Time and deltatime should always run
        float time = (System.currentTimeMillis() % 1000000L) / 1000.0F;

        // Dont render if no post processing is to happen.
        if (toProcessGroups.isEmpty())
            return;



        SetupShaderGroup.ShaderData data = new SetupShaderGroup.ShaderData(time, Minecraft.getInstance().getDeltaFrameTime());


        // Wrap up collection and render the overlays

        RenderSystem.pushMatrix();
        for (ShaderGroup group : toProcessGroups) {
            if (group instanceof SetupShaderGroup) {
                ((SetupShaderGroup) group).runPreProcess(data);
            }
            group.process(event.getPartialTicks());
        }
        RenderSystem.popMatrix();
        RenderSystem.enableTexture(); //FORGE: Fix MC-194675
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
    }

    private static void renderPostOverlays(float partialTicks) {
        Queue<ShaderGroup> toProcessGroups = new ArrayDeque<>();


        if (!setupCompleted)
            return;

        if (Minecraft.getInstance().player == null)
            return;

        if (UtilitySystems.getLossOfSelf(Minecraft.getInstance().player) > 0)
            toProcessGroups.add(MUGA_GROUP);


        // Dont render if no post processing is to happen.
        if (toProcessGroups.isEmpty())
            return;

        float time = (System.currentTimeMillis() % 1000000L) / 1000.0F;
        SetupShaderGroup.ShaderData data = new SetupShaderGroup.ShaderData(time, partialTicks);


        // Wrap up collection and render the overlays

        RenderSystem.pushMatrix();
        for (ShaderGroup group : toProcessGroups) {
            if (group instanceof SetupShaderGroup) {
                ((SetupShaderGroup) group).runPreProcess(data);
            }
            group.process(partialTicks);
        }
        RenderSystem.popMatrix();
        RenderSystem.enableTexture(); //FORGE: Fix MC-194675
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
    }

    private static void renderDistortionParticles(RenderWorldLastEvent event) {
        Minecraft.getInstance().getMainRenderTarget().unbindWrite();
        getDistortionMask().bindWrite(true);
        //getDistortionMask().setClearColor(0.5f, 0.5f, 0, 1);
        //getDistortionMask().clear(false);



        RenderSystem.clearColor(0.5f, 0.5f, 0.5f, 1);
        RenderSystem.clear(GL11.GL_COLOR_BUFFER_BIT, false);
        toggleRenderColorOverrideState(true);
        //getDistortionMask().copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());

        Runnable enable = () -> {
            RenderSystem.enableAlphaTest();
            //RenderSystem.depthFunc(GL11.GL_ALWAYS);
            //RenderSystem.enableDepthTest();
            //RenderSystem.depthMask(false);
            //RenderSystem.defaultAlphaFunc();
            //RenderSystem.enableDepthTest();
            //RenderSystem.enableFog();
            //RenderSystem.activeTexture(org.lwjgl.opengl.GL13.GL_TEXTURE2);
            //RenderSystem.enableTexture();
            //RenderSystem.activeTexture(org.lwjgl.opengl.GL13.GL_TEXTURE0);
            //RenderSystem.depthMask(true);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.alphaFunc(516, 0.003921569F);
        };
        RenderSystem.pushMatrix();
        RenderSystem.multMatrix(event.getMatrixStack().last().pose());


        Tessellator tess =
                Tessellator.getInstance();

        BufferBuilder buffer =
                tess.getBuilder();

        RenderSystem.disableBlend();
        RenderSystem.depthMask(false);
        Minecraft.getInstance().particleEngine.textureManager.bind(AtlasTexture.LOCATION_PARTICLES);

        Minecraft mc = Minecraft.getInstance();

        buffer.begin(
                GL11.GL_QUADS,
                DefaultVertexFormats.PARTICLE
        );

        enable.run(); //Forge: MC-168672 Make sure all render types have the correct GL state.
        for (Particle p : distortionParticles) {
            p.render(buffer, savedRenderInfo, event.getPartialTicks());
        }


        tess.end();

        RenderSystem.popMatrix();
        RenderSystem.depthMask(true);
        /*
        RenderSystem.depthFunc(515);
        RenderSystem.disableBlend();
        RenderSystem.defaultAlphaFunc();
        // event..turnOffLightLayer();
        RenderSystem.disableFog();*/

        getDistortionMask().unbindWrite();
        toggleRenderColorOverrideState(false);

        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);

        /*
        if (!distortionParticles.isEmpty()) {
            getDistortionMask().blitToScreen(savedWidth,savedHeight);
        }*/

        RenderSystem.disableAlphaTest();
    }

    @SubscribeEvent
    public static void postAllEffect(TickEvent.RenderTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END)) {
            renderPostOverlays(event.renderTickTime);
        }
    }



    private static float targetShakeSpeed = 0;
    private static float targetShakeStrength = 0;

    private static float trueShakeSpeed = 0;
    private static float trueShakeStrength = 0;

    private static float trueFOV = 0;
    private static float targetFOV = 0;
    public static void applyScreenshake(float speed, float strength) {
        targetShakeSpeed += speed;
        targetShakeStrength += strength;
    }

    public static void applyFOV(float amount) {
        targetFOV += amount;
    }

    @SubscribeEvent
    public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        if (targetShakeStrength <= 0.01 && trueShakeStrength <= 0.01)
            return;

        if (targetShakeSpeed <= 0.01 && trueShakeSpeed <= 0.01)
            return;

        float time = (System.currentTimeMillis() % 1000000L) / 1000.0F;
        // Second based delta time. 1 Is the max as otherwise some issues may happen
        float deltaTime = Math.min(Minecraft.getInstance().getDeltaFrameTime() / 18, 1);

        // Adds the difference between target and true to the true value. for buffering
        trueShakeSpeed += (targetShakeSpeed - trueShakeSpeed) * deltaTime;
        trueShakeStrength += (targetShakeStrength - trueShakeStrength) * deltaTime;


        event.setYaw(event.getYaw() + UtilitySystems.noise(0.3f * time * targetShakeSpeed) * targetShakeStrength * 1.5f);
        event.setPitch(event.getPitch() + UtilitySystems.noise(0.3f * time * targetShakeSpeed + 100) * targetShakeStrength * 1.5f);
        event.setRoll(event.getRoll() + UtilitySystems.noise(time * targetShakeSpeed + 200) * targetShakeStrength * 0.66f);

        // Shaking decays by half every second
        targetShakeSpeed = Math.max(-0.05f,targetShakeSpeed - (targetShakeSpeed + 0.05f) * deltaTime * 1.5f);
        targetShakeStrength = Math.max(-0.05f,targetShakeStrength - (targetShakeStrength + 0.05f) * deltaTime * 1.5f);
    }


    @SubscribeEvent
    public static void onFOVUpdate(FOVUpdateEvent event) {
        if (targetFOV <= 0.01f && trueFOV <= 0.01f && trueShakeStrength <= 0.01f)
            return;

        float time = (System.currentTimeMillis() % 1000000L) / 1000.0F;
        // Second based delta time
        float deltaTime = Math.min(1,Minecraft.getInstance().getDeltaFrameTime() * 0.5f);

        trueFOV += (targetFOV - trueFOV) * deltaTime;

        float fovBoost = (trueFOV * 2) + UtilitySystems.noise(time * trueShakeSpeed) * trueShakeStrength * 0.15f;
        //System.out.println("VALS: "+trueFOV+"/"+targetFOV+"   "+trueShakeSpeed+" "+trueShakeStrength);
        event.setNewfov(event.getFov() + fovBoost);
        targetFOV = Math.max(0, targetFOV - ((targetFOV + 0.1f) * deltaTime));
    }


    @OnlyIn(Dist.CLIENT)
    private static float shockwaveAmpl = 0;

    @OnlyIn(Dist.CLIENT)
    private static float shockwaveAmplTarget = 0;

    @OnlyIn(Dist.CLIENT)
    public static float getShockwaveAmpl() {
        return shockwaveAmpl;
    }
    @OnlyIn(Dist.CLIENT)
    public static float getShockwaveAmplTarget() {
        return shockwaveAmplTarget;
    }

    @OnlyIn(Dist.CLIENT)
    public static void incrementShockwaveTarget(float shockwaveAmplTarget) {
        EgoWeaponsRenderSystem.shockwaveAmplTarget = Math.min(4f,EgoWeaponsRenderSystem.shockwaveAmplTarget + shockwaveAmplTarget);
    }

    @OnlyIn(Dist.CLIENT)
    public static float getShockwaveAmplifier(Entity entity, float deltaTime) {
        float diff = (shockwaveAmplTarget - shockwaveAmpl) * Math.min(deltaTime * 0.2f,1);;

        // Decay is half as fast
        if (shockwaveAmplTarget < shockwaveAmpl)
            diff *= 0.5f;

        shockwaveAmpl = (shockwaveAmplTarget - shockwaveAmpl) > 0.005f ? shockwaveAmpl + diff : shockwaveAmplTarget;

        if (shockwaveAmplTarget > 0) {
            shockwaveAmplTarget -= deltaTime * 0.1f;
        } else {
            shockwaveAmplTarget = 0;
        }
        //EgoWeaponsMod.LOGGER.warn("LAST TARGET AMPL: "+shockwaveAmplTarget+"  LAST AMPL: "+shockwaveAmpl+" Deltatime: "+deltaTime);
        // Eases the shockwave value towards 1.5f so it cannot exceed that value. Still smoothly lerps.
        return (shockwaveAmpl * 3f) / (1.4f + shockwaveAmpl);
    }

    @OnlyIn(Dist.CLIENT)
    private static float lastAmpl = 0;

    @OnlyIn(Dist.CLIENT)
    public static float getLastAmpl() {
        return lastAmpl;
    }

    @OnlyIn(Dist.CLIENT)
    public static float getPanicAmplifier(Entity entity, float deltaTime) {
        float amnt = 0;



        if (entity instanceof LivingEntity) {
            if (((LivingEntity) entity).hasEffect(EgoWeaponsEffects.TERROR.get())) {
                if (((LivingEntity) entity).getEffect(EgoWeaponsEffects.TERROR.get()).getAmplifier() > 0) {
                    amnt =  1;
                } else {
                    amnt = Math.min(1,((LivingEntity) entity).getEffect(EgoWeaponsEffects.TERROR.get()).getDuration() / 300f);
                }
            }

            if (entity instanceof PlayerEntity) {
                if (SanitySystem.getSanity((PlayerEntity) entity) <= 0.05f) {
                    amnt = 1.5f;
                }
            }
        }
        float diff = (amnt - lastAmpl) * Math.min(deltaTime * 0.08f,1);;
        lastAmpl = (amnt - lastAmpl) > 0.005f ? lastAmpl + diff : amnt;
        // EgoWeaponsMod.LOGGER.warn("LAST AMPL: "+lastAmpl+ " Deltatime: "+deltaTime);
        return lastAmpl;
    }
}

package net.m3tte.ego_weapons.client.renderer;

import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.world.capabilities.UtilitySystems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderInstance;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL20;

import java.io.IOException;
import java.util.function.BiConsumer;

import static net.m3tte.ego_weapons.client.renderer.EgoWeaponsRenderSystem.*;

@OnlyIn(Dist.CLIENT)
public class EgoWeaponsShaders {
    public static ShaderInstance STAR_SHADER;
    public static SetupShaderGroup DISTORT_GROUP;
    public static SetupShaderGroup UDJAT_CCTV_GROUP;
    public static SetupShaderGroup REDMIST_GROUP;
    public static SetupShaderGroup BLACK_SILENCE_GROUP;
    public static SetupShaderGroup CHESED_GROUP;
    public static SetupShaderGroup MUGA_GROUP;
    public static SetupShaderGroup PANIC_EFFECT_GROUP;
    public static SetupShaderGroup SHOCKWAVE_DISTORTION_GROUP;

    public static boolean setupCompleted = false;
    public static ResourceLocation PORTAL_EFFECT;

    public static BiConsumer<SetupShaderGroup.ShaderData, ShaderGroup> defaultGametimeConsumer = (a, self) -> {
        ShaderUniform uniform = self.passes.get(0).getEffect().getUniform("GameTime");
        if (uniform != null)
            uniform.set(a.getGameTime());
    };

    public static void resizeScreens(int w, int h) {
        if (DISTORT_GROUP != null)
            DISTORT_GROUP.resize(w, h);

        if (UDJAT_CCTV_GROUP != null)
            UDJAT_CCTV_GROUP.resize(w, h);

        if (REDMIST_GROUP != null)
            REDMIST_GROUP.resize(w, h);

        if (CHESED_GROUP != null)
            CHESED_GROUP.resize(w, h);

        if (MUGA_GROUP != null)
            MUGA_GROUP.resize(w, h);

        if (BLACK_SILENCE_GROUP != null)
            BLACK_SILENCE_GROUP.resize(w, h);

        if (PANIC_EFFECT_GROUP != null)
            PANIC_EFFECT_GROUP.resize(w, h);

        if (SHOCKWAVE_DISTORTION_GROUP != null)
            SHOCKWAVE_DISTORTION_GROUP.resize(w, h);
    }

    public static void init(GameRenderer renderer) throws IOException {

        STAR_SHADER =
                new ShaderInstance(
                        Minecraft.getInstance().getResourceManager(),
                        "ego_weapons:noise_particle"
                );


        DISTORT_GROUP = new SetupShaderGroup(
                new ResourceLocation("ego_weapons", "shaders/post/distortion.json"),
                (a, self) -> {
                    ShaderInstance shaderInst = self.passes.get(0).getEffect();
                    shaderInst.setSampler("MaskSampler", getDistortionMask()::getColorTextureId);
                }
        );

        UDJAT_CCTV_GROUP = new SetupShaderGroup(
                new ResourceLocation("ego_weapons", "shaders/post/udjat_screen.json"),
                defaultGametimeConsumer
        );

        REDMIST_GROUP = new SetupShaderGroup(
                new ResourceLocation("ego_weapons", "shaders/post/redmist_rage.json"),
                defaultGametimeConsumer
        );

        CHESED_GROUP = new SetupShaderGroup(
                new ResourceLocation("ego_weapons", "shaders/post/cheseds_latency.json"),
                defaultGametimeConsumer
        );

        MUGA_GROUP = new SetupShaderGroup(
                new ResourceLocation("ego_weapons", "shaders/post/muga_fx.json"),
                (a, self) -> {
                    ShaderUniform uniform = self.passes.get(0).getEffect().getUniform("GameTime");
                    if (uniform != null)
                        uniform.set(a.getGameTime());

                    ShaderUniform lossOfSelf = self.passes.get(0).getEffect().getUniform("MemoryLoss");
                    if (lossOfSelf != null && Minecraft.getInstance().player != null)
                        lossOfSelf.set(UtilitySystems.getLossOfSelf(Minecraft.getInstance().player));
                }
        );

        BLACK_SILENCE_GROUP = new SetupShaderGroup(
                new ResourceLocation("ego_weapons", "shaders/post/black_silence_fog.json"),
                defaultGametimeConsumer
        );

        PANIC_EFFECT_GROUP = new SetupShaderGroup(
                new ResourceLocation("ego_weapons", "shaders/post/panic_effect.json"),
                (a, self) -> {
                    ShaderUniform uniform = self.passes.get(0).getEffect().getUniform("GameTime");
                    if (uniform != null)
                        uniform.set(a.getGameTime());

                    ShaderUniform panicAmplifier = self.passes.get(0).getEffect().getUniform("EffectAmpl");
                    if (panicAmplifier != null && Minecraft.getInstance().player != null) {
                        float val = getPanicAmplifier(Minecraft.getInstance().player, a.getDeltaTime());
                        panicAmplifier.set(val);
                    }

                }
        );

        SHOCKWAVE_DISTORTION_GROUP = new SetupShaderGroup(
                new ResourceLocation("ego_weapons", "shaders/post/shockwave_distortion.json"),
                (a, self) -> {
                    ShaderUniform uniform = self.passes.get(0).getEffect().getUniform("GameTime");
                    if (uniform != null)
                        uniform.set(a.getGameTime());

                    ShaderUniform ampl = self.passes.get(0).getEffect().getUniform("EffectAmpl");
                    if (ampl != null && Minecraft.getInstance().player != null) {
                        float val = getShockwaveAmplifier(Minecraft.getInstance().player, a.getDeltaTime());
                        ampl.set(val);
                    }

                }
        );

        resizeScreens(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());

        ShaderInstance shaderInst = EgoWeaponsShaders.DISTORT_GROUP.passes.get(0).getEffect();
        shaderInst.setSampler("MaskSampler", getDistortionMask()::getColorTextureId);

        // Setup Portal Effect
        PORTAL_EFFECT = new ResourceLocation(EgoWeaponsMod.MODID, "textures/shaders/star_particle.png");

        Minecraft.getInstance().getTextureManager().bind(PORTAL_EFFECT);

        GL20.glUniform1i(
                GL20.glGetUniformLocation(
                        EgoWeaponsShaders.STAR_SHADER.getId(),
                        "StarSampler"
                ),
                1
        );

        setupCompleted = true;
        EgoWeaponsMod.LOGGER.info("Successfully loaded shaders. Yippie!");
    }
}

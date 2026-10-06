package net.m3tte.ego_weapons.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

import java.util.function.Consumer;

import static net.m3tte.ego_weapons.client.renderer.EgoWeaponsShaders.resizeScreens;

public class FramebufferHandlers {
    private static Framebuffer HORIZONTAL_BLOOM_MASK = null;
    static int savedWidth = 0;
    static int savedHeight = 0;

    public static int getSavedWidth() {
        return savedWidth;
    }

    public static int getSavedHeight() {
        return savedHeight;
    }

    public static void setSavedWidth(int savedWidth) {
        FramebufferHandlers.savedWidth = savedWidth;
    }

    public static void setSavedHeight(int savedHeight) {
        FramebufferHandlers.savedHeight = savedHeight;
    }

    private static Framebuffer DISTORTION_MASK = null;


    public static void handleResizes(Framebuffer main) {

        if (main.width != FramebufferHandlers.getSavedWidth() || main.height != FramebufferHandlers.getSavedHeight()) {
            getDistortionMask().resize(main.width / 2, main.height / 2, Minecraft.ON_OSX);
            getBloomMask().resize(main.width / 2, main.height / 2, Minecraft.ON_OSX);
            FramebufferHandlers.setSavedHeight(main.height);
            FramebufferHandlers.setSavedWidth(main.width);

            resizeScreens(main.width, main.height);
        }
    }
    public static Framebuffer getDistortionMask() {
        if (DISTORTION_MASK == null) {
            Framebuffer main = Minecraft.getInstance().getMainRenderTarget();
            DISTORTION_MASK = new Framebuffer(main.width / 2, main.height / 2, true, Minecraft.ON_OSX);
            savedWidth = Minecraft.getInstance().getWindow().getWidth();
            savedHeight = Minecraft.getInstance().getWindow().getHeight();
        }

        return DISTORTION_MASK;
    }

    public static Framebuffer getBloomMask() {
        if (HORIZONTAL_BLOOM_MASK == null) {
            Framebuffer main = Minecraft.getInstance().getMainRenderTarget();
            HORIZONTAL_BLOOM_MASK = new Framebuffer(main.width / 2, main.height / 2, true, Minecraft.ON_OSX);
            savedWidth = Minecraft.getInstance().getWindow().getWidth();
            savedHeight = Minecraft.getInstance().getWindow().getHeight();

        }

        return HORIZONTAL_BLOOM_MASK;
    }

    public static void renderAccessoryInBuffer(Consumer<IRenderTypeBuffer> consumer) {
        Minecraft mc = Minecraft.getInstance();

// Save the framebuffer Minecraft was currently rendering into.
        Framebuffer previous = mc.getMainRenderTarget();

// Bind our framebuffer.
        getBloomMask().bindWrite(true);
        EgoWeaponsRenderSystem.toggleRenderColorOverrideState(RenderOverrideStates.BLOOM);
        getBloomMask().copyDepthFrom(previous);
//RenderSystem.clearColor(0, 0, 0, 0);
//RenderSystem.clear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, false);

// Set up rendering state...
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

// Render the entity.
        IRenderTypeBuffer.Impl buffer =
                IRenderTypeBuffer.immediate(new BufferBuilder(256));

        consumer.accept(buffer);

        buffer.endBatch();
        getBloomMask().unbindWrite();
        EgoWeaponsRenderSystem.clearRenderOverrideState();

// Restore Minecraft's framebuffer.
        previous.bindWrite(true);
    }

    public static void clearAccessoryBuffer() {
        Framebuffer previous = Minecraft.getInstance().getMainRenderTarget();
        getBloomMask().bindWrite(true);
        RenderSystem.clearColor(0, 0, 0, 0);
        RenderSystem.clear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, false);
        getBloomMask().unbindWrite();
        previous.bindWrite(true);
    }
}

package net.m3tte.ego_weapons.client.renderer;

import com.google.gson.JsonSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SetupShaderGroup extends ShaderGroup {

    private BiConsumer<ShaderData, ShaderGroup> preProcess = null;

    public SetupShaderGroup(TextureManager man, IResourceManager res, Framebuffer fbr, ResourceLocation resLoc, BiConsumer<ShaderData, ShaderGroup> consumerData) throws IOException, JsonSyntaxException {
        super(man, res, fbr, resLoc);

        this.preProcess = consumerData;
    }

    public SetupShaderGroup(ResourceLocation resLoc, BiConsumer<ShaderData, ShaderGroup> consumerData) throws IOException, JsonSyntaxException {
        super(Minecraft.getInstance().getTextureManager(), Minecraft.getInstance().getResourceManager(), Minecraft.getInstance().getMainRenderTarget(), resLoc);

        this.preProcess = consumerData;
    }

    public void runPreProcess(ShaderData data) {
        if (preProcess != null)
            preProcess.accept(data, this);
    }


    public static class ShaderData {
        public ShaderData(float gameTime, float deltaTime) {
            this.gameTime = gameTime;
            this.deltaTime = deltaTime;
        }

        private float gameTime = 0;
        private float deltaTime = 0;


        public float getDeltaTime() {
            return deltaTime;
        }

        public float getGameTime() {
            return gameTime;
        }

        public void setGameTime(float gameTime) {
            this.gameTime = gameTime;
        }
    }
}

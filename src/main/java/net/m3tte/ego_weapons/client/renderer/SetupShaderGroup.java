package net.m3tte.ego_weapons.client.renderer;

import com.google.gson.JsonSyntaxException;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SetupShaderGroup extends ShaderGroup {

    private BiConsumer<ShaderData, ShaderGroup> preProcess = null;
    private final List<DynamicFramebufferDef> dynamicFramebufferDefinitions = new LinkedList<>();
    public SetupShaderGroup(ResourceLocation resLoc, BiConsumer<ShaderData, ShaderGroup> consumerData) throws IOException, JsonSyntaxException {
        super(Minecraft.getInstance().getTextureManager(), Minecraft.getInstance().getResourceManager(), Minecraft.getInstance().getMainRenderTarget(), resLoc);

        this.preProcess = consumerData;
    }

    public SetupShaderGroup(ResourceLocation resLoc, BiConsumer<ShaderData, ShaderGroup> consumerData, DynamicFramebufferDef... defs) throws IOException, JsonSyntaxException {
        super(Minecraft.getInstance().getTextureManager(), Minecraft.getInstance().getResourceManager(), Minecraft.getInstance().getMainRenderTarget(), resLoc);

        Framebuffer mainRenderTarget = Minecraft.getInstance().getMainRenderTarget();

        for (DynamicFramebufferDef definition : defs) {
            this.dynamicFramebufferDefinitions.add(definition);

            Framebuffer relevantBuf = this.customRenderTargets.getOrDefault(definition.getName(), null);
            EgoWeaponsMod.LOGGER.log(Level.INFO, this+":\\\\OVERRIDING BUFFER WITH NAME : "+definition.getName());
            if (relevantBuf != null) {
                relevantBuf.resize((int) (mainRenderTarget.width / definition.getWidthDiv()), (int) (mainRenderTarget.height / definition.getHeightDiv()), true);
            } else {
                customRenderTargets.put(definition.getName(), new Framebuffer((int) (mainRenderTarget.width / definition.getWidthDiv()), (int) (mainRenderTarget.height / definition.getHeightDiv()), true, Minecraft.ON_OSX));
            }
        }


        this.preProcess = consumerData;
    }

    public void runPreProcess(ShaderData data) {

        if (preProcess != null)
            preProcess.accept(data, this);
    }

    @Override
    public void resize(int xs, int ys) {
        super.resize(xs, ys);

        Framebuffer mainRenderTarget = Minecraft.getInstance().getMainRenderTarget();

        for (DynamicFramebufferDef definition : this.dynamicFramebufferDefinitions) {
            Framebuffer relevantBuf = this.customRenderTargets.getOrDefault(definition.getName(), null);

            EgoWeaponsMod.LOGGER.log(Level.INFO, this+":\\\\ OVERRIDING RESIZE FOR BUFFER WITH NAME : "+definition.getName());
            if (relevantBuf != null) {
                relevantBuf.resize((int) (mainRenderTarget.width / definition.getWidthDiv()), (int) (mainRenderTarget.height / definition.getHeightDiv()), true);
            } else {
                customRenderTargets.put(definition.getName(), new Framebuffer((int) (mainRenderTarget.width / definition.getWidthDiv()), (int) (mainRenderTarget.height / definition.getHeightDiv()), true, Minecraft.ON_OSX));
            }
        }
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

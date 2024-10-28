package net.m3tte.tcorp.gameasset;

import net.m3tte.tcorp.TcorpMod;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.client.model.ClientModel;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.gameasset.Models;

public class TCorpModels<T extends Model> {

    public static final TCorpModels<?> LOGICAL_SERVER = new ServerModels();
    public T doubt;
    public T nothing_there;
    public T sunshower_fox;
    public void loadArmatures(IResourceManager resourceManager) {
        this.doubt.loadArmatureData(resourceManager);
        this.nothing_there.loadArmatureData(resourceManager);
        this.sunshower_fox.loadArmatureData(resourceManager);
    }

    public static class ServerModels extends TCorpModels<Model> {
        public ServerModels() {
            this.doubt = Models.LOGICAL_SERVER.register(new ResourceLocation(TcorpMod.MODID, "entity/doubt_a"));
            this.nothing_there = Models.LOGICAL_SERVER.register(new ResourceLocation(TcorpMod.MODID, "entity/nothing_there"));
            this.sunshower_fox = Models.LOGICAL_SERVER.register(new ResourceLocation(TcorpMod.MODID, "entity/sunshower_fox"));

        }
        public TCorpModels<?> getModels(boolean isLogicalClient) {
            return LOGICAL_SERVER;
        }
    }
}

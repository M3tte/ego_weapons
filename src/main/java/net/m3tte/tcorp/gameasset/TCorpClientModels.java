package net.m3tte.tcorp.gameasset;

import com.google.common.collect.Lists;
import net.m3tte.tcorp.TcorpMod;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.client.model.ClientModel;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.gameasset.Models;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@OnlyIn(Dist.CLIENT)
public class TCorpClientModels extends TCorpModels<ClientModel> {

    public static final TCorpClientModels LOGICAL_CLIENT = new TCorpClientModels();
    public TCorpClientModels() {
        this.doubt = ClientModels.LOGICAL_CLIENT.register(new ResourceLocation(TcorpMod.MODID, "entity/doubt_a"));
        this.nothing_there = ClientModels.LOGICAL_CLIENT.register(new ResourceLocation(TcorpMod.MODID, "entity/nothing_there"));
        this.sunshower_fox = ClientModels.LOGICAL_CLIENT.register(new ResourceLocation(TcorpMod.MODID, "entity/sunshower_fox"));
    }

    public TCorpModels<?> getModels(boolean isLogicalClient) {
        return isLogicalClient ? LOGICAL_CLIENT : LOGICAL_SERVER;
    }
}

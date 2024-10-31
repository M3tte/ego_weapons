package net.m3tte.ego_weapons.gameasset;

import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.gameasset.Models;

public class EgoWeaponsModels<T extends Model> {

    public static final EgoWeaponsModels<?> LOGICAL_SERVER = new ServerModels();
    public T doubt;
    public T nothing_there;
    public T sunshower_fox;
    public void loadArmatures(IResourceManager resourceManager) {
        this.doubt.loadArmatureData(resourceManager);
        this.nothing_there.loadArmatureData(resourceManager);
        this.sunshower_fox.loadArmatureData(resourceManager);
    }

    public static class ServerModels extends EgoWeaponsModels<Model> {
        public ServerModels() {
            this.doubt = Models.LOGICAL_SERVER.register(new ResourceLocation(EgoWeaponsMod.MODID, "entity/doubt_a"));
            this.nothing_there = Models.LOGICAL_SERVER.register(new ResourceLocation(EgoWeaponsMod.MODID, "entity/nothing_there"));
            this.sunshower_fox = Models.LOGICAL_SERVER.register(new ResourceLocation(EgoWeaponsMod.MODID, "entity/sunshower_fox"));

        }
        public EgoWeaponsModels<?> getModels(boolean isLogicalClient) {
            return LOGICAL_SERVER;
        }
    }
}

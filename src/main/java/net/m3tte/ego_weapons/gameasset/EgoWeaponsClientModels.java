package net.m3tte.ego_weapons.gameasset;

import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.client.model.ClientModel;
import yesman.epicfight.api.client.model.ClientModels;

@OnlyIn(Dist.CLIENT)
public class EgoWeaponsClientModels extends EgoWeaponsModels<ClientModel> {

    public static final EgoWeaponsClientModels LOGICAL_CLIENT = new EgoWeaponsClientModels();
    public EgoWeaponsClientModels() {
        this.doubt = ClientModels.LOGICAL_CLIENT.register(new ResourceLocation(EgoWeaponsMod.MODID, "entity/doubt_a"));
        this.nothing_there = ClientModels.LOGICAL_CLIENT.register(new ResourceLocation(EgoWeaponsMod.MODID, "entity/nothing_there"));
        this.sunshower_fox = ClientModels.LOGICAL_CLIENT.register(new ResourceLocation(EgoWeaponsMod.MODID, "entity/sunshower_fox"));
    }

    public EgoWeaponsModels<?> getModels(boolean isLogicalClient) {
        return isLogicalClient ? LOGICAL_CLIENT : LOGICAL_SERVER;
    }
}

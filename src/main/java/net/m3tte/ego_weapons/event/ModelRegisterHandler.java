package net.m3tte.ego_weapons.event;

import net.m3tte.ego_weapons.EgoWeaponsEntities;
import net.m3tte.ego_weapons.client.renderer.entities.AtelierpistolsRenderer;
import net.m3tte.ego_weapons.client.renderer.entities.AteliershotgunRenderer;
import net.m3tte.ego_weapons.client.renderer.entities.CrimsonfanprojRenderer;
import net.m3tte.ego_weapons.client.renderer.entities.MagicBulletRenderer;
import net.m3tte.ego_weapons.client.renderer.entities.mobs.DawnOfGreenDoubtRenderer;
import net.m3tte.ego_weapons.client.renderer.entities.mobs.NothingThereRenderer;
import net.m3tte.ego_weapons.client.renderer.entities.mobs.SunshowerFoxRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import static net.m3tte.ego_weapons.EgoWeaponsEntities.*;

public class ModelRegisterHandler {
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void registerModels(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EgoWeaponsEntities.ATELIER_PISTOL_BULLET.get(), AtelierpistolsRenderer.CustomRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EgoWeaponsEntities.ATELIER_SHOTGUN_BULLET.get(), AteliershotgunRenderer.CustomRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EgoWeaponsEntities.CRIMSON_WIND.get(), CrimsonfanprojRenderer.CustomRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EgoWeaponsEntities.MAGIC_BULLET.get(), MagicBulletRenderer.CustomRender::new);
        RenderingRegistry.registerEntityRenderingHandler(DAWN_OF_GREEN_DOUBT.get(), DawnOfGreenDoubtRenderer.GreenDoubtRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NOTHING_THERE.get(), NothingThereRenderer.NothingThereRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SUNSHOWER_FOX.get(), SunshowerFoxRenderer.SFoxRenderer::new);
    }


}

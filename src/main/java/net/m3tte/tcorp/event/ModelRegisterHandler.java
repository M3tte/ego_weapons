package net.m3tte.tcorp.event;

import net.m3tte.tcorp.entity.renderer.AtelierpistolsRenderer;
import net.m3tte.tcorp.entity.renderer.AteliershotgunRenderer;
import net.m3tte.tcorp.entity.renderer.CrimsonfanprojRenderer;
import net.m3tte.tcorp.entity.renderer.MagicBulletRenderer;
import net.m3tte.tcorp.item.CrimsonfanprojItem;
import net.m3tte.tcorp.item.blackSilence.AtelierpistolsItem;
import net.m3tte.tcorp.item.blackSilence.AteliershotgunItem;
import net.m3tte.tcorp.item.magic_bullet.MagicBulletProjectile;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ModelRegisterHandler {
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void registerModels(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(AtelierpistolsItem.pistol_bullet, renderManager -> new AtelierpistolsRenderer.CustomRender(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(AteliershotgunItem.shotgun_slug, renderManager -> new AteliershotgunRenderer.CustomRender(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(CrimsonfanprojItem.crimson_wind, renderManager -> new CrimsonfanprojRenderer.CustomRender(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(MagicBulletProjectile.bullet, renderManager -> new MagicBulletRenderer.CustomRender(renderManager));
    }
}

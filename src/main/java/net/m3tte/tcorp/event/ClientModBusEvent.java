package net.m3tte.tcorp.event;

import net.m3tte.tcorp.TCorpParticleRegistry;
import net.m3tte.tcorp.client.renderer.patched.item.RenderDurandal;
import net.m3tte.tcorp.client.renderer.patched.item.RenderMook;
import net.m3tte.tcorp.specialParticles.*;
import net.m3tte.tcorp.specialParticles.WheelsSmashParticle;
import net.m3tte.tcorp.specialParticles.hit.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(
        modid = "tcorp",
        value = {Dist.CLIENT},
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ClientModBusEvent {

    @SubscribeEvent(
            priority = EventPriority.LOWEST
    )
    public static void RenderRegistry(PatchedRenderersEvent.Add event) {
        event.addItemRenderer(ForgeRegistries.ITEMS.getValue(ResourceLocation.of("tcorp:durandal", ':')).getItem(), new RenderDurandal());
        event.addItemRenderer(ForgeRegistries.ITEMS.getValue(ResourceLocation.of("tcorp:mook_workshop", ':')).getItem(), new RenderMook());
    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onParticleRegistry(final ParticleFactoryRegisterEvent event) {
        Minecraft mc = Minecraft.getInstance();
        ParticleManager particleEngine = mc.particleEngine;
        particleEngine.register(TCorpParticleRegistry.DURANDAL_STRIKE.get(), DurandalStrikeParticle.Provider::new);
        particleEngine.register(TCorpParticleRegistry.DURANDAL_SLASH.get(), new DurandalHitEffect.Provider());
        particleEngine.register(TCorpParticleRegistry.RANGA_STRIKE.get(), RangaStrikeParticle.Provider::new);
        particleEngine.register(TCorpParticleRegistry.RANGA_FURIOSO_SLASH.get(), new RangaHitEffect.Provider());
        particleEngine.register(TCorpParticleRegistry.ATELIER_PISTOL_IMPACT.get(), AtelierPistolImpact.Provider::new);
        particleEngine.register(TCorpParticleRegistry.ATELIER_PISTOL_HIT.get(), new AtelierPistolHitEffect.Provider());
        particleEngine.register(TCorpParticleRegistry.ATELIER_SHOTGUN_IMPACT.get(), AtelierShotgunImpact.Provider::new);
        particleEngine.register(TCorpParticleRegistry.ATELIER_SHOTGUN_HIT.get(), new AtelierShotgunHitEffect.Provider());
        particleEngine.register(TCorpParticleRegistry.ZELKOVA_STRIKE.get(), ZelkovaStrikeParticle.Provider::new);
        particleEngine.register(TCorpParticleRegistry.ZELKOVA_HIT.get(), new ZelkovaHitEffect.Provider());
        particleEngine.register(TCorpParticleRegistry.CRYSTAL_ATELIER_STRIKE.get(), CrystalAtelierStrikeParticle.Provider::new);
        particleEngine.register(TCorpParticleRegistry.CRYSTAL_ATELIER_HIT.get(), new CrystalAtelierHitEffect.Provider());
        particleEngine.register(TCorpParticleRegistry.ALLAS_STRIKE.get(), AllasStrikeParticle.Provider::new);
        particleEngine.register(TCorpParticleRegistry.ALLAS_HIT.get(), new AllasHitEffect.Provider());
        particleEngine.register(TCorpParticleRegistry.BLEED.get(), BleedParticle.Provider::new);
        particleEngine.register(TCorpParticleRegistry.MOOK_SLASH.get(), MookSlashParticle.Provider::new);
        particleEngine.register(TCorpParticleRegistry.MOOK_JUDGEMENT_CUT.get(), new MookJudgementCutEffect.Provider());
        particleEngine.register(TCorpParticleRegistry.WHEELS_SMASH.get(), WheelsSmashParticle.Provider::new);
        particleEngine.register(TCorpParticleRegistry.WHEELS_IMPACT.get(), new WheelsImpactParticle.Provider());
        particleEngine.register(TCorpParticleRegistry.MIMICRY_STRIKE.get(), MimicryStrikeParticle.Provider::new);
        particleEngine.register(TCorpParticleRegistry.MIMICRY_HIT.get(), new MimicryHitEffect.Provider());
        particleEngine.register(TCorpParticleRegistry.MIMICRY_HORIZONTAL_STRIKE.get(), MimicryHorizontalStrikeParticle.Provider::new);
        particleEngine.register(TCorpParticleRegistry.MIMICRY_HORIZONTAL_HIT.get(), new MimicryHorizontalHit.Provider());
        particleEngine.register(TCorpParticleRegistry.MIMICRY_DASH_STRIKE.get(), MimicryDashStrike.Provider::new);
        particleEngine.register(TCorpParticleRegistry.MIMICRY_DASH_HIT.get(), new MimicryDashHit.Provider());
        particleEngine.register(TCorpParticleRegistry.MIMICRY_VERTICAL_STRIKE.get(), MimicryVerticalStrikeParticle.Provider::new);
        particleEngine.register(TCorpParticleRegistry.MIMICRY_VERTICAL_HIT.get(), new MimicryVerticalSplitHit.Provider());
        particleEngine.register(TCorpParticleRegistry.STAGGER.get(), StaggerParticle.Provider::new);
        particleEngine.register(TCorpParticleRegistry.MAGIC_BULLET_STRIKE.get(), MagicBulletStrike.Provider::new);
        particleEngine.register(TCorpParticleRegistry.MAGIC_BULLET_HIT.get(), new MagicBulletHit.Provider());
        particleEngine.register(TCorpParticleRegistry.MAGIC_BULLET_IMPACT.get(), MagicBulletImpact.Provider::new);
        particleEngine.register(TCorpParticleRegistry.MAGIC_BULLET_IMPACT_HIT.get(), new MagicBulletImpactHit.Provider());
        particleEngine.register(TCorpParticleRegistry.SOLEMN_LAMENT_FIRE_LIVING.get(), SolemnLamentParticle.Provider::new);
        particleEngine.register(TCorpParticleRegistry.SOLEMN_LAMENT_FIRE_DEPARTED.get(), SolemnLamentParticle.Provider::new);
        particleEngine.register(TCorpParticleRegistry.SOLEMN_LAMENT_LIVING_HIT.get(), new SolemnLamentLivingHit.Provider());
        particleEngine.register(TCorpParticleRegistry.SOLEMN_LAMENT_DEPARTED_HIT.get(), new SolemnLamentDepartedHit.Provider());
        particleEngine.register(TCorpParticleRegistry.SOLEMN_LAMENT_DEPARTED_BUTTERFLY.get(), SolemnLamentButterfly.Provider::new);
        particleEngine.register(TCorpParticleRegistry.SOLEMN_LAMENT_LIVING_BUTTERFLY.get(), SolemnLamentButterfly.Provider::new);
        particleEngine.register(TCorpParticleRegistry.SOLEMN_LAMENT_BURST_HIT.get(), new SolemnLamentBurstHit.Provider());
    }

}

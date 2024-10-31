package net.m3tte.ego_weapons.event;

import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.client.renderer.patched.item.RenderDurandal;
import net.m3tte.ego_weapons.client.renderer.patched.item.RenderMook;
import net.m3tte.ego_weapons.specialParticles.*;
import net.m3tte.ego_weapons.specialParticles.WheelsSmashParticle;
import net.m3tte.ego_weapons.specialParticles.hit.*;
import net.m3tte.ego_weapons.specialParticles.modelParticles.*;
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
        modid = "ego_weapons",
        value = {Dist.CLIENT},
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ClientModBusEvent {

    @SubscribeEvent(
            priority = EventPriority.LOWEST
    )
    public static void RenderRegistry(PatchedRenderersEvent.Add event) {
        event.addItemRenderer(ForgeRegistries.ITEMS.getValue(ResourceLocation.of("ego_weapons:durandal", ':')).getItem(), new RenderDurandal());
        event.addItemRenderer(ForgeRegistries.ITEMS.getValue(ResourceLocation.of("ego_weapons:mook_workshop", ':')).getItem(), new RenderMook());
    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onParticleRegistry(final ParticleFactoryRegisterEvent event) {
        Minecraft mc = Minecraft.getInstance();
        ParticleManager particleEngine = mc.particleEngine;
        particleEngine.register(EgoWeaponsParticles.DURANDAL_STRIKE.get(), DurandalStrikeParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.DURANDAL_SLASH.get(), new DurandalHitEffect.Provider());
        particleEngine.register(EgoWeaponsParticles.RANGA_STRIKE.get(), RangaStrikeParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.RANGA_FURIOSO_SLASH.get(), new RangaHitEffect.Provider());
        particleEngine.register(EgoWeaponsParticles.ATELIER_PISTOL_IMPACT.get(), AtelierPistolImpact.Provider::new);
        particleEngine.register(EgoWeaponsParticles.ATELIER_PISTOL_HIT.get(), new AtelierPistolHitEffect.Provider());
        particleEngine.register(EgoWeaponsParticles.ATELIER_SHOTGUN_IMPACT.get(), AtelierShotgunImpact.Provider::new);
        particleEngine.register(EgoWeaponsParticles.ATELIER_SHOTGUN_HIT.get(), new AtelierShotgunHitEffect.Provider());
        particleEngine.register(EgoWeaponsParticles.ZELKOVA_STRIKE.get(), ZelkovaStrikeParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.ZELKOVA_HIT.get(), new ZelkovaHitEffect.Provider());
        particleEngine.register(EgoWeaponsParticles.CRYSTAL_ATELIER_STRIKE.get(), CrystalAtelierStrikeParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.CRYSTAL_ATELIER_HIT.get(), new CrystalAtelierHitEffect.Provider());
        particleEngine.register(EgoWeaponsParticles.ALLAS_STRIKE.get(), AllasStrikeParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.ALLAS_HIT.get(), new AllasHitEffect.Provider());
        particleEngine.register(EgoWeaponsParticles.BLEED.get(), BleedParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.MOOK_SLASH.get(), MookSlashParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.MOOK_JUDGEMENT_CUT.get(), new MookJudgementCutEffect.Provider());
        particleEngine.register(EgoWeaponsParticles.WHEELS_SMASH.get(), WheelsSmashParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.WHEELS_IMPACT.get(), new WheelsImpactParticle.Provider());
        particleEngine.register(EgoWeaponsParticles.MIMICRY_STRIKE.get(), MimicryStrikeParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.MIMICRY_HIT.get(), new MimicryHitEffect.Provider());
        particleEngine.register(EgoWeaponsParticles.MIMICRY_HORIZONTAL_STRIKE.get(), MimicryHorizontalStrikeParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.MIMICRY_HORIZONTAL_HIT.get(), new MimicryHorizontalHit.Provider());
        particleEngine.register(EgoWeaponsParticles.MIMICRY_DASH_STRIKE.get(), MimicryDashStrike.Provider::new);
        particleEngine.register(EgoWeaponsParticles.MIMICRY_DASH_HIT.get(), new MimicryDashHit.Provider());
        particleEngine.register(EgoWeaponsParticles.MIMICRY_VERTICAL_STRIKE.get(), MimicryVerticalStrikeParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.MIMICRY_VERTICAL_HIT.get(), new MimicryVerticalSplitHit.Provider());
        particleEngine.register(EgoWeaponsParticles.STAGGER.get(), StaggerParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.MAGIC_BULLET_STRIKE.get(), MagicBulletStrike.Provider::new);
        particleEngine.register(EgoWeaponsParticles.MAGIC_BULLET_HIT.get(), new MagicBulletHit.Provider());
        particleEngine.register(EgoWeaponsParticles.MAGIC_BULLET_IMPACT.get(), MagicBulletImpact.Provider::new);
        particleEngine.register(EgoWeaponsParticles.MAGIC_BULLET_IMPACT_HIT.get(), new MagicBulletImpactHit.Provider());
        particleEngine.register(EgoWeaponsParticles.SOLEMN_LAMENT_FIRE_LIVING.get(), SolemnLamentParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.SOLEMN_LAMENT_FIRE_DEPARTED.get(), SolemnLamentParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_HIT.get(), new SolemnLamentLivingHit.Provider());
        particleEngine.register(EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_HIT.get(), new SolemnLamentDepartedHit.Provider());
        particleEngine.register(EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_BUTTERFLY.get(), SolemnLamentButterfly.Provider::new);
        particleEngine.register(EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_BUTTERFLY.get(), SolemnLamentButterfly.Provider::new);
        particleEngine.register(EgoWeaponsParticles.SOLEMN_LAMENT_BURST_HIT.get(), new SolemnLamentBurstHit.Provider());
        particleEngine.register(EgoWeaponsParticles.DOUBT_GROUNDSLAM_STRIKE.get(), DoubtGroundslamParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.DOUBT_GROUNDSLAM_HIT.get(), new DoubtGroundslamHit.Provider());
        particleEngine.register(EgoWeaponsParticles.DOUBT_EXPLOSION_EFFECT.get(), DoubtExplosionParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.DOUBT_EXPLODE.get(), new DoubtDeathExplosion.Provider());
        particleEngine.register(EgoWeaponsParticles.MEAT_CHUNK_EXPLOSION.get(), new MeatExplosion.Provider());
        particleEngine.register(EgoWeaponsParticles.SUNSHOWER_AUTO1_STRIKE.get(), SunshowerStrike.Provider::new);
        particleEngine.register(EgoWeaponsParticles.SUNSHOWER_AUTO1_HIT.get(), new Sunshower1Hit.Provider());
        particleEngine.register(EgoWeaponsParticles.SUNSHOWER_OPEN.get(), SunshowerOpenUmbrella.Provider::new);
        particleEngine.register(EgoWeaponsParticles.SUNSHOWER_AUTO3_STRIKE.get(), SunshowerStrike.Provider::new);
        particleEngine.register(EgoWeaponsParticles.SUNSHOWER_AUTO3_HIT.get(), new Sunshower3Hit.Provider());
        particleEngine.register(EgoWeaponsParticles.PUDDLE_STOMP_IMPACT.get(), PuddleStompSplashParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.PUDDLE_STOMP_RIPPLE.get(), PuddleStompRipple.Provider::new);
        particleEngine.register(EgoWeaponsParticles.SUNSHOWER_DRIFT.get(), RotationBoundParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.SUNSHOWER_DRIFT_B.get(), SunshowerDashB.Provider::new);
        particleEngine.register(EgoWeaponsParticles.GREAT_SPLIT_HORIZONTAL.get(), GreatSplitHorizontal.Provider::new);
        particleEngine.register(EgoWeaponsParticles.BURN_APPLY.get(), BurnEffectParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.DARK_BURN_APPLY.get(), BurnEffectParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.SINKING_APPLY.get(), SinkingParticle.Provider::new);
    }

}

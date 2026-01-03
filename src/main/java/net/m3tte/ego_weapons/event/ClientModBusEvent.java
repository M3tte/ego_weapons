package net.m3tte.ego_weapons.event;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.client.renderer.patched.item.*;
import net.m3tte.ego_weapons.specialParticles.*;
import net.m3tte.ego_weapons.specialParticles.WheelsSmashParticle;
import net.m3tte.ego_weapons.specialParticles.hit.*;
import net.m3tte.ego_weapons.specialParticles.modelParticles.*;
import net.m3tte.ego_weapons.specialParticles.numberParticle.ClashLabelParticle;
import net.m3tte.ego_weapons.specialParticles.numberParticle.DamageNumberParticle;
import net.m3tte.ego_weapons.specialParticles.numberParticle.NumberParticle;
import net.m3tte.ego_weapons.specialParticles.texturedAfterImage.TexturedAfterImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
        ItemModelsProperties.register(EgoWeaponsItems.DURANDAL_SHEATH.get(), new ResourceLocation("unsheathed"), (itemStack, clientWorld, livingEntity) -> (itemStack.hasTag() ? (itemStack.getOrCreateTag().getInt("unsheathed")) : 0));
        ItemModelsProperties.register(EgoWeaponsItems.MOOK_SHEATH.get(), new ResourceLocation("unsheathed"), (itemStack, clientWorld, livingEntity) -> (itemStack.hasTag() ? (itemStack.getOrCreateTag().getInt("unsheathed")) : 0));
        ItemModelsProperties.register(EgoWeaponsItems.RANGA_CLAW.get(), new ResourceLocation("dagger"), (itemStack, clientWorld, livingEntity) -> itemStack.getOrCreateTag().getBoolean("dagger") ? 1 : 0);
        ItemModelsProperties.register(EgoWeaponsItems.SUNSHOWER.get(), new ResourceLocation("open"), (itemStack, clientWorld, livingEntity) -> itemStack.hasTag() ? (itemStack.getTag().getInt("open")) : 0);
        ItemModelsProperties.register(EgoWeaponsItems.FULLSTOP_SNIPER_RAILGUN.get(), new ResourceLocation("charge"), (itemStack, clientWorld, livingEntity) -> itemStack.hasTag() ? (Math.min((itemStack.getTag().getInt("charge") / 3), 7)) : 0);
        ItemModelsProperties.register(EgoWeaponsItems.FULLSTOP_SNIPER_SUITCASE.get(), new ResourceLocation("dropped"), (itemStack, clientWorld, livingEntity) -> (itemStack.hasTag() ? Math.min((itemStack.getOrCreateTag().getInt("dropped")),1) : 0));
        ItemModelsProperties.register(EgoWeaponsItems.STIGMA_WORKSHOP_SWORD.get(), new ResourceLocation("glow"), (itemStack, clientWorld, livingEntity) -> (itemStack.hasTag() ? Math.min((itemStack.getOrCreateTag().getInt("glow")),1) : 0));
        ItemModelsProperties.register(EgoWeaponsItems.HEISHOU_MAO_SWORD.get(), new ResourceLocation("active"), (itemStack, clientWorld, livingEntity) -> (itemStack.hasTag() ? Math.min((itemStack.getOrCreateTag().getInt("active")),1) : 0));
        ItemModelsProperties.register(EgoWeaponsItems.MIMICRY.get(), new ResourceLocation("morph"), (itemStack, clientWorld, livingEntity) -> (itemStack.hasTag() ? Math.min((itemStack.getOrCreateTag().getInt("morph")),1) : 0));


        ItemModelsProperties.register(EgoWeaponsItems.FIREFIST_GAUNTLET.get(), new ResourceLocation("ignition"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null)
                return 0;

            int fuel = livingEntity.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.FIREFIST_SUIT.get()) ? livingEntity.getItemBySlot(EquipmentSlotType.CHEST).getOrCreateTag().getInt("d10fuel") : 0;



            if (itemStack.hasTag()) {
                int ign = itemStack.getOrCreateTag().getInt("ignition");
                if (ign > 0)
                    return ign;
            }

            if (fuel > 0 && livingEntity.hasEffect(EgoWeaponsEffects.FUEL_IGNITION.get()))
                return 2;

            return fuel > 50 || fuel <= 10 ? 0 : 2;
        });


        //event.addItemRenderer(ForgeRegistries.ITEMS.getValue(ResourceLocation.of("ego_weapons:durandal", ':')).getItem(), new RenderDurandal());
        //event.addItemRenderer(ForgeRegistries.ITEMS.getValue(ResourceLocation.of("ego_weapons:mook_workshop", ':')).getItem(), new RenderMook());
        //event.addItemRenderer(ForgeRegistries.ITEMS.getValue(ResourceLocation.of("ego_weapons:oeufi_contract", ':')).getItem(), new RenderOeufiContract());
        //event.addItemRenderer(ForgeRegistries.ITEMS.getValue(ResourceLocation.of("ego_weapons:fullstop_atelier_pistol", ':')).getItem(), new RenderFullstopAtelierLogic());
        //event.addItemRenderer(ForgeRegistries.ITEMS.getValue(ResourceLocation.of("ego_weapons:fullstop_office_railgun", ':')).getItem(), new RenderFullstopRailgun());
        event.addItemRenderer(EgoWeaponsItems.DURANDAL.get(), new RenderDurandal());
        event.addItemRenderer(EgoWeaponsItems.MOOK_WORKSHOP.get(), new RenderMook());
        event.addItemRenderer(EgoWeaponsItems.OEUFI_CONTRACT.get(), new RenderOeufiContract());
        event.addItemRenderer(EgoWeaponsItems.FULLSTOP_REP_PISTOL.get(), new RenderFullstopAtelierLogic());
        event.addItemRenderer(EgoWeaponsItems.FULLSTOP_SNIPER_RAILGUN.get(), new RenderFullstopRailgun());
        event.addItemRenderer(EgoWeaponsItems.STIGMA_WORKSHOP_SWORD.get(), new RenderStigmaWorkshopSword());



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
        particleEngine.register(EgoWeaponsParticles.TAKE_AIM.get(), AimTakeParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.MAGIC_BULLET_STRIKE.get(), (a) -> new GenericStrike.Provider(a, 4, 2f));
        particleEngine.register(EgoWeaponsParticles.MAGIC_BULLET_HIT.get(), new GenericHit.Provider(EgoWeaponsParticles.MAGIC_BULLET_STRIKE.get()));
        particleEngine.register(EgoWeaponsParticles.MAGIC_BULLET_IMPACT.get(), (a) -> new GenericStrike.Provider(a, 4, 2f));
        particleEngine.register(EgoWeaponsParticles.MAGIC_BULLET_IMPACT_HIT.get(), new GenericHit.Provider(EgoWeaponsParticles.MAGIC_BULLET_IMPACT.get()));
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
        particleEngine.register(EgoWeaponsParticles.SUNSHOWER_AUTO1_STRIKE.get(), (a) -> new GenericStrike.Provider(a, 6, 2.5f));
        particleEngine.register(EgoWeaponsParticles.SUNSHOWER_AUTO1_HIT.get(), new GenericHit.Provider(EgoWeaponsParticles.SUNSHOWER_AUTO1_STRIKE.get()));
        particleEngine.register(EgoWeaponsParticles.SUNSHOWER_OPEN.get(), SunshowerOpenUmbrella.Provider::new);
        particleEngine.register(EgoWeaponsParticles.SUNSHOWER_AUTO3_STRIKE.get(), (a) -> new GenericStrike.Provider(a, 6, 2.5f));
        particleEngine.register(EgoWeaponsParticles.SUNSHOWER_AUTO3_HIT.get(), new GenericHit.Provider(EgoWeaponsParticles.SUNSHOWER_AUTO3_STRIKE.get()));
        particleEngine.register(EgoWeaponsParticles.PUDDLE_STOMP_IMPACT.get(), PuddleStompSplashParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.PUDDLE_STOMP_RIPPLE.get(), PuddleStompRipple.Provider::new);
        particleEngine.register(EgoWeaponsParticles.SUNSHOWER_DRIFT.get(), RotationBoundParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.SUNSHOWER_DRIFT_B.get(), SunshowerDashB.Provider::new);
        particleEngine.register(EgoWeaponsParticles.GREAT_SPLIT_HORIZONTAL.get(), GreatSplitHorizontal.Provider::new);
        particleEngine.register(EgoWeaponsParticles.BURN_APPLY.get(), BurnEffectParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.DARK_BURN_APPLY.get(), BurnEffectParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.SINKING_APPLY.get(), SinkingParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.DURANDAL_SWIPE_DOWN.get(), (a) -> new SlashDownInvert.Provider(a, 2.5f, 5, new Vector3f(0.3f,1f,0)));
        particleEngine.register(EgoWeaponsParticles.DURANDAL_SWIPE_UP.get(), (a) -> new SlashDown.Provider(a, 2.5f, 5, new Vector3f(0.3f,1f,0), false));
        particleEngine.register(EgoWeaponsParticles.DURANDAL_SWIPE_HORIZONTAL.get(), DurandalSlashHorizontal.Provider::new);
        particleEngine.register(EgoWeaponsParticles.EFFECT_NUMBER.get(), NumberParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.DAMAGE_NUMBER.get(), DamageNumberParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.CLASH_NUMBER.get(), ClashLabelParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.OUFI_AUTO_STRIKE.get(), (a) -> new GenericStrike.Provider(a, 6, 2.5f));
        particleEngine.register(EgoWeaponsParticles.OUFI_AUTO_HIT.get(), new GenericHit.Provider(EgoWeaponsParticles.OUFI_AUTO_STRIKE.get()));
        particleEngine.register(EgoWeaponsParticles.OUFI_DASH_STRIKE.get(), (a) -> new GenericStrike.Provider(a, 6, 2.5f));
        particleEngine.register(EgoWeaponsParticles.OUFI_DASH_HIT.get(), new GenericHit.Provider(EgoWeaponsParticles.OUFI_DASH_STRIKE.get()));
        particleEngine.register(EgoWeaponsParticles.OUFI_SWIPE_DOWN.get(), (a) -> new SlashDown.Provider(a, 3, 10, new Vector3f(-1f,0.5f,0), new Vector3f(0.1f,0,0)));
        particleEngine.register(EgoWeaponsParticles.OUFI_PIERCE.get(), (a) -> new PierceAttack.Provider(a, 4, 10, new Vector3f(1.5f,1.5f,0), new Vector3f(0.3f,0,0), false));
        particleEngine.register(EgoWeaponsParticles.BASIC_BULLET_IMPACT.get(), (a) -> new GenericStrike.Provider(a, 4, 1.2f));
        particleEngine.register(EgoWeaponsParticles.BASIC_BULLET_FIRE.get(), (a) -> new GenericStrike.Provider(a, 4, 0.25f));
        particleEngine.register(EgoWeaponsParticles.BASIC_BULLET_FIRE_SIDE.get(), (a) -> new PierceAttack.Provider(a, 2, 3, new Vector3f(1.5f,0f,0), new Vector3f(0.1f,0,0), true));
        particleEngine.register(EgoWeaponsParticles.INCENDIARY_BULLET_IMPACT.get(), (a) -> new GenericStrike.Provider(a, 4, 1.2f));
        particleEngine.register(EgoWeaponsParticles.INCENDIARY_BULLET_FIRE.get(), (a) -> new GenericStrike.Provider(a, 4, 0.25f));
        particleEngine.register(EgoWeaponsParticles.INCENDIARY_BULLET_FIRE_SIDE.get(), (a) -> new PierceAttack.Provider(a, 2, 3, new Vector3f(1.5f,0f,0), new Vector3f(0.1f,0,0), true));
        particleEngine.register(EgoWeaponsParticles.MOONSTONE_BULLET_IMPACT.get(), (a) -> new GenericStrike.Provider(a, 4, 1.2f));
        particleEngine.register(EgoWeaponsParticles.MOONSTONE_BULLET_FIRE.get(), (a) -> new GenericStrike.Provider(a, 4, 0.25f));
        particleEngine.register(EgoWeaponsParticles.MOONSTONE_BULLET_FIRE_SIDE.get(), (a) -> new PierceAttack.Provider(a, 2, 3, new Vector3f(1.5f,0f,0), new Vector3f(0.1f,0,0), true));
        particleEngine.register(EgoWeaponsParticles.RIFLE_BASIC_BULLET_FIRE_SIDE.get(), (a) -> new PierceAttack.Provider(a, 4, 5, new Vector3f(3.5f,0,0f), new Vector3f(0.1f,0,0), true));
        particleEngine.register(EgoWeaponsParticles.RIFLE_SHOCKWAVE.get(), (a) -> new RotationBoundParticle.Provider(a, 2.5f, 8, new Vector3f(0f,0f,0), new Vector3f(0f,0,0.1f), false));
        particleEngine.register(EgoWeaponsParticles.RIFLE_ALHV_BULLET_FIRE_SIDE.get(), (a) -> new PierceAttack.Provider(a, 4, 5, new Vector3f(3.5f,0,0f), new Vector3f(0.1f,0,0), true));
        particleEngine.register(EgoWeaponsParticles.RIFLE_ALHV_SHOCKWAVE.get(), (a) -> new RotationBoundParticle.Provider(a, 2.5f, 8, new Vector3f(0f,0f,0), new Vector3f(0f,0,0.1f), true));
        particleEngine.register(EgoWeaponsParticles.FULLSTOP_MACHETE_STRIKE.get(), (a) -> new GenericStrike.Provider(a, 6, 2.5f));
        particleEngine.register(EgoWeaponsParticles.CRIT.get(), (a) -> new GenericStrike.Provider(a, 6, 2f));
        particleEngine.register(EgoWeaponsParticles.FULLSTOP_MACHETE_HIT.get(), new GenericHit.Provider(EgoWeaponsParticles.FULLSTOP_MACHETE_STRIKE.get()));
        particleEngine.register(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), new TexturedAfterImage.Provider());
        particleEngine.register(EgoWeaponsParticles.MAGIC_BULLET_FIRE_SIDE.get(), (a) -> new PierceAttack.Provider(a, 4, 5, new Vector3f(3.5f,0,0f), new Vector3f(0.1f,0,0), true));
        particleEngine.register(EgoWeaponsParticles.MAGIC_BULLET_SHOCKWAVE.get(), (a) -> new RotationBoundParticle.Provider(a, 2.5f, 8, new Vector3f(0f,0f,0), new Vector3f(0f,0,0.1f), true));
        particleEngine.register(EgoWeaponsParticles.MAGIC_BULLET_FIRE.get(), (a) -> new GenericStrike.Provider(a, 4, 0.35f));
        particleEngine.register(EgoWeaponsParticles.LIU_S6_AUTO_STRIKE.get(), (a) -> new GenericStrike.Provider(a, 6, 1.1f));
        particleEngine.register(EgoWeaponsParticles.LIU_S6_AUTO_HIT.get(), new LiuHit.Provider(EgoWeaponsParticles.LIU_S6_AUTO_STRIKE.get()));
        particleEngine.register(EgoWeaponsParticles.LIU_S6_AUTO_SIDE.get(), (a) -> new PierceAttack.Provider(a, 1.8f, 10, new Vector3f(0.8f,1.5f,0), new Vector3f(0.005f,0,0), true));
        particleEngine.register(EgoWeaponsParticles.LIU_PUNCH_SHOCKWAVE.get(), (a) -> new RotationBoundParticle.Provider(a, 2.0f, 8, new Vector3f(0f,1.5f,0), new Vector3f(0.0f,0,-0.1f), true));
        particleEngine.register(EgoWeaponsParticles.FIREFIST_STRIKE.get(), (a) -> new GenericStrike.Provider(a, 6, 2.2f));
        particleEngine.register(EgoWeaponsParticles.FIREFIST_HIT.get(), new LiuHit.Provider(EgoWeaponsParticles.FIREFIST_STRIKE.get()));
        particleEngine.register(EgoWeaponsParticles.FIREFIST_FLAME_SPEW.get(), (a) -> new PierceAttack.Provider(a, 2.4f, 15, new Vector3f(1.8f,0.5f,0f), new Vector3f(0.05f,0,0), true));

        particleEngine.register(EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_STRIKE.get(), (a) -> new GenericStrike.Provider(a, 6, 1.8f));
        particleEngine.register(EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_HIT.get(), new LiuHit.Provider(EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_STRIKE.get()));
        particleEngine.register(EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_DASH_STRIKE.get(), (a) -> new GenericStrike.Provider(a, 8, 2f));
        particleEngine.register(EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_DASH_HIT.get(), new LiuHit.Provider(EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_DASH_STRIKE.get()));
        particleEngine.register(EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_IGNITE.get(), (a) -> new GenericStrike.Provider(a, 10, 0.7f));
        particleEngine.register(EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_IGNITE_SIDE.get(), (a) -> new PierceAttack.Provider(a, 1f, 11, new Vector3f(-0.4f,0f,0), new Vector3f(-0.01f,0,0), true));
        particleEngine.register(EgoWeaponsParticles.SIMPLE_EMBER.get(), EmberParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.MAO_PARTICLE.get(), MaoParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.STIGMA_WORKSHOP_SLASH_DOWN.get(), (a) -> new SlashDownInvert.Provider(a, 2, 7, new Vector3f(0.3f,1f,0), true));
        particleEngine.register(EgoWeaponsParticles.STIGMA_WORKSHOP_SLASH_UP.get(), (a) -> new SlashDown.Provider(a, 2, 7, new Vector3f(0.3f,1f,0), true));
        particleEngine.register(EgoWeaponsParticles.MAO_BRANCH_STRIKE.get(), (a) -> new GenericStrike.Provider(a, 7, 2f));
        particleEngine.register(EgoWeaponsParticles.MAO_BRANCH_HIT.get(), new LiuHit.Provider(EgoWeaponsParticles.MAO_BRANCH_STRIKE.get()));
        particleEngine.register(EgoWeaponsParticles.JUSTITIA_STRIKE.get(), (a) -> new GenericStrike.Provider(a, 7, 2f));
        particleEngine.register(EgoWeaponsParticles.JUSTITIA_HIT.get(), new LiuHit.Provider(EgoWeaponsParticles.JUSTITIA_STRIKE.get()));
        particleEngine.register(EgoWeaponsParticles.JUSTITIA_SCALE_STRIKE.get(), (a) -> new GenericStrike.Provider(a, 7, 2.4f));
        particleEngine.register(EgoWeaponsParticles.JUSTITIA_SCALE_HIT.get(), new LiuHit.Provider(EgoWeaponsParticles.JUSTITIA_SCALE_STRIKE.get()));

        particleEngine.register(EgoWeaponsParticles.JUSTITIA_SCALE.get(), (a) -> new RotationBoundParticle.Provider(a, 2.3f, 55, new Vector3f(0f,4f,0), new Vector3f(0f,0,0f), true));
        particleEngine.register(EgoWeaponsParticles.JUSTITIA_PARTICLE.get(), JustitiaScaleParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.JUSTITIA_PARTICLE_SCALE.get(), JustitiaScaleParticle.Provider::new);

        particleEngine.register(EgoWeaponsParticles.MAO_BRANCH_CIRCULAR_HIT.get(), (a) -> new CircularSlashing.Provider(a, 2.2f, 12, new Vector3f(0f,0f,0), true));
        particleEngine.register(EgoWeaponsParticles.MAO_BRANCH_REUSE.get(), DeathriteReuseParticle.Provider::new);
        particleEngine.register(EgoWeaponsParticles.JUSTITIA_PIECE.get(), JustitiaPieceParticle.Provider::new);

        particleEngine.register(EgoWeaponsParticles.JUSTITIA_REUSE_STRIKE.get(), (a) -> new GenericStrike.Provider(a, 5, 2.5f));
        particleEngine.register(EgoWeaponsParticles.HORIZONTAL_SHOCKWAVE.get(), AirShockwaveEffect.Provider::new);
        particleEngine.register(EgoWeaponsParticles.SLASH_SHOCKWAVE.get(), SlashShockwaveEffect.Provider::new);
        particleEngine.register(EgoWeaponsParticles.SOLEMN_LAMENT_SHOCKWAVE.get(), SolemnLamentShockwave.Provider::new);
        particleEngine.register(EgoWeaponsParticles.MAGIC_BULLET_CIRCLE_SHORT.get(), (a) -> new MagicBulletCircleParticle.Provider(a, 40));
        particleEngine.register(EgoWeaponsParticles.MAGIC_BULLET_CIRCLE_LONG.get(), (a) -> new MagicBulletCircleParticle.Provider(a, 60));
        particleEngine.register(EgoWeaponsParticles.TARGET_MAGIC_BULLET_CIRCLE_SHORT.get(), (a) -> new MagicBulletTargetCircle.Provider(a, 40));
        particleEngine.register(EgoWeaponsParticles.TARGET_MAGIC_BULLET_CIRCLE_LONG.get(), (a) -> new MagicBulletTargetCircle.Provider(a, 60));
        particleEngine.register(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), ExpendLightParticle.Provider::new);

    }

}

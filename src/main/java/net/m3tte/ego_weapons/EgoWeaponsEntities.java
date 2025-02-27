package net.m3tte.ego_weapons;

import net.m3tte.ego_weapons.entities.DawnOfGreenDoubtEntity;
import net.m3tte.ego_weapons.entities.AtelierPistolsBullet.AtelierPistolBulletProj;
import net.m3tte.ego_weapons.entities.AtelierShotgunBullet.AtelierShotgunSlugProj;
import net.m3tte.ego_weapons.entities.MagicBulletProjectile.MagicBulletProj;
import net.m3tte.ego_weapons.entities.NothingThere2Entity;
import net.m3tte.ego_weapons.entities.SunshowerFoxEntity;
import net.m3tte.ego_weapons.entities.SunshowerUmbrellaEntity;
import net.m3tte.ego_weapons.world.capabilities.entitypatch.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;
import yesman.epicfight.api.forgeevent.EntityPatchRegistryEvent;

import static net.m3tte.ego_weapons.item.CrimsonfanprojItem.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EgoWeaponsEntities {

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryName, EntityType.Builder<T> entity) {
        return ENTITIES.register(registryName, () -> entity.build(registryName));
    }



    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, EgoWeaponsMod.MODID);
    public static final RegistryObject<EntityType<MagicBulletProj>> MAGIC_BULLET = register("projectile_magic_bullet", (EntityType.Builder.<MagicBulletProj>of(MagicBulletProj::new, EntityClassification.MISC)
            .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).setCustomClientFactory(MagicBulletProj::new)
            .sized(0.5f, 0.5f)));
    public static final RegistryObject<EntityType<AtelierPistolBulletProj>> ATELIER_PISTOL_BULLET = register("projectile_atelier_pistol", (EntityType.Builder.<AtelierPistolBulletProj>of(AtelierPistolBulletProj::new, EntityClassification.MISC))
            .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).setCustomClientFactory(AtelierPistolBulletProj::new)
            .sized(0.5f, 0.5f));

    public static final RegistryObject<EntityType<AtelierShotgunSlugProj>> ATELIER_SHOTGUN_BULLET = register("projectile_ateliershotgun", (EntityType.Builder.<AtelierShotgunSlugProj>of(AtelierShotgunSlugProj::new, EntityClassification.MISC))
            .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).setCustomClientFactory(AtelierShotgunSlugProj::new)
            .sized(0.5f, 0.5f));
    public static final RegistryObject<EntityType<CrimsonWindProj>> CRIMSON_WIND = register("projectile_crimsonfanproj", (EntityType.Builder.<CrimsonWindProj>of(CrimsonWindProj::new, EntityClassification.MISC)
            .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).setCustomClientFactory(CrimsonWindProj::new)
            .sized(0.5f, 0.5f)));
    public static final RegistryObject<EntityType<DawnOfGreenDoubtEntity>> DAWN_OF_GREEN_DOUBT = register("doubt", EntityType.Builder.of(DawnOfGreenDoubtEntity::new, EntityClassification.MONSTER).sized(0.6F, 2.4F).clientTrackingRange(8));

    public static final RegistryObject<EntityType<NothingThere2Entity>> NOTHING_THERE = register("nothing_there", EntityType.Builder.of(NothingThere2Entity::new, EntityClassification.MONSTER).sized(1F, 4F).clientTrackingRange(8));
    public static final RegistryObject<EntityType<SunshowerFoxEntity>> SUNSHOWER_FOX = register("sunshower_fox", EntityType.Builder.of(SunshowerFoxEntity::new, EntityClassification.CREATURE).sized(0.5F, 0.5F).clientTrackingRange(8));

    public static final RegistryObject<EntityType<SunshowerUmbrellaEntity>> SUNSHOWER_UMBRELLA = register("sunshower_umbrella", EntityType.Builder.of(SunshowerUmbrellaEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(8));


    @SubscribeEvent
    public static void setup(final EntityAttributeCreationEvent event) {
        System.out.println("SETTING UP ATTRIBUTES");
        event.put(DAWN_OF_GREEN_DOUBT.get(), DawnOfGreenDoubtEntity.createMonsterAttributes().build());
        event.put(NOTHING_THERE.get(), NothingThere2Entity.createMonsterAttributes().build());
        event.put(SUNSHOWER_FOX.get(), SunshowerFoxEntity.createMonsterAttributes().build());
        event.put(SUNSHOWER_UMBRELLA.get(), SunshowerUmbrellaEntity.createMonsterAttributes().build());
    }

    @SubscribeEvent
    public static void setup(final EntityPatchRegistryEvent event) {
        System.out.println("SETTING UP PATCHES");
        event.getTypeEntry().put(DAWN_OF_GREEN_DOUBT.get(), (e) -> DoubtAPatch::new);
        event.getTypeEntry().put(NOTHING_THERE.get(), (e) -> NothingTherePatch::new);
        event.getTypeEntry().put(SUNSHOWER_FOX.get(), (e) -> SunshowerFoxPatch::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void setup(final PatchedRenderersEvent.Add event) {
        System.out.println("SETTING UP RENDER PATCHES");
        event.addPatchedEntityRenderer(DAWN_OF_GREEN_DOUBT.get(), DoubtARenderer::new);
        event.addPatchedEntityRenderer(NOTHING_THERE.get(), NothingThereRenderer::new);
        event.addPatchedEntityRenderer(SUNSHOWER_FOX.get(), SunshowerFoxPatchRenderer::new);
    }
}

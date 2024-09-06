package net.m3tte.tcorp;

import net.m3tte.tcorp.item.CrimsonfanprojItem;
import net.m3tte.tcorp.item.blackSilence.AtelierpistolsItem;
import net.m3tte.tcorp.item.blackSilence.AteliershotgunItem;
import net.m3tte.tcorp.item.magic_bullet.MagicBulletProjectile;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCorpModEntities {



    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, "tcorp");
    public static final RegistryObject<EntityType<?>> MAGIC_BULLET = ENTITIES.register("projectile_magic_bullet", () -> MagicBulletProjectile.bullet);
    public static final RegistryObject<EntityType<?>> ATELIER_PISTOL_BULLET = ENTITIES.register("projectile_atelier_pistol", () -> AtelierpistolsItem.pistol_bullet);
    public static final RegistryObject<EntityType<?>> ATELIER_SHOTGUN_BULLET = ENTITIES.register("projectile_ateliershotgun", () -> AteliershotgunItem.shotgun_slug);
    public static final RegistryObject<EntityType<?>> CRIMSON_WIND = ENTITIES.register("projectile_crimsonfanproj", () -> CrimsonfanprojItem.crimson_wind);
}

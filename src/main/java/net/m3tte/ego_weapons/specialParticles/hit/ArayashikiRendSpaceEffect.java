package net.m3tte.ego_weapons.specialParticles.hit;


import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.MetaParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.particle.EpicFightParticles;

@OnlyIn(Dist.CLIENT)
public class ArayashikiRendSpaceEffect extends MetaParticle {
    public ArayashikiRendSpaceEffect(ClientWorld level, double x, double y, double z, double xSpeed, double sourceEntityID, double zSpeed) {
        super(level, x, y, z);
        this.level.addParticle(EgoWeaponsParticles.GENERIC_SPACE_REND.get(), this.x, this.y + (1.48f), this.z, 1, sourceEntityID, 0.0D);
        this.level.addParticle(EgoWeaponsParticles.GENERIC_SPACE_REND.get(), this.x, this.y + (1.47f), this.z, 1, sourceEntityID, 180D);
        this.level.addParticle(EgoWeaponsParticles.ARAYASHIKI_HOR_SLASH.get(), this.x, this.y + 1.5f, this.z, 0, sourceEntityID, 0D);

    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld levelIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            ArayashikiRendSpaceEffect particle = new ArayashikiRendSpaceEffect(levelIn, x, y, z, xSpeed, ySpeed, zSpeed);
            return particle;
        }
    }
}
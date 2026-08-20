package net.m3tte.ego_weapons.specialParticles.hit;


import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.MetaParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArayashikiCrossRendSpaceEffect extends MetaParticle {
    public ArayashikiCrossRendSpaceEffect(ClientWorld level, double x, double y, double z, double xSpeed, double sourceEntityID, double zSpeed) {
        super(level, x, y, z);
        this.level.addParticle(EgoWeaponsParticles.ROTATED_GENERIC_SPACE_REND.get(), this.x, this.y+1.01, this.z, -0.5, sourceEntityID, -55.0);
        this.level.addParticle(EgoWeaponsParticles.ROTATED_GENERIC_SPACE_REND.get(), this.x, this.y+1, this.z, -0.5, sourceEntityID, 125.0);
        //this.level.addParticle(EgoWeaponsParticles.ROTATED_GENERIC_SPACE_REND.get(), this.x, this.y + 0.5, this.z, -1, sourceEntityID, 55.0);
        this.level.addParticle(EgoWeaponsParticles.ARAYASHIKI_CROSS_SLASH.get(), this.x, this.y+1, this.z, 0, sourceEntityID, 0D);

    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld levelIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            ArayashikiCrossRendSpaceEffect particle = new ArayashikiCrossRendSpaceEffect(levelIn, x, y, z, xSpeed, ySpeed, zSpeed);
            return particle;
        }
    }
}
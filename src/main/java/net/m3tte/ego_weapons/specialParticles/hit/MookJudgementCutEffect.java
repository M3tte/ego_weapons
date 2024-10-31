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

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class MookJudgementCutEffect extends MetaParticle {
    public MookJudgementCutEffect(ClientWorld world, double x, double y, double z, double width, double height, double _null) {
        super(world, x, y, z);
        this.x = x + (this.level.random.nextDouble() - 0.5D) * width;
        this.y = y + (this.level.random.nextDouble() + height) * 0.5;
        this.z = z + (this.level.random.nextDouble() - 0.5D) * width;

        Random r = new Random();
        this.level.addParticle(EpicFightParticles.HIT_BLUNT.get(), this.x + randomAdder(r), this.y + randomAdder(r), this.z + randomAdder(r), 0.0D, 0.0D, 0.0D);
        for (int c = 0; c < 6; c++) {
            this.level.addParticle(EgoWeaponsParticles.MOOK_SLASH.get(), this.x + randomAdder(r), this.y + randomAdder(r), this.z + randomAdder(r), 0.0D, 0.0D, 0.0D);
        }

    }

    private float randomAdder(Random r) {
        return r.nextFloat() * 0.8f - 0.4f;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            MookJudgementCutEffect particle = new MookJudgementCutEffect(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            return particle;
        }
    }
}
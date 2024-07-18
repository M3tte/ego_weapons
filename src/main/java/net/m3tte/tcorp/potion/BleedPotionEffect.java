//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.tcorp.potion;

import net.m3tte.tcorp.TCorpParticleRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(
        bus = Bus.MOD
)
public class BleedPotionEffect {
    @ObjectHolder("tcorp:bleed")
    public static final Effect potion = null;

    public BleedPotionEffect() {
    }

    @SubscribeEvent
    public static void registerEffect(RegistryEvent.Register<Effect> event) {
        event.getRegistry().register(new EffectCustom());
    }

    public static class EffectCustom extends Effect {
        public EffectCustom() {
            super(EffectType.HARMFUL, -16777216);
            this.setRegistryName("bleed");
        }

        @Override
        public String getDescriptionId() {
            return "effect.bleed";
        }

        @Override
        public boolean isBeneficial() {
            return false;
        }

        @Override
        public boolean isInstantenous() {
            return false;
        }

        @Override
        public boolean shouldRenderInvText(EffectInstance effect) {
            return true;
        }

        @Override
        public boolean shouldRender(EffectInstance effect) {
            return true;
        }

        @Override
        public boolean shouldRenderHUD(EffectInstance effect) {
            return true;
        }

        @Override
        public void applyEffectTick(LivingEntity entity, int amplifier) {
            System.out.println("BleedTick");
            World world = entity.level;
            if (entity.tickCount % 20 == 0) {
                entity.playSound(SoundEvents.PLAYER_HURT, 1.0F, 1.0F);
                if (world instanceof ServerWorld) {
                    entity.setHealth(entity.getHealth() - (float)amplifier * 1 - 1.0F);
                    ((ServerWorld)world).sendParticles((IParticleData)TCorpParticleRegistry.BLEED.get(), entity.getX(), entity.getY() + (double)entity.getBbHeight() / 1.5, entity.getZ(), 1, 0.0, 0.0, 0.0, 0.1);
                }
            }

        }

        @Override
        public boolean isDurationEffectTick(int duration, int amplifier) {
            return true;
        }
    }
}

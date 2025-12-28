package net.m3tte.ego_weapons.procedures;

import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class DelayedEvent {
    private int ticks = 0;
    private float waitTicks;

    Consumer<?> consumer;

    public void start(int waitTicks) {
        this.waitTicks = waitTicks;
        MinecraftForge.EVENT_BUS.register(this);
    }

    public DelayedEvent(int waitTicks, Consumer<?> consumer) {
        this.waitTicks = waitTicks;
        this.consumer = consumer;

        start(waitTicks);
    }

    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            this.ticks += 1;
            if (this.ticks >= this.waitTicks) {
                consumer.accept(null);
                MinecraftForge.EVENT_BUS.unregister(this);
            }

        }
    }

}

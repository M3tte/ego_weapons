package net.m3tte.ego_weapons.procedures;

import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Consumer;

public class DelayedEvent {


    public static void animDelayEvent(LivingEntityPatch<?> patch, int waitTicks, Consumer<Integer> consumer, String name) {

        if (patch.getOriginal().level.isClientSide())
            return;

        DynamicAnimation currentanim = patch.getServerAnimator().animationPlayer.getAnimation();

        float speed = Math.max(0.01f, currentanim.getPlaySpeed(patch));

        new DelayedEvent((int)(waitTicks / speed), consumer, name);
    }
    private int ticks = 0;
    private float waitTicks;
    private String name = null;
    Consumer<?> consumer;

    public void start(int waitTicks) {
        this.waitTicks = waitTicks;
        this.ticks = 0;
        MinecraftForge.EVENT_BUS.register(this);
    }

    public DelayedEvent(int waitTicks, Consumer<Integer> consumer) {
        if (waitTicks <= 0) {
            consumer.accept(waitTicks);
            return;
        }

        this.waitTicks = waitTicks;
        this.consumer = consumer;

        this.start(waitTicks);
    }

    public DelayedEvent(int waitTicks, Consumer<Integer> consumer, String name) {
        if (waitTicks <= 0) {
            consumer.accept(waitTicks);
            return;
        }

        this.name = name;
        this.waitTicks = waitTicks;
        this.consumer = consumer;

        this.start(waitTicks);
    }

    @Override
    public String toString() {
        if (name != null)
            return name;
        return super.toString();
    }

    @SubscribeEvent
    public void tick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            this.ticks += 1;
            if (this.ticks >= this.waitTicks) {
                EgoWeaponsMod.LOGGER.debug("Finished Delayed Event : "+name+" | After "+this.ticks+" ticks.");
                consumer.accept(null);
                MinecraftForge.EVENT_BUS.unregister(this);
            }

        }
    }

}

package net.m3tte.ego_weapons;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class MixinConnector implements IMixinConnector {
    @Override
    public void connect() {
        System.out.println("mixin connected");
        Mixins.addConfiguration(("ego_weapons.mixins.json"));
    }
}

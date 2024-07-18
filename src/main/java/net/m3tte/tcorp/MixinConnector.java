package net.m3tte.tcorp;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class MixinConnector implements IMixinConnector {
    @Override
    public void connect() {
        System.out.println("mixin connected");
        Mixins.addConfiguration(("tcorp.mixins.json"));
    }
}

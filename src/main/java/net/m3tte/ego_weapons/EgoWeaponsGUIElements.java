package net.m3tte.ego_weapons;

import net.m3tte.ego_weapons.gui.BlackSilenceSwapGUI;
import net.m3tte.ego_weapons.gui.BlackSilenceSwapWindow;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;

public class EgoWeaponsGUIElements {

    public static void register(IEventBus bus) {
        BlackSilenceSwapGUI.register(bus);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerClient() {
        DeferredWorkQueue.runLater(() -> ScreenManager.register(BlackSilenceSwapGUI.getContainerType(), BlackSilenceSwapWindow::new));
    }

}

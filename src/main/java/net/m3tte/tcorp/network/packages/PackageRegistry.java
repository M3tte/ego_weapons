package net.m3tte.tcorp.network.packages;

import net.m3tte.tcorp.TcorpMod;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.gui.BlackSilenceSwapGUI;
import net.m3tte.tcorp.keybind.ArmorabilityKeyBinding;
import net.m3tte.tcorp.keybind.OpenattrinterfaceKeyBinding;
import net.m3tte.tcorp.keybind.SwapFiringModeKeyBinding;
import net.m3tte.tcorp.keybind.WeaponabilityKeyBinding;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class PackageRegistry {

    public PackageRegistry() {
        addNetworkMessage(TcorpModVariables.WorldSavedDataSyncMessage.class, TcorpModVariables.WorldSavedDataSyncMessage::buffer, TcorpModVariables.WorldSavedDataSyncMessage::new,
                TcorpModVariables.WorldSavedDataSyncMessage::handler);
        addNetworkMessage(TcorpModVariables.PlayerVariablesSyncMessage.class, TcorpModVariables.PlayerVariablesSyncMessage::buffer, TcorpModVariables.PlayerVariablesSyncMessage::new,
                TcorpModVariables.PlayerVariablesSyncMessage::handler);
        addNetworkMessage(TcorpModVariables.SyncStaggerMessage.class, TcorpModVariables.SyncStaggerMessage::buffer, TcorpModVariables.SyncStaggerMessage::new,
                TcorpModVariables.SyncStaggerMessage::handler);
        addNetworkMessage(TcorpModVariables.SyncEmotionMessage.class, TcorpModVariables.SyncEmotionMessage::buffer, TcorpModVariables.SyncEmotionMessage::new,
                TcorpModVariables.SyncEmotionMessage::handler);
        addNetworkMessage(StaggerPackages.SendStaggerMessage.class, StaggerPackages.SendStaggerMessage::buffer, StaggerPackages.SendStaggerMessage::new,
                StaggerPackages.SendStaggerMessage::handler);
        addNetworkMessage(AbilityPackages.SyncOnrushData.class, AbilityPackages.SyncOnrushData::buffer, AbilityPackages.SyncOnrushData::new,
                AbilityPackages.SyncOnrushData::handler);

        // Binding Messages
        addNetworkMessage(BlackSilenceSwapGUI.ButtonPressedMessage.class, BlackSilenceSwapGUI.ButtonPressedMessage::buffer, BlackSilenceSwapGUI.ButtonPressedMessage::new,
                BlackSilenceSwapGUI.ButtonPressedMessage::handler);
        addNetworkMessage(BlackSilenceSwapGUI.GUISlotChangedMessage.class, BlackSilenceSwapGUI.GUISlotChangedMessage::buffer, BlackSilenceSwapGUI.GUISlotChangedMessage::new,
                BlackSilenceSwapGUI.GUISlotChangedMessage::handler);
        addNetworkMessage(ArmorabilityKeyBinding.KeyBindingPressedMessage.class, ArmorabilityKeyBinding.KeyBindingPressedMessage::buffer, ArmorabilityKeyBinding.KeyBindingPressedMessage::new,
                ArmorabilityKeyBinding.KeyBindingPressedMessage::handler);
        addNetworkMessage(WeaponabilityKeyBinding.KeyBindingPressedMessage.class, WeaponabilityKeyBinding.KeyBindingPressedMessage::buffer, WeaponabilityKeyBinding.KeyBindingPressedMessage::new,
                WeaponabilityKeyBinding.KeyBindingPressedMessage::handler);
        addNetworkMessage(SwapFiringModeKeyBinding.KeyBindingPressedMessage.class, SwapFiringModeKeyBinding.KeyBindingPressedMessage::buffer, SwapFiringModeKeyBinding.KeyBindingPressedMessage::new,
                SwapFiringModeKeyBinding.KeyBindingPressedMessage::handler);
        addNetworkMessage(OpenattrinterfaceKeyBinding.KeyBindingPressedMessage.class, OpenattrinterfaceKeyBinding.KeyBindingPressedMessage::buffer, OpenattrinterfaceKeyBinding.KeyBindingPressedMessage::new,
                OpenattrinterfaceKeyBinding.KeyBindingPressedMessage::handler);
    }




    private int messageID = 0;
    public <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, PacketBuffer> encoder, Function<PacketBuffer, T> decoder,
                                      BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        TcorpMod.PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

}

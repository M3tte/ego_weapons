package net.m3tte.ego_weapons.network.packages;

import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.gui.BlackSilenceSwapGUI;
import net.m3tte.ego_weapons.keybind.ArmorabilityKeyBinding;
import net.m3tte.ego_weapons.keybind.OpenattrinterfaceKeyBinding;
import net.m3tte.ego_weapons.keybind.SwapFiringModeKeyBinding;
import net.m3tte.ego_weapons.keybind.WeaponabilityKeyBinding;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class PackageRegistry {

    public PackageRegistry() {
        addNetworkMessage(EgoWeaponsModVars.WorldSavedDataSyncMessage.class, EgoWeaponsModVars.WorldSavedDataSyncMessage::buffer, EgoWeaponsModVars.WorldSavedDataSyncMessage::new,
                EgoWeaponsModVars.WorldSavedDataSyncMessage::handler);
        addNetworkMessage(EgoWeaponsModVars.PlayerVariablesSyncMessage.class, EgoWeaponsModVars.PlayerVariablesSyncMessage::buffer, EgoWeaponsModVars.PlayerVariablesSyncMessage::new,
                EgoWeaponsModVars.PlayerVariablesSyncMessage::handler);
        addNetworkMessage(EgoWeaponsModVars.SyncStaggerMessage.class, EgoWeaponsModVars.SyncStaggerMessage::buffer, EgoWeaponsModVars.SyncStaggerMessage::new,
                EgoWeaponsModVars.SyncStaggerMessage::handler);
        addNetworkMessage(EgoWeaponsModVars.SyncEmotionLevelMSG.class, EgoWeaponsModVars.SyncEmotionLevelMSG::buffer, EgoWeaponsModVars.SyncEmotionLevelMSG::new,
                EgoWeaponsModVars.SyncEmotionLevelMSG::handler);
        addNetworkMessage(EgoWeaponsModVars.SyncSanityMessage.class, EgoWeaponsModVars.SyncSanityMessage::buffer, EgoWeaponsModVars.SyncSanityMessage::new,
                EgoWeaponsModVars.SyncSanityMessage::handler);
        addNetworkMessage(StaggerPackages.SendStaggerMessage.class, StaggerPackages.SendStaggerMessage::buffer, StaggerPackages.SendStaggerMessage::new,
                StaggerPackages.SendStaggerMessage::handler);
        addNetworkMessage(AbilityPackages.SyncOnrushData.class, AbilityPackages.SyncOnrushData::buffer, AbilityPackages.SyncOnrushData::new,
                AbilityPackages.SyncOnrushData::handler);
        addNetworkMessage(EgoWeaponsModVars.SyncCountEffectMessage.class, EgoWeaponsModVars.SyncCountEffectMessage::buffer, EgoWeaponsModVars.SyncCountEffectMessage::new,
                EgoWeaponsModVars.SyncCountEffectMessage::handler);
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
        EgoWeaponsMod.PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

}

package net.m3tte.ego_weapons.network.packages;

import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.gui.BlackSilenceSwapGUI;
import net.m3tte.ego_weapons.keybind.OpenattrinterfaceKeyBinding;
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

        addNetworkMessage(ParticlePackages.SendStaggerMessage.class, ParticlePackages.SendStaggerMessage::buffer, ParticlePackages.SendStaggerMessage::new,
                ParticlePackages.SendStaggerMessage::handler);

        addNetworkMessage(ParticlePackages.SendTakeAimParticle.class, ParticlePackages.SendTakeAimParticle::buffer, ParticlePackages.SendTakeAimParticle::new,
                ParticlePackages.SendTakeAimParticle::handler);

        addNetworkMessage(ParticlePackages.DirectionalAttackParticle.class, ParticlePackages.DirectionalAttackParticle::buffer, ParticlePackages.DirectionalAttackParticle::new,
                ParticlePackages.DirectionalAttackParticle::handler);
        addNetworkMessage(ParticlePackages.NumberLabelParticle.class, ParticlePackages.NumberLabelParticle::buffer, ParticlePackages.NumberLabelParticle::new,
                ParticlePackages.NumberLabelParticle::handler);
        addNetworkMessage(ParticlePackages.DamageLabelParticle.class, ParticlePackages.DamageLabelParticle::buffer, ParticlePackages.DamageLabelParticle::new,
                ParticlePackages.DamageLabelParticle::handler);

        addNetworkMessage(AbilityPackages.SyncOnrushData.class, AbilityPackages.SyncOnrushData::buffer, AbilityPackages.SyncOnrushData::new,
                AbilityPackages.SyncOnrushData::handler);
        addNetworkMessage(EgoWeaponsModVars.SyncCountEffectMessage.class, EgoWeaponsModVars.SyncCountEffectMessage::buffer, EgoWeaponsModVars.SyncCountEffectMessage::new,
                EgoWeaponsModVars.SyncCountEffectMessage::handler);

        // Message for entity shake
        addNetworkMessage(ParticlePackages.SendShakeMessage.class, ParticlePackages.SendShakeMessage::buffer, ParticlePackages.SendShakeMessage::new,
                ParticlePackages.SendShakeMessage::handler);
        // Binding Messages
        addNetworkMessage(KeybindPackages.GenericKeybindingPressedMessage.class, KeybindPackages.GenericKeybindingPressedMessage::buffer, KeybindPackages.GenericKeybindingPressedMessage::new,
                KeybindPackages.GenericKeybindingPressedMessage::handler);
        addNetworkMessage(BlackSilenceSwapGUI.ButtonPressedMessage.class, BlackSilenceSwapGUI.ButtonPressedMessage::buffer, BlackSilenceSwapGUI.ButtonPressedMessage::new,
                BlackSilenceSwapGUI.ButtonPressedMessage::handler);
        addNetworkMessage(BlackSilenceSwapGUI.GUISlotChangedMessage.class, BlackSilenceSwapGUI.GUISlotChangedMessage::buffer, BlackSilenceSwapGUI.GUISlotChangedMessage::new,
                BlackSilenceSwapGUI.GUISlotChangedMessage::handler);

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

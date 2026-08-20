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

public class EgoWeaponsPackageRegistry {

    public EgoWeaponsPackageRegistry() {
        addNetworkMessage(EgoWeaponsModVars.WorldSavedDataSyncMessage.class, EgoWeaponsModVars.WorldSavedDataSyncMessage::buffer, EgoWeaponsModVars.WorldSavedDataSyncMessage::new,
                EgoWeaponsModVars.WorldSavedDataSyncMessage::handler);
        addNetworkMessage(EgoWeaponsModVars.PlayerVariablesSyncMessage.class, EgoWeaponsModVars.PlayerVariablesSyncMessage::buffer, EgoWeaponsModVars.PlayerVariablesSyncMessage::new,
                EgoWeaponsModVars.PlayerVariablesSyncMessage::handler);
        addNetworkMessage(EgoWeaponsModVars.SyncStaggerMessage.class, EgoWeaponsModVars.SyncStaggerMessage::buffer, EgoWeaponsModVars.SyncStaggerMessage::new,
                EgoWeaponsModVars.SyncStaggerMessage::handler);
        addNetworkMessage(EgoWeaponsModVars.SyncInjuryMessage.class, EgoWeaponsModVars.SyncInjuryMessage::buffer, EgoWeaponsModVars.SyncInjuryMessage::new,
                EgoWeaponsModVars.SyncInjuryMessage::handler);

        addNetworkMessage(EgoWeaponsModVars.SyncEmotionLevelMSG.class, EgoWeaponsModVars.SyncEmotionLevelMSG::buffer, EgoWeaponsModVars.SyncEmotionLevelMSG::new,
                EgoWeaponsModVars.SyncEmotionLevelMSG::handler);
        addNetworkMessage(EgoWeaponsModVars.SyncSanityMessage.class, EgoWeaponsModVars.SyncSanityMessage::buffer, EgoWeaponsModVars.SyncSanityMessage::new,
                EgoWeaponsModVars.SyncSanityMessage::handler);

        addNetworkMessage(VFXPackages.SendStaggerMessage.class, VFXPackages.SendStaggerMessage::buffer, VFXPackages.SendStaggerMessage::new,
                VFXPackages.SendStaggerMessage::handler);

        addNetworkMessage(VFXPackages.SendInsanityMessage.class, VFXPackages.SendInsanityMessage::buffer, VFXPackages.SendInsanityMessage::new,
                VFXPackages.SendInsanityMessage::handler);

        addNetworkMessage(VFXPackages.SendTakeAimParticle.class, VFXPackages.SendTakeAimParticle::buffer, VFXPackages.SendTakeAimParticle::new,
                VFXPackages.SendTakeAimParticle::handler);

        addNetworkMessage(VFXPackages.DirectionalAttackParticle.class, VFXPackages.DirectionalAttackParticle::buffer, VFXPackages.DirectionalAttackParticle::new,
                VFXPackages.DirectionalAttackParticle::handler);
        addNetworkMessage(VFXPackages.MagicBulletAimPacket.class, VFXPackages.MagicBulletAimPacket::buffer, VFXPackages.MagicBulletAimPacket::new,
                VFXPackages.MagicBulletAimPacket::handler);
        addNetworkMessage(VFXPackages.NumberLabelParticle.class, VFXPackages.NumberLabelParticle::buffer, VFXPackages.NumberLabelParticle::new,
                VFXPackages.NumberLabelParticle::handler);
        addNetworkMessage(VFXPackages.DamageLabelParticle.class, VFXPackages.DamageLabelParticle::buffer, VFXPackages.DamageLabelParticle::new,
                VFXPackages.DamageLabelParticle::handler);
        addNetworkMessage(VFXPackages.ClashLabelParticle.class, VFXPackages.ClashLabelParticle::buffer, VFXPackages.ClashLabelParticle::new,
                VFXPackages.ClashLabelParticle::handler);
        addNetworkMessage(VFXPackages.ShockwaveShakePackage.class, VFXPackages.ShockwaveShakePackage::buffer, VFXPackages.ShockwaveShakePackage::new,
                VFXPackages.ShockwaveShakePackage::handler);
        addNetworkMessage(VFXPackages.SendParticlesVelocity.class, VFXPackages.SendParticlesVelocity::buffer, VFXPackages.SendParticlesVelocity::new,
                VFXPackages.SendParticlesVelocity::handler);

        addNetworkMessage(CapabilityPackages.SyncOnrushData.class, CapabilityPackages.SyncOnrushData::buffer, CapabilityPackages.SyncOnrushData::new,
                CapabilityPackages.SyncOnrushData::handler);
        addNetworkMessage(CapabilityPackages.ApplyDialogueData.class, CapabilityPackages.ApplyDialogueData::buffer, CapabilityPackages.ApplyDialogueData::new,
                CapabilityPackages.ApplyDialogueData::handler);
        addNetworkMessage(CapabilityPackages.ChangePersonality.class, CapabilityPackages.ChangePersonality::buffer, CapabilityPackages.ChangePersonality::new,
                CapabilityPackages.ChangePersonality::handler);
        addNetworkMessage(EgoWeaponsModVars.SyncCountEffectMessage.class, EgoWeaponsModVars.SyncCountEffectMessage::buffer, EgoWeaponsModVars.SyncCountEffectMessage::new,
                EgoWeaponsModVars.SyncCountEffectMessage::handler);

        // Message for entity shake
        addNetworkMessage(VFXPackages.SendShakeMessage.class, VFXPackages.SendShakeMessage::buffer, VFXPackages.SendShakeMessage::new,
                VFXPackages.SendShakeMessage::handler);
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

package net.m3tte.ego_weapons.network.packages;

import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static net.m3tte.ego_weapons.gameasset.abilities.ArmorAbilityProcedure.runArmorAbility;
import static net.m3tte.ego_weapons.gameasset.abilities.ReloadAbilityProcedure.runReloadAbility;
import static net.m3tte.ego_weapons.gameasset.abilities.WeaponAbilityProcedure.runWeaponAbility;

public class KeybindPackages {
    public static class GenericKeybindingPressedMessage {
        int type, pressedms;

        public GenericKeybindingPressedMessage(int type, int pressedms) {
            this.type = type;
            this.pressedms = pressedms;
        }

        public GenericKeybindingPressedMessage(PacketBuffer buffer) {
            this.type = buffer.readInt();
            this.pressedms = buffer.readInt();
        }

        public static void buffer(GenericKeybindingPressedMessage message, PacketBuffer buffer) {
            buffer.writeInt(message.type);
            buffer.writeInt(message.pressedms);
        }

        public static void handler(GenericKeybindingPressedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                pressAction(context.getSender(), message.type);
            });
            context.setPacketHandled(true);
        }
    }

    public static void pressAction(PlayerEntity entity, int type) {
        World world = entity.level;
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        // security measure to prevent arbitrary chunk generation
        if (!world.isLoaded(new BlockPos(x, y, z)))
            return;

        switch (type) {
            case 0:
                runWeaponAbility(entity);
                break;
            case 1:
                runArmorAbility(entity);
                break;
            case 2:
                runReloadAbility(entity);
                break;
            case 3:
                swapFireMode(entity);
                break;
        }
    }

    public static void swapFireMode(PlayerEntity entity) {
        EgoWeaponsModVars.PlayerVariables vars = entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new EgoWeaponsModVars.PlayerVariables());
        vars.firingMode = !(vars.firingMode);
        (entity.level).playSound(null, entity.blockPosition(),
                EgoWeaponsSounds.CLICK,
                SoundCategory.NEUTRAL, (float) 1, vars.firingMode ? 0.5f : 1.5f);
        vars.syncPlayerVariables(entity);
    }


}

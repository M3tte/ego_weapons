package net.m3tte.ego_weapons;

import net.m3tte.ego_weapons.commands.*;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EgoWeaponsCommands {

    @SubscribeEvent
    public void register(RegisterCommandsEvent event) {
        EgoWeaponsMod.LOGGER.info("Registering E.G.O Weapons Commands");
        StatusEffectCommands.register(event.getDispatcher());
        StaggerCommands.register(event.getDispatcher());
        SetWarningLevel.register(event.getDispatcher());
        SetteamCommand.register(event.getDispatcher());
        SetIFramesCommand.register(event.getDispatcher());
        SetEmotionCommand.register(event.getDispatcher());
        SanityCommands.register(event.getDispatcher());
        PersonalityCommands.register(event.getDispatcher());
        LightCommands.register(event.getDispatcher());
        DialogueCommands.register(event.getDispatcher());
        EgoWeaponsMod.LOGGER.info("E.G.O Weapons Commands Registered Successfully.");
    }
}

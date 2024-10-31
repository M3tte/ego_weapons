package net.m3tte.ego_weapons.mixin.epicfight;

import net.m3tte.ego_weapons.gui.ingame.PlayerStatsIndicator;
import net.m3tte.ego_weapons.gui.ingame.StatusEffectGUI;
import net.m3tte.ego_weapons.gui.ingame.ThreatLevelGUI;
import net.m3tte.ego_weapons.world.capabilities.threatlevel.ThreatLevelSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.gui.EntityIndicator;

@Mixin(value = EntityIndicator.class, remap = false)
public class EntityIndicatorMixin {
    // Lyesman/epicfight/client/gui/EntityIndicator;init()V

    @Inject(at = @At(value = "HEAD"), method = "init()V")
    private static void initializeCustomIndicators(CallbackInfo ci) {
        ThreatLevelSystem.registerThreatLevels();
        new ThreatLevelGUI();
        new PlayerStatsIndicator();
        new StatusEffectGUI();
    }
}

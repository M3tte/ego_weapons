package net.m3tte.ego_weapons.mixin;

import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.m3tte.ego_weapons.procedures.SharedFunctions.warningSounds;

@Mixin(MusicTicker.class)
public abstract class MusicTickerMixin {




    @Shadow
    private ISound currentMusic;

    @Final
    @Shadow
    private Minecraft minecraft;

    @Inject(at = @At(value = "HEAD"), method = "tick()V", cancellable = true)
    public void catchRenderMaxHealth(CallbackInfo ci) {


        EgoWeaponsModVars.MapVariables mapVars = EgoWeaponsModVars.MapVariables.get(Minecraft.getInstance().level);
        if (mapVars.globalWarningTension >= 1) {
            int tension = Math.min(3, ((int)mapVars.globalWarningTension)-1);
            if (!this.minecraft.getSoundManager().isActive(warningSounds[tension])) {
                if (this.currentMusic != null)
                    this.minecraft.getSoundManager().stop(this.currentMusic);
                System.out.println("Playing warning music for tension: "+tension+"/3");
                this.currentMusic = warningSounds[tension];
                this.minecraft.getSoundManager().play(this.currentMusic);
            }

            ci.cancel();
        }
    }

}

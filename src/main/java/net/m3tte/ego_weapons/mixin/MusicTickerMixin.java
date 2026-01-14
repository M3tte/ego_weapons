package net.m3tte.ego_weapons.mixin;

import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SimpleSound;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MusicTicker.class, remap = true)
public abstract class MusicTickerMixin {


    @Unique
    private static final SimpleSound[] egoweapons$warningSounds = {SimpleSound.forMusic(EgoWeaponsSounds.FIRST_WARNING), SimpleSound.forMusic(EgoWeaponsSounds.SECOND_WARNING), SimpleSound.forMusic(EgoWeaponsSounds.THIRD_WARNING), SimpleSound.forMusic(EgoWeaponsSounds.FOURTH_WARNING)};


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
            if (!this.minecraft.getSoundManager().isActive(egoweapons$warningSounds[tension])) {
                if (this.currentMusic != null)
                    this.minecraft.getSoundManager().stop(this.currentMusic);
                System.out.println("Playing warning music for tension: "+tension+"/3");
                this.currentMusic = egoweapons$warningSounds[tension];
                this.minecraft.getSoundManager().play(this.currentMusic);
            }

            ci.cancel();
        }
    }

}

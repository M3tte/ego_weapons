package net.m3tte.ego_weapons.mixin;

import net.m3tte.ego_weapons.world.capabilities.UtilitySystems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.ClientLanguageMap;
import net.minecraft.util.text.LanguageMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.m3tte.ego_weapons.world.capabilities.UtilitySystems.noisyText;

@Mixin(ClientLanguageMap.class)
public class LanguageMapMixin {

    @Unique
    private boolean validToBeCensored(String toTest) {

        if (toTest.startsWith("item."))
            return true;

        if (toTest.startsWith("effect."))
            return true;

        if (toTest.startsWith("death."))
            return true;

        if (toTest.startsWith("block."))
            return true;

        return toTest.startsWith("desc.");
    }

    @Inject(at = @At(value = "HEAD"), method = "getOrDefault(Ljava/lang/String;)Ljava/lang/String;", cancellable = true)
    private void injected(String inputString, CallbackInfoReturnable<String> cir) {


        if (Minecraft.getInstance().player != null) {

            float loss = UtilitySystems.getLossOfSelf(Minecraft.getInstance().player);
            if (loss > 0) {
                if (validToBeCensored(inputString)) {
                    inputString = ((ClientLanguageMap)(Object)this).getLanguageData().getOrDefault(inputString, inputString);
                    inputString = noisyText(inputString, loss, inputString.hashCode());

                    cir.setReturnValue(inputString);

                }

            }
        }



    }


}

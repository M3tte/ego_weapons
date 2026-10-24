package net.m3tte.ego_weapons.mixin;

import net.m3tte.ego_weapons.client.util.descriptionHandler.AttributeTextPlaceholder;
import net.minecraft.util.text.CharacterManager;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(CharacterManager.class)
public class CharacterManagerMixin {


    @Inject(at = @At(value = "HEAD"), method = "splitLines(Lnet/minecraft/util/text/ITextProperties;ILnet/minecraft/util/text/Style;)Ljava/util/List;", cancellable = true)
    private void returnPlaceholderTextprops(ITextProperties textProps, int p_238362_2_, Style p_238362_3_, CallbackInfoReturnable<List<ITextProperties>> cir) {

        if (textProps instanceof AttributeTextPlaceholder) {
            List<ITextProperties> arr = new ArrayList<>();
            arr.add(textProps);
            int emptyLines = ((AttributeTextPlaceholder) textProps).getEmptyLines();

            for (int i = 0; i < emptyLines; i++) {
                arr.add(new StringTextComponent(""));
            }

            cir.setReturnValue(arr);
        }
    }
}

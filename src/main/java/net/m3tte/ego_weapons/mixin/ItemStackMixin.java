package net.m3tte.ego_weapons.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.m3tte.ego_weapons.world.capabilities.EgoAttribute;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoWeaponsArmor;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Collection;
import java.util.Map;

import static net.m3tte.ego_weapons.procedures.SharedFunctions.evaluateDamageSource;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @ModifyVariable(method = "getTooltipLines(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/client/util/ITooltipFlag;)Ljava/util/List;", at = @At("STORE"), ordinal = 0)
    private Multimap<Attribute, AttributeModifier> getReducedMultimap(Multimap<Attribute, AttributeModifier> modifierMultimap) {
        ItemStack self = (ItemStack)((Object) this);
        if (self.getItem() instanceof GenericEgoWeaponsArmor) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            for (Attribute attr : modifierMultimap.keys()) {
                if (!(attr instanceof EgoAttribute) && !(attr.equals(Attributes.ARMOR))) {
                    builder.putAll(attr, modifierMultimap.get(attr));
                }
            }
            return builder.build();
        }
        return modifierMultimap;
    }
}

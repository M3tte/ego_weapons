package net.m3tte.ego_weapons.mixin;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(AttributeModifierMap.class)
public class AttributeModifierMapMixin {
    @Final
    @Shadow
    private Map<Attribute, ModifiableAttributeInstance> instances;

}

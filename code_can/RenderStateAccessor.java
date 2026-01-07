package net.m3tte.ego_weapons.mixin.rendering;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
@Mixin(value = RenderState.class, remap = false)
public interface RenderStateAccessor {

    @Accessor("name")
    String getName();

}

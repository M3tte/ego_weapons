package net.m3tte.ego_weapons.mixin.rendering;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
@Mixin(value = IRenderTypeBuffer.Impl.class, remap = false)
public interface InnerBufAccessor {

    @Accessor("fixedBuffers")
    Map<RenderType, BufferBuilder> fixedBuffers();

    @Accessor("lastState")
    Optional<RenderType> lastState();

    //@Invoker("getBuilderRaw")
    //BufferBuilder getBuilderRaw(RenderType typeIn);
}

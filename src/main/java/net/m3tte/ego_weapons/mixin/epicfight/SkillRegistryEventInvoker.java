package net.m3tte.ego_weapons.mixin.epicfight;

import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.api.forgeevent.SkillRegistryEvent;
import yesman.epicfight.skill.Skill;

import java.util.Map;


@Mixin(value = SkillRegistryEvent.class, remap = false)
public interface SkillRegistryEventInvoker {



    @Accessor("skills")
    Map<ResourceLocation, Skill> getRegisteredSkills();
}

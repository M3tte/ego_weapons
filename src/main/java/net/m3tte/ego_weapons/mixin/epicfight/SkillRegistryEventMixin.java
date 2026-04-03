package net.m3tte.ego_weapons.mixin.epicfight;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsSkills;
import net.m3tte.ego_weapons.potion.countEffects.BleedEffect;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.SkillRegistryEvent;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Consumer;

@Mixin(value = SkillRegistryEvent.class, remap = false)
public class SkillRegistryEventMixin {



    //Lyesman/epicfight/api/forgeevent/SkillRegistryEvent;registerSkill(Lyesman/epicfight/skill/Skill;Z)Lyesman/epicfight/skill/Skill;
    // Lyesman/epicfight/client/gui/EntityIndicator;init()V
    // Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;playSound(Lnet/minecraft/util/SoundEvent;FF)V
    @Inject(at = @At(value = "HEAD"), method = "registerSkill(Lyesman/epicfight/skill/Skill;Z)Lyesman/epicfight/skill/Skill;")
    private void callSkillGetter(Skill skill, boolean learnable, CallbackInfoReturnable<Skill> cir) {
        SkillRegistryEventInvoker invoker = ((SkillRegistryEventInvoker)(Object)this);

        if (EgoWeaponsSkills.REGISTERED_SKILLS_REF == null)
            EgoWeaponsSkills.REGISTERED_SKILLS_REF = invoker.getRegisteredSkills();

    }
}

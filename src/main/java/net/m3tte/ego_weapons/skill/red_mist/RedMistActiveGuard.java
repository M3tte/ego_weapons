package net.m3tte.ego_weapons.skill.red_mist;

import com.google.common.collect.Lists;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.DurandalMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.MimicryMovesetAnims;
import net.m3tte.ego_weapons.procedures.BlipTick;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCategories;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsStyles;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.gameasset.Skills;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.GuardSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.HurtEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import javax.annotation.Nullable;
import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.skill.GenericActiveGuard.canParryHeavy;


public class RedMistActiveGuard extends GuardSkill {
    public RedMistActiveGuard(Builder builder) {
        super(builder);
    }

    private static final SkillDataManager.SkillDataKey<Integer> LAST_ACTIVE;
    private static final SkillDataManager.SkillDataKey<Integer> PARRY_MOTION_COUNTER;

    private static final StaticAnimation[] MIMICRY_GUARDS = {MimicryMovesetAnims.KALI_PARRY_1, MimicryMovesetAnims.KALI_PARRY_2};
    public static Builder createBuilder(ResourceLocation resourceLocation) {
        return GuardSkill.createBuilder(resourceLocation)
                .addGuardMotion(EgoWeaponsCategories.MIMICRY, (item, player) -> MimicryMovesetAnims.KALI_GUARD_HIT)
                .addGuardBreakMotion(EgoWeaponsCategories.MIMICRY, (item, player) -> BlackSilenceMovesetAnims.RANGA_GUARD_STAGGER)
                .addAdvancedGuardMotion(EgoWeaponsCategories.MIMICRY, (item, player) ->   MIMICRY_GUARDS[player.getOriginal().getRandom().nextInt(2)]);
    }

    // Ensures the last active is actually applied
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getDataManager().registerData(LAST_ACTIVE);
        container.getDataManager().registerData(PARRY_MOTION_COUNTER);
        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.SERVER_ITEM_USE_EVENT, EVENT_UUID, (event) -> {
            CapabilityItem itemCapability = event.getPlayerPatch().getHoldingItemCapability(Hand.MAIN_HAND);
            if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.GUARD) && this.isExecutableState(event.getPlayerPatch())) {
                event.getPlayerPatch().getOriginal().startUsingItem(Hand.MAIN_HAND);
            }

            container.getDataManager().setData(LAST_ACTIVE, event.getPlayerPatch().getOriginal().tickCount);
        });
    }


    public void guard(SkillContainer container, CapabilityItem itemCapability, HurtEvent.Pre event, float knockback, float impact, boolean advanced) {
        if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.ADVANCED_GUARD)) {
            DamageSource damageSource = event.getDamageSource();
            if (this.isBlockableSource(damageSource, true)) {
                ServerPlayerEntity playerentity = event.getPlayerPatch().getOriginal();
                boolean successParrying = playerentity.tickCount     - container.getDataManager().getDataValue(LAST_ACTIVE) < 8;
                float penalty = container.getDataManager().getDataValue(PENALTY);
                event.getPlayerPatch().playSound(EpicFightSounds.CLASH, -0.05F, 0.1F);
                EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument((ServerWorld)playerentity.level, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, playerentity, damageSource.getDirectEntity());
                if (successParrying) {
                    knockback *= 0.4F;

                    penalty += 0.1F;

                } else {
                    penalty += this.getPenaltyMultiplier(itemCapability);
                }
                container.getDataManager().setDataSync(PENALTY, penalty, playerentity);

                if (damageSource.getDirectEntity() instanceof LivingEntity) {
                    knockback += (float) EnchantmentHelper.getKnockbackBonus((LivingEntity)damageSource.getDirectEntity()) * 0.1F;
                }

                event.getPlayerPatch().knockBackEntity(damageSource.getDirectEntity().position(), knockback);
                float stamina = event.getPlayerPatch().getStamina();

                if ((itemCapability.getStyle(event.getPlayerPatch()).equals(EgoWeaponsStyles.KALI_EGO) || itemCapability.getStyle(event.getPlayerPatch()).equals(EgoWeaponsStyles.KALI)) && successParrying) {
                    stamina += 0.1f + 1.5f / (1 + penalty * penalty * penalty);

                    if (stamina > event.getPlayerPatch().getMaxStamina()) {
                        stamina = event.getPlayerPatch().getMaxStamina();
                        EgoWeaponsModVars.PlayerVariables entityData = event.getPlayerPatch().getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);
                        if (!event.getPlayerPatch().getOriginal().getCooldowns().isOnCooldown(EgoWeaponsItems.MIMICRY.get()) && entityData != null && entityData.globalcooldown <= 0) {
                            event.getPlayerPatch().playAnimationSynchronized(MimicryMovesetAnims.KALI_PARRY_EVADE, 0);
                            event.getPlayerPatch().playSound(EgoWeaponsSounds.BLACK_SILENCE_EVADE, 1, 1);

                            if (damageSource.getEntity() instanceof LivingEntity) {
                                EgoWeaponsEffects.OFFENSE_LEVEL_DOWN.get().decrement((LivingEntity) damageSource.getEntity(), 0, 1);
                            }

                            BlipTick.chargeBlips(playerentity, 1, true);

                            event.getPlayerPatch().getOriginal().getCooldowns().addCooldown(EgoWeaponsItems.MIMICRY.get(), 100);
                            entityData.globalcooldown = 100;
                            entityData.syncPlayerVariables(event.getPlayerPatch().getOriginal());
                            return;
                        }
                    }
                } else {

                    if (stamina > event.getPlayerPatch().getMaxStamina() - 0.05) {
                        if (event.getDamageSource().getDirectEntity() instanceof LivingEntity) {
                            ((LivingEntity) event.getDamageSource().getDirectEntity()).addEffect(new EffectInstance(Effects.WEAKNESS, 100, 1)); // TODO: REPLACE WITH FRAGILE
                        }
                    }

                    stamina -= penalty * impact;
                }

                event.getPlayerPatch().setStamina(stamina);
                BlockType blockType = successParrying ? BlockType.ADVANCED_GUARD : (stamina >= 0.0F ? BlockType.GUARD : BlockType.GUARD_BREAK);

                // Part condition. Strong attacks cannot be parried if stamina were to reach 0
                blockType = canParryHeavy(successParrying, event.getPlayerPatch(), blockType, stamina, impact, event, penalty);
                if (blockType.equals(BlockType.GUARD_BREAK))
                    successParrying = false;

                StaticAnimation animation = this.getGuardMotion(event.getPlayerPatch(), itemCapability, blockType);
                if (animation != null) {
                    event.getPlayerPatch().playAnimationSynchronized(animation, 0.0F);
                }

                EmotionSystem.handleGuard(playerentity, event.getAmount(), impact, successParrying, event.getDamageSource().getEntity());
                this.dealEvent(event.getPlayerPatch(), event);
                return;
            }
        }

        super.guard(container, itemCapability, event, knockback, impact, false);
    }

    protected boolean isBlockableSource(DamageSource damageSource, boolean advanced) {
        return damageSource.isProjectile() && advanced || super.isBlockableSource(damageSource, false);
    }

    @Override
    protected float getPenaltyMultiplier(CapabilityItem itemCapapbility) {
        return itemCapapbility.getWeaponCategory().equals(EgoWeaponsCategories.MOOK_WORKSHOP) ? 1 : 0.6F;
    }

    @Nullable
    protected StaticAnimation getGuardMotion(PlayerPatch<?> playerpatch, CapabilityItem itemCapability, BlockType blockType) {
        if (blockType == BlockType.ADVANCED_GUARD) {
            if (itemCapability.getWeaponCategory() == EgoWeaponsCategories.DURANDAL) {
                return DurandalMovesetAnims.DURANDAL_GUARD_COUNTER;
            } else if (itemCapability.getWeaponCategory() == EgoWeaponsCategories.CRYSTAL_ATELIER) {
                return Animations.SWORD_GUARD_ACTIVE_HIT1;
            }

        }

        return super.getGuardMotion(playerpatch, itemCapability, blockType);
    }

    public Skill getPriorSkill() {
        return Skills.GUARD;
    }

    protected boolean isAdvancedGuard() {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public List<Object> getTooltipArgs() {
        List<Object> list = Lists.newArrayList();
        list.add(String.format("%s, %s, %s, %s", EgoWeaponsCategories.MIMICRY).toLowerCase());
        return list;
    }

    static {
        LAST_ACTIVE = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
        PARRY_MOTION_COUNTER = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
    }

}

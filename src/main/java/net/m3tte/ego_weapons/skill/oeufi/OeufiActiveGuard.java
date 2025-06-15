package net.m3tte.ego_weapons.skill.oeufi;

import com.google.common.collect.Lists;
import net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.OeufiAssocMovesetAnims;
import net.m3tte.ego_weapons.potion.countEffects.TremorEffect;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCategories;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.types.StaticAnimation;
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

import static net.m3tte.ego_weapons.skill.GenericActiveGuard.canParryHeavy;


public class OeufiActiveGuard extends GuardSkill {
    public OeufiActiveGuard(Builder builder) {
        super(builder);
    }

    private static final SkillDataManager.SkillDataKey<Integer> LAST_ACTIVE;
    private static final SkillDataManager.SkillDataKey<Integer> PARRY_MOTION_COUNTER;

    public static Builder createBuilder(ResourceLocation resourceLocation) {
        return GuardSkill.createBuilder(resourceLocation)
                .addGuardMotion(EgoWeaponsCategories.OEUFI, (item, player) -> OeufiAssocMovesetAnims.OEUFI_GUARD_HIT)
                .addGuardBreakMotion(EgoWeaponsCategories.OEUFI, (item, player) -> BlackSilenceMovesetAnims.RANGA_GUARD_STAGGER)
                .addAdvancedGuardMotion(EgoWeaponsCategories.OEUFI, (itemCap, playerpatch) ->
                        new StaticAnimation[] { OeufiAssocMovesetAnims.OEUFI_PARRY_2, OeufiAssocMovesetAnims.OEUFI_PARRY_1, OeufiAssocMovesetAnims.OEUFI_PARRY_2 });
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
                stamina -= penalty * impact;
                event.getPlayerPatch().setStamina(stamina);
                BlockType blockType = successParrying ? BlockType.ADVANCED_GUARD : (stamina >= 0.0F ? BlockType.GUARD : BlockType.GUARD_BREAK);


                if (blockType != BlockType.GUARD_BREAK) {
                    if (event.getDamageSource().getDirectEntity() instanceof LivingEntity) {
                        TremorEffect.incrementTremor((LivingEntity) event.getDamageSource().getDirectEntity(), 0, 1);
                    }
                }


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
        return itemCapapbility.getWeaponCategory().equals(EgoWeaponsCategories.SOLEMN_LAMENT) ? 1 : 0.6F;
    }

    @Nullable
    protected StaticAnimation getGuardMotion(PlayerPatch<?> playerpatch, CapabilityItem itemCapability, BlockType blockType) {
        if (blockType == BlockType.ADVANCED_GUARD) {
            StaticAnimation[] motions = (StaticAnimation[])this.getGuradMotionMap(blockType).getOrDefault(itemCapability.getWeaponCategory(), (a, b) -> null).apply(itemCapability, playerpatch);

            if (motions != null) {
                SkillDataManager dataManager = playerpatch.getSkill(this.getCategory()).getDataManager();
                int motionCounter = dataManager.getDataValue(PARRY_MOTION_COUNTER);
                dataManager.setDataF(PARRY_MOTION_COUNTER, (v) -> v + 1);
                motionCounter %= motions.length + 1;

                if (motionCounter == motions.length) {

                    return OeufiAssocMovesetAnims.OEUFI_PARRY_COUNTER;
                } else {
                    return motions[motionCounter];
                }


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
        list.add(String.format("%s, %s, %s, %s", EgoWeaponsCategories.SOLEMN_LAMENT).toLowerCase());
        return list;
    }

    static {
        LAST_ACTIVE = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
        PARRY_MOTION_COUNTER = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
    }

}

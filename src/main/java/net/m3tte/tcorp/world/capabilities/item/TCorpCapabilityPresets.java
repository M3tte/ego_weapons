//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.tcorp.world.capabilities.item;

import net.m3tte.tcorp.TCorpItems;
import net.m3tte.tcorp.gameasset.TCorpAnimations;
import net.m3tte.tcorp.gameasset.TCorpSkills;
import net.m3tte.tcorp.item.redmist.RedMistEGOSuit;
import net.m3tte.tcorp.item.redmist.RedMistJacket;
import net.m3tte.tcorp.potion.FuriosoPotionEffect;
import net.m3tte.tcorp.potion.SolemnLamentEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.gameasset.Skills;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

public class TCorpCapabilityPresets {


    public static final Collider LONGER_BLADE = new MultiOBBCollider(4, 0.4, 0.6, 0.9, 0.0, 0.0, -0.8);
    public static final Collider SPLIT_HORIZONTAL = new MultiOBBCollider(4, 0.4, 0.6, 1.1, 0.0, 0.0, -0.8);


    public static final Collider RIFLE = new MultiOBBCollider(4, 0.2, 1, 0.2, 0, 0.0, -0.4);

    public static final Collider SOLEMN_LAMENT_HITBOX = new MultiOBBCollider(4, 0.6, 2.8, 0.6, 0, -2.5, 0);
    public static final Collider SOLEMN_LAMENT_HITBOX_EXT = new MultiOBBCollider(4, 0.6, 3.3, 0.6, 0, -3.3, 0);
    public static final Function<Item, CapabilityItem.Builder> MOOK_WORKSHOP = (item) ->
            WeaponCapability.builder().category(TCorpCategories.MOOK_WORKSHOP).canBePlacedOffhand(false).styleProvider((playerpatch) ->
                            (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10) ? TCorpStyles.FURIOSO : Styles.TWO_HAND)

                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, TCorpAnimations.MOOK_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, TCorpAnimations.MOOK_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, TCorpAnimations.MOOK_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, TCorpAnimations.MOOK_KNEEL)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, TCorpAnimations.MOOK_SNEAK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, TCorpAnimations.MOOK_GUARD)

                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.IDLE, TCorpAnimations.MOOK_IDLE)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.WALK, TCorpAnimations.MOOK_WALK)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.RUN, TCorpAnimations.MOOK_RUN)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.KNEEL, TCorpAnimations.MOOK_KNEEL)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.SNEAK, TCorpAnimations.MOOK_SNEAK)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, TCorpAnimations.MOOK_GUARD)
                    .collider(ColliderPreset.SWORD)
                    .passiveSkill(TCorpSkills.BASIC_BLOCKABLE_PASSIVE)
                    .specialAttack(Styles.TWO_HAND, TCorpSkills.MOOK_CUT)
                    .specialAttack(TCorpStyles.FURIOSO, TCorpSkills.MOOK_CUT)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(TCorpStyles.FURIOSO, TCorpAnimations.MOOK_CUT, TCorpAnimations.MOOK_CUT, TCorpAnimations.MOOK_CUT)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(Styles.TWO_HAND, TCorpAnimations.MOOK_AUTO_1, TCorpAnimations.MOOK_AUTO_2, TCorpAnimations.MOOK_AUTO_3, TCorpAnimations.MOOK_AUTO_4, Animations.AXE_DASH, TCorpAnimations.MOOK_AUTO_JUMP)


                    .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SWORD);

    public static final Function<Item, CapabilityItem.Builder> DURANDAL = (item) ->
            WeaponCapability.builder().category(TCorpCategories.DURANDAL).canBePlacedOffhand(false).styleProvider((playerpatch) ->
                            (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10) ? TCorpStyles.FURIOSO : Styles.TWO_HAND)

                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, TCorpAnimations.DURANDAL_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, TCorpAnimations.DURANDAL_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, TCorpAnimations.DURANDAL_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, TCorpAnimations.DURANDAL_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, TCorpAnimations.DURANDAL_GUARD)

                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.IDLE, TCorpAnimations.DURANDAL_IDLE)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.WALK, TCorpAnimations.DURANDAL_IDLE)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.RUN, TCorpAnimations.DURANDAL_RUN)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.KNEEL, TCorpAnimations.DURANDAL_IDLE)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, TCorpAnimations.DURANDAL_GUARD)
                    .collider(LONGER_BLADE)
                    .passiveSkill(TCorpSkills.BASIC_BLOCKABLE_PASSIVE)
                    .specialAttack(Styles.TWO_HAND, TCorpSkills.LESSER_SPLIT_VERTICAL)
                    .specialAttack(TCorpStyles.FURIOSO, TCorpSkills.LESSER_SPLIT_VERTICAL)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(TCorpStyles.FURIOSO, TCorpAnimations.DURANDAL_FURIOSO_1, TCorpAnimations.DURANDAL_FURIOSO_2, TCorpAnimations.DURANDAL_FURIOSO_3, Animations.AXE_DASH, Animations.SWORD_AIR_SLASH)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(Styles.TWO_HAND, TCorpAnimations.DURANDAL_ATTACK_1, Animations.KATANA_AUTO1, Animations.KATANA_AUTO2, Animations.AXE_DASH, Animations.AXE_DASH, Animations.SWORD_AIR_SLASH)

                    .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SWORD);

    public static final Function<Item, CapabilityItem.Builder> MIMICRY = (item) ->
            WeaponCapability.builder().category(TCorpCategories.MIMICRY).canBePlacedOffhand(false).styleProvider((playerpatch) ->
                            (playerpatch.getOriginal().getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(TCorpItems.JACKET_OF_THE_RED_MIST.get()) ? TCorpStyles.KALI :
                                    playerpatch.getOriginal().getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(TCorpItems.RED_MIST_EGO_CHESTPLATE.get()) ? TCorpStyles.KALI_EGO : Styles.TWO_HAND))

                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, TCorpAnimations.MIMICRY_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, TCorpAnimations.MIMICRY_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, TCorpAnimations.MIMICRY_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, TCorpAnimations.MIMICRY_KNEEL)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, TCorpAnimations.MIMICRY_SNEAK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, TCorpAnimations.MIMICRY_GUARD)

                    .livingMotionModifier(TCorpStyles.KALI, LivingMotions.IDLE, TCorpAnimations.KALI_IDLE)
                    .livingMotionModifier(TCorpStyles.KALI, LivingMotions.WALK, TCorpAnimations.KALI_WALK)
                    .livingMotionModifier(TCorpStyles.KALI, LivingMotions.RUN, TCorpAnimations.KALI_RUN)
                    .livingMotionModifier(TCorpStyles.KALI, LivingMotions.KNEEL, TCorpAnimations.KALI_KNEEL)
                    .livingMotionModifier(TCorpStyles.KALI, LivingMotions.SNEAK, TCorpAnimations.KALI_SNEAK)
                    .livingMotionModifier(TCorpStyles.KALI, LivingMotions.BLOCK, TCorpAnimations.KALI_GUARD)

                    .livingMotionModifier(TCorpStyles.KALI_EGO, LivingMotions.IDLE, TCorpAnimations.KALI_EGO_IDLE)
                    .livingMotionModifier(TCorpStyles.KALI_EGO, LivingMotions.WALK, TCorpAnimations.KALI_EGO_WALK)
                    .livingMotionModifier(TCorpStyles.KALI_EGO, LivingMotions.RUN, TCorpAnimations.KALI_EGO_RUN)
                    .livingMotionModifier(TCorpStyles.KALI_EGO, LivingMotions.KNEEL, TCorpAnimations.KALI_EGO_KNEEL)
                    .livingMotionModifier(TCorpStyles.KALI_EGO, LivingMotions.SNEAK, TCorpAnimations.KALI_EGO_SNEAK)
                    .livingMotionModifier(TCorpStyles.KALI_EGO, LivingMotions.BLOCK, TCorpAnimations.KALI_GUARD)

                    .collider(LONGER_BLADE)
                    .passiveSkill(TCorpSkills.KALI_PASSIVE)
                    .specialAttack(Styles.TWO_HAND, TCorpSkills.HELLO)
                    .specialAttack(TCorpStyles.KALI, TCorpSkills.GREATER_SPLIT_VERTICAL)
                    .specialAttack(TCorpStyles.KALI_EGO, TCorpSkills.GREATER_SPLIT_VERTICAL)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(TCorpStyles.KALI_EGO, TCorpAnimations.KALI_EGO_AUTO_1, TCorpAnimations.KALI_EGO_AUTO_2, TCorpAnimations.KALI_AUTO_3, TCorpAnimations.KALI_AUTO_4, TCorpAnimations.KALI_DASH, TCorpAnimations.KALI_JUMP)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(TCorpStyles.KALI, TCorpAnimations.KALI_AUTO_1, TCorpAnimations.KALI_AUTO_2, TCorpAnimations.KALI_AUTO_3, TCorpAnimations.KALI_AUTO_4, TCorpAnimations.KALI_DASH, TCorpAnimations.KALI_JUMP)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(Styles.TWO_HAND, TCorpAnimations.MIMICRY_AUTO_1, TCorpAnimations.MIMICRY_AUTO_2, TCorpAnimations.MIMICRY_AUTO_3, TCorpAnimations.MIMICRY_DASH, TCorpAnimations.MIMICRY_AUTO_3)

                    .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SWORD);
    public static final Function<Item, CapabilityItem.Builder> WHEELS_INDUSTRY = (item) ->
            WeaponCapability.builder().category(TCorpCategories.WHEELS_INDUSTRY).canBePlacedOffhand(false).styleProvider((playerpatch) ->
                            (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10) ? TCorpStyles.FURIOSO : Styles.TWO_HAND)

                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, TCorpAnimations.WHEELS_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, TCorpAnimations.WHEELS_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, TCorpAnimations.WHEELS_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, TCorpAnimations.WHEELS_CROUCH)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, TCorpAnimations.WHEELS_SNEAK)
                    .collider(LONGER_BLADE)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.IDLE, TCorpAnimations.WHEELS_IDLE)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.WALK, TCorpAnimations.WHEELS_WALK)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.RUN, TCorpAnimations.WHEELS_RUN)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.KNEEL, TCorpAnimations.WHEELS_CROUCH)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.SNEAK, TCorpAnimations.WHEELS_SNEAK)

                    .passiveSkill(TCorpSkills.BLACKSILENCE_PASSIVE)
                    .specialAttack(Styles.TWO_HAND, TCorpSkills.WHEELS_SMASH)
                    .specialAttack(TCorpStyles.FURIOSO, TCorpSkills.WHEELS_SMASH)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(TCorpStyles.FURIOSO, TCorpAnimations.WHEELS_HEAVY, TCorpAnimations.WHEELS_HEAVY, TCorpAnimations.WHEELS_HEAVY)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(Styles.TWO_HAND, TCorpAnimations.WHEELS_AUTO_1, TCorpAnimations.WHEELS_AUTO_2, TCorpAnimations.WHEELS_AUTO_3, TCorpAnimations.WHEELS_DASH, TCorpAnimations.WHEELS_AUTO_3);

    public static final Function<Item, CapabilityItem.Builder> OLD_BOYS = (item) ->
            WeaponCapability.builder().category(TCorpCategories.OLD_BOYS).canBePlacedOffhand(false).styleProvider((playerpatch) ->
                            (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10) ? TCorpStyles.FURIOSO : Styles.ONE_HAND)


                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, TCorpAnimations.OLD_BOYS_GUARD)

                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, TCorpAnimations.OLD_BOYS_GUARD)

                    .passiveSkill(TCorpSkills.OLD_BOYS_PASSIVE)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(TCorpStyles.FURIOSO, TCorpAnimations.OLD_BOYS_FURIOSO, TCorpAnimations.OLD_BOYS_FURIOSO, TCorpAnimations.OLD_BOYS_FURIOSO)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(Styles.ONE_HAND, TCorpAnimations.OLD_BOYS_AUTO_1, TCorpAnimations.OLD_BOYS_AUTO_2, TCorpAnimations.OLD_BOYS_AUTO_3, Animations.AXE_DASH, Animations.AXE_AIRSLASH)


                    .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SWORD);

    public static final Function<Item, CapabilityItem.Builder> CRYSTAL_ATELIER = (item) -> WeaponCapability.builder()
            .category(TCorpCategories.CRYSTAL_ATELIER)

            .styleProvider((playerpatch) -> {
                if (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10)
                    return TCorpStyles.FURIOSO;

                return playerpatch.getHoldingItemCapability(Hand.OFF_HAND).getWeaponCategory() == TCorpCategories.CRYSTAL_ATELIER ? Styles.TWO_HAND : Styles.ONE_HAND;
            })

            .passiveSkill(TCorpSkills.BASIC_BLOCKABLE_PASSIVE)
            //.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            //.specialAttack(Styles.TWO_HAND, TCorpSkills.LESSER_SPLIT_VERTICAL)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == TCorpCategories.CRYSTAL_ATELIER)
            .collider(ColliderPreset.SWORD)
            .hitSound(EpicFightSounds.BLADE_HIT)
            .specialAttack(Styles.TWO_HAND, TCorpSkills.CRYSTAL_ATELIER)
            .specialAttack(TCorpStyles.FURIOSO, TCorpSkills.CRYSTAL_ATELIER)
            .newStyleCombo(Styles.TWO_HAND, TCorpAnimations.CRYSTAL_ATELIER_DODGE, TCorpAnimations.CRYSTAL_ATELIER_INSTANT_DASH, TCorpAnimations.CRYSTAL_ATELIER_INSTANT_DASH_DEBOUNCE, TCorpAnimations.CRYSTAL_ATELIER_INSTANT_DASH_DEBOUNCE, Animations.SWORD_DUAL_AIR_SLASH)
            .newStyleCombo(TCorpStyles.FURIOSO, TCorpAnimations.CRYSTAL_ATELIER_FURIOSO, TCorpAnimations.CRYSTAL_ATELIER_DODGE, TCorpAnimations.CRYSTAL_ATELIER_INSTANT_DASH_DEBOUNCE, TCorpAnimations.CRYSTAL_ATELIER_FURIOSO, Animations.SWORD_DUAL_AIR_SLASH)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .newStyleCombo(Styles.ONE_HAND, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SWORD_DASH, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SWORD)
            .canBePlacedOffhand(true);

    public static final Function<Item, CapabilityItem.Builder> ZELKOVA_MACE = (item) -> WeaponCapability.builder()
            .category(TCorpCategories.ZELKOVA_MACE)
            .styleProvider((playerpatch) -> Styles.ONE_HAND)
            .collider(ColliderPreset.SWORD)
            .hitSound(EpicFightSounds.BLUNT_HIT_HARD)
            .specialAttack(Styles.ONE_HAND, Skills.GUILLOTINE_AXE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, TCorpAnimations.ZELKOVA_IDLE)
            .newStyleCombo(Styles.ONE_HAND, Animations.AXE_AUTO1, Animations.AXE_AUTO2, Animations.AXE_DASH, Animations.AXE_AIRSLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SWORD)
            .canBePlacedOffhand(true);

    public static final Function<Item, CapabilityItem.Builder> ZELKOVA_AXE = (item) -> WeaponCapability.builder()
            .category(TCorpCategories.ZELKOVA_AXE)
            .styleProvider((playerpatch) -> {
                if (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10)
                    return TCorpStyles.FURIOSO;

                return playerpatch.getHoldingItemCapability(Hand.OFF_HAND).getWeaponCategory() == TCorpCategories.ZELKOVA_MACE ? Styles.TWO_HAND : Styles.ONE_HAND;
            })
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == TCorpCategories.ZELKOVA_MACE)
            .collider(ColliderPreset.SWORD)
            .hitSound(EpicFightSounds.BLADE_HIT)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, TCorpAnimations.ZELKOVA_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, TCorpAnimations.ZELKOVA_IDLE)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.IDLE, TCorpAnimations.ZELKOVA_IDLE)
            .specialAttack(Styles.ONE_HAND, Skills.GUILLOTINE_AXE)
            .specialAttack(Styles.TWO_HAND, Skills.GUILLOTINE_AXE)
            .specialAttack(TCorpStyles.FURIOSO, Skills.GUILLOTINE_AXE)
            .newStyleCombo(Styles.ONE_HAND, Animations.AXE_AUTO1, Animations.AXE_AUTO2, Animations.AXE_DASH, Animations.AXE_AIRSLASH)
            .newStyleCombo(Styles.TWO_HAND, TCorpAnimations.ZELKOVA_ATTACK_1, TCorpAnimations.ZELKOVA_ATTACK_2, Animations.AXE_DASH, Animations.AXE_AIRSLASH)
            .newStyleCombo(TCorpStyles.FURIOSO, TCorpAnimations.ZELKOVA_FURIOSO_1, TCorpAnimations.ZELKOVA_FURIOSO_2, TCorpAnimations.ZELKOVA_FURIOSO_1, Animations.AXE_AIRSLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SWORD)
            .canBePlacedOffhand(true);

    public static final Function<Item, CapabilityItem.Builder> RANGA_CLAW = (item) -> WeaponCapability.builder()
            .category(TCorpCategories.RANGA)
            .styleProvider((playerpatch) -> {

                if (playerpatch.getOriginal() instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) playerpatch.getOriginal();
                    if (player.getCooldowns().isOnCooldown(player.getMainHandItem().getItem()))
                        return Styles.ONE_HAND;
                }

                if (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10)
                    return TCorpStyles.FURIOSO;

                return playerpatch.getHoldingItemCapability(Hand.OFF_HAND).getWeaponCategory() == TCorpCategories.RANGA ? Styles.TWO_HAND : Styles.ONE_HAND;
            })
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == TCorpCategories.RANGA)
            .collider(ColliderPreset.DAGGER)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, TCorpAnimations.RANGA_WALK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, TCorpAnimations.RANGA_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, TCorpAnimations.RANGA_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, TCorpAnimations.RANGA_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, TCorpAnimations.RANGA_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, TCorpAnimations.RANGA_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, TCorpAnimations.RANGA_KNEEL)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.KNEEL, TCorpAnimations.RANGA_KNEEL)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, TCorpAnimations.RANGA_SNEAK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, TCorpAnimations.RANGA_SNEAK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, TCorpAnimations.RANGA_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, TCorpAnimations.RANGA_GUARD)

            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.WALK, TCorpAnimations.RANGA_WALK)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.IDLE, TCorpAnimations.RANGA_IDLE)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.RUN, TCorpAnimations.RANGA_RUN)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.KNEEL, TCorpAnimations.RANGA_KNEEL)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.SNEAK, TCorpAnimations.RANGA_SNEAK)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, TCorpAnimations.RANGA_GUARD)



            .specialAttack(Styles.ONE_HAND, TCorpSkills.RANGA_EVISCERATE)
            .specialAttack(Styles.TWO_HAND, TCorpSkills.RANGA_EVISCERATE)
            .specialAttack(TCorpStyles.FURIOSO, TCorpSkills.RANGA_EVISCERATE)
            .passiveSkill(TCorpSkills.BLACKSILENCE_PASSIVE)

            //.newStyleCombo(TCorpStyles.RANGA_EXHAUSTED, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SWORD_AUTO3, TCorpAnimations.RANGA_ATTACK_1, Animations.DAGGER_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, TCorpAnimations.RANGA_ATTACK_1, TCorpAnimations.RANGA_ATTACK_2, TCorpAnimations.RANGA_ATTACK_1, null, Animations.DAGGER_DUAL_AIR_SLASH)
            .newStyleCombo(TCorpStyles.FURIOSO, TCorpAnimations.RANGA_FURIOSO_1, TCorpAnimations.RANGA_FURIOSO_2, TCorpAnimations.RANGA_FURIOSO_3, null, Animations.DAGGER_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.DAGGER)
            .canBePlacedOffhand(true);

    public static final Function<Item, CapabilityItem.Builder> RANGA_DAGGER = (item) -> WeaponCapability.builder()
            .category(TCorpCategories.RANGA_DAGGER)
            .styleProvider((playerpatch) -> {

                if (playerpatch.getOriginal() instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) playerpatch.getOriginal();
                    if (player.getCooldowns().isOnCooldown(player.getMainHandItem().getItem()))
                        return Styles.ONE_HAND;
                }

                if (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10)
                    return TCorpStyles.FURIOSO;

                return playerpatch.getHoldingItemCapability(Hand.OFF_HAND).getWeaponCategory() == TCorpCategories.RANGA ? Styles.TWO_HAND : Styles.ONE_HAND;
            })
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == TCorpCategories.RANGA)
            .collider(ColliderPreset.DAGGER)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, TCorpAnimations.RANGA_WALK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, TCorpAnimations.RANGA_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, TCorpAnimations.RANGA_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, TCorpAnimations.RANGA_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, TCorpAnimations.RANGA_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, TCorpAnimations.RANGA_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, TCorpAnimations.RANGA_KNEEL)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.KNEEL, TCorpAnimations.RANGA_KNEEL)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, TCorpAnimations.RANGA_SNEAK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, TCorpAnimations.RANGA_SNEAK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, TCorpAnimations.RANGA_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, TCorpAnimations.RANGA_GUARD)

            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.WALK, TCorpAnimations.RANGA_WALK)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.IDLE, TCorpAnimations.RANGA_IDLE)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.RUN, TCorpAnimations.RANGA_RUN)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.KNEEL, TCorpAnimations.RANGA_KNEEL)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.SNEAK, TCorpAnimations.RANGA_SNEAK)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, TCorpAnimations.RANGA_GUARD)

            .specialAttack(Styles.ONE_HAND, TCorpSkills.RANGA_EVISCERATE)
            .specialAttack(Styles.TWO_HAND, TCorpSkills.RANGA_EVISCERATE)
            .specialAttack(TCorpStyles.FURIOSO, TCorpSkills.RANGA_EVISCERATE)
            .passiveSkill(TCorpSkills.BLACKSILENCE_PASSIVE)

            .newStyleCombo(TCorpStyles.FURIOSO, TCorpAnimations.RANGA_FURIOSO_1, TCorpAnimations.RANGA_FURIOSO_2, TCorpAnimations.RANGA_FURIOSO_3, TCorpAnimations.RANGA_ATTACK_1, Animations.DAGGER_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, TCorpAnimations.RANGA_ATTACK_3_DEBOUNCE, TCorpAnimations.RANGA_ATTACK_3_DEBOUNCE, Animations.DAGGER_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.DAGGER)
            .canBePlacedOffhand(true);

    public static final Function<Item, CapabilityItem.Builder> ALLAS_WORKSHOP = (item) -> WeaponCapability.builder()
            .category(TCorpCategories.ALLAS_WORKSHOP)
            .styleProvider((playerpatch) -> (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10) ? TCorpStyles.FURIOSO : Styles.TWO_HAND)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, TCorpAnimations.ALLAS_GUARD)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, TCorpAnimations.ALLAS_GUARD)

            .collider(ColliderPreset.SPEAR)
            .hitSound(EpicFightSounds.BLADE_HIT)
            .passiveSkill(TCorpSkills.ALLAS_PASSIVE)
            .newStyleCombo(Styles.TWO_HAND, TCorpAnimations.ALLAS_ATTACK_1, TCorpAnimations.ALLAS_DASH_DEB, TCorpAnimations.ALLAS_DASH_DEB, TCorpAnimations.ALLAS_DASH_DEB)
            .newStyleCombo(TCorpStyles.FURIOSO, TCorpAnimations.ALLAS_DASH, TCorpAnimations.ALLAS_ATTACK_1, TCorpAnimations.ALLAS_DASH_DEB, TCorpAnimations.ALLAS_DASH_DEB, TCorpAnimations.ALLAS_DASH_DEB)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SPEAR);



    public static final Function<Item, CapabilityItem.Builder> ATELIER_REVOLVER = (item) -> WeaponCapability.builder()
            .category(TCorpCategories.ATELIER_REVOLVER)
            .styleProvider((playerpatch) -> {
                if (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10)
                    return TCorpStyles.FURIOSO;

                return playerpatch.getHoldingItemCapability(Hand.OFF_HAND).getWeaponCategory() == TCorpCategories.ATELIER_REVOLVER ? Styles.TWO_HAND : Styles.ONE_HAND;
            })
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == TCorpCategories.ATELIER_REVOLVER)
            .collider(ColliderPreset.DAGGER)
            .hitSound(EpicFightSounds.BLUNT_HIT)

            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, TCorpAnimations.ATELIER_REVOLVER_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, TCorpAnimations.ATELIER_REVOLVER_GUARD)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, TCorpAnimations.ATELIER_REVOLVER_GUARD)

            .passiveSkill(TCorpSkills.ATELIER_PASSIVE)

            .newStyleCombo(Styles.ONE_HAND, Animations.SWORD_DUAL_AUTO1, Animations.SWORD_DUAL_AUTO2, Animations.SWORD_DUAL_AUTO2, Animations.DAGGER_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, Animations.SWORD_DUAL_AUTO1, Animations.SWORD_DUAL_AUTO2, Animations.SWORD_DUAL_AUTO2, Animations.DAGGER_AIR_SLASH)
            .newStyleCombo(TCorpStyles.FURIOSO, Animations.SWORD_DUAL_AUTO1, Animations.SWORD_DUAL_AUTO2, Animations.SWORD_DUAL_AUTO2, Animations.DAGGER_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.DAGGER)
            .canBePlacedOffhand(true);

    public static final Function<Item, CapabilityItem.Builder> ATELIER_SHOTGUN = (item) -> WeaponCapability.builder()
            .category(TCorpCategories.ATELIER_SHOTGUN)
            .styleProvider((playerpatch) -> {
                if (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10)
                    return TCorpStyles.FURIOSO;
                return Styles.TWO_HAND;
            })
            .collider(RIFLE)
            .hitSound(EpicFightSounds.BLUNT_HIT)

            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, TCorpAnimations.ATELIER_SHOTGUN_IDLE)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.IDLE, TCorpAnimations.ATELIER_SHOTGUN_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, TCorpAnimations.ATELIER_SHOTGUN_WALK)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.WALK, TCorpAnimations.ATELIER_SHOTGUN_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, TCorpAnimations.ATELIER_SHOTGUN_RUN)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.RUN, TCorpAnimations.ATELIER_SHOTGUN_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, TCorpAnimations.ATELIER_SHOTGUN_GUARD)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, TCorpAnimations.ATELIER_SHOTGUN_GUARD)
            .passiveSkill(TCorpSkills.ATELIER_SHOTGUN_PASSIVE)

            .newStyleCombo(Styles.TWO_HAND, TCorpAnimations.ATELIER_SHOTGUN_AUTO_1, TCorpAnimations.ATELIER_SHOTGUN_AUTO_2, TCorpAnimations.ATELIER_SHOTGUN_AUTO_3, TCorpAnimations.ATELIER_SHOTGUN_AUTO_1, TCorpAnimations.ATELIER_SHOTGUN_AUTO_2)
            .newStyleCombo(TCorpStyles.FURIOSO, TCorpAnimations.ATELIER_SHOTGUN_AUTO_1, TCorpAnimations.ATELIER_SHOTGUN_AUTO_2, TCorpAnimations.ATELIER_SHOTGUN_AUTO_3, TCorpAnimations.ATELIER_SHOTGUN_AUTO_1, TCorpAnimations.ATELIER_SHOTGUN_AUTO_2)

            .canBePlacedOffhand(false);

    public static final Function<Item, CapabilityItem.Builder> SOLEMN_LAMENT = (item) -> WeaponCapability.builder()
            .category(TCorpCategories.SOLEMN_LAMENT)
            .styleProvider((playerpatch) -> {

                boolean leftHandValid = true;
                boolean rightHandValid = true;
                if (!playerpatch.getOriginal().getMainHandItem().getItem().equals(TCorpItems.SOLEMN_LAMENT_WHITE.get())) {
                    rightHandValid = false;
                }

                if (!playerpatch.getOriginal().getOffhandItem().getItem().equals(TCorpItems.SOLEMN_LAMENT_BLACK.get())) {
                    leftHandValid = false;
                }

                if (!(rightHandValid || leftHandValid)) {
                    return Styles.ONE_HAND;
                }


                if ((SolemnLamentEffects.getAmmoCount(playerpatch.getOriginal(), SolemnLamentEffects.getLiving()) <= 0)) {
                    rightHandValid = false;
                }

                if ((SolemnLamentEffects.getAmmoCount(playerpatch.getOriginal(), SolemnLamentEffects.getDeparted()) <= 0)) {
                    leftHandValid = false;
                }


                if (rightHandValid) {
                    return (leftHandValid) ? TCorpStyles.DUAL_WIELDED : TCorpStyles.RIGHT_HANDED;
                } else if (leftHandValid) {
                    return TCorpStyles.LEFT_HANDED;
                } else {
                    return Styles.ONE_HAND;
                }
            }
            )
            .collider(SOLEMN_LAMENT_HITBOX)
            .specialAttack(TCorpStyles.DUAL_WIELDED, TCorpSkills.SOLEMN_LAMENT_BURST)
            .specialAttack(TCorpStyles.LEFT_HANDED, TCorpSkills.SOLEMN_LAMENT_BURST)
            .specialAttack(TCorpStyles.RIGHT_HANDED, TCorpSkills.SOLEMN_LAMENT_BURST)
            .specialAttack(Styles.ONE_HAND, TCorpSkills.SOLEMN_LAMENT_BURST)
            .hitSound(EpicFightSounds.BLUNT_HIT)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == TCorpCategories.SOLEMN_LAMENT)
            .livingMotionModifier(TCorpStyles.DUAL_WIELDED, LivingMotions.IDLE, TCorpAnimations.SOLEMN_LAMENT_IDLE)
            .livingMotionModifier(TCorpStyles.DUAL_WIELDED, LivingMotions.WALK, TCorpAnimations.SOLEMN_LAMENT_WALK)
            .livingMotionModifier(TCorpStyles.DUAL_WIELDED, LivingMotions.SNEAK, TCorpAnimations.SOLEMN_LAMENT_SNEAK)
            .livingMotionModifier(TCorpStyles.DUAL_WIELDED, LivingMotions.KNEEL, TCorpAnimations.SOLEMN_LAMENT_KNEEL)
            .livingMotionModifier(TCorpStyles.DUAL_WIELDED, LivingMotions.RUN, TCorpAnimations.SOLEMN_LAMENT_RUN)
            .livingMotionModifier(TCorpStyles.DUAL_WIELDED, LivingMotions.JUMP, TCorpAnimations.SOLEMN_LAMENT_JUMP)
            .livingMotionModifier(TCorpStyles.DUAL_WIELDED, LivingMotions.BLOCK, TCorpAnimations.SOLEMN_LAMENT_GUARD)

            .livingMotionModifier(TCorpStyles.RIGHT_HANDED, LivingMotions.IDLE, TCorpAnimations.SOLEMN_LAMENT_IDLE)
            .livingMotionModifier(TCorpStyles.RIGHT_HANDED, LivingMotions.WALK, TCorpAnimations.SOLEMN_LAMENT_WALK)
            .livingMotionModifier(TCorpStyles.RIGHT_HANDED, LivingMotions.SNEAK, TCorpAnimations.SOLEMN_LAMENT_SNEAK)
            .livingMotionModifier(TCorpStyles.RIGHT_HANDED, LivingMotions.KNEEL, TCorpAnimations.SOLEMN_LAMENT_KNEEL)
            .livingMotionModifier(TCorpStyles.RIGHT_HANDED, LivingMotions.RUN, TCorpAnimations.SOLEMN_LAMENT_RUN)
            .livingMotionModifier(TCorpStyles.RIGHT_HANDED, LivingMotions.JUMP, TCorpAnimations.SOLEMN_LAMENT_JUMP)
            .livingMotionModifier(TCorpStyles.RIGHT_HANDED, LivingMotions.BLOCK, TCorpAnimations.SOLEMN_LAMENT_GUARD)

            .livingMotionModifier(TCorpStyles.LEFT_HANDED, LivingMotions.IDLE, TCorpAnimations.SOLEMN_LAMENT_IDLE)
            .livingMotionModifier(TCorpStyles.LEFT_HANDED, LivingMotions.WALK, TCorpAnimations.SOLEMN_LAMENT_WALK)
            .livingMotionModifier(TCorpStyles.LEFT_HANDED, LivingMotions.SNEAK, TCorpAnimations.SOLEMN_LAMENT_SNEAK)
            .livingMotionModifier(TCorpStyles.LEFT_HANDED, LivingMotions.KNEEL, TCorpAnimations.SOLEMN_LAMENT_KNEEL)
            .livingMotionModifier(TCorpStyles.LEFT_HANDED, LivingMotions.RUN, TCorpAnimations.SOLEMN_LAMENT_RUN)
            .livingMotionModifier(TCorpStyles.LEFT_HANDED, LivingMotions.JUMP, TCorpAnimations.SOLEMN_LAMENT_JUMP)
            .livingMotionModifier(TCorpStyles.LEFT_HANDED, LivingMotions.BLOCK, TCorpAnimations.SOLEMN_LAMENT_GUARD)

            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, TCorpAnimations.SOLEMN_LAMENT_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, TCorpAnimations.SOLEMN_LAMENT_WALK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, TCorpAnimations.SOLEMN_LAMENT_SNEAK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.KNEEL, TCorpAnimations.SOLEMN_LAMENT_KNEEL)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, TCorpAnimations.SOLEMN_LAMENT_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.JUMP, TCorpAnimations.SOLEMN_LAMENT_JUMP)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, TCorpAnimations.SOLEMN_LAMENT_GUARD)
            .passiveSkill(TCorpSkills.SOLEMN_LAMENT_PASSIVE)
            //.specialAttack(TCorpStyles.DUAL_WIELDED, TCorpSkills.MAGIC_BULLET_DETONATE)
            .newStyleCombo(TCorpStyles.DUAL_WIELDED, TCorpAnimations.SOLEMN_LAMENT_AUTO_D0, TCorpAnimations.SOLEMN_LAMENT_AUTO_D1, TCorpAnimations.SOLEMN_LAMENT_AUTO_D2, TCorpAnimations.SOLEMN_LAMENT_AUTO_D3, TCorpAnimations.SOLEMN_LAMENT_AUTO_D4, TCorpAnimations.SOLEMN_LAMENT_AUTO_D5, TCorpAnimations.SOLEMN_LAMENT_AUTO_D4, TCorpAnimations.SOLEMN_LAMENT_AUTO_D5, TCorpAnimations.SOLEMN_LAMENT_AUTO_D4, TCorpAnimations.SOLEMN_LAMENT_AUTO_D5, TCorpAnimations.SOLEMN_LAMENT_AUTO_D4, TCorpAnimations.SOLEMN_LAMENT_AUTO_D5, TCorpAnimations.SOLEMN_LAMENT_AUTO_D4, TCorpAnimations.SOLEMN_LAMENT_AUTO_D5, TCorpAnimations.SOLEMN_LAMENT_AUTO_D4, TCorpAnimations.SOLEMN_LAMENT_AUTO_D5, TCorpAnimations.SOLEMN_LAMENT_AUTO_D4, TCorpAnimations.SOLEMN_LAMENT_AUTO_D5, TCorpAnimations.SOLEMN_LAMENT_DASH_L, TCorpAnimations.SOLEMN_LAMENT_JUMP_ATTACK)
            .newStyleCombo(TCorpStyles.RIGHT_HANDED, TCorpAnimations.SOLEMN_LAMENT_AUTO_R0, TCorpAnimations.SOLEMN_LAMENT_AUTO_R1, TCorpAnimations.SOLEMN_LAMENT_AUTO_R2, TCorpAnimations.SOLEMN_LAMENT_AUTO_R2, TCorpAnimations.SOLEMN_LAMENT_AUTO_R2, TCorpAnimations.SOLEMN_LAMENT_AUTO_R2, TCorpAnimations.SOLEMN_LAMENT_AUTO_R2, TCorpAnimations.SOLEMN_LAMENT_AUTO_R2, TCorpAnimations.SOLEMN_LAMENT_AUTO_R2, TCorpAnimations.SOLEMN_LAMENT_AUTO_R2, TCorpAnimations.SOLEMN_LAMENT_AUTO_R2, TCorpAnimations.SOLEMN_LAMENT_AUTO_R2, TCorpAnimations.SOLEMN_LAMENT_AUTO_R2, TCorpAnimations.SOLEMN_LAMENT_AUTO_R2, TCorpAnimations.SOLEMN_LAMENT_AUTO_R2, TCorpAnimations.SOLEMN_LAMENT_AUTO_R2, TCorpAnimations.SOLEMN_LAMENT_AUTO_R2, TCorpAnimations.SOLEMN_LAMENT_AUTO_R2, TCorpAnimations.SOLEMN_LAMENT_DASH_R, TCorpAnimations.SOLEMN_LAMENT_JUMP_ATTACK)
            .newStyleCombo(TCorpStyles.LEFT_HANDED, TCorpAnimations.SOLEMN_LAMENT_AUTO_L0, TCorpAnimations.SOLEMN_LAMENT_AUTO_L1, TCorpAnimations.SOLEMN_LAMENT_AUTO_L2, TCorpAnimations.SOLEMN_LAMENT_AUTO_L2, TCorpAnimations.SOLEMN_LAMENT_AUTO_L2, TCorpAnimations.SOLEMN_LAMENT_AUTO_L2, TCorpAnimations.SOLEMN_LAMENT_AUTO_L2, TCorpAnimations.SOLEMN_LAMENT_AUTO_L2, TCorpAnimations.SOLEMN_LAMENT_AUTO_L2, TCorpAnimations.SOLEMN_LAMENT_AUTO_L2, TCorpAnimations.SOLEMN_LAMENT_AUTO_L2, TCorpAnimations.SOLEMN_LAMENT_AUTO_L2, TCorpAnimations.SOLEMN_LAMENT_AUTO_L2, TCorpAnimations.SOLEMN_LAMENT_AUTO_L2, TCorpAnimations.SOLEMN_LAMENT_AUTO_L2, TCorpAnimations.SOLEMN_LAMENT_AUTO_L2, TCorpAnimations.SOLEMN_LAMENT_AUTO_L2, TCorpAnimations.SOLEMN_LAMENT_AUTO_L2, TCorpAnimations.SOLEMN_LAMENT_DASH_L, TCorpAnimations.SOLEMN_LAMENT_JUMP_ATTACK)
            .newStyleCombo(Styles.ONE_HAND, TCorpAnimations.SOLEMN_LAMENT_MELEE_ATTACK, TCorpAnimations.SOLEMN_LAMENT_MELEE_ATTACK, TCorpAnimations.SOLEMN_LAMENT_JUMP_ATTACK)

            .canBePlacedOffhand(true);

    public static final Function<Item, CapabilityItem.Builder> MAGIC_BULLET = (item) -> WeaponCapability.builder()
            .category(TCorpCategories.MAGIC_BULLET)
            .styleProvider((playerpatch) -> {
                return Styles.TWO_HAND;
            })
            .collider(RIFLE)
            .hitSound(EpicFightSounds.BLUNT_HIT)

            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, TCorpAnimations.MAGIC_BULLET_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, TCorpAnimations.MAGIC_BULLET_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, TCorpAnimations.MAGIC_BULLET_SNEAK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, TCorpAnimations.MAGIC_BULLET_KNEEL)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, TCorpAnimations.MAGIC_BULLET_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, TCorpAnimations.MAGIC_BULLET_JUMP)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, TCorpAnimations.MAGIC_BULLET_GUARD)
            .passiveSkill(TCorpSkills.MAGIC_BULLET_PASSIVE)
            .specialAttack(Styles.TWO_HAND, TCorpSkills.MAGIC_BULLET_DETONATE)

            .newStyleCombo(Styles.TWO_HAND, TCorpAnimations.MAGIC_BULLET_AUTO_1, TCorpAnimations.MAGIC_BULLET_AUTO_2, TCorpAnimations.MAGIC_BULLET_AUTO_3, TCorpAnimations.MAGIC_BULLET_DASH, TCorpAnimations.MAGIC_BULLET_JUMP_ATTACK)

            .canBePlacedOffhand(false);
    public TCorpCapabilityPresets() {
    }

    @SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put("durandal", DURANDAL);
        event.getTypeEntry().put("mimicry", MIMICRY);
        event.getTypeEntry().put("crystal_atelier", CRYSTAL_ATELIER);
        event.getTypeEntry().put("zelkova_mace", ZELKOVA_MACE);
        event.getTypeEntry().put("zelkova_axe", ZELKOVA_AXE);
        event.getTypeEntry().put("ranga_claw", RANGA_CLAW);
        event.getTypeEntry().put("ranga_dagger", RANGA_DAGGER);
        event.getTypeEntry().put("allas_workshop", ALLAS_WORKSHOP);
        event.getTypeEntry().put("atelier_revolver", ATELIER_REVOLVER);
        event.getTypeEntry().put("atelier_shotgun", ATELIER_SHOTGUN);
        event.getTypeEntry().put("mook_workshop", MOOK_WORKSHOP);
        event.getTypeEntry().put("old_boys_workshop", OLD_BOYS);
        event.getTypeEntry().put("wheels_industry", WHEELS_INDUSTRY);
        event.getTypeEntry().put("magic_bullet", MAGIC_BULLET);
        event.getTypeEntry().put("solemn_lament", SOLEMN_LAMENT);
    }
}

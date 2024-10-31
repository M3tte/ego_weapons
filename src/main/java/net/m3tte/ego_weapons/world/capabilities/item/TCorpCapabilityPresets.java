//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.world.capabilities.item;

import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsSkills;
import net.m3tte.ego_weapons.potion.FuriosoPotionEffect;
import net.m3tte.ego_weapons.potion.SolemnLamentEffects;
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
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

public class TCorpCapabilityPresets {


    public static final Collider LONGER_BLADE = new MultiOBBCollider(4, 0.4, 0.6, 0.9, 0.0, 0.0, -0.8);
    public static final Collider SPLIT_HORIZONTAL = new MultiOBBCollider(4, 0.4, 0.6, 1.1, 0.0, 0.0, -0.8);

    public static final Collider SUNSHOWER_COL = new MultiOBBCollider(4, 0.4, 0.6, 1, 0.0, 0.0, -1);
    public static final Collider SUNSHOWER_COL_LARGE = new MultiOBBCollider(4, 0.8, 0.8, 0.6, 0.0, 0.0, -0.6);

    public static final Collider RIFLE = new MultiOBBCollider(4, 0.2, 1, 0.2, 0, 0.0, -0.4);

    public static final Collider DoubtBlade = new MultiOBBCollider(4, 0.2, 1.3, 0.2, 0, 0.3, 0);
    public static final Collider NTBlade = new MultiOBBCollider(4, 0.7, 1.5, 1.3, 0, 0.3, 0);


    public static final Collider SOLEMN_LAMENT_HITBOX = new MultiOBBCollider(4, 0.6, 2.8, 0.6, 0, -2.5, 0);
    public static final Collider SOLEMN_LAMENT_HITBOX_EXT = new MultiOBBCollider(4, 0.6, 3.3, 0.6, 0, -3.3, 0);
    public static final Function<Item, CapabilityItem.Builder> MOOK_WORKSHOP = (item) ->
            WeaponCapability.builder().category(TCorpCategories.MOOK_WORKSHOP).canBePlacedOffhand(false).styleProvider((playerpatch) ->
                            (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10) ? TCorpStyles.FURIOSO : Styles.TWO_HAND)

                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, EgoWeaponsAnimations.MOOK_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, EgoWeaponsAnimations.MOOK_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, EgoWeaponsAnimations.MOOK_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, EgoWeaponsAnimations.MOOK_KNEEL)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, EgoWeaponsAnimations.MOOK_SNEAK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, EgoWeaponsAnimations.MOOK_GUARD)

                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.IDLE, EgoWeaponsAnimations.MOOK_IDLE)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.WALK, EgoWeaponsAnimations.MOOK_WALK)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.RUN, EgoWeaponsAnimations.MOOK_RUN)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.KNEEL, EgoWeaponsAnimations.MOOK_KNEEL)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.SNEAK, EgoWeaponsAnimations.MOOK_SNEAK)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, EgoWeaponsAnimations.MOOK_GUARD)
                    .collider(ColliderPreset.SWORD)
                    .passiveSkill(EgoWeaponsSkills.BASIC_BLOCKABLE_PASSIVE)
                    .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.MOOK_CUT)
                    .specialAttack(TCorpStyles.FURIOSO, EgoWeaponsSkills.MOOK_CUT)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(TCorpStyles.FURIOSO, EgoWeaponsAnimations.MOOK_CUT, EgoWeaponsAnimations.MOOK_CUT, EgoWeaponsAnimations.MOOK_CUT)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(Styles.TWO_HAND, EgoWeaponsAnimations.MOOK_AUTO_1, EgoWeaponsAnimations.MOOK_AUTO_2, EgoWeaponsAnimations.MOOK_AUTO_3, EgoWeaponsAnimations.MOOK_AUTO_4, Animations.AXE_DASH, EgoWeaponsAnimations.MOOK_AUTO_JUMP)


                    .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SWORD);

    public static final Function<Item, CapabilityItem.Builder> DURANDAL = (item) ->
            WeaponCapability.builder().category(TCorpCategories.DURANDAL).canBePlacedOffhand(false).styleProvider((playerpatch) ->
                            (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10) ? TCorpStyles.FURIOSO : Styles.TWO_HAND)

                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, EgoWeaponsAnimations.DURANDAL_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, EgoWeaponsAnimations.DURANDAL_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, EgoWeaponsAnimations.DURANDAL_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, EgoWeaponsAnimations.DURANDAL_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, EgoWeaponsAnimations.DURANDAL_GUARD)

                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.IDLE, EgoWeaponsAnimations.DURANDAL_IDLE)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.WALK, EgoWeaponsAnimations.DURANDAL_IDLE)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.RUN, EgoWeaponsAnimations.DURANDAL_RUN)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.KNEEL, EgoWeaponsAnimations.DURANDAL_IDLE)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, EgoWeaponsAnimations.DURANDAL_GUARD)
                    .collider(LONGER_BLADE)
                    .passiveSkill(EgoWeaponsSkills.BASIC_BLOCKABLE_PASSIVE)
                    .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.LESSER_SPLIT_VERTICAL)
                    .specialAttack(TCorpStyles.FURIOSO, EgoWeaponsSkills.LESSER_SPLIT_VERTICAL)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(TCorpStyles.FURIOSO, EgoWeaponsAnimations.DURANDAL_FURIOSO_1, EgoWeaponsAnimations.DURANDAL_FURIOSO_2, EgoWeaponsAnimations.DURANDAL_FURIOSO_3, Animations.AXE_DASH, Animations.SWORD_AIR_SLASH)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(Styles.TWO_HAND, EgoWeaponsAnimations.DURANDAL_ATTACK_1, Animations.KATANA_AUTO1, Animations.KATANA_AUTO2, Animations.AXE_DASH, Animations.AXE_DASH, Animations.SWORD_AIR_SLASH)

                    .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SWORD);

    public static final Function<Item, CapabilityItem.Builder> MIMICRY = (item) ->
            WeaponCapability.builder().category(TCorpCategories.MIMICRY).canBePlacedOffhand(false).styleProvider((playerpatch) ->
                            (playerpatch.getOriginal().getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.JACKET_OF_THE_RED_MIST.get()) ? TCorpStyles.KALI :
                                    playerpatch.getOriginal().getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.RED_MIST_EGO_CHESTPLATE.get()) ? TCorpStyles.KALI_EGO : Styles.TWO_HAND))

                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, EgoWeaponsAnimations.MIMICRY_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, EgoWeaponsAnimations.MIMICRY_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, EgoWeaponsAnimations.MIMICRY_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, EgoWeaponsAnimations.MIMICRY_KNEEL)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, EgoWeaponsAnimations.MIMICRY_SNEAK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, EgoWeaponsAnimations.MIMICRY_GUARD)

                    .livingMotionModifier(TCorpStyles.KALI, LivingMotions.IDLE, EgoWeaponsAnimations.KALI_IDLE)
                    .livingMotionModifier(TCorpStyles.KALI, LivingMotions.WALK, EgoWeaponsAnimations.KALI_WALK)
                    .livingMotionModifier(TCorpStyles.KALI, LivingMotions.RUN, EgoWeaponsAnimations.KALI_RUN)
                    .livingMotionModifier(TCorpStyles.KALI, LivingMotions.KNEEL, EgoWeaponsAnimations.KALI_KNEEL)
                    .livingMotionModifier(TCorpStyles.KALI, LivingMotions.SNEAK, EgoWeaponsAnimations.KALI_SNEAK)
                    .livingMotionModifier(TCorpStyles.KALI, LivingMotions.BLOCK, EgoWeaponsAnimations.KALI_GUARD)

                    .livingMotionModifier(TCorpStyles.KALI_EGO, LivingMotions.IDLE, EgoWeaponsAnimations.KALI_EGO_IDLE)
                    .livingMotionModifier(TCorpStyles.KALI_EGO, LivingMotions.WALK, EgoWeaponsAnimations.KALI_EGO_WALK)
                    .livingMotionModifier(TCorpStyles.KALI_EGO, LivingMotions.RUN, EgoWeaponsAnimations.KALI_EGO_RUN)
                    .livingMotionModifier(TCorpStyles.KALI_EGO, LivingMotions.KNEEL, EgoWeaponsAnimations.KALI_EGO_KNEEL)
                    .livingMotionModifier(TCorpStyles.KALI_EGO, LivingMotions.SNEAK, EgoWeaponsAnimations.KALI_EGO_SNEAK)
                    .livingMotionModifier(TCorpStyles.KALI_EGO, LivingMotions.BLOCK, EgoWeaponsAnimations.KALI_GUARD)

                    .collider(LONGER_BLADE)
                    .passiveSkill(EgoWeaponsSkills.KALI_PASSIVE)
                    .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.HELLO)
                    .specialAttack(TCorpStyles.KALI, EgoWeaponsSkills.GREATER_SPLIT_VERTICAL)
                    .specialAttack(TCorpStyles.KALI_EGO, EgoWeaponsSkills.GREATER_SPLIT_VERTICAL)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(TCorpStyles.KALI_EGO, EgoWeaponsAnimations.KALI_EGO_AUTO_1, EgoWeaponsAnimations.KALI_EGO_AUTO_2, EgoWeaponsAnimations.KALI_AUTO_3, EgoWeaponsAnimations.KALI_AUTO_4, EgoWeaponsAnimations.KALI_DASH, EgoWeaponsAnimations.KALI_JUMP)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(TCorpStyles.KALI, EgoWeaponsAnimations.KALI_AUTO_1, EgoWeaponsAnimations.KALI_AUTO_2, EgoWeaponsAnimations.KALI_AUTO_3, EgoWeaponsAnimations.KALI_AUTO_4, EgoWeaponsAnimations.KALI_DASH, EgoWeaponsAnimations.KALI_JUMP)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(Styles.TWO_HAND, EgoWeaponsAnimations.MIMICRY_AUTO_1, EgoWeaponsAnimations.MIMICRY_AUTO_2, EgoWeaponsAnimations.MIMICRY_AUTO_3, EgoWeaponsAnimations.MIMICRY_DASH, EgoWeaponsAnimations.MIMICRY_AUTO_3)

                    .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SWORD);
    public static final Function<Item, CapabilityItem.Builder> WHEELS_INDUSTRY = (item) ->
            WeaponCapability.builder().category(TCorpCategories.WHEELS_INDUSTRY).canBePlacedOffhand(false).styleProvider((playerpatch) ->
                            (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10) ? TCorpStyles.FURIOSO : Styles.TWO_HAND)

                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, EgoWeaponsAnimations.WHEELS_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, EgoWeaponsAnimations.WHEELS_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, EgoWeaponsAnimations.WHEELS_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, EgoWeaponsAnimations.WHEELS_CROUCH)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, EgoWeaponsAnimations.WHEELS_SNEAK)
                    .collider(LONGER_BLADE)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.IDLE, EgoWeaponsAnimations.WHEELS_IDLE)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.WALK, EgoWeaponsAnimations.WHEELS_WALK)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.RUN, EgoWeaponsAnimations.WHEELS_RUN)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.KNEEL, EgoWeaponsAnimations.WHEELS_CROUCH)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD)
                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.SNEAK, EgoWeaponsAnimations.WHEELS_SNEAK)

                    .passiveSkill(EgoWeaponsSkills.BLACKSILENCE_PASSIVE)
                    .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.WHEELS_SMASH)
                    .specialAttack(TCorpStyles.FURIOSO, EgoWeaponsSkills.WHEELS_SMASH)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(TCorpStyles.FURIOSO, EgoWeaponsAnimations.WHEELS_HEAVY, EgoWeaponsAnimations.WHEELS_HEAVY, EgoWeaponsAnimations.WHEELS_HEAVY)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(Styles.TWO_HAND, EgoWeaponsAnimations.WHEELS_AUTO_1, EgoWeaponsAnimations.WHEELS_AUTO_2, EgoWeaponsAnimations.WHEELS_AUTO_3, EgoWeaponsAnimations.WHEELS_DASH, EgoWeaponsAnimations.WHEELS_AUTO_3);

    public static final Function<Item, CapabilityItem.Builder> OLD_BOYS = (item) ->
            WeaponCapability.builder().category(TCorpCategories.OLD_BOYS).canBePlacedOffhand(false).styleProvider((playerpatch) ->
                            (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10) ? TCorpStyles.FURIOSO : Styles.ONE_HAND)


                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, EgoWeaponsAnimations.OLD_BOYS_GUARD)

                    .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, EgoWeaponsAnimations.OLD_BOYS_GUARD)

                    .passiveSkill(EgoWeaponsSkills.OLD_BOYS_PASSIVE)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(TCorpStyles.FURIOSO, EgoWeaponsAnimations.OLD_BOYS_FURIOSO, EgoWeaponsAnimations.OLD_BOYS_FURIOSO, EgoWeaponsAnimations.OLD_BOYS_FURIOSO)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(Styles.ONE_HAND, EgoWeaponsAnimations.OLD_BOYS_AUTO_1, EgoWeaponsAnimations.OLD_BOYS_AUTO_2, EgoWeaponsAnimations.OLD_BOYS_AUTO_3, Animations.AXE_DASH, Animations.AXE_AIRSLASH)


                    .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SWORD);

    public static final Function<Item, CapabilityItem.Builder> CRYSTAL_ATELIER = (item) -> WeaponCapability.builder()
            .category(TCorpCategories.CRYSTAL_ATELIER)

            .styleProvider((playerpatch) -> {
                if (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10)
                    return TCorpStyles.FURIOSO;

                return playerpatch.getHoldingItemCapability(Hand.OFF_HAND).getWeaponCategory() == TCorpCategories.CRYSTAL_ATELIER ? Styles.TWO_HAND : Styles.ONE_HAND;
            })

            .passiveSkill(EgoWeaponsSkills.BASIC_BLOCKABLE_PASSIVE)
            //.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            //.specialAttack(Styles.TWO_HAND, TCorpSkills.LESSER_SPLIT_VERTICAL)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == TCorpCategories.CRYSTAL_ATELIER)
            .collider(ColliderPreset.SWORD)
            .hitSound(EpicFightSounds.BLADE_HIT)
            .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.CRYSTAL_ATELIER)
            .specialAttack(TCorpStyles.FURIOSO, EgoWeaponsSkills.CRYSTAL_ATELIER)
            .newStyleCombo(Styles.TWO_HAND, EgoWeaponsAnimations.CRYSTAL_ATELIER_DODGE, EgoWeaponsAnimations.CRYSTAL_ATELIER_INSTANT_DASH, EgoWeaponsAnimations.CRYSTAL_ATELIER_INSTANT_DASH_DEBOUNCE, EgoWeaponsAnimations.CRYSTAL_ATELIER_INSTANT_DASH_DEBOUNCE, Animations.SWORD_DUAL_AIR_SLASH)
            .newStyleCombo(TCorpStyles.FURIOSO, EgoWeaponsAnimations.CRYSTAL_ATELIER_FURIOSO, EgoWeaponsAnimations.CRYSTAL_ATELIER_DODGE, EgoWeaponsAnimations.CRYSTAL_ATELIER_INSTANT_DASH_DEBOUNCE, EgoWeaponsAnimations.CRYSTAL_ATELIER_FURIOSO, Animations.SWORD_DUAL_AIR_SLASH)
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
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, EgoWeaponsAnimations.ZELKOVA_IDLE)
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
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, EgoWeaponsAnimations.ZELKOVA_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, EgoWeaponsAnimations.ZELKOVA_IDLE)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.IDLE, EgoWeaponsAnimations.ZELKOVA_IDLE)
            .specialAttack(Styles.ONE_HAND, Skills.GUILLOTINE_AXE)
            .specialAttack(Styles.TWO_HAND, Skills.GUILLOTINE_AXE)
            .specialAttack(TCorpStyles.FURIOSO, Skills.GUILLOTINE_AXE)
            .newStyleCombo(Styles.ONE_HAND, Animations.AXE_AUTO1, Animations.AXE_AUTO2, Animations.AXE_DASH, Animations.AXE_AIRSLASH)
            .newStyleCombo(Styles.TWO_HAND, EgoWeaponsAnimations.ZELKOVA_ATTACK_1, EgoWeaponsAnimations.ZELKOVA_ATTACK_2, Animations.AXE_DASH, Animations.AXE_AIRSLASH)
            .newStyleCombo(TCorpStyles.FURIOSO, EgoWeaponsAnimations.ZELKOVA_FURIOSO_1, EgoWeaponsAnimations.ZELKOVA_FURIOSO_2, EgoWeaponsAnimations.ZELKOVA_FURIOSO_1, Animations.AXE_AIRSLASH)
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
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, EgoWeaponsAnimations.RANGA_WALK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, EgoWeaponsAnimations.RANGA_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, EgoWeaponsAnimations.RANGA_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, EgoWeaponsAnimations.RANGA_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, EgoWeaponsAnimations.RANGA_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, EgoWeaponsAnimations.RANGA_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, EgoWeaponsAnimations.RANGA_KNEEL)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.KNEEL, EgoWeaponsAnimations.RANGA_KNEEL)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, EgoWeaponsAnimations.RANGA_SNEAK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, EgoWeaponsAnimations.RANGA_SNEAK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, EgoWeaponsAnimations.RANGA_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, EgoWeaponsAnimations.RANGA_GUARD)

            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.WALK, EgoWeaponsAnimations.RANGA_WALK)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.IDLE, EgoWeaponsAnimations.RANGA_IDLE)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.RUN, EgoWeaponsAnimations.RANGA_RUN)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.KNEEL, EgoWeaponsAnimations.RANGA_KNEEL)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.SNEAK, EgoWeaponsAnimations.RANGA_SNEAK)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, EgoWeaponsAnimations.RANGA_GUARD)



            .specialAttack(Styles.ONE_HAND, EgoWeaponsSkills.RANGA_EVISCERATE)
            .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.RANGA_EVISCERATE)
            .specialAttack(TCorpStyles.FURIOSO, EgoWeaponsSkills.RANGA_EVISCERATE)
            .passiveSkill(EgoWeaponsSkills.BLACKSILENCE_PASSIVE)

            //.newStyleCombo(TCorpStyles.RANGA_EXHAUSTED, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SWORD_AUTO3, TCorpAnimations.RANGA_ATTACK_1, Animations.DAGGER_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, EgoWeaponsAnimations.RANGA_ATTACK_1, EgoWeaponsAnimations.RANGA_ATTACK_2, EgoWeaponsAnimations.RANGA_ATTACK_1, null, Animations.DAGGER_DUAL_AIR_SLASH)
            .newStyleCombo(TCorpStyles.FURIOSO, EgoWeaponsAnimations.RANGA_FURIOSO_1, EgoWeaponsAnimations.RANGA_FURIOSO_2, EgoWeaponsAnimations.RANGA_FURIOSO_3, null, Animations.DAGGER_DUAL_AIR_SLASH)
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
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, EgoWeaponsAnimations.RANGA_WALK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, EgoWeaponsAnimations.RANGA_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, EgoWeaponsAnimations.RANGA_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, EgoWeaponsAnimations.RANGA_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, EgoWeaponsAnimations.RANGA_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, EgoWeaponsAnimations.RANGA_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, EgoWeaponsAnimations.RANGA_KNEEL)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.KNEEL, EgoWeaponsAnimations.RANGA_KNEEL)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, EgoWeaponsAnimations.RANGA_SNEAK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, EgoWeaponsAnimations.RANGA_SNEAK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, EgoWeaponsAnimations.RANGA_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, EgoWeaponsAnimations.RANGA_GUARD)

            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.WALK, EgoWeaponsAnimations.RANGA_WALK)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.IDLE, EgoWeaponsAnimations.RANGA_IDLE)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.RUN, EgoWeaponsAnimations.RANGA_RUN)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.KNEEL, EgoWeaponsAnimations.RANGA_KNEEL)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.SNEAK, EgoWeaponsAnimations.RANGA_SNEAK)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, EgoWeaponsAnimations.RANGA_GUARD)

            .specialAttack(Styles.ONE_HAND, EgoWeaponsSkills.RANGA_EVISCERATE)
            .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.RANGA_EVISCERATE)
            .specialAttack(TCorpStyles.FURIOSO, EgoWeaponsSkills.RANGA_EVISCERATE)
            .passiveSkill(EgoWeaponsSkills.BLACKSILENCE_PASSIVE)

            .newStyleCombo(TCorpStyles.FURIOSO, EgoWeaponsAnimations.RANGA_FURIOSO_1, EgoWeaponsAnimations.RANGA_FURIOSO_2, EgoWeaponsAnimations.RANGA_FURIOSO_3, EgoWeaponsAnimations.RANGA_ATTACK_1, Animations.DAGGER_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, EgoWeaponsAnimations.RANGA_ATTACK_3_DEBOUNCE, EgoWeaponsAnimations.RANGA_ATTACK_3_DEBOUNCE, Animations.DAGGER_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.DAGGER)
            .canBePlacedOffhand(true);

    public static final Function<Item, CapabilityItem.Builder> ALLAS_WORKSHOP = (item) -> WeaponCapability.builder()
            .category(TCorpCategories.ALLAS_WORKSHOP)
            .styleProvider((playerpatch) -> (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10) ? TCorpStyles.FURIOSO : Styles.TWO_HAND)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, EgoWeaponsAnimations.ALLAS_GUARD)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, EgoWeaponsAnimations.ALLAS_GUARD)

            .collider(ColliderPreset.SPEAR)
            .hitSound(EpicFightSounds.BLADE_HIT)
            .passiveSkill(EgoWeaponsSkills.ALLAS_PASSIVE)
            .newStyleCombo(Styles.TWO_HAND, EgoWeaponsAnimations.ALLAS_ATTACK_1, EgoWeaponsAnimations.ALLAS_DASH_DEB, EgoWeaponsAnimations.ALLAS_DASH_DEB, EgoWeaponsAnimations.ALLAS_DASH_DEB)
            .newStyleCombo(TCorpStyles.FURIOSO, EgoWeaponsAnimations.ALLAS_DASH, EgoWeaponsAnimations.ALLAS_ATTACK_1, EgoWeaponsAnimations.ALLAS_DASH_DEB, EgoWeaponsAnimations.ALLAS_DASH_DEB, EgoWeaponsAnimations.ALLAS_DASH_DEB)
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

            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, EgoWeaponsAnimations.ATELIER_REVOLVER_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, EgoWeaponsAnimations.ATELIER_REVOLVER_GUARD)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, EgoWeaponsAnimations.ATELIER_REVOLVER_GUARD)

            .passiveSkill(EgoWeaponsSkills.ATELIER_PASSIVE)

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

            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, EgoWeaponsAnimations.ATELIER_SHOTGUN_IDLE)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.IDLE, EgoWeaponsAnimations.ATELIER_SHOTGUN_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, EgoWeaponsAnimations.ATELIER_SHOTGUN_WALK)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.WALK, EgoWeaponsAnimations.ATELIER_SHOTGUN_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, EgoWeaponsAnimations.ATELIER_SHOTGUN_RUN)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.RUN, EgoWeaponsAnimations.ATELIER_SHOTGUN_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, EgoWeaponsAnimations.ATELIER_SHOTGUN_GUARD)
            .livingMotionModifier(TCorpStyles.FURIOSO, LivingMotions.BLOCK, EgoWeaponsAnimations.ATELIER_SHOTGUN_GUARD)
            .passiveSkill(EgoWeaponsSkills.ATELIER_SHOTGUN_PASSIVE)

            .newStyleCombo(Styles.TWO_HAND, EgoWeaponsAnimations.ATELIER_SHOTGUN_AUTO_1, EgoWeaponsAnimations.ATELIER_SHOTGUN_AUTO_2, EgoWeaponsAnimations.ATELIER_SHOTGUN_AUTO_3, EgoWeaponsAnimations.ATELIER_SHOTGUN_AUTO_1, EgoWeaponsAnimations.ATELIER_SHOTGUN_AUTO_2)
            .newStyleCombo(TCorpStyles.FURIOSO, EgoWeaponsAnimations.ATELIER_SHOTGUN_AUTO_1, EgoWeaponsAnimations.ATELIER_SHOTGUN_AUTO_2, EgoWeaponsAnimations.ATELIER_SHOTGUN_AUTO_3, EgoWeaponsAnimations.ATELIER_SHOTGUN_AUTO_1, EgoWeaponsAnimations.ATELIER_SHOTGUN_AUTO_2)

            .canBePlacedOffhand(false);

    public static final Function<Item, CapabilityItem.Builder> SOLEMN_LAMENT = (item) -> WeaponCapability.builder()
            .category(TCorpCategories.SOLEMN_LAMENT)
            .styleProvider((playerpatch) -> {

                boolean leftHandValid = true;
                boolean rightHandValid = true;
                if (!playerpatch.getOriginal().getMainHandItem().getItem().equals(EgoWeaponsItems.SOLEMN_LAMENT_WHITE.get())) {
                    rightHandValid = false;
                }

                if (!playerpatch.getOriginal().getOffhandItem().getItem().equals(EgoWeaponsItems.SOLEMN_LAMENT_BLACK.get())) {
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
            .specialAttack(TCorpStyles.DUAL_WIELDED, EgoWeaponsSkills.SOLEMN_LAMENT_BURST)
            .specialAttack(TCorpStyles.LEFT_HANDED, EgoWeaponsSkills.SOLEMN_LAMENT_BURST)
            .specialAttack(TCorpStyles.RIGHT_HANDED, EgoWeaponsSkills.SOLEMN_LAMENT_BURST)
            .specialAttack(Styles.ONE_HAND, EgoWeaponsSkills.SOLEMN_LAMENT_BURST)
            .hitSound(EpicFightSounds.BLUNT_HIT)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == TCorpCategories.SOLEMN_LAMENT)
            .livingMotionModifier(TCorpStyles.DUAL_WIELDED, LivingMotions.IDLE, EgoWeaponsAnimations.SOLEMN_LAMENT_IDLE)
            .livingMotionModifier(TCorpStyles.DUAL_WIELDED, LivingMotions.WALK, EgoWeaponsAnimations.SOLEMN_LAMENT_WALK)
            .livingMotionModifier(TCorpStyles.DUAL_WIELDED, LivingMotions.SNEAK, EgoWeaponsAnimations.SOLEMN_LAMENT_SNEAK)
            .livingMotionModifier(TCorpStyles.DUAL_WIELDED, LivingMotions.KNEEL, EgoWeaponsAnimations.SOLEMN_LAMENT_KNEEL)
            .livingMotionModifier(TCorpStyles.DUAL_WIELDED, LivingMotions.RUN, EgoWeaponsAnimations.SOLEMN_LAMENT_RUN)
            .livingMotionModifier(TCorpStyles.DUAL_WIELDED, LivingMotions.JUMP, EgoWeaponsAnimations.SOLEMN_LAMENT_JUMP)
            .livingMotionModifier(TCorpStyles.DUAL_WIELDED, LivingMotions.BLOCK, EgoWeaponsAnimations.SOLEMN_LAMENT_GUARD)

            .livingMotionModifier(TCorpStyles.RIGHT_HANDED, LivingMotions.IDLE, EgoWeaponsAnimations.SOLEMN_LAMENT_IDLE)
            .livingMotionModifier(TCorpStyles.RIGHT_HANDED, LivingMotions.WALK, EgoWeaponsAnimations.SOLEMN_LAMENT_WALK)
            .livingMotionModifier(TCorpStyles.RIGHT_HANDED, LivingMotions.SNEAK, EgoWeaponsAnimations.SOLEMN_LAMENT_SNEAK)
            .livingMotionModifier(TCorpStyles.RIGHT_HANDED, LivingMotions.KNEEL, EgoWeaponsAnimations.SOLEMN_LAMENT_KNEEL)
            .livingMotionModifier(TCorpStyles.RIGHT_HANDED, LivingMotions.RUN, EgoWeaponsAnimations.SOLEMN_LAMENT_RUN)
            .livingMotionModifier(TCorpStyles.RIGHT_HANDED, LivingMotions.JUMP, EgoWeaponsAnimations.SOLEMN_LAMENT_JUMP)
            .livingMotionModifier(TCorpStyles.RIGHT_HANDED, LivingMotions.BLOCK, EgoWeaponsAnimations.SOLEMN_LAMENT_GUARD)

            .livingMotionModifier(TCorpStyles.LEFT_HANDED, LivingMotions.IDLE, EgoWeaponsAnimations.SOLEMN_LAMENT_IDLE)
            .livingMotionModifier(TCorpStyles.LEFT_HANDED, LivingMotions.WALK, EgoWeaponsAnimations.SOLEMN_LAMENT_WALK)
            .livingMotionModifier(TCorpStyles.LEFT_HANDED, LivingMotions.SNEAK, EgoWeaponsAnimations.SOLEMN_LAMENT_SNEAK)
            .livingMotionModifier(TCorpStyles.LEFT_HANDED, LivingMotions.KNEEL, EgoWeaponsAnimations.SOLEMN_LAMENT_KNEEL)
            .livingMotionModifier(TCorpStyles.LEFT_HANDED, LivingMotions.RUN, EgoWeaponsAnimations.SOLEMN_LAMENT_RUN)
            .livingMotionModifier(TCorpStyles.LEFT_HANDED, LivingMotions.JUMP, EgoWeaponsAnimations.SOLEMN_LAMENT_JUMP)
            .livingMotionModifier(TCorpStyles.LEFT_HANDED, LivingMotions.BLOCK, EgoWeaponsAnimations.SOLEMN_LAMENT_GUARD)

            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, EgoWeaponsAnimations.SOLEMN_LAMENT_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, EgoWeaponsAnimations.SOLEMN_LAMENT_WALK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, EgoWeaponsAnimations.SOLEMN_LAMENT_SNEAK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.KNEEL, EgoWeaponsAnimations.SOLEMN_LAMENT_KNEEL)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, EgoWeaponsAnimations.SOLEMN_LAMENT_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.JUMP, EgoWeaponsAnimations.SOLEMN_LAMENT_JUMP)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, EgoWeaponsAnimations.SOLEMN_LAMENT_GUARD)
            .passiveSkill(EgoWeaponsSkills.SOLEMN_LAMENT_PASSIVE)
            //.specialAttack(TCorpStyles.DUAL_WIELDED, TCorpSkills.MAGIC_BULLET_DETONATE)
            .newStyleCombo(TCorpStyles.DUAL_WIELDED, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D0, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D1, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D3, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D4, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D5, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D4, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D5, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D4, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D5, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D4, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D5, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D4, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D5, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D4, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D5, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D4, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D5, EgoWeaponsAnimations.SOLEMN_LAMENT_DASH_L, EgoWeaponsAnimations.SOLEMN_LAMENT_JUMP_ATTACK)
            .newStyleCombo(TCorpStyles.RIGHT_HANDED, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R0, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R1, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R2, EgoWeaponsAnimations.SOLEMN_LAMENT_DASH_R, EgoWeaponsAnimations.SOLEMN_LAMENT_JUMP_ATTACK)
            .newStyleCombo(TCorpStyles.LEFT_HANDED, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L0, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L1, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L2, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L2, EgoWeaponsAnimations.SOLEMN_LAMENT_DASH_L, EgoWeaponsAnimations.SOLEMN_LAMENT_JUMP_ATTACK)
            .newStyleCombo(Styles.ONE_HAND, EgoWeaponsAnimations.SOLEMN_LAMENT_MELEE_ATTACK, EgoWeaponsAnimations.SOLEMN_LAMENT_MELEE_ATTACK, EgoWeaponsAnimations.SOLEMN_LAMENT_JUMP_ATTACK)

            .canBePlacedOffhand(true);

    public static final Function<Item, CapabilityItem.Builder> MAGIC_BULLET = (item) -> WeaponCapability.builder()
            .category(TCorpCategories.MAGIC_BULLET)
            .styleProvider((playerpatch) -> {
                return Styles.TWO_HAND;
            })
            .collider(RIFLE)
            .hitSound(EpicFightSounds.BLUNT_HIT)

            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, EgoWeaponsAnimations.MAGIC_BULLET_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, EgoWeaponsAnimations.MAGIC_BULLET_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, EgoWeaponsAnimations.MAGIC_BULLET_SNEAK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, EgoWeaponsAnimations.MAGIC_BULLET_KNEEL)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, EgoWeaponsAnimations.MAGIC_BULLET_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, EgoWeaponsAnimations.MAGIC_BULLET_JUMP)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, EgoWeaponsAnimations.MAGIC_BULLET_GUARD)
            .passiveSkill(EgoWeaponsSkills.MAGIC_BULLET_PASSIVE)
            .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.MAGIC_BULLET_DETONATE)

            .newStyleCombo(Styles.TWO_HAND, EgoWeaponsAnimations.MAGIC_BULLET_AUTO_1, EgoWeaponsAnimations.MAGIC_BULLET_AUTO_2, EgoWeaponsAnimations.MAGIC_BULLET_AUTO_3, EgoWeaponsAnimations.MAGIC_BULLET_DASH, EgoWeaponsAnimations.MAGIC_BULLET_JUMP_ATTACK)

            .canBePlacedOffhand(false);

    public static final Function<Item, CapabilityItem.Builder> SUNSHOWER = (item) -> WeaponCapability.builder()
            .category(TCorpCategories.SUNSHOWER)
            .styleProvider((playerpatch) -> {
                return Styles.TWO_HAND;
            })
            .collider(SUNSHOWER_COL)
            .hitSound(EpicFightSounds.BLUNT_HIT)

            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, EgoWeaponsAnimations.SUNSHOWER_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, EgoWeaponsAnimations.SUNSHOWER_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, EgoWeaponsAnimations.SUNSHOWER_SNEAK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, EgoWeaponsAnimations.SUNSHOWER_KNEEL)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, EgoWeaponsAnimations.SUNSHOWER_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, EgoWeaponsAnimations.SUNSHOWER_JUMP)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, EgoWeaponsAnimations.SUNSHOWER_GUARD)
            .passiveSkill(EgoWeaponsSkills.SUNSHOWER_PASSIVE)
            .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.SUNSHOWER_PUDDLE_STOMP)

            .newStyleCombo(Styles.TWO_HAND, EgoWeaponsAnimations.SUNSHOWER_AUTO_1, EgoWeaponsAnimations.SUNSHOWER_AUTO_2, EgoWeaponsAnimations.SUNSHOWER_AUTO_3, EgoWeaponsAnimations.SUNSHOWER_AUTO_4, EgoWeaponsAnimations.SUNSHOWER_DASH, EgoWeaponsAnimations.SUNSHOWER_JUMP_ATTACK)

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
        event.getTypeEntry().put("sunshower", SUNSHOWER);
        event.getTypeEntry().put("solemn_lament", SOLEMN_LAMENT);
    }
}

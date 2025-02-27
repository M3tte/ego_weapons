//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.world.capabilities.item;

import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsSkills;
import net.m3tte.ego_weapons.gameasset.movesets.*;
import net.m3tte.ego_weapons.potion.FuriosoPotionEffect;
import net.m3tte.ego_weapons.potion.SolemnLamentEffects;
import net.m3tte.ego_weapons.world.capabilities.AmmoSystem;
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

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

public class EgoWeaponsCapabilityPresets {


    public static final Collider WHEELS_BLADE = new MultiOBBCollider(4, 0.4, 0.6, 1.1, 0.0, 0.0, -1);
    public static final Collider LONGER_BLADE = new MultiOBBCollider(4, 0.4, 0.6, 1, 0.0, 0.0, -0.9);
    public static final Collider SPLIT_HORIZONTAL = new MultiOBBCollider(4, 0.4, 0.6, 2, 0.0, 0.0, -1.2);

    public static final Collider SUNSHOWER_COL = new MultiOBBCollider(4, 0.4, 0.4, 0.75, 0.0, 0.0, -0.75);
    public static final Collider SUNSHOWER_COL_LARGE = new MultiOBBCollider(4, 0.8, 0.8, 0.6, 0.0, 0.0, -0.6);
    public static final Collider OEUFI_HALBERD = new MultiOBBCollider(4, 0.4, 0.4, 1.1, 0.0, 0.0, -1.0);

    public static final Collider RIFLE = new MultiOBBCollider(4, 0.2, 1, 0.2, 0, 0.0, -0.4);

    public static final Collider DoubtBlade = new MultiOBBCollider(4, 0.2, 1.3, 0.2, 0, 0.3, 0);
    public static final Collider NTBlade = new MultiOBBCollider(4, 0.7, 1.5, 1.3, 0, 0.3, 0);

    public static final Collider FSTL_HITBOX = new MultiOBBCollider(4, 1.1, 1.5, 1.1, -0.1, -1.3, -0.1);

    public static final Collider FSTL_BLADE = new MultiOBBCollider(4, 0.4, 0.6, 1.1, 0.0, 0.0, -1.2);

    public static final Collider SOLEMN_LAMENT_HITBOX = new MultiOBBCollider(4, 0.6, 2.8, 0.6, 0, -2.5, 0);
    public static final Collider SOLEMN_LAMENT_HITBOX_EXT = new MultiOBBCollider(4, 0.6, 3.3, 0.6, 0, -3.3, 0);
    public static final Collider RAILGUN_HITBOX = new MultiOBBCollider(4, 0.3, 9.3, 0.3, 0, -9.3, 0);
    public static final Collider RAILGUN_HITBOX_SP = new MultiOBBCollider(4, 0.7, 14.3, 0.7, -0.3, -14.3, -0.3);
    public static final Function<Item, CapabilityItem.Builder> MOOK_WORKSHOP = (item) ->
            WeaponCapability.builder().category(EgoWeaponsCategories.MOOK_WORKSHOP).canBePlacedOffhand(false).styleProvider((playerpatch) ->
                            (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10) ? EgoWeaponsStyles.FURIOSO : Styles.TWO_HAND)

                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, BlackSilenceMovesetAnims.MOOK_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, BlackSilenceMovesetAnims.MOOK_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, BlackSilenceMovesetAnims.MOOK_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, BlackSilenceMovesetAnims.MOOK_KNEEL)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, BlackSilenceMovesetAnims.MOOK_SNEAK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, BlackSilenceMovesetAnims.MOOK_GUARD)

                    .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.IDLE, BlackSilenceMovesetAnims.MOOK_IDLE)
                    .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.WALK, BlackSilenceMovesetAnims.MOOK_WALK)
                    .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.RUN, BlackSilenceMovesetAnims.MOOK_RUN)
                    .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.KNEEL, BlackSilenceMovesetAnims.MOOK_KNEEL)
                    .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.SNEAK, BlackSilenceMovesetAnims.MOOK_SNEAK)
                    .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.BLOCK, BlackSilenceMovesetAnims.MOOK_GUARD)
                    .collider(ColliderPreset.SWORD)
                    .passiveSkill(EgoWeaponsSkills.BASIC_BLOCKABLE_PASSIVE)
                    .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.MOOK_CUT)
                    .specialAttack(EgoWeaponsStyles.FURIOSO, EgoWeaponsSkills.MOOK_CUT)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(EgoWeaponsStyles.FURIOSO, BlackSilenceMovesetAnims.MOOK_CUT, BlackSilenceMovesetAnims.MOOK_CUT, BlackSilenceMovesetAnims.MOOK_CUT)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(Styles.TWO_HAND, BlackSilenceMovesetAnims.MOOK_AUTO_1, BlackSilenceMovesetAnims.MOOK_AUTO_2, BlackSilenceMovesetAnims.MOOK_AUTO_3, BlackSilenceMovesetAnims.MOOK_AUTO_4, Animations.AXE_DASH, BlackSilenceMovesetAnims.MOOK_AUTO_JUMP)


                    .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SWORD);

    public static final Function<Item, CapabilityItem.Builder> DURANDAL = (item) ->
            WeaponCapability.builder().category(EgoWeaponsCategories.DURANDAL).canBePlacedOffhand(false).styleProvider((playerpatch) ->
                            (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10) ? EgoWeaponsStyles.FURIOSO : Styles.TWO_HAND)

                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, DurandalMovesetAnims.DURANDAL_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, DurandalMovesetAnims.DURANDAL_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, DurandalMovesetAnims.DURANDAL_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, DurandalMovesetAnims.DURANDAL_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, DurandalMovesetAnims.DURANDAL_GUARD)

                    .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.IDLE, DurandalMovesetAnims.DURANDAL_IDLE)
                    .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.WALK, DurandalMovesetAnims.DURANDAL_WALK)
                    .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.RUN, DurandalMovesetAnims.DURANDAL_RUN)
                    .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.KNEEL, DurandalMovesetAnims.DURANDAL_IDLE)
                    .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.BLOCK, DurandalMovesetAnims.DURANDAL_GUARD)
                    .collider(LONGER_BLADE)
                    .passiveSkill(EgoWeaponsSkills.BASIC_BLOCKABLE_PASSIVE)
                    .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.DURANDAL_CLEAVE)
                    .specialAttack(EgoWeaponsStyles.FURIOSO, EgoWeaponsSkills.DURANDAL_CLEAVE)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(EgoWeaponsStyles.FURIOSO, DurandalMovesetAnims.DURANDAL_FURIOSO_1, DurandalMovesetAnims.DURANDAL_FURIOSO_2, DurandalMovesetAnims.DURANDAL_FURIOSO_3, Animations.AXE_DASH, Animations.SWORD_AIR_SLASH)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(Styles.TWO_HAND, DurandalMovesetAnims.DURANDAL_AUTO_1, DurandalMovesetAnims.DURANDAL_AUTO_2, DurandalMovesetAnims.DURANDAL_AUTO_3, DurandalMovesetAnims.DURANDAL_AUTO_4, DurandalMovesetAnims.DURANDAL_DASH_ATTACK, DurandalMovesetAnims.DURANDAL_JUMP_ATTACK)

                    .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SWORD);

    public static final Function<Item, CapabilityItem.Builder> MIMICRY = (item) ->
            WeaponCapability.builder().category(EgoWeaponsCategories.MIMICRY).canBePlacedOffhand(false).styleProvider((playerpatch) ->
                            (playerpatch.getOriginal().getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.JACKET_OF_THE_RED_MIST.get()) ? EgoWeaponsStyles.KALI :
                                    playerpatch.getOriginal().getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.RED_MIST_EGO_CHESTPLATE.get()) ? EgoWeaponsStyles.KALI_EGO : Styles.TWO_HAND))

                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, MimicryMovesetAnims.MIMICRY_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, MimicryMovesetAnims.MIMICRY_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, MimicryMovesetAnims.MIMICRY_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, MimicryMovesetAnims.MIMICRY_KNEEL)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, MimicryMovesetAnims.MIMICRY_SNEAK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, MimicryMovesetAnims.MIMICRY_GUARD)

                    .livingMotionModifier(EgoWeaponsStyles.KALI, LivingMotions.IDLE, MimicryMovesetAnims.KALI_IDLE)
                    .livingMotionModifier(EgoWeaponsStyles.KALI, LivingMotions.WALK, MimicryMovesetAnims.KALI_WALK)
                    .livingMotionModifier(EgoWeaponsStyles.KALI, LivingMotions.RUN, MimicryMovesetAnims.KALI_RUN)
                    .livingMotionModifier(EgoWeaponsStyles.KALI, LivingMotions.KNEEL, MimicryMovesetAnims.KALI_KNEEL)
                    .livingMotionModifier(EgoWeaponsStyles.KALI, LivingMotions.SNEAK, MimicryMovesetAnims.KALI_SNEAK)
                    .livingMotionModifier(EgoWeaponsStyles.KALI, LivingMotions.BLOCK, MimicryMovesetAnims.KALI_GUARD)

                    .livingMotionModifier(EgoWeaponsStyles.KALI_EGO, LivingMotions.IDLE, MimicryMovesetAnims.KALI_EGO_IDLE)
                    .livingMotionModifier(EgoWeaponsStyles.KALI_EGO, LivingMotions.WALK, MimicryMovesetAnims.KALI_EGO_WALK)
                    .livingMotionModifier(EgoWeaponsStyles.KALI_EGO, LivingMotions.RUN, MimicryMovesetAnims.KALI_EGO_RUN)
                    .livingMotionModifier(EgoWeaponsStyles.KALI_EGO, LivingMotions.KNEEL, MimicryMovesetAnims.KALI_EGO_KNEEL)
                    .livingMotionModifier(EgoWeaponsStyles.KALI_EGO, LivingMotions.SNEAK, MimicryMovesetAnims.KALI_EGO_SNEAK)
                    .livingMotionModifier(EgoWeaponsStyles.KALI_EGO, LivingMotions.BLOCK, MimicryMovesetAnims.KALI_GUARD)

                    .collider(LONGER_BLADE)
                    .passiveSkill(EgoWeaponsSkills.KALI_PASSIVE)
                    .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.HELLO)
                    .specialAttack(EgoWeaponsStyles.KALI, EgoWeaponsSkills.GREATER_SPLIT_VERTICAL)
                    .specialAttack(EgoWeaponsStyles.KALI_EGO, EgoWeaponsSkills.GREATER_SPLIT_VERTICAL)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(EgoWeaponsStyles.KALI_EGO, MimicryMovesetAnims.KALI_EGO_AUTO_1, MimicryMovesetAnims.KALI_EGO_AUTO_2, MimicryMovesetAnims.KALI_AUTO_3, MimicryMovesetAnims.KALI_AUTO_4, MimicryMovesetAnims.KALI_DASH, MimicryMovesetAnims.KALI_JUMP)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(EgoWeaponsStyles.KALI, MimicryMovesetAnims.KALI_AUTO_1, MimicryMovesetAnims.KALI_AUTO_2, MimicryMovesetAnims.KALI_AUTO_3, MimicryMovesetAnims.KALI_AUTO_4, MimicryMovesetAnims.KALI_DASH, MimicryMovesetAnims.KALI_JUMP)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(Styles.TWO_HAND, MimicryMovesetAnims.MIMICRY_AUTO_1, MimicryMovesetAnims.MIMICRY_AUTO_2, MimicryMovesetAnims.MIMICRY_AUTO_3, MimicryMovesetAnims.MIMICRY_DASH, MimicryMovesetAnims.MIMICRY_AUTO_3)

                    .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SWORD);
    public static final Function<Item, CapabilityItem.Builder> WHEELS_INDUSTRY = (item) ->
            WeaponCapability.builder().category(EgoWeaponsCategories.WHEELS_INDUSTRY).canBePlacedOffhand(false).styleProvider((playerpatch) ->
                            (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10) ? EgoWeaponsStyles.FURIOSO : Styles.TWO_HAND)

                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, BlackSilenceMovesetAnims.WHEELS_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, BlackSilenceMovesetAnims.WHEELS_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, BlackSilenceMovesetAnims.WHEELS_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, BlackSilenceMovesetAnims.WHEELS_CROUCH)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, BlackSilenceMovesetAnims.WHEELS_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, BlackSilenceMovesetAnims.WHEELS_SNEAK)
                    .collider(WHEELS_BLADE)
                    .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.IDLE, BlackSilenceMovesetAnims.WHEELS_IDLE)
                    .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.WALK, BlackSilenceMovesetAnims.WHEELS_WALK)
                    .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.RUN, BlackSilenceMovesetAnims.WHEELS_RUN)
                    .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.KNEEL, BlackSilenceMovesetAnims.WHEELS_CROUCH)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, BlackSilenceMovesetAnims.WHEELS_GUARD)
                    .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.SNEAK, BlackSilenceMovesetAnims.WHEELS_SNEAK)

                    .passiveSkill(EgoWeaponsSkills.WHEELS_PASSIVE)
                    .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.WHEELS_SMASH)
                    .specialAttack(EgoWeaponsStyles.FURIOSO, EgoWeaponsSkills.WHEELS_SMASH)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(EgoWeaponsStyles.FURIOSO, BlackSilenceMovesetAnims.WHEELS_HEAVY, BlackSilenceMovesetAnims.WHEELS_HEAVY, BlackSilenceMovesetAnims.WHEELS_HEAVY)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(Styles.TWO_HAND, BlackSilenceMovesetAnims.WHEELS_AUTO_1, BlackSilenceMovesetAnims.WHEELS_AUTO_2, BlackSilenceMovesetAnims.WHEELS_AUTO_3, BlackSilenceMovesetAnims.WHEELS_DASH, BlackSilenceMovesetAnims.WHEELS_AUTO_3);

    public static final Function<Item, CapabilityItem.Builder> OLD_BOYS = (item) ->
            WeaponCapability.builder().category(EgoWeaponsCategories.OLD_BOYS).canBePlacedOffhand(false).styleProvider((playerpatch) ->
                            (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10) ? EgoWeaponsStyles.FURIOSO : Styles.ONE_HAND)


                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, BlackSilenceMovesetAnims.OLD_BOYS_GUARD)

                    .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.BLOCK, BlackSilenceMovesetAnims.OLD_BOYS_GUARD)

                    .passiveSkill(EgoWeaponsSkills.OLD_BOYS_PASSIVE)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(EgoWeaponsStyles.FURIOSO, BlackSilenceMovesetAnims.OLD_BOYS_FURIOSO, BlackSilenceMovesetAnims.OLD_BOYS_FURIOSO, BlackSilenceMovesetAnims.OLD_BOYS_FURIOSO)
                    .hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(Styles.ONE_HAND, BlackSilenceMovesetAnims.OLD_BOYS_AUTO_1, BlackSilenceMovesetAnims.OLD_BOYS_AUTO_2, BlackSilenceMovesetAnims.OLD_BOYS_AUTO_3, Animations.AXE_DASH, Animations.AXE_AIRSLASH)


                    .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SWORD);

    public static final Function<Item, CapabilityItem.Builder> CRYSTAL_ATELIER = (item) -> WeaponCapability.builder()
            .category(EgoWeaponsCategories.CRYSTAL_ATELIER)

            .styleProvider((playerpatch) -> {
                if (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10)
                    return EgoWeaponsStyles.FURIOSO;

                return playerpatch.getHoldingItemCapability(Hand.OFF_HAND).getWeaponCategory() == EgoWeaponsCategories.CRYSTAL_ATELIER ? Styles.TWO_HAND : Styles.ONE_HAND;
            })

            .passiveSkill(EgoWeaponsSkills.BASIC_BLOCKABLE_PASSIVE)
            //.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            //.specialAttack(Styles.TWO_HAND, TCorpSkills.LESSER_SPLIT_VERTICAL)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == EgoWeaponsCategories.CRYSTAL_ATELIER)
            .collider(ColliderPreset.SWORD)
            .hitSound(EpicFightSounds.BLADE_HIT)
            .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.CRYSTAL_ATELIER)
            .specialAttack(EgoWeaponsStyles.FURIOSO, EgoWeaponsSkills.CRYSTAL_ATELIER)
            .newStyleCombo(Styles.TWO_HAND, BlackSilenceMovesetAnims.CRYSTAL_ATELIER_DODGE, BlackSilenceMovesetAnims.CRYSTAL_ATELIER_INSTANT_DASH, BlackSilenceMovesetAnims.CRYSTAL_ATELIER_INSTANT_DASH_DEBOUNCE, BlackSilenceMovesetAnims.CRYSTAL_ATELIER_INSTANT_DASH_DEBOUNCE, Animations.SWORD_DUAL_AIR_SLASH)
            .newStyleCombo(EgoWeaponsStyles.FURIOSO, BlackSilenceMovesetAnims.CRYSTAL_ATELIER_FURIOSO, BlackSilenceMovesetAnims.CRYSTAL_ATELIER_DODGE, BlackSilenceMovesetAnims.CRYSTAL_ATELIER_INSTANT_DASH_DEBOUNCE, BlackSilenceMovesetAnims.CRYSTAL_ATELIER_FURIOSO, Animations.SWORD_DUAL_AIR_SLASH)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .newStyleCombo(Styles.ONE_HAND, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SWORD_DASH, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SWORD)
            .canBePlacedOffhand(true);

    public static final Function<Item, CapabilityItem.Builder> ZELKOVA_MACE = (item) -> WeaponCapability.builder()
            .category(EgoWeaponsCategories.ZELKOVA_MACE)
            .styleProvider((playerpatch) -> Styles.ONE_HAND)
            .collider(ColliderPreset.SWORD)
            .hitSound(EpicFightSounds.BLUNT_HIT_HARD)
            .specialAttack(Styles.ONE_HAND, Skills.GUILLOTINE_AXE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, BlackSilenceMovesetAnims.ZELKOVA_IDLE)
            .newStyleCombo(Styles.ONE_HAND, Animations.AXE_AUTO1, Animations.AXE_AUTO2, Animations.AXE_DASH, Animations.AXE_AIRSLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SWORD)
            .canBePlacedOffhand(true);

    public static final Function<Item, CapabilityItem.Builder> ZELKOVA_AXE = (item) -> WeaponCapability.builder()
            .category(EgoWeaponsCategories.ZELKOVA_AXE)
            .styleProvider((playerpatch) -> {
                if (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10)
                    return EgoWeaponsStyles.FURIOSO;

                return playerpatch.getHoldingItemCapability(Hand.OFF_HAND).getWeaponCategory() == EgoWeaponsCategories.ZELKOVA_MACE ? Styles.TWO_HAND : Styles.ONE_HAND;
            })
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == EgoWeaponsCategories.ZELKOVA_MACE)
            .collider(ColliderPreset.SWORD)
            .hitSound(EpicFightSounds.BLADE_HIT)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, BlackSilenceMovesetAnims.ZELKOVA_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, BlackSilenceMovesetAnims.ZELKOVA_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, BlackSilenceMovesetAnims.ZELKOVA_WALK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, BlackSilenceMovesetAnims.ZELKOVA_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, BlackSilenceMovesetAnims.ZELKOVA_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, BlackSilenceMovesetAnims.ZELKOVA_RUN)
            .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.IDLE, BlackSilenceMovesetAnims.ZELKOVA_IDLE)
            .specialAttack(Styles.ONE_HAND, Skills.GUILLOTINE_AXE)
            .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.ZELKOVA_SHATTERING_SPIN)
            .specialAttack(EgoWeaponsStyles.FURIOSO, Skills.GUILLOTINE_AXE)
            .newStyleCombo(Styles.ONE_HAND, Animations.AXE_AUTO1, Animations.AXE_AUTO2, Animations.AXE_DASH, Animations.AXE_AIRSLASH)
            .newStyleCombo(Styles.TWO_HAND, BlackSilenceMovesetAnims.ZELKOVA_ATTACK_1, BlackSilenceMovesetAnims.ZELKOVA_ATTACK_2, BlackSilenceMovesetAnims.ZELKOVA_DASH_1, BlackSilenceMovesetAnims.ZELKOVA_JUMP_ATTACK)
            .newStyleCombo(EgoWeaponsStyles.FURIOSO, BlackSilenceMovesetAnims.ZELKOVA_FURIOSO_1, BlackSilenceMovesetAnims.ZELKOVA_FURIOSO_2, BlackSilenceMovesetAnims.ZELKOVA_FURIOSO_1, BlackSilenceMovesetAnims.ZELKOVA_JUMP_ATTACK)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SWORD)
            .canBePlacedOffhand(true);

    public static final Function<Item, CapabilityItem.Builder> RANGA_CLAW = (item) -> WeaponCapability.builder()
            .category(EgoWeaponsCategories.RANGA)
            .styleProvider((playerpatch) -> {

                if (playerpatch.getOriginal() instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) playerpatch.getOriginal();
                    if (player.getCooldowns().isOnCooldown(player.getMainHandItem().getItem()))
                        return Styles.ONE_HAND;
                }

                if (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10)
                    return EgoWeaponsStyles.FURIOSO;

                return playerpatch.getHoldingItemCapability(Hand.OFF_HAND).getWeaponCategory() == EgoWeaponsCategories.RANGA ? Styles.TWO_HAND : Styles.ONE_HAND;
            })
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == EgoWeaponsCategories.RANGA)
            .collider(ColliderPreset.DAGGER)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, BlackSilenceMovesetAnims.RANGA_WALK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, BlackSilenceMovesetAnims.RANGA_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, BlackSilenceMovesetAnims.RANGA_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, BlackSilenceMovesetAnims.RANGA_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, BlackSilenceMovesetAnims.RANGA_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, BlackSilenceMovesetAnims.RANGA_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, BlackSilenceMovesetAnims.RANGA_KNEEL)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.KNEEL, BlackSilenceMovesetAnims.RANGA_KNEEL)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, BlackSilenceMovesetAnims.RANGA_SNEAK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, BlackSilenceMovesetAnims.RANGA_SNEAK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, BlackSilenceMovesetAnims.RANGA_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, BlackSilenceMovesetAnims.RANGA_GUARD)

            .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.WALK, BlackSilenceMovesetAnims.RANGA_WALK)
            .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.IDLE, BlackSilenceMovesetAnims.RANGA_IDLE)
            .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.RUN, BlackSilenceMovesetAnims.RANGA_RUN)
            .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.KNEEL, BlackSilenceMovesetAnims.RANGA_KNEEL)
            .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.SNEAK, BlackSilenceMovesetAnims.RANGA_SNEAK)
            .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.BLOCK, BlackSilenceMovesetAnims.RANGA_GUARD)



            .specialAttack(Styles.ONE_HAND, EgoWeaponsSkills.RANGA_EVISCERATE)
            .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.RANGA_EVISCERATE)
            .specialAttack(EgoWeaponsStyles.FURIOSO, EgoWeaponsSkills.RANGA_EVISCERATE)
            .passiveSkill(EgoWeaponsSkills.BLACKSILENCE_PASSIVE)

            //.newStyleCombo(TCorpStyles.RANGA_EXHAUSTED, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SWORD_AUTO3, TCorpAnimations.RANGA_ATTACK_1, Animations.DAGGER_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, BlackSilenceMovesetAnims.RANGA_ATTACK_1, BlackSilenceMovesetAnims.RANGA_ATTACK_2, BlackSilenceMovesetAnims.RANGA_ATTACK_3, null, Animations.DAGGER_DUAL_AIR_SLASH)
            .newStyleCombo(EgoWeaponsStyles.FURIOSO, BlackSilenceMovesetAnims.RANGA_FURIOSO_1, BlackSilenceMovesetAnims.RANGA_FURIOSO_2, BlackSilenceMovesetAnims.RANGA_FURIOSO_3, null, Animations.DAGGER_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.DAGGER)
            .canBePlacedOffhand(true);


    public static final Function<Item, CapabilityItem.Builder> ALLAS_WORKSHOP = (item) -> WeaponCapability.builder()
            .category(EgoWeaponsCategories.ALLAS_WORKSHOP)
            .styleProvider((playerpatch) -> (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10) ? EgoWeaponsStyles.FURIOSO : Styles.TWO_HAND)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, BlackSilenceMovesetAnims.ALLAS_GUARD)
            .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.BLOCK, BlackSilenceMovesetAnims.ALLAS_GUARD)

            .collider(ColliderPreset.SPEAR)
            .hitSound(EpicFightSounds.BLADE_HIT)
            .passiveSkill(EgoWeaponsSkills.ALLAS_PASSIVE)
            .newStyleCombo(Styles.TWO_HAND, BlackSilenceMovesetAnims.ALLAS_ATTACK_1, BlackSilenceMovesetAnims.ALLAS_DASH_DEB, BlackSilenceMovesetAnims.ALLAS_DASH_DEB, BlackSilenceMovesetAnims.ALLAS_DASH_DEB)
            .newStyleCombo(EgoWeaponsStyles.FURIOSO, BlackSilenceMovesetAnims.ALLAS_DASH, BlackSilenceMovesetAnims.ALLAS_ATTACK_1, BlackSilenceMovesetAnims.ALLAS_DASH_DEB, BlackSilenceMovesetAnims.ALLAS_DASH_DEB, BlackSilenceMovesetAnims.ALLAS_DASH_DEB)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.SPEAR);



    public static final Function<Item, CapabilityItem.Builder> ATELIER_REVOLVER = (item) -> WeaponCapability.builder()
            .category(EgoWeaponsCategories.ATELIER_REVOLVER)
            .styleProvider((playerpatch) -> {






                int ammoCount = playerpatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("ammo");
                if (ammoCount > 0) {
                    if (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10)
                        return EgoWeaponsStyles.FURIOSO;

                    return playerpatch.getHoldingItemCapability(Hand.OFF_HAND).getWeaponCategory() == EgoWeaponsCategories.ATELIER_REVOLVER ? Styles.TWO_HAND : Styles.ONE_HAND;

                }

                return EgoWeaponsStyles.DUAL_WIELDED;
            })
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == EgoWeaponsCategories.ATELIER_REVOLVER)
            .collider(ColliderPreset.DAGGER)
            .hitSound(EpicFightSounds.BLUNT_HIT)

            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AtelierLogicMovesetAnims.ATELIER_REVOLVER_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AtelierLogicMovesetAnims.ATELIER_REVOLVER_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AtelierLogicMovesetAnims.ATELIER_REVOLVER_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AtelierLogicMovesetAnims.ATELIER_REVOLVER_RUN)
            .livingMotionModifier(EgoWeaponsStyles.DUAL_WIELDED, LivingMotions.BLOCK, AtelierLogicMovesetAnims.ATELIER_REVOLVER_GUARD)
            .livingMotionModifier(EgoWeaponsStyles.DUAL_WIELDED, LivingMotions.IDLE, AtelierLogicMovesetAnims.ATELIER_REVOLVER_IDLE)
            .livingMotionModifier(EgoWeaponsStyles.DUAL_WIELDED, LivingMotions.WALK, AtelierLogicMovesetAnims.ATELIER_REVOLVER_WALK)
            .livingMotionModifier(EgoWeaponsStyles.DUAL_WIELDED, LivingMotions.RUN, AtelierLogicMovesetAnims.ATELIER_REVOLVER_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, AtelierLogicMovesetAnims.ATELIER_REVOLVER_GUARD)
            .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.BLOCK, AtelierLogicMovesetAnims.ATELIER_REVOLVER_GUARD)

            .passiveSkill(EgoWeaponsSkills.ATELIER_PASSIVE)
            .specialAttack(EgoWeaponsStyles.DUAL_WIELDED, EgoWeaponsSkills.ATELIER_PISTOLS_FRENZY)
            .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.ATELIER_PISTOLS_FRENZY)

            .newStyleCombo(EgoWeaponsStyles.DUAL_WIELDED, AtelierLogicMovesetAnims.ATELIER_REVOLVER_AUTO_1, AtelierLogicMovesetAnims.ATELIER_REVOLVER_AUTO_2, AtelierLogicMovesetAnims.ATELIER_REVOLVER_AUTO_3, Animations.SWORD_DASH, AtelierLogicMovesetAnims.ATELIER_REVOLVER_JUMP_ATTACK)
            .newStyleCombo(Styles.TWO_HAND, AtelierLogicMovesetAnims.ATELIER_REVOLVER_AUTO_1a, AtelierLogicMovesetAnims.ATELIER_REVOLVER_AUTO_2a, AtelierLogicMovesetAnims.ATELIER_REVOLVER_AUTO_1a, AtelierLogicMovesetAnims.ATELIER_REVOLVER_JUMP_ATTACK)
            .newStyleCombo(EgoWeaponsStyles.DUAL_WIELDED, AtelierLogicMovesetAnims.ATELIER_REVOLVER_AUTO_1, AtelierLogicMovesetAnims.ATELIER_REVOLVER_AUTO_2, AtelierLogicMovesetAnims.ATELIER_REVOLVER_AUTO_3, AtelierLogicMovesetAnims.ATELIER_REVOLVER_RELOAD, AtelierLogicMovesetAnims.ATELIER_REVOLVER_JUMP_ATTACK)
            .newStyleCombo(EgoWeaponsStyles.FURIOSO, AtelierLogicMovesetAnims.ATELIER_REVOLVER_AUTO_1a, AtelierLogicMovesetAnims.ATELIER_REVOLVER_AUTO_2a, AtelierLogicMovesetAnims.ATELIER_REVOLVER_AUTO_1a, AtelierLogicMovesetAnims.ATELIER_REVOLVER_AUTO_1a)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK).collider(ColliderPreset.DAGGER)
            .canBePlacedOffhand(true);

    public static final Function<Item, CapabilityItem.Builder> ATELIER_SHOTGUN = (item) -> WeaponCapability.builder()
            .category(EgoWeaponsCategories.ATELIER_SHOTGUN)
            .styleProvider((playerpatch) -> {
                if (playerpatch.getOriginal().hasEffect(FuriosoPotionEffect.potion) && playerpatch.getOriginal().getPersistentData().getDouble("furiosoattacks") < 10)
                    return EgoWeaponsStyles.FURIOSO;
                return Styles.TWO_HAND;
            })
            .collider(RIFLE)
            .hitSound(EpicFightSounds.BLUNT_HIT)

            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AtelierLogicMovesetAnims.ATELIER_SHOTGUN_IDLE)
            .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.IDLE, AtelierLogicMovesetAnims.ATELIER_SHOTGUN_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AtelierLogicMovesetAnims.ATELIER_SHOTGUN_WALK)
            .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.WALK, AtelierLogicMovesetAnims.ATELIER_SHOTGUN_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AtelierLogicMovesetAnims.ATELIER_SHOTGUN_RUN)
            .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.RUN, AtelierLogicMovesetAnims.ATELIER_SHOTGUN_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AtelierLogicMovesetAnims.ATELIER_SHOTGUN_GUARD)
            .livingMotionModifier(EgoWeaponsStyles.FURIOSO, LivingMotions.BLOCK, AtelierLogicMovesetAnims.ATELIER_SHOTGUN_GUARD)
            .passiveSkill(EgoWeaponsSkills.ATELIER_SHOTGUN_PASSIVE)

            .newStyleCombo(Styles.TWO_HAND, AtelierLogicMovesetAnims.ATELIER_SHOTGUN_AUTO_1, AtelierLogicMovesetAnims.ATELIER_SHOTGUN_AUTO_2, AtelierLogicMovesetAnims.ATELIER_SHOTGUN_AUTO_3, AtelierLogicMovesetAnims.ATELIER_SHOTGUN_AUTO_1, AtelierLogicMovesetAnims.ATELIER_SHOTGUN_AUTO_2)
            .newStyleCombo(EgoWeaponsStyles.FURIOSO, AtelierLogicMovesetAnims.ATELIER_SHOTGUN_AUTO_1, AtelierLogicMovesetAnims.ATELIER_SHOTGUN_AUTO_2, AtelierLogicMovesetAnims.ATELIER_SHOTGUN_AUTO_3, AtelierLogicMovesetAnims.ATELIER_SHOTGUN_AUTO_1, AtelierLogicMovesetAnims.ATELIER_SHOTGUN_AUTO_2)

            .canBePlacedOffhand(false);

    public static final Function<Item, CapabilityItem.Builder> SOLEMN_LAMENT = (item) -> WeaponCapability.builder()
            .category(EgoWeaponsCategories.SOLEMN_LAMENT)
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
                    return (leftHandValid) ? EgoWeaponsStyles.DUAL_WIELDED : EgoWeaponsStyles.RIGHT_HANDED;
                } else if (leftHandValid) {
                    return EgoWeaponsStyles.LEFT_HANDED;
                } else {
                    return Styles.ONE_HAND;
                }
            }
            )
            .collider(SOLEMN_LAMENT_HITBOX)
            .specialAttack(EgoWeaponsStyles.DUAL_WIELDED, EgoWeaponsSkills.SOLEMN_LAMENT_BURST)
            .specialAttack(EgoWeaponsStyles.LEFT_HANDED, EgoWeaponsSkills.SOLEMN_LAMENT_BURST)
            .specialAttack(EgoWeaponsStyles.RIGHT_HANDED, EgoWeaponsSkills.SOLEMN_LAMENT_BURST)
            .specialAttack(Styles.ONE_HAND, EgoWeaponsSkills.SOLEMN_LAMENT_BURST)
            .hitSound(EpicFightSounds.BLUNT_HIT)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == EgoWeaponsCategories.SOLEMN_LAMENT)
            .livingMotionModifier(EgoWeaponsStyles.DUAL_WIELDED, LivingMotions.IDLE, SolemnLamentMovesetAnims.SOLEMN_LAMENT_IDLE)
            .livingMotionModifier(EgoWeaponsStyles.DUAL_WIELDED, LivingMotions.WALK, SolemnLamentMovesetAnims.SOLEMN_LAMENT_WALK)
            .livingMotionModifier(EgoWeaponsStyles.DUAL_WIELDED, LivingMotions.SNEAK, SolemnLamentMovesetAnims.SOLEMN_LAMENT_SNEAK)
            .livingMotionModifier(EgoWeaponsStyles.DUAL_WIELDED, LivingMotions.KNEEL, SolemnLamentMovesetAnims.SOLEMN_LAMENT_KNEEL)
            .livingMotionModifier(EgoWeaponsStyles.DUAL_WIELDED, LivingMotions.RUN, SolemnLamentMovesetAnims.SOLEMN_LAMENT_RUN)
            .livingMotionModifier(EgoWeaponsStyles.DUAL_WIELDED, LivingMotions.JUMP, SolemnLamentMovesetAnims.SOLEMN_LAMENT_JUMP)
            .livingMotionModifier(EgoWeaponsStyles.DUAL_WIELDED, LivingMotions.BLOCK, SolemnLamentMovesetAnims.SOLEMN_LAMENT_GUARD)

            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.IDLE, SolemnLamentMovesetAnims.SOLEMN_LAMENT_IDLE)
            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.WALK, SolemnLamentMovesetAnims.SOLEMN_LAMENT_WALK)
            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.SNEAK, SolemnLamentMovesetAnims.SOLEMN_LAMENT_SNEAK)
            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.KNEEL, SolemnLamentMovesetAnims.SOLEMN_LAMENT_KNEEL)
            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.RUN, SolemnLamentMovesetAnims.SOLEMN_LAMENT_RUN)
            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.JUMP, SolemnLamentMovesetAnims.SOLEMN_LAMENT_JUMP)
            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.BLOCK, SolemnLamentMovesetAnims.SOLEMN_LAMENT_GUARD)

            .livingMotionModifier(EgoWeaponsStyles.LEFT_HANDED, LivingMotions.IDLE, SolemnLamentMovesetAnims.SOLEMN_LAMENT_IDLE)
            .livingMotionModifier(EgoWeaponsStyles.LEFT_HANDED, LivingMotions.WALK, SolemnLamentMovesetAnims.SOLEMN_LAMENT_WALK)
            .livingMotionModifier(EgoWeaponsStyles.LEFT_HANDED, LivingMotions.SNEAK, SolemnLamentMovesetAnims.SOLEMN_LAMENT_SNEAK)
            .livingMotionModifier(EgoWeaponsStyles.LEFT_HANDED, LivingMotions.KNEEL, SolemnLamentMovesetAnims.SOLEMN_LAMENT_KNEEL)
            .livingMotionModifier(EgoWeaponsStyles.LEFT_HANDED, LivingMotions.RUN, SolemnLamentMovesetAnims.SOLEMN_LAMENT_RUN)
            .livingMotionModifier(EgoWeaponsStyles.LEFT_HANDED, LivingMotions.JUMP, SolemnLamentMovesetAnims.SOLEMN_LAMENT_JUMP)
            .livingMotionModifier(EgoWeaponsStyles.LEFT_HANDED, LivingMotions.BLOCK, SolemnLamentMovesetAnims.SOLEMN_LAMENT_GUARD)

            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, SolemnLamentMovesetAnims.SOLEMN_LAMENT_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, SolemnLamentMovesetAnims.SOLEMN_LAMENT_WALK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, SolemnLamentMovesetAnims.SOLEMN_LAMENT_SNEAK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.KNEEL, SolemnLamentMovesetAnims.SOLEMN_LAMENT_KNEEL)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, SolemnLamentMovesetAnims.SOLEMN_LAMENT_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.JUMP, SolemnLamentMovesetAnims.SOLEMN_LAMENT_JUMP)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, SolemnLamentMovesetAnims.SOLEMN_LAMENT_GUARD)
            .passiveSkill(EgoWeaponsSkills.SOLEMN_LAMENT_PASSIVE)
            //.specialAttack(TCorpStyles.DUAL_WIELDED, TCorpSkills.MAGIC_BULLET_DETONATE)
            .newStyleCombo(EgoWeaponsStyles.DUAL_WIELDED, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D0, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D1, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D3, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D4, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D5, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D4, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D5, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D4, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D5, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D4, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D5, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D4, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D5, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D4, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D5, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D4, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D5, SolemnLamentMovesetAnims.SOLEMN_LAMENT_DASH_L, SolemnLamentMovesetAnims.SOLEMN_LAMENT_JUMP_ATTACK)
            .newStyleCombo(EgoWeaponsStyles.RIGHT_HANDED, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R0, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R1, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_DASH_R, SolemnLamentMovesetAnims.SOLEMN_LAMENT_JUMP_ATTACK)
            .newStyleCombo(EgoWeaponsStyles.LEFT_HANDED, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L0, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L1, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L2, SolemnLamentMovesetAnims.SOLEMN_LAMENT_DASH_L, SolemnLamentMovesetAnims.SOLEMN_LAMENT_JUMP_ATTACK)
            .newStyleCombo(Styles.ONE_HAND, SolemnLamentMovesetAnims.SOLEMN_LAMENT_MELEE_ATTACK, SolemnLamentMovesetAnims.SOLEMN_LAMENT_MELEE_ATTACK, SolemnLamentMovesetAnims.SOLEMN_LAMENT_JUMP_ATTACK)

            .canBePlacedOffhand(true);

    public static final Function<Item, CapabilityItem.Builder> MAGIC_BULLET = (item) -> WeaponCapability.builder()
            .category(EgoWeaponsCategories.MAGIC_BULLET)
            .styleProvider((playerpatch) -> {
                return Styles.TWO_HAND;
            })
            .collider(RIFLE)
            .hitSound(EpicFightSounds.BLUNT_HIT)

            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, MagicBulletMovesetAnims.MAGIC_BULLET_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, MagicBulletMovesetAnims.MAGIC_BULLET_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, MagicBulletMovesetAnims.MAGIC_BULLET_SNEAK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, MagicBulletMovesetAnims.MAGIC_BULLET_KNEEL)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, MagicBulletMovesetAnims.MAGIC_BULLET_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, MagicBulletMovesetAnims.MAGIC_BULLET_JUMP)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, MagicBulletMovesetAnims.MAGIC_BULLET_GUARD)
            .passiveSkill(EgoWeaponsSkills.MAGIC_BULLET_PASSIVE)
            .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.MAGIC_BULLET_DETONATE)

            .newStyleCombo(Styles.TWO_HAND, MagicBulletMovesetAnims.MAGIC_BULLET_AUTO_1, MagicBulletMovesetAnims.MAGIC_BULLET_AUTO_2, MagicBulletMovesetAnims.MAGIC_BULLET_AUTO_3, MagicBulletMovesetAnims.MAGIC_BULLET_DASH, MagicBulletMovesetAnims.MAGIC_BULLET_JUMP_ATTACK)

            .canBePlacedOffhand(false);

    public static final Function<Item, CapabilityItem.Builder> OEUFI_ASSOC = (item) -> WeaponCapability.builder()
            .category(EgoWeaponsCategories.OEUFI)
            .styleProvider((playerpatch) -> {
                return Styles.TWO_HAND;
            })
            .collider(OEUFI_HALBERD)
            .hitSound(EpicFightSounds.BLUNT_HIT)

            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, OeufiAssocMovesetAnims.OEUFI_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, OeufiAssocMovesetAnims.OEUFI_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, OeufiAssocMovesetAnims.OEUFI_SNEAK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, OeufiAssocMovesetAnims.OEUFI_KNEEL)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, OeufiAssocMovesetAnims.OEUFI_RUN)
            //.livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, EgoWeaponsAnimations.MAGIC_BULLET_JUMP)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, OeufiAssocMovesetAnims.OEUFI_GUARD)
            .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.OEUFI_SLAM)
            .passiveSkill(EgoWeaponsSkills.OEUFI_PASSIVE)
            .newStyleCombo(Styles.TWO_HAND, OeufiAssocMovesetAnims.OEUFI_AUTO_1, OeufiAssocMovesetAnims.OEUFI_AUTO_2, OeufiAssocMovesetAnims.OEUFI_AUTO_3, OeufiAssocMovesetAnims.OEUFI_DASH, OeufiAssocMovesetAnims.OEUFI_AUTO_2)

            .canBePlacedOffhand(false);

    public static final Function<Item, CapabilityItem.Builder> FULLSTOP_OFFICE_REP = (item) -> WeaponCapability.builder()
            .category(EgoWeaponsCategories.FULLSTOP_REP)
            .styleProvider((playerpatch) -> {
                if (EgoWeaponsItems.FULLSTOP_REP_PISTOL.get().getItem().equals(playerpatch.getOriginal().getItemInHand(Hand.OFF_HAND).getItem())) {
                    EgoWeaponsModVars.PlayerVariables entityData = playerpatch.getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

                    if (entityData != null) {
                        if (entityData.firingMode) {
                            if (AmmoSystem.getAmmoCount(playerpatch.getOriginal().getOffhandItem()) > 0) {
                                return EgoWeaponsStyles.LEFT_HANDED;
                            }
                        }
                    }
                }

                if (EgoWeaponsItems.FULLSTOP_REP_PISTOL.get().getItem().equals(playerpatch.getOriginal().getItemInHand(Hand.MAIN_HAND).getItem())) {
                    EgoWeaponsModVars.PlayerVariables entityData = playerpatch.getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

                    if (entityData != null) {
                        if (entityData.firingMode) {
                            if (AmmoSystem.getAmmoCount(playerpatch.getOriginal().getMainHandItem()) > 0) {
                                return EgoWeaponsStyles.LEFT_HANDED;
                            }
                        }
                        return Styles.ONE_HAND;
                    }
                }


                return EgoWeaponsStyles.RIGHT_HANDED;
            })
            .collider(SOLEMN_LAMENT_HITBOX)
            .hitSound(EpicFightSounds.BLUNT_HIT)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == EgoWeaponsCategories.FULLSTOP_REP)

            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_WALK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_SNEAK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.KNEEL, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_KNEEL)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, AtelierLogicMovesetAnims.ATELIER_REVOLVER_GUARD)

            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.IDLE, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_IDLE)
            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.WALK, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_WALK)
            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.SNEAK, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_SNEAK)
            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.KNEEL, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_KNEEL)
            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.RUN, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_RUN)
            //.livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, EgoWeaponsAnimations.MAGIC_BULLET_JUMP)
            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.BLOCK, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_GUARD)

            .livingMotionModifier(EgoWeaponsStyles.LEFT_HANDED, LivingMotions.IDLE, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_IDLE)
            .livingMotionModifier(EgoWeaponsStyles.LEFT_HANDED, LivingMotions.WALK, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_WALK)
            .livingMotionModifier(EgoWeaponsStyles.LEFT_HANDED, LivingMotions.SNEAK, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_SNEAK)
            .livingMotionModifier(EgoWeaponsStyles.LEFT_HANDED, LivingMotions.KNEEL, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_KNEEL)
            .livingMotionModifier(EgoWeaponsStyles.LEFT_HANDED, LivingMotions.RUN, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_RUN)
            //.livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, EgoWeaponsAnimations.MAGIC_BULLET_JUMP)
            .livingMotionModifier(EgoWeaponsStyles.LEFT_HANDED, LivingMotions.BLOCK, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_GUARD)

            .specialAttack(EgoWeaponsStyles.RIGHT_HANDED, EgoWeaponsSkills.FULLSTOP_REP_INNATE)
            .specialAttack(EgoWeaponsStyles.LEFT_HANDED, EgoWeaponsSkills.FULLSTOP_REP_INNATE)
            .passiveSkill(EgoWeaponsSkills.FULLSTOP_REP_PASSIVE)
            .newStyleCombo(EgoWeaponsStyles.RIGHT_HANDED, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_AUTO_B_1, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_AUTO_B_2, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_AUTO_B_3, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_DASH, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_JUMP_ATTACK)
            .newStyleCombo(EgoWeaponsStyles.LEFT_HANDED, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_AUTO_G_1, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_AUTO_G_2, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_AUTO_G_3, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_AUTO_G_FINAL, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_DASH_G, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_JUMP_ATTACK)
            .newStyleCombo(Styles.ONE_HAND, FullstopOfficeRepMovesetAnims.FULLSTOP_REP_IDLE)
            .canBePlacedOffhand(true);

    public static final Function<Item, CapabilityItem.Builder> FULLSTOP_OFFICE_SNIPER = (item) -> WeaponCapability.builder()
            .category(EgoWeaponsCategories.FULLSTOP_SNIPER)
            .styleProvider((playerpatch) -> {

                EgoWeaponsModVars.PlayerVariables entityData = playerpatch.getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

                if (entityData.firingMode) {
                    if (AmmoSystem.getAmmoCount(playerpatch.getOriginal().getMainHandItem()) > 0) {
                        return EgoWeaponsStyles.RIGHT_HANDED;
                    }
                }


                return Styles.ONE_HAND;
            })
            .collider(SOLEMN_LAMENT_HITBOX_EXT)
            .hitSound(EpicFightSounds.BLUNT_HIT)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == EgoWeaponsCategories.FULLSTOP_REP)

            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_WALK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_SNEAK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.KNEEL, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_KNEEL)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.JUMP, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_JUMP)

            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.IDLE, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_IDLE)
            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.WALK, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_WALK)
            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.SNEAK, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_SNEAK)
            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.KNEEL, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_KNEEL)
            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.RUN, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_RUN)
            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.BLOCK, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_GUARD)
            .livingMotionModifier(EgoWeaponsStyles.RIGHT_HANDED, LivingMotions.JUMP, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_JUMP)

            .specialAttack(EgoWeaponsStyles.RIGHT_HANDED, EgoWeaponsSkills.FULLSTOP_SNIPER_INNATE)
            .passiveSkill(EgoWeaponsSkills.FULLSTOP_SNIPER_PASSIVE)
            .newStyleCombo(EgoWeaponsStyles.RIGHT_HANDED, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_AUTO_G_1, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_AUTO_G_2, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_AUTO_G_3, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_AUTO_G_1, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_AUTO_G_1)
            .newStyleCombo(Styles.ONE_HAND, FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_IDLE)

            .canBePlacedOffhand(false);
    public static final Function<Item, CapabilityItem.Builder> SUNSHOWER = (item) -> WeaponCapability.builder()
            .category(EgoWeaponsCategories.SUNSHOWER)
            .styleProvider((playerpatch) -> {
                return Styles.TWO_HAND;
            })
            .collider(SUNSHOWER_COL)
            .hitSound(EpicFightSounds.BLUNT_HIT)

            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, SunshowerMovesetAnims.SUNSHOWER_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, SunshowerMovesetAnims.SUNSHOWER_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, SunshowerMovesetAnims.SUNSHOWER_SNEAK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, SunshowerMovesetAnims.SUNSHOWER_KNEEL)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, SunshowerMovesetAnims.SUNSHOWER_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, SunshowerMovesetAnims.SUNSHOWER_JUMP)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, SunshowerMovesetAnims.SUNSHOWER_GUARD)
            .passiveSkill(EgoWeaponsSkills.SUNSHOWER_PASSIVE)
            .specialAttack(Styles.TWO_HAND, EgoWeaponsSkills.SUNSHOWER_PUDDLE_STOMP)

            .newStyleCombo(Styles.TWO_HAND, SunshowerMovesetAnims.SUNSHOWER_AUTO_1, SunshowerMovesetAnims.SUNSHOWER_AUTO_2, SunshowerMovesetAnims.SUNSHOWER_AUTO_3, SunshowerMovesetAnims.SUNSHOWER_AUTO_4, SunshowerMovesetAnims.SUNSHOWER_DASH, SunshowerMovesetAnims.SUNSHOWER_JUMP_ATTACK)

            .canBePlacedOffhand(false);
    public EgoWeaponsCapabilityPresets() {
    }

    @SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put("durandal", DURANDAL);
        event.getTypeEntry().put("mimicry", MIMICRY);
        event.getTypeEntry().put("crystal_atelier", CRYSTAL_ATELIER);
        event.getTypeEntry().put("zelkova_mace", ZELKOVA_MACE);
        event.getTypeEntry().put("zelkova_axe", ZELKOVA_AXE);
        event.getTypeEntry().put("ranga_claw", RANGA_CLAW);
        event.getTypeEntry().put("allas_workshop", ALLAS_WORKSHOP);
        event.getTypeEntry().put("atelier_revolver", ATELIER_REVOLVER);
        event.getTypeEntry().put("atelier_shotgun", ATELIER_SHOTGUN);
        event.getTypeEntry().put("mook_workshop", MOOK_WORKSHOP);
        event.getTypeEntry().put("old_boys_workshop", OLD_BOYS);
        event.getTypeEntry().put("wheels_industry", WHEELS_INDUSTRY);
        event.getTypeEntry().put("magic_bullet", MAGIC_BULLET);
        event.getTypeEntry().put("sunshower", SUNSHOWER);
        event.getTypeEntry().put("solemn_lament", SOLEMN_LAMENT);
        event.getTypeEntry().put("oeufi_association", OEUFI_ASSOC);
        event.getTypeEntry().put("fullstop_office_rep", FULLSTOP_OFFICE_REP);
        event.getTypeEntry().put("fullstop_office_sniper", FULLSTOP_OFFICE_SNIPER);
    }
}

package net.m3tte.tcorp;

import net.minecraft.client.audio.Sound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.util.HashMap;
import java.util.Map;

public class TCorpSounds {

    private static ResourceLocation locationFrom(String loc) {
        return new ResourceLocation(TcorpMod.MODID, loc);
    }

    public static Map<ResourceLocation, SoundEvent> SOUNDS = new HashMap<>();

    private static SoundEvent generateSoundEvent(ResourceLocation location) {
        SoundEvent event = new SoundEvent(location);
        SOUNDS.put(location, event);
        return event;
    }

    public static SoundEvent FIRST_WARNING = generateSoundEvent(locationFrom("first_warning"));
    public static SoundEvent SECOND_WARNING = generateSoundEvent(locationFrom("second_warning"));
    public static SoundEvent THIRD_WARNING = generateSoundEvent(locationFrom("third_warning"));
    public static SoundEvent FOURTH_WARNING = generateSoundEvent(locationFrom("fourth_warning"));

    public static SoundEvent MAGIC_BULLET_SPIN_SWING = generateSoundEvent(locationFrom("magic_bullet.spin.swing"));
    public static SoundEvent MAGIC_BULLET_SPIN_HIT = generateSoundEvent(locationFrom("magic_bullet.spin.hit"));
    public static SoundEvent MAGIC_BULLET_SPIN_SPAM = generateSoundEvent(locationFrom("magic_bullet.spin.spam"));
    public static SoundEvent MAGIC_BULLET_SPIN_DETONATE = generateSoundEvent(locationFrom("magic_bullet.spin.detonate"));

    public static SoundEvent SOLEMN_LAMENT_SPECIAL_RELOAD = generateSoundEvent(locationFrom("solemn_lament.special.reload"));
    public static SoundEvent SOLEMN_LAMENT_SPECIAL_READY = generateSoundEvent(locationFrom("solemn_lament.special.ready"));
    public static SoundEvent SOLEMN_LAMENT_SPECIAL_IMPACT = generateSoundEvent(locationFrom("solemn_lament.special.impact"));

    public static SoundEvent SOLEMN_LAMENT_RELOAD = generateSoundEvent(locationFrom("solemn_lament.reload"));
    public static SoundEvent SOLEMN_LAMENT_SPIN = generateSoundEvent(locationFrom("solemn_lament.spin"));
    public static SoundEvent SOLEMN_LAMENT_READY = generateSoundEvent(locationFrom("solemn_lament.ready"));
    public static SoundEvent SOLEMN_LAMENT_FAST_BLACK = generateSoundEvent(locationFrom("solemn_lament.fast.black"));
    public static SoundEvent SOLEMN_LAMENT_FAST_WHITE = generateSoundEvent(locationFrom("solemn_lament.fast.white"));
    public static SoundEvent SOLEMN_LAMENT_AUTO_BLACK = generateSoundEvent(locationFrom("solemn_lament.auto.black"));
    public static SoundEvent SOLEMN_LAMENT_AUTO_WHITE = generateSoundEvent(locationFrom("solemn_lament.auto.white"));
    public static SoundEvent MAGIC_BULLET_AIM_2 = generateSoundEvent(locationFrom("magic_bullet.aim.2"));
    public static SoundEvent MAGIC_BULLET_FIRE_2 = generateSoundEvent(locationFrom("magic_bullet.fire.2"));
    public static SoundEvent MAGIC_BULLET_FIRE_SWING = generateSoundEvent(locationFrom("magic_bullet.fire.swing"));
    public static SoundEvent MAGIC_BULLET_FIRE_3 = generateSoundEvent(locationFrom("magic_bullet.fire.3"));
    public static SoundEvent MAGIC_BULLET_BREATHE = generateSoundEvent(locationFrom("magic_bullet.breathe"));
    public static SoundEvent MAGIC_BULLET_SWING_1 = generateSoundEvent(locationFrom("magic_bullet.swing.1"));
    public static SoundEvent MAGIC_BULLET_SWING_2 = generateSoundEvent(locationFrom("magic_bullet.swing.2"));
    public static SoundEvent MAGIC_BULLET_HIT_1 = generateSoundEvent(locationFrom("magic_bullet.hit.1"));
    public static SoundEvent MAGIC_BULLET_HIT_2 = generateSoundEvent(locationFrom("magic_bullet.hit.2"));
    public static SoundEvent MAGIC_BULLET_AIM_1 = generateSoundEvent(locationFrom("magic_bullet.aim.1"));
    public static SoundEvent MAGIC_BULLET_FIRE_1 = generateSoundEvent(locationFrom("magic_bullet.fire.1"));
    public static SoundEvent NOTHING_THERE_LOVE_YOU = generateSoundEvent(locationFrom("nothing_there.i_love_you"));
    public static SoundEvent NOTHING_THERE_SLASH = generateSoundEvent(locationFrom("nothing_there.slash"));
    public static SoundEvent NOTHING_THERE_GOODBYE = generateSoundEvent(locationFrom("nothing_there.goodbye"));
    public static SoundEvent NOTHING_THERE_BLUNT = generateSoundEvent(locationFrom("nothing_there.blunt"));
    public static SoundEvent NOTHING_THERE_HELLO = generateSoundEvent(locationFrom("nothing_there.hello"));
    public static SoundEvent PAPER_FLIP = generateSoundEvent(locationFrom("paper_flip"));
    public static SoundEvent KALI_HARD_HORIZONTAL = generateSoundEvent(locationFrom("kali.hard_horizontal"));
    public static SoundEvent KALI_HORIZONTAL = generateSoundEvent(locationFrom("kali.horizontal"));
    public static SoundEvent KALI_SPLIT_HORIZONTAL_HIT = generateSoundEvent(locationFrom("kali.split.horizontal.hit"));
    public static SoundEvent KALI_SPLIT_HORIZONTAL_RING = generateSoundEvent(locationFrom("kali.split.horizontal.ring"));
    public static SoundEvent KALI_SPLIT_HORIZONTAL_SWING = generateSoundEvent(locationFrom("kali.split.horizontal.swing"));
    public static SoundEvent KALI_STAB = generateSoundEvent(locationFrom("kali.stab"));
    public static SoundEvent KALI_SPLIT_VERTICAL_HIT = generateSoundEvent(locationFrom("kali.split.vertical.hit"));
    public static SoundEvent KALI_SPLIT_VERTICAL_SLASH = generateSoundEvent(locationFrom("kali.split.vertical.slash"));
    public static SoundEvent WHEELS_IMPACT = generateSoundEvent(locationFrom("blacksilence.wheels.impact"));
    public static SoundEvent FINGER_SNAP = generateSoundEvent(locationFrom("fingersnap"));
    public static SoundEvent ROLAND = generateSoundEvent(locationFrom("roland"));
    public static SoundEvent ATELIER_SHOOT = generateSoundEvent(locationFrom("ateliershoot"));
    public static SoundEvent DICE_ROLL = generateSoundEvent(locationFrom("dice_roll"));
    public static SoundEvent RESULT_POSITIVE = generateSoundEvent(locationFrom("result_positive"));
    public static SoundEvent THAT_IS_THAT_AND_THIS_IS_THIS = generateSoundEvent(locationFrom("thatsthatthisisthis"));
    public static SoundEvent HEAVY_SLASH = generateSoundEvent(locationFrom("heavyslash"));
    public static SoundEvent BULLET_CRIT = generateSoundEvent(locationFrom("bullet_crit"));
    public static SoundEvent CROSS_SLASH = generateSoundEvent(locationFrom("cross_slash"));
    public static SoundEvent WOOSH = generateSoundEvent(locationFrom("woosh"));
    public static SoundEvent HEAVY_WOOSH = generateSoundEvent(locationFrom("heavy_woosh"));
    public static SoundEvent METAL_CLASH = generateSoundEvent(locationFrom("metal_clash"));
    public static SoundEvent OMINOUS_BELL = generateSoundEvent(locationFrom("ominousbell"));
    public static SoundEvent OMINOUS_EFFECT = generateSoundEvent(locationFrom("ominouseffect"));
    public static SoundEvent BLACK_SILENCE_EVADE = generateSoundEvent(locationFrom("blacksilence.evade"));
    public static SoundEvent BLACK_SILENCE_CRYSTAL_ATELIER = generateSoundEvent(locationFrom("blacksilence.crystal_atelier"));
    public static SoundEvent BLACK_SILENCE_ZELKOVA_AXE = generateSoundEvent(locationFrom("blacksilence.zelkova.axe"));
    public static SoundEvent BLACK_SILENCE_ZELKOVA_MACE = generateSoundEvent(locationFrom("blacksilence.zelkova.mace"));
    public static SoundEvent BLACK_SILENCE_DURANDAL_STRONG = generateSoundEvent(locationFrom("blacksilence.durandal.strong"));
    public static SoundEvent BLACK_SILENCE_CRYSTAL_ATELIER_STRONG = generateSoundEvent(locationFrom("blacksilence.crystal_atelier.strong"));
    public static SoundEvent BLACK_SILENCE_UNSHEATH = generateSoundEvent(locationFrom("blacksilence.mook.unsheath"));
    public static SoundEvent BLACK_SILENCE_MOOK_HIT = generateSoundEvent(locationFrom("blacksilence.mook.hit"));
    public static SoundEvent BLACK_SILENCE_MOOK_CUT = generateSoundEvent(locationFrom("blacksilence.mook.cut"));
    public static SoundEvent SWORD_STAB = generateSoundEvent(locationFrom("sword_stab"));
    public static SoundEvent STAGGER = generateSoundEvent(locationFrom("stagger"));
    public static SoundEvent GUARD_WIN = generateSoundEvent(locationFrom("guard_win"));
    public static SoundEvent CLOCK_TICKING = generateSoundEvent(locationFrom("clockticking"));
    public static SoundEvent CLICK = generateSoundEvent(locationFrom("click"));
    public static SoundEvent BLACK_SILENCE_ATELIER_REVOLVER = generateSoundEvent(locationFrom("blacksilence.atelier.revolver"));
    public static SoundEvent BLACK_SILENCE_SHORT_SWORD = generateSoundEvent(locationFrom("blacksilence.shortsword"));
    public static SoundEvent BLACK_SILENCE_SHOTGUN = generateSoundEvent(locationFrom("blacksilence.atelier.shotgun"));
    public static SoundEvent BLACK_SILENCE_DURANDAL_DOWN = generateSoundEvent(locationFrom("blacksilence.durandal.down"));
    public static SoundEvent BLACK_SILENCE_DURANDAL_UP = generateSoundEvent(locationFrom("blacksilence.durandal.up"));
}

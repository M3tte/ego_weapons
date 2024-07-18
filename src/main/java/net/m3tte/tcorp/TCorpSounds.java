package net.m3tte.tcorp;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.util.HashMap;
import java.util.Map;

public class TCorpSounds {

    private static ResourceLocation locationFrom(String loc) {
        return new ResourceLocation(TcorpMod.MODID, loc);
    }

    public static Map<ResourceLocation, SoundEvent> SOUNDS = new HashMap<>();

    private static SoundEvent generateSoundEvent(SoundEvent event) {
        SOUNDS.put(event.getLocation(), event);
        return event;
    }

    public static SoundEvent NOTHING_THERE_LOVE_YOU = generateSoundEvent(new SoundEvent(locationFrom("nothing_there.i_love_you")));
    public static SoundEvent NOTHING_THERE_SLASH = generateSoundEvent(new SoundEvent(locationFrom("nothing_there.slash")));
    public static SoundEvent NOTHING_THERE_GOODBYE = generateSoundEvent(new SoundEvent(locationFrom("nothing_there.goodbye")));
    public static SoundEvent NOTHING_THERE_BLUNT = generateSoundEvent(new SoundEvent(locationFrom("nothing_there.blunt")));
    public static SoundEvent NOTHING_THERE_HELLO = generateSoundEvent(new SoundEvent(locationFrom("nothing_there.hello")));
    public static SoundEvent PAPER_FLIP = generateSoundEvent(new SoundEvent(locationFrom("paper_flip")));
    public static SoundEvent KALI_HARD_HORIZONTAL = generateSoundEvent(new SoundEvent(locationFrom("kali.hard_horizontal")));
    public static SoundEvent KALI_HORIZONTAL = generateSoundEvent(new SoundEvent(locationFrom("kali.horizontal")));
    public static SoundEvent KALI_SPLIT_HORIZONTAL_HIT = generateSoundEvent(new SoundEvent(locationFrom("kali.split.horizontal.hit")));
    public static SoundEvent KALI_SPLIT_HORIZONTAL_RING = generateSoundEvent(new SoundEvent(locationFrom("kali.split.horizontal.ring")));
    public static SoundEvent KALI_SPLIT_HORIZONTAL_SWING = generateSoundEvent(new SoundEvent(locationFrom("kali.split.horizontal.swing")));
    public static SoundEvent KALI_STAB = generateSoundEvent(new SoundEvent(locationFrom("kali.stab")));
    public static SoundEvent KALI_SPLIT_VERTICAL_HIT = generateSoundEvent(new SoundEvent(locationFrom("kali.split.vertical.hit")));
    public static SoundEvent KALI_SPLIT_VERTICAL_SLASH = generateSoundEvent(new SoundEvent(locationFrom("kali.split.vertical.slash")));
    public static SoundEvent WHEELS_IMPACT = generateSoundEvent(new SoundEvent(locationFrom("blacksilence.wheels.impact")));
    public static SoundEvent FINGER_SNAP = generateSoundEvent(new SoundEvent(locationFrom("fingersnap")));
    public static SoundEvent ROLAND = generateSoundEvent(new SoundEvent(locationFrom("roland")));
    public static SoundEvent ATELIER_SHOOT = generateSoundEvent(new SoundEvent(locationFrom("ateliershoot")));
    public static SoundEvent DICE_ROLL = generateSoundEvent(new SoundEvent(locationFrom("dice_roll")));
    public static SoundEvent RESULT_POSITIVE = generateSoundEvent(new SoundEvent(locationFrom("result_positive")));
    public static SoundEvent THAT_IS_THAT_AND_THIS_IS_THIS = generateSoundEvent(new SoundEvent(locationFrom("thatsthatthisisthis")));
    public static SoundEvent HEAVY_SLASH = generateSoundEvent(new SoundEvent(locationFrom("heavyslash")));
    public static SoundEvent BULLET_CRIT = generateSoundEvent(new SoundEvent(locationFrom("bullet_crit")));
    public static SoundEvent CROSS_SLASH = generateSoundEvent(new SoundEvent(locationFrom("cross_slash")));
    public static SoundEvent WOOSH = generateSoundEvent(new SoundEvent(locationFrom("woosh")));
    public static SoundEvent HEAVY_WOOSH = generateSoundEvent(new SoundEvent(locationFrom("heavy_woosh")));
    public static SoundEvent METAL_CLASH = generateSoundEvent(new SoundEvent(locationFrom("metal_clash")));
    public static SoundEvent OMINOUS_BELL = generateSoundEvent(new SoundEvent(locationFrom("ominousbell")));
    public static SoundEvent OMINOUS_EFFECT = generateSoundEvent(new SoundEvent(locationFrom("ominouseffect")));
    public static SoundEvent BLACK_SILENCE_EVADE = generateSoundEvent(new SoundEvent(locationFrom("blacksilence.evade")));
    public static SoundEvent BLACK_SILENCE_CRYSTAL_ATELIER = generateSoundEvent(new SoundEvent(locationFrom("blacksilence.crystal_atelier")));
    public static SoundEvent BLACK_SILENCE_ZELKOVA_AXE = generateSoundEvent(new SoundEvent(locationFrom("blacksilence.zelkova.axe")));
    public static SoundEvent BLACK_SILENCE_ZELKOVA_MACE = generateSoundEvent(new SoundEvent(locationFrom("blacksilence.zelkova.mace")));
    public static SoundEvent BLACK_SILENCE_DURANDAL_STRONG = generateSoundEvent(new SoundEvent(locationFrom("blacksilence.durandal.strong")));
    public static SoundEvent BLACK_SILENCE_CRYSTAL_ATELIER_STRONG = generateSoundEvent(new SoundEvent(locationFrom("blacksilence.crystal_atelier.strong")));
    public static SoundEvent BLACK_SILENCE_UNSHEATH = generateSoundEvent(new SoundEvent(locationFrom("blacksilence.mook.unsheath")));
    public static SoundEvent BLACK_SILENCE_MOOK_HIT = generateSoundEvent(new SoundEvent(locationFrom("blacksilence.mook.hit")));
    public static SoundEvent BLACK_SILENCE_MOOK_CUT = generateSoundEvent(new SoundEvent(locationFrom("blacksilence.mook.cut")));
    public static SoundEvent SWORD_STAB = generateSoundEvent(new SoundEvent(locationFrom("sword_stab")));
    public static SoundEvent STAGGER = generateSoundEvent(new SoundEvent(locationFrom("stagger")));
    public static SoundEvent GUARD_WIN = generateSoundEvent(new SoundEvent(locationFrom("guard_win")));
    public static SoundEvent CLOCK_TICKING = generateSoundEvent(new SoundEvent(locationFrom("clockticking")));
    public static SoundEvent CLICK = generateSoundEvent(new SoundEvent(locationFrom("click")));
    public static SoundEvent BLACK_SILENCE_ATELIER_REVOLVER = generateSoundEvent(new SoundEvent(locationFrom("blacksilence.atelier.revolver")));
    public static SoundEvent BLACK_SILENCE_SHORT_SWORD = generateSoundEvent(new SoundEvent(locationFrom("blacksilence.shortsword")));
    public static SoundEvent BLACK_SILENCE_SHOTGUN = generateSoundEvent(new SoundEvent(locationFrom("blacksilence.atelier.shotgun")));
    public static SoundEvent BLACK_SILENCE_DURANDAL_DOWN = generateSoundEvent(new SoundEvent(locationFrom("blacksilence.durandal.down")));
    public static SoundEvent BLACK_SILENCE_DURANDAL_UP = generateSoundEvent(new SoundEvent(locationFrom("blacksilence.durandal.up")));
}

package net.m3tte.ego_weapons;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;

import java.util.HashMap;
import java.util.Map;

public class EgoWeaponsSounds {

    private static ResourceLocation locationFrom(String loc) {
        return new ResourceLocation(EgoWeaponsMod.MODID, loc);
    }

    public static Map<ResourceLocation, SoundEvent> SOUNDS = new HashMap<>();

    private static SoundEvent generateSoundEvent(ResourceLocation location) {
        SoundEvent event = new SoundEvent(location);
        SOUNDS.put(location, event);
        return event;
    }

    public static void registerSounds(RegistryEvent.Register<net.minecraft.util.SoundEvent> event) {
        for (Map.Entry<ResourceLocation, net.minecraft.util.SoundEvent> sound : SOUNDS.entrySet())
            event.getRegistry().register(sound.getValue().setRegistryName(sound.getKey()));
    }

    public static SoundEvent FIRST_WARNING = generateSoundEvent(locationFrom("first_warning"));
    public static SoundEvent SECOND_WARNING = generateSoundEvent(locationFrom("second_warning"));
    public static SoundEvent THIRD_WARNING = generateSoundEvent(locationFrom("third_warning"));
    public static SoundEvent FOURTH_WARNING = generateSoundEvent(locationFrom("fourth_warning"));

    public static SoundEvent POISE_CRIT = generateSoundEvent(locationFrom("effects.poise.crit"));
    public static SoundEvent SUNSHOWER_AUTO_1 = generateSoundEvent(locationFrom("sunshower.auto.1"));
    public static SoundEvent SUNSHOWER_AUTO_2 = generateSoundEvent(locationFrom("sunshower.auto.2"));
    public static SoundEvent SUNSHOWER_AUTO_4 = generateSoundEvent(locationFrom("sunshower.auto.4"));

    public static SoundEvent OUFI_IMPACT_1 = generateSoundEvent(locationFrom("oufi.impact.1"));
    public static SoundEvent OUFI_IMPACT_2 = generateSoundEvent(locationFrom("oufi.impact.2"));
    public static SoundEvent OUFI_IMPACT_UP = generateSoundEvent(locationFrom("oufi.impact.up"));
    public static SoundEvent OUFI_IMPACT_DOWN = generateSoundEvent(locationFrom("oufi.impact.down"));
    public static SoundEvent OUFI_PIERCE = generateSoundEvent(locationFrom("oufi.pierce.normal"));
    public static SoundEvent OUFI_PIERCE_HEAVY = generateSoundEvent(locationFrom("oufi.pierce.heavy"));
    public static SoundEvent OUFI_SWING = generateSoundEvent(locationFrom("oufi.swing"));
    public static SoundEvent OUFI_SWING_PIERCE = generateSoundEvent(locationFrom("oufi.swing.pierce"));
    public static SoundEvent OUFI_CONTRACT_OPEN = generateSoundEvent(locationFrom("oufi.contract.open"));
    public static SoundEvent OUFI_CONTRACT_INTERRUPT = generateSoundEvent(locationFrom("oufi.contract.interrupted"));
    public static SoundEvent OUFI_CONTRACT_COUNTER = generateSoundEvent(locationFrom("oufi.contract.counter"));
    public static SoundEvent OUFI_CONTRACT_FINISH = generateSoundEvent(locationFrom("oufi.contract.finish"));
    public static SoundEvent FULLSTOP_REP_FIRE = generateSoundEvent(locationFrom("full_stop_rep.fire"));
    public static SoundEvent FULLSTOP_REP_HEAVY_FIRE = generateSoundEvent(locationFrom("full_stop_rep.fire_heavy"));
    public static SoundEvent FULLSTOP_REP_RELOAD = generateSoundEvent(locationFrom("full_stop_rep.reload"));
    public static SoundEvent FULLSTOP_REP_SLICE = generateSoundEvent(locationFrom("full_stop_rep.slice"));
    public static SoundEvent FULLSTOP_REP_HEAVY_SLICE = generateSoundEvent(locationFrom("full_stop_rep.heavy_slice"));
    public static SoundEvent FULLSTOP_REP_JUMP_SLICE = generateSoundEvent(locationFrom("full_stop_rep.jump_slice"));
    public static SoundEvent FULLSTOP_REP_SLIDE = generateSoundEvent(locationFrom("full_stop_rep.slide"));
    public static SoundEvent FULLSTOP_FULL_STOP_TO_LIFE = generateSoundEvent(locationFrom("full_stop_rep.dialogue.full_stop_to_life"));
    public static SoundEvent FULLSTOP_FULL_STOP_TO_LIFE_G = generateSoundEvent(locationFrom("full_stop_rep.dialogue.full_stop_to_life_g"));
    public static SoundEvent FULLSTOP_REP_TAKE_CARE_OF_THEM = generateSoundEvent(locationFrom("full_stop_rep.dialogue.take_care_of_them"));
    public static SoundEvent FULLSTOP_REP_FINISH_THEM_OFF = generateSoundEvent(locationFrom("full_stop_rep.dialogue.finish_them_off"));

    public static SoundEvent FULLSTOP_SNIPER_AUTO_FIRE = generateSoundEvent(locationFrom("full_stop_sniper.auto.fire"));
    public static SoundEvent FULLSTOP_SNIPER_MOVE_ASIDE = generateSoundEvent(locationFrom("full_stop_sniper.dialogue.move_aside"));
    public static SoundEvent FULLSTOP_SNIPER_OKAY_SEE_EM = generateSoundEvent(locationFrom("full_stop_sniper.dialogue.okay_see_em"));
    public static SoundEvent FULLSTOP_SNIPER_TARGET_CONFIRMED = generateSoundEvent(locationFrom("full_stop_sniper.dialogue.alright_target_confirmed"));
    public static SoundEvent FULLSTOP_SNIPER_AUTO_HIT = generateSoundEvent(locationFrom("full_stop_sniper.auto.hit"));
    public static SoundEvent FULLSTOP_SNIPER_AUTO_START = generateSoundEvent(locationFrom("full_stop_sniper.auto.start"));
    public static SoundEvent FULLSTOP_SNIPER_INNATE_START = generateSoundEvent(locationFrom("full_stop_sniper.innate.start"));
    public static SoundEvent FULLSTOP_SNIPER_INNATE_FLIP = generateSoundEvent(locationFrom("full_stop_sniper.innate.flip"));
    public static SoundEvent FULLSTOP_SNIPER_INNATE_HIT = generateSoundEvent(locationFrom("full_stop_sniper.innate.hit"));
    public static SoundEvent FULLSTOP_SNIPER_INNATE_FIRE = generateSoundEvent(locationFrom("full_stop_sniper.innate.fire"));
    public static SoundEvent FULLSTOP_SNIPER_INNATE_CHARGE = generateSoundEvent(locationFrom("full_stop_sniper.innate.charge"));
    public static SoundEvent FULLSTOP_SNIPER_INNATE_AFTER = generateSoundEvent(locationFrom("full_stop_sniper.innate.after"));
    public static SoundEvent FULLSTOP_SNIPER_SPECIAL_START = generateSoundEvent(locationFrom("full_stop_sniper.special.start"));
    public static SoundEvent FULLSTOP_SNIPER_SPECIAL_HIT = generateSoundEvent(locationFrom("full_stop_sniper.special.hit"));
    public static SoundEvent FULLSTOP_SNIPER_SPECIAL_FIRE = generateSoundEvent(locationFrom("full_stop_sniper.special.fire"));
    public static SoundEvent FULLSTOP_SNIPER_SPECIAL_CHARGE = generateSoundEvent(locationFrom("full_stop_sniper.special.charge"));
    public static SoundEvent FULLSTOP_SNIPER_RELOAD = generateSoundEvent(locationFrom("full_stop_sniper.reload"));
    public static SoundEvent TARGET_SPOTTED = generateSoundEvent(locationFrom("full_stop_rep.target_spotted"));
    public static SoundEvent SUNSHOWER_SPREAD_OUT_1 = generateSoundEvent(locationFrom("sunshower.spread_out.1"));
    public static SoundEvent SUNSHOWER_SPREAD_OUT_2 = generateSoundEvent(locationFrom("sunshower.spread_out.2"));
    public static SoundEvent SUNSHOWER_SPREAD_OUT_3 = generateSoundEvent(locationFrom("sunshower.spread_out.3"));
    public static SoundEvent SUNSHOWER_PUDDLE_STOMP_1 = generateSoundEvent(locationFrom("sunshower.puddle_stomp.1"));
    public static SoundEvent SUNSHOWER_PUDDLE_STOMP_2 = generateSoundEvent(locationFrom("sunshower.puddle_stomp.2"));
    public static SoundEvent SUNSHOWER_PUDDLE_STOMP_3 = generateSoundEvent(locationFrom("sunshower.puddle_stomp.3"));
    public static SoundEvent SUNSHOWER_COUNTER_1 = generateSoundEvent(locationFrom("sunshower.counter.1"));
    public static SoundEvent SUNSHOWER_COUNTER_2 = generateSoundEvent(locationFrom("sunshower.counter.2"));

    public static SoundEvent NOTHING_THERE_WALK = generateSoundEvent(locationFrom("nothing_there.walk"));
    public static SoundEvent NOTHING_THERE_IDLE_1 = generateSoundEvent(locationFrom("nothing_there.idle.1"));
    public static SoundEvent NOTHING_THERE_IDLE_2 = generateSoundEvent(locationFrom("nothing_there.idle.2"));

    public static SoundEvent NOTHING_THERE_STOMP = generateSoundEvent(locationFrom("nothing_there.stomp"));
    public static SoundEvent NOTHING_THERE_SCREECH_WINDUP = generateSoundEvent(locationFrom("nothing_there.screech.windup"));
    public static SoundEvent NOTHING_THERE_SCREECH_FLESH = generateSoundEvent(locationFrom("nothing_there.screech.flesh"));
    public static SoundEvent NOTHING_THERE_SCREECH_LOW = generateSoundEvent(locationFrom("nothing_there.screech.low"));
    public static SoundEvent NOTHING_THERE_SCREECH_HIGH = generateSoundEvent(locationFrom("nothing_there.screech.high"));

    public static SoundEvent NOTHING_THERE_VOICE_1 = generateSoundEvent(locationFrom("nothing_there.voice.1"));
    public static SoundEvent NOTHING_THERE_VOICE_2 = generateSoundEvent(locationFrom("nothing_there.voice.2"));
    public static SoundEvent NOTHING_THERE_VOICE_3 = generateSoundEvent(locationFrom("nothing_there.voice.3"));
    public static SoundEvent NOTHING_THERE_VOICE_4 = generateSoundEvent(locationFrom("nothing_there.voice.4"));
    public static SoundEvent NOTHING_THERE_GOODBYE_KILL = generateSoundEvent(locationFrom("nothing_there.goodbye.kill"));
    public static SoundEvent NOTHING_THERE_HI = generateSoundEvent(locationFrom("nothing_there.hi_man"));
    public static SoundEvent NOTHING_THERE_HEAVY_SLASH = generateSoundEvent(locationFrom("nothing_there.heavy_slash"));
    public static SoundEvent NOTHING_THERE_SLASH_ALT = generateSoundEvent(locationFrom("nothing_there.slash.1"));
    public static SoundEvent NOTHING_THERE_BLUNT_ALT = generateSoundEvent(locationFrom("nothing_there.blunt.1"));
    public static SoundEvent DOUBT_DEATH = generateSoundEvent(locationFrom("doubt.death"));
    public static SoundEvent DOUBT_HIT_LIGHT = generateSoundEvent(locationFrom("doubt.hit.light"));
    public static SoundEvent DOUBT_CHARGE = generateSoundEvent(locationFrom("doubt.charge"));
    public static SoundEvent DOUBT_HIT_VERTICAL_FIRST = generateSoundEvent(locationFrom("doubt.hit.vertical.first"));
    public static SoundEvent DOUBT_HIT_VERTICAL_SECOND = generateSoundEvent(locationFrom("doubt.hit.vertical.second"));

    public static SoundEvent MAGIC_BULLET_DIALOGUE_ASSIST = generateSoundEvent(locationFrom("magic_bullet.dialogue.assist"));
    public static SoundEvent MAGIC_BULLET_DIALOGUE_FIRE = generateSoundEvent(locationFrom("magic_bullet.dialogue.fire"));
    public static SoundEvent MAGIC_BULLET_SPIN_SWING = generateSoundEvent(locationFrom("magic_bullet.spin.swing"));
    public static SoundEvent MAGIC_BULLET_SPIN_HIT = generateSoundEvent(locationFrom("magic_bullet.spin.hit"));
    public static SoundEvent MAGIC_BULLET_SPIN_SPAM = generateSoundEvent(locationFrom("magic_bullet.spin.spam"));
    public static SoundEvent MAGIC_BULLET_SPIN_DETONATE = generateSoundEvent(locationFrom("magic_bullet.spin.detonate"));

    public static SoundEvent SOLEMN_LAMENT_SPECIAL_RELOAD = generateSoundEvent(locationFrom("solemn_lament.special.reload"));
    public static SoundEvent SOLEMN_LAMENT_SPECIAL_READY = generateSoundEvent(locationFrom("solemn_lament.special.spin"));
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
    public static SoundEvent TREMOR_BURST = generateSoundEvent(locationFrom("tremor_burst"));
}

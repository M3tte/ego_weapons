package net.m3tte.ego_weapons.world.capabilities.gamerules;

import net.minecraft.world.GameRules;

public class EgoWeaponsGamerules {
    public static GameRules.RuleKey<GameRules.BooleanValue> ENABLE_CLASHING;
    public static GameRules.RuleKey<GameRules.BooleanValue> ENABLE_DAMAGEINDICATORS;
    public static GameRules.RuleKey<GameRules.BooleanValue> ENABLE_CLASHINDICATOR;
    public static GameRules.RuleKey<GameRules.IntegerValue> DIALOGUE_DENSITY;
    public static GameRules.RuleKey<GameRules.BooleanValue> DIALOGUE_BUBBLES;
    public static GameRules.RuleKey<GameRules.BooleanValue> SHOW_PLAYER_INFO;
    public static GameRules.RuleKey<GameRules.IntegerValue> LORE_DAMAGE_CONVERSION;
    public static GameRules.RuleKey<GameRules.BooleanValue> PALE_DEALS_PERCENT;


    public static void registerRules() {
        ENABLE_CLASHING = GameRules.register("doClashing", GameRules.Category.MISC, GameRules.BooleanValue.create(false));
        ENABLE_DAMAGEINDICATORS = GameRules.register("doDamageIndicators", GameRules.Category.MISC, GameRules.BooleanValue.create(true));
        ENABLE_CLASHINDICATOR = GameRules.register("doClashingIndicators", GameRules.Category.MISC, GameRules.BooleanValue.create(true));
        DIALOGUE_DENSITY = GameRules.register("dialogueDensity", GameRules.Category.MISC, GameRules.IntegerValue.create(2));
        DIALOGUE_BUBBLES = GameRules.register("doSpeechBubbles", GameRules.Category.MISC, GameRules.BooleanValue.create(true));
        SHOW_PLAYER_INFO = GameRules.register("showStats", GameRules.Category.MISC, GameRules.BooleanValue.create(true));
        LORE_DAMAGE_CONVERSION = GameRules.register("loreDamageTypePercent", GameRules.Category.MISC, GameRules.IntegerValue.create(0));
        PALE_DEALS_PERCENT = GameRules.register("paleDealsPercent", GameRules.Category.MISC, GameRules.BooleanValue.create(false));
    }

}

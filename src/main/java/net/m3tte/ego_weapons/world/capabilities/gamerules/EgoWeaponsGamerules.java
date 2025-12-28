package net.m3tte.ego_weapons.world.capabilities.gamerules;

import net.minecraft.world.GameRules;

public class EgoWeaponsGamerules {
    public static GameRules.RuleKey<GameRules.BooleanValue> ENABLE_CLASHING;
    public static GameRules.RuleKey<GameRules.BooleanValue> ENABLE_DAMAGEINDICATORS;
    public static GameRules.RuleKey<GameRules.BooleanValue> ENABLE_CLASHINDICATOR;
    public static GameRules.RuleKey<GameRules.IntegerValue> DIALOGUE_DENSITY;
    public static GameRules.RuleKey<GameRules.BooleanValue> DIALOGUE_BUBBLES;


    public static void registerRules() {
        ENABLE_CLASHING = GameRules.register("enableClashing", GameRules.Category.MISC, GameRules.BooleanValue.create(false));
        ENABLE_DAMAGEINDICATORS = GameRules.register("enableDamageIndicators", GameRules.Category.MISC, GameRules.BooleanValue.create(true));
        ENABLE_CLASHINDICATOR = GameRules.register("enableClashingIndicators", GameRules.Category.MISC, GameRules.BooleanValue.create(true));
        DIALOGUE_DENSITY = GameRules.register("dialogueDensity", GameRules.Category.MISC, GameRules.IntegerValue.create(2));
        DIALOGUE_BUBBLES = GameRules.register("enableSpeechBubbles", GameRules.Category.MISC, GameRules.BooleanValue.create(true));



    }
}

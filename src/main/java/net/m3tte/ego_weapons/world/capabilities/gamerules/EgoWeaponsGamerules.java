package net.m3tte.ego_weapons.world.capabilities.gamerules;

import net.minecraft.world.GameRules;

public class EgoWeaponsGamerules {
    public static GameRules.RuleKey<GameRules.BooleanValue> ENABLE_CLASHING;
    public static GameRules.RuleKey<GameRules.BooleanValue> ENABLE_DAMAGEINDICATORS;
    public static GameRules.RuleKey<GameRules.BooleanValue> ENABLE_CLASHINDICATOR;


    public static void registerRules() {
        ENABLE_CLASHING = GameRules.register("enableClashing", GameRules.Category.MISC, GameRules.BooleanValue.create(false));
        ENABLE_DAMAGEINDICATORS = GameRules.register("enableDamageIndicators", GameRules.Category.MISC, GameRules.BooleanValue.create(true));
        ENABLE_CLASHINDICATOR = GameRules.register("enableClashingIndicators", GameRules.Category.MISC, GameRules.BooleanValue.create(true));



    }
}

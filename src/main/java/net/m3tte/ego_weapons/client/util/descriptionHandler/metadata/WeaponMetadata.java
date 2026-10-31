package net.m3tte.ego_weapons.client.util.descriptionHandler.metadata;

import net.m3tte.ego_weapons.potion.countEffects.EffectLike;
import net.m3tte.ego_weapons.world.capabilities.DamageDefaults;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.minecraft.item.Item;

public class WeaponMetadata extends ItemMetadata {
    private float damage = 0;
    private float attackSpeed = 0;

    public float getDamage() {
        return damage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }


    public WeaponMetadata(String itemIdentifier, Item targetItem, float damage, float attackSpeed, EffectLike... keywords) {
        super(itemIdentifier, targetItem, keywords);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }
}

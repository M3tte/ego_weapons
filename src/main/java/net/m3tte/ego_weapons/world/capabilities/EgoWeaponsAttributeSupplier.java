package net.m3tte.ego_weapons.world.capabilities;


import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;

import java.util.function.Consumer;

public class EgoWeaponsAttributeSupplier extends AttributeModifierMap {
    private final AttributeModifierMap egoWeaponsInstances;



    public EgoWeaponsAttributeSupplier(AttributeModifierMap copy) {
        super(copy.instances);
        this.egoWeaponsInstances = AttributeModifierMap.builder()
                .add(EgoWeaponsAttributes.PIERCE_RESISTANCE.get())
                .add(EgoWeaponsAttributes.SLASH_RESISTANCE.get())
                .add(EgoWeaponsAttributes.BLUNT_RESISTANCE.get())
                .add(EgoWeaponsAttributes.RED_RESISTANCE.get())
                .add(EgoWeaponsAttributes.WHITE_RESISTANCE.get())
                .add(EgoWeaponsAttributes.BLACK_RESISTANCE.get())
                .add(EgoWeaponsAttributes.PALE_RESISTANCE.get())

                .add(EgoWeaponsAttributes.MAX_SANITY.get())
                .add(EgoWeaponsAttributes.MAX_LIGHT.get())
                .add(EgoWeaponsAttributes.MAX_STAGGER.get())
                .build();
    }

    @Override
    public ModifiableAttributeInstance createInstance(Consumer<ModifiableAttributeInstance> onDirty, Attribute attribute) {
        ModifiableAttributeInstance instance = super.createInstance(onDirty, attribute);

        if (instance == null) {
            instance = this.egoWeaponsInstances.createInstance(onDirty, attribute);
        }

        return instance;
    }
}

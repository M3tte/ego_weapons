//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.specialParticles.numberParticle.NumberParticleTypes;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.Objects;
import java.util.UUID;

public class TremorDecayEffect extends TremorEffect {

    static AttributeModifier armorMod = new AttributeModifier(UUID.fromString("7f1b85c9-3e96-4b42-ae76-0ae88ee099ed"),"tremorDecayArmorDebuff", -1, AttributeModifier.Operation.ADDITION);

    static AttributeModifier stunresMod = new AttributeModifier(UUID.fromString("7f1b85c9-3e96-4b42-ae76-0ae88ee099ed"), "tremorDecayStunDebuff", -0.1, AttributeModifier.Operation.MULTIPLY_BASE);
    static AttributeModifier toughnessMod = new AttributeModifier(UUID.fromString("7f1b85c9-3e96-4b42-ae76-0ae88ee099ed"), "tremorDecaytoughnessDebuff", -0.2, AttributeModifier.Operation.ADDITION);

    public TremorDecayEffect() {
        super(EffectType.HARMFUL, "tremor_decay",-16777216);
    }

    @Override
    public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.addAttributeModifiers(living, attrman, amplifier);

        amplifier = amplifier + 1;

        if (amplifier < 4)
            return;

        amplifier = amplifier - 4;

        ModifiableAttributeInstance armorInstance = attrman.getInstance(Attributes.ARMOR);
        ModifiableAttributeInstance toughnessInstance = attrman.getInstance(Attributes.ARMOR_TOUGHNESS);
        ModifiableAttributeInstance stunInstance = attrman.getInstance(EpicFightAttributes.STUN_ARMOR.get());

        if (armorInstance != null) {
            armorInstance.removeModifier(armorMod);
            armorInstance.addPermanentModifier(new AttributeModifier(armorMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((amplifier) / 4, armorMod), armorMod.getOperation()));
        }

        if (toughnessInstance != null) {
            toughnessInstance.removeModifier(toughnessMod);
            toughnessInstance.addPermanentModifier(new AttributeModifier(toughnessMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((amplifier) / 4, toughnessMod), toughnessMod.getOperation()));
        }

        if (stunInstance != null) {
            stunInstance.removeModifier(stunresMod);
            stunInstance.addPermanentModifier(new AttributeModifier(stunresMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((amplifier) / 4, stunresMod), stunresMod.getOperation()));
        }

        attrman.save();
    }

    @Override
    public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.removeAttributeModifiers(living, attrman, amplifier);

        ModifiableAttributeInstance armorInstance = attrman.getInstance(Attributes.ARMOR);
        ModifiableAttributeInstance toughnessInstance = attrman.getInstance(Attributes.ARMOR_TOUGHNESS);
        ModifiableAttributeInstance stunInstance = attrman.getInstance(EpicFightAttributes.STUN_ARMOR.get());
        if (armorInstance != null)
            armorInstance.removeModifier(armorMod);
        if (toughnessInstance != null)
            toughnessInstance.removeModifier(toughnessMod);
        if (stunInstance != null)
            stunInstance.removeModifier(stunresMod);
        attrman.save();
    }

    @Override
    public String getDescriptionId() {
        return "effect.tremor_decay";
    }



}

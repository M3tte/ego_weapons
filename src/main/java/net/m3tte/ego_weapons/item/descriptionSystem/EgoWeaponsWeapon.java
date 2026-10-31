package net.m3tte.ego_weapons.item.descriptionSystem;

import net.m3tte.ego_weapons.client.util.descriptionHandler.StatisticsPage;
import net.m3tte.ego_weapons.client.util.descriptionHandler.AttributeTextPlaceholder;
import net.m3tte.ego_weapons.client.util.descriptionHandler.EmptyDescriptionPage;
import net.m3tte.ego_weapons.client.util.descriptionHandler.MultiDescriptionPage;
import net.m3tte.ego_weapons.client.util.descriptionHandler.metadata.ItemMetadata;
import net.m3tte.ego_weapons.client.util.descriptionHandler.metadata.PageMetadata;
import net.m3tte.ego_weapons.client.util.descriptionHandler.metadata.WeaponMetadata;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.potion.countEffects.EffectLike;
import net.m3tte.ego_weapons.procedures.TooltipFuncs;
import net.m3tte.ego_weapons.world.capabilities.DamageResistanceSystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.types.StaticAnimation;

import java.util.HashMap;
import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.*;

public class EgoWeaponsWeapon extends SwordItem {
    public EgoWeaponsWeapon(IItemTier tier, int dmg, float attackSpeed, Properties props) {
        super(tier, dmg, attackSpeed, props);
    }



    protected GenericEgoDamage.DamageTypes mainDamageType = GenericEgoDamage.DamageTypes.RED;
    protected GenericEgoDamage.AttackTypes mainAttackType = GenericEgoDamage.AttackTypes.GENERIC;


    public String getDefaultKillIdentifier() {
        return null;
    }

    public StaticAnimation getDefaultStunAnim(int strength) {
        return EgoWeaponsAnimations.CLASH_STUN_BASIC;
    }

    protected String identifier = "";
    protected EmptyDescriptionPage defaultPageResult = new EmptyDescriptionPage();
    protected HashMap<Integer, EmptyDescriptionPage> pages = null;
    protected String tierText = "";

    public void addPage(EmptyDescriptionPage page) {
        pages.put(pages.size(), page);
    }

    public void addMultiPage(EmptyDescriptionPage... page) {
        pages.put(pages.size(), new MultiDescriptionPage(page[0].getIdentifier(), page));
    }

    public float getAttackSpeed() {
        return 1f;
    }

    public void setupWeaponDescription() {
        addPage(new StatisticsPage(EmptyDescriptionPage.PAGE_TYPE.INFO, AttributeTextPlaceholder.PlaceholderType.WEAPON, 6, new WeaponMetadata("item_meta", this, this.getAttackSpeed(), this.getDamage(), getKeywords())));
    }



    public EffectLike[] getKeywords() {
        return new EffectLike[]{};
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);

        if (pages == null) {
            this.pages = new HashMap<>();
            setupWeaponDescription();
        }

        TooltipFuncs.generateItemDescription(list, "desc.ego_weapons."+this.identifier+".desc");

        EmptyDescriptionPage page = this.pages.getOrDefault(EgoWeaponsKeybinds.getUiPage(), defaultPageResult);

        if (page.isShowTags()) {
            list.add(new AttributeTextPlaceholder(AttributeTextPlaceholder.PlaceholderType.TAGS, 1, new ItemMetadata("tags_item", itemstack.getItem(), getKeywords())));
            list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
        }

        list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

        // Replaces the old page identifier
        list.add(new AttributeTextPlaceholder(AttributeTextPlaceholder.PlaceholderType.H_LINE, 0, new PageMetadata("h_line_page", this.pages.size(), EgoWeaponsKeybinds.getUiPage(), this.pages)));
        //list.add(new TranslationTextComponent("item.ego_weapons.generic.page", EgoWeaponsKeybinds.getUiPage()+1, pages.size()));
        //list.add(new TranslationTextComponent(tierText));
        list.add(new StringTextComponent(" "));
        page.render(itemstack, world, list);

        generateStatusHelp(list);
        generateOffhandHelp(list);
    }

    public String getIdentifier() {
        return identifier;
    }

    public EmptyDescriptionPage getDefaultPageResult() {
        return defaultPageResult;
    }

    public HashMap<Integer, EmptyDescriptionPage> getPages() {
        return pages;
    }

    public String getTierText() {
        return tierText;
    }
}

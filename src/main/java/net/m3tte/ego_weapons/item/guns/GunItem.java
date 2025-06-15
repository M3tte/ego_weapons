package net.m3tte.ego_weapons.item.guns;

import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GunItem extends EgoWeaponsWeapon {

    private int maxAmmo;
    private GunCaliber caliber;

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public GunCaliber getCaliber() {
        return caliber;
    }

    public GunItem(IItemTier tier, int p_i48460_2_, float p_i48460_3_, Properties props, int maxAmmo, GunCaliber caliber) {
        super(tier, p_i48460_2_, p_i48460_3_, props);
        this.maxAmmo = maxAmmo;
        this.caliber = caliber;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World p_77624_2_, List<ITextComponent> list, ITooltipFlag tooltipflag) {
        super.appendHoverText(stack, p_77624_2_, list, tooltipflag);
    }
}

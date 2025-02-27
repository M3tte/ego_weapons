package net.m3tte.ego_weapons.item.guns;

import net.m3tte.ego_weapons.world.capabilities.AmmoType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.m3tte.ego_weapons.item.guns.AmmoDescriptors.*;

public class AmmoItem extends Item {

    private GunCaliber caliber;
    private int ammoType;

    public AmmoType getAmmoType() {
        return AmmoType.values()[ammoType];
    }

    public GunCaliber getCaliber() {
        return caliber;
    }

    public AmmoItem(Properties props, int ammoType, GunCaliber caliber) {
        super(props);
        this.ammoType = ammoType;
        this.caliber = caliber;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag tooltipflag) {
        super.appendHoverText(stack, world, list, tooltipflag);
        switch (this.ammoType) {
            case 0:
            case 3:
                basicBulletDescriptor(stack, world, list);
                break;
            case 1:
                incendiaryBulletDescription(stack, world, list);
                break;
            case 2:
                moonstoneBulletDescription(stack, world, list);
                break;
            case 4:
                ALHVBulletDescription(stack, world, list);
                break;
        }
    }
}

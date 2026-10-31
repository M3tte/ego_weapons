package net.m3tte.ego_weapons.client.util.descriptionHandler;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public class ConditionalDescriptionPage extends EmptyDescriptionPage {

    private Supplier<EmptyDescriptionPage> pageSupplier;

    public ConditionalDescriptionPage(PAGE_TYPE identifier, boolean showTags, Supplier<EmptyDescriptionPage> pageSupplier) {
        super(identifier, showTags);
        this.pageSupplier = pageSupplier;
    }

    public ConditionalDescriptionPage(PAGE_TYPE identifier, Supplier<EmptyDescriptionPage> pageSupplier) {
        super(identifier);
        this.pageSupplier = pageSupplier;
    }

    @Override
    public void render(ItemStack itemstack, World world, List<ITextComponent> list) {
        this.pageSupplier.get().render(itemstack, world, list);
    }
}

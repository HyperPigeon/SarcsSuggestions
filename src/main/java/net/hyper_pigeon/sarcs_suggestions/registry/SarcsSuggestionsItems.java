package net.hyper_pigeon.sarcs_suggestions.registry;

import net.fabricmc.fabric.impl.registry.sync.FabricRegistry;
import net.hyper_pigeon.sarcs_suggestions.items.HerbicideItem;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

public class SarcsSuggestionsItems {
    public static final HerbicideItem HERBICIDE_ITEM =
            new HerbicideItem(new Item.Settings().group(ItemGroup.MISC));


    public static void init(){
        Registry.register(Registry.ITEM, new Identifier("sarcs_suggestions","herbicide"), HERBICIDE_ITEM);

    }
}

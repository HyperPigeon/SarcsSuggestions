package net.hyper_pigeon.sarcs_suggestions.registry;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.hyper_pigeon.sarcs_suggestions.blocks.PoisonousPotatoesBlock;
import net.minecraft.block.Material;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SarcsSuggestionsBlocks {
    public static final PoisonousPotatoesBlock POISONOUS_POTATOES_BLOCK =
            new PoisonousPotatoesBlock(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));

    public static void init(){
        Registry.register(Registry.BLOCK, new Identifier("sarcs_suggestions", "poisonous_potato_block"), POISONOUS_POTATOES_BLOCK);
        BlockRenderLayerMap.INSTANCE.putBlock(POISONOUS_POTATOES_BLOCK, RenderLayer.getCutout());
    }
}

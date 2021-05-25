package net.hyper_pigeon.sarcs_suggestions.mixin;

import net.hyper_pigeon.sarcs_suggestions.registry.SarcsSuggestionsBlocks;
import net.minecraft.item.*;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
public abstract class ItemsMixin {



    @Redirect(method = "<clinit>()V", at = @At(value = "NEW",target = "Lnet/minecraft/item/Item;"),
    slice = @Slice(
            from = @At(value = "CONSTANT", args = "stringValue=poisonous_potato"),
            to = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;POISONOUS_POTATO:Lnet/minecraft/item/Item;", opcode = Opcodes.PUTSTATIC)
    ))
    private static Item changePoisonousPotato(Item.Settings settings){
        return (new AliasedBlockItem(SarcsSuggestionsBlocks.POISONOUS_POTATOES_BLOCK, settings));
    }


}

package net.hyper_pigeon.sarcs_suggestions.mixin;

import net.hyper_pigeon.sarcs_suggestions.SarcsSuggestions;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Shadow public abstract ItemStack getStack();

    @Shadow public abstract Text getName();

    //static ArrayList<TradeOffers.Factory> LOST_AND_FOUND = new ArrayList<>();



    @Inject(method = "tick",at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ItemEntity;remove()V", shift = At.Shift.BEFORE))
    private void addToLostAndFoundTradeQueue(CallbackInfo ci){
        if(this.getStack().getItem() instanceof ToolItem || this.getStack().getItem() instanceof ArmorItem || this.getName() != null){
            SarcsSuggestions.LOST_AND_FOUND.add(this.getStack());
        }
    }


}

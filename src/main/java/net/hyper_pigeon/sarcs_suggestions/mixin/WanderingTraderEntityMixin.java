package net.hyper_pigeon.sarcs_suggestions.mixin;

import net.hyper_pigeon.sarcs_suggestions.SarcsSuggestions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WanderingTraderEntity.class)
public abstract class WanderingTraderEntityMixin extends MerchantEntity {

    public WanderingTraderEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "fillRecipes")
    public void addNewTrades(CallbackInfo info) {
//        TradeOffers.Factory[] factory = LostAndFoundTradeOffers.generateLostAndFoundTrades(4);
//        if (factory != null) {
//            if (this.getOffers() != null) {
//                TradeOfferList tradeOfferList = this.getOffers();
//                if(factory.length > 0){
//                    this.fillRecipesFromPool(tradeOfferList, factory, factory.length);
//                }
//            }
//        }
        TradeOfferList tradeOfferList = this.getOffers();
        for(int i = 0; i < 4; i++){
            if(!SarcsSuggestions.LOST_AND_FOUND.isEmpty()){
                TradeOffer tradeOffer = new TradeOffer(new ItemStack(Items.EMERALD, 16), SarcsSuggestions.LOST_AND_FOUND.poll(), 1, 20, 1);
                tradeOfferList.add(tradeOffer);
            }
        }
    }
}

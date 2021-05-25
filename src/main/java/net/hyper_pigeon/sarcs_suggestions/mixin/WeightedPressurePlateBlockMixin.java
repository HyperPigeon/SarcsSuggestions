package net.hyper_pigeon.sarcs_suggestions.mixin;

import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.WeightedPressurePlateBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(WeightedPressurePlateBlock.class)
public abstract class WeightedPressurePlateBlockMixin extends AbstractPressurePlateBlock {


    protected WeightedPressurePlateBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "getRedstoneOutput(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)I", at = @At("RETURN"), cancellable = true)
    public void getRedstoneOutputByEntitySize(World world, BlockPos pos, CallbackInfoReturnable<Integer> callbackInfoReturnable){
        //if weighted pressure plate is a heavy weighted pressure plate
        if(this.is(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)) {
            //gets a list of all the living entities standing on top of the pressure plate (hopefully)
            List<LivingEntity> entitiesOnPlate = world.getNonSpectatingEntities(LivingEntity.class, BOX.offset(pos));

            //gets a list of all items on the pressure plate
            List<ItemEntity> itemsOnPlate = world.getNonSpectatingEntities(ItemEntity.class, BOX.offset(pos));

            int output = 0;

            //iterates through all the living entities on the pressure plate
            for (LivingEntity livingEntity : entitiesOnPlate) {
                //adds max health * 0.25 to the redstone signal output
                output += livingEntity.getMaxHealth() * (.25);
            }

            for(ItemEntity itemEntity : itemsOnPlate){
                if(itemEntity.getStack().getItem().equals(Items.SHULKER_BOX)){

                    int size = 0;

                    //gets itemstack of the shulker box
                    ItemStack shulkerBoxStack = itemEntity.getStack();

                    //gets shulker box's tag; tag stores information of the shulker box
                    //including it's inventory
                    CompoundTag compoundTag = shulkerBoxStack.getSubTag("BlockEntityTag");

                    if (compoundTag.contains("Items", 9)) {
                        //inits defaulted list
                        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(27, ItemStack.EMPTY);
                        //reads inventory from compound tag and sets it in defaulted list
                        Inventories.fromTag(compoundTag, defaultedList);
                        //set size to the size of the defaulted list
                        size = (int) defaultedList.stream().filter(itemStack -> !itemStack.equals(ItemStack.EMPTY)).count();
                    }

                    //System.out.println(size);

                    //add size + 1 to the redstone output
                    output += (size + 1);

                }
                else {
                    output += 1;
                }
            }

            //iterates through all items on pressure plate

            //sets return value to output
            callbackInfoReturnable.setReturnValue(MathHelper.clamp(output,0,15));
        }
    }
}

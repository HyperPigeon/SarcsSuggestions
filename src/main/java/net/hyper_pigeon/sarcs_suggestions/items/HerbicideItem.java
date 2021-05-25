package net.hyper_pigeon.sarcs_suggestions.items;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class HerbicideItem extends Item {

    public HerbicideItem(Settings settings) {
        super(settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
       //BlockPos blockPos2 = blockPos.offset(context.getSide());
        if (useOnFertilizable(context.getStack(), world, blockPos, context.getPlayer())) {
//            if (!world.isClient) {
//                world.syncWorldEvent(2005, blockPos, 0);
//            }

            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    public static boolean useOnFertilizable(ItemStack stack, World world, BlockPos pos, PlayerEntity playerEntity) {
        BlockPos position = pos;
        BlockState blockState = world.getBlockState(position);
        Random random = new Random();

        if (blockState.getBlock() instanceof Fertilizable) {
            Fertilizable fertilizable = (Fertilizable)blockState.getBlock();
            if (fertilizable.isFertilizable(world, pos, blockState, world.isClient)) {
                if (world instanceof ServerWorld) {
                    if (fertilizable instanceof SpreadableBlock || fertilizable == Blocks.PODZOL){
                        
                        Block block;
                        for(int i = -1; i <= 1; i++){
                            for(int k = -1; k <= 1; k++){
                                block = world.getBlockState(pos.add(i,0,k)).getBlock();
                                if(block instanceof SpreadableBlock || block == Blocks.PODZOL) {
                                    world.setBlockState(pos.add(i, 0, k), Blocks.DIRT.getDefaultState());
                                }
                            }
                        }

//                        world.setBlockState(pos, Blocks.DIRT.getDefaultState());
//
//
//
//                        world.setBlockState(pos.add(-1,0,0), Blocks.DIRT.getDefaultState());
//                        world.setBlockState(pos.add( 1,0,0), Blocks.DIRT.getDefaultState());
//                        world.setBlockState(pos.add( 0,0,1), Blocks.DIRT.getDefaultState());
//                        world.setBlockState(pos.add( 0,0,-1), Blocks.DIRT.getDefaultState());
//                        world.setBlockState(pos.add( 1,0,1), Blocks.DIRT.getDefaultState());
//                        world.setBlockState(pos.add( 1,0,-1), Blocks.DIRT.getDefaultState());
//                        world.setBlockState(pos.add( -1,0,1), Blocks.DIRT.getDefaultState());
//                        world.setBlockState(pos.add( -1,0,-1), Blocks.DIRT.getDefaultState());
                    }
                    else {
                        world.breakBlock(pos,false,playerEntity);
                    }
                    stack.decrement(1);
                }
                return true;
            }

        }
        else if(blockState.getBlock() instanceof LeavesBlock){
            world.breakBlock(pos,false,playerEntity);
            stack.decrement(1);
        }

        return false;
    }


}

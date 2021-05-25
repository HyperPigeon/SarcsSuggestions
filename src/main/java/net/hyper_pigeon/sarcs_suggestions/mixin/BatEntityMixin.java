package net.hyper_pigeon.sarcs_suggestions.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BatEntity.class)
public abstract class BatEntityMixin extends AmbientEntity {
    protected BatEntityMixin(EntityType<? extends AmbientEntity> entityType, World world) {
        super(entityType, world);
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if(itemStack.getItem() == Items.SPIDER_EYE){
            itemStack.decrement(1);
            this.playSound(SoundEvents.BLOCK_PORTAL_TRAVEL, 0.33f, 1f);
            if(!world.isClient() && player instanceof ServerPlayerEntity){
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
                BlockPos spawnPoint = serverPlayerEntity.getSpawnPointPosition();
                if (spawnPoint != null) {
                    player.teleport(spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ());
                }
                return ActionResult.CONSUME;
            }
        }
        return super.interactMob(player,hand);
    }
}

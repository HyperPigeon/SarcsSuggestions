package net.hyper_pigeon.sarcs_suggestions.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(LlamaEntity.class)
public abstract class LlamaEntityMixin extends AbstractDonkeyEntity implements RangedAttackMob {

    protected LlamaEntityMixin(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
        super(entityType, world);
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if(itemStack.getItem() == Items.FEATHER){
            if(this.getPrimaryPassenger() != null) {
                this.playSound(SoundEvents.ENTITY_PANDA_SNEEZE, 1, -3f);

                double d = this.getX() - (double)this.getWidth() * Math.sin((double)(this.bodyYaw * 0.017453292F)) ;
                double e = this.getY() + (double)this.getHeight() - 0.3D;
                double f = this.getZ() + (double)this.getWidth() * Math.cos((double)(this.bodyYaw * 0.017453292F));

                for(int i = 0; i < 25; i++){
                    getEntityWorld().addParticle(ParticleTypes.SNEEZE, d,e,f,
                            0.1, 0, 0.1);
                }

                this.getPrimaryPassenger().stopRiding();
                return ActionResult.success(this.world.isClient);
            }
        }
        return super.interactMob(player,hand);
    }

    public void tick(){
        if(this.isTame()) {
            this.checkBlockCollision();
            List<Entity> list = this.world.getOtherEntities(this, this.getBoundingBox().expand(0.25000000298023224D, -0.009999999776482582D, 0.20000000298023224D), EntityPredicates.canBePushedBy(this));
            if (!list.isEmpty()) {
                boolean bl = !this.world.isClient && !(this.getPrimaryPassenger() instanceof PlayerEntity) && !(this.getPrimaryPassenger() instanceof WanderingTraderEntity);

                for (int j = 0; j < list.size(); ++j) {
                    Entity entity = (Entity) list.get(j);
                    if (!entity.hasPassenger(this)) {
                        if (bl && this.getPassengerList().size() < 1 && !entity.hasVehicle() && entity.getWidth() < this.getWidth() && entity instanceof LivingEntity && !(entity instanceof PlayerEntity) && !(entity instanceof LlamaEntity)) {
                            entity.startRiding(this);
                        } else {
                            this.pushAwayFrom(entity);
                        }
                    }
                }
            }
        }
        super.tick();
    }
}

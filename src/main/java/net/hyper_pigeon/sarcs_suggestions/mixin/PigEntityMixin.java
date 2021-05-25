package net.hyper_pigeon.sarcs_suggestions.mixin;

import net.hyper_pigeon.sarcs_suggestions.goal.FreezeInFearGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PigEntity.class)
public abstract class PigEntityMixin extends AnimalEntity {
    protected PigEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    public void addFearGoal(CallbackInfo callbackInfo){
        this.goalSelector.add(3,new FreezeInFearGoal<>(this, PolarBearEntity.class,10));
    }
}

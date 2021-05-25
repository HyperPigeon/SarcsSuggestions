package net.hyper_pigeon.sarcs_suggestions.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.predicate.entity.EntityPredicates;

import java.util.function.Predicate;

public class FreezeInFearGoal<T extends LivingEntity> extends Goal {

    protected final PathAwareEntity mob;
    protected final Class<T> classToFear;
    protected final float freezeDistance;
    protected final TargetPredicate withinRangePredicate;
    protected final float originalMovementSpeed;


    protected T targetEntity;

    public FreezeInFearGoal(PathAwareEntity mob, Class<T> freezeInFearType, float freezeDistance){
        this.mob = mob;
        this.classToFear = freezeInFearType;
        this.freezeDistance = freezeDistance;

        this.originalMovementSpeed = this.mob.getMovementSpeed();

        Predicate exceptCreativeOrSpectator = EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR;
        this.withinRangePredicate = new TargetPredicate().setBaseMaxDistance(freezeDistance).setPredicate(exceptCreativeOrSpectator::test);
    }

    public FreezeInFearGoal(PathAwareEntity mob, Class<T> freezeInFearType, float freezeDistance, TargetPredicate targetPredicate){
        this.mob = mob;
        this.classToFear = freezeInFearType;
        this.freezeDistance = freezeDistance;
        this.withinRangePredicate = targetPredicate;
        this.originalMovementSpeed = this.mob.getMovementSpeed();
    }

    @Override
    public boolean canStart() {
        this.targetEntity = this.mob.world.getClosestEntityIncludingUngeneratedChunks(this.classToFear, this.withinRangePredicate, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ(), this.mob.getBoundingBox().expand((double)this.freezeDistance, 3.0D, (double)this.freezeDistance));
        if (this.targetEntity == null) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean shouldContinue(){
        if(this.targetEntity.isAlive()){
            return this.mob.squaredDistanceTo(this.targetEntity) <= freezeDistance;
        }
        return false;
    }

    public void start() {
        this.mob.getNavigation().setSpeed(0);
    }

    public void stop() {
        this.targetEntity = null;
        this.mob.getNavigation().setSpeed(originalMovementSpeed);
    }

//    public void tick() {
//        this.mob.getNavigation().setSpeed(0);
//    }
}

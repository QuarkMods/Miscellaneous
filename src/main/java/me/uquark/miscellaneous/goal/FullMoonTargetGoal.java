package me.uquark.miscellaneous.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class FullMoonTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
    public FullMoonTargetGoal(MobEntity mob, Class targetClass, boolean checkVisibility) {
        super(mob, targetClass, checkVisibility);
    }

    public FullMoonTargetGoal(MobEntity mob, Class targetClass, boolean checkVisibility, Predicate targetPredicate) {
        super(mob, targetClass, checkVisibility, targetPredicate);
    }

    public FullMoonTargetGoal(MobEntity mob, Class targetClass, boolean checkVisibility, boolean checkCanNavigate) {
        super(mob, targetClass, checkVisibility, checkCanNavigate);
    }

    public FullMoonTargetGoal(MobEntity mob, Class targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, @Nullable Predicate targetPredicate) {
        super(mob, targetClass, reciprocalChance, checkVisibility, checkCanNavigate, targetPredicate);
    }

    @Override
    public boolean canStart() {
        // check full moon
        return (mob.world.getMoonPhase() == 0) && super.canStart();
    }
}

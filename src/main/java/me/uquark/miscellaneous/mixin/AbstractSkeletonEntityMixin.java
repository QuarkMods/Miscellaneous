package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.goal.FullMoonTargetGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin extends HostileEntity {
    protected AbstractSkeletonEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("HEAD"))
    protected void initFullMoon(CallbackInfo ci) {
        targetSelector.add(1, new FullMoonTargetGoal<HostileEntity>(
            this,
            HostileEntity.class,
            true,
            (livingEntity) -> !(livingEntity instanceof AbstractSkeletonEntity)
        ));
    }
}
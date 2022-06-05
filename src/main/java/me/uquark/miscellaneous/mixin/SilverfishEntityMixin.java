package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.goal.FullMoonTargetGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SilverfishEntity.class)
public abstract class SilverfishEntityMixin extends HostileEntity {
    protected SilverfishEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("HEAD"))
    protected void initFullMoon(CallbackInfo ci) {
        targetSelector.add(1, new FullMoonTargetGoal<HostileEntity>(
                this,
                HostileEntity.class,
                true,
                (livingEntity) -> !(livingEntity instanceof SilverfishEntity)
        ));
    }
}

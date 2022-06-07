package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.goal.FullMoonTargetGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpiderEntity.class)
public abstract class SpiderEntityMixin extends HostileEntity {
    protected SpiderEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("HEAD"))
    protected void initFullMoon(CallbackInfo ci) {
        targetSelector.add(1, new FullMoonTargetGoal<HostileEntity>(
                this,
                HostileEntity.class,
                true,
                (livingEntity) -> !(livingEntity instanceof SpiderEntity)
        ));
    }
}

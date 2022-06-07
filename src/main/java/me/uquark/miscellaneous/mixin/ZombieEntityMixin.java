package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.goal.FullMoonTargetGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin extends HostileEntity {
    protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("HEAD"))
    protected void initFullMoon(CallbackInfo ci) {
        targetSelector.add(1, new FullMoonTargetGoal<HostileEntity>(
                this,
                HostileEntity.class,
                true,
                (livingEntity) -> !(livingEntity instanceof ZombieEntity)
        ));
    }
}

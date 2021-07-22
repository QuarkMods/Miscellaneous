package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.util.PathManager;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(GrassBlock.class)
public abstract class GrassBlockMixin extends SpreadableBlock implements Fertilizable {
    protected GrassBlockMixin(Settings settings) {
        super(settings);
    }

    private static final PathManager PATH_MANAGER = new PathManager(50, 100);

    private static void turnIntoPath(World world, BlockPos pos) {
        world.setBlockState(pos, Blocks.DIRT_PATH.getDefaultState());
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient && entity instanceof PlayerEntity) {
            if (PATH_MANAGER.step(pos, entity.getId())) {
                turnIntoPath(world, pos);
                PATH_MANAGER.forget(pos);
            }
        }
    }
}

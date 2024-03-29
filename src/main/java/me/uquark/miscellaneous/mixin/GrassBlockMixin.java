package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.miscellaneous.util.PathManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GrassBlock.class)
public abstract class GrassBlockMixin extends SpreadableBlock implements Fertilizable {
    protected GrassBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient) {
            Miscellaneous.PATH_MANAGER.step(world, pos, entity);
        }
    }
}

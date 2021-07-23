package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.Miscellaneous;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirtPathBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DirtPathBlock.class)
public abstract class DirtPathBlockMixin extends Block {
    protected DirtPathBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient) {
            Miscellaneous.PATH_MANAGER.step(world, pos, entity);
        }
    }
}

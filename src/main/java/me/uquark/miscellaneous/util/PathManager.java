package me.uquark.miscellaneous.util;

import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.RootedDirtBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PathManager {
    private static final Random random = new Random();

    private static class PathBlock {
        public final int transformationThreshold;
        public final int recoverThreshold;
        public final World world;
        public final BlockPos blockPos;
        public int steps;
        public long lastStep;
        public boolean isPath;

        public PathBlock(World world, BlockPos blockPos, int minTransformationThreshold, int maxTransformationThreshold, int minRecoverThreshold, int maxRecoverThreshold) {
            this.world = world;
            this.blockPos = blockPos;
            transformationThreshold = random.nextInt(maxTransformationThreshold - minTransformationThreshold + 1) + minTransformationThreshold;
            recoverThreshold = random.nextInt(maxRecoverThreshold - minRecoverThreshold + 1) + minRecoverThreshold;
        }

        public void step() {
            steps++;

            lastStep = System.currentTimeMillis();

            if (steps >= transformationThreshold) {
                turn();
            }
        }

        public void turn() {
            world.setBlockState(blockPos, Blocks.DIRT_PATH.getDefaultState());
            isPath = true;
        }

        public void recover() {
            world.setBlockState(blockPos, Blocks.GRASS_BLOCK.getDefaultState());
            steps = 0;
            isPath = false;
        }

        public void tickRecover() {
            if (System.currentTimeMillis() - lastStep >= recoverThreshold) {
                recover();
            }
        }
    }

    private static class StepperEntity {
        public PathBlock lastBlock;

        public void step(PathBlock block) {
            lastBlock = block;
        }
    }

    private static final int MIN_TRANSFORMATION_THRESHOLD = 1;
    private static final int MAX_TRANSFORMATION_THRESHOLD = 1;
    private static final int MIN_RECOVER_THRESHOLD = 5000;
    private static final int MAX_RECOVER_THRESHOLD = 10000;
    private static final int RECOVER_TICK_PERIOD = 1000;

    private final HashMap<Long, PathBlock> pathBlocks = new HashMap<>();
    private final HashMap<Integer, StepperEntity> steppers = new HashMap<>();

    private final Timer recoverTimer = new Timer();

    public PathManager() {
        recoverTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                pathBlocks.forEach((aLong, pathBlock) -> {
                    if (pathBlock.isPath) {
                        pathBlock.tickRecover();
                    }
                });
            }
        }, 1, RECOVER_TICK_PERIOD);
    }

    public void step(World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof PlayerEntity || entity instanceof MerchantEntity)) {
            return;
        }

        PathBlock pathBlock = pathBlocks.get(pos.asLong());
        if (pathBlock == null) {
            pathBlock = new PathBlock(world, pos, MIN_TRANSFORMATION_THRESHOLD, MAX_TRANSFORMATION_THRESHOLD, MIN_RECOVER_THRESHOLD, MAX_RECOVER_THRESHOLD);
            pathBlocks.put(pos.asLong(), pathBlock);
        }

        StepperEntity stepper = steppers.get(entity.getId());
        if (stepper == null) {
            stepper = new StepperEntity();
            steppers.put(entity.getId(), stepper);
        }

        if (stepper.lastBlock == pathBlock) {
            pathBlock.lastStep = System.currentTimeMillis();
            return;
        }

        stepper.step(pathBlock);
        pathBlock.step();
    }
}

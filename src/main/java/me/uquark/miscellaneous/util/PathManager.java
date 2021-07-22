package me.uquark.miscellaneous.util;

import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Random;

public class PathManager {
    private static final Random random = new Random();

    private final int MIN_TRANSFORMATION_THRESHOLD;
    private final int MAX_TRANSFORMATION_THRESHOLD;
    private final HashMap<Long, Integer> threshold = new HashMap<>();
    private final HashMap<Long, Integer> steps = new HashMap<>();
    private final HashMap<Integer, Long> lastStep = new HashMap<>();

    public PathManager(int minTransformationThreshold, int maxTransformationThreshold) {
        MIN_TRANSFORMATION_THRESHOLD = minTransformationThreshold;
        MAX_TRANSFORMATION_THRESHOLD = maxTransformationThreshold;
    }

    private void initBlock(Long key) {
        threshold.put(key, random.nextInt(MAX_TRANSFORMATION_THRESHOLD - MIN_TRANSFORMATION_THRESHOLD + 1) + MIN_TRANSFORMATION_THRESHOLD);
        steps.put(key, 0);
    }

    public boolean step(BlockPos pos, int stepperId) {
        Long key = pos.asLong();

        if (lastStep.containsKey(stepperId)) {
            if (lastStep.get(stepperId).equals(key)) {
                return false;
            }
        }

        lastStep.put(stepperId, key);

        if (!threshold.containsKey(key) || !steps.containsKey(key)) {
            initBlock(key);
        }

        int currentSteps = steps.get(key);
        currentSteps++;
        steps.put(key, currentSteps);

        return currentSteps >= threshold.get(key);
    }

    public void forget(BlockPos pos) {
        Long key = pos.asLong();

        steps.remove(key);
        threshold.remove(key);
    }
}

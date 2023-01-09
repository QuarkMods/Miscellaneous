package me.uquark.miscellaneous.item;

import me.uquark.miscellaneous.Miscellaneous;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class SickleItem extends MiningToolItem {
    private enum SickleHarvestBehavior {
        X,
        Z,
        NE,
        NW,
        SE,
        SW;

        public void run(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
            ArrayList<Vec3i> deltas = new ArrayList<>();
            switch (this) {
                case X -> {
                    deltas.add(new Vec3i(-1, 0, 0));
                    deltas.add(new Vec3i(1, 0, 0));
                }
                case Z -> {
                    deltas.add(new Vec3i(0, 0, -1));
                    deltas.add(new Vec3i(0, 0, 1));
                }
                case NE -> {
                    deltas.add(new Vec3i(-1, 0, 0));
                    deltas.add(new Vec3i(0, 0, 1));
                }
                case NW -> {
                    deltas.add(new Vec3i(1, 0, 0));
                    deltas.add(new Vec3i(0, 0, 1));
                }
                case SE -> {
                    deltas.add(new Vec3i(-1, 0, 0));
                    deltas.add(new Vec3i(0, 0, -1));
                }
                case SW -> {
                    deltas.add(new Vec3i(1, 0, 0));
                    deltas.add(new Vec3i(0, 0, -1));
                }
            }
            for (Vec3i delta : deltas) {
                BlockPos nPos = pos.add(delta);
                BlockState nState = world.getBlockState(nPos);
                if (nState == null)
                    continue;
                if (nState.getBlock() == state.getBlock()) {
                    world.breakBlock(nPos, true, miner);
                    stack.damage(1, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                }
            }
        }
    }

    private static final Random random = new Random();
    protected SickleItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(attackDamage, attackSpeed, material, BlockTags.HOE_MINEABLE, settings);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(this));
    }

    public static void onHarvest(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getBlock() instanceof CropBlock) {
            if (miner.headYaw >= -22.5 && miner.headYaw < 22.5) {
                SickleHarvestBehavior.X.run(stack, world, state, pos, miner);
            }
            if (miner.headYaw >= 22.5 && miner.headYaw < 67.5) {
                SickleHarvestBehavior.SW.run(stack, world, state, pos, miner);
            }
            if (miner.headYaw >= 67.5 && miner.headYaw < 112.5) {
                SickleHarvestBehavior.Z.run(stack, world, state, pos, miner);
            }
            if (miner.headYaw >= 112.5 && miner.headYaw < 157.5) {
                SickleHarvestBehavior.NW.run(stack, world, state, pos, miner);
            }
            if (Math.abs(miner.headYaw) >= 157.5 && Math.abs(miner.headYaw) < 180 && miner.headYaw < 0) {
                SickleHarvestBehavior.X.run(stack, world, state, pos, miner);
            }
            if (miner.headYaw >= -157.5 && miner.headYaw < -112.5) {
                SickleHarvestBehavior.NE.run(stack, world, state, pos, miner);
            }
            if (miner.headYaw >= -112.5 && miner.headYaw < -67.5) {
                SickleHarvestBehavior.Z.run(stack, world, state, pos, miner);
            }
            if (miner.headYaw >= -67.5 && miner.headYaw < -22.5) {
                SickleHarvestBehavior.SE.run(stack, world, state, pos, miner);
            }
        }
    }

    public static void register() {
        Identifier woodId = new Identifier(Miscellaneous.MODID, "wooden_sickle");
        Identifier stoneId = new Identifier(Miscellaneous.MODID, "stone_sickle");
        Identifier ironId = new Identifier(Miscellaneous.MODID, "iron_sickle");
        Identifier goldId = new Identifier(Miscellaneous.MODID, "golden_sickle");
        Identifier diamondId = new Identifier(Miscellaneous.MODID, "diamond_sickle");
        Identifier netheriteId = new Identifier(Miscellaneous.MODID, "netherite_sickle");
        Registry.register(Registries.ITEM, woodId, new SickleItem(ToolMaterials.WOOD, 0, -3.0f, new Item.Settings()));
        Registry.register(Registries.ITEM, stoneId, new SickleItem(ToolMaterials.STONE, 0, -3.0f, new Item.Settings()));
        Registry.register(Registries.ITEM, ironId, new SickleItem(ToolMaterials.IRON, 0, -3.0f, new Item.Settings()));
        Registry.register(Registries.ITEM, goldId, new SickleItem(ToolMaterials.GOLD, 0, -3.0f, new Item.Settings()));
        Registry.register(Registries.ITEM, diamondId, new SickleItem(ToolMaterials.DIAMOND, 0, -3.0f, new Item.Settings()));
        Registry.register(Registries.ITEM, netheriteId, new SickleItem(ToolMaterials.NETHERITE, 0, -3.0f, new Item.Settings()));
    }
}

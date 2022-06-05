package me.uquark.miscellaneous.behavior;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CleanWoolBehavior implements CauldronBehavior {
    @Override
    public ActionResult interact(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
        if (!world.isClient) {
            ItemStack itemStack = new ItemStack(Items.WHITE_WOOL);
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
            if (stack.isEmpty()) {
                player.setStackInHand(hand, itemStack);
            } else if (player.getInventory().insertStack(itemStack)) {
                player.playerScreenHandler.syncState();
            } else {
                player.dropItem(itemStack, false);
            }
            LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        }
        return ActionResult.success(world.isClient);
    }

    public static void register() {
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.BLACK_WOOL, new CleanWoolBehavior());
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.BLUE_WOOL, new CleanWoolBehavior());
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.BROWN_WOOL, new CleanWoolBehavior());
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.CYAN_WOOL, new CleanWoolBehavior());
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.GRAY_WOOL, new CleanWoolBehavior());
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.GREEN_WOOL, new CleanWoolBehavior());
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.LIGHT_BLUE_WOOL, new CleanWoolBehavior());
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.LIGHT_GRAY_WOOL, new CleanWoolBehavior());
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.LIME_WOOL, new CleanWoolBehavior());
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.MAGENTA_WOOL, new CleanWoolBehavior());
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.ORANGE_WOOL, new CleanWoolBehavior());
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.PINK_WOOL, new CleanWoolBehavior());
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.PURPLE_WOOL, new CleanWoolBehavior());
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.RED_WOOL, new CleanWoolBehavior());
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.YELLOW_WOOL, new CleanWoolBehavior());
    }
}

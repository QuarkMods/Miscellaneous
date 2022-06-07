package me.uquark.miscellaneous.enchantment;

import com.chocohead.mm.api.ClassTinkerers;
import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.miscellaneous.util.TreeDefinition;
import me.uquark.quarkcore.enchantment.AbstractEnchantment;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LumberjackEnchantment extends AbstractEnchantment {
    public static final EnchantmentTarget AXE = ClassTinkerers.getEnum(EnchantmentTarget.class, "AXE");

    protected LumberjackEnchantment() {
        super(Miscellaneous.MODID, "lumberjack", Rarity.UNCOMMON, AXE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 30;
    }

    @Override
    public int getMaxPower(int level) {
        return 80;
    }

    public void cutDown(PlayerEntity player, World world, BlockPos pos) {
        if (world.isClient)
            return;
        TreeDefinition treeDefinition = new TreeDefinition(world, pos);
        if (!treeDefinition.isTree)
            return;
        ItemStack toolStack = player.getMainHandStack();
        for (BlockPos treeBlock : treeDefinition.blocks) {
            if (treeBlock.equals(pos))
                continue;
            float hardness = world.getBlockState(treeBlock).getHardness(world, pos);
            boolean broken = world.breakBlock(treeBlock, false, player);
            if (broken && hardness != 0.0f) {
                toolStack.damage(2, player, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
            }
        }
        if (!player.isCreative())
            Block.dropStack(world, pos, new ItemStack(treeDefinition.type.getLog(), treeDefinition.blocks.size() - 1));
    }
}

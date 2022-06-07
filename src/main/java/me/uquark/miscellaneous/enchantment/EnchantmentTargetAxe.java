package me.uquark.miscellaneous.enchantment;

import me.uquark.miscellaneous.mixin.EnchantmentTargetMixin;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;

public class EnchantmentTargetAxe extends EnchantmentTargetMixin {
    @Override
    public boolean isAcceptableItem(Item item) {
        return item instanceof AxeItem;
    }
}

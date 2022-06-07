package me.uquark.miscellaneous.enchantment;

import me.uquark.miscellaneous.mixin.EnchantmentTargetMixin;
import net.minecraft.item.Item;

public class EnchantmentTargetAny extends EnchantmentTargetMixin {
    @Override
    public boolean isAcceptableItem(Item item) {
        return true;
    }
}

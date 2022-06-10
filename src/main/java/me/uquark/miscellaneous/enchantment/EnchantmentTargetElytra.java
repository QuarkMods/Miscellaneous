package me.uquark.miscellaneous.enchantment;

import me.uquark.miscellaneous.mixin.EnchantmentTargetMixin;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;

public class EnchantmentTargetElytra extends EnchantmentTargetMixin {
    @Override
    public boolean isAcceptableItem(Item item) {
        return item instanceof ElytraItem;
    }
}

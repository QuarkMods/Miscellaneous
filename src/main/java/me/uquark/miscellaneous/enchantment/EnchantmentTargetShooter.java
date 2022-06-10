package me.uquark.miscellaneous.enchantment;

import me.uquark.miscellaneous.mixin.EnchantmentTargetMixin;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;

public class EnchantmentTargetShooter extends EnchantmentTargetMixin {
    @Override
    public boolean isAcceptableItem(Item item) {
        return item instanceof BowItem || item instanceof CrossbowItem;
    }
}

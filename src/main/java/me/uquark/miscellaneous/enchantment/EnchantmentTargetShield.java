package me.uquark.miscellaneous.enchantment;

import me.uquark.miscellaneous.mixin.EnchantmentTargetMixin;
import net.minecraft.item.Item;
import net.minecraft.item.ShieldItem;

public class EnchantmentTargetShield extends EnchantmentTargetMixin {
    @Override
    public boolean isAcceptableItem(Item item) {
        return item instanceof ShieldItem;
    }
}

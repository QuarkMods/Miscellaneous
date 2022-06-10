package me.uquark.miscellaneous.enchantment;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.enchantment.AbstractEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class RingProtectionEnchantment extends AbstractEnchantment {

    protected RingProtectionEnchantment() {
        super(Miscellaneous.MODID, "ring_protection", Rarity.COMMON, CustomEnchantmentTarget.SHIELD, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinPower(int level) {
        return 30 + (level-1) * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return getMinPower(level) + 20;
    }

    public int getMaxUsingDuration(ItemStack stack) {
        if (!isEnchanted(stack))
            return 0;
        return (getEnchantmentLevel(stack) * 2) * 1000;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != Enchantments.REFLECTION_ENCHANTMENT && super.canAccept(other);
    }
}

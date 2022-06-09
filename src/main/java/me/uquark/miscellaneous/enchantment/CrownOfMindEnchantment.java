package me.uquark.miscellaneous.enchantment;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.enchantment.AbstractEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class CrownOfMindEnchantment extends AbstractEnchantment {
    protected CrownOfMindEnchantment() {
        super(Miscellaneous.MODID, "crown_of_mind", Rarity.RARE, EnchantmentTarget.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinPower(int level) {
        return 30 + (level-1) * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return getMinPower(level) + 20;
    }

    public float getExperienceFactor(int level) {
        if (level == 0)
            return 1;
        if (level == 5) {
            return 2;
        }

        return 0.15f + (level - 1) * 0.1f;
    }
}

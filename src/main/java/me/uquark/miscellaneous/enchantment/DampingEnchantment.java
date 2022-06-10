package me.uquark.miscellaneous.enchantment;

import com.chocohead.mm.api.ClassTinkerers;
import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.enchantment.AbstractEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class DampingEnchantment extends AbstractEnchantment {
    public static final EnchantmentTarget ELYTRA = ClassTinkerers.getEnum(EnchantmentTarget.class, "ELYTRA");

    protected DampingEnchantment() {
        super(Miscellaneous.MODID, "damping", Rarity.COMMON, ELYTRA, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getMinPower(int level) {
        return 20 + (level - 1) * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return getMinPower(level) + 20;
    }

    public float getDamageFactor(int level) {
        return 1.0f - (level) * 0.2f;
    }
}

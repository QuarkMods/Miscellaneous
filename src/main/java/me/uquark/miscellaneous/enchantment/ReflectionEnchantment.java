package me.uquark.miscellaneous.enchantment;

import com.chocohead.mm.api.ClassTinkerers;
import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.enchantment.AbstractEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class ReflectionEnchantment extends AbstractEnchantment {
    public static final EnchantmentTarget SHIELD = ClassTinkerers.getEnum(EnchantmentTarget.class, "SHIELD");

    protected ReflectionEnchantment() {
        super(Miscellaneous.MODID, "reflection", Rarity.RARE, SHIELD, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinPower(int level) {
        return 25 + level * 5;
    }

    @Override
    public int getMaxPower(int level) {
        return getMinPower(level) + 50;
    }
}

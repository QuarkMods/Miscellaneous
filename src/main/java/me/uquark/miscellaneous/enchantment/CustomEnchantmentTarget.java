package me.uquark.miscellaneous.enchantment;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.enchantment.EnchantmentTarget;

public class CustomEnchantmentTarget {
    public static final EnchantmentTarget ANY = ClassTinkerers.getEnum(EnchantmentTarget.class, "ANY");
    public static final EnchantmentTarget ELYTRA = ClassTinkerers.getEnum(EnchantmentTarget.class, "ELYTRA");
    public static final EnchantmentTarget SHOOTER = ClassTinkerers.getEnum(EnchantmentTarget.class, "SHOOTER");
    public static final EnchantmentTarget AXE = ClassTinkerers.getEnum(EnchantmentTarget.class, "AXE");
    public static final EnchantmentTarget SHIELD = ClassTinkerers.getEnum(EnchantmentTarget.class, "SHIELD");
    public static final EnchantmentTarget ALL_WEAPON = ClassTinkerers.getEnum(EnchantmentTarget.class, "ALL_WEAPON");
}

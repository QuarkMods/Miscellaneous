package me.uquark.miscellaneous;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class EarlyRiser implements Runnable {
    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();
        String enchantmentTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1886");
        ClassTinkerers.enumBuilder(enchantmentTarget, new String[0])
                .addEnumSubclass("ANY", "me.uquark.miscellaneous.enchantment.EnchantmentTargetAny")
                .addEnumSubclass("AXE", "me.uquark.miscellaneous.enchantment.EnchantmentTargetAxe")
                .addEnumSubclass("SHIELD", "me.uquark.miscellaneous.enchantment.EnchantmentTargetShield")
                .addEnumSubclass("ELYTRA", "me.uquark.miscellaneous.enchantment.EnchantmentTargetElytra")
                .build();
    }
}

package me.uquark.miscellaneous.enchantment;

import com.chocohead.mm.api.ClassTinkerers;
import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.enchantment.AbstractEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class CharmOfComebackEnchantment extends AbstractEnchantment {
    public static final EnchantmentTarget ANY = ClassTinkerers.getEnum(EnchantmentTarget.class, "ANY");

    protected CharmOfComebackEnchantment() {
        super(Miscellaneous.MODID, "charm_of_comeback", Rarity.RARE, ANY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 30;
    }

    @Override
    public int getMaxPower(int level) {
        return 80;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != Enchantments.BINDING_CURSE && other != Enchantments.VANISHING_CURSE && super.canAccept(other);
    }
}

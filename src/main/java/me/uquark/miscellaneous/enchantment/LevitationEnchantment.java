package me.uquark.miscellaneous.enchantment;

import com.chocohead.mm.api.ClassTinkerers;
import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.enchantment.AbstractEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class LevitationEnchantment extends AbstractEnchantment {
    protected LevitationEnchantment() {
        super(Miscellaneous.MODID, "levitation", Rarity.COMMON, CustomEnchantmentTarget.SHOOTER, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinPower(int level) {
        return 20 + (level-1) * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMaxPower(level) + 20;
    }

    public int getDuration(int level) {
        return level * 20;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        super.onTargetDamaged(user, target, level);
        if (!user.world.isClient)
            if (target instanceof LivingEntity)
                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, getDuration(level)), user);
    }
}

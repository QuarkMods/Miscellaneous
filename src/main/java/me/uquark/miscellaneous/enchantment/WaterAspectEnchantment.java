package me.uquark.miscellaneous.enchantment;

import com.chocohead.mm.api.ClassTinkerers;
import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.enchantment.AbstractEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.StriderEntity;

public class WaterAspectEnchantment extends AbstractEnchantment {

    protected WaterAspectEnchantment() {
        super(Miscellaneous.MODID, "water_aspect", Rarity.COMMON, CustomEnchantmentTarget.ALL_WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 20;
    }

    @Override
    public int getMaxPower(int level) {
        return 40;
    }

    public float getDamageFactor(LivingEntity user, Entity target) {
        float factor = 1;
        if (user.isWet())
            if (user.isInsideWaterOrBubbleColumn()) {
                factor += 0.4f;
            } else if (user.isTouchingWaterOrRain()) {
                factor += 0.2f;
            }
        if (target instanceof LivingEntity) {
            if (((LivingEntity) target).hurtByWater()) {
                factor += 0.2;
            } else {
                boolean netherEntity = target instanceof BlazeEntity ||
                    target instanceof GhastEntity ||
                    target instanceof HoglinEntity ||
                    target instanceof MagmaCubeEntity ||
                    target instanceof StriderEntity ||
                    target instanceof WitherSkeletonEntity ||
                    target instanceof AbstractPiglinEntity ||
                    target instanceof ZombifiedPiglinEntity;
                if (netherEntity)
                    factor += 0.2;
            }
        }

        return factor;
    }
}

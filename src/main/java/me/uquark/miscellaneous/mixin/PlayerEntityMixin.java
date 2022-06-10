package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.effect.Effects;
import me.uquark.miscellaneous.enchantment.Enchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow
    @Final
    private PlayerInventory inventory;
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    /**
     * @author UQuark
     * @reason Bound Inventory
     */
    @Overwrite
    public boolean shouldAlwaysDropXp() {
        return getStatusEffect(Effects.BOUND_INVENTORY_EFFECT) == null;
    }

    @ModifyVariable(method = "addExperience", at = @At("HEAD"), ordinal = 0)
    public int addExperience(int experience) {
        if (experience < 0)
            return experience;
        int level = EnchantmentHelper.getEquipmentLevel(Enchantments.CROWN_OF_MIND_ENCHANTMENT, this);
        float factor = Enchantments.CROWN_OF_MIND_ENCHANTMENT.getExperienceFactor(level);
        return (int)Math.ceil((float)experience * factor);
    }
}

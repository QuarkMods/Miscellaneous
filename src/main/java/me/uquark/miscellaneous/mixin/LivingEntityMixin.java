package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    public abstract boolean blockedByShield(DamageSource source);
    @Shadow
    protected abstract void damageShield(float amount);
    @Shadow
    protected abstract void takeShieldHit(LivingEntity attacker);
    @Shadow
    protected ItemStack activeItemStack = ItemStack.EMPTY;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "damage", at = @At("HEAD"))
    public void damage(DamageSource source, float amount, CallbackInfoReturnable ci) {
        if (amount > 0.0f && this.blockedByShield(source) && Enchantments.REFLECTION_ENCHANTMENT.isEnchanted(activeItemStack)) {
            float reflectedAmount = amount;
            switch (Enchantments.REFLECTION_ENCHANTMENT.getEnchantmentLevel(activeItemStack)) {
                case 1 -> reflectedAmount *= 0.45;
                case 2 -> reflectedAmount *= 0.65;
                case 3 -> reflectedAmount *= 0.85;
                default -> reflectedAmount = 0;
            }

            Entity entity = source.getSource();
            if (!source.isProjectile() && entity instanceof LivingEntity)
                entity.damage(DamageSource.MAGIC, reflectedAmount);
        }
    }
}

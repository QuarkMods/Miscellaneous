package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.enchantment.Enchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    protected abstract boolean isBlocking();
    @Shadow
    public abstract boolean blockedByShield(DamageSource source);
    @Shadow
    protected ItemStack activeItemStack = ItemStack.EMPTY;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "blockedByShield", at = @At("HEAD"), cancellable = true)
    public void blockedByShieldMixin(DamageSource source, CallbackInfoReturnable<Boolean> ci) {
        if (Enchantments.RING_PROTECTION_ENCHANTMENT.isEnchanted(activeItemStack)) {
            Vec3d sourcePos = source.getPosition();
            Entity entity = source.getSource();

            boolean canPierce = entity instanceof PersistentProjectileEntity && ((PersistentProjectileEntity) entity).getPierceLevel() > 0;

            if (!source.bypassesArmor() && this.isBlocking() && !canPierce && sourcePos != null) {
                ci.setReturnValue(true);
            }
        }
    }

    @Inject(method = "damage", at = @At("HEAD"))
    public void damageMixin(DamageSource source, float amount, CallbackInfoReturnable ci) {
        if (amount > 0.0f && this.blockedByShield(source) && Enchantments.REFLECTION_ENCHANTMENT.isEnchanted(activeItemStack)) {
            float reflectedAmount = amount * Enchantments.REFLECTION_ENCHANTMENT.getReflectionFactor(activeItemStack);
            Entity entity = source.getSource();
            if (!source.isProjectile() && entity instanceof LivingEntity)
                entity.damage(DamageSource.MAGIC, reflectedAmount);
        }
    }

    @ModifyArg(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), index = 1)
    public float handleElytraDamage(float d) {
        int level = EnchantmentHelper.getEquipmentLevel(Enchantments.DAMPING_ENCHANTMENT, (LivingEntity)(Entity)this);
        if (level > 0) {
            return d * Enchantments.DAMPING_ENCHANTMENT.getDamageFactor(level);
        }
        return d;
    }
}

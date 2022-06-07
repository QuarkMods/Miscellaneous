package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

@Mixin(ShieldItem.class)
public abstract class ShieldItemMixin extends Item {
    private static final Timer timer = new Timer();
    private static final HashMap<LivingEntity, TimerTask> tasks = new HashMap<>();

    public ShieldItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "use", at = @At("HEAD"))
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable ci) {
        if (!world.isClient) {
            if (Enchantments.RING_PROTECTION_ENCHANTMENT.isEnchanted(user.getStackInHand(hand))) {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        user.disableShield(true);
                    }
                };
                tasks.put(user, task);
                timer.schedule(task, Enchantments.RING_PROTECTION_ENCHANTMENT.getMaxUsingDuration(user.getStackInHand(hand)));
            }
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!world.isClient) {
            if (Enchantments.RING_PROTECTION_ENCHANTMENT.isEnchanted(stack)) {
                TimerTask task = tasks.get(user);
                if (task == null) {
                    ((PlayerEntity) user).disableShield(true);
                }
                else if (task.cancel()) {
                    task.run();
                }
            }
        }
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }
}

package me.uquark.miscellaneous.item;

import com.mojang.serialization.Lifecycle;
import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.potion.BrewingRecipeHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import java.util.Optional;
import java.util.OptionalInt;

public class StackableLingeringPotionItem extends SplashPotionItem {
    public final Identifier id = new Identifier("minecraft", "lingering_potion");

    public StackableLingeringPotionItem() {
        super(new Settings().group(ItemGroup.BREWING).maxCount(16));
    }

    public void register() {
        try {
            if (!BrewingRecipeHelper.registerPotionType(this))
                return;
            if (!BrewingRecipeHelper.registerItemRecipe(me.uquark.miscellaneous.item.Items.STACKABLE_SPLASH_POTION_ITEM, Items.DRAGON_BREATH, me.uquark.miscellaneous.item.Items.STACKABLE_LINGERING_POTION_ITEM))
                return;
            Optional<RegistryKey<Item>> opt = Registry.ITEM.getKey(Items.LINGERING_POTION);
            if (opt.isEmpty())
                return;
            RegistryKey<Item> key = opt.get();

            int oldRawId = Registry.ITEM.getRawId(Items.LINGERING_POTION);

            Items.LINGERING_POTION = this;
            Registry.ITEM.keyToEntry.remove(key);
            Registry.ITEM.replace(OptionalInt.of(oldRawId), key, this, Lifecycle.stable());
        } catch (Exception e) {
            Miscellaneous.LOGGER.error("Failed to replace default potion item");
            e.printStackTrace();
        }
    }
}

package me.uquark.miscellaneous.item;

import com.mojang.serialization.Lifecycle;
import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.potion.BrewingRecipeHelper;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleDefaultedRegistry;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.OptionalInt;

public class StackableLingeringPotionItem extends SplashPotionItem {
    public final Identifier id = new Identifier("minecraft", "lingering_potion");

    public StackableLingeringPotionItem() {
        super(new Settings().maxCount(16));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.add(this));
    }

    public void register() {
        try {
            if (!BrewingRecipeHelper.registerPotionType(this))
                return;

            if (!(Registries.ITEM instanceof SimpleDefaultedRegistry<Item> r)) {
                Miscellaneous.LOGGER.error("Not a simple registry");
                return;
            }

            Optional<RegistryKey<Item>> opt = r.getKey(Items.LINGERING_POTION);
            if (opt.isEmpty())
                return;
            RegistryKey<Item> key = opt.get();

            int oldRawId = r.getRawId(Items.LINGERING_POTION);

            Items.LINGERING_POTION = this;

            r.keyToEntry.remove(key);
            r.set(oldRawId, key, this, Lifecycle.stable());
        } catch (Exception e) {
            Miscellaneous.LOGGER.error("Failed to replace default potion item");
            e.printStackTrace();
        }
    }
}

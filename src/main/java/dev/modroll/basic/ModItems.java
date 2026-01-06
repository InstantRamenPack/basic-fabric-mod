package dev.modroll.basic;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {

    public static Item register(
            String name,
            Function<Item.Settings, Item> itemFactory,
            Item.Settings settings
    ) {
        // Create a unique registry key for this item.
        RegistryKey<Item> itemKey = RegistryKey.of(
                RegistryKeys.ITEM,
                Identifier.of(Basic.MOD_ID, name)
        );

        // Create the item instance using the provided factory and settings.
        Item item = itemFactory.apply(settings.registryKey(itemKey));

        // Register the item with Minecraft's item registry.
        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    public static void initialize() {

    }

    public static final Item TASER = register(
            "taser",
            Item::new,
            new Item.Settings()
    );
}

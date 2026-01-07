package dev.modroll.basic.item;

import dev.modroll.basic.Basic;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
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
        RegistryKey<Item> itemKey = RegistryKey.of(
                RegistryKeys.ITEM,
                Identifier.of(Basic.MOD_ID, name)
        );
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    public static void registerModItems() {
        // Called during mod initialization to register our items.

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((itemGroup) -> {
                itemGroup.add(ModItems.TASER);
        });
    }

    public static final Item TASER = register(
            "taser",
            Item::new,
            new Item.Settings()
    );
}

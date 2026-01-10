package dev.modroll.basic.item;

import dev.modroll.basic.Basic;
import dev.modroll.basic.effect.ShockEffect;
import dev.modroll.basic.entity.ModEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.loot.slot.ItemStream;
import net.minecraft.particle.SimpleParticleType;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems implements ModInitializer{


    @Override
    public void onInitialize() {
        // ...
    }


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
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) -> {
            itemGroup.add(ModItems.SQUIRREL_HIDE);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register((itemGroup) -> {
            itemGroup.add(ModItems.SQUIRREL_SPAWN_EGG);
        });
    }


        public static final StatusEffect SHOCK =
            Registry.register(Registries.STATUS_EFFECT, Identifier.of("basic", "shock"), new ShockEffect());

    private static Item register(String id, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of("mymod", id), item);
    }

    public static final Item TASER = register(
            "taser",
            TaserItem::new,
            new TaserItem.Settings()
    );

    public static final Item SQUIRREL_HIDE = register(
            "squirrel_hide",
            Item::new,
            new Item.Settings()
    );

    public static final Item SQUIRREL_SPAWN_EGG = register(
            "squirrel_spawn_egg",
            SpawnEggItem::new,
            new Item.Settings().spawnEgg(ModEntities.SQUIRREL)
    );


}

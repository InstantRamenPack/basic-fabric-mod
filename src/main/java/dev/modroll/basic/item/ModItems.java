package dev.modroll.basic.item;

import dev.modroll.basic.Basic;
import dev.modroll.basic.effect.ShockEffect;
import dev.modroll.basic.effect.OccultistEffect;
import dev.modroll.basic.entity.ModEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
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
        public static final StatusEffect OCCULTIST =
            Registry.register(Registries.STATUS_EFFECT, Identifier.of("basic", "occultist"), new OccultistEffect());



    public static final Item TASER = register(
            "taser",
            TaserItem::new,
            new TaserItem.Settings().maxCount(1).maxDamage(250)
    );
    public static final Item FLESH_BATON = register(
            "flesh_baton",
            FleshBaton::new,
            new FleshBaton.Settings()
                    .maxCount(1)
                    .maxDamage(250)
                    .sword(ToolMaterial.WOOD,2.0f,0.5f)
    );

    public static final Item ORNATE_HAT = register(
            "ornate_hat",
            OrnateHat::new,
            new OrnateHat.Settings()
                    .maxCount(1)
                    .equippable(EquipmentSlot.byName("head"))
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

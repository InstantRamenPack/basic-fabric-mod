package dev.modroll.basic.entity;

import dev.modroll.basic.Basic;
import dev.modroll.basic.entity.custom.SquirrelEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<SquirrelEntity> SQUIRREL = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(Basic.MOD_ID, "squirrel"),
            EntityType.Builder.create(SquirrelEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.5f, 0.875f)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(Basic.MOD_ID, "squirrel"))));

    public static void registerModEntities() {
        Basic.LOGGER.info("Registering Mod Entities for " + Basic.MOD_ID);
    }
}

package dev.modroll.squirrel.world.gen;

import dev.modroll.squirrel.entity.ModEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntitySpawns {
    public static void addSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA),
                SpawnGroup.CREATURE, ModEntities.SQUIRREL, 30, 2, 8);

        SpawnRestriction.register(ModEntities.SQUIRREL, SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING, AnimalEntity::isValidNaturalSpawn);
    }
}

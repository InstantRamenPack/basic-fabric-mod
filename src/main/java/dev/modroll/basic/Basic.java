package dev.modroll.basic;

import dev.modroll.basic.entity.ModEntities;
import dev.modroll.basic.entity.custom.SquirrelEntity;
import dev.modroll.basic.item.ModItems;
import dev.modroll.basic.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Basic implements ModInitializer {
	public static final String MOD_ID = "basic";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final SimpleParticleType SQUIRREL_ZAP_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType SHOCK_PARTICLE = FabricParticleTypes.simple();

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModEntities.registerModEntities();
		ModWorldGeneration.generateModWorldGen();

		FabricDefaultAttributeRegistry.register(ModEntities.SQUIRREL, SquirrelEntity.createAttributes());

        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Basic.MOD_ID, "squirrel_zap_particle"), SQUIRREL_ZAP_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Basic.MOD_ID, "shock_particle"), SHOCK_PARTICLE);

    }


}

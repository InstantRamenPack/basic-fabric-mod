package dev.modroll.basic;

import dev.modroll.basic.entity.ModEntities;
import dev.modroll.basic.entity.custom.SquirrelEntity;
import dev.modroll.basic.item.ModItems;
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
    // This DefaultParticleType gets called when you want to use your particle in code.
    public static final SimpleParticleType SQUIRREL_ZAP_PARTICLE = FabricParticleTypes.simple();



	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModEntities.registerModEntities();
		FabricDefaultAttributeRegistry.register(ModEntities.SQUIRREL, SquirrelEntity.createAttributes());


        // Register our custom particle type in the mod initializer.
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Basic.MOD_ID, "squirrel_zap_particle"), SQUIRREL_ZAP_PARTICLE);


    }


}

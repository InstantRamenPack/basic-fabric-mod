package dev.modroll.basic;

import dev.modroll.basic.entity.ModEntities;
import dev.modroll.basic.entity.custom.SquirrelEntity;
import dev.modroll.basic.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Basic implements ModInitializer {
	public static final String MOD_ID = "basic";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModEntities.registerModEntities();
		FabricDefaultAttributeRegistry.register(ModEntities.SQUIRREL, SquirrelEntity.createAttributes());
	}
}

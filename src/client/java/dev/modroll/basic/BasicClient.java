package dev.modroll.basic;

import dev.modroll.basic.entity.ModEntities;
import dev.modroll.basic.entity.client.SquirrelModel;
import dev.modroll.basic.entity.client.SquirrelRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.EntityRendererFactories;

public class BasicClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererFactories.register(ModEntities.SQUIRREL, SquirrelRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SquirrelModel.SQUIRREL, SquirrelModel::getTexturedModelData);
    }
}

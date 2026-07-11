package dev.modroll.squirrel;

import dev.modroll.squirrel.entity.ModEntities;
import dev.modroll.squirrel.entity.client.SquirrelModel;
import dev.modroll.squirrel.entity.client.SquirrelRenderer;
import dev.modroll.squirrel.entity.client.particle.ShockParticle;
import dev.modroll.squirrel.entity.client.particle.SquirrelZapParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.EntityRendererFactories;

public class SquirrelClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererFactories.register(ModEntities.SQUIRREL, SquirrelRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SquirrelModel.SQUIRREL, SquirrelModel::getTexturedModelData);

        ParticleFactoryRegistry.getInstance().register(Squirrel.SQUIRREL_ZAP_PARTICLE, SquirrelZapParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Squirrel.SHOCK_PARTICLE, ShockParticle.Factory::new);
    }
}

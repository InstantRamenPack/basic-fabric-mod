package dev.modroll.basic;

import dev.modroll.basic.entity.ModEntities;
import dev.modroll.basic.entity.client.SquirrelModel;
import dev.modroll.basic.entity.client.SquirrelRenderer;
import dev.modroll.basic.entity.client.particle.ShockParticle;
import dev.modroll.basic.entity.client.particle.SquirrelZapParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.EntityRendererFactories;

public class BasicClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererFactories.register(ModEntities.SQUIRREL, SquirrelRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SquirrelModel.SQUIRREL, SquirrelModel::getTexturedModelData);

        ParticleFactoryRegistry.getInstance().register(Basic.SQUIRREL_ZAP_PARTICLE, SquirrelZapParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Basic.SHOCK_PARTICLE, ShockParticle.Factory::new);
    }
}

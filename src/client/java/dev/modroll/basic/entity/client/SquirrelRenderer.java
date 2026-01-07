package dev.modroll.basic.entity.client;

import dev.modroll.basic.Basic;
import dev.modroll.basic.entity.custom.SquirrelEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;

public class SquirrelRenderer extends MobEntityRenderer<SquirrelEntity, LivingEntityRenderState, SquirrelModel> {
    private static final Identifier TEXTURE =
            Identifier.of(Basic.MOD_ID, "textures/entity/squirrel.png");

    public SquirrelRenderer(EntityRendererFactory.Context context) {
        super(context, new SquirrelModel(context.getPart(SquirrelModel.SQUIRREL)), 0.3f);
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}

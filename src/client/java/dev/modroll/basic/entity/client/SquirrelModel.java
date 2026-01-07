package dev.modroll.basic.entity.client;

import dev.modroll.basic.Basic;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;

public class SquirrelModel extends EntityModel<LivingEntityRenderState> {
    public static final EntityModelLayer SQUIRREL =
            new EntityModelLayer(Identifier.of(Basic.MOD_ID, "squirrel"), "main");

    public SquirrelModel(ModelPart root) {
        // TODO: import from Blockbench
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();
        // TODO: import real model from Blockbench, currently just a cube
        root.addChild(
                "cube",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-8.0F, -24.0F, -8.0F, 16.0F, 16.0F, 16.0F),
                ModelTransform.origin(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void setAngles(LivingEntityRenderState state) {

    }
}

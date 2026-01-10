package dev.modroll.basic.entity.client;

import dev.modroll.basic.Basic;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class SquirrelModel extends EntityModel<SquirrelRenderState> {
    private final ModelPart legs;
    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart armright;
    private final ModelPart armleft;
    private final ModelPart bb_main;
    private final ModelPart body;

    private final Animation walkingAnimation;
    private final Animation idlingAnimation;

    public static final EntityModelLayer SQUIRREL = new EntityModelLayer(Identifier.of(Basic.MOD_ID, "squirrel"), "main");


    public SquirrelModel(ModelPart root) {
        super(root);
        this.legs = root.getChild("legs");
        this.head = root.getChild("head");
        this.tail = root.getChild("tail");
        this.armright = root.getChild("armright");
        this.armleft = root.getChild("armleft");
        this.bb_main = root.getChild("bb_main");
        this.body = root.getChild("body");

        this.walkingAnimation = SquirrelAnimations.WALKING.createAnimation(root);
        this.idlingAnimation = SquirrelAnimations.IDLING.createAnimation(root);
    }
    
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData legs = modelPartData.addChild("legs", ModelPartBuilder.create().uv(0, 22).cuboid(2.0F, -1.0F, -3.0F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F))
                .uv(14, 22).cuboid(-4.0F, -1.0F, -3.0F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 23.0F, 0.0F));

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -3.0F, -5.0F, 6.0F, 5.0F, 6.0F, new Dilation(0.0F))
                .uv(24, 7).cuboid(1.0F, -5.0F, -2.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(24, 29).cuboid(-3.0F, -5.0F, -2.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 13.0F, 1.3F));

        ModelPartData tail = modelPartData.addChild("tail", ModelPartBuilder.create().uv(12, 29).cuboid(-1.0F, -0.5F, -2.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 29).cuboid(-1.0F, -2.5F, 0.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F))
                .uv(18, 11).cuboid(-2.0F, -8.5F, 1.0F, 4.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 20.5F, 3.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 48.0F, 0.0F)); // <-- put body in the normal entity space

        body.addChild("body_r1", ModelPartBuilder.create().uv(0, 11)
                        .cuboid(-2.5F, -7.0F, 3.0F, 5.0F, 7.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -25.0F, -4.0F, 0.1745F, 0.0F, 0.0F));
        ModelPartData armright = modelPartData.addChild("armright", ModelPartBuilder.create().uv(28, 22).cuboid(-1.0F, -1.0F, -4.0F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, 18.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        ModelPartData armleft = modelPartData.addChild("armleft", ModelPartBuilder.create().uv(24, 0).cuboid(-1.0F, -1.0F, -4.0F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 18.0F, 0.0F, 0.3491F, 0.0F, 0.0F));


        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(SquirrelRenderState state) {
        super.setAngles(state);
        this.head.pitch = state.pitch * 0.017453292F;
        this.head.yaw = state.relativeHeadYaw * 0.017453292F;
        this.walkingAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 1.5F, 4.0F);
        this.idlingAnimation.apply(state.idleAnimationState, state.age);
    }
}

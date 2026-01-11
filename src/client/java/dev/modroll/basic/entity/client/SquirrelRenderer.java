package dev.modroll.basic.entity.client;

import dev.modroll.basic.Basic;
import dev.modroll.basic.entity.custom.SquirrelEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class SquirrelRenderer extends MobEntityRenderer<SquirrelEntity, SquirrelRenderState, SquirrelModel> {

    public SquirrelRenderer(EntityRendererFactory.Context context) {
        super(context, new SquirrelModel(context.getPart(SquirrelModel.SQUIRREL)), 0.5f);
    }

    @Override
    public Identifier getTexture(SquirrelRenderState state) {
        return Identifier.of(Basic.MOD_ID, "textures/entity/squirrel/squirrel.png");
    }

    @Override
    public SquirrelRenderState createRenderState() {
        return new SquirrelRenderState();
    }

    @Override
    public void updateRenderState(SquirrelEntity entity, SquirrelRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.climbingAnimationState.copyFrom(entity.climbingAnimationState);
        state.climbing = entity.isClimbing();
        state.climbYaw = state.climbing ? getClimbYaw(entity) : 0.0F;
    }

    private static float getClimbYaw(SquirrelEntity entity) {
        Direction facing = entity.getClimbDirection();
        if (facing == null) {
            facing = entity.getHorizontalFacing();
        }
        return facing.getPositiveHorizontalDegrees() * 0.017453292F;
    }
}

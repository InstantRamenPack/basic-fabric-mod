package dev.modroll.basic.entity.client;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

public class SquirrelRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState climbingAnimationState = new AnimationState();
    public boolean climbing;
    public float climbYaw;
}

package dev.modroll.basic.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;


public class OccultistEffect extends StatusEffect {
    public OccultistEffect() {
        super(StatusEffectCategory.HARMFUL, 0x052063);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true; // run every tick
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {

        if (entity.age % 40 == 0) {
            entity.damage(world,entity.getDamageSources().magic(),0.0f);
        }
        return super.applyUpdateEffect(world,entity, amplifier);
    }
}

package dev.modroll.squirrel.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;


public class OccultistEffect extends StatusEffect {
    public OccultistEffect() {
        super(StatusEffectCategory.HARMFUL, 0x052063);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true; // run every tick
    }
}

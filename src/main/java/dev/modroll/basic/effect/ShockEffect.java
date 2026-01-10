package dev.modroll.basic.effect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;




public class ShockEffect extends StatusEffect {
    public ShockEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xE9B8B3);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true; // run every tick
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {

        // Keep a constant Slowness IV while Shock is active
        // (duration slightly > 20 so it never "drops" between refreshes)
        entity.addStatusEffect(new StatusEffectInstance(
                StatusEffects.SLOWNESS,
                25,    // ticks
                3,     // amplifier 3 = Slowness IV = ~60% slow
                false,  // ambient (subtle)
                false, // show particles
                false   // show icon
        ));

        // Deal 1.0F damage (half-heart) once per second
        if (entity.age % 20 == 0) {
            entity.damage(world,entity.getDamageSources().magic(),1.0f);

            if (entity.getEntityWorld() instanceof ServerWorld) {
                // random.nextInt(N) == 0 => 1/N chance
                if (world.random.nextInt(16) == 0) {
                    LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world, SpawnReason.COMMAND);
                    if (lightning != null) {
                        lightning.refreshPositionAfterTeleport(entity.getX(), entity.getY(), entity.getZ());
                        // Optional: make it a "visual" strike only (no fire/damage):
                         lightning.setCosmetic(true);
                        entity.damage(world,world.getDamageSources().magic(),8.0f);
                        world.spawnEntity(lightning);
                    }
                }
            }



        }
        return super.applyUpdateEffect(world,entity, amplifier);
    }
}

package dev.modroll.basic.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;

public class FleshBaton extends Item {
    public FleshBaton(Settings settings) {
        super(settings);
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addStatusEffect(new StatusEffectInstance(RegistryEntry.of(ModItems.OCCULTIST), 200, 4));
        stack.damage(1, attacker, (attacker.getActiveHand()));
        super.postHit(stack, target, attacker);

    }
}

package dev.modroll.squirrel.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;

public class TaserItem extends Item {
    public TaserItem(Settings settings) {
        super(settings);
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addStatusEffect(new StatusEffectInstance(RegistryEntry.of(ModItems.SHOCK), 80, 4));
        stack.damage(1, attacker, (attacker.getActiveHand()));
        super.postHit(stack, target, attacker);
    }
}

package dev.modroll.basic.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;

public class OrnateHat extends Item {
    public OrnateHat(Settings settings) {
        super(settings);

    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @org.jspecify.annotations.Nullable EquipmentSlot slot) {
        if (slot == EquipmentSlot.HEAD) {
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(RegistryEntry.of(ModItems.OCCULTIST), 95, 0));
            }
        }
        super.inventoryTick(stack, world, entity, slot);
    }
}


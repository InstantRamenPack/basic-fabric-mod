package dev.modroll.basic.entity.custom;

import dev.modroll.basic.entity.ModEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SpiderNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

public class SquirrelEntity extends AnimalEntity implements RangedAttackMob {
    private static final TrackedData<Byte> SQUIRREL_FLAGS = DataTracker.registerData(SquirrelEntity.class, TrackedDataHandlerRegistry.BYTE);

    public SquirrelEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(1, new PowderSnowJumpGoal(this, this.getEntityWorld()));
        this.goalSelector.add(2, new ProjectileAttackGoal(this, 1.0, 20, 16.0F));
        this.goalSelector.add(3, new EscapeDangerGoal(this, 2.2));
        this.goalSelector.add(4, new FleeEntityGoal<>(this, WolfEntity.class, 10.0F, 2.2, 2.2));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.6));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
        this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, WolfEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.MAX_HEALTH, 8.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.5F)
                .add(EntityAttributes.ATTACK_DAMAGE, 0.5F);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SpiderNavigation(this, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SQUIRREL_FLAGS, (byte)0);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getEntityWorld().isClient()) {
            this.setClimbingWall(this.horizontalCollision);
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isClimbing() {
        return this.isClimbingWall();
    }

    public boolean isClimbingWall() {
        return (this.dataTracker.get(SQUIRREL_FLAGS) & 1) != 0;
    }

    public void setClimbingWall(boolean climbing) {
        byte b = this.dataTracker.get(SQUIRREL_FLAGS);
        if (climbing) {
            b = (byte)(b | 1);
        } else {
            b = (byte)(b & -2);
        }

        this.dataTracker.set(SQUIRREL_FLAGS, b);
    }

    @Override
    @Nullable
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.SQUIRREL.create(world, SpawnReason.BREEDING);
    }

    @Override
    public void fall(double heightDifference, boolean onGround, net.minecraft.block.BlockState state, net.minecraft.util.math.BlockPos pos) {
        // do nothing
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        // copied from SonicBoomTask.keepRunning
        ServerWorld serverWorld = (ServerWorld) this.getEntityWorld();
        Vec3d vec3d = this.getEyePos();
        Vec3d vec3d2 = target.getEyePos().subtract(vec3d);
        Vec3d vec3d3 = vec3d2.normalize();

        if (vec3d2.length() < 12) {
            this.playSound(SoundEvents.ENTITY_WARDEN_SONIC_BOOM, 3.0F, 1.0F);
            if (target.damage(serverWorld, serverWorld.getDamageSources().sonicBoom(this), (float) this.getAttributeValue(EntityAttributes.ATTACK_DAMAGE))) {
                int i = MathHelper.floor(vec3d2.length()) + 7;
                for (int j = 1; j < i; j++) {
                    Vec3d vec3d4 = vec3d.add(vec3d3.multiply(j));
                    serverWorld.spawnParticles(ParticleTypes.SONIC_BOOM, vec3d4.x, vec3d4.y, vec3d4.z, 1, 0.0, 0.0, 0.0, 0.0);
                }
                double d = 0.3 * (1.0 - target.getAttributeValue(EntityAttributes.KNOCKBACK_RESISTANCE));
                double e = 1.5 * (1.0 - target.getAttributeValue(EntityAttributes.KNOCKBACK_RESISTANCE));
                target.addVelocity(vec3d3.getX() * e, vec3d3.getY() * d, vec3d3.getZ() * e);
            }
        }
    }
}

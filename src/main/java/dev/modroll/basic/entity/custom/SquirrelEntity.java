package dev.modroll.basic.entity.custom;

import dev.modroll.basic.Basic;
import dev.modroll.basic.entity.ModEntities;
import net.minecraft.block.*;
import dev.modroll.basic.item.ModItems;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
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
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.rule.GameRules;
import org.jspecify.annotations.Nullable;

public class SquirrelEntity extends AnimalEntity implements RangedAttackMob {

    private static final float SONIC_RANGE = 12.0F;

    private static final TrackedData<Byte> SQUIRREL_FLAGS = DataTracker.registerData(SquirrelEntity.class, TrackedDataHandlerRegistry.BYTE);
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    int moreBerryTicks = 0;

    public SquirrelEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new PowderSnowJumpGoal(this, this.getEntityWorld()));
        this.goalSelector.add(2, new ProjectileAttackGoal(this, 1.0, 20, SONIC_RANGE));
        this.goalSelector.add(3, new EscapeDangerGoal(this, 2.2));
        this.goalSelector.add(4, new FleeEntityGoal<>(this, WolfEntity.class, 10.0F, 2.2, 2.2));
        this.goalSelector.add(5, new EatBerriesGoal(this, 1.0F, 24));
        this.goalSelector.add(9, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
        this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, WolfEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(
                this,
                LivingEntity.class,
                true,
                (entity, world) -> entity.hasStatusEffect(RegistryEntry.of(ModItems.OCCULTIST)) && entity != this
        ));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.MAX_HEALTH, 12.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.3F)
                .add(EntityAttributes.ATTACK_DAMAGE, 4.0F);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_OTHER, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_CAUTIOUS, 0.0F);
        return new SquirrelNavigation(this, world);
    }

    @Override
    public int getSafeFallDistance() {
        return 256;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SQUIRREL_FLAGS, (byte)0);
    }

    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.putInt("MoreBerryTicks", this.moreBerryTicks);
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        this.moreBerryTicks = view.getInt("MoreBerryTicks", 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getEntityWorld().isClient()) {
            this.setupAnimationStates();
        } else {
            if (this.moreBerryTicks > 0) {
                this.moreBerryTicks = this.moreBerryTicks - this.random.nextInt(3);
                if (this.moreBerryTicks < 0) {
                    this.moreBerryTicks = 0;
                }
            }
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        this.setClimbingWall(this.horizontalCollision);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 20;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
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

        if (this.isOnGround() && vec3d2.length() < SONIC_RANGE) {
            this.setVelocity(this.getVelocity().x,1.0f,this.getVelocity().z);
        }
        else if (!this.isOnGround() && vec3d2.length() < SONIC_RANGE + 6 && getVelocity().y < 0.1f) {

            if (target.damage(serverWorld, serverWorld.getDamageSources().sonicBoom(this), (float) this.getAttributeValue(EntityAttributes.ATTACK_DAMAGE))) {
                int i = MathHelper.floor(vec3d2.length()) + 7;
                this.playSound(SoundEvents.ENTITY_WARDEN_SONIC_BOOM, 3.0F, 1.0F);
                for (int j = 0; j < i * 2; j++) {
                    double t = j * 0.5; // half-block spacing
                    Vec3d vec3d4 = vec3d.add(vec3d3.multiply(t));

                    serverWorld.spawnParticles(
                            Basic.SQUIRREL_ZAP_PARTICLE,
                            vec3d4.x, vec3d4.y, vec3d4.z, 1, 0.0 + 0.01 * (j % 2), 0.0, 0.0, 0.0 + 0.01 * (j % 2)
                    );
                }

                double d = 0.3 * (1.0 - target.getAttributeValue(EntityAttributes.KNOCKBACK_RESISTANCE));
                double e = 1.5 * (1.0 - target.getAttributeValue(EntityAttributes.KNOCKBACK_RESISTANCE));
                target.addVelocity(vec3d3.getX() * e, vec3d3.getY() * d, vec3d3.getZ() * e);
            }
        }
    }

    static class EatBerriesGoal extends MoveToTargetPosGoal {
        private final SquirrelEntity squirrel;
        private boolean wantsBerries;
        private boolean hasTarget;

        public EatBerriesGoal(SquirrelEntity squirrel, double speed, int range) {
            super(squirrel, speed, range);
            this.squirrel = squirrel;
        }

        @Override
        public boolean canStart() {
            if (this.cooldown <= 0) {
                if (!getServerWorld(this.squirrel).getGameRules().getValue(GameRules.DO_MOB_GRIEFING)) {
                    return false;
                }

                this.hasTarget = false;
                this.wantsBerries = this.squirrel.wantsBerries();
            }

            return super.canStart();
        }

        @Override
        public boolean shouldContinue() {
            return this.hasTarget && super.shouldContinue();
        }

        @Override
        public void tick() {
            super.tick();
            this.squirrel.getLookControl().lookAt(
                    this.targetPos.getX() + 0.5,
                    this.targetPos.getY() + 1,
                    this.targetPos.getZ() + 0.5,
                    10.0F,
                    this.squirrel.getMaxLookPitchChange()
            );
            if (hasReached() || this.isStoppedNearTarget()) {
                World world = this.squirrel.getEntityWorld();
                if (!world.isClient() && this.hasTarget && castToServerWorld(world).getGameRules().getValue(GameRules.DO_MOB_GRIEFING)) {
                    BlockPos berryPos = this.targetPos.up();
                    BlockState berryState = world.getBlockState(berryPos);
                    if (berryState.isOf(Blocks.SWEET_BERRY_BUSH)) {
                        this.pickSweetBerries(world, berryPos, berryState);
                        this.squirrel.moreBerryTicks = 40;
                    } else {
                        berryPos = this.targetPos;
                        berryState = world.getBlockState(berryPos);
                        if (CaveVines.hasBerries(berryState)) {
                            this.pickGlowBerries(world, this.targetPos, berryState);
                            this.squirrel.moreBerryTicks = 40;
                        }
                    }
                }

                this.hasTarget = false;
                this.cooldown = 10;
            }
        }

        private boolean isStoppedNearTarget() {
            return this.squirrel.getNavigation().isIdle()
                    && this.squirrel.squaredDistanceTo(Vec3d.ofCenter(this.targetPos)) <= 4;
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            if (!this.wantsBerries || this.hasTarget) {
                return false;
            }

            BlockState aboveState = world.getBlockState(pos.up());
            if (aboveState.isOf(Blocks.SWEET_BERRY_BUSH)) {
                if (aboveState.get(SweetBerryBushBlock.AGE) >= 2) {
                    this.hasTarget = true;
                    return true;
                }
                return false;
            }

            BlockState blockState = world.getBlockState(pos);
            if (CaveVines.hasBerries(blockState)) {
                this.hasTarget = true;
                return true;
            }

            return false;
        }

        private void pickGlowBerries(World world, BlockPos pos, BlockState state) {
            CaveVines.pickBerries(this.squirrel, state, world, pos);
        }

        private void pickSweetBerries(World world, BlockPos pos, BlockState state) {
            world.setBlockState(pos, state.with(SweetBerryBushBlock.AGE, 1), Block.NOTIFY_LISTENERS);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(this.squirrel));
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    boolean wantsBerries() {
        return this.moreBerryTicks <= 0;
    }

    private static class SquirrelNavigation extends SpiderNavigation {
        @Nullable
        private BlockPos targetPos;

        SquirrelNavigation(SquirrelEntity entity, World world) {
            super(entity, world);
        }

        @Override
        public void tick() {
            if (!this.isIdle()) {
                super.tick();
                return;
            }

            if (this.targetPos != null) {
                float reachDistance = 0.75F;
                boolean reachedTarget = this.targetPos.isWithinDistance(this.entity.getEntityPos(), reachDistance);
                if (!reachedTarget && this.entity.getY() > this.targetPos.getY()) {
                    BlockPos flatTarget = BlockPos.ofFloored(this.targetPos.getX(), this.entity.getY(), this.targetPos.getZ());
                    reachedTarget = flatTarget.isWithinDistance(this.entity.getEntityPos(), reachDistance);
                }

                if (!reachedTarget) {
                    this.entity.getMoveControl().moveTo(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ(), this.speed);
                } else {
                    this.targetPos = null;
                }
            }
        }
    }
}

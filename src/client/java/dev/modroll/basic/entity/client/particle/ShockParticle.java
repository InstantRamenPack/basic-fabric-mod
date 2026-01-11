//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.modroll.basic.entity.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.BillboardParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;

@Environment(EnvType.CLIENT)
public class ShockParticle extends BillboardParticle {
    private final SpriteProvider spriteProvider;

    private static double originX;
    private static double originY;
    private static double originZ;
    private static float start;
    protected ShockParticle(ClientWorld world, double x, double y, double z, double velocityX, SpriteProvider spriteProvider) {

        //this.zRotation = (float) (Random.create().nextFloat() * 2 * Math.PI);
        //this.lastZRotation = zRotation;
        super(world, x, y, z, (double)0.0F, (double)0.0F, (double)0.0F, spriteProvider.getFirst());
        start = this.random.nextFloat() * ((float)Math.PI * 2F);
        this.zRotation = start;
        this.lastZRotation = start;
        this.maxAge = 8;
        float f = 1.0F;
        this.red = f;
        this.green = f;
        this.blue = f;
        this.scale = 1.0F + this.random.nextFloat()*0.25f;
        this.spriteProvider = spriteProvider;
        this.updateSprite(spriteProvider);

    }

    public int getBrightness(float tint) {
        return 15728880;
    }

    public void tick() {
        //this.zRotation = start;
        //this.lastZRotation = start;
        if (age < 1) {
            this.zRotation = Random.create().nextFloat() * 3.1415f;
        }
        this.lastX = this.x;
        this.lastY = this.y;
        this.lastZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.updateSprite(this.spriteProvider);
        }
    }

    public BillboardParticle.RenderType getRenderType() {
        return RenderType.PARTICLE_ATLAS_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, Random random) {
            return new ShockParticle(clientWorld, d, e, f, g, this.spriteProvider);
        }
    }
}

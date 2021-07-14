package love.marblegate.flowingagony.fx.particle.cursedantipathyparticle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CursedAntipathyParticle extends SpriteTexturedParticle {

    protected CursedAntipathyParticle(ClientWorld world, double x, double y, double z, float diameter) {
        super(world, x, y, z);
        maxAge = 100;
        motionX = (Math.random() * 2.0D - 1.0D) * (double)0.1F;
        motionY = 0F;
        motionZ = (Math.random() * 2.0D - 1.0D) * (double)0.1F;
        final float PARTICLE_SCALE_FOR_ONE_METRE = 0.1F;
        particleScale = PARTICLE_SCALE_FOR_ONE_METRE * diameter;
        canCollide = true;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<CursedAntipathyParticleData> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle makeParticle(CursedAntipathyParticleData typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            CursedAntipathyParticle cursedHatredParticle = new CursedAntipathyParticle(worldIn, x, y, z, typeIn.getDiameter());
            cursedHatredParticle.selectSpriteRandomly(spriteSet);
            return cursedHatredParticle;
        }
    }
}

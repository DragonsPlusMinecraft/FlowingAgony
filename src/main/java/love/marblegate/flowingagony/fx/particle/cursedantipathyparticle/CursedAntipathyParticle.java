package love.marblegate.flowingagony.fx.particle.cursedantipathyparticle;

import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CursedAntipathyParticle extends TextureSheetParticle {

    protected CursedAntipathyParticle(ClientLevel world, double x, double y, double z, float diameter) {
        super(world, x, y, z);
        lifetime = 100;
        xd = (Math.random() * 2.0D - 1.0D) * (double) 0.1F;
        yd = 0F;
        zd = (Math.random() * 2.0D - 1.0D) * (double) 0.1F;
        final float PARTICLE_SCALE_FOR_ONE_METRE = 0.1F;
        quadSize = PARTICLE_SCALE_FOR_ONE_METRE * diameter;
        hasPhysics = true;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<CursedAntipathyParticleData> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(CursedAntipathyParticleData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            CursedAntipathyParticle cursedHatredParticle = new CursedAntipathyParticle(worldIn, x, y, z, typeIn.getDiameter());
            cursedHatredParticle.pickSprite(spriteSet);
            return cursedHatredParticle;
        }
    }
}

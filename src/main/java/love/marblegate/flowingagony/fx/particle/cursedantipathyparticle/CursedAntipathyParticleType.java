package love.marblegate.flowingagony.fx.particle.cursedantipathyparticle;


import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;

public class CursedAntipathyParticleType extends ParticleType<CursedAntipathyParticleData> {
    public CursedAntipathyParticleType() {
        super(false, CursedAntipathyParticleData.DESERIALIZER);
    }

    @Override
    public Codec<CursedAntipathyParticleData> codec() {
        return Codec.unit(new CursedAntipathyParticleData(0));
    }
}

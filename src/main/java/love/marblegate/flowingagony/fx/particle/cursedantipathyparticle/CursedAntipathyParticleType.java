package love.marblegate.flowingagony.fx.particle.cursedantipathyparticle;


import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;

public class CursedAntipathyParticleType extends ParticleType<CursedAntipathyParticleOption> {
    public CursedAntipathyParticleType() {
        super(false, CursedAntipathyParticleOption.DESERIALIZER);
    }

    @Override
    public Codec<CursedAntipathyParticleOption> codec() {
        return Codec.unit(new CursedAntipathyParticleOption(0));
    }
}

package love.marblegate.flowingagony.particle.cursedantipathyparticle;


import com.mojang.serialization.Codec;
import net.minecraft.particles.ParticleType;

public class CursedAntipathyParticleType extends ParticleType<CursedAntipathyParticleData> {
    public CursedAntipathyParticleType() {
        super(false, CursedAntipathyParticleData.DESERIALIZER);
    }

    @Override
    public Codec<CursedAntipathyParticleData> func_230522_e_() {
        return Codec.unit(new CursedAntipathyParticleData(0));
    }
}

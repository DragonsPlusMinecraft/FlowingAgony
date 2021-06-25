package love.marblegate.flowingagony.particle.cursedantipathyparticle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import love.marblegate.flowingagony.registry.ParticleRegistry;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

import java.util.Locale;

public class CursedAntipathyParticleData implements IParticleData {
    private final float diameter;

    public static final IDeserializer<CursedAntipathyParticleData> DESERIALIZER = new IDeserializer<CursedAntipathyParticleData>() {
        @Override
        public CursedAntipathyParticleData deserialize(ParticleType<CursedAntipathyParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            return new CursedAntipathyParticleData(reader.readFloat());
        }

        @Override
        public CursedAntipathyParticleData read(ParticleType<CursedAntipathyParticleData> particleTypeIn, PacketBuffer buffer) {
            return new CursedAntipathyParticleData(buffer.readFloat());
        }
    };

    public CursedAntipathyParticleData(float diameter) {
        this.diameter = diameter;
    }

    @Override
    public ParticleType<?> getType() {
        return ParticleRegistry.cursed_antipathy_particle.get();
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeFloat(this.diameter);
    }

    @Override
    public String getParameters() {
        return String.format(Locale.ROOT, "%s %.2f",
                this.getType().getRegistryName(), diameter);
    }

    public float getDiameter() {
        return diameter;
    }
}

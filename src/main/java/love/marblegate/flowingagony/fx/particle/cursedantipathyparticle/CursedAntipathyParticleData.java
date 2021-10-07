package love.marblegate.flowingagony.fx.particle.cursedantipathyparticle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import love.marblegate.flowingagony.fx.ParticleRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

import java.util.Locale;

public class CursedAntipathyParticleData implements ParticleOptions {
    private final float diameter;

    public static final Deserializer<CursedAntipathyParticleData> DESERIALIZER = new Deserializer<CursedAntipathyParticleData>() {
        @Override
        public CursedAntipathyParticleData fromCommand(ParticleType<CursedAntipathyParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            return new CursedAntipathyParticleData(reader.readFloat());
        }

        @Override
        public CursedAntipathyParticleData fromNetwork(ParticleType<CursedAntipathyParticleData> particleTypeIn, FriendlyByteBuf buffer) {
            return new CursedAntipathyParticleData(buffer.readFloat());
        }
    };

    public CursedAntipathyParticleData(float diameter) {
        this.diameter = diameter;
    }

    @Override
    public ParticleType<?> getType() {
        return ParticleRegistry.CURSED_ANTIPATHY_PARTICLE.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeFloat(diameter);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f",
                getType().getRegistryName(), diameter);
    }

    public float getDiameter() {
        return diameter;
    }
}

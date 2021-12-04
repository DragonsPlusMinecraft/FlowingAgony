package love.marblegate.flowingagony.fx.particle.cursedantipathyparticle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import love.marblegate.flowingagony.fx.ParticleRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Locale;

public class CursedAntipathyParticleOption implements ParticleOptions {
    private final float diameter;

    public static final Deserializer<CursedAntipathyParticleOption> DESERIALIZER = new Deserializer<CursedAntipathyParticleOption>() {
        @Override
        public CursedAntipathyParticleOption fromCommand(ParticleType<CursedAntipathyParticleOption> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            return new CursedAntipathyParticleOption(reader.readFloat());
        }

        @Override
        public CursedAntipathyParticleOption fromNetwork(ParticleType<CursedAntipathyParticleOption> particleTypeIn, FriendlyByteBuf buffer) {
            return new CursedAntipathyParticleOption(buffer.readFloat());
        }
    };

    public CursedAntipathyParticleOption(float diameter) {
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

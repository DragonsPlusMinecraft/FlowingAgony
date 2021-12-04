package love.marblegate.flowingagony.network;

import love.marblegate.flowingagony.network.packet.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;


public class Networking {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1.0";
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation("flowingagony", "mod_networking"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION)
        );
        INSTANCE.registerMessage(
                nextID(),
                PlaySoundPacket.class,
                PlaySoundPacket::toBytes,
                PlaySoundPacket::new,
                PlaySoundPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
        INSTANCE.registerMessage(
                nextID(),
                ParticleEffectPacket.class,
                ParticleEffectPacket::toBytes,
                ParticleEffectPacket::new,
                ParticleEffectPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
        INSTANCE.registerMessage(
                nextID(),
                PlaySoundWIthLocationPacket.class,
                PlaySoundWIthLocationPacket::toBytes,
                PlaySoundWIthLocationPacket::new,
                PlaySoundWIthLocationPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
        INSTANCE.registerMessage(
                nextID(),
                AbnormalJoySyncPacket.class,
                AbnormalJoySyncPacket::toBytes,
                AbnormalJoySyncPacket::new,
                AbnormalJoySyncPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
        INSTANCE.registerMessage(
                nextID(),
                RemoveEffectSyncToClientPacket.class,
                RemoveEffectSyncToClientPacket::toBytes,
                RemoveEffectSyncToClientPacket::new,
                RemoveEffectSyncToClientPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
    }
}

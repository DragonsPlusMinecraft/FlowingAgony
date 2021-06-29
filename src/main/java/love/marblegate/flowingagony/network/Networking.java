package love.marblegate.flowingagony.network;

import love.marblegate.flowingagony.network.packet.EffectPacket;
import love.marblegate.flowingagony.network.packet.PlaySoundPacket;
import love.marblegate.flowingagony.network.packet.PlaySoundWIthLocationPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;


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
                PlaySoundPacket::handle
        );
        INSTANCE.registerMessage(
                nextID(),
                EffectPacket.class,
                EffectPacket::toBytes,
                EffectPacket::new,
                EffectPacket::handle
        );
        INSTANCE.registerMessage(
                nextID(),
                PlaySoundWIthLocationPacket.class,
                PlaySoundWIthLocationPacket::toBytes,
                PlaySoundWIthLocationPacket::new,
                PlaySoundWIthLocationPacket::handle
        );
    }
}

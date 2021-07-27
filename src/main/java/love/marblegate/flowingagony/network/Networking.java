package love.marblegate.flowingagony.network;

import love.marblegate.flowingagony.network.packet.*;
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
                RemoveEffectSyncToClientPacket.class,
                RemoveEffectSyncToClientPacket::toBytes,
                RemoveEffectSyncToClientPacket::new,
                RemoveEffectSyncToClientPacket::handle
        );
    }
}

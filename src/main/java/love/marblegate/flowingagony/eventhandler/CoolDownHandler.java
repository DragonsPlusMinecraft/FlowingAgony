package love.marblegate.flowingagony.eventhandler;

import love.marblegate.flowingagony.capibility.cooldown.CoolDown;
import love.marblegate.flowingagony.capibility.cooldown.CoolDownType;
import love.marblegate.flowingagony.capibility.cooldown.ICoolDown;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class CoolDownHandler {

    @SubscribeEvent
    public static void handle(TickEvent.PlayerTickEvent event) {
        if (!event.player.world.isRemote()) {
            if (event.phase == TickEvent.Phase.START) {
                LazyOptional<ICoolDown> coolDownCap = event.player.getCapability(CoolDown.COOL_DOWN_CAPABILITY);
                coolDownCap.ifPresent(cap -> {
                    for (CoolDownType coolDownType : CoolDownType.values()) {
                        if (!cap.isReady(coolDownType)) {
                            cap.decrease(coolDownType);
                        }
                    }
                });
            }
        }
    }
}

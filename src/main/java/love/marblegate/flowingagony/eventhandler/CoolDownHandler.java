package love.marblegate.flowingagony.eventhandler;

import love.marblegate.flowingagony.capibility.ModCapManager;
import love.marblegate.flowingagony.capibility.CoolDown;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class CoolDownHandler {

    @SubscribeEvent
    public static void handle(TickEvent.PlayerTickEvent event) {
        if (!event.player.level.isClientSide()) {
            if (event.phase == TickEvent.Phase.START) {
                LazyOptional<CoolDown> coolDownCap = event.player.getCapability(ModCapManager.COOL_DOWN_CAPABILITY);
                coolDownCap.ifPresent(cap -> {
                    for (CoolDown.CoolDownType coolDownType : CoolDown.CoolDownType.values()) {
                        if (!cap.isReady(coolDownType)) {
                            cap.decrease(coolDownType);
                        }
                    }
                });
            }
        }
    }
}

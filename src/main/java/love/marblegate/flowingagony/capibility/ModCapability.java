package love.marblegate.flowingagony.capibility;

import love.marblegate.flowingagony.capibility.abnormaljoy.IAbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.hatredbloodlineenchantment.IHatredBloodlineStatusCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ModCapability {
    @CapabilityInject(IHatredBloodlineStatusCapability.class)
    public static Capability<IHatredBloodlineStatusCapability> HATRED_BLOODLINE_CAPABILITY;

    @CapabilityInject(IAbnormalJoyCapability.class)
    public static Capability<IAbnormalJoyCapability> ABNORMALJOY_CAPABILITY;
}
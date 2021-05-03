package love.marblegate.flowingagony.capibility;

import love.marblegate.flowingagony.capibility.abnormaljoy.IAbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.hatredbloodlineenchantment.IHatredBloodlikeEnchantmentCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ModCapability {
    @CapabilityInject(IHatredBloodlikeEnchantmentCapability.class)
    public static Capability<IHatredBloodlikeEnchantmentCapability> HATRED_BLOODLINE_CAPABILITY;

    @CapabilityInject(IAbnormalJoyCapability.class)
    public static Capability<IAbnormalJoyCapability> ABNORMALJOY_CAPABILITY;
}
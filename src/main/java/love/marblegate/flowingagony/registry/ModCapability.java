package love.marblegate.flowingagony.registry;

import love.marblegate.flowingagony.capibility.AbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.HatredBloodlineEnchantmentCapability;
import love.marblegate.flowingagony.capibility.IAbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.IHatredBloodlikeEnchantmentCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ModCapability {
    @CapabilityInject(HatredBloodlineEnchantmentCapability.class)
    public static Capability<IHatredBloodlikeEnchantmentCapability> HATRED_BLOODLINE_CAPABILITY;

    @CapabilityInject(AbnormalJoyCapability.class)
    public static Capability<IAbnormalJoyCapability> ABNORMALJOY_CAPABILITY;
}
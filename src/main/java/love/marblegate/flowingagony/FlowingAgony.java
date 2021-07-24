package love.marblegate.flowingagony;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.enchantment.CustomEnchantmentType;
import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.registry.ParticleRegistry;
import love.marblegate.flowingagony.registry.SoundRegistry;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("flowingagony")
public class FlowingAgony {


    public FlowingAgony(){
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.ACT_CONFIG);

        EnchantmentRegistry.ENCHANTMENT.register(FMLJavaModLoadingContext.get().getModEventBus());
        EffectRegistry.EFFECT.register(FMLJavaModLoadingContext.get().getModEventBus());
        SoundRegistry.SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ParticleRegistry.PARTICLE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

        CustomEnchantmentType.addToItemGroup();
    }
}

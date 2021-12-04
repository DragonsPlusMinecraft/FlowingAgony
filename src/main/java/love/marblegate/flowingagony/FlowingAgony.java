package love.marblegate.flowingagony;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.effect.EffectRegistry;
import love.marblegate.flowingagony.enchantment.CustomEnchantmentCategory;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import love.marblegate.flowingagony.fx.ParticleRegistry;
import love.marblegate.flowingagony.fx.SoundRegistry;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("flowingagony")
public class FlowingAgony {


    public FlowingAgony() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Configuration.ACT_CONFIG);

        EnchantmentRegistry.ENCHANTMENT.register(FMLJavaModLoadingContext.get().getModEventBus());
        EffectRegistry.EFFECT.register(FMLJavaModLoadingContext.get().getModEventBus());
        SoundRegistry.SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ParticleRegistry.PARTICLE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

        CustomEnchantmentCategory.addToItemGroup();
    }
}

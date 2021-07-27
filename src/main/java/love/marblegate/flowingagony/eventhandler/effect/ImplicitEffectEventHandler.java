package love.marblegate.flowingagony.eventhandler.effect;

import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber()
public class ImplicitEffectEventHandler {
    @SubscribeEvent
    public static void doHatredBloodlineEnchantmentActiveEffectEvent(LivingHurtEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                if(((PlayerEntity )event.getSource().getTrueSource()).isPotionActive(EffectRegistry.HATRED_BLOODLINE_ENCHANTMENT_ACTIVE.get())){
                    int effectLvl = ((PlayerEntity )event.getSource().getTrueSource()).getActivePotionEffect(EffectRegistry.HATRED_BLOODLINE_ENCHANTMENT_ACTIVE.get()).getAmplifier()+1;
                    event.setAmount((float)(event.getAmount()*(1+effectLvl*0.25)));
                }
            }
        }
    }
}

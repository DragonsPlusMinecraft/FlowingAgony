package love.marblegate.flowingagony.eventhandler.effect;

import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class ImplicitEffectEventHandler {
    @SubscribeEvent
    public static void doHatredBloodlineEnchantmentActiveEffectEvent(LivingHurtEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                if(((PlayerEntity )event.getSource().getTrueSource()).isPotionActive(EffectRegistry.hatred_bloodline_enchantment_active_effect.get())){
                    int effectLvl = ((PlayerEntity )event.getSource().getTrueSource()).getActivePotionEffect(EffectRegistry.hatred_bloodline_enchantment_active_effect.get()).getAmplifier()+1;
                    event.setAmount((float)(event.getAmount()*(1+effectLvl*0.25)));
                }
            }
        }
    }
}

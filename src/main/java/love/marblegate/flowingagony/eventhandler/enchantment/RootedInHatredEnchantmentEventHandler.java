package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.capibility.hatredbloodlinestatus.HatredBloodlineStatusCapability;
import love.marblegate.flowingagony.capibility.hatredbloodlinestatus.IHatredBloodlineStatusCapability;
import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.PlayerUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class RootedInHatredEnchantmentEventHandler {

    @SubscribeEvent
    public static void doResentfulSoulEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(!event.isCanceled()) {
                if (event.getEntityLiving() instanceof PlayerEntity) {
                    if (event.getAmount() >= event.getEntityLiving().getHealth()) {
                        int enchantmentLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel(((PlayerEntity) event.getEntityLiving()), EnchantmentRegistry.resentful_soul_enchantment.get(), EquipmentSlotType.HEAD);
                        if (enchantmentLvl != 0) {
                            if (event.getEntityLiving().getLastAttackedEntityTime() <= (25 + enchantmentLvl * 25)) {
                                event.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doTooResentfulToDieEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if (!event.isCanceled()) {
                if(event.getEntityLiving() instanceof PlayerEntity) {
                    if (event.getAmount() >= event.getEntityLiving().getHealth()) {
                        int enchantmentLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel(((PlayerEntity) event.getEntityLiving()), EnchantmentRegistry.too_resentful_to_die_enchantment.get(), EquipmentSlotType.HEAD);
                        if (!event.getEntityLiving().isPotionActive(EffectRegistry.extreme_hatred_effect.get())) {
                            if (enchantmentLvl != 0) {
                                event.getEntityLiving().heal(1 + enchantmentLvl * 3);
                                event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.extreme_hatred_effect.get(), 7200));
                                event.setCanceled(true);
                            }
                        } else {
                            int potionLvl = event.getEntityLiving().getActivePotionEffect(EffectRegistry.extreme_hatred_effect.get()).getAmplifier() + 1;
                            if (potionLvl == 1) {
                                event.getEntityLiving().heal(1 + enchantmentLvl * 2);
                                event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.extreme_hatred_effect.get(), 7200, 1));
                                event.setCanceled(true);
                            } else if (potionLvl == 2) {
                                event.getEntityLiving().heal(1 + enchantmentLvl);
                                event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.extreme_hatred_effect.get(), 7200, 2));
                                event.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doOutrageousSpiritEnchantmentEvent(LivingHurtEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                int enchantmentLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel(((PlayerEntity)event.getSource().getTrueSource()), EnchantmentRegistry.outrageous_spirit_enchantment.get(),EquipmentSlotType.MAINHAND);
                if(enchantmentLvl!=0){
                    int negativeEffectCount = 0;
                    if(((PlayerEntity) event.getSource().getTrueSource()).isBurning()) negativeEffectCount++;
                    negativeEffectCount += ((PlayerEntity) event.getSource().getTrueSource()).getActivePotionEffects().stream().filter(
                            EffectInstance -> EffectInstance.getPotion().getEffectType()== EffectType.HARMFUL
                    ).count();
                    event.setAmount(event.getAmount()*(1+((0.2f+(0.1f*enchantmentLvl))*negativeEffectCount)));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doHatredBloodlikeEnchantmentEvent_acvtivateHatredBloodlineMarkOnDeath(LivingDeathEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                int enchantLvl = PlayerUtil.getTotalLevelPlayerArmorEnchantedSameEnchantment((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.hatred_bloodline_enchantment.get());
                if(enchantLvl!=0){
                    LazyOptional<IHatredBloodlineStatusCapability> statusCap = event.getEntityLiving().getCapability(HatredBloodlineStatusCapability.HATRED_BLOODLINE_STATUS_CAPABILITY);
                    statusCap.ifPresent(
                            cap-> {
                                cap.setActiveLevel(enchantLvl);
                            }
                    );
                }
            }
        }
    }

    @SubscribeEvent
    public static void doHatredBloodlikeEnchantmentEvent_activeEnchantmentEffectWhenRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.getPlayer().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                LazyOptional<IHatredBloodlineStatusCapability> statusCap = event.getEntityLiving().getCapability(HatredBloodlineStatusCapability.HATRED_BLOODLINE_STATUS_CAPABILITY);
                statusCap.ifPresent(
                        cap -> {
                            if (cap.getActiveLevel() != 0) {
                                int hatredBloodlineLevel = cap.getActiveLevel();
                                event.getPlayer().addPotionEffect(new EffectInstance(EffectRegistry.hatred_bloodline_enchantment_active_effect.get(), 800 * hatredBloodlineLevel, hatredBloodlineLevel - 1));
                                cap.setActiveLevel(0);
                            }
                        }
                );
            }
        }
    }

    @SubscribeEvent
    public static void doFreshRevengeEnchantmentEvent_applyBuff(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                int enchantmentLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel(((PlayerEntity)event.getSource().getTrueSource()), EnchantmentRegistry.fresh_revenge_enchantment.get(),EquipmentSlotType.MAINHAND);
                if(enchantmentLvl!=0){
                    if(event.getEntityLiving().getLastAttackedEntityTime()<=(12+enchantmentLvl*4)){
                        ((PlayerEntity)event.getSource().getTrueSource()).addPotionEffect(new EffectInstance(EffectRegistry.fresh_revenge_enchantment_active_effect.get(),100 * enchantmentLvl));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doFreshRevengeEnchantmentEvent_appleMultiplierToAttack(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                if(((PlayerEntity)event.getSource().getTrueSource()).isPotionActive(EffectRegistry.fresh_revenge_enchantment_active_effect.get()))
                    event.setAmount(event.getAmount()*(1+((event.getEntityLiving().getRNG().nextFloat())*1.5f)));
            }
        }
    }
}

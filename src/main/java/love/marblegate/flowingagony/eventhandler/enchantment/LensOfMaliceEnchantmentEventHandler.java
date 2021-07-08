package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.PlaySoundWIthLocationPacket;
import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EntityUtil;
import love.marblegate.flowingagony.util.PlayerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber()
public class LensOfMaliceEnchantmentEventHandler {

    @SubscribeEvent
    public static void doVengeanceEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity && event.getSource().getTrueSource() instanceof LivingEntity){
                int enchantmentLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel(((PlayerEntity)event.getEntityLiving()), EnchantmentRegistry.vengeance.get(),EquipmentSlotType.HEAD);
                if(enchantmentLvl != 0){
                    if(event.getSource().getTrueSource() instanceof LivingEntity)
                        ((LivingEntity)event.getSource().getTrueSource()).addPotionEffect(new EffectInstance(EffectRegistry.cursed_hatred_effect.get(),180,enchantmentLvl-1));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doPerceivedMaliceEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity && event.getSource().getTrueSource() instanceof LivingEntity){
                int enchantmentLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel(((PlayerEntity)event.getEntityLiving()),EnchantmentRegistry.perceived_malice.get(), EquipmentSlotType.HEAD);
                if(enchantmentLvl!=0){
                    if(event.getSource().getTrueSource() instanceof PlayerEntity){
                        ((PlayerEntity) event.getSource().getTrueSource()).addPotionEffect(new EffectInstance(EffectRegistry.cursed_antipathy_effect.get(),200));
                        ((PlayerEntity) event.getSource().getTrueSource()).addPotionEffect(new EffectInstance(Effects.SLOWNESS,200));
                    }
                    else{
                            event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.cursed_antipathy_effect.get(),200,enchantmentLvl-1));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doMaliceOutbreakEnchantmentEvent(LivingDamageEvent event){
        if(event.getEntityLiving() instanceof PlayerEntity && event.getSource().getTrueSource() instanceof LivingEntity){
            int enchantmentLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.malice_outbreak.get(),EquipmentSlotType.HEAD);
            if(enchantmentLvl!=0){
                if(event.getSource().getTrueSource() instanceof LivingEntity){
                    ((LivingEntity) event.getSource().getTrueSource()).applyKnockback(0.4f * enchantmentLvl,-event.getEntityLiving().getLookVec().x,-event.getEntityLiving().getLookVec().z);
                    //Play Sound FX
                    if (!event.getSource().getTrueSource().world.isRemote) {
                        Networking.INSTANCE.send(
                                PacketDistributor.NEAR.with(
                                        () -> new PacketDistributor.TargetPoint(event.getSource().getTrueSource().getPosX(),event.getSource().getTrueSource().getPosY(),event.getSource().getTrueSource().getPosZ(),
                                                192,event.getSource().getTrueSource().world.getDimensionKey())
                                ),
                                new PlaySoundWIthLocationPacket(PlaySoundWIthLocationPacket.ModSoundType.MALICE_OUTBREAK_KNOCKBACK_SOUND,true,
                                        event.getSource().getTrueSource().getPosX(),
                                        event.getSource().getTrueSource().getPosY()+event.getSource().getTrueSource().getEyeHeight(),
                                        event.getSource().getTrueSource().getPosZ()));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doInfectiousMaliceEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity && event.getSource().getTrueSource() instanceof LivingEntity){
                int enchantNum = PlayerUtil.getTotalPiecePlayerArmorEnchantedSameEnchantment((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.infectious_malice.get());
                if(enchantNum!=0){
                    if(event.getSource().getTrueSource() instanceof PlayerEntity){
                        ((PlayerEntity) event.getSource().getTrueSource()).addPotionEffect(new EffectInstance(EffectRegistry.cursed_hatred_effect.get(),200 * enchantNum));
                    }
                    else {
                        List<EffectInstance> effects = ((LivingEntity) event.getSource().getTrueSource()).getActivePotionEffects().stream().filter(EffectInstance ->
                                EffectInstance.getPotion().getEffectType()== EffectType.HARMFUL).collect(Collectors.toList());
                        if(!effects.isEmpty()){
                            List<LivingEntity> targets = PlayerUtil.getTargetListUnderSameType((PlayerEntity) event.getEntityLiving(),8,2, (LivingEntity) event.getSource().getTrueSource());
                            int effectCount = effects.size();
                            if (effectCount <= enchantNum){
                                for (EffectInstance effect : effects){
                                    for(LivingEntity target: targets){
                                        target.addPotionEffect(effect);
                                    }
                                }
                            }
                            else{
                                List<EffectInstance> selectedEffect = new ArrayList<>();
                                while(enchantNum>0){
                                    EffectInstance effect = effects.get(event.getEntityLiving().getRNG().nextInt(effectCount));
                                    if(!selectedEffect.contains(effect)){
                                        selectedEffect.add(effect);
                                        enchantNum--;
                                    }
                                }
                                for(EffectInstance effect:selectedEffect){
                                    for(LivingEntity target: targets){
                                        target.addPotionEffect(effect);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doISeeYouNowEnchantmentEvent(LivingSetAttackTargetEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getTarget() instanceof PlayerEntity){
                if(PlayerUtil.isPlayerSpecificSlotEnchanted(((PlayerEntity)event.getTarget()),EnchantmentRegistry.i_see_you_now.get(),EquipmentSlotType.HEAD)){
                    event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.GLOWING,6000));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doBackAndFillEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity && EntityUtil.isMonster(event.getEntityLiving())){
                int enchantmentLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getSource().getTrueSource(),EnchantmentRegistry.back_and_fill.get(),EquipmentSlotType.MAINHAND);
                if(enchantmentLvl!=0){
                    if(event.getEntityLiving().getAttackingEntity() == event.getSource().getTrueSource()){
                        event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.back_and_fill_enchantment_active_effect.get(),100,enchantmentLvl-1));
                    } else {
                        event.setAmount(event.getAmount()+enchantmentLvl+1);
                    }
                }
            }
        }
    }
}

package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.PlaySoundPacket;
import love.marblegate.flowingagony.network.packet.RemoveEffectSyncToClientPacket;
import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.PlayerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber()
public class InnerPotentialEnchantmentEventHandler {

    @SubscribeEvent
    public static void doStubbornStepEnchantmentEvent_addKnockBackResistenceModifier(LivingKnockBackEvent event){
        if(event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                int enchantLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.stubborn_step.get(),EquipmentSlotType.LEGS);
                if(enchantLvl != 0){
                    event.setStrength(event.getStrength() * (1 - enchantLvl * 0.15f));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doStubbornStepEnchantmentEvent_cancelFloatingEffect(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity && event.getSource().getTrueSource() instanceof LivingEntity){
                if(PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.stubborn_step.get(), EquipmentSlotType.LEGS)){
                    if(((PlayerEntity)(event.getEntityLiving())).isPotionActive(Effects.LEVITATION)){
                        ((PlayerEntity)(event.getEntityLiving())).removeActivePotionEffect(Effects.LEVITATION);
                        //Sync to Client
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayerEntity) event.getEntityLiving()
                                ),
                                new RemoveEffectSyncToClientPacket(Effects.LEVITATION));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doFrivolousStepEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity && event.getSource().getTrueSource() instanceof LivingEntity){
                int enchantLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.frivolous_step.get(), EquipmentSlotType.LEGS);
                if(enchantLvl != 0){
                    if(enchantLvl == 1)
                        ((PlayerEntity)(event.getEntityLiving())).addPotionEffect(new EffectInstance(EffectRegistry.frivolous_step_enchantment_active.get(),200));
                    else
                        ((PlayerEntity)(event.getEntityLiving())).addPotionEffect(new EffectInstance(EffectRegistry.frivolous_step_enchantment_active.get(),200,1));
                    if(((PlayerEntity)(event.getEntityLiving())).isPotionActive(Effects.SLOWNESS)){
                        ((PlayerEntity)(event.getEntityLiving())).removeActivePotionEffect(Effects.SLOWNESS);
                        //Sync to Client
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayerEntity) event.getEntityLiving()
                                ),
                                new RemoveEffectSyncToClientPacket(Effects.SLOWNESS));
                    }
                }
            }
        }

    }

    @SubscribeEvent
    public static void doPotentialBurstEnchantmentEvent_addSpeedModifier(TickEvent.PlayerTickEvent event){
        if(event.phase== TickEvent.Phase.START){
            if(!event.player.world.isRemote()){
                int enchantLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel(event.player,EnchantmentRegistry.potential_burst.get(),EquipmentSlotType.FEET);
                if(enchantLvl!=0){
                    if(event.player.getHealth()<=(3+enchantLvl)){
                        if(!(event.player.isSprinting()||event.player.isSwimming()||event.player.isElytraFlying())){
                            if(event.player.isPotionActive(EffectRegistry.potential_burst_enchantment_active.get())){
                                int nextAmplifier = Math.min(event.player.getActivePotionEffect(EffectRegistry.potential_burst_enchantment_active.get()).getAmplifier()+1,150);
                                event.player.addPotionEffect(new EffectInstance(EffectRegistry.potential_burst_enchantment_active.get(),20,nextAmplifier));
                            } else {
                                event.player.addPotionEffect(new EffectInstance(EffectRegistry.potential_burst_enchantment_active.get(),20));
                            }
                        }
                        else{
                            if(event.player.isPotionActive(EffectRegistry.potential_burst_enchantment_active.get())){
                                event.player.removeActivePotionEffect(EffectRegistry.potential_burst_enchantment_active.get());
                                //Sync to Client
                                Networking.INSTANCE.send(
                                        PacketDistributor.PLAYER.with(
                                                () -> (ServerPlayerEntity) event.player
                                        ),
                                        new RemoveEffectSyncToClientPacket(EffectRegistry.potential_burst_enchantment_active.get()));
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doMiraculousEscapeEnchantmentEvent_launch(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            if (event.getEntityLiving().getHealth() < 4f) {
                if (PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.miraculous_escape.get(), EquipmentSlotType.FEET)) {
                    if(!((PlayerEntity)(event.getEntityLiving())).isPotionActive(EffectRegistry.miraculous_escape_enchantment_active.get())){
                        //Play Sound Effect
                        if (!((PlayerEntity)(event.getEntityLiving())).world.isRemote) {
                            Networking.INSTANCE.send(
                                    PacketDistributor.PLAYER.with(
                                            () -> (ServerPlayerEntity) (event.getEntityLiving())
                                    ),
                                    new PlaySoundPacket(PlaySoundPacket.ModSoundType.MIRACULOUS_ESCAPE_HEARTBEAT,true));
                        }
                        ((PlayerEntity)(event.getEntityLiving())).addPotionEffect(new EffectInstance(EffectRegistry.miraculous_escape_enchantment_force_escape.get(),40));
                        ((PlayerEntity)(event.getEntityLiving())).addPotionEffect(new EffectInstance(EffectRegistry.miraculous_escape_enchantment_active.get(),200));
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public static void doMiraculousEscapeEnchantmentEvent_processFallDamage(LivingDamageEvent event) {
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                if(event.getSource().getDamageType().equals("fall")||event.getSource().getDamageType().equals("cramming")||event.getSource().getDamageType().equals("inWall")) {
                    if(((PlayerEntity)(event.getEntityLiving())).isPotionActive(EffectRegistry.miraculous_escape_enchantment_active.get())){
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doArmorUpEnchantmentEvent(LivingDamageEvent event) {
        if(!event.getEntityLiving().world.isRemote()) {
            if (!event.isCanceled()) {
                if (event.getEntityLiving() instanceof PlayerEntity) {
                    int enchantLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.armor_up.get(), EquipmentSlotType.CHEST);
                    if (enchantLvl != 0) {
                        if (((PlayerEntity)(event.getEntityLiving())).getHealth() < (5 + enchantLvl)) {
                            float existYellowHeart = ((PlayerEntity)(event.getEntityLiving())).getAbsorptionAmount();
                            if (existYellowHeart + 1 < (5 + enchantLvl)) {
                                ((PlayerEntity)(event.getEntityLiving())).setAbsorptionAmount(existYellowHeart + 1);
                            }
                        }
                    }
                }
            }
        }
    }
}

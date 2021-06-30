package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.PlaySoundPacket;
import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.PlayerUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber()
public class InnerPotentialEnchantmentEventHandler {

    @SubscribeEvent
    public static void doStubbornStepEnchantmentEvent_saddKnockBackResistenceModifier(LivingKnockBackEvent event){
        if(event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                int enchantLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.stubborn_step_enchantment.get(),EquipmentSlotType.LEGS);
                if(enchantLvl != 0){
                    event.setStrength(event.getStrength() * (0.3f + enchantLvl * 0.2f));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doStubbornStepEnchantmentEvent_cancelFloatingEffect(LivingAttackEvent event){
        if(event.getEntityLiving() instanceof PlayerEntity){
            if(PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.stubborn_step_enchantment.get(), EquipmentSlotType.LEGS)){
                if(((PlayerEntity)(event.getEntityLiving())).isPotionActive(Effects.LEVITATION))
                    ((PlayerEntity)(event.getEntityLiving())).removeActivePotionEffect(Effects.LEVITATION);
            }
        }
    }

    @SubscribeEvent
    public static void doFrivolousStepEnchantmentEvent(LivingAttackEvent event){
        if(event.getEntityLiving() instanceof PlayerEntity){
            int enchantLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.frivolous_step_enchantment.get(), EquipmentSlotType.LEGS);
            if(enchantLvl != 0){
                if(((PlayerEntity)(event.getEntityLiving())).isPotionActive(Effects.SLOWNESS))
                    ((PlayerEntity)(event.getEntityLiving())).removeActivePotionEffect(Effects.SLOWNESS);
                if(!event.getEntityLiving().world.isRemote()){
                    if(enchantLvl == 1)
                        ((PlayerEntity)(event.getEntityLiving())).addPotionEffect(new EffectInstance(EffectRegistry.frivolous_step_enchantment_active_effect.get(),200));
                    else
                        ((PlayerEntity)(event.getEntityLiving())).addPotionEffect(new EffectInstance(EffectRegistry.frivolous_step_enchantment_active_effect.get(),200,1));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doPotentialBurstEnchantmentEvent_addSpeedModifier(TickEvent.PlayerTickEvent event){
        if(event.phase== TickEvent.Phase.START){
            int enchantLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel(event.player,EnchantmentRegistry.potential_burst_enchantment.get(),EquipmentSlotType.FEET);
            if(enchantLvl!=0){
                if(event.player.getHealth()<=(6+2*enchantLvl)){
                    if(!(event.player.isSprinting()||event.player.isSwimming()||event.player.isElytraFlying())){
                        if(enchantLvl==1){
                            if(!event.player.world.isRemote())
                                event.player.addPotionEffect(new EffectInstance(EffectRegistry.potential_burst_enchantment_active_effect.get(),3));
                        }
                        if(enchantLvl==2)
                            if(!event.player.world.isRemote())
                                event.player.addPotionEffect(new EffectInstance(EffectRegistry.potential_burst_enchantment_active_effect.get(),3,1));
                    }
                    else{
                        if(enchantLvl==1){
                            event.player.removeActivePotionEffect(EffectRegistry.potential_burst_enchantment_active_effect.get());
                        }
                        if(enchantLvl==2){
                            event.player.removeActivePotionEffect(EffectRegistry.potential_burst_enchantment_active_effect.get());
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doPotentialBurstEnchantmentEvent_modifyJumpVector(LivingEvent.LivingJumpEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            int enchantLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.potential_burst_enchantment.get(),EquipmentSlotType.FEET);
            if (enchantLvl != 0) {
                if(((PlayerEntity)(event.getEntityLiving())) .getHealth()<=(6+2*enchantLvl)){
                    ((PlayerEntity)(event.getEntityLiving())).setMotion(((PlayerEntity)(event.getEntityLiving())).getMotion().add(0d,0.5d*enchantLvl,0d));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doMiraculousEscapeEnchantmentEvent_launch(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            if (event.getEntityLiving().getHealth() < 4f) {
                if (PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.miraculous_escape_enchantment.get(), EquipmentSlotType.FEET)) {
                    if(!((PlayerEntity)(event.getEntityLiving())).isPotionActive(EffectRegistry.miraculous_escape_enchantment_active_effect.get())){
                        //Play Sound Effect
                        if (!((PlayerEntity)(event.getEntityLiving())).world.isRemote) {
                            Networking.INSTANCE.send(
                                    PacketDistributor.PLAYER.with(
                                            () -> (ServerPlayerEntity) (event.getEntityLiving())
                                    ),
                                    new PlaySoundPacket(PlaySoundPacket.ModSoundType.MIRACULOUS_ESCAPE_HEARTBEAT,true));
                        }
                        ((PlayerEntity)(event.getEntityLiving())).addPotionEffect(new EffectInstance(EffectRegistry.miraculous_escape_enchantment_force_escape_effect.get(),40));
                        ((PlayerEntity)(event.getEntityLiving())).addPotionEffect(new EffectInstance(EffectRegistry.miraculous_escape_enchantment_active_effect.get(),200));
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
                    if(((PlayerEntity)(event.getEntityLiving())).isPotionActive(EffectRegistry.miraculous_escape_enchantment_active_effect.get())){
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
                    int enchantLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.armor_up_enchantment.get(), EquipmentSlotType.CHEST);
                    if (enchantLvl != 0) {
                        if (((PlayerEntity)(event.getEntityLiving())).getHealth() < (5 + enchantLvl)) {
                            float existYellowHeart = ((PlayerEntity)(event.getEntityLiving())).getAbsorptionAmount();
                            if (existYellowHeart + 1 > (5 + enchantLvl * 5)) {
                                ((PlayerEntity)(event.getEntityLiving())).setAbsorptionAmount(5 + enchantLvl * 5);
                            } else {
                                ((PlayerEntity)(event.getEntityLiving())).setAbsorptionAmount(existYellowHeart + 1);
                            }
                        }
                    }
                }
            }
        }
    }
}

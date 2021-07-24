package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.capibility.abnormaljoy.AbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.abnormaljoy.IAbnormalJoyCapability;
import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.AbnormalJoySyncPacket;
import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import love.marblegate.flowingagony.util.EntityUtil;
import love.marblegate.flowingagony.util.StandardUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

@Mod.EventBusSubscriber()
public class MadeOfSufferingEnchantmentEventHandler {
    @SubscribeEvent
    public static void onDrowningPhobiaEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity && StandardUtil.shouldReflectDamage(event)){
                if(event.getEntityLiving().isSwimming()){
                    int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.DROWNING_PHOBIA.get(), EquipmentSlotType.HEAD, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                    if(enchantmentLvl!=0){
                        dealPhobiaEffectDamage(event,Effects.BLINDNESS,enchantmentLvl);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBurningPhobiaEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity && StandardUtil.shouldReflectDamage(event)){
                if(event.getEntityLiving().isInLava()||event.getEntityLiving().getFireTimer()>0){
                    if(!event.getSource().isFireDamage()){
                        int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.BURNING_PHOBIA.get(), EquipmentSlotType.HEAD, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                        if(enchantmentLvl!=0){
                            dealPhobiaEffectDamage(event,Effects.SLOWNESS,enchantmentLvl);
                        }
                    }
                }
            }
        }
    }

    private static void dealPhobiaEffectDamage(LivingDamageEvent event, Effect effect_1 ,int enchantmentLvl) {
        event.getEntityLiving().addPotionEffect(new EffectInstance(effect_1,500 - enchantmentLvl * 100));
        event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.NAUSEA,500 - enchantmentLvl * 100));
        List<LivingEntity> targets = EntityUtil.getTargetsExceptOneself((PlayerEntity) event.getEntityLiving(),12,2, livingEntity->EntityUtil.isHostile(livingEntity,false));
        for(LivingEntity target: targets) {
            target.attackEntityFrom(CustomDamageSource.causePhobiaDamage((PlayerEntity) event.getEntityLiving()), event.getAmount()*(1.5F+0.5F*enchantmentLvl));
        }
        if(event.getSource().getTrueSource() instanceof LivingEntity){
            if(!targets.contains((LivingEntity) event.getSource().getTrueSource()) && event.getEntityLiving()!=event.getSource().getTrueSource())
                ((LivingEntity)event.getSource().getTrueSource()).attackEntityFrom(CustomDamageSource.causePhobiaDamage((PlayerEntity) event.getEntityLiving()), event.getAmount()*(1.5F+0.5F*enchantmentLvl));

        }

    }

    @SubscribeEvent
    public static void onPrayerOfPainEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.PRAYER_OF_PAIN.get(),EquipmentSlotType.HEAD, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantLvl!=0){
                    if(event.getEntityLiving().getHealth()<(4+enchantLvl*2)){
                        if(event.getEntityLiving().isPotionActive(EffectRegistry.LET_ME_SAVOR_IT.get())){
                            if(event.getEntityLiving().getActivePotionEffect(EffectRegistry.LET_ME_SAVOR_IT.get()).getAmplifier()<9)
                            event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.LET_ME_SAVOR_IT.get(),72000,
                                    event.getEntityLiving().getActivePotionEffect(EffectRegistry.LET_ME_SAVOR_IT.get()).getAmplifier()+1));
                        } else {
                            event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.LET_ME_SAVOR_IT.get(),72000));
                        }
                    }
                }
            }
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                if(((PlayerEntity) event.getSource().getTrueSource()).isPotionActive(EffectRegistry.LET_ME_SAVOR_IT.get())){
                    int effectLvl = ((PlayerEntity) event.getSource().getTrueSource()).getActivePotionEffect(EffectRegistry.LET_ME_SAVOR_IT.get()).getAmplifier();
                    event.setAmount(event.getAmount()*(1-0.09f*(effectLvl+1)));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onConstrainedHeartEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.CONSTRAINED_HEART.get(),EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl!=0) {
                    if(event.getSource().isFireDamage()||event.getSource().getDamageType().equals("inWall")||
                            event.getSource().getDamageType().equals("cramming")||event.getSource().getDamageType().equals("fall")||
                            event.getSource().getDamageType().equals("flyIntoWall")||event.getSource().getDamageType().equals("wither")||
                            (event.getSource().getDamageType().equals("magic")||((PlayerEntity)event.getEntityLiving()).isPotionActive(Effects.POISON))){
                        grandAbnormalJoyPoint(event, enchantLvl);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPiercingFeverEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.PIERCING_FEVER.get(),EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl!=0){
                    if(event.getSource().isProjectile() || event.getSource().getDamageType().equals("cactus") || event.getSource().getDamageType().equals("sweetBerryBush")){
                        grandAbnormalJoyPoint(event, enchantLvl);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDestructionWorshipEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.DESTRUCTION_WORSHIP.get(),EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl!=0){
                    if(event.getSource().isMagicDamage() || event.getSource().isExplosion() || event.getSource().getDamageType().equals("lightningBolt")){
                        grandAbnormalJoyPoint(event, enchantLvl);
                    }
                }
            }
        }
    }

    private static void grandAbnormalJoyPoint(LivingDamageEvent event, int enchantLvl) {
        LazyOptional<IAbnormalJoyCapability> pointCap = event.getEntityLiving().getCapability(AbnormalJoyCapability.ABNORMALJOY_CAPABILITY);
        pointCap.ifPresent(
                cap-> {
                    cap.add(event.getAmount()*(0.25F*(1+enchantLvl)));
                    //Sync to Client
                    Networking.INSTANCE.send(
                            PacketDistributor.PLAYER.with(
                                    () -> (ServerPlayerEntity) (event.getEntityLiving())
                            ),
                            new AbnormalJoySyncPacket(cap.get()));
                }
        );
    }


}

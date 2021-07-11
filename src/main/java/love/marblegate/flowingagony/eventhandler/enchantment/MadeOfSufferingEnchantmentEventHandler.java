package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.capibility.abnormaljoy.AbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.abnormaljoy.IAbnormalJoyCapability;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.AbnormalJoySyncPacket;
import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import love.marblegate.flowingagony.util.EntityUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
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
            if(event.getEntityLiving() instanceof PlayerEntity){
                if(event.getEntityLiving().isSwimming()){
                    if(EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.drowning_phobia_enchantment.get(), EquipmentSlotType.HEAD, EnchantmentUtil.ItemEncCalOp.GENERAL)==1){
                        dealPhobiaEffectDamage(event,Effects.BLINDNESS);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBurningPhobiaEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                if(event.getEntityLiving().isInLava()||event.getEntityLiving().getFireTimer()>0){
                    if(!(event.getSource().getDamageType().equals("inFire")||event.getSource().getDamageType().equals("onFire")||event.getSource().getDamageType().equals("lava"))){
                        if(EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.burning_phobia_enchantment.get(), EquipmentSlotType.HEAD, EnchantmentUtil.ItemEncCalOp.GENERAL)==1){
                            dealPhobiaEffectDamage(event,Effects.SLOWNESS);
                        }
                    }
                }
            }
        }
    }

    private static void dealPhobiaEffectDamage(LivingDamageEvent event, Effect effect_1) {
        event.getEntityLiving().addPotionEffect(new EffectInstance(effect_1,600));
        event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.NAUSEA,600));
        List<LivingEntity> targets = EntityUtil.getTargetsExceptOneself((PlayerEntity) event.getEntityLiving(),12,2, LivingEntity->
                LivingEntity instanceof MonsterEntity || LivingEntity instanceof SlimeEntity);
        for(LivingEntity target: targets) {
            target.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) event.getEntityLiving()), event.getAmount()*3);
        }
    }

    @SubscribeEvent
    public static void onPrayerOfPainEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.prayer_of_pain_enchantment.get(),EquipmentSlotType.HEAD, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantLvl!=0){
                    if(event.getEntityLiving().getHealth()<(4+enchantLvl*2)){
                        if(event.getEntityLiving().isPotionActive(EffectRegistry.let_me_savor_it.get())){
                            if(event.getEntityLiving().getActivePotionEffect(EffectRegistry.let_me_savor_it.get()).getAmplifier()<9)
                            event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.let_me_savor_it.get(),72000,
                                    event.getEntityLiving().getActivePotionEffect(EffectRegistry.let_me_savor_it.get()).getAmplifier()+1));
                        } else {
                            event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.let_me_savor_it.get(),72000));
                        }
                    }
                }
            }
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                if(((PlayerEntity) event.getSource().getTrueSource()).isPotionActive(EffectRegistry.let_me_savor_it.get())){
                    int effectLvl = ((PlayerEntity) event.getSource().getTrueSource()).getActivePotionEffect(EffectRegistry.let_me_savor_it.get()).getAmplifier();
                    event.setAmount(event.getAmount()*(1-0.09f*(effectLvl+1)));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onConstrainedHeartEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                if (EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.constrained_heart_enchantment.get(), EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.GENERAL)==1) {
                    if(event.getSource().getDamageType().equals("inFire")||event.getSource().getDamageType().equals("onFire")||
                            event.getSource().getDamageType().equals("lava")||event.getSource().getDamageType().equals("inWall")||
                            event.getSource().getDamageType().equals("cramming")||event.getSource().getDamageType().equals("fall")||
                            event.getSource().getDamageType().equals("flyIntoWall")||event.getSource().getDamageType().equals("wither")||
                            (event.getSource().getDamageType().equals("magic")&&event.getEntityLiving().isPotionActive(Effects.POISON))){
                        LazyOptional<IAbnormalJoyCapability> pointCap = event.getEntityLiving().getCapability(AbnormalJoyCapability.ABNORMALJOY_CAPABILITY);
                        pointCap.ifPresent(
                                cap-> {
                                    cap.add(event.getAmount());
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
            }
        }
    }

    @SubscribeEvent
    public static void onPiercingFeverEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                if (EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.piercing_fever_enchantment.get(), EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.GENERAL)==1) {
                    if(event.getSource().getTrueSource() instanceof LivingEntity || event.getSource().getDamageType().equals("cactus")){
                        LazyOptional<IAbnormalJoyCapability> pointCap = event.getEntityLiving().getCapability(AbnormalJoyCapability.ABNORMALJOY_CAPABILITY);
                        pointCap.ifPresent(
                                cap-> {
                                    cap.add(event.getAmount());
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
            }
        }
    }


}

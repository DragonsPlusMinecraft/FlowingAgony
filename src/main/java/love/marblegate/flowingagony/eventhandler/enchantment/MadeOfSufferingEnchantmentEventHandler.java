package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.capibility.abnormaljoy.IAbnormalJoyCapability;
import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.capibility.ModCapability;
import love.marblegate.flowingagony.util.PlayerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber()
public class MadeOfSufferingEnchantmentEventHandler {
    @SubscribeEvent
    public static void onDrowningPhobiaEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                if(event.getEntityLiving().isSwimming()){
                    if(PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.drowning_phobia_enchantment.get(), EquipmentSlotType.HEAD)){
                        dealPhobiaEffectDamage(event,Effects.BLINDNESS,Effects.NAUSEA);
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
                        if(PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.burning_phobia_enchantment.get(), EquipmentSlotType.HEAD)){
                            dealPhobiaEffectDamage(event,Effects.SLOWNESS,Effects.NAUSEA);
                        }
                    }
                }
            }
        }
    }

    private static void dealPhobiaEffectDamage(LivingDamageEvent event,Effect effect_1, Effect effect_2) {
        event.getEntityLiving().addPotionEffect(new EffectInstance(effect_1,600));
        event.getEntityLiving().addPotionEffect(new EffectInstance(effect_2,600));
        List<LivingEntity> targets = PlayerUtil.getTargetList((PlayerEntity) event.getEntityLiving(),12,2, LivingEntity->
                LivingEntity instanceof MonsterEntity || LivingEntity instanceof SlimeEntity
                        || LivingEntity instanceof WitherEntity);
        for(LivingEntity target: targets) {
            target.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) event.getEntityLiving()), event.getAmount()*3);
        }
    }

    @SubscribeEvent
    public static void onPrayerOfPainEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                int enchantLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.prayer_of_pain_enchantment.get(),EquipmentSlotType.HEAD);
                if(enchantLvl!=0){
                    if(event.getEntityLiving().getHealth()<(4+enchantLvl*2)){
                        if(event.getEntityLiving().isPotionActive(EffectRegistry.let_me_savor_it_effect.get())){
                            if(event.getEntityLiving().getActivePotionEffect(EffectRegistry.let_me_savor_it_effect.get()).getAmplifier()<9)
                            event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.let_me_savor_it_effect.get(),72000,
                                    event.getEntityLiving().getActivePotionEffect(EffectRegistry.let_me_savor_it_effect.get()).getAmplifier()+1));
                        } else {
                            event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.let_me_savor_it_effect.get(),72000));
                        }
                    }
                }
            }
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                if(((PlayerEntity) event.getSource().getTrueSource()).isPotionActive(EffectRegistry.let_me_savor_it_effect.get())){
                    int effectLvl = ((PlayerEntity) event.getSource().getTrueSource()).getActivePotionEffect(EffectRegistry.let_me_savor_it_effect.get()).getAmplifier();
                    event.setAmount(event.getAmount()*(1-0.09f*(effectLvl+1)));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onConstrainedHeartEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                if (PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.constrained_heart_enchantment.get(), EquipmentSlotType.CHEST)) {
                    if(event.getSource().getDamageType().equals("inFire")||event.getSource().getDamageType().equals("onFire")||
                            event.getSource().getDamageType().equals("lava")||event.getSource().getDamageType().equals("inWall")||
                            event.getSource().getDamageType().equals("cramming")||event.getSource().getDamageType().equals("fall")||
                            event.getSource().getDamageType().equals("flyIntoWall")||event.getSource().getDamageType().equals("wither")||
                            (event.getSource().getDamageType().equals("magic")&&event.getEntityLiving().isPotionActive(Effects.POISON))){
                        LazyOptional<IAbnormalJoyCapability> pointCap = event.getEntityLiving().getCapability(ModCapability.ABNORMALJOY_CAPABILITY);
                        pointCap.ifPresent(
                                cap-> {
                                    cap.add(event.getAmount());
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
                if (PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.piercing_fever_enchantment.get(), EquipmentSlotType.CHEST)) {
                    if(event.getSource().getDamageType().equals("mob")||event.getSource().getDamageType().equals("player")||
                            event.getSource().getDamageType().equals("cactus")){
                        LazyOptional<IAbnormalJoyCapability> pointCap = event.getEntityLiving().getCapability(ModCapability.ABNORMALJOY_CAPABILITY);
                        pointCap.ifPresent(
                                cap-> {
                                    cap.add(event.getAmount());
                                }
                        );
                    }
                }
            }
        }
    }


}

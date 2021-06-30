package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.effect.LightburnFungalInfectionEffect;
import love.marblegate.flowingagony.effect.special.ImplicitBaseEffect;
import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.RemoveEffectSyncToClientPacket;
import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.PlayerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.monster.PhantomEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber()
public class TheMistakensEnchantmentEventHandler {

    @SubscribeEvent
    public static void doShadowbornEnchantmentEvent(TickEvent.PlayerTickEvent event){
        if(event.phase == TickEvent.Phase.START){
            if(!event.player.world.isRemote()){
                if (event.player.isPotionActive(Effects.BLINDNESS)) {
                    if (event.player.world.getLight(new BlockPos(event.player.getPosition())) >= 5) {
                        if (PlayerUtil.isPlayerSpecificSlotEnchanted(event.player, EnchantmentRegistry.shadowborn_enchantment.get(), EquipmentSlotType.HEAD)) {
                            event.player.removeActivePotionEffect(Effects.BLINDNESS);
                            //Sync to Client
                            Networking.INSTANCE.send(
                                    PacketDistributor.PLAYER.with(
                                            () -> (ServerPlayerEntity) event.player
                                    ),
                                    new RemoveEffectSyncToClientPacket(RemoveEffectSyncToClientPacket.EffectType.BLINDNESS));
                        }
                    }
                }
                if(event.player.world.getLight(new BlockPos(event.player.getPosition()))<=5){
                    if(PlayerUtil.isPlayerSpecificSlotEnchanted(event.player,EnchantmentRegistry.shadowborn_enchantment.get(), EquipmentSlotType.HEAD)){
                        if(!event.player.isPotionActive(Effects.NIGHT_VISION)){
                            event.player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION,1200));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doPrototypeChaoticEnchantmentEvent(PotionEvent.PotionAddedEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                int enchantLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel(((PlayerEntity)event.getEntityLiving()),EnchantmentRegistry.prototype_chaotic_enchantment.get(),EquipmentSlotType.CHEST);
                if(enchantLvl!=0){
                    if(!(event.getPotionEffect().getPotion() instanceof ImplicitBaseEffect)){
                        float absorptionAmount = ((PlayerEntity)event.getEntityLiving()).getAbsorptionAmount();
                        if(absorptionAmount+(1+enchantLvl)>=(10+enchantLvl*10)){
                            ((PlayerEntity)event.getEntityLiving()).setAbsorptionAmount((10+enchantLvl*10));
                        }
                        else{
                            ((PlayerEntity)event.getEntityLiving()).setAbsorptionAmount(absorptionAmount+(1+enchantLvl));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doPrototypeChaoticTypeBetaEnchantmentEvent(PotionEvent.PotionAddedEvent event){
        if(event.getEntityLiving() instanceof PlayerEntity){
            if(PlayerUtil.isPlayerSpecificSlotEnchanted(((PlayerEntity)event.getEntityLiving()),EnchantmentRegistry.prototype_chaotic_type_beta_enchantment.get(),EquipmentSlotType.CHEST)){
                if(!(event.getPotionEffect().getPotion() instanceof ImplicitBaseEffect)){
                    if(event.getPotionEffect().getPotion().getEffectType() == EffectType.BENEFICIAL){
                        if(PlayerUtil.isPlayerSpecificSlotEnchanted(((PlayerEntity)event.getEntityLiving()),EnchantmentRegistry.prototype_chaotic_enchantment.get(),EquipmentSlotType.CHEST)){
                            if(!event.getEntityLiving().world.isRemote()){
                                event.getPotionEffect().combine(new EffectInstance(event.getPotionEffect().getPotion(),event.getPotionEffect().getDuration()*3));
                            }
                            List<EffectInstance> negativeEffects= ((PlayerEntity)event.getEntityLiving()).getActivePotionEffects().stream().filter(EffectInstance->
                                    EffectInstance.getPotion().getEffectType() == EffectType.HARMFUL).filter(EffectInstance->
                                    EffectInstance.isCurativeItem(new ItemStack(Items.MILK_BUCKET))).collect(Collectors.toList());
                            if(!negativeEffects.isEmpty()){
                                for(EffectInstance effect : negativeEffects){
                                    ((PlayerEntity)event.getEntityLiving()).removePotionEffect(effect.getPotion());
                                }
                            }
                        }
                        else{
                            if(!event.getEntityLiving().world.isRemote()){
                                event.getPotionEffect().combine(new EffectInstance(event.getPotionEffect().getPotion(),event.getPotionEffect().getDuration()*2));
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void doCorruptedKindredEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                if(!event.isCanceled()){
                    int enchantLvl = PlayerUtil.isPlayerArmorEnchantedWithEnchantmentLevel((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.corrupted_kindred_enchantment.get());
                    if(enchantLvl!=0){
                        Random temp = event.getEntityLiving().getRNG();
                        if(event.getSource().getTrueSource() instanceof ZombieEntity || event.getSource().getTrueSource() instanceof SkeletonEntity){
                            if(temp.nextInt(100)<(4-enchantLvl)){
                                ((PlayerEntity)event.getEntityLiving()).addPotionEffect(new EffectInstance(EffectRegistry.curse_of_undead_effect.get(),144000));
                            }
                            event.setCanceled(true);
                        }else if(event.getSource().getTrueSource() instanceof PhantomEntity || event.getSource().getTrueSource() instanceof WitherSkeletonEntity){
                            if(temp.nextInt(100)<(4-enchantLvl)){
                                ((PlayerEntity)event.getEntityLiving()).addPotionEffect(new EffectInstance(EffectRegistry.curse_of_undead_effect.get(),144000));
                            }
                            event.setAmount(event.getAmount()*(1f-(0.2f+0.1f*enchantLvl)));
                        }else if(event.getSource().getTrueSource() instanceof WitherEntity){
                            if(temp.nextInt(100)<(4-enchantLvl)){
                                ((PlayerEntity)event.getEntityLiving()).addPotionEffect(new EffectInstance(EffectRegistry.curse_of_undead_effect.get(),144000));
                            }
                            event.setAmount(event.getAmount()*(1f-(0.05f+0.05f*enchantLvl)));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doLightburnFungalParasiticEnchantmentEvent_applyProtectionAndSpreadFungalEffect(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                if(!event.isCanceled()) {
                    if (event.getSource().getDamageType().equals("fall")) {
                        if (PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.lightburn_fungal_parasitic_enchantment.get(), EquipmentSlotType.CHEST)) {
                            event.setAmount(event.getAmount() * 0.8f);
                        }
                    } else if (event.getSource().getDamageType().equals("explosion")) {
                        if (PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.lightburn_fungal_parasitic_enchantment.get(), EquipmentSlotType.CHEST)) {
                            event.setAmount(event.getAmount() * 0.8f);
                        }
                    } else if (event.getSource().getDamageType().equals("onFire") || event.getSource().getDamageType().equals("inFire") || event.getSource().getDamageType().equals("lava")) {
                        if (PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.lightburn_fungal_parasitic_enchantment.get(), EquipmentSlotType.CHEST)) {
                            event.setAmount(event.getAmount() * 0.7f);
                        }
                    }
                }
                if(event.getSource().getTrueSource() instanceof LivingEntity){
                    if(PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.lightburn_fungal_parasitic_enchantment.get(),EquipmentSlotType.CHEST)) {
                        List<LivingEntity> targets = PlayerUtil.getTargetList((PlayerEntity) event.getEntityLiving(), 8, 2, x -> true);
                        if (!targets.isEmpty()) {
                            for (LivingEntity target : targets) {
                                target.addPotionEffect(new EffectInstance(EffectRegistry.lightburn_fungal_infection_effect.get(), 200));
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doLightburnFungalParasiticEnchantmentEvent_removeCurrentImmuningEffect(TickEvent.PlayerTickEvent event){
        if(!event.player.world.isRemote()){
            if(PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.player,EnchantmentRegistry.lightburn_fungal_parasitic_enchantment.get(),EquipmentSlotType.CHEST)){
                if(event.player.isPotionActive(Effects.POISON)){
                    event.player.removeActivePotionEffect(Effects.POISON);
                    //Sync to Client
                    Networking.INSTANCE.send(
                            PacketDistributor.PLAYER.with(
                                    () -> (ServerPlayerEntity) event.player
                            ),
                            new RemoveEffectSyncToClientPacket(RemoveEffectSyncToClientPacket.EffectType.POISON));
                }
                if(event.player.isPotionActive(EffectRegistry.lightburn_fungal_infection_effect.get())){
                    event.player.removeActivePotionEffect(EffectRegistry.lightburn_fungal_infection_effect.get());
                    //Sync to Client
                    Networking.INSTANCE.send(
                            PacketDistributor.PLAYER.with(
                                    () -> (ServerPlayerEntity) event.player
                            ),
                            new RemoveEffectSyncToClientPacket(RemoveEffectSyncToClientPacket.EffectType.LIGHTBURN_FUNGAL_INFECTION));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doLightburnFungalParasiticEnchantmentEvent_addImmunity(PotionEvent.PotionApplicableEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                if(PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.lightburn_fungal_parasitic_enchantment.get(),EquipmentSlotType.CHEST)){
                    if(event.getPotionEffect().getPotion().equals(Effects.POISON)){
                        event.setResult(Event.Result.DENY);
                    }
                    if(event.getPotionEffect().getPotion().equals(EffectRegistry.lightburn_fungal_infection_effect.get())){
                        event.setResult(Event.Result.DENY);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doBurialObjectCurseEvent(LivingDeathEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                BlockPos originalDeathPos = event.getEntityLiving().getPosition();
                AxisAlignedBB scanningArea = new AxisAlignedBB(originalDeathPos.getX()-16,originalDeathPos.getY()-1,originalDeathPos.getZ()-16,originalDeathPos.getX()+16,originalDeathPos.getY()+1,originalDeathPos.getZ()+16);
                List<PlayerEntity> players = event.getEntityLiving().world.getEntitiesWithinAABB(PlayerEntity.class,scanningArea);
                for(PlayerEntity player : players){
                    if(PlayerUtil.isPlayerArmorEnchanted(player, EnchantmentRegistry.burial_object_curse.get())){
                        player.attackEntityFrom(CustomDamageSource.causeBurialObjectDamage(((PlayerEntity)event.getEntityLiving())),120);
                    }
                }
            }
        }
    }

}

package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.RemoveEffectSyncToClientPacket;
import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EffectUtil;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import love.marblegate.flowingagony.util.EntityUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
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
    public static void doShadowbornEnchantmentEvent_applyAndRemoveEffect(TickEvent.PlayerTickEvent event){
        if(event.phase == TickEvent.Phase.START){
            if(!event.player.world.isRemote()){
                if (event.player.isPotionActive(Effects.BLINDNESS)) {
                    if (event.player.world.getLight(new BlockPos(event.player.getPosition())) >= 5) {
                        if (EnchantmentUtil.isPlayerItemEnchanted(event.player, EnchantmentRegistry.shadowborn.get(), EquipmentSlotType.HEAD, EnchantmentUtil.ItemEncCalOp.GENERAL)==1) {
                            event.player.removeActivePotionEffect(Effects.BLINDNESS);
                            //Sync to Client
                            Networking.INSTANCE.send(
                                    PacketDistributor.PLAYER.with(
                                            () -> (ServerPlayerEntity) event.player
                                    ),
                                    new RemoveEffectSyncToClientPacket(Effects.BLINDNESS));
                        }
                    }
                }
                if(event.player.world.getLight(new BlockPos(event.player.getPosition()))<=5){
                    if(EnchantmentUtil.isPlayerItemEnchanted(event.player,EnchantmentRegistry.shadowborn.get(), EquipmentSlotType.HEAD, EnchantmentUtil.ItemEncCalOp.GENERAL)==1){
                        if(!event.player.isPotionActive(Effects.NIGHT_VISION)){
                            event.player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION,1200));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doShadowBornEnchantmentEvent_addImmunity(PotionEvent.PotionApplicableEvent event){
        if(!event.getEntityLiving().world.isRemote() && !(event.getResult() == Event.Result.DENY)){
            if(event.getEntityLiving() instanceof PlayerEntity){
                if (EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.shadowborn.get(), EquipmentSlotType.HEAD, EnchantmentUtil.ItemEncCalOp.GENERAL)==1){
                    if (event.getEntityLiving().world.getLight(new BlockPos(event.getEntityLiving().getPosition())) >= 5){
                        if(event.getPotionEffect().getPotion() == Effects.BLINDNESS){
                            event.setResult(Event.Result.DENY);
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
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted(((PlayerEntity)event.getEntityLiving()),EnchantmentRegistry.prototype_chaotic.get(),EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantLvl!=0){
                    if(EffectUtil.isEffectShown(event.getPotionEffect())){
                        if(((PlayerEntity)event.getEntityLiving()).isPotionActive(EffectRegistry.prototype_chaotic_enchantment_active.get())){
                            int newEffectAmplifier = Math.min(((PlayerEntity)event.getEntityLiving()).getActivePotionEffect(EffectRegistry.prototype_chaotic_enchantment_active.get()).getAmplifier()+enchantLvl,29);
                            ((PlayerEntity)event.getEntityLiving()).addPotionEffect(new EffectInstance(EffectRegistry.prototype_chaotic_enchantment_active.get(),1200,newEffectAmplifier));
                        } else {
                            ((PlayerEntity)event.getEntityLiving()).addPotionEffect(new EffectInstance(EffectRegistry.prototype_chaotic_enchantment_active.get(),1200,enchantLvl-1));
                        }
                        ((PlayerEntity)event.getEntityLiving()).heal(enchantLvl);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doPrototypeChaoticTypeBetaEnchantmentEvent(PotionEvent.PotionAddedEvent event){
        if(!event.getEntityLiving().world.isRemote() && event.getEntityLiving() instanceof PlayerEntity){
            if(EnchantmentUtil.isPlayerItemEnchanted(((PlayerEntity)event.getEntityLiving()),EnchantmentRegistry.prototype_chaotic_type_beta.get(),EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.GENERAL)==1){
                if(EffectUtil.isEffectShown(event.getPotionEffect())){
                    if(event.getPotionEffect().getPotion().getEffectType() == EffectType.BENEFICIAL && !event.getPotionEffect().getPotion().isInstant()){
                        if(EnchantmentUtil.isPlayerItemEnchanted(((PlayerEntity)event.getEntityLiving()),EnchantmentRegistry.prototype_chaotic.get(),EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.GENERAL)==1){
                            event.getPotionEffect().combine(new EffectInstance(event.getPotionEffect().getPotion(),event.getPotionEffect().getDuration()*3));
                            List<EffectInstance> negativeEffects = ((PlayerEntity)event.getEntityLiving()).getActivePotionEffects().stream()
                                    .filter(effectInstance-> effectInstance.getPotion().getEffectType() == EffectType.HARMFUL)
                                    .filter(effectInstance-> effectInstance.isCurativeItem(new ItemStack(Items.MILK_BUCKET)))
                                    .filter(EffectUtil::isEffectShown)
                                    .collect(Collectors.toList());
                            if(!negativeEffects.isEmpty()){
                                for(EffectInstance effect : negativeEffects){
                                    ((PlayerEntity)event.getEntityLiving()).removePotionEffect(effect.getPotion());
                                    //Notify client to remove effect
                                    Networking.INSTANCE.send(
                                            PacketDistributor.PLAYER.with(
                                                    () -> (ServerPlayerEntity) event.getEntityLiving()
                                            ),
                                            new RemoveEffectSyncToClientPacket(effect.getPotion()));
                                }
                            }
                        }
                        else{
                            event.getPotionEffect().combine(new EffectInstance(event.getPotionEffect().getPotion(),event.getPotionEffect().getDuration()*2));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void doCorruptedKindredEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity && event.getSource().getTrueSource() instanceof LivingEntity){
                if(!event.isCanceled()){
                    int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.corrupted_kindred.get(),EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                    if(enchantLvl!=0){
                        if(EntityUtil.isAggresiveUndead((LivingEntity) event.getSource().getTrueSource())){
                            Random temp = event.getEntityLiving().getRNG();
                            if(temp.nextInt(100)<(6-enchantLvl)){
                                ((PlayerEntity)event.getEntityLiving()).addPotionEffect(new EffectInstance(EffectRegistry.curse_of_undead.get(),144000));
                            }
                            if(EntityUtil.isCommonUndead((LivingEntity) event.getSource().getTrueSource())){
                                if(enchantLvl==5) event.setCanceled(true);
                                else{
                                    event.setAmount(event.getAmount()*(1f-(0.5f+0.1f*enchantLvl)));
                                }
                            }else if(EntityUtil.isRareUndead((LivingEntity) event.getSource().getTrueSource())){
                                event.setAmount(event.getAmount()*(1f-0.1f*enchantLvl));
                            }else if(event.getSource().getTrueSource() instanceof WitherEntity){
                                if(enchantLvl>1){
                                    event.setAmount(event.getAmount()*(1f-0.05f*(enchantLvl-1)));
                                }
                            }
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
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.lightburn_fungal_parasitic.get(), EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantmentLvl!=0){
                    if(event.getSource().getTrueSource() instanceof LivingEntity){
                        List<LivingEntity> targets = EntityUtil.getTargetsExceptOneself((PlayerEntity) event.getEntityLiving(), 8, 2,
                                Config.VILLAGER_SAFE_MODE.get()?livingEntity -> !(livingEntity instanceof VillagerEntity) : x -> true);
                        if (!targets.isEmpty()) {
                            Random rand = event.getEntityLiving().getRNG();
                            for (LivingEntity target : targets) {
                                if(rand.nextDouble()< 0.125D * (enchantmentLvl + 1))
                                    target.addPotionEffect(new EffectInstance(EffectRegistry.lightburn_fungal_infection.get(), 120));
                            }
                        }
                    }
                    if(!event.isCanceled()) {
                        if (event.getSource().getDamageType().equals("fall")||event.getSource().getDamageType().equals("explosion")||event.getSource().isFireDamage())
                                event.setAmount(event.getAmount() * (1 - 0.05F * (enchantmentLvl + 1)));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doLightburnFungalParasiticEnchantmentEvent_removeCurrentImmuneEffect(TickEvent.PlayerTickEvent event){
        if(!event.player.world.isRemote()){
            if(EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.player,EnchantmentRegistry.lightburn_fungal_parasitic.get(),EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.GENERAL)==1){
                if(event.player.isPotionActive(Effects.POISON)){
                    event.player.removeActivePotionEffect(Effects.POISON);
                    //Sync to Client
                    Networking.INSTANCE.send(
                            PacketDistributor.PLAYER.with(
                                    () -> (ServerPlayerEntity) event.player
                            ),
                            new RemoveEffectSyncToClientPacket(Effects.POISON));
                }
                if(event.player.isPotionActive(EffectRegistry.lightburn_fungal_infection.get())){
                    event.player.removeActivePotionEffect(EffectRegistry.lightburn_fungal_infection.get());
                    //Sync to Client
                    Networking.INSTANCE.send(
                            PacketDistributor.PLAYER.with(
                                    () -> (ServerPlayerEntity) event.player
                            ),
                            new RemoveEffectSyncToClientPacket(EffectRegistry.lightburn_fungal_infection.get()));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doLightburnFungalParasiticEnchantmentEvent_addImmunity(PotionEvent.PotionApplicableEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                if(EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.lightburn_fungal_parasitic.get(),EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.GENERAL)==1){
                    if(event.getPotionEffect().getPotion().equals(Effects.POISON)){
                        event.setResult(Event.Result.DENY);
                    }
                    if(event.getPotionEffect().getPotion().equals(EffectRegistry.lightburn_fungal_infection.get())){
                        event.setResult(Event.Result.DENY);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doScholarOfOriginalSinEnchantmentEvent_addWeakness(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity && !event.isCanceled() && event.getSource() != DamageSource.OUT_OF_WORLD){
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.scholar_of_original_sin.get(),EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantmentLvl!=0){
                    float extraDamage = Math.min(event.getAmount() * (1.1F - 0.1F * enchantmentLvl),10);
                    event.setAmount(event.getAmount()+extraDamage);
                }
            }
        }
    }

    @SubscribeEvent
    public static void doScholarOfOriginalSinEnchantmentEvent_extendHarmfulEffect(PotionEvent.PotionAddedEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.scholar_of_original_sin.get(),EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantmentLvl!=0){
                    if(event.getPotionEffect().getPotion().getEffectType()==EffectType.HARMFUL&&EffectUtil.isEffectShown(event.getPotionEffect()))
                        event.getPotionEffect().combine(new EffectInstance(event.getPotionEffect().getPotion(), (int) (event.getPotionEffect().getDuration()*(2.1-0.1*enchantmentLvl))));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doScholarOfOriginalSinEnchantmentEvent_extraEXP(PlayerXpEvent.PickupXp event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.scholar_of_original_sin.get(),EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantmentLvl!=0){
                    event.getOrb().xpValue = (int) (event.getOrb().xpValue * (1.35 + 0.15 * enchantmentLvl));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doOriginalSinErosionEnchantmentEvent_decreaseAttack(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getSource().getTrueSource() ,EnchantmentRegistry.original_sin_erosion.get(),EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantmentLvl!=0){
                    float damageModified = Math.max(event.getAmount() - 5 + enchantmentLvl,0);
                    event.setAmount(damageModified);
                }
            }
        }
    }

    @SubscribeEvent
    public static void doOriginalSinErosionEnchantmentEvent_extraEXP(PlayerXpEvent.PickupXp event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.original_sin_erosion.get(),EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantmentLvl!=0){
                    event.getOrb().xpValue = (int) (event.getOrb().xpValue * (1.05 + 0.05 * enchantmentLvl));
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
                    if(EnchantmentUtil.isPlayerArmorEnchanted(player, EnchantmentRegistry.burial_object.get(), EnchantmentUtil.ArmorEncCalOp.GENERAL)==1){
                        player.attackEntityFrom(CustomDamageSource.causeBurialObjectDamage(((PlayerEntity)event.getEntityLiving())),120);
                    }
                }
            }
        }
    }

}

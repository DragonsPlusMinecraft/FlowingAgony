package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import love.marblegate.flowingagony.util.EntityUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber()
public class SurvivalTricksEnchantmentEventHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void doSurvivalShortcutEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(!event.isCanceled() && event.getSource() != DamageSource.OUT_OF_WORLD) {
                if (event.getEntityLiving() instanceof PlayerEntity) {
                    int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.survival_shortcut.get(),EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                    if(enchantmentLvl!=0){
                        if(event.getSource().getTrueSource() instanceof PlayerEntity){
                            event.setAmount(event.getAmount() * (0.95F - 0.05F * enchantmentLvl));
                        } else {
                            if (!(event.getAmount() < 9 - enchantmentLvl)){
                                List<LivingEntity> entities = EntityUtil.getTargetsExceptOneself((PlayerEntity) event.getEntityLiving(), 16, 2,
                                        Config.VILLAGER_SAFE_MODE.get() ? livingEntity -> !EntityUtil.isHostile(livingEntity,false) && !(livingEntity instanceof VillagerEntity) : livingEntity -> !EntityUtil.isHostile(livingEntity,false));
                                damageTransfer(entities, event, enchantmentLvl, false);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void doSurvivalSRuseEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(!event.isCanceled() && event.getSource() != DamageSource.OUT_OF_WORLD) {
                if (event.getEntityLiving() instanceof PlayerEntity) {
                    int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.survival_ruse.get(),EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                    if(enchantmentLvl!=0){
                        if(event.getSource().getTrueSource() instanceof PlayerEntity){
                            event.setAmount(event.getAmount() * (0.95F - 0.05F * enchantmentLvl));
                        } else {
                            if (!(event.getAmount() < 9 - enchantmentLvl)){
                                List<LivingEntity> entities = EntityUtil.getTargetsExceptOneself((PlayerEntity) event.getEntityLiving(), 16, 2, livingEntity -> EntityUtil.isHostile(livingEntity,false) );
                                damageTransfer(entities, event, enchantmentLvl, false);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void doNecessaryEvilEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(!event.isCanceled() && event.getSource() != DamageSource.OUT_OF_WORLD) {
                if (event.getEntityLiving() instanceof PlayerEntity) {
                    int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.necessary_evil.get(),EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                    if(enchantmentLvl!=0){
                        if(event.getSource().getTrueSource() instanceof PlayerEntity){
                            event.setAmount(event.getAmount() * (0.85F - 0.05F * enchantmentLvl));
                        } else {
                            if (!(event.getAmount() < 13 - enchantmentLvl)){
                                List<LivingEntity> entities = EntityUtil.getTargetsExceptOneself((PlayerEntity) event.getEntityLiving(), 16, 2,
                                        Config.VILLAGER_SAFE_MODE.get() ? livingEntity -> !(livingEntity instanceof PlayerEntity) && !(livingEntity instanceof VillagerEntity) : livingEntity ->!(livingEntity instanceof PlayerEntity));
                                damageTransfer(entities, event, enchantmentLvl, true);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void damageTransfer(List<LivingEntity> entities, LivingDamageEvent event,int lvl, boolean shareMode) {
        if(!entities.isEmpty()){
            if(shareMode){
                float damageSharedPerEntity = event.getAmount()/entities.size();
                for(LivingEntity entity: entities){
                    entity.attackEntityFrom(DamageSource.GENERIC,damageSharedPerEntity);
                }
            }else{
                LivingEntity target;
                if(entities.size()==1)
                    target = entities.get(0);
                else{
                    target = getLuckyOne(entities,event.getEntityLiving().getRNG());
                }
                target.attackEntityFrom(DamageSource.GENERIC,event.getAmount());
            }
            event.setCanceled(true);
        } else {
            if(event.getSource().getDamageType().equals("explosion")){
                if(shareMode) event.setAmount(event.getAmount()*(0.7F - lvl * 0.1F));
                else event.setAmount(event.getAmount()*(0.9F - lvl * 0.1F));
            } else {
                if(shareMode) event.setAmount(event.getAmount()*(0.85F - lvl * 0.05F));
                else event.setAmount(event.getAmount()*(0.95F - lvl * 0.05F));
            }
        }
    }

    public static LivingEntity getLuckyOne(List<LivingEntity> entities, Random random){
        return entities.get(random.nextInt(entities.size()));
    }
}

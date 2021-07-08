package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EntityUtil;
import love.marblegate.flowingagony.util.GodRollingDiceUtil;
import love.marblegate.flowingagony.util.PlayerUtil;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber()
public class SurvivalTricksEnchantmentEventHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void doSurvivalShortcutEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(!event.isCanceled() && event.getSource() != DamageSource.OUT_OF_WORLD) {
                if (event.getEntityLiving() instanceof PlayerEntity) {
                    int enchantmentLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.survival_shortcut.get(),EquipmentSlotType.CHEST);
                    if(enchantmentLvl!=0){
                        if(event.getSource().getTrueSource() instanceof PlayerEntity){
                            event.setAmount(event.getAmount() * (0.95F - 0.05F * enchantmentLvl));
                        } else {
                            if (!(event.getAmount() < 9 - enchantmentLvl)){
                                List<LivingEntity> entities = PlayerUtil.getTargetList((PlayerEntity) event.getEntityLiving(), 16, 2, livingEntity -> !EntityUtil.isHostile(livingEntity,false) );
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
                    int enchantmentLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.survival_ruse.get(),EquipmentSlotType.CHEST);
                    if(enchantmentLvl!=0){
                        if(event.getSource().getTrueSource() instanceof PlayerEntity){
                            event.setAmount(event.getAmount() * (0.95F - 0.05F * enchantmentLvl));
                        } else {
                            if (!(event.getAmount() < 9 - enchantmentLvl)){
                                List<LivingEntity> entities = PlayerUtil.getTargetList((PlayerEntity) event.getEntityLiving(), 16, 2, livingEntity -> EntityUtil.isHostile(livingEntity,false) );
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
                    int enchantmentLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.necessary_evil.get(),EquipmentSlotType.CHEST);
                    if(enchantmentLvl!=0){
                        if(event.getSource().getTrueSource() instanceof PlayerEntity){
                            event.setAmount(event.getAmount() * (0.85F - 0.05F * enchantmentLvl));
                        } else {
                            if (!(event.getAmount() < 13 - enchantmentLvl)){
                                List<LivingEntity> entities = PlayerUtil.getTargetList((PlayerEntity) event.getEntityLiving(), 16, 2, livingEntity->!(livingEntity instanceof PlayerEntity));
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
                    target = GodRollingDiceUtil.getLuckyOne(entities,event.getEntityLiving().getRNG());
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
}

package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.GodRollingDiceUtil;
import love.marblegate.flowingagony.util.ItemUtil;
import love.marblegate.flowingagony.util.PlayerUtil;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber()
public class SurvivalTricksEnchantmentEventHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void doSurvivalShortcutEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(!event.isCanceled()) {
                if (event.getEntityLiving() instanceof PlayerEntity) {
                    if (event.getAmount() > 6f) {
                        if (PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.survival_shortcut_enchantment.get(), EquipmentSlotType.CHEST)) {
                            List<LivingEntity> entities = PlayerUtil.getTargetList((PlayerEntity) event.getEntityLiving(), 16, 2, LivingEntity -> !((LivingEntity instanceof MonsterEntity) || (LivingEntity instanceof FlyingEntity) || (LivingEntity instanceof SlimeEntity) || (LivingEntity instanceof EnderDragonEntity)));
                            damageTransfer(entities, event, false);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void doSurvivalSRuseEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(!event.isCanceled()) {
                if (event.getEntityLiving() instanceof PlayerEntity) {
                    if (event.getAmount() > 6f) {
                        if (PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.survival_ruse_enchantment.get(), EquipmentSlotType.CHEST)) {
                            List<LivingEntity> entities = PlayerUtil.getTargetList((PlayerEntity) event.getEntityLiving(), 16, 2, LivingEntity -> ((LivingEntity instanceof MonsterEntity) || (LivingEntity instanceof FlyingEntity) || (LivingEntity instanceof SlimeEntity) || (LivingEntity instanceof EnderDragonEntity)));
                            damageTransfer(entities, event, false);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void doNecessaryEvilEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity) {
                if (!event.isCanceled()) {
                    if (event.getAmount() > 10f) {
                        if (PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.necessary_evil_enchantment.get(), EquipmentSlotType.CHEST)) {
                            List<LivingEntity> entities = PlayerUtil.getTargetList((PlayerEntity) event.getEntityLiving(), 16, 2, LivingEntity -> !(LivingEntity instanceof PlayerEntity));
                            damageTransfer(entities, event, true);
                        }
                    }
                }
            }
        }
    }

    private static void damageTransfer(List<LivingEntity> entities, LivingDamageEvent event, boolean advancedMode) {
        if(!entities.isEmpty()){
            LivingEntity target;
            if(advancedMode){
                for(LivingEntity entity: entities){
                    entity.attackEntityFrom(DamageSource.GENERIC.setDamageBypassesArmor(),event.getAmount());
                }
            }else{
                if(entities.size()==1)
                    target = entities.get(0);
                else{
                    target = GodRollingDiceUtil.getLuckyOne(entities,event.getEntityLiving().getRNG());
                }
            }
            event.setCanceled(true);
        } else {
            if(event.getSource().getDamageType().equals("explosion")){
                if(advancedMode) event.setAmount(event.getAmount()*0.25f);
                else event.setAmount(event.getAmount()*0.5f);
            } else {
                if(advancedMode) event.setAmount(event.getAmount()*0.5f);
                else event.setAmount(event.getAmount()*0.75f);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void doMorirsDeathwishEnchantmentEvent_mendOnHurt(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(!event.isCanceled()){
                if(event.getEntityLiving() instanceof PlayerEntity){
                    List<ItemStack> items = PlayerUtil.getItemStackWithCertainEnchantment((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.morirs_deathwish_enchantment.get());
                    for(ItemStack item: items){
                        item.setDamage(item.getDamage()-3*MathHelper.floor(event.getAmount()));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doMorirsDeathwishEnchantmentEvent_mendOnDeath(LivingDeathEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(!event.isCanceled()){
                if(event.getEntityLiving() instanceof PlayerEntity){
                    List<ItemStack> items = PlayerUtil.getItemStackWithCertainEnchantment((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.morirs_deathwish_enchantment.get());
                    for(ItemStack item: items){
                        item.setDamage(item.getDamage()-64);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doMorirsLifeboundEnchantmentEvent_mendOnHeal(LivingHealEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(!event.isCanceled()){
                if(event.getEntityLiving() instanceof PlayerEntity){
                    List<ItemStack> items = PlayerUtil.getItemStackWithCertainEnchantment((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.morirs_lifebound_enchantment.get());
                    for(ItemStack item: items){
                        item.setDamage(item.getDamage()-10*MathHelper.floor(event.getAmount()));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doMorirsLifeBoundEnchantmentEvent_damageOnDeath(LivingDeathEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(!event.isCanceled()){
                if(event.getEntityLiving() instanceof PlayerEntity){
                    List<ItemStack> items = PlayerUtil.getItemStackWithCertainEnchantment((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.morirs_lifebound_enchantment.get());
                    for(ItemStack item: items){
                        item.damageItem(64,event.getEntityLiving(),x->{});
                    }
                }
            }
        }
    }

}

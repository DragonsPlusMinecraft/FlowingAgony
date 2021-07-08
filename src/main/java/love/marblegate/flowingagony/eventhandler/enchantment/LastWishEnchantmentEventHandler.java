package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.GodRollingDiceUtil;
import love.marblegate.flowingagony.util.PlayerUtil;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber()
public class LastWishEnchantmentEventHandler {

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

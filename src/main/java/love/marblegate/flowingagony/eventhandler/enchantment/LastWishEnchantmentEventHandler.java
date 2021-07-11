package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.capibility.cooldown.CoolDown;
import love.marblegate.flowingagony.capibility.cooldown.CoolDownType;
import love.marblegate.flowingagony.capibility.cooldown.ICoolDown;
import love.marblegate.flowingagony.capibility.lastsweetdream.ILastSweetDreamCapability;
import love.marblegate.flowingagony.capibility.lastsweetdream.LastSweetDreamCapability;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
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
                    List<ItemStack> items = EnchantmentUtil.getItemStackWithEnchantment((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.morirs_deathwish.get());
                    for(ItemStack item: items){
                        int repairPoint = 0;
                        for(int i=0;i<Math.floor(event.getAmount());i++)
                            repairPoint += 1 + event.getEntityLiving().getRNG().nextInt(3);
                        item.setDamage(item.getDamage() - repairPoint);
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
                    List<ItemStack> items = EnchantmentUtil.getItemStackWithEnchantment((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.morirs_deathwish.get());
                    if(!items.isEmpty()){
                        LazyOptional<ICoolDown> coolDownCap = event.getEntityLiving().getCapability(CoolDown.COOL_DOWN_CAPABILITY);
                        coolDownCap.ifPresent(
                                cap -> {
                                    if(cap.isReady(CoolDownType.MORIRS_DEATHWISH_DEATHMENDING)){
                                        for(ItemStack item: items){
                                            item.setDamage(item.getDamage() - 64);
                                        }
                                        cap.set(CoolDownType.MORIRS_DEATHWISH_DEATHMENDING,12000);
                                    }
                        });
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
                    List<ItemStack> items = EnchantmentUtil.getItemStackWithEnchantment((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.morirs_lifebound.get());
                    for(ItemStack item: items){
                        int repairPoint = 0;
                        for(int i=0;i<Math.floor(event.getAmount());i++)
                            repairPoint += 1 + event.getEntityLiving().getRNG().nextInt(10);
                        item.setDamage(item.getDamage() - repairPoint);
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
                    List<ItemStack> items = EnchantmentUtil.getItemStackWithEnchantment((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.morirs_lifebound.get());
                    for(ItemStack item: items){
                        item.damageItem(32,event.getEntityLiving(),x->{});
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doGuidensRegretEnchantmentEvent(LivingDeathEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(!event.isCanceled()){
                if(event.getSource().getTrueSource() instanceof PlayerEntity){
                    List<ItemStack> items = EnchantmentUtil.getItemStackWithEnchantment((PlayerEntity) event.getSource().getTrueSource(),EnchantmentRegistry.guidens_regret.get());
                    for(ItemStack item: items){
                        int repairPoint = 1 + event.getEntityLiving().getRNG().nextInt(3);
                        item.setDamage(item.getDamage() - repairPoint);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doLastSweetDreamEnchantmentEvent_saveItem(ItemTossEvent event){
        if(!event.getPlayer().world.isRemote()){
            if(!event.isCanceled()){
                System.out.print((float)event.getEntityItem().getItem().getDamage()/event.getEntityItem().getItem().getMaxDamage()+"\n");
                if(EnchantmentUtil.isItemEnchanted(event.getEntityItem().getItem(),EnchantmentRegistry.last_sweet_dream.get()) ==1 && (float)event.getEntityItem().getItem().getDamage()/event.getEntityItem().getItem().getMaxDamage() > 0.9F){
                    LazyOptional<ILastSweetDreamCapability> itemCap = event.getPlayer().getCapability(LastSweetDreamCapability.LAST_SWEET_DREAM_CAPABILITY);
                    itemCap.ifPresent(
                            cap -> {
                                if(cap.isEmpty()){
                                    cap.saveItemStack(event.getEntityItem().getItem());
                                } else {
                                    ItemStack oldItem = cap.getItemStack();
                                    cap.clear();
                                    cap.saveItemStack(event.getEntityItem().getItem());
                                    InventoryHelper.spawnItemStack(event.getPlayer().world,event.getPlayer().getPosX(),event.getPlayer().getPosY()+2,event.getPlayer().getPosZ(),oldItem);
                                }
                                event.setCanceled(true);
                            }
                    );
                }
            }
        }
    }

    @SubscribeEvent
    public static void doLastSweetDreamEnchantmentEvent_retrieveItem(PlayerWakeUpEvent event){
        if(!event.getPlayer().world.isRemote()){
            LazyOptional<ILastSweetDreamCapability> itemCap = event.getPlayer().getCapability(LastSweetDreamCapability.LAST_SWEET_DREAM_CAPABILITY);
            itemCap.ifPresent(
                    cap -> {
                        if(!cap.isEmpty()){
                            ItemStack savedItem = cap.getItemStack();
                            savedItem.setDamage(0);
                            InventoryHelper.spawnItemStack(event.getPlayer().world,event.getPlayer().getPosX(),event.getPlayer().getPosY()+2,event.getPlayer().getPosZ(),savedItem);
                            cap.clear();
                        }
                    }
            );
        }
    }

}

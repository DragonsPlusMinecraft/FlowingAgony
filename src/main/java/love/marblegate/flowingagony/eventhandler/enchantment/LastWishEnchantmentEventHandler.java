package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.capibility.CapabilityManager;
import love.marblegate.flowingagony.capibility.CoolDown;
import love.marblegate.flowingagony.capibility.LastSweetDreamCapability;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.damagesource.DamageSource;
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
    public static void doMorirsDeathwishEnchantmentEvent_mendOnHurt(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (!event.isCanceled()) {
                if (event.getEntityLiving() instanceof Player && event.getSource() != DamageSource.OUT_OF_WORLD) {
                    List<ItemStack> items = EnchantmentUtil.getItemStackWithEnchantment((Player) event.getEntityLiving(), EnchantmentRegistry.MORIRS_DEATHWISH.get());
                    for (ItemStack item : items) {
                        int repairPoint = 0;
                        if (event.getAmount() < 1) {
                            repairPoint += Math.floor(event.getAmount() * event.getEntityLiving().getRandom().nextInt(3));
                        } else {
                            //I really did not expect how could someone applies damage more than 100!!!!!
                            int temp = (int) Math.max(Math.floor(event.getAmount()), 100);
                            for (int i = 0; i < temp; i++)
                                repairPoint += 1 + event.getEntityLiving().getRandom().nextInt(3);
                        }
                        item.setDamageValue(item.getDamageValue() - repairPoint);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doMorirsDeathwishEnchantmentEvent_mendOnDeath(LivingDeathEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (!event.isCanceled()) {
                if (event.getEntityLiving() instanceof Player) {
                    List<ItemStack> items = EnchantmentUtil.getItemStackWithEnchantment((Player) event.getEntityLiving(), EnchantmentRegistry.MORIRS_DEATHWISH.get());
                    if (!items.isEmpty()) {
                        LazyOptional<CoolDown> coolDownCap = event.getEntityLiving().getCapability(CapabilityManager.COOL_DOWN_CAPABILITY);
                        coolDownCap.ifPresent(
                                cap -> {
                                    if (cap.isReady(CoolDown.CoolDownType.MORIRS_DEATHWISH_DEATHMENDING)) {
                                        for (ItemStack item : items) {
                                            item.setDamageValue(item.getDamageValue() - 64);
                                        }
                                        cap.set(CoolDown.CoolDownType.MORIRS_DEATHWISH_DEATHMENDING, 12000);
                                    }
                                });
                    }

                }
            }
        }
    }

    @SubscribeEvent
    public static void doMorirsLifeboundEnchantmentEvent_mendOnHeal(LivingHealEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (!event.isCanceled()) {
                if (event.getEntityLiving() instanceof Player) {
                    List<ItemStack> items = EnchantmentUtil.getItemStackWithEnchantment((Player) event.getEntityLiving(), EnchantmentRegistry.MORIRS_LIFEBOUND.get());
                    for (ItemStack item : items) {
                        int repairPoint = 0;
                        if (event.getAmount() < 1) {
                            repairPoint += Math.floor(event.getAmount() * event.getEntityLiving().getRandom().nextInt(3));
                        } else {
                            //It's the same as above
                            int temp = (int) Math.max(Math.floor(event.getAmount()), 100);
                            for (int i = 0; i < temp; i++)
                                repairPoint += 1 + event.getEntityLiving().getRandom().nextInt(10);
                        }
                        item.setDamageValue(item.getDamageValue() - repairPoint);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doMorirsLifeBoundEnchantmentEvent_damageOnDeath(LivingDeathEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (!event.isCanceled()) {
                if (event.getEntityLiving() instanceof Player) {
                    List<ItemStack> items = EnchantmentUtil.getItemStackWithEnchantment((Player) event.getEntityLiving(), EnchantmentRegistry.MORIRS_LIFEBOUND.get());
                    for (ItemStack item : items) {
                        item.hurtAndBreak(32, event.getEntityLiving(), x -> {
                        });
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doGuidensRegretEnchantmentEvent(LivingDeathEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (!event.isCanceled()) {
                if (event.getSource().getEntity() instanceof Player) {
                    List<ItemStack> items = EnchantmentUtil.getItemStackWithEnchantment((Player) event.getSource().getEntity(), EnchantmentRegistry.GUIDENS_REGRET.get());
                    for (ItemStack item : items) {
                        int repairPoint = 1 + event.getEntityLiving().getRandom().nextInt(3);
                        item.setDamageValue(item.getDamageValue() - repairPoint);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doLastSweetDreamEnchantmentEvent_saveItem(ItemTossEvent event) {
        if (!event.getPlayer().level.isClientSide()) {
            if (!event.isCanceled()) {
                if (EnchantmentUtil.isItemEnchanted(event.getEntityItem().getItem(), EnchantmentRegistry.LAST_SWEET_DREAM.get()) == 1 && event.getEntityItem().getItem().isDamageableItem()) {
                    if ((float) event.getEntityItem().getItem().getDamageValue() / event.getEntityItem().getItem().getMaxDamage() > 0.9F) {
                        LazyOptional<LastSweetDreamCapability> itemCap = event.getPlayer().getCapability(CapabilityManager.LAST_SWEET_DREAM_CAPABILITY);
                        itemCap.ifPresent(
                                cap -> {
                                    if (cap.isEmpty()) {
                                        cap.saveItemStack(event.getEntityItem().getItem());
                                    } else {
                                        ItemStack oldItem = cap.getItemStack();
                                        cap.clear();
                                        cap.saveItemStack(event.getEntityItem().getItem());
                                        Containers.dropItemStack(event.getPlayer().level, event.getPlayer().getX(), event.getPlayer().getY() + 2, event.getPlayer().getZ(), oldItem);
                                    }
                                    event.setCanceled(true);
                                }
                        );
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doLastSweetDreamEnchantmentEvent_retrieveItem(PlayerWakeUpEvent event) {
        if (!event.getPlayer().level.isClientSide()) {
            LazyOptional<LastSweetDreamCapability> itemCap = event.getPlayer().getCapability(CapabilityManager.LAST_SWEET_DREAM_CAPABILITY);
            itemCap.ifPresent(
                    cap -> {
                        if (!cap.isEmpty()) {
                            ItemStack savedItem = cap.getItemStack();
                            savedItem.setDamageValue(0);
                            Containers.dropItemStack(event.getPlayer().level, event.getPlayer().getX(), event.getPlayer().getY() + 2, event.getPlayer().getZ(), savedItem);
                            cap.clear();
                        }
                    }
            );
        }
    }

}

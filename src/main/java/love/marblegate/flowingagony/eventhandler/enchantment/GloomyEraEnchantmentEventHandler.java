package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.GodRollingDiceUtil;
import love.marblegate.flowingagony.util.ItemUtil;
import love.marblegate.flowingagony.util.PlayerUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber()
public class GloomyEraEnchantmentEventHandler {
    @SubscribeEvent
    public static void doRegularCustomerProgramEnchanemtnetEvent(LivingDeathEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getSource().getTrueSource() instanceof PlayerEntity) {
                if (((PlayerEntity) (event.getSource().getTrueSource())).getHeldItemMainhand().getItem() instanceof SwordItem || ((PlayerEntity) (event.getSource().getTrueSource())).getHeldItemMainhand().getItem() instanceof AxeItem) {
                    if (PlayerUtil.isPlayerSpecificSlotEnchanted(((PlayerEntity) (event.getSource().getTrueSource())), EnchantmentRegistry.regular_customer_program_enchantment.get(), EquipmentSlotType.MAINHAND)) {
                        CompoundNBT weaponNBT = ((PlayerEntity) (event.getSource().getTrueSource())).getHeldItemMainhand().getTag();
                        if (!weaponNBT.contains("regular_customer_program_target")) {
                            weaponNBT.putString("regular_customer_program_target", event.getEntityLiving().getEntityString());
                        } else {
                            String recordedTarget = weaponNBT.getString("regular_customer_program_target");
                            if (recordedTarget.equals(event.getEntityLiving().getEntityString())) {
                                ItemStack reward = new ItemStack(Items.GOLD_NUGGET);
                                if (event.getEntityLiving() instanceof MonsterEntity)
                                    reward.setCount(event.getEntityLiving().getRNG().nextInt(12) + 1);
                                else reward.setCount(event.getEntityLiving().getRNG().nextInt(6) + 1);
                                event.getEntityLiving().world.addEntity(new ItemEntity(event.getEntityLiving().world,
                                        event.getEntityLiving().getPosX(), event.getEntityLiving().getPosY(),
                                        event.getEntityLiving().getPosZ(), reward));
                            } else {
                                weaponNBT.putString("regular_customer_program_target", event.getEntityLiving().getEntityString());
                            }
                        }
                        ((PlayerEntity) (event.getSource().getTrueSource())).getHeldItemMainhand().setTag(weaponNBT);
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public static void doCleansingBeforeUsingEnchantmentEvent_updateResult(AnvilUpdateEvent event) {
        if(!Config.HYBRID_SERVER_USER.get()){
            if (!event.getPlayer().world.isRemote()) {
                if (ItemUtil.isItemEnchanted(event.getLeft(), EnchantmentRegistry.cleansing_before_using_enchantment.get())
                        && event.getLeft().getDamage() == 0 && event.getLeft().getItem().equals(event.getRight().getItem())) {
                    event.setCost(1);
                    ItemStack result = event.getLeft().copy();
                    Map<Enchantment, Integer> left = EnchantmentHelper.getEnchantments(result);
                    left.remove(EnchantmentRegistry.cleansing_before_using_enchantment.get());
                    Map<Enchantment, Integer> right = EnchantmentHelper.getEnchantments(event.getRight().copy());
                    boolean goVanillaForConflict = false;
                    for (Enchantment lEnchantment : left.keySet()) {
                        for (Enchantment rEnchantment : right.keySet()) {
                            if (!lEnchantment.isCompatibleWith(rEnchantment)) {
                                if (!lEnchantment.equals(rEnchantment)) {
                                    goVanillaForConflict = true;
                                    break;
                                }
                            }
                        }
                        if (goVanillaForConflict) break;
                    }
                    if (!goVanillaForConflict) {
                        right.forEach(((enchantment, integer) -> {
                            left.merge(enchantment, integer, (oldV, newV) -> {
                                if (oldV < newV) return newV;
                                else if (oldV == newV) {
                                    if (oldV < enchantment.getMaxLevel()) return oldV + 1;
                                }
                                return oldV;
                            });
                        }));
                        EnchantmentHelper.setEnchantments(left, result);
                        event.setOutput(result);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doComeBackAtDuskEnchantmentEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (!event.player.world.isRemote()) {
                if (!PlayerUtil.isPlayerArmorEnchanted(event.player, EnchantmentRegistry.dirty_money_enchantment.get())) {
                    if (event.player.world.getDayTime() % 24000 > 10999 && event.player.world.getDayTime() % 24000 < 13501) {
                        if (!event.player.isPotionActive(Effects.HERO_OF_THE_VILLAGE)) {
                            if (PlayerUtil.isPlayerArmorEnchanted(event.player, EnchantmentRegistry.come_back_at_dusk_enchantment.get())) {
                                event.player.addPotionEffect(new EffectInstance(Effects.HERO_OF_THE_VILLAGE, 300, event.player.getRNG().nextInt(3) + 1));
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doDirtyMoneyEnchantmentEvent_preventHOTVEffect(PotionEvent.PotionApplicableEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                if (event.getPotionEffect().getPotion().equals(Effects.HERO_OF_THE_VILLAGE)) {
                    if (PlayerUtil.isPlayerArmorEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.dirty_money_enchantment.get())) {
                        event.setResult(Event.Result.DENY);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doDirtyMoneyEnchantmentEvent_preventHOTVEffect(LivingDeathEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof VillagerEntity) {
                if (event.getSource().getTrueSource() instanceof PlayerEntity) {
                    int enchantLvl = PlayerUtil.getHighestLevelPlayerArmorEnchantedSameEnchantment((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.dirty_money_enchantment.get());
                    if (enchantLvl != 0) {
                        ItemStack reward_emerald = new ItemStack(Items.EMERALD);
                        ItemStack reward_gold = new ItemStack(Items.GOLD_INGOT);
                        reward_emerald.setCount(event.getEntityLiving().getRNG().nextInt(2) + enchantLvl - 1);
                        reward_gold.setCount(event.getEntityLiving().getRNG().nextInt(enchantLvl + 1));
                        event.getEntityLiving().world.addEntity(new ItemEntity(event.getEntityLiving().world,
                                event.getEntityLiving().getPosX(), event.getEntityLiving().getPosY(),
                                event.getEntityLiving().getPosZ(), reward_emerald));
                        event.getEntityLiving().world.addEntity(new ItemEntity(event.getEntityLiving().world,
                                event.getEntityLiving().getPosX(), event.getEntityLiving().getPosY(),
                                event.getEntityLiving().getPosZ(), reward_gold));
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void doPilferageCreedEnchantmentEvent(LivingFallEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                if (PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.pilferage_creed_enchantment.get(), EquipmentSlotType.FEET)) {
                    if (event.getDistance() >= 5.0f) {
                        List<LivingEntity> targets = PlayerUtil.getTargetList((PlayerEntity) event.getEntityLiving(), 5, 1, LivingEntity -> LivingEntity instanceof VillagerEntity);
                        if (!targets.isEmpty()) {
                            targets.forEach(LivingEntity -> {
                                List<MerchantOffer> offers = ((VillagerEntity) LivingEntity).getOffers();
                                if (!offers.isEmpty()) {
                                    List<ItemStack> outcome = GodRollingDiceUtil.rollDiceForPilferage(((PlayerEntity)event.getEntityLiving()).getItemStackFromSlot(EquipmentSlotType.FEET),(VillagerEntity) LivingEntity, offers, event.getEntityLiving().getRNG(), event.getDistance());
                                    outcome.forEach(ItemStack -> {
                                        LivingEntity.world.addEntity(new ItemEntity(LivingEntity.world,
                                                LivingEntity.getPosX(), LivingEntity.getPosY(),
                                                LivingEntity.getPosZ(), ItemStack));
                                    });
                                }
                            });
                        }
                    }
                }
            }
        }
    }
}


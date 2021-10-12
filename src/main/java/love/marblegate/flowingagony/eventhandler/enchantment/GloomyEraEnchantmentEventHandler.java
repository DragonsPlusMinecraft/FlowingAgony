package love.marblegate.flowingagony.eventhandler.enchantment;

import com.google.common.collect.Maps;
import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import love.marblegate.flowingagony.util.EntityUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Mod.EventBusSubscriber()
public class GloomyEraEnchantmentEventHandler {
    @SubscribeEvent
    public static void doRegularCustomerProgramEnchantmentEvent(LivingDeathEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player && (event.getEntityLiving() instanceof ZombieVillager || event.getEntityLiving() instanceof Witch)) {
                if (EnchantmentUtil.isPlayerItemEnchanted((Player) event.getSource().getEntity(), EnchantmentRegistry.REGULAR_CUSTOMER_PROGRAM.get(), EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                    List<LivingEntity> targets = EntityUtil.getTargetsExceptOneself(event.getEntityLiving(), 12, 2, livingEntity -> livingEntity instanceof Villager);
                    if (!targets.isEmpty()) {
                        for (LivingEntity target : targets) {
                            ((Villager) target).getGossips().add(((Player) event.getSource().getEntity()).getUUID(), GossipType.MINOR_POSITIVE, 1);
                        }
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public static void doCleansingBeforeUsingEnchantmentEvent(LivingDeathEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player && event.getEntityLiving() instanceof Villager) {
                if (EnchantmentUtil.isPlayerItemEnchanted((Player) event.getSource().getEntity(), EnchantmentRegistry.CLEANSING_BEFORE_USING.get(), EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                    event.getEntityLiving().getItemInHand(InteractionHand.MAIN_HAND).setRepairCost(0);
                    if (event.getEntityLiving().getItemInHand(InteractionHand.MAIN_HAND).isDamageableItem()) {
                        event.getEntityLiving().getItemInHand(InteractionHand.MAIN_HAND).setDamageValue(event.getEntityLiving().getItemInHand(InteractionHand.MAIN_HAND).getDamageValue() - 10);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doComeBackAtDuskEnchantmentEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (!event.player.level.isClientSide()) {
                if (EnchantmentUtil.isPlayerArmorEnchanted(event.player, EnchantmentRegistry.DIRTY_MONEY.get(), EnchantmentUtil.ArmorEncCalOp.GENERAL) == 0) {
                    if (event.player.level.getDayTime() % 24000 > 10999 && event.player.level.getDayTime() % 24000 < 13501) {
                        if (EnchantmentUtil.isPlayerArmorEnchanted(event.player, EnchantmentRegistry.COME_BACK_AT_DUSK.get(), EnchantmentUtil.ArmorEncCalOp.GENERAL) == 1) {
                            if (!event.player.hasEffect(MobEffects.HERO_OF_THE_VILLAGE)) {
                                int amplifier;
                                double temp = Math.random();
                                if (temp < 0.9) amplifier = 0;
                                else if (temp < 0.95) amplifier = 1;
                                else if (temp < 0.98) amplifier = 2;
                                else if (temp < 0.99) amplifier = 3;
                                else amplifier = 4;
                                event.player.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 300, amplifier));
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doDirtyMoneyEnchantmentEvent_preventHOTVEffect(PotionEvent.PotionApplicableEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                if (event.getPotionEffect().getEffect().equals(MobEffects.HERO_OF_THE_VILLAGE)) {
                    if (EnchantmentUtil.isPlayerArmorEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.DIRTY_MONEY.get(), EnchantmentUtil.ArmorEncCalOp.GENERAL) == 1) {
                        event.setResult(Event.Result.DENY);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doDirtyMoneyEnchantmentEvent_dropGoods(LivingDeathEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Villager && event.getSource().getEntity() instanceof Player) {
                int enchantLvl = EnchantmentUtil.isPlayerArmorEnchanted((Player) event.getSource().getEntity(), EnchantmentRegistry.DIRTY_MONEY.get(), EnchantmentUtil.ArmorEncCalOp.HIGHEST_LEVEL);
                if (enchantLvl != 0) {
                    if (Math.random() < 0.1 * enchantLvl) {
                        Containers.dropItemStack(event.getEntityLiving().level, event.getEntityLiving().getX(), event.getEntityLiving().getY() + 2, event.getEntityLiving().getZ(), Items.EMERALD.getDefaultInstance());
                    }
                    if (Math.random() < 0.02 * enchantLvl) {
                        Containers.dropItemStack(event.getEntityLiving().level, event.getEntityLiving().getX(), event.getEntityLiving().getY() + 2, event.getEntityLiving().getZ(), Items.GOLD_INGOT.getDefaultInstance());
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void doPilferageCreedEnchantmentEvent(LivingFallEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                if (EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.PILFERAGE_CREED.get(), EquipmentSlot.FEET, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                    if (event.getDistance() >= 5.0f) {
                        List<LivingEntity> targets = EntityUtil.getTargetsExceptOneself((Player) event.getEntityLiving(), 5, 1, LivingEntity -> LivingEntity instanceof Villager);
                        if (!targets.isEmpty()) {
                            targets.forEach(LivingEntity -> {
                                List<MerchantOffer> offers = ((Villager) LivingEntity).getOffers();
                                if (!offers.isEmpty()) {
                                    List<ItemStack> outcome = rollDiceForPilferage(event.getEntityLiving().getItemBySlot(EquipmentSlot.FEET), (Villager) LivingEntity, offers, event.getEntityLiving().getRandom(), event.getDistance());
                                    outcome.forEach(ItemStack -> LivingEntity.level.addFreshEntity(new ItemEntity(LivingEntity.level,
                                            LivingEntity.getX(), LivingEntity.getY(),
                                            LivingEntity.getZ(), ItemStack)));
                                }
                            });
                        }
                    }
                }
            }
        }
    }

    static List<ItemStack> rollDiceForPilferage(ItemStack armorFeet, Villager villagerEntity, List<MerchantOffer> offers, Random random, double fallingHeight) {
        int extraluck = Mth.floor(fallingHeight);
        extraluck = Math.min(extraluck, 15);
        extraluck -= 5;
        List<ItemStack> itemStacks = new ArrayList<>();
        boolean success = false;
        if (random.nextInt(100) < 30 + 5 * extraluck) {
            int temp = random.nextInt(offers.size());
            itemStacks.add(offers.get(temp).getResult().copy());
            success = true;
        }
        if (random.nextInt(100) < 15 + 2.5f * extraluck && offers.size() > 3) {
            int temp = random.nextInt(offers.size());
            itemStacks.add(offers.get(temp).getResult().copy());
            success = true;
        }
        if (random.nextInt(100) < 5 + 1.5f * extraluck && offers.size() > 5) {
            int temp = random.nextInt(offers.size());
            itemStacks.add(offers.get(temp).getResult().copy());
            success = true;
        }
        if (random.nextInt(100) < 30 + 5 * extraluck) {
            if (!Configuration.GeneralSetting.VILLAGER_SAFE_MODE.get())
                villagerEntity.hurt(DamageSource.GENERIC, 1 + extraluck * 0.5f);
        }
        if (success) {
            if (!armorFeet.equals(ItemStack.EMPTY))
                armorFeet.hurtAndBreak(30, villagerEntity, x -> {
                });
        }
        return itemStacks;
    }

    @SubscribeEvent
    public static void doCarefullyIdentifiedEnchantmentEvent(BlockEvent.BreakEvent event) {
        if (!event.getWorld().isClientSide() && !event.isCanceled()) {
            if (event.getState().getBlock() == Blocks.STONE) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(event.getPlayer(), EnchantmentRegistry.CAREFULLY_IDENTIFIED.get(), EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                int silkTouchEnchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(event.getPlayer(), Enchantments.SILK_TOUCH, EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL);
                int fortuneEnchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(event.getPlayer(), Enchantments.BLOCK_FORTUNE, EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL);
                if (enchantmentLvl != 0) {
                    if (enchantmentLvl < 2) {
                        if (Math.random() < 0.01) {
                            ItemStack coal = silkTouchEnchantmentLvl == 1 ? Items.COAL_ORE.getDefaultInstance() : Items.COAL.getDefaultInstance();
                            if (fortuneEnchantmentLvl == 1) {
                                if (Math.random() < 0.5) coal.grow(1);
                            }
                            Containers.dropItemStack((Level) event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), coal);
                        }
                    }
                    if (enchantmentLvl < 3) {
                        if (Math.random() < 0.005) {
                            ItemStack iron = Items.IRON_ORE.getDefaultInstance();
                            if (fortuneEnchantmentLvl == 1) {
                                if (Math.random() < 0.5) iron.grow(1);
                            }
                            Containers.dropItemStack((Level) event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), iron);
                        }
                    }
                    if (enchantmentLvl < 4) {
                        if (Math.random() < 0.001) {
                            ItemStack gold = Items.GOLD_ORE.getDefaultInstance();
                            if (fortuneEnchantmentLvl == 1) {
                                if (Math.random() < 0.5) gold.grow(1);
                            }
                            Containers.dropItemStack((Level) event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), gold);
                        }
                        if (Math.random() < 0.002) {
                            ItemStack redstone = silkTouchEnchantmentLvl == 1 ? Items.REDSTONE_ORE.getDefaultInstance() : Items.REDSTONE.getDefaultInstance();
                            if (fortuneEnchantmentLvl == 1) {
                                if (Math.random() < 0.5) redstone.grow(1);
                            }
                            Containers.dropItemStack((Level) event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), redstone);
                        }
                    }
                    if (enchantmentLvl < 5) {
                        if (Math.random() < 0.001) {
                            ItemStack lapis = silkTouchEnchantmentLvl == 1 ? Items.LAPIS_ORE.getDefaultInstance() : Items.LAPIS_LAZULI.getDefaultInstance();
                            if (fortuneEnchantmentLvl == 1) {
                                if (Math.random() < 0.5) lapis.grow(1);
                            }
                            Containers.dropItemStack((Level) event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), lapis);
                        }
                    }
                    if (enchantmentLvl < 6) {
                        if (Math.random() < 0.001) {
                            ItemStack emerald = silkTouchEnchantmentLvl == 1 ? Items.EMERALD_ORE.getDefaultInstance() : Items.EMERALD.getDefaultInstance();
                            if (fortuneEnchantmentLvl == 1) {
                                if (Math.random() < 0.5) emerald.grow(1);
                            }
                            Containers.dropItemStack((Level) event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), emerald);
                        }
                        if (Math.random() < 0.001) {
                            ItemStack diamond = silkTouchEnchantmentLvl == 1 ? Items.DIAMOND_ORE.getDefaultInstance() : Items.DIAMOND.getDefaultInstance();
                            if (fortuneEnchantmentLvl == 1) {
                                if (Math.random() < 0.5) diamond.grow(1);
                            }
                            Containers.dropItemStack((Level) event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), diamond);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doNimbleFingerEnchantmentEvent(AnvilUpdateEvent event) {
        if (!Configuration.CompatibilitySetting.HYBRID_SERVER_USER.get()) {
            if (!event.getPlayer().level.isClientSide()) {
                if (EnchantmentUtil.isItemEnchanted(event.getLeft(), EnchantmentRegistry.NIMBLE_FINGER.get()) == 1
                        && event.getLeft().getDamageValue() == 0) {
                    if (isSameCategory(event.getLeft().getItem(), event.getRight().getItem())) {
                        ItemStack result = event.getLeft().copy();
                        Map<Enchantment, Integer> left = EnchantmentHelper.getEnchantments(result);
                        Map<Enchantment, Integer> right = EnchantmentHelper.getEnchantments(event.getRight());
                        Map<Enchantment, Integer> output = Maps.newLinkedHashMap();
                        for (Enchantment lEnchantment : left.keySet()) {
                            if (lEnchantment != EnchantmentRegistry.NIMBLE_FINGER.get())
                                output.put(lEnchantment, left.get(lEnchantment));
                        }
                        for (Enchantment rEnchantment : right.keySet()) {
                            boolean compatible = true;
                            for (Enchantment lEnchantment : left.keySet()) {
                                if (!rEnchantment.isCompatibleWith(lEnchantment))
                                    compatible = false;
                                if (rEnchantment == lEnchantment) {
                                    if (right.get(rEnchantment) > left.get(lEnchantment)) {
                                        output.replace(rEnchantment, right.get(rEnchantment));
                                    }
                                }
                            }
                            if (compatible)
                                output.put(rEnchantment, right.get(rEnchantment));
                        }
                        EnchantmentHelper.setEnchantments(output, result);
                        event.setCost(event.getLeft().getBaseRepairCost() + event.getRight().getBaseRepairCost() + 1);
                        event.setOutput(result);
                    }
                }
            }
        }
    }

    static boolean isSameCategory(Item lItem, Item rItem) {
        if (lItem instanceof SwordItem)
            return rItem instanceof SwordItem;
        else if (lItem instanceof AxeItem)
            return rItem instanceof AxeItem;
        else if (lItem instanceof HoeItem)
            return rItem instanceof HoeItem;
        else if (lItem instanceof PickaxeItem)
            return rItem instanceof PickaxeItem;
        else if (lItem instanceof ShovelItem)
            return rItem instanceof ShovelItem;
        else if (lItem instanceof ArmorItem) {
            if (rItem instanceof ArmorItem) {
                return ((ArmorItem) lItem).getSlot() == ((ArmorItem) rItem).getSlot();
            } else
                return false;
        }
        return false;
    }
}


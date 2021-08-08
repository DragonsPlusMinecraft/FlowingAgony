package love.marblegate.flowingagony.eventhandler.enchantment;

import com.google.common.collect.Maps;
import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EntityUtil;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.GossipType;
import net.minecraft.world.World;
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
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity && (event.getEntityLiving() instanceof ZombieVillagerEntity || event.getEntityLiving() instanceof WitchEntity)){
                if(EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getSource().getTrueSource(),EnchantmentRegistry.REGULAR_CUSTOMER_PROGRAM.get(),EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL)==1){
                    List<LivingEntity> targets = EntityUtil.getTargetsExceptOneself(event.getEntityLiving(),12,2,livingEntity -> livingEntity instanceof VillagerEntity);
                    if(!targets.isEmpty()){
                        for(LivingEntity target:targets){
                            ((VillagerEntity) target).getGossip().add(((PlayerEntity)event.getSource().getTrueSource()).getUniqueID(), GossipType.MINOR_POSITIVE,1);
                        }
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public static void doCleansingBeforeUsingEnchantmentEvent(LivingDeathEvent event) {
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity && event.getEntityLiving() instanceof VillagerEntity){
                if(EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getSource().getTrueSource(),EnchantmentRegistry.CLEANSING_BEFORE_USING.get(),EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL)==1){
                    event.getEntityLiving().getHeldItem(Hand.MAIN_HAND).setRepairCost(0);
                    if(event.getEntityLiving().getHeldItem(Hand.MAIN_HAND).isDamageable()){
                        event.getEntityLiving().getHeldItem(Hand.MAIN_HAND).setDamage(event.getEntityLiving().getHeldItem(Hand.MAIN_HAND).getDamage()-10);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doComeBackAtDuskEnchantmentEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (!event.player.world.isRemote()) {
                if (EnchantmentUtil.isPlayerArmorEnchanted(event.player, EnchantmentRegistry.DIRTY_MONEY.get(), EnchantmentUtil.ArmorEncCalOp.GENERAL)==0){
                    if (event.player.world.getDayTime() % 24000 > 10999 && event.player.world.getDayTime() % 24000 < 13501) {
                        if (EnchantmentUtil.isPlayerArmorEnchanted(event.player, EnchantmentRegistry.COME_BACK_AT_DUSK.get(), EnchantmentUtil.ArmorEncCalOp.GENERAL)==1){
                            if (!event.player.isPotionActive(Effects.HERO_OF_THE_VILLAGE)) {
                                int amplifier;
                                double temp = Math.random();
                                if(temp<0.9) amplifier = 0;
                                else if(temp<0.95) amplifier = 1;
                                else if(temp<0.98) amplifier = 2;
                                else if(temp<0.99) amplifier = 3;
                                else amplifier = 4;
                                event.player.addPotionEffect(new EffectInstance(Effects.HERO_OF_THE_VILLAGE, 300, amplifier));
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
                    if (EnchantmentUtil.isPlayerArmorEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.DIRTY_MONEY.get(), EnchantmentUtil.ArmorEncCalOp.GENERAL)==1) {
                        event.setResult(Event.Result.DENY);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doDirtyMoneyEnchantmentEvent_dropGoods(LivingDeathEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof VillagerEntity && event.getSource().getTrueSource() instanceof PlayerEntity) {
                int enchantLvl = EnchantmentUtil.isPlayerArmorEnchanted((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.DIRTY_MONEY.get(), EnchantmentUtil.ArmorEncCalOp.HIGHEST_LEVEL);
                if (enchantLvl != 0) {
                    if(Math.random()<0.1*enchantLvl){
                        InventoryHelper.spawnItemStack(event.getEntityLiving().world,event.getEntityLiving().getPosX(),event.getEntityLiving().getPosY()+2,event.getEntityLiving().getPosZ(),Items.EMERALD.getDefaultInstance());
                    }
                    if(Math.random()<0.02*enchantLvl){
                        InventoryHelper.spawnItemStack(event.getEntityLiving().world,event.getEntityLiving().getPosX(),event.getEntityLiving().getPosY()+2,event.getEntityLiving().getPosZ(),Items.GOLD_INGOT.getDefaultInstance());
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void doPilferageCreedEnchantmentEvent(LivingFallEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                if (EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.PILFERAGE_CREED.get(), EquipmentSlotType.FEET, EnchantmentUtil.ItemEncCalOp.GENERAL)==1) {
                    if (event.getDistance() >= 5.0f) {
                        List<LivingEntity> targets = EntityUtil.getTargetsExceptOneself((PlayerEntity) event.getEntityLiving(), 5, 1, LivingEntity -> LivingEntity instanceof VillagerEntity);
                        if (!targets.isEmpty()) {
                            targets.forEach(LivingEntity -> {
                                List<MerchantOffer> offers = ((VillagerEntity) LivingEntity).getOffers();
                                if (!offers.isEmpty()) {
                                    List<ItemStack> outcome = rollDiceForPilferage(event.getEntityLiving().getItemStackFromSlot(EquipmentSlotType.FEET),(VillagerEntity) LivingEntity, offers, event.getEntityLiving().getRNG(), event.getDistance());
                                    outcome.forEach(ItemStack -> LivingEntity.world.addEntity(new ItemEntity(LivingEntity.world,
                                            LivingEntity.getPosX(), LivingEntity.getPosY(),
                                            LivingEntity.getPosZ(), ItemStack)));
                                }
                            });
                        }
                    }
                }
            }
        }
    }

    static List<ItemStack> rollDiceForPilferage(ItemStack armorFeet, VillagerEntity villagerEntity, List<MerchantOffer> offers, Random random, double fallingHeight){
        int extraluck = MathHelper.floor(fallingHeight);
        extraluck= Math.min(extraluck, 15);
        extraluck -=5;
        List<ItemStack> itemStacks = new ArrayList<>();
        boolean success = false;
        if(random.nextInt(100) < 30 + 5*extraluck){
            int temp = random.nextInt(offers.size());
            itemStacks.add(offers.get(temp).getSellingStack().copy());
            success = true;
        }
        if(random.nextInt(100) < 15 + 2.5f*extraluck&&offers.size()>3){
            int temp = random.nextInt(offers.size());
            itemStacks.add(offers.get(temp).getSellingStack().copy());
            success = true;
        }
        if(random.nextInt(100) < 5 + 1.5f*extraluck&&offers.size()>5){
            int temp = random.nextInt(offers.size());
            itemStacks.add(offers.get(temp).getSellingStack().copy());
            success = true;
        }
        if(random.nextInt(100) < 30 + 5 * extraluck){
            if(!Configuration.VILLAGER_SAFE_MODE.get()) villagerEntity.attackEntityFrom(DamageSource.GENERIC,1+extraluck*0.5f);
        }
        if(success){
            if(!armorFeet.equals(ItemStack.EMPTY))
                armorFeet.damageItem(30,villagerEntity,x->{});
        }
        return itemStacks;
    }

    @SubscribeEvent
    public static void doCarefullyIdentifiedEnchantmentEvent(BlockEvent.BreakEvent event){
        if(!event.getWorld().isRemote() && !event.isCanceled()){
            if(event.getState().getBlock() == Blocks.STONE){
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(event.getPlayer(),EnchantmentRegistry.CAREFULLY_IDENTIFIED.get(),EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                int silkTouchEnchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(event.getPlayer(), Enchantments.SILK_TOUCH,EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL);
                int fortuneEnchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(event.getPlayer(), Enchantments.FORTUNE,EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL);
                if(enchantmentLvl!=0){
                    if(enchantmentLvl<2){
                        if(Math.random()<0.01){
                            ItemStack coal = silkTouchEnchantmentLvl == 1? Items.COAL_ORE.getDefaultInstance() : Items.COAL.getDefaultInstance();
                            if(fortuneEnchantmentLvl == 1){
                                if(Math.random()<0.5) coal.grow(1);
                            }
                            InventoryHelper.spawnItemStack((World) event.getWorld(),event.getPos().getX(), event.getPos().getY(),event.getPos().getZ(),coal);
                        }
                    }
                    if(enchantmentLvl<3){
                        if(Math.random()<0.005){
                            ItemStack iron = Items.IRON_ORE.getDefaultInstance();
                            if(fortuneEnchantmentLvl == 1){
                                if(Math.random()<0.5) iron.grow(1);
                            }
                            InventoryHelper.spawnItemStack((World) event.getWorld(),event.getPos().getX(), event.getPos().getY(),event.getPos().getZ(),iron);
                        }
                    }
                    if(enchantmentLvl<4){
                        if(Math.random()<0.001){
                            ItemStack gold = Items.GOLD_ORE.getDefaultInstance();
                            if(fortuneEnchantmentLvl == 1){
                                if(Math.random()<0.5) gold.grow(1);
                            }
                            InventoryHelper.spawnItemStack((World) event.getWorld(),event.getPos().getX(), event.getPos().getY(),event.getPos().getZ(),gold);
                        }
                        if(Math.random()<0.002){
                            ItemStack redstone = silkTouchEnchantmentLvl == 1 ? Items.REDSTONE_ORE.getDefaultInstance() : Items.REDSTONE.getDefaultInstance();
                            if(fortuneEnchantmentLvl == 1){
                                if(Math.random()<0.5) redstone.grow(1);
                            }
                            InventoryHelper.spawnItemStack((World) event.getWorld(),event.getPos().getX(), event.getPos().getY(),event.getPos().getZ(),redstone);
                        }
                    }
                    if(enchantmentLvl<5){
                        if(Math.random()<0.001){
                            ItemStack lapis = silkTouchEnchantmentLvl == 1 ? Items.LAPIS_ORE.getDefaultInstance() : Items.LAPIS_LAZULI.getDefaultInstance();
                            if(fortuneEnchantmentLvl == 1){
                                if(Math.random()<0.5) lapis.grow(1);
                            }
                            InventoryHelper.spawnItemStack((World) event.getWorld(),event.getPos().getX(), event.getPos().getY(),event.getPos().getZ(),lapis);
                        }
                    }
                    if(enchantmentLvl<6){
                        if(Math.random()<0.001){
                            ItemStack emerald = silkTouchEnchantmentLvl == 1 ? Items.EMERALD_ORE.getDefaultInstance() : Items.EMERALD.getDefaultInstance();
                            if(fortuneEnchantmentLvl == 1){
                                if(Math.random()<0.5) emerald.grow(1);
                            }
                            InventoryHelper.spawnItemStack((World) event.getWorld(),event.getPos().getX(), event.getPos().getY(),event.getPos().getZ(),emerald);
                        }
                        if(Math.random()<0.001){
                            ItemStack diamond = silkTouchEnchantmentLvl == 1 ? Items.DIAMOND_ORE.getDefaultInstance() : Items.DIAMOND.getDefaultInstance();
                            if(fortuneEnchantmentLvl == 1){
                                if(Math.random()<0.5) diamond.grow(1);
                            }
                            InventoryHelper.spawnItemStack((World) event.getWorld(),event.getPos().getX(), event.getPos().getY(),event.getPos().getZ(),diamond);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doNimbleFingerEnchantmentEvent(AnvilUpdateEvent event) {
        if(!Configuration.HYBRID_SERVER_USER.get()){
            if (!event.getPlayer().world.isRemote()) {
                if (EnchantmentUtil.isItemEnchanted(event.getLeft(), EnchantmentRegistry.NIMBLE_FINGER.get()) == 1
                        && event.getLeft().getDamage() == 0) {
                    if(isSameCategory(event.getLeft().getItem(),event.getRight().getItem())){
                        ItemStack result = event.getLeft().copy();
                        Map<Enchantment, Integer> left = EnchantmentHelper.getEnchantments(result);
                        Map<Enchantment, Integer> right = EnchantmentHelper.getEnchantments(event.getRight());
                        Map<Enchantment, Integer> output = Maps.newLinkedHashMap();
                        for (Enchantment lEnchantment : left.keySet()) {
                            if(lEnchantment!=EnchantmentRegistry.NIMBLE_FINGER.get())
                                output.put(lEnchantment,left.get(lEnchantment));
                        }
                        for (Enchantment rEnchantment : right.keySet()) {
                            boolean compatible = true;
                            for (Enchantment lEnchantment : left.keySet()) {
                                if(!rEnchantment.isCompatibleWith(lEnchantment))
                                    compatible = false;
                                if(rEnchantment==lEnchantment){
                                    if(right.get(rEnchantment)>left.get(lEnchantment)){
                                        output.replace(rEnchantment,right.get(rEnchantment));
                                    }
                                }
                            }
                            if(compatible)
                                output.put(rEnchantment,right.get(rEnchantment));
                        }
                        EnchantmentHelper.setEnchantments(output, result);
                        event.setCost(event.getLeft().getRepairCost() + event.getRight().getRepairCost() + 1);
                        event.setOutput(result);
                    }
                }
            }
        }
    }

    static boolean isSameCategory(Item lItem,Item rItem){
        if(lItem instanceof SwordItem)
            return rItem instanceof SwordItem;
        else if(lItem instanceof AxeItem)
            return rItem instanceof AxeItem;
        else if(lItem instanceof HoeItem)
            return rItem instanceof HoeItem;
        else if(lItem instanceof PickaxeItem)
            return rItem instanceof PickaxeItem;
        else if(lItem instanceof ShovelItem)
            return rItem instanceof ShovelItem;
        else if(lItem instanceof ArmorItem) {
            if (rItem instanceof ArmorItem) {
                return ((ArmorItem) lItem).getEquipmentSlot() == ((ArmorItem) rItem).getEquipmentSlot();
            } else
                return false;
        }
        return false;
    }
}


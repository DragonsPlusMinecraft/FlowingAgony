package love.marblegate.flowingagony.eventhandler.enchantment;

import com.google.common.collect.Maps;
import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.GodRollingDiceUtil;
import love.marblegate.flowingagony.util.ItemUtil;
import love.marblegate.flowingagony.util.PlayerUtil;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
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
    public static void doRegularCustomerProgramEnchantmentEvent(AnvilUpdateEvent event) {

    }


    @SubscribeEvent
    public static void doCleansingBeforeUsingEnchantmentEvent(AnvilUpdateEvent event) {

    }

    @SubscribeEvent
    public static void doComeBackAtDuskEnchantmentEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (!event.player.world.isRemote()) {
                if (!PlayerUtil.isPlayerArmorEnchanted(event.player, EnchantmentRegistry.dirty_money.get())) {
                    if (event.player.world.getDayTime() % 24000 > 10999 && event.player.world.getDayTime() % 24000 < 13501) {
                        if (PlayerUtil.isPlayerArmorEnchanted(event.player, EnchantmentRegistry.come_back_at_dusk.get())){
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
                    if (PlayerUtil.isPlayerArmorEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.dirty_money.get())) {
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
                int enchantLvl = PlayerUtil.getHighestLevelPlayerArmorEnchantedSameEnchantment((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.dirty_money.get());
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
                if (PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.pilferage_creed.get(), EquipmentSlotType.FEET)) {
                    if (event.getDistance() >= 5.0f) {
                        List<LivingEntity> targets = PlayerUtil.getTargetList((PlayerEntity) event.getEntityLiving(), 5, 1, LivingEntity -> LivingEntity instanceof VillagerEntity);
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
            if(!Config.VILLAGER_SAFE_MODE.get()) villagerEntity.attackEntityFrom(DamageSource.GENERIC,1+extraluck*0.5f);
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
                int enchantmentLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel(event.getPlayer(),EnchantmentRegistry.carefully_identified.get(),EquipmentSlotType.MAINHAND);
                boolean hasSilkTouch = PlayerUtil.isPlayerSpecificSlotEnchanted(event.getPlayer(), Enchantments.SILK_TOUCH,EquipmentSlotType.MAINHAND);
                boolean hasFortune = PlayerUtil.isPlayerSpecificSlotEnchanted(event.getPlayer(), Enchantments.FORTUNE,EquipmentSlotType.MAINHAND);
                if(enchantmentLvl!=0){
                    if(enchantmentLvl<2){
                        if(Math.random()<0.01){
                            ItemStack coal = hasSilkTouch ? Items.COAL_ORE.getDefaultInstance() : Items.COAL.getDefaultInstance();
                            if(hasFortune){
                                if(Math.random()<0.5) coal.grow(1);
                            }
                            InventoryHelper.spawnItemStack((World) event.getWorld(),event.getPos().getX(), event.getPos().getY(),event.getPos().getZ(),coal);
                        }
                    }
                    if(enchantmentLvl<3){
                        if(Math.random()<0.005){
                            ItemStack iron = Items.IRON_ORE.getDefaultInstance();
                            if(hasFortune){
                                if(Math.random()<0.5) iron.grow(1);
                            }
                            InventoryHelper.spawnItemStack((World) event.getWorld(),event.getPos().getX(), event.getPos().getY(),event.getPos().getZ(),iron);
                        }
                    }
                    if(enchantmentLvl<4){
                        if(Math.random()<0.001){
                            ItemStack gold = Items.GOLD_ORE.getDefaultInstance();
                            if(hasFortune){
                                if(Math.random()<0.5) gold.grow(1);
                            }
                            InventoryHelper.spawnItemStack((World) event.getWorld(),event.getPos().getX(), event.getPos().getY(),event.getPos().getZ(),gold);
                        }
                        if(Math.random()<0.002){
                            ItemStack redstone = hasSilkTouch ? Items.REDSTONE_ORE.getDefaultInstance() : Items.REDSTONE.getDefaultInstance();
                            if(hasFortune){
                                if(Math.random()<0.5) redstone.grow(1);
                            }
                            InventoryHelper.spawnItemStack((World) event.getWorld(),event.getPos().getX(), event.getPos().getY(),event.getPos().getZ(),redstone);
                        }
                    }
                    if(enchantmentLvl<5){
                        if(Math.random()<0.001){
                            ItemStack lapis = hasSilkTouch ? Items.LAPIS_ORE.getDefaultInstance() : Items.LAPIS_LAZULI.getDefaultInstance();
                            if(hasFortune){
                                if(Math.random()<0.5) lapis.grow(1);
                            }
                            InventoryHelper.spawnItemStack((World) event.getWorld(),event.getPos().getX(), event.getPos().getY(),event.getPos().getZ(),lapis);
                        }
                    }
                    if(enchantmentLvl<6){
                        if(Math.random()<0.001){
                            ItemStack emerald = hasSilkTouch ? Items.EMERALD_ORE.getDefaultInstance() : Items.EMERALD.getDefaultInstance();
                            if(hasFortune){
                                if(Math.random()<0.5) emerald.grow(1);
                            }
                            InventoryHelper.spawnItemStack((World) event.getWorld(),event.getPos().getX(), event.getPos().getY(),event.getPos().getZ(),emerald);
                        }
                        if(Math.random()<0.001){
                            ItemStack diamond = hasSilkTouch ? Items.DIAMOND_ORE.getDefaultInstance() : Items.DIAMOND.getDefaultInstance();
                            if(hasFortune){
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
        if(!Config.HYBRID_SERVER_USER.get()){
            if (!event.getPlayer().world.isRemote()) {
                if (ItemUtil.isItemEnchanted(event.getLeft(), EnchantmentRegistry.nimble_finger.get())
                        && event.getLeft().getDamage() == 0) {
                    if(isSameCategory(event.getLeft().getItem(),event.getRight().getItem())){
                        ItemStack result = event.getLeft().copy();
                        Map<Enchantment, Integer> left = EnchantmentHelper.getEnchantments(result);
                        Map<Enchantment, Integer> right = EnchantmentHelper.getEnchantments(event.getRight());
                        Map<Enchantment, Integer> output = Maps.newLinkedHashMap();
                        for (Enchantment lEnchantment : left.keySet()) {
                            if(lEnchantment!=EnchantmentRegistry.nimble_finger.get())
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


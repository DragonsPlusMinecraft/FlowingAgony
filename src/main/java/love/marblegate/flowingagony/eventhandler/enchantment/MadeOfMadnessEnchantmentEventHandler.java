package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EntityUtil;
import love.marblegate.flowingagony.util.PlayerUtil;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber()
public class MadeOfMadnessEnchantmentEventHandler {
    @SubscribeEvent
    public static void onAgonyScreamerEnchantmentEven(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()) {
            if (event.getSource().getTrueSource() instanceof PlayerEntity) {
                int enchantmentLvl =  PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.agony_screamer.get(), EquipmentSlotType.MAINHAND);
                if (enchantmentLvl!=0) {
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.agony_resonance.get(), 140 + 20 * enchantmentLvl,enchantmentLvl));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onInsanePoetEnchantmentEven(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getSource().getTrueSource() instanceof PlayerEntity) {
                int enchantmentLvl =  PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.insane_poet.get(), EquipmentSlotType.MAINHAND);
                if (enchantmentLvl!=0) {
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.listen_to_me_singing.get(), enchantmentLvl * 40,enchantmentLvl-1));
                    ((PlayerEntity) event.getSource().getTrueSource()).addPotionEffect(new EffectInstance(EffectRegistry.insane_poet_enchantment_active.get(),enchantmentLvl * 40));
                    event.setAmount(event.getAmount() * 0.1f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPaperBrainEnchantmentEven(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                int enchantLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.paper_brain.get(), EquipmentSlotType.MAINHAND);
                if(enchantLvl!=0){
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.paper_brain_enchantment_active.get(),20+40*enchantLvl,enchantLvl-1));
                    event.setAmount(event.getAmount()*0.1f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onShockTherapyEnchantmentEven(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                int enchantLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.shock_therapy.get(), EquipmentSlotType.MAINHAND);
                if(enchantLvl!=0){
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.shock_therapy_enchantment_active.get(),20+40*enchantLvl,enchantLvl-1));
                    event.setAmount(event.getAmount()*0.1f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onCuttingWatermelonDreamEnchantmentEvent_dealDamage(BlockEvent.BreakEvent event){
        if(!event.getPlayer().world.isRemote()){
            if(event.getState().getBlock().equals(Blocks.MELON)){
                if(PlayerUtil.isPlayerSpecificSlotEnchanted(event.getPlayer(),EnchantmentRegistry.cutting_watermelon_dream.get(),EquipmentSlotType.MAINHAND)){
                    List<LivingEntity> targets = PlayerUtil.getTargetList(event.getPlayer(),12,2,
                            livingEntity -> EntityUtil.isHostile(livingEntity,false));
                    if(!targets.isEmpty()){
                        boolean hasSilkTouch = PlayerUtil.isPlayerSpecificSlotEnchanted(event.getPlayer(),Enchantments.FORTUNE,EquipmentSlotType.MAINHAND);
                        int unbreakingEnchantmentLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel(event.getPlayer(),Enchantments.UNBREAKING,EquipmentSlotType.MAINHAND);
                        float damage = 0;
                        if(event.getPlayer().getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem() instanceof ToolItem){
                            damage += ((ToolItem) event.getPlayer().getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem()).getAttackDamage();
                        }
                        if(hasSilkTouch)
                            damage *= 0.5;
                        if(event.getPlayer().world.getDayTime()%24000>13000)
                            damage *= (4+event.getPlayer().getRNG().nextDouble()*2);
                        else damage *= (2+event.getPlayer().getRNG().nextDouble());
                        for(LivingEntity target: targets) {
                            target.attackEntityFrom(CustomDamageSource.CUTTING_WATERMELON_DREAM, damage);
                        }
                        int damageAppliedToItem = 5;
                        if(unbreakingEnchantmentLvl!=0){
                            if(unbreakingEnchantmentLvl==3)
                                damageAppliedToItem = 3;
                            else damageAppliedToItem = 4;
                        }
                        event.getPlayer().getItemStackFromSlot(EquipmentSlotType.MAINHAND).damageItem(damageAppliedToItem, event.getPlayer(),x->{});
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onCuttingWatermelonDreamEnchantmentEvent_dropHead(LivingDeathEvent event){
        if(!event.getEntityLiving().world.isRemote && !event.isCanceled()
                && event.getSource()==CustomDamageSource.CUTTING_WATERMELON_DREAM
                && event.getSource().getTrueSource() instanceof PlayerEntity
                && EntityUtil.supportHeadDrop(event.getEntityLiving())){
            if(PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getSource().getTrueSource(),EnchantmentRegistry.cutting_watermelon_dream.get(),EquipmentSlotType.MAINHAND)){
                boolean hasSilkTouch = PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getSource().getTrueSource(),Enchantments.SILK_TOUCH,EquipmentSlotType.MAINHAND);
                int fortuneEnchantmentLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getSource().getTrueSource(),Enchantments.FORTUNE,EquipmentSlotType.MAINHAND);
                if(hasSilkTouch){
                    double dropHeadRate =  0.025 + 0.01 * fortuneEnchantmentLvl;
                    if(Math.random()<dropHeadRate){
                        if(event.getEntityLiving() instanceof ZombieEntity)
                            InventoryHelper.spawnItemStack(event.getEntityLiving().world,event.getEntityLiving().getPosX(),event.getEntityLiving().getPosY(),event.getEntityLiving().getPosZ(), Items.ZOMBIE_HEAD.getDefaultInstance());
                        else if(event.getEntityLiving() instanceof SkeletonEntity)
                            InventoryHelper.spawnItemStack(event.getEntityLiving().world,event.getEntityLiving().getPosX(),event.getEntityLiving().getPosY(),event.getEntityLiving().getPosZ(), Items.SKELETON_SKULL.getDefaultInstance());
                        else if(event.getEntityLiving() instanceof CreeperEntity)
                            InventoryHelper.spawnItemStack(event.getEntityLiving().world,event.getEntityLiving().getPosX(),event.getEntityLiving().getPosY(),event.getEntityLiving().getPosZ(), Items.CREEPER_HEAD.getDefaultInstance());
                        else if(event.getEntityLiving() instanceof WitherSkeletonEntity)
                            InventoryHelper.spawnItemStack(event.getEntityLiving().world,event.getEntityLiving().getPosX(),event.getEntityLiving().getPosY(),event.getEntityLiving().getPosZ(), Items.WITHER_SKELETON_SKULL.getDefaultInstance());
                        else if(event.getEntityLiving() instanceof EnderDragonEntity)
                            InventoryHelper.spawnItemStack(event.getEntityLiving().world,event.getEntityLiving().getPosX(),event.getEntityLiving().getPosY(),event.getEntityLiving().getPosZ(), Items.DRAGON_HEAD.getDefaultInstance());
                    }
                }
            }
        }
    }

}

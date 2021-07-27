package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import love.marblegate.flowingagony.util.EntityUtil;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.potion.EffectInstance;
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
                int enchantmentLvl =  EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.AGONY_SCREAMER.get(), EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl!=0) {
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.AGONY_RESONANCE.get(), 140 + 20 * enchantmentLvl,enchantmentLvl));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onInsanePoetEnchantmentEven(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getSource().getTrueSource() instanceof PlayerEntity) {
                int enchantmentLvl =  EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.INSANE_POET.get(), EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl!=0) {
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.LISTEN_TO_ME_SINGING.get(), enchantmentLvl * 40,enchantmentLvl-1));
                    ((PlayerEntity) event.getSource().getTrueSource()).addPotionEffect(new EffectInstance(EffectRegistry.INSANE_POET_ENCHANTMENT_ACTIVE.get(),enchantmentLvl * 40));
                    event.setAmount(event.getAmount() * 0.1f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPaperBrainEnchantmentEven(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.PAPER_BRAIN.get(), EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantLvl!=0){
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.PAPER_BRAIN_ENCHANTMENT_ACTIVE.get(),20+40*enchantLvl,enchantLvl-1));
                    event.setAmount(event.getAmount()*0.1f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onShockTherapyEnchantmentEven(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.SHOCK_THERAPY.get(), EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantLvl!=0){
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.SHOCK_THERAPY_ENCHANTMENT_ACTIVE.get(),20+40*enchantLvl,enchantLvl-1));
                    event.setAmount(event.getAmount()*0.1f);
                }
            }
        }
    }

}

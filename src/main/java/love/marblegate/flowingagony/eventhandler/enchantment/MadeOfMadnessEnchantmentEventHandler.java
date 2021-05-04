package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.PlayerUtil;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ToolItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber()
public class MadeOfMadnessEnchantmentEventHandler {
    @SubscribeEvent
    public static void onAgonyScreamerEnchantmentEven(LivingAttackEvent event){
        if(!event.getEntityLiving().world.isRemote()) {
            if (event.getSource().getTrueSource() instanceof PlayerEntity) {
                if (PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.agony_screamer_enchantment.get(), EquipmentSlotType.MAINHAND)) {
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.agony_resonance_effect.get(), 400));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onInsanePoetEnchantmentEven(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getSource().getTrueSource() instanceof PlayerEntity) {
                if (PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.insane_poet_enchantment.get(), EquipmentSlotType.MAINHAND)) {
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.listen_to_me_singing_effect.get(), 400));
                    event.setAmount(event.getAmount() * 0.1f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPaperBrainEnchantmentEven(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                int enchantLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.paper_brain_enchantment.get(), EquipmentSlotType.MAINHAND);
                if(enchantLvl!=0){
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.paper_brain_enchantment_active_effect.get(),20+40*enchantLvl,enchantLvl-1));
                    event.setAmount(event.getAmount()*0.1f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onShockTherapyEnchantmentEven(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                int enchantLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.shock_therapy_enchantment.get(), EquipmentSlotType.MAINHAND);
                if(enchantLvl!=0){
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.shock_therapy_enchantment_active_effect.get(),20+40*enchantLvl,enchantLvl-1));
                    event.setAmount(event.getAmount()*0.1f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onCuttingWatermelonDreamEnchantmentEvent(BlockEvent.BreakEvent event){
        if(!event.getPlayer().world.isRemote()){
            if(event.getState().getBlock().equals(Blocks.MELON)){
                if(PlayerUtil.isPlayerSpecificSlotEnchanted(event.getPlayer(),EnchantmentRegistry.cutting_watermelon_dream_enchantment.get(),EquipmentSlotType.MAINHAND)
                &&!PlayerUtil.isPlayerSpecificSlotEnchanted(event.getPlayer(), Enchantments.MENDING,EquipmentSlotType.MAINHAND)){
                    float damage = 0;
                    if(event.getPlayer().getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem() instanceof ToolItem){
                        damage += ((ToolItem) event.getPlayer().getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem()).getAttackDamage();
                    }
                    List<LivingEntity> targets = PlayerUtil.getTargetList(event.getPlayer(),12,2,LivingEntity->
                            LivingEntity instanceof MonsterEntity || LivingEntity instanceof SlimeEntity
                                    || LivingEntity instanceof WitherEntity);
                    if(event.getPlayer().world.getDayTime()%24000>13000) damage *= (4+event.getPlayer().getRNG().nextDouble()*2);
                    else damage *= (2+event.getPlayer().getRNG().nextDouble());
                    for(LivingEntity target: targets) {
                        target.attackEntityFrom(DamageSource.MAGIC, damage);
                    }
                }
            }
        }
    }

}

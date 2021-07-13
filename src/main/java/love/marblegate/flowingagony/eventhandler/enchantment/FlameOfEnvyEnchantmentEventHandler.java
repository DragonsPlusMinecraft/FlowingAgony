package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import love.marblegate.flowingagony.util.EntityUtil;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;

@Mod.EventBusSubscriber()
public class FlameOfEnvyEnchantmentEventHandler {
    @SubscribeEvent
    public static void doEnviousKindEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.envious_kind.get(), EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantmentLvl!=0){
                    double diff = (event.getEntityLiving().getHealth() - ((PlayerEntity) event.getSource().getTrueSource()).getHealth());
                    if(diff>=0){
                        int amplifier = (int) Math.floor(diff/10);
                        ((PlayerEntity) event.getSource().getTrueSource()).addPotionEffect(new EffectInstance(EffectRegistry.envious_being.get(),200, amplifier));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doEyesoreEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.eyesore.get(), EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantmentLvl!=0){
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.eyesore_enchantment_active.get(),61,enchantmentLvl-1));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doThornInFleshEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.thorn_in_flesh.get(), EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantmentLvl!=0) {
                    if (event.getEntityLiving() instanceof PlayerEntity)
                        event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.thorn_in_flesh_active_for_player.get(), 60 + 40 * enchantmentLvl, enchantmentLvl - 1));
                    else
                        event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.thorn_in_flesh_active.get(), 60 + 40 * enchantmentLvl, enchantmentLvl - 1));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doCovertKnifeEnchantmentEvent(ProjectileImpactEvent.Arrow event) {
        if (!event.getEntity().world.isRemote()) {
            if (event.getRayTraceResult() instanceof EntityRayTraceResult) {
                if (((EntityRayTraceResult) event.getRayTraceResult()).getEntity() instanceof EndermanEntity) {
                    if (event.getArrow().getShooter() != null) {
                        if (event.getArrow().getShooter() instanceof PlayerEntity) {
                            int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getArrow().getShooter(), EnchantmentRegistry.covert_knife.get(), EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                            if(enchantmentLvl!=0){
                                if(enchantmentLvl == 3 || (enchantmentLvl == 2 ? (Math.random() < 0.75) : (Math.random() < 0.5))){
                                    ((EntityRayTraceResult) event.getRayTraceResult()).getEntity().attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) event.getArrow().getShooter()), 9f);
                                    if (EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getArrow().getShooter(), Enchantments.FLAME, EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL)==1){
                                        ((EntityRayTraceResult) event.getRayTraceResult()).getEntity().setFire(5);
                                    }
                                    if(event.getArrow() instanceof SpectralArrowEntity){
                                        ((EndermanEntity) ((EntityRayTraceResult) event.getRayTraceResult()).getEntity()).addPotionEffect(new EffectInstance(Effects.GLOWING, 200));
                                    }
                                    if(event.getArrow() instanceof ArrowEntity){
                                        Potion potion = ObfuscationReflectionHelper.getPrivateValue(ArrowEntity.class, (ArrowEntity) event.getArrow(),"field_184560_g");
                                        if(potion != Potions.EMPTY){
                                            List<EffectInstance> effectsOnArrow = potion.getEffects();
                                            for(EffectInstance effect: effectsOnArrow){
                                                int duration = MathHelper.ceil(effect.getDuration()*0.125f);
                                                ((EndermanEntity) ((EntityRayTraceResult) event.getRayTraceResult()).getEntity()).addPotionEffect(new EffectInstance(effect.getPotion(),duration,effect.getAmplifier()));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doSourceOfEnvyEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity && event.getSource().getTrueSource() instanceof LivingEntity){
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.source_of_envy.get(), EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantmentLvl!=0) {
                    if(event.getSource().getTrueSource() instanceof PlayerEntity){
                        List<LivingEntity> availableEnvySpreadTargets = EntityUtil.getTargetsExceptOneself((PlayerEntity) event.getEntityLiving(),12,2, livingEntity -> EntityUtil.isHostile(livingEntity,false));
                        if(!availableEnvySpreadTargets.isEmpty()){
                            for(LivingEntity spreadTarget:availableEnvySpreadTargets){
                                if(Math.random()<0.08 + 0.02 * enchantmentLvl)
                                    spreadTarget.setRevengeTarget((LivingEntity) event.getSource().getTrueSource());
                            }
                        }
                    } else {
                        List<LivingEntity> availableEnvySpreadTargets = EntityUtil.getTargetsOfSameType((PlayerEntity) event.getEntityLiving(),12,2, (LivingEntity) event.getSource().getTrueSource(),true);
                        if(!availableEnvySpreadTargets.isEmpty()){
                            for(LivingEntity spreadTarget:availableEnvySpreadTargets){
                                if(Math.random()<0.15 + 0.05 * enchantmentLvl)
                                    spreadTarget.setRevengeTarget((LivingEntity) event.getSource().getTrueSource());
                            }
                        }
                    }
                }
            }
        }
    }
}

package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.PlayerUtil;
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
    public static void doEnciousKindEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity && event.getSource().getTrueSource() instanceof LivingEntity){
                if(PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.encious_kind_enchantment.get(), EquipmentSlotType.CHEST)){
                    double diff = ((LivingEntity) event.getSource().getTrueSource()).getHealth() - event.getEntityLiving().getHealth();
                    if(diff>=1){
                        diff = diff>10?10:diff;
                        event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.encious_being_effect.get(),200, MathHelper.floor(diff)-1));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doEyesoreEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof LivingEntity && event.getSource().getTrueSource() instanceof PlayerEntity){
                if(PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.eyesore_enchantment.get(), EquipmentSlotType.MAINHAND)){
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.eyesore_enchantment_active_effect.get(),60));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doThornInFleshEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof LivingEntity && event.getSource().getTrueSource() instanceof PlayerEntity){
                if(PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.thorn_in_flesh_enchantment.get(), EquipmentSlotType.MAINHAND)){
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.thron_in_flesh_active_effect.get(),180));
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
                            if (PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getArrow().getShooter(), EnchantmentRegistry.covert_knife_enchantment.get(), EquipmentSlotType.MAINHAND)) {
                                ((EntityRayTraceResult) event.getRayTraceResult()).getEntity().attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) event.getArrow().getShooter()), 9f);
                                if (PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getArrow().getShooter(), Enchantments.FLAME, EquipmentSlotType.MAINHAND)){
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

    @SubscribeEvent
    public static void doSourceOfEnvyEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity && event.getSource().getTrueSource() instanceof LivingEntity){
                if(PlayerUtil.isPlayerSpecificSlotEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.source_of_envy_enchantment.get(), EquipmentSlotType.CHEST)){
                    List<LivingEntity> availableEnvySpreadTargets = PlayerUtil.getTargetListUnderSameType((PlayerEntity) event.getEntityLiving(),12,2, (LivingEntity) event.getSource().getTrueSource());
                    if(!availableEnvySpreadTargets.isEmpty()){
                        for(LivingEntity spreadTarget:availableEnvySpreadTargets){
                            spreadTarget.setRevengeTarget((LivingEntity) event.getSource().getTrueSource());
                        }
                    }
                }
            }
        }
    }
}

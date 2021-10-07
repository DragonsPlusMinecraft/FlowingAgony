package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.effect.EffectRegistry;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import love.marblegate.flowingagony.util.EntityUtil;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.util.Mth;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.util.List;

@Mod.EventBusSubscriber()
public class FlameOfEnvyEnchantmentEventHandler {
    @SubscribeEvent
    public static void doEnviousKindEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getSource().getEntity(), EnchantmentRegistry.ENVIOUS_KIND.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    double diff = (event.getEntityLiving().getHealth() - ((Player) event.getSource().getEntity()).getHealth());
                    if (diff >= 0) {
                        int amplifier = (int) Math.floor(diff / 10);
                        ((Player) event.getSource().getEntity()).addEffect(new MobEffectInstance(EffectRegistry.ENVIOUS_BEING.get(), 200, amplifier));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doEyesoreEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getSource().getEntity(), EnchantmentRegistry.EYESORE.get(), EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    event.getEntityLiving().addEffect(new MobEffectInstance(EffectRegistry.EYESORE_ENCHANTMENT_ACTIVE.get(), 61, enchantmentLvl - 1));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doThornInFleshEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getSource().getEntity(), EnchantmentRegistry.THORN_IN_FLESH.get(), EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    if (event.getEntityLiving() instanceof Player)
                        event.getEntityLiving().addEffect(new MobEffectInstance(EffectRegistry.THORN_IN_FLESH_ACTIVE_FOR_PLAYER.get(), 60 + 40 * enchantmentLvl, enchantmentLvl - 1));
                    else
                        event.getEntityLiving().addEffect(new MobEffectInstance(EffectRegistry.THORN_IN_FLESH_ACTIVE.get(), 60 + 40 * enchantmentLvl, enchantmentLvl - 1));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doCovertKnifeEnchantmentEvent(ProjectileImpactEvent event) {
        if (!event.getEntity().level.isClientSide()) {
            if (event.getRayTraceResult() instanceof EntityHitResult) {
                if (((EntityHitResult) event.getRayTraceResult()).getEntity() instanceof EnderMan) {
                    if (event.getProjectile().getOwner() != null) {
                        if (event.getProjectile().getOwner() instanceof Player) {
                            int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getProjectile().getOwner(), EnchantmentRegistry.COVERT_KNIFE.get(), EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                            if (enchantmentLvl != 0) {
                                if (enchantmentLvl == 3 || (enchantmentLvl == 2 ? (Math.random() < 0.75) : (Math.random() < 0.5))) {
                                    ((EntityHitResult) event.getRayTraceResult()).getEntity().hurt(DamageSource.playerAttack((Player) event.getProjectile().getOwner()), 9f);
                                    if (EnchantmentUtil.isPlayerItemEnchanted((Player) event.getProjectile().getOwner(), Enchantments.FLAMING_ARROWS, EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                                        ((EntityHitResult) event.getRayTraceResult()).getEntity().setSecondsOnFire(5);
                                    }
                                    if (event.getProjectile() instanceof SpectralArrow) {
                                        ((EnderMan) ((EntityHitResult) event.getRayTraceResult()).getEntity()).addEffect(new MobEffectInstance(MobEffects.GLOWING, 200));
                                    }
                                    if (event.getProjectile() instanceof Arrow) {
                                        Potion potion = ObfuscationReflectionHelper.getPrivateValue(Arrow.class, (Arrow) event.getProjectile(), "potion");
                                        if (potion != Potions.EMPTY) {
                                            List<MobEffectInstance> effectsOnArrow = potion.getEffects();
                                            for (MobEffectInstance effect : effectsOnArrow) {
                                                int duration = Mth.ceil(effect.getDuration() * 0.125f);
                                                ((EnderMan) ((EntityHitResult) event.getRayTraceResult()).getEntity()).addEffect(new MobEffectInstance(effect.getEffect(), duration, effect.getAmplifier()));
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
    /*public static void doCovertKnifeEnchantmentEvent(ProjectileImpactEvent.Arrow event) {
        if (!event.getEntity().level.isClientSide()) {
            if (event.getRayTraceResult() instanceof EntityHitResult) {
                if (((EntityHitResult) event.getRayTraceResult()).getEntity() instanceof EnderMan) {
                    if (event.getArrow().getOwner() != null) {
                        if (event.getArrow().getOwner() instanceof Player) {
                            int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getArrow().getOwner(), EnchantmentRegistry.COVERT_KNIFE.get(), EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                            if (enchantmentLvl != 0) {
                                if (enchantmentLvl == 3 || (enchantmentLvl == 2 ? (Math.random() < 0.75) : (Math.random() < 0.5))) {
                                    ((EntityHitResult) event.getRayTraceResult()).getEntity().hurt(DamageSource.playerAttack((Player) event.getArrow().getOwner()), 9f);
                                    if (EnchantmentUtil.isPlayerItemEnchanted((Player) event.getArrow().getOwner(), Enchantments.FLAMING_ARROWS, EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                                        ((EntityHitResult) event.getRayTraceResult()).getEntity().setSecondsOnFire(5);
                                    }
                                    if (event.getArrow() instanceof SpectralArrow) {
                                        ((EnderMan) ((EntityHitResult) event.getRayTraceResult()).getEntity()).addEffect(new MobEffectInstance(MobEffects.GLOWING, 200));
                                    }
                                    if (event.getArrow() instanceof Arrow) {
                                        Potion potion = ObfuscationReflectionHelper.getPrivateValue(Arrow.class, (Arrow) event.getArrow(), "potion");
                                        if (potion != Potions.EMPTY) {
                                            List<MobEffectInstance> effectsOnArrow = potion.getEffects();
                                            for (MobEffectInstance effect : effectsOnArrow) {
                                                int duration = Mth.ceil(effect.getDuration() * 0.125f);
                                                ((EnderMan) ((EntityHitResult) event.getRayTraceResult()).getEntity()).addEffect(new MobEffectInstance(effect.getEffect(), duration, effect.getAmplifier()));
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
    }*/

    @SubscribeEvent
    public static void doSourceOfEnvyEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player && event.getSource().getEntity() instanceof LivingEntity) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.SOURCE_OF_ENVY.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    if (event.getSource().getEntity() instanceof Player) {
                        List<LivingEntity> availableEnvySpreadTargets = EntityUtil.getTargetsExceptOneself((Player) event.getEntityLiving(), 12, 2, livingEntity -> EntityUtil.isHostile(livingEntity, false));
                        if (!availableEnvySpreadTargets.isEmpty()) {
                            for (LivingEntity spreadTarget : availableEnvySpreadTargets) {
                                if (Math.random() < 0.08 + 0.02 * enchantmentLvl)
                                    spreadTarget.setLastHurtByMob((LivingEntity) event.getSource().getEntity());
                            }
                        }
                    } else {
                        List<LivingEntity> availableEnvySpreadTargets = EntityUtil.getTargetsOfSameType((Player) event.getEntityLiving(), 12, 2, (LivingEntity) event.getSource().getEntity(), true);
                        if (!availableEnvySpreadTargets.isEmpty()) {
                            for (LivingEntity spreadTarget : availableEnvySpreadTargets) {
                                if (Math.random() < 0.15 + 0.05 * enchantmentLvl)
                                    spreadTarget.setLastHurtByMob((LivingEntity) event.getSource().getEntity());
                            }
                        }
                    }
                }
            }
        }
    }
}

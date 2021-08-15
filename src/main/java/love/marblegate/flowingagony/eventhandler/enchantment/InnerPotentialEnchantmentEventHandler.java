package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.PlaySoundPacket;
import love.marblegate.flowingagony.network.packet.RemoveEffectSyncToClientPacket;
import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber()
public class InnerPotentialEnchantmentEventHandler {

    @SubscribeEvent
    public static void doStubbornStepEnchantmentEvent_addKnockBackResistenceModifier(LivingKnockBackEvent event) {
        if (event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.STUBBORN_STEP.get(), EquipmentSlotType.LEGS, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl != 0) {
                    event.setStrength(event.getStrength() * (1 - enchantLvl * 0.15f));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doStubbornStepEnchantmentEvent_cancelFloatingEffect(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity && event.getSource().getTrueSource() instanceof LivingEntity) {
                if (EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.STUBBORN_STEP.get(), EquipmentSlotType.LEGS, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                    if (((PlayerEntity) (event.getEntityLiving())).isPotionActive(Effects.LEVITATION)) {
                        ((PlayerEntity) (event.getEntityLiving())).removeActivePotionEffect(Effects.LEVITATION);
                        //Sync to Client
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayerEntity) event.getEntityLiving()
                                ),
                                new RemoveEffectSyncToClientPacket(Effects.LEVITATION));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doFrivolousStepEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity && event.getSource().getTrueSource() instanceof LivingEntity) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.FRIVOLOUS_STEP.get(), EquipmentSlotType.LEGS, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl != 0) {
                    if (enchantLvl == 1)
                        ((PlayerEntity) (event.getEntityLiving())).addPotionEffect(new EffectInstance(EffectRegistry.FRIVOLOUS_STEP_ENCHANTMENT_ACTIVE.get(), 200));
                    else
                        ((PlayerEntity) (event.getEntityLiving())).addPotionEffect(new EffectInstance(EffectRegistry.FRIVOLOUS_STEP_ENCHANTMENT_ACTIVE.get(), 200, 1));
                    if (((PlayerEntity) (event.getEntityLiving())).isPotionActive(Effects.SLOWNESS)) {
                        ((PlayerEntity) (event.getEntityLiving())).removeActivePotionEffect(Effects.SLOWNESS);
                        //Sync to Client
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayerEntity) event.getEntityLiving()
                                ),
                                new RemoveEffectSyncToClientPacket(Effects.SLOWNESS));
                    }
                }
            }
        }

    }

    @SubscribeEvent
    public static void doPotentialBurstEnchantmentEvent_addSpeedModifier(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (!event.player.world.isRemote()) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted(event.player, EnchantmentRegistry.POTENTIAL_BURST.get(), EquipmentSlotType.FEET, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl != 0) {
                    if (event.player.getHealth() <= (3 + enchantLvl)) {
                        if (!(event.player.isSprinting() || event.player.isSwimming() || event.player.isElytraFlying())) {
                            if (event.player.isPotionActive(EffectRegistry.POTENTIAL_BURST_ENCHANTMENT_ACTIVE.get())) {
                                int nextAmplifier = Math.min(event.player.getActivePotionEffect(EffectRegistry.POTENTIAL_BURST_ENCHANTMENT_ACTIVE.get()).getAmplifier() + 1, 150);
                                event.player.addPotionEffect(new EffectInstance(EffectRegistry.POTENTIAL_BURST_ENCHANTMENT_ACTIVE.get(), 20, nextAmplifier));
                            } else {
                                event.player.addPotionEffect(new EffectInstance(EffectRegistry.POTENTIAL_BURST_ENCHANTMENT_ACTIVE.get(), 20));
                            }
                        } else {
                            if (event.player.isPotionActive(EffectRegistry.POTENTIAL_BURST_ENCHANTMENT_ACTIVE.get())) {
                                event.player.removeActivePotionEffect(EffectRegistry.POTENTIAL_BURST_ENCHANTMENT_ACTIVE.get());
                                //Sync to Client
                                Networking.INSTANCE.send(
                                        PacketDistributor.PLAYER.with(
                                                () -> (ServerPlayerEntity) event.player
                                        ),
                                        new RemoveEffectSyncToClientPacket(EffectRegistry.POTENTIAL_BURST_ENCHANTMENT_ACTIVE.get()));
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doMiraculousEscapeEnchantmentEvent_launch(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            if (event.getEntityLiving().getHealth() < 4f) {
                if (EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.MIRACULOUS_ESCAPE.get(), EquipmentSlotType.FEET, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                    if (!((PlayerEntity) (event.getEntityLiving())).isPotionActive(EffectRegistry.MIRACULOUS_ESCAPE_ENCHANTMENT_ACTIVE.get())) {
                        //Play Sound Effect
                        if (!((PlayerEntity) (event.getEntityLiving())).world.isRemote) {
                            Networking.INSTANCE.send(
                                    PacketDistributor.PLAYER.with(
                                            () -> (ServerPlayerEntity) (event.getEntityLiving())
                                    ),
                                    new PlaySoundPacket(PlaySoundPacket.ModSoundType.MIRACULOUS_ESCAPE_HEARTBEAT, true));
                        }
                        ((PlayerEntity) (event.getEntityLiving())).addPotionEffect(new EffectInstance(EffectRegistry.MIRACULOUS_ESCAPE_ENCHANTMENT_FORCE_ESCAPE.get(), 40));
                        ((PlayerEntity) (event.getEntityLiving())).addPotionEffect(new EffectInstance(EffectRegistry.MIRACULOUS_ESCAPE_ENCHANTMENT_ACTIVE.get(), 200));
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public static void doMiraculousEscapeEnchantmentEvent_processFallDamage(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                if (event.getSource().getDamageType().equals("fall") || event.getSource().getDamageType().equals("cramming") || event.getSource().getDamageType().equals("inWall")) {
                    if (((PlayerEntity) (event.getEntityLiving())).isPotionActive(EffectRegistry.MIRACULOUS_ESCAPE_ENCHANTMENT_ACTIVE.get())) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doArmorUpEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (!event.isCanceled()) {
                if (event.getEntityLiving() instanceof PlayerEntity) {
                    int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getEntityLiving(), EnchantmentRegistry.ARMOR_UP.get(), EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                    if (enchantLvl != 0) {
                        if (((PlayerEntity) (event.getEntityLiving())).getHealth() < (5 + enchantLvl)) {
                            float existYellowHeart = ((PlayerEntity) (event.getEntityLiving())).getAbsorptionAmount();
                            if (existYellowHeart + 1 < (5 + enchantLvl)) {
                                ((PlayerEntity) (event.getEntityLiving())).setAbsorptionAmount(existYellowHeart + 1);
                            }
                        }
                    }
                }
            }
        }
    }
}

package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.effect.EffectRegistry;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.PlaySoundPacket;
import love.marblegate.flowingagony.network.packet.RemoveEffectSyncToClientPacket;
import love.marblegate.flowingagony.util.EffectUtil;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

@Mod.EventBusSubscriber()
public class InnerPotentialEnchantmentEventHandler {

    @SubscribeEvent
    public static void doStubbornStepEnchantmentEvent_addKnockBackResistenceModifier(LivingKnockBackEvent event) {
        if (event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.STUBBORN_STEP.get(), EquipmentSlot.LEGS, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl != 0) {
                    event.setStrength(event.getStrength() * (1 - enchantLvl * 0.15f));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doStubbornStepEnchantmentEvent_cancelFloatingEffect(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player && event.getSource().getEntity() instanceof LivingEntity) {
                if (EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.STUBBORN_STEP.get(), EquipmentSlot.LEGS, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                    if (((Player) (event.getEntityLiving())).hasEffect(MobEffects.LEVITATION)) {
                        ((Player) (event.getEntityLiving())).removeEffectNoUpdate(MobEffects.LEVITATION);
                        //Sync to Client
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayer) event.getEntityLiving()
                                ),
                                new RemoveEffectSyncToClientPacket(MobEffects.LEVITATION));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doFrivolousStepEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player && event.getSource().getEntity() instanceof LivingEntity) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.FRIVOLOUS_STEP.get(), EquipmentSlot.LEGS, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl != 0) {
                    if (enchantLvl == 1)
                        ((Player) (event.getEntityLiving())).addEffect(EffectUtil.genImplicitEffect(EffectRegistry.FRIVOLOUS_STEP_ENCHANTMENT_ACTIVE.get(), 200));
                    else
                        ((Player) (event.getEntityLiving())).addEffect(EffectUtil.genImplicitEffect(EffectRegistry.FRIVOLOUS_STEP_ENCHANTMENT_ACTIVE.get(), 200, 1));
                    if (((Player) (event.getEntityLiving())).hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                        ((Player) (event.getEntityLiving())).removeEffectNoUpdate(MobEffects.MOVEMENT_SLOWDOWN);
                        //Sync to Client
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayer) event.getEntityLiving()
                                ),
                                new RemoveEffectSyncToClientPacket(MobEffects.MOVEMENT_SLOWDOWN));
                    }
                }
            }
        }

    }

    @SubscribeEvent
    public static void doPotentialBurstEnchantmentEvent_addSpeedModifier(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (!event.player.level.isClientSide()) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted(event.player, EnchantmentRegistry.POTENTIAL_BURST.get(), EquipmentSlot.FEET, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl != 0) {
                    if (event.player.getHealth() <= (3 + enchantLvl)) {
                        if (!(event.player.isSprinting() || event.player.isSwimming() || event.player.isFallFlying())) {
                            if (event.player.hasEffect(EffectRegistry.POTENTIAL_BURST_ENCHANTMENT_ACTIVE.get())) {
                                int nextAmplifier = Math.min(event.player.getEffect(EffectRegistry.POTENTIAL_BURST_ENCHANTMENT_ACTIVE.get()).getAmplifier() + 1, 150);
                                event.player.addEffect(EffectUtil.genImplicitEffect(EffectRegistry.POTENTIAL_BURST_ENCHANTMENT_ACTIVE.get(), 20, nextAmplifier));
                            } else {
                                event.player.addEffect(EffectUtil.genImplicitEffect(EffectRegistry.POTENTIAL_BURST_ENCHANTMENT_ACTIVE.get(), 20));
                            }
                        } else {
                            if (event.player.hasEffect(EffectRegistry.POTENTIAL_BURST_ENCHANTMENT_ACTIVE.get())) {
                                event.player.removeEffectNoUpdate(EffectRegistry.POTENTIAL_BURST_ENCHANTMENT_ACTIVE.get());
                                //Sync to Client
                                Networking.INSTANCE.send(
                                        PacketDistributor.PLAYER.with(
                                                () -> (ServerPlayer) event.player
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
        if (event.getEntityLiving() instanceof Player) {
            if (event.getEntityLiving().getHealth() < 4f) {
                if (EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.MIRACULOUS_ESCAPE.get(), EquipmentSlot.FEET, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                    if (!((Player) (event.getEntityLiving())).hasEffect(EffectRegistry.MIRACULOUS_ESCAPE_ENCHANTMENT_ACTIVE.get())) {
                        //Play Sound Effect
                        if (!((Player) (event.getEntityLiving())).level.isClientSide) {
                            Networking.INSTANCE.send(
                                    PacketDistributor.PLAYER.with(
                                            () -> (ServerPlayer) (event.getEntityLiving())
                                    ),
                                    new PlaySoundPacket(PlaySoundPacket.ModSoundType.MIRACULOUS_ESCAPE_HEARTBEAT, true));
                        }
                        ((Player) (event.getEntityLiving())).addEffect(EffectUtil.genImplicitEffect(EffectRegistry.MIRACULOUS_ESCAPE_ENCHANTMENT_FORCE_ESCAPE.get(), 40));
                        ((Player) (event.getEntityLiving())).addEffect(EffectUtil.genImplicitEffect(EffectRegistry.MIRACULOUS_ESCAPE_ENCHANTMENT_ACTIVE.get(), 200));
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public static void doMiraculousEscapeEnchantmentEvent_processFallDamage(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                if (event.getSource().getMsgId().equals("fall") || event.getSource().getMsgId().equals("cramming") || event.getSource().getMsgId().equals("inWall")) {
                    if (((Player) (event.getEntityLiving())).hasEffect(EffectRegistry.MIRACULOUS_ESCAPE_ENCHANTMENT_ACTIVE.get())) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doArmorUpEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (!event.isCanceled()) {
                if (event.getEntityLiving() instanceof Player) {
                    int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.ARMOR_UP.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                    if (enchantLvl != 0) {
                        if (((Player) (event.getEntityLiving())).getHealth() < (5 + enchantLvl)) {
                            float existYellowHeart = ((Player) (event.getEntityLiving())).getAbsorptionAmount();
                            if (existYellowHeart + 1 < (5 + enchantLvl)) {
                                ((Player) (event.getEntityLiving())).setAbsorptionAmount(existYellowHeart + 1);
                            }
                        }
                    }
                }
            }
        }
    }
}

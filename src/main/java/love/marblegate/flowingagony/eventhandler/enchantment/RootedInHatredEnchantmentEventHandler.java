package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.capibility.CapabilityManager;
import love.marblegate.flowingagony.capibility.HatredBloodlineStatusCapability;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.PlaySoundPacket;
import love.marblegate.flowingagony.effect.EffectRegistry;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

@Mod.EventBusSubscriber()
public class RootedInHatredEnchantmentEventHandler {

    @SubscribeEvent
    public static void doResentfulSoulEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide() && !event.isCanceled()) {
            if (event.getEntityLiving() instanceof Player && event.getSource() != DamageSource.OUT_OF_WORLD && !event.getSource().getMsgId().equals("flowingagony.burial_object_curse")) {
                if (event.getAmount() >= event.getEntityLiving().getHealth()) {
                    int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(((Player) event.getEntityLiving()), EnchantmentRegistry.RESENTFUL_SOUL.get(), EquipmentSlot.HEAD, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                    if (enchantmentLvl != 0) {
                        if (event.getEntityLiving().getLastHurtMobTimestamp() <= (25 + enchantmentLvl * 25)) {
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doTooResentfulToDieEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide() && !event.isCanceled()) {
            if (event.getEntityLiving() instanceof Player && !event.getEntityLiving().equals(event.getSource().getEntity()) && event.getSource() != DamageSource.OUT_OF_WORLD && !event.getSource().getMsgId().equals("flowingagony.burial_object_curse")) {
                if (event.getAmount() >= event.getEntityLiving().getHealth()) {
                    int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(((Player) event.getEntityLiving()), EnchantmentRegistry.TOO_RESENTFUL_TO_DIE.get(), EquipmentSlot.HEAD, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                    if (!((Player) event.getEntityLiving()).hasEffect(EffectRegistry.EXTREME_HATRED.get())) {
                        if (enchantmentLvl != 0) {
                            ((Player) event.getEntityLiving()).heal(1 + enchantmentLvl * 3);
                            ((Player) event.getEntityLiving()).addEffect(new MobEffectInstance(EffectRegistry.EXTREME_HATRED.get(), 7200));
                            event.setCanceled(true);
                            //Play Sound Effect - Stage 1
                            if (!event.getEntityLiving().level.isClientSide) {
                                Networking.INSTANCE.send(
                                        PacketDistributor.PLAYER.with(
                                                () -> (ServerPlayer) event.getEntityLiving()
                                        ),
                                        new PlaySoundPacket(PlaySoundPacket.ModSoundType.EXTREME_HATRED_FIRST_STAGE, true));
                            }
                        }
                    } else {
                        if (enchantmentLvl != 0) {
                            int potionLvl = ((Player) event.getEntityLiving()).getEffect(EffectRegistry.EXTREME_HATRED.get()).getAmplifier() + 1;
                            if (potionLvl == 1) {
                                ((Player) event.getEntityLiving()).heal(1 + enchantmentLvl * 2);
                                ((Player) event.getEntityLiving()).addEffect(new MobEffectInstance(EffectRegistry.EXTREME_HATRED.get(), 7200, 1));
                                event.setCanceled(true);
                                //Play Sound Effect - Stage 2
                                if (!event.getEntityLiving().level.isClientSide) {
                                    Networking.INSTANCE.send(
                                            PacketDistributor.PLAYER.with(
                                                    () -> (ServerPlayer) event.getEntityLiving()
                                            ),
                                            new PlaySoundPacket(PlaySoundPacket.ModSoundType.EXTREME_HATRED_MEDIUM_STAGE, true));
                                }
                            } else if (potionLvl == 2) {
                                ((Player) event.getEntityLiving()).heal(1 + enchantmentLvl);
                                ((Player) event.getEntityLiving()).addEffect(new MobEffectInstance(EffectRegistry.EXTREME_HATRED.get(), 7200, 2));
                                event.setCanceled(true);
                                //Play Sound Effect - Stage 3
                                if (!event.getEntityLiving().level.isClientSide) {
                                    Networking.INSTANCE.send(
                                            PacketDistributor.PLAYER.with(
                                                    () -> (ServerPlayer) event.getEntityLiving()
                                            ),
                                            new PlaySoundPacket(PlaySoundPacket.ModSoundType.EXTREME_HATRED_FINAL_STAGE, true));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doOutrageousSpiritEnchantmentEvent(LivingHurtEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(((Player) event.getSource().getEntity()), EnchantmentRegistry.OUTRAGEOUS_SPIRIT.get(), EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    int negativeEffectCount = 0;
                    if (((Player) event.getSource().getEntity()).isOnFire()) negativeEffectCount++;
                    negativeEffectCount += ((Player) event.getSource().getEntity()).getActiveEffects().stream().filter(
                            EffectInstance -> EffectInstance.getEffect().getCategory() == MobEffectCategory.HARMFUL
                    ).count();
                    event.setAmount(event.getAmount() + negativeEffectCount * enchantmentLvl);
                }
            }
        }
    }

    @SubscribeEvent
    public static void doHatredBloodlikeEnchantmentEvent_acvtivateHatredBloodlineMarkOnDeath(LivingDeathEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                int enchantLvl = EnchantmentUtil.isPlayerArmorEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.HATRED_BLOODLINE.get(), EnchantmentUtil.ArmorEncCalOp.TOTAL_LEVEL);
                if (enchantLvl != 0) {
                    LazyOptional<HatredBloodlineStatusCapability> statusCap = event.getEntityLiving().getCapability(CapabilityManager.HATRED_BLOODLINE_STATUS_CAPABILITY);
                    statusCap.ifPresent(
                            cap -> cap.setActiveLevel(enchantLvl)
                    );
                }
            }
        }
    }

    @SubscribeEvent
    public static void doHatredBloodlikeEnchantmentEvent_activeEnchantmentEffectWhenRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.getPlayer().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                LazyOptional<HatredBloodlineStatusCapability> statusCap = event.getEntityLiving().getCapability(CapabilityManager.HATRED_BLOODLINE_STATUS_CAPABILITY);
                statusCap.ifPresent(
                        cap -> {
                            if (cap.getActiveLevel() != 0) {
                                int hatredBloodlineLevel = cap.getActiveLevel();
                                event.getPlayer().addEffect(new MobEffectInstance(EffectRegistry.HATRED_BLOODLINE_ENCHANTMENT_ACTIVE.get(), 800 * hatredBloodlineLevel, hatredBloodlineLevel - 1));
                                cap.setActiveLevel(0);
                            }
                        }
                );
            }
        }
    }

    @SubscribeEvent
    public static void doFreshRevengeEnchantmentEvent_applyBuff(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(((Player) event.getSource().getEntity()), EnchantmentRegistry.FRESH_REVENGE.get(), EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    if (event.getEntityLiving().getLastHurtMobTimestamp() <= (20 + enchantmentLvl * 4)) {
                        ((Player) event.getSource().getEntity()).addEffect(new MobEffectInstance(EffectRegistry.FRESH_REVENGE_ENCHANTMENT_ACTIVE.get(), 200, enchantmentLvl - 1));
                    }
                }
            }
        }
    }
}

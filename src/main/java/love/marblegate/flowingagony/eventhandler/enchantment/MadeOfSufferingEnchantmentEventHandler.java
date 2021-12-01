package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.capibility.AbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.ModCapManager;
import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.effect.EffectRegistry;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.AbnormalJoySyncPacket;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import love.marblegate.flowingagony.util.EntityUtil;
import love.marblegate.flowingagony.util.StandardUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import java.util.List;

@Mod.EventBusSubscriber()
public class MadeOfSufferingEnchantmentEventHandler {
    @SubscribeEvent
    public static void onDrowningPhobiaEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player && StandardUtil.shouldReflectDamage(event)) {
                if (event.getEntityLiving().isSwimming()) {
                    int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.DROWNING_PHOBIA.get(), EquipmentSlot.HEAD, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                    if (enchantmentLvl != 0) {
                        dealPhobiaEffectDamage(event, MobEffects.BLINDNESS, enchantmentLvl);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBurningPhobiaEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player && StandardUtil.shouldReflectDamage(event)) {
                if (event.getEntityLiving().isInLava() || event.getEntityLiving().getRemainingFireTicks() > 0) {
                    if (!event.getSource().isFire()) {
                        int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.BURNING_PHOBIA.get(), EquipmentSlot.HEAD, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                        if (enchantmentLvl != 0) {
                            dealPhobiaEffectDamage(event, MobEffects.MOVEMENT_SLOWDOWN, enchantmentLvl);
                        }
                    }
                }
            }
        }
    }

    private static void dealPhobiaEffectDamage(LivingDamageEvent event, MobEffect effect_1, int enchantmentLvl) {
        event.getEntityLiving().addEffect(new MobEffectInstance(effect_1, 500 - enchantmentLvl * 100));
        event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.CONFUSION, 500 - enchantmentLvl * 100));
        List<LivingEntity> targets = EntityUtil.getTargetsExceptOneself((Player) event.getEntityLiving(), 12, 2, livingEntity -> EntityUtil.isHostile(livingEntity, false));
        for (LivingEntity target : targets) {
            target.hurt(CustomDamageSource.causePhobiaDamage((Player) event.getEntityLiving()), event.getAmount() * (1.5F + 0.5F * enchantmentLvl));
        }
        if (event.getSource().getEntity() instanceof LivingEntity) {
            if (!targets.contains((LivingEntity) event.getSource().getEntity()) && event.getEntityLiving() != event.getSource().getEntity())
                ((LivingEntity) event.getSource().getEntity()).hurt(CustomDamageSource.causePhobiaDamage((Player) event.getEntityLiving()), event.getAmount() * (1.5F + 0.5F * enchantmentLvl));

        }

    }

    @SubscribeEvent
    public static void onPrayerOfPainEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.PRAYER_OF_PAIN.get(), EquipmentSlot.HEAD, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl != 0) {
                    if (event.getEntityLiving().getHealth() < (4 + enchantLvl * 2)) {
                        if (event.getEntityLiving().hasEffect(EffectRegistry.LET_ME_SAVOR_IT.get())) {
                            if (event.getEntityLiving().getEffect(EffectRegistry.LET_ME_SAVOR_IT.get()).getAmplifier() < 9)
                                event.getEntityLiving().addEffect(new MobEffectInstance(EffectRegistry.LET_ME_SAVOR_IT.get(), 72000,
                                        event.getEntityLiving().getEffect(EffectRegistry.LET_ME_SAVOR_IT.get()).getAmplifier() + 1));
                        } else {
                            event.getEntityLiving().addEffect(new MobEffectInstance(EffectRegistry.LET_ME_SAVOR_IT.get(), 72000));
                        }
                    }
                }
            }
            if (event.getSource().getEntity() instanceof Player) {
                if (((Player) event.getSource().getEntity()).hasEffect(EffectRegistry.LET_ME_SAVOR_IT.get())) {
                    int effectLvl = ((Player) event.getSource().getEntity()).getEffect(EffectRegistry.LET_ME_SAVOR_IT.get()).getAmplifier();
                    event.setAmount(event.getAmount() * (1 - 0.09f * (effectLvl + 1)));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onConstrainedHeartEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.CONSTRAINED_HEART.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl != 0) {
                    if (event.getSource().isFire() || event.getSource().getMsgId().equals("inWall") ||
                            event.getSource().getMsgId().equals("cramming") || event.getSource().getMsgId().equals("fall") ||
                            event.getSource().getMsgId().equals("flyIntoWall") || event.getSource().getMsgId().equals("wither") ||
                            (event.getSource().getMsgId().equals("magic") || ((Player) event.getEntityLiving()).hasEffect(MobEffects.POISON))) {
                        grandAbnormalJoyPoint(event, enchantLvl);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPiercingFeverEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.PIERCING_FEVER.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl != 0) {
                    if (event.getSource().isProjectile() || event.getSource().getMsgId().equals("cactus") || event.getSource().getMsgId().equals("sweetBerryBush")) {
                        grandAbnormalJoyPoint(event, enchantLvl);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDestructionWorshipEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.DESTRUCTION_WORSHIP.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl != 0) {
                    if (event.getSource().isMagic() || event.getSource().isExplosion() || event.getSource().getMsgId().equals("lightningBolt")) {
                        grandAbnormalJoyPoint(event, enchantLvl);
                    }
                }
            }
        }
    }

    private static void grandAbnormalJoyPoint(LivingDamageEvent event, int enchantLvl) {
        LazyOptional<AbnormalJoyCapability> pointCap = event.getEntityLiving().getCapability(ModCapManager.ABNORMALJOY_CAPABILITY);
        pointCap.ifPresent(
                cap -> {
                    cap.add(event.getAmount() * (0.25F * (1 + enchantLvl)));
                    //Sync to Client
                    Networking.INSTANCE.send(
                            PacketDistributor.PLAYER.with(
                                    () -> (ServerPlayer) (event.getEntityLiving())
                            ),
                            new AbnormalJoySyncPacket(cap.get()));
                }
        );
    }


}

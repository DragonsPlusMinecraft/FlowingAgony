package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.effect.EffectRegistry;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.RemoveEffectSyncToClientPacket;
import love.marblegate.flowingagony.util.EffectUtil;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import love.marblegate.flowingagony.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber()
public class TheMistakensEnchantmentEventHandler {

    @SubscribeEvent
    public static void doShadowbornEnchantmentEvent_applyAndRemoveEffect(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (!event.player.level.isClientSide()) {
                if (event.player.hasEffect(MobEffects.BLINDNESS)) {
                    if (event.player.level.getMaxLocalRawBrightness(new BlockPos(event.player.blockPosition())) >= 5) {
                        if (EnchantmentUtil.isPlayerItemEnchanted(event.player, EnchantmentRegistry.SHADOWBORN.get(), EquipmentSlot.HEAD, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                            event.player.removeEffectNoUpdate(MobEffects.BLINDNESS);
                            //Sync to Client
                            Networking.INSTANCE.send(
                                    PacketDistributor.PLAYER.with(
                                            () -> (ServerPlayer) event.player
                                    ),
                                    new RemoveEffectSyncToClientPacket(MobEffects.BLINDNESS));
                        }
                    }
                }
                if (event.player.level.getMaxLocalRawBrightness(new BlockPos(event.player.blockPosition())) <= 5) {
                    if (EnchantmentUtil.isPlayerItemEnchanted(event.player, EnchantmentRegistry.SHADOWBORN.get(), EquipmentSlot.HEAD, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                        if (!event.player.hasEffect(MobEffects.NIGHT_VISION)) {
                            event.player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1200));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doShadowBornEnchantmentEvent_addImmunity(PotionEvent.PotionApplicableEvent event) {
        if (!event.getEntityLiving().level.isClientSide() && !(event.getResult() == Event.Result.DENY)) {
            if (event.getEntityLiving() instanceof Player) {
                if (EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.SHADOWBORN.get(), EquipmentSlot.HEAD, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                    if (event.getEntityLiving().level.getMaxLocalRawBrightness(new BlockPos(event.getEntityLiving().blockPosition())) >= 5) {
                        if (event.getPotionEffect().getEffect() == MobEffects.BLINDNESS) {
                            event.setResult(Event.Result.DENY);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doPrototypeChaoticEnchantmentEvent(PotionEvent.PotionAddedEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted(((Player) event.getEntityLiving()), EnchantmentRegistry.PROTOTYPE_CHAOTIC.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl != 0) {
                    if (EffectUtil.isExplicit(event.getPotionEffect())) {
                        if (((Player) event.getEntityLiving()).hasEffect(EffectRegistry.PROTOTYPE_CHAOTIC_ENCHANTMENT_ACTIVE.get())) {
                            int newEffectAmplifier = Math.min(((Player) event.getEntityLiving()).getEffect(EffectRegistry.PROTOTYPE_CHAOTIC_ENCHANTMENT_ACTIVE.get()).getAmplifier() + enchantLvl, 29);
                            ((Player) event.getEntityLiving()).addEffect(EffectUtil.genImplicitEffect(EffectRegistry.PROTOTYPE_CHAOTIC_ENCHANTMENT_ACTIVE.get(), 1200, newEffectAmplifier));
                        } else {
                            ((Player) event.getEntityLiving()).addEffect(EffectUtil.genImplicitEffect(EffectRegistry.PROTOTYPE_CHAOTIC_ENCHANTMENT_ACTIVE.get(), 1200, enchantLvl - 1));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doPrototypeChaoticTypeBetaEnchantmentEvent(PotionEvent.PotionAddedEvent event) {
        if (!event.getEntityLiving().level.isClientSide() && event.getEntityLiving() instanceof Player) {
            if (EnchantmentUtil.isPlayerItemEnchanted(((Player) event.getEntityLiving()), EnchantmentRegistry.PROTOTYPE_CHAOTIC_TYPE_BETA.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                if (EffectUtil.isExplicit(event.getPotionEffect())) {
                    if (event.getPotionEffect().getEffect().getCategory() == MobEffectCategory.BENEFICIAL && !event.getPotionEffect().getEffect().isInstantenous()) {
                        if (EnchantmentUtil.isPlayerItemEnchanted(((Player) event.getEntityLiving()), EnchantmentRegistry.PROTOTYPE_CHAOTIC.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                            event.getPotionEffect().update(new MobEffectInstance(event.getPotionEffect().getEffect(), event.getPotionEffect().getDuration() * 3));
                            List<MobEffectInstance> negativeEffects = ((Player) event.getEntityLiving()).getActiveEffects().stream()
                                    .filter(effectInstance -> effectInstance.getEffect().getCategory() == MobEffectCategory.HARMFUL)
                                    .filter(effectInstance -> effectInstance.isCurativeItem(new ItemStack(Items.MILK_BUCKET)))
                                    .filter(EffectUtil::isExplicit)
                                    .collect(Collectors.toList());
                            if (!negativeEffects.isEmpty()) {
                                for (MobEffectInstance effect : negativeEffects) {
                                    ((Player) event.getEntityLiving()).removeEffect(effect.getEffect());
                                    //Notify client to remove effect
                                    Networking.INSTANCE.send(
                                            PacketDistributor.PLAYER.with(
                                                    () -> (ServerPlayer) event.getEntityLiving()
                                            ),
                                            new RemoveEffectSyncToClientPacket(effect.getEffect()));
                                }
                            }
                        } else {
                            event.getPotionEffect().update(new MobEffectInstance(event.getPotionEffect().getEffect(), event.getPotionEffect().getDuration() * 2));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void doCorruptedKindredEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player && event.getSource().getEntity() instanceof LivingEntity) {
                if (!event.isCanceled()) {
                    int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.CORRUPTED_KINDRED.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                    if (enchantLvl != 0) {
                        if (EntityUtil.isAggresiveUndead((LivingEntity) event.getSource().getEntity())) {
                            Random temp = event.getEntityLiving().getRandom();
                            if (temp.nextInt(100) < (6 - enchantLvl)) {
                                ((Player) event.getEntityLiving()).addEffect(new MobEffectInstance(EffectRegistry.CURSE_OF_UNDEAD.get(), 144000));
                            }
                            if (EntityUtil.isCommonUndead((LivingEntity) event.getSource().getEntity())) {
                                if (enchantLvl == 5) event.setCanceled(true);
                                else {
                                    event.setAmount(event.getAmount() * (1f - (0.5f + 0.1f * enchantLvl)));
                                }
                            } else if (EntityUtil.isRareUndead((LivingEntity) event.getSource().getEntity())) {
                                event.setAmount(event.getAmount() * (1f - 0.1f * enchantLvl));
                            } else if (event.getSource().getEntity() instanceof WitherBoss) {
                                if (enchantLvl > 1) {
                                    event.setAmount(event.getAmount() * (1f - 0.05f * (enchantLvl - 1)));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doLightburnFungalParasiticEnchantmentEvent_applyProtectionAndSpreadFungalEffect(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.LIGHTBURN_FUNGAL_PARASITIC.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    if (event.getSource().getEntity() instanceof LivingEntity) {
                        List<LivingEntity> targets = EntityUtil.getTargetsExceptOneself((Player) event.getEntityLiving(), 8, 2,
                                Configuration.GeneralSetting.VILLAGER_SAFE_MODE.get() ? livingEntity -> !(livingEntity instanceof Villager) : x -> true);
                        if (!targets.isEmpty()) {
                            Random rand = event.getEntityLiving().getRandom();
                            for (LivingEntity target : targets) {
                                if (rand.nextDouble() < 0.125D * (enchantmentLvl + 1))
                                    target.addEffect(new MobEffectInstance(EffectRegistry.LIGHTBURN_FUNGAL_INFECTION.get(), 120));
                            }
                        }
                    }
                    if (!event.isCanceled()) {
                        if (event.getSource().getMsgId().equals("fall") || event.getSource().getMsgId().equals("explosion") || event.getSource().isFire())
                            event.setAmount(event.getAmount() * (1 - 0.05F * (enchantmentLvl + 1)));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doLightburnFungalParasiticEnchantmentEvent_removeCurrentImmuneEffect(TickEvent.PlayerTickEvent event) {
        if (!event.player.level.isClientSide()) {
            if (EnchantmentUtil.isPlayerItemEnchanted((Player) event.player, EnchantmentRegistry.LIGHTBURN_FUNGAL_PARASITIC.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                if (event.player.hasEffect(MobEffects.POISON)) {
                    event.player.removeEffectNoUpdate(MobEffects.POISON);
                    //Sync to Client
                    Networking.INSTANCE.send(
                            PacketDistributor.PLAYER.with(
                                    () -> (ServerPlayer) event.player
                            ),
                            new RemoveEffectSyncToClientPacket(MobEffects.POISON));
                }
                if (event.player.hasEffect(EffectRegistry.LIGHTBURN_FUNGAL_INFECTION.get())) {
                    event.player.removeEffectNoUpdate(EffectRegistry.LIGHTBURN_FUNGAL_INFECTION.get());
                    //Sync to Client
                    Networking.INSTANCE.send(
                            PacketDistributor.PLAYER.with(
                                    () -> (ServerPlayer) event.player
                            ),
                            new RemoveEffectSyncToClientPacket(EffectRegistry.LIGHTBURN_FUNGAL_INFECTION.get()));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doLightburnFungalParasiticEnchantmentEvent_addImmunity(PotionEvent.PotionApplicableEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                if (EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.LIGHTBURN_FUNGAL_PARASITIC.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                    if (event.getPotionEffect().getEffect().equals(MobEffects.POISON)) {
                        event.setResult(Event.Result.DENY);
                    }
                    if (event.getPotionEffect().getEffect().equals(EffectRegistry.LIGHTBURN_FUNGAL_INFECTION.get())) {
                        event.setResult(Event.Result.DENY);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doScholarOfOriginalSinEnchantmentEvent_addWeakness(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player && !event.isCanceled() && event.getSource() != DamageSource.OUT_OF_WORLD) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.SCHOLAR_OF_ORIGINAL_SIN.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    float extraDamage = Math.min(event.getAmount() * (1.1F - 0.1F * enchantmentLvl), 10);
                    event.setAmount(event.getAmount() + extraDamage);
                }
            }
        }
    }

    @SubscribeEvent
    public static void doScholarOfOriginalSinEnchantmentEvent_extendHarmfulEffect(PotionEvent.PotionAddedEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.SCHOLAR_OF_ORIGINAL_SIN.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    if (event.getPotionEffect().getEffect().getCategory() == MobEffectCategory.HARMFUL && EffectUtil.isExplicit(event.getPotionEffect()))
                        event.getPotionEffect().update(new MobEffectInstance(event.getPotionEffect().getEffect(), (int) (event.getPotionEffect().getDuration() * (2.1 - 0.1 * enchantmentLvl))));
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void doScholarOfOriginalSinEnchantmentEvent_extraEXP(PlayerXpEvent.PickupXp event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.SCHOLAR_OF_ORIGINAL_SIN.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    event.getPlayer().giveExperiencePoints((int) (event.getOrb().value * (0.35 + 0.15 * enchantmentLvl)));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doOriginalSinErosionEnchantmentEvent_decreaseAttack(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getSource().getEntity(), EnchantmentRegistry.ORIGINAL_SIN_EROSION.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    float damageModified = Math.max(event.getAmount() - 5 + enchantmentLvl, 0);
                    event.setAmount(damageModified);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void doOriginalSinErosionEnchantmentEvent_extraEXP(PlayerXpEvent.PickupXp event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player && !event.isCanceled()) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.ORIGINAL_SIN_EROSION.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    event.getPlayer().giveExperiencePoints((int) (event.getOrb().value * (0.05 + 0.05 * enchantmentLvl)));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doBurialObjectCurseEvent(LivingDeathEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                BlockPos originalDeathPos = event.getEntityLiving().blockPosition();
                AABB scanningArea = new AABB(originalDeathPos.getX() - 16, originalDeathPos.getY() - 1, originalDeathPos.getZ() - 16, originalDeathPos.getX() + 16, originalDeathPos.getY() + 1, originalDeathPos.getZ() + 16);
                List<Player> players = event.getEntityLiving().level.getEntitiesOfClass(Player.class, scanningArea);
                for (Player player : players) {
                    if (EnchantmentUtil.isPlayerArmorEnchanted(player, EnchantmentRegistry.BURIAL_OBJECT.get(), EnchantmentUtil.ArmorEncCalOp.GENERAL) == 1) {
                        player.hurt(CustomDamageSource.causeBurialObjectDamage(((Player) event.getEntityLiving())), 120);
                    }
                }
            }
        }
    }

}

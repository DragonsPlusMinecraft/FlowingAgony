package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import love.marblegate.flowingagony.util.EntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber()
public class SurvivalTricksEnchantmentEventHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void doSurvivalShortcutEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (!event.isCanceled() && event.getSource() != DamageSource.OUT_OF_WORLD && !event.getSource().getMsgId().equals("flowingagony.burial_object_curse")) {
                if (event.getEntityLiving() instanceof Player) {
                    int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.SURVIVAL_SHORTCUT.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                    if (enchantmentLvl != 0) {
                        if (event.getSource().getEntity() instanceof Player) {
                            event.setAmount(event.getAmount() * (0.95F - 0.05F * enchantmentLvl));
                        } else {
                            if (!(event.getAmount() < 9 - enchantmentLvl)) {
                                List<LivingEntity> entities = EntityUtil.getTargetsExceptOneself((Player) event.getEntityLiving(), 16, 2,
                                        Configuration.GeneralSetting.VILLAGER_SAFE_MODE.get() ? livingEntity -> !EntityUtil.isHostile(livingEntity, false) && !(livingEntity instanceof Villager) : livingEntity -> !EntityUtil.isHostile(livingEntity, false));
                                damageTransfer(entities, event, enchantmentLvl, false);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void doSurvivalSRuseEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (!event.isCanceled() && event.getSource() != DamageSource.OUT_OF_WORLD && !event.getSource().getMsgId().equals("flowingagony.burial_object_curse")) {
                if (event.getEntityLiving() instanceof Player) {
                    int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.SURVIVAL_RUSE.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                    if (enchantmentLvl != 0) {
                        if (event.getSource().getEntity() instanceof Player) {
                            event.setAmount(event.getAmount() * (0.95F - 0.05F * enchantmentLvl));
                        } else {
                            if (!(event.getAmount() < 9 - enchantmentLvl)) {
                                List<LivingEntity> entities = EntityUtil.getTargetsExceptOneself((Player) event.getEntityLiving(), 16, 2, livingEntity -> EntityUtil.isHostile(livingEntity, false));
                                damageTransfer(entities, event, enchantmentLvl, false);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void doNecessaryEvilEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (!event.isCanceled() && event.getSource() != DamageSource.OUT_OF_WORLD && !event.getSource().getMsgId().equals("flowingagony.burial_object_curse")) {
                if (event.getEntityLiving() instanceof Player) {
                    int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.NECESSARY_EVIL.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                    if (enchantmentLvl != 0) {
                        if (event.getSource().getEntity() instanceof Player) {
                            event.setAmount(event.getAmount() * (0.85F - 0.05F * enchantmentLvl));
                        } else {
                            if (!(event.getAmount() < 13 - enchantmentLvl)) {
                                List<LivingEntity> entities = EntityUtil.getTargetsExceptOneself((Player) event.getEntityLiving(), 16, 2,
                                        Configuration.GeneralSetting.VILLAGER_SAFE_MODE.get() ? livingEntity -> !(livingEntity instanceof Player) && !(livingEntity instanceof Villager) : livingEntity -> !(livingEntity instanceof Player));
                                damageTransfer(entities, event, enchantmentLvl, true);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void damageTransfer(List<LivingEntity> entities, LivingDamageEvent event, int lvl, boolean shareMode) {
        if (!entities.isEmpty()) {
            if (shareMode) {
                float damageSharedPerEntity = event.getAmount() / entities.size();
                for (LivingEntity entity : entities) {
                    entity.hurt(DamageSource.GENERIC, damageSharedPerEntity);
                }
            } else {
                LivingEntity target;
                if (entities.size() == 1)
                    target = entities.get(0);
                else {
                    target = getLuckyOne(entities, event.getEntityLiving().getRandom());
                }
                target.hurt(DamageSource.GENERIC, event.getAmount());
            }
            event.setCanceled(true);
        } else {
            if (event.getSource().getMsgId().equals("explosion")) {
                if (shareMode) event.setAmount(event.getAmount() * (0.7F - lvl * 0.1F));
                else event.setAmount(event.getAmount() * (0.9F - lvl * 0.1F));
            } else {
                if (shareMode) event.setAmount(event.getAmount() * (0.85F - lvl * 0.05F));
                else event.setAmount(event.getAmount() * (0.95F - lvl * 0.05F));
            }
        }
    }

    public static LivingEntity getLuckyOne(List<LivingEntity> entities, Random random) {
        return entities.get(random.nextInt(entities.size()));
    }
}

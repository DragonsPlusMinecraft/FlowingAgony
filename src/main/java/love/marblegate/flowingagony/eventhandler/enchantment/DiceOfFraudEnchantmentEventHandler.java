package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.capibility.CapabilityManager;
import love.marblegate.flowingagony.capibility.CoolDown;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.Mth;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber()
public class DiceOfFraudEnchantmentEventHandler {

    @SubscribeEvent
    public static void doTricksterEnchantmentEvent(AttackEntityEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getTarget() instanceof LivingEntity) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted(event.getPlayer(), EnchantmentRegistry.TRICKSTER.get(), EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl == 1) {
                    appendixEffectForTrickster((LivingEntity) event.getTarget(), ((LivingEntity) event.getTarget()).getRandom().nextInt(5) + 1);
                } else if (enchantLvl == 2) {
                    int aEffect = ((LivingEntity) event.getTarget()).getRandom().nextInt(5) + 1;
                    int bEffect = ((LivingEntity) event.getTarget()).getRandom().nextInt(5) + 1;
                    while (aEffect == bEffect) {
                        bEffect = ((LivingEntity) event.getTarget()).getRandom().nextInt(5) + 1;
                    }
                    appendixEffectForTrickster((LivingEntity) event.getTarget(), aEffect);
                    appendixEffectForTrickster((LivingEntity) event.getTarget(), bEffect);
                }
            }
        }
    }

    static void appendixEffectForTrickster(LivingEntity entity, int diceNum) {
        switch (diceNum) {
            case 1:
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 100));
                break;
            case 2:
                entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 100));
                break;
            case 3:
                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100));
                break;
            case 4:
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100));
                break;
            case 5:
                entity.setSecondsOnFire(5);
                break;
        }
    }

    @SubscribeEvent
    public static void doAnEnchantedGoldenAppleADayEnchantmentEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.getPlayer().level.isClientSide()) {
            int enchantNum = EnchantmentUtil.isPlayerArmorEnchanted(event.getPlayer(), EnchantmentRegistry.AN_ENCHANTED_GOLDEN_APPLE_A_DAY.get(), EnchantmentUtil.ArmorEncCalOp.TOTAL_PIECE);
            if (enchantNum != 0) {
                LazyOptional<CoolDown> coolDownCap = event.getEntityLiving().getCapability(CapabilityManager.COOL_DOWN_CAPABILITY);
                coolDownCap.ifPresent(
                        cap -> {
                            if (cap.isReady(CoolDown.CoolDownType.AN_ENCHANTED_GOLDEN_APPLE_A_DAY)) {
                                if (enchantNum == 1) {
                                    int tempInt = event.getPlayer().getRandom().nextInt(4);
                                    switch (tempInt) {
                                        case 0:
                                            event.getPlayer().addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3));
                                            break;
                                        case 1:
                                            event.getPlayer().addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1));
                                            break;
                                        case 2:
                                            event.getPlayer().addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000));
                                            break;
                                        case 3:
                                            event.getPlayer().addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000));
                                            break;
                                    }
                                } else if (enchantNum == 4) {
                                    event.getPlayer().addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3));
                                    event.getPlayer().addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1));
                                    event.getPlayer().addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000));
                                    event.getPlayer().addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000));
                                } else if (enchantNum < 4) {
                                    Set<Integer> temp = new HashSet<>();
                                    int tempCount = enchantNum;
                                    while (tempCount > 0) {
                                        int tempInt = event.getPlayer().getRandom().nextInt(4);
                                        if (!temp.contains(tempInt)) {
                                            switch (tempInt) {
                                                case 0:
                                                    event.getPlayer().addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3));
                                                    break;
                                                case 1:
                                                    event.getPlayer().addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1));
                                                    break;
                                                case 2:
                                                    event.getPlayer().addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000));
                                                    break;
                                                case 3:
                                                    event.getPlayer().addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000));
                                                    break;
                                            }
                                            temp.add(tempInt);
                                            tempCount--;
                                        }
                                    }
                                }
                                cap.set(CoolDown.CoolDownType.AN_ENCHANTED_GOLDEN_APPLE_A_DAY, 18000);
                            }
                        });
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void doDeathpunkEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (!event.isCanceled()) {
                if (event.getEntityLiving() instanceof Player) {
                    if (event.getAmount() >= event.getEntityLiving().getHealth() && (!event.getSource().getMsgId().equals("outOfWorld"))) {
                        if (EnchantmentUtil.isPlayerItemEnchanted(((Player) event.getEntityLiving()), EnchantmentRegistry.DEATHPUNK.get(), EquipmentSlot.CHEST, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                            int solution = event.getEntityLiving().getRandom().nextInt(4);
                            int health = Mth.floor((((Player) event.getEntityLiving()).getHealth()));
                            int maxHealth = Mth.floor((((Player) event.getEntityLiving()).getMaxHealth()));
                            boolean damageEnchantment = false;
                            switch (solution) {
                                case 0:
                                    int foodLevel = ((Player) event.getEntityLiving()).getFoodData().getFoodLevel() + Mth.floor(((Player) event.getEntityLiving()).getFoodData().getSaturationLevel());
                                    if (foodLevel <= health) damageEnchantment = true;
                                    else {
                                        if (foodLevel > maxHealth) foodLevel = maxHealth;
                                        ((Player) event.getEntityLiving()).setHealth(foodLevel);
                                        ((Player) event.getEntityLiving()).getFoodData().setFoodLevel(health);
                                        ((Player) event.getEntityLiving()).getFoodData().addExhaustion(((Player) event.getEntityLiving()).getFoodData().getSaturationLevel() * 4);
                                    }
                                    break;
                                case 1:
                                    int oxygenLevel = Mth.floor(((Player) event.getEntityLiving()).getAirSupply() / (((Player) event.getEntityLiving()).getMaxAirSupply() / (float) maxHealth));
                                    if (oxygenLevel <= health) damageEnchantment = true;
                                    else {
                                        if (oxygenLevel > maxHealth) oxygenLevel = maxHealth;
                                        event.getEntityLiving().setHealth(oxygenLevel);
                                        event.getEntityLiving().setAirSupply(health * (event.getEntityLiving().getMaxAirSupply() / maxHealth));
                                    }
                                    break;
                                case 2:
                                    int expPoint = ((Player) event.getEntityLiving()).totalExperience;
                                    int exchangeCost = (Mth.floor(((Player) event.getEntityLiving()).getMaxHealth()) - health) * 30;
                                    if (expPoint <= exchangeCost) damageEnchantment = true;
                                    else {
                                        event.getEntityLiving().setHealth(event.getEntityLiving().getMaxHealth());
                                        ((Player) event.getEntityLiving()).giveExperiencePoints(-exchangeCost);
                                    }
                                    break;
                                case 3:
                                    damageEnchantment = true;
                                    break;
                            }
                            if (damageEnchantment) {
                                event.getEntityLiving().setHealth(((Player) event.getEntityLiving()).getMaxHealth());
                                Map<Enchantment, Integer> enchantmentList = EnchantmentHelper.getEnchantments(event.getEntityLiving().getItemBySlot(EquipmentSlot.CHEST));
                                enchantmentList.remove(EnchantmentRegistry.DEATHPUNK.get());
                                EnchantmentHelper.setEnchantments(enchantmentList, ((Player) event.getEntityLiving()).getItemBySlot(EquipmentSlot.CHEST));
                            }
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doSavorTheTastedEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted(((Player) (event.getSource().getEntity())), EnchantmentRegistry.SAVOR_THE_TASTED.get(), EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl != 0) {
                    CompoundTag weaponNBT = ((Player) (event.getSource().getEntity())).getMainHandItem().getTag();
                    //Prevent NPE
                    if (weaponNBT != null && event.getEntityLiving().getEncodeId() != null) {
                        if (!weaponNBT.contains("savor_the_tasted_target")) {
                            weaponNBT.putString("savor_the_tasted_target", event.getEntityLiving().getEncodeId());
                        } else {
                            String recordedTarget = weaponNBT.getString("savor_the_tasted_target");
                            if (recordedTarget.equals(event.getEntityLiving().getEncodeId())) {
                                float modifiedDamage = event.getAmount() + event.getEntityLiving().getRandom().nextInt(5) + enchantLvl * 4 - 1;
                                event.setAmount(modifiedDamage);
                            } else {
                                weaponNBT.putString("savor_the_tasted_target", event.getEntityLiving().getEncodeId());
                            }
                        }
                        ((Player) (event.getSource().getEntity())).getMainHandItem().setTag(weaponNBT);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doExoticHealerEnchantmentEvent(LivingHealEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                int enchantmentLvl = EnchantmentUtil.isPlayerArmorEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.EXOTIC_HEALER.get(), EnchantmentUtil.ArmorEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    int dice = event.getEntityLiving().getRandom().nextInt(100);
                    float modifier = 1 + (enchantmentLvl - 1) * 0.1F;
                    if (dice < 33) {
                        event.setCanceled(true);
                    } else if (dice < 66) {
                        event.setAmount(event.getAmount() * 2 * modifier);
                    } else if (dice < 90) {
                        event.setAmount(event.getAmount() * 3 * modifier);
                    } else if (dice < 91) {
                        event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, (int) (600 * modifier)));
                    } else if (dice < 92) {
                        event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, (int) (600 * modifier)));
                    } else if (dice < 93) {
                        event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.REGENERATION, (int) (600 * modifier)));
                    } else if (dice < 94) {
                        event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, (int) (600 * modifier)));
                    } else if (dice < 95) {
                        event.getEntityLiving().hurt(new DamageSource("flowingagony.exotic_healer"), event.getAmount() * modifier);
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

}

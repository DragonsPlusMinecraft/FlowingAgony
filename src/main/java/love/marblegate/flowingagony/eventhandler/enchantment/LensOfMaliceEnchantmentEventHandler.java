package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.PlaySoundWIthLocationPacket;
import love.marblegate.flowingagony.effect.EffectRegistry;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import love.marblegate.flowingagony.util.EntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber()
public class LensOfMaliceEnchantmentEventHandler {

    @SubscribeEvent
    public static void doVengeanceEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player && event.getSource().getEntity() instanceof LivingEntity) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(((Player) event.getEntityLiving()), EnchantmentRegistry.VENGEANCE.get(), EquipmentSlot.HEAD, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    if (event.getSource().getEntity() instanceof LivingEntity)
                        ((LivingEntity) event.getSource().getEntity()).addEffect(new MobEffectInstance(EffectRegistry.CURSED_HATRED.get(), 180, enchantmentLvl - 1));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doPerceivedMaliceEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player && event.getSource().getEntity() instanceof LivingEntity) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(((Player) event.getEntityLiving()), EnchantmentRegistry.PERCEIVED_MALICE.get(), EquipmentSlot.HEAD, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    if (event.getSource().getEntity() instanceof Player) {
                        ((Player) event.getSource().getEntity()).addEffect(new MobEffectInstance(EffectRegistry.CURSED_ANTIPATHY.get(), 200));
                        ((Player) event.getSource().getEntity()).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200));
                    } else {
                        ((LivingEntity) event.getSource().getEntity()).addEffect(new MobEffectInstance(EffectRegistry.CURSED_ANTIPATHY.get(), 200, enchantmentLvl - 1));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doMaliceOutbreakEnchantmentEvent(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof Player && event.getSource().getEntity() instanceof LivingEntity) {
            int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.MALICE_OUTBREAK.get(), EquipmentSlot.HEAD, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
            if (enchantmentLvl != 0) {
                if (event.getSource().getEntity() instanceof LivingEntity) {
                    ((LivingEntity) event.getSource().getEntity()).knockback(0.4f * enchantmentLvl, -event.getEntityLiving().getLookAngle().x, -event.getEntityLiving().getLookAngle().z);
                    //Play Sound FX
                    if (!event.getSource().getEntity().level.isClientSide) {
                        Networking.INSTANCE.send(
                                PacketDistributor.NEAR.with(
                                        () -> new PacketDistributor.TargetPoint(event.getSource().getEntity().getX(), event.getSource().getEntity().getY(), event.getSource().getEntity().getZ(),
                                                192, event.getSource().getEntity().level.dimension())
                                ),
                                new PlaySoundWIthLocationPacket(PlaySoundWIthLocationPacket.ModSoundType.MALICE_OUTBREAK_KNOCKBACK_SOUND, true,
                                        event.getSource().getEntity().getX(),
                                        event.getSource().getEntity().getY() + event.getSource().getEntity().getEyeHeight(),
                                        event.getSource().getEntity().getZ()));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doInfectiousMaliceEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player && event.getSource().getEntity() instanceof LivingEntity) {
                int enchantNum = EnchantmentUtil.isPlayerArmorEnchanted((Player) event.getEntityLiving(), EnchantmentRegistry.INFECTIOUS_MALICE.get(), EnchantmentUtil.ArmorEncCalOp.TOTAL_PIECE);
                if (enchantNum != 0) {
                    if (event.getSource().getEntity() instanceof Player) {
                        ((Player) event.getSource().getEntity()).addEffect(new MobEffectInstance(EffectRegistry.CURSED_HATRED.get(), 200 * enchantNum));
                    } else {
                        List<MobEffectInstance> effects = ((LivingEntity) event.getSource().getEntity()).getActiveEffects().stream().filter(EffectInstance ->
                                EffectInstance.getEffect().getCategory() == MobEffectCategory.HARMFUL).collect(Collectors.toList());
                        if (!effects.isEmpty()) {
                            List<LivingEntity> targets = EntityUtil.getTargetsOfSameType(event.getEntityLiving(), 8, 2, (LivingEntity) event.getSource().getEntity(), true);
                            int effectCount = effects.size();
                            if (effectCount <= enchantNum) {
                                for (MobEffectInstance effect : effects) {
                                    for (LivingEntity target : targets) {
                                        target.addEffect(effect);
                                    }
                                }
                            } else {
                                List<MobEffectInstance> selectedEffect = new ArrayList<>();
                                while (enchantNum > 0) {
                                    MobEffectInstance effect = effects.get(event.getEntityLiving().getRandom().nextInt(effectCount));
                                    if (!selectedEffect.contains(effect)) {
                                        selectedEffect.add(effect);
                                        enchantNum--;
                                    }
                                }
                                for (MobEffectInstance effect : selectedEffect) {
                                    for (LivingEntity target : targets) {
                                        target.addEffect(effect);
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
    public static void doISeeYouNowEnchantmentEvent(LivingSetAttackTargetEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getTarget() instanceof Player) {
                if (EnchantmentUtil.isPlayerItemEnchanted(((Player) event.getTarget()), EnchantmentRegistry.I_SEE_YOU_NOW.get(), EquipmentSlot.HEAD, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                    event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.GLOWING, 6000));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doBackAndFillEnchantmentEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player && (EntityUtil.isHostile(event.getEntityLiving(), false) && EntityUtil.isNeutral(event.getEntityLiving(), false))) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getSource().getEntity(), EnchantmentRegistry.BACK_AND_FILL.get(), EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    if (event.getEntityLiving().getKillCredit() == event.getSource().getEntity()) {
                        event.getEntityLiving().addEffect(new MobEffectInstance(EffectRegistry.BACK_AND_FILL_ENCHANTMENT_ACTIVE.get(), 100, enchantmentLvl - 1));
                    } else {
                        event.setAmount(event.getAmount() + enchantmentLvl + 1);
                    }
                }
            }
        }
    }
}

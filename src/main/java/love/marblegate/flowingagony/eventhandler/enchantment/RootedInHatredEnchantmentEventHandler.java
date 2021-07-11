package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.capibility.hatredbloodlinestatus.HatredBloodlineStatusCapability;
import love.marblegate.flowingagony.capibility.hatredbloodlinestatus.IHatredBloodlineStatusCapability;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.PlaySoundPacket;
import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber()
public class RootedInHatredEnchantmentEventHandler {

    @SubscribeEvent
    public static void doResentfulSoulEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(!event.isCanceled()) {
                if (event.getEntityLiving() instanceof PlayerEntity) {
                    if (event.getAmount() >= event.getEntityLiving().getHealth()) {
                        int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(((PlayerEntity) event.getEntityLiving()), EnchantmentRegistry.resentful_soul.get(), EquipmentSlotType.HEAD, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                        if (enchantmentLvl != 0) {
                            if (event.getEntityLiving().getLastAttackedEntityTime() <= (25 + enchantmentLvl * 25)) {
                                event.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doTooResentfulToDieEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if (!event.isCanceled()) {
                if(event.getEntityLiving() instanceof PlayerEntity && !event.getEntityLiving().equals(event.getSource().getTrueSource())) {
                    if (event.getAmount() >= event.getEntityLiving().getHealth()) {
                        int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(((PlayerEntity) event.getEntityLiving()), EnchantmentRegistry.too_resentful_to_die.get(), EquipmentSlotType.HEAD, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                        if (!((PlayerEntity)event.getEntityLiving()).isPotionActive(EffectRegistry.extreme_hatred.get())){
                            if (enchantmentLvl != 0) {
                                ((PlayerEntity)event.getEntityLiving()).heal(1 + enchantmentLvl * 3);
                                ((PlayerEntity)event.getEntityLiving()).addPotionEffect(new EffectInstance(EffectRegistry.extreme_hatred.get(), 7200));
                                event.setCanceled(true);
                                //Play Sound Effect - Stage 1
                                if (!event.getEntityLiving().world.isRemote) {
                                    Networking.INSTANCE.send(
                                            PacketDistributor.PLAYER.with(
                                                    () -> (ServerPlayerEntity) event.getEntityLiving()
                                            ),
                                            new PlaySoundPacket(PlaySoundPacket.ModSoundType.EXTREME_HATRED_FIRST_STAGE,true));
                                }
                            }
                        } else {
                            if (enchantmentLvl != 0){
                                int potionLvl = ((PlayerEntity)event.getEntityLiving()).getActivePotionEffect(EffectRegistry.extreme_hatred.get()).getAmplifier() + 1;
                                if (potionLvl == 1) {
                                    ((PlayerEntity)event.getEntityLiving()).heal(1 + enchantmentLvl * 2);
                                    ((PlayerEntity)event.getEntityLiving()).addPotionEffect(new EffectInstance(EffectRegistry.extreme_hatred.get(), 7200, 1));
                                    event.setCanceled(true);
                                    //Play Sound Effect - Stage 2
                                    if (!event.getEntityLiving().world.isRemote) {
                                        Networking.INSTANCE.send(
                                                PacketDistributor.PLAYER.with(
                                                        () -> (ServerPlayerEntity) event.getEntityLiving()
                                                ),
                                                new PlaySoundPacket(PlaySoundPacket.ModSoundType.EXTREME_HATRED_MEDIUM_STAGE,true));
                                    }
                                } else if (potionLvl == 2) {
                                    ((PlayerEntity)event.getEntityLiving()).heal(1 + enchantmentLvl);
                                    ((PlayerEntity)event.getEntityLiving()).addPotionEffect(new EffectInstance(EffectRegistry.extreme_hatred.get(), 7200, 2));
                                    event.setCanceled(true);
                                    //Play Sound Effect - Stage 3
                                    if (!event.getEntityLiving().world.isRemote) {
                                        Networking.INSTANCE.send(
                                                PacketDistributor.PLAYER.with(
                                                        () -> (ServerPlayerEntity) event.getEntityLiving()
                                                ),
                                                new PlaySoundPacket(PlaySoundPacket.ModSoundType.EXTREME_HATRED_FINAL_STAGE,true));
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
    public static void doOutrageousSpiritEnchantmentEvent(LivingHurtEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(((PlayerEntity)event.getSource().getTrueSource()), EnchantmentRegistry.outrageous_spirit.get(),EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantmentLvl!=0){
                    int negativeEffectCount = 0;
                    if(((PlayerEntity) event.getSource().getTrueSource()).isBurning()) negativeEffectCount++;
                    negativeEffectCount += ((PlayerEntity) event.getSource().getTrueSource()).getActivePotionEffects().stream().filter(
                            EffectInstance -> EffectInstance.getPotion().getEffectType()== EffectType.HARMFUL
                    ).count();
                    event.setAmount( event.getAmount() + negativeEffectCount * enchantmentLvl );
                }
            }
        }
    }

    @SubscribeEvent
    public static void doHatredBloodlikeEnchantmentEvent_acvtivateHatredBloodlineMarkOnDeath(LivingDeathEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                int enchantLvl = EnchantmentUtil.isPlayerArmorEnchanted((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.hatred_bloodline.get(), EnchantmentUtil.ArmorEncCalOp.TOTAL_LEVEL);
                if(enchantLvl!=0){
                    LazyOptional<IHatredBloodlineStatusCapability> statusCap = event.getEntityLiving().getCapability(HatredBloodlineStatusCapability.HATRED_BLOODLINE_STATUS_CAPABILITY);
                    statusCap.ifPresent(
                            cap-> cap.setActiveLevel(enchantLvl)
                    );
                }
            }
        }
    }

    @SubscribeEvent
    public static void doHatredBloodlikeEnchantmentEvent_activeEnchantmentEffectWhenRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.getPlayer().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                LazyOptional<IHatredBloodlineStatusCapability> statusCap = event.getEntityLiving().getCapability(HatredBloodlineStatusCapability.HATRED_BLOODLINE_STATUS_CAPABILITY);
                statusCap.ifPresent(
                        cap -> {
                            if (cap.getActiveLevel() != 0) {
                                int hatredBloodlineLevel = cap.getActiveLevel();
                                event.getPlayer().addPotionEffect(new EffectInstance(EffectRegistry.hatred_bloodline_enchantment_active.get(), 800 * hatredBloodlineLevel, hatredBloodlineLevel - 1));
                                cap.setActiveLevel(0);
                            }
                        }
                );
            }
        }
    }

    @SubscribeEvent
    public static void doFreshRevengeEnchantmentEvent_applyBuff(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(((PlayerEntity)event.getSource().getTrueSource()), EnchantmentRegistry.fresh_revenge.get(),EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantmentLvl!=0){
                    if(event.getEntityLiving().getLastAttackedEntityTime()<=(20+enchantmentLvl*4)){
                        ((PlayerEntity)event.getSource().getTrueSource()).addPotionEffect(new EffectInstance(EffectRegistry.fresh_revenge_enchantment_active.get(),200,enchantmentLvl-1));
                    }
                }
            }
        }
    }
}

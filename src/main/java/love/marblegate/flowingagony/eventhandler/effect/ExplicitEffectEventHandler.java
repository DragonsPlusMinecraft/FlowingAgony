package love.marblegate.flowingagony.eventhandler.effect;

import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.effect.EffectRegistry;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.PlaySoundPacket;
import love.marblegate.flowingagony.network.packet.RemoveEffectSyncToClientPacket;
import love.marblegate.flowingagony.util.EntityUtil;
import love.marblegate.flowingagony.util.PlayerUtil;
import love.marblegate.flowingagony.util.StandardUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;

@Mod.EventBusSubscriber()
public class ExplicitEffectEventHandler {
    @SubscribeEvent
    public static void doCursedHatredEffectEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving().hasEffect(EffectRegistry.CURSED_HATRED.get()) && event.getSource() != CustomDamageSource.CURSED_HATRED) {
                int potionLvl = event.getEntityLiving().getEffect(EffectRegistry.CURSED_HATRED.get()).getAmplifier() + 1;
                event.getEntityLiving().removeEffect(EffectRegistry.CURSED_HATRED.get());
                event.getEntityLiving().hurt(CustomDamageSource.CURSED_HATRED, (float) (potionLvl * 2 * (((event.getEntityLiving() instanceof Player) ? (0.9 - 0.1 * Math.random()) : 1))));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void doExtremeHatredEffectEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player) {
                if (((Player) (event.getSource().getEntity())).hasEffect(EffectRegistry.EXTREME_HATRED.get())) {
                    int potionLvl = ((Player) (event.getSource().getEntity())).getEffect(EffectRegistry.EXTREME_HATRED.get()).getAmplifier() + 1;
                    if (event.getAmount() * (1 + potionLvl) >= event.getEntityLiving().getHealth()) {
                        ((Player) (event.getSource().getEntity())).removeEffect(EffectRegistry.EXTREME_HATRED.get());
                        //Remove Sound Effect If Killing Action Is Confirmed
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayer) event.getSource().getEntity()
                                ),
                                new PlaySoundPacket(PlaySoundPacket.ModSoundType.EXTREME_HATRED_FIRST_STAGE, false));
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayer) event.getSource().getEntity()
                                ),
                                new PlaySoundPacket(PlaySoundPacket.ModSoundType.EXTREME_HATRED_MEDIUM_STAGE, false));
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayer) event.getSource().getEntity()
                                ),
                                new PlaySoundPacket(PlaySoundPacket.ModSoundType.EXTREME_HATRED_FINAL_STAGE, false));
                    }
                    event.setAmount(event.getAmount() * (1 + potionLvl));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doCurseOfUndeadEffectEvent_applyBurning_setPlayerOnFireIfNoHelmet(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (!event.player.level.isClientSide()) {
                if (event.player.hasEffect(EffectRegistry.CURSE_OF_UNDEAD.get())) {
                    if (event.player.level.getDayTime() % 24000 < 12000) {
                        if (!event.player.level.isThundering() && !event.player.level.isRaining()) {
                            if (event.player.level.canSeeSky(event.player.blockPosition())) {
                                if (!PlayerUtil.hasHelmet(event.player))
                                    event.player.setSecondsOnFire(5);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doCurseOfUndeadEffectEvent_changeFoodEffect(LivingEntityUseItemEvent.Finish event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                if (event.getItem().getItem().equals(Items.ROTTEN_FLESH)) {
                    if (event.getEntityLiving().hasEffect(EffectRegistry.CURSE_OF_UNDEAD.get())) {
                        ((Player) event.getEntityLiving()).heal(1);
                        ((Player) event.getEntityLiving()).addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 60));
                    }
                } else if (event.getItem().getItem().equals(Items.ENCHANTED_GOLDEN_APPLE)) {
                    if (event.getEntityLiving().hasEffect(EffectRegistry.CURSE_OF_UNDEAD.get())) {
                        event.getEntityLiving().removeEffect(EffectRegistry.CURSE_OF_UNDEAD.get());
                        //Notify client to remove effect
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayer) event.getEntityLiving()
                                ),
                                new RemoveEffectSyncToClientPacket(EffectRegistry.CURSE_OF_UNDEAD.get()));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doCurseOfUndeadEffectEvent_applyArmorDamageAmplifier(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide() && !event.isCanceled()) {
            if (event.getEntityLiving() instanceof Player) {
                if (event.getEntityLiving().hasEffect(EffectRegistry.CURSE_OF_UNDEAD.get())) {
                    if (event.getSource().isFire()) {
                        event.setAmount(event.getAmount() * 2);
                        if (PlayerUtil.hasHelmet((Player) event.getEntityLiving())) {
                            ((Player) event.getEntityLiving()).getItemBySlot(EquipmentSlot.HEAD).hurtAndBreak(1, event.getEntityLiving(), a -> {
                            });
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doAgonyResonanceEffectEvent(PotionEvent.PotionAddedEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getPotionEffect().getEffect().equals(EffectRegistry.AGONY_RESONANCE.get())) {
                if (event.getEntityLiving().hasEffect(EffectRegistry.BEEN_RESONATED.get())) {
                    event.getEntityLiving().removeEffect(EffectRegistry.BEEN_RESONATED.get());
                    //Do not Sync to Client
                    //It is due to I did not write packet to handle remove mob's effect
                }
                List<LivingEntity> entities = EntityUtil.getTargetsExceptOneself(event.getEntityLiving(), 8, 2, x -> true);
                entities.forEach(LivingEntity -> LivingEntity.addEffect(new MobEffectInstance(EffectRegistry.BEEN_RESONATED.get(), event.getPotionEffect().getDuration(), event.getPotionEffect().getAmplifier())));
            }
        }
    }

    @SubscribeEvent
    public static void doBeenResonatedEffectEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving().hasEffect(EffectRegistry.BEEN_RESONATED.get()) && event.getSource() != CustomDamageSource.AGONY_RESONANCE) {
                List<LivingEntity> entities = EntityUtil.getTargetsExceptOneself(event.getEntityLiving(), 8, 2, livingEntity ->
                        livingEntity.hasEffect(EffectRegistry.AGONY_RESONANCE.get()));
                int damageIndex = event.getEntityLiving().getEffect(EffectRegistry.BEEN_RESONATED.get()).getAmplifier() + 1;
                entities.forEach(LivingEntity -> LivingEntity.hurt(CustomDamageSource.AGONY_RESONANCE, event.getAmount() * (0.35F + damageIndex * 0.15F)));
            }
        }
    }

    @SubscribeEvent
    public static void onLetMeSavorItEffectEvent_reduceDamage(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player) {
                if (((Player) event.getSource().getEntity()).hasEffect(EffectRegistry.LET_ME_SAVOR_IT.get())) {
                    int effectLvl = ((Player) event.getSource().getEntity()).getEffect(EffectRegistry.LET_ME_SAVOR_IT.get()).getAmplifier() + 1;
                    event.setAmount(event.getAmount() * (1 - 0.09F * effectLvl));
                }
            }
        }
    }


    @SubscribeEvent
    public static void onLetMeSavorItEffectEvent_reflectDamage(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player && StandardUtil.shouldReflectDamage(event)) {
                if ((event.getEntityLiving()).hasEffect(EffectRegistry.LET_ME_SAVOR_IT.get())) {
                    int effectLvl = (event.getEntityLiving().getEffect(EffectRegistry.LET_ME_SAVOR_IT.get())).getAmplifier() + 1;
                    if (event.getSource().getEntity() instanceof LivingEntity) {
                        //If the enemy has same buff, do not reflect damage
                        if (!((LivingEntity) event.getSource().getEntity()).hasEffect(EffectRegistry.LET_ME_SAVOR_IT.get()))
                            event.getSource().getEntity().
                                    hurt(CustomDamageSource.causeLetMeSavorItDamage(event.getEntityLiving()), effectLvl * event.getAmount());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLetMeSavorItEffectEvent_cancelEffect(TickEvent.PlayerTickEvent event) {
        if (!event.player.level.isClientSide()) {
            if (event.phase == TickEvent.Phase.START) {
                if ((event.player.hasEffect(EffectRegistry.LET_ME_SAVOR_IT.get()))) {
                    if (event.player.getHealth() > 12) {
                        event.player.removeEffectNoUpdate(EffectRegistry.LET_ME_SAVOR_IT.get());
                        //Sync to Client
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayer) event.player
                                ),
                                new RemoveEffectSyncToClientPacket(EffectRegistry.LET_ME_SAVOR_IT.get()));
                    }


                }
            }
        }
    }

}

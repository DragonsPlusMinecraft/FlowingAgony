package love.marblegate.flowingagony.eventhandler.effect;

import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.PlaySoundPacket;
import love.marblegate.flowingagony.network.packet.RemoveEffectSyncToClientPacket;
import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.util.EntityUtil;
import love.marblegate.flowingagony.util.PlayerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

@Mod.EventBusSubscriber()
public class ExplicitEffectEventHandler {
    @SubscribeEvent
    public static void doCursedHatredEffectEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving().isPotionActive(EffectRegistry.cursed_hatred.get())){
                int potionLvl = event.getEntityLiving().getActivePotionEffect(EffectRegistry.cursed_hatred.get()).getAmplifier()+1;
                event.getEntityLiving().removePotionEffect(EffectRegistry.cursed_hatred.get());
                event.getEntityLiving().attackEntityFrom(CustomDamageSource.CURSED_HATRED, (float) (potionLvl * 2 * (((event.getEntityLiving() instanceof PlayerEntity)?(0.9 - 0.1 * Math.random()):1))));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void doExtremeHatredEffectEvent(LivingHurtEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                if(((PlayerEntity)(event.getSource().getTrueSource())).isPotionActive(EffectRegistry.extreme_hatred.get())){
                    int potionLvl = event.getEntityLiving().getActivePotionEffect(EffectRegistry.extreme_hatred.get()).getAmplifier()+1;
                    if(event.getAmount()*(1+potionLvl)>=event.getEntityLiving().getHealth()){
                        ((PlayerEntity)(event.getSource().getTrueSource())).removePotionEffect(EffectRegistry.extreme_hatred.get());
                        //Remove Sound Effect If Killing Action Is Confirmed
                        if (!event.getSource().getTrueSource().world.isRemote) {
                            Networking.INSTANCE.send(
                                    PacketDistributor.PLAYER.with(
                                            () -> (ServerPlayerEntity) event.getSource().getTrueSource()
                                    ),
                                    new PlaySoundPacket(PlaySoundPacket.ModSoundType.EXTREME_HATRED_FIRST_STAGE,false));
                            Networking.INSTANCE.send(
                                    PacketDistributor.PLAYER.with(
                                            () -> (ServerPlayerEntity) event.getSource().getTrueSource()
                                    ),
                                    new PlaySoundPacket(PlaySoundPacket.ModSoundType.EXTREME_HATRED_MEDIUM_STAGE,false));
                            Networking.INSTANCE.send(
                                    PacketDistributor.PLAYER.with(
                                            () -> (ServerPlayerEntity) event.getSource().getTrueSource()
                                    ),
                                    new PlaySoundPacket(PlaySoundPacket.ModSoundType.EXTREME_HATRED_FINAL_STAGE,false));
                        }
                    }
                    event.setAmount(event.getAmount()*(1+potionLvl));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doCurseOfUndeadEffectEvent_applyBurning_setPlayerOnFireIfNoHelmet(TickEvent.PlayerTickEvent event){
        if(event.phase == TickEvent.Phase.START){
            if(!event.player.world.isRemote()){
                if(event.player.isPotionActive(EffectRegistry.curse_of_undead.get())){
                    if(event.player.world.getDayTime()%24000<12000){
                        if(!event.player.world.isThundering() && !event.player.world.isRaining()){
                            if(event.player.world.canSeeSky(event.player.getPosition())){
                                if(PlayerUtil.hasHelmet(event.player))
                                    event.player.setFire(5);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doCurseOfUndeadEffectEvent_changeFoodEffect(LivingEntityUseItemEvent.Finish event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                if(event.getItem().getItem().equals(Items.ROTTEN_FLESH)){
                    if(event.getEntityLiving().isPotionActive(EffectRegistry.curse_of_undead.get())){
                        ((PlayerEntity)event.getEntityLiving()).heal(1);
                        ((PlayerEntity)event.getEntityLiving()).addPotionEffect(new EffectInstance(Effects.STRENGTH,60));
                    }
                }
                else if(event.getItem().getItem().equals(Items.ENCHANTED_GOLDEN_APPLE)){
                    if(event.getEntityLiving().isPotionActive(EffectRegistry.curse_of_undead.get())){
                        event.getEntityLiving().removePotionEffect(EffectRegistry.curse_of_undead.get());
                        //Notify client to remove effect
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayerEntity) event.getEntityLiving()
                                ),
                                new RemoveEffectSyncToClientPacket(EffectRegistry.curse_of_undead.get()));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doCurseOfUndeadEffectEvent_applyArmorDamageAmplifier(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()&&!event.isCanceled()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                if(event.getEntityLiving().isPotionActive(EffectRegistry.curse_of_undead.get())){
                    if(event.getSource().isFireDamage()){
                        event.setAmount(event.getAmount()*2);
                        if(PlayerUtil.hasHelmet((PlayerEntity) event.getEntityLiving())){
                            ((PlayerEntity)event.getEntityLiving()).getItemStackFromSlot(EquipmentSlotType.HEAD).damageItem(1,event.getEntityLiving(),a->{});
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doAgonyResonanceEffectEvent(PotionEvent.PotionAddedEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getPotionEffect().getPotion().equals(EffectRegistry.agony_resonance.get())){
                List<LivingEntity> entities = EntityUtil.getTargetListExceptOneself(event.getEntityLiving(),8,2, x->true);
                entities.forEach(LivingEntity->{
                    if(LivingEntity.isPotionActive(EffectRegistry.been_resonated.get())){
                        LivingEntity.removePotionEffect(EffectRegistry.been_resonated.get());
                        //Sync to Client
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayerEntity) event.getEntityLiving()
                                ),
                                new RemoveEffectSyncToClientPacket(EffectRegistry.been_resonated.get()));
                    }
                    LivingEntity.addPotionEffect(new EffectInstance(EffectRegistry.been_resonated.get(),event.getPotionEffect().getDuration(),event.getPotionEffect().getAmplifier()));
                });
            }
        }
    }

    @SubscribeEvent
    public static void doBeenResonatedEffectEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving().isPotionActive(EffectRegistry.been_resonated.get())){
                List<LivingEntity> entities = EntityUtil.getTargetListExceptOneself(event.getEntityLiving(),8,2, LivingEntity->
                    LivingEntity.isPotionActive(EffectRegistry.agony_resonance.get()));
                int damageIndex = event.getEntityLiving().getActivePotionEffect(EffectRegistry.been_resonated.get()).getAmplifier() + 1;
                entities.forEach(LivingEntity-> LivingEntity.attackEntityFrom(new DamageSource("agony_resonance"),event.getAmount() * (0.35F + damageIndex * 0.15F)));
            }
        }
    }

    @SubscribeEvent
    public static void onLetMeSavorItEffectEvent_dealDamage(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                if ((event.getEntityLiving()).isPotionActive(EffectRegistry.let_me_savor_it.get())) {
                    int effectLvl = (event.getEntityLiving().getActivePotionEffect(EffectRegistry.let_me_savor_it.get())).getAmplifier() + 1;
                    if (event.getSource().getTrueSource() instanceof LivingEntity) {
                        if(!((LivingEntity)event.getSource().getTrueSource()).isPotionActive(EffectRegistry.let_me_savor_it.get()))
                            event.getSource().getTrueSource().
                                    attackEntityFrom(CustomDamageSource.causeLetMeSavorItDamage(event.getEntityLiving()),effectLvl * event.getAmount());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLetMeSavorItEffectEvent_cancelEffect(TickEvent.PlayerTickEvent event) {
        if (!event.player.world.isRemote()) {
            if(event.phase== TickEvent.Phase.START){
                if ((event.player.isPotionActive(EffectRegistry.let_me_savor_it.get()))){
                    if(event.player.getHealth()>12)
                        event.player.removeActivePotionEffect(EffectRegistry.let_me_savor_it.get());
                }
            }
        }
    }

}

package love.marblegate.flowingagony.eventhandler.effect;

import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.PlaySoundPacket;
import love.marblegate.flowingagony.network.packet.RemoveEffectSyncToClientPacket;
import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.util.EntityUtil;
import love.marblegate.flowingagony.util.PlayerUtil;
import love.marblegate.flowingagony.util.StandardUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
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
            if(event.getEntityLiving().isPotionActive(EffectRegistry.CURSED_HATRED.get()) && event.getSource()!=CustomDamageSource.CURSED_HATRED){
                int potionLvl = event.getEntityLiving().getActivePotionEffect(EffectRegistry.CURSED_HATRED.get()).getAmplifier()+1;
                event.getEntityLiving().removePotionEffect(EffectRegistry.CURSED_HATRED.get());
                event.getEntityLiving().attackEntityFrom(CustomDamageSource.CURSED_HATRED, (float) (potionLvl * 2 * (((event.getEntityLiving() instanceof PlayerEntity)?(0.9 - 0.1 * Math.random()):1))));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void doExtremeHatredEffectEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                if(((PlayerEntity) (event.getSource().getTrueSource())).isPotionActive(EffectRegistry.EXTREME_HATRED.get())){
                    int potionLvl = ((PlayerEntity) (event.getSource().getTrueSource())).getActivePotionEffect(EffectRegistry.EXTREME_HATRED.get()).getAmplifier()+1;
                    if(event.getAmount()*(1+potionLvl)>=event.getEntityLiving().getHealth()){
                        ((PlayerEntity)(event.getSource().getTrueSource())).removePotionEffect(EffectRegistry.EXTREME_HATRED.get());
                        //Remove Sound Effect If Killing Action Is Confirmed
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
                    event.setAmount(event.getAmount()*(1+potionLvl));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doCurseOfUndeadEffectEvent_applyBurning_setPlayerOnFireIfNoHelmet(TickEvent.PlayerTickEvent event){
        if(event.phase == TickEvent.Phase.START){
            if(!event.player.world.isRemote()){
                if(event.player.isPotionActive(EffectRegistry.CURSE_OF_UNDEAD.get())){
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
                    if(event.getEntityLiving().isPotionActive(EffectRegistry.CURSE_OF_UNDEAD.get())){
                        ((PlayerEntity)event.getEntityLiving()).heal(1);
                        ((PlayerEntity)event.getEntityLiving()).addPotionEffect(new EffectInstance(Effects.STRENGTH,60));
                    }
                }
                else if(event.getItem().getItem().equals(Items.ENCHANTED_GOLDEN_APPLE)){
                    if(event.getEntityLiving().isPotionActive(EffectRegistry.CURSE_OF_UNDEAD.get())){
                        event.getEntityLiving().removePotionEffect(EffectRegistry.CURSE_OF_UNDEAD.get());
                        //Notify client to remove effect
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayerEntity) event.getEntityLiving()
                                ),
                                new RemoveEffectSyncToClientPacket(EffectRegistry.CURSE_OF_UNDEAD.get()));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doCurseOfUndeadEffectEvent_applyArmorDamageAmplifier(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()&&!event.isCanceled()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                if(event.getEntityLiving().isPotionActive(EffectRegistry.CURSE_OF_UNDEAD.get())){
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
            if(event.getPotionEffect().getPotion().equals(EffectRegistry.AGONY_RESONANCE.get())){
                if(event.getEntityLiving().isPotionActive(EffectRegistry.BEEN_RESONATED.get())){
                    event.getEntityLiving().removePotionEffect(EffectRegistry.BEEN_RESONATED.get());
                    //Do not Sync to Client
                    //It is due to I did not write packet to handle remove mob's effect
                }
                List<LivingEntity> entities = EntityUtil.getTargetsExceptOneself(event.getEntityLiving(),8,2, x->true);
                entities.forEach(LivingEntity-> LivingEntity.addPotionEffect(new EffectInstance(EffectRegistry.BEEN_RESONATED.get(),event.getPotionEffect().getDuration(),event.getPotionEffect().getAmplifier())));
            }
        }
    }

    @SubscribeEvent
    public static void doBeenResonatedEffectEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving().isPotionActive(EffectRegistry.BEEN_RESONATED.get()) && event.getSource()!=CustomDamageSource.AGONY_RESONANCE){
                List<LivingEntity> entities = EntityUtil.getTargetsExceptOneself(event.getEntityLiving(),8,2, LivingEntity->
                    LivingEntity.isPotionActive(EffectRegistry.AGONY_RESONANCE.get()));
                int damageIndex = event.getEntityLiving().getActivePotionEffect(EffectRegistry.BEEN_RESONATED.get()).getAmplifier() + 1;
                entities.forEach(LivingEntity-> LivingEntity.attackEntityFrom(CustomDamageSource.AGONY_RESONANCE,event.getAmount() * (0.35F + damageIndex * 0.15F)));
            }
        }
    }

    @SubscribeEvent
    public static void onLetMeSavorItEffectEvent_reduceDamage(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getSource().getTrueSource() instanceof PlayerEntity) {
                if (((PlayerEntity) event.getSource().getTrueSource()).isPotionActive(EffectRegistry.LET_ME_SAVOR_IT.get())) {
                    int effectLvl = ((PlayerEntity) event.getSource().getTrueSource()).getActivePotionEffect(EffectRegistry.LET_ME_SAVOR_IT.get()).getAmplifier() + 1;
                    event.setAmount(event.getAmount() * (1 - 0.09F*effectLvl));
                }
            }
        }
    }


    @SubscribeEvent
    public static void onLetMeSavorItEffectEvent_reflectDamage(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity && StandardUtil.shouldReflectDamage(event)) {
                if ((event.getEntityLiving()).isPotionActive(EffectRegistry.LET_ME_SAVOR_IT.get())) {
                    int effectLvl = (event.getEntityLiving().getActivePotionEffect(EffectRegistry.LET_ME_SAVOR_IT.get())).getAmplifier() + 1;
                    if (event.getSource().getTrueSource() instanceof LivingEntity) {
                        //If the enemy has same buff, do not reflect damage
                        if(!((LivingEntity)event.getSource().getTrueSource()).isPotionActive(EffectRegistry.LET_ME_SAVOR_IT.get()))
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
                if ((event.player.isPotionActive(EffectRegistry.LET_ME_SAVOR_IT.get()))){
                    if(event.player.getHealth()>12){
                        event.player.removeActivePotionEffect(EffectRegistry.LET_ME_SAVOR_IT.get());
                        //Sync to Client
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayerEntity) event.player
                                ),
                                new RemoveEffectSyncToClientPacket(EffectRegistry.LET_ME_SAVOR_IT.get()));
                    }


                }
            }
        }
    }

}

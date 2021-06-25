package love.marblegate.flowingagony.eventhandler.effect;

import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.PlaySoundPacket;
import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.util.EntityUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
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

import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber()
public class ExplicitEffectEventHandler {
    @SubscribeEvent
    public static void doCursedHatredEffectEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving().isPotionActive(EffectRegistry.cursed_hatred_effect.get())){
                int potionLvl = event.getEntityLiving().getActivePotionEffect(EffectRegistry.cursed_hatred_effect.get()).getAmplifier()+1;
                event.setAmount((float)(event.getAmount()*(1.5+potionLvl*0.3)));
                event.getEntityLiving().removePotionEffect(EffectRegistry.cursed_hatred_effect.get());
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void doExtremeHatredEffectEvent(LivingHurtEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                if(((PlayerEntity)(event.getSource().getTrueSource())).isPotionActive(EffectRegistry.extreme_hatred_effect.get())){
                    int potionLvl = event.getEntityLiving().getActivePotionEffect(EffectRegistry.extreme_hatred_effect.get()).getAmplifier()+1;
                    if(event.getAmount()*(1+potionLvl)>=event.getEntityLiving().getHealth()){
                        ((PlayerEntity)(event.getSource().getTrueSource())).removePotionEffect(EffectRegistry.extreme_hatred_effect.get());
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
    public static void doCurseOfUndeadEffectEvent_applyBurningInSunlightEffect(TickEvent.PlayerTickEvent event){
        if(event.phase == TickEvent.Phase.START){
            if(!event.player.world.isRemote()){
                if(event.player.isPotionActive(EffectRegistry.curse_of_undead_effect.get())){
                    if(event.player.world.getDayTime()%24000<12000){
                        if(!(event.player.world.isThundering()&&event.player.world.isThundering())){
                            if(event.player.world.canSeeSky(event.player.getPosition())){
                                Iterator<ItemStack> armor = event.player.getArmorInventoryList().iterator();
                                boolean hasHeadArmor = false;
                                while(armor.hasNext()){
                                    ItemStack itemStack = armor.next();
                                    if(itemStack.getItem() instanceof ArmorItem){
                                        if(((ArmorItem) itemStack.getItem()).getEquipmentSlot().equals(EquipmentSlotType.HEAD)){
                                            hasHeadArmor = true;
                                            break;
                                        }
                                    }
                                }
                                if(!hasHeadArmor){
                                    event.player.setFire(5);
                                }
                            }
                        }
                    }
                    if(event.player.isPotionActive(Effects.HUNGER)){
                        event.player.removeActivePotionEffect(Effects.HUNGER);
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
                    if(event.getEntityLiving().isPotionActive(EffectRegistry.curse_of_undead_effect.get())){
                        event.getEntityLiving().heal(4);
                    }
                }
                else if(event.getItem().getItem().equals(Items.ENCHANTED_GOLDEN_APPLE)){
                    if(event.getEntityLiving().isPotionActive(EffectRegistry.curse_of_undead_effect.get())){
                        event.getEntityLiving().removePotionEffect(EffectRegistry.curse_of_undead_effect.get());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doCurseOfUndeadEffectEvent_applyArmorDamageAmplifier(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                if(event.getEntityLiving().isPotionActive(EffectRegistry.curse_of_undead_effect.get())){
                    if(event.getSource().getDamageType().equals("inFire")||event.getSource().getDamageType().equals("onFire")||event.getSource().getDamageType().equals("lava")){
                        event.setAmount(event.getAmount()*2);
                        for (ItemStack itemStack : event.getEntityLiving().getArmorInventoryList()) {
                            if (itemStack.getItem() instanceof ArmorItem) {
                                if (((ArmorItem) itemStack.getItem()).getEquipmentSlot().equals(EquipmentSlotType.HEAD)) {
                                    if (itemStack.getDamage() > 1) {
                                        itemStack.setDamage(itemStack.getDamage() - 1);
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
    public static void doAgonyResonanceEffectEvent(PotionEvent.PotionAddedEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getPotionEffect().getPotion().equals(EffectRegistry.agony_resonance_effect.get())){
                List<LivingEntity> entities = EntityUtil.getTargetListExceptOneself(event.getEntityLiving(),8,2, x->true);
                entities.forEach(LivingEntity->{
                    if(LivingEntity.isPotionActive(EffectRegistry.been_resonated_effect.get()))
                        LivingEntity.removePotionEffect(EffectRegistry.been_resonated_effect.get());
                    LivingEntity.addPotionEffect(new EffectInstance(EffectRegistry.been_resonated_effect.get(),400));
                });
            }
        }
    }

    @SubscribeEvent
    public static void doBeenResonatedEffectEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving().isPotionActive(EffectRegistry.been_resonated_effect.get())){
                List<LivingEntity> entities = EntityUtil.getTargetListExceptOneself(event.getEntityLiving(),8,2, LivingEntity->
                    LivingEntity.isPotionActive(EffectRegistry.agony_resonance_effect.get()));
                entities.forEach(LivingEntity-> LivingEntity.attackEntityFrom(new DamageSource("agony_resonance"),event.getAmount()));
            }
        }
    }

    @SubscribeEvent
    public static void onLetMeSavorItEffectEvent_dealDamage(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                if ((event.getEntityLiving()).isPotionActive(EffectRegistry.let_me_savor_it_effect.get())) {
                    int effectLvl = (event.getEntityLiving().getActivePotionEffect(EffectRegistry.let_me_savor_it_effect.get())).getAmplifier() + 1;
                    if (event.getSource().getTrueSource() instanceof LivingEntity) {
                        if(!((LivingEntity)event.getSource().getTrueSource()).isPotionActive(EffectRegistry.let_me_savor_it_effect.get()))
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
                if ((event.player.isPotionActive(EffectRegistry.let_me_savor_it_effect.get()))){
                    if(event.player.getHealth()>12)
                        event.player.removeActivePotionEffect(EffectRegistry.let_me_savor_it_effect.get());
                }
            }
        }
    }

}

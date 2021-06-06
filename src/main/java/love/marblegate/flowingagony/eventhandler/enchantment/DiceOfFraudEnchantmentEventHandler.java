package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.GodRollingDiceUtil;
import love.marblegate.flowingagony.util.PlayerUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
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
    public static void doTricksterEnchantmentEvent(AttackEntityEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getTarget() instanceof LivingEntity){
                int enchantLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel(event.getPlayer(),EnchantmentRegistry.trickster_enchantment.get(),EquipmentSlotType.MAINHAND);
                if(enchantLvl==1){
                    GodRollingDiceUtil.appendixEffectForTrickster((LivingEntity) event.getTarget(),((LivingEntity) event.getTarget()).getRNG().nextInt(5)+1);
                }
                else if(enchantLvl==2){
                    int aEffect = ((LivingEntity) event.getTarget()).getRNG().nextInt(5)+1;
                    int bEffect = ((LivingEntity) event.getTarget()).getRNG().nextInt(5)+1;
                    while(aEffect==bEffect){
                        bEffect = ((LivingEntity) event.getTarget()).getRNG().nextInt(5)+1;
                    }
                    GodRollingDiceUtil.appendixEffectForTrickster((LivingEntity) event.getTarget(),aEffect);
                    GodRollingDiceUtil.appendixEffectForTrickster((LivingEntity) event.getTarget(),bEffect);
                }
            }
        }
    }

    @SubscribeEvent
    public static void doAnEnchantedAppleADayEnchantmentEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        if(!event.getPlayer().world.isRemote()){
            int enchantNum = PlayerUtil.getTotalPiecePlayerArmorEnchantedSameEnchantment(event.getPlayer(),EnchantmentRegistry.an_enchanted_apple_a_day.get());
            if(enchantNum!=0){
                if(enchantNum == 1){
                    int tempInt = event.getPlayer().getRNG().nextInt(4);
                    switch (tempInt){
                        case 0:
                            event.getPlayer().addPotionEffect(new EffectInstance(Effects.ABSORPTION,2400,3));
                            break;
                        case 1:
                            event.getPlayer().addPotionEffect(new EffectInstance(Effects.REGENERATION,400,1));
                            break;
                        case 2:
                            event.getPlayer().addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE,6000));
                            break;
                        case 3:
                            event.getPlayer().addPotionEffect(new EffectInstance(Effects.RESISTANCE,6000));
                            break;
                    }
                } else if(enchantNum == 4){
                    event.getPlayer().addPotionEffect(new EffectInstance(Effects.ABSORPTION,2400,3));
                    event.getPlayer().addPotionEffect(new EffectInstance(Effects.REGENERATION,400,1));
                    event.getPlayer().addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE,6000));
                    event.getPlayer().addPotionEffect(new EffectInstance(Effects.RESISTANCE,6000));
                } else if(enchantNum < 4){
                    Set<Integer> temp = new HashSet<Integer>();
                    int tempCount = enchantNum;
                    while(tempCount>0){
                        int tempInt = event.getPlayer().getRNG().nextInt(4);
                        if(temp.contains(tempInt)){
                            continue;
                        } else {
                            switch (tempInt){
                                case 0:
                                    event.getPlayer().addPotionEffect(new EffectInstance(Effects.ABSORPTION,2400,3));
                                    break;
                                case 1:
                                    event.getPlayer().addPotionEffect(new EffectInstance(Effects.REGENERATION,400,1));
                                    break;
                                case 2:
                                    event.getPlayer().addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE,6000));
                                    break;
                                case 3:
                                    event.getPlayer().addPotionEffect(new EffectInstance(Effects.RESISTANCE,6000));
                                    break;
                            }
                            temp.add(tempInt);
                            tempCount--;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void doDeathpunkEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(!event.isCanceled()){
                if(event.getEntityLiving() instanceof PlayerEntity) {
                    if (event.getAmount() >= event.getEntityLiving().getHealth() && (!event.getSource().getDamageType().equals("outOfWorld"))) {
                        if (PlayerUtil.isPlayerSpecificSlotEnchanted(((PlayerEntity) event.getEntityLiving()), EnchantmentRegistry.deathpunk_enchantment.get(), EquipmentSlotType.CHEST)) {
                            int solution = event.getEntityLiving().getRNG().nextInt(4);
                            int health = MathHelper.floor(((PlayerEntity) event.getEntityLiving()).getHealth());
                            int maxHealth = MathHelper.floor(event.getEntityLiving().getMaxHealth());
                            boolean damageEnchantment = false;
                            switch (solution) {
                                case 0:
                                    int foodLevel = ((PlayerEntity) event.getEntityLiving()).getFoodStats().getFoodLevel() + MathHelper.floor(((PlayerEntity) event.getEntityLiving()).getFoodStats().getSaturationLevel());
                                    if (foodLevel <= health) damageEnchantment = true;
                                    else {
                                        if (foodLevel > maxHealth) foodLevel = maxHealth;
                                        event.getEntityLiving().setHealth(foodLevel);
                                        ((PlayerEntity) event.getEntityLiving()).getFoodStats().setFoodLevel(health);
                                        ((PlayerEntity) event.getEntityLiving()).getFoodStats().setFoodSaturationLevel(0);
                                    }
                                    break;
                                case 1:
                                    int oxygenLevel = MathHelper.floor(((PlayerEntity) event.getEntityLiving()).getAir() / (((PlayerEntity) event.getEntityLiving()).getMaxAir() / (float) maxHealth));
                                    if (oxygenLevel <= health) damageEnchantment = true;
                                    else {
                                        if (oxygenLevel > maxHealth) oxygenLevel = maxHealth;
                                        event.getEntityLiving().setHealth(oxygenLevel);
                                        ((PlayerEntity) event.getEntityLiving()).setAir(health * (((PlayerEntity) event.getEntityLiving()).getMaxAir() / maxHealth));
                                    }
                                    break;
                                case 2:
                                    int expPoint = ((PlayerEntity) event.getEntityLiving()).experienceTotal;
                                    int exchangeCost = (MathHelper.floor(event.getEntityLiving().getMaxHealth()) - health) * 30;
                                    if (expPoint <= exchangeCost) damageEnchantment = true;
                                    else {
                                        event.getEntityLiving().setHealth(event.getEntityLiving().getMaxHealth());
                                        ((PlayerEntity) event.getEntityLiving()).giveExperiencePoints(-exchangeCost);
                                    }
                                    break;
                                case 3:
                                    damageEnchantment = true;
                                    break;
                            }
                            if (damageEnchantment) {
                                event.getEntityLiving().setHealth(event.getEntityLiving().getMaxHealth());
                                Map<Enchantment, Integer> enchantmentList = EnchantmentHelper.getEnchantments(((PlayerEntity) event.getEntityLiving()).getItemStackFromSlot(EquipmentSlotType.CHEST));
                                enchantmentList.remove(EnchantmentRegistry.deathpunk_enchantment.get());
                                EnchantmentHelper.setEnchantments(enchantmentList, ((PlayerEntity) event.getEntityLiving()).getItemStackFromSlot(EquipmentSlotType.CHEST));
                            }
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doSavorTheTastedEnchantmentEvent(LivingAttackEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                if(((PlayerEntity)(event.getSource().getTrueSource())).getHeldItemMainhand().getItem() instanceof SwordItem ){
                    int enchantLvl = PlayerUtil.isPlayerSpecificSlotWithEnchantmentLevel(((PlayerEntity)(event.getSource().getTrueSource())), EnchantmentRegistry.savor_the_taste_enchantment.get(),EquipmentSlotType.MAINHAND);
                    if(enchantLvl!=0){
                        CompoundNBT weaponNBT = ((PlayerEntity)(event.getSource().getTrueSource())).getHeldItemMainhand().getTag();
                        if(!weaponNBT.contains("savor_the_tasted_target")){
                            weaponNBT.putString("savor_the_tasted_target",event.getEntityLiving().getEntityString());
                        }
                        else{
                            String recordedTarget = weaponNBT.getString("savor_the_tasted_target");
                            if(recordedTarget.equals(event.getEntityLiving().getEntityString())){
                                float damage = event.getAmount();
                                switch(enchantLvl){
                                    case 1:
                                        damage *= (0.3f +(event.getEntityLiving().getRNG().nextInt(41) * 0.01f));
                                        break;
                                    case 2:
                                        damage *= (0.7f +(event.getEntityLiving().getRNG().nextInt(51) * 0.01f));
                                        break;
                                    case 3:
                                        damage *= (0.2f +(event.getEntityLiving().getRNG().nextInt(61) * 0.01f));
                                        break;
                                    default:
                                }
                                event.getEntityLiving().attackEntityFrom(DamageSource.GENERIC.setDamageBypassesArmor(),damage);
                            }
                            else{
                                weaponNBT.putString("savor_the_tasted_target",event.getEntityLiving().getEntityString());
                            }
                        }
                        ((PlayerEntity)(event.getSource().getTrueSource())).getHeldItemMainhand().setTag(weaponNBT);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doExoticHealerEnchantmentEvent(LivingHealEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                if(PlayerUtil.isPlayerArmorEnchanted((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.exotic_healer_enchantment.get())){
                    int dice = event.getEntityLiving().getRNG().nextInt(100);
                    if(dice<33){
                        event.setCanceled(true);
                    }else if(dice<66){
                        event.setAmount(event.getAmount()*2);
                    }else if(dice<90){
                        event.setAmount(event.getAmount()*3);
                    }else if(dice<91){
                        event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.STRENGTH,600));
                    }else if(dice<92){
                        event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.SPEED,600));
                    }else if(dice<93){
                        event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.REGENERATION,600));
                    }else if(dice<94){
                        event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.INVISIBILITY,600));
                    }else if(dice<95){
                        event.getEntityLiving().attackEntityFrom(new DamageSource("flowingagony.exotic_healer"),event.getAmount());
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

}

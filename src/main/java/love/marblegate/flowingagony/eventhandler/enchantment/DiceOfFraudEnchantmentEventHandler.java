package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.capibility.cooldown.CoolDown;
import love.marblegate.flowingagony.capibility.cooldown.CoolDownType;
import love.marblegate.flowingagony.capibility.cooldown.ICoolDown;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
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
    public static void doTricksterEnchantmentEvent(AttackEntityEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getTarget() instanceof LivingEntity){
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted(event.getPlayer(),EnchantmentRegistry.trickster.get(),EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantLvl==1){
                    appendixEffectForTrickster((LivingEntity) event.getTarget(),((LivingEntity) event.getTarget()).getRNG().nextInt(5)+1);
                }
                else if(enchantLvl==2){
                    int aEffect = ((LivingEntity) event.getTarget()).getRNG().nextInt(5)+1;
                    int bEffect = ((LivingEntity) event.getTarget()).getRNG().nextInt(5)+1;
                    while(aEffect==bEffect){
                        bEffect = ((LivingEntity) event.getTarget()).getRNG().nextInt(5)+1;
                    }
                    appendixEffectForTrickster((LivingEntity) event.getTarget(),aEffect);
                    appendixEffectForTrickster((LivingEntity) event.getTarget(),bEffect);
                }
            }
        }
    }

    static void appendixEffectForTrickster(LivingEntity entity, int diceNum){
        switch(diceNum){
            case 1:
                entity.addPotionEffect(new EffectInstance(Effects.POISON,100));
                break;
            case 2:
                entity.addPotionEffect(new EffectInstance(Effects.WITHER,100));
                break;
            case 3:
                entity.addPotionEffect(new EffectInstance(Effects.WEAKNESS,100));
                break;
            case 4:
                entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS,100));
                break;
            case 5:
                entity.setFire(5);
                break;
        }
    }

    @SubscribeEvent
    public static void doAnEnchantedGoldenAppleADayEnchantmentEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        if(!event.getPlayer().world.isRemote()) {
            int enchantNum = EnchantmentUtil.isPlayerArmorEnchanted(event.getPlayer(), EnchantmentRegistry.an_enchanted_apple_a_day.get(), EnchantmentUtil.ArmorEncCalOp.TOTAL_PIECE);
            if (enchantNum != 0) {
                LazyOptional<ICoolDown> coolDownCap = event.getEntityLiving().getCapability(CoolDown.COOL_DOWN_CAPABILITY);
                coolDownCap.ifPresent(
                        cap -> {
                            if (cap.isReady(CoolDownType.AN_ENCHANTED_GOLDEN_APPLE_A_DAY)) {
                                if (enchantNum == 1) {
                                    int tempInt = event.getPlayer().getRNG().nextInt(4);
                                    switch (tempInt) {
                                        case 0:
                                            event.getPlayer().addPotionEffect(new EffectInstance(Effects.ABSORPTION, 2400, 3));
                                            break;
                                        case 1:
                                            event.getPlayer().addPotionEffect(new EffectInstance(Effects.REGENERATION, 400, 1));
                                            break;
                                        case 2:
                                            event.getPlayer().addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 6000));
                                            break;
                                        case 3:
                                            event.getPlayer().addPotionEffect(new EffectInstance(Effects.RESISTANCE, 6000));
                                            break;
                                    }
                                } else if (enchantNum == 4) {
                                    event.getPlayer().addPotionEffect(new EffectInstance(Effects.ABSORPTION, 2400, 3));
                                    event.getPlayer().addPotionEffect(new EffectInstance(Effects.REGENERATION, 400, 1));
                                    event.getPlayer().addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 6000));
                                    event.getPlayer().addPotionEffect(new EffectInstance(Effects.RESISTANCE, 6000));
                                } else if (enchantNum < 4) {
                                    Set<Integer> temp = new HashSet<>();
                                    int tempCount = enchantNum;
                                    while (tempCount > 0) {
                                        int tempInt = event.getPlayer().getRNG().nextInt(4);
                                        if (!temp.contains(tempInt)) {
                                            switch (tempInt) {
                                                case 0:
                                                    event.getPlayer().addPotionEffect(new EffectInstance(Effects.ABSORPTION, 2400, 3));
                                                    break;
                                                case 1:
                                                    event.getPlayer().addPotionEffect(new EffectInstance(Effects.REGENERATION, 400, 1));
                                                    break;
                                                case 2:
                                                    event.getPlayer().addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 6000));
                                                    break;
                                                case 3:
                                                    event.getPlayer().addPotionEffect(new EffectInstance(Effects.RESISTANCE, 6000));
                                                    break;
                                            }
                                            temp.add(tempInt);
                                            tempCount--;
                                        }
                                    }
                                }
                                cap.set(CoolDownType.AN_ENCHANTED_GOLDEN_APPLE_A_DAY, 18000);
                            }
                        });
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void doDeathpunkEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(!event.isCanceled()){
                if(event.getEntityLiving() instanceof PlayerEntity) {
                    if (event.getAmount() >= event.getEntityLiving().getHealth() && (!event.getSource().getDamageType().equals("outOfWorld"))) {
                        if (EnchantmentUtil.isPlayerItemEnchanted(((PlayerEntity) event.getEntityLiving()), EnchantmentRegistry.deathpunk.get(), EquipmentSlotType.CHEST, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                            int solution = event.getEntityLiving().getRNG().nextInt(4);
                            int health = MathHelper.floor((((PlayerEntity) event.getEntityLiving()).getHealth()));
                            int maxHealth = MathHelper.floor((((PlayerEntity) event.getEntityLiving()).getMaxHealth()));
                            boolean damageEnchantment = false;
                            switch (solution) {
                                case 0:
                                    int foodLevel = ((PlayerEntity) event.getEntityLiving()).getFoodStats().getFoodLevel() + MathHelper.floor(((PlayerEntity) event.getEntityLiving()).getFoodStats().getSaturationLevel());
                                    if (foodLevel <= health) damageEnchantment = true;
                                    else {
                                        if (foodLevel > maxHealth) foodLevel = maxHealth;
                                        ((PlayerEntity) event.getEntityLiving()).setHealth(foodLevel);
                                        ((PlayerEntity) event.getEntityLiving()).getFoodStats().setFoodLevel(health);
                                        ((PlayerEntity) event.getEntityLiving()).getFoodStats().addExhaustion(((PlayerEntity) event.getEntityLiving()).getFoodStats().getSaturationLevel()*4);
                                    }
                                    break;
                                case 1:
                                    int oxygenLevel = MathHelper.floor(((PlayerEntity) event.getEntityLiving()).getAir() / (((PlayerEntity) event.getEntityLiving()).getMaxAir() / (float) maxHealth));
                                    if (oxygenLevel <= health) damageEnchantment = true;
                                    else {
                                        if (oxygenLevel > maxHealth) oxygenLevel = maxHealth;
                                        event.getEntityLiving().setHealth(oxygenLevel);
                                        event.getEntityLiving().setAir(health * (event.getEntityLiving().getMaxAir() / maxHealth));
                                    }
                                    break;
                                case 2:
                                    int expPoint = ((PlayerEntity) event.getEntityLiving()).experienceTotal;
                                    int exchangeCost = (MathHelper.floor(((PlayerEntity) event.getEntityLiving()).getMaxHealth()) - health) * 30;
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
                                event.getEntityLiving().setHealth(((PlayerEntity) event.getEntityLiving()).getMaxHealth());
                                Map<Enchantment, Integer> enchantmentList = EnchantmentHelper.getEnchantments(event.getEntityLiving().getItemStackFromSlot(EquipmentSlotType.CHEST));
                                enchantmentList.remove(EnchantmentRegistry.deathpunk.get());
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
    public static void doSavorTheTastedEnchantmentEvent(LivingDamageEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getSource().getTrueSource() instanceof PlayerEntity){
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted(((PlayerEntity)(event.getSource().getTrueSource())), EnchantmentRegistry.savor_the_taste.get(),EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if(enchantLvl!=0){
                    CompoundNBT weaponNBT = ((PlayerEntity)(event.getSource().getTrueSource())).getHeldItemMainhand().getTag();
                    if(!weaponNBT.contains("savor_the_tasted_target")){
                        weaponNBT.putString("savor_the_tasted_target",event.getEntityLiving().getEntityString());
                    }
                    else{
                        String recordedTarget = weaponNBT.getString("savor_the_tasted_target");
                        if(recordedTarget.equals(event.getEntityLiving().getEntityString())){
                            float modifiedDamage = event.getAmount() + event.getEntityLiving().getRNG().nextInt(5) + enchantLvl * 4 - 1;
                            event.setAmount(modifiedDamage);
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

    @SubscribeEvent
    public static void doExoticHealerEnchantmentEvent(LivingHealEvent event){
        if(!event.getEntityLiving().world.isRemote()){
            if(event.getEntityLiving() instanceof PlayerEntity){
                int enchantmentLvl = EnchantmentUtil.isPlayerArmorEnchanted((PlayerEntity) event.getEntityLiving(),EnchantmentRegistry.exotic_healer.get(), EnchantmentUtil.ArmorEncCalOp.TOTAL_LEVEL);
                if(enchantmentLvl!=0){
                    int dice = event.getEntityLiving().getRNG().nextInt(100);
                    float modifier = 1 + (enchantmentLvl - 1) * 0.1F;
                    if(dice<33){
                        event.setCanceled(true);
                    }else if(dice<66){
                        event.setAmount(event.getAmount()*2*modifier);
                    }else if(dice<90){
                        event.setAmount(event.getAmount()*3*modifier);
                    }else if(dice<91){
                        event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.STRENGTH, (int) (600*modifier)));
                    }else if(dice<92){
                        event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.SPEED, (int) (600*modifier)));
                    }else if(dice<93){
                        event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.REGENERATION, (int) (600*modifier)));
                    }else if(dice<94){
                        event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.INVISIBILITY, (int) (600*modifier)));
                    }else if(dice<95){
                        event.getEntityLiving().attackEntityFrom(new DamageSource("flowingagony.exotic_healer"),event.getAmount() * modifier);
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

}

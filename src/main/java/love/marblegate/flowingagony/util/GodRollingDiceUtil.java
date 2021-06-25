package love.marblegate.flowingagony.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GodRollingDiceUtil {
    public static void appendixEffectForTrickster(LivingEntity entity, int diceNum){
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

    public static LivingEntity getLuckyOne(List<LivingEntity> entities, Random random){
        return entities.get(random.nextInt(entities.size()));
    }

    public static List<ItemStack> rollDiceForPilferage(ItemStack armorFeet,VillagerEntity villagerEntity, List<MerchantOffer> offers, Random random, double fallingHeight){
        int extraluck = MathHelper.floor(fallingHeight);
        extraluck= Math.min(extraluck, 15);
        extraluck -=5;
        List<ItemStack> itemStacks = new ArrayList<>();
        boolean success = false;
        if(random.nextInt(100) < 30 + 5*extraluck){
            int temp = random.nextInt(offers.size());
            itemStacks.add(offers.get(temp).getSellingStack().copy());
            success = true;
        }
        if(random.nextInt(100) < 15 + 2.5f*extraluck&&offers.size()>3){
            int temp = random.nextInt(offers.size());
            itemStacks.add(offers.get(temp).getSellingStack().copy());
            success = true;
        }
        if(random.nextInt(100) < 5 + 1.5f*extraluck&&offers.size()>5){
            int temp = random.nextInt(offers.size());
            itemStacks.add(offers.get(temp).getSellingStack().copy());
            success = true;
        }
        if(random.nextInt(100) < 30 + 5*extraluck){
            villagerEntity.attackEntityFrom(DamageSource.GENERIC,1+extraluck*0.5f);
        }
        if(success){
            if(!armorFeet.equals(ItemStack.EMPTY))
            armorFeet.damageItem(30,villagerEntity,x->{});
        }
        return itemStacks;
    }
}

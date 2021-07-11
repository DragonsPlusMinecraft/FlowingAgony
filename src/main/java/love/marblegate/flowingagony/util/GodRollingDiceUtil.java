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
}

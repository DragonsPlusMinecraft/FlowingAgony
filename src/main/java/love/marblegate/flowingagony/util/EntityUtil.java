package love.marblegate.flowingagony.util;

import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;
import java.util.function.Predicate;

public class EntityUtil {
    public static List<LivingEntity> getTargetListExceptOneself(LivingEntity central, float radius, float height, Predicate<LivingEntity> predicate){
        AxisAlignedBB aabb = new AxisAlignedBB(central.getPosition().getX()-radius,central.getPosition().getY()-height,central.getPosition().getZ()-radius,central.getPosition().getX()+radius,central.getPosition().getY()+height,central.getPosition().getZ()+radius);
        List<LivingEntity> entities = central.world.getEntitiesWithinAABB(LivingEntity.class,aabb,predicate);
        entities.remove(central);
        return entities;
    }

    public static boolean isMonster(LivingEntity livingEntity){
        return livingEntity instanceof MonsterEntity || livingEntity instanceof SlimeEntity || livingEntity instanceof FlyingEntity || livingEntity instanceof EnderDragonEntity;
    }

    public static boolean isAggresiveUndead(LivingEntity livingEntity){
        return isCommonUndead(livingEntity) || isRareUndead(livingEntity) || livingEntity instanceof WitherEntity;
    }

    public static boolean isCommonUndead(LivingEntity livingEntity){
        return livingEntity instanceof ZombieEntity || livingEntity instanceof SkeletonEntity;
    }

    public static boolean isRareUndead(LivingEntity livingEntity){
        return livingEntity instanceof PhantomEntity || livingEntity instanceof WitherSkeletonEntity || livingEntity instanceof StrayEntity || livingEntity instanceof GhastEntity || livingEntity instanceof ZoglinEntity;
    }

    public static boolean isPassiveUndead(LivingEntity livingEntity){
        return livingEntity instanceof ZombieHorseEntity || livingEntity instanceof SkeletonHorseEntity;
    }



}

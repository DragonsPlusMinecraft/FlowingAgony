package love.marblegate.flowingagony.util;

import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;
import java.util.function.Predicate;

public class EntityUtil {
    public static List<LivingEntity> getTargetsExceptOneself(LivingEntity center, float radius, float height, Predicate<LivingEntity> predicate){
        AxisAlignedBB aabb = new AxisAlignedBB(center.getPosition().getX()-radius,center.getPosition().getY()-height,center.getPosition().getZ()-radius,center.getPosition().getX()+radius,center.getPosition().getY()+height,center.getPosition().getZ()+radius);
        List<LivingEntity> entities = center.world.getEntitiesWithinAABB(LivingEntity.class,aabb,predicate);
        entities.remove(center);
        return entities;
    }

    public static List<LivingEntity> getTargetsExceptOneself(PlayerEntity center, float radius, float height, Predicate<LivingEntity> predicate){
        return getTargetsExceptOneself((LivingEntity) center,radius,height,predicate);
    }

    public static List<LivingEntity> getTargetsOfSameType(LivingEntity center, float radius, float height, LivingEntity sourceEntity,boolean excludeOneself){
        AxisAlignedBB aabb = new AxisAlignedBB(center.getPosition().getX()-radius,center.getPosition().getY()-height,center.getPosition().getZ()-radius,center.getPosition().getX()+radius,center.getPosition().getY()+height,center.getPosition().getZ()+radius);
        List<LivingEntity> entities = center.world.getEntitiesWithinAABB(LivingEntity.class,aabb,livingEntity -> livingEntity.getClass() == sourceEntity.getClass());
        if(excludeOneself) entities.remove(center);
        return entities;
    }

    public static boolean isHostile(LivingEntity livingEntity,boolean restrictMode){
        if(restrictMode){
            return (livingEntity instanceof MonsterEntity && !(livingEntity instanceof ZombiePigmanEntity) && !(livingEntity instanceof SpiderEntity) && !(livingEntity instanceof EndermanEntity)) ||
                    livingEntity instanceof SlimeEntity ||
                    livingEntity instanceof FlyingEntity ||
                    livingEntity instanceof EnderDragonEntity;
        }else{
            return livingEntity instanceof MonsterEntity ||
                    livingEntity instanceof SlimeEntity ||
                    livingEntity instanceof FlyingEntity ||
                    livingEntity instanceof EnderDragonEntity;
        }
    }

    public static boolean isNeutral(LivingEntity livingEntity,boolean restrictMode){
        if(restrictMode){
            return  isNeutral(livingEntity,false) ||
                    livingEntity instanceof EndermanEntity ||
                    livingEntity instanceof ZombiePigmanEntity ||
                    livingEntity instanceof SpiderEntity;
        } else {
            return livingEntity instanceof BeeEntity ||
                    livingEntity instanceof DolphinEntity ||
                    livingEntity instanceof IronGolemEntity ||
                    livingEntity instanceof WolfEntity ||
                    livingEntity instanceof PandaEntity ||
                    livingEntity instanceof PolarBearEntity ||
                    livingEntity instanceof LlamaEntity;
        }
    }

    public static boolean isAggresiveUndead(LivingEntity livingEntity){
        return isCommonUndead(livingEntity) || isRareUndead(livingEntity) || livingEntity instanceof WitherEntity;
    }

    public static boolean isCommonUndead(LivingEntity livingEntity){
        return livingEntity instanceof ZombieEntity || livingEntity instanceof SkeletonEntity;
    }

    public static boolean isRareUndead(LivingEntity livingEntity){
        return livingEntity instanceof PhantomEntity || livingEntity instanceof WitherSkeletonEntity || livingEntity instanceof StrayEntity || livingEntity instanceof GhastEntity || livingEntity instanceof ZombiePigmanEntity;
    }

    public static boolean isPassiveUndead(LivingEntity livingEntity){
        return livingEntity instanceof ZombieHorseEntity || livingEntity instanceof SkeletonHorseEntity;
    }

    public static boolean supportHeadDrop(LivingEntity livingEntity){
        return  ((livingEntity instanceof ZombieEntity) && !(livingEntity instanceof ZombieVillagerEntity) && !(livingEntity instanceof DrownedEntity) && !(livingEntity instanceof HuskEntity)) ||
                livingEntity instanceof SkeletonEntity || livingEntity instanceof CreeperEntity ||
                livingEntity instanceof EnderDragonEntity || livingEntity instanceof WitherSkeletonEntity;
    }



}

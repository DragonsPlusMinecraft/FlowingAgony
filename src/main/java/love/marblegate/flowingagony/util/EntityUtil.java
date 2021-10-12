package love.marblegate.flowingagony.util;

import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Predicate;

public class EntityUtil {
    public static List<LivingEntity> getTargetsExceptOneself(LivingEntity center, float radius, float height, Predicate<LivingEntity> predicate) {
        AABB aabb = new AABB(center.blockPosition().getX() - radius, center.blockPosition().getY() - height, center.blockPosition().getZ() - radius, center.blockPosition().getX() + radius, center.blockPosition().getY() + height, center.blockPosition().getZ() + radius);
        List<LivingEntity> entities = center.level.getEntitiesOfClass(LivingEntity.class, aabb, predicate);
        entities.remove(center);
        return entities;
    }

    public static List<LivingEntity> getTargetsExceptOneself(Player center, float radius, float height, Predicate<LivingEntity> predicate) {
        return getTargetsExceptOneself((LivingEntity) center, radius, height, predicate);
    }

    public static List<LivingEntity> getTargetsOfSameType(LivingEntity center, float radius, float height, LivingEntity sourceEntity, boolean excludeOneself) {
        AABB aabb = new AABB(center.blockPosition().getX() - radius, center.blockPosition().getY() - height, center.blockPosition().getZ() - radius, center.blockPosition().getX() + radius, center.blockPosition().getY() + height, center.blockPosition().getZ() + radius);
        List<LivingEntity> entities = center.level.getEntitiesOfClass(LivingEntity.class, aabb, livingEntity -> livingEntity.getClass() == sourceEntity.getClass());
        if (excludeOneself) entities.remove(center);
        return entities;
    }

    public static boolean isHostile(LivingEntity livingEntity, boolean restrictMode) {
        if (restrictMode) {
            return (livingEntity instanceof Monster && !(livingEntity instanceof Piglin) && !(livingEntity instanceof Spider) && !(livingEntity instanceof EnderMan)) ||
                    livingEntity instanceof Slime ||
                    livingEntity instanceof FlyingMob ||
                    livingEntity instanceof Hoglin ||
                    livingEntity instanceof EnderDragon;
        } else {
            return livingEntity instanceof Monster ||
                    livingEntity instanceof Slime ||
                    livingEntity instanceof FlyingMob ||
                    livingEntity instanceof Hoglin ||
                    livingEntity instanceof EnderDragon;
        }
    }

    public static boolean isNeutral(LivingEntity livingEntity, boolean restrictMode) {
        if (restrictMode) {
            return isNeutral(livingEntity, false) ||
                    livingEntity instanceof EnderMan ||
                    livingEntity instanceof Piglin ||
                    livingEntity instanceof ZombifiedPiglin ||
                    livingEntity instanceof Spider;
        } else {
            return livingEntity instanceof Bee ||
                    livingEntity instanceof Dolphin ||
                    livingEntity instanceof IronGolem ||
                    livingEntity instanceof Wolf ||
                    livingEntity instanceof Panda ||
                    livingEntity instanceof PolarBear ||
                    livingEntity instanceof Llama;
        }
    }

    public static boolean isAggresiveUndead(LivingEntity livingEntity) {
        return isCommonUndead(livingEntity) || isRareUndead(livingEntity) || livingEntity instanceof WitherBoss;
    }

    public static boolean isCommonUndead(LivingEntity livingEntity) {
        return livingEntity instanceof Zombie || livingEntity instanceof Skeleton;
    }

    public static boolean isRareUndead(LivingEntity livingEntity) {
        return livingEntity instanceof Phantom || livingEntity instanceof WitherSkeleton || livingEntity instanceof Stray || livingEntity instanceof Ghast || livingEntity instanceof Zoglin;
    }

//    public static boolean isPassiveUndead(LivingEntity livingEntity) {
//        return livingEntity instanceof ZombieHorse || livingEntity instanceof SkeletonHorse;
//    }

    public static boolean supportHeadDrop(LivingEntity livingEntity) {
        return ((livingEntity instanceof Zombie) && !(livingEntity instanceof ZombieVillager) && !(livingEntity instanceof Drowned) && !(livingEntity instanceof ZombifiedPiglin) && !(livingEntity instanceof Husk)) ||
                livingEntity instanceof Skeleton || livingEntity instanceof Creeper ||
                livingEntity instanceof EnderDragon || livingEntity instanceof WitherSkeleton;
    }


}

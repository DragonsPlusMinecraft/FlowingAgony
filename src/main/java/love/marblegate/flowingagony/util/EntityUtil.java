package love.marblegate.flowingagony.util;

import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
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

}

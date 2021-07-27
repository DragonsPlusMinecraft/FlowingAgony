package love.marblegate.flowingagony.util.proxy;

import net.minecraft.particles.IParticleData;
import net.minecraft.potion.Effect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public interface IProxy {

    default void removeEffect(Effect effect){}

}

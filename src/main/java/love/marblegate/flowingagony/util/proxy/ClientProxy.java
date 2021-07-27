package love.marblegate.flowingagony.util.proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Effect;

public class ClientProxy implements IProxy{

    @Override
    public void removeEffect(Effect effect){
        Minecraft.getInstance().player.removeActivePotionEffect(effect.getEffect());
    }
}

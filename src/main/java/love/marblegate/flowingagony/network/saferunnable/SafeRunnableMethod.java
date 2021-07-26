package love.marblegate.flowingagony.network.saferunnable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.particles.IParticleData;
import net.minecraft.world.World;

public class SafeRunnableMethod {

    public static void addParticleForceNear(IParticleData particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        World world = Minecraft.getInstance().world;
        ActiveRenderInfo info = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
        if (info.isValid() && info.getProjectedView().squareDistanceTo(x, y, z) <= 512.0D) {
            world.addParticle(particleData, true, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }

}

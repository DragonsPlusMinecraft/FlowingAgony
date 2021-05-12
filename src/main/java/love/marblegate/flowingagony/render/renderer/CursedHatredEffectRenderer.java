package love.marblegate.flowingagony.render.renderer;

import love.marblegate.flowingagony.FlowingAgony;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class CursedHatredEffectRenderer {


    private static ShaderGroup shaderGroup;
    private static Framebuffer framebuffer;
    private static final Logger LOGGER = LogManager.getLogger(FlowingAgony.class);

    public static void loadShader() {
        if (shaderGroup != null) {
            shaderGroup.close();
        }

        final ResourceLocation location = new ResourceLocation("flowingagony", "shaders/post/cursed_hatred_effect.json");

        try {
            final Minecraft minecraft = Minecraft.getInstance();
            final MainWindow mainWindow = minecraft.getMainWindow();
            shaderGroup = new ShaderGroup(minecraft.getTextureManager(), minecraft.getResourceManager(), minecraft.getFramebuffer(), location);
            shaderGroup.createBindFramebuffers(mainWindow.getFramebufferWidth(), mainWindow.getFramebufferHeight());
            framebuffer = shaderGroup.getFramebufferRaw("flowingagony:final");
        } catch (IOException e) {
            LOGGER.warn("Failed to load shader: {}", location, e);
            shaderGroup = null;
            framebuffer = null;
        }
    }
}

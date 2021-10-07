package love.marblegate.flowingagony.render.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import love.marblegate.flowingagony.capibility.AbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.CapabilityManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;

import java.util.concurrent.atomic.AtomicInteger;

public class AbnormalJoyHUD extends GuiComponent {
    private final int width;
    private final int height;
    private final Minecraft minecraft;
    private final ResourceLocation HUD = new ResourceLocation("flowingagony", "textures/gui/flowing_agony_hud_1.png");
    private PoseStack matrixStack;

    public AbnormalJoyHUD(PoseStack matrixStack) {
        width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        minecraft = Minecraft.getInstance();
        this.matrixStack = matrixStack;
    }

    public void setMatrixStack(PoseStack stack) {
        matrixStack = stack;
    }

    public void render() {

        if (!minecraft.options.hideGui && minecraft.gameMode.hasExperience()) {
            AtomicInteger abnormalJoyPoint = new AtomicInteger();
            LazyOptional<AbnormalJoyCapability> pointCap = minecraft.player.getCapability(CapabilityManager.ABNORMALJOY_CAPABILITY);
            pointCap.ifPresent(
                    cap -> abnormalJoyPoint.set((int) Math.floor(cap.get()))
            );
            if (abnormalJoyPoint.get() != 0) {
                //FIXME
                // - Need Test
                RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
                minecraft.getTextureManager().bindForSetup(HUD);
                /*RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                minecraft.getTextureManager().bind(HUD)*/;
                int x = width / 2 - 91;
                int y = height - 32 + 3 + 3;
                //Render Background
                blit(matrixStack, x, y, 0, 0, 182, 3);

                int k = (int) (abnormalJoyPoint.get() / 100F * 182.0F);
                //Render Filler
                blit(matrixStack, x, y, 0, 3, k, 3);
            }
        }
    }
}

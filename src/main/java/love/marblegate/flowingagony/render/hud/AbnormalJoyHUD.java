package love.marblegate.flowingagony.render.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import love.marblegate.flowingagony.capibility.abnormaljoy.AbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.abnormaljoy.IAbnormalJoyCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;

import java.util.concurrent.atomic.AtomicInteger;

public class AbnormalJoyHUD extends AbstractGui {
    private final int width;
    private final int height;
    private final Minecraft minecraft;
    private final ResourceLocation HUD = new ResourceLocation("flowingagony", "textures/gui/flowing_agony_hud_1.png");
    private MatrixStack matrixStack;

    public AbnormalJoyHUD(MatrixStack matrixStack) {
        width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        height = Minecraft.getInstance().getMainWindow().getScaledHeight();
        minecraft = Minecraft.getInstance();
        this.matrixStack = matrixStack;
    }

    public void setMatrixStack(MatrixStack stack) {
        matrixStack = stack;
    }

    public void render() {

        if(!minecraft.gameSettings.hideGUI&&minecraft.playerController.gameIsSurvivalOrAdventure()){
            AtomicInteger abnormalJoyPoint = new AtomicInteger();
            LazyOptional<IAbnormalJoyCapability> pointCap = minecraft.player.getCapability(AbnormalJoyCapability.ABNORMALJOY_CAPABILITY);
            pointCap.ifPresent(
                    cap-> abnormalJoyPoint.set((int) Math.floor(cap.get()))
            );
            if(abnormalJoyPoint.get()!=0) {
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                minecraft.getTextureManager().bindTexture(HUD);
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

package love.marblegate.flowingagony.render;

import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import static com.mojang.blaze3d.vertex.VertexFormat.Mode.QUADS;

public class CustomRenderType extends RenderType {

    public CustomRenderType(String nameIn, VertexFormat formatIn, VertexFormat.Mode drawModeIn, int bufferSizeIn, boolean useDelegateIn, boolean needsSortingIn, Runnable setupTaskIn, Runnable clearTaskIn) {
        super(nameIn, formatIn, drawModeIn, bufferSizeIn, useDelegateIn, needsSortingIn, setupTaskIn, clearTaskIn);
    }

    public static final RenderType PERCEIVED_MALICE_INDICATOR = RenderType.create("flowingagony.perceived_malice_indicator",
            DefaultVertexFormat.POSITION_COLOR, QUADS, 256, false, false,
            RenderType.CompositeState.builder()
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setTextureState(NO_TEXTURE)
                    .setDepthTestState(NO_DEPTH_TEST)
                    .setCullState(NO_CULL)
                    .setLightmapState(NO_LIGHTMAP)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(false));

}

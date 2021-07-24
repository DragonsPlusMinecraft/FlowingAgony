package love.marblegate.flowingagony.render.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.render.CustomRenderType;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class PerceivedMaliceEffectRenderer {

    @SuppressWarnings("rawtypes")
    @SubscribeEvent
    public static void render(RenderLivingEvent.Post event){
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (EnchantmentUtil.isPlayerItemEnchanted(player,EnchantmentRegistry.PERCEIVED_MALICE.get(), EquipmentSlotType.HEAD, EnchantmentUtil.ItemEncCalOp.GENERAL)==1) {
            if(event.getEntity() instanceof MonsterEntity) {
                if (player.getPositionVec().distanceTo(event.getEntity().getPositionVec())<=24)
                    highlight(event.getMatrixStack(), event.getBuffers(), event.getEntity());
            }
        }
    }

    private static void highlight(MatrixStack matrixStack, IRenderTypeBuffer buffer, LivingEntity entity){
        IVertexBuilder builder = buffer.getBuffer(CustomRenderType.PERCEIVED_MALICE_INDICATOR);
        Matrix4f positionMatrix = matrixStack.getLast().getMatrix();
        buildShapeDynamically(builder,positionMatrix, entity.world.getDayTime()%100);
    }

    private static void buildShapeDynamically(IVertexBuilder builder, Matrix4f matrix, long standardSequence){
        buildLayer(builder,matrix,standardSequence*0.02f,0.1f,standardSequence*0.001f);
        standardSequence = standardSequence-20<0? standardSequence+80: standardSequence-20;
        buildLayer(builder,matrix,standardSequence*0.02f,0.2f,standardSequence*0.001f);
        standardSequence = standardSequence-20<0? standardSequence+80: standardSequence-20;
        buildLayer(builder,matrix,standardSequence*0.02f,0.3f,standardSequence*0.001f);
        standardSequence = standardSequence-20<0? standardSequence+80: standardSequence-20;
        buildLayer(builder,matrix,standardSequence*0.02f,0.4f,standardSequence*0.001f);
        standardSequence = standardSequence-20<0? standardSequence+80: standardSequence-20;
        buildLayer(builder,matrix,standardSequence*0.02f,0.5f,standardSequence*0.001f);
    }


    private static void buildLayer(IVertexBuilder builder, Matrix4f matrix, float boxHeight, float redGrade, float alphaGrade){
        add(builder, matrix, -0.5f, 0, -0.5f, redGrade,alphaGrade);
        add(builder, matrix, 0.5f, 0, -0.5f,redGrade,alphaGrade);
        add(builder, matrix, -0.5f, 0, 0.5f, redGrade,alphaGrade);
        add(builder, matrix,  0.5f, 0, 0.5f,redGrade,alphaGrade);

        add(builder, matrix, -0.5f, 0, -0.5f,redGrade,alphaGrade);
        add(builder, matrix,  -0.5f, 0, 0.5f,redGrade,alphaGrade);
        add(builder, matrix, 0.5f, 0, -0.5f,redGrade,alphaGrade);
        add(builder, matrix,  0.5f, 0, 0.5f,redGrade,alphaGrade);

        add(builder, matrix, -0.5f, 0, -0.5f,redGrade,alphaGrade);
        add(builder, matrix, -0.5f, boxHeight, -0.5f,redGrade,alphaGrade);
        add(builder, matrix, 0.5f, 0, -0.5f,redGrade,alphaGrade);
        add(builder, matrix, 0.5f, boxHeight, -0.5f,redGrade,alphaGrade);

        add(builder, matrix, -0.5f, 0, 0.5f,redGrade,alphaGrade);
        add(builder, matrix, -0.5f, boxHeight, 0.5f,redGrade,alphaGrade);
        add(builder, matrix, 0.5f, 0, 0.5f,redGrade,alphaGrade);
        add(builder, matrix, 0.5f, boxHeight, 0.5f,redGrade,alphaGrade);
    }
    private static void add(IVertexBuilder builder, Matrix4f matrix, float dx, float dy, float dz, float redGrade, float alphaGrade) {
        builder.pos(matrix, dx, dy, dz)
                .color(redGrade, 0.0f, 0.0f, alphaGrade)
                .endVertex();
    }
}

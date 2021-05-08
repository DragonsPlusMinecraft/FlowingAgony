package love.marblegate.flowingagony.render.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.render.CustomRenderType;
import love.marblegate.flowingagony.util.PlayerUtil;
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

    @SubscribeEvent
    public static void render(RenderLivingEvent.Post event){
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (PlayerUtil.isPlayerSpecificSlotEnchanted(player,EnchantmentRegistry.perceived_malice_enchantment.get(), EquipmentSlotType.HEAD)) {
            if(event.getEntity() instanceof MonsterEntity) {
                if (player.getPositionVec().distanceTo(event.getEntity().getPositionVec())<=32)
                    highlight(event.getMatrixStack(), event.getBuffers(), event.getEntity());
            }
        }
    }

    private static void highlight(MatrixStack matrixStack, IRenderTypeBuffer buffer, LivingEntity entity){
        IVertexBuilder builder = buffer.getBuffer(CustomRenderType.MONSTER_ENDICATOR);
        Matrix4f positionMatrix = matrixStack.getLast().getMatrix();
        pop(builder,positionMatrix, entity.world.getDayTime()%100);
    }

    private static void pop(IVertexBuilder builder, Matrix4f matrix, long standardSequence){
        popFrame(builder,matrix,standardSequence*0.02f,0.1f,standardSequence*0.001f);
        standardSequence = standardSequence-20<0? standardSequence+80: standardSequence-20;
        popFrame(builder,matrix,standardSequence*0.02f,0.2f,standardSequence*0.001f);
        standardSequence = standardSequence-20<0? standardSequence+80: standardSequence-20;
        popFrame(builder,matrix,standardSequence*0.02f,0.3f,standardSequence*0.001f);
        standardSequence = standardSequence-20<0? standardSequence+80: standardSequence-20;
        popFrame(builder,matrix,standardSequence*0.02f,0.4f,standardSequence*0.001f);
        standardSequence = standardSequence-20<0? standardSequence+80: standardSequence-20;
        popFrame(builder,matrix,standardSequence*0.02f,0.5f,standardSequence*0.001f);
    }


    private static void popFrame(IVertexBuilder builder, Matrix4f matrix,float boxHeight, float redGrade, float alphaGrade){
        popLine(builder, matrix, -0.5f, 0, -0.5f, 0.5f, 0, -0.5f,redGrade,alphaGrade);
        popLine(builder, matrix, -0.5f, 0, 0.5f, 0.5f, 0, 0.5f,redGrade,alphaGrade);

        popLine(builder, matrix, -0.5f, 0, -0.5f, -0.5f, 0, 0.5f,redGrade,alphaGrade);
        popLine(builder, matrix, 0.5f, 0, -0.5f, 0.5f, 0, 0.5f,redGrade,alphaGrade);

        popLine(builder, matrix, -0.5f, 0, -0.5f, -0.5f, boxHeight, -0.5f,redGrade,alphaGrade);
        popLine(builder, matrix, 0.5f, 0, -0.5f, 0.5f, boxHeight, -0.5f,redGrade,alphaGrade);
        popLine(builder, matrix, -0.5f, 0, 0.5f, -0.5f, boxHeight, 0.5f,redGrade,alphaGrade);
        popLine(builder, matrix, 0.5f, 0, 0.5f, 0.5f, boxHeight, 0.5f,redGrade,alphaGrade);
    }
    private static void popLine(IVertexBuilder builder, Matrix4f matrix, float dx1, float dy1, float dz1, float dx2, float dy2, float dz2, float redGrade, float alphaGrade) {
        builder.pos(matrix, dx1, dy1, dz1)
                .color(redGrade, 0.0f, 0.0f, alphaGrade)
                .endVertex();
        builder.pos(matrix, dx2, dy2, dz2)
                .color(redGrade, 0.0f, 0.0f, alphaGrade)
                .endVertex();
    }
}

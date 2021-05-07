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
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class MonsterIndicatorRenderer {

    @SubscribeEvent
    public static void render(RenderLivingEvent.Post event){
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (PlayerUtil.isPlayerSpecificSlotEnchanted(player,EnchantmentRegistry.i_see_you_now_enchantment.get(), EquipmentSlotType.HEAD)
                ||PlayerUtil.isPlayerSpecificSlotEnchanted(player,EnchantmentRegistry.perceived_malice_enchantment.get(), EquipmentSlotType.HEAD)) {
            if(event.getEntity() instanceof MonsterEntity)
                highlight(event.getMatrixStack(), event.getBuffers(), event.getEntity());
        }
    }

    private static void highlight(MatrixStack matrixStack, IRenderTypeBuffer buffer, LivingEntity entity){
        IVertexBuilder builder = buffer.getBuffer(CustomRenderType.MONSTER_ENDICATOR);
        Matrix4f positionMatrix = matrixStack.getLast().getMatrix();
        popFrame(builder,positionMatrix);

    }


    private static void popFrame(IVertexBuilder builder, Matrix4f matrix){
        popLine(builder, matrix, -0.5f, 0, -0.5f, 0.5f, 0, -0.5f);
        popLine(builder, matrix, -0.5f, 2, -0.5f, 0.5f, 2, -0.5f);
        popLine(builder, matrix, -0.5f, 0, 0.5f, 0.5f, 0, 0.5f);
        popLine(builder, matrix, -0.5f, 2, 0.5f, 0.5f, 2, 0.5f);

        popLine(builder, matrix, -0.5f, 0, -0.5f, -0.5f, 0, 0.5f);
        popLine(builder, matrix, 0.5f, 0, -0.5f, 0.5f, 0, 0.5f);
        popLine(builder, matrix, -0.5f, 2, -0.5f, 0, 2, 0.5f);
        popLine(builder, matrix, 0.5f, 2, -0.5f, 0.5f, 2, 0.5f);

        popLine(builder, matrix, -0.5f, 0, -0.5f, -0.5f, 2, -0.5f);
        popLine(builder, matrix, 0.5f, 0, -0.5f, 0.5f, 2, -0.5f);
        popLine(builder, matrix, -0.5f, 0, 0.5f, -0.5f, 2, 0.5f);
        popLine(builder, matrix, 0.5f, 0, 0.5f, 0.5f, 2, 0.5f);
    }
    private static void popLine(IVertexBuilder builder, Matrix4f matrix, float dx1, float dy1, float dz1, float dx2, float dy2, float dz2) {
        builder.pos(matrix, dx1, dy1, dz1)
                .color(205, 0.0f, 0.0f, 35)
                .endVertex();
        builder.pos(matrix, dx2, dy2, dz2)
                .color(205, 0.0f, 0.0f, 35)
                .endVertex();
    }
}

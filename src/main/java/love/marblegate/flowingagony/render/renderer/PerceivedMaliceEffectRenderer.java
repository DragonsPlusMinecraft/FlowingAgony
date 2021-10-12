package love.marblegate.flowingagony.render.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import love.marblegate.flowingagony.render.CustomRenderType;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class PerceivedMaliceEffectRenderer {

    @SuppressWarnings("rawtypes")
    @SubscribeEvent
    public static void render(RenderLivingEvent.Post event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (EnchantmentUtil.isPlayerItemEnchanted(player, EnchantmentRegistry.PERCEIVED_MALICE.get(), EquipmentSlot.HEAD, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
            if (event.getEntity() instanceof Monster) {
                if (player.position().distanceTo(event.getEntity().position()) <= 24)
                    highlight(event.getMatrixStack(), event.getBuffers(), event.getEntity());
            }
        }
    }

    private static void highlight(PoseStack matrixStack, MultiBufferSource buffer, LivingEntity entity) {
        VertexConsumer builder = buffer.getBuffer(CustomRenderType.PERCEIVED_MALICE_INDICATOR);
        Matrix4f positionMatrix = matrixStack.last().pose();
        buildShapeDynamically(builder, positionMatrix, entity.level.getDayTime() % 100);
    }

    private static void buildShapeDynamically(VertexConsumer builder, Matrix4f matrix, long standardSequence) {
        buildLayer(builder, matrix, standardSequence * 0.02f, 0.1f, standardSequence * 0.001f);
        standardSequence = standardSequence - 20 < 0 ? standardSequence + 80 : standardSequence - 20;
        buildLayer(builder, matrix, standardSequence * 0.02f, 0.2f, standardSequence * 0.001f);
        standardSequence = standardSequence - 20 < 0 ? standardSequence + 80 : standardSequence - 20;
        buildLayer(builder, matrix, standardSequence * 0.02f, 0.3f, standardSequence * 0.001f);
        standardSequence = standardSequence - 20 < 0 ? standardSequence + 80 : standardSequence - 20;
        buildLayer(builder, matrix, standardSequence * 0.02f, 0.4f, standardSequence * 0.001f);
        standardSequence = standardSequence - 20 < 0 ? standardSequence + 80 : standardSequence - 20;
        buildLayer(builder, matrix, standardSequence * 0.02f, 0.5f, standardSequence * 0.001f);
    }


    private static void buildLayer(VertexConsumer builder, Matrix4f matrix, float boxHeight, float redGrade, float alphaGrade) {
        add(builder, matrix, -0.5f, 0, -0.5f, redGrade, alphaGrade);
        add(builder, matrix, 0.5f, 0, -0.5f, redGrade, alphaGrade);
        add(builder, matrix, -0.5f, 0, 0.5f, redGrade, alphaGrade);
        add(builder, matrix, 0.5f, 0, 0.5f, redGrade, alphaGrade);

        add(builder, matrix, -0.5f, 0, -0.5f, redGrade, alphaGrade);
        add(builder, matrix, -0.5f, 0, 0.5f, redGrade, alphaGrade);
        add(builder, matrix, 0.5f, 0, -0.5f, redGrade, alphaGrade);
        add(builder, matrix, 0.5f, 0, 0.5f, redGrade, alphaGrade);

        add(builder, matrix, -0.5f, 0, -0.5f, redGrade, alphaGrade);
        add(builder, matrix, -0.5f, boxHeight, -0.5f, redGrade, alphaGrade);
        add(builder, matrix, 0.5f, 0, -0.5f, redGrade, alphaGrade);
        add(builder, matrix, 0.5f, boxHeight, -0.5f, redGrade, alphaGrade);

        add(builder, matrix, -0.5f, 0, 0.5f, redGrade, alphaGrade);
        add(builder, matrix, -0.5f, boxHeight, 0.5f, redGrade, alphaGrade);
        add(builder, matrix, 0.5f, 0, 0.5f, redGrade, alphaGrade);
        add(builder, matrix, 0.5f, boxHeight, 0.5f, redGrade, alphaGrade);
    }

    private static void add(VertexConsumer builder, Matrix4f matrix, float dx, float dy, float dz, float redGrade, float alphaGrade) {
        builder.vertex(matrix, dx, dy, dz)
                .color(redGrade, 0.0f, 0.0f, alphaGrade)
                .endVertex();
    }
}

package net.reversteam.strangematter.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.reversteam.strangematter.entity.ChronoBlisterProjectileEntity;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class ChronoBlisterProjectileRenderer extends EntityRenderer<ChronoBlisterProjectileEntity> {

    private static final ResourceLocation CHRONO_BLISTER_PROJECTILE_TEXTURE = ResourceLocation.fromNamespaceAndPath("strangematter", "textures/entity/time_bubble.png");

    public ChronoBlisterProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(ChronoBlisterProjectileEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                      MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        // Make it face the camera
        poseStack.mulPose(net.minecraft.client.Minecraft.getInstance().gameRenderer.getMainCamera().rotation());

        // Render a small quad with amber/orange tint
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(getTextureLocation(entity)));

        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();

        // Scale size based on charge progress (0.0 to 1.0)
        float chargeProgress = entity.getChargeProgress();
        float minSize = 0.1f; // Start very small
        float maxSize = 0.2f; // Full size when charged
        float size = minSize + (maxSize - minSize) * chargeProgress;

        float alpha = 0.8f;
        // Amber/orange color for time dilation
        float red = 1.0f;
        float green = 0.65f;
        float blue = 0.0f;

        // Render the quad
        vertexConsumer.addVertex(matrix, -size, -size, 0.0f)
            .setColor(red, green, blue, alpha)
            .setUv(0.0f, 1.0f)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(packedLight)
            .setNormal(poseStack.last(), 0.0f, 0.0f, 1.0f)
            ;

        vertexConsumer.addVertex(matrix, size, -size, 0.0f)
            .setColor(red, green, blue, alpha)
            .setUv(1.0f, 1.0f)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(packedLight)
            .setNormal(poseStack.last(), 0.0f, 0.0f, 1.0f)
            ;

        vertexConsumer.addVertex(matrix, size, size, 0.0f)
            .setColor(red, green, blue, alpha)
            .setUv(1.0f, 0.0f)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(packedLight)
            .setNormal(poseStack.last(), 0.0f, 0.0f, 1.0f)
            ;

        vertexConsumer.addVertex(matrix, -size, size, 0.0f)
            .setColor(red, green, blue, alpha)
            .setUv(0.0f, 0.0f)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(packedLight)
            .setNormal(poseStack.last(), 0.0f, 0.0f, 1.0f)
            ;

        poseStack.popPose();

        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ChronoBlisterProjectileEntity entity) {
        return CHRONO_BLISTER_PROJECTILE_TEXTURE;
    }
}


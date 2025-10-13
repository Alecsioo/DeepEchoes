package com.github.alecsio.deepechoes.projectiles.client;

import com.github.alecsio.deepechoes.projectiles.SonicBoomProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class SonicBoomProjectileRenderer extends EntityRenderer<SonicBoomProjectile> {

    public SonicBoomProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(SonicBoomProjectile entity) {
        return null;
    }

    @Override
    public void render(SonicBoomProjectile entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        // --- Debug line settings ---
        Vec3 motion = entity.getDeltaMovement().normalize();
        double length = 2.0; // how long to draw the line (in blocks/units)

        // Interpolated position for smooth rendering
        double x = Mth.lerp(partialTick, entity.xOld, entity.getX());
        double y = Mth.lerp(partialTick, entity.yOld, entity.getY());
        double z = Mth.lerp(partialTick, entity.zOld, entity.getZ());

        // The line goes from (0,0,0) to (motion * length)
        poseStack.pushPose();
        poseStack.translate(0.0, 0.0, 0.0); // entity's render origin is already centered at its position

        // Start & end points of the line in model space
        Vec3 start = Vec3.ZERO;
        Vec3 end = motion.scale(length);

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.LINES);
        Matrix4f matrix = poseStack.last().pose();

        // Simple white line
        float r = 1.0f, g = 1.0f, b = 1.0f, a = 1.0f;

        consumer.addVertex(matrix, (float) start.x, (float) start.y, (float) start.z)
                .setColor(r, g, b, a)
                .setNormal(0, 1, 0);

        consumer.addVertex(matrix, (float) end.x, (float) end.y, (float) end.z)
                .setColor(r, g, b, a)
                .setNormal(0, 1, 0);

        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

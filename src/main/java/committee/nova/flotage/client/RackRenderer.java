package committee.nova.flotage.client;

import committee.nova.flotage.impl.tile.RackBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

public class RackRenderer implements BlockEntityRenderer<RackBlockEntity> {

    public RackRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(RackBlockEntity tile, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack stack = tile.getStack(0);
        Direction direction = tile.getItemDirection();
        int iPos = (int) tile.getPos().asLong();

        if (!stack.isEmpty()) {
            matrices.push();

            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            boolean blockItem = itemRenderer.getModel(stack, tile.getWorld(), null, iPos).hasDepth();

            if (blockItem) {
                renderBlock(matrices, direction);
            } else {
                renderItemLayingDown(matrices, direction);
            }

            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers, iPos);
            matrices.pop();
        }
    }

    private void renderItemLayingDown(MatrixStack matrixStackIn, Direction direction) {
        matrixStackIn.translate(0.5d, 1d, 0.5d);

        float f = -direction.asRotation();
        matrixStackIn.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(f));

        matrixStackIn.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.f));

        matrixStackIn.scale(0.6f, 0.6f, 0.6f);
    }

    private void renderBlock(MatrixStack matrixStackIn, Direction direction) {
        matrixStackIn.translate(0.5d, 1.15d, .5d);

        float f = -direction.asRotation();
        matrixStackIn.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(f));

        matrixStackIn.scale(0.8f, 0.8f, 0.8f);
    }
}

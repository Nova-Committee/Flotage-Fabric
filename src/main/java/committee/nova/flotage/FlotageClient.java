package committee.nova.flotage;

import committee.nova.flotage.client.RackRenderer;
import committee.nova.flotage.init.TileRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class FlotageClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(TileRegistry.RACK_BLOCK_ENTITY, RackRenderer::new);
    }
}

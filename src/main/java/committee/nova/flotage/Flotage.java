package committee.nova.flotage;

import committee.nova.flotage.impl.tile.RackBlockEntity;
import committee.nova.flotage.init.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Flotage implements ModInitializer {
    public static final String MOD_NAME = "Flotage";
    public static final String MOD_ID = "flotage";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {

        TabRegistry.register();
        RecipeRegistry.register();
        BlockRegistry.register();
        TileRegistry.register();
        ItemRegistry.register();

        ItemStorage.SIDED.registerForBlockEntity(RackBlockEntity::getExposedStorage, TileRegistry.RACK_BLOCK_ENTITY);

        LOGGER.info("Flotage setup done!");
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}

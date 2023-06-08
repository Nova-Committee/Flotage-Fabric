package committee.nova.flotage;

import committee.nova.flotage.impl.tile.RackBlockEntity;
import committee.nova.flotage.init.BlockRegistry;
import committee.nova.flotage.init.ItemRegistry;
import committee.nova.flotage.init.RecipeRegistry;
import committee.nova.flotage.init.TileRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Flotage implements ModInitializer {
    public static final String MOD_NAME = "Flotage";
    public static final String MOD_ID = "flotage";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final ItemGroup TAB = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "main"))
            .icon(() -> Registry.BLOCK.get(id("oak_raft")).asItem().getDefaultStack()).build();

    @Override
    public void onInitialize() {

        RecipeRegistry.register();
        BlockRegistry.register();
        TileRegistry.register();
        ItemRegistry.register();

        ItemStorage.SIDED.registerForBlockEntity(RackBlockEntity::getExposedStorage, TileRegistry.RACK_BLOCK_ENTITY);
        LOGGER.info("Fabricatech setup done!");
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}

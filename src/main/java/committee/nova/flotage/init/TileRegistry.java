package committee.nova.flotage.init;

import committee.nova.flotage.Flotage;
import committee.nova.flotage.impl.tile.RackBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class TileRegistry {
    public static final BlockEntityType<RackBlockEntity> RACK_BLOCK_ENTITY =
            FabricBlockEntityTypeBuilder.create(RackBlockEntity::new, BlockRegistry.getRacks()).build();

    public static void register() {
        tile(RACK_BLOCK_ENTITY, "rack");
    }

    private static void tile(BlockEntityType<?> tile, String id) {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, Flotage.id(id), tile);
    }

    public static BlockEntityType<?> get(String id) {
        return Registry.BLOCK_ENTITY_TYPE.get(Flotage.id(id));
    }

}

package committee.nova.flotage.init;

import committee.nova.flotage.Flotage;
import committee.nova.flotage.impl.item.RaftItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;


public class ItemRegistry {
    public static void register() {
        for (BlockMember member : BlockMember.values()) {
            raftItem(member.raft(), settings());
            raftItem(member.brokenRaft(), settings());
            blockItem(member.fence(), settings());
            blockItem(member.crossedFence(), settings());
            blockItem(member.rack(), settings());
        }
    }

    private static void blockItem(String id, Item.Settings settings) {
        item(id, new BlockItem(Registry.BLOCK.get(Flotage.id(id)), settings));
    }

    private static void raftItem(String id, Item.Settings settings) {
        item(id, new RaftItem(Registry.BLOCK.get(Flotage.id(id)), settings));
    }

    private static void item(String id, Item item) {
        Registry.register(Registry.ITEM, Flotage.id(id), item);
    }

    public static Item.Settings settings() {
        return new FabricItemSettings().group(Flotage.TAB);
    }

    public static Item get(String id) {
        return Registry.ITEM.get(Flotage.id(id));
    }
}

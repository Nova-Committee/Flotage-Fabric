package committee.nova.flotage.init;

import committee.nova.flotage.Flotage;
import committee.nova.flotage.impl.item.RaftItem;
import committee.nova.flotage.util.BlockMember;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;


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
        item(id, new BlockItem(Registries.BLOCK.get(Flotage.id(id)), settings));
    }

    private static void raftItem(String id, Item.Settings settings) {
        item(id, new RaftItem(Registries.BLOCK.get(Flotage.id(id)), settings));
    }

    private static void item(String id, Item item) {
        Registry.register(Registries.ITEM, Flotage.id(id), item);
    }

    public static Item.Settings settings() {
        return new FabricItemSettings();
    }

    public static Item get(String id) {
        return Registries.ITEM.get(Flotage.id(id));
    }
}

package committee.nova.flotage.init;

import committee.nova.flotage.Flotage;
import committee.nova.flotage.impl.block.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Arrays;

public class BlockRegistry {
    public static void register() {
        for (BlockMember member : BlockMember.values()) {
            woodenRaft(member);
            brokenRaft(member);
            fence(member);
            crossedFence(member);
            rack(member);
        }
    }

    private static void woodenRaft(BlockMember member) {
        block(member.raft(), new WoodenRaftBlock(member));
    }

    private static void brokenRaft(BlockMember member) {
        block(member.brokenRaft(), new BrokenRaftBlock(member));
    }

    private static void fence(BlockMember member) {
        block(member.fence(), new SimpleFenceBlock(member));
    }

    private static void crossedFence(BlockMember member) {
        block(member.crossedFence(), new CrossedFenceBlock(member));
    }

    private static void rack(BlockMember member) {
        block(member.rack(), new RackBlock(member));
    }

    private static void block(String id, Block block) {
        Registry.register(Registry.BLOCK, Flotage.id(id), block);
    }

    private static void block(String id, AbstractBlock.Settings settings) {
        block(id, new Block(settings));
    }

    public static Block get(String id) {
        return Registry.BLOCK.get(Flotage.id(id));
    }

    public static Block[] getRacks() {
        ArrayList<Block> list = new ArrayList<>();
        Arrays.stream(BlockMember.values()).forEach(member -> list.add(get(member.rack())));
        return list.toArray(new Block[0]);
    }
}

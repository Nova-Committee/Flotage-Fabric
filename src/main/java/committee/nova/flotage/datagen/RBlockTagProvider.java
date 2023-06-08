package committee.nova.flotage.datagen;

import committee.nova.flotage.init.BlockMember;
import committee.nova.flotage.init.BlockRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RBlockTagProvider extends FabricTagProvider<Block> {
    public RBlockTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, Registry.BLOCK, "Fabricatech Block Tag");
    }

    @Override
    protected void generateTags() {
        //FabricTagBuilder<Block> pickaxeBuilder = getOrCreateTagBuilder(TagKey.of(Registry.BLOCK_KEY, new Identifier("minecraft", "mineable/pickaxe")));
        //FabricTagBuilder<Block> axeBuilder = getOrCreateTagBuilder(TagKey.of(Registry.BLOCK_KEY, new Identifier("minecraft", "mineable/axe")));

        FabricTagBuilder<Block> pickaxe = tag("minecraft:mineable/pickaxe");
        FabricTagBuilder<Block> axe = tag("minecraft:mineable/axe");
        FabricTagBuilder<Block> rack = tag("flotage:rack");
        FabricTagBuilder<Block> fence = tag("flotage:fence");
        FabricTagBuilder<Block> crossedFence = tag("flotage:crossed_fence");
        FabricTagBuilder<Block> raft = tag("flotage:raft");
        FabricTagBuilder<Block> brokenRaft = tag("flotage:broken_raft");

        for (BlockMember member : BlockMember.values()){
            rack.add(BlockRegistry.get(member.rack()));
            fence.add(BlockRegistry.get(member.fence()));
            crossedFence.add(BlockRegistry.get(member.crossedFence()));
            raft.add(BlockRegistry.get(member.raft()));
            brokenRaft.add(BlockRegistry.get(member.brokenRaft()));
        }

        for (BlockMember member : BlockMember.values()) {
            axe.add(BlockRegistry.get(member.crossedFence()));
            axe.add(BlockRegistry.get(member.fence()));
            axe.add(BlockRegistry.get(member.raft()));
            axe.add(BlockRegistry.get(member.brokenRaft()));
            axe.add(BlockRegistry.get(member.rack()));
        }
    }

    protected void block(Block block, FabricTagBuilder<Block> builder) {
        builder.add(block);
    }

    private FabricTagBuilder<Block> tag(String path) {
        return getOrCreateTagBuilder(TagKey.of(Registry.BLOCK_KEY, new Identifier(path)));
    }
}

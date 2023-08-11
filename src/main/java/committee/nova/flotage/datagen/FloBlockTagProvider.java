package committee.nova.flotage.datagen;

import committee.nova.flotage.Flotage;
import committee.nova.flotage.init.BlockRegistry;
import committee.nova.flotage.util.BlockMember;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class FloBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public FloBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        FabricTagProvider<Block>.FabricTagBuilder pickaxe = this.getOrCreateTagBuilder(TagKey.of(Registries.BLOCK.getKey(), new Identifier("mineable/pickaxe")));
        FabricTagProvider<Block>.FabricTagBuilder axe = this.getOrCreateTagBuilder(TagKey.of(Registries.BLOCK.getKey(), new Identifier("mineable/axe")));
        FabricTagProvider<Block>.FabricTagBuilder rack = this.getOrCreateTagBuilder(TagKey.of(Registries.BLOCK.getKey(), Flotage.id("rack")));
        FabricTagProvider<Block>.FabricTagBuilder fence = this.getOrCreateTagBuilder(TagKey.of(Registries.BLOCK.getKey(), Flotage.id("fence")));
        FabricTagProvider<Block>.FabricTagBuilder crossedFence = this.getOrCreateTagBuilder(TagKey.of(Registries.BLOCK.getKey(), Flotage.id("crossed_fence")));
        FabricTagProvider<Block>.FabricTagBuilder raft = this.getOrCreateTagBuilder(TagKey.of(Registries.BLOCK.getKey(), Flotage.id("raft")));
        FabricTagProvider<Block>.FabricTagBuilder brokenRaft = this.getOrCreateTagBuilder(TagKey.of(Registries.BLOCK.getKey(), Flotage.id("broken_raft")));

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
}

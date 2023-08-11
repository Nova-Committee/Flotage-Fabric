package committee.nova.flotage.datagen;

import committee.nova.flotage.Flotage;
import committee.nova.flotage.init.BlockRegistry;
import committee.nova.flotage.util.BlockMember;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Optional;


public class FloModelProvider extends FabricModelProvider {
    protected static BlockStateModelGenerator blockGenerator;
    protected static ItemModelGenerator itemGenerator;
    public FloModelProvider(FabricDataOutput dataGenerator) {
        super(dataGenerator);
    }

    private static void itemMember(BlockMember member) {
        Block raft = BlockRegistry.get(member.raft());
        Block brokenRaft = BlockRegistry.get(member.brokenRaft());
        Block rack = BlockRegistry.get(member.rack());

        itemGenerator.register(raft.asItem(), itemModel(raft));
        itemGenerator.register(brokenRaft.asItem(), itemModel(brokenRaft));
        itemGenerator.register(rack.asItem(), itemModel(rack));
    }

    private static void member(BlockMember member) {
        Block raft = BlockRegistry.get(member.raft());
        Block brokenRaft = BlockRegistry.get(member.brokenRaft());
        Block rack = BlockRegistry.get(member.rack());

        blockGenerator.registerSingleton(raft, memberTexture(member), model("raft"));
        blockGenerator.registerSingleton(brokenRaft, memberTexture(member), model("broken_raft"));
        blockGenerator.registerSingleton(rack, memberTexture(member), model("rack"));
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        blockGenerator = generator;
        for (BlockMember member : BlockMember.values()) {
            member(member);
        }
    }

    private static Model model(String path) {
        return new Model(Optional.of(Flotage.id("base/" + path)), Optional.empty(), TextureKey.TOP, TextureKey.SIDE, TextureKey.PARTICLE);
    }

    private static TextureMap memberTexture(BlockMember member) {
        Identifier id = new Identifier(member.log().getNamespace(), "block/" + member.log().getPath());
        return new TextureMap()
                .put(TextureKey.TOP, new Identifier(member.log().getNamespace(), "block/" + member.log().getPath() + "_top"))
                .put(TextureKey.SIDE, id)
                .put(TextureKey.PARTICLE, id);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        itemGenerator = generator;
        for (BlockMember member : BlockMember.values()) {
            itemMember(member);
        }
    }

    private static Model itemModel(Block block) {
        return new Model(Optional.of(Flotage.id("block/" + Registries.BLOCK.getId(block).getPath())), Optional.empty());
    }
}

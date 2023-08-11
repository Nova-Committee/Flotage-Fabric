package committee.nova.flotage.datagen;

import committee.nova.flotage.init.BlockRegistry;
import committee.nova.flotage.init.ItemRegistry;
import committee.nova.flotage.util.BlockMember;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;

public class FloBlockLootProvider extends FabricBlockLootTableProvider {

    public FloBlockLootProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        for (BlockMember member : BlockMember.values()) {
            addDrop(BlockRegistry.get(member.raft()));
            addDrop(BlockRegistry.get(member.brokenRaft()), member.repairBlock.asItem());
            addDrop(BlockRegistry.get(member.fence()));
            addDrop(BlockRegistry.get(member.crossedFence()), drops(ItemRegistry.get(member.fence()), ConstantLootNumberProvider.create(2f)));
            addDrop(BlockRegistry.get(member.rack()));
        }
    }
}

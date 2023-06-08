package committee.nova.flotage.datagen;

import committee.nova.flotage.Flotage;
import committee.nova.flotage.init.BlockMember;
import committee.nova.flotage.init.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class RBlockLootProvider extends SimpleFabricLootTableProvider {
    protected static BiConsumer<Identifier, LootTable.Builder> consumer;

    public RBlockLootProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, LootContextTypes.BLOCK);
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> biConsumer) {
        consumer = biConsumer;

        for (BlockMember member : BlockMember.values()) {
            block(member.raft(), ItemRegistry.get(member.raft()), 1);
            block(member.brokenRaft(), member.repairBlock.asItem(), 1);
            block(member.fence(), ItemRegistry.get(member.fence()), 1);
            block(member.crossedFence(), ItemRegistry.get(member.fence()), 2);
            block(member.rack(), ItemRegistry.get(member.rack()), 1);
        }
    }

    protected void block(String id, Item drop, int count) {
        consumer.accept(Flotage.id("blocks/" + id), LootTable.builder().pool(BlockLootTableGenerator.addSurvivesExplosionCondition(drop, LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f)).with(ItemEntry.builder(drop).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(count)))))));
    }
}

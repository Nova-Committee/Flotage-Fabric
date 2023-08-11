package committee.nova.flotage.init;

import committee.nova.flotage.Flotage;
import committee.nova.flotage.util.BlockMember;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TabRegistry {
    public static final Identifier TEXTURE = new Identifier("textures/gui/container/creative_inventory/flotage.png");
    public static ItemGroup TAB;

    public static void register() {
        TAB = Registry.register(Registries.ITEM_GROUP, Flotage.id("tab"),
                FabricItemGroup.builder()
                        .displayName(Text.translatable("itemGroup.flotage.tab"))
                        .entries(((displayContext, entries) -> {
                            for (BlockMember member : BlockMember.values()) {
                                entries.add(new ItemStack(ItemRegistry.get(member.raft())));
                                entries.add(new ItemStack(ItemRegistry.get(member.brokenRaft())));
                                entries.add(new ItemStack(ItemRegistry.get(member.fence())));
                                entries.add(new ItemStack(ItemRegistry.get(member.crossedFence())));
                                entries.add(new ItemStack(ItemRegistry.get(member.rack())));
                            }
                        }))
                        .icon(() -> new ItemStack(ItemRegistry.get(BlockMember.OAK.rack())))
                        .texture("flotage.png")
                        .noRenderedName()
                        .noScrollbar()
                        .build());
    }
}

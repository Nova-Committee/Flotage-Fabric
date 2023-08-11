package committee.nova.flotage.init;

import committee.nova.flotage.Flotage;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class TabRegistry {

    public static ItemGroup TAB;

    public static void register() {
        TAB = Registry.register(Registries.ITEM_GROUP, Flotage.id("tab"),
                FabricItemGroup.builder()
                        .displayName(Text.translatable("itemGroup.flotage.tab"))
                        .build());
    }
}

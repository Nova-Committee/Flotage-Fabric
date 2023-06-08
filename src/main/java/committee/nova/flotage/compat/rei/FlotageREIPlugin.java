package committee.nova.flotage.compat.rei;

import committee.nova.flotage.Flotage;
import committee.nova.flotage.compat.rei.category.RackREICategory;
import committee.nova.flotage.compat.rei.display.RackREIDisplay;
import committee.nova.flotage.impl.recipe.RackRecipe;
import committee.nova.flotage.impl.recipe.RackRecipeType;
import committee.nova.flotage.init.BlockRegistry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public class FlotageREIPlugin implements REIClientPlugin {
    public static final CategoryIdentifier<RackREIDisplay> RACK_DISPLAY_CATEGORY = CategoryIdentifier.of(Flotage.MOD_ID, "plugins/rack");

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new RackREICategory());
        List<EntryStack<?>> list = new ArrayList<>();
        Arrays.stream(BlockRegistry.getRacks()).toList().forEach((block -> list.add(EntryStacks.of(block))));
        registry.addWorkstations(RACK_DISPLAY_CATEGORY, list.toArray(new EntryStack<?>[0]));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(RackRecipe.class, RackRecipeType.INSTANCE, RackREIDisplay::new);
    }
}

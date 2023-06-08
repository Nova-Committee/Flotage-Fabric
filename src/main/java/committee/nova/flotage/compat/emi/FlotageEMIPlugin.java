package committee.nova.flotage.compat.emi;

import committee.nova.flotage.compat.emi.recipe.RackEMIRecipe;
import committee.nova.flotage.impl.recipe.RackRecipe;
import committee.nova.flotage.impl.recipe.RackRecipeType;
import committee.nova.flotage.init.BlockMember;
import committee.nova.flotage.init.BlockRegistry;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;

import static committee.nova.flotage.Flotage.id;

public class FlotageEMIPlugin implements EmiPlugin {
    public static final Identifier RACK_TEXTURE = id("textures/gui/emi_widgets.png");
    public static final EmiStack RACK_WORKSTATION = EmiStack.of(BlockRegistry.get(BlockMember.OAK.rack()));
    public static final EmiRecipeCategory RACK_CATEGORY
            = new EmiRecipeCategory(id("rack"), RACK_WORKSTATION, new EmiTexture(RACK_TEXTURE, 0, 0, 16, 16));

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(RACK_CATEGORY);

        registry.addWorkstation(RACK_CATEGORY, EmiIngredient.of(Ingredient.ofItems(BlockRegistry.getRacks())));

        RecipeManager manager = registry.getRecipeManager();

        for (RackRecipe recipe : manager.listAllOfType(RackRecipeType.INSTANCE)) {
            registry.addRecipe(new RackEMIRecipe(recipe));
        }
    }
}

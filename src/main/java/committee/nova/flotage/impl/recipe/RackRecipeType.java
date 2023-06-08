package committee.nova.flotage.impl.recipe;

import net.minecraft.recipe.RecipeType;

public class RackRecipeType implements RecipeType<RackRecipe> {
    private RackRecipeType() {}

    public static final RackRecipeType INSTANCE = new RackRecipeType();
}

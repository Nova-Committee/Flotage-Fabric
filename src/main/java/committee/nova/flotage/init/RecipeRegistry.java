package committee.nova.flotage.init;

import committee.nova.flotage.Flotage;
import committee.nova.flotage.impl.recipe.RackRecipeSerializer;
import committee.nova.flotage.impl.recipe.RackRecipeType;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.registry.Registry;


public class RecipeRegistry {
    public static void register() {
        recipe(RackRecipeSerializer.INSTANCE, RackRecipeType.INSTANCE, "rack");
    }

    private static void recipe(RecipeSerializer<?> serializer, RecipeType<?> type, String id) {
        Registry.register(Registry.RECIPE_TYPE, Flotage.id(id), type);
        Registry.register(Registry.RECIPE_SERIALIZER, Flotage.id(id), serializer);
    }

    public static RecipeType<?> getType(String id) {
        return Registry.RECIPE_TYPE.get(Flotage.id(id));
    }

    public static RecipeSerializer<?> getSerializer(String id) {
        return Registry.RECIPE_SERIALIZER.get(Flotage.id(id));
    }
}

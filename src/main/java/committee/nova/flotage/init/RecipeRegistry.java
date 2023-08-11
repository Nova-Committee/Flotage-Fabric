package committee.nova.flotage.init;

import committee.nova.flotage.Flotage;
import committee.nova.flotage.impl.recipe.RackRecipeSerializer;
import committee.nova.flotage.impl.recipe.RackRecipeType;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;


public class RecipeRegistry {
    public static void register() {
        recipe(RackRecipeSerializer.INSTANCE, RackRecipeType.INSTANCE, "rack");
    }

    private static void recipe(RecipeSerializer<?> serializer, RecipeType<?> type, String id) {
        Registry.register(Registries.RECIPE_TYPE, Flotage.id(id), type);
        Registry.register(Registries.RECIPE_SERIALIZER, Flotage.id(id), serializer);
    }

    public static RecipeType<?> getType(String id) {
        return Registries.RECIPE_TYPE.get(Flotage.id(id));
    }

    public static RecipeSerializer<?> getSerializer(String id) {
        return Registries.RECIPE_SERIALIZER.get(Flotage.id(id));
    }
}

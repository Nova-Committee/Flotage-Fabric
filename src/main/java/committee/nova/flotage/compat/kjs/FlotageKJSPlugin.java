package committee.nova.flotage.compat.kjs;

import committee.nova.flotage.compat.kjs.recipe.RackRecipeJS;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.RegisterRecipeHandlersEvent;

import static committee.nova.flotage.Flotage.id;

public class FlotageKJSPlugin extends KubeJSPlugin {
    @Override
    public void addRecipes(RegisterRecipeHandlersEvent event) {
        event.register(id("rack"), RackRecipeJS::new);
    }
}

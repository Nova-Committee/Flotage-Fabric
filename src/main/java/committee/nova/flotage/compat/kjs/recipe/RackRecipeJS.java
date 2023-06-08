package committee.nova.flotage.compat.kjs.recipe;

import committee.nova.flotage.init.WorkingMode;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.util.ListJS;

public class RackRecipeJS extends RecipeJS {
    @Override
    public void create(ListJS args) {
        this.inputItems.add(this.parseIngredientItem(args.get(0)));
        this.outputItems.add(this.parseResultItem(args.get(1)));

        json.addProperty("processtime", (Number) args.get(2));

        if (args.size() > 3)
            json.addProperty("mode", WorkingMode.match((String) args.get(3)).toString());
    }

    @Override
    public void deserialize() {
        this.outputItems.add(this.parseResultItem(this.json.get("result")));
        this.inputItems.add(this.parseIngredientItem(this.json.get("ingredient")));
    }

    @Override
    public void serialize() {
        if (this.serializeOutputs) {
            this.json.add("result", this.outputItems.get(0).toResultJson());
        }

        if (this.serializeInputs) {
            this.json.add("ingredient", this.inputItems.get(0).toJson());
        }
    }
}

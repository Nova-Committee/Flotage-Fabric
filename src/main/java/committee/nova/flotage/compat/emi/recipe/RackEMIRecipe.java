package committee.nova.flotage.compat.emi.recipe;

import committee.nova.flotage.compat.emi.FlotageEMIPlugin;
import committee.nova.flotage.impl.recipe.RackRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RackEMIRecipe implements EmiRecipe {

    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public RackEMIRecipe(RackRecipe recipe) {
        this.id = recipe.getId();
        this.input = List.of(EmiIngredient.of(recipe.getIngredients().get(0)));
        this.output = List.of(EmiStack.of(recipe.getOutput()));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return FlotageEMIPlugin.RACK_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output;
    }

    @Override
    public int getDisplayWidth() {
        return 110;
    }

    @Override
    public int getDisplayHeight() {
        return 40;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(input.get(0), 15, 10);
        widgets.addSlot(output.get(0), 79, 10).recipeContext(this);

        widgets.addFillingArrow(46, 10, 20 * 20);
    }
}

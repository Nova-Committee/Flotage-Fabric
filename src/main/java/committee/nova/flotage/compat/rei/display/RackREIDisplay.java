package committee.nova.flotage.compat.rei.display;

import committee.nova.flotage.compat.rei.FlotageREIPlugin;
import committee.nova.flotage.impl.recipe.RackRecipe;
import committee.nova.flotage.util.WorkingMode;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.Collections;

public class RackREIDisplay extends BasicDisplay {
    private final int processtime;
    private final WorkingMode mode;

    public RackREIDisplay(RackRecipe recipe) {
        super(EntryIngredients.ofIngredients(recipe.getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.getResult())));
        this.processtime = recipe.getProcesstime();
        this.mode = recipe.getMode();
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return FlotageREIPlugin.RACK_DISPLAY_CATEGORY;
    }


    public int getProcesstime() {
        return processtime;
    }

    public WorkingMode getMode() {
        return mode;
    }
}

package committee.nova.flotage.compat.rei.category;

import committee.nova.flotage.Flotage;
import committee.nova.flotage.compat.rei.FlotageREIPlugin;
import committee.nova.flotage.compat.rei.display.RackREIDisplay;
import committee.nova.flotage.init.BlockMember;
import committee.nova.flotage.init.BlockRegistry;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Arrow;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
@ApiStatus.Internal
public class RackREICategory implements DisplayCategory<RackREIDisplay> {
    private static final Identifier GUI_TEXTURE = Flotage.id("textures/gui/rack_rei.png");

    @Override
    public CategoryIdentifier<? extends RackREIDisplay> getCategoryIdentifier() {
        return FlotageREIPlugin.RACK_DISPLAY_CATEGORY;
    }

    @Override
    public Text getTitle() {
        return new TranslatableText("block.flotage.rack");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(BlockRegistry.get(BlockMember.OAK.rack()));
    }

    @Override
    public List<Widget> setupDisplay(RackREIDisplay display, Rectangle bounds) {
        Point origin = bounds.getLocation();
        final List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createRecipeBase(bounds));

        Rectangle bgBounds = centeredIntoRecipeBase(new Point(origin.x, origin.y), 91, 54);
        widgets.add(Widgets.createTexturedWidget(GUI_TEXTURE, new Rectangle(bgBounds.x, bgBounds.y, 91, 54), 10, 9));

        widgets.add(Widgets.createSlot(new Point(bgBounds.x + 7, bgBounds.y + 1))
                .entries(display.getInputEntries().get(0)).markInput().disableBackground());

        Arrow arrow = Widgets.createArrow(new Point(bgBounds.x + 39, bgBounds.y + 18)).animationDurationTicks(20);
        widgets.add(arrow);

        Text text = new TranslatableText("tip.flotage.rack.mode").append(new TranslatableText("tip.flotage.rack.mode." + display.getMode().toString()));
        widgets.add(Widgets.withTooltip(arrow, new TranslatableText("tip.flotage.rack.processtime", display.getProcesstime() / 20), text));

        widgets.add(Widgets.createSlot(new Point(bgBounds.x + 74, bgBounds.y + 18))
                .entries(display.getOutputEntries().get(0)).markOutput().disableBackground());

        return widgets;
    }

    public static Rectangle centeredIntoRecipeBase(Point origin, int width, int height) {
        return centeredInto(new Rectangle(origin.x, origin.y, 150, 74), width, height);
    }

    public static Rectangle centeredInto(Rectangle origin, int width, int height) {
        return new Rectangle(origin.x + (origin.width - width) / 2, origin.y + (origin.height - height) / 2, width, height);
    }

    @Override
    public int getDisplayHeight() {
        return 73;
    }
}

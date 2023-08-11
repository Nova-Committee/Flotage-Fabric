package committee.nova.flotage.impl.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import committee.nova.flotage.util.WorkingMode;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import static net.minecraft.recipe.ShapedRecipe.outputFromJson;

public class RackRecipeSerializer implements RecipeSerializer<RackRecipe> {
    private RackRecipeSerializer() {
    }

    public static final RackRecipeSerializer INSTANCE = new RackRecipeSerializer();

    @Override
    public RackRecipe read(Identifier id, JsonObject json) {
        Ingredient ingredient = Ingredient.fromJson(JsonHelper.getObject(json, "ingredient"));
        ItemStack result;
        JsonElement element = json.get("result");
        if (element.isJsonObject())
            result = outputFromJson((JsonObject) element);
        else {
            String string = element.getAsString();
            Item item = Registries.ITEM.getOrEmpty(new Identifier(string)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + string + "'"));
            result = new ItemStack(item);
        }
        int processtime = json.get("processtime").getAsInt();
        WorkingMode mode = WorkingMode.match(json.get("mode").getAsString());

        return new RackRecipe(id, ingredient, result, processtime, mode);
    }

    @Override
    public RackRecipe read(Identifier id, PacketByteBuf buf) {
        Ingredient ingredient = Ingredient.fromPacket(buf);
        ItemStack result = buf.readItemStack();
        int processtime = buf.readInt();
        WorkingMode mode = WorkingMode.match(buf.readString());
        return new RackRecipe(id, ingredient, result, processtime, mode);
    }

    @Override
    public void write(PacketByteBuf buf, RackRecipe recipe) {
        recipe.ingredient.write(buf);
        buf.writeItemStack(recipe.result);
        buf.writeInt(recipe.getProcesstime());
        buf.writeString(recipe.getMode().toString());
    }
}

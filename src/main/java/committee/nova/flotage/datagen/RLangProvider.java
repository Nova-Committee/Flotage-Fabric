package committee.nova.flotage.datagen;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import committee.nova.flotage.Flotage;
import committee.nova.flotage.init.BlockRegistry;
import committee.nova.flotage.init.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.Map;

public abstract class RLangProvider implements DataProvider {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    protected Map<String, String> map = Maps.newLinkedHashMap();
    protected final DataGenerator generator;
    private final String locale;

    public RLangProvider(DataGenerator generator, String locale) {
        this.generator = generator;
        this.locale = locale;
    }

    @Override
    public void run(DataCache cache) {
        Path path = getLangJsonPath(this.generator.getOutput());
        this.map.clear();
        init();
        JsonObject object = new JsonObject();
        map.forEach(object::addProperty);

        try {
            DataProvider.writeToPath(GSON, cache, object, path);
        } catch (Exception var7) {
            LOGGER.error("Couldn't save {}", path, var7);
        }
    }

    private Path getLangJsonPath(Path root) {
        return root.resolve("assets/" + Flotage.MOD_ID + "/lang/" + locale + ".json");
    }

    @Override
    public String getName() {
        return beautifyName(locale) + " Language Files";
    }

    protected abstract void init();

    protected void add(String key, String value) {
        map.put(key, value);
    }

    protected void add(Item item, String name) {
        map.put(item.getTranslationKey(), name);
    }

    protected void item(String id) {
        map.put(ItemRegistry.get(id).getTranslationKey(), beautifyName(id));
    }

    protected void item(String id, String name) {
        map.put(ItemRegistry.get(id).getTranslationKey(), name);
    }

    protected void block(String id, String name) {
        map.put(BlockRegistry.get(id).getTranslationKey(), name);
    }

    protected void tip(String path, String tip) {
        map.put("tip." + Flotage.MOD_ID + "." + path, tip);
    }

    @SuppressWarnings("SameParameterValue")
    protected void add( ItemGroup tab, String name) {
        map.put("itemGroup." + tab.getName(), name);
    }

    protected void add(Block block, String name) {
        map.put(block.getTranslationKey(), name);
    }

    public static String beautifyName(String name) {
        String[] str1 = name.split("_");
        StringBuilder str2 = new StringBuilder();
        for(int i = 0 ; i < str1.length; i++) {
            str1[i] = str1[i].substring(0,1).toUpperCase() + str1[i].substring(1);
            if(i == str1.length-1)
                str2.append(str1[i]);
            else
                str2.append(str1[i]).append(" ");
        }
        return str2.toString();
    }
}

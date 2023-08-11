package committee.nova.flotage;

import committee.nova.flotage.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class FlotageDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        generator.createPack().addProvider(FloModelProvider::new);
        generator.createPack().addProvider(FloBlockLootProvider::new);
        generator.createPack().addProvider(FloLangProvider.English::new);
        generator.createPack().addProvider(FloLangProvider.Chinese::new);
        generator.createPack().addProvider(FloBlockTagProvider::new);

        Flotage.LOGGER.info("Flotage dataGen done!");
    }
}

package committee.nova.flotage;

import committee.nova.flotage.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class FlotageDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        generator.addProvider(RModelProvider::new);
        generator.addProvider(RBlockLootProvider::new);
        generator.addProvider(LangENUSProvider::new);
        generator.addProvider(LangZHCNProvider::new);
        generator.addProvider(RBlockTagProvider::new);

        Flotage.LOGGER.info("Fabricatech dataGen done!");
    }
}

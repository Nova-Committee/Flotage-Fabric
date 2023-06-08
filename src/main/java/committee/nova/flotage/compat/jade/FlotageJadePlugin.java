package committee.nova.flotage.compat.jade;

import committee.nova.flotage.compat.jade.provider.RackBlockTipProvider;
import committee.nova.flotage.impl.block.RackBlock;
import committee.nova.flotage.impl.tile.RackBlockEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class FlotageJadePlugin implements IWailaPlugin {

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(RackBlockTipProvider.INSTANCE, RackBlock.class);
    }

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(RackBlockTipProvider.INSTANCE, RackBlockEntity.class);
    }
}

package committee.nova.flotage.datagen;

import committee.nova.flotage.Flotage;
import committee.nova.flotage.init.BlockMember;
import committee.nova.flotage.init.WorkingMode;
import net.minecraft.data.DataGenerator;

public class LangENUSProvider extends RLangProvider {
    public LangENUSProvider(DataGenerator generator) {
        super(generator, "en_us");
    }

    @Override
    protected void init() {
        add(Flotage.TAB, "Flotage");
        add("tip.flotage.rack.processtime", "%s second(s) processing");
        add("tip.flotage.rack.mode", "Condition: ");
        add("block.flotage.rack", "Rack Processing");
        add("config.jade.plugin_flotage.rack_blockentity", "Rack Working Mode");
        add("emi.category.flotage.rack", "Rack Processing");

        for (WorkingMode mode : WorkingMode.values()) {
            add("tip.flotage.rack.mode." + mode.toString(), beautifyName(mode.toString()));
        }

        for (BlockMember member : BlockMember.values()) {
            block(member.raft(), beautifyName(member.raft()));
            block(member.brokenRaft(), beautifyName(member.brokenRaft()));
            block(member.fence(), beautifyName(member.fence()));
            block(member.crossedFence(), beautifyName(member.crossedFence()));
            block(member.rack(), beautifyName(member.rack()));
        }
    }
}

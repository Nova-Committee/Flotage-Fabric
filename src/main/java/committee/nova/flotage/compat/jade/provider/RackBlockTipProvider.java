package committee.nova.flotage.compat.jade.provider;

import committee.nova.flotage.Flotage;
import committee.nova.flotage.util.WorkingMode;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class RackBlockTipProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    public static final RackBlockTipProvider INSTANCE = new RackBlockTipProvider();

    private RackBlockTipProvider() {}

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
        if (accessor.getServerData().contains("WorkingMode")) {
            WorkingMode mode = WorkingMode.match(accessor.getServerData().getString("WorkingMode"));
            MutableText text = Text.translatable("tip.flotage.rack.mode").append(Text.translatable("tip.flotage.rack.mode." + mode.toString()));
            tooltip.add(text);
        }
    }

    @Override
    public Identifier getUid() {
        return Flotage.id("rack_blockentity");
    }

    @Override
    public void appendServerData(NbtCompound nbt, BlockAccessor blockAccessor) {
        nbt.putString("WorkingMode", WorkingMode.judge(blockAccessor.getLevel(), blockAccessor.getPosition()).toString());
    }
}

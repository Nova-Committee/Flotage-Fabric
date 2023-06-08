package committee.nova.flotage.compat.jade.provider;

import committee.nova.flotage.Flotage;
import committee.nova.flotage.init.WorkingMode;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class RackBlockTipProvider implements IBlockComponentProvider, IServerDataProvider<BlockEntity> {
    public static final RackBlockTipProvider INSTANCE = new RackBlockTipProvider();

    private RackBlockTipProvider() {}

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
        if (accessor.getServerData().contains("WorkingMode")) {
            WorkingMode mode = WorkingMode.match(accessor.getServerData().getString("WorkingMode"));
            MutableText text = new TranslatableText("tip.flotage.rack.mode").append(new TranslatableText("tip.flotage.rack.mode." + mode.toString()));
            tooltip.add(text);
        }
    }

    @Override
    public Identifier getUid() {
        return Flotage.id("rack_blockentity");
    }

    @Override
    public void appendServerData(NbtCompound nbt, ServerPlayerEntity serverPlayerEntity, World world, BlockEntity blockEntity, boolean b) {
        nbt.putString("WorkingMode", WorkingMode.judge(world, blockEntity.getPos()).toString());
    }
}

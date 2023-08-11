package committee.nova.flotage.impl.block;

import committee.nova.flotage.init.BlockRegistry;
import committee.nova.flotage.util.BlockMember;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.Instrument;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;

public class WoodenRaftBlock extends RaftBlock {

    public WoodenRaftBlock(BlockMember member) {
        super(Settings.create().instrument(Instrument.BASS).sounds(BlockSoundGroup.WOOD).burnable().strength(1.5F,1F), member);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(WATERLOGGED);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        if (world.hasRain(pos.up()) && world.random.nextBoolean()) {
            world.setBlockState(pos, BlockRegistry.get(member.brokenRaft()).getDefaultState(), Block.NOTIFY_ALL);
        }
    }
}

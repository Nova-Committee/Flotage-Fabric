package committee.nova.flotage.impl.block;

import committee.nova.flotage.init.BlockMember;
import committee.nova.flotage.init.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class WoodenRaftBlock extends RaftBlock {

    public WoodenRaftBlock(BlockMember member) {
        super(Settings.of(Material.WOOD).strength(1.5F,1F), member);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(WATERLOGGED);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.hasRain(pos.up()) && world.random.nextBoolean()) {
            world.setBlockState(pos, BlockRegistry.get(member.brokenRaft()).getDefaultState(), Block.NOTIFY_ALL);
        }
    }

}

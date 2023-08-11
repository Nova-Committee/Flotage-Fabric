package committee.nova.flotage.impl.block;

import committee.nova.flotage.init.BlockRegistry;
import committee.nova.flotage.util.BlockMember;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.Instrument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BrokenRaftBlock extends RaftBlock {
    public BrokenRaftBlock( BlockMember member) {
        super(Settings.create().instrument(Instrument.BASS).sounds(BlockSoundGroup.WOOD).burnable().noCollision().strength(1.5F,1F), member);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() == member.repairBlock.asItem()) {
            Boolean property = world.getBlockState(hit.getBlockPos()).get(WATERLOGGED);
            world.setBlockState(pos, BlockRegistry.get(member.raft()).getDefaultState().with(WATERLOGGED, property), Block.NOTIFY_ALL);
            if (!player.isCreative() && world.random.nextFloat() < 0.3f)
                stack.decrement(1);
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }
}

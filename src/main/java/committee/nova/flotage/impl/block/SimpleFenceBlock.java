package committee.nova.flotage.impl.block;

import committee.nova.flotage.util.BlockMember;
import committee.nova.flotage.init.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class SimpleFenceBlock extends Block implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public final BlockMember member;

    public SimpleFenceBlock(BlockMember member) {
        super(Settings.create().instrument(Instrument.BASS).sounds(BlockSoundGroup.WOOD).burnable().strength(1.5F,1F));
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.NORTH));
        this.member = member;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack handStack = player.getStackInHand(hand);
        if (handStack.getItem() instanceof BlockItem blockItem) {
            if (blockItem.getBlock() instanceof SimpleFenceBlock fence) {
                if (fence == this) {
                    world.setBlockState(pos, BlockRegistry.get(member.crossedFence()).getDefaultState(), Block.NOTIFY_ALL);
                    if (!player.isCreative()) {
                        handStack.decrement(1);
                    }
                    return ActionResult.success(world.isClient);
                }
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return createShape(state.get(FACING) == Direction.WEST || state.get(FACING) == Direction.EAST);
    }

    protected static VoxelShape createShape(boolean rotated) {
        VoxelShape shape1;
        VoxelShape shape2;
        VoxelShape shape3;
        VoxelShape shape4;
        if (rotated) {
            shape1 = Block.createCuboidShape(6.75, 0, 3, 9.25, 16, 5.5);
            shape2 = Block.createCuboidShape(6.75, 0, 10.5, 9.25, 16, 13);
            shape3 = Block.createCuboidShape(7,5.5,0,9, 7.5, 16);
            shape4 = Block.createCuboidShape(7,11,0,9, 13, 16);
        }else {
            shape1 = Block.createCuboidShape(3, 0, 6.75, 5.5, 16, 9.25);
            shape2 = Block.createCuboidShape(10.5, 0, 6.75, 13, 16, 9.25);
            shape3 = Block.createCuboidShape(0,5.5,7,16, 7.5, 9);
            shape4 = Block.createCuboidShape(0,11,7,16, 13, 9);
        }
        return VoxelShapes.union(shape1, shape2, shape3, shape4);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World worldAccess = ctx.getWorld();
        boolean bl = worldAccess.getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER;
        return this.getDefaultState().with(WATERLOGGED, bl).with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Nullable
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }
}

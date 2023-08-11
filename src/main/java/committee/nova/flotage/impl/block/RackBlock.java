package committee.nova.flotage.impl.block;

import committee.nova.flotage.impl.tile.RackBlockEntity;
import committee.nova.flotage.util.BlockMember;
import committee.nova.flotage.init.TileRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.Instrument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class RackBlock extends BlockWithEntity implements BlockEntityProvider {
    public final BlockMember member;

    public RackBlock(BlockMember member) {
        super(Settings.create().instrument(Instrument.BASS).sounds(BlockSoundGroup.WOOD).burnable().strength(1.5F,1F));
        this.member = member;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult result) {
        if (!(world.getBlockEntity(pos) instanceof RackBlockEntity tile))
            return ActionResult.PASS;

        ItemStack handStack = player.getStackInHand(hand);
        ItemStack storedStack = tile.getItem();
        tile.setItemDirection(player.getHorizontalFacing());
        if (handStack.isEmpty() && storedStack.isEmpty()) {
            return ActionResult.PASS;
        } else if (handStack.isEmpty()) {
            if (!player.getInventory().insertStack(tile.getItem())) {
                tile.inventoryChanged();
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), tile.getItem());
            }
            return ActionResult.success(world.isClient());
        }else if (storedStack.isEmpty()) {
            tile.setStack(0, handStack.split(1));
            tile.inventoryChanged();
            return ActionResult.success(world.isClient());
        }else {
            if (storedStack.getItem() != handStack.getItem()) {
                if (!player.getInventory().insertStack(tile.getItem())) {
                    tile.inventoryChanged();
                    ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), tile.getItem());
                }
                tile.setStack(0, handStack.split(1));
                tile.inventoryChanged();
                return ActionResult.success(world.isClient());
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape1 = Block.createCuboidShape(0,0,0,1,16,1);
        VoxelShape shape2 = Block.createCuboidShape(15,0,0,16,16,1);
        VoxelShape shape3 = Block.createCuboidShape(0,0,15,1,16,16);
        VoxelShape shape4 = Block.createCuboidShape(15,0,15,16,16,16);
        VoxelShape shape5 = Block.createCuboidShape(0,14.05,0,16,15.5,16);
        return VoxelShapes.union(shape1, shape2, shape3, shape4, shape5);
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RackBlockEntity(pos, state);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return !world.getBlockState(pos.down()).isOf(this);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof RackBlockEntity) {
            if (world instanceof ServerWorld) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
            }
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof RackBlockEntity tile) {
            return  !tile.getItem().isEmpty() ? 10 : 0;
        }
        return super.getComparatorOutput(state, world, pos);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return RackBlock.checkType(world, type, TileRegistry.RACK_BLOCK_ENTITY);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> checkType(World world, BlockEntityType<T> givenType, BlockEntityType<? extends RackBlockEntity> expectedType) {
        return world.isClient ? null : RackBlock.checkType(givenType, expectedType, RackBlockEntity::tick);
    }
}

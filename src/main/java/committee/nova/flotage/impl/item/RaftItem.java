package committee.nova.flotage.impl.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class RaftItem extends BlockItem {

    public RaftItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = BucketItem.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        }
        if (blockHitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = blockHitResult.getBlockPos();
            if (world.getFluidState(blockPos).isOf(Fluids.WATER)) {
                ActionResult actionResult = this.place(new ItemPlacementContext(user, hand, itemStack, blockHitResult));
                switch (actionResult) {
                    case SUCCESS -> {
                        return TypedActionResult.success(itemStack);
                    }
                    case CONSUME -> {
                        return TypedActionResult.consume(itemStack);
                    }
                    case FAIL -> {
                        return TypedActionResult.fail(itemStack);
                    }
                    case PASS ->  {
                        return TypedActionResult.pass(itemStack);
                    }
                }
            }
        }
        return super.use(world, user, hand);
    }
}

package committee.nova.flotage.impl.tile;

import committee.nova.flotage.impl.recipe.RackRecipe;
import committee.nova.flotage.impl.recipe.RackRecipeType;
import committee.nova.flotage.init.TileRegistry;
import committee.nova.flotage.init.WorkingMode;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RackBlockEntity extends BlockEntity implements BlockEntityInv, RecipeUnlocker, RecipeInputProvider {
    public final RecipeType<RackRecipe> recipeType = RackRecipeType.INSTANCE;

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private int processTimeTotal;
    private int processTime;
    private Direction itemDirection = Direction.NORTH;
    private final Object2IntOpenHashMap<Identifier> recipesUsed = new Object2IntOpenHashMap<>();

    @SuppressWarnings("UnstableApiUsage")
    private final SingleStackStorage[] internalStoreStorage = new SingleStackStorage[0];

    public RackBlockEntity(BlockPos pos, BlockState state) {
        super(TileRegistry.get("rack"), pos, state);
    }

    @NotNull
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, items);
        this.processTimeTotal = nbt.getShort("ProcessTimeTotal");
        this.processTime = nbt.getShort("ProcessTime");
        this.itemDirection = Direction.valueOf(nbt.getString("ItemDirection"));
     
        NbtCompound nbtCompound = nbt.getCompound("RecipesUsed");
        for (String string : nbtCompound.getKeys()) {
            this.recipesUsed.put(new Identifier(string), nbtCompound.getInt(string));
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, items);
        nbt.putShort("ProcessTimeTotal", (short)this.processTimeTotal);
        nbt.putShort("ProcessTime", (short)this.processTime);
        nbt.putString("ItemDirection", this.itemDirection.name());

        NbtCompound nbtCompound = new NbtCompound();
        this.recipesUsed.forEach((identifier, count) -> nbtCompound.putInt(identifier.toString(), (int)count));
        nbt.put("RecipesUsed", nbtCompound);
    }

    public boolean setItem(ItemStack itemStack) {
        if (isEmpty() && !itemStack.isEmpty()) {
            items.set(0, itemStack);
            return true;
        }

        return false;
    }

    public void clean() {
        items.set(0, ItemStack.EMPTY);
    }

    public ItemStack getItem() {
        return items.get(0);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    public void inventoryChanged() {
        this.markDirty();
        if (world != null) {
            world.updateListeners(getPos(), getCachedState(), getCachedState(), (1) | (1 << 1));
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, RackBlockEntity tile) {
        ItemStack itemStack = tile.getItem();
        boolean flag = false;
        if (!itemStack.isEmpty()) {
            Optional<RackRecipe> optional = world.getRecipeManager().getFirstMatch(tile.recipeType, tile, world);

            if (optional.isPresent()) {
                RackRecipe recipe = optional.get();
                tile.processTimeTotal = recipe.getProcesstime();

                if (WorkingMode.judge(world, pos) == recipe.getMode()) {
                    tile.processTime++;
                    if (tile.processTime == tile.processTimeTotal) {
                        tile.processTime = 0;
                        tile.setStack(0, recipe.getOutput());
                        tile.setLastRecipe(recipe);
                        flag = true;
                    }
                }
            }
        }
        if (flag) {
            tile.inventoryChanged();
        }
    }

    @Override
    public void provideRecipeInputs(RecipeMatcher finder) {
        for (ItemStack itemStack : this.items) {
            finder.addInput(itemStack);
        }
    }

    @Override
    public void setLastRecipe(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            Identifier identifier = recipe.getId();
            this.recipesUsed.addTo(identifier, 1);
        }
    }

    @Nullable
    @Override
    public Recipe<?> getLastRecipe() {
        return null;
    }

    public Direction getItemDirection() {
        return itemDirection;
    }

    public void setItemDirection(Direction itemDirection) {
        this.itemDirection = itemDirection;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return processTime == 0;
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }

    @SuppressWarnings("UnstableApiUsage")
    public Storage<ItemVariant> getExposedStorage(Direction side) {
        return new CombinedStorage<>(List.of(
                getInternalStoreStorage(side),
                InventoryStorage.of(this, side)
        ));
    }

    @SuppressWarnings("UnstableApiUsage")
    private Storage<ItemVariant> getInternalStoreStorage(Direction side) {
        Objects.requireNonNull(side);
        if (internalStoreStorage[0] == null) {
            internalStoreStorage[0] = new SingleStackStorage() {
                @Override
                protected ItemStack getStack() {
                    return getItem();
                }

                @Override
                protected void setStack(ItemStack stack) {
                    if (stack.isEmpty()) {
                        clean();
                    } else {
                        setItem(stack);
                    }
                }

                @Override
                protected int getCapacity(ItemVariant itemVariant) {
                    return 1 - super.getCapacity(itemVariant);
                }

                @Override
                protected boolean canInsert(ItemVariant itemVariant) {
                    return RackBlockEntity.this.canInsert(0, itemVariant.toStack(), side);
                }

                @Override
                protected boolean canExtract(ItemVariant itemVariant) {
                    return RackBlockEntity.this.canExtract(0, itemVariant.toStack(), side);
                }

                @Override
                protected void onFinalCommit() {
                    inventoryChanged();
                }
            };
        }
        return internalStoreStorage[0];
    }
}

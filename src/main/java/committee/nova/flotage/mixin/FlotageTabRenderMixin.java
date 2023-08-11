package committee.nova.flotage.mixin;


import committee.nova.flotage.init.TabRegistry;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public abstract class FlotageTabRenderMixin {

    @Inject(method = "renderTabIcon", at = @At("HEAD"), cancellable = true)
    private void renderTabIcon(DrawContext context, ItemGroup group, CallbackInfo info) {
        if (group.equals(TabRegistry.TAB) && group == TabRegistry.TAB) {
            boolean bl = group == CreativeInventoryScreen.selectedTab;
            boolean bl2 = group.getRow() == ItemGroup.Row.TOP;
            int i = group.getColumn();
            int j = i * 26;
            int k = 0;
            int l = ((CreativeInventoryScreen)(Object)this).x + ((CreativeInventoryScreen)(Object)this).getTabX(group);
            int m = ((CreativeInventoryScreen)(Object)this).y;
            int n = 32;
            if (bl) {
                k += 32;
            }
            if (bl2) {
                m -= 29;
            } else {
                k += 64;
                m += ((CreativeInventoryScreen)(Object)this).backgroundHeight - 3;
            }
            context.drawTexture(TabRegistry.TEXTURE, l, m, j, k, 26, 32);
            context.getMatrices().push();
            context.getMatrices().translate(0.0f, 0.0f, 100.0f);
            int n2 = bl2 ? 1 : -1;
            ItemStack itemStack = group.getIcon();
            if (bl) {
                m -= bl2 ? 3 : -3;
            }
            context.drawItem(itemStack, l += 5, m += 8 + n2);
            context.drawItemInSlot(((CreativeInventoryScreen)(Object)this).textRenderer, itemStack, l, m);
            context.getMatrices().pop();

            info.cancel();
        }
    }
}

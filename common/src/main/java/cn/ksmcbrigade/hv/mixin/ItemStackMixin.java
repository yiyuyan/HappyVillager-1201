package cn.ksmcbrigade.hv.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Shadow private int count;

    @Shadow @Final @Deprecated private Item item;

    @Inject(method = "isEmpty",at = @At("RETURN"),cancellable = true)
    public void isEmpty(CallbackInfoReturnable<Boolean> cir){
        ItemStack stack = (ItemStack) (Object)(this);
        cir.setReturnValue(stack==ItemStack.EMPTY|| this.count == 0 || this.item == Items.AIR);
    }
}

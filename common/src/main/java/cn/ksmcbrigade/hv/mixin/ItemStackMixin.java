package cn.ksmcbrigade.hv.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin{
    @Unique
    public boolean unEmpty = false;

    @Inject(method = "isEmpty",at = @At("RETURN"),cancellable = true)
    public void isEmpty(CallbackInfoReturnable<Boolean> cir){
        if(this.unEmpty)cir.setReturnValue(false);
    }
}

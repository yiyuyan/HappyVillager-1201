package cn.ksmcbrigade.hv.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;

@Mixin(MerchantOffer.class)
public class MerchantOfferMixin {

    @Inject(method = "getCostA",at = @At("RETURN"),cancellable = true)
    public void costA(CallbackInfoReturnable<ItemStack> cir){
        ItemStack stack = cir.getReturnValue();
        try {
            Field u = stack.getClass().getDeclaredField("unEmpty");
            u.setAccessible(true);
            u.set(stack,true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        cir.setReturnValue(stack);
    }
    
    @Redirect(method = "getCostA",at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"))
    public int modifyMax(int a, int b){
        return b;
    }

    @ModifyConstant(method = "getCostA",constant = @Constant(intValue = 1))
    public int modify(int constant){
        return Integer.MIN_VALUE;
    }
}

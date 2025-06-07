package cn.ksmcbrigade.hv.mixin;

import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(MerchantOffer.class)
public class MerchantOfferMixin {

    @Redirect(method = "getCostA",at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"))
    public int modifyMax(int a, int b){
        return b;
    }

    @Redirect(method = "getCostA",at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(III)I"))
    public int modify(int p_14046_, int p_14047_, int p_14048_){
        return p_14046_;
    }
}

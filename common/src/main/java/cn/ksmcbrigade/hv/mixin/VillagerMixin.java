package cn.ksmcbrigade.hv.mixin;

import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Villager.class)
public class VillagerMixin {
    @ModifyArg(method = "updateSpecialPrices",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/trading/MerchantOffer;addToSpecialPriceDiff(I)V"))
    public int addSpecialPriceDiff(int v){
        return -Math.abs(v);
    }
}

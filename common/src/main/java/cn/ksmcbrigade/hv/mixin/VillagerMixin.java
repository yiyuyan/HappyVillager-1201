package cn.ksmcbrigade.hv.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {

    @Unique
    private int last = 0;

    public VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
    }

    @Redirect(method = "updateSpecialPrices",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/trading/MerchantOffer;addToSpecialPriceDiff(I)V"))
    public void addSpecialPriceDiff(MerchantOffer instance, int add){
        instance.addToSpecialPriceDiff(-Math.abs(add));
        if(instance.getSpecialPriceDiff()>last){
            instance.setSpecialPriceDiff(last);
        }
        else if(instance.getSpecialPriceDiff()<last){
            last = instance.getSpecialPriceDiff();
        }
    }
}

package cn.ksmcbrigade.hv.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {

    @Unique
    private float heal = 20;

    @Inject(method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V",at = @At("TAIL"))
    public void init(EntityType entityType, Level level, CallbackInfo ci){
        this.heal = this.getMaxHealth();
    }

    @Unique
    private Random random = new Random();

    public VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyArg(method = "updateSpecialPrices",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/trading/MerchantOffer;addToSpecialPriceDiff(I)V"))
    public int addSpecialPriceDiff(int v){
        return -Math.abs(v);
    }

    @Unique
    @Override
    public void tick(){
        super.tick();
        if(this.getHealth()<this.heal){
            this.heal = this.getHealth();
            updatePrize();
        }

    }

    @Unique
    @Override
    public boolean hurt(DamageSource source,float value){
        updatePrize();
        return super.hurt(source,value);
    }

    @Unique
    private void updatePrize(){
        if(this.isClientSide()){
            System.out.println("CLIENT SIDE");
            return;
        }
        for (MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.addToSpecialPriceDiff(-random.nextInt(0, 1));
        }
    }
}

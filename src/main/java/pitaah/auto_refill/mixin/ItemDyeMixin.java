package pitaah.auto_refill.mixin;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemDye;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pitaah.auto_refill.AutoRefill;

@Mixin(value = ItemDye.class, remap = false)
public abstract class ItemDyeMixin {

	@Inject(method = "onItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/IBonemealable;onBonemealUsed(Lnet/minecraft/core/item/ItemStack;Lnet/minecraft/core/entity/player/EntityPlayer;Lnet/minecraft/core/world/World;IIILnet/minecraft/core/util/helper/Side;DD)Z"))
	private void HandleOnItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> cir) {
		AutoRefill.CheckRefill(entityplayer, world, true);
	}

}

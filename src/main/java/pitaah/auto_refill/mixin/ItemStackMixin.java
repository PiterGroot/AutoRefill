package pitaah.auto_refill.mixin;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pitaah.auto_refill.AutoRefill;

@Mixin(value = ItemStack.class, remap = false)
public abstract class ItemStackMixin {

	@Inject(method = "consumeItem", at = @At("HEAD"))
	private void OnConsumeItem(EntityPlayer entityplayer, CallbackInfoReturnable<Boolean> ci)
	{
		AutoRefill.CheckRefill(entityplayer, entityplayer.world, true);
	}
}

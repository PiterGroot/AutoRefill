package pitaah.auto_refill.mixin;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pitaah.auto_refill.AutoRefill;

@Mixin(value = EntityPlayer.class, remap = false)
public class EntityPlayerMixin {

	@Inject(method = "dropCurrentItem", at = @At("HEAD"))
	private void OndropCurrentItem(boolean dropFullStack, CallbackInfo ci)
	{
		AutoRefill.CheckRefillFromDropping(dropFullStack);
	}
}

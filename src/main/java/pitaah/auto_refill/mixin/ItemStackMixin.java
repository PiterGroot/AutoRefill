package pitaah.auto_refill.mixin;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pitaah.auto_refill.AutoRefill;
import pitaah.auto_refill.AutoRefillModSettingsRegister;

@Mixin(value = ItemStack.class, remap = false)
public abstract class ItemStackMixin {

	@Inject(method = "consumeItem", at = @At("HEAD"))
	private void OnConsumeItem(EntityPlayer entityplayer, CallbackInfoReturnable<Boolean> ci)
	{
		if(!AutoRefillModSettingsRegister.modSettings.autoRefillDoAnyRefillOnItems().value)
			return;

		AutoRefill.CheckRefill(entityplayer, entityplayer.world, true);
	}

	@Inject(method = "damageItem", at = @At("TAIL"))
	private void OndamageItem(int i, Entity entity, CallbackInfo ci)
	{
		AutoRefill.CheckRefillForDurability((EntityPlayer)entity, true);
	}
}

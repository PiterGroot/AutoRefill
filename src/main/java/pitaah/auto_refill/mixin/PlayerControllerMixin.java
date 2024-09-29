package pitaah.auto_refill.mixin;
import pitaah.auto_refill.AutoRefill;

import net.minecraft.client.player.controller.PlayerController;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerController.class, remap = false)
public abstract class PlayerControllerMixin {

	@Inject(method = "tick", at = @At("TAIL"))
	public void OnTick(CallbackInfo ci)
	{
		if(!AutoRefill.shouldRefill)
			return;

		AutoRefill.DoRefill();
	}
}

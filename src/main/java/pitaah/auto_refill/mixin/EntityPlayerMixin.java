package pitaah.auto_refill.mixin;

import net.minecraft.core.entity.player.Player;
import org.lwjgl.Sys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pitaah.auto_refill.AutoRefill;

@Mixin(value = Player.class, remap = false)
public abstract class EntityPlayerMixin {

	@Inject(method = "dropCurrentItem", at = @At("HEAD"))
	private void OndropCurrentItem(boolean dropFullStack, CallbackInfo ci)
	{
		Player player = (Player) ( Object) this;
		AutoRefill.CheckRefillFromDropping(player, dropFullStack);
	}
}

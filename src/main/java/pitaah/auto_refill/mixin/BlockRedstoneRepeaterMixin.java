package pitaah.auto_refill.mixin;
import pitaah.auto_refill.AutoRefill;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.block.BlockRedstoneRepeater;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

@Mixin(value = BlockRedstoneRepeater.class, remap = false)
public abstract class BlockRedstoneRepeaterMixin
{
	@Inject(method = "onBlockPlaced", at = @At("HEAD"))
	public void OnBlockRedstoneRepeaterPlaced(World world, int x, int y, int z, Side side, EntityLiving entity, double sideHeight, CallbackInfo ci)
	{
		AutoRefill.CheckRefill(entity, world, false);
	}
}



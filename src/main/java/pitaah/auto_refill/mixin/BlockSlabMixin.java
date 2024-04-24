package pitaah.auto_refill.mixin;
import pitaah.auto_refill.AutoRefill;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.block.BlockSlab;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

@Mixin(value = BlockSlab.class, remap = false)
public abstract class BlockSlabMixin
{
	@Inject(method = "onBlockPlaced", at = @At("HEAD"))
	public void OnBlockSlabPlaced(World world, int x, int y, int z, Side side, EntityLiving entity, double sideHeight, CallbackInfo ci)
	{
		AutoRefill.OnBlockPlaced(entity, world, true);
	}
}

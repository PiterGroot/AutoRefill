package pitaah.examplemod.mixin;
import pitaah.examplemod.ExampleMod;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.block.BlockAxisAligned;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

@Mixin(value = BlockAxisAligned.class, remap = false)
public abstract class BlockAxisAlignedMixin
{
	@Inject(method = "onBlockPlaced", at = @At("HEAD"))
	public void OnBlockAxisAlignedPlaced(World world, int x, int y, int z, Side side, EntityLiving entity, double sideHeight, CallbackInfo ci)
	{
		ExampleMod.OnBlockPlaced(entity, false);
	}
}

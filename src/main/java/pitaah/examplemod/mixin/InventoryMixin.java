package pitaah.examplemod.mixin;
import pitaah.examplemod.ExampleMod;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = Block.class, remap = false)
public abstract class InventoryMixin {

	public void onBlockPlaced(World world, int x, int y, int z, Side side, EntityLiving entity, double sideHeight) {
		ItemStack currentStack = entity.getHeldItem();

		int currentStackSize = currentStack.stackSize;
		ExampleMod.LOGGER.info(currentStackSize + " " + x + " " + y + " " + z);

		if(currentStackSize <= 0)
		{
			EntityPlayer e = (EntityPlayer) entity;
			for (int i = 0; i < e.inventory.mainInventory.length; i++) {
				if(e.inventory.mainInventory[i] == null)
					continue;

				if(e.inventory.mainInventory[i].itemID == currentStack.itemID) {
					if((e.inventory.currentItem) == i)
						continue;

					ExampleMod.LOGGER.info("Found another stack");
					break;
				}
			}
		}
	}
}

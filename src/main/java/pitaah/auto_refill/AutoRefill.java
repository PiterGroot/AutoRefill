package pitaah.auto_refill;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoRefill implements ModInitializer {
    public static final String MOD_ID = "auto_refill";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void print(String message){
		LOGGER.info(message);
	}

	public static void OnBlockPlaced(EntityLiving entityLiving, World world, boolean ignoreSizeCheck) {
		ItemStack currentStack = entityLiving.getHeldItem();
		int currentStackSize = currentStack.stackSize;

		if(ignoreSizeCheck)  currentStackSize--;
		if(currentStackSize < 0) currentStackSize = 0;

		if(currentStackSize > 0)
			return;

		EntityPlayer entityPlayer = (EntityPlayer)entityLiving;
		for (int i = 0; i < entityPlayer.inventory.mainInventory.length; i++) {
			if(entityPlayer.inventory.mainInventory[i] == null)
				continue;

			if(entityPlayer.inventory.mainInventory[i].itemID == currentStack.itemID) {
				int currentSelectedSlot = entityPlayer.inventory.currentItem;

				if(currentSelectedSlot == i)
					continue;

				ItemStack stackToGrab = ItemStack.copyItemStack(entityPlayer.inventory.mainInventory[i]);

				if(i < currentSelectedSlot)
				{
					for (int j = 0; j < currentSelectedSlot; j++)
					{
						if(entityPlayer.inventory.mainInventory[j] == null)
							continue;

						if(entityPlayer.inventory.mainInventory[j].itemID == currentStack.itemID)
						{
							print("Found a stack to consume at: " + j);
							stackToGrab.stackSize += entityPlayer.inventory.mainInventory[j].stackSize;
							entityPlayer.inventory.setInventorySlotContents(j, null);
						}
					}
					stackToGrab.stackSize--;
				}

				entityPlayer.inventory.setInventorySlotContents(i, null);
				entityPlayer.inventory.insertItem(stackToGrab, true);

				print(currentSelectedSlot + " " + (i));

				float pitch = (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1;
				world.playSoundAtEntity((Entity)null, entityLiving, "random.pop", .5f, pitch);
				break;
			}
		}
	}
    @Override
    public void onInitialize() {
        LOGGER.info("AutoRefill initialized.");
    }
}

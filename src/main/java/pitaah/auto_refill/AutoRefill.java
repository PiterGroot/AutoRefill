package pitaah.auto_refill;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoRefill implements ModInitializer {
    public static final String MOD_ID = "auto_refill";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static boolean shouldRefill;
	public static World lastWorld;
	public static EntityPlayer lastEntityPlayer;
	private static ItemStack lastStackToGrab;
	private static int lastSlotIDToConsume;
	private static int lastSlotIDToPlace;

	@Override
	public void onInitialize() {
		LOGGER.info("AutoRefill initialized.");
	}

	public static void CheckRefillFromDropping(boolean ignoreSizeCheck)
	{
		ItemStack currentStack = lastEntityPlayer.getHeldItem();

		System.out.println(currentStack);

		if(currentStack == null)
			return;

		if(!ignoreSizeCheck){
			int currentStackSize = currentStack.stackSize;
			if(currentStackSize > 1)
				return;
		}

		DoRefillCheck(currentStack, lastEntityPlayer, lastWorld, ignoreSizeCheck);
	}

	public static void CheckRefill(EntityLiving entityLiving, World world, boolean ignoreSizeCheck) {
		ItemStack currentStack = entityLiving.getHeldItem();

		if (currentStack == null)
			return;

		int currentStackSize = currentStack.stackSize;

		if (ignoreSizeCheck) {
			currentStackSize--;
			if (currentStackSize < 0) currentStackSize = 0;
		}

		if (currentStackSize > 0)
			return;

		DoRefillCheck(currentStack, entityLiving, world, ignoreSizeCheck);
	}

	private static void DoRefillCheck(ItemStack currentStack, EntityLiving entityLiving, World world, boolean ignoreSizeCheck) {
		EntityPlayer entityPlayer = (EntityPlayer)entityLiving;
		for (int i = 0; i < entityPlayer.inventory.mainInventory.length; i++) {
			if(entityPlayer.inventory.mainInventory[i] == null)
				continue;

			if(entityPlayer.inventory.mainInventory[i].itemID == currentStack.itemID) {
				int currentSelectedSlot = entityPlayer.inventory.currentItem;

				if(currentSelectedSlot == i)
					continue;

				ItemStack stackToGrab = ItemStack.copyItemStack(entityPlayer.inventory.mainInventory[i]);

				shouldRefill = true;
				lastWorld = world;

				lastStackToGrab = stackToGrab;

				lastSlotIDToConsume = i;
				lastSlotIDToPlace = currentSelectedSlot;
				System.out.println("Should do refill");
				break;
			}
		}
	}

	public static void DoRefill(){
		lastEntityPlayer.inventory.setInventorySlotContents(lastSlotIDToConsume, null);
		lastEntityPlayer.inventory.setInventorySlotContents(lastSlotIDToPlace, lastStackToGrab);

		float pitch = (lastWorld.rand.nextFloat() - lastWorld.rand.nextFloat()) * 0.2F + 1;
		lastWorld.playSoundAtEntity((Entity)null, lastEntityPlayer, "random.pop", .5f, pitch);

		AutoRefill.shouldRefill = false;
	}
}

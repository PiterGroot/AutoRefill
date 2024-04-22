package pitaah.examplemod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class ExampleMod implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "examplemod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void print(String message){
		LOGGER.info(message);
	}

	public static void OnBlockPlaced(EntityLiving entityLiving, boolean ignoreSizeCheck) {
		ItemStack currentStack = entityLiving.getHeldItem();
		int currentStackSize = currentStack.stackSize;

		if(!ignoreSizeCheck && currentStackSize > 0)
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

				entityPlayer.inventory.setInventorySlotContents(i, null);
				entityPlayer.inventory.insertItem(stackToGrab, true);
				break;
			}
		}
	}
    @Override
    public void onInitialize() {
        LOGGER.info("ExampleMod initialized.");
    }

	@Override
	public void beforeGameStart() {
		LOGGER.info("Loaded in the game!");
	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}
}

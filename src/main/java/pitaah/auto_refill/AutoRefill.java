package pitaah.auto_refill;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

import pitaah.auto_refill.item.AutoRefillIconItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;
import java.util.Properties;

public class AutoRefill implements ModInitializer, GameStartEntrypoint {
    public static final String MOD_ID = "auto_refill";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final int STARTING_ITEM_ID;

	static {
		Properties prop = new Properties();
		prop.setProperty("starting_item_id", "26680");
		ConfigHandler config = new ConfigHandler(MOD_ID, prop);
		STARTING_ITEM_ID = config.getInt("starting_item_id");
		config.updateConfig();
	}

	public static boolean shouldRefill;
	public static World lastWorld;
	public static EntityPlayer lastEntityPlayer;
	private static ItemStack lastStackToGrab;
	private static int lastSlotIDToConsume;
	private static int lastSlotIDToPlace;
	public static Item AutoRefillDebugIcon;
	@Override
	public void onInitialize() { LOGGER.info("AutoRefill initialized."); }

	@Override
	public void beforeGameStart() {
		AutoRefillDebugIcon = ItemHelper.createItem(MOD_ID, new AutoRefillIconItem("debug", STARTING_ITEM_ID));
	}

	@Override
	public void afterGameStart() {
		AutoRefillModSettingsRegister.onLoad();
	}

	public static void CheckRefillForDurability(EntityPlayer player, boolean ignoreSizeCheck)
	{
		if(!AutoRefillModSettingsRegister.modSettings.autoRefillDoRefillOnTools().value)
			return;

		ItemStack currentStack = player.getHeldItem();

		if(currentStack == null)
			return;

		if(currentStack.stackSize <= 0 && currentStack.getMetadata() <= 0)
			DoRefillCheck(currentStack, player, player.world, ignoreSizeCheck);
	}

	public static void CheckRefillFromDropping(EntityPlayer player, boolean ignoreSizeCheck)
	{
		if(!AutoRefillModSettingsRegister.modSettings.autoRefillDoRefillOnDrop().value)
			return;

		ItemStack currentStack = player.getHeldItem();

		if(currentStack == null)
			return;

		if(!ignoreSizeCheck){
			int currentStackSize = currentStack.stackSize;
			if(currentStackSize > 1)
				return;
		}

		DoRefillCheck(currentStack, player, player.world, ignoreSizeCheck);
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

			if(!AutoRefillModSettingsRegister.modSettings.autoRefillDoRefillOnFood().value && IsFoodItem(currentStack))
				continue;;

			if(entityPlayer.inventory.mainInventory[i].itemID == currentStack.itemID) {
				int currentSelectedSlot = entityPlayer.inventory.currentItem;

				if(currentSelectedSlot == i)
					continue;

				ItemStack stackToGrab = ItemStack.copyItemStack(entityPlayer.inventory.mainInventory[i]);

				shouldRefill = true;

				lastEntityPlayer = entityPlayer;
				lastWorld = world;

				lastStackToGrab = stackToGrab;

				lastSlotIDToConsume = i;
				lastSlotIDToPlace = currentSelectedSlot;
				break;
			}
		}
	}

	public static void DoRefill(){
		if(!AutoRefillModSettingsRegister.modSettings.autoRefillDoAnyRefill().value)
		{
			shouldRefill = false;
			return;
		}

		lastEntityPlayer.inventory.setInventorySlotContents(lastSlotIDToConsume, null);
		lastEntityPlayer.inventory.setInventorySlotContents(lastSlotIDToPlace, lastStackToGrab);

		if(AutoRefillModSettingsRegister.modSettings.autoRefillPlaySound().value)
		{
			float pitch = (lastWorld.rand.nextFloat() - lastWorld.rand.nextFloat()) * 0.2F + 1;
			lastWorld.playSoundAtEntity((Entity)null, lastEntityPlayer, "random.pop", .5f, pitch);
		}

		AutoRefill.shouldRefill = false;
	}

	private static boolean IsFoodItem(ItemStack itemStack)
	{
		Item item = itemStack.getItem();

		return  (item.id == Item.foodApple.id || item.id == Item.foodAppleGold.id || item.id == Item.foodBread.id || item.id == Item.foodCake.id
		|| item.id == Item.foodCookie.id || item.id == Item.foodCherry.id || item.id == Item.foodPorkchopRaw.id || item.id == Item.foodFishCooked.id
		|| item.id == Item.foodFishRaw.id || item.id == Item.foodPorkchopCooked.id || item.id == Item.foodPumpkinPie.id || item.id == Item.foodStewMushroom.id);
	}
}

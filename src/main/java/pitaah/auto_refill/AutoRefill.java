package pitaah.auto_refill;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;

import pitaah.auto_refill.item.AutoRefillIconItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.ModelEntrypoint;

import java.util.Properties;
import java.util.function.Supplier;

public class AutoRefill implements ModInitializer, GameStartEntrypoint, ModelEntrypoint {
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
	public static Player lastEntityPlayer;
	private static ItemStack lastStackToGrab;
	private static int lastSlotIDToConsume;
	private static int lastSlotIDToPlace;
	public static Item AutoRefillDebugIcon;
	@Override
	public void onInitialize() { LOGGER.info("AutoRefill initialized."); }

	@Override
	public void beforeGameStart()
	{
		AutoRefillDebugIcon = new ItemBuilder(MOD_ID)
			.build(new Item("debug", MOD_ID + ":" + "item/debug", STARTING_ITEM_ID));

		LOGGER.info(AutoRefillDebugIcon.namespaceID.toString());
	}

	@Override
	public void initItemModels(ItemModelDispatcher dispatcher)
	{
		LOGGER.info("initItemModels!");

		ModelHelper.setItemModel(AutoRefillDebugIcon, () -> {
			ItemModelStandard model = new ItemModelStandard(AutoRefillDebugIcon, MOD_ID);
			model.icon = TextureRegistry.getTexture(AutoRefillDebugIcon.namespaceID);
			return  model;
		});
	}

	@Override
	public void afterGameStart() {
		AutoRefillModSettingsRegister.onLoad();
	}

	public static void CheckRefillForDurability(Player player, boolean ignoreSizeCheck)
	{
		if(!AutoRefillModSettingsRegister.modSettings.autoRefillDoRefillOnTools().value)
			return;

		ItemStack currentStack = player.getHeldItem();

		if(currentStack == null)
			return;

		if(currentStack.stackSize <= 0 && currentStack.getMetadata() <= 0)
			DoRefillCheck(currentStack, player, player.world, ignoreSizeCheck);
	}

	public static void CheckRefillFromDropping(Player player, boolean ignoreSizeCheck)
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

	public static void CheckRefill(Player entityLiving, World world, boolean ignoreSizeCheck) {
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

	private static void DoRefillCheck(ItemStack currentStack, Player entityLiving, World world, boolean ignoreSizeCheck) {
		Player entityPlayer = (Player)entityLiving;
		for (int i = 0; i < entityPlayer.inventory.mainInventory.length; i++) {
			if(entityPlayer.inventory.mainInventory[i] == null)
				continue;

			if(!AutoRefillModSettingsRegister.modSettings.autoRefillDoRefillOnFood().value && IsFoodItem(currentStack))
				continue;;

			if(entityPlayer.inventory.mainInventory[i].itemID == currentStack.itemID) {
				int currentSelectedSlot = entityPlayer.inventory.getCurrentItemIndex();

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

		lastEntityPlayer.inventory.setItem(lastSlotIDToConsume, null);
		lastEntityPlayer.inventory.setItem(lastSlotIDToPlace, lastStackToGrab);

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

		return  (item.id == Items.FOOD_APPLE.id || item.id == Items.FOOD_APPLE_GOLD.id || item.id == Items.FOOD_BREAD.id || item.id == Items.FOOD_CAKE.id
		|| item.id == Items.FOOD_COOKIE.id || item.id == Items.FOOD_CHERRY.id || item.id == Items.FOOD_PORKCHOP_RAW.id || item.id == Items.FOOD_FISH_COOKED.id
		|| item.id == Items.FOOD_FISH_RAW.id || item.id == Items.FOOD_PORKCHOP_COOKED.id || item.id == Items.FOOD_PUMPKIN_PIE.id || item.id == Items.FOOD_STEW_MUSHROOM.id);
	}

	@Override
	public void initBlockModels(BlockModelDispatcher dispatcher) {}

	@Override
	public void initEntityModels(EntityRenderDispatcher dispatcher) {}

	@Override
	public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {}

	@Override
	public void initBlockColors(BlockColorDispatcher dispatcher) {}
}

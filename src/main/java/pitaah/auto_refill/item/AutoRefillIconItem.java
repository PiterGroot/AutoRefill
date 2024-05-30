package pitaah.auto_refill.item;

import net.minecraft.core.item.Item;

public class AutoRefillIconItem extends Item {
	public AutoRefillIconItem(String key, int id) {
		super(key, id);
		notInCreativeMenu = true;
	}
}

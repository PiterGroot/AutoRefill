package pitaah.auto_refill;

import net.minecraft.client.option.BooleanOption;

public interface IAutoRefillModOptions {
	BooleanOption autoRefillPlaySound();

	BooleanOption autoRefillDoRefillOnDrop();

	BooleanOption autoRefillDoRefillOnTools();
	BooleanOption autoRefillDoRefillOnFood();

	BooleanOption autoRefillDoAnyRefill();

	BooleanOption autoRefillDoAnyRefillOnItems();
}

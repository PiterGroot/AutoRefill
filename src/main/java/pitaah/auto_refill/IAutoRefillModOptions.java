package pitaah.auto_refill;

import net.minecraft.client.option.OptionBoolean;

public interface IAutoRefillModOptions {
	OptionBoolean autoRefillPlaySound();

	OptionBoolean autoRefillDoRefillOnDrop();

	OptionBoolean autoRefillDoRefillOnTools();
	OptionBoolean autoRefillDoRefillOnFood();

	OptionBoolean autoRefillDoAnyRefill();

	OptionBoolean autoRefillDoAnyRefillOnItems();
}

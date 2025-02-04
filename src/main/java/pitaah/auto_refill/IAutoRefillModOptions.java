package pitaah.auto_refill;

import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionFloat;
import net.minecraft.client.option.OptionVolume;

public interface IAutoRefillModOptions {
	OptionBoolean autoRefillPlaySound();

	OptionBoolean autoRefillDoRefillOnDrop();

	OptionBoolean autoRefillDoRefillOnTools();
	OptionBoolean autoRefillDoRefillOnFood();

	OptionBoolean autoRefillDoAnyRefill();

	OptionBoolean autoRefillDoAnyRefillOnItems();

	OptionFloat autoRefillSoundVolume();
}

package pitaah.auto_refill.mixin;

import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import pitaah.auto_refill.IAutoRefillModOptions;

@Mixin(value = GameSettings.class, remap = false)
public class GameSettingsMixin implements IAutoRefillModOptions {
	@Unique
	private final GameSettings thisAs = (GameSettings)(Object)this;

	@Unique
	public final OptionBoolean playRefillSound = new OptionBoolean(thisAs, "playRefillSound", true);

	@Unique
	public final OptionBoolean useRefillForDropping = new OptionBoolean(thisAs, "useRefillForDropping", true);

	@Unique
	public final OptionBoolean useRefillForTools = new OptionBoolean(thisAs, "useRefillForTools", true);

	@Unique
	public final OptionBoolean useRefillForFood = new OptionBoolean(thisAs, "useRefillForFood", true);

	@Unique
	public final OptionBoolean useAnyRefillOnItems = new OptionBoolean(thisAs, "useRefillOnItems", true);

	@Unique
	public final OptionBoolean useAnyRefill = new OptionBoolean(thisAs, "useAnyRefill", true);

	@Override
	public OptionBoolean autoRefillPlaySound() { return playRefillSound; }

	@Override
	public OptionBoolean autoRefillDoRefillOnDrop() {
		return useRefillForDropping;
	}

	@Override
	public OptionBoolean autoRefillDoRefillOnTools() { return useRefillForTools; }

	@Override
	public OptionBoolean autoRefillDoRefillOnFood() { return useRefillForFood; }

	@Override
	public OptionBoolean autoRefillDoAnyRefill() { return useAnyRefill; }

	@Override
	public OptionBoolean autoRefillDoAnyRefillOnItems() { return useAnyRefillOnItems; }
}

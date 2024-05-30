package pitaah.auto_refill.mixin;

import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import pitaah.auto_refill.IAutoRefillModOptions;

@Mixin(value = GameSettings.class, remap = false)
public class GameSettingsMixin implements IAutoRefillModOptions {
	@Unique
	private final GameSettings thisAs = (GameSettings)(Object)this;

	@Unique
	public final BooleanOption playRefillSound = new BooleanOption(thisAs, "playRefillSound", true);

	@Unique
	public final BooleanOption useRefillForDropping = new BooleanOption(thisAs, "useRefillForDropping", true);

	@Unique
	public final BooleanOption useRefillForTools = new BooleanOption(thisAs, "useRefillForTools", true);

	@Unique
	public final BooleanOption useAnyRefillOnItems = new BooleanOption(thisAs, "useRefillOnItems", true);

	@Unique
	public final BooleanOption useAnyRefill = new BooleanOption(thisAs, "useAnyRefill", true);

	@Override
	public BooleanOption autoRefillPlaySound() { return playRefillSound; }

	@Override
	public BooleanOption autoRefillDoRefillOnDrop() {
		return useRefillForDropping;
	}

	@Override
	public BooleanOption autoRefillDoRefillOnTools() { return useRefillForTools; }

	@Override
	public BooleanOption autoRefillDoAnyRefill() { return useAnyRefill; }

	@Override
	public BooleanOption autoRefillDoAnyRefillOnItems() { return useAnyRefillOnItems; }
}

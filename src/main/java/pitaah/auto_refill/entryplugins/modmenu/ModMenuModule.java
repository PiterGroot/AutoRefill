package pitaah.auto_refill.entryplugins.modmenu;

import io.github.prospector.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.Screen;
import pitaah.auto_refill.AutoRefill;
import pitaah.auto_refill.AutoRefillModSettingsRegister;

import java.util.function.Function;

public class ModMenuModule implements ModMenuApi {

	@Override
	@SuppressWarnings("UnstableApiUsage")
	public String getModId() {
		return AutoRefill.MOD_ID;
	}

	@Override
	public Function<Screen, ? extends Screen> getConfigScreenFactory() {
		return (AutoRefillModSettingsRegister::getOptionsPage);
	}
}

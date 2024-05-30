package pitaah.auto_refill.entryplugins.modmenu;

import io.github.prospector.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.GuiScreen;
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
	public Function<GuiScreen, ? extends GuiScreen> getConfigScreenFactory() {
		return (AutoRefillModSettingsRegister::getOptionsPage);
	}
}

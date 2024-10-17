package pitaah.auto_refill;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.options.GuiOptions;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.GameSettings;
import net.minecraft.core.item.ItemStack;

public class AutoRefillModSettingsRegister {
	public static GameSettings gameSettings;
	public static IAutoRefillModOptions modSettings;
	public static OptionsPage refillOptions;

	public static void onLoad(){
		gameSettings = Minecraft.getMinecraft(Minecraft.class).gameSettings;
		modSettings = (IAutoRefillModOptions) gameSettings;
		refillOptions = new OptionsPage("AutoRefill", new ItemStack(AutoRefill.AutoRefillDebugIcon))
			.withComponent(new BooleanOptionComponent(modSettings.autoRefillDoAnyRefill()))
			.withComponent(new BooleanOptionComponent(modSettings.autoRefillPlaySound()))
			.withComponent(new BooleanOptionComponent(modSettings.autoRefillDoRefillOnDrop()))
			.withComponent(new BooleanOptionComponent(modSettings.autoRefillDoRefillOnTools()))
			.withComponent(new BooleanOptionComponent(modSettings.autoRefillDoRefillOnFood()))
		    .withComponent(new BooleanOptionComponent(modSettings.autoRefillDoAnyRefillOnItems()));

		OptionsPages.register(refillOptions);
	}

	public static GuiOptions getOptionsPage(GuiScreen parent){
		return new GuiOptions(parent, refillOptions);
	}
}

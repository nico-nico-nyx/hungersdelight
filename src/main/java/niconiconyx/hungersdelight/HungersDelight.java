package niconiconyx.hungersdelight;

import net.fabricmc.api.ModInitializer;

import niconiconyx.hungersdelight.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HungersDelight implements ModInitializer {
	public static final String MOD_ID = "hungersdelight";
	public static final String MOD_NAME = "Hunger's Delight";
	//Code by: Nico_Nico_Nyx

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info(MOD_ID + "Initializing...");
		Config.Init(MOD_ID,MOD_NAME, Config.class);
	}
}
package dev.vesper.polyphonic;

import dev.vesper.polyphonic.platform.Platform;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? fabric {
import dev.vesper.polyphonic.platform.fabric.FabricPlatform;
//?} neoforge {
/*import dev.vesper.soundtags.platform.neoforge.NeoforgePlatform;
 *///?} forge {
/*import dev.vesper.soundtags.platform.forge.ForgePlatform;
 *///?}

@SuppressWarnings("LoggingSimilarMessage")
public class Polyphonic {

	public static final String MOD_ID = /*$ mod_id*/ "polyphonic";
	public static final String MOD_VERSION = /*$ mod_version*/ "1.0.0";
	public static final String MOD_FRIENDLY_NAME = /*$ mod_name*/ "Polyphonic";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final Platform PLATFORM = createPlatformInstance();

	public static void onInitialize() {
		LOGGER.info("Initializing {} on {}", MOD_ID, Polyphonic.xplat().loader());
		LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);
	}

	public static void onInitializeClient() {
		LOGGER.info("Initializing {} Client on {}", MOD_ID, Polyphonic.xplat().loader());
		LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);
	}

	static Platform xplat() {
		return PLATFORM;
	}

	private static Platform createPlatformInstance() {
		//? fabric {
		return new FabricPlatform();
		//?} neoforge {
		/*return new NeoforgePlatform();
		 *///?} forge {
		/*return new ForgePlatform();
		 *///?}
	}

	private static Identifier id(String path) {
		//? > 1.19.2 {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
		 //?} <= 1.19.2 {
		/*return new Identifier(MOD_ID, path);
		*///?}
	}

	private static Identifier id(String namespace, String path) {
		//? > 1.19.2 {
		return Identifier.fromNamespaceAndPath(namespace, path);
		 //?} <= 1.19.2 {
		/*return new Identifier(namespace, path);
		*///?}
	}
}

package dev.vesper.polyphonic.common.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Predicate;

public class TagChecker {
	/**
	 * Our helper method for checking if a feature is being used by a Resource Pack. MUST be included in all tag additions
	 * @param pathFilter
	 * @param key The sound event being looked for, use the ID that was set in PolyphonicSoundEvents
	 * @return boolean
	 */
	public static boolean packHasFeature(Predicate<String> pathFilter, String key){
		ResourceManager rm = Minecraft.getInstance().getResourceManager();
		Map<Identifier, Resource> matches = rm.listResources("", loc -> pathFilter.test(loc.getPath()));

		for (Map.Entry<Identifier, Resource> entry : matches.entrySet()){
			if (jsonContainsKey(entry.getValue(), key)){
				return true;
			}
		}
		return false;
	}

	private static boolean jsonContainsKey(Resource resource, String key){
		try (InputStreamReader reader = new InputStreamReader(resource.open(), StandardCharsets.UTF_8)) {
			JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
			return json.has(key);
		} catch (IOException e) {
			return false;
		}
	}
}

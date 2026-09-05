package dev.vesper.polyphonic.common.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Predicate;

public class TagChecker {

	public static boolean anyJsonHasKey(ResourceManager resourceManager, Predicate<String> pathFilter, String key){
		Map<Identifier, Resource> matches = resourceManager.listResources("", loc -> pathFilter.test(loc.getPath()));

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

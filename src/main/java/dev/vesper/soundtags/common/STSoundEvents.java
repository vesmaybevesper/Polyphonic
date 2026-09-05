package dev.vesper.soundtags.common;

import net.minecraft.core.Registry;
//? >=1.20{
import net.minecraft.core.registries.BuiltInRegistries;
//?}
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class STSoundEvents {
	public static final SoundEvent LIGHTNING_STRIKE_FAR = register("weather.lightning.far");
	public static final SoundEvent LIGHTNING_STRIKE_MED = register("weather.lightning.med");

	//? >=1.21{
	private static SoundEvent register(final String id) {
		return register(Identifier.withDefaultNamespace(id));
	}
	//?} 1.20{
	/*private static SoundEvent register(final String id) {
		return register(Identifier.tryParse(id));
	}
	*///?}


	//? >=1.20{
	private static SoundEvent register(final Identifier id) {
		return register(id, id);
	}

	private static SoundEvent register(final Identifier id, final Identifier soundId) {
		return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(soundId));
	}
	//?} <1.20{
	/*private static SoundEvent register(String string) {
		return Registry.register(Registry.SOUND_EVENT, string, new SoundEvent(new Identifier(string)));
	}
	*///?}
}

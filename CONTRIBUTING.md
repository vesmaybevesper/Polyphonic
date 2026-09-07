# Contributing

## Contributor License Agreement
Before your contribution can be merged, you must agree to the [Harmony Individual Contributor License Agreement](CONTRIBUTING.md).

In short, it means:

* You keep ownership of your code. 
* You grant the project a permanent, irrevocable license to use, modify, and relicense your contribution under any terms, including commercial ones. 
* You confirm the code is yours to contribute, Or.
* If You do not own the Copyright in the entire work of authorship, you must include the other Copyright Owner(s), License(s), and Source(s) so that we may verify its ability to be included.

To accept the CLA, post the following comment verbatim in your pull request:

>I have read the Contributor License Agreement in CLA.md and I agree to its terms.

Your pull request will not be merged until this comment is present. This comment, tied to your GitHub identity, serves as your signature.

## Contributing Guidelines

If you want to contribute but don't have any features of your own in mind check out the [Issues](https://github.com/vesmaybevesper/Polyphonic/issues) tab or our [TODO](TODO.md) document.

Read this document **IN ITS ENTIRETY** before submitting a Pull Request.

### Adding Tags:

Tag additions should be implemented completely, meaning that if it is a variation tag, it must be added to all places the tag it's a variant of is (if applicable), or if it's a new tag, it must be added to all applicable locations. Any omissions must be explained in your Pull Request.

If other tags play at the same time to make up the whole sound, you should make the necessary adjustments to have a cohesive sound experience.

Condition checks should be spun out to their own methods unless the checks are very compact to maximize readability. In that same vein, please give a comment explaining choices so that others who may have to modify or update your code can easily understand it.

All tag-playing code must use our method `TagChecker.packHasFeature()` to check if that tag is present, and if not, return any original sound behavior that may be in the base game; for this reason, it is recommended to use MixinExtras to add your tag.

Example:

For our weather.lightning.med tag we must add our tag with appropriate check to `LightningBolt` via `LightningBoltMixin`

_Vanilla_
```` java
// Code continues from above
public void tick() {
        super.tick();
        if (this.life == 2) {
            if (this.level().isClientSide()) {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 10000.0F, 0.8F + this.random.nextFloat() * 0.2F, false);
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.WEATHER, 2.0F, 0.5F + this.random.nextFloat() * 0.2F, false);
            }
// Code continues below
````

_Mixin_

```` java
// Code continues from above
@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V", ordinal = 0))
	private void polyphonic$playThunder(Level instance, double d, double e, double f, SoundEvent soundEvent, SoundSource soundSource, float g, float h, boolean bl, Operation<Void> original) {
			if (this.polyphonic$isDistant() && TagChecker.packHasFeature("weather.lightning.far")){
				this.level().playLocalSound(d, e, f, PolyphonicSoundEvents.LIGHTNING_STRIKE_FAR, SoundSource.WEATHER, g, h, bl);
			} else if (this.polyphonic$isMedium() && TagChecker.packHasFeature("weather.lightning.med")){
				this.level().playLocalSound(d, e, f, PolyphonicSoundEvents.LIGHTNING_STRIKE_MED, SoundSource.WEATHER, g, h, bl);
			} else {
				original.call(instance, d, e, f, soundEvent, soundSource, g, h, bl);
			}
	}

	@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V", ordinal = 1))
	private void polyphonic$playThunderImpact(Level instance, double d, double e, double f, SoundEvent soundEvent, SoundSource soundSource, float g, float h, boolean bl, Operation<Void> original){
		if (this.polyphonic$isDistant() && TagChecker.packHasFeature("weather.lightning.far")){
			// Shouldn't play a lightning impact if the strike is distant
		} else if (this.polyphonic$isMedium() && TagChecker.packHasFeature("weather.lightning.med")){
			// Play impact sound at 1/2 vanilla volume if at medium distance
			this.level().playLocalSound(d, e, f, SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.WEATHER, 1.0f, h, bl);
		} else {
			original.call(instance, d, e, f, soundEvent, soundSource, g, h, bl);
		}
	}


	@Unique
	private boolean polyphonic$isDistant() {
		Vec2 strikePos = new Vec2(((float) this.getX()), ((float) this.getZ()));
		int simDistanceBlocks = Minecraft.getInstance().options.simulationDistance().get() * 16;
		assert Minecraft.getInstance().player != null;
		Vec2 playerPos = new Vec2(((float) Minecraft.getInstance().player.getX()), ((float) Minecraft.getInstance().player.getZ()));
		float distToStrikeX = Mth.abs(playerPos.x - strikePos.x);
		// this is .y simply because it's a Vec2, we pipe the Z pos into that spot
		float distToStrikeY = Mth.abs(playerPos.y - strikePos.y);
		return distToStrikeX >= simDistanceBlocks * .75 || distToStrikeY >= simDistanceBlocks * .75;
	}

	@Unique
	private boolean polyphonic$isMedium() {
		Vec2 strikePos = new Vec2(((float) this.getX()), ((float) this.getZ()));
		int simDistanceBlocks = Minecraft.getInstance().options.simulationDistance().get() * 16;
		assert Minecraft.getInstance().player != null;
		Vec2 playerPos = new Vec2(((float) Minecraft.getInstance().player.getX()), ((float) Minecraft.getInstance().player.getZ()));
		float distToStrikeX = Mth.abs(playerPos.x - strikePos.x);
		// this is .y simply because it's a Vec2, we pipe the Z pos into that spot
		float distToStrikeY = Mth.abs(playerPos.y - strikePos.y);
		return distToStrikeX >= simDistanceBlocks * .45 || distToStrikeY >= simDistanceBlocks * .45;
	}
// Code continues below
````

### Versioning:

Pull Requests must be completely versioned for all currently supported versions. Those being:

| Loader | Versions                           |
|--------|------------------------------------|
| Forge  | 1.20.x    |
| Fabric | 1.20.x, 1.21.x, 26.x |
| NeoForge | 1.21.x, 26.x                     |

While the best effort will be made to keep the above list accurate, please double-check `settings.gradle.kts` & `stonecutter.properties.toml` to find a complete list of versions.

If your change requires a changing of the way versions are split in order to version correctly, you are responsible for adding that version and updating anything else that may need to be updated as a result. I recommend using [mcsrc.dev](https://mcsrc.dev/) to compare code across versions if you are unsure.

Pull requests porting the mod to versions prior to 1.20 will not be accepted, neither will ports to versions that have been explicitly dropped.

### Mod Compatibility:

Code that can be changed within this mod with checks for loaded mods will be accepted, but compatibility changes to use tags from this mod should be made in the target mod and will not be accepted. I would like to avoid adding more mixins than needed and really don't want to have to deal with MixinSquared.

### Credits:

Make sure you add yourself to the credits file when you make a commit! Add whatever name you want to be associated with and (optionally) what you added! Just don't remove anyone else's name or contributions!

### AI Usage

This project will not accept fully AI generated code. If your Pull Request contains AI generated code you _MUST_ outline what the AI contributed and what you contributed. Failure to disclose AI usage may result in a blacklist on your Pull Requests.

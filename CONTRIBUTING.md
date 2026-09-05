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

### Adding Tags:

Tag additions should be implemented completely, meaning that if it is a variation tag, it must be added to all places the tag it's a variant of is, or if it's a new tag, it must be added to all applicable locations. Any omissions must be explained in your Pull Request.

Example:

For a weather.rain.below tag we must add our tag with appropriate check to both `LevelRender` via `LevelRendererMixin`

_Vanilla_
```` java
// Code continues from above
if (blockPos2 != null && random.nextInt(3) < this.rainSoundTime++) {
				this.rainSoundTime = 0;
				if (blockPos2.getY() > blockPos.getY() + 1 && levelReader.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPos).getY() > Mth.floor((float) blockPos.getY())) {
					this.minecraft.level.playLocalSound(blockPos2, SoundEvents.WEATHER_RAIN_ABOVE, SoundSource.WEATHER, 0.1F, 0.5F, false);
				} else {
					this.minecraft.level.playLocalSound(blockPos2, SoundEvents.WEATHER_RAIN, SoundSource.WEATHER, 0.2F, 1.0F, false);
				}
			}
// Code continues below
````

_Mixin_

```` java
// Code continues from above
if (blockPos2 != null && random.nextInt(3) < this.rainSoundTime++) {
				this.rainSoundTime = 0;
				if (blockPos2.getY() > blockPos.getY() + 1 && levelReader.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPos).getY() > Mth.floor((float) blockPos.getY())) {
					this.minecraft.level.playLocalSound(blockPos2, SoundEvents.WEATHER_RAIN_ABOVE, SoundSource.WEATHER, 0.1F, 0.5F, false);
				} else if (//Condition goes here) {
				this.minecraft.level.playLocalSound(blockPos2, SoundEvents.WEATHER_RAIN_BELOW, SoundSource.WEATHER, 0.1F, 0.5F, false);
				} else {
					this.minecraft.level.playLocalSound(blockPos2, SoundEvents.WEATHER_RAIN, SoundSource.WEATHER, 0.2F, 1.0F, false);
				}
			}
// Code continues below
````

_and_ to `WeatherEffectRenderer` via `LevelEffectRendererMixin` (>= 1.21.1)

_Vanilla_

```` java
// Code continues from above
if (rainParticlePosition != null && random.nextInt(3) < this.rainSoundTime++) {
                this.rainSoundTime = 0;
                if (rainParticlePosition.getY() > cameraPosition.getY() + 1
                    && level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, cameraPosition).getY() > Mth.floor((float)cameraPosition.getY())) {
                    level.playLocalSound(rainParticlePosition, SoundEvents.WEATHER_RAIN_ABOVE, SoundSource.WEATHER, 0.1F, 0.5F, false);
                } else {
                    level.playLocalSound(rainParticlePosition, SoundEvents.WEATHER_RAIN, SoundSource.WEATHER, 0.2F, 1.0F, false);
                }
            }
// Code continues below
````

_Mixin_

```` java
// Code continues from above
if (rainParticlePosition != null && random.nextInt(3) < this.rainSoundTime++) {
                this.rainSoundTime = 0;
                if (rainParticlePosition.getY() > cameraPosition.getY() + 1
                    && level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, cameraPosition).getY() > Mth.floor((float)cameraPosition.getY())) {
                    level.playLocalSound(rainParticlePosition, SoundEvents.WEATHER_RAIN_ABOVE, SoundSource.WEATHER, 0.1F, 0.5F, false);
                } else if (//Condition goes here) {
                    level.playLocalSound(rainParticlePosition, SoundEvents.WEATHER_RAIN_BELOW, SoundSource.WEATHER, 0.1F, 0.5F, false);
                } else {
                    level.playLocalSound(rainParticlePosition, SoundEvents.WEATHER_RAIN, SoundSource.WEATHER, 0.2F, 1.0F, false);
                }
            }
// Code continues below
````

### Versioning:

Pull Requests must be completely versioned for all currently supported versions. Those being:

| Loader | Versions                           |
|--------|------------------------------------|
| Forge  | 1.18.x, 1.19.x, 1.20.x    |
| Fabric | 1.18.x, 1.19.x, 1.20.x, 1.21.x, 26.x |
| NeoForge | 1.21.x, 26.x                     |

While the best effort will be made to keep the above list accurate, please double check `settings.gradle.kts` & `stonecutter.properties.toml` to find a complete list of versions.

If your change requires a changing of the way versions are split in order to version correctly, you are responsible for adding that version and updating anything else that may need to be updated as a result. I recommend using [mcsrc.dev](https://mcsrc.dev/) to compare code across versions if you are unsure.

Pull requests porting the mod to versions prior to 1.18 will not be accepted, neither will ports to versions that have been explicitly dropped.

### Mod Compatibility:

Code that can be changed within this mod with checks for loaded mods will be accepted, but compatibility changes to use tags from this mod should be made in the target mod and will not be accepted. I would like to avoid adding more mixins than needed and really don't want to have to deal with MixinSquared.

### Credits:

Make sure you add yourself to the credits file when you make a commit! Add whatever name you want to be associated with and (optionally) what you added! Just don't remove anyone else's name or contributions!

### AI Usage

This project will not accept fully AI generated code. If your Pull Request contains AI generated code you _MUST_ outline what the AI contributed and what you contributed. Failure to disclose AI usage may result in a blacklist on your Pull Requests.

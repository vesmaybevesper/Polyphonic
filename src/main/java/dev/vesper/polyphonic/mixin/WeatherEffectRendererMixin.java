package dev.vesper.polyphonic.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.vesper.polyphonic.common.PolyphonicSoundEvents;
import dev.vesper.polyphonic.common.utils.MixinDummy;
import dev.vesper.polyphonic.common.utils.RangeCheckers;
import dev.vesper.polyphonic.common.utils.TagChecker;
import net.minecraft.client.multiplayer.ClientLevel;
//? if >=1.21.4
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//~ if <1.21.4 'WeatherEffectRenderer' -> 'MixinDummy'
@Mixin(WeatherEffectRenderer.class)
public class WeatherEffectRendererMixin {

	//? if >=1.21.4 {
	// This method overwrites the above rain
	@WrapOperation(method = "tickRainParticles(Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/client/Camera;ILnet/minecraft/server/level/ParticleStatus;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;playLocalSound(Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V", ordinal = 0))
	private void polyphonic$tickRainParticles$invoke$above(ClientLevel instance, BlockPos blockPos, SoundEvent soundEvent, SoundSource soundSource, float volume, float pitch, boolean b, Operation<Void> original){
		if (TagChecker.packHasFeature("weather.rain.hit_water") && RangeCheckers.withinRange(blockPos, 8) && instance.getBlockState(blockPos).is(Blocks.WATER)){
			instance.playLocalSound(blockPos, PolyphonicSoundEvents.RAIN_ON_WATER, SoundSource.WEATHER, 0.1f, 0.5f, false);
		} else {
			original.call(instance, blockPos, soundEvent, soundSource, volume, pitch, b);
		}
	}

	// This method overwrites the default rain
	@WrapOperation(method = "tickRainParticles(Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/client/Camera;ILnet/minecraft/server/level/ParticleStatus;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;playLocalSound(Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V", ordinal = 1))
	private void polyphonic$tickRainParticles$invoke$default(ClientLevel instance, BlockPos blockPos, SoundEvent soundEvent, SoundSource soundSource, float volume, float pitch, boolean b, Operation<Void> original){
		if (TagChecker.packHasFeature("weather.rain.hit_water") && RangeCheckers.withinRange(blockPos, 8) && instance.getBlockState(blockPos).is(Blocks.WATER)){
			instance.playLocalSound(blockPos, PolyphonicSoundEvents.RAIN_ON_WATER, SoundSource.WEATHER, 0.2f, 1f, false);
		} else {
			original.call(instance, blockPos, soundEvent, soundSource, volume, pitch, b);
		}
	}
	//?}
}

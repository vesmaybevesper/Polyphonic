package dev.vesper.polyphonic.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.vesper.polyphonic.common.PolyphonicSoundEvents;
import dev.vesper.polyphonic.common.utils.RangeCheckers;
import dev.vesper.polyphonic.common.utils.TagChecker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

	//? if <=1.21.1 {
	/*@WrapOperation(method = "tickRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;playLocalSound(Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V", ordinal = 0))
	private void polyphonic$tickRain$invoke$above(ClientLevel instance, BlockPos blockPos, SoundEvent soundEvent, SoundSource soundSource, float v, float v1, boolean b, Operation<Void> original){
		if (TagChecker.packHasFeature("weather.rain.hit_water") && RangeCheckers.withinRange(blockPos, 8) && instance.getBlockState(blockPos).is(Blocks.WATER)){
			instance.playLocalSound(blockPos, PolyphonicSoundEvents.RAIN_ON_WATER, SoundSource.WEATHER, 0.1f, 0.5f, false);
		} else {
			original.call(instance, blockPos, soundEvent, soundSource, v, v1, b);
		}
	}

	@WrapOperation(method = "tickRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;playLocalSound(Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V", ordinal = 1))
	private void polyphonic$tickRain$invoke$default(ClientLevel instance, BlockPos blockPos, SoundEvent soundEvent, SoundSource soundSource, float v, float v1, boolean b, Operation<Void> original){
		if (TagChecker.packHasFeature("weather.rain.hit_water") && RangeCheckers.withinRange(blockPos, 8) && instance.getBlockState(blockPos).is(Blocks.WATER)){
			instance.playLocalSound(blockPos, PolyphonicSoundEvents.RAIN_ON_WATER, SoundSource.WEATHER, 0.2f, 1f, false);
		} else {
			original.call(instance, blockPos, soundEvent, soundSource, v, v1, b);
		}
	}
	*///?}
}

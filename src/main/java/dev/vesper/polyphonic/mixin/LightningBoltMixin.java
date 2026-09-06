package dev.vesper.polyphonic.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import dev.vesper.polyphonic.common.PolyphonicSoundEvents;
import dev.vesper.polyphonic.common.utils.TagChecker;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

/* *
 * @author Vesper (VesMaybeVesper)
 */
@Mixin(LightningBolt.class)
@MixinEnvironment(type = MixinEnvironment.Env.MAIN)
public abstract class LightningBoltMixin extends Entity {

	public LightningBoltMixin(EntityType<?> p_19870_, Level p_19871_) {
		super(p_19870_, p_19871_);
	}

	@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V", ordinal = 0))
	private void polyphonic$playThunder(Level instance, double d, double e, double f, SoundEvent soundEvent, SoundSource soundSource, float g, float h, boolean bl, Operation<Void> original) {
			if (this.polyphonic$isDistant() && TagChecker.packHasFeature(path -> path.equals("sounds.json"), "weather.lightning.far")){
				this.level().playLocalSound(d, e, f, PolyphonicSoundEvents.LIGHTNING_STRIKE_FAR, SoundSource.WEATHER, g, h, bl);
			} else if (this.polyphonic$isMedium() && TagChecker.packHasFeature(path -> path.equals("sounds.json"), "weather.lightning.med")){
				this.level().playLocalSound(d, e, f, PolyphonicSoundEvents.LIGHTNING_STRIKE_MED, SoundSource.WEATHER, g, h, bl);
			} else {
				original.call(instance, d, e, f, soundEvent, soundSource, g, h, bl);
			}
	}

	@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V", ordinal = 1))
	private void polyphonic$playThunderImpact(Level instance, double d, double e, double f, SoundEvent soundEvent, SoundSource soundSource, float g, float h, boolean bl, Operation<Void> original){
		if (this.polyphonic$isDistant() && TagChecker.packHasFeature(path -> path.equals("sounds.json"), "weather.lightning.far")){
			// Shouldn't play a lightning impact if the strike is distant
		} else if (this.polyphonic$isMedium() && TagChecker.packHasFeature(path -> path.equals("sounds.json"), "weather.lightning.med")){
			// Play impact sound at 1/2 vanilla volume if at medium distance
			this.level().playLocalSound(d, e, f, SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.WEATHER, 1.0f, h, bl);
		} else {
			original.call(instance, d, e, f, soundEvent, soundSource, g, h, bl);
		}
	}


	@Unique
	private boolean polyphonic$isDistant() {
		Vec2 strikePos = new Vec2(((float) this.getX()), ((float) this.getY()));
		//? >=1.19{
		int renderDistBlocks = Minecraft.getInstance().options.renderDistance().get() * 16;
		//?} 1.18{
		/*int renderDistBlocks = Minecraft.getInstance().options.renderDistance * 16;
		*///?}
		assert Minecraft.getInstance().player != null;
		Vec2 playerPos = new Vec2(((float) Minecraft.getInstance().player.getX()), ((float) Minecraft.getInstance().player.getY()));
		int distToStrikeX = (int) (playerPos.x - strikePos.x);
		int distToStrikeY = (int) (playerPos.y - strikePos.y);
		return distToStrikeX >= renderDistBlocks * .75 || distToStrikeY >= renderDistBlocks * .75;
	}

	@Unique
	private boolean polyphonic$isMedium() {
		Vec2 strikePos = new Vec2(((float) this.getX()), ((float) this.getY()));
		//? >=1.19{
		int renderDistBlocks = Minecraft.getInstance().options.renderDistance().get() * 16;
		//?} 1.18{
		/*int renderDistBlocks = Minecraft.getInstance().options.renderDistance * 16;
		*///?}
		assert Minecraft.getInstance().player != null;
		Vec2 playerPos = new Vec2(((float) Minecraft.getInstance().player.getX()), ((float) Minecraft.getInstance().player.getY()));
		int distToStrikeX = (int) (playerPos.x - strikePos.x);
		int distToStrikeY = (int) (playerPos.y - strikePos.y);
		return distToStrikeX >= renderDistBlocks * .45 || distToStrikeY >= renderDistBlocks * .45;
	}

	@Unique
	ResourceManager rm = Minecraft.getInstance().getResourceManager();
}

package dev.vesper.soundtags.mixin;

import com.google.common.collect.Sets;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import dev.vesper.soundtags.common.STSoundEvents;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
//? forge{
/*import net.minecraftforge.event.ForgeEventFactory;
*///?} neoforge{
/*import net.neoforged.neoforge.event.EventHooks;
*///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Set;

@Mixin(LightningBolt.class)
@MixinEnvironment(type = MixinEnvironment.Env.MAIN)
public abstract class LightningBoltMixin extends Entity {

	@Shadow
	private int life;

	@Shadow
	private int flashes;

	@Shadow
	public long seed;

	@Shadow
	private boolean visualOnly;

	@Shadow
	private ServerPlayer cause;

	@Shadow
	private final Set<Entity> hitEntities = Sets.newHashSet();

	//? <=1.21{
	/*@Shadow
	private void spawnFire(int p_20871_) {}

	@Shadow
	private static void clearCopperOnLightningStrike(Level p_147151_, BlockPos p_147152_){}
	*///?} >=26.1{
	@Shadow
	private void spawnFire(int additionalSources) {}

	@Shadow
	private static void clearCopperOnLightningStrike(Level level, BlockPos struckPos){}
	//?}

	@Shadow
	private BlockPos getStrikePosition(){return  null;}

	@Shadow
	private void powerLightningRod(){}

	public LightningBoltMixin(EntityType<?> p_19870_, Level p_19871_) {
		super(p_19870_, p_19871_);
	}

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void afterLoadLevel(CallbackInfo ci) {
		// There needs to be someway to skip all this and use vanilla if no resourcepack using this is enabled
		// I also need to mixin extras to make it wayyy more compact
		//? >=1.20{
		super.tick();
		if (this.life == 2){
			if (this.level().isClientSide()){
			if (this.weatherTags$isDistant()){
				this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), STSoundEvents.LIGHTNING_STRIKE_FAR, SoundSource.BLOCKS, 10000.0F, 0.8F + this.random.nextFloat() * 0.2F, false);
			} else if (this.weatherTags$isMedium()){
				this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), STSoundEvents.LIGHTNING_STRIKE_MED, SoundSource.BLOCKS, 10000.0F, 0.8F + this.random.nextFloat() * 0.2F, false);
				this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.BLOCKS, 1.0F, 0.5F + this.random.nextFloat() * 0.2F, false);
			} else {
				this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.BLOCKS, 10000.0F, 0.8F + this.random.nextFloat() * 0.2F, false);
				this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.BLOCKS, 2.0F, 0.5F + this.random.nextFloat() * 0.2F, false);
				}
			} else {
				Difficulty difficulty = this.level().getDifficulty();
				if (difficulty == Difficulty.NORMAL || difficulty == Difficulty.HARD) {
					this.spawnFire(4);
				}

				this.powerLightningRod();
				clearCopperOnLightningStrike(this.level(), this.getStrikePosition());
				this.gameEvent(GameEvent.LIGHTNING_STRIKE);
			}
		}

		--this.life;
		if (this.life < 0) {
			if (this.flashes == 0) {
				if (this.level() instanceof ServerLevel) {
					List<Entity> list = this.level().getEntities(this, new AABB(this.getX() - (double)15.0F, this.getY() - (double)15.0F, this.getZ() - (double)15.0F, this.getX() + (double)15.0F, this.getY() + (double)6.0F + (double)15.0F, this.getZ() + (double)15.0F), (p_147140_) -> p_147140_.isAlive() && !this.hitEntities.contains(p_147140_));

					for(ServerPlayer serverplayer : ((ServerLevel)this.level()).getPlayers((p_147157_) -> p_147157_.distanceTo(this) < 256.0F)) {
						CriteriaTriggers.LIGHTNING_STRIKE.trigger(serverplayer, (LightningBolt) (Object) this, list);
					}
				}

				this.discard();
			} else if (this.life < -this.random.nextInt(10)) {
				--this.flashes;
				this.life = 1;
				this.seed = this.random.nextLong();
				this.spawnFire(0);
			}
		}

		if (this.life >= 0) {
			if (!(this.level() instanceof ServerLevel)) {
				this.level().setSkyFlashTime(2);
			} else if (!this.visualOnly) {
				List<Entity> list1 = this.level().getEntities(this, new AABB(this.getX() - (double)3.0F, this.getY() - (double)3.0F, this.getZ() - (double)3.0F, this.getX() + (double)3.0F, this.getY() + (double)6.0F + (double)3.0F, this.getZ() + (double)3.0F), Entity::isAlive);

				for(Entity entity : list1) {
					/*? if forge {*//*if (!ForgeEventFactory.onEntityStruckByLightning(entity, (LightningBolt) (Object) this)) { *//*?}*/
					/*? if neoforge {*//*if (!EventHooks.onEntityStruckByLightning(entity, (LightningBolt) (Object) this)) { *//*?}*/
						// Okay, Neo & Lex [Forge] dont like this, not sure if its whining just cause or if it will actually error
						entity.thunderHit((ServerLevel)this.level(), (LightningBolt) (Object) this);
					/*? if forge || neoforge {*//*}*//*?}*/
				}

				this.hitEntities.addAll(list1);
				if (this.cause != null) {
					CriteriaTriggers.CHANNELED_LIGHTNING.trigger(this.cause, list1);
				}
			}
		}
		//?} <1.20{
		/*super.tick();
		if (this.life == 2){
			if (this.level.isClientSide()){
				if (this.weatherTags$isDistant()){
					this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), STSoundEvents.LIGHTNING_STRIKE_FAR, SoundSource.BLOCKS, 10000.0F, 0.8F + this.random.nextFloat() * 0.2F, false);
				} else if (this.weatherTags$isMedium()){
					this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), STSoundEvents.LIGHTNING_STRIKE_MED, SoundSource.BLOCKS, 10000.0F, 0.8F + this.random.nextFloat() * 0.2F, false);
					this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.BLOCKS, 2.0F, 0.5F + this.random.nextFloat() * 0.2F, false);
				} else {
					this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.BLOCKS, 10000.0F, 0.8F + this.random.nextFloat() * 0.2F, false);
					this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.BLOCKS, 2.0F, 0.5F + this.random.nextFloat() * 0.2F, false);
				}
			} else {
				Difficulty difficulty = this.level.getDifficulty();
				if (difficulty == Difficulty.NORMAL || difficulty == Difficulty.HARD) {
					this.spawnFire(4);
				}

				this.powerLightningRod();
				clearCopperOnLightningStrike(this.level, this.getStrikePosition());
				this.gameEvent(GameEvent.LIGHTNING_STRIKE);
			}
		}

		--this.life;
		if (this.life < 0) {
			if (this.flashes == 0) {
				if (this.level instanceof ServerLevel) {
					List<Entity> list = this.level.getEntities(this, new AABB(this.getX() - (double)15.0F, this.getY() - (double)15.0F, this.getZ() - (double)15.0F, this.getX() + (double)15.0F, this.getY() + (double)6.0F + (double)15.0F, this.getZ() + (double)15.0F), (p_147140_) -> p_147140_.isAlive() && !this.hitEntities.contains(p_147140_));

					for(ServerPlayer serverplayer : ((ServerLevel)this.level).getPlayers((p_147157_) -> p_147157_.distanceTo(this) < 256.0F)) {
						CriteriaTriggers.LIGHTNING_STRIKE.trigger(serverplayer, (LightningBolt) (Object) this, list);
					}
				}

				this.discard();
			} else if (this.life < -this.random.nextInt(10)) {
				--this.flashes;
				this.life = 1;
				this.seed = this.random.nextLong();
				this.spawnFire(0);
			}
		}

		if (this.life >= 0) {
			if (!(this.level instanceof ServerLevel)) {
				this.level.setSkyFlashTime(2);
			} else if (!this.visualOnly) {
				List<Entity> list1 = this.level.getEntities(this, new AABB(this.getX() - (double)3.0F, this.getY() - (double)3.0F, this.getZ() - (double)3.0F, this.getX() + (double)3.0F, this.getY() + (double)6.0F + (double)3.0F, this.getZ() + (double)3.0F), Entity::isAlive);

				for(Entity entity : list1) {
					/^? if forge {^/if (!ForgeEventFactory.onEntityStruckByLightning(entity, (LightningBolt) (Object) this)) { /^?}^/
					/^? if neoforge {^//^if (!EventHooks.onEntityStruckByLightning(entity, (LightningBolt) (Object) this)) { ^//^?}^/
						entity.thunderHit((ServerLevel)this.level, (LightningBolt) (Object) this);
					/^? if forge || neoforge {^/}/^?}^/
				}

				this.hitEntities.addAll(list1);
				if (this.cause != null) {
					CriteriaTriggers.CHANNELED_LIGHTNING.trigger(this.cause, list1);
				}
			}
		}
		*///?}
		ci.cancel();
	}


	@Unique
	private boolean weatherTags$isDistant() {
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
	private boolean weatherTags$isMedium() {
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
}

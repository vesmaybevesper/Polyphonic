package dev.vesper.polyphonic.common.utils;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class RangeCheckers {

	public static boolean withinRange(Vec3 checkedPos, int range){
		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
		return withinRange(BlockPos.containing(checkedPos.x, checkedPos.y, checkedPos.z), camera, range);
	}

	public static boolean withinRange(Vec3 checkedPos, Camera camera, int range){
		return withinRange(BlockPos.containing(checkedPos.x, checkedPos.y, checkedPos.z), camera, range);
	}

	public static boolean withinRange(BlockPos checkedPos, int range){
		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
		return withinRange(checkedPos, camera, range);
	}

	public static boolean withinRange(BlockPos checkedPos, Camera camera, int range){
		BlockPos playerPos = camera.blockPosition();
		int rainX = checkedPos.getX();
		int rainZ = checkedPos.getZ();
		return Mth.abs(rainX - playerPos.getX()) <= range || Mth.abs(rainZ - playerPos.getZ()) <= range;
	}
}

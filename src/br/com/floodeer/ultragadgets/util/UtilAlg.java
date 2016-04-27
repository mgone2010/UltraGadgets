package br.com.floodeer.ultragadgets.util;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import br.com.floodeer.ultragadgets.UltraGadgets;

public class UtilAlg {
	
	public static TreeSet<String> sortKey(Set<String> paramSet) {
		TreeSet<String> localTreeSet = new TreeSet<>();
		for (String str : paramSet) {
			localTreeSet.add(str);
		}
		return localTreeSet;
	}

	static UltraGadgets plugin = UltraGadgets.get();

	public static Vector getTrajectory(Entity paramEntity1, Entity paramEntity2) {
		return getTrajectory(paramEntity1.getLocation().toVector(), paramEntity2.getLocation().toVector());
	}

	public static Vector getTrajectory(Entity paramEntity, Player paramPlayer) {
		return getTrajectory(paramEntity.getLocation().toVector(), paramPlayer.getLocation().toVector());
	}

	public static Vector getTrajectory(Location paramLocation, Player paramPlayer) {
		return getTrajectory(paramLocation.toVector(), paramPlayer.getLocation().toVector());
	}

	public static Vector getTrajectory(Location paramLocation1, Location paramLocation2) {
		return getTrajectory(paramLocation1.toVector(), paramLocation2.toVector());
	}

	public static Vector getTrajectory(Vector paramVector1, Vector paramVector2) {
		return paramVector2.subtract(paramVector1).normalize();
	}

	public static Vector getTrajectory2d(Entity paramEntity1, Entity paramEntity2) {
		return getTrajectory2d(paramEntity1.getLocation().toVector(), paramEntity2.getLocation().toVector());
	}

	public static Vector getTrajectory2d(Location paramLocation1, Location paramLocation2) {
		return getTrajectory2d(paramLocation1.toVector(), paramLocation2.toVector());
	}

	public static Vector getTrajectory2d(Vector paramVector1, Vector paramVector2) {
		return paramVector2.subtract(paramVector1).setY(0).normalize();
	}

	public static boolean HasSight(Location paramLocation, Player paramPlayer) {
		return (HasSight(paramLocation, paramPlayer.getLocation()))
				|| (HasSight(paramLocation, paramPlayer.getEyeLocation()));
	}

	public static boolean HasSight(Location paramLocation1, Location paramLocation2) {
		Location localLocation = new Location(paramLocation1.getWorld(), paramLocation1.getX(), paramLocation1.getY(),
				paramLocation1.getZ());

		double d = 0.1D;
		Vector localVector = getTrajectory(paramLocation1, paramLocation2).multiply(0.1D);
		while (UtilMath.offset(localLocation, paramLocation2) > d) {
			localLocation.add(localVector);
			if (!UtilBlock.airFoliage(localLocation.getBlock())) {
				return false;
			}
		}
		return true;
	}

	public static float GetPitch(Vector paramVector) {
		double d1 = paramVector.getX();
		double d2 = paramVector.getY();
		double d3 = paramVector.getZ();
		double d4 = Math.sqrt(d1 * d1 + d3 * d3);

		double d5 = Math.toDegrees(Math.atan(d4 / d2));
		if (d2 <= 0.0D) {
			d5 += 90.0D;
		} else {
			d5 -= 90.0D;
		}
		return (float) d5;
	}

	public static float GetYaw(Vector paramVector) {
		double d1 = paramVector.getX();
		double d2 = paramVector.getZ();

		double d3 = Math.toDegrees(Math.atan(-d1 / d2));
		if (d2 < 0.0D) {
			d3 += 180.0D;
		}
		return (float) d3;
	}

	public static Vector Normalize(Vector paramVector) {
		if (paramVector.length() > 0.0D) {
			paramVector.normalize();
		}
		return paramVector;
	}

	public static Vector Clone(Vector paramVector) {
		return new Vector(paramVector.getX(), paramVector.getY(), paramVector.getZ());
	}

	public static <T> T Random(List<T> paramList) {
		return (T) paramList.get(UtilMath.r(paramList.size()));
	}
}

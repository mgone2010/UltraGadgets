package br.com.floodeer.ultragadgets.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class UtilVelocity {
	public static Vector getBumpVector(Entity paramEntity, Location paramLocation, double paramDouble) {
		Vector localVector = paramEntity.getLocation().toVector().subtract(paramLocation.toVector()).normalize();
		localVector.multiply(paramDouble);
		return localVector;
	}

	public static void knockback(Player p, Location loc, double d, double d2, boolean d3) {
		Location l = p.getLocation();
		l.setPitch(0.0F);
		loc.setPitch(0.0F);
		Vector v = l.toVector().subtract(loc.toVector()).normalize();
		if (d3) {
			v = loc.toVector().subtract(l.toVector()).normalize();
		}

		v.setY(d2);
		p.setVelocity(v.multiply(d));
		return;
	}

	public static Vector getRandomVectorline() {
		int min = -5;
		int max = 5;
		int rz = (int) (Math.random() * (max - min) + min);
		int rx = (int) (Math.random() * (max - min) + min);

		double miny = -5.0D;
		double maxy = -1.0D;
		double ry = Math.random() * (maxy - miny) + miny;

		return new Vector(rx, ry, rz).normalize();
	}

	public static Vector getPullVector(Entity paramEntity, Location paramLocation, double paramDouble) {
		Vector localVector = paramLocation.toVector().subtract(paramEntity.getLocation().toVector()).normalize();
		localVector.multiply(paramDouble);
		return localVector;
	}

	public static void bumpEntity(Entity paramEntity, Location paramLocation, double paramDouble) {
		paramEntity.setVelocity(getBumpVector(paramEntity, paramLocation, paramDouble));
	}

	public static void bumpEntity(Entity paramEntity, Location paramLocation, double paramDouble1,
			double paramDouble2) {
		Vector localVector = getBumpVector(paramEntity, paramLocation, paramDouble1);
		localVector.setY(paramDouble2);
		paramEntity.setVelocity(localVector);
	}

	public static void pullEntity(Entity paramEntity, Location paramLocation, double paramDouble) {
		paramEntity.setVelocity(getPullVector(paramEntity, paramLocation, paramDouble));
	}

	public static void pullEntity(Entity paramEntity, Location paramLocation, double paramDouble1,
			double paramDouble2) {
		Vector localVector = getPullVector(paramEntity, paramLocation, paramDouble1);
		localVector.setY(paramDouble2);
		paramEntity.setVelocity(localVector);
	}

	public static void velocity(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3) {
		velocity(paramEntity, paramEntity.getLocation().getDirection(), paramDouble1, false, 0.0D, paramDouble2,
				paramDouble3);
	}

	public static void velocity(Entity paramEntity, Vector paramVector, double paramDouble1, boolean paramBoolean,
			double paramDouble2, double paramDouble3, double paramDouble4) {
		if ((Double.isNaN(paramVector.getX())) || (Double.isNaN(paramVector.getY()))
				|| (Double.isNaN(paramVector.getZ())) || (paramVector.length() == 0.0D)) {
			return;
		}
		if (paramBoolean) {
			paramVector.setY(paramDouble2);
		}
		paramVector.normalize();
		paramVector.multiply(paramDouble1);

		paramVector.setY(paramVector.getY() + paramDouble3);
		if (paramVector.getY() > paramDouble4) {
			paramVector.setY(paramDouble4);
		}
		paramEntity.setFallDistance(0.0F);
		paramEntity.setVelocity(paramVector);
	}

	public static final Vector rotateAroundAxisX(Vector paramVector, double paramDouble) {
		double d3 = Math.cos(paramDouble);
		double d4 = Math.sin(paramDouble);
		double d1 = paramVector.getY() * d3 - paramVector.getZ() * d4;
		double d2 = paramVector.getY() * d4 + paramVector.getZ() * d3;
		return paramVector.setY(d1).setZ(d2);
	}

	public static final Vector rotateAroundAxisY(Vector paramVector, double paramDouble) {
		double d3 = Math.cos(paramDouble);
		double d4 = Math.sin(paramDouble);
		double d1 = paramVector.getX() * d3 + paramVector.getZ() * d4;
		double d2 = paramVector.getX() * -d4 + paramVector.getZ() * d3;
		return paramVector.setX(d1).setZ(d2);
	}

	public static final Vector rotateAroundAxisZ(Vector paramVector, double paramDouble) {
		double d3 = Math.cos(paramDouble);
		double d4 = Math.sin(paramDouble);
		double d1 = paramVector.getX() * d3 - paramVector.getY() * d4;
		double d2 = paramVector.getX() * d4 + paramVector.getY() * d3;
		return paramVector.setX(d1).setY(d2);
	}

	public static final Vector rotateVector(Vector paramVector, double paramDouble1, double paramDouble2,
			double paramDouble3) {
		rotateAroundAxisX(paramVector, paramDouble1);
		rotateAroundAxisY(paramVector, paramDouble2);
		rotateAroundAxisZ(paramVector, paramDouble3);
		return paramVector;
	}

	public static final double angleToXAxis(Vector paramVector) {
		return Math.atan2(paramVector.getX(), paramVector.getY());
	}

	public static void velocity(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3,
			boolean paramBoolean) {
		velocity(paramEntity, paramEntity.getLocation().getDirection(), paramDouble1, false, 0.0D, paramDouble2,
				paramDouble3, paramBoolean);
	}

	public static void velocity(Entity paramEntity, Vector paramVector, double paramDouble1, boolean paramBoolean1,
			double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean2) {
		if ((Double.isNaN(paramVector.getX())) || (Double.isNaN(paramVector.getY()))
				|| (Double.isNaN(paramVector.getZ())) || (paramVector.length() == 0.0D)) {
			return;
		}
		if (paramBoolean1) {
			paramVector.setY(paramDouble2);
		}
		paramVector.normalize();
		paramVector.multiply(paramDouble1);

		paramVector.setY(paramVector.getY() + paramDouble3);
		if (paramVector.getY() > paramDouble4) {
			paramVector.setY(paramDouble4);
		}
		if (paramBoolean2) {
			paramVector.setY(paramVector.getY() + 0.2D);
		}
		paramEntity.setFallDistance(0.0F);
		paramEntity.setVelocity(paramVector);
	}

	public static Vector rotateX(Vector v, double a) {
		double y = Math.cos(a) * v.getY() - Math.sin(a) * v.getZ();
		double z = Math.sin(a) * v.getY() + Math.cos(a) * v.getZ();
		return v.setY(y).setZ(z);
	}

	public static Vector rotateY(Vector v, double b) {
		double x = Math.cos(b) * v.getX() + Math.sin(b) * v.getZ();
		double z = -Math.sin(b) * v.getX() + Math.cos(b) * v.getZ();
		return v.setX(x).setY(z);
	}

	public static final Vector rotateZ(Vector v, double c) {
		double x = Math.cos(c) * v.getX() - Math.sin(c) * v.getY();
		double y = Math.sin(c) * v.getX() + Math.cos(c) * v.getY();
		return v.setX(x).setY(y);
	}
}
package br.com.floodeer.ultragadgets.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class UtilLocations {

	public static Entity[] getNearbyEntities(Location l, int radius) {
		int chunkRadius = radius < 16 ? 1 : (radius - radius % 16) / 16;
		HashSet<Entity> radiusEntities = new HashSet<>();
		for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
			for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
				int x = (int) l.getX();
				int y = (int) l.getY();
				int z = (int) l.getZ();
				Entity[] arrayOfEntity;
				int j = (arrayOfEntity = new Location(l.getWorld(), x + chX * 16, y, z + chZ * 16).getChunk()
						.getEntities()).length;
				for (int i = 0; i < j; i++) {
					Entity e = arrayOfEntity[i];
					if ((e.getLocation().distance(l) <= radius) && (e.getLocation().getBlock() != l.getBlock())) {
						radiusEntities.add(e);
					}
				}
			}
		}
		return (Entity[]) radiusEntities.toArray(new Entity[radiusEntities.size()]);
	}

	public static Location getLocation(Location paramLocation, double paramDouble1, double paramDouble2,
			double paramDouble3) {
		return new Location(paramLocation.getWorld(), paramLocation.getX() + paramDouble1,
				paramLocation.getY() + paramDouble2, paramLocation.getZ() + paramDouble3);
	}

	public static List<Location> getCircleBlocks(Location paramLocation, int paramInt) {
		ArrayList<Location> localArrayList = new ArrayList<>();

		int i = paramLocation.getBlockX() - paramInt / 2;
		int j = paramLocation.getBlockY() - paramInt / 2;
		int k = paramLocation.getBlockZ() - paramInt / 2;
		for (int m = i; m < i + paramInt; m++) {
			for (int n = j; n < j + paramInt; n++) {
				for (int i1 = k; i1 < k + paramInt; i1++) {
					localArrayList.add(paramLocation.getWorld().getBlockAt(m, n, i1).getLocation());
				}
			}
		}
		return localArrayList;
	}

	public static List<Player> getNearbyPlayers(Location paramLocation, double paramDouble) {
		ArrayList<Player> localArrayList = new ArrayList<>();
		for (Player localPlayer : Bukkit.getOnlinePlayers()) {
			if ((localPlayer.getWorld() == paramLocation.getWorld())
					&& (localPlayer.getLocation().distance(paramLocation) <= paramDouble)) {
				localArrayList.add(localPlayer);
			}
		}
		return localArrayList;
	}

	public static List<LivingEntity> getNearbyEntitiesArrays(Location paramLocation, double paramDouble) {
		ArrayList<LivingEntity> localArrayList = new ArrayList<>();
		for (LivingEntity localEntities : paramLocation.getWorld().getLivingEntities()) {
			if (localEntities.getLocation().distance(paramLocation) <= paramDouble) {
				localArrayList.add(localEntities);
			}
		}
		return localArrayList;
	}

	public static List<Location> getCircle(Location paramLocation, double paramDouble, int paramInt) {
		ArrayList<Location> localArrayList = new ArrayList<>();
		double d1 = 6.283185307179586D / paramInt;
		for (int i = 0; i < paramInt; i++) {
			double d2 = i * d1;

			double d3 = paramLocation.getX() + paramDouble * Math.cos(d2);
			double d4 = paramLocation.getZ() + paramDouble * Math.sin(d2);

			localArrayList.add(new Location(paramLocation.getWorld(), d3, paramLocation.getY(), d4));
		}
		return localArrayList;
	}

	public static List<Location> getSphere(Location loc, int r, int h, boolean hollow, boolean sphere, int plus_y) {
		List<Location> circleblocks = new ArrayList<>();
		int cx = loc.getBlockX();
		int cy = loc.getBlockY();
		int cz = loc.getBlockZ();
		for (int x = cx - r; x <= cx + r; x++) {
			for (int z = cz - r; z <= cz + r; z++) {
				for (int y = sphere ? cy - r : cy; y < (sphere ? cy + r : cy + h); y++) {
					double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
					if ((dist < r * r) && ((!hollow) || (dist >= (r - 1) * (r - 1)))) {
						Location l = new Location(loc.getWorld(), x, y + plus_y, z);
						circleblocks.add(l);
					}
				}
			}
		}
		return circleblocks;
	}

	public static Location getTargetBlock(Player paramPlayer, int paramInt) {
		Location localLocation = paramPlayer.getEyeLocation();
		Vector localVector = localLocation.getDirection().normalize();

		Block localBlock = null;
		for (int i = 0; i <= paramInt; i++) {
			localLocation.add(localVector);
			localBlock = localLocation.getBlock();
			if (localBlock.getType() != Material.AIR) {
				return localLocation;
			}
		}
		return localLocation;
	}

	public static Player getTarget(Player player, int distanceX, int distanceY, int distanceZ, int finalDistance) {
		List<Entity> n = player.getNearbyEntities(distanceX, distanceY, distanceZ);
		ArrayList<Player> nearPlayers = new ArrayList<>();
		for (Entity e : n) {
			if ((e instanceof Player)) {
				nearPlayers.add((Player) e);
			}
		}
		Player target = null;
		BlockIterator bItr = new BlockIterator(player, finalDistance);
		while (bItr.hasNext()) {
			Block block = bItr.next();
			int bx = block.getX();
			int by = block.getY();
			int bz = block.getZ();
			for (Player e : nearPlayers) {
				Location loc = e.getLocation();
				double ex = loc.getX();
				double ey = loc.getY();
				double ez = loc.getZ();
				if ((bx - 0.75D <= ex) && (ex <= bx + 1.75D) && (bz - 0.75D <= ez) && (ez <= bz + 1.75D)
						&& (by - 1 <= ey) && (ey <= by + 2.5D)) {
					target = e;
					break;
				}
			}
		}
		return target;
	}

	public static List<Block> getBlocksInRadius(Location location, int radius, boolean hollow) {
		List<Block> blocks = new ArrayList<>();

		int bX = location.getBlockX();
		int bY = location.getBlockY();
		int bZ = location.getBlockZ();
		for (int x = bX - radius; x <= bX + radius; x++) {
			for (int y = bY - radius; y <= bY + radius; y++) {
				for (int z = bZ - radius; z <= bZ + radius; z++) {
					double distance = (bX - x) * (bX - x) + (bY - y) * (bY - y) + (bZ - z) * (bZ - z);
					if ((distance < radius * radius) && ((!hollow) || (distance >= (radius - 1) * (radius - 1)))) {
						Location l = new Location(location.getWorld(), x, y, z);
						blocks.add(l.getBlock());
					}
				}
			}
		}
		return blocks;
	}

	public static List<Block> getNearbyBlocks(Location location, int radius) {
		List<Block> blocks = new ArrayList<Block>();
		for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
			for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
				for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
					blocks.add(location.getWorld().getBlockAt(x, y, z));
				}
			}
		}
		return blocks;
	}

	public static boolean checkEmptyArea(Location corner1, Location corner2) {
		if (corner1.getWorld() != corner2.getWorld()) {
			return false;
		}
		World world = corner1.getWorld();
		for (int x = corner1.getBlockX(); x <= corner2.getBlockX(); x++) {
			for (int y = corner1.getBlockY(); y <= corner2.getBlockY(); y++) {
				for (int z = corner1.getBlockZ(); z <= corner2.getBlockZ(); z++) {
					Location location = new Location(world, x, y, z);
					Block block = location.getBlock();
					if (block.getType() != Material.AIR) {
						return false;
					}
					Entity[] arrayOfEntity;
					int j = (arrayOfEntity = getNearbyEntities(location, 2)).length;
					for (int i = 0; i < j; i++) {
						Entity entity = arrayOfEntity[i];
						if (((entity instanceof ItemFrame)) || ((entity instanceof Painting))) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public static Block checkEmptyArea(Block b, int maxradius) {
		BlockFace[] faces = { BlockFace.UP, BlockFace.NORTH, BlockFace.EAST };
		BlockFace[][] orth = { { BlockFace.NORTH, BlockFace.EAST }, { BlockFace.UP, BlockFace.EAST },
				{ BlockFace.NORTH, BlockFace.UP } };
		for (int r = 0; r <= maxradius; r++) {
			for (int s = 0; s < 6; s++) {
				BlockFace f = faces[s % 3];
				BlockFace[] o = orth[s % 3];
				if (s >= 3)
					f = f.getOppositeFace();
				Block c = b.getRelative(f, r);
				for (int x = -r; x <= r; x++) {
					for (int y = -r; y <= r; y++) {
						Block a = c.getRelative(o[0], x).getRelative(o[1], y);
						if (a.getTypeId() == 0 && a.getRelative(BlockFace.UP).getTypeId() == 0)
							return a;
					}
				}
			}
		}
		return null;
	}

	public static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
	public static final BlockFace[] radial = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST,
			BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };

	public static BlockFace yawToFace(float yaw) {
		return yawToFace(yaw, true);
	}

	public static BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
		if (useSubCardinalDirections) {
			return radial[(Math.round(yaw / 45.0F) & 0x7)];
		}
		return axis[(Math.round(yaw / 90.0F) & 0x3)];
	}

	public static Vector pitchYawToVector(Location l) {
		double pitch = (l.getPitch() + 90.0F) * 3.141592653589793D / 180.0D;
		double yaw = (l.getYaw() + 90.0F) * 3.141592653589793D / 180.0D;

		double x = Math.sin(pitch) * Math.cos(yaw);
		double y = Math.sin(pitch) * Math.sin(yaw);
		double z = Math.cos(pitch);

		return new Vector(x, z, y);
	}

	public static float getYaw(Vector motion) {
		double dx = motion.getX();
		double dz = motion.getZ();
		double yaw = 0.0D;
		if (dx != 0.0D) {
			if (dx < 0.0D) {
				yaw = 4.71238898038469D;
			} else {
				yaw = 1.5707963267948966D;
			}
			yaw -= Math.atan(dz / dx);
		} else if (dz < 0.0D) {
			yaw = 3.141592653589793D;
		}
		return (float) (-yaw * 180.0D / 3.141592653589793D);
	}

}
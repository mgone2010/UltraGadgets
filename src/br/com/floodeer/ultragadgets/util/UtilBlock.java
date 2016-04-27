package br.com.floodeer.ultragadgets.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.floodeer.ultragadgets.UltraGadgets;

public class UtilBlock {

	public static HashSet<Byte> blockPassSet = new HashSet<Byte>();
	public static Map<Location, String> blocksToRestore = new HashMap<>();
	public static HashMap<Player, ArrayList<Blocks>> blocktorestore = new HashMap<>();
	public static ArrayList<Block> blockstorestore = new ArrayList<>();

	public static List<Block> getBlocksInRadius(Location location, int radius, boolean hollow) {
		List<Block> blocks = new ArrayList<>();

		int bX = location.getBlockX();
		int bY = location.getBlockY();
		int bZ = location.getBlockZ();

		for (int x = bX - radius; x <= bX + radius; x++) {
			for (int y = bY - radius; y <= bY + radius; y++) {
				for (int z = bZ - radius; z <= bZ + radius; z++) {

					double distance = ((bX - x) * (bX - x) + (bY - y) * (bY - y) + (bZ - z) * (bZ - z));

					if (distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1)))) {
						Location l = new Location(location.getWorld(), x, y, z);
						if (l.getBlock().getType() != Material.BARRIER)
							blocks.add(l.getBlock());
					}
				}

			}
		}

		return blocks;
	}

	public static boolean isOnGround(Entity entity) {
		Block block = entity.getLocation().getBlock().getRelative(BlockFace.DOWN);
		if (block.getType().isSolid())
			return true;
		return false;
	}

	public static double getDistance(int x1, int z1, int x2, int z2) {
		int dx = x1 - x2;
		int dz = z1 - z2;
		return Math.sqrt((dx * dx + dz * dz));
	}

	@SuppressWarnings("deprecation")
	public static void forceRestore() {
		for (Location loc : blocksToRestore.keySet()) {
			Block b = loc.getBlock();
			String s = blocksToRestore.get(loc);
			Material m = Material.valueOf(s.split(",")[0]);
			byte d = Byte.valueOf(s.split(",")[1]);
			b.setType(m);
			b.setData(d);
		}
	}

	public static void restoreBlockAt(final Location LOCATION) {
		Bukkit.getScheduler().runTaskAsynchronously(UltraGadgets.get(), new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if (!blocksToRestore.containsKey(LOCATION))
					return;
				Block b = LOCATION.getBlock();
				String s = blocksToRestore.get(LOCATION);
				Material m = Material.valueOf(s.split(",")[0]);
				byte d = Byte.valueOf(s.split(",")[1]);
				for (Player player : b.getLocation().getWorld().getPlayers())
					player.sendBlockChange(LOCATION, m, d);
				blocksToRestore.remove(LOCATION);
			}
		});
	}

	public static void setToRestoreIgnoring(final Block BLOCK, final Material NEW_TYPE, final byte NEW_DATA,
			final int TICK_DELAY) {
		Bukkit.getScheduler().runTaskAsynchronously(UltraGadgets.get(), new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if (blocksToRestore.containsKey(BLOCK.getLocation()))
					return;
				if (!blocksToRestore.containsKey(BLOCK.getLocation())) {
					blocksToRestore.put(BLOCK.getLocation(), BLOCK.getType().toString() + "," + BLOCK.getData());
					for (Player player : BLOCK.getLocation().getWorld().getPlayers())
						player.sendBlockChange(BLOCK.getLocation(), NEW_TYPE, NEW_DATA);
					Bukkit.getScheduler().runTaskLater(UltraGadgets.get(), new Runnable() {
						@Override
						public void run() {
							restoreBlockAt(BLOCK.getLocation());
						}
					}, TICK_DELAY);
				}
			}
		});
	}

	@SuppressWarnings("deprecation")
	public static void setToRestore(final Block BLOCK, final Material NEW_TYPE, final byte NEW_DATA,
			final int TICK_DELAY) {
		if (blocksToRestore.containsKey(BLOCK.getLocation()))
			return;
		Block bUp = BLOCK.getRelative(BlockFace.UP);
		if (BLOCK.getType() != Material.AIR && BLOCK.getType() != Material.SIGN_POST
				&& BLOCK.getType() != Material.CHEST && BLOCK.getType() != Material.STONE_PLATE
				&& BLOCK.getType() != Material.WOOD_PLATE && BLOCK.getType() != Material.WALL_SIGN
				&& BLOCK.getType() != Material.WALL_BANNER && BLOCK.getType() != Material.STANDING_BANNER
				&& BLOCK.getType() != Material.CROPS && BLOCK.getType() != Material.LONG_GRASS
				&& BLOCK.getType() != Material.SAPLING && BLOCK.getType() != Material.DEAD_BUSH
				&& BLOCK.getType() != Material.RED_ROSE && BLOCK.getType() != Material.RED_MUSHROOM
				&& BLOCK.getType() != Material.BROWN_MUSHROOM && BLOCK.getType() != Material.TORCH
				&& BLOCK.getType() != Material.LADDER && BLOCK.getType() != Material.VINE
				&& BLOCK.getType() != Material.DOUBLE_PLANT && BLOCK.getType() != Material.PORTAL
				&& BLOCK.getType() != Material.CACTUS && BLOCK.getType() != Material.WATER
				&& BLOCK.getType() != Material.STATIONARY_WATER && BLOCK.getType() != Material.LAVA
				&& BLOCK.getType() != Material.STATIONARY_LAVA && BLOCK.getType() != Material.PORTAL
				&& BLOCK.getType() != Material.ENDER_PORTAL && BLOCK.getType() != Material.SOIL
				&& BLOCK.getType() != Material.BARRIER && BLOCK.getType() != Material.COMMAND
				&& BLOCK.getType() != Material.DROPPER && BLOCK.getType() != Material.DISPENSER
				&& !BLOCK.getType().toString().toLowerCase().contains("door") && BLOCK.getType() != Material.BED
				&& BLOCK.getType() != Material.BED_BLOCK && !isPortalBlock(BLOCK)
				&& net.minecraft.server.v1_8_R3.Block.getById(BLOCK.getTypeId()).getMaterial().isSolid() && a(bUp)
				&& BLOCK.getType().getId() != 43 && BLOCK.getType().getId() != 44) {
			if (!blocksToRestore.containsKey(BLOCK.getLocation())) {
				blocksToRestore.put(BLOCK.getLocation(), BLOCK.getType().toString() + "," + BLOCK.getData());
				for (Player player : BLOCK.getLocation().getWorld().getPlayers())
					player.sendBlockChange(BLOCK.getLocation(), NEW_TYPE, NEW_DATA);
				Bukkit.getScheduler().runTaskLater(UltraGadgets.get(), new Runnable() {
					@Override
					public void run() {
						restoreBlockAt(BLOCK.getLocation());
					}
				}, TICK_DELAY);
			}
		}
	}

	@SuppressWarnings("deprecation")
	private static boolean a(Block b) {
		if (b.getType() == Material.AIR
				|| net.minecraft.server.v1_8_R3.Block.getById(b.getTypeId()).getMaterial().isSolid())
			return true;
		return false;
	}

	public static boolean isPortalBlock(Block b) {
		for (BlockFace face : BlockFace.values())
			if (b.getRelative(face).getType() == Material.PORTAL)
				return true;
		return false;
	}

	@SuppressWarnings("deprecation")
	public static boolean solid(Block paramBlock) {
		if (paramBlock == null) {
			return false;
		}
		return solid(paramBlock.getTypeId());
	}

	public static boolean solid(int paramInt) {
		return solid((byte) paramInt);
	}

	public static boolean solid(byte paramByte) {
		if (blockPassSet.isEmpty()) {
			blockPassSet.add(Byte.valueOf((byte) 0));
			blockPassSet.add(Byte.valueOf((byte) 6));
			blockPassSet.add(Byte.valueOf((byte) 8));
			blockPassSet.add(Byte.valueOf((byte) 9));
			blockPassSet.add(Byte.valueOf((byte) 10));
			blockPassSet.add(Byte.valueOf((byte) 11));
			blockPassSet.add(Byte.valueOf((byte) 26));
			blockPassSet.add(Byte.valueOf((byte) 27));
			blockPassSet.add(Byte.valueOf((byte) 28));
			blockPassSet.add(Byte.valueOf((byte) 30));
			blockPassSet.add(Byte.valueOf((byte) 31));
			blockPassSet.add(Byte.valueOf((byte) 32));
			blockPassSet.add(Byte.valueOf((byte) 37));
			blockPassSet.add(Byte.valueOf((byte) 38));
			blockPassSet.add(Byte.valueOf((byte) 39));
			blockPassSet.add(Byte.valueOf((byte) 40));
			blockPassSet.add(Byte.valueOf((byte) 50));
			blockPassSet.add(Byte.valueOf((byte) 51));
			blockPassSet.add(Byte.valueOf((byte) 55));
			blockPassSet.add(Byte.valueOf((byte) 59));
			blockPassSet.add(Byte.valueOf((byte) 63));
			blockPassSet.add(Byte.valueOf((byte) 64));
			blockPassSet.add(Byte.valueOf((byte) 65));
			blockPassSet.add(Byte.valueOf((byte) 66));
			blockPassSet.add(Byte.valueOf((byte) 68));
			blockPassSet.add(Byte.valueOf((byte) 69));
			blockPassSet.add(Byte.valueOf((byte) 70));
			blockPassSet.add(Byte.valueOf((byte) 71));
			blockPassSet.add(Byte.valueOf((byte) 72));
			blockPassSet.add(Byte.valueOf((byte) 75));
			blockPassSet.add(Byte.valueOf((byte) 76));
			blockPassSet.add(Byte.valueOf((byte) 77));
			blockPassSet.add(Byte.valueOf((byte) 78));
			blockPassSet.add(Byte.valueOf((byte) 83));
			blockPassSet.add(Byte.valueOf((byte) 90));
			blockPassSet.add(Byte.valueOf((byte) 92));
			blockPassSet.add(Byte.valueOf((byte) 93));
			blockPassSet.add(Byte.valueOf((byte) 94));
			blockPassSet.add(Byte.valueOf((byte) 96));
			blockPassSet.add(Byte.valueOf((byte) 101));
			blockPassSet.add(Byte.valueOf((byte) 102));
			blockPassSet.add(Byte.valueOf((byte) 104));
			blockPassSet.add(Byte.valueOf((byte) 105));
			blockPassSet.add(Byte.valueOf((byte) 106));
			blockPassSet.add(Byte.valueOf((byte) 107));
			blockPassSet.add(Byte.valueOf((byte) 111));
			blockPassSet.add(Byte.valueOf((byte) 115));
			blockPassSet.add(Byte.valueOf((byte) 116));
			blockPassSet.add(Byte.valueOf((byte) 117));
			blockPassSet.add(Byte.valueOf((byte) 118));
			blockPassSet.add(Byte.valueOf((byte) 119));
			blockPassSet.add(Byte.valueOf((byte) 120));
			blockPassSet.add(Byte.valueOf((byte) -85));
		}
		return !blockPassSet.contains(Byte.valueOf(paramByte));
	}

	public static HashSet<Byte> blockAirFoliageSet = new HashSet<Byte>();

	@SuppressWarnings("deprecation")
	public static boolean airFoliage(Block paramBlock) {
		if (paramBlock == null) {
			return false;
		}
		return airFoliage(paramBlock.getTypeId());
	}

	public static boolean airFoliage(int paramInt) {
		return airFoliage((byte) paramInt);
	}

	public static boolean airFoliage(byte paramByte) {
		if (blockAirFoliageSet.isEmpty()) {
			blockAirFoliageSet.add(Byte.valueOf((byte) 0));
			blockAirFoliageSet.add(Byte.valueOf((byte) 6));
			blockAirFoliageSet.add(Byte.valueOf((byte) 31));
			blockAirFoliageSet.add(Byte.valueOf((byte) 32));
			blockAirFoliageSet.add(Byte.valueOf((byte) 37));
			blockAirFoliageSet.add(Byte.valueOf((byte) 38));
			blockAirFoliageSet.add(Byte.valueOf((byte) 39));
			blockAirFoliageSet.add(Byte.valueOf((byte) 40));
			blockAirFoliageSet.add(Byte.valueOf((byte) 51));
			blockAirFoliageSet.add(Byte.valueOf((byte) 59));
			blockAirFoliageSet.add(Byte.valueOf((byte) 104));
			blockAirFoliageSet.add(Byte.valueOf((byte) 105));
			blockAirFoliageSet.add(Byte.valueOf((byte) 115));
			blockAirFoliageSet.add(Byte.valueOf((byte) -115));
			blockAirFoliageSet.add(Byte.valueOf((byte) -114));
		}
		return blockAirFoliageSet.contains(Byte.valueOf(paramByte));
	}

	public static HashSet<Byte> fullSolid = new HashSet<Byte>();

	@SuppressWarnings("deprecation")
	public static boolean fullSolid(Block paramBlock) {
		if (paramBlock == null) {
			return false;
		}
		return fullSolid(paramBlock.getTypeId());
	}

	public static boolean fullSolid(int paramInt) {
		return fullSolid((byte) paramInt);
	}

	public static boolean fullSolid(byte paramByte) {
		if (fullSolid.isEmpty()) {
			fullSolid.add(Byte.valueOf((byte) 1));
			fullSolid.add(Byte.valueOf((byte) 2));
			fullSolid.add(Byte.valueOf((byte) 3));
			fullSolid.add(Byte.valueOf((byte) 4));
			fullSolid.add(Byte.valueOf((byte) 5));
			fullSolid.add(Byte.valueOf((byte) 7));
			fullSolid.add(Byte.valueOf((byte) 12));
			fullSolid.add(Byte.valueOf((byte) 13));
			fullSolid.add(Byte.valueOf((byte) 14));
			fullSolid.add(Byte.valueOf((byte) 15));
			fullSolid.add(Byte.valueOf((byte) 16));
			fullSolid.add(Byte.valueOf((byte) 17));
			fullSolid.add(Byte.valueOf((byte) 18));
			fullSolid.add(Byte.valueOf((byte) 19));
			fullSolid.add(Byte.valueOf((byte) 20));
			fullSolid.add(Byte.valueOf((byte) 21));
			fullSolid.add(Byte.valueOf((byte) 22));
			fullSolid.add(Byte.valueOf((byte) 23));
			fullSolid.add(Byte.valueOf((byte) 24));
			fullSolid.add(Byte.valueOf((byte) 25));
			fullSolid.add(Byte.valueOf((byte) 29));
			fullSolid.add(Byte.valueOf((byte) 33));
			fullSolid.add(Byte.valueOf((byte) 35));
			fullSolid.add(Byte.valueOf((byte) 41));
			fullSolid.add(Byte.valueOf((byte) 42));
			fullSolid.add(Byte.valueOf((byte) 43));
			fullSolid.add(Byte.valueOf((byte) 44));
			fullSolid.add(Byte.valueOf((byte) 45));
			fullSolid.add(Byte.valueOf((byte) 46));
			fullSolid.add(Byte.valueOf((byte) 47));
			fullSolid.add(Byte.valueOf((byte) 48));
			fullSolid.add(Byte.valueOf((byte) 49));
			fullSolid.add(Byte.valueOf((byte) 56));
			fullSolid.add(Byte.valueOf((byte) 57));
			fullSolid.add(Byte.valueOf((byte) 58));
			fullSolid.add(Byte.valueOf((byte) 60));
			fullSolid.add(Byte.valueOf((byte) 61));
			fullSolid.add(Byte.valueOf((byte) 62));
			fullSolid.add(Byte.valueOf((byte) 73));
			fullSolid.add(Byte.valueOf((byte) 74));
			fullSolid.add(Byte.valueOf((byte) 79));
			fullSolid.add(Byte.valueOf((byte) 80));
			fullSolid.add(Byte.valueOf((byte) 82));
			fullSolid.add(Byte.valueOf((byte) 84));
			fullSolid.add(Byte.valueOf((byte) 86));
			fullSolid.add(Byte.valueOf((byte) 87));
			fullSolid.add(Byte.valueOf((byte) 88));
			fullSolid.add(Byte.valueOf((byte) 89));
			fullSolid.add(Byte.valueOf((byte) 91));
			fullSolid.add(Byte.valueOf((byte) 95));
			fullSolid.add(Byte.valueOf((byte) 97));
			fullSolid.add(Byte.valueOf((byte) 98));
			fullSolid.add(Byte.valueOf((byte) 99));
			fullSolid.add(Byte.valueOf((byte) 100));
			fullSolid.add(Byte.valueOf((byte) 103));
			fullSolid.add(Byte.valueOf((byte) 110));
			fullSolid.add(Byte.valueOf((byte) 112));
			fullSolid.add(Byte.valueOf((byte) 121));
			fullSolid.add(Byte.valueOf((byte) 123));
			fullSolid.add(Byte.valueOf((byte) 124));
			fullSolid.add(Byte.valueOf((byte) 125));
			fullSolid.add(Byte.valueOf((byte) 126));
			fullSolid.add(Byte.valueOf((byte) -97));
			fullSolid.add(Byte.valueOf((byte) -94));
			fullSolid.add(Byte.valueOf((byte) -84));
			fullSolid.add(Byte.valueOf((byte) -127));
			fullSolid.add(Byte.valueOf((byte) -123));
			fullSolid.add(Byte.valueOf((byte) -119));
			fullSolid.add(Byte.valueOf((byte) -118));
			fullSolid.add(Byte.valueOf((byte) -104));
			fullSolid.add(Byte.valueOf((byte) -103));
			fullSolid.add(Byte.valueOf((byte) -101));
			fullSolid.add(Byte.valueOf((byte) -98));
		}
		return fullSolid.contains(Byte.valueOf(paramByte));
	}

	public static Block setBlock(final Location l, int id, int data, int time, final boolean fakeblock, boolean frosty,
			final Player p) {
		return null;

	}

	public static HashSet<Byte> blockUseSet = new HashSet<Byte>();

	@SuppressWarnings("deprecation")
	public static boolean usable(Block paramBlock) {
		if (paramBlock == null) {
			return false;
		}
		return usable(paramBlock.getTypeId());
	}

	public static boolean usable(int paramInt) {
		return usable((byte) paramInt);
	}

	public static boolean usable(byte paramByte) {
		if (blockUseSet.isEmpty()) {
			blockUseSet.add(Byte.valueOf((byte) 23));
			blockUseSet.add(Byte.valueOf((byte) 26));
			blockUseSet.add(Byte.valueOf((byte) 33));
			blockUseSet.add(Byte.valueOf((byte) 47));
			blockUseSet.add(Byte.valueOf((byte) 54));
			blockUseSet.add(Byte.valueOf((byte) 58));
			blockUseSet.add(Byte.valueOf((byte) 61));
			blockUseSet.add(Byte.valueOf((byte) 62));
			blockUseSet.add(Byte.valueOf((byte) 64));
			blockUseSet.add(Byte.valueOf((byte) 69));
			blockUseSet.add(Byte.valueOf((byte) 71));
			blockUseSet.add(Byte.valueOf((byte) 77));
			blockUseSet.add(Byte.valueOf((byte) 93));
			blockUseSet.add(Byte.valueOf((byte) 94));
			blockUseSet.add(Byte.valueOf((byte) 96));
			blockUseSet.add(Byte.valueOf((byte) 107));
			blockUseSet.add(Byte.valueOf((byte) 116));
			blockUseSet.add(Byte.valueOf((byte) 117));
			blockUseSet.add(Byte.valueOf((byte) -126));
			blockUseSet.add(Byte.valueOf((byte) -111));
			blockUseSet.add(Byte.valueOf((byte) -110));
			blockUseSet.add(Byte.valueOf((byte) -102));
			blockUseSet.add(Byte.valueOf((byte) -98));
		}
		return blockUseSet.contains(Byte.valueOf(paramByte));
	}

	public static HashMap<Block, Double> getInRadius(Location paramLocation, double paramDouble) {
		return getInRadius(paramLocation, paramDouble, 999.0D);
	}

	public static HashMap<Block, Double> getInRadius(Location paramLocation, double paramDouble1, double paramDouble2) {
		HashMap<Block, Double> localHashMap = new HashMap<Block, Double>();
		int i = (int) paramDouble1 + 1;
		for (int j = -i; j <= i; j++) {
			for (int k = -i; k <= i; k++) {
				for (int m = -i; m <= i; m++) {
					if (Math.abs(m) <= paramDouble2) {
						Block localBlock = paramLocation.getWorld().getBlockAt((int) (paramLocation.getX() + j),
								(int) (paramLocation.getY() + m), (int) (paramLocation.getZ() + k));

						double d = UtilMath.offset(paramLocation, localBlock.getLocation().add(0.5D, 0.5D, 0.5D));
						if (d <= paramDouble1) {
							localHashMap.put(localBlock, Double.valueOf(1.0D - d / paramDouble1));
						}
					}
				}
			}
		}
		return localHashMap;
	}

	public static HashMap<Block, Double> getInRadius(Block paramBlock, double paramDouble) {
		HashMap<Block, Double> localHashMap = new HashMap<Block, Double>();
		int i = (int) paramDouble + 1;
		for (int j = -i; j <= i; j++) {
			for (int k = -i; k <= i; k++) {
				for (int m = -i; m <= i; m++) {
					Block localBlock = paramBlock.getRelative(j, m, k);

					double d = UtilMath.offset(paramBlock.getLocation(), localBlock.getLocation());
					if (d <= paramDouble) {
						localHashMap.put(localBlock, Double.valueOf(1.0D - d / paramDouble));
					}
				}
			}
		}
		return localHashMap;
	}

	@SuppressWarnings("deprecation")
	public static boolean isBlock(ItemStack paramItemStack) {
		if (paramItemStack == null) {
			return false;
		}
		return (paramItemStack.getTypeId() > 0) && (paramItemStack.getTypeId() < 256);
	}

	public static Block getHighest(World paramWorld, int paramInt1, int paramInt2) {
		return getHighest(paramWorld, paramInt1, paramInt2, null);
	}

	public static Block getHighest(World paramWorld, int paramInt1, int paramInt2, HashSet<Material> paramHashSet) {
		Block localBlock = paramWorld.getHighestBlockAt(paramInt1, paramInt2);
		while ((airFoliage(localBlock)) || (localBlock.getType() == Material.LEAVES)
				|| ((paramHashSet != null) && (paramHashSet.contains(localBlock.getType())))) {
			localBlock = localBlock.getRelative(BlockFace.DOWN);
		}
		return localBlock.getRelative(BlockFace.UP);
	}

	public static ArrayList<Block> getSurrounding(Block paramBlock, boolean paramBoolean) {
		ArrayList<Block> localArrayList = new ArrayList<>();
		if (paramBoolean) {
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					for (int k = -1; k <= 1; k++) {
						if ((i != 0) || (j != 0) || (k != 0)) {
							localArrayList.add(paramBlock.getRelative(i, j, k));
						}
					}
				}
			}
		} else {
			localArrayList.add(paramBlock.getRelative(BlockFace.UP));
			localArrayList.add(paramBlock.getRelative(BlockFace.DOWN));
			localArrayList.add(paramBlock.getRelative(BlockFace.NORTH));
			localArrayList.add(paramBlock.getRelative(BlockFace.SOUTH));
			localArrayList.add(paramBlock.getRelative(BlockFace.EAST));
			localArrayList.add(paramBlock.getRelative(BlockFace.WEST));
		}
		return localArrayList;
	}

	public boolean isVisible(Block paramBlock) {
		for (Block localBlock : getSurrounding(paramBlock, false)) {
			if (!localBlock.getType().isOccluding()) {
				return true;
			}
		}
		return false;
	}

	public static Map<Player, ArrayList<Location>> arrays = new HashMap<>();

	@SuppressWarnings("deprecation")
	public static void setBlock(Location l, int m, int data, Player p) {
		l.getBlock().setTypeIdAndData(m, (byte) data, true);
		if (!blocktorestore.containsKey(p)) {
			blocktorestore.put(p, new ArrayList<Blocks>());
			blocktorestore.get(p).add(new Blocks(l.getBlock()));
		} else {
			blocktorestore.get(p).add(new Blocks(l.getBlock()));
		}

	}

	@SuppressWarnings("deprecation")
	public static void setBlock(Location l, Material mat, int data, Player p) {
		l.getBlock().setTypeIdAndData(mat.getId(), (byte) data, true);
		if (!blocktorestore.containsKey(p)) {
			blocktorestore.put(p, new ArrayList<Blocks>());
			blocktorestore.get(p).add(new Blocks(l.getBlock()));
		} else {
			blocktorestore.get(p).add(new Blocks(l.getBlock()));
		}
	}

	@SuppressWarnings("deprecation")
	public static void restoreBlocks(Player p) {
		if (blocktorestore.containsKey(p)) {
			Iterator<Blocks> iter = blocktorestore.get(p).iterator();
			while (iter.hasNext()) {
				Blocks save = (Blocks) iter.next();
				save.getBlock().getWorld().playEffect(save.getBlock().getLocation(), Effect.STEP_SOUND, save.getBlock().getTypeId());
				save.getBlock().setType(Material.AIR);
			}
			blocktorestore.remove(p);
		}
	}
}
package br.com.floodeer.ultragadgets.gadgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.scheduler.SchedulerEvent;
import br.com.floodeer.ultragadgets.scheduler.SchedulerType;
import br.com.floodeer.ultragadgets.util.Direction;
import br.com.floodeer.ultragadgets.util.UtilBlock;
import br.com.floodeer.ultragadgets.util.UtilCooldown;
import br.com.floodeer.ultragadgets.util.UtilMath;
import br.com.floodeer.ultragadgets.util.UtilTitle;

public class TrampolimGadget extends Gadget implements Listener {

	public TrampolimGadget() {
		super(UltraGadgets.getCfg().trampolimCooldown * 1000, Gadgets.TRAMPOLIM.toString(), Gadgets.TRAMPOLIM,
				Material.HOPPER);
	}

	HashMap<Player, ArrayList<Block>> _tBlocks = new HashMap<>();
	public final Map<Player, List<Block>> playerBlocks = new HashMap<>();
	public final Map<Player, Location> playerTrampoline = new HashMap<>();
	public static final ArrayList<Block> localArrayList = new ArrayList<>();
	public final List<Player> tOwner = new ArrayList<>();

	private ArrayList<Location> createSquare(Block center, int radius) {
		ArrayList<Location> locs = new ArrayList<>();
		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				if ((x == -radius) || (x == radius) || (z == -radius) || (z == radius)) {
					Block block = center.getWorld().getBlockAt(center.getX() + x, center.getY(), center.getZ() + z);
					locs.add(block.getLocation());
				}
			}
		}
		return locs;
	}

	public boolean checkArea(Player p) {
		ArrayList<Location> s0 = createSquare(p.getLocation().getBlock(), 0);
		ArrayList<Location> s1 = createSquare(p.getLocation().getBlock(), 1);
		ArrayList<Location> s2 = createSquare(p.getLocation().getBlock(), 2);
		ArrayList<Location> s3 = createSquare(p.getLocation().getBlock(), 3);
		ArrayList<Location> s4 = createSquare(p.getLocation().getBlock(), 4);

		ArrayList<Location> y0 = createSquare(p.getLocation().add(0.0D, 1.0D, 0.0D).getBlock(), 0);
		ArrayList<Location> y1 = createSquare(p.getLocation().add(0.0D, 1.0D, 0.0D).getBlock(), 1);
		ArrayList<Location> y2 = createSquare(p.getLocation().add(0.0D, 1.0D, 0.0D).getBlock(), 2);
		ArrayList<Location> y3 = createSquare(p.getLocation().add(0.0D, 1.0D, 0.0D).getBlock(), 3);
		ArrayList<Location> y4 = createSquare(p.getLocation().add(0.0D, 1.0D, 0.0D).getBlock(), 4);
		for (Location l0 : s0) {
			if (!l0.getBlock().isEmpty()) {
				return false;
			}
		}
		for (Location l1 : s1) {
			if (!l1.getBlock().isEmpty()) {
				return false;
			}
		}
		for (Location l2 : s2) {
			if (!l2.getBlock().isEmpty()) {
				return false;
			}
		}
		for (Location l3 : s3) {
			if (!l3.getBlock().isEmpty()) {
				return false;
			}
		}
		for (Location l4 : s4) {
			if (!l4.getBlock().isEmpty()) {
				return false;
			}
		}
		for (Location Y0 : y0) {
			if (!Y0.getBlock().isEmpty()) {
				return false;
			}
		}
		for (Location Y1 : y1) {
			if (!Y1.getBlock().isEmpty()) {
				return false;
			}
		}
		for (Location Y2 : y2) {
			if (!Y2.getBlock().isEmpty()) {
				return false;
			}
		}
		for (Location Y3 : y3) {
			if (!Y3.getBlock().isEmpty()) {
				return false;
			}
		}
		for (Location Y4 : y4) {
			if (!Y4.getBlock().isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public static final char[] schematic = { 
	'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'B', 'B', 'B', 'B', 'B', 'A', 'A',
	'B', 'B', 'B', 'B', 'B', 'A', 'A', 'B', 'B', 'B', 'B', 'B', 
	'A', 'A', 'B', 'B', 'B', 'B', 'B', 'A', 'A',
	'B', 'B', 'B', 'B', 'B', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A' };

	@SuppressWarnings("deprecation")
	public void buildTrampolim(final Player player) {
		if(!UtilBlock.isOnGround(player)) {
			player.sendMessage(ChatColor.RED + "Você precisa estar no chão!");
			UtilCooldown.setCooldown(player, this.gadgetName, 0);
			return;
		}
		if (!checkArea(player)) {
			player.sendMessage(ChatColor.RED + "Você precisa de mais espaço!");
			UtilCooldown.setCooldown(player, this.gadgetName, 0);
			return;
		}
		Location playerLocation = player.getLocation();
		Direction direction = Direction.getCardinalDirection(player);
		Location corner = playerLocation.clone().add(-4.0D, 1.0D, -4.0D);
		Location line = corner.clone();
		Location location = corner.clone();
		List<Block> blocks = new ArrayList<Block>();
		for (int i = 0; i < schematic.length; i++) {
			if (i % 7 == 0) {
				location = line.add(1.0D, 0.0D, 0.0D).clone();
			}
			location.add(0.0D, 0.0D, 1.0D);
			if (i == 24) {
				this.playerTrampoline.put(player, location.clone().add(0.0D, 1.0D, 0.0D));
			}
			char type = schematic[i];
			Block block = location.getBlock();
			block.setType(Material.WOOL);
			block.setData(type == 'A' ? DyeColor.BLUE.getWoolData() : DyeColor.BLACK.getWoolData());
			blocks.add(block);
		}
		Location blockLocation = playerLocation.clone().add(0.0D, 1.0D, 0.0D);
		Block firstStair = blockLocation.add(direction == Direction.SOUTH ? -5 : direction == Direction.NORTH ? 5 : 0,
				-1.0D, direction == Direction.WEST ? -5 : direction == Direction.EAST ? 5 : 0).getBlock();
		Block secondStair = blockLocation.add(direction == Direction.SOUTH ? 1 : direction == Direction.NORTH ? -1 : 0,
				1.0D, direction == Direction.WEST ? 1 : direction == Direction.EAST ? -1 : 0).getBlock();
		firstStair.setType(Material.WOOD_STAIRS);
		firstStair.setData((byte) (direction == Direction.WEST ? 2
				: direction == Direction.EAST ? 3 : direction == Direction.NORTH ? 1 : 0));
		secondStair.setType(Material.WOOD_STAIRS);
		secondStair.setData((byte) (direction == Direction.WEST ? 2
				: direction == Direction.EAST ? 3 : direction == Direction.NORTH ? 1 : 0));
		blocks.add(firstStair);
		blocks.add(secondStair);
		player.teleport(playerLocation.add(0.0D, 2.0D, 0.0D));
		this.playerBlocks.put(player, blocks);
		new BukkitRunnable() {
			int step = 0;

			@Override
			public void run() {
				if (!player.isOnline()) {
					breakTrampolim(player);
				}
				++step;
				if (step == UltraGadgets.getCfg().trampolimTempo || Gadgets.getPlayerGadget(player) != Gadgets.TRAMPOLIM) {
					cancel();
					breakTrampolim(player);
				}
			}
		}.runTaskTimer(UltraGadgets.get(), 0, 20);
	}

	@SuppressWarnings("deprecation")
	public void breakTrampolim(Player player) {
		List<Block> blocks = playerBlocks.remove(player);
		if (blocks != null) {
			for (Block block : blocks) {
				player.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getTypeId());
				block.setType(Material.AIR);
			}
			playerTrampoline.remove(player);
			tOwner.remove(player);
		} else {
			playerBlocks.remove(player);
			_tBlocks.remove(player);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void update(SchedulerEvent e) {
		if (e.getType() == SchedulerType.TICK) {
			for (Player localPlayer : Bukkit.getOnlinePlayers()) {
				Block localBlock = localPlayer.getWorld().getBlockAt(localPlayer.getLocation().add(0.0D, -1.0D, 0.0D));
				if ((localBlock.getType() == Material.WOOL) && (localBlock.getData() == (byte) 15)) {
					localPlayer.setVelocity(new Vector(0.0D, UtilMath.randomRange(1.5, 4.2), 0.0D));
				}
			}
		}
	}

	@Override
	void onInteract(Player p) {
		buildTrampolim(p);
	}

	@Override
	void onCooldown(Player p) {
		long cooldown = UtilCooldown.getCooldown(p, this.gadgetName) / 1000;
		UtilTitle title = new UtilTitle(
				UltraGadgets.getCfg().title.replaceAll("<cooldown>", String.valueOf(cooldown)).replaceAll("<gadget>",
						Gadgets.getPlayerGadget(p).toString().toLowerCase().replaceAll("_", "")),
				UltraGadgets.getCfg().subtitle.replaceAll("<cooldown>", String.valueOf(cooldown))
						.replaceAll("<gadget>", Gadgets.getPlayerGadget(p).toString().toLowerCase().replaceAll("_", ""))
						.replaceAll("&", "§"),
				6, 8, 6);
		title.setTimingsToTicks();
		title.send(p);
	}
}

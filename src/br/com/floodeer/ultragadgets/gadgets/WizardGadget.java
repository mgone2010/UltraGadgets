package br.com.floodeer.ultragadgets.gadgets;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.google.common.collect.Maps;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.util.ItemFactory;
import br.com.floodeer.ultragadgets.util.ParticleEffect;
import br.com.floodeer.ultragadgets.util.UtilBlock;
import br.com.floodeer.ultragadgets.util.UtilCooldown;
import br.com.floodeer.ultragadgets.util.UtilFirework;
import br.com.floodeer.ultragadgets.util.UtilLocations;
import br.com.floodeer.ultragadgets.util.UtilMath;
import br.com.floodeer.ultragadgets.util.UtilTitle;

public class WizardGadget extends Gadget implements Listener {

	enum WandType {
		EXPLOSIVE, LOVE, ICE;
	}

	Map<Player, WandType> type = Maps.newHashMap();

	public WizardGadget() {
		super(UltraGadgets.getCfg().wizardCooldown * 1000, Gadgets.WIZARD.toString(), Gadgets.WIZARD,
				Material.IRON_HOE);
	}

	@SuppressWarnings("deprecation")
	private void spawnFallingBlocks(final Location loc) {
		new BukkitRunnable() {
			int i = 1;

			@Override
			public void run() {
				if (i == 6) {
					cancel();
				}
				for (Block b : UtilLocations.getBlocksInRadius(loc, i, true)) {
					if (b.getType() != Material.AIR && b.getRelative(BlockFace.UP).getType() == Material.AIR) {
						FallingBlock fb = loc.getWorld().spawnFallingBlock(b.getLocation().clone().add(0, 1.1f, 0),
								b.getType(), b.getData());
						fb.setVelocity(new Vector(0, UtilMath.randomRange(0.7, 1.4), 0));
						fb.setDropItem(false);
						fb.setMetadata("ugGadget", new FixedMetadataValue(UltraGadgets.get(), null));
					}
				}
				++i;
			}
		}.runTaskTimer(UltraGadgets.get(), 0, 1);
	}
	
	private void setIce(Location loc) {
		for (Block b : UtilLocations.getBlocksInRadius(loc, (int)UtilMath.randomRange(4, 7), false)) {
			if(UtilBlock.fullSolid(b))
			UtilBlock.setToRestore(b, Material.ICE, (byte)0, 5*20);
		}
	}

	@Override
	void onInteract(final Player p) {
		final Location loc = p.getLocation().clone();
		final Vector dir = loc.getDirection().normalize();
		if(!type.containsKey(p) || type.containsKey(p) && type.get(p) == WandType.EXPLOSIVE) {
			new BukkitRunnable() {
				double t = 0;

				@Override
				public void run() {
					t += 0.5;
					if (Gadgets.getPlayerGadget(p) != Gadgets.WIZARD) {
						cancel();
						return;
					}
					t = t + Math.PI / 8;
					double x = dir.getX() * t;
					double y = dir.getY() * t + 1.5;
					double z = dir.getZ() * t;
					loc.add(x, y, z);
					ParticleEffect.FLAME.display(0, 0, 0, 1, 1, loc, 120 * 5);
					ParticleEffect.DRIP_LAVA.display(0, 0, 0, 1, 20, loc, 120 * 5);
					ParticleEffect.LAVA.display(0, 0, 0, 1, 3, loc, 120 * 5);
					if (UtilBlock.solid(loc.getBlock())) {
						cancel();
						ParticleEffect.EXPLOSION_NORMAL.display(0, 0, 0, 1, 120, loc, 100 * 3);
						loc.getWorld().playSound(loc, Sound.EXPLODE, 2.0F, 1.0F);
						spawnFallingBlocks(loc);
						return;
					}
					loc.subtract(x, y, z);
					if (t > Math.PI * 20) {
						this.cancel();
					}
				}
			}.runTaskTimer(UltraGadgets.get(), 0, 1);
		}else if(type.containsKey(p) && type.get(p) == WandType.LOVE) {
			new BukkitRunnable() {
				double t = 0;

				@Override
				public void run() {
					t += 0.5;
					if (Gadgets.getPlayerGadget(p) != Gadgets.WIZARD) {
						cancel();
						return;
					}
					t = t + Math.PI / 8;
					double x = dir.getX() * t;
					double y = dir.getY() * t + 1.5;
					double z = dir.getZ() * t;
					loc.add(x, y, z);
					ParticleEffect.HEART.display(0, 0, 0, 1, 1, loc, 120 * 5);
					ParticleEffect.FIREWORKS_SPARK.display(0, 0, 0, 0.1F, 38, loc, 120 * 5);
					if (UtilBlock.solid(loc.getBlock())) {
						cancel();
						UtilFirework.playInstantFirework(loc,FireworkEffect.builder().with(Type.BALL).withColor(Color.RED).build());
						spawnFallingBlocks(loc);
						loc.getWorld().playSound(loc, Sound.EXPLODE, 2.0F, 1.0F);
						return;
					}
					loc.subtract(x, y, z);
					if (t > Math.PI * 20) {
						this.cancel();
					}
				}
			}.runTaskTimer(UltraGadgets.get(), 0, 1);
		}else if(type.containsKey(p) && type.get(p) == WandType.ICE) {
			new BukkitRunnable() {
				double t = 0;

				@Override
				public void run() {
					t += 0.5;
					if (Gadgets.getPlayerGadget(p) != Gadgets.WIZARD) {
						cancel();
						return;
					}
					t = t + Math.PI / 8;
					double x = dir.getX() * t;
					double y = dir.getY() * t + 1.5;
					double z = dir.getZ() * t;
					loc.add(x, y, z);
					ParticleEffect.CLOUD.display(0, 0, 0, 0.3F, 2, loc, 120 * 5);
					ParticleEffect.SNOWBALL.display(0, 0, 0, 1, 25, loc, 120 * 5);
					ParticleEffect.DRIP_WATER.display(0, 0, 0, 1, 25, loc, 120 * 5);
					if (UtilBlock.solid(loc.getBlock())) {
						cancel();
						UtilFirework.playInstantFirework(loc,FireworkEffect.builder().with(Type.BALL).withColor(Color.RED).build());
					    setIce(loc);
						loc.getWorld().playSound(loc, Sound.EXPLODE, 2.0F, 1.0F);
						return;
					}
					loc.subtract(x, y, z);
					if (t > Math.PI * 20) {
						this.cancel();
					}
				}
			}.runTaskTimer(UltraGadgets.get(), 0, 1);
		}
	}

	@Override
	void onCooldown(Player p) {
		long cooldown = UtilCooldown.getCooldown(p, this.gadgetName) / 1000;
		UtilTitle title = new UtilTitle(
		UltraGadgets.getCfg().title.replaceAll("<cooldown>", String.valueOf(cooldown)).replaceAll("<gadget>",
		Gadgets.getPlayerGadget(p).toString().toLowerCase().replaceAll("_", "")),
		UltraGadgets.getCfg().subtitle.replaceAll("<cooldown>", String.valueOf(cooldown)).replaceAll("<gadget>",
		Gadgets.getPlayerGadget(p).toString().toLowerCase().replaceAll("_", "")).replaceAll("&", "§"),
		6, 8, 6);
		title.setTimingsToTicks();
		title.send(p);
	}

	@EventHandler
	public void onBlockChangeState(EntityChangeBlockEvent event) {
		if (event.getEntity() instanceof FallingBlock) {
			if (event.getEntity().hasMetadata("ugGadget")) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() != Action.LEFT_CLICK_BLOCK) {
			return;
		}
		if (Gadgets.hasGadgetSelected(e.getPlayer(), Gadgets.WIZARD) && ItemFactory.isHoe(e.getItem())) {
			if(!type.containsKey(e.getPlayer())) {
				type.put(e.getPlayer(), WandType.LOVE);
			}else{
				String copy = ChatColor.translateAlternateColorCodes('&',  UltraGadgets.getCfg().wizardNome);
				if(type.get(e.getPlayer()) == WandType.EXPLOSIVE) {
					type.put(e.getPlayer(), WandType.LOVE);
					ItemFactory.setName(e.getItem(), copy + " §7- Amor");
				}else if(type.get(e.getPlayer()) == WandType.LOVE) {
					type.put(e.getPlayer(), WandType.ICE);
					ItemFactory.setName(e.getItem(), copy + " §7- Congelamento");
				}else if(type.get(e.getPlayer()) == WandType.ICE) {
					type.put(e.getPlayer(), WandType.EXPLOSIVE);
					ItemFactory.setName(e.getItem(), copy + " §7- Explosão");
				}
			}
			e.getPlayer().updateInventory();
		}
	}
}

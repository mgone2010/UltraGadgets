package br.com.floodeer.ultragadgets.gadgets;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.util.ParticleEffect;
import br.com.floodeer.ultragadgets.util.UtilBlock;
import br.com.floodeer.ultragadgets.util.UtilCooldown;
import br.com.floodeer.ultragadgets.util.UtilLocations;
import br.com.floodeer.ultragadgets.util.UtilMath;
import br.com.floodeer.ultragadgets.util.UtilTitle;
import br.com.floodeer.ultragadgets.util.UtilVelocity;

public class BombGadget extends Gadget implements Listener {

	public BombGadget() {
		super(UltraGadgets.getCfg().bombCooldown * 1000, Gadgets.BOMBA.toString().toLowerCase(), Gadgets.BOMBA, Material.CLAY_BALL);
	}
	@Override
	void onInteract(final Player p) {
		ItemStack localBomba = new ItemStack(Material.CLAY_BALL);
		final Item localItemDrop = p.getWorld().dropItem(p.getLocation(), localBomba);
		localItemDrop.setVelocity(p.getEyeLocation().getDirection().multiply(1.2D).normalize());
		localItemDrop.setPickupDelay(Integer.MAX_VALUE);
		long run = 12;
	    final int i = new BukkitRunnable() {
			
			@Override
			public void run() {
				if(Gadgets.getPlayerGadget(p) != Gadgets.BOMBA || !p.isOnline()) {
					localItemDrop.remove();
					cancel();
					return;
				}
				localItemDrop.getWorld().playSound(localItemDrop.getLocation(), Sound.BURP, 2.5F, 12);
			}
		}.runTaskTimer(UltraGadgets.get(), 20, --run).getTaskId();

		Bukkit.getScheduler().scheduleSyncDelayedTask(UltraGadgets.get(), new Runnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				if(Gadgets.getPlayerGadget(p) != Gadgets.BOMBA || !p.isOnline()) {
					return;
				}
			    Bukkit.getScheduler().cancelTask(i);
				ParticleEffect.EXPLOSION_HUGE.display(3.0F, 1.0F, 3.0F, 3.0F, 18, localItemDrop.getLocation(), 50.0D);
				localItemDrop.getWorld().playSound(localItemDrop.getLocation(), Sound.EXPLODE, 5.0F, 1.0F);
				for (Player p : UtilLocations.getNearbyPlayers(localItemDrop.getLocation(), 8.5)) {
					UtilVelocity.knockback(p, localItemDrop.getLocation(), 1.8, 1.5, true);
				}
				byte b = (byte) UtilMath.random.nextInt(15);
				Location localLocation = localItemDrop.getLocation();
				for (Block localBlock : UtilBlock.getInRadius(localLocation, 8.5D).keySet()) {
					if (UtilBlock.solid(localBlock)) {
						if (!UtilBlock.blocksToRestore.containsKey(localBlock)) {
							if (localBlock.getType() != Material.getMaterial(159)) {
								UtilBlock.setToRestore(localBlock, Material.getMaterial(159), b, 8 * 20);
							}
						}
					}
				}
				localItemDrop.remove();
			}
		}, 8 * 20L);
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
}

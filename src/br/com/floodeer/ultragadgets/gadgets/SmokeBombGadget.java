package br.com.floodeer.ultragadgets.gadgets;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.util.ItemFactory;
import br.com.floodeer.ultragadgets.util.ParticleEffect;
import br.com.floodeer.ultragadgets.util.UtilCooldown;
import br.com.floodeer.ultragadgets.util.UtilLocations;
import br.com.floodeer.ultragadgets.util.UtilTitle;

public class SmokeBombGadget extends Gadget implements Listener {
	
	public SmokeBombGadget() {
		super(UltraGadgets.getCfg().smokeBombCooldown *1000, Gadgets.SMOKE_BOMB.toString(), Gadgets.SMOKE_BOMB, Material.COAL_BLOCK);
	}

	private void play(final Player p) {
		final ItemStack item = ItemFactory.buildItemStack(Material.COAL_BLOCK, "CoalMeta");
		Vector v = p.getLocation().getDirection().multiply(0.9715437181D).normalize();
		final Item i  = p.getWorld().dropItem(p.getLocation().add(v), item);
		i.setVelocity(v);
		i.setPickupDelay(Integer.MAX_VALUE);
		new BukkitRunnable() {
			int prestep = 0;
			@Override
			public void run() {
				++prestep;
				for(Player players : UtilLocations.getNearbyPlayers(i.getLocation(), 5.0)) {
					players.playSound(i.getLocation(), Sound.FUSE, 3.0f, 1.0f);
				}
				if(prestep == 8 || Gadgets.getPlayerGadget(p) != Gadgets.SMOKE_BOMB) {
					cancel();
					if(Gadgets.getPlayerGadget(p) != Gadgets.SMOKE_BOMB) {
						return;
					}
					new BukkitRunnable() {
						int steps = 0;
						@Override
						public void run() {
							++steps;
							if(steps >= 35 || Gadgets.getPlayerGadget(p) != Gadgets.SMOKE_BOMB || !p.isOnline()) {
								i.remove();
								cancel();
								return;
							}
							if(!UltraGadgets.getCfg().nerfGadgetsLags)
							ParticleEffect.EXPLOSION_HUGE.display(2, 2, 2, 1.0f, 120, i.getLocation(), 120*3d);
							else
								ParticleEffect.EXPLOSION_HUGE.display(1, 1, 1, 1.0f, 120, i.getLocation(), 120d);
							i.getWorld().playSound(i.getLocation(), Sound.FIZZ, 3.0F, 1.0F);
						}
					}.runTaskTimer(UltraGadgets.get(), 0, 5);
					return;
				}
			}
		}.runTaskTimer(UltraGadgets.get(), 0, 20);
	}
	
	@Override
	void onInteract(Player p) {
		play(p);
	}

	@Override
	void onCooldown(Player p) {
		long cooldown = UtilCooldown.getCooldown(p, this.gadgetName) / 1000;
		UtilTitle title = new UtilTitle(
		UltraGadgets.getCfg().title.replaceAll("<cooldown>", String.valueOf(cooldown)).replaceAll("<gadget>",
		UltraGadgets.getCfg().smokeBombNome),
		UltraGadgets.getCfg().subtitle.replaceAll("<cooldown>", String.valueOf(cooldown)).replaceAll("<gadget>",
	    UltraGadgets.getCfg().smokeBombNome).replaceAll("&", "§"),
		6, 8, 6);
		title.setTimingsToTicks();
		title.send(p);
	}
}

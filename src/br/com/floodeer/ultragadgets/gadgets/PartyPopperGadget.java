package br.com.floodeer.ultragadgets.gadgets;


import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.util.ItemFactory;
import br.com.floodeer.ultragadgets.util.UtilCooldown;
import br.com.floodeer.ultragadgets.util.UtilMath;
import br.com.floodeer.ultragadgets.util.UtilTitle;

public class PartyPopperGadget extends Gadget implements Listener {

	public PartyPopperGadget() {
		super(UltraGadgets.getCfg().partyCooldown*1000, Gadgets.PARTY_POPPER.toString().toLowerCase(), Gadgets.PARTY_POPPER, Material.ENDER_CHEST);
	}

	@Override
	void onInteract(final Player p) {
		p.getWorld().playSound(p.getLocation(), Sound.CAT_MEOW, 0.5F, 1.0F);
		new BukkitRunnable() {
			int step = 0;
			@Override
			public void run() {
				if(!p.isOnline() || Gadgets.getPlayerGadget(p) != Gadgets.PARTY_POPPER) {
					cancel();
				}
				++step;
				if(step >= 12*20) {
					cancel();
				}
				final Item i = p.getWorld().dropItem(p.getLocation().add(0.0D, 1.59700000596046448D, 0.0D), ItemFactory.buildItemStackArrays(Material.WOOL, (byte)UtilMath.random.nextInt(15)));
				i.setPickupDelay(Integer.MAX_VALUE);
				i.setVelocity(new Vector(0.0D, 0.5D, 0.0D).add(UtilMath.getRandomCircleVector().multiply(0.1D)));
				i.getWorld().playSound(i.getLocation(), Sound.CHICKEN_EGG_POP, 0.5F, 1.0F);
				new BukkitRunnable() {
					@Override
					public void run() {
						i.remove();	
					}
				}.runTaskLater(UltraGadgets.get(), 20);
			}
		}.runTaskTimer(UltraGadgets.get(), 0, 1L);
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

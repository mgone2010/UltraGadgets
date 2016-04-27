package br.com.floodeer.ultragadgets.gadgets;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.util.UtilCooldown;
import br.com.floodeer.ultragadgets.util.UtilFirework;
import br.com.floodeer.ultragadgets.util.UtilMath;
import br.com.floodeer.ultragadgets.util.UtilTitle;

public class WitherShootGadget extends Gadget implements Listener{ 
	
	public WitherShootGadget() {
		super(UltraGadgets.getCfg().witherShootCooldown*1000, Gadgets.WITHER_SHOOT.toString().toLowerCase(), Gadgets.WITHER_SHOOT, Material.COAL);
	}

	@Override
	void onInteract(final Player p) {
		new BukkitRunnable() {
			int step = 0;
			@Override
			public void run() {
				++step;
				if(step == 10 || Gadgets.getPlayerGadget(p) != Gadgets.WITHER_SHOOT || !p.isOnline()) {
					cancel();
					return;
				}
				final WitherSkull ws = (WitherSkull)p.launchProjectile(WitherSkull.class);
		        ws.setVelocity(UtilMath.getRandomVector());
		        ws.setMetadata("ws", new FixedMetadataValue(UltraGadgets.get(), "ws"));
		        ws.setBounce(true);
		        new BukkitRunnable() {
					@Override
					public void run() {
						UtilFirework.playInstantFirework(ws.getLocation(), FireworkEffect.builder().withColor(Color.BLACK).withColor(Color.WHITE).with(FireworkEffect.Type.BALL_LARGE).build());
				        ws.remove();
					}
				}.runTaskLater(UltraGadgets.get(), 8);
				
			}
		}.runTaskTimer(UltraGadgets.get(), 0, 10);
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

	@EventHandler(priority = EventPriority.HIGHEST)
	public void explode(EntityExplodeEvent e) {
		if(e.getEntity().hasMetadata("ws")) {
			e.setCancelled(true);
		}
	}
}

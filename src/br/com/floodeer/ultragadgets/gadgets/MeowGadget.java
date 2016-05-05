package br.com.floodeer.ultragadgets.gadgets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.util.ParticleEffect;
import br.com.floodeer.ultragadgets.util.UtilCooldown;
import br.com.floodeer.ultragadgets.util.UtilTitle;

public class MeowGadget extends Gadget implements Listener {

	public MeowGadget() {
		super(UltraGadgets.getCfg().meowCooldown * 1000, "Meow", Gadgets.FUN_GUN, Material.BLAZE_ROD);
	}
	
	List<Player> launched = new ArrayList<>();

	@Override
	void onInteract(Player p) {
		EnderPearl enderpearl = (EnderPearl) p.launchProjectile(EnderPearl.class);
		Snowball snow = (Snowball) p.launchProjectile(Snowball.class);
		enderpearl.setMetadata("meow", new FixedMetadataValue(UltraGadgets.get(), null));
		snow.setMetadata("meow", new FixedMetadataValue(UltraGadgets.get(), null));
		launched.add(p);
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

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void localProject(ProjectileHitEvent localProjectHit) {
		if ((localProjectHit.getEntity() instanceof EnderPearl)) {
			EnderPearl localEnder = (EnderPearl) localProjectHit.getEntity();
			if (localEnder.hasMetadata("meow")) {
				Location localLocation = localEnder.getLocation();

				ParticleEffect.FLAME.display(0.0F, 0.0F, 0.0F, 1.0F, 5, localLocation, 20.0D);

				ParticleEffect.HEART.display(0.0F, 0.0F, 0.0F, 1.0F, 5, localLocation, 20.0D);

				ParticleEffect.SMOKE_NORMAL.display(0.0F, 0.0F, 0.0F, 1.0F, 5, localLocation, 20.0D);

				ParticleEffect.SMOKE_LARGE.display(0.0F, 0.0F, 0.0F, 1.0F, 5, localLocation, 20.0D);

				ParticleEffect.CLOUD.display(0.0F, 0.0F, 0.0F, 1.0F, 5, localLocation, 20.0D);

				ParticleEffect.LAVA.display(0.0F, 0.0F, 0.0F, 1.0F, 5, localLocation, 20.0D);

				localLocation.getWorld().playSound(localLocation.getBlock().getLocation(), Sound.CAT_MEOW, 2.0F, 1.0F);
				localLocation.getWorld().playSound(localLocation.getBlock().getLocation(), Sound.WOLF_GROWL, 2.0F,1.0F);
			}
		}
		if ((localProjectHit.getEntity() instanceof Snowball)) {
			Snowball snows = (Snowball) localProjectHit.getEntity();
			if (snows.hasMetadata("meow")) {
				Location localLocation = snows.getLocation();

				ParticleEffect.FLAME.display(0.0F, 0.0F, 0.0F, 1.0F, 5, localLocation, 20.0D);

				ParticleEffect.HEART.display(0.0F, 0.0F, 0.0F, 1.0F, 5, localLocation, 20.0D);

				ParticleEffect.SMOKE_NORMAL.display(0.0F, 0.0F, 0.0F, 1.0F, 5, localLocation, 20.0D);

				ParticleEffect.SMOKE_LARGE.display(0.0F, 0.0F, 0.0F, 1.0F, 5, localLocation, 20.0D);

				ParticleEffect.CLOUD.display(0.0F, 0.0F, 0.0F, 1.0F, 5, localLocation, 20.0D);

				ParticleEffect.LAVA.display(0.0F, 0.0F, 0.0F, 1.0F, 5, localLocation, 20.0D);

				localLocation.getWorld().playSound(localLocation.getBlock().getLocation(), Sound.CAT_MEOW, 2.0F, 1.0F);

				localLocation.getWorld().playSound(localLocation.getBlock().getLocation(), Sound.WOLF_BARK, 2.0F, 1.0F);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void playerTeleport(final PlayerTeleportEvent e) {
		if(e.getCause().equals(TeleportCause.ENDER_PEARL) && launched.contains(e.getPlayer())) {
			e.setCancelled(true);
			new BukkitRunnable() {
				
				@Override
				public void run() {
					launched.remove(e.getPlayer());				
				}
			}.runTaskLater(UltraGadgets.get(), 20l);
			
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Snowball || e.getDamager() instanceof EnderPearl) {
			if(e.getDamager().hasMetadata("meow")) {
				e.setCancelled(true);
			}
		}
	}
}

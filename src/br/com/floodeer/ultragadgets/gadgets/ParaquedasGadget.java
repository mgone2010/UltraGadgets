package br.com.floodeer.ultragadgets.gadgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.scheduler.SchedulerEvent;
import br.com.floodeer.ultragadgets.util.ParticleEffect;
import br.com.floodeer.ultragadgets.util.UtilBlock;
import br.com.floodeer.ultragadgets.util.UtilCooldown;
import br.com.floodeer.ultragadgets.util.UtilLocations;
import br.com.floodeer.ultragadgets.util.UtilMath;
import br.com.floodeer.ultragadgets.util.UtilTitle;

public class ParaquedasGadget extends Gadget implements Listener {

	List<Player> players = new ArrayList<>();

	public ParaquedasGadget() {
		super(UltraGadgets.getCfg().paraquedas * 1000, Gadgets.PARAQUEDAS.toString().toLowerCase(), Gadgets.PARAQUEDAS,
				Material.LEASH);
	}

	@Override
	void onInteract(final Player p) {
		Location corner1 = p.getLocation().clone().add(3, 80, 3);
		Location corner2 = p.getLocation();
		if (UtilLocations.checkEmptyArea(corner1, corner2)) {
			players.add(p);
			p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 3.0F, 0.0F);
			ParticleEffect.EXPLOSION_HUGE.display(0.0F, 0.0F, 0.0F, 1.0F, 1, p.getLocation(), 180);
			p.setVelocity(new Vector(0, 120, 0));
			new BukkitRunnable() {
				@Override
				public void run() {
					for (int i = 0; i <= 10; i++) {
						final Chicken localChicken1 = (Chicken) p.getWorld().spawnEntity(p.getLocation().clone().add(UtilMath.randomRange(-1.7F, 1.7F), 0.0D, UtilMath.randomRange(-1.7F, 1.7F)),EntityType.CHICKEN);
						localChicken1.setLeashHolder(p);
						localChicken1.setMetadata("PARAQUEDAS", new FixedMetadataValue(UltraGadgets.get(), null));
					}
					p.setFallDistance(0.0F);
					p.setVelocity(new Vector(0, -0.2, 0));
					Bukkit.getScheduler().runTaskTimer(UltraGadgets.get(), new Runnable() {
						public void run() {
							if (!UtilBlock.isOnGround(p) || p.isOnline() || Gadgets.getPlayerGadget(p) != Gadgets.PARAQUEDAS || !p.isDead()) return;
							Iterator<Entity> localIterator = p.getWorld().getEntities().iterator();
							while (localIterator.hasNext()) {
								Entity localEntity = (Entity) localIterator.next();
								if ((localEntity instanceof Chicken)) {
									Chicken localChicken = (Chicken) localEntity;
									if ((localChicken.isLeashed()) && (localChicken.getLeashHolder() == p)) {
										localChicken.remove();
									}
								}
							}
							players.remove(p);
						}
					}, 0, 12L);
				}
			}.runTaskLater(UltraGadgets.get(), 38);
		} else {
			p.sendMessage(ChatColor.RED + "Você precisa de mais espaço!");
		}
	}

	@Override
	void onCooldown(Player p) {
		for (Entity localEntity : p.getWorld().getEntities()) {
			if ((localEntity instanceof Chicken)) {
				Chicken paramChicken = (Chicken) localEntity;
				if ((paramChicken.isLeashed() && paramChicken.getLeashHolder() == p && paramChicken.isValid())) {
					ParticleEffect.SMOKE_NORMAL.display(0.0F, 0.0F, 0.0F, 0.0F, 0, paramChicken.getLocation(), 10.0D);
					paramChicken.remove();
				}
			}
		}
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

	@EventHandler
	public void onUnleash(EntityUnleashEvent e) {
		if (e.getEntity().hasMetadata("PARAQUEDAS")) {
			Chicken localChicken = (Chicken) e.getEntity();
			localChicken.setLeashHolder(null);
			ParticleEffect.SMOKE_NORMAL.display(0.0F, 0.0F, 0.0F, 0.0F, 0, localChicken.getLocation(), 10.0D);
			e.getEntity().remove();
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent paramPlayerQuitEvent) {
		Player paramPlayer = paramPlayerQuitEvent.getPlayer();
		for (Entity localEntity : paramPlayer.getWorld().getEntities()) {
			if ((localEntity instanceof Chicken)) {
				Chicken localChicken = (Chicken) localEntity;
				if ((localChicken.isLeashed()) && (localChicken.getLeashHolder() == paramPlayer)
						&& (localChicken.hasMetadata("PARAQUEDAS"))) {
					ParticleEffect.SMOKE_NORMAL.display(0.0F, 0.0F, 0.0F, 0.0F, 0, localChicken.getLocation(), 10.0D);
					localChicken.remove();
				}
			}
		}
	}

	@EventHandler
	public void onUpdate(SchedulerEvent paramUpdateEvent) {
		for (Player paramPlayer : Bukkit.getOnlinePlayers()) {
			for (Entity localEntity : paramPlayer.getWorld().getEntitiesByClass(Chicken.class)) {
				Chicken localChicken = (Chicken) localEntity;
				if ((localChicken.isLeashed()) && (localChicken.getLeashHolder() == paramPlayer)
						&& (paramPlayer.getWorld().getBlockAt(paramPlayer.getLocation().add(0.0D, -1.0D, 0.0D))
								.getType() != Material.AIR))
					localChicken.remove();
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent paramEntityDamageEvent) {
		if (((paramEntityDamageEvent.getEntity() instanceof Chicken))
				&& (paramEntityDamageEvent.getEntity().hasMetadata("PARAQUEDAS"))) {
			paramEntityDamageEvent.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerDamage(EntityDamageEvent e) {
		if (((e.getEntity() instanceof Player)) && (e.getCause() == EntityDamageEvent.DamageCause.FALL)) {
			Player paramPlayer = (Player) e.getEntity();
			if (players.contains(paramPlayer))
				e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerMove(PlayerMoveEvent paramPlayerMoveEvent) {
		Player localPlayer = paramPlayerMoveEvent.getPlayer();
		if ((localPlayer.getFallDistance() > 0.0F) && (localPlayer.getVelocity().getY() < -0.1000000014901161D)) {
			for (Entity localEntity : localPlayer.getNearbyEntities(20.0D, 20.0D, 40.0D)) {
				if ((localEntity instanceof Chicken)) {
					Chicken localChicken = (Chicken) localEntity;
					if ((localChicken.isLeashed()) && (localChicken.getLeashHolder() == localPlayer)) {
						if (localChicken.getLocation().getY() > localPlayer.getLocation().getY() + 2.0D) {
							localPlayer.setVelocity(localPlayer.getVelocity().add(new Vector(0.0D, 0.01D, 0.0D)));
							localPlayer.setFallDistance(0.0F);
							if (localPlayer.getVelocity().getY() > -0.1000000014901161D) {
								localPlayer.setVelocity(localPlayer.getVelocity().setY(-0.1F));
							}
						}
					}
				}
			}
		}
	}
}

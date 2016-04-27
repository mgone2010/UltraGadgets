package br.com.floodeer.ultragadgets.gadgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.google.common.collect.Maps;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.util.UtilCooldown;
import br.com.floodeer.ultragadgets.util.UtilLocations;
import br.com.floodeer.ultragadgets.util.UtilMath;
import br.com.floodeer.ultragadgets.util.UtilParticle;
import br.com.floodeer.ultragadgets.util.UtilParticle.ParticleType;
import br.com.floodeer.ultragadgets.util.UtilTitle;

public class VectorTNTGadget extends Gadget implements Listener {
	
	public VectorTNTGadget() {
		super(UltraGadgets.getCfg().vectorTNTCooldown * 1000, Gadgets.VECTORTNT.toString(), Gadgets.VECTORTNT, Material.TNT);
	}
	
	Map<TNTPrimed, Player> localMap = Maps.newHashMap();
	List<TNTPrimed> tntPrimed = new ArrayList<>();

	@Override
	void onInteract(Player p) {
		TNTPrimed localTNT = (TNTPrimed) p.getWorld().spawn(p.getLocation(), TNTPrimed.class);
		localTNT.setMetadata("ugadgets", new FixedMetadataValue(UltraGadgets.get(), null));
		localTNT.setVelocity(p.getLocation().getDirection().multiply(2.0));
		localTNT.setIsIncendiary(false);
		localMap.put(localTNT, p);
		tntPrimed.add(localTNT);
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
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityExplode(final EntityExplodeEvent e) {
		if(e.getEntity() instanceof TNTPrimed) {	
			if(tntPrimed.contains(e.getEntity())) {
				e.setCancelled(true);
				tntPrimed.remove(e.getEntity());
			}
			final TNTPrimed localTNT = (TNTPrimed)e.getEntity();
			if(localTNT.hasMetadata("LocalData")) {
				e.setCancelled(true);
				new UtilParticle(ParticleType.SMOKE_NORMAL, 0.0, 1, 0.0).sendToLocation(localTNT.getLocation());
				new UtilParticle(ParticleType.EXPLOSION_LARGE, 0.0D, 2, 0.0D).sendToLocation(localTNT.getLocation());
			}
			if(localTNT.hasMetadata("ugadgets")) {
				final Player p = localMap.get(localTNT);
				new UtilParticle(ParticleType.EXPLOSION_HUGE, 0.0D, 2, 0.0D).sendToLocation(localTNT.getLocation());
				Iterator<LivingEntity> nearbyIterator = UtilLocations.getNearbyEntitiesArrays(localTNT.getLocation(), 7).iterator();
				while(nearbyIterator.hasNext()) {
					LivingEntity ent = (LivingEntity)nearbyIterator.next();
					if(!ent.hasMetadata("NPC") && !ent.hasMetadata("ugpets")) {
						Vector vec = UtilMath.getRandomVector().multiply(0.8);
						ent.setVelocity(vec);
					}
				}
				e.setCancelled(true);
				int runner = 1;
				if(UltraGadgets.getCfg().nerfGadgetsLags) {
					runner = 5;
				}
				new BukkitRunnable() {
					int step = 0;
					@Override
					public void run() {
						++step;
						int timeToCancel = 80;
						if(UltraGadgets.getCfg().nerfGadgetsLags) {
							timeToCancel = 15;
						}
						if(step >= timeToCancel || Gadgets.getPlayerGadget(p) != Gadgets.VECTORTNT || !p.isOnline()) {
							cancel();
							localMap.remove(p);
							if(step != timeToCancel) {
								for(Entity tnts : e.getLocation().getWorld().getEntitiesByClass(TNTPrimed.class)) {
									if(tnts.hasMetadata("LocalData")) {
										tnts.remove();
									}
								}
							}
 						}		
						TNTPrimed newTNT = e.getEntity().getWorld().spawn(localTNT.getLocation(), TNTPrimed.class);
					    newTNT.setVelocity(new Vector(0.0D, UtilMath.randomRange(0.8, 1.6), 0.0D).add(UtilMath.getRandomCircleVector().multiply(0.5D)));
					    newTNT.setMetadata("LocalData", new FixedMetadataValue(UltraGadgets.get(), null));
					    tntPrimed.add(newTNT);
					}
				}.runTaskTimer(UltraGadgets.get(), 0, runner);
			}
		}
	}
}

package br.com.floodeer.ultragadgets.particles;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import br.com.floodeer.ultragadgets.enumeration.Particle;
import br.com.floodeer.ultragadgets.scheduler.SchedulerEvent;
import br.com.floodeer.ultragadgets.scheduler.SchedulerType;
import br.com.floodeer.ultragadgets.util.UtilParticle;

public class RorationParticles implements Listener {

	double radialsPerStep = 0.19634954084936207D;
	float radius = 0.4F;
	float step = 0.0F;

	@EventHandler
	public void onUpdate(SchedulerEvent paramUpdateEvent) {
		if (paramUpdateEvent.getType() == SchedulerType.TICK) {
			for (Player localPlayer : Particle.entrySet()) {
				if (Particle.hasParticle(localPlayer, Particle.DRIP_WATER)) {
					if (localPlayer.isValid()) {
						Vector localVector = new Vector(Math.cos(this.radialsPerStep * this.step) * this.radius, 0.0D, Math.sin(this.radialsPerStep * this.step) * this.radius);
						Location localLocation1 = (Location) localPlayer.getLocation();
						Location localLocation2 = new Location(localPlayer.getWorld(), localLocation1.getX(),localLocation1.getY() + 2.0D, localLocation1.getZ());
						localLocation2.add(localVector);
						new UtilParticle(UtilParticle.ParticleType.DRIP_WATER, 0.0D, 1, 1.0E-4D).sendToLocation(localLocation2);
						localLocation2.subtract(localVector);
					}
				}else if(Particle.hasParticle(localPlayer, Particle.DRIP_LAVA)) {
					if (localPlayer.isValid()) {
						Vector localVector = new Vector(Math.cos(this.radialsPerStep * this.step) * this.radius, 0.0D, Math.sin(this.radialsPerStep * this.step) * this.radius);
						Location localLocation1 = (Location) localPlayer.getLocation();
						Location localLocation2 = new Location(localPlayer.getWorld(), localLocation1.getX(),localLocation1.getY() + 2.0D, localLocation1.getZ());
						localLocation2.add(localVector);
						new UtilParticle(UtilParticle.ParticleType.DRIP_LAVA, 0.0D, 1, 1.0E-4D).sendToLocation(localLocation2);
						localLocation2.subtract(localVector);
					}
				}else if(Particle.hasParticle(localPlayer, Particle.FLAMES)) {
					if (localPlayer.isValid()) {
						Vector localVector = new Vector(Math.cos(this.radialsPerStep * this.step) * this.radius, 0.0D, Math.sin(this.radialsPerStep * this.step) * this.radius);
						Location localLocation1 = (Location) localPlayer.getLocation();
						Location localLocation2 = new Location(localPlayer.getWorld(), localLocation1.getX(),localLocation1.getY() + 2.0D, localLocation1.getZ());
						localLocation2.add(localVector);
						new UtilParticle(UtilParticle.ParticleType.FLAME, 0.0D, 1, 1.0E-4D).sendToLocation(localLocation2);
						localLocation2.subtract(localVector);
					}
				}else if(Particle.hasParticle(localPlayer, Particle.HAPPY_VILLAGER)) {
					if (localPlayer.isValid()) {
						Vector localVector = new Vector(Math.cos(this.radialsPerStep * this.step) * this.radius, 0.0D, Math.sin(this.radialsPerStep * this.step) * this.radius);
						Location localLocation1 = (Location) localPlayer.getLocation();
						Location localLocation2 = new Location(localPlayer.getWorld(), localLocation1.getX(),localLocation1.getY() + 2.0D, localLocation1.getZ());
						localLocation2.add(localVector);
						new UtilParticle(UtilParticle.ParticleType.VILLAGER_HAPPY, 0.0D, 1, 1.0E-4D).sendToLocation(localLocation2);
						localLocation2.subtract(localVector);
					}
				}else if(Particle.hasParticle(localPlayer, Particle.ANGRY_VILLAGER)) {
					if (localPlayer.isValid()) {
						Vector localVector = new Vector(Math.cos(this.radialsPerStep * this.step) * this.radius, 0.0D, Math.sin(this.radialsPerStep * this.step) * this.radius);
						Location localLocation1 = (Location) localPlayer.getLocation();
						Location localLocation2 = new Location(localPlayer.getWorld(), localLocation1.getX(),localLocation1.getY() + 2.0D, localLocation1.getZ());
						localLocation2.add(localVector);
						new UtilParticle(UtilParticle.ParticleType.VILLAGER_ANGRY, 0.0D, 1, 1.0E-4D).sendToLocation(localLocation2);
						localLocation2.subtract(localVector);
					}
				}else if(Particle.hasParticle(localPlayer, Particle.FIREWORK)) {
					if (localPlayer.isValid()) {
						Vector localVector = new Vector(Math.cos(this.radialsPerStep * this.step) * this.radius, 0.0D, Math.sin(this.radialsPerStep * this.step) * this.radius);
						Location localLocation1 = (Location) localPlayer.getLocation();
						Location localLocation2 = new Location(localPlayer.getWorld(), localLocation1.getX(),localLocation1.getY() + 2.0D, localLocation1.getZ());
						localLocation2.add(localVector);
						new UtilParticle(UtilParticle.ParticleType.FIREWORKS_SPARK, 0.0D, 1, 1.0E-4D).sendToLocation(localLocation2);
						localLocation2.subtract(localVector);
					}
				}else if(Particle.hasParticle(localPlayer, Particle.HEART)) {
					if (localPlayer.isValid()) {
						Vector localVector = new Vector(Math.cos(this.radialsPerStep * this.step) * this.radius, 0.0D, Math.sin(this.radialsPerStep * this.step) * this.radius);
						Location localLocation1 = (Location) localPlayer.getLocation();
						Location localLocation2 = new Location(localPlayer.getWorld(), localLocation1.getX(),localLocation1.getY() + 2.0D, localLocation1.getZ());
						localLocation2.add(localVector);
						new UtilParticle(UtilParticle.ParticleType.HEART, 0.0D, 1, 1.0E-4D).sendToLocation(localLocation2);
						localLocation2.subtract(localVector);
					}
				}
			}
		}
		 this.step += 1.0F;
	}
}

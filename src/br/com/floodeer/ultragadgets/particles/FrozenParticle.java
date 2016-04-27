package br.com.floodeer.ultragadgets.particles;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import br.com.floodeer.ultragadgets.enumeration.Particle;
import br.com.floodeer.ultragadgets.listener.PlayerLocationListener;
import br.com.floodeer.ultragadgets.scheduler.SchedulerEvent;
import br.com.floodeer.ultragadgets.scheduler.SchedulerType;
import br.com.floodeer.ultragadgets.util.UtilParticle;
import br.com.floodeer.ultragadgets.util.UtilParticle.ParticleType;
import br.com.floodeer.ultragadgets.util.UtilVelocity;

public class FrozenParticle implements Listener {

	float radius = 2.0F;
	int particles = 14;
	float height = 0.0F;
	int step = 0;
	double angularVelocityX = 0.07853981633974483D;

	@EventHandler
	public void paramFrozenUpdate(SchedulerEvent paramUpdateEvent) {
		if (paramUpdateEvent.getType() == SchedulerType.TICK) {
			for (Player p : Particle.entrySet()) {
				if (Particle.hasParticle(p, Particle.FROZEN)) {
					if (!PlayerLocationListener.isMoving(p)) {
						double d1 = step * angularVelocityX;
						Location localLocation1 = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
						Location localLocation2 = localLocation1;
						localLocation1.add(0.0D, height, 0.0D);
						for (int i = 0; i < particles; i++) {
							double d2 = Math.cos(i * 3.141592653589793D / 4.0D) * radius;
							double d3 = height;
							double d4 = Math.sin(i * 3.141592653589793D / 4.0D) * radius;
							Vector localVector = new Vector(d2, d3, d4);
							UtilVelocity.rotateVector(localVector, 0.0D, -d1, 0.0D);
							localLocation2.add(localVector);
							new UtilParticle(ParticleType.SNOW_SHOVEL, 0.0D, 1, 0.0D).sendToLocation(localLocation2);
							localLocation2.subtract(localVector);
						}
						if (step % 2 == 0) {
							height += 0.1F;
							if (height > 1.9D) {
								new UtilParticle(ParticleType.SNOW_SHOVEL, 0.20000000298023224D, 50, 0.20000000149011612D).sendToLocation(localLocation1.clone().add(0.0D, 2.5D, 0.0D));
								height = 0.0F;
							}
							radius -= 0.1F;
							if (radius < 0.1F) {
								radius = 2.0F;
							}
						}
						step += 1;
					} else {
						new UtilParticle(ParticleType.SNOW_SHOVEL, 0.10000000149011612D, 5, 0.20000000298023224D).sendToLocation(p.getLocation().clone().subtract(0.0D, 0.2D, 0.0D));
					}
				}
			}
		}
	}
}

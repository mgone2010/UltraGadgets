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
import br.com.floodeer.ultragadgets.util.ParticleEffect;
import br.com.floodeer.ultragadgets.util.UtilParticle;
import br.com.floodeer.ultragadgets.util.UtilParticles;
import br.com.floodeer.ultragadgets.util.UtilParticle.ParticleType;

public class HelixParticle implements Listener {

	double i = 0;

	@EventHandler
	public void onUpdate(SchedulerEvent e) {
		if (e.getType() == SchedulerType.TICK) {
			for (Player p : Particle.entrySet()) {
				if (Particle.hasParticle(p, Particle.HELIX)) {
					if (PlayerLocationListener.isMoving(p)) {
						new UtilParticle(ParticleType.REDSTONE, 0.10000000149011612D, 5, 0.20000000298023224D).sendToLocation(p.getLocation());
						return;
					}
					Location location = p.getLocation();
					Location location2 = location.clone();
					double radius = 1.1d;
					double radius2 = 1.1d;
					double particles = 100;

					for (int step = 0; step < 100; step += 4) {
						double inc = (2 * Math.PI) / particles;
						double angle = step * inc + i;
						Vector v = new Vector();
						v.setX(Math.cos(angle) * radius);
						v.setZ(Math.sin(angle) * radius);
						UtilParticles.display(ParticleEffect.REDSTONE, location.add(v));
						location.subtract(v);
						location.add(0, 0.12d, 0);
						radius -= 0.044f;
					}
					for (int step = 0; step < 100; step += 4) {
						double inc = (2 * Math.PI) / particles;
						double angle = step * inc + i + 3.5;
						Vector v = new Vector();
						v.setX(Math.cos(angle) * radius2);
						v.setZ(Math.sin(angle) * radius2);
						UtilParticles.display(ParticleEffect.REDSTONE, location2.add(v));
						location2.subtract(v);
						location2.add(0, 0.12d, 0);
						radius2 -= 0.044f;
					}
					i += 0.05;
				}
			}
		}
	}
}

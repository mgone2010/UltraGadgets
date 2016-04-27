package br.com.floodeer.ultragadgets.particles;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import br.com.floodeer.ultragadgets.enumeration.Particle;
import br.com.floodeer.ultragadgets.listener.PlayerLocationListener;
import br.com.floodeer.ultragadgets.scheduler.SchedulerEvent;
import br.com.floodeer.ultragadgets.scheduler.SchedulerType;
import br.com.floodeer.ultragadgets.util.UtilParticle;
import br.com.floodeer.ultragadgets.util.UtilParticle.ParticleType;

public class TornadoParticle implements Listener {

	float LineNumber = 3.0F;
	float j = 0.0F;
	float radius = 0.3F;
	float heightEcart = 0.01F;

	@EventHandler
	public void onUpdate(SchedulerEvent paramUpdateEvent) {
		if (paramUpdateEvent.getType() == SchedulerType.TICK) {
			for (Player p : Particle.entrySet()) {
				if ((Particle.hasParticle(p, Particle.TORNADO)) && (p.isValid())) {
					if (!PlayerLocationListener.isMoving(p)) {
						Location localLocation = p.getLocation();
						localLocation.setY(localLocation.getY() + 2.0D);
						for (int i = 0; i < this.LineNumber; i++) {
							localLocation.add(Math.cos(this.j) * this.radius, this.j * this.heightEcart,Math.sin(this.j) * this.radius);
							new UtilParticle(ParticleType.FIREWORKS_SPARK, 0.0D, 1, 0.0D).sendToLocation(localLocation);
						}
						this.j += 0.2F;
						if (this.j > 50.0F) {
							this.j = 0.0F;
						}
					} else if (!p.isInsideVehicle()) {
						new UtilParticle(ParticleType.FIREWORKS_SPARK, 0.10000000149011612D, 4, 0.30000001192092896D).sendToLocation(p.getLocation().add(0.0D, 1.0D, 0.0D));
					}
				}
			}
		}
	}
}

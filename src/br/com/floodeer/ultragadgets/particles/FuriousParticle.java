package br.com.floodeer.ultragadgets.particles;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Particle;
import br.com.floodeer.ultragadgets.listener.PlayerLocationListener;
import br.com.floodeer.ultragadgets.scheduler.SchedulerEvent;
import br.com.floodeer.ultragadgets.scheduler.SchedulerType;
import br.com.floodeer.ultragadgets.util.UtilParticle;
import br.com.floodeer.ultragadgets.util.UtilParticle.ParticleType;
import br.com.floodeer.ultragadgets.util.UtilVelocity;

public class FuriousParticle implements Listener {

	private int particles = 130;
	private int particlesPerIteration = 8;
	private float size = 1.0F;
	private float xFactor = 1.0F;
	private float yFactor = 0.6F;
	private float zFactor = 1.0F;
	private float yOffset = 0.6F;
	private double xRotation;
	private double yRotation;
	private double zRotation = 0.0D;
	private int step;
	UltraGadgets plugin = UltraGadgets.get();

	@EventHandler
	public void paramFrozenUpdate(SchedulerEvent paramUpdateEvent) {
		if (paramUpdateEvent.getType() == SchedulerType.TICK) {
			for (Player p : Particle.entrySet()) {
				if (Particle.hasParticle(p, Particle.FURIOUS)) {
					if (!PlayerLocationListener.isMoving(p)) {
						Location localLocation = p.getLocation();
						Vector localVector = new Vector();
						for (int i = 0; i < this.particlesPerIteration; i++) {
							this.step += 1;
							if (this.step > 400) {
								this.step = 0;
							}
							float f1 = 3.1415927F / this.particles * this.step;
							float f2 = (float) (Math.sin(f1 * 2.7182817F * this.particlesPerIteration / this.particles)* this.size);
							float f3 = f2 * 3.1415927F * f1;

							localVector.setX(this.xFactor * f2 * -Math.cos(f3));
							localVector.setZ(this.zFactor * f2 * -Math.sin(f3));
							localVector.setY(this.yFactor * Math.cos(f2 / 3.1415927F * f3) + this.yOffset);

							UtilVelocity.rotateVector(localVector, this.xRotation, this.yRotation, this.zRotation);
							new UtilParticle(ParticleType.FLAME, 0.0D, 1, 0.0D).sendToLocation(localLocation.add(localVector));

							localLocation.subtract(localVector);
						}
					} else if (!p.isInsideVehicle()) {
						new UtilParticle(ParticleType.FLAME, 0.10000000149011612D, 4, 0.30000001192092896D).sendToLocation(p.getLocation().add(0.0D, 1.0D, 0.0D));
					}
				}
			}
		}
	}
}

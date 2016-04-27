package br.com.floodeer.ultragadgets.particles;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


import br.com.floodeer.ultragadgets.enumeration.Particle;
import br.com.floodeer.ultragadgets.scheduler.SchedulerEvent;
import br.com.floodeer.ultragadgets.scheduler.SchedulerType;
import br.com.floodeer.ultragadgets.util.UtilMath;
import br.com.floodeer.ultragadgets.util.UtilParticle;

public class CloudParticle implements Listener {
	
	@EventHandler
	public void onUpdate(SchedulerEvent e) {
		if(e.getType() == SchedulerType.TICK) {
			for(Player p : Particle.entrySet()) {
				if(Particle.hasParticle(p, Particle.CLOUD)) {
					Location localLocation = p.getLocation();      
		             localLocation.setY(p.getLocation().getY() + 3.700000047683716D);
		             new UtilParticle(UtilParticle.ParticleType.SNOW_SHOVEL, 0.0D, 2, 0.0D).sendToLocation(localLocation.add(UtilMath.randomRange(-0.6F, 0.6F), 0.0D, UtilMath.randomRange(-0.6F, 0.6F)));
		             new UtilParticle(UtilParticle.ParticleType.CLOUD, 0.0D, 4, 0.0D).sendToLocation(localLocation.add(UtilMath.randomRange(-0.7F, 0.7F), 0.0D, UtilMath.randomRange(-0.7F, 0.7F)));
		             new UtilParticle(UtilParticle.ParticleType.CLOUD, 0.0D, 4, 0.0D).sendToLocation(localLocation.add(UtilMath.randomRange(-0.7F, 0.7F), 0.0D, UtilMath.randomRange(-0.7F, 0.7F)));
		             localLocation.subtract(localLocation);
				}
			}
		}
	}
}

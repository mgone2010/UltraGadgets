package br.com.floodeer.ultragadgets.pets;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import br.com.floodeer.ultragadgets.enumeration.Pets;
import br.com.floodeer.ultragadgets.util.ParticleEffect;
import br.com.floodeer.ultragadgets.util.UtilParticle;
import br.com.floodeer.ultragadgets.util.UtilParticle.ParticleType;

public class PetWolf extends Pet {

	public PetWolf(Player owner) {
		super(Pets.WOLF, owner);
	}

	@Override
	void onScheduler() {
		if(isAngry()) {
			ParticleEffect.REDSTONE.display(0, 0, 0, 0.5f, 15, this.getLocation().clone().add(0, 0.6, 0), 50);
		}
	}

	@Override
	public void onSpawn() {
		Location loc = this.getLocation();
		new UtilParticle(ParticleType.HEART, 1, 6, 2).sendToLocation(loc.clone().add(0, 1.0, 0));
	}
}

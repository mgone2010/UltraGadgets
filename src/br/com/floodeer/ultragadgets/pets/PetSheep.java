package br.com.floodeer.ultragadgets.pets;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import br.com.floodeer.ultragadgets.enumeration.PetType;

public class PetSheep extends Pet {

	public PetSheep(Player owner) {
		super(PetType.SHEEP, owner);
	}

	@Override
	public void onScheduler() {
		if(updateStep() == 5) {
			
		}
	}

	@Override
	public void onSpawn(Entity e) {}
}

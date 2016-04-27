package br.com.floodeer.ultragadgets;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.enumeration.Particle;
import br.com.floodeer.ultragadgets.pets.Pet;

public class UltraPlayer {
	
	private UUID uuid;
	private String name;
	private Gadgets selectedGadget;
	private Particle selectedParticle;
	private Pet selectedPet;
	private Map<Pet, String> petName = Maps.newHashMap();

	public UltraPlayer(UUID uuid) {
		this.uuid = uuid;
		this.name = UltraGadgets.get().getServer().getPlayer(uuid).getName();
	}
	
	public String getUUIDtoString() {
		return uuid.toString();
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public String getName() {
		return name;
	}
	
	public Gadgets getGadget() {
		return selectedGadget;
	}
	
	public Particle selectedParticle() {
		return selectedParticle;
	}
	
	public Pet selectedPet() {
		return selectedPet;
	}
	
	public String petName(Pet pet) {
		return petName.get(pet);
	}

}

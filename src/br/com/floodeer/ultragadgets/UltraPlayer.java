package br.com.floodeer.ultragadgets;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.enumeration.Particle;
import br.com.floodeer.ultragadgets.pets.Pet;
import br.com.floodeer.ultragadgets.storage.PlayerDataFile;
import br.com.floodeer.ultragadgets.storage.PlayerDataYaml;

public class UltraPlayer {
	
	private UUID uuid;
	private String name;
	private Gadgets selectedGadget;
	private Particle selectedParticle;
	private Pet selectedPet;
	private Map<Pet, String> petName = Maps.newHashMap();
	private PlayerDataFile file;

	public UltraPlayer(UUID uuid) {
		this.uuid = uuid;
		this.name = UltraGadgets.get().getServer().getPlayer(uuid).getName();
		file = PlayerDataYaml.getPlayerYaml(getP());
	}
	
	public Player getP() {
		return Bukkit.getPlayer(uuid);
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
	
	public void setPetName(Pet pet, String name) {
		petName.put(pet, name);
	}
	
	public PlayerDataFile getData() {
		return file;
	}
	
	public void query(String string, Object obj) {
		getData().set(string, obj);
		getData().save();
	}
}

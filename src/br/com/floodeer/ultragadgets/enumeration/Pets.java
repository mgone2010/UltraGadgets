package br.com.floodeer.ultragadgets.enumeration;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import br.com.floodeer.ultragadgets.pets.Pet;
import br.com.floodeer.ultragadgets.pets.PetManager;
import br.com.floodeer.ultragadgets.storage.PlayerDataFile;
import br.com.floodeer.ultragadgets.storage.PlayerDataYaml;

public enum Pets {
	
	SHEEP("SHEEP"),
	WOLF("WOLF"),
	CAT("CAT"),
	ENDERMAN("ENDERMAN"),
	SLIME("SLIME"),
	ZOMBIE("ZOMBIE"),
	RABBIT("RABBIT"),
	VILLAGER("VILLAGER"),
	SKELETON("SKELETON"),
	CUSTOM_SWAG("SWAG"),
	ENDERMITE("ENDERMITE");
	
	private String pettype;
	
	Pets(String pet) {
		this.pettype = pet;
	}
	
	@Override
	public String toString() {
		return pettype;
	}
	
	public static Pets get(String from) {
		for(Pets pets : Pets.values()) {
			if(pets.toString().equalsIgnoreCase(from)) {
				return pets;
			}
		}
		return null;
	}
	
	public static Pets fromEntity(EntityType type) {
		if(type == EntityType.SHEEP) {
			return Pets.SHEEP;
		}else if(type == EntityType.WOLF) {
			return Pets.WOLF;
		}else if(type == EntityType.OCELOT) {
			return Pets.CAT;
		}else if(type == EntityType.ENDERMAN) {
			return Pets.ENDERMAN;
		}else if(type == EntityType.SLIME) {
			return Pets.SLIME;
		}else if(type == EntityType.ZOMBIE) {
			return Pets.ZOMBIE;
		}else if(type == EntityType.VILLAGER) {
			return Pets.VILLAGER;
		}else if(type == EntityType.SKELETON) {
			return Pets.SKELETON;
		}else if(type == EntityType.PIG_ZOMBIE) {
			return Pets.CUSTOM_SWAG;
		}else if(type == EntityType.ENDERMITE) {
			return Pets.ENDERMITE;
		}else if(type == EntityType.RABBIT) {
			return Pets.RABBIT;
		}
		return null;
	}
	
	public static void setPlayerPetValue(Player p, Pets type) {
		PlayerDataFile data = PlayerDataYaml.getPlayerYaml(p);
		data.set("UGPlayer.PetAtivado", type.toString());
		data.save();
	}
	
	public static String getPetValue(Player p) {
		PlayerDataFile data = PlayerDataYaml.getPlayerYaml(p);
		return data.getString("UGPlayer.PetAtivado");
	}
	
	public static Pets getPetTypeValue(Player p) {
		PlayerDataFile data = PlayerDataYaml.getPlayerYaml(p);
		return get(data.getString("UGPlayer.PetAtivado"));
	}
	
	public static Entity getEntityPet(Player p) {
		return PetManager.petEntity.get(p.getUniqueId());
	}
	
	public static Pet getPetEntity(Player p) {
		return PetManager.petType.get(p.getUniqueId());
	}
	
	public static boolean hasPetSpawned(Player p) {
		if(PetManager.petEntity.containsKey(p.getUniqueId()) ||
				PetManager.petType.containsKey(p.getUniqueId())) {
			return true;
		}
		return false;
	}
}

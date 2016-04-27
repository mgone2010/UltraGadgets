package br.com.floodeer.ultragadgets.pets;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PetManager {
	
	  public static HashMap<UUID, Entity> pet = new HashMap<>();
	  
	  public static void create(Player p, Entity on) {
		 pet.put(p.getUniqueId(), on);  
	  }

}

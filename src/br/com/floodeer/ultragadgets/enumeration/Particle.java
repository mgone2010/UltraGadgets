package br.com.floodeer.ultragadgets.enumeration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import br.com.floodeer.ultragadgets.storage.PlayerDataFile;
import br.com.floodeer.ultragadgets.storage.PlayerDataYaml;

public enum Particle {
	
	HEART("HEART"),
	DRIP_WATER("DRIP_WATER"),
	DRIP_LAVA("DRIP_LAVA"),
	ANGRY_VILLAGER("ANGRY_VILLAGER"),
	HAPPY_VILLAGER("HAPPY_VILLAGER"),
	FLAMES("FLAMES"),
	MAGIC("MAGIC"),
	FIREWORK("FIREWORK"),
	FROZEN("FROZEN"),
	HELIX("HELIX"),
	CLOUD("CLOUD"),
	LILLY("LILLY"),
	FURIOUS("FURIOUS"),
	TORNADO("TORNADO"),
	SHIELD("SHIELD");
	
	private String name;
	
	Particle(String name) {
	 this.name = name;	
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	private static Map<Player, Particle> playerEffect = new HashMap<>();
	
	public static Set<Player> entrySet() {
		return playerEffect.keySet();
	}
	
	public Particle fromString(String str) {
		switch (str) {
		case "HEART":
			return HEART;
		case "DRIP_WATER":
			return DRIP_WATER;
		case "DRIP_LAVA":
			return DRIP_LAVA;	
		case "ANGRY_VILLAGER":
			return ANGRY_VILLAGER;
		case "HAPPY_VILLAGER":
			return HAPPY_VILLAGER;
		case "FLAMES":
			return FLAMES;
		case "MAGIC":
			return MAGIC;
		case "FIREWORK":
			return FIREWORK;
		case "FROZEN":
			return FROZEN;
		case "CLOUD":
			return CLOUD;
		case "LILLY":
			return LILLY;
		case "FURIOUS":
			return FURIOUS;
		case "TORNADO":
			return TORNADO;
		case "HELIX":
		  return HELIX;
		  
		case "SHIELD":
			return SHIELD;
		}
		return null;
	}
	public static void active(Player p, Particle particle) {
		if(hasParticle(p)) {
			remove(p);
		}
		playerEffect.put(p, particle);
		PlayerDataFile data = PlayerDataYaml.getPlayerYaml(p);
		data.set("UGPlayer.ParticulaAtivada", particle.toString().toUpperCase());
		data.save();
	}
	
	public static void remove(Player p) {
		if(hasParticle(p)) {
			playerEffect.remove(p);
		}
	}

	public static boolean hasParticle(Player p) {
		PlayerDataFile data = PlayerDataYaml.getPlayerYaml(p);
		if(playerEffect.containsKey(p) || data.contains("UGPlayer.ParticulaAtivada") && (!data.getString("UGPlayer.ParticulaAtivada").equalsIgnoreCase("nenhuma"))) {
			return true;
		}
		return false;
	}
	
	public static boolean hasParticle(Player p, Particle particle) {
		PlayerDataFile data = PlayerDataYaml.getPlayerYaml(p);
		if(hasParticle(p) && playerEffect.get(p) == particle || data.getString("UGPlayer.ParticulaAtivada").toUpperCase().equalsIgnoreCase(particle.toString().toUpperCase())) {
			return true;
		}
		return false;
	}
	
	public Particle getPlayerParticle(Player p) {
		PlayerDataFile data = PlayerDataYaml.getPlayerYaml(p);
		return fromString(data.getString("UGPlayer.ParticulaAtivada"));
	}
	
	public String getPlayerParticleToString(Player p) {
		return getPlayerParticle(p).toString();
	}
}

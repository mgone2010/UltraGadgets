package br.com.floodeer.ultragadgets.util;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class PlayerUtils {

	Map<String, pInventory> inventories = new HashMap<>();
	Map<String, ItemStack[]> armor = new HashMap<>();
	
	public void saveArmorContents(Player p) {
		armor.put(p.getUniqueId().toString(), p.getInventory().getArmorContents());
		p.getInventory().setArmorContents(null);
	}
	
	public void restoreArmorContents(Player p) {
		if(armor.containsKey(p.getUniqueId().toString())) {
			p.getInventory().setArmorContents(null);
			p.getInventory().setArmorContents(armor.get(p.getUniqueId().toString()));
			armor.remove(p);
		}
	}
	 
    public void saveInv(Player p) {
    	inventories.put(p.getUniqueId().toString(), new pInventory(p.getInventory().getContents(), p.getInventory().getArmorContents(), p.getLevel(), p.getExp(), p.getGameMode()));
    }
    
	public void restoreInv(Player player) {
		if (player != null) {
			if (inventories.containsKey(player.getUniqueId().toString())) {
				player.getInventory().clear();
				player.getInventory().setHelmet(null);
				player.getInventory().setChestplate(null);
				player.getInventory().setLeggings(null);
				player.getInventory().setBoots(null);
				for (PotionEffect effect : player.getActivePotionEffects()) {
					player.removePotionEffect(effect.getType());
				}
				player.getInventory().setContents(inventories.get(player.getUniqueId().toString()).getContent());
				player.getInventory().setArmorContents(inventories.get(player.getUniqueId().toString()).getArmor());
				player.setLevel(inventories.get(player.getUniqueId().toString()).getLevel());
				player.setExp(inventories.get(player.getUniqueId().toString()).getExp());
				player.setGameMode(inventories.get(player.getUniqueId().toString()).getGameMode());
				inventories.remove(player.getUniqueId().toString());
			}
		}
	}
	
	public void safePlayer(Player p) {
		p.setFallDistance(0F);
		p.setHealth(p.getMaxHealth());
	}

	private class pInventory {

		private ItemStack[] content;
		private ItemStack[] armor;
		private float exp;
		private GameMode gameMode;
		private int level;

		public pInventory(ItemStack[] content, ItemStack[] armor, int l, float f, GameMode gameMode) {
			this.content = content;
			this.armor = armor;
			level = l;
			exp = f;
			this.gameMode = gameMode;
		}

		public ItemStack[] getContent() {
			return content;
		}

		public ItemStack[] getArmor() {
			return armor;
		}

		public int getLevel() {
			return level;
		}

		public float getExp() {
			return exp;
		}

		public GameMode getGameMode() {
			return gameMode;
		}
	}
}

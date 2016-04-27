package br.com.floodeer.ultragadgets.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import br.com.floodeer.ultragadgets.UltraGadgets;

public abstract class Menu {
	

	private Listener listener;
	private String title;
	
	public Menu(String title, int slots) {
		this.title = title;
		listener = new onInventoryClick();
		Bukkit.getPluginManager().registerEvents(listener, UltraGadgets.get());
	}
	
	public abstract void onClick(Player p, int slot);
	public abstract void onClose(Player p);
	public abstract void onClickAir(Player p);
	
	public class onInventoryClick implements Listener {
		@EventHandler
		public void onInventoryClickEvent(InventoryClickEvent e) {
			if(e.getInventory().getName().equalsIgnoreCase(title)) {
				e.setCancelled(true);
				int slot = e.getRawSlot();
				Player p = (Player)e.getWhoClicked();
				onClick(p, slot);
				if(e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.AIR) {
					onClickAir(p);
				}
			}
		}
	}
}

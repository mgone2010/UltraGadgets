package br.com.floodeer.ultragadgets.gadgets;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.util.UtilCooldown;

public abstract class Gadget {

	public long cooldown;
	public String gadgetName;
	private Gadgets type;
	private Material materialType;

	public Gadget(long cooldown, String gadget, Gadgets type, Material materialType) {
		this.cooldown = cooldown;
		this.gadgetName = gadget;
		this.type = type;
		this.materialType = materialType;
		UltraGadgets.get().getServer().getPluginManager().registerEvents(new gadgetListener(), UltraGadgets.get());
	}

	abstract void onInteract(Player p);

	abstract void onCooldown(Player p);

	public class gadgetListener implements Listener {

		@EventHandler
		public void onGadgetInteract(PlayerInteractEvent e) {
			if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
			if (Gadgets.hasGadgetSelected(e.getPlayer(), type)) {
				for(String s : UltraGadgets.getCfg().disableWorlds) {
					if(e.getPlayer().getWorld().getName().equalsIgnoreCase(s)) {
						return;
					}
				}
				if (e.getPlayer().getItemInHand().getType() == materialType) {
					if (UtilCooldown.tryCooldown(e.getPlayer(), gadgetName, cooldown)) {
						onInteract(e.getPlayer());
					} else {
						onCooldown(e.getPlayer());
					}
				}
			}
		}
	}
}

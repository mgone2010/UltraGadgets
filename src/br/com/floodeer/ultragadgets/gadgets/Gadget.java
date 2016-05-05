package br.com.floodeer.ultragadgets.gadgets;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.eventAPI.GadgetCooldownEvent;
import br.com.floodeer.ultragadgets.eventAPI.PlayerUseGadgetEvent;
import br.com.floodeer.ultragadgets.util.UtilCooldown;

public abstract class Gadget {

	public long cooldown;
	public String gadgetName;
	private Gadgets type;
	private ItemStack materialType;

	public Gadget(long cooldown, String gadget, Gadgets type, Material material) {
		this.cooldown = cooldown;
		this.gadgetName = gadget;
		this.type = type;
		this.materialType = new ItemStack(material);
		UltraGadgets.get().getServer().getPluginManager().registerEvents(new gadgetListener(), UltraGadgets.get());
	}
	
	public Gadget(long cooldown, String gadget, Gadgets type, ItemStack material) {
		this.cooldown = cooldown;
		this.gadgetName = gadget;
		this.type = type;
		this.materialType = material;
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
				if (e.getPlayer().getItemInHand().getType() == materialType.getType()) {
					PlayerUseGadgetEvent event = new PlayerUseGadgetEvent(e.getPlayer(), type);
					Bukkit.getPluginManager().callEvent(event);
					if(event.isCancelled()) {
						e.setCancelled(true);
						return;
					}
					GadgetCooldownEvent cevent = new GadgetCooldownEvent(e.getPlayer(), type);
					if(!cevent.isCancelled())
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

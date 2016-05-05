package br.com.floodeer.ultragadgets.eventAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import br.com.floodeer.ultragadgets.enumeration.Gadgets;

public class GadgetCooldownEvent extends Event implements Cancellable {
	
	private Player player;
	private Gadgets gadget;
	private int currentCooldown;
	private boolean cancelled;
	
	public GadgetCooldownEvent(Player owner, Gadgets gadget) {
		this.player = owner;
		this.gadget = gadget;
		currentCooldown = 0;
	}
	
	public GadgetCooldownEvent(Player owner, Gadgets gadget, int currentCooldown) {
		this.player = owner;
		this.gadget = gadget;
		this.currentCooldown = currentCooldown;
	}
	
	/**
	 * O jogador que usou o gadget.
	 * 
	 * @return - O player.
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * O tipo de gadget usado.
	 * 
	 * @return - O gadget.
	 */
	public Gadgets getGadget() {
		return gadget;
	}
	
	public int getCooldown() {
      return currentCooldown;		
	}
	
	private static final HandlerList handlers = new HandlerList();
	 
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean value) {
		cancelled = value;
	}
}

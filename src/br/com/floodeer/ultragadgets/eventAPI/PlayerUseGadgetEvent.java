package br.com.floodeer.ultragadgets.eventAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import br.com.floodeer.ultragadgets.enumeration.Gadgets;

public class PlayerUseGadgetEvent extends Event implements Cancellable {
	
	private Player player;
	private Gadgets gadget;
	private boolean cancelled;
	
	public PlayerUseGadgetEvent(Player player, Gadgets gadget) {
		this.player = player;
		this.gadget = gadget;
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

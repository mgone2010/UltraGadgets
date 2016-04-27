package br.com.floodeer.ultragadgets.eventAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import br.com.floodeer.ultragadgets.enumeration.Gadgets;

public class PlayerGadgetSelectEvent extends Event implements Cancellable {

	private Player player;
	private Gadgets toGadget;
	private Gadgets fromGadget;
	private boolean cancelled;
	
	
	public PlayerGadgetSelectEvent(Player player, Gadgets toGadget, Gadgets fromGadget) {
		this.player = player;
		this.toGadget = toGadget;
		this.fromGadget = fromGadget;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Gadgets toGadget() {
		return toGadget;
	}
	
	public Gadgets fromGadget() {
		return fromGadget;
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

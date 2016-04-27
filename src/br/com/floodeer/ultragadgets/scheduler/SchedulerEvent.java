package br.com.floodeer.ultragadgets.scheduler;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SchedulerEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private SchedulerType _type;

	public SchedulerEvent(SchedulerType paramUpdateType) {
		this._type = paramUpdateType;
	}

	public SchedulerType getType() {
		return this._type;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
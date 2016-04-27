package br.com.floodeer.ultragadgets.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import br.com.floodeer.ultragadgets.scheduler.SchedulerEvent;
import br.com.floodeer.ultragadgets.scheduler.SchedulerType;

public class PlayerLocationListener implements Listener {

	private static final List<String> Moving = new ArrayList<>();
	private final HashMap<UUID, Location> lastBlockLocation = new HashMap<>();

	@EventHandler
	public void onUpdate(SchedulerEvent paramUpdateEvent) {
		if (paramUpdateEvent.getType() == SchedulerType.FASTEST) {
			for (Player localPlayer : Bukkit.getOnlinePlayers()) {
				Location localLocation1 = localPlayer.getLocation();
				Location localLocation2 = (Location) this.lastBlockLocation.get(localPlayer.getUniqueId());
				if (this.lastBlockLocation.get(localPlayer.getUniqueId()) == null) {
					this.lastBlockLocation.put(localPlayer.getUniqueId(), localLocation1);
					localLocation2 = (Location) this.lastBlockLocation.get(localPlayer.getUniqueId());
				}
				this.lastBlockLocation.put(localPlayer.getUniqueId(), localPlayer.getLocation());
				if ((localLocation2.getX() != localLocation1.getX()) || (localLocation2.getY() != localLocation1.getY())
						|| (localLocation2.getZ() != localLocation1.getZ())) {
					if (!Moving.contains(localPlayer.getName())) {
						Moving.add(localPlayer.getName());
					}
				} else if (Moving.contains(localPlayer.getName())) {
					Moving.remove(localPlayer.getName());
				}
			}
		}
	}

	public static boolean isMoving(Player paramPlayer) {
		if (Moving.contains(paramPlayer.getName())) {
			return true;
		}
		return false;
	}
}

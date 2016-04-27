package br.com.floodeer.ultragadgets.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import br.com.floodeer.ultragadgets.scheduler.SchedulerEvent;
import br.com.floodeer.ultragadgets.scheduler.SchedulerType;

public class ServerStep implements Listener {

	public static int step = 0;

	@EventHandler
	public void onUpdate(SchedulerEvent e) {
		if (e.getType() == SchedulerType.TICK) {
			step += 1;
			if (step >= 3000) {
				step = 0;
			}
		}
	}
}

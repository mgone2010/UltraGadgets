package br.com.floodeer.ultragadgets.scheduler;

import org.bukkit.plugin.java.JavaPlugin;

public class SchedulerRunner implements Runnable {
	private JavaPlugin _plugin;

	public SchedulerRunner(JavaPlugin paramJavaPlugin) {
		this._plugin = paramJavaPlugin;
		this._plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this._plugin, this, 0L, 1L);
	}

	public void run() {
		SchedulerType[] arrayOfUpdateType;
		int j = (arrayOfUpdateType = (SchedulerType[]) SchedulerType.class.getEnumConstants()).length;
		for (int i = 0; i < j; i++) {
			SchedulerType localUpdateType = arrayOfUpdateType[i];
			if (localUpdateType.Elapsed()) {
				this._plugin.getServer().getPluginManager().callEvent(new SchedulerEvent(localUpdateType));
			}
		}
	}
}

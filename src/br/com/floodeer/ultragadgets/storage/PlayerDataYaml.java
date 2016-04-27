package br.com.floodeer.ultragadgets.storage;

import java.io.File;
import java.util.UUID;

import org.bukkit.entity.Player;

import br.com.floodeer.ultragadgets.UltraGadgets;

public class PlayerDataYaml {

	public static PlayerDataFile getPlayerYaml(Player player) {

		return new PlayerDataFile(UltraGadgets.get().getDataFolder().getAbsolutePath() + File.separator + "players"
				+ File.separator + player.getUniqueId() + ".yml");

	}

	public static PlayerDataFile getOfflinePlayerYaml(UUID fromUUID) {

		return new PlayerDataFile(UltraGadgets.get().getDataFolder().getAbsolutePath() + File.separator + "players"
				+ File.separator + fromUUID + ".yml");

	}
}
package br.com.floodeer.ultragadgets.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import br.com.floodeer.ultragadgets.scheduler.SchedulerEvent;
import br.com.floodeer.ultragadgets.scheduler.SchedulerType;

public class Gravity implements Listener {

	protected static HashMap<UUID, Vector> velocities = new HashMap<UUID, Vector>();
	protected static HashMap<UUID, Location> positions = new HashMap<UUID, Location>();
	protected static HashMap<UUID, Boolean> onGround = new HashMap<UUID, Boolean>();
	public static List<Player> inGravity = new ArrayList<Player>();

	@SuppressWarnings("deprecation")
	public static void taskUpdateVelocity() {
		for (Player paramPlayer : Bukkit.getOnlinePlayers()) {
			if (inGravity.contains(paramPlayer)) {
				Vector paramVectorToUpdate1 = paramPlayer.getVelocity().clone();
				UUID localUUID = paramPlayer.getUniqueId();
				if ((velocities.containsKey(localUUID)) && (onGround.containsKey(localUUID))
						&& (!paramPlayer.isOnGround()) && (!paramPlayer.isInsideVehicle())) {
					Vector paramVectorToUpdate2 = (Vector) velocities.get(localUUID);
					Vector paramVectorToUpdate3;
					if (!((Boolean) onGround.get(localUUID)).booleanValue()) {
						paramVectorToUpdate3 = paramVectorToUpdate2.clone();
						paramVectorToUpdate3.subtract(paramVectorToUpdate1);
						double d1 = paramVectorToUpdate3.getY();
						if ((d1 > 0.0D)
								&& ((paramVectorToUpdate1.getY() < -0.01D) || (paramVectorToUpdate1.getY() > 0.01D))) {
							Location paramUpdaterLocation = paramPlayer.getLocation().clone();
							double d2 = 1.0D;
							while (paramUpdaterLocation.getBlockY() >= 0) {
								Block localBlock = paramUpdaterLocation.getBlock();

								d2 = 0.3D;
								if (localBlock.getType() != Material.AIR) {
									break;
								}
								paramUpdaterLocation.setY(paramUpdaterLocation.getY() - 1.0D);
							}
							paramVectorToUpdate1.setY(paramVectorToUpdate2.getY() - d1 * d2);
							int k = (paramVectorToUpdate1.getX() >= -0.001D) && (paramVectorToUpdate1.getX() <= 0.001D)
									? 0 : 1;
							int m = (paramVectorToUpdate2.getX() >= -0.001D) && (paramVectorToUpdate2.getX() <= 0.001D)
									? 0 : 1;
							if ((k != 0) && (m != 0)) {
								paramVectorToUpdate1.setX(paramVectorToUpdate2.getX());
							}
							int n = (paramVectorToUpdate1.getZ() >= -0.001D) && (paramVectorToUpdate1.getZ() <= 0.001D)
									? 0 : 1;
							int i1 = (paramVectorToUpdate2.getZ() >= -0.001D) && (paramVectorToUpdate2.getZ() <= 0.001D)
									? 0 : 1;
							if ((n != 0) && (i1 != 0)) {
								paramVectorToUpdate1.setZ(paramVectorToUpdate2.getZ());
							}
							paramPlayer.setVelocity(paramVectorToUpdate1.clone());
						}
					} else if (((paramPlayer instanceof Player)) && (positions.containsKey(localUUID))) {
						paramVectorToUpdate3 = paramPlayer.getLocation().toVector();
						Vector paramVectorToUpdate4 = ((Location) positions.get(localUUID)).toVector();
						Vector paramVectorToUpdate5 = paramVectorToUpdate3.subtract(paramVectorToUpdate4);
						paramVectorToUpdate1.setX(paramVectorToUpdate5.getX());
						paramVectorToUpdate1.setZ(paramVectorToUpdate5.getZ());
					}
					paramPlayer.setVelocity(paramVectorToUpdate1.clone());
				}
				velocities.put(localUUID, paramVectorToUpdate1.clone());
				onGround.put(localUUID, Boolean.valueOf(paramPlayer.isOnGround()));
				positions.put(localUUID, paramPlayer.getLocation());
			}
		}
	}

	@EventHandler
	public void update(SchedulerEvent e) {
		if (e.getType() == SchedulerType.TICK) {
			taskUpdateVelocity();
		}
	}
}

package br.com.floodeer.ultragadgets.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import br.com.floodeer.ultragadgets.UltraGadgets;

public class UtilEnt {

	public static Item dropItem(Location loc, ItemStack item) {
		ItemFactory.setName(item, String.valueOf((float) UtilMath.randomRange(0, 100)));
		Item toDrop = loc.getWorld().dropItem(loc, item);
		return toDrop;
	}

	public static Entity dropItemToRemove(ItemStack i, Location l, String meta, int duration) {
		final Entity e = l.getWorld().dropItem(l, i);
		e.setMetadata(meta, new FixedMetadataValue(UltraGadgets.get(), meta));
		if (duration > 0) {
			Bukkit.getScheduler().runTaskLater(UltraGadgets.get(), new Runnable() {
				public void run() {
					if (e.isValid()) {
						e.remove();
					}
				}
			}, duration * 20L);
		}
		return e;
	}
	
	

	public static void dropItemToRemove(Location loc, ItemStack item, int count, long time) {
		for (int i = count; i < count; i++) {
			final Item drop = dropItem(loc, item);
			drop.setPickupDelay(Integer.MAX_VALUE);
			new BukkitRunnable() {

				@Override
				public void run() {
					drop.remove();

				}
			}.runTaskLater(UltraGadgets.get(), time);
		}
	}

	public static void dropItemToRemove(Location loc, ItemStack item, int count, long time, Vector vector) {
		for (int i = count; i < count; i++) {
			final Item drop = dropItem(loc, item);
			drop.setPickupDelay(Integer.MAX_VALUE);
			drop.setVelocity(vector);
			new BukkitRunnable() {

				@Override
				public void run() {
					drop.remove();

				}
			}.runTaskLater(UltraGadgets.get(), time);
		}
	}

	public static void dropItemToRemove(Location loc, ItemStack item, long time) {
		final Item drop = dropItem(loc, item);
		drop.setPickupDelay(Integer.MAX_VALUE);
		new BukkitRunnable() {

			@Override
			public void run() {
				drop.remove();
			}
		}.runTaskLater(UltraGadgets.get(), time);
	}

	public static void dropItemToRemove(Location loc, ItemStack item, long time, Vector vector) {
		final Item drop = dropItem(loc, item);
		drop.setPickupDelay(Integer.MAX_VALUE);
		drop.setVelocity(vector);
		new BukkitRunnable() {
			@Override
			public void run() {
				drop.remove();
			}
		}.runTaskLater(UltraGadgets.get(), time);
	}

	public static Item dropItem(Location loc, ItemStack item, long time, Vector vector) {
		final Item drop = dropItem(loc, item);
		drop.setPickupDelay(Integer.MAX_VALUE);
		drop.setVelocity(vector);
		return drop;
	}
}

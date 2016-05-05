package br.com.floodeer.ultragadgets.gadgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.util.ItemFactory;
import br.com.floodeer.ultragadgets.util.ParticleEffect;
import br.com.floodeer.ultragadgets.util.UtilCooldown;
import br.com.floodeer.ultragadgets.util.UtilMath;
import br.com.floodeer.ultragadgets.util.UtilTitle;

public class ExplosivePoopGadget extends Gadget implements Listener {

	private static ItemStack item() {
		return ItemFactory.buildItemStackArrays(Material.INK_SACK, (byte)3);
	}
	
	public ExplosivePoopGadget() {
		super(UltraGadgets.getCfg().explosiveCooldown * 1000, Gadgets.EXPLOSIVE_POOP.toString(), Gadgets.EXPLOSIVE_POOP,
				item());
	}

	private final Map<Player, Item> bombsItem = new HashMap<>();
	private final Map<Player, List<Item>> entitiesMap = new HashMap<>();

	@Override
	void onInteract(final Player p) {
		World world = p.getWorld();
		Location location = p.getLocation();
		final Item item = world.dropItem(location, ItemFactory.buildItemStackArrays(Material.INK_SACK, (byte) 3));
		item.setVelocity(location.getDirection().multiply(1));
		item.setPickupDelay(Integer.MAX_VALUE);
		world.playSound(location, Sound.FUSE, 10.0F, 5.0F);
		new BukkitRunnable() {
			public void run() {
				if (!item.isDead()) {
					Location location = item.getLocation();
					World world = location.getWorld();
					item.remove();
					location.getWorld().playSound(location, Sound.EXPLODE, 2.0F, 0.0F);
					ParticleEffect.EXPLOSION_NORMAL.display(0.0F, 0.0F, 0.0F, 1.0F, 100, location, 180);
					List<Item> entities = new ArrayList<>();
					for (int i = 0; i < UtilMath.random.nextInt(41) + 40; i++) {
						Item poop = world.dropItem(location,ItemFactory.buildItemStackArrays(Material.INK_SACK, (byte) 3));
						float x = -0.8F + (float) (Math.random() * 2.6D);
						float y = -0.4F + (float) (Math.random() * 1.8D);
						float z = -0.8F + (float) (Math.random() * 2.6D);
						poop.setVelocity(new Vector(x, y, z));
						poop.setPickupDelay(Integer.MAX_VALUE);
						entities.add(poop);
					}
					for (int i = 0; i < UtilMath.random.nextInt(21) + 20; i++) {
						Item poop = world.dropItem(location,ItemFactory.buildItemStackArrays(Material.WOOL, (byte) 12));
						float x = -0.8F + (float) (Math.random() * 2.6D);
						float y = -0.4F + (float) (Math.random() * 1.8D);
						float z = -0.8F + (float) (Math.random() * 2.6D);
						poop.setVelocity(new Vector(x, y, z));
						poop.setPickupDelay(Integer.MAX_VALUE);
						entities.add(poop);
					}
					entitiesMap.put(p, entities);
				}
			}
		}.runTaskLater(UltraGadgets.get(), 80L);
		bombsItem.put(p, item);
		new BukkitRunnable() {

			@Override
			public void run() {
				Item bomb = bombsItem.remove(p);
				if ((bomb != null) && (!bomb.isDead())) {
					bomb.getLocation().getWorld().playSound(bomb.getLocation(), Sound.EXPLODE, 3.0F, 0.0F);
					ParticleEffect.EXPLOSION_HUGE.display(0.0F, 0.0F, 0.0F, 1.0F, 100, bomb.getLocation(), 180);
					bomb.remove();
				}
				List<Item> entities = entitiesMap.remove(p);
				if (entities != null) {
					for (Item entity : entities) {
						if (!entity.isDead()) {
							entity.getLocation().getWorld().createExplosion(entity.getLocation().getX(),entity.getLocation().getY(), entity.getLocation().getZ(), 5F, false, false);
							entity.remove();
						}
					}
				}
			}
		}.runTaskLater(UltraGadgets.get(), 8 * 20L);
	}

	@Override
	void onCooldown(Player p) {
		long cooldown = UtilCooldown.getCooldown(p, this.gadgetName) / 1000;
		UtilTitle title = new UtilTitle(
		UltraGadgets.getCfg().title.replaceAll("<cooldown>", String.valueOf(cooldown)).replaceAll("<gadget>",
		UltraGadgets.getCfg().explosivePoopNome),
		UltraGadgets.getCfg().subtitle.replaceAll("<cooldown>", String.valueOf(cooldown)).replaceAll("<gadget>",
	    UltraGadgets.getCfg().explosivePoopNome).replaceAll("&", "§"),
		6, 8, 6);
		title.setTimingsToTicks();
		title.send(p);
	}
}
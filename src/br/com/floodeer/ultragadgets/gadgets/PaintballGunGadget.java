package br.com.floodeer.ultragadgets.gadgets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.util.UtilBlock;
import br.com.floodeer.ultragadgets.util.UtilMath;

public class PaintballGunGadget extends Gadget implements Listener {

	List<Player> pb = new ArrayList<>();
	List<Player> smallCooldown = new ArrayList<>();
	
	public PaintballGunGadget() {
		super(0, Gadgets.PAINTBALL.toString().toLowerCase(), Gadgets.PAINTBALL, Material.DIAMOND_BARDING);
	}
	
	@Override
	void onInteract(final Player p) {
		if(smallCooldown.contains(p)) {
			return;
		}
		Snowball ender = (Snowball) p.launchProjectile(Snowball.class);
		ender.getWorld().playSound(ender.getLocation(), Sound.CHICKEN_EGG_POP, 1.5F, 1.2F);
		ender.setMetadata("FromPaintball", new FixedMetadataValue(UltraGadgets.get(), null));
		pb.add(p);
		smallCooldown.add(p);
		Bukkit.getScheduler().runTaskLater(UltraGadgets.get(), new Runnable() {

			@Override
			public void run() {
				smallCooldown.remove(p);
			}
		}, 4L);
	}
	

	@Override
	void onCooldown(Player p) {}

	@EventHandler
	public void onProjHit(ProjectileHitEvent event) {
		if (event.getEntity().getType() != EntityType.SNOWBALL)
			return;
		if (((event.getEntity().getShooter() instanceof Player)) && event.getEntity().hasMetadata("FromPaintball")) {
			byte b = (byte) UtilMath.random.nextInt(15);
			Location localLocation = event.getEntity().getLocation().add(event.getEntity().getVelocity());
			localLocation.getWorld().playEffect(localLocation, Effect.STEP_SOUND, 49);
			for (Block localBlock : UtilBlock.getInRadius(localLocation, 1.5D).keySet()) {
				if (UtilBlock.solid(localBlock)) {
					if (!UtilBlock.blocksToRestore.containsKey(localBlock)) {
						if (localBlock.getType() != Material.WOOL) {
							UtilBlock.setToRestore(localBlock, Material.WOOL, b, 60);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (((e.getDamager() instanceof Snowball))) {
			Snowball ender = (Snowball) e.getDamager();
			if (ender.hasMetadata("FromPaintball")) {
				e.setCancelled(true);
			}
		}
	}
}
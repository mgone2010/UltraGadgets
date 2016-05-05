package br.com.floodeer.ultragadgets.gadgets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.util.EntityUtils;
import br.com.floodeer.ultragadgets.util.ParticleEffect;
import br.com.floodeer.ultragadgets.util.PlayerUtils;
import br.com.floodeer.ultragadgets.util.UtilCooldown;
import br.com.floodeer.ultragadgets.util.UtilLocations;
import br.com.floodeer.ultragadgets.util.UtilMath;
import br.com.floodeer.ultragadgets.util.UtilTitle;

public class FumeganteGadget extends Gadget implements Listener {

	PlayerUtils pUtils = new PlayerUtils();
	List<Player> going = new ArrayList<>();
	
	public FumeganteGadget() {
		super(UltraGadgets.getCfg().fumeganteCooldown*1000, 
		Gadgets.FUMEGANTE.toString(), 
		Gadgets.FUMEGANTE, 
		Material.BLAZE_POWDER);
	}
	private void spawnFallingBlocks(final Location loc) {
		new BukkitRunnable() {
			int i = 1;

			@Override
			public void run() {
				if (i == 6) {
					cancel();
				}
				for (Block b : UtilLocations.getBlocksInRadius(loc, i, true)) {
					if (b.getType() != Material.AIR && b.getRelative(BlockFace.UP).getType() == Material.AIR) {
						@SuppressWarnings("deprecation")
						FallingBlock fb = loc.getWorld().spawnFallingBlock(b.getLocation().clone().add(0, 1.1f, 0),b.getType(), b.getData());
						fb.setVelocity(new Vector(0, UtilMath.randomRange(0.5, 1.7), 0));
						fb.setDropItem(false);
						fb.setMetadata("ugGadget", new FixedMetadataValue(UltraGadgets.get(), null));
					}
				}
				++i;
			}
		}.runTaskTimer(UltraGadgets.get(), 0, 1);
	}

	@Override
	void onInteract(final Player p) {
		pUtils.saveInv(p);
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		going.add(p);
		p.getLocation().setPitch(0);
		p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
		new BukkitRunnable() {
			int particlesPerSecond = 5;
			@Override
			public void run() {
				if(UltraGadgets.getCfg().nerfGadgetsLags) {
					if(particlesPerSecond >= 8) {
						particlesPerSecond = 0;
					}
				}
				++particlesPerSecond;
				if(!going.contains(p)){
					cancel();
					return;
				}
				ParticleEffect.FIREWORKS_SPARK.display(0, 0, 0, 0.2F, particlesPerSecond, p.getLocation().clone().subtract(0, 0.2, 0), 120 * 5);
				ParticleEffect.FLAME.display(0, 0, 0, 0.2F, particlesPerSecond, p.getLocation().clone().subtract(0, 0.1, 0), 120 * 5);
				ParticleEffect.CLOUD.display(0, 0, 0, 0.2F, particlesPerSecond, p.getLocation().clone().subtract(0, 0.3, 0), 120 * 5);
		    	ParticleEffect.ENCHANTMENT_TABLE.display(0.5f, 0, 0.5f, 0.5F, particlesPerSecond, p.getLocation().clone().subtract(0, 0.5, 0), 120 * 5);
			}
		}.runTaskTimer(UltraGadgets.get(), 0, 3);
		new BukkitRunnable() {
			int steps = 10;
			@Override
			public void run() {
				--steps;
				new UtilTitle("§a§lProcessando:", "§7" + steps, 0, 20, 0).send(p);;
				if(steps <= 3) {
			    	UtilTitle title = new UtilTitle("§c§lProcessando:", "§7" + steps, 0, 20, 0);
			    	title.setTimingsToTicks();
			    	title.send(p);
			        for(Player near : UtilLocations.getNearbyPlayers(p.getLocation(), 10)) {
			        	near.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0F, 1F);
			        }
				}
			    if(steps == 0) {
			    	p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 3.0F, 0.0F);
					ParticleEffect.EXPLOSION_HUGE.display(0.0F, 0.0F, 0.0F, 1.0F, 100, p.getLocation(), 180);
					p.setVelocity(new Vector(0, 250, 0));
			    	cancel();
			    	if(UltraGadgets.getCfg().nerfGadgetsLags) {
				    	going.remove(p);
			    	}
			    	new BukkitRunnable() {
						
						@Override
						public void run() {
					    	pUtils.restoreInv(p);
					    	pUtils.safePlayer(p);
					    	if(going.contains(p))
						    	going.remove(p);
					    	p.setVelocity(new Vector(0, -250, 0));
					    	new BukkitRunnable() {
								
								@Override
								public void run() {
								      if(EntityUtils.isGrounded(p) || !p.isOnline() || Gadgets.getPlayerGadget(p) != Gadgets.FUMEGANTE) {
								    	  cancel();
								    	  spawnFallingBlocks(p.getLocation());
								    	  p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 3.0F, 0.0F);
									      ParticleEffect.EXPLOSION_HUGE.display(0.0F, 0.0F, 0.0F, 1.0F, 100, p.getLocation(), 180);
								      }
								}
							}.runTaskTimer(UltraGadgets.get(),0,1);
						}
					}.runTaskLater(UltraGadgets.get(), 2*20);
			    }
			}
		}.runTaskTimer(UltraGadgets.get(), 0, 20);
	}

	@Override
	void onCooldown(Player p) {
		long cooldown = UtilCooldown.getCooldown(p, this.gadgetName) / 1000;
		UtilTitle title = new UtilTitle(
		UltraGadgets.getCfg().title.replaceAll("<cooldown>", String.valueOf(cooldown)).replaceAll("<gadget>",
		Gadgets.getPlayerGadget(p).toString().toLowerCase().replaceAll("_", "")),
		UltraGadgets.getCfg().subtitle.replaceAll("<cooldown>", String.valueOf(cooldown)).replaceAll("<gadget>",
		Gadgets.getPlayerGadget(p).toString().toLowerCase().replaceAll("_", "")).replaceAll("&", "§"),
		6, 8, 6);
		title.setTimingsToTicks();
		title.send(p);
	}
}

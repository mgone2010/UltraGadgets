package br.com.floodeer.ultragadgets.gadgets;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Maps;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.scheduler.SchedulerEvent;
import br.com.floodeer.ultragadgets.scheduler.SchedulerType;
import br.com.floodeer.ultragadgets.util.Gravity;
import br.com.floodeer.ultragadgets.util.UtilCooldown;
import br.com.floodeer.ultragadgets.util.UtilParticle;
import br.com.floodeer.ultragadgets.util.UtilTitle;

public class GravityGadget extends Gadget implements Listener {
	
	Map<Player, Block> gravityBlocks = Maps.newHashMap();
	
	public void buildStation(final Player p) {
		Location l = p.getLocation();
		final Block b = l.add(0,1,0).getBlock();
		if(b.getType() == Material.AIR) {
			b.setType(Material.SLIME_BLOCK);
			b.getRelative(BlockFace.UP).setType(Material.IRON_FENCE);
			b.getRelative(BlockFace.DOWN).setType(Material.IRON_FENCE);
			b.setMetadata("gadgetBlock", new FixedMetadataValue(UltraGadgets.get(), "ugBlocks"));
			b.getRelative(BlockFace.UP).setMetadata("gadgetBlock", new FixedMetadataValue(UltraGadgets.get(), "ugBlocks"));
			b.getRelative(BlockFace.DOWN).setMetadata("gadgetBlock", new FixedMetadataValue(UltraGadgets.get(), "ugBlocks"));
			gravityBlocks.put(p,b);
			new BukkitRunnable() {
				int steps = 0;
				@Override
				public void run() {
					++steps;
					if(steps >= UltraGadgets.getCfg().gravityTempo && gravityBlocks.containsKey(p)) {
						p.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType());
						p.getWorld().playEffect(b.getRelative(BlockFace.UP).getLocation(), Effect.STEP_SOUND, b.getType());
						p.getWorld().playEffect(b.getRelative(BlockFace.DOWN).getLocation(), Effect.STEP_SOUND, b.getType());
						b.setType(Material.AIR);
						b.getRelative(BlockFace.UP).setType(Material.AIR);
						b.getRelative(BlockFace.DOWN).setType(Material.AIR);
						gravityBlocks.remove(p);
						gravityBlocks.remove(b);
						Gravity.inGravity.clear();
						cancel();
						return;
					}
				}
			}.runTaskTimer(UltraGadgets.get(), 0, 20);
		}
	}
	
	@EventHandler
	public void onDestroy(BlockBreakEvent e) {
		if(e.getBlock().hasMetadata("gadgetBlock")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onUpdate(SchedulerEvent e) {
		if(e.getType() == SchedulerType.TICK) {
			for(Player p : gravityBlocks.keySet()) {
				if(!p.isOnline() || Gadgets.getPlayerGadget(p) != Gadgets.GRAVIDADE || p.getWorld() != gravityBlocks.get(p).getLocation().getWorld()) {
					gravityBlocks.get(p).setType(Material.AIR);
					gravityBlocks.get(p).getRelative(BlockFace.UP).setType(Material.AIR);
					gravityBlocks.get(p).getRelative(BlockFace.DOWN).setType(Material.AIR);
					gravityBlocks.remove(gravityBlocks.get(p));
					gravityBlocks.remove(p);
					return;
				}
			}
 			for(Block b : gravityBlocks.values()) {
				new UtilParticle(UtilParticle.ParticleType.SPELL, 3.80000000074505806D, 1, 3.80000001192092896D).sendToLocation(b.getLocation());
			    new UtilParticle(UtilParticle.ParticleType.REDSTONE, 0.60000001192092896D, 2, 0.60000001192092896D).sendToLocation(b.getLocation());
				  for(Player p : Bukkit.getOnlinePlayers()) {
					 if(p.getLocation().distance(b.getLocation()) <= 8.5) {
						  Gravity.inGravity.add(p);
					 }else{
						 if(Gravity.inGravity.contains(p)) {
							 Gravity.inGravity.remove(p);
						 }
					 }
				  }
			  }
		   }
		}
	
	public GravityGadget() {
		super(UltraGadgets.getCfg().gravidadeCooldown*1000, Gadgets.GRAVIDADE.toString(), Gadgets.GRAVIDADE, Material.IRON_FENCE);
	}

	@Override
	void onInteract(Player p) {
		buildStation(p);
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

package br.com.floodeer.ultragadgets.gadgets;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.util.ParticleEffect;
import br.com.floodeer.ultragadgets.util.UtilCooldown;
import br.com.floodeer.ultragadgets.util.UtilParticle;
import br.com.floodeer.ultragadgets.util.UtilParticle.ParticleType;
import br.com.floodeer.ultragadgets.util.UtilTitle;

public class IceBombGadget extends Gadget implements Listener {

	public IceBombGadget() {
		super(UltraGadgets.getCfg().iceBombCooldown * 1000, Gadgets.ICE_BOMB.toString(), Gadgets.ICE_BOMB, Material.ICE);
	}

	@Override
	void onInteract(final Player p) {
		final Item d = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.ICE));
		d.setMetadata("pickup", new FixedMetadataValue(UltraGadgets.get(), "pickup"));
		d.setVelocity(p.getEyeLocation().getDirection());
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(UltraGadgets.get(), new Runnable() {
			public void run() {
				new UtilParticle(ParticleType.EXPLOSION_LARGE, 0.0D, 2, 0.0D).sendToLocation(d.getLocation());
				p.playSound(p.getLocation(), Sound.EXPLODE, 10.0F, 1.0F);
				for (int x1 = 0; x1 <= 16; x1++) {
					double x = 0.0D;
					double y = 0.0D;
					double z = 0.0D;
					Location eLoc = d.getLocation();
					World w = eLoc.getWorld();
					Location bLoc = d.getLocation();
					x = -0.5F + (float) (Math.random() * 1.5D);
					y = 1.0D;
					z = -0.5F + (float) (Math.random() * 1.5D);
					@SuppressWarnings("deprecation")
					FallingBlock fb = w.spawnFallingBlock(bLoc, Material.ICE, (byte) 0);
					fb.setMetadata("gadget", new FixedMetadataValue(UltraGadgets.get(), null));
					fb.setDropItem(false);
					fb.setVelocity(new Vector(x, y, z));
				}
				d.remove();
			}
		}, 40L);
	}

	@Override
	void onCooldown(Player p) {
		long cooldown = UtilCooldown.getCooldown(p, this.gadgetName) / 1000;
		UtilTitle title = new UtilTitle(
				UltraGadgets.getCfg().title.replaceAll("<cooldown>", String.valueOf(cooldown)).replaceAll("<gadget>",
						Gadgets.getPlayerGadget(p).toString().toLowerCase().replaceAll("_", "")),
				UltraGadgets.getCfg().subtitle.replaceAll("<cooldown>", String.valueOf(cooldown))
						.replaceAll("<gadget>", Gadgets.getPlayerGadget(p).toString().toLowerCase().replaceAll("_", ""))
						.replaceAll("&", "§"),
				6, 8, 6);
		title.setTimingsToTicks();
		title.send(p);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void EntityChange(EntityChangeBlockEvent e) {
		if (e.getEntityType() == EntityType.FALLING_BLOCK) {
			FallingBlock fallingBlock = (FallingBlock) e.getEntity();
			if (((fallingBlock.getBlockId() == Material.ICE.getId())
					|| (fallingBlock.getBlockId() == Material.FIRE.getId())) && (fallingBlock.hasMetadata("gadget"))) {
				ParticleEffect.SPELL_WITCH.display(0, 0, 0, 1.5F, 20, e.getBlock().getLocation(), 120*5);
				ParticleEffect.WATER_SPLASH.display(0, 0, 0, 1.5F, 20, e.getBlock().getLocation(), 120*5);
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		if(e.getItem().hasMetadata("pick")) {
			e.setCancelled(true);
		}
	}
}

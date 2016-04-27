package br.com.floodeer.ultragadgets.menus;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Particle;
import br.com.floodeer.ultragadgets.util.ItemFactory;
import br.com.floodeer.ultragadgets.util.UtilMenu;

public class ParticlesMenu extends Menu implements Listener {
	
	public ParticlesMenu() {
		super(UltraGadgets.getCfg().particlesMenu.replaceAll("&", "§"), 3);
	}
	
	public static void show(Player p) {
		UtilMenu particles = new UtilMenu(UltraGadgets.get(), p, UltraGadgets.getCfg().particlesMenu.replaceAll("&", "§"), 6);
		particles.setItem(10, ItemFactory.buildItemStack(Material.REDSTONE, "§cHelix"));
		particles.setItem(11, ItemFactory.buildItemStack(Material.SNOW_BALL, "§cFrost"));
		particles.setItem(12, ItemFactory.buildItemStack(Material.WEB, "§cTornado"));
		particles.setItem(13, ItemFactory.buildItemStack(Material.WATER_LILY, "§cLilly"));
		particles.setItem(14, ItemFactory.buildItemStack(Material.BLAZE_ROD, "§cFurioso"));
		particles.setItem(15, ItemFactory.buildItemStack(Material.SNOW_BLOCK, "§cNuvem"));
		particles.setItem(16, ItemFactory.buildItemStack(Material.WATER_BUCKET, "§cAgua"));
		particles.setItem(19, ItemFactory.buildItemStack(Material.LAVA_BUCKET, "§cLava"));
		particles.setItem(20, ItemFactory.buildItemStack(Material.FLINT_AND_STEEL, "§cFlame"));
		particles.setItem(21, ItemFactory.buildItemStack(Material.EMERALD, "§cHappy Villager"));
		particles.setItem(22, ItemFactory.buildItemStack(Material.BLAZE_POWDER, "§cAngry Villager"));
		particles.setItem(23, ItemFactory.buildItemStack(Material.RED_ROSE, "§cHeart"));
		particles.setItem(24, ItemFactory.buildItemStack(Material.ENCHANTMENT_TABLE, "§cMagic"));
		particles.setItem(25, ItemFactory.buildItemStack(Material.FIREWORK, "§cFirework"));
		particles.setItem(28, ItemFactory.buildItemStack(Material.NETHER_STAR, "§cShield"));
		if(Particle.hasParticle(p)) {
			particles.setItem(49, ItemFactory.buildGlowedItemStack(Material.BARRIER, "§cResetar partícula", ItemFactory.colorList(Arrays.asList("§7Clique para remover sua particula atual!"))));
		}else{
			particles.setItem(49, ItemFactory.buildItemStack(Material.BARRIER, "§cResetar partícula", ItemFactory.colorList(Arrays.asList("§7Remover particulas"))));
		}
		particles.build();
		particles.showMenu(p);
	}

	@Override
	public void onClick(Player p, int slot) {
	  if(slot == 10) {
		  Particle.active(p, Particle.HELIX);
		  p.closeInventory();
	  }else if(slot == 11) {
		  Particle.active(p, Particle.FROZEN);
		  p.closeInventory();
	  }else if(slot == 12) {
		  Particle.active(p, Particle.TORNADO);
		  p.closeInventory();
	  }else if(slot == 13) {
		  Particle.active(p, Particle.LILLY);
		  p.closeInventory();
	  }else if(slot == 14) {
		  Particle.active(p, Particle.FURIOUS);
		  p.closeInventory();
	  }else if(slot == 15) {
		  Particle.active(p, Particle.CLOUD);
		  p.closeInventory();
	  }else if(slot == 16) {
		  Particle.active(p, Particle.DRIP_WATER);
		  p.closeInventory();
	  }else if(slot == 19) {
		  Particle.active(p, Particle.DRIP_LAVA);
		  p.closeInventory();
	  }else if(slot == 20) {
		  Particle.active(p, Particle.FLAMES);
		  p.closeInventory();
	  }else if(slot == 21) {
		  Particle.active(p, Particle.HAPPY_VILLAGER);
		  p.closeInventory();
	  }else if(slot == 22) {
		  Particle.active(p, Particle.ANGRY_VILLAGER);
		  p.closeInventory();
	  }else if(slot == 23) {
		  Particle.active(p, Particle.HEART);
		  p.closeInventory();
	  }else if(slot == 24) {
		  Particle.active(p, Particle.MAGIC);
		  p.closeInventory();
	  }else if(slot == 25) {
		  Particle.active(p, Particle.FIREWORK);
		  p.closeInventory();
	  }else if(slot == 28) {
		  Particle.active(p, Particle.SHIELD);
		  p.closeInventory();
	  }else if(slot == 49) {
		  Particle.remove(p);
		  p.closeInventory();
	  }
	}

	@Override
	public void onClose(Player p) {}

	@Override
	public void onClickAir(Player p) {}

}

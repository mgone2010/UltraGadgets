package br.com.floodeer.ultragadgets.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.util.ItemFactory;
import br.com.floodeer.ultragadgets.util.UtilMenu;

public class MainMenu extends Menu implements Listener {

	public MainMenu() {
		super(UltraGadgets.getCfg().mainMenu.replaceAll("&", "§"), 6);
	}
	
	public static void show(Player p) {
		UtilMenu menu = new UtilMenu(UltraGadgets.get(), UltraGadgets.getCfg().mainMenu.replaceAll("&", "§"), 6);
		menu.setItem(4, ItemFactory.buildItemStack(Material.PISTON_BASE, "§bGadgets", null, 1, (byte)0));
		menu.setItem(11, ItemFactory.buildItemStack(Material.BONE, "§bPets", null, 1, (byte)0));
		menu.setItem(15, ItemFactory.buildItemStack(Material.NETHER_STAR, "§bPartículas", null, 1, (byte)0));
		menu.setItem(22, ItemFactory.buildItemStack(Material.SKULL_ITEM, "§bFantasias", null, 1, (byte)1));
		menu.setItem(29, ItemFactory.buildItemStack(Material.LEATHER_CHESTPLATE, "§bGuarda Roupa", null, 1, (byte)0));
		menu.setItem(40, ItemFactory.buildItemStack(Material.DIAMOND_HELMET, "§bHats", null, 1, (byte)0));
		menu.setItem(33, ItemFactory.buildItemStack(Material.SADDLE, "§bMontaria", null, 1, (byte)0));
		menu.build();
		menu.showMenu(p);
	}

	@Override
	public void onClick(Player p, int slot) {
		if(slot == 4) {
			GadgetsMenu.show(p);
		}else if(slot == 15) {
			ParticlesMenu.show(p);
		}else if(slot == 11) {
			PetMenu.show(p);
		}
	}

	@Override
	public void onClose(Player p) {}

	@Override
	public void onClickAir(Player p) {}
}

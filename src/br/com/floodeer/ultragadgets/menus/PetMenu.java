package br.com.floodeer.ultragadgets.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Pets;
import br.com.floodeer.ultragadgets.pets.PetSheep;
import br.com.floodeer.ultragadgets.pets.PetWolf;
import br.com.floodeer.ultragadgets.util.ItemFactory;
import br.com.floodeer.ultragadgets.util.UtilMenu;

public class PetMenu extends Menu implements Listener {

	public PetMenu() {
		super("DevPetMenu", 6);
	}
	
	public static void show(Player p) {
		UtilMenu pets = new UtilMenu(UltraGadgets.get(), p, "DevPetMenu", 6);
		pets.setItem(10, ItemFactory.buildItemStack(Material.WOOL, "§bOvelha"));
		pets.setItem(11, ItemFactory.buildItemStack(Material.BONE, "§bCachorro"));
		pets.build();
		pets.showMenu(p);
	}

	@Override
	public void onClick(Player p, int slot) {
		if(slot == 10) {
			if(Pets.hasPetSpawned(p)) {
				Pets.getPetEntity(p).despawn();
			}
			PetSheep sheep = new PetSheep(p);
			sheep.setName("&b" + p.getName() + "'s &fpet");
			p.closeInventory();
		}else if(slot == 11) {
			if(Pets.hasPetSpawned(p)) {
				Pets.getPetEntity(p).despawn();
			}
			PetWolf sheep = new PetWolf(p);
			sheep.setName("&b" + p.getName() + "'s &fpet");
			p.closeInventory();
		}
	}

	@Override
	public void onClose(Player p) {}

	@Override
	public void onClickAir(Player p) {}
}

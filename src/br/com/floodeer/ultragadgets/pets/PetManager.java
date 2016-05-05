package br.com.floodeer.ultragadgets.pets;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Pets;
import br.com.floodeer.ultragadgets.util.AnvilGUI;
import br.com.floodeer.ultragadgets.util.EntityUtils;
import br.com.floodeer.ultragadgets.util.ItemFactory;
import br.com.floodeer.ultragadgets.util.UtilMenu;
import br.com.floodeer.ultragadgets.util.AnvilGUI.AnvilClickEvent;

public class PetManager implements Listener {

	public static HashMap<UUID, Entity> petEntity = new HashMap<>();
	public static HashMap<UUID, Pet> petType = new HashMap<>();

	public static void create(Player p, Entity ent, Pet pet) {
		petEntity.put(p.getUniqueId(), ent);
		petType.put(p.getUniqueId(), pet);
		Pets.setPlayerPetValue(p, Pets.fromEntity(ent.getType()));
		PetCreator.criarPet((LivingEntity)ent, p.getUniqueId());
	}
	
	public static void petMenu(Player p) {
		UtilMenu menu = new UtilMenu(UltraGadgets.get(), p, "§8Seu pet", 5);
		menu.setItem(2, ItemFactory.buildItemStack(Material.CAKE, "§7Extra"));
		menu.setItem(4, ItemFactory.buildItemStack(Material.ENDER_PEARL, "§7Teleportar ao pet"));
		menu.setItem(6, ItemFactory.buildItemStack(Material.REDSTONE_COMPARATOR, "§7Configurar Path/Pet AI"));
		menu.setItem(22, ItemFactory.buildItemStack(Material.EYE_OF_ENDER, "§7Puxar pet"));
		menu.setItem(38, ItemFactory.buildItemStack(Material.NAME_TAG, "§7Renomear pet"));
		menu.setItem(40, ItemFactory.buildItemStack(Material.LEASH, "§7Amarrar pet"));
		menu.setItem(42, ItemFactory.buildGlowedItemStack(Material.NETHER_STAR, "§7Transformar pet"));
		menu.build();
		menu.showMenu(p);
	}
	
	public static void extra(Player p) {
		UtilMenu menu = new UtilMenu(UltraGadgets.get(), p, "§8Pet Extras", 1);
		if(Pets.getEntityPet(p).getType() == EntityType.SHEEP) {
		menu.setItem(4, ItemFactory.buildItemStack(Material.WOOL, "§7Dropar lã (Clique!)"));
		}else if(Pets.getEntityPet(p).getType() == EntityType.WOLF){
			menu.setItem(4, ItemFactory.buildItemStack(Material.BLAZE_POWDER, "§7Bravo (Clique!)"));
		}
		
		menu.build();
		menu.showMenu(p);
	}

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (e.getRightClicked().hasMetadata("ugPet")) {
			if (p.isSneaking() && p.getItemInHand() != null && p.getItemInHand().getType() == Material.AIR) {
				petMenu(p);
			}
		}
	}
	
	@EventHandler
	public void interact(InventoryClickEvent e) {
		if(e.getInventory().getName().equalsIgnoreCase("§8Seu pet")) {
			final Player p = (Player)e.getWhoClicked();
			e.setCancelled(true);
			if(e.getRawSlot() == 2) {
		        p.closeInventory();
				extra(p);
			}else if(e.getRawSlot() == 4) {
				Entity ent = Pets.getEntityPet(p);
				if(ent.isValid() && ent != null) {
					ent.teleport(p.getLocation().clone().add(0, 0.5, 0));
			        p.closeInventory();
				}
			}else if(e.getRawSlot() == 6) {
				//TODO
			}else if(e.getRawSlot() == 22) {
		        p.closeInventory();
				Entity ent = Pets.getEntityPet(p);
				if(ent.isValid() && ent != null) {
					if(EntityUtils.isGrounded(ent))
					p.teleport(ent.getLocation());
					else
						p.sendMessage("§cLocal inseguro.");
				}
			}else if(e.getRawSlot() == 38) {
		        p.closeInventory();
				  final AnvilGUI gui = new AnvilGUI(p, new AnvilGUI.AnvilClickEventHandler() {
					@Override
					public void onAnvilClick(AnvilClickEvent event) {
						if(event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT){
						String name = event.getName();
					    Pets.getPetEntity(p).setName(name);
					    event.setWillClose(true);
		                event.setWillDestroy(true);
		                UltraGadgets.getUPlayer(p.getUniqueId()).setPetName(Pets.getPetEntity(p), name);
						}else{
							  event.setWillClose(false);
				              event.setWillDestroy(false);
						}	
					}
				});
				 gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, ItemFactory.buildGlowedItemStack(Material.PAPER, "§eDigite o nome", Arrays.asList("§7Use §c& §7para cores")));
			     gui.open();
			}else if(e.getRawSlot() == 40) {
		        p.closeInventory();
				Entity entity = Pets.getPetEntity(p).getEntity();
		        p.setLeashHolder(entity);
			}
		}else if(e.getInventory().getName().equalsIgnoreCase("§8Pet Extras")) {
			final Player p = (Player)e.getWhoClicked();
			e.setCancelled(true);
	        p.closeInventory();
	        if(e.getRawSlot() == 4) {
	        	Pet pet = Pets.getPetEntity(p);
	        	if(pet.isAngry()) {
	        		pet.setAngry(false);
	        	}else{
	        		pet.setAngry(true);
	        	}
	        }
		}
	}
}

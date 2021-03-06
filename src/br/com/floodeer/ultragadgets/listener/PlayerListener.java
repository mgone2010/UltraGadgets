package br.com.floodeer.ultragadgets.listener;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Maps;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.UltraPlayer;
import br.com.floodeer.ultragadgets.enumeration.Particle;
import br.com.floodeer.ultragadgets.enumeration.Pets;
import br.com.floodeer.ultragadgets.menus.MainMenu;
import br.com.floodeer.ultragadgets.pets.PetManager;
import br.com.floodeer.ultragadgets.storage.PlayerDataFile;
import br.com.floodeer.ultragadgets.storage.PlayerDataYaml;
import br.com.floodeer.ultragadgets.util.ItemFactory;

public class PlayerListener implements Listener {
	
	public static Map<UUID, UltraPlayer> ugPlayers = Maps.newHashMap();
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onDrop(PlayerDropItemEvent e) {
		if(ItemFactory.hasDisplayName(e.getItemDrop().getItemStack(), UltraGadgets.getCfg().itemNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemDrop().getItemStack(), UltraGadgets.getCfg().iceBombNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemDrop().getItemStack(), UltraGadgets.getCfg().bombaGadgetNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemDrop().getItemStack(), UltraGadgets.getCfg().meowGadgetNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemDrop().getItemStack(), UltraGadgets.getCfg().partyPopperNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemDrop().getItemStack(), UltraGadgets.getCfg().paintballGunNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemDrop().getItemStack(), UltraGadgets.getCfg().paraquedasNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemDrop().getItemStack(), UltraGadgets.getCfg().trampolimNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemDrop().getItemStack(), UltraGadgets.getCfg().vectorTNTNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemDrop().getItemStack(), UltraGadgets.getCfg().witherShootNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemDrop().getItemStack(), UltraGadgets.getCfg().djNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemDrop().getItemStack(), UltraGadgets.getCfg().gravidadeNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemDrop().getItemStack(), UltraGadgets.getCfg().fumeganteNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemDrop().getItemStack(), UltraGadgets.getCfg().explosivePoopNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemDrop().getItemStack(), UltraGadgets.getCfg().discoBallNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(ItemFactory.hasDisplayName(e.getCurrentItem(), UltraGadgets.getCfg().itemNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getCurrentItem(), UltraGadgets.getCfg().iceBombNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getCurrentItem(), UltraGadgets.getCfg().bombaGadgetNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getCurrentItem(), UltraGadgets.getCfg().meowGadgetNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getCurrentItem(), UltraGadgets.getCfg().partyPopperNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getCurrentItem(), UltraGadgets.getCfg().paintballGunNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getCurrentItem(), UltraGadgets.getCfg().paraquedasNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getCurrentItem(), UltraGadgets.getCfg().trampolimNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getCurrentItem(), UltraGadgets.getCfg().vectorTNTNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getCurrentItem(), UltraGadgets.getCfg().witherShootNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getCurrentItem(), UltraGadgets.getCfg().gravidadeNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getCurrentItem(), UltraGadgets.getCfg().djNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getCurrentItem(), UltraGadgets.getCfg().fumeganteNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getCurrentItem(), UltraGadgets.getCfg().explosivePoopNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getCurrentItem(), UltraGadgets.getCfg().discoBallNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}
	}
	
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(ItemFactory.hasDisplayName(e.getItemInHand(), UltraGadgets.getCfg().itemNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemInHand(), UltraGadgets.getCfg().trampolimNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemInHand(), UltraGadgets.getCfg().vectorTNTNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemInHand(), UltraGadgets.getCfg().iceBombNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemInHand(), UltraGadgets.getCfg().partyPopperNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemInHand(), UltraGadgets.getCfg().witherShootNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemInHand(), UltraGadgets.getCfg().djNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemInHand(), UltraGadgets.getCfg().gravidadeNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}else if(ItemFactory.hasDisplayName(e.getItemInHand(), UltraGadgets.getCfg().discoBallNome.replaceAll("&", "§"))) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(ItemFactory.hasDisplayName(e.getItem(), UltraGadgets.getCfg().itemNome.replaceAll("&", "§"))) {
			MainMenu.show(e.getPlayer());
		}
		if(ItemFactory.hasDisplayName(e.getItem(), UltraGadgets.getCfg().wizardNome.replaceAll("&", "§"))) {
			e.setUseInteractedBlock(Result.DENY);
		}
	}

	@EventHandler
	public void onJoin(final PlayerJoinEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				PlayerDataFile data = PlayerDataYaml.getPlayerYaml(e.getPlayer());
				data.add("UGPlayer.Nome", e.getPlayer().getName());
				data.add("UGPlayer.GadgetSelecionado", "Nenhum");
				data.save();
				e.getPlayer().getInventory().setItem(UltraGadgets.getCfg().itemSlot, 
				ItemFactory.buildItemStack(Material.valueOf(UltraGadgets.getCfg().ugItem), 
				UltraGadgets.getCfg().itemNome.replaceAll("&", "§"), null, 1, (byte)UltraGadgets.getCfg().ugItemData));
				if(!ugPlayers.containsKey(e.getPlayer().getUniqueId())) {
					ugPlayers.put(e.getPlayer().getUniqueId(), new UltraPlayer(e.getPlayer().getUniqueId()));
				}
			}
		}.runTaskLater(UltraGadgets.get(), 5);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(Particle.hasParticle(e.getPlayer())) {
			Particle.remove(e.getPlayer());
		}
		if(Pets.hasPetSpawned(e.getPlayer())) {
			Pets.getPetEntity(e.getPlayer()).despawn();
			if(PetManager.petEntity.containsKey(e.getPlayer().getUniqueId())) {
				PetManager.petEntity.remove(e.getPlayer().getUniqueId());
			}
			if(PetManager.petType.containsKey(e.getPlayer().getUniqueId())) {
				PetManager.petType.remove(e.getPlayer().getUniqueId());
			}
		}
	}
	
	public static UltraPlayer get(UUID uuid) {
		if(ugPlayers.containsKey(uuid)) {
			return ugPlayers.get(uuid);
		}
		return null;
	}
}

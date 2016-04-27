package br.com.floodeer.ultragadgets.enumeration;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.eventAPI.PlayerGadgetSelectEvent;
import br.com.floodeer.ultragadgets.storage.PlayerDataFile;
import br.com.floodeer.ultragadgets.storage.PlayerDataYaml;
import br.com.floodeer.ultragadgets.util.ItemFactory;

public enum Gadgets {

	NENHUM("NENHUM"), 
	BOMBA("BOMBA"), 
	MEOW("MEOW"), 
	PARTY_POPPER("PARTY_POPPER"), 
	PAINTBALL("PAINTBALL_GUN"), 
	PARAQUEDAS("PARAQUEDAS"), 
	TRAMPOLIM("TRAMPOLIM"), 
	FOGUETE("FOGUETE"), 
	GRAVIDADE("BLOCO_DE_GRAVIDADE"), 
	VECTORTNT("VECTOR_TNT"), WITHER_SHOOT("WITHER_SHOOT"), 
    VAMPIRE("VAMPIRE"), 
    EXPLOSIVE_POOP("EXPLOSIVE_POOP"), 
    DISCO_ARMOR("DISCO_ARMOR"),
    SMOKE_BOMB("SMOKE_BOMB"), 
    ICE_BOMB("ICE_BOMB"), 
    DISCO_BALL("DISCO_BALL"),
    WIZARD("WIZARD"),
    DJ("DJ");

	private String gadget;

	Gadgets(String gadget) {
		this.gadget = gadget;
	}

	@Override
	public String toString() {
		return gadget;
	}

	public static Gadgets fromString(String s) {
		switch (s) {
		case "BOMBA":
			return BOMBA;

		case "MEOW":
			return Gadgets.MEOW;

		case "PARTY_POPPER":
			return Gadgets.PARTY_POPPER;

		case "PAINTBALL_GUN":
			return PAINTBALL;

		case "PARAQUEDAS":
			return Gadgets.PARAQUEDAS;

		case "TRAMPOLIM":
			return Gadgets.TRAMPOLIM;
			
		case "FOGUETE":
			return Gadgets.FOGUETE;
			
		case "BLOCO_DE_GRAVIDADE":
			return Gadgets.GRAVIDADE;
			
		case "VECTOR_TNT":
			return Gadgets.VECTORTNT;

		case "ICE_BOMB":
			return Gadgets.ICE_BOMB;
			
		case "WITHER_SHOOT":
			return Gadgets.WITHER_SHOOT;
			
		case "DJ":
			return Gadgets.DJ;
			
		case "WIZARD":
			return Gadgets.WIZARD;
		}
		return null;
	}

	public static Gadgets getPlayerGadget(Player p) {
		PlayerDataFile data = PlayerDataYaml.getPlayerYaml(p);
		if (hasSelected(p)) {
			return fromString(data.getString("UGPlayer.GadgetSelecionado").toUpperCase());
		} else {
			return Gadgets.NENHUM;
		}
	}

	public static boolean hasSelected(Player p) {
		PlayerDataFile data = PlayerDataYaml.getPlayerYaml(p);
		if (!data.getString("UGPlayer.GadgetSelecionado").equalsIgnoreCase("nenhum"))
			return true;
		return false;
	}

	public static boolean hasGadgetSelected(Player p, Gadgets g) {
		if (getPlayerGadget(p) == g)
			return true;
		return false;
	}

	public static void selectGadget(Player p, Gadgets gadget) {
		Gadgets atGadget = getPlayerGadget(p);
		PlayerGadgetSelectEvent event = new PlayerGadgetSelectEvent(p, gadget, atGadget);
		Bukkit.getPluginManager().callEvent(event);
		if(event.isCancelled()) return;
		PlayerDataFile data = PlayerDataYaml.getPlayerYaml(p);
		data.set("UGPlayer.GadgetSelecionado", gadget.toString().toUpperCase());
		data.save();
		switch (gadget) {
		case BOMBA:
			p.getInventory().setItem(UltraGadgets.getCfg().gadgetSlot, ItemFactory.buildItemStackArrays(
					Material.CLAY_BALL, UltraGadgets.getCfg().bombaGadgetNome.replaceAll("&", "§")));
			break;
		case DISCO_ARMOR:
			break;
		case EXPLOSIVE_POOP:
			break;
		case FOGUETE:
			break;
		case GRAVIDADE:
			p.getInventory().setItem(UltraGadgets.getCfg().gadgetSlot, ItemFactory.buildItemStackArrays(
					Material.IRON_FENCE, UltraGadgets.getCfg().gravidadeNome.replaceAll("&", "§")));
			break;
		case MEOW:
			p.getInventory().setItem(UltraGadgets.getCfg().gadgetSlot, ItemFactory.buildItemStackArrays(
					Material.BLAZE_ROD, UltraGadgets.getCfg().meowGadgetNome.replaceAll("&", "§")));
			break;
		case NENHUM:
			p.getInventory().setItem(UltraGadgets.getCfg().gadgetSlot, new ItemStack(Material.AIR));
			break;
		case PAINTBALL:
			p.getInventory().setItem(UltraGadgets.getCfg().gadgetSlot, ItemFactory.buildItemStackArrays(
					Material.DIAMOND_BARDING, UltraGadgets.getCfg().paintballGunNome.replaceAll("&", "§")));
			break;
		case PARAQUEDAS:
			p.getInventory().setItem(UltraGadgets.getCfg().gadgetSlot, ItemFactory.buildItemStackArrays(Material.LEASH,
					UltraGadgets.getCfg().paraquedasNome.replaceAll("&", "§")));
			break;
		case PARTY_POPPER:
			p.getInventory().setItem(UltraGadgets.getCfg().gadgetSlot, ItemFactory.buildItemStackArrays(
					Material.ENDER_CHEST, UltraGadgets.getCfg().partyPopperNome.replaceAll("&", "§")));
			break;
		case SMOKE_BOMB:
			break;
		case TRAMPOLIM:
			p.getInventory().setItem(UltraGadgets.getCfg().gadgetSlot, ItemFactory.buildItemStackArrays(Material.HOPPER,
					UltraGadgets.getCfg().trampolimNome.replaceAll("&", "§")));
			break;
		case VAMPIRE:
			break;
		case VECTORTNT:
			p.getInventory().setItem(UltraGadgets.getCfg().gadgetSlot, ItemFactory.buildItemStackArrays(Material.TNT,
					UltraGadgets.getCfg().vectorTNTNome.replaceAll("&", "§")));
			break;
		case WITHER_SHOOT:
			p.getInventory().setItem(UltraGadgets.getCfg().gadgetSlot, ItemFactory.buildItemStackArrays(Material.COAL,
					UltraGadgets.getCfg().witherShootNome.replaceAll("&", "§")));
			break;
			
		case ICE_BOMB:
			p.getInventory().setItem(UltraGadgets.getCfg().gadgetSlot, ItemFactory.buildItemStackArrays(Material.ICE,
					UltraGadgets.getCfg().iceBombNome.replaceAll("&", "§")));
			break;
			
		case DJ:
			p.getInventory().setItem(UltraGadgets.getCfg().gadgetSlot, ItemFactory.buildItemStackArrays(Material.JUKEBOX,
					UltraGadgets.getCfg().djNome.replaceAll("&", "§")));
			break;
			
		case WIZARD:
			p.getInventory().setItem(UltraGadgets.getCfg().gadgetSlot, ItemFactory.buildItemStackArrays(Material.IRON_HOE,
					UltraGadgets.getCfg().wizardNome.replaceAll("&", "§")));
			break;
		default:
			break;
		}
	}
}

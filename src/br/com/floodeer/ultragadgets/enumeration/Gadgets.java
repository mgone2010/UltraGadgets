package br.com.floodeer.ultragadgets.enumeration;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.eventAPI.PlayerSelectGadgetEvent;
import br.com.floodeer.ultragadgets.storage.PlayerDataFile;
import br.com.floodeer.ultragadgets.storage.PlayerDataYaml;
import br.com.floodeer.ultragadgets.util.ItemFactory;

public enum Gadgets {

	NENHUM("NENHUM"), 
	BOMBA("BOMBA"), 
	FUN_GUN("FUN_GUN"), 
	PARTY_POPPER("PARTY_POPPER"), 
	PAINTBALL("PAINTBALL_GUN"), 
	PARAQUEDAS("PARAQUEDAS"), 
	TRAMPOLIM("TRAMPOLIM"), 
	FOGUETE("FOGUETE"), 
	GRAVIDADE("BLOCO_DE_GRAVIDADE"), 
	VECTORTNT("VECTOR_TNT"), 
	WITHER_SHOOT("WITHER_SHOOT"), 
    EXPLOSIVE_POOP("EXPLOSIVE_POOP"), 
    SMOKE_BOMB("SMOKE_BOMB"), 
    ICE_BOMB("ICE_BOMB"), 
    DISCO_BALL("DISCO_BALL"),
    WIZARD("WIZARD"),
    DJ("DJ"),
    FUMEGANTE("FUMEGANTE");

	private String gadget;

	Gadgets(String gadget) {
		this.gadget = gadget;
	}

	@Override
	public String toString() {
		return gadget;
	}

	public static Gadgets fromString(String s) {
		for(Gadgets g : Gadgets.values()) {
			if(g.toString().equalsIgnoreCase(s)) {
				return g;
			}
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
		PlayerSelectGadgetEvent event = new PlayerSelectGadgetEvent(p, gadget, atGadget);
		Bukkit.getPluginManager().callEvent(event);
		if(event.isCancelled()) return;
		PlayerDataFile data = PlayerDataYaml.getPlayerYaml(p);
		data.set("UGPlayer.GadgetSelecionado", gadget.toString().toUpperCase());
		data.save();
		int slot = UltraGadgets.getCfg().gadgetSlot;
		switch (gadget) {
		case BOMBA:
			p.getInventory().setItem(slot, ItemFactory.buildItemStackArrays(Material.CLAY_BALL, UltraGadgets.getCfg().bombaGadgetNome.replaceAll("&", "§")));
			break;
		case EXPLOSIVE_POOP:
			p.getInventory().setItem(slot, ItemFactory.buildItemStackArrays(Material.INK_SACK, UltraGadgets.getCfg().explosivePoopNome.replaceAll("&", "§"), null, 1, (byte)3));
			break;
		case FOGUETE:
			break;
		case GRAVIDADE:
			p.getInventory().setItem(slot, ItemFactory.buildItemStackArrays(Material.IRON_FENCE, UltraGadgets.getCfg().gravidadeNome.replaceAll("&", "§")));
			break;
		case FUN_GUN:
			p.getInventory().setItem(slot, ItemFactory.buildItemStackArrays(Material.BLAZE_ROD, UltraGadgets.getCfg().meowGadgetNome.replaceAll("&", "§")));
			break;
		case NENHUM:
			p.getInventory().setItem(slot, new ItemStack(Material.AIR));
			break;
		case PAINTBALL:
			p.getInventory().setItem(slot, ItemFactory.buildItemStackArrays(Material.DIAMOND_BARDING, UltraGadgets.getCfg().paintballGunNome.replaceAll("&", "§")));
			break;
		case PARAQUEDAS:
			p.getInventory().setItem(slot, ItemFactory.buildItemStackArrays(Material.LEASH,UltraGadgets.getCfg().paraquedasNome.replaceAll("&", "§")));
			break;
		case PARTY_POPPER:
			p.getInventory().setItem(slot, ItemFactory.buildItemStackArrays(Material.ENDER_CHEST, UltraGadgets.getCfg().partyPopperNome.replaceAll("&", "§")));
			break;
		case SMOKE_BOMB:
			p.getInventory().setItem(slot, ItemFactory.buildItemStackArrays(Material.COAL_BLOCK, UltraGadgets.getCfg().smokeBombNome.replaceAll("&", "§")));
			break;
		case TRAMPOLIM:
			p.getInventory().setItem(slot, ItemFactory.buildItemStackArrays(Material.HOPPER, UltraGadgets.getCfg().trampolimNome.replaceAll("&", "§")));
			break;
		case VECTORTNT:
			p.getInventory().setItem(slot, ItemFactory.buildItemStackArrays(Material.TNT,UltraGadgets.getCfg().vectorTNTNome.replaceAll("&", "§")));
			break;
		case WITHER_SHOOT:
			p.getInventory().setItem(slot, ItemFactory.buildItemStackArrays(Material.COAL,UltraGadgets.getCfg().witherShootNome.replaceAll("&", "§")));
			break;
			
		case ICE_BOMB:
			p.getInventory().setItem(slot, ItemFactory.buildItemStackArrays(Material.ICE,UltraGadgets.getCfg().iceBombNome.replaceAll("&", "§")));
			break;
			
		case DJ:
			p.getInventory().setItem(slot, ItemFactory.buildItemStackArrays(Material.JUKEBOX,UltraGadgets.getCfg().djNome.replaceAll("&", "§")));
			break;
			
		case WIZARD:
			p.getInventory().setItem(slot, ItemFactory.buildItemStackArrays(Material.IRON_HOE,UltraGadgets.getCfg().wizardNome.replaceAll("&", "§")));
			break;
		case FUMEGANTE:
			p.getInventory().setItem(slot, ItemFactory.buildItemStackArrays(Material.BLAZE_POWDER,UltraGadgets.getCfg().fumeganteNome.replaceAll("&", "§")));
			break;
		case DISCO_BALL:
			p.getInventory().setItem(slot, ItemFactory.buildItemStackArrays(Material.STAINED_GLASS, UltraGadgets.getCfg().discoBallNome.replaceAll("&", "§"), null, 1, (byte)12));
		    break;
		default:
			break;
		}
	}
}

package br.com.floodeer.ultragadgets.menus;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.config.ConfigFile;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.util.ItemFactory;
import br.com.floodeer.ultragadgets.util.UtilMenu;

public class GadgetsMenu extends Menu implements Listener {

	private static ConfigFile c = UltraGadgets.getCfg();

	public GadgetsMenu() {
		super(c.gadgetsMenu.replaceAll("&", "§"), 6);
	}

	public static void show(Player p) {
		UtilMenu gadgets = new UtilMenu(UltraGadgets.get(), p, UltraGadgets.getCfg().gadgetsMenu.replaceAll("&", "§"),
				6);
		if (!p.hasPermission("ug.gadgets.icebomb")) {
			gadgets.setItem(10, ItemFactory.buildItemStackArrays(Material.valueOf(c.noPermItem), "§cIce Bomb",Arrays.asList("§cSem permissões."), 1, (byte) c.noPermItemData));
		} else {
			if (Gadgets.getPlayerGadget(p) == Gadgets.ICE_BOMB) {
				gadgets.setItem(10, ItemFactory.buildGlowedItemStack(Material.ICE, c.iceBombNome.replaceAll("&", "§"),ItemFactory.colorList(c.iceBombLore)));
			} else {
				gadgets.setItem(10, ItemFactory.buildItemStack(Material.ICE, c.iceBombNome.replaceAll("&", "§"),ItemFactory.colorList(c.iceBombLore)));
			}
		}

		if (!p.hasPermission("ug.gadgets.bomba")) {
			gadgets.setItem(11, ItemFactory.buildItemStackArrays(Material.valueOf(c.noPermItem), "§cBomba",
					Arrays.asList("§cSem permissões."), 1, (byte) c.noPermItemData));
		} else {
			if (Gadgets.getPlayerGadget(p) == Gadgets.BOMBA) {
				gadgets.setItem(11, ItemFactory.buildGlowedItemStack(Material.CLAY_BALL,
						c.bombaGadgetNome.replaceAll("&", "§"), ItemFactory.colorList(c.bombaGadgetLore)));
			} else {
				gadgets.setItem(11, ItemFactory.buildItemStack(Material.CLAY_BALL, c.bombaGadgetNome.replaceAll("&", "§"),
						ItemFactory.colorList(c.bombaGadgetLore)));
			}
		}

		if (!p.hasPermission("ug.gadgets.meow")) {
			gadgets.setItem(12, ItemFactory.buildItemStackArrays(Material.valueOf(c.noPermItem), "§cMeow",
					Arrays.asList("§cSem permissões."), 1, (byte) c.noPermItemData));
		} else {
			if (Gadgets.getPlayerGadget(p) == Gadgets.FUN_GUN) {
				gadgets.setItem(12, ItemFactory.buildGlowedItemStack(Material.BLAZE_ROD,
						c.bombaGadgetNome.replaceAll("&", "§"), ItemFactory.colorList(c.meowGadgetLore)));
			} else {
				gadgets.setItem(12, ItemFactory.buildItemStack(Material.BLAZE_ROD, c.meowGadgetNome.replaceAll("&", "§"),
						ItemFactory.colorList(c.meowGadgetLore)));
			}
		}

		if (!p.hasPermission("ug.gadgets.partypopper")) {
			gadgets.setItem(13, ItemFactory.buildItemStackArrays(Material.valueOf(c.noPermItem), "§cPartyPopper",
					Arrays.asList("§cSem permissões."), 1, (byte) c.noPermItemData));
		} else {
			if (Gadgets.getPlayerGadget(p) == Gadgets.PARTY_POPPER) {
				gadgets.setItem(13, ItemFactory.buildGlowedItemStack(Material.ENDER_CHEST,
						c.partyPopperNome.replaceAll("&", "§"), ItemFactory.colorList(c.partyPopperLore)));
			} else {
				gadgets.setItem(13, ItemFactory.buildItemStack(Material.ENDER_CHEST,
						c.partyPopperNome.replaceAll("&", "§"), ItemFactory.colorList(c.partyPopperLore)));
			}
		}
		if (!p.hasPermission("ug.gadgets.paintballgun")) {
			gadgets.setItem(14, ItemFactory.buildItemStackArrays(Material.valueOf(c.noPermItem), "§cPaintballGun",
					Arrays.asList("§cSem permissões."), 1, (byte) c.noPermItemData));
		} else {
			if (Gadgets.getPlayerGadget(p) == Gadgets.PAINTBALL) {
				gadgets.setItem(14, ItemFactory.buildGlowedItemStack(Material.DIAMOND_BARDING,
						c.paintballGunNome.replaceAll("&", "§"), ItemFactory.colorList(c.paintballGunLore)));
			} else {
				gadgets.setItem(14, ItemFactory.buildItemStack(Material.DIAMOND_BARDING,
						c.paintballGunNome.replaceAll("&", "§"), ItemFactory.colorList(c.paintballGunLore)));
			}
		}

		if (!p.hasPermission("ug.gadgets.paraquedas")) {
			gadgets.setItem(15, ItemFactory.buildItemStackArrays(Material.valueOf(c.noPermItem), "§cParaquedas",
					Arrays.asList("§cSem permissões."), 1, (byte) c.noPermItemData));
		} else {
			if (Gadgets.getPlayerGadget(p) == Gadgets.PARAQUEDAS) {
				gadgets.setItem(15, ItemFactory.buildGlowedItemStack(Material.LEASH,
						c.paraquedasNome.replaceAll("&", "§"), ItemFactory.colorList(c.paraquedasLore)));
			} else {
				gadgets.setItem(15, ItemFactory.buildItemStack(Material.LEASH, c.paraquedasNome.replaceAll("&", "§"),
						ItemFactory.colorList(c.paraquedasLore)));
			}
		}

		if (!p.hasPermission("ug.gadgets.trampolim")) {
			gadgets.setItem(16, ItemFactory.buildItemStackArrays(Material.valueOf(c.noPermItem), "§cTrampolim",
					Arrays.asList("§cSem permissões."), 1, (byte) c.noPermItemData));
		} else {
			if (Gadgets.getPlayerGadget(p) == Gadgets.TRAMPOLIM) {
				gadgets.setItem(16, ItemFactory.buildGlowedItemStack(Material.HOPPER,
						c.trampolimNome.replaceAll("&", "§"), ItemFactory.colorList(c.trampolimLore)));
			} else {
				gadgets.setItem(16, ItemFactory.buildItemStack(Material.HOPPER, c.trampolimNome.replaceAll("&", "§"),
						ItemFactory.colorList(c.trampolimLore)));
			}
		}

		if (!p.hasPermission("ug.gadgets.vectorTNT")) {
			gadgets.setItem(19, ItemFactory.buildItemStackArrays(Material.valueOf(c.noPermItem), "§cVectorTNT", Arrays.asList("§cSem permissões."), 1, (byte) c.noPermItemData));
		} else {
			if (Gadgets.getPlayerGadget(p) == Gadgets.VECTORTNT) {
				gadgets.setItem(19, ItemFactory.buildGlowedItemStack(Material.TNT, c.vectorTNTNome.replaceAll("&", "§"), ItemFactory.colorList(c.vectorTNTLore)));
			} else {
				gadgets.setItem(19, ItemFactory.buildItemStack(Material.TNT, c.vectorTNTNome.replaceAll("&", "§"), ItemFactory.colorList(c.vectorTNTLore)));
			}
		}
		if (!p.hasPermission("ug.gadgets.witherShoot")) {
			gadgets.setItem(20, ItemFactory.buildItemStackArrays(Material.valueOf(c.noPermItem), "§cWitherShoot",Arrays.asList("§cSem permissões."), 1, (byte) c.noPermItemData));
		} else {
			if (Gadgets.getPlayerGadget(p) == Gadgets.WITHER_SHOOT) {
				gadgets.setItem(20, ItemFactory.buildGlowedItemStack(Material.COAL, c.witherShootNome.replaceAll("&", "§"), ItemFactory.colorList(c.witherShootLore)));
			} else {
				gadgets.setItem(20, ItemFactory.buildItemStack(Material.COAL, c.witherShootNome.replaceAll("&", "§"), ItemFactory.colorList(c.witherShootLore)));
			}
		}
		
		if (!p.hasPermission("ug.gadgets.dj")) {
			gadgets.setItem(21, ItemFactory.buildItemStackArrays(Material.valueOf(c.noPermItem), "§cDj",Arrays.asList("§cSem permissões."), 1, (byte) c.noPermItemData));
		} else {
			if (Gadgets.getPlayerGadget(p) == Gadgets.DJ) {
				gadgets.setItem(21, ItemFactory.buildGlowedItemStack(Material.JUKEBOX, c.djNome.replaceAll("&", "§"), ItemFactory.colorList(c.djLore)));
			} else {
				gadgets.setItem(21, ItemFactory.buildItemStack(Material.JUKEBOX, c.djNome.replaceAll("&", "§"), ItemFactory.colorList(c.djLore)));
			}
		}
		
		if (!p.hasPermission("ug.gadgets.gravidade")) {
			gadgets.setItem(22, ItemFactory.buildItemStackArrays(Material.valueOf(c.noPermItem), "§cGravidade",Arrays.asList("§cSem permissões."), 1, (byte) c.noPermItemData));
		} else {
			if (Gadgets.getPlayerGadget(p) == Gadgets.GRAVIDADE) {
				gadgets.setItem(22, ItemFactory.buildGlowedItemStack(Material.IRON_FENCE, c.gravidadeNome.replaceAll("&", "§"), ItemFactory.colorList(c.gravidadeLore)));
			} else {
				gadgets.setItem(22, ItemFactory.buildItemStack(Material.IRON_FENCE, c.gravidadeNome.replaceAll("&", "§"), ItemFactory.colorList(c.gravidadeLore)));
			}
		}
		
		if (!p.hasPermission("ug.gadgets.wizard")) {
			gadgets.setItem(23, ItemFactory.buildItemStackArrays(Material.valueOf(c.noPermItem), "§cWizard",Arrays.asList("§cSem permissões."), 1, (byte) c.noPermItemData));
		} else {
			if (Gadgets.getPlayerGadget(p) == Gadgets.WIZARD) {
				gadgets.setItem(23, ItemFactory.buildGlowedItemStack(Material.IRON_HOE, c.wizardNome.replaceAll("&", "§"), ItemFactory.colorList(c.wizardLore)));
			} else {
				gadgets.setItem(23, ItemFactory.buildItemStack(Material.IRON_HOE, c.wizardNome.replaceAll("&", "§"), ItemFactory.colorList(c.wizardLore)));
			}
		}

		if (!p.hasPermission("ug.gadgets.fumegante")) {
			gadgets.setItem(24, ItemFactory.buildItemStackArrays(Material.valueOf(c.noPermItem), "§cFumegante",Arrays.asList("§cSem permissões."), 1, (byte) c.noPermItemData));
		} else {
			if (Gadgets.getPlayerGadget(p) == Gadgets.FUMEGANTE) {
				gadgets.setItem(24, ItemFactory.buildGlowedItemStack(Material.BLAZE_POWDER, c.fumeganteNome.replaceAll("&", "§"), ItemFactory.colorList(c.fumeganteLore)));
			} else {
				gadgets.setItem(24, ItemFactory.buildItemStack(Material.BLAZE_POWDER, c.fumeganteNome.replaceAll("&", "§"), ItemFactory.colorList(c.fumeganteLore)));
			}
		}
		
		if (!p.hasPermission("ug.gadgets.explosivepoop")) {
			gadgets.setItem(25, ItemFactory.buildItemStackArrays(Material.valueOf(c.noPermItem), "§cExplosive Poop",Arrays.asList("§cSem permissões."), 1, (byte) c.noPermItemData));
		} else {
			if (Gadgets.getPlayerGadget(p) == Gadgets.EXPLOSIVE_POOP) {
				gadgets.setItem(25, ItemFactory.buildGlowedItemStackArrays(Material.INK_SACK, c.explosivePoopNome.replaceAll("&", "§"), ItemFactory.colorList(c.explosivePlore), 1, (byte)3));
			} else {
				gadgets.setItem(25, ItemFactory.buildItemStackArrays(Material.INK_SACK, c.explosivePoopNome.replaceAll("&", "§"), ItemFactory.colorList(c.explosivePlore), 1, (byte)3));
			}
		}
		
		if (!p.hasPermission("ug.gadgets.discoball")) {
			gadgets.setItem(28, ItemFactory.buildItemStackArrays(Material.valueOf(c.noPermItem), "§cDiscoBall",Arrays.asList("§cSem permissões."), 1, (byte) c.noPermItemData));
		} else {
			if (Gadgets.getPlayerGadget(p) == Gadgets.DISCO_BALL) {
				gadgets.setItem(28, ItemFactory.buildGlowedItemStackArrays(Material.STAINED_GLASS, c.discoBallNome.replaceAll("&", "§"), ItemFactory.colorList(c.discoBallLore), 1, (byte)12));
			} else {
				gadgets.setItem(28, ItemFactory.buildItemStackArrays(Material.STAINED_GLASS, c.discoBallNome.replaceAll("&", "§"), ItemFactory.colorList(c.discoBallLore), 1, (byte)12));
			}
		}
		
		if (!p.hasPermission("ug.gadgets.smokebomb")) {
			gadgets.setItem(29, ItemFactory.buildItemStackArrays(Material.valueOf(c.noPermItem), "§cSmokeBomb",Arrays.asList("§cSem permissões."), 1, (byte) c.noPermItemData));
		} else {
			if (Gadgets.getPlayerGadget(p) == Gadgets.SMOKE_BOMB) {
				gadgets.setItem(29, ItemFactory.buildGlowedItemStackArrays(Material.COAL_BLOCK, c.smokeBombNome.replaceAll("&", "§"), ItemFactory.colorList(c.smokebombLore), 1, (byte)0));
			} else {
				gadgets.setItem(29, ItemFactory.buildItemStackArrays(Material.COAL_BLOCK, c.smokeBombNome.replaceAll("&", "§"), ItemFactory.colorList(c.smokebombLore), 1, (byte)0));
			}
		}
		
		if(Gadgets.hasSelected(p)) {
			gadgets.setItem(49, ItemFactory.buildGlowedItemStack(Material.BARRIER, "§cResetar gadget", ItemFactory.colorList(Arrays.asList("§7Clique para remover seu gadget atual!"))));
		}else{
			gadgets.setItem(49, ItemFactory.buildItemStack(Material.BARRIER, "§cResetar gadget", ItemFactory.colorList(Arrays.asList("§7Remover gadgets"))));
		}

		gadgets.build();
		gadgets.showMenu(p);
	}

	@Override
	public void onClick(Player p, int slot) {
		if (slot == 10) {
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 0.5F);
			Gadgets.selectGadget(p, Gadgets.ICE_BOMB);
			p.closeInventory();
		} else if (slot == 11) {
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 0.5F);
			Gadgets.selectGadget(p, Gadgets.BOMBA);
			p.closeInventory();
		} else if (slot == 12) {
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 0.5F);
			Gadgets.selectGadget(p, Gadgets.FUN_GUN);
			p.closeInventory();
		} else if (slot == 13) {
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 0.5F);
			Gadgets.selectGadget(p, Gadgets.PARTY_POPPER);
			p.closeInventory();
		} else if (slot == 14) {
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 0.5F);
			Gadgets.selectGadget(p, Gadgets.PAINTBALL);
			p.closeInventory();
		} else if (slot == 15) {
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 0.5F);
			Gadgets.selectGadget(p, Gadgets.PARAQUEDAS);
			p.closeInventory();
		} else if (slot == 16) {
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 0.5F);
			Gadgets.selectGadget(p, Gadgets.TRAMPOLIM);
			p.closeInventory();
		} else if (slot == 19) {
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 0.5F);
			Gadgets.selectGadget(p, Gadgets.VECTORTNT);
			p.closeInventory();
		} else if (slot == 20) {
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 0.5F);
			Gadgets.selectGadget(p, Gadgets.WITHER_SHOOT);
			p.closeInventory();
		} else if (slot == 21) {
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 0.5F);
			Gadgets.selectGadget(p, Gadgets.DJ);
			p.closeInventory();
		}else if (slot == 22) {
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 0.5F);
			Gadgets.selectGadget(p, Gadgets.GRAVIDADE);
			p.closeInventory();
		}else if (slot == 23) {
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 0.5F);
			Gadgets.selectGadget(p, Gadgets.WIZARD);
			p.closeInventory();
		}else if (slot == 24) {
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 0.5F);
			Gadgets.selectGadget(p, Gadgets.FUMEGANTE);
			p.closeInventory();
		}else if (slot == 25) {
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 0.5F);
			Gadgets.selectGadget(p, Gadgets.EXPLOSIVE_POOP);
			p.closeInventory();
		}else if(slot == 28) {
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 0.5F);
			Gadgets.selectGadget(p, Gadgets.DISCO_BALL);
			p.closeInventory();
		}else if(slot == 29) {
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 0.5F);
			Gadgets.selectGadget(p, Gadgets.SMOKE_BOMB);
			p.closeInventory();
		}else if (slot == 49) {
			Gadgets.selectGadget(p, Gadgets.NENHUM);
			p.closeInventory();
		}
	}

	@Override
	public void onClose(Player p) {}

	@Override
	public void onClickAir(Player p) {}
}

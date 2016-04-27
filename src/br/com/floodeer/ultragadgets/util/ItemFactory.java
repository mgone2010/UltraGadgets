package br.com.floodeer.ultragadgets.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class ItemFactory {

	private static HashSet<Material> _axeSet = new HashSet<>();
	private static HashSet<Material> _swordSet = new HashSet<>();
	private static HashSet<Material> _maulSet = new HashSet<>();
	private static HashSet<Material> pickSet = new HashSet<>();
	private static HashSet<Material> diamondSet = new HashSet<>();
	private static HashSet<Material> goldSet = new HashSet<>();

    public static List<String> colorList(List<String> list) {
        ArrayList<String> strings = new ArrayList<String>();
        Iterator<String> i = list.iterator();
        while(i.hasNext()) {
            String str = (String)i.next();
            strings.add(ChatColor.translateAlternateColorCodes('&', str));
        }

        return strings;
    }
    
	public static boolean hasDisplayName(ItemStack is, String nome) {
		try {
			if (!is.hasItemMeta()) {
				return false;
			}
			if (!is.getItemMeta().hasDisplayName()) {
				return false;
			}
			if (is.getItemMeta().getDisplayName().equalsIgnoreCase(nome)) {
				return true;
			}
			return false;
		} catch (Exception localException) {
		}
		return false;
	}

	public static boolean startsName(ItemStack is, String prefix) {
		try {
			if (!is.hasItemMeta()) {
				return false;
			}
			if (!is.getItemMeta().hasDisplayName()) {
				return false;
			}
			if (is.getItemMeta().getDisplayName().startsWith(prefix)) {
				return true;
			}
			return false;
		} catch (Exception localException) {
		}
		return false;
	}

	public static boolean isAxe(ItemStack paramItemStack) {
		if (paramItemStack == null) {
			return false;
		}
		if (_axeSet.isEmpty()) {
			_axeSet.add(Material.WOOD_AXE);
			_axeSet.add(Material.STONE_AXE);
			_axeSet.add(Material.IRON_AXE);
			_axeSet.add(Material.GOLD_AXE);
			_axeSet.add(Material.DIAMOND_AXE);
		}
		return _axeSet.contains(paramItemStack.getType());
	}

	public static boolean isSword(ItemStack paramItemStack) {
		if (paramItemStack == null) {
			return false;
		}
		if (_swordSet.isEmpty()) {
			_swordSet.add(Material.WOOD_SWORD);
			_swordSet.add(Material.STONE_SWORD);
			_swordSet.add(Material.IRON_SWORD);
			_swordSet.add(Material.GOLD_SWORD);
			_swordSet.add(Material.DIAMOND_SWORD);
		}
		return _swordSet.contains(paramItemStack.getType());
	}

	public static boolean isShovel(ItemStack paramItemStack) {
		if (paramItemStack == null) {
			return false;
		}
		if (_maulSet.isEmpty()) {
			_maulSet.add(Material.WOOD_SPADE);
			_maulSet.add(Material.STONE_SPADE);
			_maulSet.add(Material.IRON_SPADE);
			_maulSet.add(Material.GOLD_SPADE);
			_maulSet.add(Material.DIAMOND_SPADE);
		}
		return _maulSet.contains(paramItemStack.getType());
	}

	public static HashSet<Material> scytheSet = new HashSet<>();

	public static boolean isHoe(ItemStack paramItemStack) {
		if (paramItemStack == null) {
			return false;
		}
		if (scytheSet.isEmpty()) {
			scytheSet.add(Material.WOOD_HOE);
			scytheSet.add(Material.STONE_HOE);
			scytheSet.add(Material.IRON_HOE);
			scytheSet.add(Material.GOLD_HOE);
			scytheSet.add(Material.DIAMOND_HOE);
		}
		return scytheSet.contains(paramItemStack.getType());
	}

	public static boolean isPickaxe(ItemStack paramItemStack) {
		if (paramItemStack == null) {
			return false;
		}
		if (pickSet.isEmpty()) {
			pickSet.add(Material.WOOD_PICKAXE);
			pickSet.add(Material.STONE_PICKAXE);
			pickSet.add(Material.IRON_PICKAXE);
			pickSet.add(Material.GOLD_PICKAXE);
			pickSet.add(Material.DIAMOND_PICKAXE);
		}
		return pickSet.contains(paramItemStack.getType());
	}

	public static boolean isDiamond(ItemStack paramItemStack) {
		if (paramItemStack == null) {
			return false;
		}
		if (diamondSet.isEmpty()) {
			diamondSet.add(Material.DIAMOND_SWORD);
			diamondSet.add(Material.DIAMOND_AXE);
			diamondSet.add(Material.DIAMOND_SPADE);
			diamondSet.add(Material.DIAMOND_HOE);
		}
		return diamondSet.contains(paramItemStack.getType());
	}

	public static boolean isGold(ItemStack paramItemStack) {
		if (paramItemStack == null) {
			return false;
		}
		if (goldSet.isEmpty()) {
			goldSet.add(Material.GOLD_SWORD);
			goldSet.add(Material.GOLD_AXE);
		}
		return goldSet.contains(paramItemStack.getType());
	}

	public static boolean isBow(ItemStack paramItemStack) {
		if (paramItemStack == null) {
			return false;
		}
		return paramItemStack.getType() == Material.BOW;
	}

	public static boolean isWeapon(ItemStack paramItemStack) {
		return (isAxe(paramItemStack)) || (isSword(paramItemStack));
	}

	public static boolean isMat(ItemStack paramItemStack, Material paramMaterial) {
		if (paramItemStack == null) {
			return false;
		}
		return paramItemStack.getType() == paramMaterial;
	}

	public static boolean isRepairable(ItemStack paramItemStack) {
		return paramItemStack.getType().getMaxDurability() > 0;
	}

	public static ItemStack buildItemStack(Material tipo, String nome, String lore, int quantidade, byte data,
			boolean enchantPass, int enchantLevel, Enchantment enchant) {
		ItemStack builder = new ItemStack(tipo, quantidade, data);
		ItemMeta meta = builder.getItemMeta();
		if(lore != null)
		meta.setLore(Arrays.asList(lore));
		meta.setDisplayName(nome);
		meta.addEnchant(enchant, enchantLevel, enchantPass);
		builder.setItemMeta(meta);
		return builder;
	}
	

	public static ItemStack buildItemStackArrays(Material tipo, String nome, List<String> lore, int quantidade, byte data, boolean enchantPass, int enchantLevel, Enchantment enchant) {
		ItemStack builder = new ItemStack(tipo, quantidade, data);
		ItemMeta meta = builder.getItemMeta();
		meta.setDisplayName(nome);
		if(lore != null) 
			meta.setLore(lore);
		meta.addEnchant(enchant, enchantLevel, enchantPass);
		builder.setItemMeta(meta);
		return builder;
	}
	
	public static ItemStack buildItemStack(Material tipo, String nome, String lore, int quantidade, byte data) {
		ItemStack builder = new ItemStack(tipo, quantidade, data);
		ItemMeta meta = builder.getItemMeta();
		if(lore != null) 
		meta.setLore(Arrays.asList(lore));
		meta.setDisplayName(nome);
		builder.setItemMeta(meta);
		return builder;
	}
	
	public static ItemStack buildItemStackArrays(Material tipo, String nome, List<String> lore, int quantidade, byte data) {
		ItemStack builder = new ItemStack(tipo, quantidade, data);
		ItemMeta meta = builder.getItemMeta();
		meta.setDisplayName(nome);
		if(lore != null) 
			meta.setLore(lore);
		builder.setItemMeta(meta);
		return builder;
	}
	
	public static ItemStack buildItemStack(Material tipo, String nome, List<String> lore) {
		ItemStack builder = new ItemStack(tipo, 1);
		ItemMeta meta = builder.getItemMeta();
		meta.setDisplayName(nome);
		if(lore != null) 
			meta.setLore(lore);
		builder.setItemMeta(meta);
		return builder;
	}
	
	public static ItemStack buildItemStack(Material tipo, String nome, String lore) {
		ItemStack builder = new ItemStack(tipo, 1, (byte)0);
		ItemMeta meta = builder.getItemMeta();
		if(lore != null) 
		meta.setLore(Arrays.asList(lore));
		meta.setDisplayName(nome);
		builder.setItemMeta(meta);
		return builder;
	}
	
	public static ItemStack buildItemStackArrays(Material tipo, String nome) {
		ItemStack builder = new ItemStack(tipo, 1, (byte)0);
		ItemMeta meta = builder.getItemMeta();
		meta.setDisplayName(nome);
		builder.setItemMeta(meta);
		return builder;
	}
	
	public static ItemStack buildGlowedItemStackArrays(Material tipo, String nome, List<String> lore, int quantidade, byte data) {
		ItemStack builder = new ItemStack(tipo, quantidade, data);
		ItemMeta meta = builder.getItemMeta();
		meta.setDisplayName(nome);
		if(lore != null) 
			meta.setLore(lore);
		builder.setItemMeta(meta);
		Glow.addGlow(builder);
		return builder;
	}
	
	public static ItemStack buildGlowedItemStack(Material tipo, String nome, String lore, int quantidade, byte data) {
		ItemStack builder = new ItemStack(tipo, quantidade, data);
		ItemMeta meta = builder.getItemMeta();
		meta.setDisplayName(nome);
		if(lore != null) 
		meta.setLore(Arrays.asList(lore));
		builder.setItemMeta(meta);
		Glow.addGlow(builder);
		return builder;
	}
	
	public static ItemStack buildGlowedItemStack(Material tipo, String nome, String lore) {
		ItemStack builder = new ItemStack(tipo, 1, (byte)0);
		ItemMeta meta = builder.getItemMeta();
		meta.setDisplayName(nome);
		if(lore != null) 
		meta.setLore(Arrays.asList(lore));
		builder.setItemMeta(meta);
		Glow.addGlow(builder);
		return builder;
	}
	
	public static ItemStack buildGlowedItemStack(Material tipo, String nome) {
		ItemStack builder = new ItemStack(tipo, 1, (byte)0);
		ItemMeta meta = builder.getItemMeta();
		meta.setDisplayName(nome);
		builder.setItemMeta(meta);
		Glow.addGlow(builder);
		return builder;
	}

	public static ItemStack buildItemStackArrays(Material tipo, byte bytes) {
		ItemStack builder = new ItemStack(tipo, 1, bytes);
		return builder;
	}
	
	public static ItemStack buildSkull(String urlToFormat, int quantidade, String name, List<String> lore) {
		ItemStack head = new ItemStack(Material.SKULL_ITEM, quantidade, (short) SkullType.PLAYER.ordinal());

		SkullMeta headMeta = (SkullMeta) head.getItemMeta();
		headMeta.setLore(lore);
		headMeta.setDisplayName(name);
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		profile.getProperties().put("textures", new Property("textures", urlToFormat));
		Field profileField;
		try {
			profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		head.setItemMeta(headMeta);
		return head;
	}

	public static ItemStack buildSimpleSkull(String urlToFormat, int q) {
		ItemStack head = new ItemStack(Material.SKULL_ITEM, q, (short) SkullType.PLAYER.ordinal());

		SkullMeta headMeta = (SkullMeta) head.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		profile.getProperties().put("textures", new Property("textures", urlToFormat));
		Field profileField;
		try {
			profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		head.setItemMeta(headMeta);
		return head;
	}

	public static void setName(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
	}

	public static void setLore(ItemStack item, ArrayList<String> lores) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lores);
		item.setItemMeta(meta);
	}

	public static void addEnchant(ItemStack item, int level, Enchantment enchant, boolean ignore) {
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(enchant, level, ignore);
		item.setItemMeta(meta);
	}

	public static ItemStack setBackArrow() {
		return buildSkull(
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ViZjkwNzQ5NGE5MzVlOTU1YmZjYWRhYjgxYmVhZmI5MGZiOWJlNDljNzAyNmJhOTdkNzk4ZDVmMWEyMyJ9fX0=",
				1, "cVoltar", Arrays.asList("�7Clique para voltar ao menu principal"));
	}

	public static ItemStack buildGlowedItemStack(Material type, String name, List<String> arrays) {
		ItemStack i = buildItemStack(type, name, arrays);
		Glow.addGlow(i);
		return i;
	}

	public static ItemStack buildItemStack(Material tipo, String nome) {
		ItemStack builder = new ItemStack(tipo, 1, (byte)0);
		ItemMeta meta = builder.getItemMeta();
		meta.setDisplayName(nome);
		builder.setItemMeta(meta);
		return builder;
	}
}

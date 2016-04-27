package br.com.floodeer.ultragadgets.util;

import java.lang.reflect.Field;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Glow
extends Enchantment
{
private static Glow glow;

public Glow()
{
  super(255);
}

public String getName()
{
  return "GLOW";
}

public int getMaxLevel()
{
  return 1;
}

public int getStartLevel()
{
  return 0;
}

public EnchantmentTarget getItemTarget()
{
  return EnchantmentTarget.ALL;
}

public boolean conflictsWith(Enchantment enchantment)
{
  return false;
}

public boolean canEnchantItem(ItemStack itemStack)
{
  return true;
}

@SuppressWarnings({ "unchecked", "deprecation" })
public static void register()
{
  Glow glow = new Glow();
  try
  {
    Field byId = Enchantment.class.getDeclaredField("byId");
    Field byName = Enchantment.class.getDeclaredField("byName");
    
    byId.setAccessible(true);
    byName.setAccessible(true);
	Map<Integer, Enchantment> byIdMap = (Map<Integer, Enchantment>) byId.get(null);
	Map<String, Enchantment> byNameMap = (Map<String, Enchantment>)byName.get(null);
    
    byIdMap.put(Integer.valueOf(glow.getId()), glow);
    byNameMap.put(glow.getName(), glow);
    Glow.glow = glow;
  }
  catch (NoSuchFieldException ex)
  {
    ex.printStackTrace();
  }
  catch (IllegalAccessException ex)
  {
    ex.printStackTrace();
  }
}

public static void addGlow(ItemStack stack)
{
  if (glow == null) {
    return;
  }
  if (stack.getType() == Material.AIR) {
    return;
  }
  ItemMeta meta = stack.getItemMeta();
  meta.addEnchant(glow, 1, false);
  stack.setItemMeta(meta);
}

public static void removeGlow(ItemStack stack)
{
  if (glow != null) {
    return;
  }
  if (stack.getType() == Material.AIR) {
    return;
  }
  ItemMeta meta = stack.getItemMeta();
  meta.addEnchant(null, 0, false);
  stack.setItemMeta(meta);
}

public static boolean isGlow(ItemStack stack)
{
  ItemMeta meta = stack.getItemMeta();
  if (meta.hasEnchants()) {
    return true;
  }
  return false;
 }
}
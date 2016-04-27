package br.com.floodeer.ultragadgets.util;

import java.lang.reflect.Field;
import java.util.HashMap;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.NavigationAbstract;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import br.com.floodeer.ultragadgets.UltraGadgets;

public class EntityUtils implements Listener {
	private static HashMap<org.bukkit.entity.Entity, String> _nameMap = new HashMap<>();
	private static HashMap<String, EntityType> creatureMap = new HashMap<>();
	private static Field _goalSelector;
	private static UltraGadgets plugin = UltraGadgets.get();

	public static HashMap<org.bukkit.entity.Entity, String> GetEntityNames() {
		return _nameMap;
	}

	public static void sendJson(Player player, Object s) {
		CraftPlayer craftPlayer = (CraftPlayer) player;
		PlayerConnection playConnection = craftPlayer.getHandle().playerConnection;
		playConnection.sendPacket(new PacketPlayOutChat(ChatSerializer.a(s.toString())));
	}

	public static void removeGoalSelectors(org.bukkit.entity.Entity paramEntity) {
		try {
			if (_goalSelector == null) {
				_goalSelector = EntityInsentient.class.getDeclaredField("goalSelector");
				_goalSelector.setAccessible(true);
			}
			if ((((CraftEntity) paramEntity).getHandle() instanceof EntityInsentient)) {
				EntityInsentient localEntityInsentient = (EntityInsentient) ((CraftEntity) paramEntity).getHandle();

				PathfinderGoalSelector localPathfinderGoalSelector = new PathfinderGoalSelector(
						((CraftWorld) paramEntity.getWorld()).getHandle().methodProfiler);

				_goalSelector.set(localEntityInsentient, localPathfinderGoalSelector);
			}
		} catch (IllegalArgumentException localIllegalArgumentException) {
			localIllegalArgumentException.printStackTrace();
		} catch (IllegalAccessException localIllegalAccessException) {
			localIllegalAccessException.printStackTrace();
		} catch (NoSuchFieldException localNoSuchFieldException) {
			localNoSuchFieldException.printStackTrace();
		} catch (SecurityException localSecurityException) {
			localSecurityException.printStackTrace();
		}
	}

	public static void populate() {
		if (creatureMap.isEmpty()) {
			creatureMap.put("Bat", EntityType.BAT);
			creatureMap.put("Blaze", EntityType.BLAZE);
			creatureMap.put("Arrow", EntityType.ARROW);
			creatureMap.put("Cave Spider", EntityType.CAVE_SPIDER);
			creatureMap.put("Chicken", EntityType.CHICKEN);
			creatureMap.put("Cow", EntityType.COW);
			creatureMap.put("Creeper", EntityType.CREEPER);
			creatureMap.put("Ender Dragon", EntityType.ENDER_DRAGON);
			creatureMap.put("Enderman", EntityType.ENDERMAN);
			creatureMap.put("Ghast", EntityType.GHAST);
			creatureMap.put("Giant", EntityType.GIANT);
			creatureMap.put("Horse", EntityType.HORSE);
			creatureMap.put("Iron Golem", EntityType.IRON_GOLEM);
			creatureMap.put("Item", EntityType.DROPPED_ITEM);
			creatureMap.put("Magma Cube", EntityType.MAGMA_CUBE);
			creatureMap.put("Mooshroom", EntityType.MUSHROOM_COW);
			creatureMap.put("Ocelot", EntityType.OCELOT);
			creatureMap.put("Pig", EntityType.PIG);
			creatureMap.put("Pig Zombie", EntityType.PIG_ZOMBIE);
			creatureMap.put("Sheep", EntityType.SHEEP);
			creatureMap.put("Silverfish", EntityType.SILVERFISH);
			creatureMap.put("Skeleton", EntityType.SKELETON);
			creatureMap.put("Slime", EntityType.SLIME);
			creatureMap.put("Snowman", EntityType.SNOWMAN);
			creatureMap.put("Spider", EntityType.SPIDER);
			creatureMap.put("Squid", EntityType.SQUID);
			creatureMap.put("Villager", EntityType.VILLAGER);
			creatureMap.put("Witch", EntityType.WITCH);
			creatureMap.put("Wither", EntityType.WITHER);
			creatureMap.put("WitherSkull", EntityType.WITHER_SKULL);
			creatureMap.put("Wolf", EntityType.WOLF);
			creatureMap.put("Zombie", EntityType.ZOMBIE);

			creatureMap.put("Item", EntityType.DROPPED_ITEM);
		}
	}

	public static String getName(org.bukkit.entity.Entity paramEntity) {
		if (paramEntity == null) {
			return "Null";
		}
		if (paramEntity.getType() == EntityType.PLAYER) {
			return ((Player) paramEntity).getName();
		}
		if (GetEntityNames().containsKey(paramEntity)) {
			return (String) GetEntityNames().get(paramEntity);
		}
		if ((paramEntity instanceof LivingEntity)) {
			LivingEntity localLivingEntity = (LivingEntity) paramEntity;
			if (localLivingEntity.getCustomName() != null) {
				return localLivingEntity.getCustomName();
			}
		}
		return getName(paramEntity.getType());
	}

	@SuppressWarnings("deprecation")
	public static String getName(EntityType paramEntityType) {
		for (String str : creatureMap.keySet()) {
			if (creatureMap.get(str) == paramEntityType) {
				return str;
			}
		}
		return paramEntityType.getName();
	}

	public static boolean hitBox(Location paramLocation, LivingEntity paramLivingEntity, double paramDouble) {
		if (UtilMath.offset(paramLocation, paramLivingEntity.getLocation().add(0.0D, 0.4D, 0.0D)) < 0.6D
				* paramDouble) {
			return true;
		}
		if ((paramLivingEntity instanceof Player)) {
			Player localPlayer = (Player) paramLivingEntity;
			if (UtilMath.offset(paramLocation, localPlayer.getEyeLocation()) < 0.4D * paramDouble) {
				return true;
			}
			if (UtilMath.offset2d(paramLocation, localPlayer.getLocation()) < 0.6D * paramDouble) {
				if ((paramLocation.getY() > localPlayer.getLocation().getY())
						&& (paramLocation.getY() < localPlayer.getEyeLocation().getY())) {
					return true;
				}
			}
		} else if ((paramLivingEntity instanceof Giant)) {
			if ((paramLocation.getY() > paramLivingEntity.getLocation().getY())
					&& (paramLocation.getY() < paramLivingEntity.getLocation().getY() + 12.0D)
					&& (UtilMath.offset2d(paramLocation, paramLivingEntity.getLocation()) < 4.0D)) {
				return true;
			}
		} else if ((paramLocation.getY() > paramLivingEntity.getLocation().getY())
				&& (paramLocation.getY() < paramLivingEntity.getLocation().getY() + 2.0D)
				&& (UtilMath.offset2d(paramLocation, paramLivingEntity.getLocation()) < 0.5D * paramDouble)) {
			return true;
		}
		return false;
	}

	public static boolean hitBox(Location paramLocation, org.bukkit.entity.Entity paramEntity, double paramDouble) {
		if (UtilMath.offset(paramLocation, paramEntity.getLocation().add(0.0D, 0.4D, 0.0D)) < 0.6D * paramDouble) {
			return true;
		}
		if ((paramEntity instanceof Player)) {
			Player localPlayer = (Player) paramEntity;
			if (UtilMath.offset(paramLocation, localPlayer.getEyeLocation()) < 0.4D * paramDouble) {
				return true;
			}
			if (UtilMath.offset2d(paramLocation, localPlayer.getLocation()) < 0.6D * paramDouble) {
				if ((paramLocation.getY() > localPlayer.getLocation().getY())
						&& (paramLocation.getY() < localPlayer.getEyeLocation().getY())) {
					return true;
				}
			}
		} else if ((paramEntity instanceof Giant)) {
			if ((paramLocation.getY() > paramEntity.getLocation().getY())
					&& (paramLocation.getY() < paramEntity.getLocation().getY() + 12.0D)
					&& (UtilMath.offset2d(paramLocation, paramEntity.getLocation()) < 4.0D)) {
				return true;
			}
		} else if ((paramLocation.getY() > paramEntity.getLocation().getY())
				&& (paramLocation.getY() < paramEntity.getLocation().getY() + 2.0D)
				&& (UtilMath.offset2d(paramLocation, paramEntity.getLocation()) < 0.5D * paramDouble)) {
			return true;
		}
		return false;
	}

	public static boolean isGrounded(org.bukkit.entity.Entity paramEntity) {
		if ((paramEntity instanceof CraftEntity)) {
			return ((CraftEntity) paramEntity).getHandle().onGround;
		}
		return UtilBlock.solid(paramEntity.getLocation().getBlock().getRelative(BlockFace.DOWN));
	}

	public static void PlayDamageSound(LivingEntity paramLivingEntity) {
		Sound localSound = Sound.HURT_FLESH;
		if (paramLivingEntity.getType() == EntityType.BAT) {
			localSound = Sound.BAT_HURT;
		} else if (paramLivingEntity.getType() == EntityType.BLAZE) {
			localSound = Sound.BLAZE_HIT;
		} else if (paramLivingEntity.getType() == EntityType.CAVE_SPIDER) {
			localSound = Sound.SPIDER_IDLE;
		} else if (paramLivingEntity.getType() == EntityType.CHICKEN) {
			localSound = Sound.CHICKEN_HURT;
		} else if (paramLivingEntity.getType() == EntityType.COW) {
			localSound = Sound.COW_HURT;
		} else if (paramLivingEntity.getType() == EntityType.CREEPER) {
			localSound = Sound.CREEPER_HISS;
		} else if (paramLivingEntity.getType() == EntityType.ENDER_DRAGON) {
			localSound = Sound.ENDERDRAGON_GROWL;
		} else if (paramLivingEntity.getType() == EntityType.ENDERMAN) {
			localSound = Sound.ENDERMAN_HIT;
		} else if (paramLivingEntity.getType() == EntityType.GHAST) {
			localSound = Sound.GHAST_SCREAM;
		} else if (paramLivingEntity.getType() == EntityType.GIANT) {
			localSound = Sound.ZOMBIE_HURT;
		} else if (paramLivingEntity.getType() == EntityType.IRON_GOLEM) {
			localSound = Sound.IRONGOLEM_HIT;
		} else if (paramLivingEntity.getType() == EntityType.MAGMA_CUBE) {
			localSound = Sound.MAGMACUBE_JUMP;
		} else if (paramLivingEntity.getType() == EntityType.MUSHROOM_COW) {
			localSound = Sound.COW_HURT;
		} else if (paramLivingEntity.getType() == EntityType.OCELOT) {
			localSound = Sound.CAT_MEOW;
		} else if (paramLivingEntity.getType() == EntityType.PIG) {
			localSound = Sound.PIG_IDLE;
		} else if (paramLivingEntity.getType() == EntityType.PIG_ZOMBIE) {
			localSound = Sound.ZOMBIE_HURT;
		} else if (paramLivingEntity.getType() == EntityType.SHEEP) {
			localSound = Sound.SHEEP_IDLE;
		} else if (paramLivingEntity.getType() == EntityType.SILVERFISH) {
			localSound = Sound.SILVERFISH_HIT;
		} else if (paramLivingEntity.getType() == EntityType.SKELETON) {
			localSound = Sound.SKELETON_HURT;
		} else if (paramLivingEntity.getType() == EntityType.SLIME) {
			localSound = Sound.SLIME_ATTACK;
		} else if (paramLivingEntity.getType() == EntityType.SNOWMAN) {
			localSound = Sound.STEP_SNOW;
		} else if (paramLivingEntity.getType() == EntityType.SPIDER) {
			localSound = Sound.SPIDER_IDLE;
		} else if (paramLivingEntity.getType() == EntityType.WITHER) {
			localSound = Sound.WITHER_HURT;
		} else if (paramLivingEntity.getType() == EntityType.WOLF) {
			localSound = Sound.WOLF_HURT;
		} else if (paramLivingEntity.getType() == EntityType.ZOMBIE) {
			localSound = Sound.ZOMBIE_HURT;
		}
		paramLivingEntity.getWorld().playSound(paramLivingEntity.getLocation(), localSound,
				1.5F + (float) (0.5D * Math.random()), 0.8F + (float) (0.4000000059604645D * Math.random()));
	}

	public static void CreatureMove(org.bukkit.entity.Entity paramEntity, Location paramLocation, float paramFloat) {
		if (!(paramEntity instanceof Creature)) {
			return;
		}
		if (UtilMath.offset(paramEntity.getLocation(), paramLocation) < 0.1D) {
			return;
		}
		EntityCreature localEntityCreature = ((CraftCreature) paramEntity).getHandle();
		NavigationAbstract localNavigationAbstract = localEntityCreature.getNavigation();
		if (UtilMath.offset(paramEntity.getLocation(), paramLocation) > 24.0D) {
			Location localLocation = paramEntity.getLocation();

			localLocation.add(UtilAlg.getTrajectory(paramEntity.getLocation(), paramLocation).multiply(24));

			localNavigationAbstract.a(localLocation.getX(), localLocation.getY(), localLocation.getZ(), paramFloat);
		} else {
			localNavigationAbstract.a(paramLocation.getX(), paramLocation.getY(), paramLocation.getZ(), paramFloat);
		}
	}

	public static boolean CreatureMoveFast(org.bukkit.entity.Entity paramEntity, Location paramLocation,
			float paramFloat) {
		if (!(paramEntity instanceof Creature)) {
			return false;
		}
		if (UtilMath.offset(paramEntity.getLocation(), paramLocation) < 0.1D) {
			return false;
		}
		if (UtilMath.offset(paramEntity.getLocation(), paramLocation) < 2.0D) {
			paramFloat = Math.min(paramFloat, 1.0F);
		}
		EntityCreature localEntityCreature = ((CraftCreature) paramEntity).getHandle();
		localEntityCreature.getControllerMove().a(paramLocation.getX(), paramLocation.getY(), paramLocation.getZ(),
				paramFloat);

		return true;
	}

	public static void setHelmet(LivingEntity paramLivingEntity, org.bukkit.inventory.ItemStack paramItemStack) {
		EntityEquipment localEntityEquipment = paramLivingEntity.getEquipment();
		localEntityEquipment.setHelmet(paramItemStack);
	}

	public static void setItemInHand(LivingEntity paramLivingEntity, org.bukkit.inventory.ItemStack paramItemStack) {
		EntityEquipment localEntityEquipment = paramLivingEntity.getEquipment();
		localEntityEquipment.setItemInHand(paramItemStack);
	}

	public static void RemoveHelmet(LivingEntity paramLivingEntity) {
		EntityEquipment localEntityEquipment = paramLivingEntity.getEquipment();
		localEntityEquipment.setHelmet(null);
	}

	public static void itemToRemove(Location l, Material tipo, byte id, long tempoToRemove, boolean randomVector,
			Vector vector) {
		final ItemStack item = new ItemStack(tipo, 1, id);

		final Item drop = l.getWorld().dropItem(l, item);
		drop.setPickupDelay(Integer.MAX_VALUE);
		if (randomVector) {
			drop.setVelocity(vector);
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			@Override
			public void run() {
				drop.remove();

			}
		}, tempoToRemove * 20);
	}
}

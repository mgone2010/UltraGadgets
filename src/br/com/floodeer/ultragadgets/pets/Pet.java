package br.com.floodeer.ultragadgets.pets;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.PetType;
import br.com.floodeer.ultragadgets.util.EntityUtils;

public abstract class Pet {

	public PetType type;
	public EntityType entityType;
	public Entity entity;
	public Class<? extends Entity> entityClass;
	public Player owner;
	public int updateSteps;
	
	public Pet(PetType type, Player owner) {
		if(owner != null) {
			this.owner = owner;
			switch (type) {
			case SHEEP:
				entityType = EntityType.SHEEP;
				entityClass = Sheep.class;
				break;
			case CAT:
				entityType = EntityType.OCELOT;
				entityClass = Ocelot.class;
				break;
			case CUSTOM_SWAG:
				entityType = EntityType.ZOMBIE;
				entityClass = Zombie.class;
				break;
			case ENDERMAN:
				entityType = EntityType.ENDERMAN;
				entityClass = Enderman.class;
				break;
			case ENDERMITE:
				entityType = EntityType.ENDERMITE;
				entityClass = Endermite.class;
				break;
			case SKELETON:
				entityType = EntityType.SKELETON;
				entityClass = Skeleton.class;
				break;
			case SLIME:
				entityType = EntityType.SLIME;
				entityClass = Slime.class;
				break;
			case VILLAGER:
				entityType = EntityType.VILLAGER;
				entityClass = Villager.class;
				break;
			case WOLF:
				entityType = EntityType.WOLF;
				entityClass = Wolf.class;
				break;
			case ZOMBIE:
				entityType = EntityType.ZOMBIE;
				entityClass = Zombie.class;
				break;
			default:
				break;
			}
			spawn();
			teleportToOwner(owner);
			startScheduler();
		}
	}
	
	
	protected void spawn() {
	     this.entity = ((CraftWorld)owner.getWorld()).spawn(owner.getLocation(), entityClass, SpawnReason.CUSTOM);
	 	 PetManager.create(this.owner, entity);
	 	 onSpawn(entity);
	}
	
	protected void startScheduler() {
		new BukkitRunnable() {
			@Override
			public void run() {
				onScheduler();
				++updateSteps;
				if(updateSteps > 10) {
					updateSteps =- 0;
				}
			}
		}.runTaskTimer(UltraGadgets.get(), 0, 20);
	}
	
	public void teleportToOwner(Player p) {
		this.entity.teleport(p);
	}
	
	public void forceMove(Location to, float f) {
		if(f <= 0.2F)
		EntityUtils.CreatureMoveFast(this.entity, to, f);
		else
			EntityUtils.CreatureMove(this.entity, to, 0.3f);
	}
	
	public abstract void onScheduler();
	
	public abstract void onSpawn(Entity e);
	
	public Player getPlayer() {
		return owner;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public Class<? extends Entity> getEntityClass() {
		return entityClass;
	}
	
	public EntityType getEntityType() {
		return entityType;
	}
	
	public PetType getPetType() {
		return type;
	}
	
	public Location getLocation() {
		return entity.getLocation();
	}
	
	public double getDistance() {
		return Math.floor(entity.getLocation().distance(owner.getLocation()));
	}
	
	public int updateStep() {
		return updateSteps;
	}
}

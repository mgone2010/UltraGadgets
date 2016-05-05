package br.com.floodeer.ultragadgets.pets;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Pets;
import br.com.floodeer.ultragadgets.util.EntityUtils;
import net.md_5.bungee.api.ChatColor;

public abstract class Pet {

	public Pets type;
	public EntityType entityType;
	public Entity entity;
	public Class<? extends Entity> entityClass;
	public Player owner;
	public int updateSteps;
	public boolean angryEntity;
	
	public Pet(Pets type, Player owner) {
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
			case RABBIT:
				entityType = EntityType.RABBIT;
				entityClass = Rabbit.class;
				break;
			}
			angryEntity = false;
			spawn();
			teleportToOwner(owner);
			startScheduler();
		}
	}
	
	
	protected void spawn() {
	     this.entity = ((CraftWorld)owner.getWorld()).spawn(owner.getLocation(), entityClass, SpawnReason.CUSTOM);
	 	 this.entity.setMetadata("ugPet", new FixedMetadataValue(UltraGadgets.get(), true));
	     PetManager.create(this.owner, this.entity, this);
	 	 onSpawn();
	}
	
	protected void startScheduler() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if(!entity.isValid() || Pets.getEntityPet(owner).getType() != entity.getType() || !Pets.hasPetSpawned(owner) || !owner.isOnline()) {
					cancel();
					return;
				}
				++updateSteps;
				onScheduler();
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
	
	public void setName(String name) {
		this.entity.setCustomNameVisible(true);
		this.entity.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
	}
	
	public void setAngry(boolean x) {
		this.angryEntity = x;
		if(x) {
		    if(this.entity.getType() == EntityType.WOLF) {
		    	((Wolf)this.entity).setAngry(true);
		    }
		}else{
			if(this.entity.getType() == EntityType.WOLF) {
		    	((Wolf)this.entity).setAngry(false);
		    }
		}
	}
	
	public void despawn() {
		this.entity.remove();
	}
	
	abstract void onScheduler();
	
	abstract void onSpawn();
	
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
	
	public Pets getPetType() {
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
	
	public String getRawName() {
		return ChatColor.stripColor(this.entity.getName());
	}
	
	public String getName() {
		return this.entity.getName();
	}
	
	public boolean isAngry() {
		return angryEntity;
	}
}

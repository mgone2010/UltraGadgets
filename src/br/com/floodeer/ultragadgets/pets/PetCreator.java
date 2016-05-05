package br.com.floodeer.ultragadgets.pets;

import java.lang.reflect.Field;
import java.util.UUID;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import br.com.floodeer.ultragadgets.scheduler.SchedulerEvent;
import br.com.floodeer.ultragadgets.scheduler.SchedulerType;
import br.com.floodeer.ultragadgets.util.EntityUtils;

public class PetCreator implements Listener {

	private static Field gsa;
	private static Field goalSelector;
	private static Field targetSelector;

	static {
		try {
			gsa = PathfinderGoalSelector.class.getDeclaredField("b");
			gsa.setAccessible(true);
			goalSelector = EntityInsentient.class.getDeclaredField("goalSelector");
			goalSelector.setAccessible(true);
			targetSelector = EntityInsentient.class.getDeclaredField("targetSelector");
			targetSelector.setAccessible(true);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}
	
	@EventHandler
	public void onScheduler(SchedulerEvent e) {
		if(e.getType() != SchedulerType.TICKS_2) return;
		for(UUID uuid : PetManager.petEntity.keySet()) {
			Entity entity = PetManager.petEntity.get(uuid);
			if(!entity.isValid()) {
				return;
			}
			Player p = Bukkit.getPlayer(uuid);
			if(entity.getLocation().distance(p.getLocation()) <= 12) {
				EntityUtils.CreatureMoveFast(entity, p.getLocation(), 1.3F);
			}else{
				if(EntityUtils.isGrounded(p))
				entity.teleport(p);
				else
					EntityUtils.CreatureMoveFast(entity, p.getLocation(), 1.6F);
			}
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if(e.getEntity().hasMetadata("ugPet")) {
			e.setCancelled(true);
		}
	}

	@SuppressWarnings("deprecation")
	public static void criarPet(LivingEntity paramLivingEntity, UUID paramUUID) {
		PetManager.petEntity.put(paramUUID, paramLivingEntity);
		try {
			EntityLiving localEntityLiving = ((CraftLivingEntity) paramLivingEntity).getHandle();
			if ((localEntityLiving instanceof EntityInsentient)) {
				PathfinderGoalSelector localPathfinderGoalSelector1 = (PathfinderGoalSelector) goalSelector.get(localEntityLiving);
				PathfinderGoalSelector localPathfinderGoalSelector2 = (PathfinderGoalSelector) targetSelector.get(localEntityLiving);

				gsa.set(localPathfinderGoalSelector1, new UnsafeList<>());
				gsa.set(localPathfinderGoalSelector2, new UnsafeList<>());

				localPathfinderGoalSelector1.a(0, new PathfinderGoalFloat((EntityInsentient) localEntityLiving));
			} else {
				throw new IllegalArgumentException(paramLivingEntity.getType().getName() + " nao parece fazer parte de EntityInsentient.");
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}
}
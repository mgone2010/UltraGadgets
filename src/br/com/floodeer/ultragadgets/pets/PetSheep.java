package br.com.floodeer.ultragadgets.pets;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Pets;
import br.com.floodeer.ultragadgets.util.ItemFactory;
import br.com.floodeer.ultragadgets.util.UtilEnt;
import br.com.floodeer.ultragadgets.util.UtilMath;
import br.com.floodeer.ultragadgets.util.UtilParticle;
import br.com.floodeer.ultragadgets.util.UtilParticle.ParticleType;

public class PetSheep extends Pet {

	public PetSheep(Player owner) {
		super(Pets.SHEEP, owner);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onScheduler() {
		if(updateStep() % 5 == 1) {
			Entity entity = this.entity;
			if(entity instanceof Sheep) {
				Sheep sheep = (Sheep)this.entity;
				DyeColor[] color = DyeColor.values();
				sheep.setColor(color[UtilMath.random.nextInt(15)]);
			}
		}
		if(updateSteps == 8) {
			if(entity instanceof Sheep) {
				final Sheep sheep = (Sheep)this.entity;
				final DyeColor color = sheep.getColor();
				new BukkitRunnable() {
					int steps = 0;
					@Override
					public void run() {
						++steps;
						if(steps == 15) {
							cancel();
							return;
						}
						if(isAngry())
						UtilEnt.dropItemToRemove(sheep.getLocation().clone().add(0, 0.8, 0), ItemFactory.buildItemStackArrays(Material.WOOL, color.getDyeData()), 60, UtilMath.getRandomVector());
					}
				}.runTaskTimer(UltraGadgets.get(), 0, 1);
			}
		}
	}

	@Override
	public void onSpawn() {
		Location loc = this.getLocation();
		new UtilParticle(ParticleType.HEART, 1, 6, 2).sendToLocation(loc.clone().add(0, 1.2, 0));
	}
}

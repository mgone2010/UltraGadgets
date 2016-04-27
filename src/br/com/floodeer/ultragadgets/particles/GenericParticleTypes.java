package br.com.floodeer.ultragadgets.particles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.util.UtilParticle;
import br.com.floodeer.ultragadgets.util.UtilParticle.ParticleType;

public class GenericParticleTypes {

	public static void playSpiral(final Location loc, final ParticleType type, final ParticleType type2, boolean alpha) {
		float rayon = 0.3F;

		float rayonAlpha = rayon;
		float rayonBeta = rayon;
		if (alpha) {
			rayonAlpha = -rayon;
			rayonBeta = rayon;
		} else {
			rayonAlpha = rayon;
			rayonBeta = -rayon;
		}
		final float rayonA = rayonAlpha;
		final float rayonB = rayonBeta;

		final int i = Bukkit.getScheduler().runTaskTimer(UltraGadgets.get(), new Runnable() {
			float k = 0.0F;
			float heightPosition = 4.0F;
			float particleDistance = 0.1F;

			public void run() {
				Location l = loc.clone().getBlock().getLocation().add(0.5D, 0.4000000059604645D, 0.5D);

				Location l2 = l.clone();

				Vector v = new Vector(this.k * Math.sin(this.k * 3.141592653589793D / 2.0D) * rayonA,this.k + this.heightPosition, this.k * Math.cos(this.k * 3.141592653589793D / 2.0D) * rayonA);
				l.add(v);
				new UtilParticle(type, 0.0D, 1, 0.0D).sendToLocation(l);
				l.subtract(v);

				Vector v2 = new Vector(this.k * Math.sin(this.k * 3.141592653589793D / 2.0D) * rayonB, this.k + this.heightPosition, this.k * Math.cos(this.k * 3.141592653589793D / 2.0D) * rayonB);
				l2.add(v2);
				new UtilParticle(type, 0.0D, 1, 0.0D).sendToLocation(l2);
				l2.subtract(v2);

				this.k -= this.particleDistance;
				if (this.k <= -4.0F) {
					this.k = 0.0F;
				}
			}
		}, 1L, 1L).getTaskId();
		Bukkit.getServer().getScheduler().runTaskLater(UltraGadgets.get(), new Runnable() {
			public void run() {
				Bukkit.getScheduler().cancelTask(i);
				new UtilParticle(type2, 0.10000000149011612D, 10, 0.30000001192092896D)
.sendToLocation(loc.clone().getBlock().getLocation().add(0.5D, 1.5D, 0.5D));
			}
		}, 40L);
	}
}

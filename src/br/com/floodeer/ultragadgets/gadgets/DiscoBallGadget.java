package br.com.floodeer.ultragadgets.gadgets;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.listener.ServerStep;
import br.com.floodeer.ultragadgets.songAPI.NBSDecoder;
import br.com.floodeer.ultragadgets.songAPI.RadioSongPlayer;
import br.com.floodeer.ultragadgets.songAPI.Song;
import br.com.floodeer.ultragadgets.songAPI.SongPlayer;
import br.com.floodeer.ultragadgets.util.ItemFactory;
import br.com.floodeer.ultragadgets.util.ParticleEffect;
import br.com.floodeer.ultragadgets.util.PlayerUtils;
import br.com.floodeer.ultragadgets.util.UtilBlock;
import br.com.floodeer.ultragadgets.util.UtilCooldown;
import br.com.floodeer.ultragadgets.util.UtilFirework;
import br.com.floodeer.ultragadgets.util.UtilMath;
import br.com.floodeer.ultragadgets.util.UtilParticle;
import br.com.floodeer.ultragadgets.util.UtilTitle;
import br.com.floodeer.ultragadgets.util.UtilParticle.ParticleType;
import br.com.floodeer.ultragadgets.util.UtilVelocity;

public class DiscoBallGadget extends Gadget implements Listener {

	private static final Map<String, SongPlayer> song = new HashMap<String, SongPlayer>();
	private static String SONG_NAME = UltraGadgets.getCfg().discoballSom;
	private static boolean NERF = UltraGadgets.getCfg().nerfGadgetsLags;
	private List<Player> near = new ArrayList<>();

	private static ItemStack item() {
		return ItemFactory.buildItemStackArrays(Material.STAINED_GLASS, (byte) 12);
	}

	public DiscoBallGadget() {
		super(UltraGadgets.getCfg().discoBallCooldown * 1000, Gadgets.DISCO_BALL.toString(), Gadgets.DISCO_BALL,item());
	}

	File file = new File(UltraGadgets.get().getDataFolder() + File.separator + "sons" + File.separator + SONG_NAME + ".nbs");

	public void startEffects(final Player p) {
		final PlayerUtils pUtils = new PlayerUtils();
		final Location l = p.getLocation().clone().add(0, 5.0, 0);
        final ArmorStand armorStand = (ArmorStand) p.getWorld().spawnEntity(l.clone().subtract(0, 1.5, 0), EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setSmall(false);
        armorStand.setHelmet(ItemFactory.buildItemStackArrays(Material.STAINED_GLASS, (byte)14));
		if (!file.exists()) {
			UltraGadgets.log("&cFalha ao encontrar som &e" + SONG_NAME + ".nbs");
			p.sendMessage(ChatColor.RED + "Ocorreu um erro interno ao iniciar eventos desse Gadget!");
			return;
		}
		int delay = 1;
		if (NERF) {
			delay = 3;
		}
		Song s = NBSDecoder.parse(file);
		SongPlayer r = new RadioSongPlayer(s);
		r.setAutoDestroy(true);
		for (Player players : l.getWorld().getPlayers()) {
			r.addPlayer(players);
			r.setPlaying(true);
		}
		song.put(p.getName(), r);
		new BukkitRunnable() {
			int steps = 0;

			@Override
			public void run() {
				++steps;
				if (steps >= UltraGadgets.getCfg().discoBallCooldown / 2 * 20 || !p.isOnline() || !Gadgets.hasGadgetSelected(p, Gadgets.DISCO_BALL)) {
					song.get(p.getName()).setPlaying(false);
					song.get(p.getName()).destroy();
					cancel();
					return;
				}
				if(steps % 10 == 1) {
					byte r = (byte) UtilMath.random.nextInt(15);
					for (Block b : UtilBlock.getBlocksInRadius(armorStand.getEyeLocation().add(-.5d, -.5d, -.5d), 10, false)) {
			          if (b.getType() == Material.WOOL || b.getType() == Material.CARPET) {
			             UtilBlock.setToRestore(b, b.getType(), r, 4);
			          }
				    } 
				}
				float lenght = 6.0F;
				double angularVelocity = 0.07853981633974483D;
				Vector direction = UtilVelocity.getRandomVectorline();
				double Rotation = 0.0D;
				Rotation = ServerStep.step * angularVelocity;
				direction.setY(-Math.abs(direction.getY()));
				for (int i = 0; i < 80; i++) {
					float ratio = i * lenght / 80.0F;
					Vector v = direction.clone().multiply(ratio);
					UtilVelocity.rotateVector(v, Rotation, Rotation, Rotation);
					ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(UtilFirework.getRandomBukkitColor()), l.clone().add(-.5, 0, -.5).clone().add(0.5, 0, 0.5).clone().add(v), 50);
				}
			}
		}.runTaskTimer(UltraGadgets.get(), 0, delay);
		new BukkitRunnable() {
			int steps = 0;

			@Override
			public void run() {
				++steps;
				if (steps >= UltraGadgets.getCfg().discoBallCooldown / 2 || !p.isOnline() || !Gadgets.hasGadgetSelected(p, Gadgets.DISCO_BALL)) {
					cancel();
					song.get(p.getName()).setPlaying(false);
					song.get(p.getName()).destroy();
					l.getBlock().getWorld().playEffect(l, Effect.STEP_SOUND, 20);
					for(Player players : near) {
						pUtils.restoreArmorContents(players);
					}
					armorStand.remove();
					return;
				}
				byte r = (byte) UtilMath.random.nextInt(15);
				armorStand.setHelmet(ItemFactory.buildItemStackArrays(Material.STAINED_GLASS, r));
				for (Player players : Bukkit.getOnlinePlayers()) {
				    if(l.distance(p.getLocation()) <= 18) {
				    if(!near.contains(players)) {
				    pUtils.saveArmorContents(players);
				    near.add(players);
				    players.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
				    players.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
				    players.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
				    players.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
				    }
					for (ItemStack localItemStack : players.getInventory().getArmorContents()) {
						if ((localItemStack.getItemMeta() instanceof LeatherArmorMeta)) {
							LeatherArmorMeta localLeatherArmorMeta = (LeatherArmorMeta) localItemStack.getItemMeta();
							Color c = Color.fromRGB(UtilMath.random.nextInt(255), UtilMath.random.nextInt(255),UtilMath.random.nextInt(255));
							localLeatherArmorMeta.setColor(c);
							localItemStack.setItemMeta(localLeatherArmorMeta);
						 }
					  }
				   }else{
					   near.remove(players);
					   pUtils.restoreArmorContents(p);
				   }
				}
				if (!NERF) {
				  new UtilParticle(ParticleType.FIREWORKS_SPARK, 0.10000000149011612D, 15, 3.30000001192092896D).sendToLocation(l);
				  ParticleEffect.NOTE.display(new ParticleEffect.NoteColor(r), l.clone().add(0, 0.5, 0), 128);   
				
				}else
					new UtilParticle(ParticleType.FIREWORKS_SPARK, 0.10000000149011612D, 5, 2.3179206D).sendToLocation(l);
			}
		}.runTaskTimer(UltraGadgets.get(), 0, 20);
	}

	@Override
	void onInteract(Player p) {
		startEffects(p);
	}

	@Override
	void onCooldown(Player p) {
		long cooldown = UtilCooldown.getCooldown(p, this.gadgetName) / 1000;
		UtilTitle title = new UtilTitle(
		UltraGadgets.getCfg().title.replaceAll("<cooldown>", String.valueOf(cooldown)).replaceAll("<gadget>",
		UltraGadgets.getCfg().discoBallNome),
		UltraGadgets.getCfg().subtitle.replaceAll("<cooldown>", String.valueOf(cooldown)).replaceAll("<gadget>",
	    UltraGadgets.getCfg().discoBallNome).replaceAll("&", "§"),
		6, 8, 6);
		title.setTimingsToTicks();
		title.send(p);
	}
}

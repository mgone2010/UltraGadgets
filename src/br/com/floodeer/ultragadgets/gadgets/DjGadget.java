package br.com.floodeer.ultragadgets.gadgets;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import br.com.floodeer.ultragadgets.UltraGadgets;
import br.com.floodeer.ultragadgets.enumeration.Gadgets;
import br.com.floodeer.ultragadgets.listener.ServerStep;
import br.com.floodeer.ultragadgets.particles.GenericParticleTypes;
import br.com.floodeer.ultragadgets.scheduler.SchedulerEvent;
import br.com.floodeer.ultragadgets.scheduler.SchedulerType;
import br.com.floodeer.ultragadgets.songAPI.NBSDecoder;
import br.com.floodeer.ultragadgets.songAPI.RadioSongPlayer;
import br.com.floodeer.ultragadgets.songAPI.Song;
import br.com.floodeer.ultragadgets.songAPI.SongPlayer;
import br.com.floodeer.ultragadgets.util.ItemFactory;
import br.com.floodeer.ultragadgets.util.ParticleEffect;
import br.com.floodeer.ultragadgets.util.UtilBlock;
import br.com.floodeer.ultragadgets.util.UtilCooldown;
import br.com.floodeer.ultragadgets.util.UtilEnt;
import br.com.floodeer.ultragadgets.util.UtilFirework;
import br.com.floodeer.ultragadgets.util.UtilLocations;
import br.com.floodeer.ultragadgets.util.UtilMath;
import br.com.floodeer.ultragadgets.util.UtilMenu;
import br.com.floodeer.ultragadgets.util.UtilTitle;
import br.com.floodeer.ultragadgets.util.UtilVelocity;
import br.com.floodeer.ultragadgets.util.UtilParticle.ParticleType;

public class DjGadget extends Gadget implements Listener {

	public DjGadget() {
		super(UltraGadgets.getCfg().djCooldown * 1000, Gadgets.DJ.toString(), Gadgets.DJ, Material.JUKEBOX);
	}

	enum Effects {
		DISCO, NOTE, PARTICULAS, DISCOBALL, JUMP;
	}

	float i = -2.0F;
	boolean loop = true;
	float i2 = 0.0F;
	boolean loop2 = false;
	float i3 = 0.0F;
	boolean loop3 = false;
	private Location ext1;
	private Location ext2;
	private Location discLocation;

	Map<Player, DJ> djPlayer = new HashMap<>();
	Map<Player, ArmorStand> stands = new HashMap<>();
	final Map<String, SongPlayer> song = new HashMap<String, SongPlayer>();
	final HashMap<ArmorStand, Boolean> animations = new HashMap<>();
	final HashMap<ArmorStand, List<Effects>> particles = new HashMap<>();
	final List<Effects> effects = new ArrayList<>();
	final File song1 = new File(UltraGadgets.get().getDataFolder() + File.separator + "sons" + File.separator
			+ UltraGadgets.getCfg().djSom1 + ".nbs");
	final File song2 = new File(UltraGadgets.get().getDataFolder() + File.separator + "sons" + File.separator
			+ UltraGadgets.getCfg().djSom2 + ".nbs");
	final File song3 = new File(UltraGadgets.get().getDataFolder() + File.separator + "sons" + File.separator
			+ UltraGadgets.getCfg().djSom3 + ".nbs");

	boolean exists() {
		if (song1.exists() && song2.exists() && song3.exists()) {
			UltraGadgets.log("&eSons encontrados!");
			return true;
		}
		return false;
	}

	public void showGadgetsActions(Player p) {
		UtilMenu gadgetActions = new UtilMenu(UltraGadgets.get(), p, "§eDj Config", 3);
		gadgetActions.setItem(0,
				ItemFactory.buildItemStack(Material.JUKEBOX, "§e§lMúsicas", Arrays.asList("§bSelecione uma música")));
		gadgetActions.setItem(9, ItemFactory.buildItemStack(Material.BLAZE_POWDER, "§e§lPartículas",
				Arrays.asList("§bAtive/Desative efeitos")));
		gadgetActions.setItem(18, ItemFactory.buildItemStack(Material.ARMOR_STAND, "§e§lAnimações",
				Arrays.asList("§bAtive/Desative animações")));

		gadgetActions.setItem(2,
				ItemFactory.buildItemStack(Material.RECORD_10, UltraGadgets.getCfg().djSom1.replaceAll("&", "§"),
						Arrays.asList("§eClique para ativar trocar a musica!")));
		gadgetActions.setItem(3,
				ItemFactory.buildItemStack(Material.RECORD_9, UltraGadgets.getCfg().djSom2.replaceAll("&", "§"),
						Arrays.asList("§eClique para ativar trocar a musica!")));
		gadgetActions.setItem(4,
				ItemFactory.buildItemStack(Material.RECORD_7, UltraGadgets.getCfg().djSom3.replaceAll("&", "§"),
						Arrays.asList("§eClique para ativar trocar a musica!")));

		gadgetActions.setItem(11, ItemFactory.buildItemStack(Material.NETHER_STAR, "§e§lNote Effect",
				Arrays.asList("§eClique para ativar/desativar!")));
		gadgetActions.setItem(12, ItemFactory.buildItemStack(Material.RECORD_11, "§e§lEfeito de Disco",
				Arrays.asList("§eClique para ativar/desativar!")));
		gadgetActions.setItem(13, ItemFactory.buildItemStack(Material.BEACON, "§e§lPartículas",
				Arrays.asList("§eClique para ativar/desativar!")));
		gadgetActions.setItem(14, ItemFactory.buildItemStack(Material.STAINED_GLASS, "§e§lDiscoBall",
				Arrays.asList("§eClique para ativar/desativar!")));

		gadgetActions.setItem(20, ItemFactory.buildItemStack(Material.TORCH, "§e§lAnimação",
				Arrays.asList("§eClique para ativar/desativar!")));
		gadgetActions.setItem(21, ItemFactory.buildItemStack(Material.SLIME_BALL, "§e§lPulo",
				Arrays.asList("§eClique para ativar/desativar!")));

		gadgetActions.build();
		gadgetActions.showMenu(p);
	}

	public void play(Player player, int songID) {
		if (songID == 1) {
			Song s = NBSDecoder.parse(song1);
			SongPlayer r = new RadioSongPlayer(s);
			r.setAutoDestroy(true);
			for (Player p : player.getWorld().getPlayers()) {
				if (song.containsKey(p.getName())) {
					song.get(p.getName()).setPlaying(false);
					song.get(p.getName()).removePlayer(p);
				}
				r.addPlayer(p);
				song.put(p.getName(), r);
			}
			r.setPlaying(true);
		} else if (songID == 2) {
			Song s = NBSDecoder.parse(song2);
			SongPlayer r = new RadioSongPlayer(s);
			r.setAutoDestroy(true);
			for (Player p : player.getWorld().getPlayers()) {
				if (song.containsKey(p.getName())) {
					song.get(p.getName()).setPlaying(false);
					song.get(p.getName()).removePlayer(p);
				}
				r.addPlayer(p);
				song.put(p.getName(), r);
			}
			r.setPlaying(true);
		} else if (songID == 3) {
			Song s = NBSDecoder.parse(song3);
			SongPlayer r = new RadioSongPlayer(s);
			r.setAutoDestroy(true);
			for (Player p : player.getWorld().getPlayers()) {
				if (song.containsKey(p.getName())) {
					song.get(p.getName()).setPlaying(false);
					song.get(p.getName()).removePlayer(p);
				}
				r.addPlayer(p);
				song.put(p.getName(), r);
			}
			r.setPlaying(true);
		}
	}

	public void discEffect(final Location l) {
		final int i = Bukkit.getScheduler().runTaskTimer(UltraGadgets.get(), new Runnable() {
			public void run() {
				double random = UtilMath.randomRange(2256, 2267);
				ItemStack disc = ItemFactory.buildItemStack(Material.RECORD_11, String.valueOf(random), "");
				Entity e = UtilEnt.dropItemToRemove(disc, l, "gadget", 1);
				e.setVelocity(new Vector(UtilMath.randomRange(-0.5F, 0.5F), UtilMath.randomRange(0.0F, 1.0F),
						UtilMath.randomRange(-0.5F, 0.5F)));
			}
		}, 1L, 1L).getTaskId();
		Bukkit.getServer().getScheduler().runTaskLater(UltraGadgets.get(), new Runnable() {
			public void run() {
				Bukkit.getScheduler().cancelTask(i);
			}
		}, 40L);
	}

	@EventHandler
	public void onArmorStand(PlayerInteractAtEntityEvent e) {
		if (e.getRightClicked().hasMetadata("djGadget")) {
			e.setCancelled(true);
			if (stands.containsKey(e.getPlayer())) {
				showGadgetsActions(e.getPlayer());
			}
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory().getName().equalsIgnoreCase("§eDj Config")) {
			e.setCancelled(true);
			int slot = e.getRawSlot();
			if (slot == 2) {
				play((Player) e.getWhoClicked(), 1);
			} else if (slot == 3) {
				play((Player) e.getWhoClicked(), 2);
			} else if (slot == 4) {
				play((Player) e.getWhoClicked(), 3);
			} else if (slot == 20) {
				ArmorStand stand = djPlayer.get(e.getWhoClicked()).getStand();
				if (animations.get(stand)) {
					animations.put(stand, false);
					e.getWhoClicked().sendMessage("§cDesativado animações.");
				} else {
					animations.put(stand, true);
					e.getWhoClicked().sendMessage("§aAtivado animações!");
				}
			} else if (slot == 11) {
				ArmorStand stand = djPlayer.get(e.getWhoClicked()).getStand();
				if (particles.get(stand).contains(Effects.NOTE)) {
					effects.remove(Effects.NOTE);
					particles.put(stand, effects);
					e.getWhoClicked().sendMessage("§cDesativado partículas de nota.");
				} else {
					effects.add(Effects.NOTE);
					particles.put(stand, effects);
					e.getWhoClicked().sendMessage("§aAtivado partículas de nota!");
				}
			} else if (slot == 12) {
				ArmorStand stand = djPlayer.get(e.getWhoClicked()).getStand();
				if (particles.get(stand).contains(Effects.DISCO)) {
					effects.remove(Effects.DISCO);
					particles.put(stand, effects);
					e.getWhoClicked().sendMessage("§cDesativado efeitos de disco.");
				} else {
					effects.add(Effects.DISCO);
					particles.put(stand, effects);
					e.getWhoClicked().sendMessage("§aAtivado efeitos de disco!");
				}
			} else if (slot == 13) {
				ArmorStand stand = djPlayer.get(e.getWhoClicked()).getStand();
				if (particles.get(stand).contains(Effects.PARTICULAS)) {
					effects.remove(Effects.PARTICULAS);
					particles.put(stand, effects);
					e.getWhoClicked().sendMessage("§cDesativado partículas.");
				} else {
					effects.add(Effects.PARTICULAS);
					particles.put(stand, effects);
					e.getWhoClicked().sendMessage("§aAtivado partículas!");
				}
			} else if (slot == 14) {
				ArmorStand stand = djPlayer.get(e.getWhoClicked()).getStand();
				if (particles.get(stand).contains(Effects.DISCOBALL)) {
					effects.remove(Effects.DISCOBALL);
					particles.put(stand, effects);
					e.getWhoClicked().sendMessage("§cDesativado DiscoBall.");
				} else {
					effects.add(Effects.DISCOBALL);
					particles.put(stand, effects);
					e.getWhoClicked().sendMessage("§aAtivado partículas de DiscoBall");
				}
			} else if (slot == 21) {
				ArmorStand stand = djPlayer.get(e.getWhoClicked()).getStand();
				if (particles.get(stand).contains(Effects.JUMP)) {
					effects.remove(Effects.JUMP);
					particles.put(stand, effects);
					e.getWhoClicked().sendMessage("§cDesativado efeitos de pulo!");
				} else {
					effects.add(Effects.JUMP);
					particles.put(stand, effects);
					e.getWhoClicked().sendMessage("§aAtivado efeitos de pulo!");
				}
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof ArmorStand && e.getEntity().hasMetadata("djGadget")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onScheduler(SchedulerEvent e) {
		if (!animations.isEmpty()) {
			ArmorStand a = null;
			for (ArmorStand armor : animations.keySet()) {
				a = armor;
			}
			if (e.getType() == SchedulerType.FASTEST) {
				if (this.loop3) {
					this.i3 += 0.2F;
					if (this.i3 >= 0.0F) {
						this.loop3 = false;
					}
				} else {
					this.i3 -= 0.2F;
					if (this.i3 <= -0.2D) {
						this.loop3 = true;
					}
				}
				if (this.loop) {
					this.i += 0.1F;
					if (this.i >= -2.0F) {
						this.loop = false;
					}
				} else {
					this.i -= 0.1F;
					if (this.i >= -3.0F) {
						this.loop = true;
					}
				}
				if (animations.get(a) == true) {
					a.setRightArmPose(new EulerAngle(this.i, 0.0D, 0.0D));
				}
			}
		}
		if (e.getType() == SchedulerType.TICK) {
			for (ArmorStand a : animations.keySet()) {
				if (particles.get(a).contains(Effects.DISCOBALL)) {
					Location l = a.getLocation().clone().add(0, 7.5, 0);
					float lenght = 10.0F;
					double angularVelocity = 0.07853981633974483D;
					Vector direction = UtilVelocity.getRandomVectorline();
					double Rotation = 0.0D;
					Rotation = ServerStep.step * angularVelocity;
					direction.setY(-Math.abs(direction.getY()));
					for (int i = 0; i < 80; i++) {
						float ratio = i * lenght / 80.0F;
						Vector v = direction.clone().multiply(ratio);
						UtilVelocity.rotateVector(v, Rotation, Rotation, Rotation);
						ParticleEffect.REDSTONE.display(
								new ParticleEffect.OrdinaryColor(UtilFirework.getRandomBukkitColor()), l.add(v), 35);
						l.subtract(v);
					}
				}
			}
			if (this.loop2) {
				this.i2 += 0.1F;
				if (this.i2 >= 0.0F) {
					this.loop2 = false;
				}
			} else {
				this.i2 -= 0.1F;
				if (this.i2 <= -0.5F) {
					this.loop2 = true;
				}
			}
			for (ArmorStand a : animations.keySet()) {
				if (animations.get(a) == true) {
					a.setLeftArmPose(new EulerAngle(-1.0D, -this.i2, 0.0D));
				}
				if (animations.get(a) == true) {
					a.setHeadPose(new EulerAngle(-this.i3, 0.0D, 0.0D));
				}
			}
		}
		if (e.getType() == SchedulerType.SLOW) {
			if (!animations.isEmpty()) {
				ArmorStand a = null;
				for (ArmorStand armor : animations.keySet()) {
					a = armor;
				}
					if (particles.get(a).contains(Effects.DISCO)) {
						if (particles.get(a).contains(Effects.JUMP)) {
							for (Player p : UtilLocations.getNearbyPlayers(a.getLocation(), 12)) {
								p.setVelocity(p.getVelocity().add(new Vector(0, UtilMath.randomRange(0.3, 0.6), 0)));
							}
						}
					}
				if (particles.get(a).contains(Effects.DISCO)) {
					discEffect(discLocation.clone());
					if (particles.get(a).contains(Effects.JUMP)) {
						for (Player p : UtilLocations.getNearbyPlayers(a.getLocation(), 12)) {
							p.setVelocity(p.getVelocity().add(new Vector(0, UtilMath.randomRange(0.3, 0.6), 0)));
						}
					}
				}
				if (this.loop == false) {
					this.loop = true;
				} else {
					this.loop = false;
				}
				if (particles.get(a).contains(Effects.PARTICULAS)) {
					GenericParticleTypes.playSpiral(ext1, ParticleType.FLAME, ParticleType.LAVA, this.loop);
					GenericParticleTypes.playSpiral(ext2, ParticleType.FLAME, ParticleType.LAVA, this.loop);
					GenericParticleTypes.playSpiral(ext1, ParticleType.CLOUD, ParticleType.FIREWORKS_SPARK, this.loop);
					GenericParticleTypes.playSpiral(ext2, ParticleType.CLOUD, ParticleType.FIREWORKS_SPARK, this.loop);
				}
			}
		}
	}

	@EventHandler
	public void onPickupDisc(PlayerPickupItemEvent e) {
		if (e.getItem().hasMetadata("gadget")) {
			e.setCancelled(true);
		}
	}

	List<DJ> djs = new ArrayList<>();

	private void buildPlataform(final Player p) {
		Location loc = p.getLocation();
		if (!UtilBlock.isOnGround(p)) {
			p.sendMessage(ChatColor.RED + "Você precisa estar no chão!");
			UtilCooldown.setCooldown(p, this.gadgetName, 0);
			return;
		}
		if (!checkArea(p)) {
			p.sendMessage(ChatColor.RED + "Você precisa de mais espaço!");
			UtilCooldown.setCooldown(p, this.gadgetName, 0);
			return;
		}
		final DjPlateform plataform = new DjPlateform(loc.subtract(0, 1, 0), p);
		final DJ dj = new DJ(p.getUniqueId());
		p.teleport(p.getLocation().clone().add(0, 2, 0));
		djPlayer.put(p, dj);
		djs.add(dj);
		dj.setDjPlataform(plataform);
		dj.setArmorStand();
		plataform.buildSupport(43, 8, 44, 8, Material.STAINED_GLASS, 0, dj.getStand());
		this.ext1 = plataform.extremity1;
		this.ext2 = plataform.extremity2;
		discLocation = dj.getStand().getLocation();
		discLocation.setDirection(dj.getStand().getLocation().getDirection());
		new BukkitRunnable() {
			float j = 0.0F;

			@Override
			public void run() {
				if (!p.isOnline() || Gadgets.getPlayerGadget(p) != Gadgets.DJ || !djPlayer.containsKey(p)
						|| p.getWorld() != dj.getStand().getWorld()) {
					plataform.destroy();
					particles.remove(dj.getStand());
					stands.remove(dj.getStand());
					animations.remove(dj.getStand());
					dj.getStand().remove();
					djs.remove(dj);
					djPlayer.remove(p);
					song.get(p.getName()).setPlaying(false);
					song.get(p.getName()).destroy();
					cancel();
				} else {
					if (particles.get(dj.getStand()).contains(Effects.NOTE)) {
						Location loc = dj.getStand().getLocation();
						loc.setY(loc.getY() + 2.2D + 0.03D);
						for (int k = 0; k < 1.0F; k++) {
							loc.add(Math.cos(j) * 0.6000000238418579D, j * 0.01F, Math.sin(j) * 0.6000000238418579D);
							ParticleEffect.NOTE.display(0.0F, 0.0F, 0.0F, 1.0F, 1, loc, 25.0D);
						}
						j += 0.2F;
						if (j > 50.0F) {
							j = 0.0F;
						}
					}
				}
			}
		}.runTaskTimer(UltraGadgets.get(), 0, 1);
		new BukkitRunnable() {
			@Override
			public void run() {
				if (djPlayer.containsKey(p)) {
					plataform.destroy();
					particles.remove(dj.getStand());
					stands.remove(dj.getStand());
					animations.remove(dj.getStand());
					dj.getStand().remove();
					djs.remove(dj);
					djPlayer.remove(p);
					song.get(p.getName()).setPlaying(false);
					song.get(p.getName()).destroy();
				}
			}
		}.runTaskLater(UltraGadgets.get(), UltraGadgets.getCfg().djTempo * 20);
	}

	@Override
	void onInteract(Player p) {
		for (DJ dj : djs) {
			if (dj.getDjPlataform().getArmorStandLocation().getWorld() == p.getWorld()) {
				p.sendMessage(ChatColor.RED + "Já há um Dj no seu mundo!");
				return;
			}
		}
		buildPlataform(p);
	}

	@Override
	void onCooldown(Player p) {
		long cooldown = UtilCooldown.getCooldown(p, this.gadgetName) / 1000;
		UtilTitle title = new UtilTitle(
				UltraGadgets.getCfg().title.replaceAll("<cooldown>", String.valueOf(cooldown)).replaceAll("<gadget>",
						Gadgets.getPlayerGadget(p).toString().toLowerCase().replaceAll("_", "")),
				UltraGadgets.getCfg().subtitle.replaceAll("<cooldown>", String.valueOf(cooldown))
						.replaceAll("<gadget>", Gadgets.getPlayerGadget(p).toString().toLowerCase().replaceAll("_", ""))
						.replaceAll("&", "§"),
				6, 8, 6);
		title.setTimingsToTicks();
		title.send(p);
	}

	final class DJ {

		private UUID djOwner;
		private DjPlateform djPlataform;
		private ArmorStand armor;
		private String song;
		private boolean particles;
		private boolean jumpEffect;
		private boolean useMoviment;
		private boolean swag;

		public DJ(UUID owner) {
			this.setDjOwner(owner);
			setSwag(true);
			setJumpEffect(true);
			setUseMoviment(true);
			setParticles(true);
		}

		public void setArmorStand() {
			armor = Bukkit.getPlayer(djOwner).getLocation().getWorld().spawn(djPlataform.getArmorStandLocation(),
					ArmorStand.class);
		}

		public ArmorStand getStand() {
			return armor;
		}

		public UUID getDjOwner() {
			return djOwner;
		}

		public void setDjOwner(UUID djOwner) {
			this.djOwner = djOwner;
		}

		public String getSong() {
			return song;
		}

		public void setSong(String song) {
			this.song = song;
		}

		public boolean isParticles() {
			return particles;
		}

		public void setParticles(boolean particles) {
			this.particles = particles;
		}

		public boolean isJumpEffect() {
			return jumpEffect;
		}

		public void setJumpEffect(boolean jumpEffect) {
			this.jumpEffect = jumpEffect;
		}

		public boolean isUseMoviment() {
			return useMoviment;
		}

		public void setUseMoviment(boolean useMoviment) {
			this.useMoviment = useMoviment;
		}

		public boolean isSwag() {
			return swag;
		}

		public void setSwag(boolean swag) {
			this.swag = swag;
		}

		public DjPlateform getDjPlataform() {
			return djPlataform;
		}

		public void setDjPlataform(DjPlateform djPlataform) {
			this.djPlataform = djPlataform;
		}
	}

	class DjPlateform {
		Location l;
		Player p;
		public Location extremity1;
		public Location extremity2;
		public Location jukebox;
		public Location laser;
		public Vector direction;

		public DjPlateform(Location l, Player p) {
			this.l = l;
			this.p = p;
		}

		public Location getArmorStandLocation() {
			Location loc = this.l.clone().add(0.0D, 2.0D, 0.0D).getBlock().getLocation().add(0.5D, 4.0D, 0.5D);
			loc.setPitch(this.l.getPitch());
			loc.setYaw(this.l.getYaw());
			return loc;
		}

		public boolean locationIsEmpty(Location l) {
			LinkedList<Location> locations = new LinkedList<>();
			Location support = l.clone();
			boolean isempty = true;
			if ((UtilLocations.yawToFace(support.getYaw()) == BlockFace.NORTH)
					|| (UtilLocations.yawToFace(support.getYaw()) == BlockFace.NORTH_EAST)
					|| (UtilLocations.yawToFace(support.getYaw()) == BlockFace.NORTH_NORTH_EAST)
					|| (UtilLocations.yawToFace(support.getYaw()) == BlockFace.NORTH_NORTH_WEST)
					|| (UtilLocations.yawToFace(support.getYaw()) == BlockFace.NORTH_WEST)) {
				locations.clear();
				locations.addAll(Arrays
						.asList(new Location[] { support.add(0.0D, 1.0D, 0.0D), support.add(0.0D, 0.0D, -1.0D).clone(),
								support.add(1.0D, 0.0D, 0.0D).clone(), support.add(1.0D, 0.0D, 0.0D).clone(),
								support.add(-3.0D, 0.0D, 0.0D).clone(), support.add(-1.0D, 0.0D, 0.0D).clone(),
								support.add(0.0D, 0.0D, 1.0D).clone(), support.add(0.0D, 0.0D, 1.0D).clone(),
								support.add(1.0D, 0.0D, 0.0D).clone(), support.add(0.0D, 1.0D, 0.0D).clone(),
								support.add(1.0D, -1.0D, 0.0D).clone(), support.add(0.0D, 0.0D, -1.0D).clone(),
								support.add(1.0D, 0.0D, 0.0D).clone(), support.add(0.0D, 1.0D, 0.0D).clone(),
								support.add(1.0D, -1.0D, 0.0D).clone(), support.add(0.0D, 0.0D, -1.0D).clone(),
								support.add(-1.0D, 0.0D, 0.0D).clone(), support.add(-2.0D, 0.0D, 0.0D).clone() }));
				for (Location loc : locations) {
					if (!loc.getBlock().isEmpty()) {
						isempty = false;
						break;
					}
				}
			} else if ((UtilLocations.yawToFace(support.getYaw()) == BlockFace.WEST)
					|| (UtilLocations.yawToFace(support.getYaw()) == BlockFace.WEST_NORTH_WEST)
					|| (UtilLocations.yawToFace(support.getYaw()) == BlockFace.WEST_SOUTH_WEST)) {
				locations.clear();
				locations.addAll(Arrays.asList(
						new Location[] { support.clone().add(0.0D, 1.0D, 0.0D), support.clone().add(-1.0D, 0.0D, 0.0D),
								support.clone().add(0.0D, 0.0D, 1.0D), support.clone().add(0.0D, 0.0D, 1.0D),
								support.clone().add(0.0D, 0.0D, -3.0D), support.clone().add(0.0D, 0.0D, -1.0D),
								support.clone().add(1.0D, 0.0D, 0.0D), support.clone().add(1.0D, 0.0D, 0.0D),
								support.clone().add(0.0D, 0.0D, 1.0D), support.clone().add(0.0D, 1.0D, 0.0D),
								support.clone().add(0.0D, -1.0D, 1.0D), support.clone().add(-1.0D, 0.0D, 0.0D),
								support.clone().add(0.0D, 0.0D, 1.0D), support.clone().add(0.0D, 1.0D, 0.0D),
								support.clone().add(0.0D, -1.0D, 1.0D), support.clone().add(-1.0D, 0.0D, 0.0D),
								support.clone().add(0.0D, 0.0D, -1.0D), support.clone().add(0.0D, 0.0D, -2.0D) }));
				for (Location loc : locations) {
					if (!loc.getBlock().isEmpty()) {
						isempty = false;
						break;
					}
				}
			} else if ((UtilLocations.yawToFace(support.getYaw()) == BlockFace.EAST)
					|| (UtilLocations.yawToFace(support.getYaw()) == BlockFace.EAST_NORTH_EAST)
					|| (UtilLocations.yawToFace(support.getYaw()) == BlockFace.EAST_SOUTH_EAST)) {
				locations.clear();
				locations.addAll(Arrays.asList(
						new Location[] { support.clone().add(0.0D, 1.0D, 0.0D), support.clone().add(1.0D, 0.0D, 0.0D),
								support.clone().add(0.0D, 0.0D, -1.0D), support.clone().add(0.0D, 0.0D, -1.0D),
								support.clone().add(0.0D, 0.0D, 3.0D), support.clone().add(0.0D, 0.0D, 1.0D),
								support.clone().add(-1.0D, 0.0D, 0.0D), support.clone().add(-1.0D, 0.0D, 0.0D),
								support.clone().add(0.0D, 0.0D, -1.0D), support.clone().add(0.0D, 1.0D, 0.0D),
								support.clone().add(0.0D, -1.0D, -1.0D), support.clone().add(1.0D, 0.0D, 0.0D),
								support.clone().add(0.0D, 0.0D, -1.0D), support.clone().add(0.0D, 1.0D, 0.0D),
								support.clone().add(0.0D, -1.0D, -1.0D), support.clone().add(1.0D, 0.0D, 0.0D),
								support.clone().add(0.0D, 0.0D, 1.0D), support.clone().add(0.0D, 0.0D, 2.0D) }));
				for (Location loc : locations) {
					if (!loc.getBlock().isEmpty()) {
						isempty = false;
						break;
					}
				}
			} else if ((UtilLocations.yawToFace(support.getYaw()) == BlockFace.SOUTH)
					|| (UtilLocations.yawToFace(support.getYaw()) == BlockFace.SOUTH_EAST)
					|| (UtilLocations.yawToFace(support.getYaw()) == BlockFace.SOUTH_SOUTH_WEST)
					|| (UtilLocations.yawToFace(support.getYaw()) == BlockFace.SOUTH_WEST)
					|| (UtilLocations.yawToFace(support.getYaw()) == BlockFace.SOUTH_SOUTH_EAST)) {
				locations.clear();
				locations.addAll(Arrays.asList(
						new Location[] { support.clone().add(0.0D, 1.0D, 0.0D), support.clone().add(0.0D, 0.0D, 1.0D),
								support.clone().add(-1.0D, 0.0D, 0.0D), support.clone().add(-1.0D, 0.0D, 0.0D),
								support.clone().add(3.0D, 0.0D, 0.0D), support.clone().add(1.0D, 0.0D, 0.0D),
								support.clone().add(0.0D, 0.0D, -1.0D), support.clone().add(0.0D, 0.0D, -1.0D),
								support.clone().add(-1.0D, 0.0D, 0.0D), support.clone().add(0.0D, 1.0D, 0.0D),
								support.clone().add(-1.0D, -1.0D, 0.0D), support.clone().add(0.0D, 0.0D, 1.0D),
								support.clone().add(-1.0D, 0.0D, 0.0D), support.clone().add(0.0D, 1.0D, 0.0D),
								support.clone().add(-1.0D, -1.0D, 0.0D), support.clone().add(0.0D, 0.0D, 1.0D),
								support.clone().add(1.0D, 0.0D, 0.0D), support.clone().add(2.0D, 0.0D, 0.0D) }));
				for (Location loc : locations) {
					if (!loc.getBlock().isEmpty()) {
						isempty = false;
						break;
					}
				}
			}
			return isempty;
		}

		public void buildSupport(int m, int data, int slab, int slabdata, Material glow, int glowdata, ArmorStand a) {
			Location support = this.l.clone();
			a.setCustomName(
					UltraGadgets.getCfg().djDisplayName.replaceAll("<player>", p.getName()).replaceAll("&", "§"));
			a.setCustomNameVisible(true);
			a.setBasePlate(false);
			a.setArms(true);
			a.setCanPickupItems(false);
			a.setHelmet(ItemFactory.buildSkull(
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODY4ZjRjZWY5NDlmMzJlMzNlYzVhZTg0NWY5YzU2OTgzY2JlMTMzNzVhNGRlYzQ2ZTViYmZiN2RjYjYifX19",
					1, "Swag", null));
			a.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
			a.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
			a.setBoots(new ItemStack(Material.LEATHER_BOOTS));
			a.setMetadata("djGadget", new FixedMetadataValue(UltraGadgets.get(), "djGadget"));
			stands.put(this.p, a);
			animations.put(a, true);
			effects.add(Effects.NOTE);
			effects.add(Effects.DISCO);
			effects.add(Effects.PARTICULAS);
			effects.add(Effects.DISCOBALL);
			effects.add(Effects.JUMP);
			particles.put(a, effects);
			Song s = NBSDecoder.parse(song3);
			SongPlayer r = new RadioSongPlayer(s);
			r.setAutoDestroy(true);
			for (Player p : a.getWorld().getPlayers()) {
				r.addPlayer(p);
				r.setPlaying(true);
			}
			song.put(p.getName(), r);

			if ((UtilLocations.yawToFace(this.l.getYaw()) == BlockFace.NORTH)
					|| (UtilLocations.yawToFace(this.l.getYaw()) == BlockFace.NORTH_EAST)
					|| (UtilLocations.yawToFace(this.l.getYaw()) == BlockFace.NORTH_NORTH_EAST)
					|| (UtilLocations.yawToFace(this.l.getYaw()) == BlockFace.NORTH_NORTH_WEST)
					|| (UtilLocations.yawToFace(this.l.getYaw()) == BlockFace.NORTH_WEST)) {
				UtilBlock.setBlock(support.add(0.0D, 1.0D, 0.0D), m, data, this.p);
				UtilBlock.setBlock(support.add(0.0D, 0.0D, -1.0D), slab, slabdata, this.p);

				UtilBlock.setBlock(support.add(1.0D, 0.0D, 0.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(1.0D, 0.0D, 0.0D), m, data, this.p);

				UtilBlock.setBlock(support.add(-3.0D, 0.0D, 0.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(-1.0D, 0.0D, 0.0D), m, data, this.p);

				UtilBlock.setBlock(support.add(0.0D, 0.0D, 1.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(0.0D, 0.0D, 1.0D), m, data, this.p);
				this.extremity1 = support.clone();
				this.extremity1.setPitch(this.l.getPitch());
				this.extremity1.setYaw(this.l.getYaw());

				UtilBlock.setBlock(support.add(1.0D, 0.0D, 0.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(0.0D, 1.0D, 0.0D), glow, glowdata, this.p);

				UtilBlock.setBlock(support.add(1.0D, 0.0D, 0.0D), Material.JUKEBOX, 0, p);
				this.jukebox = support.getBlock().getLocation().add(0.5D, 0.0D, 0.5D).clone();
				Vector vector = support.getBlock().getLocation().add(0.5D, 0.0D, 0.5D).toVector()
						.subtract(a.getLocation().toVector());
				this.direction = vector;
				this.jukebox.setDirection(vector);
				a.teleport(a.getLocation().setDirection(vector));

				UtilBlock.setBlock(support.add(0.0D, -1.0D, 0.0D), slab, slabdata, this.p);

				UtilBlock.setBlock(support.add(1.0D, 0.0D, 0.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(0.0D, 1.0D, 0.0D), glow, glowdata, this.p);

				UtilBlock.setBlock(support.add(1.0D, -1.0D, 0.0D), m, data, this.p);
				this.extremity2 = support.clone();
				this.extremity2.setPitch(this.l.getPitch());
				this.extremity2.setYaw(this.l.getYaw());
				UtilBlock.setBlock(support.add(0.0D, 0.0D, -1.0D), slab, slabdata, this.p);

				UtilBlock.setBlock(support.add(-1.0D, 0.0D, 0.0D), m, data, this.p);
				UtilBlock.setBlock(support.add(-2.0D, 0.0D, 0.0D), m, data, this.p);
			} else if ((UtilLocations.yawToFace(this.l.getYaw()) == BlockFace.WEST)
					|| (UtilLocations.yawToFace(this.l.getYaw()) == BlockFace.WEST_NORTH_WEST)
					|| (UtilLocations.yawToFace(this.l.getYaw()) == BlockFace.WEST_SOUTH_WEST)) {
				UtilBlock.setBlock(support.add(0.0D, 1.0D, 0.0D), m, data, this.p);
				UtilBlock.setBlock(support.add(-1.0D, 0.0D, 0.0D), slab, slabdata, this.p);

				UtilBlock.setBlock(support.add(0.0D, 0.0D, 1.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(0.0D, 0.0D, 1.0D), m, data, this.p);

				UtilBlock.setBlock(support.add(0.0D, 0.0D, -3.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(0.0D, 0.0D, -1.0D), m, data, this.p);

				UtilBlock.setBlock(support.add(1.0D, 0.0D, 0.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(1.0D, 0.0D, 0.0D), m, data, this.p);
				this.extremity1 = support.clone();
				this.extremity1.setPitch(this.l.getPitch());
				this.extremity1.setYaw(this.l.getYaw());
				UtilBlock.setBlock(support.add(0.0D, 0.0D, 1.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(0.0D, 1.0D, 0.0D), glow, glowdata, this.p);

				UtilBlock.setBlock(support.add(0.0D, 0.0D, 1.0D), Material.JUKEBOX, 0, this.p);
				this.jukebox = support.getBlock().getLocation().clone();
				Vector vector = support.getBlock().getLocation().add(0.5D, 0.0D, 0.5D).toVector()
						.subtract(a.getLocation().toVector());
				this.direction = vector;
				this.jukebox.setDirection(vector);
				a.teleport(a.getLocation().setDirection(vector));
				UtilBlock.setBlock(support.add(0.0D, -1.0D, 0.0D), slab, slabdata, this.p);

				UtilBlock.setBlock(support.add(0.0D, 0.0D, 1.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(0.0D, 1.0D, 0.0D), glow, glowdata, this.p);

				UtilBlock.setBlock(support.add(0.0D, -1.0D, 1.0D), m, data, this.p);
				this.extremity2 = support.clone();
				UtilBlock.setBlock(support.add(-1.0D, 0.0D, 0.0D), slab, slabdata, this.p);

				UtilBlock.setBlock(support.add(0.0D, 0.0D, -1.0D), m, data, this.p);
				UtilBlock.setBlock(support.add(0.0D, 0.0D, -2.0D), m, data, this.p);
			} else if ((UtilLocations.yawToFace(this.l.getYaw()) == BlockFace.EAST)
					|| (UtilLocations.yawToFace(this.l.getYaw()) == BlockFace.EAST_NORTH_EAST)
					|| (UtilLocations.yawToFace(this.l.getYaw()) == BlockFace.EAST_SOUTH_EAST)) {
				UtilBlock.setBlock(support.add(0.0D, 1.0D, 0.0D), m, data, this.p);
				UtilBlock.setBlock(support.add(1.0D, 0.0D, 0.0D), slab, slabdata, this.p);

				UtilBlock.setBlock(support.add(0.0D, 0.0D, -1.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(0.0D, 0.0D, -1.0D), m, data, this.p);

				UtilBlock.setBlock(support.add(0.0D, 0.0D, 3.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(0.0D, 0.0D, 1.0D), m, data, this.p);

				UtilBlock.setBlock(support.add(-1.0D, 0.0D, 0.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(-1.0D, 0.0D, 0.0D), m, data, this.p);
				this.extremity1 = support.clone();
				this.extremity1.setPitch(this.l.getPitch());
				this.extremity1.setYaw(this.l.getYaw());

				UtilBlock.setBlock(support.add(0.0D, 0.0D, -1.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(0.0D, 1.0D, 0.0D), glow, glowdata, this.p);

				UtilBlock.setBlock(support.add(0.0D, 0.0D, -1.0D), Material.JUKEBOX, 0, this.p);
				this.jukebox = support.getBlock().getLocation().clone();
				Vector vector = support.getBlock().getLocation().add(0.5D, 0.0D, 0.5D).toVector()
						.subtract(a.getLocation().toVector());
				this.direction = vector;
				this.jukebox.setDirection(vector);
				a.teleport(a.getLocation().setDirection(vector));
				UtilBlock.setBlock(support.add(0.0D, -1.0D, 0.0D), slab, slabdata, this.p);

				UtilBlock.setBlock(support.add(0.0D, 0.0D, -1.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(0.0D, 1.0D, 0.0D), glow, glowdata, this.p);

				UtilBlock.setBlock(support.add(0.0D, -1.0D, -1.0D), m, data, this.p);
				this.extremity2 = support.clone();
				this.extremity2.setPitch(this.l.getPitch());
				this.extremity2.setYaw(this.l.getYaw());
				UtilBlock.setBlock(support.add(1.0D, 0.0D, 0.0D), slab, slabdata, this.p);

				UtilBlock.setBlock(support.add(0.0D, 0.0D, 1.0D), m, data, this.p);
				UtilBlock.setBlock(support.add(-0.2000000029802322D, 0.0D, 2.0D), m, data, this.p);
			} else if ((UtilLocations.yawToFace(this.l.getYaw()) == BlockFace.SOUTH)
					|| (UtilLocations.yawToFace(this.l.getYaw()) == BlockFace.SOUTH_EAST)
					|| (UtilLocations.yawToFace(this.l.getYaw()) == BlockFace.SOUTH_SOUTH_WEST)
					|| (UtilLocations.yawToFace(this.l.getYaw()) == BlockFace.SOUTH_WEST)
					|| (UtilLocations.yawToFace(this.l.getYaw()) == BlockFace.SOUTH_SOUTH_EAST)) {
				UtilBlock.setBlock(support.add(0.0D, 1.0D, 0.0D), m, data, this.p);
				UtilBlock.setBlock(support.add(0.0D, 0.0D, 1.0D), slab, slabdata, this.p);

				UtilBlock.setBlock(support.add(-1.0D, 0.0D, 0.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(-1.0D, 0.0D, 0.0D), m, data, this.p);

				UtilBlock.setBlock(support.add(3.0D, 0.0D, 0.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(1.0D, 0.0D, 0.0D), m, data, this.p);

				UtilBlock.setBlock(support.add(0.0D, 0.0D, -1.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(0.0D, 0.0D, -1.0D), m, data, this.p);
				this.extremity1 = support.clone();
				this.extremity1.setPitch(this.l.getPitch());
				this.extremity1.setYaw(this.l.getYaw());

				UtilBlock.setBlock(support.add(-1.0D, 0.0D, 0.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(0.0D, 1.0D, 0.0D), glow, glowdata, this.p);

				UtilBlock.setBlock(support.add(-1.0D, 0.0D, 0.0D), Material.JUKEBOX, 0, this.p);
				this.jukebox = support.getBlock().getLocation().clone();
				Vector vector = support.getBlock().getLocation().add(0.5D, 0.0D, 0.5D).toVector()
						.subtract(a.getLocation().toVector());
				this.direction = vector;
				this.jukebox.setDirection(vector);
				a.teleport(a.getLocation().setDirection(vector));
				UtilBlock.setBlock(support.add(0.0D, -1.0D, 0.0D), slab, slabdata, this.p);

				UtilBlock.setBlock(support.add(-1.0D, 0.0D, 0.0D), slab, slabdata, this.p);
				UtilBlock.setBlock(support.add(0.0D, 1.0D, 0.0D), glow, glowdata, this.p);

				UtilBlock.setBlock(support.add(-1.0D, -1.0D, 0.0D), m, data, this.p);
				this.extremity2 = support.clone();
				this.extremity2.setPitch(this.l.getPitch());
				this.extremity2.setYaw(this.l.getYaw());
				UtilBlock.setBlock(support.add(0.0D, 0.0D, 1.0D), slab, slabdata, this.p);

				UtilBlock.setBlock(support.add(1.0D, 0.0D, 0.0D), m, data, this.p);
				UtilBlock.setBlock(support.add(2.0D, 0.0D, 0.0D), m, data, this.p);
			}
		}

		public void destroy() {
			UtilBlock.restoreBlocks(this.p);
		}
	}

	private ArrayList<Location> createSquare(Block center, int radius) {
		ArrayList<Location> locs = new ArrayList<>();
		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				if ((x == -radius) || (x == radius) || (z == -radius) || (z == radius)) {
					Block block = center.getWorld().getBlockAt(center.getX() + x, center.getY(), center.getZ() + z);
					locs.add(block.getLocation());
				}
			}
		}
		return locs;
	}

	public boolean checkArea(Player p) {
		ArrayList<Location> s0 = createSquare(p.getLocation().getBlock(), 0);
		ArrayList<Location> s1 = createSquare(p.getLocation().getBlock(), 1);
		ArrayList<Location> s2 = createSquare(p.getLocation().getBlock(), 2);
		ArrayList<Location> s3 = createSquare(p.getLocation().getBlock(), 3);
		ArrayList<Location> s4 = createSquare(p.getLocation().getBlock(), 4);

		ArrayList<Location> y0 = createSquare(p.getLocation().add(0.0D, 1.0D, 0.0D).getBlock(), 0);
		ArrayList<Location> y1 = createSquare(p.getLocation().add(0.0D, 1.0D, 0.0D).getBlock(), 1);
		ArrayList<Location> y2 = createSquare(p.getLocation().add(0.0D, 1.0D, 0.0D).getBlock(), 2);
		ArrayList<Location> y3 = createSquare(p.getLocation().add(0.0D, 1.0D, 0.0D).getBlock(), 3);
		ArrayList<Location> y4 = createSquare(p.getLocation().add(0.0D, 1.0D, 0.0D).getBlock(), 4);
		for (Location l0 : s0) {
			if (!l0.getBlock().isEmpty()) {
				return false;
			}
		}
		for (Location l1 : s1) {
			if (!l1.getBlock().isEmpty()) {
				return false;
			}
		}
		for (Location l2 : s2) {
			if (!l2.getBlock().isEmpty()) {
				return false;
			}
		}
		for (Location l3 : s3) {
			if (!l3.getBlock().isEmpty()) {
				return false;
			}
		}
		for (Location l4 : s4) {
			if (!l4.getBlock().isEmpty()) {
				return false;
			}
		}
		for (Location Y0 : y0) {
			if (!Y0.getBlock().isEmpty()) {
				return false;
			}
		}
		for (Location Y1 : y1) {
			if (!Y1.getBlock().isEmpty()) {
				return false;
			}
		}
		for (Location Y2 : y2) {
			if (!Y2.getBlock().isEmpty()) {
				return false;
			}
		}
		for (Location Y3 : y3) {
			if (!Y3.getBlock().isEmpty()) {
				return false;
			}
		}
		for (Location Y4 : y4) {
			if (!Y4.getBlock().isEmpty()) {
				return false;
			}
		}
		return true;
	}
}

package br.com.floodeer.ultragadgets;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.floodeer.ultragadgets.config.ConfigFile;
import br.com.floodeer.ultragadgets.enumeration.Particle;
import br.com.floodeer.ultragadgets.enumeration.Pets;
import br.com.floodeer.ultragadgets.gadgets.BombGadget;
import br.com.floodeer.ultragadgets.gadgets.DiscoBallGadget;
import br.com.floodeer.ultragadgets.gadgets.DjGadget;
import br.com.floodeer.ultragadgets.gadgets.ExplosivePoopGadget;
import br.com.floodeer.ultragadgets.gadgets.FumeganteGadget;
import br.com.floodeer.ultragadgets.gadgets.GravityGadget;
import br.com.floodeer.ultragadgets.gadgets.IceBombGadget;
import br.com.floodeer.ultragadgets.gadgets.MeowGadget;
import br.com.floodeer.ultragadgets.gadgets.PaintballGunGadget;
import br.com.floodeer.ultragadgets.gadgets.ParaquedasGadget;
import br.com.floodeer.ultragadgets.gadgets.PartyPopperGadget;
import br.com.floodeer.ultragadgets.gadgets.SmokeBombGadget;
import br.com.floodeer.ultragadgets.gadgets.TrampolimGadget;
import br.com.floodeer.ultragadgets.gadgets.VectorTNTGadget;
import br.com.floodeer.ultragadgets.gadgets.WitherShootGadget;
import br.com.floodeer.ultragadgets.gadgets.WizardGadget;
import br.com.floodeer.ultragadgets.listener.PlayerListener;
import br.com.floodeer.ultragadgets.listener.PlayerLocationListener;
import br.com.floodeer.ultragadgets.listener.ServerStep;
import br.com.floodeer.ultragadgets.menus.GadgetsMenu;
import br.com.floodeer.ultragadgets.menus.MainMenu;
import br.com.floodeer.ultragadgets.menus.ParticlesMenu;
import br.com.floodeer.ultragadgets.menus.PetMenu;
import br.com.floodeer.ultragadgets.particles.CloudParticle;
import br.com.floodeer.ultragadgets.particles.FrozenParticle;
import br.com.floodeer.ultragadgets.particles.FuriousParticle;
import br.com.floodeer.ultragadgets.particles.HelixParticle;
import br.com.floodeer.ultragadgets.particles.LillyParticle;
import br.com.floodeer.ultragadgets.particles.MagicParticle;
import br.com.floodeer.ultragadgets.particles.RorationParticles;
import br.com.floodeer.ultragadgets.particles.ShieldParticle;
import br.com.floodeer.ultragadgets.particles.TornadoParticle;
import br.com.floodeer.ultragadgets.pets.PetCreator;
import br.com.floodeer.ultragadgets.pets.PetManager;
import br.com.floodeer.ultragadgets.scheduler.SchedulerRunner;
import br.com.floodeer.ultragadgets.util.Glow;
import br.com.floodeer.ultragadgets.util.Gravity;
import br.com.floodeer.ultragadgets.util.PastebinReporter;
import br.com.floodeer.ultragadgets.util.UnZip;

public class UltraGadgets extends JavaPlugin implements Listener {

	private static UltraGadgets ug;
	private static ConfigFile c;
	public static String system;
	public static String javaVersion;
	public static Boolean setupComplete;
	private static PastebinReporter pastebinReporter;

	public static UltraGadgets get() {
		return ug;
	}

	public static ConfigFile getCfg() {
		return c;
	}

	public static PastebinReporter getReporter() {
		return pastebinReporter;
	}
	
	public static UltraPlayer getUPlayer(UUID uuid) {
		return PlayerListener.get(uuid);
	}
	
	public static void log(Object message) {
		Validate.notNull(message, "Não foi possível fazer o log: 'Object não pode ser nulo'");
		String prefix = ChatColor.translateAlternateColorCodes('&', "&6&l[UltraGadgets]");
		Bukkit.getConsoleSender().sendMessage(
				prefix + " " + ChatColor.YELLOW + ChatColor.translateAlternateColorCodes('&', message.toString()));
	}

	private void start() {
		if (!Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
			log("*****************************************");
			log(" ");
			log(" ");
			log(" ");
			log("* UltraGadgets depende de ProtocolLib! *");
			log(" ");
			log(" ");
			log(" ");
			log("*****************************************");
			this.setEnabled(false);
		}
		if (SystemUtils.IS_JAVA_1_7) {
			log("Iniciando com compatibilidade para Java 7");
			javaVersion = "7";
		}else if(SystemUtils.IS_JAVA_1_6) {
			log("*****************************************");
			log(" ");
			log(" ");
			log(" ");
			log("* UltraGadgets não tem compatibilidade com Java 6! *");
			log(" ");
			log(" ");
			log(" ");
			log("*****************************************");
			this.setEnabled(false);
			return;
		}else{
			log("Iniciando com compatibilidade para Java 8");
			javaVersion = "8";
		}
		if (SystemUtils.IS_OS_WINDOWS) {
			log("Iniciando com compatibilidade para Windows.");
			system = "Windows X";
			if (SystemUtils.IS_OS_WINDOWS_7) {
				log("&aDetectado Windows 7.");
				system = "Windows 7";
			}
		} else if (SystemUtils.IS_OS_MAC) {
			log("Iniciando com compatibilidade para MAC");
			system = "IS OS MAC";
		} else if (SystemUtils.IS_OS_LINUX) {
			log("Iniciando com compatibilidade para Linux");
			system = "LINUX";
		}
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new SchedulerRunner(this), 1L, 1L);
		log("&cStartup finalizado, iniciando registro...");
	}

	private void register() {
		log("&7Registrando listeners...");
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new BombGadget(), this);
		pm.registerEvents(new MeowGadget(), this);
		pm.registerEvents(new PartyPopperGadget(), this);
		pm.registerEvents(new PaintballGunGadget(), this);
		pm.registerEvents(new ParaquedasGadget(), this);
		pm.registerEvents(new ExplosivePoopGadget(), this);
		pm.registerEvents(new TrampolimGadget(), this);
		pm.registerEvents(new IceBombGadget(), this);
		pm.registerEvents(new DjGadget(), this);
		pm.registerEvents(new VectorTNTGadget(), this);
		pm.registerEvents(new WitherShootGadget(), this);
		pm.registerEvents(new GravityGadget(), this);
		pm.registerEvents(new DiscoBallGadget(), this);
		pm.registerEvents(new WizardGadget(), this);
		pm.registerEvents(new PlayerListener(), this);
		pm.registerEvents(new MainMenu(), this);
		pm.registerEvents(new GadgetsMenu(), this);
		pm.registerEvents(new FrozenParticle(), this);
		pm.registerEvents(new HelixParticle(), this);
		pm.registerEvents(new CloudParticle(), this);
		pm.registerEvents(new TornadoParticle(), this);
		pm.registerEvents(new LillyParticle(), this);
		pm.registerEvents(new FuriousParticle(), this);
		pm.registerEvents(new RorationParticles(), this);
		pm.registerEvents(new ShieldParticle(), this);
		pm.registerEvents(new MagicParticle(), this);
		pm.registerEvents(new ParticlesMenu(), this);
		pm.registerEvents(new PlayerLocationListener(), this);
		pm.registerEvents(new Gravity(), this);
		pm.registerEvents(new ServerStep(), this);
		pm.registerEvents(new PetManager(), this);
		pm.registerEvents(new PetMenu(), this);
		pm.registerEvents(new PetCreator(), this);
		pm.registerEvents(new FumeganteGadget(), this);
		pm.registerEvents(new SmokeBombGadget(), this);
	}
	
	@Override
	public void onEnable() {
		pastebinReporter = new PastebinReporter("b7d713ceaa8ba3a93b886e6a070570f4");
		ug = this;
		start();
		log("&7Carregando configuração...");
		c = new ConfigFile(new File(getDataFolder(), "configuration.yml"));
		try {
			c.load();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		log(" ");
		log("&7Registrando eventos...");
		Glow.register();
		register();
		log(" ");
		log("&7Registrando comandos...");
		getCommand("ug").setExecutor(new UltraGadgetsCMD());
		log("&7Adicionado comando &f'&7ug&f'");
		getCommand("ultragadgets").setExecutor(new UltraGadgetsCMD());
		log("&7Adicionado comando &f'&7ultragadgets&f'");
		log(" ");
		log("&7Carregando arquivos necessarios...");
		File file = new File(getDataFolder() + File.separator + "players");
		if (!file.exists()) {
			file.mkdirs();
			log("&7Criado pasta UltraGadgets/players");
		}
		File sons = new File(getDataFolder() + File.separator + "sons");
		if(!sons.exists()) {
			sons.mkdirs();
			log("&7Criado pasta UltraGadgets/sons");
		}
		log("&7Extraíndo recursos...");
    	saveResource("sons.zip", true);
    	String input = new File(getDataFolder() + File.separator + "sons.zip").toString();
        String output = new File(getDataFolder() + File.separator + "sons").toString();
        try {
			UnZip.unzip(input, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		log(" ");
		log("&aPlugin habilitado com sucesso!");
		setupComplete = true;
	}
	
	@Override
	public void onDisable() {
		log("&7Desfazendo partículas pets e Gadgets...");
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(Particle.hasParticle(p)) {
				Particle.remove(p);
			}
			if(Pets.hasPetSpawned(p)) {
				Pets.getPetEntity(p).despawn();
				if(PetManager.petEntity.containsKey(p.getUniqueId())) {
					PetManager.petEntity.remove(p.getUniqueId());
				}
				if(PetManager.petType.containsKey(p.getUniqueId())) {
					PetManager.petType.remove(p.getUniqueId());
				}
			}
		}
		log("&7Descarregado com sucesso!");
	}
}

package br.com.floodeer.ultragadgets;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.floodeer.ultragadgets.config.ConfigFile;
import br.com.floodeer.ultragadgets.gadgets.BombGadget;
import br.com.floodeer.ultragadgets.gadgets.DjGadget;
import br.com.floodeer.ultragadgets.gadgets.GravityGadget;
import br.com.floodeer.ultragadgets.gadgets.MeowGadget;
import br.com.floodeer.ultragadgets.gadgets.PaintballGunGadget;
import br.com.floodeer.ultragadgets.gadgets.ParaquedasGadget;
import br.com.floodeer.ultragadgets.gadgets.PartyPopperGadget;
import br.com.floodeer.ultragadgets.gadgets.TrampolimGadget;
import br.com.floodeer.ultragadgets.gadgets.VectorTNTGadget;
import br.com.floodeer.ultragadgets.gadgets.WitherShootGadget;
import br.com.floodeer.ultragadgets.gadgets.WizardGadget;
import br.com.floodeer.ultragadgets.gadgets.IceBombGadget;
import br.com.floodeer.ultragadgets.listener.PlayerListener;
import br.com.floodeer.ultragadgets.listener.PlayerLocationListener;
import br.com.floodeer.ultragadgets.listener.ServerStep;
import br.com.floodeer.ultragadgets.menus.GadgetsMenu;
import br.com.floodeer.ultragadgets.menus.MainMenu;
import br.com.floodeer.ultragadgets.menus.ParticlesMenu;
import br.com.floodeer.ultragadgets.particles.CloudParticle;
import br.com.floodeer.ultragadgets.particles.FrozenParticle;
import br.com.floodeer.ultragadgets.particles.FuriousParticle;
import br.com.floodeer.ultragadgets.particles.HelixParticle;
import br.com.floodeer.ultragadgets.particles.LillyParticle;
import br.com.floodeer.ultragadgets.particles.MagicParticle;
import br.com.floodeer.ultragadgets.particles.RorationParticles;
import br.com.floodeer.ultragadgets.particles.ShieldParticle;
import br.com.floodeer.ultragadgets.particles.TornadoParticle;
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
		pm.registerEvents(new TrampolimGadget(), this);
		pm.registerEvents(new IceBombGadget(), this);
		pm.registerEvents(new DjGadget(), this);
		pm.registerEvents(new VectorTNTGadget(), this);
		pm.registerEvents(new WitherShootGadget(), this);
		pm.registerEvents(new GravityGadget(), this);
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
        String input = new File(getDataFolder(), "sons.zip").toString();
        File output = new File(getDataFolder() + File.separator + "sons");
        try {
			UnZip.unzip(input, output.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		log("&7Criando pasta sons.zip");
		try {
			
			UnZip.unzip(getDataFolder() + File.separator + "sons.zip", getDataFolder().getAbsolutePath());
			log("&7Pasta sons.zip extraída com sucesso!");
		} catch (IOException e) {
			e.printStackTrace();
			log("&cFalha ao extrair sons.zip.");
		}
		log(" ");
		log("&aPlugin habilitado com sucesso!");
		setupComplete = true;
	}
	
	@Override
	public void onDisable() {
		log("&7Descarregado com sucesso!");
	}
}

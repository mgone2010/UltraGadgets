package br.com.floodeer.ultragadgets.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;


public class ConfigFile extends SkyoConfig {

	@ConfigOptions(name = "UltraGadgets.MySQL.Habilitar")
	public boolean mysqlenabled = false;
	
	@ConfigOptions(name = "UltraGadgets.MySQL.Host")
	public String mysqlHost = "0.0.0.0";
	
	@ConfigOptions(name = "UltraGadgets.MySQL.Porta")
	public int port = 3306;
	
	@ConfigOptions(name = "UltraGadgets.MySQL.Usuario")
	public String user = "user";
	
	@ConfigOptions(name = "UltraGadgets.MySQL.Senha")
	public String password = "password";
	
	@ConfigOptions(name = "UltraGadgets.MySQL.Database")
	public String database = "database";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Titulo")
	public String title = "&c&lRecarregando!";

	@ConfigOptions(name = "UltraGadgets.Mensagens.Subtitulo")
	public String subtitle = "&eAguarde &c<cooldown>&es para usar &c<gadget>";
	
	@ConfigOptions(name = "UltraGadgets.Config.SemPermissao-Item")
	public String noPermItem = "INK_SACK";
	
	@ConfigOptions(name = "UltraGadgets.Config.SemPermissao-ItemData")
	public int noPermItemData = 8;
	
	@ConfigOptions(name = "UltraGadgets.Config.GadgetSlot")
	public int gadgetSlot = 0;
	
	@ConfigOptions(name = "UltraGadgets.Config.itemSlot")
	public int itemSlot = 8;
	
	@ConfigOptions(name = "UltraGadgets.Config.UltraGadgets-Item")
	public String ugItem = "BEACON";
	
	@ConfigOptions(name = "UltraGadgets.Config.UltraGadgets-ItemData")
	public int ugItemData = 0;
	
	@ConfigOptions(name = "UltraGadgets.Config.NerfGadgetsLag")
	public boolean nerfGadgetsLags = false;
	
	@ConfigOptions(name = "UltraGadgets.Config.Mundos-Desabilitar")
	public List<String> disableWorlds = Arrays.asList("World1", "World2", "MyWorld");
	
	@ConfigOptions(name = "UltraGadgets.Config.Gadgets-TrampolimTempo")
	public int trampolimTempo = 70;
	
	@ConfigOptions(name = "UltraGadgets.Config.Gadgets-DjTempo")
	public int djTempo = 50;
	
	@ConfigOptions(name = "UltraGadgets.Config.Gadgets-GravidadeTempo")
	public int gravityTempo = 25;
	
	@ConfigOptions(name = "UltraGadgets.Config.Gadgets-Dj.Sons1Display")
	public String djSom1 = "Popcorn";
	
	@ConfigOptions(name = "UltraGadgets.Config.Gadgets-Dj.Sons2Display")
	public String djSom2 = "Song of Storms";
	
	@ConfigOptions(name = "UltraGadgets.Config.Gadgets-Dj.Sons3Display")
	public String djSom3 = "Smells Like Teen Spirit";
	
	@ConfigOptions(name = "UltraGadgets.Config.Gadgets-Disco-BallSom")
	public String discoballSom = "Dubstep";
	
	@ConfigOptions(name = "UltraGadgets.Config.Gadgets-Dj.Display")
	public String djDisplayName = "&b&lDJ &e&l<player>";
	
	@ConfigOptions(name = "UltraGadgets.Cooldowns.BombGadget-Cooldown")
	public long bombCooldown = 16;
	
	@ConfigOptions(name = "UltraGadgets.Cooldowns.MeowGadget-Cooldown")
	public long meowCooldown = 5;
	
	@ConfigOptions(name = "UltraGadgets.Cooldowns.PartyPopper-Cooldown")
	public long partyCooldown = 42;

	@ConfigOptions(name = "UltraGadgets.Cooldowns.Paraquedas-Cooldown")
	public long paraquedas = 40;
	
	@ConfigOptions(name = "UltraGadgets.Cooldowns.Trampolim-Cooldown")
	public long trampolimCooldown = 60;
	
	@ConfigOptions(name = "UltraGadgets.Cooldowns.ExplosivePoop-Cooldown")
	public long explosiveCooldown = 45;
	
	@ConfigOptions(name = "UltraGadgets.Cooldowns.IceBomb-Cooldown")
	public long iceBombCooldown = 12;
	
	@ConfigOptions(name = "UltraGadgets.Cooldowns.VectorTNT-Cooldown")
	public long vectorTNTCooldown = 40;
	
	@ConfigOptions(name = "UltraGadgets.Cooldowns.DiscoArmor-Cooldown")
	public long discoArmorCooldown = 40;
	
	@ConfigOptions(name = "UltraGadgets.Cooldowns.DiscoBall-Cooldown")
	public long discoBallCooldown = 60;
	
	@ConfigOptions(name = "UltraGadgets.Cooldowns.Gravidade-Cooldown")
	public long gravidadeCooldown = 45;
	
	@ConfigOptions(name = "UltraGadgets.Cooldowns.WitherShoot-Cooldown")
	public long witherShootCooldown = 30;
	
	@ConfigOptions(name = "UltraGadgets.Cooldowns.Fumegante-Cooldown")
	public long fumeganteCooldown = 48;
	
	@ConfigOptions(name = "UltraGadgets.Cooldowns.Dj-Cooldown")
	public long djCooldown = 75;
	
	@ConfigOptions(name = "UltraGadgets.Cooldowns.Wizard-Cooldown")
	public long wizardCooldown = 8;
	
	@ConfigOptions(name = "UltraGadgets.Cooldowns.SmokeBomb-Cooldown")
	public long smokeBombCooldown = 38;
	
	@ConfigOptions(name = "UltraGadgets.Item-Nome")
	public String itemNome = "&e&lUltraGadgets";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Menus.Main")
	public String mainMenu = "&e&lUltraGadgets";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Menus.Particulas")
	public String particlesMenu = "&e&lParticulas";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Menus.Gadgets")
	public String gadgetsMenu = "&e&lGadgets";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.Bomba-Nome")
	public String bombaGadgetNome = "&bBomba";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.Meow-Nome")
	public String meowGadgetNome = "&bMeow";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.PartyPopper-Nome")
	public String partyPopperNome = "&bPartyPopper";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.PaintballGun-Nome")
	public String paintballGunNome = "&bPaintballGun";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.Paraquedas-Nome")
	public String paraquedasNome = "&bParaquedas";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.Trampolim-Nome")
	public String trampolimNome = "&bTrampolim";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.IceBomb-Nome")
	public String iceBombNome = "&bBomba de gelo";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.VectorTNT-Nome")
	public String vectorTNTNome = "&bVectorTNT";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.Fumegante-Nome")
	public String fumeganteNome = "&bFumegante";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.DiscoArmor-Nome")
	public String discoArmorNome = "&aD&bI&cS&dC&eO &1A&2R&3M&4O&5R";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.DiscoBall-Nome")
	public String discoBallNome = "&bDiscoBall";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.WitherShoot-Nome")
	public String witherShootNome = "&bWitherShoot";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.Wizard-Nome")
	public String wizardNome = "&bWizard";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.SkomeBomb-Nome")
	public String smokeBombNome = "&bSmokeBomb";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.Dj-Nome")
	public String djNome = "&bPlataforma Dj";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.ExplosivePoop-Nome")
	public String explosivePoopNome = "&bExplosive Poop";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.Gravidade-Nome")
	public String gravidadeNome = "&bGravidade";
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.ExplosivePoop-Lore")
	public List<String> explosivePlore = Arrays.asList("&6Uma festa de cores!", "&e&lCooldown: 45s");
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.Bomba-Lore")
	public List<String> bombaGadgetLore = Arrays.asList("&6BOOM");
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.Meow-Lore")
	public List<String> meowGadgetLore = Arrays.asList("&6Atire Enderpearls e bolas de neve!", "&e&lCooldown: 5s");
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.PartyPopper-Lore")
	public List<String> partyPopperLore = Arrays.asList("&6Uma festa de cores!", "&e&lCooldown: 42s");
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.PaintballGun-Lore")
	public List<String> paintballGunLore = Arrays.asList("&6Sinta-se livre para pintar o chão!", "&e&lCooldown: 0s");
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.Paraquedas-Lore")
	public List<String> paraquedasLore = Arrays.asList("&6Feito para cair com estilo!", "&e&lCooldown: 40s");
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.IceBomb-Lore")
	public List<String> iceBombLore = Arrays.asList("&6Faça uma chuva de gelo!", "&e&lCooldown: 12s");
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.VectorTNT-Lore")
	public List<String>  vectorTNTLore = Arrays.asList("&6Cria uma erupção de TNT", "&e&lCooldown: 40s");
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.Trampolim-Lore")
	public List<String> trampolimLore = Arrays.asList("&6Cria um trampolim!", "&e&lCooldown: 60s", "&e&lDuração: 30s");
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.DiscoArmor-Lore")
	public List<String> discoArmorLore = Arrays.asList("&eC&bO&aR&cE&dS");
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.DiscoBall-Lore")
	public List<String>  discoBallLore = Arrays.asList("&bDisco&4Ball");
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.WitherShoot-Lore")
	public List<String>  witherShootLore = Arrays.asList("&bAtire cabeças de wither!", "&e&lCooldown: 30s");
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.Wizard-Lore")
	public List<String>  wizardLore = Arrays.asList("&bPartículas mágicas!", "&e&lCooldown: 8s");

	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.Fumegante-Lore")
	public List<String>  fumeganteLore = Arrays.asList("&bPela ciência!", "&e&lCooldown: 48s");

	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.Dj-Lore")
	public List<String> djLore = Arrays.asList("&6Cria uma plataforma dj customizado!", "&e&lCooldown: 70s", "&e&lDuração: 50s");
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.Gravidade-Lore")
	public List<String> gravidadeLore = Arrays.asList("&6Yeah! Adiciona gravidade!", "&e&lCooldown: 45s", "&e&lDuração: 25s");
	
	@ConfigOptions(name = "UltraGadgets.Mensagens.Gadgets.SkomeBomb-Lore")
	public List<String>  smokebombLore = Arrays.asList("&bSUMIU!", "&e&lCooldown: 48s");
	
	public ConfigFile(File configFile) {
		super(configFile, Arrays.asList("UltraGadgets Configuração", 
		"DJGadget: Para trocar as músicas, basta mudar o nome para o nome EXATO da musica na pasta sons.",
		"Não use o Cooldown menor que a duração do Gadget, pode pausar problemas!",
		"Adicione quantas linhas quiser nos Lores dos menus!",
		"Você pode usar acentuação gráfica; Suporte UTF-8."));
		
	}
}

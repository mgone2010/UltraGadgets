package br.com.floodeer.ultragadgets;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.floodeer.ultragadgets.menus.GadgetsMenu;
import br.com.floodeer.ultragadgets.menus.MainMenu;
import br.com.floodeer.ultragadgets.util.PastebinReporter.ExpireDate;
import br.com.floodeer.ultragadgets.util.PastebinReporter.Paste;
import br.com.floodeer.ultragadgets.util.PastebinReporter.ReportFormat;

public class UltraGadgetsCMD implements CommandExecutor {
	
	private void debugg(final Player sender) {
		sender.sendMessage(ChatColor.RED + "Construindo report.... Isso pode levar um tempo!");
		new BukkitRunnable() {
			@Override
			public void run() {
				new BukkitRunnable() {
					@Override
					public void run() {
						final Paste pa = new Paste();
						pa.appendLine("------- ULTRA GADGETS -------");
						if(UltraGadgets.system == null || UltraGadgets.system.equals("X"))  {
						pa.appendLine("Sistema: Não especificado");
						}else{
							pa.appendLine("Sistema: " + UltraGadgets.system);
						}
						if(UltraGadgets.javaVersion == null) {
						pa.appendLine("Java: Não especificado");
						}else{
						pa.appendLine("Java: " + UltraGadgets.javaVersion);
						}
						pa.appendLine("Carregado: " + UltraGadgets.setupComplete);
						pa.appendLine("Versão: " + Bukkit.getBukkitVersion());
						Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
						String str1 = plugins[0].getName() + "(v " + plugins[0].getDescription().getVersion() + ")";
						for (int i = 1; i < plugins.length; i++) {
							str1 = str1 + ", " + plugins[i].getName() + "(v " + plugins[i].getDescription().getVersion() + ")";
						}
						pa.appendLine("Plugins: " + str1);
						pa.appendLine("Erros: Modo debugg desabilitado.");
						pa.appendLine("Config:");
						pa.appendLine(" ");
						pa.addFile(new File(UltraGadgets.get().getDataFolder().getAbsoluteFile(), "configuration.yml"));
						pa.appendLine(" ");
						pa.appendLine("------- ULTRA GADGETS -------");
						String str2 = null;
						try {
							str2 = UltraGadgets.getReporter().post("- Report - ", pa, ReportFormat.Java, ExpireDate.ONE_WEEK);
						} catch (IOException e) {
						   sender.sendMessage("§cOcorreu uma falha ao postar o debugg no site §epastebin.com");
						   sender.sendMessage("§4Você está com a conexao de Internet estavel?");
						   return;
						}
						sender.sendMessage("§aSucesso ao construir debugg: §e" + str2);
						sender.sendMessage("§cAtenção: O pastebin será deletado em 7 dias.");
					}
				}.runTaskAsynchronously(UltraGadgets.get());

			}
		}.runTaskLater(UltraGadgets.get(), 8*20);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		if (cmd.getName().equalsIgnoreCase("ultragadgets") || cmd.getName().equalsIgnoreCase("ug")) {
			if (args.length == 0) {
				sender.sendMessage("§cArgumentos insuficientes. Dica: Pressione tab para auto-completar.");
				return true;
			}
			if (args[0].equalsIgnoreCase("gadgets")) {
				GadgetsMenu.show(((Player)sender));
			}
			else if (args[0].equalsIgnoreCase("menu")) {
				MainMenu.show(((Player)sender));
			}else if(args[0].equalsIgnoreCase("reportar")) {
				debugg((Player) sender);
			}
		}
		return false;
	}
}

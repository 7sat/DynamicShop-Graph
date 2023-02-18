package xyz.jpenilla.dsgraph.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import xyz.jpenilla.dsgraph.DSGraph;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import xyz.jpenilla.dsgraph.config.StockConfig;

@CommandAlias("dsgraph|dsg")
public class CommandDSGraph extends BaseCommand {
    private final DSGraph plugin;

    public CommandDSGraph(DSGraph p) {
        plugin = p;
    }

    @Default
    @HelpCommand
    public void onHelp(CommandSender sender, CommandHelp help) {
        String m = ChatColor.AQUA + plugin.getName() + ChatColor.DARK_AQUA + " Help";
        sender.sendMessage(m);
        help.showHelp();
    }

    @Subcommand("reload")
    @CommandPermission("dsgraph.reload")
    public void onReload(CommandSender sender) {
        plugin.getCfg().load();
        plugin.getTaskManager().restart();
        sender.sendMessage("Done reloading plugin");
    }

    @Subcommand("compress")
    @CommandPermission("dsgraph.compress")
    public void onCompress(CommandSender sender) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () ->{
            sender.sendMessage("compress start");

            DSGraph.getInstance().getTaskManager().stopRecordDataTask();
            DSGraph.getInstance().getCfg().getFiles().forEach(StockConfig::compress);
            DSGraph.getInstance().getTaskManager().startRecordDataTask();

            sender.sendMessage("compress end");
                });
    }
}

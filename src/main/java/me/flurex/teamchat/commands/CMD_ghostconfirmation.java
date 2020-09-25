package me.flurex.teamchat.commands;

import me.flurex.teamchat.TeamChat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_ghostconfirmation extends Command {

    private TeamChat plugin;

    public CMD_ghostconfirmation(TeamChat plugin) {
        super("wnvahgwsgn");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer)sender;
            if(p.hasPermission(plugin.getConfigManager().getConfig().getString("permission_ghostmode"))) {
                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("yes")) {
                        for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                            if(all.hasPermission(plugin.getConfigManager().getConfig().getString("permission_use_teamchat"))) {
                                if(plugin.getLoggedin().contains(all) || plugin.getGhost().contains(all)) {
                                    plugin.sendMessage(all, plugin.getWaitingConfirmation().get(p));
                                    plugin.getWaitingConfirmation().remove(p);
                                }
                            }
                        }
                    } else {
                        plugin.getWaitingConfirmation().remove(p);
                        plugin.sendMessage(p, plugin.getConfigManager().getMessage("ghost_chat_confirm_no"));
                    }
                }
            }
        }
    }

}

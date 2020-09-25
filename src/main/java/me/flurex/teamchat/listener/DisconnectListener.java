package me.flurex.teamchat.listener;

import me.flurex.teamchat.TeamChat;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class DisconnectListener implements Listener {

    private TeamChat plugin;

    public DisconnectListener(TeamChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent e) {
        ProxiedPlayer p = e.getPlayer();
        if(plugin.getLoggedin().contains(p)) {
            plugin.getLoggedin().remove(p);
            if(p.hasPermission(plugin.getConfigManager().getConfig().getString("permission_autologin"))) {
                for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                    if(all.hasPermission(plugin.getConfigManager().getConfig().getString("permission_use_teamchat"))) {
                        if(plugin.getLoggedin().contains(all)) {
                            plugin.sendMessage(all, plugin.getConfigManager().getMessage("logout_broadcast").replace("%player%", p.getName()));
                        }
                    }
                }
            }

        }
        if(plugin.getGhost().contains(p)) {
            plugin.getGhost().remove(p);
        }
    }

}

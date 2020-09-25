package me.flurex.teamchat.listener;

import me.flurex.teamchat.TeamChat;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginListener implements Listener {

    private TeamChat plugin;

    public LoginListener(TeamChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(PostLoginEvent e) {
        ProxiedPlayer p = e.getPlayer();
        if(p.hasPermission(plugin.getConfigManager().getConfig().getString("permission_autologin"))) {
            if(p.hasPermission(plugin.getConfigManager().getConfig().getString("permission_use_teamchat"))) {
                for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                    if(all.hasPermission(plugin.getConfigManager().getConfig().getString("permission_use_teamchat"))) {
                        if(plugin.getLoggedin().contains(all)) {
                            plugin.sendMessage(all, plugin.getConfigManager().getMessage("login_broadcast").replace("%player%", p.getName()));
                        }
                    }
                }
                plugin.getLoggedin().add(p);
                plugin.sendMessage(p, plugin.getConfigManager().getMessage("login_system"));
            }
        }
        if(plugin.getConfigManager().getConfig().getBoolean("check_for_updates")) {
            if(p.hasPermission(plugin.getConfigManager().getConfig().getString("permission_update"))) {
                if(plugin.getUpdateChecker().isUpdate()) {
                    if(plugin.getConfigManager().getConfig().getString("language").equalsIgnoreCase("en")) {
                        plugin.sendMessage(p, plugin.getConfigManager().getMessage("prefix") + "There is a new update available for TeamChat!");
                        plugin.sendMessage(p, plugin.getConfigManager().getMessage("prefix") + "Installed Version: " + plugin.getDescription().getVersion());
                        plugin.sendMessage(p, plugin.getConfigManager().getMessage("prefix") + "Newest Version: " + plugin.getUpdateChecker().getNewVersion());
                    } else if(plugin.getConfigManager().getConfig().getString("language").equalsIgnoreCase("de")) {
                        plugin.sendMessage(p, plugin.getConfigManager().getMessage("prefix") + "Es ist ein neues Update für TeamChat verfügbar!");
                        plugin.sendMessage(p, plugin.getConfigManager().getMessage("prefix") + "Installierte Version: " + plugin.getDescription().getVersion());
                        plugin.sendMessage(p, plugin.getConfigManager().getMessage("prefix") + "Neueste Version: " + plugin.getUpdateChecker().getNewVersion());
                    } else {
                        ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8[§eTeamChat§8] &4ERROR: &cCould not load config file!"));
                    }
                }
            }
        }
        if(p.hasPermission(plugin.getConfigManager().getConfig().getString("permission_autoghostmode"))) {
            if(p.hasPermission(plugin.getConfigManager().getConfig().getString("permission_ghostmode"))) {
                plugin.getGhost().add(p);
                if(plugin.getLoggedin().contains(p)) {
                    plugin.getLoggedin().remove(p);
                }
                plugin.sendMessage(p, plugin.getConfigManager().getMessage("ghost_enter_system_1"));
                plugin.sendMessage(p, plugin.getConfigManager().getMessage("ghost_enter_system_2"));
            }
        }
    }

}

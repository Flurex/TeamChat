package me.flurex.teamchat.utils;

import me.flurex.teamchat.TeamChat;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.concurrent.TimeUnit;

public class Methods {

    private TeamChat plugin;

    public Methods(TeamChat plugin) {
        this.plugin = plugin;
    }

    public void startTimeoutCounter(ProxiedPlayer p) {
        ProxyServer.getInstance().getScheduler().schedule(plugin, () -> {
            if(plugin.getWaitingConfirmation().containsKey(p)) {
                plugin.getWaitingConfirmation().remove(p);
                plugin.sendMessage(p, plugin.getConfigManager().getMessage("ghost_chat_confirm_timeout"));
            }
        }, 15, TimeUnit.SECONDS);
    }

}

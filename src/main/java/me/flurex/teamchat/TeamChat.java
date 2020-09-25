package me.flurex.teamchat;

import me.flurex.teamchat.commands.CMD_ghostconfirmation;
import me.flurex.teamchat.commands.CMD_teamchat;
import me.flurex.teamchat.listener.ChatListener;
import me.flurex.teamchat.listener.DisconnectListener;
import me.flurex.teamchat.listener.LoginListener;
import me.flurex.teamchat.utils.ConfigManager;
import me.flurex.teamchat.utils.Methods;
import me.flurex.teamchat.utils.MetricsLite;
import me.flurex.teamchat.utils.UpdateChecker;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashMap;

public class TeamChat extends Plugin {

    private ConfigManager configManager;
    private UpdateChecker updateChecker;
    private Methods methods;
    public ArrayList<ProxiedPlayer> toggled;
    public ArrayList<ProxiedPlayer> loggedin;
    public ArrayList<ProxiedPlayer> ghost;
    public HashMap<ProxiedPlayer, String> waitingConfirmation;

    @Override
    public void onEnable() {
        init();
        if(configManager.getConfig().getString("language").equalsIgnoreCase("de")) {
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8##################################################"));
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §aPlugin erfolgreich aktiviert"));
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §aVersion: 2.0"));
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §aAutor: Flurex"));
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8##################################################"));
        } else if(configManager.getConfig().getString("language").equalsIgnoreCase("en")) {
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8##################################################"));
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §aPlugin successfully activated"));
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §aVersion: 2.0"));
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §aAuthor: Flurex"));
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8##################################################"));
        } else {
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §4ERROR: §cCould not load config file!"));
        }
    }

    @Override
    public void onDisable() {
        if(configManager.getConfig().getString("language").equalsIgnoreCase("de")) {
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8##################################################"));
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §cPlugin erfolgreich deaktiviert"));
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §cVersion: 2.0"));
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §cAutor: Flurex"));
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8##################################################"));
        } else if(configManager.getConfig().getString("language").equalsIgnoreCase("en")) {
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8##################################################"));
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §cPlugin successfully deactivated"));
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §cVersion: 2.0"));
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §cAuthor: Flurex"));
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8##################################################"));
        } else {
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §4ERROR: §cCould not load config file!"));
        }
    }

    public void init() {
        PluginManager pm = ProxyServer.getInstance().getPluginManager();
        // Other
        this.toggled = new ArrayList<>();
        this.loggedin = new ArrayList<>();
        this.ghost = new ArrayList<>();
        this.waitingConfirmation = new HashMap<>();
        // Classes
        this.configManager = new ConfigManager(this);
        if(this.configManager.getConfig().getBoolean("check_for_updates")) {
            this.updateChecker = new UpdateChecker(this);
        }
        this.methods = new Methods(this);
        new MetricsLite(this, 7521);
        // Commands
        pm.registerCommand(this, new CMD_teamchat(this));
        pm.registerCommand(this, new CMD_ghostconfirmation(this));
        // Listener
        pm.registerListener(this, new ChatListener(this));
        pm.registerListener(this, new DisconnectListener(this));
        pm.registerListener(this, new LoginListener(this));
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

    public Methods getMethods() {
        return methods;
    }

    public void sendMessage(ProxiedPlayer p, String msg) {
        p.sendMessage(TextComponent.fromLegacyText(msg));
    }

    public void sendSenderMessage(CommandSender sender, String msg) {
        sender.sendMessage(TextComponent.fromLegacyText(msg));
    }

    public ArrayList<ProxiedPlayer> getToggled() {
        return toggled;
    }

    public ArrayList<ProxiedPlayer> getLoggedin() {
        return loggedin;
    }

    public ArrayList<ProxiedPlayer> getGhost() {
        return ghost;
    }

    public HashMap<ProxiedPlayer, String> getWaitingConfirmation() {
        return waitingConfirmation;
    }

}

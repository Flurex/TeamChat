package me.flurex.teamchat.utils;

import me.flurex.teamchat.TeamChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigManager {

    private TeamChat plugin;
    private Configuration config;
    private Configuration messages_de;
    private Configuration messages_en;

    public ConfigManager(TeamChat plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        File config_file = new File(plugin.getDataFolder(), "config.yml");
        File messages_de_file = new File(plugin.getDataFolder(), "messages_de.yml");
        File messages_en_file = new File(plugin.getDataFolder(), "messages_en.yml");
        if(!config_file.exists()) {
            try(InputStream in = plugin.getResourceAsStream("config.yml")) {
                Files.copy(in, config_file.toPath());
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
        if(!messages_de_file.exists()) {
            try(InputStream in = plugin.getResourceAsStream("messages_de.yml")) {
                Files.copy(in, messages_de_file.toPath());
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
        if(!messages_en_file.exists()) {
            try(InputStream in = plugin.getResourceAsStream("messages_en.yml")) {
                Files.copy(in, messages_en_file.toPath());
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(config_file);
            if(getConfig().getString("language").equalsIgnoreCase("en")) {
                messages_en = ConfigurationProvider.getProvider(YamlConfiguration.class).load(messages_en_file);
            } else if(getConfig().getString("language").equalsIgnoreCase("de")) {
                messages_de = ConfigurationProvider.getProvider(YamlConfiguration.class).load(messages_de_file);
            } else {
                ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §4ERROR: §cCould not load language file!"));
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public Configuration getConfig() {
        return config;
    }

    public Configuration getMessages() {
        if(getConfig().getString("language").equalsIgnoreCase("en")) {
            return messages_en;
        } else if(getConfig().getString("language").equalsIgnoreCase("de")) {
            return messages_de;
        } else {
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §4ERROR: §cCould not load language file!"));
        }
        return null;
    }

    public String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', getMessages().getString(path).replace("%prefix%", ChatColor.translateAlternateColorCodes('&', getMessages().getString("prefix"))));
    }

}

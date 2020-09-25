package me.flurex.teamchat.utils;

import me.flurex.teamchat.TeamChat;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.Buffer;

public class UpdateChecker {

    private TeamChat plugin;
    private boolean update = false;
    private String newVersion;

    public UpdateChecker(TeamChat plugin) {
        this.plugin = plugin;
        ProxyServer.getInstance().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://api.spigotmc.org/legacy/update.php?resource=74921/");
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                    if(con != null) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String input;
                        while((input = br.readLine()) != null) {
                            if(!input.trim().equalsIgnoreCase(plugin.getDescription().getVersion().trim())) {
                                newVersion = input;
                                if(plugin.getConfigManager().getConfig().getString("language").equalsIgnoreCase("de")) {
                                    ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("==========[ UPDATE ]=========="));
                                    ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("Es ist ein neues Update für TeamChat verfügbar!"));
                                    ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("Download Link: https://www.spigotmc.org/resources/teamchat-100-customizeable-login-logout-ghostmode.74921/"));
                                    ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("==========[ UPDATE ]=========="));
                                } else if(plugin.getConfigManager().getConfig().getString("language").equalsIgnoreCase("en")) {
                                    ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("==========[ UPDATE ]=========="));
                                    ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("There is a new update available for TeamChat!"));
                                    ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("Download Link: https://www.spigotmc.org/resources/teamchat-100-customizeable-login-logout-ghostmode.74921/"));
                                    ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("==========[ UPDATE ]=========="));
                                } else {
                                    ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §4ERROR: §cCould not load config file!"));
                                }
                                update = true;
                            }
                        }
                        br.close();
                    }
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public boolean isUpdate() {
        return update;
    }

    public String getNewVersion() {
        return newVersion;
    }

}

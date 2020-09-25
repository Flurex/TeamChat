package me.flurex.teamchat.listener;

import me.flurex.teamchat.TeamChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {

    private TeamChat plugin;

    public ChatListener(TeamChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(ChatEvent e) {
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        String message = e.getMessage();
        if(message.startsWith(plugin.getConfigManager().getConfig().getString("chat_symbol"))) {
            if(plugin.getLoggedin().contains(p) || plugin.getGhost().contains(p)) {
                if(p.hasPermission(plugin.getConfigManager().getConfig().getString("permission_use_teamchat"))) {
                    String msg = message.replace(plugin.getConfigManager().getConfig().getString("chat_symbol"), "");
                    for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                        if(all.hasPermission(plugin.getConfigManager().getConfig().getString("permission_use_teamchat"))) {
                            if(plugin.getLoggedin().contains(all) || plugin.getGhost().contains(all)) {
                                if(plugin.getGhost().contains(p)) {
                                    if(plugin.getWaitingConfirmation().containsKey(p)) {
                                        plugin.sendMessage(p, plugin.getConfigManager().getMessage("ghost_chat_confirm_alredy_exists_1"));
                                        plugin.sendMessage(p, plugin.getConfigManager().getMessage("ghost_chat_confirm_alredy_exists_2"));
                                        e.setCancelled(true);
                                    } else {
                                        String tcmessage = plugin.getConfigManager().getMessage("format").replace("%player%", p.getName()).replace("%message%", ChatColor.translateAlternateColorCodes('&', msg));
                                        plugin.getWaitingConfirmation().put(p, tcmessage);
                                        plugin.getMethods().startTimeoutCounter(p);
                                        plugin.sendMessage(p, plugin.getConfigManager().getMessage("ghost_chat_confirm_1"));
                                        plugin.sendMessage(p, plugin.getConfigManager().getMessage("ghost_chat_confirm_2"));
                                        String[] config_confirm_3 = plugin.getConfigManager().getMessage("ghost_chat_confirm_3").split("%c%");
                                        TextComponent splitted_0_component = new TextComponent(config_confirm_3[0]);
                                        TextComponent splitted_2_component = new TextComponent(config_confirm_3[2]);
                                        TextComponent msg_yes_component = new TextComponent(config_confirm_3[1]);
                                        TextComponent msg_no_component = new TextComponent(config_confirm_3[3]);
                                        if(plugin.getConfigManager().getConfig().getString("language").equalsIgnoreCase("en")) {
                                            msg_yes_component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §cLeft-Click").create()));
                                            msg_no_component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §cLeft-Click").create()));
                                        } else if(plugin.getConfigManager().getConfig().getString("language").equalsIgnoreCase("de")) {
                                            msg_yes_component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §cLinksklick").create()));
                                            msg_no_component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §cLinksklick").create()));
                                        } else {
                                            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §4ERROR: §cThere was an Error with the config.yml!"));
                                        }
                                        msg_yes_component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wnvahgwsgn yes"));
                                        msg_no_component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wnvahgwsgn no"));
                                        splitted_0_component.addExtra(msg_yes_component);
                                        splitted_0_component.addExtra(splitted_2_component);
                                        splitted_0_component.addExtra(msg_no_component);
                                        p.sendMessage(splitted_0_component);
                                        e.setCancelled(true);
                                    }
                                } else {
                                    String tcmessage = plugin.getConfigManager().getMessage("format").replace("%player%", p.getName()).replace("%message%", ChatColor.translateAlternateColorCodes('&', msg));
                                    plugin.sendMessage(all, tcmessage);
                                    e.setCancelled(true);
                                }
                            }
                        }
                    }
                }
            }
        }
        if(plugin.getToggled().contains(p)) {
            if(!message.startsWith("/")) {
                for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                    if(all.hasPermission(plugin.getConfigManager().getConfig().getString("permission_use_teamchat"))) {
                        if(plugin.getLoggedin().contains(all) || plugin.getGhost().contains(all)) {
                            if(plugin.getGhost().contains(p)) {
                                if(plugin.getWaitingConfirmation().containsKey(p)) {
                                    plugin.sendMessage(p, plugin.getConfigManager().getMessage("ghost_chat_confirm_alredy_exists_1"));
                                    plugin.sendMessage(p, plugin.getConfigManager().getMessage("ghost_chat_confirm_alredy_exists_2"));
                                    e.setCancelled(true);
                                } else {
                                    String tcmessage = plugin.getConfigManager().getMessage("format").replace("%player%", p.getName()).replace("%message%", ChatColor.translateAlternateColorCodes('&', message));
                                    plugin.getWaitingConfirmation().put(p, tcmessage);
                                    plugin.getMethods().startTimeoutCounter(p);
                                    plugin.sendMessage(p, plugin.getConfigManager().getMessage("ghost_chat_confirm_1"));
                                    plugin.sendMessage(p, plugin.getConfigManager().getMessage("ghost_chat_confirm_2"));
                                    String[] config_confirm_3 = plugin.getConfigManager().getMessage("ghost_chat_confirm_3").split("%c%");
                                    TextComponent splitted_0_component = new TextComponent(config_confirm_3[0]);
                                    TextComponent splitted_2_component = new TextComponent(config_confirm_3[2]);
                                    TextComponent msg_yes_component = new TextComponent(config_confirm_3[1]);
                                    TextComponent msg_no_component = new TextComponent(config_confirm_3[3]);
                                    if(plugin.getConfigManager().getConfig().getString("language").equalsIgnoreCase("en")) {
                                        msg_yes_component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §cLeft-Click").create()));
                                        msg_no_component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §cLeft-Click").create()));
                                    } else if(plugin.getConfigManager().getConfig().getString("language").equalsIgnoreCase("de")) {
                                        msg_yes_component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §cLinksklick").create()));
                                        msg_no_component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §cLinksklick").create()));
                                    } else {
                                        ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§8 » §e§lTeamChat §8× §4ERROR: §cThere was an Error with the config.yml!"));
                                    }
                                    msg_yes_component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wnvahgwsgn yes"));
                                    msg_no_component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wnvahgwsgn no"));
                                    splitted_0_component.addExtra(msg_yes_component);
                                    splitted_0_component.addExtra(splitted_2_component);
                                    splitted_0_component.addExtra(msg_no_component);
                                    p.sendMessage(splitted_0_component);
                                    e.setCancelled(true);
                                }
                            } else {
                                String tcmessage = plugin.getConfigManager().getMessage("format").replace("%player%", p.getName()).replace("%message%", ChatColor.translateAlternateColorCodes('&', message));
                                plugin.sendMessage(all, tcmessage);
                                e.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }

}

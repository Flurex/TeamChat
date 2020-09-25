package me.flurex.teamchat.commands;

import me.flurex.teamchat.TeamChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_teamchat extends Command {

    private TeamChat plugin;

    public CMD_teamchat(TeamChat plugin) {
        super("teamchat", null, new String[] {"tc"});
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer)sender;
            if(p.hasPermission(plugin.getConfigManager().getConfig().getString("permission_use_teamchat"))) {
                if(args.length > 0) {
                    if(args[0].equalsIgnoreCase("toggle")) {
                        if(p.hasPermission(plugin.getConfigManager().getConfig().getString("permission_toggle"))) {
                            if(plugin.getToggled().contains(p)) {
                                plugin.getToggled().remove(p);
                                plugin.sendMessage(p, plugin.getConfigManager().getMessage("toggle_deactivated"));
                            } else {
                                plugin.getToggled().add(p);
                                plugin.sendMessage(p, plugin.getConfigManager().getMessage("toggle_activated"));
                            }
                        } else {
                            plugin.sendMessage(p, plugin.getConfigManager().getMessage("noperms"));
                        }
                    } else if(args[0].equalsIgnoreCase("login")) {
                        if(!plugin.getGhost().contains(p)) {
                            if(!plugin.getLoggedin().contains(p)) {
                                for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                                    if(all.hasPermission(plugin.getConfigManager().getConfig().getString("permission_use_teamchat"))) {
                                        if(plugin.getLoggedin().contains(all) || plugin.getGhost().contains(all)) {
                                            plugin.sendMessage(all, plugin.getConfigManager().getMessage("login_broadcast").replace("%player%", p.getName()));
                                        }
                                    }
                                }
                                plugin.getLoggedin().add(p);
                                plugin.sendMessage(p, plugin.getConfigManager().getMessage("login"));
                            } else {
                                plugin.sendMessage(p, plugin.getConfigManager().getMessage("alredy_logged_in"));
                            }
                        } else {
                            plugin.sendMessage(p, plugin.getConfigManager().getMessage("command_deactivated_ghostmode"));
                        }
                    } else if(args[0].equalsIgnoreCase("logout")) {
                        if(!plugin.getGhost().contains(p)) {
                            if(plugin.getLoggedin().contains(p)) {
                                plugin.getLoggedin().remove(p);
                                plugin.sendMessage(p, plugin.getConfigManager().getMessage("logout"));
                                for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                                    if(all.hasPermission(plugin.getConfigManager().getConfig().getString("permission_use_teamchat"))) {
                                        if(plugin.getLoggedin().contains(all) || plugin.getGhost().contains(all)) {
                                            plugin.sendMessage(all, plugin.getConfigManager().getMessage("logout_broadcast").replace("%player%", p.getName()));
                                        }
                                    }
                                }
                            } else {
                                plugin.sendMessage(p, plugin.getConfigManager().getMessage("alredy_logged_out"));
                            }
                        } else {
                            plugin.sendMessage(p, plugin.getConfigManager().getMessage("command_deactivated_ghostmode"));
                        }
                    } else if(args[0].equalsIgnoreCase("list")) {
                        if(p.hasPermission(plugin.getConfigManager().getConfig().getString("permission_list"))) {
                            plugin.sendMessage(p, plugin.getConfigManager().getMessage("list_header").replace("%count%", Integer.toString(plugin.getLoggedin().size())));
                            for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                                if(plugin.getLoggedin().contains(all)) {
                                    if(!plugin.getGhost().contains(all)) {
                                        if(all.hasPermission(plugin.getConfigManager().getConfig().getString("permission_use_teamchat"))) {
                                            plugin.sendMessage(p, plugin.getConfigManager().getMessage("list_player").replace("%player%", all.getName()).replace("%server%", all.getServer().getInfo().getName()));
                                        }
                                    }
                                }
                            }
                        } else {
                            plugin.sendMessage(p, plugin.getConfigManager().getMessage("noperms"));
                        }
                    } else if(args[0].equalsIgnoreCase("ghost")) {
                        if(p.hasPermission(plugin.getConfigManager().getConfig().getString("permission_ghostmode"))) {
                            if(plugin.getGhost().contains(p)) {
                                plugin.getGhost().remove(p);
                                plugin.getLoggedin().add(p);
                                plugin.sendMessage(p, plugin.getConfigManager().getMessage("ghost_leave"));
                                for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                                    if(all.hasPermission(plugin.getConfigManager().getConfig().getString("permission_use_teamchat"))) {
                                        if(plugin.getLoggedin().contains(all)) {
                                            if(!all.getName().equalsIgnoreCase(p.getName())) {
                                                plugin.sendMessage(all, plugin.getConfigManager().getMessage("login_broadcast").replace("%player%", p.getName()));
                                            }
                                        }
                                    }
                                }
                            } else {
                                plugin.getGhost().add(p);
                                plugin.getLoggedin().remove(p);
                                plugin.sendMessage(p, plugin.getConfigManager().getMessage("ghost_enter"));
                                for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                                    if(all.hasPermission(plugin.getConfigManager().getConfig().getString("permission_use_teamchat"))) {
                                        if(plugin.getLoggedin().contains(all)) {
                                            plugin.sendMessage(all, plugin.getConfigManager().getMessage("logout_broadcast").replace("%player%", p.getName()));
                                        }
                                    }
                                }
                            }
                        } else {
                            plugin.sendMessage(p, plugin.getConfigManager().getMessage("noperms"));
                        }
                    } else if(args[0].equalsIgnoreCase("reload")) {
                        if(p.hasPermission(plugin.getConfigManager().getConfig().getString("permission_reload"))) {
                            plugin.getConfigManager().loadConfig();
                            plugin.sendMessage(p, plugin.getConfigManager().getMessage("reload_successfull"));
                        }
                    } else if(this.plugin.getLoggedin().contains(p) || plugin.getGhost().contains(p)) {
                        String msg = args[0];
                        for(int i = 0; i < args.length; i++) {
                            if(i != 0) {
                                msg += " " + args[i];
                            }
                        }
                        for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                            if(all.hasPermission(plugin.getConfigManager().getConfig().getString("permission_use_teamchat"))) {
                                if(plugin.getLoggedin().contains(all) || plugin.getGhost().contains(all)) {
                                    if(plugin.getGhost().contains(p)) {
                                        if(plugin.getWaitingConfirmation().containsKey(p)) {
                                            plugin.sendMessage(p, plugin.getConfigManager().getMessage("ghost_chat_confirm_alredy_exists_1"));
                                            plugin.sendMessage(p, plugin.getConfigManager().getMessage("ghost_chat_confirm_alredy_exists_2"));
                                        } else {
                                            String message = plugin.getConfigManager().getMessage("format").replace("%player%", p.getName()).replace("%message%", ChatColor.translateAlternateColorCodes('&', msg));
                                            plugin.getWaitingConfirmation().put(p, message);
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
                                        }
                                    } else {
                                        String message = plugin.getConfigManager().getMessage("format").replace("%player%", p.getName()).replace("%message%", ChatColor.translateAlternateColorCodes('&', msg));
                                        plugin.sendMessage(all, message);
                                    }
                                }
                            }
                        }
                    } else {
                        plugin.sendMessage(p, plugin.getConfigManager().getMessage("not_logged_in"));
                    }
                } else {
                    plugin.sendMessage(p, plugin.getConfigManager().getMessage("syntax_line_1"));
                    plugin.sendMessage(p, plugin.getConfigManager().getMessage("syntax_line_2"));
                    plugin.sendMessage(p, plugin.getConfigManager().getMessage("syntax_line_3"));
                    plugin.sendMessage(p, plugin.getConfigManager().getMessage("syntax_line_4"));
                    plugin.sendMessage(p, plugin.getConfigManager().getMessage("syntax_line_5"));
                    plugin.sendMessage(p, plugin.getConfigManager().getMessage("syntax_line_6"));
                    if(p.hasPermission(plugin.getConfigManager().getConfig().getString("permission_ghostmode"))) {
                        plugin.sendMessage(p, plugin.getConfigManager().getMessage("syntax_line_7"));
                    }
                    plugin.sendMessage(p, plugin.getConfigManager().getMessage("syntax_line_8"));
                }
            } else {
                plugin.sendMessage(p, plugin.getConfigManager().getMessage("noperms"));
            }
        } else {
            plugin.sendSenderMessage(sender, plugin.getConfigManager().getMessage("executed_by_console"));
        }
    }

}

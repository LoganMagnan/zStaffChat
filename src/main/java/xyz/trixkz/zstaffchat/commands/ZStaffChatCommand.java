package xyz.trixkz.zstaffchat.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import xyz.trixkz.zstaffchat.Main;
import xyz.trixkz.zstaffchat.utils.CC;

import java.io.IOException;

public class ZStaffChatCommand extends Command {

    private Main main;

    public ZStaffChatCommand(Main main) {
        super("zsc");

        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            if (main.getAPI().getAdminConfig().getBoolean(player.getName())) {
                if (args.length == 0) {
                    player.sendMessage(new TextComponent(CC.translate("&cUsage: /zsc reload")));
                } else {
                    if (args[0].equalsIgnoreCase("reload")) {
                        try {
                            ConfigurationProvider.getProvider(YamlConfiguration.class).save(main.getSettingsConfig(), main.getSettingsFile());
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }

                        player.sendMessage(new TextComponent(CC.translate("&aReload successful")));
                    }
                }
            }
        } else {
            if (args.length == 0) {
                sender.sendMessage(new TextComponent(ChatColor.stripColor("&cUsage: /zsc reload")));
            } else {
                if (args[0].equalsIgnoreCase("reload")) {
                    try {
                        ConfigurationProvider.getProvider(YamlConfiguration.class).save(main.getSettingsConfig(), main.getSettingsFile());
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }

                    sender.sendMessage(new TextComponent(ChatColor.stripColor("&aReload successful")));
                }
            }
        }
    }
}

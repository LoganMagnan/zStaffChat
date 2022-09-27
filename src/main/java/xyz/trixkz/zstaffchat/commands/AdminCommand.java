package xyz.trixkz.zstaffchat.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import xyz.trixkz.zstaffchat.Main;
import xyz.trixkz.zstaffchat.utils.CC;

import java.io.IOException;

public class AdminCommand extends Command {

    private Main main;

    public AdminCommand(Main main) {
        super("admin", "zstaffchat.admin");

        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent(main.getSettingsConfig().getString("admin-command.no-console")));

            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length == 0) {
            for (String string : main.getSettingsConfig().getStringList("admin-command.help")) {
                player.sendMessage(new TextComponent(CC.translate(string)));
            }

            return;
        } else {
            switch (args[0]) {
                case "add":
                    ProxiedPlayer addPlayer = main.getProxy().getPlayer(args[1]);

                    if (!main.getAdminConfig().getBoolean(addPlayer.getName())) {
                        if (addPlayer.isConnected()) {
                            main.getAdminConfig().set(addPlayer.getName(), true);

                            try {
                                ConfigurationProvider.getProvider(YamlConfiguration.class).save(main.getAdminConfig(), main.getAdminFile());
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }

                            player.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("admin-command.added")
                                    .replace("(target)", addPlayer.getName()))));
                        } else {
                            player.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("admin-command.not-online"))));
                        }
                    } else {
                        player.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("admin-command.already-an-admin"))));
                    }

                    break;
                case "remove":
                    ProxiedPlayer removePlayer = main.getProxy().getPlayer(args[1]);

                    if (main.getAdminConfig().getBoolean(removePlayer.getName())) {
                        if (removePlayer.isConnected()) {
                            main.getStaffConfig().set(removePlayer.getName(), false);

                            try {
                                ConfigurationProvider.getProvider(YamlConfiguration.class).save(main.getAdminConfig(), main.getAdminFile());
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }

                            player.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("admin-command.removed")
                                    .replace("(target)", removePlayer.getName()))));
                        } else {
                            player.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("admin-command.not-online"))));
                        }
                    } else {
                        player.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("admin-command.not-an-admin"))));
                    }

                    break;
                case "list":
                    break;
            }
        }
    }
}

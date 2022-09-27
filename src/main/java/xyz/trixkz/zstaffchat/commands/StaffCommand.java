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

public class StaffCommand extends Command {

    private Main main;

    public StaffCommand(Main main) {
        super("staff", "zstaffchat.staff");

        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent(main.getSettingsConfig().getString("staff-command.no-console")));

            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length == 0) {
            for (String string : main.getSettingsConfig().getStringList("staff-command.help")) {
                player.sendMessage(new TextComponent(CC.translate(string)));
            }

            return;
        } else {
            switch (args[0]) {
                case "add":
                    ProxiedPlayer addPlayer = main.getProxy().getPlayer(args[1]);

                    if (!main.getStaffConfig().getBoolean(addPlayer.getName())) {
                        if (addPlayer.isConnected()) {
                            main.getStaffConfig().set(addPlayer.getName(), true);

                            try {
                                ConfigurationProvider.getProvider(YamlConfiguration.class).save(main.getStaffConfig(), main.getStaffFile());
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }

                            player.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("staff-command.added")
                                    .replace("(target)", addPlayer.getName()))));
                        } else {
                            player.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("staff-command.not-online"))));
                        }
                    } else {
                        player.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("staff-command.already-a-staff-member"))));
                    }

                    break;
                case "remove":
                    ProxiedPlayer removePlayer = main.getProxy().getPlayer(args[1]);

                    if (main.getStaffConfig().getBoolean(removePlayer.getName())) {
                        if (removePlayer.isConnected()) {
                            main.getStaffConfig().set(removePlayer.getName(), false);

                            try {
                                ConfigurationProvider.getProvider(YamlConfiguration.class).save(main.getStaffConfig(), main.getStaffFile());
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }

                            player.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("staff-command.removed")
                                    .replace("(target)", removePlayer.getName()))));
                        } else {
                            player.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("staff-command.not-online"))));
                        }
                    } else {
                        player.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("staff-command.not-a-staff-member"))));
                    }

                    break;
                case "list":
                    break;
            }
        }
    }
}

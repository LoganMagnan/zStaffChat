package xyz.trixkz.zstaffchat.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import xyz.trixkz.zstaffchat.Main;
import xyz.trixkz.zstaffchat.utils.CC;

public class AnnounceCommand extends Command {

    private Main main;

    public AnnounceCommand(Main main) {
        super("announce");

        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            if (main.getStaffConfig().getBoolean(player.getName())) {
                if (args.length == 0) {
                    player.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("announce-command.usage"))));
                } else {
                    String message = getMessage(args, 0);

                    main.getProxy().broadcast(new TextComponent(CC.translate(main.getSettingsConfig().getString("announce-command.message")
                            .replace("(message)", message))));
                }
            }
        } else {
            if (args.length == 0) {
                sender.sendMessage(new TextComponent(ChatColor.stripColor(main.getSettingsConfig().getString("announce-command.usage"))));
            } else {
                String message = getMessage(args, 0);

                main.getProxy().broadcast(new TextComponent(CC.translate(main.getSettingsConfig().getString("announce-command.message")
                        .replace("(message)", message))));
            }
        }
    }

    private String getMessage(String[] args, int number) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = number; i < args.length; i++) {
            stringBuilder.append(args[i]).append(number >= args.length - 1 ? "" : " ");
        }
        return stringBuilder.toString();
    }
}

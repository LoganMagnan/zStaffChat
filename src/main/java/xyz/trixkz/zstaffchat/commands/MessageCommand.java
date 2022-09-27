package xyz.trixkz.zstaffchat.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import xyz.trixkz.zstaffchat.Main;
import xyz.trixkz.zstaffchat.utils.CC;

public class MessageCommand extends Command {

    private Main main;

    public MessageCommand(Main main) {
        super("msg");

        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent(main.getSettingsConfig().getString("message-command.no-console")));

            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length == 0) {
            player.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("message-command.usage"))));
        } else {
            ProxiedPlayer target = main.getProxy().getPlayer(args[0]);

            if (target.isConnected()) {
                String message = getMessage(args, 1);
                String server = player.getServer().getInfo().getName();

                player.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("message-command.player-to-target-message")
                        .replace("(server)", server)
                        .replace("(target)", target.getName())
                        .replace("(message)", message))));

                target.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("message-command.target-to-player-message")
                        .replace("(server)", server)
                        .replace("(player)", player.getName())
                        .replace("(message)", message))));
            } else {
                player.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("message-command.not-online"))));
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

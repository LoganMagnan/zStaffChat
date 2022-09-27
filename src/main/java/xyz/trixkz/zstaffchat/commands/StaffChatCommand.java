package xyz.trixkz.zstaffchat.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import xyz.trixkz.zstaffchat.Main;
import xyz.trixkz.zstaffchat.utils.CC;

public class StaffChatCommand extends Command {

    private Main main;

    public StaffChatCommand(Main main) {
        super("staffchat");

        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent(main.getSettingsConfig().getString("staffchat-command.no-console")));

            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (main.getStaffConfig().getBoolean(player.getName())) {
            if (args.length == 0) {
                player.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("staffchat-command.usage"))));
            } else {
                String message = getMessage(args, 0);
                String server = player.getServer().getInfo().getName();

                main.getProxy().getPlayers().stream().filter(staffMembers -> main.getStaffConfig().getBoolean(staffMembers.getName())).forEach(staffMembers -> {
                    staffMembers.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("staffchat-command.message")
                            .replace("(server)", server)
                            .replace("(player)", player.getName())
                            .replace("(message)", message))));
                });
            }
        } else {
            player.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("staffchat-command.staff-members-only"))));
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

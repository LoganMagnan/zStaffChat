package xyz.trixkz.zstaffchat.listeners;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.trixkz.zstaffchat.Main;
import xyz.trixkz.zstaffchat.utils.CC;

public class RandomListeners implements Listener {

    private Main main;

    public RandomListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onChat(ChatEvent event) {
        ProxiedPlayer sender = (ProxiedPlayer) event.getSender();

        String server = sender.getServer().getInfo().getName();

        String message = event.getMessage();

        if (message.startsWith(main.getSettingsConfig().getString("staffchat.prefix"))) {
            main.getProxy().getPlayers().stream().filter(staffMembers -> main.getStaffConfig().getBoolean(staffMembers.getName())).forEach(staffMembers -> {
                event.setCancelled(true);
                staffMembers.sendMessage(new TextComponent(CC.translate(main.getSettingsConfig().getString("staffchat.message")
                        .replace("(server)", server)
                                .replace("(sender)", sender.getName())
                                        .replace("(message)", message.replace(main.getSettingsConfig().getString("staffchat.prefix"), "")))));
            });
        }
    }
}

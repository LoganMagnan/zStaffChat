package xyz.trixkz.zstaffchat.managers;

import xyz.trixkz.zstaffchat.Main;
import xyz.trixkz.zstaffchat.commands.*;

public class CommandManager {

    private Main main;

    public CommandManager(Main main) {
        this.main = main;

        registerCommands();
    }

    private void registerCommands() {
        main.getProxy().getPluginManager().registerCommand(main, new StaffCommand(main));
        main.getProxy().getPluginManager().registerCommand(main, new AdminCommand(main));
        main.getProxy().getPluginManager().registerCommand(main, new StaffChatCommand(main));
        main.getProxy().getPluginManager().registerCommand(main, new AnnounceCommand(main));
        main.getProxy().getPluginManager().registerCommand(main, new MessageCommand(main));
        main.getProxy().getPluginManager().registerCommand(main, new ZStaffChatCommand(main));
    }
}

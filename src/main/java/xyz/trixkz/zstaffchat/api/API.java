package xyz.trixkz.zstaffchat.api;

import net.md_5.bungee.config.Configuration;
import xyz.trixkz.zstaffchat.Main;

public class API {

    private Main main;

    public API(Main main) {
        this.main = main;
    }

    public Configuration getStaffConfig() {
        return main.getStaffConfig();
    }

    public Configuration getAdminConfig() {
        return main.getAdminConfig();
    }
}

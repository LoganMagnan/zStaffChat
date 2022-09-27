package xyz.trixkz.zstaffchat;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import xyz.trixkz.zstaffchat.api.API;
import xyz.trixkz.zstaffchat.listeners.RandomListeners;
import xyz.trixkz.zstaffchat.managers.CommandManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main extends Plugin {

    private File staffFile;

    private Configuration staffConfig;

    private File adminFile;

    private Configuration adminConfig;

    private File settingsFile;

    private Configuration settingsConfig;

    public API api;

    @Override
    public void onEnable() {
        api = new API(this);

        setupFiles();
        initiateManagers();
        registerEvents();
    }

    private void initiateManagers() {
        new CommandManager(this);
    }

    private void registerEvents() {
        getProxy().getPluginManager().registerListener(this, new RandomListeners(this));
    }

    private void setupFiles() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        staffFile = new File(getDataFolder(), "staff.yml");

        try {
            if (!staffFile.exists())
                Files.copy(getResourceAsStream("staff.yml"), staffFile.toPath());

            staffConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(staffFile);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        adminFile = new File(getDataFolder(), "admin.yml");

        try {
            if (!adminFile.exists())
                Files.copy(getResourceAsStream("admin.yml"), adminFile.toPath());

            adminConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(adminFile);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        settingsFile = new File(getDataFolder(), "settings.yml");

        try {
            if (!settingsFile.exists())
                Files.copy(getResourceAsStream("settings.yml"), settingsFile.toPath());

            settingsConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(settingsFile);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public File getStaffFile() {
        return staffFile;
    }

    public Configuration getStaffConfig() {
        return staffConfig;
    }

    public File getAdminFile() {
        return adminFile;
    }

    public Configuration getAdminConfig() {
        return adminConfig;
    }

    public File getSettingsFile() {
        return settingsFile;
    }

    public Configuration getSettingsConfig() {
        return settingsConfig;
    }

    public API getAPI() {
        return api;
    }
}

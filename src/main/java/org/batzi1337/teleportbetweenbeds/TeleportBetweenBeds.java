package org.batzi1337.teleportbetweenbeds;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class TeleportBetweenBeds extends JavaPlugin {
    final File bedLocationsFile = new File(getDataFolder(), "bedLocations.yml");
    final FileConfiguration fileConfiguration = getConfig();


    @Override
    public void onEnable() {
//        loadFileConfiguration();
        final BedLocationManager bedLocationManager = new BedLocationManager(fileConfiguration);
        final BedListener bedListener = new BedListener(bedLocationManager);
        final TeleportBetweenBedsCommand teleportBetweenBedsCommand = new TeleportBetweenBedsCommand(bedLocationManager);

        getServer().getPluginManager().registerEvents(bedListener, this);
        getCommand("tpbed").setExecutor(teleportBetweenBedsCommand);
    }

    @Override
    public void onDisable() {
//        saveFileConfiguration();
    }

    private void saveFileConfiguration() {
        try {
            fileConfiguration.save(bedLocationsFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadFileConfiguration() {
        try {
            fileConfiguration.load(bedLocationsFile);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}

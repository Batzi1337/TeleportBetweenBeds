package org.batzi1337.teleportbetweenbeds;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class TeleportBetweenBeds extends JavaPlugin {

    @Override
    public void onEnable() {
        final BedLocationManager bedLocationManager = new BedLocationManager(getConfig());
        final BedListener bedListener = new BedListener(bedLocationManager);
        final TeleportBetweenBedsCommand teleportBetweenBedsCommand = new TeleportBetweenBedsCommand(bedLocationManager);

        getServer().getPluginManager().registerEvents(bedListener, this);
        getCommand("tpbed").setExecutor(teleportBetweenBedsCommand);
    }

    @Override
    public void onDisable() {
        try {
            FileConfiguration configFile = getConfig();
            final File dataFolder = new File(getDataFolder(), "bedLocations.yml");
            configFile.save(dataFolder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

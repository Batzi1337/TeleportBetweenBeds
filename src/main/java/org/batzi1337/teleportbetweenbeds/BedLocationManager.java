package org.batzi1337.teleportbetweenbeds;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BedLocationManager {
    private final File configFile;
    private final Gson gson;
    private Map<UUID, List<Location>> bedLocations;

    public BedLocationManager(File configFile) {
        this.bedLocations = new HashMap<>();
        this.configFile = configFile;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        load();
    }

    public List<Location> getBedLocation(Player player) {
        return bedLocations.get(player.getUniqueId());
    }

    public void addBedLocation(UUID playerId, Location location) {
        bedLocations.computeIfAbsent(playerId, k -> new ArrayList<>()).add(location);
        save();
    }

    private void save() {
        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(bedLocations, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                bedLocations = gson.fromJson(reader, HashMap.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

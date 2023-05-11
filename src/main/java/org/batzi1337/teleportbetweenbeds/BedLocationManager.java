package org.batzi1337.teleportbetweenbeds;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BedLocationManager {
    private final FileConfiguration configFile;
    private final HashMap<UUID, List<Location>> bedLocations;

    public BedLocationManager(FileConfiguration configFile) {
        this.configFile = configFile;
//        bedLocations = (HashMap<UUID, List<Location>>) configFile.get("bedLocations.");
        bedLocations = new HashMap<>();
    }

    public List<Location> getBedLocation(Player player) {
        return bedLocations.get(player.getUniqueId());
    }

    public void addBedLocation(UUID playerId, Location location) throws IOException {
//        configFile.set("bedLocations." + playerId, location);
        bedLocations.computeIfAbsent(playerId, k -> new ArrayList<>()).add(location);
    }

    public Location findClosestBedLocationForPlayer(Player player) {
        double closestDist = Double.MAX_VALUE;
        final Location playerLocation = player.getLocation();
        Location closestBed = null;

        for (Location bedLocation : getBedLocation(player)) {
            if (bedLocation.getWorld() != player.getWorld()) {
                continue;
            }
            final Block bedBlock = bedLocation.getBlock();
            if (bedBlock.getType() == Material.WHITE_BED) {
                double dist = bedLocation.distanceSquared(playerLocation);
                if (dist < closestDist) {
                    closestDist = dist;
                    closestBed = bedLocation;
                }
            }
        }

        return closestBed;
    }
}

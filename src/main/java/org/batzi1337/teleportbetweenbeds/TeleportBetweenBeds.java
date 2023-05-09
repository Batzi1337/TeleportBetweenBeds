package org.batzi1337.teleportbetweenbeds;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class TeleportBetweenBeds extends JavaPlugin implements Listener {
    private BedLocationManager bedLocationManager;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        File configFile = new File(dataFolder, "bedLocations.json");
        bedLocationManager = new BedLocationManager(configFile);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tpbed") && sender instanceof Player player) {
            Location closestBedLocation = findClosestBedForPlayer(player);
            if (closestBedLocation != null) {
                player.teleport(closestBedLocation.add(0.5, 1, 0.5));
                player.sendMessage("Teleported to bed at " + closestBedLocation.getBlockX() + ", " + closestBedLocation.getBlockY() + ", " + closestBedLocation.getBlockZ());
            } else {
                player.sendMessage("No beds found!");
            }
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        Block bedBlock = event.getBed();
        Location bedLocation = bedBlock.getLocation();
        bedLocationManager.addBedLocation(player.getUniqueId(), bedLocation);
        player.sendMessage("Add bed at location: " + bedLocation);
    }

    private Location findClosestBedForPlayer(Player player) {
        double closestDist = Double.MAX_VALUE;
        Location playerLocation = player.getLocation();
        Location closestBed = null;

        for (Location bedLocation : bedLocationManager.getBedLocation(player)) {
            if (bedLocation.getWorld() != player.getWorld()) {
                continue;
            }
            Block bedBlock = bedLocation.getBlock();
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

package org.batzi1337.teleportbetweenbeds;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportBetweenBedsCommand implements CommandExecutor {
    private final BedLocationManager bedLocationManager;

    public TeleportBetweenBedsCommand(BedLocationManager bedLocationManager) {
        this.bedLocationManager = bedLocationManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("tpbed") && commandSender instanceof Player player) {
            Location closestBedLocation = bedLocationManager.findClosestBedLocationForPlayer(player);
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
}

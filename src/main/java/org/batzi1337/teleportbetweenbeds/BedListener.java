package org.batzi1337.teleportbetweenbeds;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.io.IOException;

public class BedListener implements Listener {
    private final BedLocationManager bedLocationManager;

    public BedListener(BedLocationManager bedLocationManager) {

        this.bedLocationManager = bedLocationManager;
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) throws IOException {
        Player player = event.getPlayer();
        Block bedBlock = event.getBed();
        Location bedLocation = bedBlock.getLocation();
        bedLocationManager.addBedLocation(player.getUniqueId(), bedLocation);
    }
}

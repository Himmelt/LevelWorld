package org.soraworld.lvlworld;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EventListener implements Listener {

    private final LevelManager manager;

    public EventListener(LevelManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (manager.stopTeleport(player, world)) {
            Location forceRespawn = manager.getForceRespawn();
            if (forceRespawn != null) {
                player.teleport(forceRespawn);
                manager.sendKey(player, "needLevelToRespawn", manager.getLevel(world), world.getName());
            } else {
                manager.consoleKey("unknownRespawnWorld");
            }
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        World world = event.getTo().getWorld();
        if (manager.stopTeleport(player, world)) {
            event.setCancelled(true);
            manager.sendKey(player, "needLevelTo", manager.getLevel(world), world.getName());
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        World world = event.getRespawnLocation().getWorld();
        if (manager.stopTeleport(player, world)) {
            Location forceRespawn = manager.getForceRespawn();
            if (forceRespawn != null) {
                player.teleport(forceRespawn);
                manager.sendKey(player, "needLevelToRespawn", manager.getLevel(world), world.getName());
            } else {
                manager.consoleKey("unknownRespawnWorld");
            }
        }
    }
}

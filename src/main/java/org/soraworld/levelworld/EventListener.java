package org.soraworld.levelworld;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EventListener implements Listener {

    private final LevelConfig config;

    public EventListener(LevelConfig config) {
        this.config = config;
    }

    @EventHandler
    public void onPlayerChangeDimension(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (config.cantTeleport(player, world)) {
            Location forceRespawn = config.getForceRespawn();
            if (forceRespawn != null) {
                player.teleport(forceRespawn);
                config.send(player, "needLevelToRespawn", config.getLevel(world), world.getName());
            } else {
                config.console("unknownRespawnWorld");
            }
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        World world = event.getTo().getWorld();
        if (config.cantTeleport(player, world)) {
            event.setCancelled(true);
            config.send(player, "needLevelTo", config.getLevel(world), world.getName());
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        World world = event.getRespawnLocation().getWorld();
        if (config.cantTeleport(player, world)) {
            Location forceRespawn = config.getForceRespawn();
            if (forceRespawn != null) {
                player.teleport(forceRespawn);
                config.send(player, "needLevelToRespawn", config.getLevel(world), world.getName());
            } else {
                config.console("unknownRespawnWorld");
            }
        }
    }

}

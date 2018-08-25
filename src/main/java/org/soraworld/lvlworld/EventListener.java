package org.soraworld.lvlworld;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class EventListener {

    private final LevelManager manager;

    public EventListener(LevelManager manager) {
        this.manager = manager;
    }

    @Listener
    public void onPlayerTeleport(MoveEntityEvent.Teleport event, @First Player player) {
        World world = event.getToTransform().getExtent();
        if (manager.stopTeleport(player, world)) {
            event.setCancelled(true);
            manager.sendKey(player, "needLevelTo", manager.getLevel(world), world.getName());
        }
    }

    @Listener
    public void onPlayerRespawn(RespawnPlayerEvent event, @First Player player) {
        World world = event.getToTransform().getExtent();
        if (manager.stopTeleport(player, world)) {
            Location<World> forceRespawn = manager.getForceRespawn().toLocation();
            if (forceRespawn != null) {
                event.setToTransform(new Transform<>(forceRespawn));
                manager.sendKey(player, "needLevelToRespawn", manager.getLevel(world), world.getName());
            } else {
                manager.consoleKey("unknownRespawnWorld");
            }
        }
    }
}

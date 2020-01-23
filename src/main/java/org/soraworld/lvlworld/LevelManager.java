package org.soraworld.lvlworld;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.soraworld.hocon.node.Setting;
import org.soraworld.violet.manager.SpigotManager;
import org.soraworld.violet.util.ChatColor;

import java.nio.file.Path;
import java.util.HashMap;

/**
 * @author Himmelt
 */
public class LevelManager extends SpigotManager {

    @Setting(comment = "comment.defaultLevel")
    private int defaultLevel = 0;
    @Setting(comment = "comment.forceRespawn")
    private Location forceRespawn = null;
    @Setting(comment = "comment.levels")
    private HashMap<String, Integer> levels = new HashMap<>();

    public LevelManager(LevelWorld plugin, Path path) {
        super(plugin, path);
    }

    @Override
    public ChatColor defChatColor() {
        return ChatColor.GREEN;
    }

    @Override
    public void beforeLoad() {
        options.registerType(new LocationSerializer());
    }

    @Override
    public void afterLoad() {
        if (forceRespawn == null) {
            consoleKey("unknownRespawnWorld");
        }
    }

    public boolean stopTeleport(Player player, World world) {
        if (player != null && world != null && !player.hasPermission("lvlworld.bypass")) {
            if (forceRespawn == null || !world.equals(forceRespawn.getWorld())) {
                return player.getLevel() < levels.getOrDefault(world.getName(), defaultLevel);
            }
        }
        return false;
    }

    public Location getForceRespawn() {
        return forceRespawn;
    }

    public void setForceRespawn(Location location) {
        forceRespawn = location;
        save();
    }

    public int getLevel(World world) {
        return levels.getOrDefault(world.getName(), defaultLevel);
    }

    public void setLevel(World world, int lvl) {
        levels.put(world.getName(), lvl);
        save();
    }

    public int getDefaultLevel() {
        return defaultLevel;
    }

    public void setDefaultLevel(int level) {
        this.defaultLevel = level;
        save();
    }
}

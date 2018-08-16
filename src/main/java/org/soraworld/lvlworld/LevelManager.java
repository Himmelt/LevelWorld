package org.soraworld.lvlworld;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.soraworld.hocon.node.Setting;
import org.soraworld.violet.api.IPlugin;
import org.soraworld.violet.manager.VioletManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.HashMap;

public class LevelManager extends VioletManager {

    @Setting(comment = "comment.defaultLevel")
    private int defaultLevel = 0;
    @Setting(comment = "comment.forceRespawn")
    private Location forceRespawn = null;
    @Setting(comment = "comment.levels")
    private HashMap<String, Integer> levels = new HashMap<>();

    public LevelManager(IPlugin plugin, Path path) {
        super(plugin, path);
    }

    @Nonnull
    public String defChatHead() {
        return "[LevelWorld] ";
    }

    @Nullable
    public String defAdminPerm() {
        return "lvlworld.admin";
    }

    @Nonnull
    public ChatColor defChatColor() {
        return ChatColor.GREEN;
    }

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

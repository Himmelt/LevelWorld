package org.soraworld.lvlworld;

import org.soraworld.hocon.node.Setting;
import org.soraworld.violet.manager.SpongeManager;
import org.soraworld.violet.util.ChatColor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.util.HashMap;

public class LevelManager extends SpongeManager {

    @Setting(comment = "comment.defaultLevel")
    private int defaultLevel = 0;
    @Setting(comment = "comment.forceRespawn")
    private Location forceRespawn = null;
    @Setting(comment = "comment.levels")
    private HashMap<String, Integer> levels = new HashMap<>();

    public LevelManager(LevelWorld plugin, Path path) {
        super(plugin, path);
    }

    @Nonnull
    public ChatColor defChatColor() {
        return ChatColor.GREEN;
    }

    public void beforeLoad() {
        options.registerType(new Location.Serializer());
    }

    public void afterLoad() {
        if (forceRespawn == null) {
            consoleKey("unknownRespawnWorld");
        }
    }

    public boolean stopTeleport(Player player, World world) {
        if (player != null && world != null && !player.hasPermission("lvlworld.bypass")) {
            if (forceRespawn == null || !world.equals(forceRespawn.getWorld())) {
                return player.get(Keys.EXPERIENCE_LEVEL).orElse(0) < levels.getOrDefault(world.getName(), defaultLevel);
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

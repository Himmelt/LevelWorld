package org.soraworld.lvlworld;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.soraworld.hocon.node.*;
import org.soraworld.hocon.serializer.TypeSerializer;
import org.soraworld.violet.api.IPlugin;
import org.soraworld.violet.manager.VioletManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.HashMap;

import static org.bukkit.util.NumberConversions.toDouble;
import static org.bukkit.util.NumberConversions.toFloat;

public class LevelManager extends VioletManager {

    @Setting(comment = "comment.defaultLevel")
    private int defaultLevel = 0;
    @Setting(comment = "comment.forceRespawn")
    private Location forceRespawn = null;
    @Setting(comment = "comment.levels")
    private HashMap<String, Integer> levels = new HashMap<>();

    public LevelManager(IPlugin plugin, Path path) {
        super(plugin, path);
        options.getSerializers().registerType(Location.class, new TypeSerializer<Location>() {
            @Override
            public Location deserialize(@Nonnull Type type, @Nonnull Node node) {
                if (node instanceof NodeMap) {
                    HashMap<String, String> map = ((NodeMap) node).asStringMap();
                    World world = Bukkit.getWorld(map.get("world"));
                    if (world != null) {
                        return new Location(world, toDouble(map.get("x")), toDouble(map.get("y")), toDouble(map.get("z")), toFloat(map.get("yaw")), toFloat(map.get("pitch")));
                    }
                }
                return null;
            }

            @Override
            public Node serialize(@Nonnull Type type, Location loc, @Nonnull NodeOptions options) {
                if (loc != null) {
                    NodeMap map = new NodeMap(options);
                    map.setNode("world", loc.getWorld().getName());
                    map.setNode("x", loc.getX());
                    map.setNode("y", loc.getY());
                    map.setNode("z", loc.getZ());
                    map.setNode("yaw", loc.getYaw());
                    map.setNode("pitch", loc.getPitch());
                    return map;
                } else return new NodeBase(options, null, false);
            }
        });
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

package org.soraworld.levelworld;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.soraworld.violet.config.IIConfig;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LevelConfig extends IIConfig {

    private int defaultLevel = 0;
    private Location forceRespawn = null;
    private final HashMap<String, Integer> levels = new HashMap<>();

    public LevelConfig(File path, Plugin plugin) {
        super(path, plugin);
    }

    protected void loadOptions() {
        levels.clear();
        defaultLevel = config_yaml.getInt("default", 0);
        ConfigurationSection secForce = config_yaml.getConfigurationSection("forceRespawn");
        if (secForce != null) {
            try {
                forceRespawn = Location.deserialize(secForce.getValues(false));
            } catch (Exception ignored) {
                console("unknownRespawnWorld");
            }
        }
        ConfigurationSection section = config_yaml.getConfigurationSection("levels");
        if (section != null) {
            for (String world : section.getKeys(false)) {
                levels.put(world, section.getInt(world, 0));
            }
        }
    }

    protected void saveOptions() {
        config_yaml.set("default", defaultLevel);
        if (forceRespawn != null) config_yaml.set("forceRespawn", forceRespawn.serialize());
        ConfigurationSection section = config_yaml.createSection("levels");
        for (Map.Entry<String, Integer> entry : levels.entrySet()) {
            section.set(entry.getKey(), entry.getValue());
        }
    }

    public void afterLoad() {

    }

    protected ChatColor defaultChatColor() {
        return ChatColor.GREEN;
    }

    protected String defaultChatHead() {
        return "LevelWorld";
    }

    public String defaultAdminPerm() {
        return "lvlworld.admin";
    }

    public boolean cantTeleport(Player player, World world) {
        if (player != null && world != null) {
            int need = levels.getOrDefault(world.getName(), defaultLevel);
            return player.getLevel() < need;
        }
        return true;
    }

    public Location getForceRespawn() {
        return forceRespawn;
    }

    public void setForceRespawn(Location location) {
        this.forceRespawn = location;
    }

    public int getLevel(World world) {
        return levels.getOrDefault(world.getName(), defaultLevel);
    }

    public void setLevel(World world, int lvl) {
        levels.put(world.getName(), lvl);
    }

    public int getDefaultLevel() {
        return defaultLevel;
    }

    public void setDefaultLevel(int level) {
        this.defaultLevel = level;
    }
}

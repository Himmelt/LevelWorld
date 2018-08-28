package org.soraworld.lvlworld;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.soraworld.violet.command.Paths;
import org.soraworld.violet.command.Sub;
import org.soraworld.violet.manager.SpigotManager;

public class LevelCommand {
    @Sub(perm = "admin", onlyPlayer = true)
    public static void level(SpigotManager manager, CommandSender source, Paths args) {
        if (manager instanceof LevelManager && source instanceof Player) {
            LevelManager level = (LevelManager) manager;
            Player player = (Player) source;
            if (args.notEmpty()) {
                try {
                    int lvl = Integer.valueOf(args.get(0));
                    level.setLevel(player.getWorld(), lvl < 0 ? 0 : lvl);
                    level.sendKey(player, "setLevel", level.getLevel(player.getWorld()));
                } catch (Throwable ignored) {
                    level.sendKey(player, "invalidInt");
                }
            } else level.sendKey(player, "getLevel", level.getLevel(player.getWorld()));
        }
    }

    @Sub(paths = {"default"}, perm = "admin", onlyPlayer = true)
    public static void fun_default(SpigotManager manager, CommandSender source, Paths args) {
        if (manager instanceof LevelManager && source instanceof Player) {
            LevelManager level = (LevelManager) manager;
            Player player = (Player) source;
            if (args.notEmpty()) {
                try {
                    int lvl = Integer.valueOf(args.get(0));
                    level.setDefaultLevel(lvl < 0 ? 0 : lvl);
                    level.sendKey(player, "setDefault", level.getDefaultLevel());
                } catch (Throwable ignored) {
                    level.sendKey(player, "invalidInt");
                }
            } else level.sendKey(player, "getDefault", level.getDefaultLevel());
        }
    }

    @Sub(perm = "admin", onlyPlayer = true)
    public static void force(SpigotManager manager, CommandSender source, Paths args) {
        if (manager instanceof LevelManager && source instanceof Player) {
            LevelManager level = (LevelManager) manager;
            Player player = (Player) source;
            level.setForceRespawn(player.getLocation());
            manager.sendKey(player, "setForceRespawn");
        }
    }
}

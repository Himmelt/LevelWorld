package org.soraworld.lvlworld;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.soraworld.violet.command.Args;
import org.soraworld.violet.command.SpigotCommand;
import org.soraworld.violet.command.Sub;

/**
 * @author Himmelt
 */
public final class LevelCommand {
    @Sub(perm = "admin", onlyPlayer = true)
    public static void level(SpigotCommand self, CommandSender sender, Args args) {
        LevelManager manager = (LevelManager) self.manager;
        Player player = (Player) sender;
        if (args.notEmpty()) {
            try {
                int lvl = Integer.parseInt(args.get(0));
                manager.setLevel(player.getWorld(), Math.max(lvl, 0));
                manager.sendKey(player, "setLevel", manager.getLevel(player.getWorld()));
            } catch (Throwable ignored) {
                manager.sendKey(player, "invalidInt");
            }
        } else {
            manager.sendKey(player, "getLevel", manager.getLevel(player.getWorld()));
        }
    }

    @Sub(path = "default", perm = "admin", onlyPlayer = true)
    public static void funDefault(SpigotCommand self, CommandSender sender, Args args) {
        LevelManager manager = (LevelManager) self.manager;
        Player player = (Player) sender;
        if (args.notEmpty()) {
            try {
                int lvl = Integer.parseInt(args.get(0));
                manager.setDefaultLevel(Math.max(lvl, 0));
                manager.sendKey(player, "setDefault", manager.getDefaultLevel());
            } catch (Throwable ignored) {
                manager.sendKey(player, "invalidInt");
            }
        } else {
            manager.sendKey(player, "getDefault", manager.getDefaultLevel());
        }
    }

    @Sub(perm = "admin", onlyPlayer = true)
    public static void force(SpigotCommand self, CommandSender sender, Args args) {
        LevelManager manager = (LevelManager) self.manager;
        Player player = (Player) sender;
        manager.setForceRespawn(player.getLocation());
        manager.sendKey(player, "setForceRespawn");
    }
}

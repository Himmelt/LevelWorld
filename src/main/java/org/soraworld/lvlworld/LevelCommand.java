package org.soraworld.lvlworld;

import org.soraworld.violet.command.Paths;
import org.soraworld.violet.command.Sub;
import org.soraworld.violet.manager.SpongeManager;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;

public class LevelCommand {
    @Sub(perm = "admin", onlyPlayer = true)
    public static void level(SpongeManager manager, CommandSource source, Paths args) {
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
    public static void fun_default(SpongeManager manager, CommandSource source, Paths args) {
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
    public static void force(SpongeManager manager, CommandSource source, Paths args) {
        if (manager instanceof LevelManager && source instanceof Player) {
            LevelManager level = (LevelManager) manager;
            Player player = (Player) source;
            level.setForceRespawn(new Location(player.getLocation()));
            manager.sendKey(player, "setForceRespawn");
        }
    }
}

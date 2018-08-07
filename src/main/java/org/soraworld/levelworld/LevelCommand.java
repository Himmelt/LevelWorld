package org.soraworld.levelworld;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.soraworld.violet.command.CommandViolet;
import org.soraworld.violet.command.IICommand;
import org.soraworld.violet.constant.Violets;

import java.util.ArrayList;

public class LevelCommand extends CommandViolet {
    public LevelCommand(String name, String perm, LevelConfig config, Plugin plugin) {
        super(name, perm, config, plugin);
        addSub(new IICommand("level", perm, config, true) {
            public boolean execute(Player player, ArrayList<String> args) {
                if (args.isEmpty()) {
                    config.send(player, "getLevel", config.getLevel(player.getWorld()));
                } else {
                    try {
                        int lvl = Integer.valueOf(args.get(0));
                        config.setLevel(player.getWorld(), lvl < 0 ? 0 : lvl);
                        config.send(player, "setLevel", config.getLevel(player.getWorld()));
                    } catch (Throwable ignored) {
                        config.sendV(player, Violets.KEY_INVALID_INT);
                    }
                }
                return true;
            }
        });
        addSub(new IICommand("default", perm, config, true) {
            public boolean execute(Player player, ArrayList<String> args) {
                if (args.isEmpty()) {
                    config.send(player, "getDefault", config.getDefaultLevel());
                } else {
                    try {
                        int lvl = Integer.valueOf(args.get(0));
                        config.setDefaultLevel(lvl < 0 ? 0 : lvl);
                        config.send(player, "setDefault", config.getDefaultLevel());
                    } catch (Throwable ignored) {
                        config.sendV(player, Violets.KEY_INVALID_INT);
                    }
                }
                return true;
            }
        });
        addSub(new IICommand("force", perm, config, true) {
            public boolean execute(Player player, ArrayList<String> args) {
                config.setForceRespawn(player.getLocation());
                config.send(player, "setForceRespawn");
                return true;
            }
        });
    }
}

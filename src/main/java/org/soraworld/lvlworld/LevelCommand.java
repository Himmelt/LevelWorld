package org.soraworld.lvlworld;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.soraworld.violet.Violets;
import org.soraworld.violet.command.CommandArgs;
import org.soraworld.violet.command.ICommand;
import org.soraworld.violet.command.VioletCommand;

public class LevelCommand extends VioletCommand {
    public LevelCommand(String perm, boolean onlyPlayer, LevelManager level, String... aliases) {
        super(perm, onlyPlayer, level, aliases);
        addSub(new ICommand(perm, true, level, "level") {
            public void execute(Player player, CommandArgs args) {
                if (args.notEmpty()) {
                    try {
                        int lvl = Integer.valueOf(args.get(0));
                        level.setLevel(player.getWorld(), lvl < 0 ? 0 : lvl);
                        manager.sendKey(player, "setLevel", level.getLevel(player.getWorld()));
                    } catch (Throwable ignored) {
                        manager.sendKey(player, Violets.KEY_INVALID_INT);
                    }
                } else manager.sendKey(player, "getLevel", level.getLevel(player.getWorld()));
            }
        });
        addSub(new ICommand(perm, true, level, "default") {
            public void execute(Player player, CommandArgs args) {
                if (args.empty()) {
                    try {
                        int lvl = Integer.valueOf(args.get(0));
                        level.setDefaultLevel(lvl < 0 ? 0 : lvl);
                        manager.sendKey(player, "setDefault", level.getDefaultLevel());
                    } catch (Throwable ignored) {
                        manager.sendKey(player, Violets.KEY_INVALID_INT);
                    }
                } else manager.sendKey(player, "getDefault", level.getDefaultLevel());
            }
        });
        addSub(new ICommand(perm, true, level, "force") {
            public void execute(Player player, CommandArgs args) {
                level.setForceRespawn(player.getLocation());
                manager.sendKey(player, "setForceRespawn");
            }
        });
    }

    protected void sendUsage(CommandSender sender) {
        manager.sendKey(sender, Violets.KEY_CMD_USAGE, "/lvlworld level|default|force");
    }
}
